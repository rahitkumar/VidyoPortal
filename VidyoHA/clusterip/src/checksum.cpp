#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<pthread.h>
#include<string.h>
#include<fcntl.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<netinet/tcp.h>
#include<errno.h>

#include"params.h"
#include"clusterip.h"
#include"clusterinfo.h"
#include"miscutils.h"
#include"checksum.h"
#include"reqmsg.h"

extern CClusterInfo clusterInfo;
extern int NewChecksumAvailable;

extern CMiscUtils cmu;
extern pthread_mutex_t csum_mutex;
extern pthread_cond_t csum_mutex_cond;

void NamVal(char *buf, char *name, char *value);
int GetNamValFromFile(const char *filename, const char *name, char *val);


//! \brief This thread updates the checksum in the cluster memory. 
//! The cip_admin(cluster_admin) need to send a command to clusterip
//! to wakeup this sleeping thread.  This thread is using 
//! conditional mutex.
//! 
void *checksum(void *args)
{
   CParams *cp = (CParams *)args;

   char backupfile[256];
   char csum[64];

   snprintf(backupfile, 256, "%s/PORTAL_BACKUP.vidyo", cp->Backupdir()); 

   cmu.LogMsg( INFO, "checksum thread started...");
   while(1){

      if ( cmu.FileCheckSum( backupfile, csum ) == NULL ) {
         cmu.LogErr( "Failed to get the checksum of (%s).", backupfile);
         cmu.LogMsg( INFO, "Failed to get the checksum of (%s).", backupfile);
      }else{
         cmu.LogMsg( INFO, "Backup File: (%s): CSUM:(%s)", backupfile, csum);
         clusterInfo.MyBackupChecksum( csum );
         clusterInfo.MyLastState( clusterInfo.State() );
         clusterInfo.UpdateLastSyncTime();
         
         /// update the duration of backup or syncing...
         if ( clusterInfo.MyRole() == ACTIVE ) 
            BackupDuration( cp );
         else
            SyncDuration();


      }
      cmu.LogMsg( INFO, "Waiting for new checksum ...");
      memset(csum, 0, sizeof(csum));
      pthread_mutex_lock( &csum_mutex );
      pthread_cond_wait( &csum_mutex_cond, & csum_mutex );
      cmu.LogMsg( INFO, "New Checksum Available...");
      pthread_mutex_unlock( &csum_mutex );

   }
}


//! \brief Returns how long in seconds the previous backup took place. This code will execute on ACTIVE node only.
int BackupDuration( CParams *cp )
{
   char backup_info[256];
   char val[128];
   int ret = 0;

   memset(backup_info, 0, sizeof(backup_info));

   snprintf(backup_info, 256, "%s/PORTAL_BACKUP.info", cp->Backupdir());

   if ( GetNamValFromFile(backup_info, "DURATION", val) == 1 ) {
      clusterInfo.BackupDuration( atoi( val ) );
      ret = 1;
   }
   

   return ret;
}

//! \brief Returns how long does the STANDBY node syncs the DB.
int SyncDuration()
{
   const char *SYNC_INFO="/tmp/sync_backup.info";
   char val[128];
   int ret = 0;

   if ( GetNamValFromFile(SYNC_INFO, "DURATION", val) == 1 ) {
      clusterInfo.SyncDuration( atoi( val ) );
      ret = 1;
   }

   return ret;
}

//! \brief Parse the name/value pair buf and returns the name and value. 
void NamVal(char *buf, char *name, char *value)
{
   char *saveptr;
   char *ptr;

   ptr = (char *)strtok_r(buf, "=", &saveptr);
   if ( ptr == NULL )
      return;

   strcpy( name, ptr );

   ptr = (char *)strtok_r(NULL, "\n", &saveptr);
   if ( ptr == NULL )
      return;

   strcpy(value, ptr);
}


//! \brief Reads the a file containing a data in name/value pair format.
//! returns 1 if a valid name is found, otherwise return 0.
int GetNamValFromFile(const char *filename, const char *pname, char *val)
{
   FILE *fp;
   char buf[128];
   char name[128];
   int ret = 0;

   if ( (fp = fopen(filename, "r")) == NULL )
      return -1;

   while(1) {
      memset(buf, 0, sizeof(buf));
      memset(name, 0, sizeof(name));
      if ( fgets(buf, sizeof(buf), fp ) == NULL ) 
         break;

      NamVal(buf, name, val);
      if ( ! strcmp(name, pname ) ) {
         ret = 1;
         break;
      }
   }

   fclose( fp );
   return ret;
}

//! \brief Set the DB version of the cluster global memory. This function reads the /opt/vidyo/ha/db_version
//!        file which is created by the ha_portal.sh
int SetDBVersion()
{
   char val[128];
   int ret = 0;
   const char *db_version_file="/opt/vidyo/ha/db_version";

   if ( GetNamValFromFile(db_version_file, "DB_VERSION", val) == 1 ) {
      clusterInfo.MyDBVersion( val );
      ret = 1;
   }

   return ret;
}

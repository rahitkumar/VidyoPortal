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


void NamVal(char *buf, char *name, char *value);
int GetNamValFromFile(const char *filename, const char *name, char *val);


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
   CClusterInfo *clusterinfo=CClusterInfo::GetInstance();
   char val[128];
   int ret = 0;
   const char *db_version_file="/opt/vidyo/ha/db_version";

   if ( GetNamValFromFile(db_version_file, "DB_VERSION", val) == 1 ) {
      clusterinfo->MyDBVersion( val );
      ret = 1;
   }

   return ret;
}

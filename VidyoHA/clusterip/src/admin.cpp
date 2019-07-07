#include<pthread.h>
#include<string.h>
#include<stdlib.h>
#include"clusterip.h"
#include"csocket.h"
#include"clusterinfo.h"

extern CClusterInfo clusterInfo;
extern pthread_mutex_t csum_mutex;
extern pthread_cond_t csum_mutex_cond;


void admin_menu( CSocket s );

void *admin(void *args)
{
   CSocket cSock;
   CMiscUtils cmu;
 
   if ( !cSock.Create() ){
      cmu.LogErr("Failed to create socket for admin thread!!!");
      exit(1);   
   }

   //if ( ! cSock.Bind( 9999, "0.0.0.0." ) ) {
   if ( ! cSock.Bind( 9999, "127.0.0.1" ) ) {
      cmu.LogErr("Failed to bind socket for admin thread!!!");
      exit(1);   
   }

   if ( ! cSock.Listen() ) {
      cmu.LogErr("Failed to create a listen socket for admin thread!!!");
      exit(1);   
   }

   while(1){
      CSocket newSock;
      if ( ! cSock.Accept( newSock ) ) {
         cmu.LogErr("Failed to Accept new connection...\n");
         break;
         //TODO: restart admin thread or restart the whole apps...
      }

      printf("Received new socket connection...\n");
      //clusterInfo.ClusterDataToSocket( newSock.GetSocketID() );
      admin_menu( newSock );

      newSock.Close();

   }
   return (void *)NULL;
}

void admin_menu( CSocket s )
{
   char menu[512];
   unsigned char buf[512];
   char temp[128];
   bool loop = true;
   int len;
   CMiscUtils cmu;
   
   while( loop ) {
      //sprintf(menu, "Status = [%c]", clusterInfo.MyStatus() );
      //s.Send( menu );

      //if ( clusterInfo.MyStatus() == ONLINE_STATUS ) 
      //   snprintf(menu, 512, "\nAdmin Menu\n----------\n\nS - Set node to MAINTENANCE\nP - Print Cluster Infomation\nW - Cluster Data(Wide)\nC - Send DB Checksum\nX - Exit\n\n");
      //else
      //   snprintf(menu, 512, "\nAdmin Menu\n----------\n\nO - Set node to ONLINE\nP - Print Cluster Infomation\nW - Cluster Data(Wide)\nC - Send DB Checksum\nX - Exit\n\n");
   
      len = s.Recv( buf );
      if ( len <= 0 ) 
         break;

      if ( !strncmp((char *)buf, "CINFO-P", 7 ) ) {
          clusterInfo.ClusterDataToSocket( s.GetSocketID() );
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-W", 7 ) ) {
          clusterInfo.ClusterDataToSocketWide( s.GetSocketID() );
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-S", 7 ) ) {  // ** set node to maintenance
          if ( clusterInfo.MyStatus() == MAINTENANCE_STATUS )
             break;

          clusterInfo.MyStatus( MAINTENANCE_STATUS );
          clusterInfo.State( MAINTENANCE_STATE );
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-O", 7 ) ) {  // ** remove node from maintenance
          if ( clusterInfo.MyStatus() == MAINTENANCE_STATUS ) {
             clusterInfo.Reset();
             clusterInfo.State( ONLINE_STATE );
          }
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-C", 7 ) ) {  // ** send DB checksum to peer
          pthread_cond_signal( &csum_mutex_cond );
          if ( clusterInfo.MyStatus() == MAINTENANCE_STATUS ) {
          }
          break;
      }

      if ( !strncmp((char *)buf, "CINFO-F", 7 ) ) {  // ** force ACTIVE node to go STANDBY
          if ( clusterInfo.MyStatus() == ONLINE_STATUS && 
               clusterInfo.MyRole() == ACTIVE && 
               clusterInfo.PeerStatus() == ONLINE_STATUS ) {
             //clusterInfo.Reset();
             clusterInfo.State( ACTIVE_TO_STANDBY );
             clusterInfo.SetForceStandbyTime();
             cmu.LogMsg( INFO, "Force STANDBY initiated by the user...");
          }else {
             cmu.LogMsg( INFO, "Force STANDBY is not allowed at this time...");
          }
          break;
      }

      if ( strlen((char *)buf) > 0 )
         break;

      //s.Send( menu );
      //memset( buf, 0, sizeof(buf) );
      //if ( s.Recv( buf ) < 0 )
      //   break;

      //switch( *buf ) {
      //case 'p':
      //case 'P':
      //    clusterInfo.ClusterDataToSocket( s.GetSocketID() );
      //    break;
      //case 'w':
      //case 'W':
      //    clusterInfo.ClusterDataToSocketWide( s.GetSocketID() );
      //    break;
      //case 'S':
      //case 's':
      //    strcpy( temp, "Set node to standby (y/n) ?" );
      //    s.Send( temp );
      //    clusterInfo.MyStatus( MAINTENANCE_STATUS );
      //    clusterInfo.State( MAINTENANCE_STATE );
      //    break;
      //case 'o':
      //case 'O':
      //    strcpy( temp, "Remove node from standby (y/n) ?" );
      //    s.Send( temp );
      //    clusterInfo.Reset();
      //    clusterInfo.State( ONLINE_STATE );
      //    break;
      //case 'x':
      //case 'X':
      //    loop = false;
      //}
   }
}

#include<string.h>
#include<stdlib.h>
#include<stdio.h>
#include<getopt.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <sys/types.h>
#include <unistd.h>
#include<signal.h>

//#include"miscutils.h"

//CMiscUtils *CMiscUtils::pInstance = NULL;

void sigHandler(int sig);

const char *version="2.0.0";
char socket_name[128];
char command[64];
char event_command[64];
char repl_status[64];
char cmd[64];

int LocalClientSocket();
void ParseArgs(int argc, char **argv);
void Help();

int main(int argc, char **argv)
{
   int len;
   int sd;
   int ret;
 //  CMiscUtils *cmu = CMiscUtils::Initialize();
   unsigned char buffer[2048];

   ParseArgs(argc, argv);

   if ( strlen(event_command) > 0 )
      strcpy(command, event_command);
   else if ( strlen(repl_status) > 0 )
      strcpy(command, repl_status);
   else
      sprintf(command, "CINFO-%c", *cmd);

   signal(SIGALRM, sigHandler);

   alarm(2);
   if ( (sd = LocalClientSocket() ) < 0 ){
      printf("Failed to connect!");
      exit(1);
   }

   ret = write(sd, command, sizeof(command)-1);
   if ( ret < 0 ) 
      perror("write");

   memset( buffer, 0, sizeof(buffer) );
   if ( (len = recv(sd, buffer, sizeof(buffer)-1, 0) ) > 0 ) {
      printf("%s\n", buffer);
   }

   close(sd);
   alarm(0);
}


void ParseArgs(int argc, char **argv)
{
 int c;
   int optind = 0;

   static struct option long_options[] = {
      {"socket", 1, 0, 'i' },
      {"command", 1, 0, 'c' },
      {"version", 0, 0, 'v' },
      {"event", 1, 0, 'e' },
      {"replstat", 1, 0, 'r' },
      {"help", 0, 0, 'h' },
      { 0, 0, 0, }
   };

   memset(cmd, 0, sizeof(cmd));
   memset(socket_name, 0, sizeof(socket_name));
   strcpy(socket_name, "/var/run/clusterip.socket");
   memset(event_command, 0, sizeof(event_command));
   memset(repl_status, 0, sizeof(repl_status));

   while(1) {
      if ( (c = getopt_long(argc, argv, "e:s:c:r:hv",  long_options, &optind )) < 0 )
         break;

      switch(c) {
      case 's':
         strncpy(socket_name, optarg, sizeof(socket_name)-1);
         break;
      case 'c':
         strncpy(cmd, optarg, 16);
         break;
      case 'e':
         snprintf(event_command, 32, "CEVENT-%s", optarg);
         break;
      case 'r':
         snprintf(repl_status, 63, "REPL_STATUS-%s", optarg);
         break;
      case 'v':
         printf("cip_admin ver: %s\n", version);
         exit(1);
         break;
      case 'h':
         Help();
      }
   }

   if ( strlen(event_command) >  0 )
      return;

   if ( strlen(repl_status) >  0 )
      return;

   if ( strlen( cmd ) <= 0 )
      Help();
}


void Help()
{
   printf("Usage: cip_admin [OPTION]...\n");
   printf("Command line to set/get information from the clusterip application\n\n");
   printf("OPTIONS:\n");
   printf("-s, --socket    Local socket name (PF_UNIX). Default (/var/run/clusterip.socket)\n");
   printf("-c, --command   Commands available to the cluster ip.\n");
   printf("                valid commands: O, P, S, W\n");
   printf("                O - remove node from standby state.\n");
   printf("                P - Display Cluster Information in name=value format.\n"); 
   printf("                W - Display Cluster Information in wide format.\n");
   printf("                F - Force Standby.\n");
   printf("                S - Set node to standby. Use with care.\n");
   printf("-e, --event     Valid events: 1,2,3,4,5,6,7,8,9,10,11,12,13\n");
   printf("                1 - NPNG (Link Test Failed)\n");
   printf("                2 - PNG (Link Test OK)\n");
   printf("                3 - NHB (No Heartbeat)\n");
   printf("                4 - HB_A_L (HB from ACTIVE with link ok)\n");
   printf("                5 - HB_S_L (HB from STANDBY with link ok)\n");
   printf("                6 - HB_S_NL (HB from STANDBY with no link)\n");
   printf("                7 - HB_A_NL (HB from ACTIVE with no link)\n");
   printf("                8 - HB_NAK (Rejected to become ACTIVE)\n");
   printf("                9 - HB_ACK (Accepted to become ACTIVE)\n");
   printf("                10 - HB_REQ (Requesting to become ACTIVE)\n");
   printf("                11 - HB_A2S (ACTIVE going to STANDBY.  STANDBY prepare to become ACTIVE)\n");
   printf("                12 - MAINT (Put node into maintenance)\n");
   printf("                13 - F_S_B (Force standby)\n");
   printf("-r, --replstat <status>\n");
   printf("-v, --version   Show version.\n");
   printf("-h, --help      Show help.\n"); 
   exit(0);
}


int LocalClientSocket()
{
   struct sockaddr_un address;
   int sd;

   sd = socket(PF_UNIX, SOCK_STREAM, 0);
   if ( sd < 0 ) {
      perror("socket!");
      exit(1);
   }

   memset(&address, 0, sizeof(struct sockaddr_un));
   address.sun_family = AF_UNIX;
   strncpy(address.sun_path, socket_name, sizeof(address.sun_path)-1);

   if ( connect(sd, (struct sockaddr *)&address, sizeof(address) ) < 0 ) {
      return -1;
   }

   return sd;
}


void sigHandler(int sig)
{
   exit(1);
}

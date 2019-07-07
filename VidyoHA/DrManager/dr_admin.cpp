#include<string.h>
#include<stdlib.h>
#include<stdio.h>
#include<getopt.h>
#include <sys/socket.h>
#include <sys/un.h>
#include <sys/types.h>
#include <unistd.h>
#include<signal.h>

void sigHandler(int sig);

const char *version="1.0.0";
char socket_name[128];
char command[64];
char event_command[64];
char cmd[64];

int LocalClientSocket();
void ParseArgs(int argc, char **argv);
void Help();

int main(int argc, char **argv)
{
   int len;
   int sd;
   unsigned char buffer[2048];

   if ( getuid() != 0 ) {
      printf("Must be root to run this program!\n");
      exit(1);
   }   

   memset(command, 0, sizeof(command));
   memset(event_command, 0, sizeof(event_command));
   ParseArgs(argc, argv);

   if ( strlen(event_command) > 0 )
      strncpy(command, event_command, sizeof(command) -1 );
   else
      sprintf(command, "DRCMD-%c", *cmd);

   signal(SIGALRM, sigHandler);

   alarm(2);
   if ( (sd = LocalClientSocket() ) < 0 ){
      printf("Failed to connect!");
      exit(1);
   }

   //printf("Sending Command: %s\n", command);
   write(sd, command, strlen(command));

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
      {"help", 10, 0, 'h' },
      { 0, 0, 0, }
   };

   memset(cmd, 0, sizeof(cmd));
   memset(socket_name, 0, sizeof(socket_name));
   strcpy(socket_name, "/var/run/drmgr.sock");
   memset(event_command, 0, sizeof(event_command));

   while(1) {
      if ( (c = getopt_long(argc, argv, "e:s:c:hv",  long_options, &optind )) < 0 )
         break;

      switch(c) {
      case 's':
         strncpy(socket_name, optarg, sizeof(socket_name)-1);
         break;
      case 'c':
         strncpy(cmd, optarg, 16);
         break;
      case 'e':
         snprintf(event_command, 32, "DREVENT-%s", optarg);
         break;
      case 'v':
         printf("dr_admin ver: %s\n", version);
         exit(1);
         break;
      case 'h':
         Help();
      }
   }

   if ( strlen(event_command) >  0 )
      return;

   if ( strlen( cmd ) <= 0 )
      Help();
}


void Help()
{
   printf("Usage: dr_admin [OPTION]...\n");
   printf("Command line to set/get information from the drmgr application\n\n");
   printf("OPTIONS:\n");
   printf("-s, --socket    Local socket name (PF_UNIX). Default (/var/run/drmgr.sock)\n");
   printf("-c, --command   Commands available to the drmgr.\n");
   printf("                S - Get the current state.\n");
   printf("-e, --event     Valid events: 1,2,3,4,5,6,7,8,9,10,11\n");
   printf("                1 - MAINT (Start Maintenance)\n");
   printf("                2 - MAINT_END (End Maintenance)\n");
   printf("                3 - MATCH_PUBIP (Match Public IP)\n");
   printf("                4 - NO_MATCH_PUBIP (Public IP do not match)\n");
   printf("                5 - C_PUBIP (Public FQDN resolves to a new IP address)\n");
   printf("                6 - NO_DNS (NO Response from DNS Server or ADC)\n");
   printf("                7 - REPL_OK (DB and Filesystem Replication OK)\n");
   printf("                8 - REPL_FAIL (DB or Filesystem Replication failure)\n");
   printf("                9 - REPL_STOP (DB or Filesystem Replication has been stopped)\n");
   printf("               10 - PUBKEY_OK (Public keys imported successfully )\n");
   printf("               11 - ACTIVE_READY (Public keys imported successfully )\n");
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

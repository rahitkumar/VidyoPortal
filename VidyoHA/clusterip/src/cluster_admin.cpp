#include<string.h>
#include<stdlib.h>
#include<stdio.h>
#include<getopt.h>
#include"csocket.h"

int CMiscUtils::RunLevel=0;

int port = 9999;
const char *version="1.0.0";
char ip[64];
char command[64];
char cmd[64];

void ParseArgs(int argc, char **argv);
void Help();

int main(int argc, char **argv)
{
   CSocket cs;
   int len;
   unsigned char buffer[2048];

   memset(ip, 0, sizeof(ip));
   strcpy(ip, "127.0.0.1");

   ParseArgs(argc, argv);

   sprintf(command, "CINFO-%c", *cmd);

   if ( ! cs.Create() ) {
      perror("Create");
      exit(1);
   }
   if ( ! cs.Connect( ip, port, 0, 3) ) {
      perror("Connect");
      exit(1);
   }

   cs.Send(command);

   cs.SetNonBlocking( false );
   memset( buffer, 0, sizeof(buffer) );
   len = cs.Recv(buffer);
   if ( len > 0 ) {
      printf("%s\n", buffer);
   }

   cs.Close();
}


void ParseArgs(int argc, char **argv)
{
 int c;
   int optind = 0;

   static struct option long_options[] = {
      {"ip", 1, 0, 'i' },
      {"port", 1, 0, 'p' },
      {"command", 1, 0, 'c' },
      {"version", 0, 0, 'v' },
      {"help", 10, 0, 'h' },
      { 0, 0, 0, }
   };

   memset(cmd, 0, sizeof(cmd));
   while(1) {
      if ( (c = getopt_long(argc, argv, "i:p:c:hv",  long_options, &optind )) < 0 )
         break;

      switch(c) {
      case 'i':
         strncpy(ip, optarg, 64);
         break;
      case 'p':
         port = atol( optarg );
         break;
      case 'c':
         strncpy(cmd, optarg, 16);
         break;
      case 'v':
         printf("cip_admin ver: %s\n", version);
         exit(1);
         break;
      case 'h':
         Help();
      }
   }

   if ( strlen( cmd ) <= 0 )
      Help();
}


void Help()
{
   printf("Usage: cip_admin [OPTION]...\n");
   printf("Command line to set/get information from the clusterip application\n\n");
   printf("OPTIONS:\n");
   printf("-i, --ip        IP Address of the clusterip. Default is 127.0.0.1\n");
   printf("-p, --port      Admin port of the clusterip, Default is 9999\n");
   printf("-c, --command   Commands available to the cluster ip.\n");
   printf("                valid commands: O, P, S, W\n");
   printf("                O - remove node from standby state.\n");
   printf("                P - Display Cluster Information in name=value format.\n"); 
   printf("                W - Display Cluster Information in wide format.\n");
   printf("                S - Set node to standby. Use with care.\n");
   printf("-v, --version   Show version.\n");
   printf("-h, --help      Show help.\n"); 
   exit(0);
}

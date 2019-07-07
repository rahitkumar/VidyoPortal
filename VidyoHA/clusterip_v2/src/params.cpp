using namespace std;
#include<stdio.h>
#include<getopt.h>
#include<stdlib.h>
#include<string.h>
#include<arpa/inet.h>
#include"params.h"

bool CParams::ParseArgs(int argc, char **argv)
{
   int c;
   int optind = 0;

   static struct option long_options[] = {
      {"local-ip", 1, 0, 'l' },
      {"peer-ip", 1, 0, 'i' },
      {"local-eth1-ip", 1, 0, 'w' },
      {"peer-eth1-ip", 1, 0, 'x' },
      {"port", 1, 0, 'p' },
      {"cluster-ip", 1, 0, 'c' },
      {"next-hop-ip", 1, 0, 'r' },
      {"interval", 1, 0, 'n' },
      {"max-negotiate", 1, 0, 'm' },
      {"ping-interval", 1, 0, 'z' },
      {"timeout", 1, 0, 't'},
      {"basedir", 1, 0, 'b'},
      {"loglevel", 1, 0, 'L'},
      {"daemon", 0, 0, 'd'},
      {"preferred", 0, 0, 'a'},
      {"help", 0, 0, 'h'},
      {"encrypt", 0, 0, 'E'},
      {"version", 0, 0, 'v'},
      {"adminsocket", 0, 0, 's'},
      {"backup-dir", 1, 0, 'B'},
      { 0, 0, 0, }
   };

   memset(next_hop_ip, 0, sizeof(next_hop_ip));
   memset(cluster_ip, 0, sizeof(cluster_ip));
   memset(my_ip, 0, sizeof(my_ip));
   memset(peer_ip, 0, sizeof(peer_ip));
   memset(my_eth1_ip, 0, sizeof(my_eth1_ip));
   memset(peer_eth1_ip, 0, sizeof(peer_eth1_ip));
   memset(adminsocket, 0, sizeof(adminsocket));
   strcpy(adminsocket, "/var/run/clusterip.socket");

   while(1){
      if ( (c = getopt_long(argc, argv, "s:B:r:b:z:l:p:i:n:m:t:w:x:hvdaE",  long_options, &optind )) < 0 )
         break;
      
      switch(c) {
      case 'a': preferred = true;
         break;
      case 'B': backupdir = optarg;
         break;
      case 'b': basedir = optarg;
         break;
      case 'r':
         strncpy(next_hop_ip, optarg, sizeof(next_hop_ip));
         break; 
      case 'c':
         strncpy(cluster_ip, optarg, sizeof(cluster_ip));
         break; 
      case 's':
         strncpy(adminsocket, optarg, sizeof(adminsocket));
         break; 
      case 'l':
         strncpy(my_ip, optarg, sizeof(my_ip));
         break; 
      case 'i':
         strncpy(peer_ip, optarg, sizeof(peer_ip));
         break; 
      case 'w':
         strncpy(my_eth1_ip, optarg, sizeof(my_eth1_ip));
         break; 
      case 'x':
         strncpy(peer_eth1_ip, optarg, sizeof(peer_eth1_ip));
         break; 
      case 'z':
         ping_interval = atoi( optarg );
         break; 
      case 'm':
         max_negotiate = atoi( optarg );
         break; 
      case 'L':
         loglevel = atoi( optarg );
         break; 
      case 'p':
         strncpy(port, optarg, sizeof(port));
         break; 
      case 'n':
         interval = atoi ( optarg );
         break; 
      case 't':
         timeout = atoi( optarg );
         break; 
      case 'h':
         Usage();
         break;
      case 'E':
         encrypt = true;
         break;
      case 'd':
         daemon = true;
         break;
      case 'v':
         Version();
         break;
      }

   }

   //check all the mandatory fields...
   //
   //printf("my_ip=%u\n", my_ip);
   //printf("peer_ip=%u\n", peer_ip);
   //printf("cluster_ip=%u\n", cluster_ip);

   if ( strlen(my_ip) == 0 || strlen(cluster_ip) == 0 || strlen(peer_ip) == 0 || strlen(next_hop_ip) == 0 )  {
      printf("One of the mandatory parameter is missing: \n  --local-ip, --cluster-ip, --peer-ip, --next-hop-ip\n\n");
      Usage();
   }

   if ( preferred )  // preferred node should have a lesser maximum negotiate counter.
      max_negotiate -= 1;
  
   return true;
}


void CParams::Usage()
{
   printf("Usage: clusterip [OPTION]...\n\n");
   printf("This program will be used for configuring a High Availabilty for Portal 1+1.\n");
   printf("It is reponsible for checking the node status and managing the cluster IP\n");
   printf("(a.k.a) as the floating IP between the two nodes\n\n");
   printf("OPTIONS:\n");
   printf("  -a, --preferred       Mark the node as preferred with higher priority. Default is non-preferred\n");
   printf("  -b, --basedir         Base directory for the clusterip application.\n");
   printf("  -B, --backupdir       Location of the backup file...\n");
   printf("  -r, --next-hop-ip     Next hop IP address. Normally the address of the router.\n");
   printf("  -c, --cluster-ip      Floating IP address of the remote node in the cluster.\n"); 
   printf("  -l, --local-ip        Local IP address of this node.\n");
   printf("  -i, --peer-ip         Static IP address of the remote node in the cluster.\n");
   printf("  -w, --local-eth1-ip   Local ETH1 IP address of this node.(Optional)\n");
   printf("  -x, --peer-eth1-ip    Static ETH1 IP address of the remote node in the cluster.(Optional)\n");
   printf("  -p, --port            Listening port of the remote node. Default is 8888.\n");
   printf("  -m, --max-negotiate   Maximum retry for requesting to become acvtive. Default is 5.\n");
   printf("  -n, --interval        Interval in seconds when exchanging heartbeat. Default is 3.\n");
   printf("  -z, --ping-interval   Ping interval in seconds to the next hop. Default is 2.\n");
   printf("  -t, --timeout         Maximum number of seconds without receiving heartbeat before\n");
   printf("                        considering the remote node failed. Default is 30\n");
   printf("  -L, --loglevel        Sets the loglevel of the application. Default is 0.\n");
   printf("                        Valid loglevels: 0 - disable, 1-WARNING, 2-INFO, 3-DEBUG\n");
   printf("  -E, --encrypt         Encrypt the UDP data going to the network.\n");
   printf("  -d, --daemon          Run the program as daemon.\n");
   printf("  -s, --adminsocket     Admin listening socket. Default (/var/run/clusterip.socket)\n");
   printf("  -v, --version         Show version.\n");
   printf("  -h, --help            Show help.\n");
   exit(0);
}


void CParams::Version()
{
   printf("%s %s\n", progname.c_str(), version.c_str());
   exit(0);
}


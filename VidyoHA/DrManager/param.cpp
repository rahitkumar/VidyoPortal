#include "param.h"
#include <stdio.h>
#include <stdlib.h>
#include <getopt.h>

CParam *CParam::Initialize()
{
   if ( ! pInstance ) {
      pInstance = new CParam();
   }
   return pInstance;
}

CParam *CParam::GetInstance()
{
   return pInstance;
}

void CParam::Version(const char *v)
{
   version = v;
}

void CParam::ParseArgs(int argc, char **argv)
{
   int c;
   int optind = 0;

   static struct option long_options[] = {
      {"node-id", 1, 0, 'n' },
      {"node-version", 1, 0, 'N' },
      {"display-name", 1, 0, 'd' },
      {"admin-socket", 1, 0, 'a' },
      {"bin-dir", 1, 0, 'b' },
      {"config", 1, 0, 'c' },
      {"public-ip", 1, 0, 'p' },
      {"eth0-ip", 1, 0, 'i' },
      {"eth1-ip", 1, 0, 'I' },
      {"reg-interval", 1, 0, 'r' },
      {"reg-timeout", 1, 0, 't' },
      {"dns-interval", 1, 0, 's' },
      {"version-ip", 0, 0, 'v' },
      {"daemon", 0, 0, 'D' },
      {"help", 0, 0, 'h' },
      {"log-warning", 0, 0, 'W' },
      {"log-info", 0, 0, 'F' },
      {"log-debug", 0, 0, 'G' },
      { 0, 0, 0, 0}
   };

   node_id="";
   node_version="";
   display_name="";
   public_ip="";
   eth0_ip="";
   eth1_ip="";
   daemon = false;
   logwarning = false;
   loginfo = false;
   logdebug = false;
   bindir = "/opt/vidyo/ha/dr";
   reg_tmo = 3;
   reg_interval = 30;
   dns_interval = 5;

   while(1){
      if ( (c = getopt_long(argc, argv, "a:b:n:N:d:p:i:I:b:r:t:s:c:vhWFGD",  long_options, &optind )) < 0 )
         break;

      switch(c) {
         case 'h': 
            Usage();
            exit(0);
            break;
         case 'v':
            printf("Version: %s\n", version.c_str());
            exit(0);
            break;
         case 'W':
            logwarning = true;
            break;
         case 'F':
            loginfo = true;
            break;
         case 'G':
            logdebug = true;
            break;
         case 'D':
            daemon = true;
            break;
         case 'n':
            node_id = optarg;
            break;
         case 'N':
            node_version = optarg;
            break;
         case 'd':
            display_name = optarg;
            break;
         case 'p':
            public_ip = optarg;
            break;
         case 'i':
            eth0_ip = optarg;
            break;
         case 'I':
            eth1_ip = optarg;
            break;
         case 'b':
            bindir = optarg;
            break;
         case 'a':
            admin_socket = optarg;
            break;
         case 'c':
            local_config = optarg;
            break;
         case 'r':
            reg_interval = atoi(optarg);
            break;
         case 't':
            reg_tmo = atoi(optarg);
            break;
         case 's':
            dns_interval = atoi(optarg);
            break;
      }
   }

   if ( node_id.length() == 0 ) {
   } 
}

void CParam::Usage()
{
   printf("Usage: drmgr [OPTIONS]...\n");
   printf("Version: %s\n\n", version.c_str());
   printf("\
  -a, --admin-socket=<value>   Name socket for administrative commands.\n\
  -b, --bin-dir=<value>        Binary/Scrpts directory for drmgr.\n\
  -c, --config=<value>         Filename containing a node specific config.\n\
  -n, --node-id=<value>        Unique nodeID in a DR group(required).\n\
  -N, --node-version=<value>   Current portal version(required).\n\
  -d, --display-name=<value>   Name of the node(required).\n\
  -p, --public-ip=<value>      Public IP Address of the server(required).\n\
  -i, --eth0-ip=<value>        IP Address of eth0 interface(required).\n\
  -I, --eth1-ip=<value>        IP Address of eth1 interface(optional).\n\
  -v, --version                Show version.\n\
  -h, --help                   Show help.\n\
  -W, --log-warning            Enable logging of warning message(optional)\n\
  -F, --log-info               Enable logging of informational message(optional)\n\
  -G, --log-debug              Enable logging of debug message(optional)\n\
  -b, --bin-dir                Location of the the scripts and other binary program\n\
  -r, --reg-interval=<value>   Number of seconds interval for every registration(Default 30).\n\
  -t, --reg-timeout=<value>    Number of seconds to wait to get response from the ACTIVE node(Default 3).\n\
  -s, --dns-interval=<value>   Number of seconds to wait for each dns lookup(Default 5).\n\
\n");

}

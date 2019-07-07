#ifndef _PARAMS_H_
#define _PARAMS_H_

using namespace std;

#include<string>

class CParams
{
public:
   CParams(){};
   ~CParams(){};
   CParams( const char *pn, const char *ver) : version(ver), progname(pn) 
   { 
      daemon = false; 
      timeout = 30;
      preferred = false;
      max_negotiate = 8;
      interval = 3;
      ping_interval = 2;
      loglevel = 0;
      basedir = "/opt/vidyo/ha";
      backupdir = "/home/dbsync";
      encrypt = false;
   }

   bool ParseArgs(int argc, char **);
   void Usage();
   void Version();

   const char * Port() const { return port; }
   const char *PeerIP() const { return peer_ip; }
   const char *MyIP() const { return my_ip; }
   const char *PeerETH1IP() const { return peer_eth1_ip; }
   const char *MyETH1IP() const { return my_eth1_ip; }
   const char *ClusterIP() const { return cluster_ip; }
   const char *NextHopIP() const { return next_hop_ip; }
   const char *AdminSocket() const { return adminsocket; }
   int Interval() const { return interval; }
   int MaxNegotiate() const { return max_negotiate; }
   int PingInterval() const { return ping_interval; }
   int Timeout() const { return timeout; }
   int LogLevel() const { return loglevel; }
   bool Daemonize() const { return daemon; }
   bool Preferred() const { return preferred; }
   bool Encrypt() const { return encrypt; }
   const char *Basedir() const { return basedir.c_str(); }
   const char *Backupdir() const { return backupdir.c_str(); }
   
private:
   char port[8];
   char my_ip[64];
   char peer_ip[64];
   char my_eth1_ip[64];
   char peer_eth1_ip[64];
   char cluster_ip[64];
   char next_hop_ip[64];
   char adminsocket[256];
   int ping_interval;
   int interval;
   int timeout;
   int loglevel;
   int max_negotiate;
   string version;
   string progname;
   string basedir;
   string backupdir;
   bool daemon;
   bool preferred;
   bool encrypt;
};

#endif

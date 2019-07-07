#ifndef _PARAM_H_
#define _PARAM_H_

using namespace std;

#include<string>

class CParam
{
public:
   static CParam *Initialize();
   static CParam *GetInstance();
   void ParseArgs(int argc, char **argv);

   const char *NodeId() const { return node_id.c_str(); }
   const char *NodeVersion() const { return node_version.c_str(); }
   const char *DisplayName() const { return display_name.c_str(); }
   const char *PublicIp() const { return public_ip.c_str(); }
   const char *Eth0Ip() const { return eth0_ip.c_str(); }
   const char *Eth1Ip() const { return eth1_ip.c_str(); }
   const char *Version() const { return version.c_str(); }
   const char *BinDir() const { return bindir.c_str(); }
   const char *LocalConfig() const { return local_config.c_str(); }
   const char *AdminSocket() const { return admin_socket.c_str(); }
   bool Daemonize() const { return daemon; }
   bool EnableLogWarning() const { return logwarning;}
   bool EnableLogInfo() const { return loginfo;}
   bool EnableLogDebug() const { return logdebug;}
   int  RegInterval() const { return reg_interval;}
   int  RegTimeout() const { return reg_tmo;}
   int  DnsCheckInterval() const { return dns_interval;}
   void Version(const char *v);
   void Usage();

private:
   string node_id;
   string node_version;
   string display_name;
   string public_ip;
   string eth0_ip;
   string eth1_ip;
   string version;
   string bindir;
   string local_config;
   string admin_socket;
   int reg_tmo;
   int reg_interval;
   int dns_interval;
   bool daemon;
   bool logwarning;
   bool loginfo;
   bool logdebug;
   static CParam *pInstance;
};

#endif

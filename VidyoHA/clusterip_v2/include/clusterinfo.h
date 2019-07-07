#ifndef _CLUSTER_INFO_H_
#define _CLUSTER_INFO_H_

#include<pthread.h>
#include<stdio.h>
#include"clusterip.h"
#include"clusterinfo.h"
#include"statemachine.h"

class CClusterInfo
{
public:
   static CClusterInfo* Initialize();
   static CClusterInfo* GetInstance();
   void InitClusterData(pthread_mutex_t m, const char *localip, const char *clusterip, const bool b);

   void Reset();
   char* XmlParse(const char *xml, const char *fname, char *value);
   bool UpdateClusterInfo(const char *data, int len);
   void ReadClusterInfo(ClusterData *cData);
   void DumpClusterData(FILE *fp);
   void ClusterDataToSocket(int sd);
   void ClusterDataToSocketWide(int sd);
   char MyRole();
   char MyLastRole();
   char PeerAction();
   char MyLastAction();
   char PeerRole();
   char *ClusterIP();
   char *MyIP();
   char *PeerIP();
   int NegotiateCounter();
   char PeerStatus();
   char MyStatus();
   char PeerPriority();
   unsigned int PeerSeqno();
   time_t MyUptime();
   time_t LastACK();
   time_t PeerLastHB();
   time_t LastSyncTime();
   unsigned int MySeqno();
   char State();
   char MyLastState();
   UINT ExpectedSeqno();
   time_t LastAck2HB();
   const char *StrRole( char c );
   const char *StrAction( char c );
   const char *StrState( char c );
   const char *StrStatus( char c );
   const char *MyDBVersion();
   const char *PeerDBVersion();
   const char *DBSyncStatus();

   HA_EVENTS Events();

   int LastPingOK();
   bool ChecksumACKRecv();
   time_t ForceStandbyTime();
   int LastHBWatcherHB();
   int LastPingerHB();
   int LastClientHB();
   int LastServerHB();
   time_t LastHBSent();

   void UpdateLastHBSent();
   void UpdatePeerLastHB();
   void PeerRole(char r);
   void ResetLastACK();
   void MyLastAction(char a);
   void MyRole(char r);
   void MyLastRole(char r);
   void NegotiateCounter(int c);
   void PeerStatus( char s);
   void MyStatus(char s);

   void State( char s );
   void UpdateLastSyncTime();

   void MyLastState( char s );
   void MySeqno(unsigned int x);
   void PeerSeqno(unsigned int x);
   void ExpectedSeqno(UINT x);
   void UpdateLastACK2HB();
   void PingOK();
   void ChecksumACKRecv(bool b);
   void SetForceStandbyTime();
   void SetServerHB();
   void SetClientHB();
   void SetPingerHB();
   void SetHBWatcherHB();
   void Preferred(bool b);
   void MyDBVersion(const char *s);
   void PeerDBVersion(const char *s);
   void DBSyncStatus(const char *s);

private:
   CClusterInfo();
   ~CClusterInfo();
   pthread_mutex_t mutex;
   ClusterData m_cData;
   static CClusterInfo *pInstance;
};

#endif

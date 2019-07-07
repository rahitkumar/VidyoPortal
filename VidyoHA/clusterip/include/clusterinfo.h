#ifndef _CLUSTER_INFO_H_
#define _CLUSTER_INFO_H_

#include<pthread.h>
#include<stdio.h>
#include"clusterip.h"

class CClusterInfo
{
public:
   CClusterInfo();
   CClusterInfo(pthread_mutex_t m);
   ~CClusterInfo();

   void Reset();
   char* XmlParse(const char *xml, const char *fname, char *value);
   bool UpdateClusterInfo(const char *data, int len);
   void ReadClusterInfo(ClusterData *cData);
   void DumpClusterData(FILE *fp);
   void ClusterDataToSocket(int sd);
   void ClusterDataToSocketWide(int sd);
   void Initialize( const char *localip, const char *clusrterip, bool b );
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
   const char *MyBackupChecksum();
   const char *PeerBackupChecksum();
   const char *MyDBVersion();
   const char *PeerDBVersion();

   int LastPingOK();
   int SyncDuration();
   int BackupDuration();
   bool ChecksumACKRecv();
   time_t ForceStandbyTime();
   int LastHBWatcherHB();
   int LastPingerHB();
   int LastClientHB();
   int LastServerHB();


   void PeerRole( char r);
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
   void MyBackupChecksum(const char *cs);
   void PeerBackupChecksum(const char *cs);
   void ChecksumACKRecv(bool b);
   void SetForceStandbyTime();
   void BackupDuration(int d);
   void SyncDuration(int d);
   void SetServerHB();
   void SetClientHB();
   void SetPingerHB();
   void SetHBWatcherHB();
   void Preferred(bool b);
   void MyDBVersion(const char *s);
   void PeerDBVersion(const char *s);

private:
   pthread_mutex_t mutex;
   ClusterData m_cData;
};

#endif

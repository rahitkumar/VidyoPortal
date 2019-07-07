
#include<pthread.h>
#include<string.h>
#include<stdlib.h>
#include <string.h>

#include<time.h>
#include<netinet/in.h>
#include<arpa/inet.h>
#include<netinet/tcp.h>

#include"clusterinfo.h"
#include"clusterip.h"
#include"miscutils.h"

CClusterInfo* CClusterInfo::Initialize()
{
   if ( ! pInstance ) {
      pInstance = new CClusterInfo;
   }

   return pInstance;
} 

CClusterInfo* CClusterInfo::GetInstance()
{
   return pInstance;
}




//! \brief Default Constructor
CClusterInfo::CClusterInfo()
{

}

void CClusterInfo::InitClusterData(pthread_mutex_t m, const char *localip, const char *clusterip, const bool b)
{
   mutex = m;
   time( &m_cData.my_uptime );
   Reset();
   strncpy(m_cData.my_ip, localip, sizeof(m_cData.my_ip));
   strncpy(m_cData.cluster_ip, clusterip, sizeof(m_cData.cluster_ip));
   m_cData.preferred = b;
}

//! \brief Default Destructor
CClusterInfo::~CClusterInfo()
{

}

//! \brief Reset ClusterData information...
void CClusterInfo::Reset()
{
   time_t t;
   time(&t);

   m_cData.last_ack = t;
   m_cData.peer_last_heartbeat = t;
   m_cData.last_hb_sent = t;
   m_cData.last_ping_ok = t;

   m_cData.peer_heartbeat_ctr = 0;
   m_cData.peer_seqno = 0;
   m_cData.peer_role = UNKNOWN_ROLE;
   m_cData.my_seqno = 0;
   m_cData.expected_seqno = 0;
   m_cData.my_role = UNKNOWN_ROLE;
   m_cData.status = OFFLINE_STATUS;
   m_cData.state = UNKNOWN_STATE;
   m_cData.my_last_state = UNKNOWN_STATE;
   m_cData.checksum_ack_recv = false;
   m_cData.force_standby_time = 0;
   memset(m_cData.dbsync_status, 0, sizeof(m_cData.dbsync_status));
}

bool CClusterInfo::UpdateClusterInfo(const char *data, int datalen)
{
   time_t t;
   char temp[1024];
   CMiscUtils *cmu=CMiscUtils::GetInstance();

   pthread_mutex_lock( &mutex );

   memset(temp, 0, sizeof(temp));
   if ( XmlParse( data, "MsgType", temp) == NULL ){
      cmu->LogMsg(DEBUG, "unknown MsgType");
      pthread_mutex_unlock( &mutex );
      return false;
   }

   cmu->LogMsg(DEBUG, "MsgType [%s]", temp);
   if ( strcmp(temp, "CINFO" ) ) {
      cmu->LogMsg(DEBUG, "unknown MsgType");
      pthread_mutex_unlock( &mutex );
      return false;
   }

   memset(temp, 0, sizeof(temp));
   if ( XmlParse( data, "SeqNo", temp) == NULL ){
      cmu->LogMsg(DEBUG, "unknown SeqNo");
      pthread_mutex_unlock( &mutex );
      return false;
   }

   cmu->LogMsg(DEBUG, "SeqNo [%s]", temp);
   m_cData.peer_seqno = atoi(temp);
    
   memset(temp, 0, sizeof(temp));
   if ( XmlParse( data, "Priority", temp) == NULL ) {
      cmu->LogMsg(DEBUG, "unknown Priority");
      pthread_mutex_unlock( &mutex );
      return false;
   }

   cmu->LogMsg(DEBUG, "Priority [%s]", temp);
   m_cData.peer_priority = *temp;

   memset(temp, 0, sizeof(temp));
   if ( XmlParse( data, "Role", temp) == NULL ) {
      cmu->LogMsg(DEBUG, "unknown Role");
      pthread_mutex_unlock( &mutex );
      return false;
   }

   cmu->LogMsg(DEBUG, "PeerRole [%s]", temp);
   m_cData.peer_role = *temp;

   memset(temp, 0, sizeof(temp));
   if ( XmlParse( data, "Event", temp) == NULL ){
      cmu->LogMsg(DEBUG, "unknown Event");
      pthread_mutex_unlock( &mutex );
      return false;
   }

   cmu->LogMsg(DEBUG, "Event [%s]", temp);
   m_cData.event = (HA_EVENTS) atoi(temp);

   memset(temp, 0, sizeof(temp));
   if ( XmlParse( data, "Uptime", temp) == NULL ) {
      cmu->LogMsg(DEBUG, "unknown Uptime");
      pthread_mutex_unlock( &mutex );
      return false;
   }

   cmu->LogMsg(DEBUG, "Uptime [%s]", temp);
   m_cData.peer_uptime = atoi( temp );

   memset(temp, 0, sizeof(temp));
   if ( XmlParse( data, "Ip", temp) == NULL ) {
      cmu->LogMsg(DEBUG, "unknown Ip");
      pthread_mutex_unlock( &mutex );
      return false;
   }

   cmu->LogMsg(DEBUG, "Ip(peerIP) [%s]", temp);
   strcpy(m_cData.peer_ip, temp);

   memset(temp, 0, sizeof(temp));
   if ( XmlParse( data, "DbVersion", temp) == NULL ) {
      cmu->LogMsg(INFO, "unknown DBVersion");
      pthread_mutex_unlock( &mutex );
      return false;
   }

   cmu->LogMsg(DEBUG, "DbVersion(peer DB version) [%s]", temp);

   memset( m_cData.peer_db_version, 0, sizeof(m_cData.peer_db_version));
   strcpy(m_cData.peer_db_version, temp);

   memset(temp, 0, sizeof(temp));
   if ( MyRole() !=  ACTIVE_ROLE )
      ;
      //memset(m_cData.dbsync_status, 0, sizeof(m_cData.dbsync_status));
   else {
      if ( XmlParse( data, "DBSyncStatus", temp) != NULL ) {
         strncpy(m_cData.dbsync_status, temp, sizeof(m_cData.dbsync_status) - 1);
      }
   }

   cmu->LogMsg(DEBUG, "DBSyncStatus [%s]", temp);

   time(&t);
   if ( m_cData.event == HB2_A_L || m_cData.event == HB2_A_NL )
      ;
      // do not update the peer_last_heartbeat if it is just a secondary heartbeat.  
      // otherwise  we wont be able to determine if there is a primary heartbeat or not.
      // Note: At this time, the secondary heartbeat is just for detecting split-brain.
   else
      m_cData.peer_last_heartbeat = t;

   if ( m_cData.event == HB2_A_L )
      m_cData.event = HB_A_L;
   else if ( m_cData.event == HB2_A_NL )
      m_cData.event = HB_A_NL;

   printf("peer last heartbeat = %ld\n", m_cData.peer_last_heartbeat);

   pthread_mutex_unlock( &mutex );

   return true;
}

void CClusterInfo::ReadClusterInfo(ClusterData *cData)
{
   pthread_mutex_lock(&mutex);
   memcpy( cData, &m_cData, sizeof(ClusterData)); 
   pthread_mutex_unlock(&mutex);
}

//! \brief Send the cluster information in wide format to a socket
void CClusterInfo::ClusterDataToSocketWide(int sd)
{
   char buffer[2048];
   char current_ip[64];
   CMiscUtils *cmu=CMiscUtils::GetInstance();
   CStateMachine *cSM=CStateMachine::GetInstance();
   char sync_time[64];

   memset(buffer, 0, sizeof(0));
   memset(sync_time, 0, sizeof(sync_time));

   pthread_mutex_lock(&mutex);

   ctime_r( &m_cData.last_sync_time, sync_time );

   sync_time[ strlen(sync_time) - 1 ]  = 0;

   if ( MyRole() == ACTIVE_ROLE )
      strncpy(current_ip, m_cData.cluster_ip, sizeof(current_ip));
   else
      strncpy(current_ip, m_cData.my_ip, sizeof(current_ip));
   
   snprintf(buffer, 2048, 
"Current Status: (%s)\n\
----------------\n\
Role: %-18s             State: %-15s\n\
IP: %-18s     \n\
Uptime: %u secs\n\
Link Test: %d sec(s) ago\n\
%s %s\n\n\
Peer Status: (%s)\n\
------------\n\
IP: %-16s\nRole: %-14s       Uptime: %u secs\n\
Heartbeat: %d sec(s) ago\n\n\n\
%s %s\n",

StrStatus(m_cData.status ),
StrRole( m_cData.my_role ), 
cSM->CurrentStateStr(),
current_ip,
MyUptime(),
LastPingOK(),
( m_cData.my_role == ACTIVE_ROLE ? "Last DB Snapshot:" : "" ),
( m_cData.my_role == ACTIVE_ROLE ? sync_time : "" ),
StrStatus(m_cData.peer_status ),
m_cData.peer_ip,
StrRole(m_cData.peer_role ),

(m_cData.peer_uptime == 0 ? 0 : m_cData.peer_uptime), 
(m_cData.peer_last_heartbeat > 0 ? (int)PeerLastHB() : -1 ),
( m_cData.my_role == ACTIVE_ROLE ? "DBSync Status:" : "" ),
( m_cData.my_role == ACTIVE_ROLE  ? m_cData.dbsync_status : "" )
);

   //printf("%s\n", buffer);
   pthread_mutex_unlock(&mutex);

   if ( sd <= 0 ) 
      return;

   if ( send(sd, buffer, strlen(buffer), 0) < 0 ) {
     cmu->LogErr("ClusterDataToSocket(): Failed to send data to tcp client...\n"); 
   }
}


//! \brief Send the ClusterData to a tcp client...
void CClusterInfo::ClusterDataToSocket(int sd)
{
   char buffer[2048];
   CMiscUtils *cmu=CMiscUtils::GetInstance();
   CStateMachine *cSM=CStateMachine::GetInstance();
   time_t now;

   memset(buffer, 0, sizeof(0));

   pthread_mutex_lock(&mutex);
   time( &now );
   
   snprintf(buffer, 2048, 
"MyRole=%s\n\
MyStatus=%s\n\
MyState=%s\n\
MyLastRole=%s\n\
MyLastAction=%s\n\
MySeqno=%d\n\
NegotiateCounter=%d\n\
MyIP=%s\n\
ClusterIP=%s\n\
PeerIP=%s\n\
PeerRole=%s\n\
PeerStatus=%s\n\
PeerUptime=%u\n\
PeerSeqno=%u\n\
PeerHeartbeatCounter=%d\n\
PeerLastHeartbeat=%u\n\
LastACKFromPeer=%u\n\
LastPingOK=%d\n\
LastDBSync=%d\n\
ExpectedSerialNumber=%d\n\
MyDBVersion=%s\n\
PeerDBVersion=%s\n\
LastHBReceiverHB=%d\n\
LastClientHB=%d\n\
LastPingerHB=%d\n\
LastHBWatcherHB=%d\n\
Preferred=%s\n\
DBSyncStatus=%s\n",

   StrRole( m_cData.my_role ), 
   StrStatus(m_cData.status ),
   cSM->CurrentStateStr(),
   StrRole(m_cData.my_last_role),
   cSM->CurrentEventStr(),
   m_cData.my_seqno,
   m_cData.negotiate_ctr,
   m_cData.my_ip,
   m_cData.cluster_ip, 
   m_cData.peer_ip, 
   StrRole(m_cData.peer_role ),

   StrStatus(m_cData.peer_status ),
   (m_cData.peer_uptime == 0 ? 0 : m_cData.peer_uptime), 
   m_cData.peer_seqno, 
   m_cData.peer_heartbeat_ctr,
   (int)PeerLastHB(),
   (int)LastACK(), 
   LastPingOK(),
   (int)m_cData.last_sync_time,
   ExpectedSeqno(),
   m_cData.my_db_version,
   m_cData.peer_db_version,
   (int)(now - m_cData.server_last_heartbeat),
   (int)(now - m_cData.client_last_heartbeat),
   (int)(now - m_cData.pinger_last_heartbeat),
   (int)(now - m_cData.hb_watcher_last_heartbeat),
   ( m_cData.preferred ? "true" : "false" ),
   ( ( cSM->CurrentState() == ACTIVE_NOSTANDBY ||  cSM->CurrentState() == ACTIVE_NOLINK_NOSTANDBY ) ? "" :  m_cData.dbsync_status )
);

   pthread_mutex_unlock(&mutex);

   if ( sd <= 0 ) 
      return;

   if ( send(sd, buffer, strlen(buffer), 0) < 0 ) {
     cmu->LogErr("ClusterDataToSocket(): Failed to send data to tcp client...\n"); 
   }
}

//! \brief Returns the last time the node ACKknowledge a heartbeat

void CClusterInfo::DumpClusterData(FILE *fp)
{
   char s_ip[64];
   pthread_mutex_lock(&mutex);
   if ( fp == stdout ) fprintf(fp, "=====================================================\n");
   
   inet_ntop(AF_INET, (void *)&m_cData.peer_ip, s_ip, INET_ADDRSTRLEN );
   fprintf(fp, "MyRole: %c  Status: %c  Last Role: %c   Last Action: %c Seqno: %d\n", 
             m_cData.my_role, m_cData.status, m_cData.my_last_role, m_cData.my_last_action, m_cData.my_seqno);
   fprintf(fp, "Negotiate Counter: %d\n", m_cData.negotiate_ctr);
   fprintf(fp, "Peer's IP: %-16s   Role: %c   Status: %c\n", s_ip, m_cData.peer_role, m_cData.peer_status);
   fprintf(fp, "Peer's Uptime: %10u   SeqNo: %10u   Action: %c\n", m_cData.peer_uptime, 
                    m_cData.peer_seqno, m_cData.peer_action);
   if ( m_cData.peer_last_heartbeat > 0 ){
      fprintf(fp, "Peer Heartbeat Counter: %d\n", m_cData.peer_heartbeat_ctr);
      fprintf(fp, "Peer Last Heartbeat: %u second(s) ago\n", (int)PeerLastHB());
   }
   fprintf(fp, "Last ACK from Peer: %u sec(s)\n", (int)LastACK());
   fprintf(fp, "Expected Serial Number: %d\n", ExpectedSeqno()); 
   
   if ( fp == stdout ) fprintf(fp, "=====================================================\n");

   pthread_mutex_unlock(&mutex);
   fflush(fp);
}

//! \brief Returns the last time we sync our backup file
time_t CClusterInfo::LastSyncTime()
{
   return m_cData.last_sync_time;
}

//! \brief Returns the uptime of the clusterinfo.
time_t CClusterInfo::MyUptime()
{
   time_t now;
   time( &now );
   return now-m_cData.my_uptime;
}

//! \brief Returns the portal DB version. 
const char *CClusterInfo::MyDBVersion()
{
   return m_cData.my_db_version;
}

//! \brief Returns the peer portal DB version. 
const char *CClusterInfo::PeerDBVersion()
{
   return m_cData.peer_db_version;
}

//! \brief Returns the last time the node ACKknowledge a heartbeat
time_t CClusterInfo::LastAck2HB()
{
   time_t now;

   time(&now);

   return  now - m_cData.last_ack2hb;
}

void CClusterInfo::UpdateLastHBSent()
{
   pthread_mutex_lock(&mutex);
   time( &m_cData.last_hb_sent );
   pthread_mutex_unlock(&mutex);
}

void CClusterInfo::UpdatePeerLastHB()
{
   pthread_mutex_lock(&mutex);
   time( &m_cData.peer_last_heartbeat );
   pthread_mutex_unlock(&mutex);
}

//! \brief Returns the number of seconds HB was sent.
time_t CClusterInfo::LastHBSent()
{
   time_t now;

   time(&now);

   return  now - m_cData.last_hb_sent;
}

///  Accessors...

//! \brief Returns my role whether I am the Active, Standby or Unknown.
char CClusterInfo::MyRole()
{
   return m_cData.my_role; 
}

//! \brief Returns my last role whether I am the Active, Standby or Unknown.
char CClusterInfo::MyLastRole()
{
   return m_cData.my_role; 
}

//! \brief Returns the peer role whether the peer is Active, Standby or Unknown
char CClusterInfo::PeerRole()
{
   return m_cData.peer_role; 
}

//! \brief Returns the peer role whether the peer is Active, Standby or Unknown
char CClusterInfo::PeerAction()
{
   return m_cData.peer_action; 
}

//! \brief Returns the peer role whether the peer is Active, Standby or Unknown
char CClusterInfo::MyLastAction()
{
   return m_cData.my_last_action; 
}

//! \brief Return the peer status: Active, Standby or Unknown
char CClusterInfo::PeerStatus()
{
   return m_cData.peer_status;
}

//! \brief Returns my status: Active, Standby or Unknown
char CClusterInfo::MyStatus()
{
   return m_cData.status;
}

//! \brief Returns the numeric local IP address.
char * CClusterInfo::MyIP()
{
   return m_cData.my_ip;
}

//! \brief Returns the numeric IP address of the peer.
char * CClusterInfo::PeerIP()
{
   return m_cData.peer_ip;
}

//! \brief Returns the number of seconds from the last ACK.
time_t CClusterInfo::LastACK()
{
   time_t now;
   time(&now);
   return now - m_cData.last_ack;
}

time_t CClusterInfo::PeerLastHB()
{
   time_t now;
   time(&now);
   return now - m_cData.peer_last_heartbeat;
}

//! \brief return the negotiate counter.
int CClusterInfo::NegotiateCounter()
{
   return m_cData.negotiate_ctr;
}

//! \brief Returns the peer priority. Returns P if peer is preferred else returns N.  
char CClusterInfo::PeerPriority()
{
   return m_cData.peer_priority;
}

//! \brief Returns the peer's sequence number.
UINT CClusterInfo::MySeqno()
{
   return m_cData.my_seqno;
}

//! \brief Returns the peer's sequence number.
UINT CClusterInfo::PeerSeqno()
{
   return m_cData.peer_seqno;
}

//! \brief Returns the node state
char CClusterInfo::State()
{
   return m_cData.state;
}

//! \brief Returns the last node state
char CClusterInfo::MyLastState()
{
   return m_cData.my_last_state;
}

//! \brief Set the expected sequence number from Peer.
UINT CClusterInfo::ExpectedSeqno()
{
   return m_cData.expected_seqno;
}

//! \brief Returns the number of seconds since we have a successful ping to the next hop.
int CClusterInfo::LastPingOK()
{
   time_t now;
   time(&now);
   return now - m_cData.last_ping_ok;
}


//! \brief Returns the time the user force the ACTIVE node to go STANDBY.
time_t CClusterInfo::ForceStandbyTime()
{
   return m_cData.force_standby_time;
}


//! \brief Returns the description of the role code.
const char *CClusterInfo::StrRole( char c )
{
   const char *ptr;

   switch( c ) {
   case 0:
   case ' ':
   case UNKNOWN_ROLE: ptr = "UNKNOWN"; break;
   case STANDBY_ROLE: ptr = "STANDBY"; break;
   case ACTIVE_ROLE: ptr = "ACTIVE"; break;
   default: ptr = " ";
   }
   
   return ptr;
}

//! \brief Returns the description of the status code.
const char *CClusterInfo::StrStatus( char c )
{
   const char *ptr;

   switch( c ) {
   case ONLINE_STATUS: ptr = "ONLINE"; break;
   case OFFLINE_STATUS: ptr = "OFFLINE"; break;
   case MAINTENANCE_STATUS: ptr = "MAINTENANCE"; break;
   default: ptr = " ";
   }

   return ptr;
}

#if 0
//! \brief Returns the description of the state code
const char *CClusterInfo::StrState( char c )
{
   const char *ptr;

   switch( c ) {
   case UNKNOWN_STATE: ptr = "UNKNOWN_STATE"; break;
   case NEGOTIATION_STATE: ptr = "NEGOTIATION_STATE"; break;
   case UNKNOWN_TO_ACTIVE: ptr = "UNKNOWN_TO_ACTIVE"; break;
   case UNKNOWN_TO_STANDBY: ptr = "UNKNOWN_TO_STANDBY"; break;
   case STANDBY_TO_ACTIVE: ptr = "STANDBY_TO_ACTIVE"; break;
   case ACTIVE_TO_STANDBY: ptr = "ACTIVE_TO_STANDBY"; break;
   case ACTIVE_STATE: ptr = "ACTIVE_STATE"; break;
   case STANDBY_STATE: ptr = "STANDBY_STATE"; break;
   case MAINTENANCE_STATE: ptr = "MAINTENANCE_STATE"; break;
   case ONLINE_STATE: ptr = "ONLINE_STATE"; break;
   case ACTIVE_TO_MAINTENANCE: ptr = "ACTIVE_TO_MAINTENANCE"; break;
   case STANDBY_TO_MAINTENANCE: ptr = "STANDBY_TO_MAINTENANCE"; break;
   default: ptr = " ";
   }

   return ptr;
}

#endif

#if 0
//! \brief Returns the description of the action code
const char *CClusterInfo::StrAction( char c )
{
   const char *ptr;

   switch(c) {
   case NEGOTIATE: ptr = "NEGOTIATE"; break;
   case HEARTBEAT: ptr = "HEARTBEAT"; break;
   case ONLINE: ptr = "GO_ONLINE"; break;
   //case MAINTENANCE: ptr = "GO_MAINTENANCE"; break;
   case ACK2NEG: ptr = "NEGOTIATE_ACK"; break;
   case ACK2HB: ptr = "HEARTBEAT_ACK"; break;
   case NAK: ptr = "NEGATIVE_ACK"; break;
   case REJECT : ptr = "REJECT"; break;
   case SYN: ptr = "SYNC"; break;
   case SYNACK: ptr = "SYNC_ACK"; break;
   case RST: ptr = "RESET"; break;
   case ACTIVE2MAINTENANCE: ptr = "ACTIVE_TO_MAINTENANCE"; break;
   case ACTIVE2MAINTENANCE_ACK: ptr = "ACTIVE_TO_MAINTENANCE_ACK"; break;
   case CHECKSUM: ptr = "CHECKSUM"; break;
   case CHECKSUM_ACK: ptr = "CHECKSUM_ACK"; break;
   default: ptr = " ";

   }

   return ptr;
}

#endif

//! \brief returns the number of seconds since the last heartbeat of the server thread.
int CClusterInfo::LastServerHB()
{
   time_t now;

   time( &now );
   return now - m_cData.server_last_heartbeat;
}

//! \brief returns the number of seconds since the last  heartbeat of the client thread
int CClusterInfo::LastClientHB()
{
   time_t now;

   time( &now );
   return now - m_cData.client_last_heartbeat;
}

//! \brief Returns the number of seconds since the last the heartbeat of the pinger thread
int CClusterInfo::LastPingerHB()
{
   time_t now;

   time( &now );
   return now - m_cData.pinger_last_heartbeat;
}

//! \brief Returns the number of seconds since the last the heartbeat of the hb_watcher thread
int CClusterInfo::LastHBWatcherHB()
{
   time_t now;

   time( &now );
   return now - m_cData.hb_watcher_last_heartbeat;
}

//! \brief Returns the DBSyncStatus
const char * CClusterInfo::DBSyncStatus()
{
   return m_cData.dbsync_status;
}

//////////////////  setters

//! \brief Sets the peer role whether the peer is Active, Standby or Unknown
void CClusterInfo::PeerRole( char r)
{
   pthread_mutex_lock( &mutex );
   m_cData.peer_role = r;
   pthread_mutex_unlock( &mutex );
}

//! \brief Sets the last ack time.
void CClusterInfo::ResetLastACK()
{
   pthread_mutex_lock( &mutex );
   time( &m_cData.last_ack );
   pthread_mutex_unlock( &mutex );
}


//! \brief Set the peer status: Active, Standby or Unknown
void CClusterInfo::PeerStatus( char s)
{
   pthread_mutex_lock( &mutex );
   m_cData.peer_status = s;
   pthread_mutex_unlock( &mutex );
}

//! \brief Set my status: Active, Standby or Unknown
void CClusterInfo::MyStatus(char s)
{
   pthread_mutex_lock( &mutex );
   m_cData.status = s;
   pthread_mutex_unlock( &mutex );
}


//! \brief Set my last action.
void CClusterInfo::MyLastAction(char a)
{
   pthread_mutex_lock( &mutex );
   m_cData.my_last_action = a; 
   pthread_mutex_unlock( &mutex );
}

//! \brief Set my current role
void CClusterInfo::MyRole(char r)
{
   pthread_mutex_lock( &mutex );
   m_cData.my_role = r;
   pthread_mutex_unlock( &mutex );
}

void CClusterInfo::MyLastRole(char r)
{
   pthread_mutex_lock( &mutex );
   printf(">>>>>>>>>>>>>>>>>> %lu: MyLastRole: %c : new_role %c\n", pthread_self(), m_cData.my_last_role, r);
   m_cData.my_last_role = r;
   
   pthread_mutex_unlock( &mutex );
}

//! \brief Set the negotiate counter to c. 
void CClusterInfo::NegotiateCounter( int c )
{
   pthread_mutex_lock( &mutex );
   m_cData.negotiate_ctr = c;
   pthread_mutex_unlock( &mutex );
}

//! \brief Set the node state
void CClusterInfo::State( char s )
{
   pthread_mutex_lock( &mutex );
   m_cData.state = s;
   pthread_mutex_unlock( &mutex );
}

//! \brief Set the last node state
void CClusterInfo::MyLastState( char s )
{
   pthread_mutex_lock( &mutex );
   m_cData.my_last_state = s;
   pthread_mutex_unlock( &mutex );
}

//! \brief Set the Node sequence number.
void CClusterInfo::MySeqno(UINT x)
{
   pthread_mutex_lock( &mutex );
   m_cData.my_seqno = x;
   pthread_mutex_unlock( &mutex );
}

//! \brief Set the Node sequence number.
void CClusterInfo::PeerSeqno(UINT x)
{
   pthread_mutex_lock( &mutex );
   m_cData.peer_seqno = x;
   pthread_mutex_unlock( &mutex );
}

//! \brief Set the expected sequence number from Peer.
void CClusterInfo::ExpectedSeqno(UINT x)
{
   pthread_mutex_lock( &mutex );
   m_cData.expected_seqno = x;
   pthread_mutex_unlock( &mutex );
}

//! \brief Update the last time the node ACKknowledge a heartbeat
void CClusterInfo::UpdateLastACK2HB()
{
   time_t now;
   time(&now);
   pthread_mutex_lock( &mutex );
   m_cData.last_ack2hb = now;
   pthread_mutex_unlock( &mutex );
}

void CClusterInfo::PingOK()
{
   pthread_mutex_lock( &mutex );
   time( &m_cData.last_ping_ok );
   pthread_mutex_unlock( &mutex );

}


//! \brief Set the status whether the ACK to Checksum is recv or not...
void CClusterInfo::ChecksumACKRecv(bool b)
{
   pthread_mutex_lock( &mutex );
   m_cData.checksum_ack_recv = b;
   pthread_mutex_unlock( &mutex );
}


//! \brief Set the time when the user force the active node to go standby.
void CClusterInfo::SetForceStandbyTime()
{
   pthread_mutex_lock( &mutex );
   time( &m_cData.force_standby_time );
   pthread_mutex_unlock( &mutex );
}

//! \brief Returns the last time we sync our backup file
void CClusterInfo::UpdateLastSyncTime()
{
   pthread_mutex_lock( &mutex );
   time( &m_cData.last_sync_time );
   pthread_mutex_unlock( &mutex );
}



//! \brief update the heartbeat timestamp of the server
void CClusterInfo::SetServerHB()
{
   pthread_mutex_lock( &mutex );
   time( &m_cData.server_last_heartbeat );
   pthread_mutex_unlock( &mutex );
}
//! \brief update the heartbeat timestamp of the client
void CClusterInfo::SetClientHB()
{
   pthread_mutex_lock( &mutex );
   time( &m_cData.client_last_heartbeat );
   pthread_mutex_unlock( &mutex );
}

//! \brief update the heartbeat timestamp of the client
void CClusterInfo::SetPingerHB()
{
   pthread_mutex_lock( &mutex );
   time( &m_cData.pinger_last_heartbeat );
   pthread_mutex_unlock( &mutex );
}

//! \brief update the heartbeat timestamp of the client
void CClusterInfo::SetHBWatcherHB()
{
   pthread_mutex_lock( &mutex );
   time( &m_cData.hb_watcher_last_heartbeat );
   pthread_mutex_unlock( &mutex );
}

//! \brief Set the node whether the node is preferred or non-preferred.
void CClusterInfo::Preferred(bool b)
{
   pthread_mutex_lock( &mutex );
   m_cData.preferred = b;
   pthread_mutex_unlock( &mutex );
}

//! \brief Set the portal DB version. 
void CClusterInfo::MyDBVersion(const char *s)
{
   pthread_mutex_lock( &mutex );
   strncpy(m_cData.my_db_version, s, sizeof(m_cData.my_db_version)-1);
   pthread_mutex_unlock( &mutex );
}

//! \brief Set the portal DB version of the peer. 
void CClusterInfo::PeerDBVersion(const char *s)
{
   pthread_mutex_lock( &mutex );
   strncpy(m_cData.peer_db_version, s, sizeof(m_cData.peer_db_version)-1);
   pthread_mutex_unlock( &mutex );
}


//! \brief Set the DBSync Status.
void CClusterInfo::DBSyncStatus(const char *s)
{
   pthread_mutex_lock( &mutex );
   strncpy(m_cData.dbsync_status, s, sizeof(m_cData.dbsync_status)-1);
   pthread_mutex_unlock( &mutex );
}


HA_EVENTS CClusterInfo::Events()
{
   return m_cData.event;
}

//! \brief Simple XmlParser. 
//!  If xml field appears more than once then it only return the value of the first
//!  occurrence.
char* CClusterInfo::XmlParse(const char *xml, const char *fname, char *value)
{
   char *ptr;
   char start_fn[128];
   char end_fn[128];
   int startpos, len;

   snprintf(start_fn, sizeof(start_fn),  "<%s>", fname);
   snprintf(end_fn, sizeof(end_fn),  "</%s>", fname);

   if ( (ptr = (char *)strstr(xml, start_fn)) == NULL )
      return NULL;

   startpos = strlen(xml) - strlen(ptr) + strlen(start_fn);

   //printf("1.ptr [%s] len=%d  xmllen=%d  datalen=%d\n", ptr, strlen(ptr), strlen(xml), strlen(xml)-strlen(ptr));
   if ( (ptr = (char *)strstr(xml, (const char *)end_fn)) == NULL )
      return NULL;
   //printf("2.ptr [%s] len=%d  xmllen=%d  datalen=%d\n", ptr, strlen(ptr), strlen(xml), strlen(xml)-strlen(ptr));

   len = strlen(xml) - startpos - strlen(ptr) - ((strlen(start_fn) - strlen(end_fn)) * 2) - 2;

   strncpy(value, xml+startpos, len);
   value[len] = 0;
   return value;
}


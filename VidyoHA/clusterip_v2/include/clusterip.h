#ifndef _CLUSTER_IP_H_
#define _CLUSTER_IP_H_

#include"statemachine.h"

const int MAX_MSG_SIZE=1024;
typedef unsigned char UCHAR;
typedef unsigned int UINT;
typedef unsigned long ULONG;

/////////////////////// TODO  review this ////////////////////////
//action code
const char NEGOTIATE = 'N';
const char HEARTBEAT = 'H';
const char ONLINE = 'O';
//const char MAINTENANCE = 'F';
const char ACK2NEG = 'A';  // ack to negotiate request
const char ACK2HB = 'B';   // act to heartbeat
const char NAK = 'K';  // negative acknowledgment
const char REJECT = 'R';
const char SYN = 'S'; // synchronize serial number
const char SYNACK = 'Y'; // ACK to SYNC.
const char RST = 'T'; // Reset. Active received unexpected seqno. Standby has to RESYNC.
const char ACTIVE2MAINTENANCE='M'; // command from ACTIVE to request standby to take over...
const char ACTIVE2MAINTENANCE_ACK='Z'; // ack from standby to ACTIVE2MAINTENANCE. Active will go to standby.
const char ACTIVE2STANDBY='$'; // command from ACTIVE to request STANDBY to take over...
const char ACTIVE2STANDBY_ACK='%'; // ack from standby to ACTIVE2STANDBY. Active will go to standby.

const char CHECKSUM='E'; // send checksum of the db to peer.
const char CHECKSUM_ACK='Q'; // ACK to CHECKSUM command...


const char OPTIONAL_STATUS='G'; // Optional status when eth1 is configured.

//role
const char ACTIVE_ROLE = 'M';
const char STANDBY_ROLE = 'S';
const char UNKNOWN_ROLE = 'U';

//status
const char ONLINE_STATUS='A';
const char OFFLINE_STATUS='I'; // node unable to ping the next hop
const char MAINTENANCE_STATUS='S';

//states
const char UNKNOWN_STATE='0';
const char NEGOTIATION_STATE='1';
const char UNKNOWN_TO_ACTIVE='2';
const char UNKNOWN_TO_STANDBY='3';
const char STANDBY_TO_ACTIVE='4';
const char ACTIVE_TO_STANDBY='5';
const char ACTIVE_STATE='6';
const char STANDBY_STATE='7';
const char MAINTENANCE_STATE='8';
const char ONLINE_STATE='9';
const char ACTIVE_TO_MAINTENANCE='A';
const char STANDBY_TO_MAINTENANCE='B';
const char CHECKSUM_STATE='C';
const char OPTIONAL_STATUS_STATE='C';

/////////////////////////////////////////////////////////////////

typedef struct ClusterData
{
   char state;
   char status;
   char peer_priority;
   char my_last_state;
   char my_last_action;
   char my_last_role;
   time_t my_uptime;
   char peer_action;
   char peer_role;
   UINT peer_uptime;
   char my_role;
   char peer_status;
   int peer_heartbeat_ctr;
   int negotiate_ctr;
   time_t peer_last_heartbeat;
   time_t last_ack2hb;
   time_t last_ack;
   time_t last_ping_ok;
   char cluster_ip[64];
   char peer_ip[64];
   char my_ip[64];
   UINT my_seqno;
   UINT peer_seqno;
   UINT expected_seqno;
   char my_db_version[16];
   char peer_db_version[16];
   char dbsync_status[64];
   bool checksum_ack_recv;
   time_t last_sync_time;
   time_t force_standby_time;
   time_t server_last_heartbeat;
   time_t client_last_heartbeat;
   time_t pinger_last_heartbeat;
   time_t hb_watcher_last_heartbeat;
   time_t last_hb_sent;
   bool preferred;
   HA_EVENTS event;
}ClusterData;

int EventHandler();
void PushEventsToQueue(HA_EVENTS ev);
void *HB_Receiver(void *args);
void *admin(void *args);
void *SecondaryHeartbeat(void *args);
void *hb_watcher(void *args);
void *thread_watcher(void *args);


int HB_Sender( CParams *cp, HA_EVENTS event );

bool IsIPV6(const char *ip);
void StartAsStandby();
void Demote( bool self_demote );
void Promote();
void ResetRebootCounter();
void SplitBrain();
int UDPSend( CParams *cp, UCHAR *buffer, const int len, bool eth1_enabled = false);
void ProcessRecvData(unsigned char *buffer, const int len, CParams *cp, const char *srcip );
bool IsEventHB(const HA_EVENTS event);
void InitializeEventHandlerScripts(CParams cp);
HA_EVENTS SecondaryHBEvent();
void ClearEventQueue();

#endif

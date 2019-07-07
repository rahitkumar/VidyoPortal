#ifndef _STATEMACHINE_H_
#define _STATEMACHINE_H_

using namespace std;

#include<stdio.h>
#include<pthread.h>
#include<sys/types.h>
#include<sys/stat.h>
#include<unistd.h>
#include<queue>

#define DR_STATUS      "/opt/vidyo/data/dr/local/status"
#define DR_STATUS_LOCK "/opt/vidyo/data/dr/local/status.lock"

const int MAX_STATES = 7;
class CState;

enum DR_STATES {
   STARTUP=0,
   PRE_MAINTENANCE,
   MAINTENANCE,
   PRE_STANDBY,
   STANDBY,
   PRE_ACTIVE,
   ACTIVE
};

enum DR_EVENTS {
   MAINT, // Enter maintenance
   MAINT_END, // Leave maintenance
   MATCH_PUBIP, // event when ADC(F5/Radware) resolves that public FQDN to the public IP Address of the server.
   NO_MATCH_PUBIP, // event when ADC(F5/Radware) does NOT resolve that public FQDN to the public IP Address of the server.
   C_PUBIP, // event when the public IP address of ACTIVE node is change.
   NO_DNS,  // no response from DNS Server
   REPL_OK,  // DB/Filesystem replication started successfully.
   REPL_FAIL, // event when either DB/Filesystem replication failed.
   REPL_STOP, // event when DB/FS replication stopped. 
   PUBKEY_OK, // SSH public keys were imported successfully to the active node. 
   ACTIVE_READY, // Event when all the pre-requisite to become active is done. 
   NONE
};

// singleton class
class CStateMachine
{
public:
   static CStateMachine *Initialize();
   static CStateMachine *GetInstance();
   void InitializeStates();
   int EventHandler();
   void PushEventToQueue(DR_EVENTS ev);

   DR_STATES CurrentState( DR_STATES curstate );
   void CurrentEvent( DR_EVENTS curevent );
   void PreviousEvent( DR_EVENTS curevent );

   DR_STATES CurrentState() { return m_current_state; }
   DR_STATES PreviousState() { return m_previous_state; }
   DR_EVENTS CurrentEvent() { return m_current_event; }
   DR_EVENTS PreviousEvent() { return m_previous_event; }
   bool SSHKeyGenerated() { return m_ssh_keys_generated; }

   void SSHKeyGenerated( bool tf );
   const char *CurrentEventStr( const bool previous = false );
   const char *CurrentStateStr( const bool previous = false);
   const char *EventStr( DR_EVENTS );
   const char *StateStr( DR_STATES );

   const char *PreviousStateStr();
   const char *PreviousEventStr();
   void ProcessEvents();
   bool UpdateLocalStatus( DR_STATES  drstate );
   //void SetParameters(const CParams param);
   //CParams GetParameters() const { return cp; };
   pthread_mutex_t GetMutex() const { return sm_mutex; }


private:
                      // do not initialize it here... compiler prints some warning if done on a singleton class.
   CStateMachine() {} // : m_current_state(ACTIVE_WITH_STANDBY_NOLINK), m_current_event(NONE), m_promoted(false) { }
   ~CStateMachine() {};
   CState *cState[MAX_STATES];
   DR_STATES m_current_state;
   DR_EVENTS m_current_event;
   DR_STATES m_previous_state;
   DR_EVENTS m_previous_event;
   bool m_ssh_keys_generated;

   //CParams   cp;
   queue<DR_EVENTS> event_queue;
   static pthread_mutex_t sm_mutex;
   static pthread_mutex_t event_mutex;
   static pthread_cond_t event_mutex_cond;
   static CStateMachine *pInstance;
};

class CState
{
public:
   int Event() { return m_event; }
   void Event(const int ev) { m_event = ev; }
   int NextState() { return m_event; }
   void NextState(const int st) { m_next_state = st; }
   virtual DR_STATES Handler(DR_EVENTS pevent) = 0;
protected:
   int m_event;
   int m_next_state;
};


class CStartup : public CState
{
   virtual DR_STATES Handler( DR_EVENTS );
};

class CPreMaintenance : public CState
{
   virtual DR_STATES Handler( DR_EVENTS );
};

class CMaintenance : public CState
{
   virtual DR_STATES Handler( DR_EVENTS );
};

class CPreStandby : public CState
{
   virtual DR_STATES Handler( DR_EVENTS );
};

class CStandby : public CState
{
   virtual DR_STATES Handler( DR_EVENTS );
};

class CPreActive : public CState
{
   virtual DR_STATES Handler( DR_EVENTS );
};

class CActive : public CState
{
   virtual DR_STATES Handler( DR_EVENTS );
};


#endif

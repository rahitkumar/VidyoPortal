#ifndef _STATE_MACHINE_H_
#define _STATE_MACHINE_H_

#include<stdio.h>
#include<time.h>

#include"params.h"

const int MAX_STATES=11;
const int MAX_NEGOTIATE=5;

enum HA_STATES { REBOOT=0,
                 NEGOTIATION,
                 NEGOTIATION_NOLINK,
                 STANDBY,
                 STANDBY_NOLINK,
                 ACTIVE_NOSTANDBY,
                 ACTIVE_NOLINK_NOSTANDBY,
                 ACTIVE_NOLINK_STANDBY_NOLINK,
                 ACTIVE_WITH_STANDBY,
                 ACTIVE_WITH_STANDBY_NOLINK,
                 MAINTENANCE
               };

enum HA_EVENTS { NONE=0,
                 NPNG,    // failed to do a link test
                 PNG,     // link test successful
                 NHB,     // no heartbeat from peer.
                 HB_A_L,  // heartbeat received by STANDBY, ACTIVE has a link
                 HB_S_L,  // heartbeat received by ACTIVE, STANDBY has link
                 HB_S_NL, // heartbeat received by ACTIVE, STANDBY has no link
                 HB_A_NL, // heartbeat received by STANDBY, ACTIVE has no link
                 HB_NAK,  // heartbeat requesting to become ACTIVE has been denied.
                 HB_ACK,  // heartbeat requesting to become ACTIVE has been accepted.
                 HB_REQ,  // heartbeat requesting to become ACTIVE.
                 HB_A2S,  // heartbeat sent by ACTIVE informing STANDBY that ACTIVE is going to STANDY.
                          // STANDBY node should prepare to become ACTIVE.
                 MAINT,   // Maintenance event
                 F_S_B,    // Force Standby
                 HB2_A_NL, // secondary heartbeat sent by the ACTIVE node when no link
                 HB2_A_L,  // secondary heartbeat sent by the ACTIVE node when there is a link
               };

class CState
{
public:
   int Event() { return m_event; }
   void Event(const int ev) { m_event = ev; }
   int NextState() { return m_event; }
   void NextState(const int st) { m_next_state = st; }
   virtual HA_STATES Handler(HA_EVENTS pevent) = 0;
protected:
   int m_event;
   int m_next_state;
};


//!\brief singleton class.
class CStateMachine
{
public:
   static CStateMachine *Initialize();
   static CStateMachine *GetInstance();

   bool Promoted() { return m_promoted; }
   
   void Promoted(bool); 
   void InitializeStates();
   HA_STATES CurrentState( HA_STATES curstate );
   void CurrentEvent( HA_EVENTS curevent );
   void PreviousEvent( HA_EVENTS curevent );
   
   HA_STATES CurrentState() { return m_current_state; }
   HA_EVENTS CurrentEvent() { return m_current_event; }
   HA_EVENTS PreviousEvent() { return m_previous_event; }

   const char *CurrentEventStr( const bool previous = false );
   const char *CurrentStateStr( const bool previous = false);

   const char *PreviousStateStr();
   const char *PreviousEventStr();
   void ProcessEvents();
   void SetParameters(const CParams param);
   CParams GetParameters() const { return cp; };
   pthread_mutex_t GetMutex() const { return sm_mutex; }

private:
                      // do not initialize it here... compiler prints some warning if done on a singleton class.
   CStateMachine() {} // : m_current_state(ACTIVE_WITH_STANDBY_NOLINK), m_current_event(NONE), m_promoted(false) { }
   ~CStateMachine() {};
   CState *cState[MAX_STATES];
   bool m_promoted;
   HA_STATES m_current_state;
   HA_EVENTS m_current_event;
   HA_STATES m_previous_state;
   HA_EVENTS m_previous_event;
   CParams   cp;
   pthread_mutex_t sm_mutex;
   static CStateMachine *pInstance;
};

class CMaintenance : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CMaintenance() {};
   ~CMaintenance() {};
};

class CReboot : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CReboot() {};
   ~CReboot() {};
};


class CNegotiation : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CNegotiation() : m_max_negotiate( MAX_NEGOTIATE ), m_last_negotiate_time(0) {};
   ~CNegotiation() {};
private:
   int m_max_negotiate;
   time_t m_last_negotiate_time; 
};

class CNegotiationNoLink : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CNegotiationNoLink() : m_max_negotiate( MAX_NEGOTIATE ) {};
   ~CNegotiationNoLink() {};
private:
   int m_max_negotiate;
   time_t m_last_negotiate_time; 
};

class CStandby : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CStandby() {};
   ~CStandby() {};
};

class CStandbyNoLink : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CStandbyNoLink() {};
   ~CStandbyNoLink() {};
};

class CActiveNoStandby : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CActiveNoStandby() {};
   ~CActiveNoStandby() {};
};

class CActiveNoLinkNoStandby : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CActiveNoLinkNoStandby() {};
   ~CActiveNoLinkNoStandby() {};
};

class CActiveNoLinkStandbyNoLink : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CActiveNoLinkStandbyNoLink() {};
   ~CActiveNoLinkStandbyNoLink() {};
};

class CActiveWithStandby : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CActiveWithStandby() {};
   ~CActiveWithStandby() {};
};

class CActiveWithStandbyNoLink : public CState
{
public:
   virtual HA_STATES Handler(HA_EVENTS);
   CActiveWithStandbyNoLink() {};
   ~CActiveWithStandbyNoLink() {};
};



#endif

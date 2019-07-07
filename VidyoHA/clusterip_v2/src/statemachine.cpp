#include<queue>
#include "statemachine.h"
#include "clusterip.h"
#include "clusterinfo.h"
#include "miscutils.h"

using namespace std;

//! \brief Initialize this singleton object. This has to be called once only.
CStateMachine* CStateMachine::Initialize()
{
   if ( !pInstance ) {
      pInstance = new CStateMachine;
   }

   return pInstance;
}


//! \brief Promote a node. Set p_promoted to true to promote a node. This should be called
//! before node becomes ACTIVE.  Set p_promoted to false to demote a node from ACTIVE to STANDBY. 
//! On startup Promoted will return false.
void CStateMachine::Promoted(bool p_promoted)
{
   m_promoted = p_promoted;
}

//! \brief Intialize the states of the state machibe. This should be called once only.
void CStateMachine::InitializeStates()
{
   cState[ REBOOT ] = new CReboot;
   cState[ NEGOTIATION ] = new CNegotiation;
   cState[ NEGOTIATION_NOLINK ] = new CNegotiationNoLink;
   cState[ STANDBY ] = new CStandby;
   cState[ STANDBY_NOLINK ] = new CStandbyNoLink;
   cState[ ACTIVE_NOSTANDBY ] = new CActiveNoStandby;
   cState[ ACTIVE_NOLINK_NOSTANDBY ] = new CActiveNoLinkNoStandby;
   cState[ ACTIVE_NOLINK_STANDBY_NOLINK ] = new CActiveNoLinkStandbyNoLink;
   cState[ ACTIVE_WITH_STANDBY ] = new CActiveWithStandby;
   cState[ ACTIVE_WITH_STANDBY_NOLINK ] = new CActiveWithStandbyNoLink;
   cState[ MAINTENANCE ] = new CMaintenance;

   m_current_state = NEGOTIATION_NOLINK;
   m_current_event = NONE;
   m_previous_state = REBOOT;
   m_previous_event = NONE;
   m_promoted = false;
   pthread_mutex_init( &sm_mutex, NULL );
}

//! \brief Set the application paramters. This will allow this object to access the CParam object.
void CStateMachine::SetParameters(const CParams param)
{
    cp = param;
}

//! \brief Returns the current state of the state machine object.
HA_STATES CStateMachine::CurrentState( HA_STATES curstate )
{
   m_current_state = curstate;
   return m_current_state;
}

//! \brief Returns the current event of the state machine object.
void CStateMachine::CurrentEvent( HA_EVENTS curevent )
{
   m_current_event = curevent;
}

//! \brief Returns the current event of the state machine object.
void CStateMachine::PreviousEvent( HA_EVENTS curevent )
{
   m_previous_event = curevent;
}


//! \brief This is the main function that will process all the events.
//! Events will be read from an event queue.
void CStateMachine::ProcessEvents()
{
   CClusterInfo *cCI = CClusterInfo::GetInstance();
   CMiscUtils *cmu = CMiscUtils::GetInstance();
   char LinkStatus = OFFLINE_STATUS;
   char NodeRole = UNKNOWN_ROLE;
   char PeerNodeRole = UNKNOWN_ROLE;
   char PeerLinkStatus = OFFLINE_STATUS;

   if ( m_current_state != m_previous_state )
      m_previous_state = m_current_state;

   if ( m_current_event != m_previous_event ){
      if ( m_current_event != NONE )
         m_previous_event = m_current_event;
   } //else
     // m_current_event = NONE;

   //printf("%d   %d\n", m_current_event, m_previous_event);

   if ( CurrentEvent() != NONE ) {
      cmu->LogMsg(INFO, "State(%d): %s , Event(%d): %s", 
             m_current_state, CurrentStateStr(),  
             m_current_event, CurrentEventStr() );
   }

   // prepare to update clusterinfo with our current role and link status  
   // prepare to update clusterinfo about the peer role and link status.
   switch( m_current_state ) {
      case REBOOT:
      case MAINTENANCE:
      case NEGOTIATION_NOLINK:
         break;
      case NEGOTIATION:
         LinkStatus = ONLINE_STATUS;
         break;
      case STANDBY:
         LinkStatus = ONLINE_STATUS;
         NodeRole = STANDBY_ROLE;
         PeerLinkStatus = ONLINE_STATUS;
         PeerNodeRole = ACTIVE_ROLE;
         break;
      case STANDBY_NOLINK:
         LinkStatus = ONLINE_STATUS;
         NodeRole = STANDBY_ROLE;
         PeerLinkStatus = ONLINE_STATUS;
         PeerNodeRole = ACTIVE_ROLE;
         break;
      case ACTIVE_NOSTANDBY:
         LinkStatus = ONLINE_STATUS;
         NodeRole = ACTIVE_ROLE;
         PeerLinkStatus = OFFLINE_STATUS;
         PeerNodeRole = UNKNOWN_ROLE;
         break;
      case ACTIVE_WITH_STANDBY:
         LinkStatus = ONLINE_STATUS;
         NodeRole = ACTIVE_ROLE;
         PeerLinkStatus = ONLINE_STATUS;
         PeerNodeRole = STANDBY_ROLE;
         break;
      case ACTIVE_WITH_STANDBY_NOLINK:
         LinkStatus = ONLINE_STATUS;
         NodeRole = ACTIVE_ROLE;
         PeerLinkStatus = OFFLINE_STATUS;
         PeerNodeRole = STANDBY_ROLE;
         break;
      case ACTIVE_NOLINK_NOSTANDBY:
         LinkStatus = OFFLINE_STATUS;
         NodeRole = ACTIVE_ROLE;
         PeerLinkStatus = OFFLINE_STATUS;
         PeerNodeRole = UNKNOWN_ROLE;
         break;
      case ACTIVE_NOLINK_STANDBY_NOLINK:
         LinkStatus = OFFLINE_STATUS;
         NodeRole = ACTIVE_ROLE;
         PeerLinkStatus = OFFLINE_STATUS;
         PeerNodeRole = STANDBY_ROLE;
         break;
   }

    cCI->MyStatus( LinkStatus );
    cCI->MyRole( NodeRole );
    cCI->PeerStatus( PeerLinkStatus );

    // Run the Handler on the current state
    cState[ m_current_state ]->Handler( m_current_event );
}


//! \brief Get and instance of CSateMachine object. Note: CStateMachine::Initialize() must be called before using this function.
CStateMachine* CStateMachine::GetInstance()
{
   return pInstance;
}

//! \brief Returns the string value of the current state. This is an overloaded function. If 'previous' is set to true,
//! then this function will the return the previous state rather than the current. Note: 'previous' is set to false by default.
const char* CStateMachine::CurrentStateStr( const bool previous )
{
   const char *str;

   switch( ( previous ? m_previous_state : m_current_state ) ) {
      case REBOOT: str = "REBOOT"; break;
      case NEGOTIATION: str = "NEGOTIATION"; break;
      case NEGOTIATION_NOLINK: str = "NEGOTIATION_NOLINK"; break;
      case STANDBY: str = "STANDBY"; break;
      case STANDBY_NOLINK: str = "STANDBY_NOLINK"; break;
      case ACTIVE_NOSTANDBY: str = "ACTIVE_NOSTANDBY"; break;
      case ACTIVE_NOLINK_NOSTANDBY: str = "ACTIVE_NOLINK_NOSTANDBY"; break;
      case ACTIVE_NOLINK_STANDBY_NOLINK: str = "ACTIVE_NOLINK_STANDBY_NOLINK"; break;
      case ACTIVE_WITH_STANDBY: str = "ACTIVE_WITH_STANDBY"; break;
      case ACTIVE_WITH_STANDBY_NOLINK: str = "ACTIVE_WITH_STANDBY_NOLINK"; break;
      case MAINTENANCE: str = "MAINTENANCE"; break;
      default: str = "UNKNOWN";
   }

   return str;
}

//! \brief Returns the string value of the current event. This is an overloaded function. If 'previous' is set to true,
//! then this function will the return the previous event rather than the current. Note: 'previous' is set to false by default.
const char* CStateMachine::CurrentEventStr( const bool previous )
{
   const char *str;

   switch( ( previous ? m_previous_event : m_current_event ) ) {
      case NONE: str = "NONE"; break;
      case NPNG: str = "NO_LINK"; break;
      case PNG: str = "LINK_OK"; break;
      case NHB: str = "NO_HB"; break;
      case HB_A_L: str = "HB_A_L"; break;
      case HB_S_L: str = "HB_S_L"; break;
      case HB_S_NL: str = "HB_S_NL"; break;
      case HB_A_NL: str = "HB_A_NL"; break;
      case HB_REQ: str = "NEGOTIATE_REQ"; break;
      case HB_NAK: str = "NEGOTIATE_NAK"; break;
      case HB_ACK: str = "NEGOTIATE_ACK"; break;
      case HB_A2S: str = "ACTIVE_TO_STANDBY"; break;
      case MAINT: str = "MAINTENANCE"; break;
      case F_S_B: str = "FORCE_STANDBY"; break;
      default: str = "UNKNOWN";
   }

   return str;
}


//! \brief Returns the string value of the previous state.
const char* CStateMachine::PreviousStateStr()
{
   return CurrentStateStr( true );
}

//! \brief Returns the string value of the previous event.
const char* CStateMachine::PreviousEventStr()
{
   return CurrentEventStr( true );
}

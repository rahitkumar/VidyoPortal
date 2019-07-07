#include <queue>
#include "log.h"
#include "statemachine.h"
#include "utils.h"

using namespace std;

pthread_mutex_t CStateMachine::sm_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t CStateMachine::event_mutex = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t CStateMachine::event_mutex_cond = PTHREAD_COND_INITIALIZER;

//! \brief Initialize this singleton object. This has to be called once only.
CStateMachine* CStateMachine::Initialize()
{
   if ( !pInstance ) {
      pInstance = new CStateMachine;
//      pthread_mutex_init( &sm_mutex, NULL );
//      pthread_mutex_init( &event_mutex, NULL );
//      pthread_cond_init( &event_mutex_cond, NULL);
   }

   return pInstance;
}


//! \brief Intialize the states of the state machibe. This should be called once only.
void CStateMachine::InitializeStates()
{
   cState[ STARTUP ] = new CStartup;
   cState[ PRE_MAINTENANCE ] = new CPreMaintenance;
   cState[ MAINTENANCE ] = new CMaintenance;
   cState[ PRE_STANDBY ] = new CPreStandby;
   cState[ STANDBY ] = new CStandby;
   cState[ PRE_ACTIVE ] = new CPreActive;
   cState[ ACTIVE ] = new CActive;

   m_current_state = STARTUP;
   m_current_event = NONE;
   m_previous_state = STARTUP;
   m_previous_event = NONE;
   m_ssh_keys_generated = false;
}

//! \brief Set the application paramters. This will allow this object to access the CParam object.
//void CStateMachine::SetParameters(const CParams param)
//{
//    cp = param;
//}

//! \brief Returns the current state of the state machine object.
DR_STATES CStateMachine::CurrentState( DR_STATES curstate )
{
   pthread_mutex_lock ( &sm_mutex );
   m_current_state = curstate;
   pthread_mutex_unlock ( &sm_mutex );
   return m_current_state;
}

//! \brief Returns the current event of the state machine object.
void CStateMachine::CurrentEvent( DR_EVENTS curevent )
{
   m_current_event = curevent;
}

//! \brief Returns the current event of the state machine object.
void CStateMachine::PreviousEvent( DR_EVENTS prevevent )
{
   m_previous_event = prevevent;
}


//! \brief This is the main function that will process all the events.
//! Events will be read from an event queue.
void CStateMachine::ProcessEvents()
{
   //m_current_state =  STARTUP;
   if ( m_current_state != m_previous_state )
      m_previous_state = m_current_state;

   if ( m_current_event != m_previous_event ){
      if ( m_current_event != NONE )
         m_previous_event = m_current_event;
   } //else

   printf("Current State: %s, CurrentEvent: %s\n", CurrentStateStr(), CurrentEventStr() );
   LogInfo("Current State: %s, CurrentEvent: %s\n", CurrentStateStr(), CurrentEventStr() );

   switch( m_current_state ) {
      case STARTUP:
         break;
      case PRE_MAINTENANCE:
         break;
      case MAINTENANCE:
         break;
      case PRE_STANDBY:
         break;
      case STANDBY:
         break;
      case PRE_ACTIVE:
         break;
      case ACTIVE:
         break;
   }

   // Run the Handler on the current state
   cState[ m_current_state ]->Handler( m_current_event );

   if ( m_previous_state != m_current_state ) {
      UpdateLocalStatus( m_current_state );
   }
}


//! \brief Get and instance of CStateMachine object. Note: CStateMachine::Initialize() must be called before using this function.
CStateMachine* CStateMachine::GetInstance()
{
   return pInstance;
}

//! \brief Returns the string value of the current state. This is an overloaded function. If 'previous' is set to true,
//! then this function will the return the previous state rather than the current. Note: 'previous' is set to false by default.
const char* CStateMachine::CurrentStateStr( const bool previous )
{
   return StateStr( previous ? m_previous_state : m_current_state );
}

//! \brief Returns the string value of the current event. This is an overloaded function. If 'previous' is set to true,
//! then this function will the return the previous event rather than the current. Note: 'previous' is set to false by default.
const char* CStateMachine::CurrentEventStr( const bool previous )
{
   return EventStr(  previous ? m_previous_event : m_current_event );
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

//! \brief Return a string represting the event
const char* CStateMachine::EventStr( DR_EVENTS ev )
{
   const char *str;

   switch( ev ) {
      case MAINT: str = "MAINT"; break;
      case MAINT_END: str = "MAINT_END"; break;
      case MATCH_PUBIP: str = "MATCH_PUBIP"; break;
      case NO_MATCH_PUBIP: str = "NO_MATCH_PUBIP"; break;
      case C_PUBIP: str = "C_PUBIP"; break;
      case NO_DNS: str = "NO_DNS"; break;
      case REPL_OK: str = "REPL_OK"; break;
      case REPL_FAIL: str = "REPL_FAIL"; break;
      case REPL_STOP: str = "REPL_STOP"; break;
      case PUBKEY_OK: str = "PUBKEY_OK"; break;
      case ACTIVE_READY: str = "ACTIVE_READY"; break;
      case NONE: str = "NONE"; break;
      default: str = "UNKNOWN";
   }

   return str;
}

//! \brief Return a string represting the state
const char* CStateMachine::StateStr( DR_STATES state )
{
   const char *str;

   switch( state ) {
      case STARTUP: str = "STARTUP"; break;
      case PRE_MAINTENANCE: str = "PRE_MAINTENANCE"; break;
      case MAINTENANCE: str = "MAINTENANCE"; break;
      case PRE_STANDBY: str = "PRE_STANDBY"; break;
      case STANDBY: str = "STANDBY"; break;
      case PRE_ACTIVE: str = "PRE_ACTIVE"; break;
      case ACTIVE: str = "ACTIVE"; break;
      default: str = "UNKNOWN";
   }

   return str;
}


//! \brief Process the events received from the event queue.
int CStateMachine::EventHandler()
{
   DR_EVENTS event;

   while(1){
      printf("waiting for event...\n");
      pthread_mutex_lock ( &event_mutex );
      while( event_queue.empty() )
         pthread_cond_wait( &event_mutex_cond, &event_mutex);

      if ( event_queue.empty() ) {
         printf("Q is empty...\n");
         pthread_mutex_unlock ( &event_mutex );
         continue;
      }

      event = event_queue.front();
      event_queue.pop();

      pthread_mutex_unlock ( &event_mutex );

      printf("Event: %s\n", EventStr( event ));
      CurrentEvent( event );
      ProcessEvents();
   }
   return 1;
}

//! \brief Push DR Events to Queue. Note: this can be called from multiple thread.
void CStateMachine::PushEventToQueue(DR_EVENTS ev)
{
   pthread_mutex_lock( &event_mutex );
   event_queue.push( ev );
   pthread_cond_signal( &event_mutex_cond );
   pthread_mutex_unlock( &event_mutex );
}


bool CStateMachine::UpdateLocalStatus( DR_STATES  drstate )
{
   FILE *fp;

   LogInfo("Updating status to [%s]\n", StateStr( drstate ) );

   if ( !lockfile(DR_STATUS_LOCK, 3 ))  { // try to acquire lock and set timeout to 3 seconds.
   /// timeout occur..
      LogError("Unable to acquire lock on DR_STATUS_LOCK!");
      return false;
   }


   if ( (fp = fopen(DR_STATUS, "w")) == NULL ) {
      LogError("failed to update %s", DR_STATUS);
      return false;
   }

   fprintf(fp, "DR_STATE=%s\n", StateStr( drstate ) );
   fflush(fp);
   fclose(fp);

   if ( !unlockfile(DR_STATUS_LOCK ) ) { // try to acquire lock and set timeout to 3 seconds.
      LogError("failed to unlock DR_STATUS_LOCK!");
      return false;
   }

   return true;
}

void CStateMachine::SSHKeyGenerated( bool tf )
{
   m_ssh_keys_generated = tf;
}




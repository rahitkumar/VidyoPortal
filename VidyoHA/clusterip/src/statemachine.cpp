using namespace std;

#include <string.h>
#include "clusterinfo.h"
#include "clusterip.h"
#include "statemachine.h"
#include "params.h"

extern CClusterInfo clusterInfo;

//! \brief StateMachine Handler...
//! action is the peer action otherwise it will be NULL.
void CStateMachine::ProcessStateMachine(const char state, const char action)
{
   if ( action == OPTIONAL_STATUS ) {
       LogMsg( DEBUG, "ProcessStateMachine(): STATE: %s  ACTION: OPTIONAL_STATUS  ROLE(Current/Peer): %c/%c", 
          clusterInfo.StrState(state), clusterInfo.MyRole(), clusterInfo.PeerRole());
       if ( (clusterInfo.MyRole() == clusterInfo.PeerRole()) && clusterInfo.MyRole() == ACTIVE ) {
          LogMsg( WARNING, "ProcessStateMachine(): SPLIT-BRAIN!!! system will reboot...");
          SplitBrain(); 
       }

       return;
   }

   if ( action != ACTIVE2MAINTENANCE && ( clusterInfo.MyStatus() == MAINTENANCE_STATUS  || clusterInfo.MyStatus() == OFFLINE_STATUS ) ){
       LogMsg( DEBUG, "ProcessStateMachine(): STATE: %s   ACTION: %c", clusterInfo.StrState(state), action);
       sleep(1); /// maybe this is not needed!!! 
       return;
   }

   LogMsg( DEBUG, "ProcessStateMachine(): STATE: %c   ACTION: %c", state, action);

   switch( state ) {
   case UNKNOWN_STATE:
        UnknownState(action);
        break;
   case NEGOTIATION_STATE:
        NegotiationState(action);
        break;
   case UNKNOWN_TO_ACTIVE:
        UnknownToActive(action);
        break;
   case UNKNOWN_TO_STANDBY:
        UnknownToStandby(action);
        break;
   case ACTIVE_TO_STANDBY:
        ActiveToStandby(action);
        break;
   case STANDBY_TO_ACTIVE:
        StandbyToActive(action);
        break;
   case ACTIVE_STATE:
        Active(action);
        break;
   case STANDBY_STATE:
        Standby(action);
        break;
   case STANDBY_TO_MAINTENANCE:
        StandbyToMaintenance(action);
        break; 
   case ACTIVE_TO_MAINTENANCE: //TODO:  user puts the node into standby mode or maintenance mode...
        ActiveToMaintenance(action);
        break;
   case MAINTENANCE_STATE:
        MaintenanceState(action);
        break;
   case ONLINE_STATE:
        OnlineState(action);
        break;
   }
}

int CStateMachine::ChecksumState(const char action)
{
}

int CStateMachine::StandbyToMaintenance(const char action)
{
   int seqno = clusterInfo.MySeqno() + 1;
   unsigned char buffer[1024];

   LogMsg( INFO, "StandbyToMaintenance(): ACTION: %c", action);

   clusterInfo.MyRole( UNKNOWN );
   clusterInfo.MyStatus ( MAINTENANCE_STATUS );
   clusterInfo.State( MAINTENANCE_STATE );

   crm->Assemble( buffer, seqno, clusterInfo.MyRole(), MAINTENANCE );
   clusterInfo.MySeqno( seqno );
   UDPSend(cp, buffer, strlen((char *)buffer));
   return 1;
}

int CStateMachine::ActiveToMaintenance(const char action)
{
   int seqno = clusterInfo.MySeqno() + 1;
   unsigned char buffer[1024];

   LogMsg( INFO, "ActiveToMaintenance(): ACTION: %c", action);
   clusterInfo.MyRole( UNKNOWN );

   crm->Assemble( buffer, seqno, clusterInfo.MyRole(), ACTIVE2MAINTENANCE );
   clusterInfo.MySeqno( seqno );
   UDPSend(cp, buffer, strlen((char *)buffer));
   clusterInfo.State( MAINTENANCE_STATE );
   clusterInfo.MyStatus ( MAINTENANCE_STATUS );
   Demote( true );  // argument is true if user force the node to become standby...     

   return 1;
}

int CStateMachine::MaintenanceState(const char action)
{
   if ( clusterInfo.MyRole() == UNKNOWN ) {
      //printf("Role Unknown: Status: Maintenance...\n");
      LogMsg( DEBUG, "MaintenanceState(): ROLE: UNKNOWN    ACTION: %c", action);
      return 1;
   }

   if ( clusterInfo.MyRole() == ACTIVE ) {
      LogMsg( INFO, "MaintenanceState(): ROLE: ACTIVE    ACTION: %c", action);
      clusterInfo.State( ACTIVE_TO_MAINTENANCE );
   }else if ( clusterInfo.MyRole() == STANDBY ){
      LogMsg( INFO, "MaintenanceState(): ROLE: STANDBY   ACTION: %c", action);
      clusterInfo.State( STANDBY_TO_MAINTENANCE );
   }
   clusterInfo.MyStatus( MAINTENANCE_STATUS );
   
   return 1;
}

int CStateMachine::OnlineState(const char action)
{

   LogMsg( INFO, "OnlineState(): ACTION: %s Role: %s", clusterInfo.StrAction(action), clusterInfo.StrRole(clusterInfo.MyRole()));
   clusterInfo.MyStatus( ONLINE_STATUS );
   clusterInfo.State( UNKNOWN_STATE );

   return 1;
}

int CStateMachine::UnknownState(const char action)
{
   //** ignore the state and action...
   LogMsg( INFO, "UnknownState(): ACTION: %s", clusterInfo.StrAction(action));
   clusterInfo.State( NEGOTIATION_STATE );
   ResetRebootCounter();

   return 1;
}

// TODO: Serialize the access to this function.
// It's possible that we are already the ACTIVE node while at the NegotiationState. In this case,
// we should NAK the NEGOTIATION request from the peer. 

int CStateMachine::NegotiationState(const char action)
{
   int negctr = clusterInfo.NegotiateCounter() + 1;    
   unsigned int seqno = clusterInfo.MySeqno() + 1;
   UCHAR buffer[512];
    
   printf("NegotiationState(): ACTION: %s\n", clusterInfo.StrAction(action));

   pthread_mutex_lock( &sm_mutex );

   if ( clusterInfo.State() != NEGOTIATION_STATE ){
      pthread_mutex_unlock( &sm_mutex );
      return 1;
   }

   if ( action == 0) {
      LogMsg( INFO, "NegotiationState(): Role: %s", clusterInfo.StrRole(clusterInfo.MyRole()));

      clusterInfo.NegotiateCounter( negctr );
      if ( negctr > cp->MaxNegotiate() ){
         LogMsg( INFO, "NegotiationState(): MAX NEGOTIATION REACHED!!!");
         clusterInfo.MyLastRole( clusterInfo.MyRole() );
         clusterInfo.MyRole( ACTIVE );
         clusterInfo.State( UNKNOWN_TO_ACTIVE );
      }else{
         clusterInfo.MyLastAction( NEGOTIATE );
         crm->Assemble( buffer, seqno, 'U', NEGOTIATE );
         clusterInfo.MySeqno( seqno );
 
         if ( UDPSend( cp, buffer, strlen((char *)buffer)) < 0 ){
            perror("UDPSend");
         }
      }
   }else {  //** we received action from the peer
      LogMsg( INFO, "NegotiationState(): PEER ACTION: %s  Role: %s", clusterInfo.StrAction(action), 
         clusterInfo.StrRole(clusterInfo.MyRole()));
      switch(action) {
      case NEGOTIATE:
           if ( ( cp->Preferred() && clusterInfo.PeerPriority() != 'P' ) || clusterInfo.MyRole() == ACTIVE ) { 
              // if we are the preferred node and peer is not then reject the request...
              LogMsg( INFO, "NegotiationState(): NAK'ing Peer. My Role: %s", clusterInfo.StrRole(clusterInfo.MyRole()));
              clusterInfo.MyLastRole( clusterInfo.MyRole() );
              clusterInfo.MyRole( ACTIVE );
              clusterInfo.NegotiateCounter(0);
              clusterInfo.State( UNKNOWN_TO_ACTIVE );
              crm->Assemble( buffer, seqno, clusterInfo.MyRole(), NAK );
           }else{ // let's check if the peer is the preferred...
              if ( clusterInfo.PeerPriority() == 'P' && !cp->Preferred() )
                 crm->Assemble( buffer, seqno, clusterInfo.MyRole(), ACK2NEG );
              else {
                 LogMsg(WARNING, "Warning!!! Both nodes are non-preferred... Calculating the IP Address...\n");
                 if ( clusterInfo.MyIP() > clusterInfo.PeerIP() ){
                    LogMsg(INFO, "This node has higher priority...");
                    clusterInfo.MyLastRole( clusterInfo.MyRole() );
                    clusterInfo.MyRole( ACTIVE );
                    clusterInfo.NegotiateCounter(0);
                    clusterInfo.State( UNKNOWN_TO_ACTIVE );
                    crm->Assemble( buffer, seqno, clusterInfo.MyRole(), NAK );
                 }else{
                    LogMsg(INFO, "Peer Has priority...\n");
                    crm->Assemble( buffer, seqno, clusterInfo.MyRole(), ACK2NEG );
                    clusterInfo.MyRole( STANDBY );
                    clusterInfo.NegotiateCounter(0);
                    clusterInfo.State( UNKNOWN_TO_STANDBY );
                 }
              }
           }
           clusterInfo.MySeqno( seqno );
           UDPSend(cp, buffer, strlen((char *)buffer));
           break;
      case ACK2NEG:
           printf("Becoming Active...\n");
           LogMsg( INFO, "NegotiationState(): ACK2NEG rec'd. Becoming ACTIVE...");
           clusterInfo.MyLastRole( clusterInfo.MyRole() );
           clusterInfo.MyRole( ACTIVE );
           clusterInfo.NegotiateCounter(0);
           clusterInfo.State( UNKNOWN_TO_ACTIVE );
           clusterInfo.DumpClusterData( stdout );

           break;
      case NAK:
           LogMsg( INFO, "NegotiationState(): NAK rec'd. Becoming STANDBY...");
           clusterInfo.MyRole( STANDBY );
           clusterInfo.NegotiateCounter(0);
           clusterInfo.State( UNKNOWN_TO_STANDBY );
           break; 
      }
   }

   pthread_mutex_unlock( &sm_mutex );

   return 1;
}

int CStateMachine::UnknownToActive(const char action)
{
   LogMsg(INFO, "UnknownToActive... Current Role: %c   Action: %c", clusterInfo.MyRole(), action);
   clusterInfo.MySeqno( 0 );
   clusterInfo.NegotiateCounter( 0 );
   clusterInfo.MyLastRole( clusterInfo.MyRole() );
   clusterInfo.MyRole( ACTIVE );
   clusterInfo.State( ACTIVE_STATE );
   Promote();
   return 1;
}

int CStateMachine::UnknownToStandby(const char action)
{
   LogMsg(INFO, "UnknownToStandby...");
   clusterInfo.MySeqno( 0 );
   clusterInfo.NegotiateCounter( 0 );
   clusterInfo.MyLastRole( UNKNOWN );
   clusterInfo.MyRole( STANDBY );
   clusterInfo.State( STANDBY_STATE );
   StartAsStandby();
   return 1;
}

int CStateMachine::StandbyToActive(const char action)
{
   LogMsg(INFO, "StandbyToActive... Current Role: %c   ACTION: %c", clusterInfo.MyRole(), action);
   clusterInfo.MyLastRole( STANDBY );
   clusterInfo.MyRole( ACTIVE );
   clusterInfo.State( ACTIVE_STATE );
   Promote();
   return 1;
}

//! \brief This state should happen only when the user force the ACTIVE node
//! to become STANDBY.  TODO(if needed): Wait for ACK from peer before going to STANDBY.
int CStateMachine::ActiveToStandby(const char action)
{
   int seqno = clusterInfo.MySeqno() + 1;
   UCHAR buffer[512];

   LogMsg(INFO, "ActiveToStandby...");

   if ( action == 0 ) {
   }

   crm->Assemble( buffer, seqno, clusterInfo.MyRole(), ACTIVE2STANDBY, clusterInfo.MyBackupChecksum() );
   clusterInfo.MySeqno( seqno );
   UDPSend(cp, buffer, strlen((char *)buffer));
   Demote( true );  // argument is true if user force the node to become standby...     
   clusterInfo.Reset();
   clusterInfo.State( STANDBY_STATE );
   clusterInfo.MyRole( STANDBY );

   if ( action == ACTIVE2STANDBY_ACK ) {
      LogMsg(INFO, "Received ACTIVE2STANDBY_ACK...");
   }

   return 1;
}

int CStateMachine::Active(const char action)
{
   int seqno = clusterInfo.MySeqno() + 1;
   UCHAR buffer[512];

   LogMsg(DEBUG, "Active... ACTION: %c", action);
   if ( action == 0 ) {
      // TODO: check when is the last time we sent HB...
      // We may not need to send HB to STANDBY. A HB data from Standby is good enough for us to assume that the standby is still 
      // alive.
      clusterInfo.MyLastAction( HEARTBEAT );
      crm->Assemble( buffer, seqno, clusterInfo.MyRole(), HEARTBEAT, clusterInfo.MyBackupChecksum() );
      clusterInfo.MySeqno( seqno );
      UDPSend(cp, buffer, strlen((char *)buffer));

      return 1;
   }

   // TODO: check when is the last time we received an ACK for Heartbeat...
   switch(action) {

      case MAINTENANCE: // standby node is going into standby status...
           LogMsg( INFO, "Peer is going into maintenance.");
           clusterInfo.PeerStatus( MAINTENANCE_STATUS );
           break;
      case HEARTBEAT:
           printf("<Active State> HeartBeat...[%d][%d] LastHBACK:%u\n", clusterInfo.PeerSeqno(), clusterInfo.ExpectedSeqno(), (unsigned int)clusterInfo.LastAck2HB());

           // if last heartbeat from standby is older that 30 secs then update the expected sequence number to active's current
           // sequence number.
           if ( clusterInfo.LastAck2HB() > 20 ) {
              LogMsg(INFO, "Last heartbeat from STANDBY is too old... Request to re-sync!!!");
              crm->Assemble( buffer, seqno, clusterInfo.MyRole(), RST);
              clusterInfo.UpdateLastACK2HB();
           }else {   
              if ( clusterInfo.PeerSeqno() == clusterInfo.ExpectedSeqno() ) {
                 clusterInfo.UpdateLastACK2HB();
                 crm->Assemble( buffer, seqno, clusterInfo.MyRole(), ACK2HB);
                 clusterInfo.ExpectedSeqno( clusterInfo.ExpectedSeqno() + 1 );
              }else {
                 clusterInfo.DumpClusterData( stdout );
                 printf("<Active State> HeartBeat Received! incorrect seqno ...\n" );
                 crm->Assemble( buffer, seqno, clusterInfo.MyRole(), RST);
              }
           }
           clusterInfo.MySeqno( seqno );
           UDPSend(cp, buffer, strlen((char *)buffer));
           break;
      case NEGOTIATE:  ////** peer just started and requesting to have the cluster IP. Reject the request...
           LogMsg( INFO, "<Active State> Negotiate Request received...");
           crm->Assemble( buffer, seqno, clusterInfo.MyRole(), NAK);
           clusterInfo.MySeqno( seqno );
           UDPSend(cp, buffer, strlen((char *)buffer));
           break;
      case SYN:  ////** standby requesting to sync the sequence number
           LogMsg(INFO, "<Active State> SYN Request received from peer...\n");
           if ( clusterInfo.ExpectedSeqno() == 0 ){
              clusterInfo.ExpectedSeqno( clusterInfo.MySeqno() + 1 );
           }else {
              clusterInfo.ExpectedSeqno( clusterInfo.MySeqno() );
           }
           crm->Assemble( buffer, clusterInfo.ExpectedSeqno(), clusterInfo.MyRole(), SYNACK);
           clusterInfo.MySeqno( seqno );
           UDPSend(cp, buffer, strlen((char *)buffer));
           break;
      case ACK2HB:
           printf("<Active State> Heartbeat ACK received...\n");
           clusterInfo.PeerStatus( ONLINE_STATUS );
           break;
   }

   return 1;
}

int CStateMachine::Standby(const char action)
{
   UCHAR buffer[512];
   int seqno = clusterInfo.MySeqno();


   if ( action == 0 ) {
      // TODO: check when is the last time we sent HB and recv ACK from ACTIVE...
      crm->Assemble( buffer,  seqno, clusterInfo.MyRole(), HEARTBEAT, clusterInfo.MyBackupChecksum() );
      UDPSend(cp, buffer, strlen((char *)buffer));
      return 1;
   }

   LogMsg(DEBUG, "STANDBY State... ACTION: %s", clusterInfo.StrAction(action));
   switch(action) {

      case ACTIVE2STANDBY:
           LogMsg( INFO, "<Standby State> Active is about to go to STANDBY...\n");
           crm->Assemble( buffer, seqno, clusterInfo.MyRole(), ACTIVE2STANDBY_ACK); // this may not be needed but send it anyway
           clusterInfo.MySeqno( seqno );
           UDPSend(cp, buffer, strlen((char *)buffer));
           clusterInfo.State( STANDBY_TO_ACTIVE );
           break;
      case ACTIVE2MAINTENANCE:
           LogMsg( INFO, "<Standby State> Active is about to go to MAINTENANCE...\n");
           crm->Assemble( buffer, seqno, clusterInfo.MyRole(), ACTIVE2MAINTENANCE_ACK); // this may not be needed but send it anyway
           clusterInfo.MySeqno( seqno );
           UDPSend(cp, buffer, strlen((char *)buffer));
           clusterInfo.State( STANDBY_TO_ACTIVE );
           break;
      case HEARTBEAT:
           printf("<Standby State> HeartBeat...\n");
           crm->Assemble( buffer, seqno, clusterInfo.MyRole(), ACK2HB);
           clusterInfo.MySeqno( seqno );
           UDPSend(cp, buffer, strlen((char *)buffer));
           break;
      case NEGOTIATE:  ///** peer just started and requesting to have the cluster IP. Reject the request...
           //** should not go into state, otherwise there is a timing issue... REJECT the request any, and become active.
           printf("<Standby State> Negotiate Request received...\n");
           clusterInfo.State( STANDBY_TO_ACTIVE );
           crm->Assemble( buffer, seqno, clusterInfo.MyRole(), NAK);
           clusterInfo.MySeqno( seqno );
           UDPSend(cp, buffer, strlen((char *)buffer));
           break;
      case SYN:  ////** standby requesting to sync the sequence number
           printf("<Standby State> SYN Request received...Should not go into this state!!!\n");
           break;
      case SYNACK: /*** synchronize our sequence number to the ACTIVE... ***/
           clusterInfo.MySeqno( clusterInfo.PeerSeqno() );
           LogMsg(INFO, "ACTIVE's expected sequence number is: %d\n", clusterInfo.PeerSeqno());
           break;
      case RST: /*** ACTIVE wants to resync the seqno ***////
           LogMsg(INFO, "ACTIVE node wants to resync sequence number...\n");
           crm->Assemble( buffer, seqno, clusterInfo.MyRole(), SYN);
           UDPSend(cp, buffer, strlen((char *)buffer));
           break;
      case ACK2HB:
           printf("<Standby State> Heartbeat ACK received...\n");
           clusterInfo.MySeqno( seqno + 1 );
           clusterInfo.PeerStatus( ONLINE_STATUS );
           break;
   }

   return 1;
}


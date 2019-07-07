#ifndef _STATEMACHINE_H_
#define _STATEMACHINE_H_

using namespace std;

#include"params.h"
#include"reqmsg.h"
#include"miscutils.h"


class CStateMachine : public CMiscUtils
{
public:
   CStateMachine( CParams *param, CReqMsg *req ) : cp(param), crm(req) {}
   void ProcessStateMachine(const char state, const char action=0);
   int UnknownState(const char action);
   int NegotiationState(const char action);
   int UnknownToActive(const char action);
   int UnknownToStandby(const char action);
   int StandbyToActive(const char action);
   int ActiveToStandby(const char action);
   int ActiveToMaintenance(const char action);
   int StandbyToMaintenance(const char action);
   int Active(const char action);
   int Standby(const char action);
   int MaintenanceState(const char action);
   int OnlineState(const char action);
   int ChecksumState(const char action);


private:
   CParams *cp;
   CReqMsg *crm;
   static pthread_mutex_t sm_mutex;
};

#endif



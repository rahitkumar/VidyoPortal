#ifndef _REQ_MSG_H_
#define _REQ_MSG_H_

#include "clusterip.h"
#include "statemachine.h"

class CReqMsg
{
public:
   CReqMsg();
   ~CReqMsg();
   void Uptime(unsigned int ut);
   void IP(const char *ip);
   void Priority(char c);
   void DBVersion(const char *s);

   char *Assemble(UCHAR *outbuf, unsigned int seqno, const char role, const HA_EVENTS action, const char *repl_status = NULL );


private:
   char priority;
   unsigned int uptime;
   char ipaddr[64];
   char db_version[16];
};

#endif

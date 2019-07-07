using namespace std;

#include<stdio.h>
#include<time.h>
#include<string.h>
#include"reqmsg.h"
#include"clusterip.h"

extern time_t starttime;

CReqMsg::~CReqMsg()
{

}

CReqMsg::CReqMsg()
{
   uptime = 0;
   memset( ipaddr, 0, sizeof(ipaddr) );
   memset( db_version, 0, sizeof(db_version) );
}

//! \brief Sets the uptime on the message.
void CReqMsg::Uptime(unsigned int ut)
{
   uptime = ut;
}

//! \brief Set the IP address on the message
void CReqMsg::IP(const char *ip)
{
   strcpy(ipaddr, ip);
}

void CReqMsg::Priority(char c)
{
   priority = c;
}

void CReqMsg::DBVersion(const char *s)
{
   strncpy(db_version, s, 10);
}

//! \brief Assemble a request message for negotiation depending on the 'action' parameter.
//! Action Code:
//! NEGOTIATE = 'N';
//! HEARTBEAT = 'H';
//! ONLINE = 'O';
//! STANDBY = 'F';
//! ACK = 'A';
//! REJECT = 'R';
//! Note: valid roles 'U' - Unknown, 'M' - Active  'S' - Standby   
char *CReqMsg::Assemble(UCHAR *outbuf, unsigned int seqno, const char role, const char action, const char *csum)
{
   char buffer[1024];
   char temp[128];
   time_t now;
   time( &now );
   uptime = now - starttime;

   strcpy(buffer, "<MsgType>CINFO</MsgType>");

   sprintf(temp, "<SeqNo>%u</SeqNo>", seqno);
   strcat(buffer, temp);

   sprintf(temp, "<Priority>%c</Priority>", priority);
   strcat(buffer, temp);

   sprintf(temp, "<Role>%c</Role>", role);
   strcat(buffer, temp);

   sprintf(temp, "<Action>%c</Action>", action);
   strcat(buffer, temp);

   sprintf(temp, "<Uptime>%u</Uptime>", uptime);
   strcat(buffer, temp);

   sprintf(temp, "<Ip>%s</Ip>", ipaddr);
   strcat(buffer, temp);

   sprintf(temp, "<DbVersion>%s</DbVersion>", db_version);
   strcat(buffer, temp);

   if ( csum != NULL ) {
      sprintf(temp, "<Checksum>%s</Checksum>", csum);
      strcat(buffer, temp);
   }

   return strcpy((char *)outbuf, buffer);

   //return (char *)outbuf;
}


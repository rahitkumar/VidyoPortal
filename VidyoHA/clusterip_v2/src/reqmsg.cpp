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

//! \brief Assemble a request message.
//! Note: valid roles 'U' - Unknown, 'M' - Active  'S' - Standby   
char *CReqMsg::Assemble(UCHAR *outbuf, unsigned int seqno, const char role, const HA_EVENTS event, const char *repl_status)
{
   char buffer[1024];
   char temp[256];
   int temp_size=sizeof(temp)-1;
   time_t now;
   time( &now );
   uptime = now - starttime;

   memset(buffer, 0, sizeof(buffer));
   strcpy(buffer, "<MsgType>CINFO</MsgType>");

   memset(temp, 0, sizeof(temp));
   snprintf(temp, temp_size, "<SeqNo>%u</SeqNo>", seqno);
   strcat(buffer, temp);

   memset(temp, 0, sizeof(temp));
   snprintf(temp, temp_size, "<Priority>%c</Priority>", priority);
   strcat(buffer, temp);

   memset(temp, 0, sizeof(temp));
   snprintf(temp, temp_size, "<Role>%c</Role>", role);
   strcat(buffer, temp);

   memset(temp, 0, sizeof(temp));
   snprintf(temp, temp_size, "<Event>%d</Event>", event);
   strcat(buffer, temp);

   memset(temp, 0, sizeof(temp));
   snprintf(temp, temp_size, "<Uptime>%u</Uptime>", uptime);
   strcat(buffer, temp);

   memset(temp, 0, sizeof(temp));
   snprintf(temp, temp_size, "<Ip>%s</Ip>", ipaddr);
   strcat(buffer, temp);

   memset(temp, 0, sizeof(temp));
   snprintf(temp, temp_size, "<DbVersion>%s</DbVersion>", db_version);
   strcat(buffer, temp);

   if ( repl_status && strlen(repl_status) > 0 ) {
      memset(temp, 0, sizeof(temp));
      snprintf(temp, temp_size, "<DBSyncStatus>%s</DBSyncStatus>", repl_status);
      strcat(buffer, temp);
   }

   return strcpy((char *)outbuf, buffer);

   //return (char *)outbuf;
}


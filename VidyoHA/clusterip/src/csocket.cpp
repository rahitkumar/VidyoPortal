// Implementation of the Socket class.
// TODO: validate accept()... trap EINTR

using namespace std;

#include <fcntl.h>
#include <iostream>

#include<netinet/in.h>
#include<arpa/inet.h>
#include<netinet/tcp.h>

#include <string.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>

#include "csocket.h"


CSocket::CSocket() : m_sock ( -1 )
{
  memset ( &m_addr, 0, sizeof ( m_addr ) );
  //LogMsg("CSocketConstructor...");
}

CSocket::~CSocket()
{
  if ( IsValid() ){
    //LogErr("Destructor: Closing socket ID [%d]", m_sock);
    //close ( m_sock );
    m_sock = -1;
  }
}

bool CSocket::Close()
{
  //LogErr("CSocket::Close(): Closing socket ID [%d]", m_sock);
   if ( shutdown( m_sock, SHUT_RDWR ) ){
      LogErr("shutdown(): %s", strerror_r(errno, s_error, sizeof(s_error)));
   }
   if ( close( m_sock ) < 0 ){
      LogErr("close(): %s", strerror_r(errno, s_error, sizeof(s_error)));
   }
   m_sock = -1;
   return true;
}

bool CSocket::Create(int socktype)
{
   m_sock = socket ( AF_INET, socktype, 0 );

   if ( ! IsValid() )
      return false;

   // TIME_WAIT - argh
   int on = 1;
  if ( setsockopt ( m_sock, SOL_SOCKET, SO_REUSEADDR, ( const char* ) &on, sizeof ( on ) ) == -1 )
    return false;

  return true;
}


bool CSocket::Bind ( const int port, const char *bindip )
{
   if ( ! IsValid() ) {
      return false;
   }

   m_addr.sin_family = AF_INET;
   m_addr.sin_port = htons ( port );
   if ( bindip ) {
      int status = inet_pton ( AF_INET, bindip, &m_addr.sin_addr );
      if ( status <  0 )
      return false;
   }else {
      m_addr.sin_addr.s_addr = INADDR_ANY;
   }

   int bind_return = bind(m_sock, (struct sockaddr *)&m_addr, sizeof(m_addr));


   if ( bind_return == -1 ) {
      LogErr("bind(): %s\n", strerror_r(errno, s_error, sizeof(s_error)-1));
      return false;
   }

   return true;
}


bool CSocket::Listen() 
{
   if ( ! IsValid() ) {
      return false;
   }

   int listen_return = listen ( m_sock, MAXCONNECTIONS );

   if ( listen_return == -1 ) {
      LogErr("listen(): %s\n", strerror_r(errno, s_error, sizeof(s_error)));
      return false;
   }

   return true;
}


bool CSocket::Accept ( CSocket&  new_socket ) 
{
   int addr_length = sizeof ( m_addr );
   char temp[32];
   
   while(1){
      new_socket.m_sock = accept ( m_sock, ( sockaddr * ) &m_addr, ( socklen_t * ) &addr_length );
     if ( new_socket.m_sock < 0 && errno == EINTR )
       continue; 
     break;
   }
   if ( new_socket.m_sock <= 0 )
      return false;

   struct sockaddr_in sockname;
   socklen_t socklen=17;

   if ( getpeername(new_socket.m_sock, (struct sockaddr *)&sockname, &socklen) == 0 ){
      new_socket.clientip = inet_ntop( AF_INET, &sockname.sin_addr, temp, sizeof(temp) );
      new_socket.clientipn = sockname.sin_addr.s_addr;
      new_socket.clientport = sockname.sin_port;
   }else{
      LogErr("getpeername() :%s\n", strerror_r(errno, s_error, sizeof(s_error)));
   }
   return true;
}


bool CSocket::Send ( const std::string s ) const
{
   int status = ::send ( m_sock, s.c_str(), s.size(), MSG_NOSIGNAL );
   if ( status == -1 )
      return false;
   else
      return true;
}

bool CSocket::Send ( const char *s ) const
{
   int status = ::send ( m_sock, s, strlen(s), MSG_NOSIGNAL );
   if ( status == -1 )
      return false;
   else
      return true;
}

bool CSocket::Send ( unsigned const char *s, const int len ) const
{
   int status = ::send ( m_sock, s, len, MSG_NOSIGNAL );
   if ( status == -1 )
      return false;
   else
      return true;
}

int CSocket::Recv ( std::string& s ) 
{
   char buf [ MAXRECV + 1 ];
   int ret;

   s = "";

   memset ( buf, 0, MAXRECV + 1 );

   while(1){
      int status = ::recv ( m_sock, buf, MAXRECV, 0 );

      if ( status == -1 ) {
         if ( errno == EINTR )
            continue;
         ErrCode("CSocket::recv", errno);
         ret=-1;
      }else if ( status == 0 ) {
         //ErrCode("CSocket::recv", errno);
         ret=0;
      }else {
         s = buf;
         ret=status;
      }

      break;
   }

   return ret;
}

int CSocket::Recv ( unsigned char *pbuf )
{
   unsigned char buf [ MAXRECV + 1 ];
   int status;

   memset ( buf, 0, MAXRECV + 1 );

   while(1){
      status = recv ( m_sock, buf, MAXRECV, 0 );

      if ( status == -1 ) {
         if ( errno == EINTR )
            continue;
         ErrCode("CSocket::recv", errno);
      }else if ( status == 0 ) {
      }else {
         memcpy(pbuf, buf, status);
      }

      break;
   }

   return status;
}

int CSocket::Recv ( unsigned char *pbuf, const int readlen )
{
   unsigned char buf [ MAXRECV + 1 ];
   int status;

   memset ( buf, 0, MAXRECV + 1 );

   while(1){
      status = recv ( m_sock, buf, readlen, 0 );

      if ( status == -1 ) {
         if ( errno == EINTR )
            continue;
         ErrCode("CSocket::recv", errno);
      }else if ( status == 0 ) {
      }else {
         memcpy(pbuf, buf, status);
      }

      break;
   }

   return status;
}


//! \brief Establish a TCP connection to a TCPServer
//! host - string that contains the host IP address
//! port - host port number
//! sport - The source port to be used when establishig a TCP connection to the
//!         TCP server. If the sport is 0, then the kernel will use the nex 
//!         available port.
//! tmo - number of seconds to wait when establishing a TCP connection.
bool CSocket::Connect ( const std::string host, const int port, const int sport, const int tmo )
{
   fd_set rds, wds,eds;
   struct timeval tv;
   int ret;
   struct sockaddr_in client_sock;
   //char temp[32];

   if ( ! IsValid() ) return false;

   m_addr.sin_family = AF_INET;
   m_addr.sin_port = htons ( port );

   int status = inet_pton ( AF_INET, host.c_str(), &m_addr.sin_addr );

   if ( errno == EAFNOSUPPORT ) return false;

   // set the m_sock to non-blocking mode
   SetNonBlocking( true );

   if ( sport > 0 ){
      memset(&client_sock, 0, sizeof(client_sock));
      client_sock.sin_family = AF_INET;
      client_sock.sin_port = htons( sport ); //let's specify the source port   
      client_sock.sin_addr.s_addr = htonl( INADDR_ANY );

      ret=bind( m_sock, (struct sockaddr *)&client_sock, sizeof(struct sockaddr));
      if ( ret < 0){
         LogErr("Connect()->bind():%s\n", 
              strerror_r(errno, s_error, sizeof(s_error)));
         return false;
      }

      //LogMsg(4, "Connecting to [%s][%d] from src port[%d]\n", 
      //   inet_ntop(AF_INET, &m_addr.sin_addr, temp, sizeof(temp)), port, sport);

   }else{
    //  LogMsg(4, "Connecting to [%s][%d]\n", inet_ntop(AF_INET, &m_addr.sin_addr, temp, sizeof(temp)), port );
   }
   status = connect ( m_sock, ( sockaddr * ) &m_addr, sizeof ( m_addr ) );
   if ( status <= 0 ){
      if ( errno == EINPROGRESS ){
      }
      while(1){
         FD_ZERO( &rds );
         FD_ZERO( &wds );
         FD_ZERO( &eds );
         FD_SET( m_sock, &wds);
         FD_SET( m_sock, &eds);
         FD_SET( m_sock, &rds);

         tv.tv_sec=tmo;
         tv.tv_usec=0;
         ret=select(m_sock+1,  &rds, &wds, &eds, &tv);

         if ( ret > 0 && FD_ISSET(m_sock, &wds) && FD_ISSET(m_sock, &rds)){
            //LogErr("connect()->select(): %s\n", strerror_r(errno, s_error, sizeof(s_error)));
            status=-1;
            break;
         }else if ( ret > 0 && FD_ISSET(m_sock, &wds) ){
            status=0;
            break;
         }else if ( ret < 0 && errno != EINTR ){
            LogErr("Error Connecting to %s:%d\n", host.c_str(), port);
            status=-1;
            break;
         }else if ( ret == 0 ){
            LogErr("(Timer Expired)!Error Connecting to %s:%d\n", host.c_str(), port);
            status=-1;
            break;
         }
      }
   }

   if ( status < 0 )
      return false;
   else{
      clientip = host;
      return true;
   }
}

//! \brief Establish a TCP connection to a TCPServer
//! host - string that contains the host IP address
//! port - host port number
//! sport - The source port to be used when establishig a TCP connection to the
//!         TCP server. If the sport is 0, then the kernel will use the nex 
//!         available port.
//! source_ip - The source IP address to be used when establishing a TCP connection to the TCP Server. If the source_ip is "", then it will use INADDR_ANY.
//! tmo - number of seconds to wait when establishing a TCP connection.
bool CSocket::Connect ( const std::string host, const int port, const int sport,const std::string source_ip,  const int tmo )
{
   fd_set rds, wds,eds;
   struct timeval tv;
   int ret;
   struct sockaddr_in client_sock;
   //char temp[32];
   //char temp2[32];
   

   if ( ! IsValid() ) return false;

   m_addr.sin_family = AF_INET;
   m_addr.sin_port = htons ( port );

   int status = inet_pton ( AF_INET, host.c_str(), &m_addr.sin_addr );

   if ( errno == EAFNOSUPPORT ) return false;

   // set the m_sock to non-blocking mode
   SetNonBlocking( true );

   memset(&client_sock, 0, sizeof(client_sock));
   client_sock.sin_family = AF_INET;
   if ( sport > 0 ){
      client_sock.sin_port = htons( sport ); //let's specify the source port   
   }else{
      client_sock.sin_port = INADDR_ANY; //let's specify the source port   
   }

   if ( source_ip.length() > 0 ) {
      status = inet_pton ( AF_INET, source_ip.c_str(), &client_sock.sin_addr );
  
      if ( errno == EAFNOSUPPORT ) 
         return false;
   }else
      client_sock.sin_addr.s_addr = htonl( INADDR_ANY );

   ret=bind( m_sock, (struct sockaddr *)&client_sock, sizeof(struct sockaddr));
   if ( ret < 0){
      LogErr("Connect()->bind():%s\n", 
              strerror_r(errno, s_error, sizeof(s_error)));
      return false;
   }

   //LogMsg(4, "Connecting to [%s][%d] from src port[%s:%d]\n", 
   //      inet_ntop(AF_INET, &m_addr.sin_addr, temp, sizeof(temp)), port, 
   //      inet_ntop(AF_INET, &client_sock.sin_addr, temp2, sizeof(temp2)), client_sock.sin_port );

   //printf("Connecting to [%s][%d] from src port[%s:%d]\n", 
   //      inet_ntop(AF_INET, &m_addr.sin_addr, temp, sizeof(temp)), port, 
   //      inet_ntop(AF_INET, &client_sock.sin_addr, temp2, sizeof(temp2)), client_sock.sin_port );

   status = connect ( m_sock, ( sockaddr * ) &m_addr, sizeof ( m_addr ) );

   if ( status <= 0 ){
      if ( errno == EINPROGRESS ){
         //LogMsg(4,"Connection in progress...\n");
      }
      while(1){
         FD_ZERO( &rds );
         FD_ZERO( &wds );
         FD_ZERO( &eds );
         FD_SET( m_sock, &wds);
         FD_SET( m_sock, &eds);
         FD_SET( m_sock, &rds);

         tv.tv_sec=tmo;
         tv.tv_usec=0;
         ret=select(m_sock+1,  &rds, &wds, &eds, &tv);
         //ret=select(m_sock+1,  NULL, &wds, NULL, &tv);

         if ( ret > 0 && FD_ISSET(m_sock, &wds) && FD_ISSET(m_sock, &rds)){
            //LogErr("connect()->select(): %s\n", strerror_r(errno, s_error, sizeof(s_error)));
            status=-1;
            break;
         }else if ( ret > 0 && FD_ISSET(m_sock, &wds) ){
            status=0;
            break;
         }else if ( ret < 0 && errno != EINTR ){
            LogErr("Error Connecting to %s:%d\n", host.c_str(), port);
            status=-1;
            break;
         }else if ( ret == 0 ){
            LogErr("(Timer Expired)!Error Connecting to %s:%d\n", host.c_str(), port);
            status=-1;
            break;
         }
      }
   }

   if ( status < 0 )
      return false;
   else{
      clientip = host;
      return true;
   }
}

//! \brief Establish a TCP connection to a TCPServer
//! host - string that contains the host IP address
//! port - host port number
//! Note: This function will use the default TCP connect timeout used by the i
//!       system.
bool CSocket::Connect ( const std::string host, const int port )
{
   if ( ! IsValid() ) return false;

   m_addr.sin_family = AF_INET;
   m_addr.sin_port = htons ( port );

   int status = inet_pton ( AF_INET, host.c_str(), &m_addr.sin_addr );

   if ( errno == EAFNOSUPPORT ) return false;

   status = ::connect ( m_sock, ( sockaddr * ) &m_addr, sizeof ( m_addr ) );

   if ( status == 0 ){
      clientip = host;
      return true;
   }else
      return false;
}

//! \brief This function logs the error code.
void CSocket::ErrCode( const char *desc, int err )  
{
   const char *ptr;
   switch( err ){
   case EBADF: ptr = "EBADF: Invalid Desriptor"; break;
   case ECONNREFUSED: ptr = "ECONNREFUSED: Connection Refused!"; break;
   case ENOTCONN: ptr = "ENOTCONN: Disconnected socket!"; break;
   case ENOTSOCK: ptr = "ENOTSOCK: Invalid Socket!"; break;
   case EAGAIN: ptr = "EAGAIN: No data received!"; break;
   case EINTR: ptr = "EINTR: Interrupted by a signal!"; break;
   case EFAULT: ptr = "EFAULT: Invalid address space!"; break;
   case EINVAL: ptr = "EINVAL: Invalid argument!"; break;
   case ENOMEM: ptr = "ENOMEM: Cannot allocate memory!"; break;
   default:
      ptr = strerror_r(errno, s_error, sizeof(s_error));
   }

   LogErr("%s[%s]\n", desc, ptr);

   errcode=err;
}

//! \brief Set the socket to blocking or non-blocking.
//! If b is ture then socket is in "NON-BLOCKING mode"
//!
void CSocket::SetNonBlocking ( const bool b )
{
   int opts;

   opts = fcntl ( m_sock, F_GETFL );

   if ( opts < 0 ) {
      return;
   }

   if ( b )
      opts = ( opts | O_NONBLOCK );
   else
     opts = ( opts & ~O_NONBLOCK );

   fcntl ( m_sock, F_SETFL, opts );
}

//! \brief Returns the client ip address in string. 
//! This function is useful when implementing a TCP Server.
string CSocket::GetClientIP()
{
   return clientip;
}

//! \brief Returns the client ip address in numeric value. 
//! This function is useful when implementing a TCP Server.

unsigned int CSocket::GetClientIPn()
{
   return clientipn;
}
int CSocket::KeepAliveEnabled(const bool flag)
{
   //int keep=( flag ? 1 : 0 );
   int keep=1;
   int ret=1;

   if (setsockopt(m_sock, SOL_SOCKET, SO_KEEPALIVE, &keep, sizeof(int)) == -1){
      ret=-1;
      LogErr("KeepAliveEnabled(): failed on setsockopt : %s\n", 
         strerror_r(errno, s_error, sizeof(s_error)));
   }

   return ret;
}


int CSocket::SetKeepAliveIdleTimer(const int idle)
{
   int idletimer=idle;
   //int idletimer=3;

   //printf("setting idle timer to [%d]\n", idletimer);

   if(setsockopt(m_sock,IPPROTO_TCP,TCP_KEEPIDLE,&idletimer,sizeof(int)) == -1) {
      LogErr("SetKeepAliveIdleTimer(): setsockopt(SO_KEEPIDLE) : %s\n",
         strerror_r(errno, s_error, sizeof(s_error)));
      return -1;
   }
   return 1;
}

int CSocket::SetKeepAliveInterval(const int interval)
{
   int intvl=interval;
   //int intvl=1;

   //printf("setting interval to [%d]\n", intvl);
   if(setsockopt(m_sock,IPPROTO_TCP,TCP_KEEPINTVL,&intvl,sizeof(int)) == -1) {
      LogErr("SetKeepAliveInterval(): setsockopt(SO_KEEPINTVL) %s\n", 
         strerror_r(errno, s_error, sizeof(s_error)));
      return -1;
   }

   return 1;
}


int CSocket::SetKeepAliveMaxCount(const int count)
{
   int cnt=count;

   //printf("Setting max_count to [%d]\n", cnt);
   if(setsockopt(m_sock,IPPROTO_TCP,TCP_KEEPCNT,&cnt,sizeof(int)) == -1) {
      LogErr("SetKeepAliveMaxCount(): setsockopt(SO_KEEPCOUNT) :%s\n",
         strerror_r(errno, s_error, sizeof(s_error)));
      return -1;
   }
   return 1;
}

void CSocket::PrintSocketID() 
{ 
   std::cout << "Socket ID[" << m_sock << "]" << endl;
}

//! Wait for event on the socket
//! returns:
//!    0 if timer expires. 1 Event triggered, -1 Error
int CSocket::Wait4Event ( int tmo )
{
   int selret;
   fd_set rds;
   struct timeval tv;
   //int ret;

   FD_ZERO( &rds );
   FD_SET( m_sock, &rds );  
   tv.tv_sec=tmo;
   tv.tv_usec=0;
       
   while(1){
      selret = select(m_sock+1, &rds, NULL, NULL, &tv);    
      if ( selret < 0  && errno == EINTR )
         continue;

      break;
   } 
   //if ( selret == 0 )
   //   ret=0;
   //else if ( selret > 0 )
   //   ret=1;
   //else 
   //   ret=selret;

   return selret;
}

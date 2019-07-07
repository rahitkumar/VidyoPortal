#ifndef Socket_class
#define Socket_class

using namespace std;

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <unistd.h>
#include <string>
#include <arpa/inet.h>

#include "miscutils.h"

enum SOCKET_TYPE { TCP, UDP };

const int MAXHOSTNAME = 200;
const int MAXCONNECTIONS = 2048;
const int MAXRECV = 2048;
const int TCP_READ_DISCONNECT = 0;
const int TCP_READ_OK = 1;
const int TCP_READ_TIMEOUT = -1;
const int TCP_SELECT_ERROR = -2;
const int TCP_READ_ERROR = -3;

class CSocket : public CMiscUtils
{
 public:
  CSocket();
  virtual ~CSocket();

  // Server initialization
  bool Close();
  bool Create( int socktype = SOCK_STREAM );
  bool Bind ( const int port, const char *bindip = NULL );
  bool Listen() ;
  bool Accept ( CSocket& ) ;

  // Client initialization
  bool Connect ( const std::string host, const int port );
  bool Connect ( const std::string host, const int port, const int sport, const int tmo );
  bool Connect ( const std::string host, const int port, const int sport, const std::string source_ip, const int tmo );

  // Data Transimission
  bool Send ( const char *s ) const;
  bool Send ( const std::string ) const;
  bool Send ( unsigned const char *s, const int len ) const;
  int  Recv ( unsigned char *pbuf );
  int  Recv ( unsigned char *pbuf, const int readlen );
  int Recv ( std::string& ) ;
  void ErrCode( const char *desc, int err ) ;
  int  KeepAliveEnabled(const bool flag);
  int  SetKeepAliveMaxCount(const int count);
  int  SetKeepAliveInterval(const int interval);
  int  SetKeepAliveIdleTimer(const int idle);
 
  void SetNonBlocking( const bool );
  void PrintSocketID();
  int  GetSocketID() { return m_sock; }
  int  GetErrorCode() { return errcode; }
  string GetClientIP();
  unsigned int GetClientIPn();
  unsigned int GetClientPort() { return clientport; };

  bool IsValid() const { return m_sock != -1; }
  int Wait4Event ( int tmo );


 private:

  int m_sock;
  sockaddr_in m_addr;
  int errcode;
  string clientip;
  unsigned int clientipn;
  unsigned int clientport;
  char s_error[128];
};


#endif

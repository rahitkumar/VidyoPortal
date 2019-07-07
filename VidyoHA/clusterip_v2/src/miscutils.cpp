#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <syslog.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <pthread.h>
#include <unistd.h>


#include "miscutils.h"

CMiscUtils* CMiscUtils::Initialize()
{
   if ( !pInstance )
      pInstance = new CMiscUtils;

   return pInstance;
}

CMiscUtils* CMiscUtils::GetInstance()
{
   return pInstance;
}

void CMiscUtils::RunLevel( int rl )
{
   m_runlevel = rl;
}


//! Brief Default construct for CMiscUtils.
CMiscUtils::CMiscUtils() : errstr(NULL)
{
}

//! \brief Default destructor for CMiscUtils.
CMiscUtils::~CMiscUtils()
{

}

//!
//! \brief Open a connection to the syslog daemon.
//! This function opens and connection and sets the message identifier for
//! every message being logged.
//!

void CMiscUtils::OpenLog(const char *ident)
{
   //openlog(ident, LOG_NOWAIT, LOG_USER|LOG_LOCAL6);
   openlog(ident, LOG_NOWAIT, LOG_LOCAL3);
}

//! \brief Wrapper function to syslog(). 
//! This is a wrapper function to sylog() and will allow application
//! to log the message depending on the runlevel
//! 

void CMiscUtils::LogMsg(int level, const char *fmt, ...)
{
   if ( m_runlevel >= level ){
      va_list args;
      char buffer[2048];
      char logmsg[2048];
      const char *loglevel;

 
      switch( level ) {
         case WARNING: loglevel = "WARNING"; break;
         case INFO: loglevel = "INFO"; break;
         case DEBUG: loglevel = "DEBUG"; break;
         default: loglevel = "UNKNOWN"; 
      }

      va_start(args, fmt);
      vsnprintf(buffer, sizeof(buffer), fmt, args);
      va_end(args);

      snprintf(logmsg, 2048,  "[%lu](%s)%s", pthread_self(), loglevel, buffer);
      //printf("%s\n", logmsg);
      syslog(LOG_NOTICE, logmsg);
   }
}

void CMiscUtils::LogMsg(const char *fmt, ...)
{
   va_list args;
   char buffer[2048];
   char logmsg[2048];

   va_start(args, fmt);
   vsnprintf(buffer, sizeof(buffer), fmt, args);
   va_end(args);

   snprintf(logmsg, 2048,  "[%lu]%s", pthread_self(), buffer);
   syslog(LOG_NOTICE, logmsg);
}

//! \brief Write mesage to system logs. Use this only for logging errors!
void CMiscUtils::LogErr(const char *fmt, ...)
{
   va_list args;
   char buffer[2048];
   char logmsg[2048];

   va_start(args, fmt);
   vsnprintf(buffer, sizeof(logmsg), fmt, args);
   va_end(args);

   snprintf(logmsg, 2048,  "[%lu]WARNING!!! %s", pthread_self(), buffer);
   syslog(LOG_NOTICE, logmsg);
}

//! \brief Create an error string depending on the errno.
void CMiscUtils::SetError( const int err, const char *desc )
{
   char buf[2048];

   errstr  = strerror_r(err, buf, sizeof(buf));
}

//! \brief Returns the error string set by SetError()
char * CMiscUtils::GetError()
{
  return errstr;
}

//! \brief This function will run the application as a daemon process.
void CMiscUtils::Daemonize()
{
   int pid, sid;

   if ( getppid() == 1 )
      return;

   pid = fork();
   if ( pid < 0 ) {
      perror("fork()");
      exit(-1);
   }else if (pid > 0 )
      exit(0);

   umask(0);

   if ((sid=setsid()) < 0 ){
      perror("setsid()");
      exit(-1);
   };

   if ( chdir("/") < 0 ){
      perror("cghdir");
      exit(-1);
   }

   freopen("/dev/null", "r", stdin);
   freopen("/dev/null", "w", stdout);
   freopen("/dev/null", "w", stderr);
}


bool CMiscUtils::BOOL(char *tf)
{
   return ( strcasecmp(tf, "TRUE") == 0 ? true : false );
}


const char *CMiscUtils::FileCheckSum(char *filename, char *csum)
{
   FILE *fp;
   char cmd[256];
   char buffer[256];
   int ret;
   struct stat st;

   if ( stat(filename, &st) < 0 ) {
      return (char *)NULL;
   }
 
   sprintf(cmd, "/usr/bin/sha1sum %s | awk '{print $1}'", filename);

   if ( (fp = popen(cmd, "r")) == NULL )
      return (char *)NULL;

   memset(buffer, 0, sizeof(buffer));

   ret = fread(buffer, 1, sizeof(buffer) - 1, fp );
   
   if ( buffer[ ret -1 ] == '\n' ) 
      buffer[ ret - 1] = 0;
   strcpy(csum, buffer);

   fclose(fp);
   
   return csum;
}

void CMiscUtils::XOR(const unsigned char *in, int inlen, unsigned char *out, int *outlen)
{
   int len;

   for ( len = 0; len < inlen; len++ ) {
      out[len] = in[len] ^ 0xEF;
   }

   *outlen = len;
}

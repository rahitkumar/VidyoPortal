#include "log.h"

CLog* CLog::pInstance = NULL;
//pthread_mutex_t CLog::log_mutex = PTHREAD_MUTEX_INITIALIZER;

//!\brief Default constructor
CLog::CLog()
{
   logErrorEnabled = 1; // error should be log all the time
   logWarnEnabled = 0;
   logDebugEnabled = 0;
   logInfoEnabled = 0;
}

//!\brief Default destructor
CLog::~CLog()
{

}

CLog* CLog::Initialize( const char *filename, const int facility )
{
   if ( !pInstance ) {
      pInstance = new CLog();
      if ( filename )
         openlog(filename, LOG_NOWAIT, facility);
//      pthread_mutex_init(&log_mutex, NULL);
   }   

   return pInstance;
}

CLog* CLog::GetInstance()
{
   return pInstance;
}

void CLog::LogMsg(const int l, const char *fmt, ...)
{
   va_list args;
   char buffer[2048];
   
   if ( l == LOG_ERROR_ENABLED && !logErrorEnabled )
      return;
 
   if ( l == LOG_DEBUG_ENABLED && !logDebugEnabled )
      return;

   if ( l == LOG_INFO_ENABLED && !logInfoEnabled )
      return;

   if ( l == LOG_WARN_ENABLED && !logWarnEnabled )
      return;



   va_start(args, fmt);
   vsnprintf(buffer, sizeof(buffer), fmt, args);
   va_end(args);
 
   fprintf(stdout, "[%s]\n", buffer);
   fflush(stdout);
//   pthread_mutex_lock(&log_mutex);
   syslog(LOG_NOTICE, "%s", buffer);
//   pthread_mutex_unlock(&log_mutex);
}

void CLog::LogMsg(const char *fmt, ...)
{
   va_list args;
   char buffer[2048];
   
   va_start(args, fmt);
   vsnprintf(buffer, sizeof(buffer), fmt, args);
   va_end(args);
 
//   pthread_mutex_lock(&log_mutex);
   syslog(LOG_NOTICE, "%s", buffer);
//   pthread_mutex_unlock(&log_mutex);
}


void CLog::EnableError()
{
   logErrorEnabled = 1;
}

void CLog::EnableDebug()
{
   logDebugEnabled = 1;
}
void CLog::EnableWarn()
{
   logWarnEnabled = 1;
}
void CLog::EnableInfo()
{
   logInfoEnabled = 1;
}

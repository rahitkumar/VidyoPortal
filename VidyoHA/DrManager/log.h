#ifndef _LOG_H_
#define _LOG_H_

#include <stdio.h>
#include <syslog.h>
#include <stdarg.h>
#include <pthread.h>

const int LOG_ERROR_ENABLED=0;
const int LOG_WARN_ENABLED=1;
const int LOG_DEBUG_ENABLED=2;
const int LOG_INFO_ENABLED=3;

#define DBG(fmt, ...) {\
char __buf__[2048];snprintf(__buf__,sizeof(__buf__)-1, fmt, ##__VA_ARGS__);\
fprintf(stdout, "%s(%d):%s: %s\n",__FILE__,__LINE__,__func__, __buf__); fflush(stdout); }

#define Logger(fmt, ...) {\
char __buf__[2048];snprintf(__buf__,sizeof(__buf__)-1, fmt, ##__VA_ARGS__);\
CLog::GetInstance()->LogMsg("%s(%d):%s: %s",__FILE__,__LINE__,__func__, __buf__); }

#define LogDebug(fmt, ...) {\
char __buf__[2048];snprintf(__buf__,sizeof(__buf__)-1, fmt, ##__VA_ARGS__);\
CLog::GetInstance()->LogMsg(LOG_DEBUG_ENABLED, "DEBUG:%s(%d):%s: %s",__FILE__,__LINE__,__func__, __buf__); }

#define LogInfo(fmt, ...) {\
char __buf__[2048];snprintf(__buf__,sizeof(__buf__)-1, fmt, ##__VA_ARGS__);\
CLog::GetInstance()->LogMsg(LOG_INFO_ENABLED, "INFO:%s(%d):%s: %s",__FILE__,__LINE__,__func__, __buf__); }

#define LogWarn(fmt, ...) {\
char __buf__[2048];snprintf(__buf__,sizeof(__buf__)-1, fmt, ##__VA_ARGS__);\
CLog::GetInstance()->LogMsg(LOG_WARN_ENABLED, "WARN:%s(%d):%s: %s",__FILE__,__LINE__,__func__, __buf__); }

#define LogError(fmt, ...) {\
char __buf__[2048];snprintf(__buf__,sizeof(__buf__)-1, fmt, ##__VA_ARGS__);\
CLog::GetInstance()->LogMsg(LOG_ERROR_ENABLED, "ERROR:%s(%d):%s: %s",__FILE__,__LINE__,__func__, __buf__); }

class CLog
{
public:
   static CLog *Initialize(const char *filename = NULL, const int facility = LOG_LOCAL0);
   static CLog *GetInstance();
   void LogMsg(const int l, const char *fmt, ...);
   void LogMsg(const char *fmt, ...);
   void EnableWarn();
   void EnableError();
   void EnableDebug();
   void EnableInfo();
private:
   CLog();
   ~CLog();
//   static pthread_mutex_t log_mutex;
   static CLog *pInstance;
   int logErrorEnabled;
   int logWarnEnabled;
   int logDebugEnabled;
   int logInfoEnabled;
};

#endif

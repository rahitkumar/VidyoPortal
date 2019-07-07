#ifndef _MISC_UTILS_
#define _MISC_UTILS_

#include<string>
#include<stdio.h>

using namespace std;

enum LOG_LEVEL { WARNING=1, INFO, DEBUG };
class CMiscUtils
{

public:
   static CMiscUtils *Initialize();
   static CMiscUtils *GetInstance();
   void OpenLog(const char *ident);
   void LogMsg(int level, const char *fmt, ...);
   void LogMsg(const char *fmt, ...);
   void LogErr(const char *fmt, ...);

   void RunLevel(int rl );

   void SetError(const int err, const char *desc);
   char * GetError();
   void Daemonize();
   bool BOOL(char *tf);
   const char *FileCheckSum(char *filename, char *csum);
   void XOR(const unsigned char *in, int inlen, unsigned char *out, int *outlen);


private:
   CMiscUtils();
   ~CMiscUtils();
   char *errstr;
   int m_runlevel;
   static CMiscUtils *pInstance;

};


#endif

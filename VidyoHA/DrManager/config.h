#ifndef _CONFIG_H_
#define _CONFIG_H_

using namespace std;

#include <stdio.h>
#include <string>
#include <map>

class CConfig
{
public:
   static CConfig *Initialize();
   static CConfig *GetInstance();
   bool Read(const char *fn);
   char *ReadValue(const char *fn, const char *name);
   bool Comment(const char *str);
   const char *NamVal(const char *name);
   void Dump();
private:
   static CConfig *pInstance;
   FILE *fp;
   map<string, string> nv;
};

#endif


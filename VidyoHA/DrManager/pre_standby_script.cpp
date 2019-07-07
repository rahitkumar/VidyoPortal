#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>
#include "drmgr.h"
#include "param.h"
#include "log.h"
#include "drmgr.h"
#include "mydefs.h"


// Note: This function will block until the PRE_STANDBY_SCRIPT returns
bool start_prestandby_script()
{
   CParam *cP=CParam::GetInstance();
   char command[128];
   char errorstr[128];
   int ret;

   snprintf(command, SIZE(command), "%s/%s > /dev/null 2>&1 &", cP->BinDir(), PRE_STANDBY_SCRIPT);
   ret = system(command);

   if ( ret < 0 ) {
      memset(errorstr, 0, sizeof(errorstr));
      strerror_r(errno, errorstr, SIZE(errorstr)); 
      LogError("unable to run %s:(%s): ret:%d", PRE_STANDBY_SCRIPT, strerror(errno), ret);
      LogError("unable to run %s:(%s): ret:%d", PRE_STANDBY_SCRIPT, errorstr, ret);
      return false;
   }else
      return true;
}

// Note: This function will block until the SSH_KEY_GEN returns
bool start_ssh_keygen()
{
   CParam *cP=CParam::GetInstance();
   char command[128];
   char errorstr[128];
   int ret;

   snprintf(command, SIZE(command), "%s/%s > /dev/null 2>&1", cP->BinDir(), SSH_KEY_GEN);
   ret = system(command);

   if ( ret < 0 ) {
      memset(errorstr, 0, sizeof(errorstr));
      strerror_r(errno, errorstr, SIZE(errorstr)); 
      LogError("unable to run %s:(%s)", command, errorstr);
      return false;
   }else
      return true;
}

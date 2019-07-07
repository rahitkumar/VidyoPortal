#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <errno.h>
#include "drmgr.h"
#include "param.h"
#include "log.h"
#include "drmgr.h"
#include "mydefs.h"


// Note: This function will block until the PRE_ACTIVE_SCRIPT returns
bool start_preactive_script()
{
   CParam *cP=CParam::GetInstance();
   char command[128];
   char errorstr[128];
   int ret;

   snprintf(command, SIZE(command), "%s/%s > /dev/null 2>&1", cP->BinDir(), PRE_ACTIVE_SCRIPT);
   LogInfo("About to run [%s]", command);
   ret = system(command);
   //LogInfo("Done: ret = %d", ret );
   DBG("Done: ret = %d, %d(%s)", ret, errno, strerror(errno) );

   if ( ret < 0 ) {
      memset(errorstr, 0, sizeof(errorstr));
      strerror_r(errno, errorstr, SIZE(errorstr)); 
      LogError("unable to run %s:%d(%s)", command, errno, errorstr);
      return false;
   }else
      return true;

}

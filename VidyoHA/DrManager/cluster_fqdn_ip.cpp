#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "log.h"
#include "param.h"
#include "cluster_fqdn_ip.h"
#include "utils.h"
#include "mydefs.h"
#include "drmgr.h"


char *get_cluster_fqdns_ip(char *cluster_ip)
{
   CParam *cP=CParam::GetInstance();
   FILE *pp;
   char command[128];
   char IP[64];
   int nread;
   int status;

   snprintf(command, SIZE(command), "%s/%s", cP->BinDir(), REVERSE_DNS);

   if ( ( pp =  popen(command, "r") ) == NULL ) {
      LogError("Failed to run reverse_dns script!");
      return NULL;
   }

   nread=fread(IP, SIZE(IP), 1, pp);
   pclose(pp);
   remove_cr( IP );
   
#if 0
   fprintf(stdout, "before WAIT ----------------------\n");
   fflush(stdout);
   wait(&status);
   if ( WIFEXITED(status) )
      fprintf(stdout, "child terminated normally...[%d]\n", WEXITSTATUS(status) );
   else
      fprintf(stdout, "child terminated with error!!!\n");

   fflush(stdout);
#endif
   return strncpy(cluster_ip, IP, MAX_SIZE_IP);
}

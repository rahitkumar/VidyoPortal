#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include "utils.h"
#include "mydefs.h"

//! \brief This function will run the application as a daemon process.
void Daemonize()
{
   int pid, sid;

   if ( getppid() == 1 )
      return;

   pid = fork();
   if ( pid < 0 ) {
      perror("fork()");
      exit(1);
   }else if (pid > 0 )
      exit(0);

   umask(0);

   if ((sid=setsid()) < 0 ){
      perror("setsid()");
      exit(1);
   };

   if ( chdir("/") < 0 ){
      perror("chdir");
      exit(1);
   }

   if ( freopen("/dev/null", "r", stdin) == NULL ) 
      Failed("freopen: stdin");

   if ( freopen("/dev/null", "w", stdout) == NULL ) 
      Failed("freopen: stdout");

   if ( freopen("/dev/null", "w", stderr) == NULL ) 
      Failed("freopen: stderr");
}

void Failed(const char *errmsg)
{
   fprintf(stderr, "%s\n", errmsg);
   exit(1);
}

int file_exists(const char *fn)
{
   struct stat st;
   return (stat(fn, &st) == 0 ? 1 : 0 );
}


bool lockfile(const char *fn, const int tmo)
{
   bool ret=false;
   int ctr = tmo*2;
   int fd;

   while(ctr-- > 0) {
      if ( ( fd = open(fn, O_WRONLY | O_CREAT | O_EXCL, S_IRUSR|S_IWUSR)) >= 0 ) {
         ret = true;
         break;
      }
      usleep(500000);
   }

   close(fd);
   return ret;
}

bool unlockfile(const char *fn)
{
   if ( unlink(fn) == 0 )
      return true;
   else
      return false;
}

char * remove_cr(char *str)
{
   char *ptr=str;

   while( *ptr ) {
      if ( *ptr == '\n' )
         *ptr = 0;
      ptr++;
   }
   return str;
}


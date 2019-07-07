#!/bin/bash 
###########################################################################
# Description: Check the mandatory portal processes. Log the process that 
#              is not running. The exit code will be non-zero if any of the
#              process below is not running.
#
# Date Created: 04-26-2016
#
# Modification History
###########################################################################

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV

. $LOG_FUNCS

is_apache_running()
{
   PID_FILE=/var/run/apache2.pid
   if [ -f $PID_FILE ]; then
      PID=$(cat /var/run/apache2.pid)
      if [ -f /proc/${PID}/status ]; then
         N=$(cat /proc/${PID}/status | grep "Name:")
         if ! [ "$N" = "apache2" ]; then
            return 0
         fi
      fi
   fi
      
   return 1
}

is_tomcat_running()
{
   local TC_PROC=0
   local TCNP_PROC=0
   local RC=0
   local TC

   TC=$(ps aux | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{print $1}')
   for P in $TC; do
      if [ "$P" = tomcat ]; then
         TC_PROC=1
      fi

      if [ "$P" = tomcatnp ]; then
         TCNP_PROC=1
      fi
   done

   if [ $TC_PROC -ne 1 ]; then
      vlog3  "Tomcat(privilege) is not running"
      RC=1
   fi

   if [ $TCNP_PROC -ne 1 ]; then
      vlog3 "Tomcat(non-privilege) is not running"
      RC=1
   fi

   return $RC
}


is_vm_running()
{
   PIDS=$(pidof VidyoManager)
   let CTR=0
   for p in $PIDS; do
      let CTR++
   done

   [ $CTR -eq 0 ] && return 1

   [ $CTR -eq 1 ] && vlog3 "Warning!!! Number of VM Process is: $CTR"
   
   return 0
}

is_mysql_running()
{
   /usr/local/mysql/bin/mysqladmin $MYSQL_OPTS status > /dev/null 2>&1

   return $?
}

is_drmgr_running()
{
   local DRMGR_PIDS=$(pidof drmgr)
   [ -n "$DRMGR_PIDS" ] && return 0

   return 1
}

check_processes()
{
   if ! is_apache_running; then 
      vlog3 "**** Warning **** Apache is not running..."
      return 1
   fi

   if ! is_tomcat_running; then 
      vlog3 "**** Warning **** tomcat is not running..."
      return 2
   fi

   if ! is_mysql_running; then 
      vlog3 "**** Warning **** MySQL is not running..."
      return 3
   fi

   STATE=
   eval $(DR_ADMIN -cS)
   if [ "$STATE" = ACTIVE ]; then
      if ! is_vm_running; then 
         vlog3 "**** Warning **** VidyoManager is not running..."
         return 4
      fi
   fi

   if ! is_drmgr_running; then 
      vlog3 "**** Warning **** drmgr is not running..."
      return 5
   fi

   return 0
}

if check_processes; then
   exit 0
else
   exit 1
fi

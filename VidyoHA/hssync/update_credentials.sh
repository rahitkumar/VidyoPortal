#!/bin/bash

LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh

[ -f $LOG_FUNCS ] || exit 1

. $LOG_FUNCS

stop_apache_tomcat()
{
   local PIDS=

   vlog3 "Stopping tomcat..."
   /etc/init.d/tomcat stop > /root/ha/tomcat.log 2>&1
   sleep 1
   CTR=5
   while [ $CTR -gt 0 ]; do
      PIDS=$(ps -ef | grep java | grep catalina.startup.Bootstrap | awk '{ print $2 }')
      [ -z "$PIDS" ] && break
      vlog3 "[$CTR] WARNING! tomcat still running..."
      sleep 1
      kill $PIDS >/dev/null 2>&1
      ((CTR--))
      if [ $CTR -eq 0 ]; then
         vlog3 "[$CTR] WARNING! tomcat still running...sending SIGKILL"
         kill -9 $PIDS > /dev/null 2>&1
         sleep 1
      fi
   done
   ## do another check
   PIDS=$(ps -ef | grep java | grep catalina.startup.Bootstrap | awk '{ print $2 }')
   if [ -z "$PIDS" ]; then
      vlog3 "Tomcat successfully stopped."
   else
      vlog3 "ERROR!!! Unable to stop Tomcat."
   fi

   vlog3 "Stopping apache2..."
   /etc/init.d/apache2 stop > /root/ha/apache2.log 2>&1
}

start_apache_tomcat()
{
   vlog3 "Starting tomcat..."
   /etc/init.d/tomcat restart > /root/ha/tomcat.log 2>&1 &

   vlog3 "rebuilding apache config..."
   /opt/vidyo/bin/apache_init.sh
}

vlog3 "DB credentials has been changed..."
vlog3 "Restarting tomcat..."
/opt/vidyo/bin/set_db_password.sh update
stop_apache_tomcat
start_apache_tomcat

vlog3 "restarting vp_snmp_agent..."
/opt/vidyo/app/vp_snmp_agent/bin/vp_snmp_agent.sh stop > /dev/null 2>&1
/opt/vidyo/app/vp_snmp_agent/bin/vp_snmp_agent.sh start > /dev/null 2>&1
 ## restart portal batch too
PORTAL_BATCH_PID=$(ps -ef | grep process.name=PORTAL_BATCH_JOBS | grep -v grep | awk '{ print $2}')
if [ -n "$PORTAL_BATCH_PID" ]; then
    vlog3 "restarting vidyo-portal-batch. PID [$PORTAL_BATCH_PID]..."
    kill -9 $PORTAL_BATCH_PID > /dev/null 2>&1
fi



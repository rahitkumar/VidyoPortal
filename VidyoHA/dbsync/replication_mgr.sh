#!/bin/bash

PIDFILE=/var/run/replication_mgr.pid
DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf
[ ! -f $DR_ENV ] && exit

. $DR_ENV



if [ "$1" = "start" ]; then
   if [ -f $PIDFILE ]; then
      echo "Previous replication mgr maybe running..."
      RMPID=$(cat $PIDFILE)
      [ -n "$RMPID" ] && kill -9 $RMPID > /dev/null 2>&1
      rm -f $PIDFILE
      sleep 3
   fi
   /sbin/start-stop-daemon --background --oknodo --start --pidfile=$PIDFILE --make-pidfile --exec=$REPL_MGR -- "monitor" #> /dev/null 2>&1
   logger "after daemonizing..."
   exit 0
elif [ "$1" = "stop" ]; then
   if [ -f $PIDFILE ]; then
      RMPID=$(cat $PIDFILE)
      [ -n "$RMPID" ] && kill -9 $RMPID
      rm -f $PIDFILE
   fi
   exit
elif [ "$1" = "monitor" ]; then
   logger "monitor..."
   DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf
   [ ! -f $DR_ENV ] && exit

   . $DR_ENV

   [ -f $LOG_FUNCS ] && . $LOG_FUNCS

   vlog3 "starting to monitor replcation_controller..."
else
   exit 1
fi

## TODO: exit immediately if DR is not enabled.
. $DR_FUNC

PROGNAME=$(basename $DRMGR)

while true; do
   sleep 2
   if ! pidof $PROGNAME; then
      vlog3 "$PROG is not running... aborting"
      exit 0
   fi
   if ! state_STANDBY; then
      #vlog3 "This node is not in STANDBY state"
      continue
   fi

   vlog3 "Executing [$REPL_CTRL]"
   $REPL_CTRL
done

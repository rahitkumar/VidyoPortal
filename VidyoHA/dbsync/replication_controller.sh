#!/bin/bash
#############################################################################
# Filename: replication_controller.sh
# Description: This script will be used my replication_mgr.sh.
# NOTE: Run this on STANDBY node only...
#
# Modification History:
# 10-26-2015  Initial coding (Eric)
#############################################################################

MONITOR_INTERVAL=3
BEHIND_MASTER=30
DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV



[ ! -f $DB_REPL ] && exit 1

. $DB_REPL

. $ERROR_CHECK
. $DR_FUNC

#exec >/tmp/error.log 2>&1

trap exit_handler EXIT SIGINT SIGTERM

exit_handler()
{
   vlog3 "exit_handler(): Stopping slave replication"
   $STOP_SLAVE
   vlog3 "exit_handler(): Stopping ssh tunnel"
   $SSH_TUNNEL stop > /dev/null 2>&1
   exit 0
}

tunnel_ok()
{
   [ -f $TUNNEL_OK ] && return 0
   return 1
}

echo DB_REPL=[$DB_REPL]

[ -f $LOG_FUNCS ] && . $LOG_FUNCS

mkdir -p $DB_DATA_REPL $DB_REPL_STATUS_DIR

BEHIND_MASTER_CTR=0
SLAVE_ERR_CTR=0

rm -f $TUNNEL_OK

SLAVE_NOT_RUNNING_CTR=0
while true; do
   if [ ! -f $REPL_MGR_PIDFILE ]; then
      vlog3 "Replication manager is not running... aborting..."
      break
   fi

   if ! state_STANDBY; then
      vlog3 "This node is not in STANDBY state"
      break
   fi

   vlog3 "DEBUG STANDBY..."

   if ! tunnel_ok; then
      vlog3 "Starting SSH tunnel for replication..."
      $SSH_TUNNEL start > /dev/null 2>&1
      if [ $? -eq 0 ]; then
         $SET_STATE TUNNEL_UP
      else
         vlog3 "($$)failed to start the SSH-tunnel. Replication will not start!"
         $SET_STATE TUNNEL_DOWN
         $DR_ADMIN --event=8
         sleep 10
         continue
      fi
   fi
   
   Slave_IO_Running=
   Slave_SQL_Running=
   Last_IO_Errno=
   Last_SQL_Errno=
   Seconds_Behind_Master=

   eval $(mysql $MYSQL_OPTS -E -e "SHOW SLAVE STATUS" | \
      grep -E "\<Slave_IO_Running\>|\<Slave_SQL_Running\>|\<Last_IO_Errno\>|\<Last_SQL_Errno\>|\<Seconds_Behind_Master\>" | \
      sed 's/: /=/g' )

   if [ "$Slave_IO_Running" = "Yes" -a "$Slave_SQL_Running" = "Yes" ]; then
      if [ $Seconds_Behind_Master -gt $BEHIND_MASTER ]; then
         if [ $BEHIND_MASTER_CTR -gt 5 ]; then
            vlog3 "Warning: Seconds_Behind_Master: $Seconds_Behind_Master. RESETTING SLAVE"
            BEHIND_MASTER_CTR=0
            $DR_ADMIN --event=9
            $SET_STATE BEHIND_MASTER
            $START_SLAVE FORCE_DOWNLOAD
         fi
         vlog3 "Warning: Seconds_Behind_Master: $Seconds_Behind_Master"
         ((BEHIND_MASTER_CTR++))
         sleep $MONITOR_INTERVAL
         continue
      fi
      if [ $Last_IO_Errno -eq 0 -a $Last_IO_Errno -eq 0 ]; then
         $SET_STATE SLAVE_OK
         $DR_ADMIN --event=7
         touch $DB_REPL_LAST_OK
      else
         if [ $SLAVE_ERR_CTR -gt 5 ]; then
            vlog3 "Warning: SLAVE_ERR_CRT: $SLAVE_ERR_CTR, Restarting replication..."
            SLAVE_ERR_CTR=0
            $START_SLAVE
         else
            if [ $Last_IO_Errno -ne 0 ]; then 
               $SET_STATE NET_ERR
               $DR_ADMIN --event=8
            else
               $SET_STATE SLAVE_ERR
               $DR_ADMIN --event=8
            fi
            ((SLAVE_ERR_CTR++))
            vlog3 "Warning: Last_IO_Errno: $Last_IO_Errno, Last_IO_Errno: $Last_IO_Errno, ERR_CTR: $SLAVE_ERR_CTR"
         fi
      fi
   else
      ((SLAVE_NOT_RUNNING_CTR++))
      vlog3 "Error! SLAVE_NOT_RUNNING_CTR: $SLAVE_NOT_RUNNING_CTR"
      if [ $SLAVE_NOT_RUNNING_CTR -gt 5 ]; then
         SLAVE_NOT_RUNNING_CTR=0
         $DR_ADMIN --event=9
         $START_SLAVE FORCE_DOWNLOAD
      fi
      if ! net_err; then
         $DR_ADMIN --event=9
         $SET_STATE SLAVE_NOT_RUNNING
      fi
      if ! $START_SLAVE; then
         if net_err; then
            vlog3 "Error! Failed to setup tunnel..."
            sleep 3
         else
            vlog3 "Error! Failed to start slave"
         fi
         $DR_ADMIN --event=8
      fi
   fi

   SLEEP_CTR=0
   while [ $SLEEP_CTR -lt $MONITOR_INTERVAL ]; do 
      if ! state_STANDBY; then
         break
      fi 
      ((SLEEP_CTR++))
      sleep 1
   done
done

$STOP_SLAVE

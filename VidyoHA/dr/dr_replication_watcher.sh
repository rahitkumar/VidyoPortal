#!/bin/bash
#############################################################################
# Filename: dr_replication_watcher.sh
# Description: Monitor the file system and database replication.
# NOTE: Run this on STANDBY node only... (this is a modified version of the
#       replication_watcher.sh used in hot-standby.
#
# Modification History:
# 02-08-2017 Initial coding (Eric)
#############################################################################

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin
MONITOR_INTERVAL=10
BEHIND_MASTER=30
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"
TUNNEL_OK=/root/ha/tunnel_ok
SLAVE_STATUS=/opt/vidyo/ha/hssync/slave_status.sh
STOP_SLAVE=/opt/vidyo/ha/dr/dr_stop_slave.sh
SSH_TUNNEL=/opt/vidyo/ha/dr/dr_tunnel.sh
RESTART_SLAVE_REPLICATION=/opt/vidyo/ha/dr/dr_restart_slave_replication.sh
RESUME_SLAVE_REPLICATION=/opt/vidyo/ha/hssync/resume_slave_replication.sh
RSYNC_FILES=/opt/vidyo/ha/dr/dr_rsync.sh
RSYNC_INTERVAL=60 ## every 1 min
LAST_TIME_RSYNC=0
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
DR_CONF=/opt/vidyo/conf/dr/dr.conf
SCRIPT=/opt/vidyo/ha/dr/dr_replication_watcher.sh
PIDFILE=/var/run/replication_watcher.pid
CREATE_MASTER_SNAPSHOT=/opt/vidyo/ha/dr/dr_create_master_snapshot.sh
DBA=/opt/vidyo/etc/db/access.conf
IS_NODE_ACTIVE=/opt/vidyo/ha/dr/isnodeactive.sh
GET_STATE=/opt/vidyo/ha/dr/get_state.sh
SET_STATE=/opt/vidyo/ha/dr/set_state.sh
REGISTER_TO_NEW_ACTIVE_SITE=/opt/vidyo/ha/dr/register_to_new_active_site.sh
MYPPID=

[ -f $LOG_FUNCS ] || exit 1
[ -f $DB_REPL ] || exit 2
[ -f $HA_CONF ] || exit 3

update_firewall()
{
   vlog3 "updating firewall..."
   iptables -F
   /opt/vidyo/bin/firewallSettings.sh start
   /opt/vidyo/bin/vmsoap_fw.sh
}

tunnel_ok()
{
   [ -f $TUNNEL_OK ] && return 0
   return 1
}

exit_handler()
{
   vlog3 "exit_handler(): Stopping slave replication"
   $STOP_SLAVE
   exit 0
}

update_replication_status()
{
   echo "\
$DB_SNAPSHOT_OLD:\
$REPLICATION_RUNNING:\
$REPLICATION_ERROR:\
$SECONDS_BEHIND_MASTER:\
$LAST_TIME_DB_INSYNC:\
$LAST_TIME_RSYNC_OK\
" > $REPLICATION_STATUS
}

case "$1" in 
   start)
      /sbin/start-stop-daemon --background --oknodo --start --pidfile=$PIDFILE --make-pidfile --exec=$SCRIPT -- "monitor" $2 #> /dev/null 2>&1
      exit 0
      ;;
   stop)
      if [ -f $PIDFILE ]; then
         RMPID=$(cat $PIDFILE)
         [ -n "$RMPID" ] && kill -9 $RMPID
         rm -f $PIDFILE
      fi

      exit 
      ;;
   monitor)
      ;;
   *)
      exit
      ;;
esac

trap exit_handler EXIT SIGINT SIGTERM

. $LOG_FUNCS
. $DB_REPL

MYPPID=$2

BEHIND_MASTER_CTR=0
SLAVE_ERR_CTR=0
SLAVE_NOT_RUNNING_CTR=0
DB_SNAPSHOT_OLD=N
DRMGR_RUNNING=Y
REPLICATION_RUNNING=N
vlog3 "Started... drmgr pid: $MYPPID, Resetting tunnel..."
#$SSH_TUNNEL stop > /dev/null 2>&1
$STOP_SLAVE
rm -f $TUNNEL_OK

. $DR_CONF
. $HA_CONF

## get our DB version
LOCAL_DBVERSION=
if [ -f $DB_VERSION_INFO ]; then
   . $DB_VERSION_INFO
   LOCAL_DBVERSION=$DB_VERSION
fi

vlog3 "Local Database Version: $LOCAL_DBVERSION"

LAST_TIME_DB_INSYNC=0
LAST_TIME_RSYNC_OK=0

while true; do
   SLEEP_CTR=0
   update_replication_status
   while [ $SLEEP_CTR -lt $MONITOR_INTERVAL -a ! -f $MAINT_FLAG ]; do 
      if [ -f $DRMGR_PID ]; then
         DRPID=$(cat $DRMGR_PID) 
         if [ ! -f /proc/$DRPID/status ]; then
            vlog3 "drmgr is not running." 
            ##TODO:what if the status file exists but belong to other process ???
            DRMGR_RUNNING=N
            break
         fi
         if [ "$DRPID" != "$MYPPID" ]; then
            vlog3 "Warning!!! drmgr has been restarted. MyPPD[$MYPPID], DRPID[$DRPID]"
            DRMGR_RUNNING=N
            break
         fi
      else
         vlog3 "drmgr pid not found"
         DRMGR_RUNNING=N
         break
      fi
      sleep 1
      ((SLEEP_CTR++))
      if $IS_NODE_ACTIVE; then
         :
         sleep 3
         ## do something here
      else
         ## check if we detected a new ACTIVE site
         if [ -f $NEW_ACTIVE_NODE ]; then
            rm -f $NEW_ACTIVE_NODE
            $REGISTER_TO_NEW_ACTIVE_SITE
         fi
      fi
      
   done

   if [ $DRMGR_RUNNING != Y ]; then
     break
   fi

   . $HA_CONF

   if [ -f $MAINT_FLAG ]; then
      vlog3 "Exit - Maintenance Mode Detected"
      break
   fi

   if [ "$VIDYO_HA" != "ENABLED" ]; then
      vlog3 "Exit - HA Not Enabled"
      break
   fi

   if [ "$HA_MODE" != "DR" ]; then
      vlog3 "Exit - HA Mode is not set to DR"
      break
   fi

   if $IS_NODE_ACTIVE; then
      continue
   fi

   if [ ! -f $ACTIVE_NODE_CONF ]; then
      STATE=$($GET_STATE) 
      if [ "$STATE" = PRE_ACTIVE ]; then
         vlog3 "This site is in PRE_ACTIVE state..."
      else
         vlog3 "No ACTIVE site config found..."
      fi
      sleep 3
      continue
   fi

   ## make sure that the nodes have the same db version
   DBVERSION=
   [ -f $ACTIVE_NODE_CONF ] && . $ACTIVE_NODE_CONF
   ACTIVE_DBVERSION=$DBVERSION
   

   if [ -z "$LOCAL_DBVERSION" -o -z "$ACTIVE_DBVERSION" ]; then
      vlog3 "Unknown Node Version[$LOCAL_DBVERSION][$ACTIVE_DBVERSION]!"
      $STOP_SLAVE
      REPLICATION_RUNNING=N
      REPLICATION_ERROR="DB_VERSION_UNKNOWN"
      touch $DB_VERSION_MISMATCH
      sleep 5
      continue
   fi

   if [ "$ACTIVE_DBVERSION" != "$LOCAL_DBVERSION" ]; then
      vlog3 "Warning!!! Node Version not identical[$LOCAL_DBVERSION][$ACTIVE_DBVERSION]!"
      $STOP_SLAVE
      REPLICATION_RUNNING=N
      REPLICATION_ERROR="DB_VERSION_MISMATCH"
      touch $DB_VERSION_MISMATCH
      sleep 5
      continue
   fi

   rm -f $DB_VERSION_MISMATCH
 
   if ! tunnel_ok; then
      vlog3 "Starting DR SSH tunnel for replication..."
      $SSH_TUNNEL start > /dev/null 2>&1
      if [ $? -eq 0 ]; then
         touch $TUNNEL_OK
         if [ $DB_SNAPSHOT_OLD = Y -o  ! -f $DB_SNAPSHOT_COMPRESS ]; then  ## do a clean start of replication
            vlog3 "No DB snapshot found. Forcing a restart of DB Replication after 15 secs."
            sleep 15
            $RESTART_SLAVE_REPLICATION
            RC=$?
            if [ $RC -eq 10 ]; then
               vlog3 "DB Snapshot too old"
               DB_SNAPSHOT_OLD=Y
               sleep $MONITOR_INTERVAL
               continue
            elif [ $RC -eq 5 ]; then ## No DB snapshot found
               DB_SNAPSHOT_OLD=Y  ## treat this as DB_SNAPSHOT_OLD
               vlog3 "DB Snapshot not found!!!"
               sleep 15
               continue
            else
               DB_SNAPSHOT_OLD=N
            fi
         else
            $RESUME_SLAVE_REPLICATION
         fi
      else
         vlog3 "($$)failed to start the SSH-tunnel. Replication will not start!"
         sleep 5
         continue
      fi
   fi

   if [ $DB_SNAPSHOT_OLD = Y ]; then
      $RESTART_SLAVE_REPLICATION
      RC=$?
      if [ $RC -eq 10 ]; then
         vlog3 "Database Snapshot is too old. Request a new DB snapshot from ACTIVE site"
         DB_SNAPSHOT_OLD=Y
         sleep $MONITOR_INTERVAL
         continue
      elif [ $RC -eq 5 ]; then ## No DB snapshot found
         DB_SNAPSHOT_OLD=Y  ## treat this as DB_SNAPSHOT_OLD
         vlog3 "DB Snapshot not found!!!"
         sleep 15
         continue
      else
         DB_SNAPSHOT_OLD=N
      fi
   fi
   
   Slave_IO_Running=
   Slave_SQL_Running=
   Last_IO_Errno=
   Last_SQL_Errno=
   Seconds_Behind_Master=

   eval $($SLAVE_STATUS)

   if [ "$Slave_IO_Running" = "Yes" -a "$Slave_SQL_Running" = "Yes" ]; then
      REPLICATION_RUNNING=Y
      REPLICATION_ERROR=
      SECONDS_BEHIND_MASTER=$Seconds_Behind_Master
      [ "$SECONDS_BEHIND_MASTER" = 0 ] && LAST_TIME_DB_INSYNC=$(date +%s)

      if [ $Seconds_Behind_Master -gt $BEHIND_MASTER ]; then
         if [ $BEHIND_MASTER_CTR -gt 5 ]; then
            vlog3 "Warning: Seconds_Behind_Master: $Seconds_Behind_Master. RESETTING SLAVE"
            BEHIND_MASTER_CTR=0
            $RESTART_SLAVE_REPLICATION
            RC=$?
            if [ $RC -eq 10 ]; then
               vlog3 "DB Snapshot too old"
               DB_SNAPSHOT_OLD=Y
               sleep $MONITOR_INTERVAL
               continue
            elif [ $RC -eq 5 ]; then ## No DB snapshot found
               DB_SNAPSHOT_OLD=Y  ## treat this as DB_SNAPSHOT_OLD
               vlog3 "DB Snapshot not found!!!"
               sleep 15
               continue
            else
               DB_SNAPSHOT_OLD=N
            fi
         fi
         vlog3 "Warning: Seconds_Behind_Master: $Seconds_Behind_Master"
         ((BEHIND_MASTER_CTR++))
         sleep $MONITOR_INTERVAL
         continue
      fi
      if [ $Last_IO_Errno -eq 0 -a $Last_IO_Errno -eq 0 ]; then
         SLAVE_NOT_RUNNING_CTR=0
         SLAVE_ERR_CTR=0
         vlog3 "Replication Status: $Seconds_Behind_Master second(s) behind master, IORunning: $Slave_IO_Running, SQLRunning: $Slave_SQL_Running" 
      else
         if [ $SLAVE_ERR_CTR -gt 5 ]; then
            vlog3 "Warning: SLAVE_ERR_CTR: $SLAVE_ERR_CTR, Restarting replication..."
            SLAVE_ERR_CTR=0
            $RESTART_SLAVE_REPLICATION
            if [ $? -eq 10 ]; then
               vlog3 "DB Snapshot too old"
               DB_SNAPSHOT_OLD=Y
               sleep $MONITOR_INTERVAL
               continue
            elif [ $RC -eq 5 ]; then ## No DB snapshot found
               DB_SNAPSHOT_OLD=Y  ## treat this as DB_SNAPSHOT_OLD
               vlog3 "DB Snapshot not found!!!"
               sleep 15
               continue
            else
               DB_SNAPSHOT_OLD=N
            fi
         else
            if [ $Last_IO_Errno -ne 0 ]; then 
               vlog3 "Warning! IO Error: $Last_IO_Errno, $Last_IO_Error"
            fi
            if [ $Last_SQL_Errno -ne 0 ]; then 
               vlog3 "Warning! SQL Error: $Last_SQL_Error, $Last_SQL_Error"
            fi
            ((SLAVE_ERR_CTR++))
         fi
      fi
   else
      ((SLAVE_NOT_RUNNING_CTR++))
      REPLICATION_RUNNING=N
      REPLICATION_ERROR="${Last_IO_Errno}_${Last_SQL_Errno}"
      vlog3 "Error! SLAVE_NOT_RUNNING_CTR: $SLAVE_NOT_RUNNING_CTR"
      if [ $Last_IO_Errno -ne 0 ]; then 
         vlog3 "Warning! IO Error: $Last_IO_Errno, $Last_IO_Error"
      fi
      if [ $Last_SQL_Errno -ne 0 ]; then 
         vlog3 "Warning! SQL Error: $Last_SQL_Errno, $Last_SQL_Error"
      fi
      if [ $SLAVE_NOT_RUNNING_CTR -gt 2 -a $SLAVE_NOT_RUNNING_CTR -lt 4 ]; then
         vlog3 "Restarting tunnel..."
         $SSH_TUNNEL stop > /dev/null 2>&1
         rm -f $TUNNEL_OK
         continue
      elif [ $SLAVE_NOT_RUNNING_CTR -gt 4 -a $SLAVE_NOT_RUNNING_CTR -lt 8 ]; then
         SLAVE_NOT_RUNNING_CTR=0
         vlog3 "Slave not running after max tunnel restart..."
         $RESTART_SLAVE_REPLICATION
         if [ $? -eq 10 ]; then
            vlog3 "DB Snapshot too old"
            DB_SNAPSHOT_OLD=Y
            sleep $MONITOR_INTERVAL
            continue
         elif [ $RC -eq 5 ]; then ## No DB snapshot found
            DB_SNAPSHOT_OLD=Y  ## treat this as DB_SNAPSHOT_OLD
            vlog3 "DB Snapshot not found!!!"
            sleep 15
            continue
         else
            DB_SNAPSHOT_OLD=N
         fi
         sleep 4
      else
         $RESUME_SLAVE_REPLICATION
      fi
      continue;
   fi

   NOW=$(date +%s)
   
   ((LAST_RSYNC=NOW-LAST_TIME_RSYNC))
   
   if [ $LAST_RSYNC -ge $RSYNC_INTERVAL ]; then
      SHA_DBA=$(sha1sum $DBA | awk '{print $1}')

      FW_CONF_CSUM=$(sha1sum /opt/vidyo/conf.d/firewall.conf | awk '{print $1}')

      START_RSYNC_TIME=$(date +%s)
      $RSYNC_FILES
      if [ $? -eq 0 ]; then
         LAST_TIME_RSYNC_OK=$(date +%s)
         touch $LAST_TIME_RSYNC_FILE
      fi
      LAST_TIME_RSYNC=$(date +%s)
      NEW_SHA_DBA=$(sha1sum $DBA | awk '{print $1}')

      ## check if DB password was changed, then restart tomcat
      if [ "$SHA_DBA" != "$NEW_SHA_DBA" ]; then
         /opt/vidyo/ha/hssync/update_credentials.sh
      fi

      ((RSYNC_DURATION=LAST_TIME_RSYNC-START_RSYNC_TIME))
      vlog3 "RRSYNC Duration: $RSYNC_DURATION"

      NEW_FW_CONF_CSUM=$(sha1sum /opt/vidyo/conf.d/firewall.conf | awk '{print $1}')

      ### restart firewall if chnages are detected
      if [ "$NEW_FW_CONF_CSUM" != "$FW_CONF_CSUM" ]; then
         vlog3 "Change detected on firewall.."
         update_firewall
      fi
    
   fi
done

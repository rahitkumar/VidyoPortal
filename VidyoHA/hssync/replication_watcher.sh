#!/bin/bash
#############################################################################
# Filename: replication_watcher.sh
# Description: Monitor the file system and database replication.
# NOTE: Run this on STANDBY node only...
#
# Modification History:
# 05-19-2016 Initial coding (Eric)
#############################################################################

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin
MONITOR_INTERVAL=10
BEHIND_MASTER=30
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"
TUNNEL_OK=/root/ha/tunnel_ok
SLAVE_STATUS=/opt/vidyo/ha/hssync/slave_status.sh
STOP_SLAVE=/opt/vidyo/ha/hssync/stop_slave.sh
SSH_TUNNEL=/opt/vidyo/ha/hssync/ssh_tunnel.sh
RESTART_SLAVE_REPLICATION=/opt/vidyo/ha/hssync/restart_slave_replication.sh
RESUME_SLAVE_REPLICATION=/opt/vidyo/ha/hssync/resume_slave_replication.sh
RSYNC_FILES=/opt/vidyo/ha/bin/rsyncfiles.sh
RSYNC_INTERVAL=60 ## every 1 min
LAST_TIME_RSYNC=0
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
ADMIN=/opt/vidyo/ha/bin/cip_admin
SCRIPT=/opt/vidyo/ha/hssync/replication_watcher.sh
PIDFILE=/var/run/replication_watcher.pid
CREATE_MASTER_SNAPSHOT=/opt/vidyo/ha/hssync/create_master_snapshot.sh
DBA=/opt/vidyo/etc/db/access.conf

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

dbsync_status()
{
   local REPL_STATUS=$(echo $* | sed 's/ /_/g')
   $ADMIN -r "$REPL_STATUS"
}

exit_handler()
{
   vlog3 "exit_handler(): Stopping slave replication"
   $STOP_SLAVE
   vlog3 "exit_handler(): Stopping ssh tunnel"
   $SSH_TUNNEL stop > /dev/null 2>&1
   rm -f $TUNNEL_OK
   exit 0
}

case "$1" in 
   start)
      /sbin/start-stop-daemon --background --oknodo --start --pidfile=$PIDFILE --make-pidfile --exec=$SCRIPT -- "monitor" #> /dev/null 2>&1
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


BEHIND_MASTER_CTR=0
SLAVE_ERR_CTR=0
SLAVE_NOT_RUNNING_CTR=0
DB_SNAPSHOT_OLD=U
$SSH_TUNNEL stop > /dev/null 2>&1
rm -f $TUNNEL_OK

while true; do
   ## break if the node is not standby
   SLEEP_CTR=0
   while [ $SLEEP_CTR -lt $MONITOR_INTERVAL ]; do 
      sleep 1
      ## break if not standby
      ((SLEEP_CTR++))
      ((MOD=$SLEEP_CTR%3))  ## check DBSyncStatus every 3 seconds
      if [ $MOD -eq 0 ]; then
         eval $($ADMIN --command=P)
         if [ "$MyRole" != "STANDBY" ]; then
            if [ "$DBSyncStatus" = "-1000" ]; then
               $CREATE_MASTER_SNAPSHOT 
               sleep 20 
               SLEEP_CTR=$MONITOR_INTERVAL
            fi
            continue
         fi
      fi
   done

   . $HA_CONF

   if [ -f $MAINT_FLAG ]; then
      vlog3 "Exit - Maintenance Mode Detected"
      break
   fi

   if [ "$VIDYO_HA" != "ENABLED" ]; then
      vlog3 "Exit - HA Not Enabled"
      break
   fi

   eval $($ADMIN --command=P)
   if [ "$MyRole" != "STANDBY" ]; then
      if [ "$DBSyncStatus" = "-1000" ]; then
         $CREATE_MASTER_SNAPSHOT 
      fi
      sleep 3
      continue
   fi

   if [ -z "$MyDBVersion" -o -z "$PeerDBVersion" ]; then
      vlog3 "Unknown DB Version[$MyDBVersion][$PeerDBVersion]!"
      $STOP_SLAVE
      sleep 5
      continue
   fi

   if [ "$MyDBVersion" != "$PeerDBVersion" ]; then
      vlog3 "Warning!!! DB Version not identical[$MyDBVersion][$PeerDBVersion]!"
      dbsync_status "-1001"
      $STOP_SLAVE
      sleep 5
      continue
   fi
 

   if ! tunnel_ok; then
      vlog3 "Starting SSH tunnel for replication...[$DB_SNAPSHOT]"
      $SSH_TUNNEL start > /dev/null 2>&1
      if [ $? -eq 0 ]; then
         touch $TUNNEL_OK
         if [ $DB_SNAPSHOT_OLD = Y -o  ! -f $DB_SNAPSHOT_COMPRESS ]; then  ## do a clean start of replication
            vlog3 "No DB snapshot found. Forcing a restart of DB Replication after 15 secs."
            sleep 15
            $RESTART_SLAVE_REPLICATION
            RC=$?
            if [ $RC -eq 10 ]; then
               dbsync_status "-1000"  ## DB snapshot too old
               vlog3 "dbsync_status = -1000"
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
         dbsync_status "-1100"
         sleep 5
         continue
      fi
   fi

   if [ $DB_SNAPSHOT_OLD = Y ]; then
      $RESTART_SLAVE_REPLICATION
      RC=$?
      if [ $RC -eq 10 ]; then
         dbsync_status "-1000"  ## DB snapshot too old
         vlog3 "dbsync_status = -1000"
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
      dbsync_status "$Seconds_Behind_Master"
      if [ $Seconds_Behind_Master -gt $BEHIND_MASTER ]; then
         if [ $BEHIND_MASTER_CTR -gt 5 ]; then
            vlog3 "Warning: Seconds_Behind_Master: $Seconds_Behind_Master. RESETTING SLAVE"
            BEHIND_MASTER_CTR=0
            $RESTART_SLAVE_REPLICATION
            RC=$?
            if [ $RC -eq 10 ]; then
               dbsync_status "-1000"  ## DB snapshot too old
               vlog3 "dbsync_status = -1000"
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
               dbsync_status "-1000"  ## DB snapshot too old
               vlog3 "dbsync_status = -1000"
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
      vlog3 "Error! SLAVE_NOT_RUNNING_CTR: $SLAVE_NOT_RUNNING_CTR"
      if [ $Last_IO_Errno -ne 0 ]; then 
         vlog3 "Warning! IO Error: $Last_IO_Errno, $Last_IO_Error"
         dbsync_status "-1101"
      fi
      if [ $Last_SQL_Errno -ne 0 ]; then 
         vlog3 "Warning! SQL Error: $Last_SQL_Errno, $Last_SQL_Error"
         dbsync_status "-1200"
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
            dbsync_status "-1000"  ## DB snapshot too old
            vlog3 "dbsync_status = -1000"
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
      LAST_TIME_RSYNC=$(date +%s)
      NEW_SHA_DBA=$(sha1sum $DBA | awk '{print $1}')

      ## check if DB password was changed, then restart tomcat
      if [ "$SHA_DBA" != "$NEW_SHA_DBA" ]; then
         /opt/vidyo/ha/hssync/update_credentials.sh
      fi

      dbsync_status "$Seconds_Behind_Master"  #update the dbsync status to clear the dbsyns status set by $RSYNC_FILES

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

$STOP_SLAVE

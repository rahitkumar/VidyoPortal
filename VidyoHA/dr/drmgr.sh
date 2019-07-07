#!/bin/bash
#####################################################
# Description: This will monitor & manage the nodes # 
#              in the disaster recovery group       #
#####################################################

DR_CONF=/opt/vidyo/conf/dr/dr.conf
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
GET_SSH_PORT=/opt/vidyo/ha/bin/get_ssh_port.sh
TUNNEL_OK=/root/ha/tunnel_ok
IS_NODE_ACTIVE=/opt/vidyo/ha/dr/isnodeactive.sh
DR_HEALTH_CHECK=/opt/vidyo/ha/dr/dr_health_check.sh
DR_HEALTH_CHECK_FAILED_CTR=0
ACTIVE_TO_STANDBY=/opt/vidyo/ha/dr/active_to_standby.sh
STANDBY_TO_ACTIVE=/opt/vidyo/ha/dr/standby_to_active.sh
ACTIVE_TO_MAINTENANCE=/opt/vidyo/ha/dr/active_to_maintenance.sh
STANDBY_TO_MAINTENANCE=/opt/vidyo/ha/dr/standby_to_maintenance.sh
GET_STATE=/opt/vidyo/ha/dr/get_state.sh
SET_STATE=/opt/vidyo/ha/dr/set_state.sh
REGISTER=/opt/vidyo/ha/dr/register.sh
DR_REPLICATION_WATCHER=/opt/vidyo/ha/dr/dr_replication_watcher.sh
WHOISACTIVE=/opt/vidyo/ha/dr/whoisactive.sh
PURGE_OLD_SITE=/opt/vidyo/ha/dr/purge_old_sites_conf.sh
GET_DB_VERSION=/opt/vidyo/ha/bin/get_db_version.sh
VPSTATUS=/opt/vidyo/ha/dr/dr_vidyoportal_status.sh
DR_COMMON_FUNC=/opt/vidyo/ha/dr/dr_common_func.sh
SSH_TUNNEL=/opt/vidyo/ha/dr/dr_tunnel.sh

[ -f $DR_CONF ] || exit 1
[ -f $DB_REPL ] || exit 1
[ -f $LOG_FUNCS ] || exit 1
[ -f $HA_CONF ] || exit 1
[ -f $GET_SSH_PORT ] || exit 1
[ -f $DR_COMMON_FUNC ] || exit 1
. $DR_COMMON_FUNC


. $LOG_FUNCS
. $DR_CONF
. $DB_REPL

. $LOCAL_NODE_CONF

mkdir -p $VARRUN

determine_active_node()
{
   . $HA_CONF
   . $DR_CONF
   if [ "$HA_MODE" != "DR" ]; then
      vlog3 "Exit - HA Mode is not set to DR"
      break
   fi

   vlog3 "determining the ACTIVE site's IP..."
   until [ -f $ACTIVE_IP_FILE ]; do
     if [ -f $MAINT_FLAG ]; then
        break
     fi
    sleep 1
   done
   vlog3 "determining the ACTIVE site's IP...DONE"
}

update_previous_active_ip()
{
   local PREV_IP=
   local CURR_IP=

   if [ -f $ACTIVE_IP_FILE ]; then
      CURR_IP=$(cat $ACTIVE_IP_FILE)
   fi

   if [ ! -f $PREVIOUS_ACTIVE_IP_FILE ]; then
      [ -f $ACTIVE_IP_FILE ] && cp -f $ACTIVE_IP_FILE $PREVIOUS_ACTIVE_IP_FILE

   fi
   PREV_IP=$(cat $PREVIOUS_ACTIVE_IP_FILE)

   if [ "$CURR_IP" = "$PREV_IP" ]; then
      echo "No change"
      return 1
   else
      echo "Change"
      [ -f $ACTIVE_IP_FILE ] && cp -f $ACTIVE_IP_FILE $PREVIOUS_ACTIVE_IP_FILE
      return 0
   fi
   return 1
}

standby_to_active()
{
   vlog3 standby_to_active
   $STANDBY_TO_ACTIVE
}

active_to_standby()
{
   vlog3 active_to_standby
   $ACTIVE_TO_STANDBY
}

active_to_maintenance()
{
   vlog3 active_to_maintenance
   $ACTIVE_TO_MAINTENANCE
}

standby_to_maintenance()
{
   vlog3 standby_to_maintenance
   $STANDBY_TO_MAINTENANCE
}

exit_handler()
{
   . $DR_CONF
   vlog3 "exit_handler(): clearing temp files..."
   [ -z "$VARRUN" ] && VARRUN=/var/run/dr
   rm -f $VARRUN/*
   exit 0
}

trap exit_handler EXIT SIGINT SIGTERM

. $HA_CONF

rm -f /opt/vidyo/ha/ha_portal.stop

if [ -f $MAINT_FLAG ]; then
   vlog3 "Maintenance Mode Detected. Aborting"
   exit
fi

mkdir -p /root/ha/

## let's remove the status page...
$VPSTATUS DISABLE

## save the db version
$GET_DB_VERSION

## run the dns poller
$WHOISACTIVE start
sleep 5

## save the process id
echo $$ > $DRMGR_PID
MAINTENANCE_STARTED=YES
vlog3 "Started with pid [$$]"
determine_active_node
$SET_STATE "UNKNOWN"
PREV_NODE_STATE=UNKNOWN
NODE_STATE=UNKNOWN
$DR_REPLICATION_WATCHER start $$  ## pass the PID so that watcher knows its parent
REGISTER_TIME=0
LAST_SUCCESS_REGISTER=$(date +%s)

$PURGE_OLD_SITES
LAST_TIME_OLD_SITES_PURGED=$(date +%s)

ENABLE_FORCE_ACTIVE_CTR=0

## save the original NOACTIVETIMEOUT
ORIG_NOACTIVETIMEOUT=$NOACTIVETIMEOUT

while true; do 
   ## purge old sites daily
   NOW=$(date +%s)
   ((AGE=NOW-LAST_TIME_OLD_SITES_PURGED))
   if [ $AGE -gt 14400 ]; then  ## try to purge every 4hours
      vlog3 "Purging old sites"
      $PURGE_OLD_SITES
      LAST_TIME_OLD_SITES_PURGED=$(date +%s)
   fi
   VIDYO_HA=
   [ -f $HA_CONF ] && . $HA_CONF

   if [ "$VIDYO_HA" != ENABLED ]; then
      vlog3 "Disaster Recovery is not enabled"
      break
   fi

   if [ -f $MAINT_FLAG ]; then
      vlog3 "Maintenance Mode Detected. Aborting...Current State: $NODE_STATE"
      $SET_STATE MAINTENANCE
      if [ "$MAINTENANCE_STARTED" = "YES" ]; then
        : ## do nothing
      else
         MAINTENANCE_STARTED=YES
         ##if [ $($GET_STATE) = "ACTIVE" ]; then
         if [ $NODE_STATE = "ACTIVE" ]; then
            active_to_maintenance
         else
            standby_to_maintenance
         fi
         
      fi
      break
      #sleep 3
      #continue
   fi

   if [ ! -f $ACTIVE_IP_FILE ]; then  ## whoisactive.sh is not running or unable to talk to ADC
      if [ "$NODE_STATE" = UNKNOWN ]; then
         if debug_mode; then
            vlog3 "DEBUG - ACTIVE_IP_FILE not found. NODE_STATE[$NODE_STATE]"
         fi
         REGISTER_TIME=0
         LAST_SUCCESS_REGISTER=$(date +%s)
         NOACTIVETIMEOUT=$ORIG_NOACTIVETIMEOUT
         sleep 2
      else
         if [ "$NODE_STATE" = ACTIVE ]; then
            vlog3 "Warning! No ACTIVE_IP_FILE found. Demoting to STANDBY"
            active_to_standby
         fi
         vlog3 "Warning! No ACTIVE_IP_FILE found. Stopping tunnel"
         $SSH_TUNNEL stop > /dev/null 2>&1
         NODE_STATE=UNKNOWN
         PREV_NODE_STATE=UNKNOWN
         $SET_STATE "UNKNOWN"
      fi
      continue
   fi
   MAINTENANCE_STARTED=
  
   if $IS_NODE_ACTIVE; then
      ## update the LAST_SUCCESS_REGISTER so that when site becomes STANDBY it
      #@ will NOT remember the last time it register when  this site was STANDBY.
      LAST_SUCCESS_REGISTER=$(date +%s)
      #vlog3 "PREV [$PREV_NODE_STATE],  CURRENT[$NODE_STATE]"
      if force_active; then
         #vlog3 "Force ACTIVE.. Maybe we should try to register !!!"
         :
      else
          ENABLE_FORCE_ACTIVE_CTR=0
      fi

      if [[ $PREV_NODE_STATE =~ ACTIVE ]]; then
         :
      else
         $SET_STATE PRE_ACTIVE
         if [ "$NODE_STATE" = PRE_ACTIVE ]; then
            standby_to_active
            $SET_STATE ACTIVE
            DR_HEALTH_CHECK_FAILED_CTR=0
         fi
      fi
      if [ "$NODE_STATE" = "ACTIVE" ]; then
         if ! $DR_HEALTH_CHECK; then
            ((DR_HEALTH_CHECK_FAILED_CTR++))
            vlog3 "DR_HEALTH_CHECK_FAILED [$DR_HEALTH_CHECK_FAILED_CTR]"
            if [ $DR_HEALTH_CHECK_FAILED_CTR -gt $MAX_FAILED_DR_HEALTH_CHECK ]; then
               vlog3 "Do something!!! REBOOT REBOOT REBOOT"
               $VPSTATUS DISABLE
               /sbin/reboot
               sleep 10
            fi
         fi
      fi
   else
      NOW=$(date +%s)
      ((ELAPSE=NOW-REGISTER_TIME))
      
      if [ $ELAPSE -ge $REGISTER_INTERVAL -a "$($GET_STATE)" = STANDBY ]; then
         REGISTER_TIME=$(date +%s)
         vlog3 "About to register..."
         if $REGISTER; then
            LAST_SUCCESS_REGISTER=$(date +%s)
            NOACTIVETIMEOUT=$ORIG_NOACTIVETIMEOUT
         else
            if [ $NOACTIVETIMEOUT -gt 0 ]; then
               if [ -z "$LAST_SUCCESS_REGISTER" ]; then
                  vlog3 "Failed to register. Last Successful Registration: <UNKNOWN>"
               else
                  ((LAST_SUCCESS_REGISTER_SECONDS=$(date +%s)-LAST_SUCCESS_REGISTER))
                  ((FORCE_ACTIVE_TIMER=NOACTIVETIMEOUT-LAST_SUCCESS_REGISTER_SECONDS))
                  if debug_mode; then
                     vlog3 "Failed to register. Last Successful Registration: $LAST_SUCCESS_REGISTER_SECONDS ago. Threshold: $NOACTIVETIMEOUT"
                  fi
                  #if [ $LAST_SUCCESS_REGISTER_SECONDS -gt $NOACTIVETIMEOUT  ]; then
                  if [ $FORCE_ACTIVE_TIMER -le 0 ]; then
                     vlog3 "Warning! Exceeded the No Active Threshold. Enabling FORCE_ACTIVE for $MAXFORCEACTIVE seconds ..."
                     enable_force_active
                     ((ENABLE_FORCE_ACTIVE_CTR++))

                     #. $LOCAL_NODE_CONF  #reload the configuration to read the original NOACTIVETIMEOUT
                     #((TEMPNOACTIVETIMEOUT=NOACTIVETIMEOUT+NOACTIVETIMEOUT*ENABLE_FORCE_ACTIVE_CTR))
                     ((TEMPNOACTIVETIMEOUT=NOACTIVETIMEOUT*2))
                     NOACTIVETIMEOUT=$TEMPNOACTIVETIMEOUT
                    vlog3 "NOACTIVETIMEOUT is set to $NOACTIVETIMEOUT. Max enable force active count: $ENABLE_FORCE_ACTIVE_CTR/$MAX_ENABLE_FORCE_ACTIVE_CTR"
                    if [ $ENABLE_FORCE_ACTIVE_CTR -gt $MAX_ENABLE_FORCE_ACTIVE_CTR ]; then
                       ENABLE_FORCE_ACTIVE_CTR=0
                    fi
                  else
                     vlog3 "Failed to register. [$ENABLE_FORCE_ACTIVE_CTR]Force ACTIVE will be activated in $FORCE_ACTIVE_TIMER seconds if registration failure continues"
                  fi
               fi
            fi
         fi
      fi
      #echo "DEBUG - Node is not ACTIVE"
      if update_previous_active_ip; then
         vlog3 "Detected a new ACTIVE node..."
         touch $NEW_ACTIVE_NODE
      fi
      if [ "$PREV_NODE_STATE" != STANDBY ]; then
         if [ "$NODE_STATE" = ACTIVE ]; then
            active_to_standby
            LAST_SUCCESS_REGISTER=$(date +%s)
         fi
         $SET_STATE STANDBY
      fi
   fi
   PREV_NODE_STATE=$NODE_STATE
   NODE_STATE=$($GET_STATE)
   if [ "$NODE_STATE" != "$PREV_NODE_STATE" ]; then
      vlog3 "State change from [$PREV_NODE_STATE] to [$NODE_STATE]"
   fi

   COUNT=5
   while [ $COUNT -gt 0 -a ! -f $MAINT_FLAG ]; do
      ((COUNT--))
      if debug_mode; then
         vlog3 "DEBUG - COUNT: $COUNT,  inside 5 sec delay"
      fi
      sleep 1
   done
done
vlog3 "End"
touch /opt/vidyo/ha/ha_portal.stop

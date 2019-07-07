#!/bin/bash
#####################################################
# Description: Run a query from ADC to determine the
#              IP address of the ACTIVE node. This 
#              must be started during server startup.
#
# whoisactive.sh [Options]
# Options:
#     start - will continueosly query the IP address
#             of the ACTIVE node from ADC every 
#             5 seconds (default).
#     stop  - stop the script from querying ADC.
#####################################################

DR_CONF=/opt/vidyo/conf/dr/dr.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
SCRIPT=/opt/vidyo/ha/dr/whoisactive.sh
DR_COMMON_FUNC=/opt/vidyo/ha/dr/dr_common_func.sh

[ -f $DR_CONF ] || exit 1


. $LOG_FUNCS
. $DR_CONF
. $DR_COMMON_FUNC

PIDFILE=$VARRUN/whoisactive.pid
INTERVAL=$VARRUN/whoisactive.interval
mkdir -p $VARRUN
chmod 750 $VARRUN

monitor_ip()
{
   local THRESHOLD=0
   local MAXTHRESHOLD=3
   local DIG_FAIL_CTR=0

   [ "$1" = ONCE ] || vlog3 "Starting whoisactive as daemon process..."
   while true; do
      VIDYO_HA=
      [ -f $HA_CONF ] && . $HA_CONF
      if [ -f $MAINT_FLAG ]; then
         vlog3 "Maintenance Mode Detected. Aborting..."
         break
      fi
      if [ "$VIDYO_HA" != ENABLED  -o "$HA_MODE" != DR ]; then
         [ -f $ACTIVE_IP_FILE ] && rm -f $ACTIVE_IP_FILE
         break
         #sleep 7
         #continue
      fi 
      if force_active; then
         vlog3 "Force ACTIVE is enabled on this site. Time left: $(force_active_time_left)"
      fi
      . $LOCAL_NODE_CONF
      if [ -z "$ADCADDRESS" ]; then
         vlog3 "ERROR! ADC Address is not set."
         exit 1
      fi
      if [ -z "$PUBLICFQDN" ]; then
         vlog3 "ERROR! Public FQDN Address is not set."
         exit 1
      fi
      
      while [ $DIG_FAIL_CTR -lt 5 ]; do
         dig @$ADCADDRESS  $PUBLICFQDN  +short +time=3 +tries=2 > ${ACTIVE_IP_FILE_TMP}.1
         RC=$?
         [ $RC -eq 0 ] && break
         ((DIG_FAIL_CTR++))
         vlog3 "[$DIG_FAIL_CTR] Warning! Failed to resolve Public FQDN. RC: $RC"
         sleep 3
      done
      DIG_FAIL_CTR=0
      tail -1 ${ACTIVE_IP_FILE_TMP}.1 > $ACTIVE_IP_FILE_TMP
      if [ $RC -ne 0 ]; then
         vlog3 "Warning! Unable to resolve the IP Address of the Public FQDN."
         rm -f $ACTIVE_IP_FILE
      else
         if [ -f $ACTIVE_IP_FILE ]; then
            NEW_ACTIVE_IP=$(cat $ACTIVE_IP_FILE_TMP)

            ## ERIC review this condition below...!!!!
            if force_active && [ "$NEW_ACTIVE_IP" = "$PUBLICIP" ]; then
               THRESHOLD=0
               mv -f $ACTIVE_IP_FILE_TMP $ACTIVE_IP_FILE
               vlog3 "ACTIVE IP($NEW_ACTIVE_IP) match our PUBLIC IP($PUBLICIP). Disabling force active now"
               disable_force_active
            else
               if ! cmp -s $ACTIVE_IP_FILE $ACTIVE_IP_FILE_TMP ; then
                  NEW_ACTIVE_IP_FILE_TMP=$(cat $ACTIVE_IP_FILE_TMP)
                  ((THRESHOLD++))
                  vlog3 "[$THRESHOLD/$MAXTHRESHOLD] Warning! Detected changes in ACTIVE IP. New IP[$NEW_ACTIVE_IP_FILE_TMP]"
                  if [ $THRESHOLD -ge $MAXTHRESHOLD ]; then
                     THRESHOLD=0
                     vlog3 "Max threshold reached! Updating the ACTIVE IP..."
                     mv -f $ACTIVE_IP_FILE_TMP $ACTIVE_IP_FILE
                  fi 
               else
                  THRESHOLD=0
                  mv -f $ACTIVE_IP_FILE_TMP $ACTIVE_IP_FILE
               fi
            fi
         else
            mv -f $ACTIVE_IP_FILE_TMP $ACTIVE_IP_FILE
         fi
      fi

      if [ "$1" = "ONCE" ]; then
         cat $ACTIVE_IP_FILE
         exit $RC
      fi
      if [ -f $INTERVAL ]; then
         DELAY=$(cat $INTERVAL|tr -cd "[0-9]")
         [ -n "$DELAY" ] && sleep $DELAY
      else
         sleep $DNS_POLL_INTERVAL
      fi
      if [ -f ${PIDFILE}.stop ]; then
         rm -f ${PIDFILE}.stop 
         rm -f $PIDFILE
         rm -f $ACTIVE_IP_FILE
         rm -f $ACTIVE_IP_FILE_TMP
         break
      fi
   done

}

case $1 in
   start)
      /sbin/start-stop-daemon --background --oknodo --start --pidfile=$PIDFILE --make-pidfile --exec=$SCRIPT -- "monitor" #> /dev/null 2>&1
   exit 0
      ;;
   stop)
      touch ${PIDFILE}.stop
      ;;
   monitor)
      monitor_ip 
      ;;
   *)
      if force_active; then
         PUBLICIP=
         . $LOCAL_NODE_CONF
         echo $PUBLICIP
      else
         if [ -f $ACTIVE_IP_FILE ]; then
            cat $ACTIVE_IP_FILE
         else
            monitor_ip ONCE
         fi
      fi
      ;;
esac

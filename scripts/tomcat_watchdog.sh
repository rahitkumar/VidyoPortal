#!/bin/bash
#####################################################################
# Filename: tomcat_watcher.sh
# Description: Monitors tomcat response time within the sliding window.
#              The sliding window is calculated using maximum number of
#              samples($MAX_BUCKET_SIZE-1) multiplied by number of second 
#              interval($N) for every sample.
#              if N is 5 then MAX_BUCKET_SIZE is 25 then the sliding window
#              is 2 mins (120 seconds)
#              If percentage of failure with $TEST_WINDOW is >=
#              $MAX_PCT_FAILURE then this script will restart tomcat.
#####################################################################

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:$PATH

N=5
AJPPORT=8009
AJPPORT_NP=9009
FAILED=0

READ_TMO=10
INTERVAL=5
MAX_PCT_FAILURE=50
NP_MAX_PCT_FAILURE=40
MAX_BUCKET_SIZE=25
((MAX_BUCKET_INDEX=MAX_BUCKET_SIZE-1))
BUCKET_INDEX=0
DEBUG=0

RELOAD_FLAG=/root/tomcat_watchdog_reload


logme()
{
   logger -t "tomcat_watchdog.sh" "$*"
}

initialize_sliding_window()
{
   unset BUCKET
   BUCKET_INDEX=${#BUCKET[@]}
   unset NP_BUCKET
}

log_sliding_window()
{
   [ $DEBUG -eq 1 ] && logme "BUCKET = ${BUCKET[@]}"
   [ $DEBUG -eq 1 ] && logme "NP_BUCKET = ${NP_BUCKET[@]}"
}

## return 0 if the percentage failed is less than MAX_PCT_FAILURE
check_sliding_window()
{
   local BUCKET_SIZE=${#BUCKET[@]}
   local NP_BUCKET_SIZE=${#NP_BUCKET[@]}
   local FAILED=0
   local NP_FAILED=0
   local IDX=0
   
   [ $BUCKET_SIZE -lt $MAX_BUCKET_SIZE ] && return 0

   ## if BUCKET is full calculate the percentage failure
   
   until [ $IDX -ge $MAX_BUCKET_SIZE ]; do
      if [ ${BUCKET[$IDX]} -eq 1 ]; then
         ((FAILED++))
      fi
      if [ ${NP_BUCKET[$IDX]} -eq 1 ]; then
         ((NP_FAILED++))
      fi
      ((IDX++))
   done

   ## shift the elements to the left. 
   BUCKET=("${BUCKET[@]:1}")
   NP_BUCKET=("${NP_BUCKET[@]:1}")


   #IDX=1
   #((BUCKET_SIZE--))

   #until [ $IDX -ge $MAX_BUCKET_SIZE ]; do
   #   BUCKET[$IDX-1]=${BUCKET[$IDX]}
   #   NP_BUCKET[$IDX-1]=${NP_BUCKET[$IDX]}
   #   ((IDX++))
   #done

   ((FAILED_PCT=FAILED*100/$MAX_BUCKET_SIZE))
   ((NP_FAILED_PCT=NP_FAILED*100/$MAX_BUCKET_SIZE))

   [ $FAILED_PCT -gt 0 ] && logme "(priv)Percentage Failure is $FAILED_PCT %"
   [ $NP_FAILED_PCT -gt 0 ] && logme "(non-priv)Percentage Failure is $NP_FAILED_PCT %"

   [ $FAILED_PCT -ge  $MAX_PCT_FAILURE ] && return 1
   [ $NP_FAILED_PCT -ge  $NP_MAX_PCT_FAILURE ] && return 1

   return 0
}

##########################################################################
#The tomcat will start listening to the AJP port when it is ready to serve.
#This happens then catalina.out prints 'Server startup'.
##########################################################################
is_tomcat_ready()
{
   local RC1=0
   local RC2=0

   /opt/vidyo/bin/ajp_ping.py 127.0.0.1 $AJPPORT > /dev/null 2>&1
   RC1=$?
   if [ $RC1 -eq 0 ]; then
      logme "Super Tomcat is ready..."
   fi

   /opt/vidyo/bin/ajp_ping.py 127.0.0.1 $AJPPORT_NP > /dev/null 2>&1
   RC2=$?
   if [ $RC2 -eq 0 ]; then
      logme "Non-priv Tomcat is ready..."
   fi

   if [ $RC1 -eq 0 -a $RC2 -eq 0 ]; then
      return 0
   else
      return 1
   fi
}

is_node_standby()
{
   local VMCOUNT=$(pgrep -c VidyoManager)
   local RET=0
   ## VidyoManager will not run on a standby node.
   if [ $VMCOUNT -gt 0 ]; then
       RET=1
   fi   
   return $RET
   
   ## ignore the code below for now... we cannot rely on the CLUSTERIP anymore
   ## to detemine of node is active or standby
   
   local HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
   local ETH0_IPS=$(/sbin/ip addr show dev eth0 scope global | grep inet | tr "/" " " | awk '{ print $2 }')
   local ret

   [ -f $HA_CONF ] || return 1

   . $HA_CONF

   [ "$VIDYO_HA" != "ENABLED" ] && return 1

   [ -z "$CLUSTER_IP" ] && return 1

   ret=0
   for I in $ETH0_IPS; do
      if [ "$I" = "$CLUSTER_IP" ]; then
         ret=1
         break
      fi
   done
  
   return $ret
}

is_update_in_progress()
{
   local UFLAG=/opt/vidyo/temp/root/update_in_progress

   local now=$(date +%s)
   local flag_tm
   local tmdiff

   if [ ! -f $UFLAG ]; then
      return 1
   fi

   flag_tm=$(stat --format=%X $UFLAG)

   tm_diff=$((now - flag_tm))

   if (( tm_diff > 1800 )); then
      ### file is too old... not valid anymore. files is 30 mins old.
      return 1
   fi

   return 0
}


tomcat_pid()
{
  echo `ps aux | grep org.apache.catalina.startup.Bootstrap | grep -v grep | awk '{ print $2 }'`
}

#################################################################
# determine whether webserver is accessible on IPv4 or IPv6 only
#################################################################
get_web_address()
{
   TEMP_WEBADDRESS=127.0.0.1
   IPV4=$(ip addr show dev eth0 | grep "\<inet\>")
   if [ -z "$IPV4" ]; then
      TEMP_WEBADDRESS=[::1]
   fi

   HTTP_NO_REDIRECT=N
   HTTPS_ONLY=N
   [ -f /opt/vidyo/etc/ssl/private/ssl-forced ] && HTTPS_ONLY=$(cat /opt/vidyo/etc/ssl/private/ssl-forced)
   [ -f /opt/vidyo/etc/ssl/private/ssl-forced-no-redirect ] && HTTP_NO_REDIRECT=$(cat /opt/vidyo/etc/ssl/private/ssl-forced-no-redirect)

   

   if [ "$HTTP_NO_REDIRECT" = "Y" -o "$HTTPS_ONLY" = "Y" ]; then
      WEBADDRESS="https://${TEMP_WEBADDRESS}"
   else
      WEBADDRESS="http://${TEMP_WEBADDRESS}"
   fi

   logme "WEBADDRESS=[$WEBADDRESS]"

}


if [ "$1" = "start" ]; then
    /sbin/start-stop-daemon --background --oknodo --start --pidfile=/var/run/tomcat_watcher.pid --make-pidfile --startas=/opt/vidyo/bin/tomcat_watchdog.sh  --exec=/opt/vidyo/bin/tomcat_watchdog.sh  > /dev/null 2>&1
    exit 0
elif [ "$1" = "stop" ]; then
    if [ -f /var/run/tomcat_watcher.pid ]; then
       TWPID=$(cat /var/run/tomcat_watcher.pid)
       [ -n "$TWPID" ] && kill -9 $TWPID
       rm -f /var/run/tomcat_watcher.pid
    fi
    exit
fi

logme "Started..."
START_TIME=$(date +%s)
START_MONITOR=0

initialize_sliding_window
get_web_address

while true; do
   if [ -f $RELOAD_FLAG ]; then
      logme "Retrieving web address..."
      get_web_address
      rm -f $RELOAD_FLAG
   fi
   sleep $INTERVAL
   #NEXT_REQUEST_TIME=$(date +%s)
   #((NEXT_REQUEST_TIME+=5))

   if is_update_in_progress; then
      START_MONITOR=0
      START_TIME=$(date +%s)
      logme "Portal update is in progress...."
      continue;
   fi

   if is_node_standby; then
      ## give an extra sleep
      sleep 30
      START_MONITOR=0
      START_TIME=$(date +%s)
      continue;
   fi

   PIDS=$(tomcat_pid)

   if [ -z "$PIDS" ]; then
      logme "Tomcat is not running..."
      START_TIME=$(date +%s)
      continue
   fi

   ## do not test the tomcat if not yet ready

   if [ $START_MONITOR -eq 0 ]; then
      while ! is_tomcat_ready; do
         logme "Tomcat is not yet ready..."
         sleep 5
         continue
      done
      logme "Tomcat is now ready...Start monitoring."
      START_MONITOR=1
      START_TIME=$(date +%s)
      initialize_sliding_window
   fi

   HTTP_RESP=$(wget  --server-response --read-timeout=$READ_TMO --tries=1 --no-check-certificate  --delete-after  $WEBADDRESS/super/js/SuperApp/resources/icons/new.png 2>&1 | grep HTTP | tr "a-z" "A-Z")
   HTTP_RESP_NP=$(wget  --server-response --read-timeout=$READ_TMO --tries=1 --no-check-certificate  --delete-after $WEBADDRESS/blank.png 2>&1 | grep HTTP | tr "a-z" "A-Z")

   #NOW=$(date +%s)
   #((N=NEXT_REQUEST_TIME-NOW))
 
   #logme "DEBUG Response from tomcat: [$HTTP_RESP]"
   #logme "DEBUG Response from tomcatnp: [$HTTP_RESP_NP]"

   if [[ ! "$HTTP_RESP" =~ "200 OK" ]]; then
      logme "Warning!!! Response from tomcat: [$HTTP_RESP]"
      BUCKET[$BUCKET_INDEX]=1
   else
      BUCKET[$BUCKET_INDEX]=0
   fi

   if [[ ! "$HTTP_RESP_NP" =~ "200 OK"|"403 FORBIDDEN" ]]; then
      logme "Warning!!! Response from non-privilege tomcat: [$HTTP_RESP_NP]"
      NP_BUCKET[$BUCKET_INDEX]=1
   else
      NP_BUCKET[$BUCKET_INDEX]=0
   fi

   if [ $BUCKET_INDEX -lt $MAX_BUCKET_INDEX ]; then
      ((BUCKET_INDEX++))
   fi


   [ $DEBUG -eq 1 ] && logme "BUCKET_INDEX: $BUCKET_INDEX,  MAX_BUCKET_SIZE: $MAX_BUCKET_SIZE"

   log_sliding_window

   if ! check_sliding_window; then
      logme "Percentage Failure: tomcat - $FAILED_PCT %, tomcatnp - $NP_FAILED_PCT, restarting tomcat..."
      /etc/init.d/tomcat restart > /dev/null 2>&1
      START_MONITOR=0
      #N=5
   fi

done

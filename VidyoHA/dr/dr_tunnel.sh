#!/bin/bash
########################################################################
# Filename: dr_tunnel.sh
# Description: Create a secured tunnel from STANDBY site to ACTIVE site.
#              This secured tunnel will be used by mysql for replication.
# Note: This is needed to run only on STANDBY node and should be run by
#       a user that can ssh to the peer node without requiring password.
#       In this case, it should be the $DBSYNC_USER defined on $HA_CONF.
########################################################################

DR_CONF=/opt/vidyo/conf/dr/dr.conf
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
GET_SSH_PORT=/opt/vidyo/ha/bin/get_ssh_port.sh
TUNNEL_OK=/root/ha/tunnel_ok
IS_NODE_ACTIVE=/opt/vidyo/ha/dr/isnodeactive.sh


[ -f $DR_CONF ] || exit 1
[ -f $DB_REPL ] || exit 1
[ -f $LOG_FUNCS ] || exit 1
[ -f $HA_CONF ] || exit 1
[ -f $GET_SSH_PORT ] || exit 1

. $DR_CONF
. $DB_REPL
. $LOG_FUNCS
. $HA_CONF

. /opt/vidyo/adm/myfunctions.sh

#get the ssh port
eval $($GET_SSH_PORT)

SSH_ID="-i /home/dbsync/.ssh/id_rsa"
SSH_OPT="$SSH_ID -o Port=$SSH_PORT -o ConnectTimeout=5 -o PasswordAuthentication=no -o StrictHostKeyChecking=no"

GREPCMD="ssh $SSH_OPT -L ${SSH_TUNNEL_LOCAL_PORT}:127.0.0.1:${SSH_TUNNEL_DEST_PORT} -l dbsync"


PID=$(ps -ef | grep -v grep |grep -i "$GREPCMD" | awk '{ print $2}')
if [ -n "$PID" ]; then
   kill -TERM $PID
   sleep 1
fi

if [ "$1" = "stop" ]; then
   vlog3 "removing ssh tunnel..."
   rm -f $TUNNEL_OK
   RC=$?
elif [ "$1" = "start" ]; then
   ## make sure we are not the ACTIVE site
   if $IS_NODE_ACTIVE; then
      vlog3 "Warning! Attempt to establish DR Tunnel from ACTIVE site."
      exit 1
   fi

   ## check if we have the ACTIVE site's IP address
   if [ ! -f $ACTIVE_IP_FILE ]; then
      vlog3 "Warning! Can't find the ACTIVE site's IP. Tunnel wont establish"
      exit 1
   fi

   if [ ! -f $ACTIVE_NODE_CONF ]; then
      vlog3 "Warning! No configuration for ACTIVE node. Tunnel wont establish"
      exit 1
   fi

   ## now get the IP address that will be used by ACTIVE site for ssh.
   . $ACTIVE_NODE_CONF
   ACTIVE_IP=$(cat $ACTIVE_IP_FILE)
   if [ "$ACTIVE_IP" = "$DATASYNCIP" ]; then
      vlog3 "DataSyncIp is same as ACTIVE's site Public IP"
   else
      vlog3 "DataSyncIp is NOT same as ACTIVE's site Public IP"
   fi

   if [ -n "$DATASYNCIP" ]; then
      TARGET_IP=$DATASYNCIP
   else
      TARGET_IP=$ACTIVE_IP
   fi

   CMD="ssh $SSH_OPT -L ${SSH_TUNNEL_LOCAL_PORT}:127.0.0.1:${SSH_TUNNEL_DEST_PORT} -l dbsync $TARGET_IP -p $SSH_PORT -f -N"

   SSH_TMP=$(mktemp)
   vlog3 "creating ssh tunnel...[$CMD]"
   $CMD > $SSH_TMP 2>&1
   RC=$?
   if [ $RC -ne 0 ]; then
      vlog3 "Failed to establish a tunnel: $RC"
   fi

   SSH_OUT=$(cat $SSH_TMP)

   echo $SSH_OUT | grep -iq "avoid man-in-the-middle"
   if [ $? -eq 0 ]; then
      vlog3 "=============================================================="
      vlog3 "WARNING!!! REMOTE HOST IDENTIFICATION HAS CHANGED!            "
      vlog3 "PORT FORWARDING is DISABLED to avoid man-in-the-middle attacks"
      vlog3 "Please RESET the TRUSTED HOSTS list from Admin Console"
      vlog3 "=============================================================="
      RC=1
   fi

   rm -f $SSH_TMP

else
   RC=1
fi

exit $RC

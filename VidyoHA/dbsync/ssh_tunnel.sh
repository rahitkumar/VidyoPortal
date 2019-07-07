#!/bin/bash
########################################################################
# Filename: ssh_tunnel.sh
# Description: Create a secured tunnel from STANDBY node to ACTIVE node.
#              This secured tunnel will be used by mysql for replication.
# Note: This is needed to run only on STANDBY node and should be run by
#       a user that can ssh to the peer node without requiring password.
#       In this case, it should be the $DBSYNC_USER defined on $HA_CONF.
# Modification History:
# 04-04-2012 - Initial Coding(Eric)
########################################################################

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV


[ -f $LOG_FUNCS ] && . $LOG_FUNCS

vlog3 "starting...[$*]"

[ ! -f $DR_CONF ] && exit 1
[ ! -f $DB_REPL ] && exit 1

. $DR_CONF
. $DB_REPL

CLUSTER_IP=$(/opt/vidyo/ha/dr/reverse_dns.sh)

#get the ssh port
eval $($GET_SSH_PORT)

#CMD="ssh -o ConnectTimeout=10 -L ${SSH_TUNNEL_LOCAL_PORT}:127.0.0.1:${SSH_TUNNEL_DEST_PORT} $CLUSTER_IP -p $SSH_PORT -f -N"
CMD="ssh $SSH_OPT -L ${SSH_TUNNEL_LOCAL_PORT}:127.0.0.1:${SSH_TUNNEL_DEST_PORT} $CLUSTER_IP -p $SSH_PORT -f -N"

PID=
PID=$(ps -ef | grep -v grep |grep -i "$CMD" | awk '{ print $2}')
echo PID[$PID]
if [ -n "$PID" ]; then
   kill -TERM $PID
   sleep 1
fi

if [ "$1" = "stop" ]; then
   vlog3 "removing ssh tunnel..."
   rm -f $TUNNEL_OK
elif [ "$1" = "start" ]; then
   vlog3 "creating ssh tunnel...[$CMD]"
   sudo -u $DBSYNC_USER  $CMD 2>&1
   RC=$?
   if [ $RC -ne 0 ]; then
      vlog3 "Failed to establish a tunnel: $RC"
      $SET_STATE NET_ERR
      rm -f $TUNNEL_OK
   else
      $SET_STATE TUNNEL_UP
      touch $TUNNEL_OK
   fi
fi

exit $RC

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

DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
GET_SSH_PORT=/opt/vidyo/ha/bin/get_ssh_port.sh
TUNNEL_OK=/root/ha/tunnel_ok



[ -f $DB_REPL ] || exit 1
. $DB_REPL

[ -f $LOG_FUNCS ] || exit 1
. $LOG_FUNCS

[ -f $HA_CONF ] || exit 1
. $HA_CONF

[ -f $DB_REPL ] ||  exit 1
. $DB_REPL

[ -f $GET_SSH_PORT ] || exit 1

. /opt/vidyo/adm/myfunctions.sh

#get the ssh port
eval $($GET_SSH_PORT)

SSH_ID="-i /home/dbsync/.ssh/id_rsa"
SSH_OPT="$SSH_ID -o Port=$SSH_PORT -o ConnectTimeout=5 -o PasswordAuthentication=no -o StrictHostKeyChecking=no"

if [ "$HB_IFC" = eth1 ]; then
   TARGET_IP=$PEER_ETH1_IP
else
   if [ "$ENABLE_SHARED_IP" = y ]; then
      if IsIPV6 "$CLUSTER_IP"; then
         TARGET_IP=[$PEER_IP]
      else
         TARGET_IP=$PEER_IP
      fi
   else
      TARGET_IP=$PEER_IP
   fi
   
fi

CMD="ssh $SSH_OPT -L ${SSH_TUNNEL_LOCAL_PORT}:127.0.0.1:${SSH_TUNNEL_DEST_PORT} -l dbsync $TARGET_IP -p $SSH_PORT -f -N"


#echo CMD=[$CMD]

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
   RC=$?
elif [ "$1" = "start" ]; then
   SSH_TMP=$(mktemp)
   vlog3 "creating ssh tunnel...[$CMD]"
   $CMD > $SSH_TMP 2>&1
   RC=$?

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

   if [ $RC -ne 0 ]; then
      vlog3 "Failed to establish a tunnel: $RC"
   fi
else
   RC=1
fi

exit $RC

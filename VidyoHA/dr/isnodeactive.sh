#!/bin/bash
#####################################################
# Description: Determine of the size is active or not.
# exit code is 0 when node is ACTIVE otherwise STANDBY
#####################################################

DR_CONF=/opt/vidyo/conf/dr/dr.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
DR_COMMON_FUNC=/opt/vidyo/ha/dr/dr_common_func.sh


[ -f $DR_CONF ] || exit 1
. $DR_CONF

[ -f $DR_COMMON_FUNC ] || exit 1
. $DR_COMMON_FUNC


[ -f $LOCAL_NODE_CONF ] || exit 1
[ -f $HA_CONF ] || exit 1

if force_active; then
   exit 0   
fi

[ -f $ACTIVE_IP_FILE ] || exit 1

ACTIVE_IP=$(cat $ACTIVE_IP_FILE)
. $LOCAL_NODE_CONF

[ "$PUBLICIP" = "$ACTIVE_IP" ] && exit 0
exit 1

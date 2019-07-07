#!/bin/bash

DR_CONF=/opt/vidyo/conf/dr/dr.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf

[ -f $DR_CONF ] || exit 1
[ -f $HA_CONF ] && . $HA_CONF

. $DR_CONF

if [ "$VIDYO_HA" != "ENABLED" ]; then
   echo NOT ENABLED
   exit 0
fi

if [ -f $MAINT_FLAG ]; then
   echo MAINTENANCE
   exit 0
fi

CURR_STATE=UNKNOWN
if [ -f $NODE_STATE_FILE ]; then
   CURR_STATE=$(cat $NODE_STATE_FILE)
fi
if [ -z "$CURR_STATE" ]; then
   CURR_STATE=UNKNOWN
fi
echo $CURR_STATE

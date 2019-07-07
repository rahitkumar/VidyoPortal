#!/bin/bash
#####################################################
# Description: This will monitor & manage the nodes # 
#              in the disaster recovery group       #
#####################################################

DR_CONF=/opt/vidyo/conf/dr/dr.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
TUNNEL_OK=/root/ha/tunnel_ok
IS_NODE_ACTIVE=/opt/vidyo/ha/dr/isnodeactive.sh
GET_STATE=/opt/vidyo/ha/dr/get_state.sh
SET_STATE=/opt/vidyo/ha/dr/set_state.sh
STOP_SLAVE=/opt/vidyo/ha/dr/dr_stop_slave.sh
REGISTER=/opt/vidyo/ha/dr/register.sh
ROTATE_SNAPSHOT=/opt/vidyo/ha/hssync/rotate_db_snapshot.sh



[ -f $DR_CONF ] || exit 1
[ -f $LOG_FUNCS ] || exit 1
[ -f $HA_CONF ] || exit 1

. $LOG_FUNCS
. $DR_CONF
. $HA_CONF

# stop slave replication
$STOP_SLAVE

# remove active node's configuration
rm -f $ACTIVE_NODE_CONF

# remove database snapshot. rsync a new snapshot from the new site
$ROTATE_SNAPSHOT

# register to the new site
$REGISTER
exit $?

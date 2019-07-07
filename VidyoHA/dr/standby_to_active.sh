#!/bin/bash
#########################################################
# Description: This script will be executed by drmgr.sh
#              whenever STANDBY site becomes ACTIVE. 
#
#########################################################

DR_CONF=/opt/vidyo/conf/dr/dr.conf
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
IS_NODE_ACTIVE=/opt/vidyo/ha/dr/isnodeactive.sh
GET_STATE=/opt/vidyo/ha/dr/get_state.sh
SET_STATE=/opt/vidyo/ha/dr/set_state.sh
STOP_SLAVE=/opt/vidyo/ha/dr/dr_stop_slave.sh
CREATE_DB_SNAPSHOT=/opt/vidyo/ha/dr/dr_create_master_snapshot.sh
BLOCK_TOMCATNP=/opt/vidyo/ha/dr/block_tomcatnp.sh
ROTATE_SNAPSHOT=/opt/vidyo/ha/hssync/rotate_db_snapshot.sh
PURGE_OLD_SITE=/opt/vidyo/ha/dr/purge_old_sites_conf.sh
DR_COMMON_FUNC=/opt/vidyo/ha/dr/dr_common_func.sh

[ -f $DR_CONF ] || exit 1
[ -f $DB_REPL ] || exit 1
[ -f $LOG_FUNCS ] || exit 1
[ -f $HA_CONF ] || exit 1

. $LOG_FUNCS
. $DR_CONF
. $DB_REPL
. $DR_COMMON_FUNC

if $IS_NODE_ACTIVE; then
   STATE=$($GET_STATE)
   vlog3 "STATE = $STATE"
   if [ "$STATE" != PRE_ACTIVE ]; then
      vlog3 "Error! Node must on on PRE_ACTIVE state."
      exit 1
   fi
fi

$PURGE_OLD_SITE DELETE_ALL_STANDBY

vlog3 "Stopping database replication...Stopping tunnel"
$STOP_SLAVE

$ROTATE_SNAPSHOT
vlog3 "Creating database snapshot..."
$CREATE_DB_SNAPSHOT FORCE
vlog3 "Starting Web Server..."
/etc/init.d/apache2 restart
vlog3 "Starting VidyoManager..."
/opt/vidyo/StartVC2.sh
$BLOCK_TOMCATNP stop


if [ -f $FORCE_ACTIVE_FLAG ]; then
   vlog3 "Resetting the FORCE ACTIVE timer..."
   enable_force_active  ## reset the force active timeout
fi


#!/bin/bash
#########################################################
# Description: This script will be executed by drmgr.sh
#              whenever ACTIVE site goes to STANDBY.
#
#########################################################

DR_CONF=/opt/vidyo/conf/dr/dr.conf
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
IS_NODE_ACTIVE=/opt/vidyo/ha/dr/isnodeactive.sh
DR_TUNNEL=/opt/vidyo/ha/dr/dr_tunnel.sh
GET_STATE=/opt/vidyo/ha/dr/get_state.sh
SET_STATE=/opt/vidyo/ha/dr/set_state.sh
VPSTATUS=/opt/vidyo/ha/dr/dr_vidyoportal_status.sh
BLOCK_TOMCATNP=/opt/vidyo/ha/dr/block_tomcatnp.sh


[ -f $DR_CONF ] || exit 1
[ -f $DB_REPL ] || exit 1
[ -f $LOG_FUNCS ] || exit 1
[ -f $HA_CONF ] || exit 1

. $LOG_FUNCS
. $DR_CONF
. $DB_REPL

vlog3 "Disabling status page..."
$VPSTATUS DISABLE

vlog3 "Rotating DB Snapshot..."
$ROTATE_SNAPSHOT

vlog3 "Stopping VidyoManager..."
/opt/vidyo/StopVC2.sh

vlog3 "Restarting Web Server..."
/etc/init.d/apache2 restart


$BLOCK_TOMCATNP start


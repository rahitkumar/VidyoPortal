#!/bin/bash
#############################################################################
# Filename: monitor_replication.sh
# Description: Monitor and update the state of the mysql replication and 
#              rsync.
# NOTE: Run this on STANDBY node only...
#
# Modification History:
# 10-26-2015  Initial coding (Eric)
#############################################################################

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV

[ -z "$1" ] && exit 1

[ ! -f $DB_REPL ] && exit 1

. $DB_REPL

LAST_STATE_FILE=$DB_REPL_STATUS_DIR/.last_state

[ -f $LOG_FUNCS ] && . $LOG_FUNCS

[ -f $LAST_STATE_FILE ] && LAST_STATE=$(cat $LAST_STATE_FILE)

if [ $DB_REPL_STATUS_DIR/.last_state ]; then
   [ "$LAST_STATE" = "$1" ] && exit 0
fi

rm -f $DB_REPL_STATUS_DIR/*
echo $1 > $LAST_STATE_FILE
touch ${DB_REPL_STATUS_DIR}/$1
vlog3 "Replication State: $1"

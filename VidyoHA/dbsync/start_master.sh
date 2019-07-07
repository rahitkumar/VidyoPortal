#!/bin/bash
###########################################################################
# Filename: start_master.sh
# Description: This script is will be used by a ACTIVE node to create a DB
#              snapshot.
###########################################################################


DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV


## NOTE: This should be executed on the ACTIVE nodes only!!!

[ ! -f $DB_REPL ] && exit 1

. $DB_REPL

[ -f $LOG_FUNCS ] && . $LOG_FUNCS


vlog3 "About to create DB snapshot..."
/opt/vidyo/ha/dbsync/create_master_snapshot.sh > /dev/null 2>&1
if [ $? -ne 0 ]; then
   vlog3 "Failed to create a new DB snapshot.."
   $SET_STATE MASTER_FAILED
   exit 1
fi

$SET_STATE DB_BACKUP_OK

exit 0

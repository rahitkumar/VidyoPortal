#!/bin/bash
###################################################################################
# Description: This script will be executed the drmgr when the state is PRE_ACTIVE.
# Following procedure will be performed by this script.
# 1. Clear the authorized_keys of dbsync and root user
# 2. Stop DB replication if running.
# 3. Stop FS replication if running.
# 4. Restart SSH (maybe ???)
# 5. Clear all the data in /opt/vidyo/data/dr 
# 6. Create DB snapshot.
###################################################################################

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV


[ -f $LOG_FUNCS ] && . $LOG_FUNCS
[ -f $DR_FUNCS ] && . $DR_FUNCS

if state_ACTIVE; then
   vlog3 "Error! This script will run on standby node only..."
   exit 1
fi

#vlog3 "Generating keys..."
#$GEN_KEYS
#vlog3 "Generating keys...Done"

$INIT_DR_DATA

vlog3 "Stopping VidyoManager..."
/opt/vidyo/StopVC2.sh

vlog3 "Stopping replication mgr..."
$REPL_MGR stop
sleep 3
vlog3 "Starting replication mgr..."
$REPL_MGR start

vlog3 "exit code: $?"
exit 0

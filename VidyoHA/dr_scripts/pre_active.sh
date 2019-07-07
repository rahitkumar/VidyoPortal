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

vlog3 "Purging lock file..."
rm -f $LOCKFILE

vlog3 "Purging authorized keys..."

if [ -f $DBSYNC_AUTH ]; then
   vlog3 "Purging dbsync's authorized keys..."
   > $DBSYNC_AUTH
else
   vlog3 "Warning! dbsync's authorized keys not found"
fi

if [ -f $ROOT_AUTH ]; then
   vlog3 "Purging root's authorized keys..."
   > $ROOT_AUTH
else
   vlog3 "Warning! root's authorized keys not found"
fi

### stop the replication...

vlog3 "Stopping slave replication..."
$STOP_SLAVE  ## this will teardown the ssh tunnel too.

$REPL_MGR stop

$INIT_DR_DATA

vlog3 "Create database snapshot..."
$CREATE_SNAPSHOT

vlog3 "Starting VidyoManager..."
/opt/vidyo/StartVC2.sh


vlog3 "Setting state to ACTIVE"
$DR_ADMIN --event=11
RC=$?
RC=0
vlog3 "RC=$RC"
exit $RC

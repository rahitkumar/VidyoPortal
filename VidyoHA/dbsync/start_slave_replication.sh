#!/bin/bash
###########################################################################
# Filename: start_slave_replication.sh
# Description: This script is will be used by a STANDBY node to start mysql 
#              replication. 
#
# Note: The SSH tunnel must be established before mysql replication starts.
#       MySQL replication will use the SSH tunnel to secure the data 
#       being replication.  
#
###########################################################################


DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV


## NOTE: This should be executed on the STANDBY nodes only!!!
[ ! -f $DB_REPL ] && exit 1

. $DB_REPL
. $DR_FUNC

[ -f $LOG_FUNCS ] && . $LOG_FUNCS

if ! state_STANDBY; then
   vlog3 "This node is not in STANDBY state"
   exit 1
fi

## start rsync'g the files that we are interested
/opt/vidyo/ha/dbsync/rSync.sh 
if [ $? -ne 0 ]; then
   vlog3 "failed to rsync files.  Replication will not start!"
   exit 1
fi

if [ "$1" = "FORCE_DOWNLOAD" ]; then
   vlog3 "FORCE_DOWNLOAD..."
   [ -f $DB_SNAPSHOT_COMPRESS ] && rm -f $DB_SNAPSHOT_COMPRESS
   [ -f $DB_SNAPSHOT_CSUM ] && rm -f $DB_SNAPSHOT_CSUM
   FORCE=YES
fi

SNAPSHOT_TIME=0
LAST_TIME_SYNC_OK=0
if [ -f $DB_SNAPSHOT_COMPRESS ]; then
   SNAPSHOT_TIME=$(stat --printf=%Y $DB_SNAPSHOT_COMPRESS)
else
   FORCE=YES
   rm -f $DB_REPL_LAST_OK
fi

if [ -f $DB_REPL_LAST_OK ]; then
   LAST_TIME_SYNC_OK=$(stat --printf=%Y $DB_REPL_LAST_OK)
fi

vlog3 "Snapshot time: $SNAPSHOT_TIME, Last Sync Time: $LAST_TIME_SYNC_OK"

if [ $SNAPSHOT_TIME -gt $LAST_TIME_SYNC_OK -o "$FORCE" = YES ]; then

   TEMP_CSUM=
   [ -f $DB_SNAPSHOT_CSUM ] && TEMP_CSUM=$(cat $DB_SNAPSHOT_CSUM)

   ### scp the db snapshot from the ACTIVE node(MASTER DB).
   ### use $DBSYNC_USER to scp the file from the peer so that it won't prompt for
   ### a password.
   vlog3 "Copying DB snapshot from the MASTER DB..."
   /opt/vidyo/ha/dbsync/copy_db_snapshot.sh
   if [ $? -ne 0 ]; then
      vlog3 "failed to get $DB_SNAPSHOT_COMPRESS !!!" 
      exit 1
   fi

   [ -f $DB_SNAPSHOT_CSUM ] && LOCAL_CSUM=$(cat $DB_SNAPSHOT_CSUM)


   if [ "$TEMP_CSUM" = "$LOCAL_CSUM" -o "$FORCE" != YES ]; then
      vlog3 "No changes from the previous snaphot. Skipping full DB import"
   else
      vlog3 "updating master info..."
      ## stop the replication before importing the DB
      ## replication transport will happen on SSH tunnel
      mysql $MYSQL_OPTS << EOF
STOP SLAVE;

CHANGE MASTER TO MASTER_PASSWORD="$DB_REPL_PASS";
CHANGE MASTER TO MASTER_USER="$DB_REPL_USER";
CHANGE MASTER TO MASTER_HOST="127.0.0.1";
CHANGE MASTER TO MASTER_PORT=$SSH_TUNNEL_LOCAL_PORT;
CHANGE MASTER TO MASTER_CONNECT_RETRY=3;
CHANGE MASTER TO MASTER_RETRY_COUNT=5;
SET GLOBAL SLAVE_NET_TIMEOUT=$SLAVE_NET_TIMEOUT

EOF
      vlog3 "importing DB Snapshot..."
      zcat  $DB_SNAPSHOT_COMPRESS | mysql $MYSQL_OPTS
   fi 
else
   vlog3 "DB Snapshot is older than the last time replication is running. Continuing replication..."
fi

vlog3 "start slave..."
mysqladmin $MYSQL_OPTS START-SLAVE > /dev/null 2>&1

exit $?

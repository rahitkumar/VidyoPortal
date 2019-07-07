#!/bin/bash 
###########################################################################
# Filename: restart_slave_replication.sh
# Description: This script is will be used by a STANDBY node to restart a 
#              new mysql replication.  This will reimport the DB snapshot.
#              Use this script only when mysql-replication cannot resume 
#              do to any error.
#
# Note: The SSH tunnel must be established before mysql replication starts.
#       MySQL replication will use the SSH tunnel to secure the data 
#       being replication.  
#
###########################################################################

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"
DBA=/opt/vidyo/etc/db/access.conf

SNAPSHOT_MAX_AGE=3600   ## must be configurable by user

[ -f $LOG_FUNCS ] || exit 1

## NOTE: This should be executed on the STANDBY nodes only!!!
[ -f $DB_REPL ] || exit 1

. $DB_REPL
. $LOG_FUNCS

## Node MUST be STANDBY

SHA_DBA=$(sha1sum $DBA | awk '{print $1}')

vlog3 "Start File System Sync"
## sync the file system first including DB snapshot
/opt/vidyo/ha/bin/rsyncfiles.sh
if [ $? -eq 0 ]; then
   vlog3 "File System Sync Successful..."
else
   vlog3 "Warning! File System Sync Failed!!!"
fi

NEW_SHA_DBA=$(sha1sum $DBA | awk '{print $1}')

if [ "$SHA_DBA" != "$NEW_SHA_DBA" ]; then
   /opt/vidyo/ha/hssync/update_credentials.sh
fi

if [ -f $DB_SNAPSHOT_COMPRESS ]; then
   SNAPSHOT_TIMESTAMP=$(stat --printf="%Y" $DB_SNAPSHOT_COMPRESS)
   SNAPSHOT_TIME=$(stat --printf="%y" $DB_SNAPSHOT_COMPRESS)
   NOW=$(date +%s)
   ((AGE=NOW-SNAPSHOT_TIMESTAMP))
   SNAPSHOT_CHECKSUM=$(sha1sum $DB_SNAPSHOT_COMPRESS | awk '{print $1}')
else
   vlog3 "Warning!!! No available DB SNAPSHOT found..."
   exit 5
fi

vlog3 "SNAPSHOT_TIMESTAMP: $SNAPSHOT_TIME, AGE: $AGE second(s), CHECKSUM: $SNAPSHOT_CHECKSUM"

if [ $AGE -gt $SNAPSHOT_MAX_AGE ]; then
   vlog3 "Warning!!! DB SNAPSHOT is old, request to create a new DB Snapshot to ACTIVE node."
   exit 10
fi

   mysql $MYSQL_OPTS << EOF
STOP SLAVE;
RESET SLAVE ALL;

CHANGE MASTER TO MASTER_HOST="127.0.0.1";
CHANGE MASTER TO MASTER_PORT=$SSH_TUNNEL_LOCAL_PORT;
CHANGE MASTER TO MASTER_CONNECT_RETRY=3;
CHANGE MASTER TO MASTER_RETRY_COUNT=5;
SET GLOBAL SLAVE_NET_TIMEOUT=$SLAVE_NET_TIMEOUT;
EOF


## 12345 - any positive number will signal the active node that DBsync in progress
/opt/vidyo/ha/bin/cip_admin -r 12345  
vlog3 "importing DB Snapshot..."
zcat  $DB_SNAPSHOT_COMPRESS | mysql $MYSQL_OPTS

vlog3 "start slave..."
   mysql $MYSQL_OPTS << EOF
START SLAVE USER='$DB_REPL_USER' PASSWORD="$DB_REPL_PASS";
EOF

exit $?

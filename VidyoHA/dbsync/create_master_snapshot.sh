#!/bin/bash
### Make sure that we are running this on ACTIVE node only.

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV


[ ! -f $DB_REPL ] && exit 1

. $DB_REPL

[ -f $LOG_FUNCS ] && . $LOG_FUNCS

mkdir -p $DB_DATA_REPL $DB_REPL_STATUS_DIR

vlog3 "Creating DB Snapshot..."
## use mysqldump to create snapshot.. append the MASTER INFO to the dump file  
#mysqldump --databases information_schema mysql portal2  --master-data > $TEMP_DB_SNAPSHOT
#mysqldump --all-databases --master-data > $TEMP_DB_SNAPSHOT
mysqldump $MYSQL_OPTS --databases portal2 mysql --master-data > $TEMP_DB_SNAPSHOT

#compress the snapshot file
gzip --force --best $TEMP_DB_SNAPSHOT

#save the checksum
sha1sum $TEMP_DB_SNAPSHOT_COMPRESS | awk '{print $1}' > $TEMP_DB_SNAPSHOT_CSUM
mv -f $TEMP_DB_SNAPSHOT_COMPRESS $DB_SNAPSHOT_COMPRESS
mv -f $TEMP_DB_SNAPSHOT_CSUM $DB_SNAPSHOT_CSUM

vlog3 "Finished creating DB Snapshot..."

vlog3 "Purging old binary logs"
mysql $MYSQL_OPTS << EOF
PURGE BINARY LOGS BEFORE DATE_SUB(NOW(), INTERVAL 2 DAY );
EOF
vlog3 "Done"

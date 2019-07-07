#!/bin/bash
### Make =sure that we are running this on ACTIVE node only.

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
LOCK_FILE=/opt/vidyo/temp/root/dbrepl.lock

exit_handler()
{
   rm -f $LOCK_FILE
   return 
}

. $DB_REPL
[ -f $LOG_FUNCS ] && . $LOG_FUNCS

if [ -f $LOCK_FILE ]; then
   NOW=$(date +%s)
   FILE_TIME=$(stat --print=%Y $LOCK_FILE)
   ((FILE_AGE=NOW-FILE_TIME))
   if [ $FILE_AGE -lt 120 ]; then  ## 2 mins
      vlog3 "Warning!!! Another instance of Create DB Snapshot is running."
      exit 1
   else
      vlog3 "Warning!!! Previous instance of Create DB snapshot did not complete properly."
   fi
fi

trap exit_handler EXIT SIGINT SIGTERM

touch $LOCK_FILE

NOW=$(date +%s)
if [ -f $DB_SNAPSHOT_COMPRESS ]; then
   FILE_TIME=$(stat --print=%Y $DB_SNAPSHOT_COMPRESS)
   ((FILE_AGE=NOW-FILE_TIME))
   if [ $FILE_AGE -lt 15 ]; then
      vlog3 "DB Snapshot created $FILE_AGE sec(s) ago."
      exit 1
   fi
fi

## move the previous DB snapshot so that STANDBY node will get the latest
## DB snapshot.
/opt/vidyo/ha/hssync/rotate_db_snapshot.sh

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
SS_CHECKSUM=$(cat $DB_SNAPSHOT_CSUM)

vlog3 "Finished creating DB Snapshot..., DB Checksum [$SS_CHECKSUM]"

vlog3 "Purging old binary logs"
mysql $MYSQL_OPTS << EOF
PURGE BINARY LOGS BEFORE DATE_SUB(NOW(), INTERVAL 2 DAY );
EOF

#update clusterinfo DB Snaphot time
/opt/vidyo/ha/bin/cip_admin --command=C

vlog3 "Done"


#!/bin/bash -e
########################################################################
# Filename: fix_db_schema.sh
# Description: The the portal2 database for any discrepancy and fix it.
# Note: This script will stop apache2 and VM/VR/VP
# 
# TODO: what to do if there are more columns on a table before the fix ???
########################################################################

logme()
{
   logger -t "$(basename $0)" "$*"
   echo "$(date) $(basename $0): $*"
}

### stop apache, so we dont get heartbeat message or new components

PATH=$PATH:/usr/local/mysql/bin
ARCHIVE_DIR=/opt/vidyo/archives/database/
PORTAL2_BACKUP=$ARCHIVE_DIR/portal2db.sql

mkdir -p $ARCHIVE_DIR

logme "Checking discrepancy on the portal2 database..."

if /opt/vidyo/bin/compare_create_update_db.sh; then
   logme "No discrepancy found..."
   exit 
fi

logme "Warning!!! Database discrepancy found..."

logme "Stopping apache server..."
/etc/init.d/apache2 stop

logme "Stopping VM/VR/VidyoProxy..."
/opt/vidyo/StopVC2.sh

logme "Creating database backup..."
mysqldump --no-create-db --no-create-info --complete-insert portal2 > $PORTAL2_BACKUP

logme "Re-creating portal2 database..."
## let's make sure we remove portal2. If portal2 directory is not empty the DROP DATABASE command will fail 
mysql --force -e "DROP DATABASE portal2" || true
rm -rf /var/lib/mysql/portal2 > /dev/null 2>&1

/opt/vidyo/bin/CreatePortal20Db.sh

mysql -Nse 'show tables' portal2 |  while read table; do
  mysql portal2 --force -e "truncate table $table"
done

logme "Restoring previous data to portal2 database..."
mysql --force portal2 < $PORTAL2_BACKUP > /opt/vidyo/logs/fix_db_schema.log 2>&1

[ -f ${PORTAL2_BACKUP}.gz ] && mv -f ${PORTAL2_BACKUP}.gz ${PORTAL2_BACKUP}.gz.1
gzip $PORTAL2_BACKUP


#!/bin/bash
############################################################
# Description: This script will be executed from the cron.
#              This will create a full database snapshot on
#              a STANDBY node/site. Will keep a copy of the
#              last 5 days. 
# Note: The latest DB snapshot on each day will be kept but
#       the current day will contain hourly backup.
############################################################


#determine if the node/site is active.

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
LOGGER=/opt/vidyo/bin/vidyologger.sh
DAILY_BACKUP_DIR=/opt/vidyo/data/portal/dbbackup
TODAY=${DAILY_BACKUP_DIR}/0
NEW_BACKUP=$DAILY_BACKUP_DIR/0/db_backup_$(date +%m%d%Y%H%M).sql

[ -f $HA_CONF ] || exit 1

. $HA_CONF
. $LOGGER

mkdir -p $DAILY_BACKUP_DIR/{0,1,2,3,4,5}

if [ "$VIDYO_HA" != ENABLED ]; then
   exit 1
fi

if [ -f $MAINT_FLAG ];then
   vlog3 "Maintenance mode detected. Skipping Database Backup."
   exit 0
fi

if [ "$HA_MODE" = DR ]; then
   STATE=$(/opt/vidyo/ha/dr/get_state.sh)
else
   MyRole=
   ADMIN=/opt/vidyo/ha/bin/cip_admin
   eval $($ADMIN --command=P | grep -E "MyRole|PeerStatus|MyState")
   if [ "$MyRole" = ACTIVE ]; then
      STATE=ACTIVE
   else
      STATE=STANDBY
   fi 
fi

if [ "$STATE" = ACTIVE ]; then
   vlog3 "This site/node is active. Skipping auto database backup"
   exit 1
fi

## get the timestamp of the latest file on TODAY directory
LATEST_FILE=$(ls -1tr $TODAY | tail -1)

vlog3 "*** Creating SCHEDULED Database Snapshot ***"
mysqldump $MYSQL_OPTS --databases portal2 mysql  > $NEW_BACKUP 

if [ -z "$LATEST_FILE" ]; then
   ## no files to rotate
   exit
fi

LATEST_FILE_TODAY=${TODAY}/$LATEST_FILE
YYMMDD=$(date +%Y%m%d)
LATEST_FILE_YYMMDD=$(stat --format="%y" $LATEST_FILE_TODAY | awk '{print $1}' | tr -cd [:digit:])

if [ $YYMMDD = $LATEST_FILE_YYMMDD ]; then
   ## no need to rotate yet
   exit
fi

vlog3 "Rotating SCHEDULED database backups..."
## rotate
COUNT=5
while [ $COUNT -gt 1 ]; do
   ((X=COUNT-1))
   rm -f $DAILY_BACKUP_DIR/$COUNT/*
   mv -f $DAILY_BACKUP_DIR/$X/* $DAILY_BACKUP_DIR/$COUNT > /dev/null 2>&1
   ((COUNT--))
done
mv -f $LATEST_FILE_TODAY $DAILY_BACKUP_DIR/1 > /dev/null 2>&1
mv -f $NEW_BACKUP ${DAILY_BACKUP_DIR}/$(basename $NEW_BACKUP) > /dev/null 2>&1
rm -f $DAILY_BACKUP_DIR/0/*
mv -f ${DAILY_BACKUP_DIR}/$(basename $NEW_BACKUP) $NEW_BACKUP > /dev/null 2>&1

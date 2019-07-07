#!/bin/bash 
#############################################################################
# Filename: dr_rsync.sh
# Description: Sync all the files listed on $FILES_TO_SYNC to the $TARGET
#              machine.
#
# NOTE: Run this on SLAVE node only...
# TODO: Identify what to do if ACTIVE site goes down while rsync is running
#############################################################################

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/games:/usr/local/mysql/bin

DR_CONF=/opt/vidyo/conf/dr/dr.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
FILES_TO_SYNC=/opt/vidyo/ha/bin/syncfile.fqdn
FILES_TO_SYNC_UPLOAD=/opt/vidyo/ha/bin/syncfile.upload
FILES_TO_EXCLUDE=/opt/vidyo/ha/bin/exclude
LOGFILE=/opt/vidyo/temp/root/rsync.log
LOGFILE2=/opt/vidyo/temp/root/rsync2.log
RSYNC_STAT=${LOGFILE}.stat
RSYNC_STAT2=${LOGFILE2}.stat
#_VERBOSE="--verbose --progress --stats --human-readable"
_VERBOSE="--stats"
## comment out the CHECKSUM if it is taking too much cpu...
#CHECKSUM=--checksum

. /opt/vidyo/adm/myfunctions.sh

> $LOGFILE
> $LOGFILE2

GET_SSH_PORT=/opt/vidyo/ha/bin/get_ssh_port.sh
eval $($GET_SSH_PORT)
SSH_OPT="-o Port=$SSH_PORT -o ConnectTimeout=5 -o PasswordAuthentication=no -o StrictHostKeyChecking=no"

LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh

[ -f $DR_CONF ] || exit 1
[ -f $LOG_FUNCS ] || exit 1
[ -f $HA_CONF ] || exit 1
[ -f $GET_SSH_PORT ] || exit 1

. $DR_CONF
. $LOG_FUNCS
. $HA_CONF


## now get the IP address that will be used by ACTIVE site for ssh.
. $ACTIVE_NODE_CONF
ACTIVE_IP=$(cat $ACTIVE_IP_FILE)
if [ "$ACTIVE_IP" = "$DATASYNCIP" ]; then
   vlog3 "DataSyncIp is same as ACTIVE's site Public IP"
else
   vlog3 "DataSyncIp is NOT same as ACTIVE's site Public IP"
fi

if [ -n "$DATASYNCIP" ]; then
   TARGET_IP=$DATASYNCIP
else
   TARGET_IP=$ACTIVE_IP
fi


#vlog3 "Syncing File Set 1 from $TARGET"
START=$(date +%s)
/usr/bin/rsync $_VERBOSE --recursive --times --perms --owner --group \
      --links --compress --checksum \
      --stats \
      --keep-dirlinks \
      --delete --delete-after \
      --rsh="/usr/bin/ssh -i /home/dbsync/.ssh/id_rsa -l dbsync $SSH_OPT" \
      --rsync-path='sudo /usr/bin/rsync' \
      --log-file=$LOGFILE \
      --files-from=$FILES_TO_SYNC \
      --exclude-from=$FILES_TO_EXCLUDE \
      $TARGET_IP:/ / > $RSYNC_STAT 2>&1
RC=$?

END=$(date +%s)
((ELAPSED=END-START))
if [ $RC -eq 0 ]; then
   BYTES_TX=$(grep "Total transferred file size:" $RSYNC_STAT  | awk '{ print $5}' | tail -1)
   DELETED_FILES=$(grep "Number of deleted files:"  $RSYNC_STAT | awk '{ print $5}' | tail -1) 
   CREATED_FILES=$(grep "Number of created files:"  $RSYNC_STAT | awk '{ print $5}' | tail -1)
   vlog3 "Set 1 - Bytes TX: $BYTES_TX, Created: $CREATED_FILES, Deleted: $DELETED_FILES, Result: $RC, Elapsed: $ELAPSED"
else
   vlog3 "Error! Set 1 Result:$RC"
fi

#vlog3 "Syncing File Set 2 from $TARGET"
START=$(date +%s)
/usr/bin/rsync $_VERBOSE --recursive --times --perms --owner --group \
      --links --compress \
      --stats \
      --keep-dirlinks \
      --delete --delete-after \
      --rsh="/usr/bin/ssh -i /home/dbsync/.ssh/id_rsa -l dbsync $SSH_OPT" \
      --rsync-path='sudo /usr/bin/rsync' \
      --log-file=$LOGFILE2 \
      --files-from=$FILES_TO_SYNC_UPLOAD \
      --exclude-from=$FILES_TO_EXCLUDE \
      $TARGET_IP:/opt/vidyo/portal2/ /opt/vidyo/portal2/ > $RSYNC_STAT2 2>&1
RC=$?


END=$(date +%s)
((ELAPSED=END-START))
if [ $RC -eq 0 ]; then
   BYTES_TX=$(grep "Total transferred file size:" $RSYNC_STAT2  | awk '{ print $5}' | tail -1)
   DELETED_FILES=$(grep "Number of deleted files:"  $RSYNC_STAT2 | awk '{ print $5}' | tail -1) 
   CREATED_FILES=$(grep "Number of created files:"  $RSYNC_STAT2 | awk '{ print $5}' | tail -1)
   vlog3 "Set 2 - Bytes TX: $BYTES_TX, Created: $CREATED_FILES, Deleted: $DELETED_FILES, Result: $RC, Elapsed: $ELAPSED"
else
   vlog3 "Error! Set 2 Result:$RC"
fi


exit $RC

#!/bin/bash
#############################################################################
# Filename: rSync.sh
# Description: Sync all the files listed on $FILES_TO_SYNC to the $TARGET
#              machine.
#
# NOTE: Run this on SLAVE node only...
# Modification History:
# 02-27-2012  Initial coding (Eric)
#
# TODO: Identify what to do if MASTER becomes slave while rSync is running!!!
#############################################################################

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV

[ -f $LOG_FUNCS ] && . $LOG_FUNCS

CLUSTER_IP=$(/opt/vidyo/ha/dr/reverse_dns.sh)

TARGET=$CLUSTER_IP
#_VERBOSE="--verbose --progress --stats --human-readable"
_VERBOSE="--stats"
## comment out the CHECKSUM if it is taking too much cpu...
CHECKSUM=--checksum

> $RSYNC_LOG

GET_SSH_PORT=/opt/vidyo/ha/bin/get_ssh_port.sh
eval $($GET_SSH_PORT)
SSH_OPT="-o Port=$SSH_PORT -o ConnectTimeout=5 -o PasswordAuthentication=no -o StrictHostKeyChecking=no"

vlog3 "Syncing File Set 1"
vlog3 "rsync $_VERBOSE --recursive --times --perms --owner --group \
      --links --compress $CHECKSUM \
      --stats \
      --keep-dirlinks \
      --delete --delete-after \
      --rsh="/usr/bin/ssh $SSH_OPT" \
      --log-file=$RSYNC_LOG \
      --files-from=$FILES_TO_SYNC \
      --exclude-from=$FILES_TO_EXCLUDE \
      $TARGET:/ /"

rsync $_VERBOSE --recursive --times --perms --owner --group \
      --links --compress $CHECKSUM \
      --stats \
      --keep-dirlinks \
      --delete --delete-after \
      --rsh="/usr/bin/ssh $SSH_OPT" \
      --log-file=$RSYNC_LOG \
      --files-from=$FILES_TO_SYNC \
      --exclude-from=$FILES_TO_EXCLUDE \
      $TARGET:/ / > $RSYNC_STAT 2>&1
RC=$?

if [ $RC -eq 0 ]; then
   BYTES_TX=$(grep "Total transferred file size:" $RSYNC_STAT | sed 's/^.*://g' | awk '{ print $1}')
   DELETED_FILES=$(grep "Number of deleted files:"  $RSYNC_STAT | sed 's/^.*://g' | awk '{ print $1}')
   CREATED_FILES=$(grep "Number of created files:"  $RSYNC_STAT | sed 's/^.*://g' | awk '{ print $1}')
   vlog3 "Bytes TX: $BYTES_TX, Created: $CREATED_FILES, Deleted: $DELETED_FILES"
else
   exit $RC
fi

vlog3 "Syncing File Set 2"
rsync $_VERBOSE --recursive --times --perms --owner --group \
      --links --compress \
      --stats \
      --delete --delete-after \
      --rsh="/usr/bin/ssh $SSH_OPT" \
      --log-file=$RSYNC_LOG \
      $TARGET:/opt/vidyo/portal2/{upload,superupload} /opt/vidyo/portal2/ > $RSYNC_STAT 2>&1 
RC=$?

if [ $RC -eq 0 ]; then
   UPLOAD_BYTES_TX=$(grep "Total transferred file size:" $RSYNC_STAT | sed 's/^.*://g' | awk '{ print $1}')
   UPLOAD_DELETED_FILES=$(grep "Number of deleted files:"  $RSYNC_STAT | sed 's/^.*://g' | awk '{ print $1}')
   UPLOAD_CREATED_FILES=$(grep "Number of created files:"  $RSYNC_STAT | sed 's/^.*://g' | awk '{ print $1}')
   vlog3 "Upload Bytes TX: $UPLOAD_BYTES_TX, Upload Created: $UPLOAD_CREATED_FILES, Upload Deleted: $UPLOAD_DELETED_FILES"
else
   exit $RC
fi


exit 0

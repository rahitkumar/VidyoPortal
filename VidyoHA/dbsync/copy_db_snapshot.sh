#!/bin/bash
#############################################################################
# Filename: copy_db_snapshot.sh
# Description: Copy the DB snapshot from the MASTER DB. Use scp or rsync
#
# NOTE: Run this on STANDBY node only...
#
# Modification History:
# 04-03-2012  Initial coding (Eric)
#############################################################################


DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV

[ ! -f $DB_REPL ] && exit 1

. $DB_REPL

eval $($GET_SSH_PORT)
SSH_OPT="-o Port=$SSH_PORT -o ConnectTimeout=5 -o PasswordAuthentication=no -o StrictHostKeyChecking=no"


LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
[ -f $LOG_FUNCS ] && . $LOG_FUNCS

CLUSTER_IP=$(/opt/vidyo/ha/dr/reverse_dns.sh)

mkdir -p $DB_DATA_REPL $DB_REPL_STATUS_DIR

CURDIR=$(pwd)
eval $(echo cd ~$DBSYNC_USER)
NEWDIR=$(pwd)
cd $CURDIR

SKIP_TRANSFER=no


REMOTE_CSUM=$(ssh $SSH_OPT $CLUSTER_IP "cat $DB_SNAPSHOT_CSUM")
LOCAL_CSUM=
[ -f $DB_SNAPSHOT_CSUM ] && LOCAL_CSUM=$(cat $DB_SNAPSHOT_CSUM)

#echo $REMOTE_CSUM
#echo $LOCAL_CSUM

if [ "$REMOTE_CSUM" = "UNKNOWN" ]; then
   echo "Remote server is currently generating a new snapshot... try again!"
   exit 1
fi

if [ "$LOCAL_CSUM" = "$REMOTE_CSUM" ]; then
   echo "Remote checksum did not change... Skip file transfer"
   exit 0
fi


if [ "$SKIP_TRANSFER" != "yes" ]; then
   vlog3 "transferring DB snapshot from MASTER..."
   scp $SSH_OPT $CLUSTER_IP:$DB_SNAPSHOT_COMPRESS $TEMP_DB_SNAPSHOT_COMPRESS
   mv $TEMP_DB_SNAPSHOT_COMPRESS $DB_SNAPSHOT_COMPRESS
   sha1sum $DB_SNAPSHOT_COMPRESS | awk '{print $1}' > $DB_SNAPSHOT_CSUM 
fi

exit $?

#!/bin/bash
#####################################################################
# Description: Package and encrypt the dbsync and root public key
#
#####################################################################
DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV


[ -f $LOG_FUNCS ] && . $LOG_FUNCS

##Note: The AUTHORZED_KEYS file will be used by cluster_service.sh
## to determine whether it needs to ask the STANDBY node to send ssh 
## public keys or not...
PUBLIC_KEYS="$1"
REMOTE_CONF="$2"

if [ -f "$REMOTE_CONF" ]; then
   . $REMOTE_CONF
else
   vlog3 "Remote config [$REMOTE_CONF] not found!"
   exit 1
fi

RC=0
read KEYS
TMPDIR=$(mktemp -d --tmpdir=/root/)
echo $KEYS | tr '_' '\n' | openssl enc -d -aes-256-cbc -a -md sha1 -pass file:/opt/vidyo/vm/VidyoManagerkey.pem | tar -C $TMPDIR -xzf -

DBSYNC_PKEY=$(cat $TMPDIR/dbsync.pub | awk '{print $2}')
ROOT_PKEY=$(cat $TMPDIR/root.pub | awk '{print $2}')
if ! grep -q $DBSYNC_PKEY $DBSYNC_AUTHKEY; then
   vlog3 sed -i "/__dbsync@${PUBLICIP}__/d" $DBSYNC_AUTHKEY
   sed -i "/__dbsync@${PUBLICIP}__/d" $DBSYNC_AUTHKEY
   vlog3 "Appending keys...(dbsync)"
   cat $TMPDIR/dbsync.pub >> $DBSYNC_AUTHKEY
   chown dbsync:dbsync $DBSYNC_AUTHKEY 
else
   RC=1
fi

if ! grep -q $ROOT_PKEY $ROOT_AUTHKEY; then
   vlog3 "Appending keys...(root)"
   vlog3 sed -i "/__root@${PUBLICIP}__/d" $ROOT_AUTHKEY
   sed -i "/__root@${PUBLICIP}__/d" $ROOT_AUTHKEY
   vlog3 "Appending keys...(dbsync)"
   cat $TMPDIR/root.pub >> $ROOT_AUTHKEY
else
   RC=1
fi

vlog3 "PUBLIC_KEYS=$PUBLIC_KEYS, RC=[$RC],TMPDIR=[$TMPDIR]"
if [ -n "$PUBLIC_KEYS" -a $RC -ne 1 ]; then
   vlog3 "Updating authorized keys of dbsync"
   cat $TMPDIR/dbsync.pub > ${PUBLIC_KEYS}.dbsync
   vlog3 "Updating authorized keys of root"
   cat $TMPDIR/root.pub > ${PUBLIC_KEYS}.root
fi

rm -rf $TMPDIR
exit $RC

#!/bin/bash
#####################################################################
# Description: Package and encrypt the dbsync and root public key
#
#####################################################################

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV



set -e
FILE=$1

[ -f "$FILE" ] || exit 1

echo ROOT pubkey

DBKEY=$(sed -n '1p' $FILE)
RTKEY=$(sed -n '2p' $FILE)

KK=$(echo $DBKEY | tr '_' '\n' | openssl enc -aes-256-cbc -a -d -md sha1 -pass file:/opt/vidyo/vm/VidyoManagerkey.pem | gunzip)
PK=$(echo $KK | awk '{print $2}')
[ -f $DBSYNC_AUTH ] || touch $DBSYNC_AUTH
if ! grep -q $PK $DBSYNC_AUTH; then 
   echo $KK >> $DBSYNC_AUTH
fi

KK=$(echo $RTKEY | tr '_' '\n' | openssl enc -aes-256-cbc -a -d -md sha1 -pass file:/opt/vidyo/vm/VidyoManagerkey.pem | gunzip)
PK=$(echo $KK | awk '{print $2}')
[ -f $ROOT_AUTH ] || touch $ROOT_AUTH
if ! grep -q $PK $ROOT_AUTH; then
   echo $KK >> $ROOT_AUTH
fi

echo OK
exit 0

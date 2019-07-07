#!/bin/bash
#####################################################################
# Description: Package and encrypt the dbsync and root public key
#
#####################################################################

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV


if [ -f $ROOT_PUB -a -f $DBSYNC_PUB ]; then
   TMPDIR=$(mktemp -d --tmpdir=/root/)

   cp $DBSYNC_PUB $TMPDIR/dbsync.pub
   cp $ROOT_PUB $TMPDIR/root.pub
   
   CURDIR=$(pwd)
   cd $TMPDIR
   tar --to-stdout -cz * | openssl enc -aes-256-cbc -a -md sha1 -pass file:/opt/vidyo/vm/VidyoManagerkey.pem  | tr '\n' '_'
   cd $CURDIR

   rm -rf $TMPDIR
else
    exit 1
fi


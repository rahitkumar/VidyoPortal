#!/bin/bash
#####################################################################
# Description: Package and encrypt the dbsync and root public key
#
#####################################################################

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV


if [ -f $ROOT_PUB -a -f $DBSYNC_PUB ]; then
    cat $DBSYNC_PUB | gzip | openssl enc -aes-256-cbc -a -md sha1 -pass file:/opt/vidyo/vm/VidyoManagerkey.pem | tr '\n' '_'
    echo
    cat $ROOT_PUB | gzip | openssl enc -aes-256-cbc -a -md sha1 -pass file:/opt/vidyo/vm/VidyoManagerkey.pem | tr '\n' '_' 
else
    exit 1
fi


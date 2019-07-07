#!/bin/bash

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV


[ -f $LOG_FUNCS ] && . $LOG_FUNCS

genkey()
{
   username=$1

   if [ -f $LOCAL_NODE_CONF ]; then
      . $LOCAL_NODE_CONF
   else
      vlog3 "Warning! genkey() cannot find the local configuration"
      return 1
   fi

   CURDIR=$(pwd)
   eval $(echo cd "~${username}")
   [ $? -ne 0 ] && exit 1
   mkdir -p .ssh
   ssh-keygen -q -f .ssh/id_rsa -P "" -C "__${username}@${PUBLICIP}__"
   chmod 755 .ssh
   chown -R $username .ssh
   rc=$?
   cd $CURDIR
   return $rc
}



rm -rf $DBSYNC_SSH
if genkey dbsync; then
   vlog3 "Successfully generated new keys for dbsync user..."
else
   vlog3 "Failed to generate new keys for dbsync user..."
   exit 1
fi

rm -rf $ROOT_SSH
if genkey root; then
   vlog3 "Successfully generated new keys for root user..."
else
   vlog3 "Failed to generate new keys for root user..."
fi


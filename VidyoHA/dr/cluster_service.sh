#!/bin/bash
###################################################################
# Description: The script will be executed by the the web service. 
#              This script is needed only on the ACTIVE node.
# Note: This script will not allow to run simultaneously. 
###################################################################

HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
DR_CONF=/opt/vidyo/conf/dr/dr.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
CREATE_SNAPSHOT=/opt/vidyo/ha/dr/dr_create_master_snapshot.sh
LOCKFILE=/opt/vidyo/temp/root/dr.lock
IS_NODE_ACTIVE=/opt/vidyo/ha/dr/isnodeactive.sh

#logger -t "cluster_service.sh" "$*"

[ ! -f $DR_ENV ] && exit

[ ! -f $DR_CONF ] && exit
[ ! -f $HA_CONF ] && exit

. $DR_CONF
. $HA_CONF

trap exit_handler EXIT SIGINT SIGTERM

start_time=$(date +%s)

REGISTER_DATA=${TC_TEMP_DIR}/$1

exit_handler()
{
#  local LAST_UPDATED
#  local NOW
#  local AGE

  rm -f $REGISTER_DATA
#  [ -f $LOCKFILE ] || exit
#  LAST_UPDATED=$(stat --print=%Y $LOCKFILE)
#  NOW=$(date +%s)
#  ((AGE=NOW-LAST_UPDATED))
#  vlog3 "AGE: $AGE, NOW:$NOW, LAST_UPDATED: $LAST_UPDATED"
#  if [ $AGE -gt 30 ]; then
#     rm -f $LOCKFILE
#  fi
}

[ -f $LOG_FUNCS ] && . $LOG_FUNCS

if ! $IS_NODE_ACTIVE; then
   vlog3 "ERROR! This site is not active..."
   echo Error="SITE_NOT_ACTIVE"
   exit 1
fi

#vlog3 "LOCKFILE: $LOCKFILE"

#if ! lockfile -r 1 $LOCKFILE; then
#   vlog3 "Warning! unable to lock resources..."
#   exit 1
#fi

if [ ! -f $REGISTER_DATA ]; then
   vlog3 "$REGISTER_DATA not found!"
   echo Error="RegisterData_Not_found"
   exit 1
fi


## convert the field name to upper case
#sed -i 's/\(^.*=\)/\U\1/g' $REGISTER_DATA

#Note: sed does not support non-greedy pattern matching
#use perl to do a non-greedy replace when make the key in upper case format. 
#vlog3 "before perl..."
cat $REGISTER_DATA | perl -pe 's|(^.*?=)|\U\1|' > ${REGISTER_DATA}.tmp
#vlog3 "mv -f ${REGISTER_DATA}.tmp $REGISTER_DATA"
mv -f ${REGISTER_DATA}.tmp $REGISTER_DATA > /dev/null 2>&1
. $REGISTER_DATA

#vlog3 "after perl..."

FOLDER_NAME=${PUBLICIP//[.:]/_}
REMOTE_DATA_DIR=${DATA_DIR}/${FOLDER_NAME}
REMOTE_CONF=${REMOTE_DATA_DIR}/node.conf

vlog3 "REMOTE_DATA_DIR=$REMOTE_DATA_DIR"
mkdir -p $LOCAL_DATA_DIR
mkdir -p $REMOTE_DATA_DIR
mv -f $REGISTER_DATA $REMOTE_CONF 

## parse the replication status
if [ -n "$REPLSTATUS" ]; then
   cat << EOF >> $REMOTE_CONF
REQUEST_NEW_SNAPSHOT=$(echo $REPLSTATUS|cut -d: -f1)
REPLICATION_RUNNING=$(echo $REPLSTATUS|cut -d: -f2)
REPLICATION_ERROR=$(echo $REPLSTATUS| cut -d: -f3)
SECONDS_BEHIND_MASTER=$(echo $REPLSTATUS| cut -d: -f4)
LAST_TIME_DB_INSYNC=$(echo $REPLSTATUS| cut -d: -f5)
LAST_TIME_RSYNC_OK=$(echo $REPLSTATUS| cut -d: -f6)
EOF
   

fi

chown root:root $REMOTE_CONF

. $REMOTE_CONF
if [ "$REQUEST_NEW_SNAPSHOT" = Y ]; then
   vlog3 "NodeId: $NODEID, NodeName: $DISPLAYNAME is requesting to create a new DB snapshot..."
   $CREATE_SNAPSHOT
   vlog3 "Done creating DB Snapshot for NodeId: $NODEID, NodeName: $DISPLAYNAME"
fi

DATASYNCIP=
NODEID=
NODEVERSION=
DISPLAYNAME=
PUBLICIP=
NATIVEETH0IP=
NATIVEETH1IP=


[ -f $LOCAL_NODE_CONF ] && . $LOCAL_NODE_CONF

NODEVERSION=$(cat /opt/vidyo/VC2_VERSION)

ETH0_CONF=/opt/vidyo/conf.d/eth0.conf
ETH1_CONF=/opt/vidyo/conf.d/eth1.conf
IPV4_ADDRESS=
if [ -f $ETH0_CONF ]; then
   . $ETH0_CONF
   NATIVEETH0IP=$IPV4_ADDRESS
fi

IPV4_ADDRESS=
if [ -f $ETH1_CONF ]; then
   . $ETH1_CONF
   NATIVEETH1IP=$IPV4_ADDRESS
fi


### prepare the response back to the standby node.
echo dataSyncIp=$DATASYNCIP
echo nodeId=$NODEID
echo nodeVersion=$NODEVERSION
echo displayName=$DISPLAYNAME
echo publicIp=$PUBLICIP
echo nativeEth0Ip=$NATIVEETH0IP
echo nativeEth1Ip=$NATIVEETH1IP
[ -f $DB_VERSION_INFO ] && . $DB_VERSION_INFO
echo dbVersion=$DB_VERSION

end_time=$(date +%s)
((elapsed=end_time-start_time))
vlog3 [$DISPLAYNAME] "$end_time - $start_time, Transaction Time: $elapsed"
echo "duration=$elapsed"

#rm -f $LOCKFILE 

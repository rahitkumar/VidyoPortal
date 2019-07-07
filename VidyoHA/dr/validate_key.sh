#!/bin/bash
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
REGISTER=/opt/vidyo/ha/dr/register.sh
ACTIVE_CONF=/opt/vidyo/data/dr/active/node.conf

GET_SSH_PORT=/opt/vidyo/ha/bin/get_ssh_port.sh
eval $($GET_SSH_PORT)
SSH_ID="-i /home/dbsync/.ssh/id_rsa"
SSH_OPT="$SSH_ID -o Port=$SSH_PORT -o ConnectTimeout=5 -o PasswordAuthentication=no -o StrictHostKeyChecking=no"

DBSYNC_USER=dbsync

. $LOG_FUNCS

vlog3 "validate stage 1"
if ! $REGISTER; then
   vlog3 "Warning!!! Failed to register"
   exit 100
fi

vlog3 "validate stage 2"
if [ ! -f $ACTIVE_CONF ]; then
   vlog3 "Warning!!! Unable to find ACTIVE node's configuration..."
   exit 101
fi

vlog3 "validate stage 3"
. $ACTIVE_CONF
if [ -z "$DATASYNCIP" ]; then
   vlog3 "Warning!!! DataSyncIP is not set on ACTIVE node."
   exit 1
fi

vlog3 "validate stage 4"
ssh $SSH_OPT $DBSYNC_USER@$DATASYNCIP "whoami"
RC=$?
vlog3 "validate exit code: $RC"
exit $RC

#!/bin/bash
###########################################################################
# Filename: start_slave_replication.sh
# Description: This script is will be used by a STANDBY node to start mysql 
#              replication. It will create SSH tunnel to the ACTIVE node. 
#              MySQL replication will use the SSH tunnel to secure the data 
#              being replication.  
#
# Modification History:
# 04-04-2012 - Initial Coding(Eric)
###########################################################################

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV




## NOTE: This should be executed on the STANDBY nodes only!!!

[ ! -f $DB_REPL ] && exit 1

. $DB_REPL

LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
[ -f $LOG_FUNCS ] && . $LOG_FUNCS

vlog3 "stopping slave..."
mysqladmin $MYSQL_OPTS STOP-SLAVE
if [ $? -ne 0 ]; then
   vlog3 "Warning! Failed to stop slave"
fi

## start the SSH tunnel for mysql replication
vlog3 "stopping SSH tunnel for replication..."
$SSH_TUNNEL stop

exit $?

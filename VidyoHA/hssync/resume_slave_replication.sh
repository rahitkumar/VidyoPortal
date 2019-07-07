#!/bin/bash
###########################################################################
# Filename: resume_slave_replication.sh
# Description: Resume MySQL Replication. Use restart_slave_replication when
#              STANDBY node cannot resume replication due to error.
#
# Note: The SSH tunnel must be established before mysql replication starts.
#       MySQL replication will use the SSH tunnel to secure the data 
#       being replication.  
#
###########################################################################

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

[ -f $LOG_FUNCS ] || exit 1
[ -f $DB_REPL ] || exit 2

. $LOG_FUNCS
. $DB_REPL

## Node MUST be STANDBY

mysqladmin $MYSQL_OPTS STOP-SLAVE > /dev/null 2>&1
vlog3 "Resuming Replication. START-SLAVE"
   mysql $MYSQL_OPTS << EOF
START SLAVE USER='$DB_REPL_USER' PASSWORD="$DB_REPL_PASS";
EOF


exit $?

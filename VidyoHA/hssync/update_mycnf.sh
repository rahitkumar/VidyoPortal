#!/bin/bash

MYCNF=/etc/mysql/my.cnf
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
DB_REPL_IN=/opt/vidyo/ha/in/dbrepl.conf.in
DB_CFG=/opt/vidyo/etc/db/access.conf

. /opt/vidyo/bin/vidyologger.sh


VIDYO_REPL=
. $DB_CFG
if [ -z "$VIDYO_REPL" ]; then 
   VIDYO_REPL=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 12 | head -n1)

   echo VIDYO_REPL=$VIDYO_REPL >> $DB_CFG
   sed "s/%PASSWORD%/$VIDYO_REPL/g" $DB_REPL_IN > $DB_REPL 
fi

# Generate a random server-id
S=1$(date +%S)
((M=S%2))

if [ $M -eq 0 ]; then
   ((SVRID=RANDOM%32000+1)) 
elif [ $M -eq 1 ]; then
   ((SVRID=RANDOM%32000+32001)) 
fi

vlog3 "Generating mysql configuration for replication using serverid: $SVRID"
if [ ! -f $DB_REPL ]; then
   vlog3 "Missing config file (dbrepl.conf)"
   exit 1
fi

. $DB_REPL

mkdir -p $(dirname $MYCNF)
chown mysql:mysql $(dirname $MYCNF)

if [ ! -f $MYCNF ]; then
   cat << EOF > $MYCNF
[mysqld]
server-id               = $SVRID
replicate-ignore-db     = PORTAL_BATCH
replicate-ignore-db     = portal2audit
binlog-ignore-db        = PORTAL_BATCH
binlog-ignore-db        = portal2audit
log_bin                 = /var/lib/mysql/binlog/mysql-bin
relay_log               = /var/lib/mysql/binlog/mysql-relay
expire_logs_days        = 1
max_binlog_size         = 100M
slave_net_timeout       = $SLAVE_NET_TIMEOUT
#slave_skip_errors       = 1062
binlog_format           = MIXED

EOF
fi

chmod 644 $MYCNF

mkdir -p /var/lib/mysql/binlog/
chown -R mysql:mysql /var/lib/mysql/binlog/

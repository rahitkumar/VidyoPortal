#!/bin/bash

MYCNF=/etc/mysql/my.cnf

SVRID=$(echo "$1" | tr -cd [:digit:])

if [ -z "$SVRID" ]; then
   echo "Error! Invalid argument"
   exit 1
fi

if [ ! -f /opt/vidyo/ha/cfg/dbrepl.conf ]; then
   echo "Missing config file (dbrepl.conf)"
   exit 1
fi

. /opt/vidyo/ha/cfg/dbrepl.conf

mkdir -p $(dirname $MYCNF)



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


EOF

chmod 644 $MYCNF


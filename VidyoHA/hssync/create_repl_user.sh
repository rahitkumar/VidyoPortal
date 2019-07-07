#!/bin/bash

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin

MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

DBREPL_CONF=/opt/vidyo/ha/cfg/dbrepl.conf

[ -f $DBREPL_CONF ] || exit 1

. $DBREPL_CONF

mysql $MYSQL_OPTS << EOF
GRANT REPLICATION SLAVE ON *.* TO '$DB_REPL_USER'@'localhost' IDENTIFIED BY '$DB_REPL_PASS';
QUIT
EOF

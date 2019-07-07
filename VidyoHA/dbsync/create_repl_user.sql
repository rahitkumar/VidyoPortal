#!/bin/bash

MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf

[ ! -f $DB_REPL ] && exit 1

. $DB_REPL

mysql $MYSQL_OPTS << EOF
GRANT REPLICATION SLAVE ON *.* TO '$DB_REPL_USER'@'localhost' IDENTIFIED BY '$DB_REPL_PASS';
QUIT
EOF

#!/bin/bash

PATH=$PATH:/usr/local/mysql/bin
export PATH

MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

mysqlcheck $MYSQL_OPTS -r --databases portal2audit PORTAL_BATCH 
if [ $? -eq 0 ]; then
   logger -t  "check_db" "mysqlcheck - SUCCESS"
else
   logger -t  "check_db" "mysqlcheck - FAILED"
fi


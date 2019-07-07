#!/bin/bash

MEM=0
MEM=$(free -g | grep Mem | awk '{print $2}')

if [ $MEM -le 8 ]; then
   logger -t "update_mysql_conf.sh" "8GB detected..."
   cp /opt/vidyo/conf/mysql/my.cnf.8gb  /etc/my.cnf
elif [ $MEM -le 16 ]; then
   logger -t "update_mysql_conf.sh" "16GB detected..."
   cp /opt/vidyo/conf/mysql/my.cnf.16gb  /etc/my.cnf
elif [ $MEM -le 24 ]; then
   logger -t "update_mysql_conf.sh" "24GB detected..."
   cp /opt/vidyo/conf/mysql/my.cnf.24gb  /etc/my.cnf
elif [ $MEM -le 132 ]; then
   logger -t "update_mysql_conf.sh" "> 24GB detected..."
   cp /opt/vidyo/conf/mysql/my.cnf.template  /etc/my.cnf
fi

MYCNF=/etc/my.cnf
MEM=$(grep MemTotal: /proc/meminfo | awk '{ print $2 }')
PCT=20

((PCT_MEM=(MEM*PCT*1024)/100))

CURRENT_POOL_SIZE=$(grep innodb_buffer_pool_size  $MYCNF | tr -cd [0-9])
if [ "$CURRENT_POOL_SIZE" = "$PCT_MEM" ]; then
      logger -t "update_mysql_conf.sh" "No changes on the pool size"
else
      logger -t "update_mysql_conf.sh" "Adjusting innidb_buffer_pool_size to $PCT_MEM"
fi

sed -i "s/__20_PCT_MEM__/ $PCT_MEM/g" $MYCNF


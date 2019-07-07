#!/bin/bash

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin

DBREPL_CONF=/opt/vidyo/ha/cfg/dbrepl.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh

. $LOG_FUNCS

. $DBREPL_CONF

vlog3 "Rotating DB Snapshot..."

if [ -f ${DB_SNAPSHOT_COMPRESS}.0 ]; then
   vlog3 "${DB_SNAPSHOT_COMPRESS}.0  to ${DB_SNAPSHOT_COMPRESS}.1"
   mv -f ${DB_SNAPSHOT_COMPRESS}.0 ${DB_SNAPSHOT_COMPRESS}.1
   mv -f ${DB_SNAPSHOT_CSUM}.0 ${DB_SNAPSHOT_CSUM}.1
fi

if [ -f ${DB_SNAPSHOT_COMPRESS} ]; then
   vlog3 "${DB_SNAPSHOT_COMPRESS} to ${DB_SNAPSHOT_COMPRESS}.0"
   mv -f ${DB_SNAPSHOT_COMPRESS} ${DB_SNAPSHOT_COMPRESS}.0
   mv -f ${DB_SNAPSHOT_CSUM} ${DB_SNAPSHOT_CSUM}.0
   vlog3 "DB Snapshot has been removed"
fi



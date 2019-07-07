#!/bin/bash
#########################################################################
# Description: Run sql scripts from /opt/vidyo/portal2/sql/ 
#
#########################################################################

export PATH=$PATH:/usr/local/mysql/bin
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

mysql $MYSQL_OPTS -D portal2 < /opt/vidyo/portal2/sql/sp_searchRoomIdsAndTotalCount.sql

mysql $MYSQL_OPTS -D portal2 < /opt/vidyo/portal2/sql/sp_deleteScheduledRoom.sql

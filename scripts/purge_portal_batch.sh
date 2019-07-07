#!/bin/bash

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin

MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"
PB_DIR=/opt/vidyo/logs/vidyo-portal-batch/
if [ "$1" = NOW ]; then
   FILENAME=$PB_DIR/portal_batch_$(date +%m%d%H%M).sql.gz
else
   FILENAME=$PB_DIR/portal_batch.sql.gz
fi

OLD_FILENAME=$PB_DIR/portal_batch_old.sql.gz

[ -f $FILENAME ] && mv -f $FILENAME $OLD_FILENAME && chown vpbatch:vpbatch $OLD_FILENAME

mysqldump $MYSQL_OPTS PORTAL_BATCH 2>&1 | gzip > $FILENAME
chown vpbatch:vpbatch $FILENAME

logger -t "purge_portal_batch.sh" "purging PORTAL_BATCH DB..."


PID=$(ps -ef | grep PORTAL_BATCH_JOBS | grep -v grep | awk '{print $2}')
if [ -z "$PID" ]; then
   logger -t "purge_portal_batch.sh" "PORTAL_BATCH_JOBS is not running..."
else
   kill -TERM $PID
fi
sleep 1

mysql $MYSQL_OPTS --force << EOF
USE PORTAL_BATCH;
SET FOREIGN_KEY_CHECKS=0;
TRUNCATE TABLE BATCH_JOB_EXECUTION;
TRUNCATE TABLE BATCH_JOB_EXECUTION_CONTEXT;
TRUNCATE TABLE BATCH_JOB_EXECUTION_SEQ;
TRUNCATE TABLE BATCH_JOB_INSTANCE;
TRUNCATE TABLE BATCH_JOB_SEQ;
TRUNCATE TABLE BATCH_JOB_EXECUTION_PARAMS;
TRUNCATE TABLE BATCH_STEP_EXECUTION;
TRUNCATE TABLE BATCH_STEP_EXECUTION_CONTEXT;
TRUNCATE TABLE BATCH_STEP_EXECUTION_SEQ;
SET FOREIGN_KEY_CHECKS=1;

INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) SELECT *  From (SELECT 0 AS ID, '0' AS UNIQUE_KEY) AS tmp WHERE NOT EXISTS(SELECT *  From BATCH_STEP_EXECUTION_SEQ);
INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) SELECT *  From (SELECT 0 AS ID, '0' AS UNIQUE_KEY) AS tmp WHERE NOT EXISTS(SELECT *  From BATCH_JOB_EXECUTION_SEQ);
INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) SELECT *  From (SELECT 0 AS ID, '0' AS UNIQUE_KEY) AS tmp WHERE NOT EXISTS(SELECT *  From BATCH_JOB_SEQ);

QUIT
EOF

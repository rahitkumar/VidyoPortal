#!/bin/bash

MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

DIRNAME=/opt/vidyo/temp/tomcat/$1
USED_SEATS=$2
NUM_LINES=$3
USED_INSTALLS=$4
MULTI_TENANT=$5
[ -n "$6" ] && INCLUDE_THUMBNAIL=$6


eval $(/opt/vidyo/bin/get_db_info.sh)

/usr/local/mysql/bin/mysqldump $MYSQL_OPTS --host=$DB_HOST --port=$DB_PORT -n --databases portal2 > $DIRNAME/db.sql
if [ $? != 0  ]; then
  echo "BACKUPFAILED 1"
  exit 1
fi

echo "$USED_SEATS $NUM_LINES $USED_INSTALLS $MULTI_TENANT" > $DIRNAME/license.txt
chmod +r $DIRNAME/license.txt

mkdir -p $DIRNAME/superupload
mkdir -p $DIRNAME/upload

cd /opt/vidyo/portal2/superupload

FN=$(find . -name "*.jpg" -o -name "*.png" -o -name "*.gif" -o -name "*.swf" )
for F in $FN; do
   cp -f $F $DIRNAME/superupload
done

cd /opt/vidyo/portal2/upload

FN=$(find . -name "*.jpg" -o -name "*.png" -o -name "*.gif" -o -name "*.swf" )
for F in $FN; do
   cp -f $F $DIRNAME/upload
done

cp -f /opt/vidyo/etc/ssl/private/misc.pem $DIRNAME/ > /dev/null 2>&1
cp -f /opt/vidyo/etc/db/access.conf $DIRNAME/ > /dev/null 2>&1
cp -f /opt/vidyo/etc/java/saml.jks $DIRNAME/ > /dev/null 2>&1

if [ $INCLUDE_THUMBNAIL -eq 1 ]; then
   logger "adding thumbnail to backup..."
   cp -R /opt/vidyo/portal2/thumbnail/ $DIRNAME/
   logger "done. [$?]"
else
  logger "skipping thumbnail from backup..."
fi

chown -R tomcat:tomcat $DIRNAME

echo "BACKUPSUCCESS"

#!/bin/bash

logger "restore_backup.sh" $*

MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

save_portal_version()
{
   PORTAL_VER=$(mysql -uroot -Dportal2 -N -s -e "SELECT configurationValue FROM Configuration WHERE configurationName='PortalVersion'")
   logger -t "restore_database.sh" "save_portal_version: $PORTAL_VER"
}

restore_portal_version()
{
   if [ -z "$PORTAL_VER" ]; then
      logger -t "restore_database.sh" "Portal Version is empty... ignoring"
      return 1
   fi

   logger -t "restore_database.sh" "restore_portal_version: $PORTAL_VER"
   mysql -uroot -Dportal2 -N -s -e "UPDATE Configuration SET configurationValue=\"$PORTAL_VER\" WHERE configurationName='PortalVersion'" > /dev/null 2>&1
   if [ $? -ne 0 ]; then
      logger -t "restore_database.sh" "Warning!!! Failed to restore the portal version..."
   fi
}


DIRNAME=/opt/vidyo/temp/tomcat/$1
USED_SEATS=$2
NUM_LINES=$3
USED_INSTALLS=$4
MULTI_TENANT=$5

if [ ! -f $DIRNAME/license.txt ]
then
  logger "RESTOREFAILED 4"
  echo "RESTOREFAILED 4"
  exit 1
fi

if [ `cat $DIRNAME/license.txt|awk '{print $1}'` -gt $USED_SEATS ]
then
  logger "RESTOREFAILED 5"
  echo "RESTOREFAILED 5"
  exit 1
fi

if [ `cat $DIRNAME/license.txt|awk '{print $2}'` -gt $NUM_LINES ]
then
  logger "RESTOREFAILED 6"
  echo "RESTOREFAILED 6"
  exit 1
fi

if [ `cat $DIRNAME/license.txt|awk '{print $3}'` -gt $USED_INSTALLS ]
then
  logger "RESTOREFAILED 7"
  echo "RESTOREFAILED 7"
  exit 1
fi

if [ "`cat $DIRNAME/license.txt|awk '{print $4}'`" == "true" ] && [ "$MULTI_TENANT" == "false" ]
then
  logger "RESTOREFAILED 8"
  echo "RESTOREFAILED 8"
  exit 1
fi

#save_portal_version

eval $(/opt/vidyo/bin/get_db_info.sh)

/usr/local/mysql/bin/mysql $MYSQL_OPTS --host=$DB_HOST --port=$DB_PORT portal2 < $DIRNAME/db.sql > /dev/null 2>&1
if [ $? != 0  ]; then
  logger "RESTOREFAILED 9"
  echo "RESTOREFAILED 9"
  exit 1
fi


mysql $MYSQL_OPTS -Dportal2 -N -s -e "TRUNCATE TABLE Endpoints" > /dev/null 2>&1

#restore_portal_version

find $DIRNAME -exec ls -l {} \; > /tmp/find.txt

cp -f $DIRNAME/superupload/* /opt/vidyo/portal2/superupload/
cp -f $DIRNAME/upload/* /opt/vidyo/portal2/upload/
cp -f $DIRNAME/misc.pem /opt/vidyo/etc/ssl/private/misc.pem
cp -f $DIRNAME/saml.jks /opt/vidyo/etc/java/saml.jks
#cp -f $DIRNAME/access.conf /opt/vidyo/etc/db/access.conf

if [ -d $DIRNAME/thumbnail ]; then
   cp -Rf $DIRNAME/thumbnail /opt/vidyo/portal2/
fi

/opt/vidyo/bin/update_file_permission.sh

#chmod 600 /opt/vidyo/etc/db/access.conf
#/opt/vidyo/bin/set_db_password.sh update > /dev/null 2>&1

logger "RESTORESUCCESS"
echo "RESTORESUCCESS"


#!/bin/bash
##############################################################
# Description: Set a unique DB password for vidyo user and
#              root user.
# Note: This script will update the tomcat DB configuration.
##############################################################
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin

if [[ "$1" =~ start|update ]]; then
   ## do nothing
   :
else
   exit 0
fi

DB_CFG=/opt/vidyo/etc/db/access.conf
OLD_UMASK=$(umask)
MYCNF=/root/.my.cnf
MYINIT=/opt/vidyo/temp/mysql/init
CONTEXT_XML=/usr/local/tomcat/conf/context.xml
NP_CONTEXT_XML=/usr/local/tomcatnp/conf/context.xml
PORTAL_BATCH_CONFIG=/opt/vidyo/portal2/vidyo-portal-batch/config/vidyo-portal-batch-data-source-context.xml

umask 0077

if [ "$1" = start ]; then
   VIDYO=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 8 | head -n1)
   ROOT=$(cat /dev/urandom | tr -dc 'a-zA-Z0-9' | fold -w 12 | head -n1)

   [ -f $DB_CFG ] && exit
   logger "Creating new db access..."
   mkdir -p $(dirname $DB_CFG)
   cat << EOF > $DB_CFG
VIDYO=$VIDYO
ROOT=$ROOT
EOF
else
   logger "Updating db access..."
   [ -f $DB_CFG ] || exit 1
   . $DB_CFG
fi

cat << EOF > $MYCNF
[client]
password=$ROOT
EOF

## update tomcat config file
eval $(cat $CONTEXT_XML | grep "password="  | tr " " "\n" | sort -u | grep password=)

if [ -n "$VIDYO_REPL" ]; then
   cat << EOF > $MYINIT
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('$ROOT');
SET PASSWORD FOR 'vidyo'@'localhost' = PASSWORD('$VIDYO');
SET PASSWORD FOR 'vidyorepl'@'localhost' = PASSWORD('$VIDYO_REPL');
EOF
else
   cat << EOF > $MYINIT
SET PASSWORD FOR 'root'@'localhost' = PASSWORD('$ROOT');
SET PASSWORD FOR 'vidyo'@'localhost' = PASSWORD('$VIDYO');
EOF

fi
chown mysql:mysql $MYINIT

sed -i "s/$password/$VIDYO/" $CONTEXT_XML
sed -i "s/$password/$VIDYO/" $NP_CONTEXT_XML
sed -i "s/$password/$VIDYO/" $PORTAL_BATCH_CONFIG
sed -i "s/v1dy03x/$VIDYO/" $PORTAL_BATCH_CONFIG
sed -i "s/v1dy03x/$VIDYO/" $CONTEXT_XML
sed -i "s/v1dy03x/$VIDYO/" $NP_CONTEXT_XML
umask $OLD_UMASK

/etc/init.d/mysql stop > /dev/null 2>&1
mysqld_safe --init-file=$MYINIT  > /dev/null 2>&1 &
sleep 5
rm -f $MYINIT

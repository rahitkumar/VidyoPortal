#!/bin/bash

PATH=$PATH:/usr/local/mysql/bin

logger -t vsendmail.sh "vsendmail.sh $*"
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"
ENCPASS=$(mysql $MYSQL_OPTS -D portal2 --silent -N -e "SELECT ConfigurationValue FROM Configuration WHERE ConfigurationName = 'smtpPassword'")
if [ -z "$ENCPASS" ]; then
   msmtp $*
else
   PASS=$(echo $ENCPASS | openssl enc -aes-256-cbc -a -d -md sha1 -pass file:/opt/vidyo/etc/ssl/private/misc.pem)
   msmtp --passwordeval="echo $PASS" $*
fi

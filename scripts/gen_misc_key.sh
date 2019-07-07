#!/bin/bash
###############################################
# Note: This should be called on every reboot.
###############################################

MKEY=/opt/vidyo/etc/ssl/private/misc.pem
EPFLAG=/opt/vidyo/vidyo_updates/ENCRYPT_PASSWORD
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

if [  -f $MKEY ]; then
   exit
fi

openssl genrsa | tr -d '\n' > $MKEY
chmod 640 $MKEY
chown root:webapps $MKEY

[ -f $EPFLAG ] && . $EPFLAG

if [ "$CONFIGURATION" = 1 -a "$SERVICES" = 1 ]; then
   ## This scenario should happen only on a new image.
   PASSWORD=$(echo -n password | openssl enc -aes-256-cbc -a -md sha1 -pass file:$MKEY)
   mysql $MYSQL_OPTS -D portal2 -e "UPDATE Services SET password='$PASSWORD' WHERE roleID=1"
fi


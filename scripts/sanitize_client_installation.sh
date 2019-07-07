#!/bin/bash
##################################################################################
# Description: Update ClientInstallations2 table and set IP and Hostname to NULL.
# Note: Must be run from cronjob once a day
##################################################################################

PATH=$PATH:/usr/local/mysql/bin
MYSQL_OPT=
if [ -f /root/.my.cnf ]; then
   MYSQL_OPT="--defaults-extra-file=/root/.my.cnf "
fi

if [ "$1" = "INSTALL_CRON" ]; then
   sed -i "/sanitize_client_installation.sh/d" /etc/crontab
   echo "15 23 * * * root /opt/vidyo/bin/sanitize_client_installation.sh  > /dev/null 2>&1" >> /etc/crontab
else
   logger -t "sanitize_client_installation.sh" "Updating ClientInstallations2. Setting ipAddress, hostname to NULL"
   mysql $MYSQL_OPT -uroot -D portal2 -e "UPDATE ClientInstallations2 SET ipAddress=NULL, hostname=NULL"
fi

#!/bin/bash

export PATH=$PATH:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin

. /opt/vidyo/bin/vidyologger.sh
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

CDR_STATUS=$(mysql $MYSQL_OPTS portal2 --silent -N -e \
 "SELECT  configurationValue FROM Configuration WHERE configurationName='CdrAllowDeny'")

vlog0 "CDR_STATUS=$CDR_STATUS"

MYSQL=
FW_CONF=/opt/vidyo/conf.d/firewall.conf
if [ -f $FW_CONF ]; then
   sed -i '/MYSQL_ETH.*/d' $FW_CONF
   . $FW_CONF
fi

[ -z "$MYSQL" ] && echo MYSQL=REJECT >> $FW_CONF

[ -z "$CDR_STATUS" ] && CDR_STATUS=0

if [ $CDR_STATUS -gt 0 ]; then
   vlog0 "CDR is enabled"
   CDR_HOST_ACCESS=$(mysql $MYSQL_OPTS portal2 --silent -N -e \
      "SELECT  configurationValue FROM Configuration WHERE configurationName='CdrAccessFromHost'")
   if [ -n "$CDR_HOST_ACCESS" ]; then
      /opt/vidyo/bin/firewallSettings.sh enableCdr $CDR_HOST_ACCESS
   else
      vlog0 "CDR_HOST_ACCESS is not set!"
      /opt/vidyo/bin/firewallSettings.sh disableCdr
   fi
else
   vlog0 "CDR is disabled"
   /opt/vidyo/bin/firewallSettings.sh disableCdr
fi

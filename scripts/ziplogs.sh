#!/bin/bash
######################################################################
# Description: Create a zip file that will contain server log files.
# Note: The zip file will be password protected. Use must provide the
# password via STDIN.
# The script script will echo the filename of the zip file.
######################################################################

read PASS
ZIPFILE=$(mktemp -u --tmpdir=/home/tomcat --suffix=.zip)

echo $ZIPFILE

if [ -n "$PASS" ]; then
   /usr/bin/zip -e -P "$PASS"  -r $ZIPFILE \
   /opt/vidyo/vm/vm.log* \
   /opt/vidyo/vidyorouter2/vr2.log* \
   /var/log/apache2 \
   /var/log/syslog* \
   /var/log/kern* \
   /opt/vidyo/logs/hotstandby.log* \
   /opt/vidyo/logs/vidyo-portal-batch/ \
   /opt/vidyo/logs/install/create_update_portaldb_* \
   /opt/vidyo/logs/install/UpgradePortalDb.logs \
   /opt/vidyo/logs/snmp.log /usr/local/tomcat/logs/ \
   /usr/local/tomcatnp/logs/ > /dev/null 2>&1
else
   /usr/bin/zip -r $ZIPFILE \
   /opt/vidyo/vm/vm.log* \
   /opt/vidyo/vidyorouter2/vr2.log* \
   /var/log/apache2 \
   /var/log/syslog* \
   /var/log/kern* \
   /opt/vidyo/logs/hotstandby.log* \
   /opt/vidyo/logs/vidyo-portal-batch/ \
   /opt/vidyo/logs/install/create_update_portaldb_* \
   /opt/vidyo/logs/install/UpgradePortalDb.logs \
   /opt/vidyo/logs/snmp.log /usr/local/tomcat/logs/ \
   /usr/local/tomcatnp/logs/ > /dev/null 2>&1

fi

chown tomcat:tomcat $ZIPFILE
exit 0

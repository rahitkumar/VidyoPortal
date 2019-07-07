#!/bin/bash

## Create a new file that has domain and bundle certificates.
BUNDLE_CERT=/opt/vidyo/etc/ssl/certs/domain-server-bundle.crt
DOMAIN_CERT=/opt/vidyo/etc/ssl/certs/domain-server.crt
DOMAIN_BUNDLE_CERT=${DOMAIN_CERT}-bundle
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"


> $DOMAIN_BUNDLE_CERT

[ -f $DOMAIN_CERT ] && cat $DOMAIN_CERT >> $DOMAIN_BUNDLE_CERT
echo >> $DOMAIN_BUNDLE_CERT
[ -f $BUNDLE_CERT ] && cat $BUNDLE_CERT >> $DOMAIN_BUNDLE_CERT


[ -f $DOMAIN_BUNDLE_CERT ] && chown root:tomcat $DOMAIN_BUNDLE_CERT

ulimit -n 100000
ulimit -c unlimited

[ -x /opt/vidyo/bin/updatevrconfig.sh ] && /opt/vidyo/bin/updatevrconfig.sh

pkill -9 VidyoManager
pkill -9 vr2

sleep 2

[ -f /opt/vidyo/vm/vmconfig.xml ] && rm /opt/vidyo/vm/vmconfig.xml
[ -f /opt/vidyo/vm/NetworkConfig.xml ] && rm /opt/vidyo/vm/NetworkConfig.xml
[ -f /opt/vidyo/vidyorouter2/vrconfig.xml ] && rm /opt/vidyo/vidyorouter2/vrconfig.xml

logger 'Starting VidyoConferencing'
if [ -d /opt/vidyo/vm ] ; then
   cd /opt/vidyo/vm
   /opt/vidyo/vm/VidyoManager --sslinfo > SSLINFO
   /opt/vidyo/vm/VidyoManager -x
   sleep 1
fi

if [ -d /opt/vidyo/vidyorouter2 ] ; then
   cd /opt/vidyo/vidyorouter2
   /opt/vidyo/vidyorouter2/vr2 --sslinfo > SSLINFO
   /opt/vidyo/vidyorouter2/vr2 -x
   sleep 1
fi

logger 'VidyoConferencing started'

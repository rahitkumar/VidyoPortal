#!/bin/bash

for D in tomcat tomcat2 tomcat3 tomcatnp; do
   TCDIR=/usr/local/${D}
   [ -d $TCDIR ] || continue
   find $TCDIR/logs/ -name "catalina.*.log" -mtime +29 -exec rm -f {} \;
   find $TCDIR/logs/ -name "localhost_access_log.*" -mtime +9 -exec rm -f {} \;
   find $TCDIR/logs/ -name "localhost_access_log*.txt" -mmin +1440 -exec gzip -f {} \;
   find $TCDIR/logs/ -mtime +90 -exec rm -f {} \;
done


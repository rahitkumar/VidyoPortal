#!/bin/bash
logger -t "purge_temp.sh" "Started"
find /usr/local/tomcat/temp -name "axis2*" -mtime +1  -exec rm -rf {} \; > /dev/null 2>&1
find /usr/local/tomcatnp/temp -name "axis2*" -mtime +1  -exec rm -rf {} \; > /dev/null 2>&1
logger -t "purge_temp.sh" "Done"

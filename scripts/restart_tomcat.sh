#!/bin/bash

if [ $(id -u) -ne 0 ]; then
   sudo -n $0 $*
   exit $?
fi

#Script gets invoked by daemon process which restarts tomcat from within super application (tomcat)
/etc/init.d/tomcat restart  > /dev/null 2>&1

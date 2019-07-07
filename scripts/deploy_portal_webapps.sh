#!/bin/bash

if [ -f /opt/vidyo/FORCE_DEPLOY ]; then
   echo  "Deploying VidyoPortal webapps..."

   /opt/vidyo/bin/tcapp_deployer.sh

   mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/lib
   mkdir -p /usr/local/tomcat/webapps/super/WEB-INF/lib
   mkdir -p /usr/local/tomcatnp/webapps/ROOT/WEB-INF/lib
   mkdir -p /usr/local/tomcatnp/webapps/admin/WEB-INF/lib

   cp -f /usr/local/tomcatnp/webapps/ROOT/WEB-INF/lib/* /usr/local/tomcat/webapps/super/WEB-INF/lib/
   cp -f /usr/local/tomcatnp/webapps/ROOT/WEB-INF/lib/* /usr/local/tomcat/webapps/ROOT/WEB-INF/lib/
   cp -f /usr/local/tomcatnp/webapps/ROOT/WEB-INF/lib/* /usr/local/tomcatnp/webapps/admin/WEB-INF/lib/
   cp -f /usr/local/tomcat/webapps/ROOT/WEB-INF/lib/* /opt/vidyo/portal2/vidyo-portal-batch/lib

   /opt/vidyo/bin/update_tomcat_file_perm.sh
   rm -f /opt/vidyo/FORCE_DEPLOY
fi


#!/bin/bash

/opt/vidyo/app/VPLicenseMgr/bin/VPLicenseMgr --query > /opt/vidyo/portal2/license/license.txt
chmod 640 /opt/vidyo/portal2/license/license.txt
chown tomcat:webapps /opt/vidyo/portal2/license/license.txt

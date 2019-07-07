#!/bin/bash
################################################################
# Filename: common-functions.sh
# Description: This script contains functions to be called
#              from Portal and VR installer. Any routine that
#              is common to portal and vr install is encourage
#              to go into this file...
#
# Date Created: 07-27-2012
#
# Note: source this file from install.sh and vrinstall.sh
################################################################


#################################
# log message into system logs  #
# logme <your  message>         #
#################################
logme()
{
   logger -t $(basename $0) -p local0.notice "$*"
}


###################################################
# use this script to generate a file that will
# contain the private key and domain certificate.
# This is now needed on the new VidyoManager
###################################################
generate_domain_key_file()
{
   local CERTS_DIR=/opt/vidyo/etc/ssl/certs
   local CERT_NAME=domain-server.crt
   local KEY_NAME=/opt/vidyo/etc/ssl/private/domain-server.key
   local TARGET_NAME=$CERTS_DIR/$CERT_NAME-key

   local let status=0

   if [ -f $CERTS_DIR/$CERT_NAME ]
   then
      sed -n '/BEGIN.*PRIVATE KEY/, /END.*PRIVATE KEY/p' $KEY_NAME > $TARGET_NAME
      echo >> $TARGET_NAME
      cat $CERTS_DIR/$CERT_NAME >> $TARGET_NAME
   else
      logger -t "INSTALL generate_domain_key_file.WARNING" "no $CERTS_DIR/$CERT_NAME found."
      let status=4
   fi

   return $status
}

IsVersionSupported()
{
   [[ "$1" =~ ^3\.4\.2|^3\.4\.3|^3\.4\.4|^3\.4\.5|^3\.4\.6|^3\.5\.0|^17\.1\.0|^17\.1\.1|^17\.2\.0|^17\.3\.0|^17\.3\.1|^18\.1\.0|^18\.2\.0|^18\.2\.1|^18\.3\.0|^18\.3\.1|^18\.4\.0|^19\.1\.0    ]] && return 0

   return 1
}

IsOSVersionSupported()
{
   ## do not support Ubuntu 8.04 as of 3.3.1

   ## always return true for now until we decided what to do. we may need to block a certain hardware type and not the OS version.

   #. /etc/lsb-release
   #if [ "$DISTRIB_RELEASE" = "8.04" ]; then
   #   return 1
   #fi
   return 0
}


install_default_banners()
{
   local DIR=$1
   local LOGIN_BANNER=/opt/vidyo/conf.d/loginbanner
   local WELCOME_BANNER=/opt/vidyo/conf.d/welcomebanner

   if [ -f /opt/vidyo/Vendor/DISA ]; then
      [ -f $LOGIN_BANNER ] || cp -f $DIR/loginbanner.disa $LOGIN_BANNER
      [ -f $WELCOME_BANNER ] || cp -f $DIR/welcomebanner.disa $WELCOME_BANNER
   fi

   return 0
}

update_log_level()
{
   return 0
## VPTL-8025. Do not update the loglevel anymore.
   VMCONF=/opt/vidyo/vm/localvmconfig
   VRCONF=/opt/vidyo/vidyorouter2/localvrconfig


   logme "Updating logLevel of VM/VR..."
   if [ -f $VMCONF ]; then
      logme "Updating loglevel of $VMCONF"
      sed -i 's/LogLevel=.*/LogLevel=warning info@VidyoManager debug@VMSoapIn debug@VMCsAPI debug@VMVpNotifSoap debug@VMCallControl/g' $VMCONF
      sed -i 's/MaxLogFileSizeKB=.*/MaxLogFileSizeKB=100000/g' $VMCONF
   fi

   if [ -f $VRCONF ]; then
      logme "Updating loglevel of $VRCONF"
      sed -i 's/LogLevel=.*/LogLevel=warning info@VidyoRouter2 info@LmiCsRouter/g' $VRCONF
      sed -i 's/MaxLogFileSizeKB=.*/MaxLogFileSizeKB=100000/g' $VRCONF
   fi
}

update_localvmvrconfig_with_sslinfo()
{
   ### update the location of ssl files in localvmconfig and localvrconfig
   local VMCONFIG=/opt/vidyo/vm/localvmconfig
   local VRCONFIG=/opt/vidyo/vidyorouter2/localvrconfig

   for VRVM in $VMCONFIG $VRCONFIG; do
      [ -f $VRVM ] || continue

      sed -i '/CertAuthFile=/d;
              /CertFile=/d;
              /CertWithKeyFile=/d;
              /CertKeyFile=/d' $VRVM

      cat << EOF >> $VRVM

CertAuthFile=/opt/vidyo/etc/ssl/certs/cacert.root
CertFile=/opt/vidyo/etc/ssl/certs/domain-server.crt-bundle
CertKeyFile=/opt/vidyo/etc/ssl/private/domain-server.key
CertWithKeyFile=/opt/vidyo/etc/ssl/certs/domain-server.crt-key
EOF

   done
}


update_file_permissions()
{
   logme "Updating file permissions..."
   logme "INFORMATION: updating tomcat file permission/owner..."
   /opt/vidyo/bin/update_tomcat_file_perm.sh


   ### portal and router specific

   [ -f /opt/vidyo/app/VPLicenseMgr/bin/VPLicenseMgr ] && chmod 755 /opt/vidyo/app/VPLicenseMgr/bin/VPLicenseMgr
   [ -f /opt/vidyo/vm/VidyoManager ] && chmod 750 /opt/vidyo/vm/VidyoManager
   [ -d /opt/vidyo/ha ] && chmod -R 755 /opt/vidyo/ha/bin/
   [ -d /opt/vidyo/ha ] && chmod -R 644 /opt/vidyo/ha/cfg/*
   [ -f /opt/vidyo/vidyorouter2/vr2 ] && chmod 750 /opt/vidyo/vidyorouter2/vr2
   [ -f /opt/vidyo/vidyoproxy/VPServer ] && chmod 750 /opt/vidyo/vidyoproxy/VPServer
   [ -f /etc/my.cnf ] && chmod 644 /etc/my.cnf

   if [ -d /home/dbsync ]; then
      chmod 640 /home/dbsync/*
      chown dbsync:dbsync /home/dbsync/*
   fi

   chmod o-rwx /opt/vidyo/StartVC2.sh /opt/vidyo/StopVC2.sh
   [ -d /opt/vidyo/portal2/tomcat_loadbalance ] && chmod -R o-rwx /opt/vidyo/portal2/tomcat_loadbalance
   [ -d /opt/vidyo/app/vp_snmp_agent ] && chmod -R o-rwx /opt/vidyo/app/vp_snmp_agent
   [ -f /opt/vidyo/vm/license.token ] && chmod 640 /opt/vidyo/vm/license.token

   [ -h /usr/local/mysql -o -d /usr/local/mysql ] && chown -H -R root:root /usr/local/mysql && chown -h root:root /usr/local/mysql/lib/*

   chown root:root /sbin/reboot
   chmod 750 /sbin/reboot
   chown root:root /sbin/shutdown
   chmod 750 /sbin/shutdown

   [ -f /opt/vidyo/VR_SYSTEM_ID ] && chmod 644 /opt/vidyo/VR_SYSTEM_ID
   [ -f /opt/vidyo/VM_SYSTEM_ID ] && chmod 644 /opt/vidyo/VM_SYSTEM_ID

   ## we can finally delete this folder... yay!!!
   [ -d /opt/vidyo/portal2/tools ] && rm -rf /opt/vidyo/portal2/tools
}


install_authuser()
{
   cp -f $1/authuser64 /opt/vidyo/bin
   chmod 750 /opt/vidyo/bin/authuser64
}

create_ssl_forced_no_redirect()
{
   local NOREDIRECT=/opt/vidyo/etc/ssl/private/ssl-forced-no-redirect

   if [ ! -f $NOREDIRECT ]; then
      echo "N" > $NOREDIRECT
   fi
   chmod 644 $NOREDIRECT
   chown root:tomcat $NOREDIRECT
}


check_su_requirements()
{

   local UPDATE_FAILED=/opt/vidyo/temp/root/UPDATE_FAILED
   local rc=0

   if [ -f /opt/vidyo/Vendor/DISA ]; then
      if [ ! -f /opt/vidyo/vidyo_updates/FSU20 ]; then
         logme "ERROR: FSU19 is not installed. Aborting..."
         echo "ERROR: FSU19 is not installed. Aborting..." > $UPDATE_FAILED
         rc=1
      fi
   else
      SUFLAG=/opt/vidyo/vidyo_updates/SECURITY_UPDATE_20

      if [ ! -f $SUFLAG ]; then
         logme "ERROR: $(basename $SUFLAG)  is not installed. Aborting..."
         echo "ERROR: $(basename $SUFLAG) is not installed. Aborting..." > $UPDATE_FAILED
         rc=1
      fi
   fi

   return $rc
}


check_apache_ssl_settings()
{
   APACHE_SECURITY_SETTINGS=/opt/vidyo/etc/apache/ssl_security_level.conf
   APACHE_SECURITY_LEVEL=/opt/vidyo/bin/apache_security_level.sh

   if [ ! -f $APACHE_SECURITY_SETTINGS ]; then
      return 0
   fi

   LEVEL=
   . $APACHE_SECURITY_SETTINGS
   if [ "$LEVEL" = MODERN ]; then
      $APACHE_SECURITY_LEVEL MODERN
   fi

   return 0
}

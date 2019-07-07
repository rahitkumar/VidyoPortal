#!/bin/bash

. /etc/lsb-release
. /opt/vidyo/bin/vidyologger.sh

export PATH=$PATH:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
UPDATE_FAILED=/opt/vidyo/temp/root/UPDATE_FAILED


USER=`whoami`
if [ "$USER" != "root" ] ; then
	logger "You have to be root to run this script, exiting installation"
	exit
fi
FILEPATH=`dirname $0`/../../VC2/install/scripts/production
VC2PATH=`dirname $0`/../../VC2/
WEB_DIRECTORY=/usr/local/tomcat/webapps
INSTALL_DIRECTORY=/opt/vidyo

HUNTER_DIR=$(cd $(dirname $0); pwd)
SHARED_INSTALL=$HUNTER_DIR/../SharedInstall
PRE_INSTALL=$HUNTER_DIR/../SharedInstall/pre_install.sh
POST_INSTALL=$HUNTER_DIR/../SharedInstall/post_install.sh

VIDYO_BIN_DIR=/opt/vidyo/bin
VIDYO_CONF_DIR=/opt/vidyo/conf.d

# Validate that common install environment is available for upgrade.  Stop
# upgrade if required.
#
if [ ! -x $PRE_INSTALL ]
then
   vlog0 "ERROR: cannot execute script $PRE_INSTALL"
   exit 5
fi

if [ ! -x $POST_INSTALL ]
then
   vlog0 "ERROR: cannot execute script $POST_INSTALL"
   exit 6
fi

# VP5597 - log upgrade version
#
if [ -f /opt/vidyo/VC2_VERSION ]
then
   OLD_VERSION=$(cat /opt/vidyo/VC2_VERSION)
   UPGRADE_VERSION=$(cat $FILEPATH/VC2_VERSION)

   vlog0 "UPGRADE from version = $OLD_VERSION to VC2_VERSION = $UPGRADE_VERSION"
fi

## source the common-functions.sh
. $FILEPATH/common-functions.sh

## source the target_arch file, created by VC2Checkout.sh

ARCH=`uname -m`
if [[ "$ARCH" != "x86_64" ]]; then
   vlog0 "ERROR:  Target architecture is not supported!"
   echo "ERROR:  Target architecture is not supported!" > $UPDATE_FAILED
   exit 2
fi

if [ ! "$DISTRIB_ID" = Ubuntu ] ; then
   vlog0 "ERROR: Non Ubuntu Linux Distribution not supported, exiting installation"
   exit 1
fi

if [ -f /opt/vidyo/vm/VidyoManager ] ; then
   vlog0 "ERROR: Not a standalone VidyoRouter box, exiting installation"
   exit 2
fi

if [ -f /opt/vidyo/VC2_VERSION ] ; then
   ver3=$(cat /opt/vidyo/VC2_VERSION | awk -F "_" '{ print $2,$3,$4}' | sed 's/^VC//g;s/ /\./g')
   if IsVersionSupported $ver3; then
      vlog0 "UPDATE: Upgrade from supported version"
   else
      vlog0 "ERROR: Upgrading from unsupported version $ver3, exiting upgrade"
      echo "ERROR: Upgrading from unsupported version $ver3, exiting upgrade" > $UPDATE_FAILED
      exit 2
   fi
fi

if ! check_su_requirements; then
   vlog0 "failed on check_su_requirements"
   exit 1
fi


vlog0 "INFORMATION: FILEPATH = $FILEPATH"
vlog0 "INFORMATION: WEB_DIRECTORY = $WEB_DIRECTORY"
vlog0 "INFORMATION: INSTALL_DIRECTORY = $INSTALL_DIRECTORY"

if $PRE_INSTALL 
then
   vlog0 "UPDATE: SUCCESS from $PRE_INSTALL"
else
   vlog0 "ERROR: unable to complete $PRE_INSTALL"
   exit 7
fi

## source the InstallFunctions.sh
. $FILEPATH/InstallFunctions.sh

/etc/init.d/apache2 stop

TOMCAT_INSTALLED="0"
# Let's stop tomcat and apache before changing related config files
if [ -f /etc/init.d/tomcat ] ; then
   TOMCAT_INSTALLED="1"
   /etc/init.d/tomcat stop
fi
sleep 5
pkill -9 java

# VP 5962 : no dependence on /home/admin or admin user

pkill -9 vr2
# not available, checked above --> pkill -9 VidyoManager
sleep 5

rm -f /etc/network/*/*sendmail*
awk '/^FSCKFIX/ { $1 = "FSCKFIX=yes" } { print }' /etc/default/rcS > /tmp/rcS
mv -f /tmp/rcS /etc/default/rcS
#
#awk '/^#SSLProtocol/ { $1 = "SSLProtocol" } { print }' /etc/apache2/mods-available/ssl.conf > /tmp/ssl.conf
#mv -f /tmp/ssl.conf /etc/apache2/mods-available/ssl.conf

sh $FILEPATH/installnetwork.sh

# NOTE: the /opt/vidyo/adm (admin consoles) is used on all node-types
# (portal, router, replay and gateway).  Therefore, these scripts are
# available as "shared" or "common" or "non-portal specific" files.
# The admin console is installed in $PRE_INSTALL and verified in $POST_INSTALL
#
# NOTE: please take a look at $PRE_INSTALL to determine which
# files common to all node-types (portal, router, replay, gateway)
# are placed into /opt/vidyo/bin
#
cp $FILEPATH/update_portal.sh /opt/vidyo/bin

#cp $FILEPATH/pack_security.sh /opt/vidyo/bin
#cp $FILEPATH/unpack_security.sh /opt/vidyo/bin
cp -f $FILEPATH/restart_tomcat.sh /opt/vidyo/bin/

VIDYO_SSL_PRIVATE_DIR=/opt/vidyo/etc/ssl/private
mkdir -p $VIDYO_SSL_PRIVATE_DIR

VH_FILE=$VIDYO_SSL_PRIVATE_DIR/vr2conf.vidyohost
if [ ! -f $VH_FILE ]
then
   cat << EOF > $VH_FILE
vr2conf|eth0|8443|Y|N
vr2conf|eth0|80|N|N
EOF
   # Note that vr2conf.vidyohost will be altered near the end of this script.
fi

WEB_DIRECTORY=/usr/local/tomcat/webapps
INSTALL_DIRECTORY=/opt/vidyo
VM_RMCP_PORT=17991
VR_CMCP_PORT=17990

if [ -f /opt/vidyo/vidyorouter/vrconfig ] ; then
   VR_CMCP_PORT=`grep VR_CMCP_PORT /opt/vidyo/vidyorouter/vrconfig | sed s/VR_CMCP_PORT=//g`
   mv /opt/vidyo/vidyorouter/vrconfig /opt/vidyo/vidyorouter/vrconfig.bak
fi

DEFAULT_INTF=eth0
#get default IP addr
MY_IP=`ifconfig eth0 | grep "inet addr" | awk '{print \$2}' | cut -d':' -f2`
vlog0 "INFORMATION: using $DEFAULT_INTF IP: $MY_IP"

VM_IP_ADDR=$MY_IP
VR_IP_ADDR=$MY_IP


rm -rf $WEB_DIRECTORY/vr2conf.war
rm -rf $WEB_DIRECTORY/vr2conf
rm -rf $WEB_DIRECTORY/vp2conf.war
rm -rf $WEB_DIRECTORY/vp2conf

cp $FILEPATH/vr2conf.war $WEB_DIRECTORY/.

#provide compatibility with 1.5 bookmarks
cp $FILEPATH/vrindex.html /var/www/index.html

#to stop browsers from caching our content
cp -f $FILEPATH/headers.conf /etc/apache2/mods-available/headers.conf
chmod 644 /etc/apache2/mods-available/headers.conf

if [ ! -h /etc/apache2/mods-enabled/headers.conf ] ; then
   ln -s /etc/apache2/mods-available/headers.conf /etc/apache2/mods-enabled/headers.conf
fi
if [ ! -h /etc/apache2/mods-enabled/headers.load ] ; then
   ln -s /etc/apache2/mods-available/headers.load /etc/apache2/mods-enabled/headers.load
fi
if [ ! -h /etc/apache2/mods-enabled/rewrite.load ] ; then
   ln -s /etc/apache2/mods-available/rewrite.load /etc/apache2/mods-enabled/rewrite.load
fi

# NOTE: > ports.conf, all ports specified in sites-available/default or ssl
#
# OK, but will be overwritten by apache_init.sh
#
cp $FILEPATH/default /etc/apache2/sites-available/default


#Create the install directory
mkdir -p ${INSTALL_DIRECTORY}

# give tomcat time to deploy everything before we reboot at the end
vlog0 "INFORMATION: deploying war files..."
/opt/vidyo/bin/tcapp_deployer.sh

unalias -a

cp $FILEPATH/mycacerts.cer ${INSTALL_DIRECTORY}/etc/ssl/private/vidyo-cacert.root

#upgrade StartVC2.sh
cp $FILEPATH/StartVC2.sh ${INSTALL_DIRECTORY}

#copy StopVC2 if one doesn't exist
cp $FILEPATH/StopVC2.sh /opt/vidyo/StopVC2.sh
chmod +x ${INSTALL_DIRECTORY}/StartVC2.sh
chmod +x ${INSTALL_DIRECTORY}/StopVC2.sh

# VR2 config file
if [ -f ${INSTALL_DIRECTORY}/vidyorouter2/vrconfig.xml ] ; then
   if [ -z `grep DocumentVersion ${INSTALL_DIRECTORY}/vidyorouter2/vrconfig.xml` ] ; then
      #need to upgrade
      #add the DocumentVersion 
      LAST=/tmp/.vrconfig.xml
      sed "s/<Config>/<Config><DocumentVersion>0<\/DocumentVersion>/g" ${INSTALL_DIRECTORY}/vidyorouter2/vrconfig.xml > ${LAST}
      cp ${LAST} ${INSTALL_DIRECTORY}/vidyorouter2/vrconfig.xml
   fi
else
   mkdir -p ${INSTALL_DIRECTORY}/vidyorouter2
   sed -i s/VR_IP_ADDR/${VR_IP_ADDR}/g $FILEPATH/vrconfig.xml.template
   sed -i s/VR_CMCP_PORT/${VR_CMCP_PORT}/g $FILEPATH/vrconfig.xml.template
   sed -i s/VM_IP_ADDR/${VM_IP_ADDR}/g /$FILEPATH/vrconfig.xml.template
   sed -i s/VM_RMCP_PORT/${VM_RMCP_PORT}/g $FILEPATH/vrconfig.xml.template > /opt/vidyo/vidyorouter2/vrconfig.xml
fi
cp $FILEPATH/vrconfig.xml.template /opt/vidyo/vidyorouter2/

if [ ! -f ${INSTALL_DIRECTORY}/vidyorouter2/localvrconfig ] ; then
   #just copy because there are no tokens yet in this template
   cp $FILEPATH/localvrconfig.template /opt/vidyo/vidyorouter2/localvrconfig
fi
cp $FILEPATH/localvrconfig.template /opt/vidyo/vidyorouter2/

[ -d /opt/vidyo/vidyoproxy ] && rm -rf /opt/vidyo/vidyoproxy

cp $VC2PATH/VidyoRouter/vr2 /opt/vidyo/vidyorouter2/vr2
cp $VC2PATH/VidyoRouter/Config/vrconfig.xsd /opt/vidyo/vidyorouter2/

# set execute permission to reboot, shutdown from web
# remove the sticky bit since sudo command will now be used to do a reboot/shutdown
chown root:root /sbin/reboot
chmod 750 /sbin/reboot
chown root:root /sbin/shutdown
chmod 750 /sbin/shutdown

cp $FILEPATH/rc.local /etc
cp $FILEPATH/limits.conf /etc/security

${INSTALL_DIRECTORY}/StopVC2.sh
chown root /opt/vidyo/vidyorouter2/vr2
chmod 750 /opt/vidyo/vidyorouter2/vr2
chmod 755 /opt/vidyo/StartVC2.sh

#cp -f $FILEPATH/sharedFiles/GetNetwork.sh $INSTALL_DIRECTORY/bin

if [ "$TARGET_ARCH" = "x86_64" ]; then
   sed '${s/$/ (64-bit)/}' $FILEPATH/VC2_VERSION > /opt/vidyo/VC2_VERSION
else
   cp -f $FILEPATH/VC2_VERSION /opt/vidyo/VC2_VERSION
fi

cp -f $FILEPATH/VC2_VERSION /opt/vidyo/VC2_VERSION
if [ -f /opt/vidyo/Vendor/DISA ] 
then
    echo $(cat /opt/vidyo/VC2_VERSION | sed 's/$/D/g') > /opt/vidyo/VC2_VERSION
fi

cp -f $FILEPATH/update_portal.sh    ${INSTALL_DIRECTORY}/bin/
#cp -f $FILEPATH/unpack_security.sh  ${INSTALL_DIRECTORY}/bin/
#cp -f $FILEPATH/pack_security.sh    ${INSTALL_DIRECTORY}/bin/
cp -f $FILEPATH/restart_tomcat.sh   ${INSTALL_DIRECTORY}/bin/

vlog0 "INFORMATION: starting VidyoRouter ..."

# remove tomcat ROOT if this is a standalone vidyorouter
if [ ! -f /opt/vidyo/vm/VidyoManager ] ; then
   rm -f  /usr/local/tomcat/webapps/ROOT.war
   rm -rf /usr/local/tomcat/webapps/ROOT
fi

sleep 1

chmod 440 /etc/sudoers
chown root:root /etc/sudoers

dpkg --purge ntp > /dev/null 2>&1

## make sure runsudo has write access only to root.
[ -f /opt/vidyo/adm/runsudo.sh ] && chown root:root /opt/vidyo/adm/runsudo.sh && chmod 755 /opt/vidyo/adm/runsudo.sh


[ -f $FILEPATH/updatevrconfig.sh ] && cp -f $FILEPATH/updatevrconfig.sh /opt/vidyo/bin/

### run the admin post-installation script
[ -f /opt/vidyo/adm/admin_post_installer.sh ] && /opt/vidyo/adm/admin_post_installer.sh

#allow ethtool to be used by non-privilege user
[ -f /usr/sbin/ethtool ] && chmod +s /usr/sbin/ethtool

if [ -f /etc/rc1.d/K20tomcat ] ; then
    ## send output to devnull, otherwise this script will terminate prematurely.
    update-rc.d -f tomcat remove > /dev/null 2>&1
    update-rc.d tomcat defaults 99 > /dev/null 2>&1
fi

rm -rf /usr/local/tomcat/webapps/examples
rm -rf /usr/local/tomcat/webapps/docs
rm -rf /usr/local/tomcat/webapps/manager
rm -rf /usr/local/tomcat/webapps/host-manager

cp -f $FILEPATH/tomcat.server.xml /usr/local/tomcat/conf/server.xml

cp -f $FILEPATH/tomcat.multi /etc/init.d/tomcat

### generate_domain_key_file defined in common-functions.sh
#   perform after initializeSecurity (distribute certificates)
generate_domain_key_file

vlog0 "UPDATE: installing default banners if not exist"
install_default_banners $FILEPATH

if $POST_INSTALL
then 
   vlog0 "UPDATE: successfully completed $POST_INSTALL"
else
   local let post_install_status=$?
   vlog0 "WARNING: error status from $POST_INSTALL status=$post_install_status"
fi

update_log_level
update_localvmvrconfig_with_sslinfo

#vlog0 "creating tomcat user..."
#create_tomcat_user

cp -f $FILEPATH/tomcat /etc/init.d/
cp -f $FILEPATH/router.tomcat.conf /opt/vidyo/conf.d/tomcat.conf

vlog0 "updating sudoers file..."

cp -f $FILEPATH/sudoers.portal /etc/sudoers
cp -f $FILEPATH/tomcat.sudoers.router.default /etc/sudoers.d/tomcat

rm -f /etc/sudoers.d/tomcat.sudoers.router.default
rm -f /etc/sudoers.d/tomcat.sudoers.router.secure

chown root:root /etc/sudoers.d/tomcat

vlog0 "Generating apache customer error html..."
$FILEPATH/gen_error_html.sh


#if [ -f /opt/vidyo/etc/ssl/private/PRIVILEGED_MODE_DISABLED ]; then
#   cp -f /etc/sudoers.d/tomcat.sudoers.router.secure /etc/sudoers.d/tomcat
#else
#   touch /opt/vidyo/etc/ssl/private/PRIVILEGED_MODE
#   cp -f /etc/sudoers.d/tomcat.sudoers.router.default /etc/sudoers.d/tomcat
#fi

chmod 440 /etc/sudoers.d/tomcat
#chmod 440 /etc/sudoers.d/tomcat.sudoers.router.secure
#chmod 440 /etc/sudoers.d/tomcat.sudoers.router.default

chmod 440 /etc/sudoers
chown root:root /etc/sudoers

vlog0 "installing signature validation tool..."
cp -f $FILEPATH/signcheck.sh /opt/vidyo/bin/
cp -f $FILEPATH/vidyoca.crt /opt/vidyo/etc/ssl/certs/

ln -s /opt/vidyo/bin/apache_version.sh /etc/init.d/apache_version
update-rc.d apache_version defaults 99


install_authuser $FILEPATH

create_ssl_forced_no_redirect

vlog0 "about to update file permissions..."
update_file_permissions

vlog0 "Purging archives..."
$FILEPATH/purge_archives.sh

##disable appport
vlog0 "Disabling apport..."
[ -f /etc/default/apport ] && sed -i 's/^enabled=.*/enabled=0/g'  /etc/default/apport


## check if MODERN ssl settings is enabled
check_apache_ssl_settings

vlog0 "UPDATE: finished installation of VidyoRouter to version $UPGRADE_VERSION"

/sbin/reboot

vlog0 "UPDATE: after reboot, waiting for processes to terminate."


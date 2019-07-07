#!/bin/bash

## note: this script will not work on older portal prior to 3.3.0

. /opt/vidyo/bin/vidyologger.sh
. /opt/vidyo/adm/myfunctions.sh
. /etc/lsb-release

UPDATE_FAILED=/opt/vidyo/temp/root/UPDATE_FAILED

export PATH=$PATH:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin

USER=`whoami`
if [ "$USER" != "root" ] ; then
   vlog0 "ERROR: You have to be root to run this script, exiting installation"
   exit
fi

if [ ! "$DISTRIB_ID" = Ubuntu ] ; then
   vlog0 "ERROR: Non Ubuntu Linux Distribution not supported, exiting installation"
   exit 1
fi

# is_vidyo_portal is function available from base image /opt/vidyo/adm/myfunctions.sh
#
if ! is_vidyo_portal; then
   vlog0 "ERROR: must update VidyoPortal ONLY, exiting installation"
   exit 1 
fi

FILEPATH=`dirname $0`/../../VC2/install/scripts/production
VC2PATH=`dirname $0`/../../VC2/
WEB_DIRECTORY=/usr/local/tomcat/webapps
INSTALL_DIRECTORY=/opt/vidyo
DB_AUDIT_NAME=portal2audit


HUNTER_DIR=$(cd $(dirname $0); pwd)
SHARED_INSTALL=$HUNTER_DIR/../SharedInstall
PRE_INSTALL=$HUNTER_DIR/../SharedInstall/pre_install.sh
POST_INSTALL=$HUNTER_DIR/../SharedInstall/post_install.sh

# Validate that common install environment is available for upgrade.  Stop
# upgrade if required.
#
if [ ! -x $PRE_INSTALL ]; then
   vlog0 "ERROR: cannot execute script $PRE_INSTALL"
   exit 5
fi

# VP5597 - log upgrade version
#
if [ -f /opt/vidyo/VC2_VERSION ]; then
   OLD_VERSION=$(cat /opt/vidyo/VC2_VERSION)
   UPGRADE_VERSION=$(cat $FILEPATH/VC2_VERSION)

   vlog0 "UPGRADE from version = $OLD_VERSION to VC2_VERSION = $UPGRADE_VERSION"
else
   vlog0 "Failed! Unable detect portal version from VC2_VERSION"
   exit 1 
fi

## source the common-functions.sh ' (for using ' color coded vim)
# 
. $FILEPATH/common-functions.sh
vlog0 "after processing common-functions.sh, about to source InstallFunctions.sh"

## source the InstallFunctions.sh 

portalVersion=$(cat /opt/vidyo/VC2_VERSION | awk -F "_" '{ print $2,$3,$4}' | sed 's/^VC//g;s/ /\./g')

if IsVersionSupported $portalVersion; then
   vlog0 "SUCCESS: Upgrade from supported version"
else
   vlog0 "ERROR: Upgrading from unsupported version $portalVersion, exiting upgrade"
   echo  "ERROR: Upgrading from unsupported version $portalVersion, exiting upgrade" > $UPDATE_FAILED
   exit 2
fi

if IsOSVersionSupported; then
   vlog0 "SUCCESS: OS Version is supported"
else
   vlog0 "ERROR: Upgrading from unsupported platform, exiting upgrade"
   echo "ERROR: Upgrading from unsupported platform, exiting upgrade" > $UPDATE_FAILED
   exit 1
fi

if ! check_su_requirements; then
   vlog0 "failed on check_su_requirements"
   exit 1
fi


# CAREFUL - this function if calling an older version of /opt/vidyo/adm/myfunctions.sh
# can mess up the PATH required by mysql.  After pre_install.sh it is OK.
#
#. $FILEPATH/InstallFunctions.sh

## make sure mysql/bin is on the PATH.  Previous version of /opt/vidyo/adm/myfunctions.sh overrides the $PATH and mysql/bin is not set.
## myfunctions.sh is being called inside "InstallFunctions.sh". Add the mysql/bin again here just to be sure.
PATH=$PATH:/usr/local/mysql/bin
export PATH

vlog0 "INFORMATION: PATH = $PATH"
vlog0 "INFORMATION: FILEPATH = $FILEPATH"
vlog0 "INFORMATION: VC2PATH  = $VC2PATH"
vlog0 "INFORMATION: WEB_DIRECTORY = $WEB_DIRECTORY"
vlog0 "INFORMATION: INSTALL_DIRECTORY = $INSTALL_DIRECTORY"

vlog0 "UPDATE:  Shutdown apache..."
/etc/init.d/apache2 stop > /dev/null 2>&1

sleep 1

# do a DB schema check before running the pre_installer...

$FILEPATH/compare_create_update_db.sh > /opt/vidyo/logs/install/create_update_portaldb_before.log
if [ $? -ne 0 ]; then
   vlog0 "WARNING: Database schema check failed. Upgrade cannot continue"
   echo "WARNING: Database schema check failed. Upgrade cannot continue" > $UPDATE_FAILED
   /etc/init.d/apache2 start > /dev/null 2>&1
   sleep 1
   exit 1
fi

## create a full DB backup before a upgrading portal2 DB 
mkdir -p /opt/vidyo/archives/database
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"
MYSQLDB_BACKUP=/opt/vidyo/archives/database/mysql_db_backup_$(date +%Y%m%d%H%M%S).sql
mysqldump $MYSQL_OPTS --add-drop-database --routines --databases portal2  > $MYSQLDB_BACKUP


cp -f /etc/my.cnf /opt/vidyo/archives/database/

vlog0 "Installing my.cnf..."
mkdir -p /opt/vidyo/conf/mysql/
cp -f $FILEPATH/my.cnf.* /opt/vidyo/conf/mysql/

mkdir -p /opt/vidyo/portal2/sql
cp -f $FILEPATH/sql/*.sql /opt/vidyo/portal2/sql/

## install the international flag to upload folder
mkdir -p /opt/vidyo/portal2/upload/
cp -f $FILEPATH/country-flags/*.png /opt/vidyo/portal2/upload/

vlog0 "installing tomcatnp server.xml configuration..."
mkdir -p /opt/vidyo/conf/tomcat/
cp -f $FILEPATH/tomcatnp.*.xml /opt/vidyo/conf/tomcat/


$FILEPATH/UpgradePortalDb.sh

cp -f /opt/vidyo/bin/CreatePortal20Db.sh /opt/vidyo/bin/CreatePortal20Db.sh.old
cp -f $FILEPATH/CreatePortal20Db.sh ${INSTALL_DIRECTORY}/bin/

### check for any DB discrepancy created by UpgradePortalDb.sh
$FILEPATH/compare_create_update_db.sh > /opt/vidyo/logs/install/create_update_portaldb_after.log
if [ $? -ne 0 ]; then
   mv -f /opt/vidyo/bin/CreatePortal20Db.sh.old /opt/vidyo/bin/CreatePortal20Db.sh
   vlog0 "WARNING: Database discrepancy found. Upgrade cannot continue"
   echo "WARNING: Database discrepancy found. Upgrade cannot continue" > $UPDATE_FAILED
   vlog0 "WARNING: restoring previous DB snapshot..."
   mysql $MYSQL_OPTS < $MYSQLDB_BACKUP > /opt/vidyo/logs/install/restore_mysql_db_backup.log 2>&1
   /etc/init.d/apache2 start > /dev/null 2>&1
   vlog0 "WARNING: Restoring my.cnf..."
   cp -f /opt/vidyo/archives/database/my.cnf /etc/my.cnf
   /etc/init.d/mysql restart
   sleep 1
   exit 1
fi

. /opt/vidyo/bin/vidyologger.sh
if $PRE_INSTALL ;  then
   vlog0 "SUCCESS: from $PRE_INSTALL"
else
   vlog0 "ERROR: unable to complete $PRE_INSTALL"
   ## revert back the changes done in the previous lines...
   mv -f /opt/vidyo/bin/CreatePortal20Db.sh.old /opt/vidyo/bin/CreatePortal20Db.sh
   vlog0 "WARNING: restoring previous DB snapshot..."
   mysql $MYSQL_OPTS < $MYSQLDB_BACKUP > /opt/vidyo/logs/install/restore_mysql_db_backup.log 2>&1

   exit 7
fi

gzip $MYSQLDB_BACKUP

# pre_install has run.  Now, the function vlog0 is available

sed -i s/TMPTIME=0/TMPTIME=1/g /etc/default/rcS > rcS.new
mv rcS.new /etc/default/rcS


cp -f $FILEPATH/compare_create_update_db.sh /opt/vidyo/bin
cp -f $FILEPATH/fix_db_schema.sh /opt/vidyo/bin
cp -f $FILEPATH/CreatePortal20Db.sh ${INSTALL_DIRECTORY}/bin/
cp -f $FILEPATH/get_db_info.sh      ${INSTALL_DIRECTORY}/bin/
cp -f $FILEPATH/backup_database.sh  ${INSTALL_DIRECTORY}/bin/
cp -f $FILEPATH/restore_database.sh ${INSTALL_DIRECTORY}/bin/
cp -f $FILEPATH/update_portal.sh    ${INSTALL_DIRECTORY}/bin/
cp -f $FILEPATH/restart_tomcat.sh   ${INSTALL_DIRECTORY}/bin/
cp -f $FILEPATH/nss_utils.sh        ${INSTALL_DIRECTORY}/bin/
cp -f $FILEPATH/portal_tables.sh    ${INSTALL_DIRECTORY}/bin/

cp $FILEPATH/cdr_user.sh  ${INSTALL_DIRECTORY}/bin/
cp $FILEPATH/UpgradePortalDb.sh ${INSTALL_DIRECTORY}/bin/
sed -i '1i exit' ${INSTALL_DIRECTORY}/bin/UpgradePortalDb.sh

# create the stored procedure in mysql DB
cp -f $FILEPATH/run_portal_sql.sh /opt/vidyo/bin/
/opt/vidyo/bin/run_portal_sql.sh

# run the config_nr on every reboot.
cp -f $FILEPATH/config_nr.sh        ${INSTALL_DIRECTORY}/bin/
ln -s /opt/vidyo/bin/config_nr.sh /etc/init.d/config_nr
update-rc.d config_nr defaults 98

ln -s /opt/vidyo/bin/apache_version.sh /etc/init.d/apache_version
update-rc.d apache_version defaults 99

# Lets stop tomcat and apache before changing related config files
if [ -f /etc/init.d/tomcat ] ; then
   /etc/init.d/tomcat stop
fi

pkill -9 vr2
pkill -9 VidyoManager
sleep 5

cp $FILEPATH/web.xml /usr/local/tomcat/conf/.

rm -rf /usr/local/tomcat/webapps/examples
rm -rf /usr/local/tomcat/webapps/docs
rm -rf /usr/local/tomcat/webapps/manager
rm -rf /usr/local/tomcat/webapps/host-manager

rm -f /etc/network/*/*sendmail*
awk '/^FSCKFIX/ { $1 = "FSCKFIX=yes" } { print }' /etc/default/rcS > /tmp/rcS
mv -f /tmp/rcS /etc/default/rcS
#
#awk '/^#SSLProtocol/ { $1 = "SSLProtocol" } { print }' /etc/apache2/mods-available/ssl.conf > /tmp/ssl.conf
#mv -f /tmp/ssl.conf /etc/apache2/mods-available/ssl.conf

vlog0 "UPDATE: sh $FILEPATH/installnetwork.sh"

VIDYO_SSL_PRIVATE_DIR=/opt/vidyo/etc/ssl/private

# make the dir (first time only)
#
mkdir -p $VIDYO_SSL_PRIVATE_DIR

[ -f $VIDYO_SSL_PRIVATE_DIR/vp2conf.vidyohost ] && rm -f $VIDYO_SSL_PRIVATE_DIR/vp2conf.vidyohost
[ -f $VIDYO_SSL_PRIVATE_DIR/vm2conf.vidyohost ] && rm -f $VIDYO_SSL_PRIVATE_DIR/vm2conf.vidyohost
[ -f $VIDYO_SSL_PRIVATE_DIR/vr2conf.vidyohost ] && rm -f $VIDYO_SSL_PRIVATE_DIR/vr2conf.vidyohost

vidyohostSuffix="vidyohost"
#portalUsers="vr2conf upload super admin VidyoPortalStatusService"
portalUsers="upload super admin VidyoPortalStatusService"

for userIndex in ${portalUsers}
do
   currFile="$VIDYO_SSL_PRIVATE_DIR/$userIndex.$vidyohostSuffix"
   if [ ! -f $currFile ]
   then
      cat << EOF > $currFile
$userIndex|eth0|443|Y|N
$userIndex|eth0|80|N|N
EOF
   fi 
done

# remove vr2conf from VidyoPortal
rm -f /usr/local/tomcat/webapps/vr2conf.war
rm -rf /usr/local/tomcat/webapps/vr2conf

chmod 755 -R /opt/vidyo/bin
chmod 744  $VIDYO_SSL_PRIVATE_DIR
chmod 644 -R $VIDYO_SSL_PRIVATE_DIR/*

vlog0 "TRACE: bin directory updated"

VM_SOAP_PORT=17995
VM_EMCP_PORT=17992
VM_RMCP_PORT=17991
VR_CMCP_PORT=17990
EMPTY_PORT=

DEFAULT_INTF=eth0
#get default IP addr
MY_IP=`ifconfig eth0 | grep "inet addr" | awk '{print \$2}' | cut -d':' -f2`
vlog0 "INFORMATION: using $DEFAULT_INTF IP: $MY_IP"


VM_IP_ADDR=$MY_IP
VR_IP_ADDR=$MY_IP
VP_IP_ADDR=$MY_IP

ETH0ADDR=`ifconfig eth0 | grep HWaddr | awk '{print $5}' | sed s/://g`
DISKID=`hdparm -I /dev/sda| grep "Serial Number:" | awk '{print $3}'`
OLDVMID=${ETH0ADDR}00VM0001
NEWVMID=${ETH0ADDR}${DISKID}00VM0001
OLDVRID=${ETH0ADDR}00VR0001
NEWVRID=${ETH0ADDR}${DISKID}00VR0001

# VM wsdl
sed s/VM_SOAP_PORT/${VM_SOAP_PORT}/g $VC2PATH/Wsdl/VidyoManagerService.wsdl > /opt/vidyo/vm/VidyoManagerService.wsdl

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
	sed s/VR_IP_ADDR/${VR_IP_ADDR}/g $FILEPATH/vrconfig.xml.template > /tmp/.vrconfig.xml
	sed s/VR_CMCP_PORT/${VR_CMCP_PORT}/g /tmp/.vrconfig.xml > /tmp/.vrconfig.xml.1
	sed s/VM_IP_ADDR/${VM_IP_ADDR}/g /tmp/.vrconfig.xml.1 > /tmp/.vrconfig.xml
	sed s/VM_RMCP_PORT/${VM_RMCP_PORT}/g /tmp/.vrconfig.xml > /opt/vidyo/vidyorouter2/vrconfig.xml
fi
rm /tmp/.vrconfig.xml*
cp $FILEPATH/vrconfig.xml.template /opt/vidyo/vidyorouter2/

if [ ! -f ${INSTALL_DIRECTORY}/vidyorouter2/localvrconfig ] ; then
   #just copy because there are no tokens yet in this template
   cp $FILEPATH/localvrconfig.template /opt/vidyo/vidyorouter2/localvrconfig
fi
cp $FILEPATH/localvrconfig.template /opt/vidyo/vidyorouter2/


# VM config file
if [ -f ${INSTALL_DIRECTORY}/vm/vmconfig.xml ] ; then
	if [ -z `grep DocumentVersion ${INSTALL_DIRECTORY}/vm/vmconfig.xml` ] ; then
		#need to upgrade
		#add the DocumentVersion 
		LAST=/tmp/.vmconfig.xml
		sed "s/<Config>/<Config><DocumentVersion>0<\/DocumentVersion>/g" ${INSTALL_DIRECTORY}/vm/vmconfig.xml > ${LAST}
		cp ${LAST} ${INSTALL_DIRECTORY}/vm/vmconfig.xml
	fi
else
	mkdir -p ${INSTALL_DIRECTORY}/vm	# ok even if the dir exists
	sed s/VM_IP_ADDR/${VM_IP_ADDR}/g $FILEPATH/vmconfig.xml.template > /tmp/.vmconfig.xml
	sed s/VM_SOAP_PORT/${VM_SOAP_PORT}/g /tmp/.vmconfig.xml > /tmp/.vmconfig.xml.1
	sed s/VM_EMCP_PORT/${VM_EMCP_PORT}/g /tmp/.vmconfig.xml.1 > /tmp/.vmconfig.xml
	sed s/VM_RMCP_PORT/${VM_RMCP_PORT}/g /tmp/.vmconfig.xml > /opt/vidyo/vm/vmconfig.xml
fi
rm -f /tmp/.vmconfig.xml*
cp $FILEPATH/vmconfig.xml.template /opt/vidyo/vm/

if [ ! -f ${INSTALL_DIRECTORY}/vm/localvmconfig ] ; then
	#just copy because there are no tokens yet in this template
	cp $FILEPATH/localvmconfig.template /opt/vidyo/vm/localvmconfig
fi
cp $FILEPATH/localvmconfig.template /opt/vidyo/vm/

#upgrade StartVC2.sh
cp $FILEPATH/StartVC2.sh ${INSTALL_DIRECTORY}

#copy StopVC2 if one does not exist
cp $FILEPATH/StopVC2.sh /opt/vidyo/StopVC2.sh


if [ -f ${INSTALL_DIRECTORY}/vm/NetworkConfig.xml ] ; then
	if [ -z `grep DocumentVersion ${INSTALL_DIRECTORY}/vm/NetworkConfig.xml` ] ; then
		vlog0 "UPDATE: upgrading network config"
		#need to upgrade
		#add the DocumentVersion first
		LAST=/tmp/.NetworkConfig.xml
		sed "s/<NetworkConfig>/<NetworkConfig><DocumentVersion>0<\/DocumentVersion>/g" ${INSTALL_DIRECTORY}/vm/NetworkConfig.xml > ${LAST}
		# add diskid to all local VRIDs	if we have a valid diskid
		if [ ! -z $DISKID ] ; then
			sed "s/${OLDVRID}/${NEWVRID}/g" ${LAST} > /tmp/.NetworkConfig.xml.1
			LAST=/tmp/.NetworkConfig.xml.1
		fi
		cp ${LAST} ${INSTALL_DIRECTORY}/vm/NetworkConfig.xml
	fi
else
	mkdir -p ${INSTALL_DIRECTORY}/vm	# ok even if the dir exists
	sed s/ETH0ADDR/${ETH0ADDR}/g $FILEPATH/NetworkConfig.xml.template > /tmp/.NetworkConfig.xml
	sed s/VR_IP_ADDR/${VR_IP_ADDR}/g /tmp/.NetworkConfig.xml > /tmp/.NetworkConfig.xml.1
	sed s/DISKID/${DISKID}/g /tmp/.NetworkConfig.xml.1 > /tmp/.NetworkConfig.xml
	sed s/VR_CMCP_PORT/${VR_CMCP_PORT}/g /tmp/.NetworkConfig.xml > /tmp/.NetworkConfig.xml.1
	sed s/VERSION/0/g /tmp/.NetworkConfig.xml.1 > /opt/vidyo/vm/NetworkConfig.xml
fi
rm -f /tmp/.NetworkConfig.xml*
cp $FILEPATH/NetworkConfig.xml.template /opt/vidyo/vm/


[ -d /opt/vidyo/vidyoproxy ] && rm -rf /opt/vidyo/vidyoproxy

rm -f /opt/vidyo/vidyorouter2/vr2
rm -f /opt/vidyo/vm/VidyoManager
vlog0 "UPDATE: copying VM, VR"
cp -f $VC2PATH/VidyoManager/VidyoManager /opt/vidyo/vm/VidyoManager
cp -f $VC2PATH/VidyoManager/VidyoManagerkey.pem /opt/vidyo/vm/
cp -f $VC2PATH/VidyoManager/Config/NetworkConfig.xsd /opt/vidyo/vm/
cp -f $VC2PATH/VidyoManager/Config/vmconfig.xsd /opt/vidyo/vm/
cp -f $VC2PATH/VidyoRouter/vr2 /opt/vidyo/vidyorouter2/vr2
cp -f $VC2PATH/VidyoRouter/Config/vrconfig.xsd /opt/vidyo/vidyorouter2/

### install the scripts/binary for portal 1+1
mkdir -p /opt/vidyo/ha
mkdir -p /opt/vidyo/ha/cfg
mkdir -p /opt/vidyo/ha/bin
## create folders for 1+N
mkdir -p /opt/vidyo/conf/dr
mkdir -p /opt/vidyo/data/dr
## clean the ha/bin folder
rm -f /opt/vidyo/ha/bin/*
tar xzf  $VC2PATH/VidyoHA/ha.tgz -C /opt/vidyo/ha
mv -f /opt/vidyo/ha/drconf/dr.conf /opt/vidyo/conf/dr
rm -rf /opt/vidyo/ha/drconf/
chmod 644 /opt/vidyo/ha/in/*

### install the VP snmp sub-agent
mkdir -p /opt/vidyo/app/vp_snmp_agent
tar xzf  $VC2PATH/VidyoPortalSNMP/vp_snmp_agent.tgz -C /opt/vidyo/app/vp_snmp_agent
rm -rf /opt/vidyo/app/vp_snmp_agent/lib  > /dev/null 2>&1
rm -rf /opt/vidyo/app/vp_snmp_agent/default/odbc*  > /dev/null 2>&1

mkdir -p /opt/vidyo/app/mariadb/lib
tar xzvf $FILEPATH/mariadb_lib.tgz -C /opt/vidyo/app/mariadb/lib



## install the VPLicenseMgr
mkdir -p /opt/vidyo/app/VPLicenseMgr/bin
cp -f $VC2PATH/VPLicenseMgr/VPLicenseMgr /opt/vidyo/app/VPLicenseMgr/bin

## create the license token file
/opt/vidyo/bin/get_license_info.sh VMSoapUser VMSoapPass > /opt/vidyo/vm/license.token 

## create a dbsync user if not exist
passwd --status dbsync > /dev/null 2>&1
if [ $? -ne 0 ]; then
   vlog0 "UPDATE: creating dbsync user..."
   /usr/sbin/useradd --shell /bin/bash  dbsync  --create-home
   if [ $? -ne 0 ]; then
       vlog0  "WARNING: failed to create dbsync user..."
   fi
else
   passwd -ld dbsync
fi

if [ -d /home/dbsync/.ssh ]; then
   chown -R root:dbsync /home/dbsync/.ssh
   chmod -R 600 /home/dbsync/.ssh/*
   chown -R dbsync:dbsync /home/dbsync/.ssh/id_rsa*
   [ -f /home/dbsync/.ssh/authorized_keys ] && chmod -R 640 /home/dbsync/.ssh/authorized_keys
   chmod 750 /home/dbsync/.ssh
fi

HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf

if [ -f $HA_CONF ]; then
   . $HA_CONF
   if [ "$VIDYO_HA" = "ENABLED" ]; then
      vlog0 "UPDATE: Hot-Standby is enabled..."
   else
      vlog0 "INFORMATION: Hot-Standby is not enabled..."
      /usr/sbin/usermod --shell=/bin/false dbsync > /dev/null 2>&1
   fi
else
   vlog0 "INFORMATION: Hot-Standby is not enabled..."
   /usr/sbin/usermod --shell=/bin/false dbsync > /dev/null 2>&1
fi

##################################################################
# update the sshd_config. Create a match policy on dbsync user.
# Do not allow other machine to login as dbsync unless they have
# the right credentials.
# Allow TcpForwarding only to localhost:3306(needed for ssh tunnel)
##################################################################
for F in /etc/ssh/sshd_config.default /etc/ssh/sshd_config.fips; do
   sed -i '/Match User dbsync/,$d' $F
   cat << EOF >> $F

Match User dbsync
      X11Forwarding no
      AllowTcpForwarding yes
      PermitOpen 127.0.0.1:3306
      PermitOpen [::1]:3306
      RSAAuthentication yes
      PasswordAuthentication no

EOF

done

##################


# 8/22 - do not save the upload folder... we will move it to different location.
#save uploads to be restored later
#if [ -d $WEB_DIRECTORY/upload ] ; then
#    cp -R $WEB_DIRECTORY/upload /tmp
#    RESTORE_UPLOADS=1
#    vlog0 "INFORMATION:  Saving uploads for restoring later"
#fi

rm -rf /var/www/index.html
rm -rf /var/www/portal
rm -rf $WEB_DIRECTORY/portal2.war
rm -rf $WEB_DIRECTORY/portal2

#clean up tomcat working directiry
rm -rf $WEB_DIRECTORY/../work/*

#copy portal files to tomcat
# portal.properties should be static. DB credential in this file is 
# probably not being used. Do not preserve it for now since we are 
# installing a new portal.properties
#if [ -f /usr/local/tomcat/repo/vidyoportal/portal.properties ] ; then
#   cp /usr/local/tomcat/repo/vidyoportal/portal.properties portal.properties.bak
#   vlog0 "UPDATE:  Saving portal.properties"
#fi

if [ -f /usr/local/tomcat/webapps/ROOT/crossdomain.xml ] ; then
   mv /usr/local/tomcat/webapps/ROOT/crossdomain.xml /tmp/crossdomain.xml.bak
   vlog0 "Install VC2:  Saving crossdmain"
else
    if [ ! -f /tmp/crossdomain.xml.bak ]; then
       vlog0 "UPDATE:  creating crossdmain"
       touch /tmp/crossdomain.xml.bak
    fi
fi

rm -rf /usr/local/tomcat/repo
mkdir -p /usr/local/tomcat/repo/vidyoportal
cp -f $FILEPATH/portal.properties /usr/local/tomcat/repo/vidyoportal/

rm -rf /usr/local/tomcat/work/Catalina/localhost/
rm -rf /usr/local/tomcatnp/work/Catalina/localhost/
rm -rf $WEB_DIRECTORY/{admin.war,super.war,ROOT.war,vr2conf.war,vp2conf.war,vm2conf.war,upload.war,portal.war}
rm -rf $WEB_DIRECTORY/{admin,super,ROOT,vr2conf,vp2conf,vm2conf,portal}

### configure priv and non priv tomcat
$FILEPATH/config_tomcatnp.sh

cp -f $FILEPATH/tomcat.multi /etc/init.d/tomcat
cp -f $FILEPATH/portal.tomcat.conf /opt/vidyo/conf.d/tomcat.conf
cp -f $FILEPATH/tomcat.workers.properties /etc/apache2/workers.properties
cp -f $FILEPATH/tomcat.catalina.sh /usr/local/tomcat/bin/catalina.sh
cp -f $FILEPATH/tomcatnp.catalina.sh /usr/local/tomcatnp/bin/catalina.sh

cp -f $FILEPATH/tomcat.server.xml /usr/local/tomcat/conf/server.xml
cp -f $FILEPATH/tomcatnp.server.xml /usr/local/tomcatnp/conf/server.xml

cp -f $FILEPATH/tomcat.context.xml /usr/local/tomcat/conf/context.xml
cp -f $FILEPATH/tomcatnp.context.xml /usr/local/tomcatnp/conf/context.xml

chmod +x /usr/local/tomcat/bin/catalina.sh
chmod +x /usr/local/tomcatnp/bin/catalina.sh

cp -f $FILEPATH/super.war /usr/local/tomcat/webapps/
cp -f $FILEPATH/super.ROOT.war /usr/local/tomcat/webapps/ROOT.war

cp -f $FILEPATH/admin.war /usr/local/tomcatnp/webapps/
cp -f $FILEPATH/admin.ROOT.war /usr/local/tomcatnp/webapps/ROOT.war

/opt/vidyo/bin/tcapp_deployer.sh 

### update the webapps library
mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/lib
mkdir -p /usr/local/tomcat/webapps/super/WEB-INF/lib

mkdir -p /usr/local/tomcatnp/webapps/ROOT/WEB-INF/lib
mkdir -p /usr/local/tomcatnp/webapps/admin/WEB-INF/lib

cp -f /usr/local/tomcatnp/webapps/ROOT/WEB-INF/lib/* /usr/local/tomcat/webapps/super/WEB-INF/lib/
cp -f /usr/local/tomcatnp/webapps/ROOT/WEB-INF/lib/* /usr/local/tomcat/webapps/ROOT/WEB-INF/lib/
cp -f /usr/local/tomcatnp/webapps/ROOT/WEB-INF/lib/* /usr/local/tomcatnp/webapps/admin/WEB-INF/lib/

##### install the vidyo-portal-batch-jobs
if [ -f $VC2PATH/vidyo-portal-batch/build/distributions/vidyo-portal-batch.tar.gz ]; then
   PORTAL_DIR=/opt/vidyo/portal2
   BATCH_DIR=$PORTAL_DIR/vidyo-portal-batch
   if [ -d $BATCH_DIR ]; then
     rm -rf $BATCH_DIR
   fi
   mkdir -p $BATCH_DIR
   vlog0 "Installing vidyo-portal-batch-jobs..."
   tar xzf $VC2PATH/vidyo-portal-batch/build/distributions/vidyo-portal-batch.tar.gz -C $BATCH_DIR
   cp -f /usr/local/tomcatnp/webapps/ROOT/WEB-INF/lib/* /opt/vidyo/portal2/vidyo-portal-batch/lib
   cd $BATCH_DIR/bin
   chmod +x *.sh
fi

#The following two lines provide compatibility with 1.5 bookmarks
#mkdir -p /usr/local/tomcat/webapps/portal
#cp $FILEPATH/index.html /usr/local/tomcat/webapps/portal/.
cp $FILEPATH/logging.properties /usr/local/tomcat/conf/.

#to stop browsers from caching our content
cp -f $FILEPATH/headers.conf /etc/apache2/mods-available/headers.conf
chmod 644 /etc/apache2/mods-available/headers.conf
ln -nfs /etc/apache2/mods-available/headers.conf /etc/apache2/mods-enabled/headers.conf

if [ ! -f /etc/apache2/mods-enabled/headers.load ] ; then
   ln -nfs /etc/apache2/mods-available/headers.load /etc/apache2/mods-enabled/headers.load
fi

# copy ports.conf that we need if the settings have not changed
cp -f $FILEPATH/apacheLogSetting /etc/logrotate.d/apache2

vlog0 "UPDATE: removed 2.2 apache install config.  Need update"

## install mariaDB driver
cp -f $FILEPATH/mariadb-java-client-1.1.9-vidyo.jar /usr/local/tomcat/lib


# create folder for thumbnail
mkdir -p /opt/vidyo/portal2/thumbnail
cp -f $FILEPATH/default_thumbnail.base64 /opt/vidyo/portal2/thumbnail 

# create folder for customization
mkdir -p /opt/vidyo/portal2/customization

update-rc.d -f tomcat_init remove

# Adjust for apache based security 
# mkdir -p ${INSTALL_DIRECTORY}/portal2/security
cp $FILEPATH/mycacerts.cer ${INSTALL_DIRECTORY}/etc/ssl/private/vidyo-cacert.root

# copy the java security modules into place
#
cp $FILEPATH/java.security.fips /usr/lib/jvm/jre/lib/security
cp $FILEPATH/java.security.default /usr/lib/jvm/jre/lib/security

vlog0 "UPDATE: completed copy of java security files to security folder"

# there shouldn't be any misc.key starting 3.4.x

[ -f /opt/vidyo/etc/ssl/private/misc.key ] && rm -f /opt/vidyo/etc/ssl/private/misc.key 
chmod 640 /opt/vidyo/etc/ssl/private/misc.pem
chown root:webapps /opt/vidyo/etc/ssl/private/misc.pem

# generate a unique saml keystore
cp -f $FILEPATH/gensamljks.sh  /opt/vidyo/bin/
/opt/vidyo/bin/gensamljks.sh > /dev/null 2>&1 

# install the script that generates a unique key.. This is needed on a new image 
cp -f $FILEPATH/gen_misc_key.sh  /opt/vidyo/bin/

# install the script that can be used to re-deploy portal web apps...
cp -f $FILEPATH/deploy_portal_webapps.sh  /opt/vidyo/bin/

# install the script that will be used by super to collect logs
cp -f $FILEPATH/ziplogs.sh  /opt/vidyo/bin/

# install the script that will be used to repair database...
cp -f $FILEPATH/check_db.sh  /opt/vidyo/bin/
ln -snf /opt/vidyo/bin/check_db.sh /etc/init.d/check_db
update-rc.d -f check_db remove
update-rc.d check_db defaults 80

# install the script that will block outside access to the SOAP port of VM.
cp -f $FILEPATH/vmsoap_fw.sh  /opt/vidyo/bin/

# install the script that will generate a unique DB password per box.
cp -f $FILEPATH/set_db_password.sh  /opt/vidyo/bin/
ln -snf /opt/vidyo/bin/set_db_password.sh /etc/init.d/set_db_password
update-rc.d -f set_db_password remove
update-rc.d set_db_password defaults 98

##install the script that will dynamically set the innodb_pool_size to 20% of 
## physical memory. 
cp $FILEPATH/update_mysql_conf.sh  /opt/vidyo/bin/
ln -snf /opt/vidyo/bin/update_mysql_conf.sh /etc/init.d/update_mysql_conf
update-rc.d -f update_mysql_conf remove
update-rc.d update_mysql_conf defaults 74

cp $FILEPATH/set_public_fqdn.sh  /opt/vidyo/bin/

### if access.conf is already created then run set_db_password with update parameter
### this is needed due to the configuration on portal-batch is always being replaced 
### during installation.
[ -f /opt/vidyo/etc/db/access.conf ] && /opt/vidyo/bin/set_db_password.sh update

# clean the dbsync folder to make sure no old backup files will be accidentally sync'd by standby node.
vlog0 "INFORMATION: removing old dbsync files..."
rm -f /home/dbsync/*
rm -f /root/ha/*

#no need to restart acpache, see bug 11135 
#/etc/init.d/apache2 start
# do not start tomcat anymore... use the script to manually deploy the war file...
#/etc/init.d/tomcat start
#sleep 120

if [ -f /tmp/crossdomain.xml.bak ] ; then
   mv /tmp/crossdomain.xml.bak /usr/local/tomcat/webapps/ROOT/crossdomain.xml
   chown root:tomcat /usr/local/tomcat/webapps/ROOT/crossdomain.xml
   chmod 640 /usr/local/tomcat/webapps/ROOT/crossdomain.xml
fi

unalias -a

mkdir -p /var/backup

cp $FILEPATH/rc.local /etc
cp $FILEPATH/limits.conf /etc/security
cp -f $FILEPATH/usr.sbin.tcpdump /etc/apparmor.d
aa-complain /etc/apparmor.d/usr.sbin.tcpdump

${INSTALL_DIRECTORY}/StopVC2.sh

cp -f $FILEPATH/VC2_VERSION /opt/vidyo/VC2_VERSION
if [ -f /opt/vidyo/Vendor/DISA ]
then
   echo $(cat /opt/vidyo/VC2_VERSION | sed 's/$/D/g') > /opt/vidyo/VC2_VERSION
fi

sleep 1


##crontab -u www-data $FILEPATH/mailcrontab
## do not set the mail crontab, delete previous crontab for www-data
crontab -r -u www-data >/dev/null 2>&1

## purge mails for root
[ -f /var/mail/root ] && > /var/mail/root


if [ -f /etc/rc1.d/K20tomcat ] ; then
   update-rc.d -f tomcat remove
   update-rc.d tomcat defaults 99
fi

dpkg --purge ntp > /dev/null 2>&1

## allow ethtool to be used by non-privilege user
[ -f /usr/sbin/ethtool ] && chmod +s /usr/sbin/ethtool

#touch /usr/local/tomcat/webapps/upload/nologo.png

[ -f $FILEPATH/updatevrconfig.sh ] && cp -f $FILEPATH/updatevrconfig.sh /opt/vidyo/bin/
[ -f $FILEPATH/updatevrconfig.sh ] && $FILEPATH/updatevrconfig.sh

### update the cronjob to reload the VM license every night.- needed fot portal 1+1
SCRIPT=/opt/vidyo/ha/bin/reload_vm_license.sh
sed -i "/reload_vm_license.sh/d" /etc/crontab
#echo "59 23 * * * root $SCRIPT > /dev/null 2>&1" >> /etc/crontab

### run the admin post-installation script
[ -f /opt/vidyo/adm/admin_post_installer.sh ] && /opt/vidyo/adm/admin_post_installer.sh

vlog0 "UPDATE: admin post installer finished"

# The ports are initialized as virtual hosts (/etc/apache2/sites-enabled)
# If a port is initialized in ports.conf, there is a conflict 
#
> /etc/apache2/ports.conf 


### generate_domain_key_file defined in common-functions.sh
#   perform after initializeSecurity (distribute certificates)
generate_domain_key_file

vlog0 "UPDATE: remove content of /var/www/"

# NOTE: no vPortal app references /var/www ... no php
#
rm -rf /var/www/*

# The command: nss_utils.sh --initialize should only be called once.
# The command creates the /opt/vidyo/etc/nss directory 
# Validate the directory to determine if nss_utils.sh --initialize is required.
#
if [ ! -d /opt/vidyo/etc/nss ] 
then
   # NOTE: portal installation only ...
   #
   /opt/vidyo/bin/nss_utils.sh --initialize
   /opt/vidyo/bin/nss_utils.sh --import_caroot
fi

vlog0 "UPDATE: installing default banners if not exist"
install_default_banners $FILEPATH


FLAG=/opt/vidyo/vidyo_updates/RESET_STUCK_GWVEP
if [ -f $FLAG ]; then
   vlog0 "Removing cronjob for ResetStuckGWVEPs.sh..."
   sed -i '/ResetStuckGWVEPs.sh/d' /etc/crontab
   rm -f $FLAG
fi

update_log_level
update_localvmvrconfig_with_sslinfo

vlog0 "SUCCESS: Checking CDR state from DB..."
$FILEPATH/set_cdr_state.sh

vlog0 "SUCCESS: Checking if VidyoProxy can run on the server..."
$FILEPATH/disable_vproxy.sh

cp -f $FILEPATH/vidyo-javaopts.sh /opt/vidyo/bin/
cp -f $FILEPATH/update_smtp_config.sh /opt/vidyo/bin/

## install the script that monitors the Stuck Conference in ConferenceRecord table.
cp -f $FILEPATH/ResetStuckGWVEPs.sh /opt/vidyo/bin
sed -i '/ResetStuckGWVEPs.sh/d' /etc/crontab
echo "* * * * * root /opt/vidyo/bin/ResetStuckGWVEPs.sh > /dev/null 2>&1" >> /etc/crontab

## install the script that will do the housekeeping for tomcat temp folders...
cp -f $FILEPATH/purge_temp.sh /opt/vidyo/bin
sed -i '/purge_temp.sh/d' /etc/crontab
echo "59 1 * * * root /opt/vidyo/bin/purge_temp.sh  > /dev/null 2>&1" >> /etc/crontab

## install the script that will do the housekeeping on PORTAL_BATCH DB
cp -f $FILEPATH/purge_portal_batch.sh /opt/vidyo/bin
sed -i '/purge_portal_batch.sh/d' /etc/crontab
echo "30 1 1 * * root /opt/vidyo/bin/purge_portal_batch.sh  > /dev/null 2>&1" >> /etc/crontab

cp -f $FILEPATH/purge_tomcat_logs.sh /opt/vidyo/bin
sed -i '/purge_tomcat_logs.sh/d' /etc/crontab
echo "15 23 * * * root /opt/vidyo/bin/purge_tomcat_logs.sh  > /dev/null 2>&1" >> /etc/crontab

#install the auto DB backup on cronjob
cp -f $FILEPATH/create_db_snapshot.sh /opt/vidyo/bin/

sed -i '/create_db_snapshot.sh/d' /etc/crontab
echo "45 * * * * root /opt/vidyo/bin/create_db_snapshot.sh > /dev/null 2>&1" >> /etc/crontab

cp -f $FILEPATH/vsendmail.sh /opt/vidyo/bin/

cp -f $FILEPATH/create_portal_package.sh /opt/vidyo/bin/

## install the new apache2.conf
cp -f $FILEPATH/apache2.conf /etc/apache2/

#update the mime.types
cp -f $FILEPATH/mime.types /etc/

vlog0 "Generating apache customer error html..."
$FILEPATH/gen_error_html.sh
[ -d /opt/vidyo/etc/apache2/error ] && rm -rf /opt/vidyo/etc/apache2/error

vlog0 "updating sudoers file..."

cp -f $FILEPATH/sudoers.portal /etc/sudoers
#cp -f $FILEPATH/tomcat.sudoers.portal.secure /etc/sudoers.d/
cp -f $FILEPATH/tomcat.sudoers.portal.default /etc/sudoers.d/tomcat

#install the sudoers file for dbsync user
cp -f $FILEPATH/sudoers.dbsync /etc/sudoers.d/dbsync

#install the sudoers file for vpbatch user
cp -f $FILEPATH/sudoers.vpbatch /etc/sudoers.d/vpbatch

vlog0 "installing signature validation tool..."
cp -f $FILEPATH/signcheck.sh /opt/vidyo/bin/
cp -f $FILEPATH/vidyoca.crt /opt/vidyo/etc/ssl/certs/
cp -f $FILEPATH/sanitize_client_installation.sh /opt/vidyo/bin/
cp -f $FILEPATH/portal_factory_default.sh /opt/vidyo/bin/
cp -f $FILEPATH/UpdateLicenseCache.sh /opt/vidyo/bin/

##install tomcat watchdog
cp -f $FILEPATH/tomcat_watchdog.sh /opt/vidyo/bin/
ln -snf /opt/vidyo/bin/tomcat_watchdog.sh /etc/init.d/tomcat_watchdog
update-rc.d -f tomcat_watchdog remove
update-rc.d tomcat_watchdog defaults 99

vlog0 "Updating setenv.sh..."
[ -L /usr/local/tomcat/bin/setenv.sh ] && rm -f /usr/local/tomcat/bin/setenv.sh
cp -f $FILEPATH/setenv.sh /usr/local/tomcat/bin/setenv.sh
rm -f /usr/local/tomcat/bin/setenv.sh.fips
rm -f /usr/local/tomcat/bin/setenv.sh.default

vlog0 "Install mysql logrotate conf"
cp -f $FILEPATH/mysql.logrotate /etc/logrotate.d/mysql
MYSQL_LOG=/var/log/mysql
mkdir -p $MYSQL_LOG
mkdir -p $MYSQL_LOG
chown mysql:mysql $MYSQL_LOG
chmod 750 $MYSQL_LOG

## no more priv and nonpriv sudo config
rm -f /etc/sudoers.d/tomcat.sudoers.portal.default
rm -f /etc/sudoers.d/tomcat.sudoers.portal.secure
##rm -f /etc/sudoers.d/vpbatch

chown root:root /etc/sudoers.d/tomcat

#install_authuser $FILEPATH

#if [ -f /opt/vidyo/etc/ssl/private/PRIVILEGED_MODE_DISABLED ]; then
#   cp -f /etc/sudoers.d/tomcat.sudoers.portal.secure /etc/sudoers.d/tomcat
#else
#   touch /opt/vidyo/etc/ssl/private/PRIVILEGED_MODE
#   cp -f /etc/sudoers.d/tomcat.sudoers.portal.default /etc/sudoers.d/tomcat
#fi

chmod 440 /etc/sudoers.d/tomcat
chmod 440 /etc/sudoers.d/dbsync
chmod 440 /etc/sudoers.d/vpbatch
chmod 440 /etc/sudoers
chown root:root /etc/sudoers

vlog0 "about to create vpbatch..."
id -u vpbatch > /dev/null 2>&1
if [ $? -ne 0 ]; then
   useradd vpbatch --shell=/bin/false --create-home > /dev/null 2>&1
   usermod -a -G tomcat vpbatch
else
   vlog0 "vpbatch user already exist. Skipping"
   usermod --shell /bin/false vpbatch > /dev/null 2>&1
fi

adduser vpbatch webapps > /dev/null 2>&1

# purge pwlib
if [ -d /usr/local/lib/pwlib  ]; then
   rm -rf /usr/local/lib/pwlib
   [ -L /usr/local/lib/ptlib ] && rm -f /usr/local/lib/ptlib 
fi

create_ssl_forced_no_redirect

vlog0 "about to update portal file permissions..."
update_file_permissions

if [ ! -x $POST_INSTALL ]
then
   vlog0 "ERROR: cannot execute script $POST_INSTALL"
   exit 6
fi

# Perform all post-install cleanup after successful update
#
if $POST_INSTALL
then
   vlog0 "UPDATE: successfully completed $POST_INSTALL"
else
   local let post_install_status=$?
   vlog0 "ERROR: error status from $POST_INSTALL status=$post_install_status"
fi

#### changes for HA using MySQL replication
DBFREQ_CONF=/opt/vidyo/etc/ha/dbsync.conf
if [ -f $DBFREQ_CONF ]; then
   rm -f $DBFREQ_CONF
   sed -i '/\/opt\/vidyo\/ha\/bin\/create_backup.sh/d' /etc/crontab
fi

vlog0 "Checking for old auto.cnf"
AUTO_CNF=/var/lib/mysql/auto.cnf
if [ -f $AUTO_CNF ]; then
   if grep -q 9381f0c5-19de-11e5-b636-000c291752e9 $AUTO_CNF; then
      vlog0 "found the default auto.cnf... Removing..."
      rm -f $AUTO_CNF
   fi
fi

## create folders for PKI
vlog0 "Creating folders for PKI certs..."
mkdir -p /opt/vidyo/data/VidyoPortal/tenant /opt/vidyo/conf/VidyoPortal/tenant
chown root:root /opt/vidyo/conf/VidyoPortal/tenant
chown -R tomcat:webapps /opt/vidyo/data/VidyoPortal
chmod 775 /opt/vidyo/data/{VidyoPortal,VidyoPortal/tenant}
chmod 755 /opt/vidyo/conf/{VidyoPortal,VidyoPortal/tenant}

## add new startup script before apache_init.sh. This will test the apache vhost during startup
## if the state is pending test. (VPTL-6362)

ln -snf /opt/vidyo/bin/test_vhost_config.sh /etc/init.d/test_vhost_config
update-rc.d -f test_vhost_config remove
update-rc.d test_vhost_config defaults 89


## enable the UNIQUE_ID module in apache.
ln -snf /etc/apache2/mods-available/unique_id.load /etc/apache2/mods-enabled/unique_id.load

#create batch config folder (VPTL-6764)
mkdir -p /opt/vidyo/portal2/batch-config
chown tomcat:vpbatch /opt/vidyo/portal2/batch-config
chmod 750 /opt/vidyo/portal2/batch-config
NOTFY_SERVERS_PROPERTIES=/opt/vidyo/portal2/batch-config/events-notify-servers.properties
if [ ! -f $NOTFY_SERVERS_PROPERTIES ]; then
   cat << EOF > $NOTFY_SERVERS_PROPERTIES
primary.server = 127.0.0.1
primary.server.port = 1234
secondary.server = 127.0.0.1
secondary.server.port = 1235
EOF
   chmod 640 $NOTFY_SERVERS_PROPERTIES 
   chown tomcat:tomcat $NOTFY_SERVERS_PROPERTIES
fi

vlog0 "Purging archives..."
$FILEPATH/purge_archives.sh


## un-hide 1+N Configuration in Admin Console
#sed -i '/FEATURE_DR=/d' /opt/vidyo/conf.d/features.conf
#echo FEATURE_DR=ON >> /opt/vidyo/conf.d/features.conf


# hide it again!!!
FEATURE_DR=
. /opt/vidyo/conf.d/features.conf
if [ "$FEATURE_DR" = ON ]; then
   vlog0 "DR is enabled."
else 
   sed -i '/FEATURE_DR=/d' /opt/vidyo/conf.d/features.conf
fi


##disable appport
vlog0 "Disabling apport..."
[ -f /etc/default/apport ] && sed -i 's/^enabled=.*/enabled=0/g'  /etc/default/apport


## check if MODERN ssl settings is enabled
check_apache_ssl_settings

vlog0 "SUCCESS: finished installation of VidyoConferencing version $UPGRADE_VERSION"

rm -rf /opt/vidyo/Vendor/ENABLE_BETA_FEATURES

/sbin/reboot

vlog0 "INFORMATION: just executed /sbin/reboot, waiting for program exits."

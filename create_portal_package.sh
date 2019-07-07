#!/bin/bash -ex
#######################################################
#Description: This script will build a portal package.
#             This can be executed manually or from bamboo.
#             When executing from bamboo, the build.config
#             will be generated automatically.
#######################################################

####################################################
# Sample build.config
# -------------------
# BUILD_TAG=TAG_VC17_2_0_035
# CLI_TAG=TAG_CLI_3_5_0_006
# BUILD_DEST=17.2.0
# FRAMEWORKS_DIR=/bamboo/FRAMEWORKS
# OPENSSL_VERSION=openssl-1.0.2k-fips-2.0.14
# SDK_RELEASE_DIR=/SDK
# SDK_VERSION=2_1_5_15
# SRTP_VERSION=libsrtp-1.5.3
# VMVR_TAG=TAG_VR_3_4_7_001
# PORTAL_TAG_PREFIX=TAG_VC17_2_0_
# VMVR_DIR=/bamboo/VMVR
# CLI_DIR=/bamboo/CLI/
####################################################

BUILD_CONFIG=build.config

if [ ! -f $BUILD_CONFIG ]; then
   echo "Unable to find build.config!"
   exit 1
fi

#if [ ! -d Products/VC2 ]; then
#   echo "Need to checkout portal code from git"
#   exit 1
#fi

. $BUILD_CONFIG
TOP_DIR=$(pwd)/../..


#make sure we have the CLI and VMVR package
if [ ! -f ${CLI_DIR}/${CLI_TAG}.tgz ]; then
   ## checkout CLI
   echo "Warning!  ${CLI_DIR}/${CLI_TAG}.tgz NOT found...Checking out from git"
   git clone -b $CLI_TAG ssh://git@bitbucket.vidyo.com:7999/hun/huntercli.git $CLI_TAG

   cd $CLI_TAG
   tar czvf ${CLI_TAG}.tgz admin pre_install.sh post_install.sh diagnostic_tool sharedFiles
   cp -f ${CLI_TAG}.tgz $CLI_DIR
   if [ ! -f ${CLI_DIR}/${CLI_TAG}.tgz ]; then
      echo "Failed! unable to find ${CLI_DIR}/${CLI_TAG}.tgz"
      exit 1
   fi
fi

if [ ! -f ${VMVR_DIR}/${VMVR_TAG}.tgz ]; then
   echo "Warning! ${VMVR_DIR}/${VMVR_TAG}.tgz NOT found..."
   exit 1
fi 


cd $TOP_DIR/Products/VC2
VC2_DIR=$(pwd)

## build the clusterip for portal 1+1
cd VidyoHA/clusterip_v2
aclocal
automake --add-missing
autoconf
PREF=$(dirname $(pwd))
./configure --prefix=$PREF
make install
if [ $? -ne 0 ]; then
   echo "Failed to build clusterip/cip_admin !!!"
   error_log clusterip "Failed to build clusterip/cip_admin" "Eric"
   exit 1
fi

cd $PREF
tar czf ha.tgz bin in hssync dr drconf # > /dev/null 2>&1

##build portal apps

cd $VC2_DIR/Admin/web/js/AdminApp
/opt/Sencha/Cmd/6.0.2.14/sencha app upgrade /opt/ext-js

cd $VC2_DIR/Super/web/js/SuperApp

/opt/Sencha/Cmd/6.0.2.14/sencha app upgrade /opt/ext-js

source ~/.profile
. /etc/environment

cd $VC2_DIR
./gradlew clean test release --debug --stacktrace

######### create portal package  ######################
CVSROOT=cvs-build-svc@vidyocvs:/cvs
export CVSROOT

#cd ${bamboo.build.working.directory}/${bamboo.buildNumber}
cd $TOP_DIR


mkdir -p Products/VC2/install/scripts/production

sed  -i "s/%%TAG%%/${BUILD_TAG}/g" Products/VC2/scripts/UpgradePortalDb.sh
sed  -i "s/%%TAG%%/${BUILD_TAG}/g" Products/VC2/scripts/CreatePortal20Db.sh


cd $VC2_DIR
## create new tag with the changes from UpgradePortalDb.sh CreatePortal20Db.sh build.config
git add scripts/{UpgradePortalDb.sh,CreatePortal20Db.sh}
git add build.config
git commit build.config scripts/{CreatePortal20Db.sh,UpgradePortalDb.sh} -m "updating build config for TAG ${BUILD_TAG}"
git tag -f -a ${BUILD_TAG} -m "creating new tag $BUILD_TAG"
git remote add portal $GIT_REPO
git push portal ${BUILD_TAG}
git ls-remote --exit-code --tags portal ${BUILD_TAG}

### checkout from CVS
cvs co Products/SNMP

mkdir -p Products/VC2/VidyoPortalRoot/web/snmp/
cp -f Products/SNMP/VIDYO-SNMP-MIB.txt Products/VC2/VidyoPortalRoot/web/snmp/

## Get rid of CVS
find Products -type d -name CVS -exec rm -rf {} \; > /dev/null 2>&1 || true

cd $TOP_DIR


echo $BUILD_TAG > Products/VC2/install/scripts/production/VC2_VERSION


cp Products/VC2/platform/tomcat/{tomcat.workers.properties,web.xml,tomcat.catalina.sh,tomcatnp.catalina.sh,\
tomcat.server.xml,tomcatnp.server.xml,tomcatnp.32gb.server.xml,tomcatnp.context.xml,tomcat.context.xml,\
mariadb-java-client-1.1.9-vidyo.jar,tomcat.multi} Products/VC2/install/scripts/production/.

cp Products/VC2/platform/my.cnf.template Products/VC2/install/scripts/production/. 
cp Products/VC2/platform/my.cnf.8gb Products/VC2/install/scripts/production/. 
cp Products/VC2/platform/my.cnf.16gb Products/VC2/install/scripts/production/. 
cp Products/VC2/platform/my.cnf.24gb Products/VC2/install/scripts/production/. 


cp Products/VC2/platform/{rc.local,usr.sbin.tcpdump,logging.properties,headers.conf,apacheLogSetting,\
welcomebanner.disa,loginbanner.disa,java.security.default,java.security.fips,apache2.conf,tomcat.sudoers.portal.default,\
tomcat.sudoers.router.default,sudoers.vpbatch,sudoers.dbsync,sudoers.portal,portal.tomcat.conf,router.tomcat.conf,\
vidyoca.crt,default_thumbnail.base64,mycacerts.cer,limits.conf,mime.types} Products/VC2/install/scripts/production/.

cp Products/VC2/scripts/{get_db_info.sh,backup_database.sh,restore_database.sh,update_portal.sh,\
restart_tomcat.sh,portal_tables.sh,CreatePortal20Db.sh,UpgradePortalDb.sh,portal_factory_default.sh,\
updatevrconfig.sh,nss_utils.sh,compare_create_update_db.sh,fix_db_schema.sh,set_cdr_state.sh,\
vidyo-javaopts.sh,vsendmail.sh,gensamljks.sh,gen_misc_key.sh,ziplogs.sh,check_db.sh,deploy_portal_webapps.sh,\
update_smtp_config.sh,ResetStuckGWVEPs.sh,purge_temp.sh,purge_portal_batch.sh,purge_tomcat_logs.sh,vmsoap_fw.sh,\
set_db_password.sh,set_public_fqdn.sh,signcheck.sh,run_portal_sql.sh,purge_archives.sh,StartVC2.sh,StopVC2.sh,\
setupnetwork.sh,sanitize_client_installation.sh,update_mysql_conf.sh,install.sh,vrinstall.sh,common-functions.sh,tomcat_watchdog.sh,UpdateLicenseCache.sh,mysql.logrotate,create_db_snapshot.sh,setenv.sh,config_nr.sh,UpgradePortalDb.sh,cdr_user.sh}  Products/VC2/install/scripts/production/.

cp -R Products/VC2/country-flags Products/VC2/install/scripts/production/.
cp -R Products/VC2/sql Products/VC2/install/scripts/production/.
cp Products/VC2/VidyoPortal/src/portal.properties Products/VC2/install/scripts/production/.
cp Products/VC2/Super/build/libs/super.war Products/VC2/install/scripts/production/.
cp Products/VC2/Admin/build/libs/admin.war Products/VC2/install/scripts/production/.
cp Products/VC2/VidyoRouter2Conf/build/libs/vr2conf.war Products/VC2/install/scripts/production/.
cp Products/VC2/VidyoPortalRoot/build/libs/ROOT.war Products/VC2/install/scripts/production/admin.ROOT.war
cp Products/VC2/SuperService/build/libs/ROOT.war Products/VC2/install/scripts/production/super.ROOT.war

tar -zcvf portal.tar.gz  \
   Products/VC2/vidyo-portal-batch/build/distributions/vidyo-portal-batch.tar.gz \
   Products/VC2/VidyoHA/ha.tgz \
   Products/VC2/install/scripts/production/VC2_VERSION \
   Products/VC2/install/scripts/production/rc.local \
   Products/VC2/install/scripts/production/usr.sbin.tcpdump \
   Products/VC2/install/scripts/production/CreatePortal20Db.sh \
   Products/VC2/install/scripts/production/UpgradePortalDb.sh \
   Products/VC2/install/scripts/production/portal_factory_default.sh \
   Products/VC2/install/scripts/production/updatevrconfig.sh \
   Products/VC2/install/scripts/production/nss_utils.sh \
   Products/VC2/install/scripts/production/StartVC2.sh \
   Products/VC2/install/scripts/production/StopVC2.sh \
   Products/VC2/install/scripts/production/logging.properties \
   Products/VC2/install/scripts/production/portal.properties \
   Products/VC2/install/scripts/production/my.cnf.template \
   Products/VC2/install/scripts/production/my.cnf.8gb \
   Products/VC2/install/scripts/production/my.cnf.24gb \
   Products/VC2/install/scripts/production/my.cnf.16gb \
   Products/VC2/install/scripts/production/tomcat.workers.properties \
   Products/VC2/install/scripts/production/web.xml \
   Products/VC2/install/scripts/production/mariadb-java-client-1.1.9-vidyo.jar \
   Products/VC2/install/scripts/production/headers.conf \
   Products/VC2/install/scripts/production/apacheLogSetting \
   Products/VC2/install/scripts/production/super.war \
   Products/VC2/install/scripts/production/admin.war \
   Products/VC2/install/scripts/production/admin.ROOT.war \
   Products/VC2/install/scripts/production/super.ROOT.war \
   Products/VC2/install/scripts/production/get_db_info.sh\
   Products/VC2/install/scripts/production/backup_database.sh \
   Products/VC2/install/scripts/production/restore_database.sh \
   Products/VC2/install/scripts/production/update_portal.sh \
   Products/VC2/install/scripts/production/restart_tomcat.sh \
   Products/VC2/install/scripts/production/portal_tables.sh \
   Products/VC2/install/scripts/production/setupnetwork.sh \
   Products/VC2/install/scripts/production/UpgradePortalDb.sh \
   Products/VC2/install/scripts/production/limits.conf \
   Products/VC2/install/scripts/production/mycacerts.cer \
   Products/VC2/install/scripts/production/loginbanner.disa \
   Products/VC2/install/scripts/production/welcomebanner.disa \
   Products/VC2/install/scripts/production/java.security.fips \
   Products/VC2/install/scripts/production/java.security.default \
   Products/VC2/install/scripts/production/compare_create_update_db.sh \
   Products/VC2/install/scripts/production/fix_db_schema.sh \
   Products/VC2/install/scripts/production/set_cdr_state.sh \
   Products/VC2/install/scripts/production/vidyo-javaopts.sh \
   Products/VC2/install/scripts/production/vsendmail.sh \
   Products/VC2/install/scripts/production/apache2.conf \
   Products/VC2/install/scripts/production/mime.types \
   Products/VC2/install/scripts/production/gensamljks.sh \
   Products/VC2/install/scripts/production/gen_misc_key.sh \
   Products/VC2/install/scripts/production/ziplogs.sh \
   Products/VC2/install/scripts/production/check_db.sh \
   Products/VC2/install/scripts/production/deploy_portal_webapps.sh \
   Products/VC2/install/scripts/production/update_smtp_config.sh \
   Products/VC2/install/scripts/production/ResetStuckGWVEPs.sh \
   Products/VC2/install/scripts/production/purge_temp.sh \
   Products/VC2/install/scripts/production/purge_portal_batch.sh \
   Products/VC2/install/scripts/production/purge_tomcat_logs.sh \
   Products/VC2/install/scripts/production/vmsoap_fw.sh \
   Products/VC2/install/scripts/production/set_db_password.sh \
   Products/VC2/install/scripts/production/set_public_fqdn.sh \
   Products/VC2/install/scripts/production/tomcat.sudoers.portal.default \
   Products/VC2/install/scripts/production/sudoers.vpbatch \
   Products/VC2/install/scripts/production/sudoers.dbsync \
   Products/VC2/install/scripts/production/sudoers.portal \
   Products/VC2/install/scripts/production/portal.tomcat.conf \
   Products/VC2/install/scripts/production/tomcat.multi \
   Products/VC2/install/scripts/production/tomcat.server.xml \
   Products/VC2/install/scripts/production/tomcatnp.server.xml \
   Products/VC2/install/scripts/production/tomcatnp.32gb.server.xml \
   Products/VC2/install/scripts/production/tomcat.catalina.sh \
   Products/VC2/install/scripts/production/tomcatnp.catalina.sh \
   Products/VC2/install/scripts/production/tomcat.context.xml \
   Products/VC2/install/scripts/production/tomcatnp.context.xml \
   Products/VC2/install/scripts/production/update_mysql_conf.sh \
   Products/VC2/install/scripts/production/signcheck.sh \
   Products/VC2/install/scripts/production/sanitize_client_installation.sh \
   Products/VC2/install/scripts/production/vidyoca.crt \
   Products/VC2/install/scripts/production/run_portal_sql.sh \
   Products/VC2/install/scripts/production/purge_archives.sh \
   Products/VC2/install/scripts/production/install.sh \
   Products/VC2/install/scripts/production/common-functions.sh \
   Products/VC2/install/scripts/production/tomcat_watchdog.sh \
   Products/VC2/install/scripts/production/UpdateLicenseCache.sh \
   Products/VC2/install/scripts/production/mysql.logrotate \
   Products/VC2/install/scripts/production/create_db_snapshot.sh \
   Products/VC2/install/scripts/production/setenv.sh \
   Products/VC2/install/scripts/production/config_nr.sh \
   Products/VC2/install/scripts/production/cdr_user.sh \
   Products/VC2/install/scripts/production/country-flags \
   Products/VC2/install/scripts/production/sql \
   Products/VC2/install/scripts/production/default_thumbnail.base64

tar -zcvf router.tar.gz  \
   Products/VC2/install/scripts/production/VC2_VERSION \
   Products/VC2/install/scripts/production/vr2conf.war \
   Products/VC2/install/scripts/production/setupnetwork.sh \
   Products/VC2/install/scripts/production/updatevrconfig.sh \
   Products/VC2/install/scripts/production/StartVC2.sh \
   Products/VC2/install/scripts/production/StopVC2.sh \
   Products/VC2/install/scripts/production/logging.properties \
   Products/VC2/install/scripts/production/rc.local \
   Products/VC2/install/scripts/production/tomcat.multi \
   Products/VC2/install/scripts/production/tomcat.workers.properties \
   Products/VC2/install/scripts/production/web.xml \
   Products/VC2/install/scripts/production/headers.conf \
   Products/VC2/install/scripts/production/apacheLogSetting \
   Products/VC2/install/scripts/production/limits.conf \
   Products/VC2/install/scripts/production/mycacerts.cer \
   Products/VC2/install/scripts/production/loginbanner.disa \
   Products/VC2/install/scripts/production/welcomebanner.disa \
   Products/VC2/install/scripts/production/update_portal.sh \
   Products/VC2/install/scripts/production/restart_tomcat.sh \
   Products/VC2/install/scripts/production/sudoers.portal \
   Products/VC2/install/scripts/production/router.tomcat.conf \
   Products/VC2/install/scripts/production/tomcat.sudoers.router.default \
   Products/VC2/install/scripts/production/signcheck.sh \
   Products/VC2/install/scripts/production/vrinstall.sh \
   Products/VC2/install/scripts/production/common-functions.sh \
   Products/VC2/install/scripts/production/vidyoca.crt \
   Products/VC2/install/scripts/production/purge_archives.sh
   

mkdir -p VidyoPortal VidyoRouter
mkdir -p {VidyoPortal,VidyoRouter}/Products/HunterInstall/SharedInstall
mkdir -p {VidyoPortal,VidyoRouter}/Products/HunterInstall/hunter
tar xzvf portal.tar.gz -C VidyoPortal
tar xzvf router.tar.gz -C VidyoRouter

tar xzvf ${CLI_DIR}/${CLI_TAG}.tgz -C VidyoPortal/Products/HunterInstall/SharedInstall
tar xzvf ${CLI_DIR}/${CLI_TAG}.tgz -C VidyoRouter/Products/HunterInstall/SharedInstall
tar xzvf ${VMVR_DIR}/${VMVR_TAG}.tgz -C VidyoPortal
tar xzvf ${VMVR_DIR}/${VMVR_TAG}.tgz -C VidyoRouter

cd VidyoPortal
mv -f Products/VC2/install/scripts/production/install.sh Products/HunterInstall/hunter
tar czvf ../${BUILD_TAG}.tgz Products
cd ../VidyoRouter
mv -f Products/VC2/install/scripts/production/vrinstall.sh Products/HunterInstall/hunter
tar czvf ../${BUILD_TAG}-VR64.tgz Products
cd ..
vst ${BUILD_TAG}.tgz ${BUILD_TAG}-G2signed
gzip --suffix=".vidyo" ${BUILD_TAG}-G2signed

vst ${BUILD_TAG}-VR64.tgz ${BUILD_TAG}-VR64-G2signed
gzip --suffix=".vidyo" ${BUILD_TAG}-VR64-G2signed
mv -f $BUILD_TAG*.vidyo /builds/${BUILD_DEST}

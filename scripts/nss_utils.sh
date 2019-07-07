#!/bin/bash
#####################################################################
# Filename: nss_utils.sh                               
# Description: Utilities for manipulating NSS database.
# Date Created: 11-27-2012
# Modification History:
# 11-27-2012 - Initial coding (Eric)
#####################################################################

JRE_HOME=/usr/lib/jvm/jre/lib
VIDYO_NSS=/opt/vidyo/etc/nss
NSS_CFG=$JRE_HOME/security/pkcs11.cfg
VIDYO_NSS_CFG=$VIDYO_NSS/pkcs11.cfg
PASSWORD=v1dy0123
PASSWORD_FILE=$VIDYO_NSS/nss.pwd
DB_LOCATION=$VIDYO_NSS/fipsdb
JKS=$JRE_HOME/security/cacerts
VIDYO_JKS=/opt/vidyo/etc/java/cacerts
TCENVDIR=/opt/vidyo/etc/tomcat/env


update_keystore_permission()
{
   chmod 660 $DB_LOCATION/*.db
   chown root:webapps $DB_LOCATION/*.db
   chmod 660 $VIDYO_JKS
   chown root:webapps $VIDYO_JKS
}

import_cacert()
{
   local TMPCERT
   local CERTS
   local CTR
   local FIPS

   if [ ! -f /opt/vidyo/etc/ssl/certs/cacert.root ] ; then
      logger -t "nss_utils.sh"  "WARNING! Importing cacert to NSS module. cacert.root not found!"
      exit 1
   fi

   mkdir -p /opt/vidyo/etc/java

   ## import only when the timestamp of the cacert.root is newer that the jks file...
   sleep 1

   LAST_UPDATE_CAROOT=$(stat --printf=%Y /opt/vidyo/etc/ssl/certs/cacert.root)
   if [ -f $VIDYO_JKS ]; then
      LAST_UPDATE_JRE=$(stat --printf=%Y $VIDYO_JKS)
      if [ $LAST_UPDATE_CAROOT -le $LAST_UPDATE_JRE ]; then
         logger -t "nss_utils.sh"  "/opt/vidyo/etc/ssl/certs/cacert.root is not newer than $VIDYO_JKS"
         exit 1
      fi 
   fi

   logger -t "nss_utils.sh"  "Importing /opt/vidyo/etc/ssl/certs/cacert.root..."

   if check_fips > /dev/null 2>&1 ; then 
      FIPS=true
   else
      FIPS=false
   fi

   initialize
   
   modutil -fips $FIPS -dbdir $DB_LOCATION -force

   CTR=0
   CACERTS=$(cat /opt/vidyo/etc/ssl/certs/cacert.root)
   tmpcert=/tmp/tmpcert.$$
   rm -f $JKS
   rm -f $VIDYO_JKS

   for C in $CACERTS; do
      CERTS=/tmp/certs.crt
      if [[ "$C" =~ "-----BEGIN" ]]; then
         START=yes
         END=no
         > $CERTS
         echo "-----BEGIN CERTIFICATE-----" >> $CERTS

         continue
      fi
      if [[ "$C" =~ "-----END" ]]; then
         START=no
         END=yes
         echo "-----END CERTIFICATE-----" >> $CERTS
         #openssl x509 -in $CERTS -noout -issuer 
         #echo 
         ((CTR++))
         NICKNAME=CA${CTR}
         certutil -d $DB_LOCATION -A -n $NICKNAME -t CT,C,C -a -i $CERTS -f $PASSWORD_FILE
         keytool -importcert -keystore $VIDYO_JKS -storetype JKS -storepass $PASSWORD -file $CERTS -trustcacerts -alias $NICKNAME -noprompt
         continue
      fi

      if [[ "$C" =~ "CERTIFICATE-----" ]]; then
         continue
      fi

      echo $C >> $CERTS
   done

   rm -f $JKS
   rm -f $CERTS

   # For SAML customers, make cacerts visible in /usr/lib/vm/jre/lib/security
   #
   ln -s $VIDYO_JKS $JKS > /dev/null 2>&1
   logger -t "nss_utils.sh"  "Importing /opt/vidyo/etc/ssl/certs/cacert.root...DONE"
}

initialize()
{
   [ -d $DB_LOCATON ] && rm -rf $DB_LOCATION 

   mkdir -p $VIDYO_NSS
   ## create the password file
   echo $PASSWORD > $PASSWORD_FILE

   ## create the NSS_CONFIG file
   cat  << EOF > $VIDYO_NSS_CFG
name = NSS
nssLibraryDirectory = /usr/lib/nss
nssSecmodDirectory = $DB_LOCATION
nssDbMode = readWrite
nssModule = fips
# Uncomment lines below for troubleshooting.
#showInfo = true
#nssUseSecmod = true

EOF

   ### create a softlink from java folder to vidyo folder
   ln -s $VIDYO_NSS_CFG $NSS_CFG > /dev/null 2>&1

   ln -s /usr/lib/libnss3.so /usr/lib/nss/libnss3.so > /dev/null 2>&1

   ## create the NSS database
   CURDIR=$(pwd)
   mkdir -p $DB_LOCATION
   cd $DB_LOCATION


   certutil -N -d . -f $PASSWORD_FILE

   # For SAML customers, make cacerts visible in /usr/lib/vm/jre/lib/security
   #
   if [ ! -h $JKS ]; then
      ln -s $VIDYO_JKS $JKS > /dev/null 2>&1
   fi
}

java_keystore()
{
   mkdir -p $TCENVDIR
   if [ "$1" = FIPS ]; then
      ### create keystore configuration
      cat << EOF > $TCENVDIR/01_keystore
umask 0066
#DEBUG_CATALINA_FIPS=-Djavax.net.debug=ssl,keymanager,handshake \

PASSWORD=\$(cat $PASSWORD_FILE)

CATALINA_OPTS="-Djavax.net.ssl.keyStore="NONE" \\
      -Djavax.net.ssl.keyStoreType="PKCS11" \\
      -Djavax.net.ssl.trustStoreType="PKCS11" \\
      -Djavax.net.ssl.keyStorePassword="\$PASSWORD" \\
      -Djavax.net.ssl.trustStorePassword="\$PASSWORD" \\
      -Djavax.net.ssl.trustStoreProvider="SunPKCS11-NSS" \\
      -Djavax.net.ssl.keyStoreProvider="SunPKCS11-NSS" \$DEBUG_CATALINA_FIPS"
EOF
   else
      cat << EOF > $TCENVDIR/01_keystore
umask 0066
#DEBUG_CATALINA_FIPS=-Djavax.net.debug=ssl,keymanager,handshake

PASSWORD=\$(cat /opt/vidyo/etc/nss/nss.pwd)

CATALINA_OPTS="-Djavax.net.ssl.trustStore="/opt/vidyo/etc/java/cacerts" \
      -Djavax.net.ssl.trustStorePassword="$PASSWORD" \$DEBUG_CATALINA_FIPS"
EOF
   fi

   chown -R root:webapps $TCENVDIR
   chmod 770 $TCENVDIR
   chmod 640 $TCENVDIR/*
}

set_fips()
{
   local FIPS
   FIPS=$1

   rm -f $JRE_HOME/security/java.security
 
   if [ "$FIPS" = "true" ]; then
      ln -s $JRE_HOME/security/java.security.fips  $JRE_HOME/security/java.security
      java_keystore FIPS
   else
      ln -s $JRE_HOME/security/java.security.default  $JRE_HOME/security/java.security
      java_keystore NOFIPS
   fi
   modutil -fips $FIPS -dbdir $DB_LOCATION -force
}

check_fips()
{
   modutil -chkfips true -dbdir $DB_LOCATION 
   return $?
}

listmodule()
{
   modutil -list -dbdir $DB_LOCATION
}

listcert()
{
   certutil -L -d $DB_LOCATION 
}


showx509()
{
   if [ -z "$1" ]; then
      echo "Usage: nss_utils.sh showx509 <Certificate Nickname>"
      exit 1
   fi
   certutil -L -d $DB_LOCATION -a -n "$1"
}

showx509text()
{
   if [ -z "$1" ]; then
      echo "Usage: nss_utils.sh showx509 <Certificate Nickname>"
      exit 1
   fi
   certutil -L -d $DB_LOCATION  -n "$1"
}

showallx509text()
{
   CAS=$(listcert | grep -e "CA" | awk '{print $1}')
   for c in $CAS; do
      showx509text $c
   done
}

help()
{
   cat << EOF

Usage: nss_utils.sh [OPTION]
OPTION:
  --initialize      - create a new NSS database. Warning!!! Existing NSS DB will be deleted.
  --fips            - enable FIPS on NSS module.
  --nofips          - disable FIPS on NSS module.
  --checkfips       - 
  --list            - list the certificates inside the NSS database.
  --listmodule      - list the content of NSS module.
  --showx509        - show the content of the certifate in x509 format.
  --showx509text    - show the content of the certifate in text format.
  --showallx509text - show all the contents of the certifate in text format.
  --import_caroot   - import /opt/vidyo/etc/ssl/certs/cacert.root into NSS database.
  --help            - display this.
   
EOF
   exit  0
}

logger -t "nss_utils.sh" "executed - $*"

case "$1" in
   --initialize)
      initialize
   ;;
   --import_caroot)
      import_cacert
      update_keystore_permission
   ;;
   --fips)
      set_fips true
      update_keystore_permission

   ;;
   --nofips)
      set_fips false
      update_keystore_permission
   ;;
   --checkfips)
     check_fips
   ;;
   --listmodule)
      listmodule
   ;;
   --list)
      listcert
   ;;
   --showx509text)
      showx509text "$2"
   ;;
   --showx509)
      showx509 "$2"
   ;;
   --showallx509text)
      showallx509text
   ;;
   *)
   help
   ;;
esac

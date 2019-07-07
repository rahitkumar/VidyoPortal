#!/bin/bash
##########################################################
# Filename: drconfig.sh
# Description: CLI for configuring/maintenance of Portal
#             Disaster Recovery.
# drconfig.sh <action> 
###############################################################

ACTIONS="NEW MODIFY ENABLE DISABLE START_MAINTENANCE MAINTENANCE_START_TIME \
         STOP_MAINTENANCE READ_CONF GEN_SSHKEY EXPORT_PUBKEY \
         IMPORT_PUBKEY GEN_AUTH_TOKEN IMPORT_AUTH_TOKEN EXPORT_AUTH_TOKEN READ_AUTH_TOKEN \
         ENABLE_FORCE_ACTIVE DISABLE_FORCE_ACTIVE GET_FORCE_ACTIVE_STATUS \
         SET_PUB_FQDN SET_CLUSTER_REGISTRATION_PORT"

PROG=$(basename $0)
DR_CONF=/opt/vidyo/conf/dr/dr.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
HA_CONF_IN=/opt/vidyo/ha/in/clusterip.conf.in
NODE_CONF_IN=/opt/vidyo/ha/in/node.conf.in
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
GET_STATE=/opt/vidyo/ha/dr/get_state.sh

[ -f $DR_CONF ] || exit 1
[ -f $HA_CONF ] && . $HA_CONF

. $DR_CONF
. $LOG_FUNCS

NODE_CONF=$LOCAL_NODE_CONF
SSHDIR=/home/${DBSYNC_USER}/.ssh

mkdir -p /opt/vidyo/data/dr/local

force_system_id()
{
   local VMCONF=/opt/vidyo/vm/localvmconfig
   local VRCONF=/opt/vidyo/vidyorouter2/localvrconfig

   [ -f $VMCONF ] || return 1
   [ -f $VRCONF ] || return 1
   [ -f /opt/vidyo/VM_SYSTEM_ID ] || return 1
   [ -f /opt/vidyo/VR_SYSTEM_ID ] || return 1

   if [ "$1" = true ]; then
      VmId=
      VrId=
      eval $(grep "VmId=" $VMCONF)
      if [ -z "$VmId" ]; then
         VMID=$(cat /opt/vidyo/VM_SYSTEM_ID)
         echo VmId=$VMID >> $VMCONF
      fi

      eval $(grep "VrId=" $VRCONF)
      if [ -z "$VrId" ]; then
         VRID=$(cat /opt/vidyo/VR_SYSTEM_ID)
         echo VrId=$VRID >> $VRCONF
      fi
   else
      sed -i '/VmId=/d' $VMCONF
      sed -i '/VrId=/d' $VRCONF
   fi
}

maintenance_start_time()
{
   [ -f $MAINT_FLAG ] || return
   local EPOCH=$(stat  --format="%Z" $MAINT_FLAG)
   date -d @$EPOCH
}

start_maintenance()
{
   vlog3 "start maintenance initiated..."
   rm -f /usr/local/tomcat/webapps/ROOT/VidyoPortalStatusService.html
   touch $MAINT_FLAG
}

stop_maintenance()
{
   vlog3 "stop maintenance initiated..."

   vlog3  "Removing ACTIVE sites previous configuration..."
   rm -rf $ACTIVE_NODE_CONF


   if [ ! -f /etc/mysql/my.cnf ]; then
      vlog3 "generating mysql configurion for DB replication..."
      #create new mysql configuration
      [ -x /opt/vidyo/ha/hssync/update_mycnf.sh ] && /opt/vidyo/ha/hssync/update_mycnf.sh > /dev/null 2>&1
      [ -x /opt/vidyo/ha/hssync/create_repl_user.sh ] && /opt/vidyo/ha/hssync/create_repl_user.ssh > /dev/null 2>&1
      /etc/init.d/mysql restart > /dev/null 2>&1
   fi

   rm -f $MAINT_FLAG
}

gen_sshkey()
{
   KEYGEN=/opt/vidyo/ha/bin/ha-keygen.sh
   if [ "$1" = "--force" ]; then
      rm -f $SSHDIR/id_rsa*
   fi
   ## delete the old pubkey to authorized_keys file before appending a new one.
   if [ -f $SSHDIR/id_rsa.pub ]; then
      OLDKEY=$(cat ${SSHDIR}/id_rsa.pub | awk '{print $2}')
      TEMPFILE=$(mktemp --suffix=.pubkey) 
      grep -v $OLDKEY $SSHDIR/authorized_keys > $TEMPFILE
      mv -f $TEMPFILE $SSHDIR/authorized_keys

   fi
   $KEYGEN dbsync
   cat $SSHDIR/id_rsa.pub >> $SSHDIR/authorized_keys
}

export_pubkey()
{
   if [ -f ${SSHDIR}/id_rsa.pub ]; then
      cat ${SSHDIR}/id_rsa.pub | openssl enc -aes-256-cbc -a -md sha1 -pass file:/opt/vidyo/vm/VidyoManagerkey.pem | tr '\n' ' '
      echo
      return 0
   fi
   return 1
}

import_pubkey()
{
   local TMPKEY=$(mktemp)
   local RC=0
   if [ -f ${SSHDIR}/id_rsa.pub ]; then
      read KEYS 
      echo $KEYS | tr ' ' '\n' | openssl enc -aes-256-cbc -d -a -md sha1 -pass file:/opt/vidyo/vm/VidyoManagerkey.pem  > $TMPKEY
      cp $TMPKEY /tmp/abc.key
      if [ $? -eq 0 ]; then
         PUBKEY=$(cat $TMPKEY | awk '{print $2}')
         ## do not append if this key is already existing on the authorized_key file
         if grep -q $PUBKEY $SSHDIR/authorized_keys; then
            echo -e "\nSSH key already exists!\n"
         else
            cat $TMPKEY >> $SSHDIR/authorized_keys
         fi
      else
         RC=1
      fi
      rm -f $TMPKEY
      return $RC
   fi
   return 1
}

display_actions()
{
   echo "$PROG <action>"
   echo "actions:"
   for A in $ACTIONS; do
      echo "  $A"
   done
}


new_config()
{
   if [ $# -ne 9 ]; then
      echo invalid arguments:
      echo "<node id> <display name> <data sync ip> <public ip> <Public FQDN> <ADC address> <no_active_timeout> <max_force_active> <registerport>"
      return 1
   fi 

   vlog3 "generating new DR configuration..."
   vlog3 "args [$*]"
   echo "## Generated on $(date)..." > $NODE_CONF
   sed "s/%NODEID%/$1/g;
        s/%DISPLAYNAME%/$2/g;
        s/%DATASYNCIP%/$3/g;
        s/%PUBLICIP%/$4/g;
        s/%PUBLICFQDN%/$5/g;
        s/%ADCADDRESS%/$6/g;
        s/%NOACTIVETIMEOUT%/$7/g;
        s/%MAXFORCEACTIVE%/$8/g; 
        s/%REGISTERPORT%/$9/g" $NODE_CONF_IN >> $NODE_CONF

   if [ ! -f /etc/mysql/my.cnf ]; then
      #create new mysql configuration
      vlog3 "generating mysql configurion for DB replication..."
      [ -x /opt/vidyo/ha/hssync/update_mycnf.sh ] && /opt/vidyo/ha/hssync/update_mycnf.sh
      [ -x /opt/vidyo/ha/hssync/create_repl_user.sh ] && /opt/vidyo/ha/hssync/create_repl_user.sh
   fi
}

gen_auth_token()
{
   mkdir -p $DATA_DIR
   </dev/urandom tr -dc '[:alnum:]' | head -c64 > $AUTH_TOKEN
   chown root:root $AUTH_TOKEN
   chmod 600 $AUTH_TOKEN
   sudo -u tomcat  touch /home/tomcat/CLEAR_CACHES
}

## note: export auth will require password to be entered on stdin
## arg1 file name of the password.
export_auth_token()
{
   openssl enc -aes-256-cbc -md sha256 -a -in $AUTH_TOKEN -pass file:$1
   return $?
}

## note: import auth will require password to be entered on stdin
## arg1 - file containing the password 
## arg2 - file containing the encrpted token
import_auth_token()
{
   TMPFILE=$(mktemp)
   [ $# -eq 2 ] || return 1
   openssl enc -aes-256-cbc -md sha256 -d -a -in $2  -pass file:$1 > $TMPFILE 2> /dev/null
   RC=$?
   if [ $RC -eq 0 ]; then
      mv -f $TMPFILE $AUTH_TOKEN
      sudo -u tomcat  touch /home/tomcat/CLEAR_CACHES
   else
      rm -f $TMPFILE
   fi
   return $RC
}

get_auth_token_hash()
{
   if [ -f $AUTH_TOKEN ]; then
      cat $AUTH_TOKEN | sha1sum | awk '{print $1}' 
      return 0
   else
      return 1
   fi
}

enable_dr()
{
   vlog3 "Enabling DR..."
   sed 's/^VIDYO_HA=.*/VIDYO_HA=ENABLED/g;
        s/%HA_MODE%/DR/g' $HA_CONF_IN > $HA_CONF
   /usr/sbin/usermod --shell=/bin/bash dbsync > /dev/null 2>&1
   force_system_id true
}

disable_dr()
{
   #sed -i 's/^VIDYO_HA=.*/VIDYO_HA=DISABLED/g;
   #        s/^HA_MODE=.*/HA_MODE=%HA_MODE%/g' $HA_CONF
   vlog3 "Disabling DR..."
   rm -f $HA_CONF
   rm -f /usr/local/tomcat/webapps/ROOT/VidyoPortalStatusService.html
   /usr/sbin/usermod --shell=/bin/false dbsync > /dev/null 2>&1
   force_system_id false
}


update_public_fqdn()
{
   [ -f $NODE_CONF ] || return 1
   
   sed -i "s/PUBLICFQDN=.*/PUBLICFQDN=$1/g" $NODE_CONF
   return $?
}


get_force_active_status()
{
   local STATE=$($GET_STATE)
   local PRE_ACTIVE_THRESHOLD=0
   if [ -f $FORCE_ACTIVE_FLAG ]; then
      BORN=$(stat --format=%Y $FORCE_ACTIVE_FLAG)
      NOW=$(date +%s)
      ((AGE=NOW-BORN))
      MAXFORCEACTIVE=600
      [ -f $NODE_CONF ] && . $NODE_CONF
      if [ $AGE -gt $MAXFORCEACTIVE ]; then
         if [ "$STATE" = PRE_ACTIVE ]; then
            ((PRE_ACTIVE_THRESHOLD=AGE-MAXFORCEACTIVE))
            if [ $PRE_ACTIVE_THRESHOLD -gt 60 ]; then
               vlog3 "PRE_ACTIVE state took TOO LONG while in FORCE ACTIVE. Removing from force ACTIVE"
               rm -f $FORCE_ACTIVE_FLAG
               echo DISABLED
            fi
            ((MOD=AGE%2))
            if [ $MOD -eq 0 ]; then
               vlog3 "Max force active time reached while site is in PRE_ACTIVE state. [$PRE_ACTIVE_THRESHOLD]"
            fi
         else
            vlog3 "Max force active time reached. Removing from force ACTIVE"
            rm -f $FORCE_ACTIVE_FLAG
            echo DISABLED
         fi
      else
         echo ENABLED
      fi

   else
      echo DISABLED
   fi
}

case "$1" in 
   NEW)
      new_config "$2" "$3" "$4" "$5" "$6" "$7" "$8" "$9" "${10}"
   ;;
   MODIFY)
   ;;
   ENABLE)
      enable_dr
   ;;
   DISABLE)
      disable_dr
   ;;
   MAINTENANCE_START_TIME)
      maintenance_start_time
   ;;
   START_MAINTENANCE)
      start_maintenance
   ;;
   STOP_MAINTENANCE)
      stop_maintenance
   ;;
   READ_CONF)
      [ -f $NODE_CONF ] && cat $NODE_CONF
   ;;
   GEN_SSHKEY)
      gen_sshkey $2
   ;;
   EXPORT_PUBKEY)
      export_pubkey 
   ;;
   IMPORT_PUBKEY)
      import_pubkey 
   ;;
   READ_AUTH_TOKEN)
      [ -f $AUTH_TOKEN ] && cat $AUTH_TOKEN || exit 1
   ;;
   GEN_AUTH_TOKEN)
      gen_auth_token
   ;;
   GET_AUTH_TOKEN_HASH)
      get_auth_token_hash
   ;;
   EXPORT_AUTH_TOKEN)
      export_auth_token $2
   ;;
   IMPORT_AUTH_TOKEN)
      import_auth_token $2 $3
   ;;
   ENABLE_FORCE_ACTIVE)
      touch $FORCE_ACTIVE_FLAG
   ;;
   DISABLE_FORCE_ACTIVE)
      rm -f $FORCE_ACTIVE_FLAG
   ;;
   GET_FORCE_ACTIVE_STATUS)
      get_force_active_status
   ;;
   SET_PUB_FQDN)
      update_public_fqdn $2
   ;;
   SET_CLUSTER_REGISTRATION_PORT)
      REG_PORT=$(echo $2 | tr -cd '[:digit:]')
      [ -z "$REG_PORT" ] && exit 1
      [ "$2" = "$REG_PORT" ] || exit 1
      cat << EOF > /opt/vidyo/etc/ssl/private/VidyoPortalStatusService.vidyohost
VidyoPortalStatusService|eth0|$REG_PORT|Y|N
VidyoPortalStatusService|eth0|80|N|N
EOF
      /opt/vidyo/bin/apache_init.sh  > /dev/null 2>&1 || /bin/true
      /etc/init.d/apache2 reload  > /dev/null 2>&1 || /bin/true
   ;;
   *)
      display_actions
   ;;
esac



#!/bin/bash
##########################################################
# Description: Checks the health status of the following #
#              portal application. Exit code is 0 when   #
#              the node is fine.                         #
# Following Process to be checked                        # 
#   apache, mysql, tomcat, vidyomanager                  #
#                                                        #
##########################################################

LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh
DR_CONF=/opt/vidyo/conf/dr/dr.conf
COMMON_HA_FUNC=/opt/vidyo/ha/bin/common-funcs
VPSTATUS=/opt/vidyo/ha/dr/dr_vidyoportal_status.sh

. /opt/vidyo/bin/vidyologger.sh

[ -f $DR_CONF ] || exit 1

. $DR_CONF

. $COMMON_HA_FUNC


check_server_apps()
{
   if [ ! -f $IGNORE_APACHE ]; then
      if ! is_apache_running; then
         vlog3 "**** Warning **** Apache is not running..."
         return 1
      fi
   fi

   if [ ! -f $IGNORE_TOMCAT ]; then
      if ! is_tomcat_running; then
         vlog3 "**** Warning **** tomcat is not running..."
         return 2
      fi
   fi

   if [ ! -f $IGNORE_MYSQL ]; then
      if ! is_mysql_running; then
         vlog3 "**** Warning **** MySQL is not running..."
         return 3
      fi
   fi

   if [ ! -f $IGNORE_VIDYOMANAGER ]; then
      if ! is_vm_running; then
         vlog3 "**** Warning **** VidyoManager is not running..."
         return 4
      fi
   fi

   return 0
}


APACHE=1
TOMCAT=1
MYSQL=1
VM=1
check_server_apps
RC=$?
case $RC in
  1) APACHE=0
  ;;
  2) TOMCAT=0
  ;;
  3) MYSQL=0
  ;; 
  4) VM=0
  ;;
esac

#vlog3 $VPSTATUS "${TOMCAT}${APACHE}${MYSQL}${VM}"
$VPSTATUS "${TOMCAT}${APACHE}${MYSQL}${VM}"

exit $RC

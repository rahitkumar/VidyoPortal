#!/bin/bash

DR_CONF=/opt/vidyo/conf/dr/dr.conf
LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh

[ -f $DR_CONF ] || exit 1
. $DR_CONF

ACTIVE_NODE_CONF_TMP=${ACTIVE_NODE_CONF}.tmp
WHOISACTIVE=/opt/vidyo/ha/dr/whoisactive.sh
REGPROG=/opt/vidyo/ha/dr/register


[ -f $LOCAL_NODE_CONF ] || exit 1

. $LOCAL_NODE_CONF
. $LOG_FUNCS

. /opt/vidyo/adm/myfunctions.sh

mkdir -p $(dirname $ACTIVE_NODE_CONF)

#determine the IP of the active site
ACTIVE_SITE_IP=$($WHOISACTIVE)
if [ $? -ne 0 ]; then
   vlog3 "Failed to determine the ACTIVE site's IP!!!"
   exit 1
fi

if [ "$ACTIVE_SITE_IP" = "$PUBLICIP" ]; then
   vlog3 "SYNC-NOW is not allowed on ACTIVE site!!!"
   exit 1
fi

NODE_VERSION=$(cat /opt/vidyo/VC2_VERSION)

IPV4=
## just get the 1st IP on the list
eval $(ifc2ip eth0 | awk '{ print $1}')
NATIVE_ETH0_IP=$IPV4

IPV4=
NATIVE_ETH1_IP=
eval $(ifc2ip eth1 | awk '{ print $1}')
[ -n "$IPV4" ] && NATIVE_ETH1_IP="-I $IPV4"

if [ -f $REPLICATION_STATUS ]; then
   REPL_STATUS=$(cat $REPLICATION_STATUS)
else
   REPL_STATUS=:::::
fi

[ -n "$REGISTERPORT" ] && HTTP_PORT=$REGISTERPORT

vlog3 "Registering nodeID: $NODEID, nodeName:$DISPLAYNAME to $ACTIVE_SITE_IP:$HTTP_PORT"


BASIC_AUTH=
if [ -f $AUTH_TOKEN ]; then
   TOKEN=$(cat $AUTH_TOKEN)
   BASIC_AUTH="-a  $(echo -n "$AUTH_USER:$TOKEN"|base64|tr -d '\n')"
fi

#echo $REGPROG $BASIC_AUTH -n "$NODEID" \
#         -N "$NODE_VERSION" \
#         -d "$DISPLAYNAME" \
#         -p "$PUBLICIP" \
#         -i $NATIVE_ETH0_IP $NATIVE_ETH1_IP \
#         -A $ACTIVE_SITE_IP \
#         -r $REPL_STATUS \
#         -P $HTTP_PORT \
#         -t $REGISTER_TIMEOUT \
#          $SECURED > /tmp/aaa
         
$REGPROG $BASIC_AUTH -n "$NODEID" \
         -N "$NODE_VERSION" \
         -d "$DISPLAYNAME" \
         -p "$PUBLICIP" \
         -i $NATIVE_ETH0_IP $NATIVE_ETH1_IP \
         -A $ACTIVE_SITE_IP \
         -r $REPL_STATUS \
         -P $HTTP_PORT \
         -t $REGISTER_TIMEOUT \
          $SECURED > $ACTIVE_NODE_CONF_TMP
RC=$?         
RET=0
if [ $RC -eq 0 ]; then
   STATUSCODE=
   ERROR=
   . $ACTIVE_NODE_CONF_TMP
   if [ "$STATUSCODE" = 200 ]; then
      if [ -n "$ERROR" ]; then
         RET=1
         vlog3 "Warning! Node registration: Error: $ERROR"
      else
         mv -f $ACTIVE_NODE_CONF_TMP $ACTIVE_NODE_CONF
      fi
   else
      if [ "$STATUSCODE" = 401 ]; then
         vlog3 "Warning! Node registration status code: $STATUSCODE. Please check DR Auth Key!!!"
         RET=0
      else
         vlog3 "Warning! Node registration status code: $STATUSCODE"
         RET=1
      fi
   fi
else
   vlog3 "Error! Failed to register nodeID: $NODEID, nodeName:$DISPLAYNAME"
   RET=1
fi

exit $RET

#!/bin/bash
TOMCAT_CONF=/opt/vidyo/conf.d/tomcat.conf
 
[ -f $TOMCAT_CONF ] || exit 1
 
. $TOMCAT_CONF
 
TCNP_PORT=${TOMCAT_PORT[1]}
[ -z "$TCNP_PORT" ] || TCNP_PORT=9009
 
 
if [ "$1" = start ]; then
   iptables -A INPUT -p tcp --dport $TCNP_PORT -j DROP
elif [ "$1" = stop ]; then
   while iptables -D INPUT -p tcp --dport $TCNP_PORT -j DROP >/dev/null 2>&1 ; do echo -n; done;
fi


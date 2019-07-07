#!/bin/bash
#########################################################################
# Description: Setup firewall to block access to SOAP port from outside.
#              Allow access only from the local machine.
#
#########################################################################

export PATH=$PATH:/usr/local/mysql/bin
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

ExecSQL()
{
   mysql $MYSQL_OPTS -D portal2 --connect_timeout=5 --silent -N  -e "$*"
}

PORT=$(ExecSQL "SELECT url FROM Services WHERE roleID=1 LIMIT 1" | sed "s/.*://g" | tr -cd "[:digit:]")

[ -z "$PORT" ] && PORT=17995

logger -t "soap_fw.sh" "Blocking outside access to port $PORT"

ip addr show scope global | grep inet | 
while read line; do
   TYPE=$(echo $line | awk '{print $1}')
   IP=$(echo $line | awk '{print $2}' | cut -f1 -d "/")
  
   if [ $TYPE = "inet6" ]; then
      IPT=ip6tables
   elif [ $TYPE = "inet" ]; then
      IPT=iptables
   else
      continue
   fi
   ## make sure the rule doesn't exist yet to avoid duplicate
   while $IPT -D INPUT -p tcp --dport  $PORT -s $IP -j ACCEPT > /dev/null 2>&1 ; do
     :
   done

   $IPT -A INPUT -p tcp --dport  $PORT -s $IP -j ACCEPT
done 

## allow CLUSTER IP if hot-standby is enabled
[ -f /opt/vidyo/ha/cfg/clusterip.conf ] && eval $(grep "^CLUSTER_IP=" /opt/vidyo/ha/cfg/clusterip.conf)

if [ -n "$CLUSTER_IP" ]; then
   ## make sure the rule doesn't exist yet to avoid duplicate
   while iptables -D INPUT -p tcp --dport $PORT -s $CLUSTER_IP -j ACCEPT > /dev/null 2>&1 ; do
      :
   done

   iptables -A INPUT -p tcp --dport $PORT -s $CLUSTER_IP -j ACCEPT
fi

## make sure the rule doesn't exist yet to avoid duplicate
while iptables -D INPUT -p tcp --dport $PORT -s 127.0.0.1/8 -j ACCEPT > /dev/null 2>&1 ; do
   :
done

while iptables -D INPUT -p tcp --dport $PORT -j REJECT  > /dev/null 2>&1 ; do
   :
done

# add the rule to allow traffic from localhost

iptables -A INPUT -p tcp --dport $PORT -s 127.0.0.1/8 -j ACCEPT

iptables -A INPUT -p tcp --dport $PORT -j REJECT 



#!/bin/bash

TOMCAT_CONF=/opt/vidyo/conf.d/tomcat.conf
WHOAMI=$(whoami)

if [ "$WHOAMI" = vpbatch ]; then
   logger -t "vidyo-javaopts.sh" "executed by vpbatch..."
else
   TC_BIN=$(dirname $0)
   TC_HOME=$(dirname $TC_BIN)

   logger -t "vidyo-javaopts.sh" "TC_BIN=[$TC_BIN]"

   if [ "$TC_BIN" = "/usr/local/tomcatnp/bin" ]; then
      logger -t "vidyo-javaopts.sh" "executed by tomcatnp..."

      MEM=$(free -g | grep Mem | awk '{print $2}')
      if [ $MEM -lt 24 ]; then
         TC_MEM="-Xms2g -Xmx2g"
         logger -t "vidyo-javaopts.sh" "memory detected less than 24 GB "
      elif [ $MEM -lt 30 ]; then
         TC_MEM="-Xms4g -Xmx4g"
         logger -t "vidyo-javaoptsh" "memory detected 24-29 GB "
      else
         TC_MEM="-Xms6g -Xmx6g"
         logger -t "vidyo-javaopts.sh" "memory detected > 30GB "
      fi

      JAVA_OPTS="-server $TC_MEM  -XX:-UsePerfData -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -Xnoclassgc -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$TC_HOME/logs -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$TC_HOME/logs/tomcatnp_gc.log"
   else
      logger -t "vidyo-javaopts.sh" "executed by tomcat..."
      JAVA_OPTS="-server -Xms2g -Xmx2g -XX:-UsePerfData -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -Xnoclassgc -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$TC_HOME/logs -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:$TC_HOME/logs/tomcat_gc.log"
   fi

fi

if [ -f $TOMCAT_CONF ]; then
   eval $(grep MAX_TOMCAT_INSTANCE_INDEX $TOMCAT_CONF)

   JAVA_OPTS="$JAVA_OPTS -Djgroups.bind_addr=127.0.0.1 -Djava.net.preferIPv4Stack=true -Djgroups.tcpping.initial_hosts=127.0.0.1[7800],127.0.0.1[7801],127.0.0.1[7802],127.0.0.1[7803],127.0.0.1[7804]"
fi

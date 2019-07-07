#!/bin/bash

NR=/opt/vidyo/etc/tomcat/env/02_newrelic
NR_JAR=/opt/newrelic/newrelic.jar

if [ -f $NR_JAR ]; then
   cat << EOF > $NR
   CATALINA_OPTS="\$CATALINA_OPTS -javaagent:$NR_JAR"
EOF
   chown root:webapps $NR
   chmod 640 $NR
else
   [ -f $NR ] && rm -f $NR
fi

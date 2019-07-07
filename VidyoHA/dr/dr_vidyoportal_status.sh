#!/bin/bash 

FILE=/usr/local/tomcat/webapps/ROOT/VidyoPortalStatusService.html

LOG_FUNCS=/opt/vidyo/bin/vidyologger.sh

help()
{
   cat << EOF
Usage: $(basename $0) [OPTIONS]

Options:

  DISABLE - Remove the status page.
  NNNN - Set the status of each component.

#####################################
# N - 1 if OK, 0 - Fail             #
# NNNN                              #
# ||||                              #
# |||+---- VidyoManager OK          #
# ||+----- MySQL  OK                #
# |+------ Apache2  OK              #
# +------- Tomcat  OK               #
#####################################

EOF
}


[ -z "$1" ] && help && exit 1

if [ "$1" = DISABLE ]; then
   rm -f $FILE
   exit 0
fi




TCSTAT=${1:0:1}
APACHESTAT=${1:1:1}
MYSQLSTAT=${1:2:1}
VMSTAT=${1:3:1}

[ "$TCSTAT" = 1 ] && TCSTAT=OK  || TCSTAT=FAIL
[ "$APACHESTAT" = 1 ] && APACHESTAT=OK  || APACHESTAT=FAIL
[ "$MYSQLSTAT" = 1 ] && MYSQLSTAT=OK  || MYSQLSTAT=FAIL
[ "$VMSTAT" = 1 ] && VMSTAT=OK  || VMSTAT=FAIL

cat << EOF > $FILE 
<html>
VIDYOMANAGER=$VMSTAT
<BR>
TOMCAT=$TCSTAT
<BR>
MYSQL=$MYSQLSTAT
<BR>
APACHE=$APACHESTAT
<BR>
</html>
EOF

chown root:webapps $FILE
chmod 644 $FILE


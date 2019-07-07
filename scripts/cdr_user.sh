#!/bin/bash

##########################################################
# Description: Script for managing CDR User
# Parameters
# param1 - CREATE|DROP
# param2 - Username
# param3 - Host 
# param3 - Y|N (optional) Y=allow delete
##########################################################


PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

CMD="$1"
UNAME="$2"
HOST="$3"
ALLOW_DELETE="$4"


logme()
{
   logger -t "cdr_user.sh" "$*"
#   echo "$*"
}


if [ -z "$HOST" ]; then
   logme Error! Invalid host
   exit 1
fi

if [ -z "$UNAME" ]; then
   logme Error! Invalid username
   exit 1
fi


if [ "$CMD" = "CREATE" ]; then
   read PASS
   if [ -z "$PASS" ]; then
      logme Error! Invalid password
      exit 1
   fi

   if [ "$ALLOW_DELETE" = Y ]; then
      PRIVS="SELECT, DELETE"
   else
      PRIVS="SELECT"
   fi
   logme Creating CDR user "$UNAME"@"$HOST"
   mysql $MYSQL_OPTS << EOF
   GRANT $PRIVS on portal2.ConferenceCall2 TO '$UNAME'@'$HOST' IDENTIFIED BY '$PASS'
EOF

elif [ "$CMD" = "DROP" ]; then
   logme Dropping CDR user "$UNAME"@"$HOST"
   mysql $MYSQL_OPTS << EOF
   DROP USER '$UNAME'@'$HOST'
EOF
else
   logme "Invalid Command"
   exit 1
fi

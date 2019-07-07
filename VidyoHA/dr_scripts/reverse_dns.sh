#!/bin/bash

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV




if [ $# -eq 2 ]; then
   dig @$1 $2  +short +time=3 +tries=2
   exit $?
fi

[ -f $CONF ] || exit 1

. $CONF

. $LOG_FUNCS

dig @$ADCADDRESS  $PUBLICFQDN  +short +time=3 +tries=2
RC=$?


exit $RC

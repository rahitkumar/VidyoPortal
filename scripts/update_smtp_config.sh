#!/bin/bash

PATH=$PATH:/usr/local/mysql/bin
SMTP_CONF=/opt/vidyo/conf/msmtp/msmtp.conf
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

mkdir -p /opt/vidyo/conf/msmtp

SuperEmailFrom=
SuperEmailTo=
smtpHost=
smtpPort=
smtpUsername=
smtpPassword=
smtpSecure=
smtpSslTrustAll=


eval $(mysql $MYSQL_OPTS -D portal2 --silent -N -e "SELECT CONCAT(configurationName, '=', configurationValue)  FROM  Configuration where configurationName like '%smtp%' or configurationName in ('SuperEmailFrom', 'SuperEmailTo')")

TLS=off
STARTTLS=off
TLS_CERT_CHECK=on
SMTP_HOST=$smtpHost
SMTP_PORT=$smtpPort
TIMEOUT=15
SMTP_USERNAME=$smtpUsername
FROM=$SuperEmailFrom

if [ "$smtpSecure" = STARTTLS ]; then
   TLS=on
   STARTTLS=on
elif [ "$smtpSecure" = SSL_TLS ]; then
   TLS=on
   STARTTLS=off
fi

if [ "$smtpSslTrustAll" = 1 ]; then
   TLS_CERT_CHECK=off
fi

if [ "$STARTTLS" = off -a "$TLS" = off ]; then
   TLS_CERT_CHECK=off
fi

AUTH=off
[ -n "$SMTP_USERNAME" ] && AUTH=on

cat << EOF > $SMTP_CONF
defaults
tls $TLS
tls_starttls $STARTTLS
tls_certcheck $TLS_CERT_CHECK
EOF

[ "$TLS_CERT_CHECK" = on ] && echo tls_trust_file /opt/vidyo/etc/ssl/certs/cacert.root >> $SMTP_CONF

cat << EOF >> $SMTP_CONF
timeout $TIMEOUT
dsn_return off

logfile 
syslog LOG_MAIL

account vidyo
host $SMTP_HOST
port $SMTP_PORT 
from $FROM
auth $AUTH
user $SMTP_USERNAME

account default: vidyo

EOF

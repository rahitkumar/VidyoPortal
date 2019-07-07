#!/bin/bash

OLD_JKSFILE=/opt/vidyo/etc/java/samlKeystore.jks
JKSFILE=/opt/vidyo/etc/java/saml.jks

[ -f $OLD_JKSFILE ] && rm -f $OLD_JKSFILE
[ -f $JKSFILE ] && exit 

KEY=$(mktemp key.XXXXXXXXXXXX.pem --tmpdir=/tmp)
CRT=$(mktemp crt.XXXXXXXXXXXX.pem --tmpdir=/tmp)
P12=$(mktemp p12.XXXXXXXXXXXX.pem --tmpdir=/tmp)
KEYCERT=$(mktemp keycrt.XXXXXXXXXXXX.pem --tmpdir=/tmp)
P12PASS=$(mktemp p12pass.XXXXXXXXXXXX.pem --tmpdir=/tmp)
PASS=s@ml123
ALIAS=vidyo

MAC=$(ip addr | grep ether | head -1 | awk '{ print $2 }' | tr -d '\n:')
CN=${MAC}$(date +%s)
JRE_HOME=/usr/lib/jvm/jre/lib

logger -t "gensamljks.sh" "Generating a new saml keystore..."

echo $PASS > $P12PASS

openssl genrsa -out $KEY 1024

openssl req -new -x509 -key $KEY -out $CRT -days 7300 -subj "/C=US/ST=New Jersey/L=Hackensack/O=Vidyo/CN=$CN" -SHA256

cat  $KEY $CRT > $KEYCERT

openssl pkcs12 -export -in $KEYCERT -out $P12 -name $ALIAS -password pass:$PASS

# Create a new JKS fike from p12

keytool -importkeystore -srcstoretype PKCS12 -srcalias $ALIAS -srckeystore $P12 -destkeystore $JKSFILE -deststoretype JKS -deststorepass $PASS -srcstorepass $PASS -noprompt


rm -f $KEY $CRT $KEYCERT $P12PASS $P12


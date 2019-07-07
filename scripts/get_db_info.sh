#!/bin/bash

[ $(id -u) -ne 0 ] && exit 1
CONTEXT_XML=/usr/local/tomcat/conf/context.xml

[ -f $CONTEXT_XML ] || exit 1

DBINFO=$(sed -n '/jdbc\/portal2\"/, /\<Resource name/ p ' $CONTEXT_XML | grep -E "username=|url=" )

echo $DBINFO | tr " " "\n" | grep ^username= | sed 's/username=/DB_USER=/g'
echo $DBINFO | tr " " "\n" | grep ^password= | sed 's/password=/DB_PASS=/g'
HOST_PORT=$(echo $DBINFO | tr " " "\n" | grep ^url= | sed "s/^.*\/\///g" | sed "s/\/.*//g")
DB_HOST=$(echo $HOST_PORT | cut -f1 -d:)
DB_PORT=$(echo $HOST_PORT | cut -f2 -d:)


echo "DB_HOST=$DB_HOST"
echo "DB_PORT=$DB_PORT"


#!/bin/bash

ENVDIR=/opt/vidyo/etc/tomcat/env/

TCENV=$(ls -1 $ENVDIR | sort)

for F in $TCENV; do
   . ${ENVDIR}/$F
done

#!/bin/bash

INFILE="$1"
CAFILE=/opt/vidyo/etc/ssl/certs/vidyoca.crt
ENCRYPT_PASS=cd630d95589fc705fa5c0289c2e09651893cc404
SIGNED_FILE="$1"

if [ -z "$1" ]; then
   logger -t "signcheck.sh" "invalid arguments"
   exit 1
fi

if [ ! -f "$SIGNED_FILE" ]; then
   logger -t "signcheck.sh" "unable to find $SIGNED_FILE"
   exit 1
fi

TMPFILE=$(mktemp --tmpdir=/root)

trap "rm -f $TMPFILE" EXIT

openssl smime -verify -in $INFILE -CAfile $CAFILE -out $TMPFILE
if [ $? -ne 0 ]; then
   logger -t "signcheck.sh" "failed to verify signature..."
   exit 1
fi

openssl enc -d -aes-256-cbc -pass pass:$ENCRYPT_PASS  -base64 -in $TMPFILE -out $INFILE.verified
if [ $? -ne 0 ]; then
   logger -t "signcheck.sh" "failed to decrypt..."
   exit 1
fi

exit 0

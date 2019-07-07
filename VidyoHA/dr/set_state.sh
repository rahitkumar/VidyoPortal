#!/bin/bash

DR_CONF=/opt/vidyo/conf/dr/dr.conf

[ -f $DR_CONF ] || exit 1

. $DR_CONF

echo $1 > $NODE_STATE_FILE

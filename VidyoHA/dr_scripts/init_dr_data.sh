#!/bin/bash
###################################################################################
# Description: Initialize the data directoryi where we store the peer's information.
# Note: removed everything on /opt/vidyo/data/dr except for 'local' and 'active'
###################################################################################

DR_ENV=/opt/vidyo/conf/drmgr/drenv.conf

[ ! -f $DR_ENV ] && exit

. $DR_ENV


[ -f $LOG_FUNCS ] && . $LOG_FUNCS


CURDIR=$(pwd)

if ! cd $DATA_DIR; then
   vlog3 "Failed to change directory [$DATA_DIR]"
   exit 1
fi

vlog3 "Initializing data directory..."

rm -f $NODE_LIST

for D in $(ls -1); do
   [ "$D" = local -o "$D" = "/" -o "$D" = ".." -o "$D" = "."  -o "$D" = active ] && continue
   vlog3 "removing $D"
   rm -rf "$D"
done

cd $CURDIR

vlog3 "Done..."
exit 0

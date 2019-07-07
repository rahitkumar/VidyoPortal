#!/bin/bash

DR_CONF=/opt/vidyo/conf/dr/dr.conf
VALID_CONF_AGE=259200  ## 3days - 60 * 60 * 24 * 3


[ -f $DR_CONF ] || exit 1

. $DR_CONF
. $LOG_FUNCS

OLDDIR=$(pwd)
cd $DATA_DIR

SITES=$(find . -type d | tr -d "\./" )

for S in $SITES; do
   if [ "$1" = DELETE_ALL_STANDBY ]; then
      [[ "$S" =~ local ]] && continue
   else
      [[ "$S" =~ local|active ]] && continue
   fi
   ## ignore configuration older than 3 days
   if [ -f $S/node.conf ]; then
      if [ "$1" = DELETE_ALL_STANDBY ]; then
         vlog3 "[DELETE_ALL_STANDBY] remove config for site $S"
         rm -rf $S
      else
         AGE=$(stat --format=%Z $S/node.conf)
         NOW=$(date +%s)
         ((LAST_UPDATED=NOW-AGE))
         if [ $LAST_UPDATED -ge $VALID_CONF_AGE ]; then
            vlog3 "remove config for site $S. Last update: $LAST_UPDATED secs ago"
            rm -rf $S
         fi
      fi
   fi
done


cd $OLDDIR

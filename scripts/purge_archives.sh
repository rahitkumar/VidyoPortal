#!/bin/bash

. /opt/vidyo/bin/vidyologger.sh

purge_oldImage()
{
   FOLDER=/oldImage
   [ -d $FOLDER ] || return 
   vlog0 "purging /oldImage..."
   BORN=$(stat --format=%Y $FOLDER)
   NOW=$(date +%s)
   ((AGE=NOW-BORN))
   [ $AGE -le 0 ] && return
   ((AGE/=86400))
   [ $AGE -lt 30 ] && return

   vlog0 "Deleting /oldImages [$AGE day(s) old]"
   rm -rf $FOLDER
}

purge_archives()
{
   local KEEP_COPY=2

   vlog0 "Purging archives..."
   FILES=$(ls -1rt /opt/vidyo/archives/database/mysql_db_backup* 2>/dev/null | head -n -${KEEP_COPY})
   FILES_2=$(ls -1rt /opt/vidyo/archives/database/upgrade_backup* 2>/dev/null | head -n -${KEEP_COPY})
   FILES_3=$(ls -1rt /opt/vidyo/archives/varlogs/backup_* 2>/dev/null| head -n -${KEEP_COPY})
   for F in $FILES $FILES_2 $FILES_3; do
      TS=$(stat --format=%Y $F)
      NOW=$(date +%s)
      ((AGE=NOW-TS))
      if [ "$AGE" -gt 86400 ]; then
         vlog0 "$F is older than 1 day, removing"
         rm -f $F
      fi
   done

}

purge_archives
purge_oldImage

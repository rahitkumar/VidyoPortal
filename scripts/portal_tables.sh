#!/bin/bash -
############################################################
# Filename: partial_export.sh
# Desription: export the tables from mysql database. This will
#             be used by portal.
# Date Created: 07-09-2013
###########################################################

DBUSER=root
DATABASE=portal2
OUT=/root/partialdb.sql
MYSQL_OPT="--defaults-extra-file=/root/.my.cnf "

if [ $(id -u) -ne 0 ]; then
   sudo -n $0 $1
   exit $?
fi

umask 0077

CMD="$1"

check_file()
{
   #WAIT=120
   WAIT=5
  
   while [ $WAIT -gt 0 ]; do
      if [ -f $OUT ]; then
         /usr/bin/logger -t "portal_tables.sh" "[$WAIT]waiting for $OUT to be removed..."
         sleep 1
      else
        break
      fi 
      ((WAIT--))
   done

   if [ $WAIT -gt 0 ]; then
      return 1
   fi
   /usr/bin/logger -t "portal_tables.sh" "Maximum wait has been reached."
   echo "TIMEOUT"
   exit 1
}

if [ "$CMD" = "EXPORT" ]; then

   check_file
    
   TABLES="Tenant Room"

   /usr/bin/logger -t "portal_tables.sh" "exporting $TABLES from $DATABASE"

   /usr/local/mysql/bin/mysqldump $MYSQL_OPT -u $DBUSER $DATABASE $TABLES > $OUT
   if [ $? -ne 0 ]; then
      /usr/bin/logger -t "portal_tables.sh" "failed to run mysqldump..."
      rm -f $OUT
      exit 1
   fi
elif [ "$CMD" = "IMPORT" ]; then
   if [ ! -f $OUT ]; then
      /usr/bin/logger -t "portal_tables.sh" "Error! unable to find $OUT"
      exit 1
   fi
   
   /usr/bin/logger -t "portal_tables.sh" "Importing $OUT..."
   /usr/local/mysql/bin/mysql $MYSQL_OPT -u $DBUSER -D $DATABASE < $OUT 
   if [ $? -eq 0 ]; then
      rm -f $OUT
   fi
elif [ "$CMD" = "DELETE" ]; then
   /usr/bin/logger -t "portal_tables.sh" "removing $OUT..."
   rm -f $OUT
else
   /usr/bin/logger -t "portal_tables.sh" "Invalid arguments [$CMD]"
   exit 1
fi

exit $?

#!/bin/bash

##  from Manoj ResetStuckGWVEPs edited version that shipped in PT TAG 3.0.5
##  this version adds check on ConferenceRecord table for stuck CR calls
## NOTE: if installing this on 3.0.5 or other portal with previous VEP only version, this should overwrite that

## installation from portal 3.0.5 install.sh script:
### install the script ResetStuckGWVEPs.sh
#  cp -f $FILEPATH/ResetStuckGWVEPs.sh /opt/vidyo/bin/
#  FLAG=/opt/vidyo/vidyo_updates/RESET_STUCK_GWVEP
#  if [ -f $FLAG ]; then
#     logger "ResetStuckGWVEPs.sh has been installed previously!"
#  else
#     sed -i '/ResetStuckGWVEPs.sh/d' /etc/crontab
#     echo "* * * * * root /opt/vidyo/bin/ResetStuckGWVEPs.sh" >> /etc/crontab
#     logger "ResetStuckGWVEPs.sh has been installed successfully!"
#     touch $FLAG
#  fi

## 11-14-2014 set WAITTIME to 45s and commented out the while loop alltogether as once a minute can just loop via cron, 
## and 45s wait is needed to allow time for RING state timeouts. - Chris A.

USER=`whoami`
if [ "$USER" != "root" ] ; then
        echo "You have to be root to run this script, exiting installation"
        exit
fi

export PATH=$PATH:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin:.

if ! /opt/vidyo/ha/bin/is_active.sh; then
   exit
fi

WAITTIME=45
DUMP_VEP=0
DUMP_CR=0
LOG_DIR='/root/'
LOG_FILE="$LOG_DIR/StuckVEPs_ConfRec_Clear.log"
DB_SNAP_DIR="$LOG_DIR/DB_SNAP_StuckVEP_ConfRec"
PROCESS_FLAG=/root/ResetStuckGWVEPs

mkdir -p $DB_SNAP_DIR

[ -f $PROCESS_FLAG ] && exit 1

LOG_TIME=$(date +%b%d%Y_%H%M%S_%Z)
DB_VEP_SNAP_FILE_BEFORE="$DB_SNAP_DIR/"$LOG_TIME"_VEP_FOUND.gz"
DB_VEP_SNAP_FILE_AFTER="$DB_SNAP_DIR/"$LOG_TIME"_VEP_CLEARED.gz"

ClearStuckCR()
{
   TMPCR=/opt/vidyo/temp/mysql/ConferenceRecord.txt
   LOG_TIME=$(date +%b%d%Y_%H%M%S_%Z)
   DB_CR_SNAP_FILE_BEFORE="$DB_SNAP_DIR/"$LOG_TIME"_CR_FOUND.gz"
   DB_CR_SNAP_FILE_AFTER="$DB_SNAP_DIR/"$LOG_TIME"_CR_CLEARED.gz"

   #echo ClearStuckCR
   mysql --skip-column-names --silent -Dportal2 -e "SELECT * FROM ConferenceRecord WHERE  UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(updateTime) > 35" > $TMPCR

   #cat $TMPCR
   TMPCRSIZE=$(stat --format=%s $TMPCR)

   if [ $TMPCRSIZE -gt 0 ]; then
      echo "$LOG_TIME - Found Stuck ConferenceRecord - " >> $LOG_FILE
      cat $TMPCR >> $LOG_FILE
      mysqldump -t --fields-enclosed-by="\"" --fields-terminated-by="," --fields-escaped-by="\"" --lines-terminated-by="\n" -T/opt/vidyo/temp/mysql/ portal2 ConferenceRecord

      gzip /opt/vidyo/temp/mysql/ConferenceRecord.txt
      mv -f /opt/vidyo/temp/mysql/ConferenceRecord.txt.gz $DB_CR_SNAP_FILE_BEFORE

      DUMP_CR=1
      mysql --skip-column-names --silent -Dportal2 -e "DELETE FROM ConferenceRecord WHERE UNIX_TIMESTAMP(NOW()) - UNIX_TIMESTAMP(updateTime) > 35"
   fi
   rm -f $TMPCR

   if [ $DUMP_CR == "1" ]; then
      mysqldump -t --fields-enclosed-by="\"" --fields-terminated-by="," --fields-escaped-by="\"" --lines-terminated-by="\n" -T/opt/vidyo/temp/mysql/ portal2 ConferenceRecord
      gzip /opt/vidyo/temp/mysql/ConferenceRecord.txt
      mv -f /opt/vidyo/temp/mysql/ConferenceRecord.txt.gz $DB_CR_SNAP_FILE_AFTER
      DUMP_CR=0
   fi

}

## delete files older than 30 days
find $DB_SNAP_DIR -mtime +30 -exec rm -f {} \;

touch $PROCESS_FLAG

STUCKVEPSA=$(mysql --skip-column-names -Dportal2 -e "SELECT endpointID FROM VirtualEndpoints WHERE status=1 AND (displayName != '' OR displayExt != '');")

##########################################################################
# COnferenceRecord holds intermediate state before joining conference.
# Entries will be moved from ConferenceRecord to Conferences
##########################################################################

## remove the 45s waittime and replace with 3 15s sleep to allow ClearStuckCR run every 15 seconds
   
for i in 1 2 3; do
   ClearStuckCR
   sleep 15
done

for i in ${STUCKVEPSA[@]}; do
   echo "$LOG_TIME - Found POSSIBLE Stuck Prefix - EPID: $i - Status 1 with Conf Data" >> $LOG_FILE

   STUCKVEPSB=$(mysql --skip-column-names -Dportal2 -e "SELECT endpointID FROM VirtualEndpoints WHERE endpointID=$i AND status=1 AND (displayName != '' OR displayExt != '');")

   if [ -n "$STUCKVEPSB" ]; then
      if [ $DUMP_VEP == "0" ]; then
         mysqldump -t --fields-enclosed-by="\"" --fields-terminated-by="," --fields-escaped-by="\"" --lines-terminated-by="\n" -T/opt/vidyo/temp/mysql/ portal2 VirtualEndpoints
         gzip /opt/vidyo/temp/mysql/VirtualEndpoints.txt
         mv -f /opt/vidyo/temp/mysql/VirtualEndpoints.txt.gz $DB_VEP_SNAP_FILE_BEFORE
         DUMP_VEP=1
      fi

      mysql -Dportal2 -e "UPDATE VirtualEndpoints SET displayName='', displayExt='' WHERE endpointID=$i AND status=1;"
      echo "$LOG_TIME - Found Stuck Prefix - EPID: $i - Status 1 with Conf Data..Cleared" >> $LOG_FILE

   fi
done

if [ $DUMP_VEP == "1" ]; then
   mysqldump -t --fields-enclosed-by="\"" --fields-terminated-by="," --fields-escaped-by="\"" --lines-terminated-by="\n" -T/opt/vidyo/temp/mysql/ portal2 VirtualEndpoints
   gzip /opt/vidyo/temp/mysql/VirtualEndpoints.txt
   mv -f /opt/vidyo/temp/mysql/VirtualEndpoints.txt.gz $DB_VEP_SNAP_FILE_AFTER
   DUMP_VEP=1
fi

rm -f $PROCESS_FLAG

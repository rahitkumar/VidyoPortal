#!/bin/bash

PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin
DR_CONF=/opt/vidyo/conf/dr/dr.conf
HA_CONF=/opt/vidyo/ha/cfg/clusterip.conf
DB_REPL=/opt/vidyo/ha/cfg/dbrepl.conf
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"
SLAVE_STATUS=/opt/vidyo/ha/hssync/slave_status.sh

GET_STATE=/opt/vidyo/ha/dr/get_state.sh
VALID_CONF_AGE=259200  ## 3days - 60 * 60 * 24 * 3

. /opt/vidyo/adm/myfunctions.sh
[ -f $DR_CONF ] && . $DR_CONF
[ -f $DB_REPL ] && . $DB_REPL
[ -f $HA_CONF ] && . $HA_CONF

declare -A _NODEID
declare -A _NODEVERSION
declare -A _DISPLAYNAME
declare -A _NATIVEETH0IP
declare -A _PUBLICIP
declare -A _NATIVEETH1IP
declare -A _REQUEST_NEW_SNAPSHOT
declare -A _REPLICATION_RUNNING
declare -A _REPLICATION_ERROR
declare -A _SECONDS_BEHIND_MASTER
declare -A _LAST_TIME_DB_INSYNC
declare -A _LAST_TIME_RSYNC_OK
declare -A _CONF_AGE


writexy()
{
   local COL=$1
   local ROW=$2
   
   shift 2
   tput cup $ROW $COL
   echo "$*"
}

writexyz()
{
   local COL=$1
   local ROW=$2
   local LEN=$4
   local MOD=0
   
   ((ROW=$2+$3))
   #((MOD=ROW%2))
   #if [ $MOD -ne 0 ]; then
   #   COLOR $WHITE $BLUE $BRIGHT
   #else
   #   COLOR $BLACK $WHITE $BRIGHT
   #fi
   COLOR $WHITE $BLACK $BRIGHT
   shift 4
   tput cup $ROW $COL
   STR="$*"
   printf "%-*s" $LEN "${STR:0:$LEN}"
}

writexyz_label()
{
   local COL=$1
   local ROW=$2
   local LEN=$4
   local MOD=0
   
   ((ROW=$2+$3))
   COLOR $YELLOW $BLACK $BRIGHT
   shift 4
   tput cup $ROW $COL
   STR="$*"
   printf "%*s: " $LEN "${STR:0:$LEN}"
}


get_standby_site_status()
{
   OLDDIR=$(pwd)
   cd $DATA_DIR
   SITES=$(find . -type d | tr -d "\./" )
   VALID_SITES=
   VALID_SITES_CTR=0

   STATE=$($GET_STATE)
   for S in $SITES; do
      [[ "$S" =~ local|active ]] && continue
      ## ignore configuration older than 3 days
      if [ -f $S/node.conf ]; then
         AGE=$(stat --format=%Z $S/node.conf)
         NOW=$(date +%s)
         ((LAST_UPDATED=NOW-AGE))
         if [ $LAST_UPDATED -ge $VALID_CONF_AGE ]; then
            continue  ## ignore it for now
         fi
         NODEID=
         NODEVERSION=
         DISPLAYNAME=
         NATIVEETH0IP=
         PUBLICIP=
         NATIVEETH1IP=
         REQUEST_NEW_SNAPSHOT=
         REPLICATION_RUNNING=
         REPLICATION_ERROR=
         SECONDS_BEHIND_MASTER=
         LAST_TIME_DB_INSYNC=
         LAST_TIME_RSYNC_OK=

         . $S/node.conf

         _NODEID[$S]=$NODEID
         _NODEVERSION[$S]=$NODEVERSION
         _DISPLAYNAME[$S]=$DISPLAYNAME
         _NATIVEETH0IP[$S]=$NATIVEETH0IP
         _PUBLICIP[$S]=$PUBLICIP
         _NATIVEETH1IP[$S]=$NATIVEETH1IP
         _REQUEST_NEW_SNAPSHOT[$S]=$REQUEST_NEW_SNAPSHOT
         _REPLICATION_RUNNING[$S]=$REPLICATION_RUNNING
         _REPLICATION_ERROR[$S]=$REPLICATION_ERROR
         _SECONDS_BEHIND_MASTER[$S]=$SECONDS_BEHIND_MASTER
         _LAST_TIME_DB_INSYNC[$S]=$LAST_TIME_DB_INSYNC
         _LAST_TIME_RSYNC_OK[$S]=$LAST_TIME_RSYNC_OK
         _CONF_AGE[$S]=$LAST_UPDATED
         VALID_SITES[$VALID_SITES_CTR]=$S
         ((VALID_SITES_CTR++))

      else
         echo $S has no configuration. 
      fi
       
   done
   ## we are only supporting max 5 standby sites. (0-4)
   if [ $VALID_SITES_CTR -gt 5 ]; then
      VALID_SITES_CTR=5 
   fi
}


display_standby_sites()
{
   WHITE_BLACK
   clear

   get_standby_site_status

   COLOR $YELLOW $WHITE $BRIGHT
   writexy 0 0 "                    *** STANDBY SITE STATUS ***                                    "
   COLOR $BLACK $WHITE $BRIGHT
   writexy 54 0 "$(date)"

   ROW=0
   CTR=0
   while [ $CTR -lt $VALID_SITES_CTR ]; do
      if [ $CTR -lt 2 ]; then
         ROW=1
      else
         ROW=12
      fi
      writexyz_label 0 1 $ROW 20 "Node ID"
      writexyz_label 0 2 $ROW 20 "Node Version"
      writexyz_label 0 3 $ROW 20 "DisplayName"
      writexyz_label 0 4 $ROW 20 "Public IP"
      writexyz_label 0 5 $ROW 20 "Native eth0 IP"
      writexyz_label 0 6 $ROW 20 "Native eth1 IP"
      writexyz_label 0 7 $ROW 20 "DB Snashot Needed"
      writexyz_label 0 8 $ROW 20 "DB ReplStatus"
      writexyz_label 0 9 $ROW 20 "DB Repl Sync Lag"
      writexyz_label 0 10 $ROW 20 "DB Last-InSync"
      writexyz_label 0 11 $ROW 20 "Last Files sync"
      ((CTR+=2))
   done


   CTR=0
   while [ $CTR -lt $VALID_SITES_CTR ]; do
      
      if [ $CTR -gt 1 ]; then
         ROW=12
        ((COL=(CTR-2)*31+22))
      else
         ROW=0
        ((COL=CTR*31+22))
      fi

      writexyz $COL 1 $ROW 30 ${_NODEID[${VALID_SITES[$CTR]}]}
      writexyz $COL 2 $ROW 30 ${_NODEVERSION[${VALID_SITES[$CTR]}]}
      writexyz $COL 3 $ROW 30 ${_DISPLAYNAME[${VALID_SITES[$CTR]}]}
      writexyz $COL 4 $ROW 30 ${_PUBLICIP[${VALID_SITES[$CTR]}]}
      writexyz $COL 5 $ROW 30 ${_NATIVEETH0IP[${VALID_SITES[$CTR]}]}
      writexyz $COL 6 $ROW 30 ${_NATIVEETH1IP[${VALID_SITES[$CTR]}]}
      writexyz $COL 7 $ROW 30 ${_REQUEST_NEW_SNAPSHOT[${VALID_SITES[$CTR]}]}
      
      if [ ${_CONF_AGE[${VALID_SITES[$CTR]}]} -gt 120 ]; then
         REPLICATE_STATUS="NO HEARTBEAT for ${_CONF_AGE[${VALID_SITES[$CTR]}]} (secs)"
      else
         if [ -z "${_REPLICATION_ERROR[${VALID_SITES[$CTR]}]}" ]; then
            REPLICATE_STATUS=${_REPLICATION_RUNNING[${VALID_SITES[$CTR]}]}
            [ "$REPLICATE_STATUS" = "Y" ] && REPLICATE_STATUS=RUNNING
         else
            REPLICATE_STATUS=ERROR:${_REPLICATION_ERROR[${VALID_SITES[$CTR]}]}
         fi
      fi
      writexyz $COL 8 $ROW 30 $REPLICATE_STATUS
      if [[ $REPLICATE_STATUS =~ ERROR: ]]; then
         writexyz $COL 9 $ROW 30 N/A
      else
         writexyz $COL 9 $ROW 30 ${_SECONDS_BEHIND_MASTER[${VALID_SITES[$CTR]}]}
      fi
      if [ -z "${_LAST_TIME_DB_INSYNC[${VALID_SITES[$CTR]}]}" ]; then
         LAST_TIME_DB_INSYNC_STR="Not Available"
      else
         LAST_TIME_DB_INSYNC_STR=$(date -d @${_LAST_TIME_DB_INSYNC[${VALID_SITES[$CTR]}]} '+%D %T')
      fi
      writexyz $COL 10 $ROW 30 $LAST_TIME_DB_INSYNC_STR

      if [ -z "${_LAST_TIME_RSYNC_OK[${VALID_SITES[$CTR]}]}" ]; then 
         LAST_TIME_RSYNC_OK_STR="Not Available"
      else 
         LAST_TIME_RSYNC_OK_STR=$(date -d @${_LAST_TIME_RSYNC_OK[${VALID_SITES[$CTR]}]} '+%D %T')
      fi
      writexyz $COL 11 $ROW 30 ${LAST_TIME_RSYNC_OK_STR}
      ((CTR++))

   done
}

display_standby_site_status()
{
   WHITE_BLACK
   clear
   SITE_INDEX=$1

   get_standby_site_status

   COLOR $BLUE $WHITE $BRIGHT
   writexy 0 0 "                    *** STANDBY SITE STATUS ***                                    "
   COLOR $BLUE $BLACK $BRIGHT
   writexy 0 1 "                                                                                   "
   COLOR $BLACK $WHITE $BRIGHT
   writexy 54 0 "$(date)"

   ROW=1
   ((CTR=SITE_INDEX-1))
   writexyz_label 0 1 $ROW 20 "Node ID"
   writexyz_label 0 2 $ROW 20 "Node Version"
   writexyz_label 0 3 $ROW 20 "DisplayName"
   writexyz_label 0 4 $ROW 20 "Public IP"
   writexyz_label 0 5 $ROW 20 "Native eth0 IP"
   writexyz_label 0 6 $ROW 20 "Native eth1 IP"
   writexyz_label 0 7 $ROW 20 "DB Snashot Needed"
   writexyz_label 0 8 $ROW 20 "DB ReplStatus"
   writexyz_label 0 9 $ROW 20 "DB Repl Sync Lag"
   writexyz_label 0 10 $ROW 20 "DB Last-InSync"
   writexyz_label 0 11 $ROW 20 "Last Files sync"


   COL=22

   writexyz $COL 1 $ROW 61 ${_NODEID[${VALID_SITES[$CTR]}]}
   writexyz $COL 2 $ROW 61 ${_NODEVERSION[${VALID_SITES[$CTR]}]}
   writexyz $COL 3 $ROW 61 ${_DISPLAYNAME[${VALID_SITES[$CTR]}]}
   writexyz $COL 4 $ROW 61 ${_PUBLICIP[${VALID_SITES[$CTR]}]}
   writexyz $COL 5 $ROW 61 ${_NATIVEETH0IP[${VALID_SITES[$CTR]}]}
   writexyz $COL 6 $ROW 61 ${_NATIVEETH1IP[${VALID_SITES[$CTR]}]}
   writexyz $COL 7 $ROW 61 ${_REQUEST_NEW_SNAPSHOT[${VALID_SITES[$CTR]}]}
      
   if [ ${_CONF_AGE[${VALID_SITES[$CTR]}]} -gt 120 ]; then
         REPLICATE_STATUS="NO HEARTBEAT for ${_CONF_AGE[${VALID_SITES[$CTR]}]} (secs)"
   else
      if [ -z "${_REPLICATION_ERROR[${VALID_SITES[$CTR]}]}" ]; then
         REPLICATE_STATUS=${_REPLICATION_RUNNING[${VALID_SITES[$CTR]}]}
         [ "$REPLICATE_STATUS" = "Y" ] && REPLICATE_STATUS=RUNNING
      else
         REPLICATE_STATUS=ERROR:${_REPLICATION_ERROR[${VALID_SITES[$CTR]}]}
      fi
   fi
   writexyz $COL 8 $ROW 61 $REPLICATE_STATUS
   if [[ $REPLICATE_STATUS =~ ERROR: ]]; then
      writexyz $COL 9 $ROW 61 N/A
   else
      writexyz $COL 9 $ROW 61 ${_SECONDS_BEHIND_MASTER[${VALID_SITES[$CTR]}]}
   fi
   if [ -z "${_LAST_TIME_DB_INSYNC[${VALID_SITES[$CTR]}]}" ]; then
      LAST_TIME_DB_INSYNC_STR="Not Available"
   else
      LAST_TIME_DB_INSYNC_STR=$(date -d @${_LAST_TIME_DB_INSYNC[${VALID_SITES[$CTR]}]} '+%D %T')
   fi
   writexyz $COL 10 $ROW 61 $LAST_TIME_DB_INSYNC_STR

   if [ -z "${_LAST_TIME_RSYNC_OK[${VALID_SITES[$CTR]}]}" ]; then 
      LAST_TIME_RSYNC_OK_STR="Not Available"
   else 
      LAST_TIME_RSYNC_OK_STR=$(date -d @${_LAST_TIME_RSYNC_OK[${VALID_SITES[$CTR]}]} '+%D %T')
   fi
   writexyz $COL 11 $ROW 61 ${LAST_TIME_RSYNC_OK_STR}

   COLOR $BLUE $BLACK $BRIGHT
   writexy 0 13 "                                                                                   "
   COLOR $BLACK $WHITE $BRIGHT
   writexy 0 14 "                                                                                   "
   COLOR $BLACK $WHITE $BRIGHT

}

get_num_emcp_rmcp_connections()
{
   EMCP_PORT=
   RMCP_PORT=
   EMCP_COUNT=0
   RMCP_COUNT=0
   eval $(mysql $MYSQL_OPTS portal2 -E -e "select * from vidyo_manager_config" | grep -E "EMCP_PORT:|RMCP_PORT:" | tr -d ' ' | sed 's/:/=/g')

   if [ -n "$EMCP_PORT" ]; then
      EMCP_COUNT=$(netstat -anp | grep $EMCP_PORT | grep VidyoManager | grep ESTABLISHED | wc -l)
   fi
   if [ -n "$RMCP_PORT" ]; then
      RMCP_COUNT=$(netstat -anp | grep $RMCP_PORT | grep VidyoManager | grep ESTABLISHED | wc -l)
   fi
}

site_status()
{
   WHITE_BLACK
   clear

   STATE=$($GET_STATE)
   if  [[ "$STATE" =~ ACTIVE|PRE_ACTIVE ]]; then
      while true; do
         get_standby_site_status
         LAST_DB_SNAPSHOT=UNKNOWN
         if [ -f $DB_SNAPSHOT_COMPRESS ]; then
            LAST_DB_SNAPSHOT=$(stat --format="%z" $DB_SNAPSHOT_COMPRESS | sed 's/\..*//g')
            DB_SIZE=$(du -h $DB_SNAPSHOT_COMPRESS | awk '{ print $1}')
         fi
         get_num_emcp_rmcp_connections
         WHITE_BLACK
         clear
      
         echo -e "-------------------------------------------------------------------------------"
         WHITE_BLACK; echo -n "Site Status: "
         GREEN_BLACK; echo "$STATE"
         WHITE_BLACK; echo -n "Up Time: "
         YELLOW_BLACK; echo "$(uptime -p)"
         WHITE_BLACK; echo -n "Number of EPs connected to VM: "
         YELLOW_BLACK; echo "$EMCP_COUNT"
         WHITE_BLACK; echo -n "Number of Routers connected to VM: "
         YELLOW_BLACK; echo -n "$RMCP_COUNT"
         WHITE_BLACK; echo -n "    DB Snapshot Size: "
         YELLOW_BLACK; echo "$DB_SIZE"

         WHITE_BLACK; echo -n "Last Database Snapshot: "
         YELLOW_BLACK; echo "$LAST_DB_SNAPSHOT"
         WHITE_BLACK; echo -n "# of Standby Site(s): "
         YELLOW_BLACK; echo "$VALID_SITES_CTR" 

         CTR=0
         while [ $CTR -lt $VALID_SITES_CTR ]; do
            ((INDEX=CTR+1))
            echo
            WHITE_BLACK; echo -n "  [$INDEX] Site Name: "
            YELLOW_BLACK; echo "${_DISPLAYNAME[${VALID_SITES[$CTR]}]} (${_NODEID[${VALID_SITES[$CTR]}]})" 
            WHITE_BLACK; echo -n "      Public IP: "
            YELLOW_BLACK; echo ${_PUBLICIP[${VALID_SITES[$CTR]}]}
            ((CTR++))
         done

         WHITE_BLACK
         echo
         COLOR $BLACK $WHITE $BRIGHT
         if [ $VALID_SITES_CTR -gt 1 ]; then
            echo -e " [1-$VALID_SITES_CTR] - Detail Site Status                                      [x] - Exit  "
         elif [ $VALID_SITES_CTR -eq 1 ]; then
            echo -e " [1] - Detail Site Status                                        [x] - Exit  "
         elif [ $VALID_SITES_CTR -eq 0 ]; then
            echo -e "                                                                 [x] - Exit  "
         fi
         OPT=
         read -n1 -s OPT
         case "$OPT" in
            x|X) break
            ;;
            1|2|3|4|5) WAIT=0
               if [ $OPT -le $VALID_SITES_CTR ]; then
                  while true; do
                    display_standby_site_status $OPT
                    WHITE_BLACK
                    echo
                    if read -n1 -t1 -p "Press any key to exit."; then
                       break
                    fi
                    ((WAIT++))
                    [ $WAIT -gt 120 ] && break  ## max continous refresh is 2 mins
                  done
               fi 
                 #break
            ;;
            s|S) WAIT=0
                 #while true; do
                 #  display_standby_sites
                 #  WHITE_BLACK
                 #  echo
                 #  if read -n1 -t1 -p "Press any key to exit."; then
                 #     break
                 #  fi
                 #  ((WAIT++))
                 #  [ $WAIT -gt 120 ] && break  ## max continous refresh is 2 mins
                 #done
            ;;
         esac
      done
   elif  [ "$STATE" = STANDBY ]; then
      WAIT=0
      while true; do
         WHITE_BLACK; 
         clear
         echo "------------------------------------------------------------------------------"
         eval $($SLAVE_STATUS)
         WHITE_BLACK; echo -n "Site Status: "
         CYAN_BLACK; echo "STANDBY"
         WHITE_BLACK; echo -n "Up Time: "
         YELLOW_BLACK; echo "$(uptime -p)"

         WHITE_BLACK; echo -n "Site Version: "
         YELLOW_BLACK; echo -n  "$(cat /opt/vidyo/VC2_VERSION)"

         DB_VERSION=UNKNOWN
         [ -f $DB_VERSION_INFO ] && . $DB_VERSION_INFO
         
         WHITE_BLACK; echo -n "   Site DB Version: "
         YELLOW_BLACK; echo "$DB_VERSION"

         WHITE_BLACK; echo -n "Secured Tunnel: "
         if [ -f $TUNNEL_OK ]; then
            GREEN_BLACK; echo UP
         else
            RED_BLACK;echo DOWN
         fi

         if [ -f $DB_VERSION_MISMATCH ]; then
            RED_BLACK; echo -e "\n[Warning!!!] Detected DB Version Mismatch. DB Replication Will Not Start"
         else
            WHITE_BLACK;echo -n "Last Filesystem Sync: " 
            if [ -f $LAST_TIME_RSYNC_FILE ]; then
                LAST_RSYNC_TIME=$(stat --format="%z" $LAST_TIME_RSYNC_FILE | sed 's/\..*//g')
            else 
                LAST_RSYNC_TIME=UNKNOWN
            fi 
            YELLOW_BLACK; echo $LAST_RSYNC_TIME
         fi

         ACTIVE_NODE_CONF_TMP=${ACTIVE_NODE_CONF}.tmp

         if [ -f $ACTIVE_NODE_CONF_TMP ]; then
            STATUSCODE=
            . $ACTIVE_NODE_CONF_TMP
            if [ "$STATUSCODE" != 200 ]; then
               RED_BLACK;echo -e "\n\n*** Failed to Register to ACTIVE site. Error Code: $STATUSCODE ***\n"
            fi
         fi


         if [ -f $ACTIVE_NODE_CONF ]; then
            WHITE_BLACK; echo "Database Replication"
            WHITE_BLACK; echo -n "  IO State: "
            YELLOW_BLACK; echo "$Slave_IO_State"
            WHITE_BLACK; echo -n "  Seconds Behind Master: "
            YELLOW_BLACK; echo "$Seconds_Behind_Master"
            WHITE_BLACK; echo -n "  Master Log Position: "
            YELLOW_BLACK; echo "$Read_Master_Log_Pos"
            WHITE_BLACK; echo -n "  Replication Error: "
         
            if [ "$Last_IO_Errno" = 0 -a "$Last_SQL_Errno" = 0 ]; then
               YELLOW_BLACK; echo "NONE"
            else
               echo
               if [ -n "$Last_SQL_Error" ]; then
                  WHITE_BLACK;echo -n "    SQL Error: "
                  RED_BLACK;echo "$Last_SQL_Error"
                  WHITE_BLACK;echo -n "    SQL Error Timestamp: "
                  RED_BLACK;echo "$Last_SQL_Error_Timestamp"
               fi

               if [ -n "$Last_IO_Error" ]; then
                  WHITE_BLACK;echo -n "    IO Error: "
                  RED_BLACK;echo "${Last_IO_Error}"
                  WHITE_BLACK;echo -n "    IO Error Timestamp: "
                  RED_BLACK;echo "$Last_IO_Error_Timestamp"
               fi
            fi
            . $ACTIVE_NODE_CONF
            WHITE_BLACK
            echo -e "\n------------------------------------------------------------------------------"
            GREEN_BLACK;echo "ACTIVE Site Configuration"
            echo
            WHITE_BLACK; echo -n "Site Id: "
            YELLOW_BLACK; echo -n "$NODEID"
            WHITE_BLACK; echo -n "   Site Name: "
            YELLOW_BLACK; echo "$DISPLAYNAME"

            WHITE_BLACK; echo -n "Site Version: "
            YELLOW_BLACK; echo -n "$NODEVERSION"

            WHITE_BLACK; echo -n "   Site DB Version: "
            YELLOW_BLACK; echo "$DBVERSION"
         
            WHITE_BLACK; echo -n "Native eth0 IP: "
            YELLOW_BLACK; echo "$NATIVEETH0IP"
            if [ -n "$NATIVEETH1IP" ]; then
               WHITE_BLACK; echo -n "Native eth1 IP: "
               YELLOW_BLACK; echo "$NATIVEETH1IP"
            fi
            WHITE_BLACK; echo -n "Public IP: "
            YELLOW_BLACK; echo "$PUBLICIP"
            WHITE_BLACK; echo -n "Data Sync IP: "
            YELLOW_BLACK; echo "$DATASYNCIP"
         else
             RED_BLACK; echo -e "\n\n******Warning! NO ACTIVE SITE CONFIGURATION FOUND ******"
         fi
         WHITE_BLACK; echo -e "------------------------------------------------------------------------------"
         if read -t1 -n1 -p "Press any key."; then
            break
         fi
         ((WAIT++))
         [ $WAIT -gt 120 ] && break  ## max continous refresh is 2 mins
      done
   elif  [ "$STATE" = MAINTENANCE ]; then
      WHITE_BLACK
      echo -n "Site Status: "
      YELLOW_BLACK
      echo -e "UNDER MAINTENANCE\n\n"
      GREEN_BLACK
      echo -n "Maintenance Started on " $(/opt/vidyo/ha/dr/drconfig.sh MAINTENANCE_START_TIME)
      echo -e "\n\n"
      WHITE_BLACK
      pause "Press any key."
   else
      WHITE_BLACK
      echo -n "Site Status: "
      YELLOW_BLACK
      echo -e "UNKNOWN\n\n"
      WHITE_BLACK
      pause "Press any key."
   fi
}




site_status


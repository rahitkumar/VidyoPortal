#!/bin/bash

DR_CONF=/opt/vidyo/conf/dr/dr.conf
DRCONF_SCRIPT=/opt/vidyo/ha/dr/drconfig.sh
GET_STATE=/opt/vidyo/ha/dr/get_state.sh

force_active()
{
   ## site can be forced active only for max 10 mins
   ## this is needed only if ADC cannot be configured with
   ## default or preferred site.
   if [ $($DRCONF_SCRIPT GET_FORCE_ACTIVE_STATUS) = ENABLED ]; then
      return 0
   else
      return 1
   fi
}

force_active_time_left()
{
   local STATE=$($GET_STATE)
   . $DR_CONF
   . $LOCAL_NODE_CONF
   if [ -f $FORCE_ACTIVE_FLAG ]; then
      if [ "$STATE" =  PRE_ACTIVE ]; then
         echo "Waiting to finish PRE_ACTIVE state"
         return
      fi
      BORN=$(stat --format=%Y $FORCE_ACTIVE_FLAG)
      NOW=$(date +%s)
      MAXFORCEACTIVE=600
      [ -f $LOCAL_NODE_CONF ] && . $LOCAL_NODE_CONF
      ((TIMELEFT=MAXFORCEACTIVE-(NOW-BORN)))
      echo $TIMELEFT
   else
      echo 0
   fi
}

disable_force_active()
{
   $DRCONF_SCRIPT DISABLE_FORCE_ACTIVE
}

enable_force_active()
{
   $DRCONF_SCRIPT ENABLE_FORCE_ACTIVE
}

debug_mode()
{
   [ -f /var/run/dr/debug ] && return 0
   return 1
}

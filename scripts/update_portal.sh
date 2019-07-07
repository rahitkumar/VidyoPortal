#!/bin/bash

UFLAG=/opt/vidyo/temp/root/update_in_progress
TEMP_FOLDER=/opt/vidyo/temp/root/
UPDATE_FAILED=/opt/vidyo/temp/root/UPDATE_FAILED

[ -f $UPDATE_FAILED ] && rm -f $UPDATE_FAILED

. /opt/vidyo/bin/vidyologger.sh

_Exit()
{
   [ -f $UFLAG ] && rm -f $UFLAG   
   [ -f $UPDATE_FAILED ] && chown tomcat $UPDATE_FAILED
   rm -f $TEMP_FOLDER/*.tar.gz $TEMP_FOLDER/*.vidyo $TEMP_FOLDER/*.hpvc   2>/dev/null
   rm -rf $TEMP_FOLDER/Products 2>/dev/null
   rm -f $TEMP_FOLDER/*.verified 2> /dev/null
   [ -f "$SFILE" ] && rm -f $SFILE
   exit $1
}

update_in_progress()
{
   local now=$(date +%s)
   local flag_tm
   local tmdiff

   if [ ! -f $UFLAG ]; then
      touch $UFLAG
      chmod 755 $UFLAG
      return 1
   fi

   flag_tm=$(stat --format=%X $UFLAG)

   tm_diff=$((now - flag_tm))

   if (( tm_diff > 1800 )); then
      ### file is too old... not valid anymore. files is 30 mins old.
      touch $UFLAG
      return 1
   fi

   return 0
}

do_install()
{
    CURRENT_INSTALL=""
    if [ -f /opt/vidyo/vm/VidyoManager ]; then
       CURRENT_INSTALL=$TEMP_FOLDER/Products/HunterInstall/hunter/install.sh
    else
       CURRENT_INSTALL=$TEMP_FOLDER/Products/HunterInstall/hunter/vrinstall.sh
    fi

    if [ ! -f $CURRENT_INSTALL ]; then
       vlog0 "No installer found..."
       echo "UPDATEFAILED 4"
      _Exit 1
    fi

    umask 0022
    #Silent installation without updating user database
    #echo "no" | 
    $CURRENT_INSTALL &> /opt/vidyo/logs/install-`date +%F-%T`.log
    RC=$?
    if [ $RC -eq 0 ]; then
       [ -f $TEMP_FOLDER/NOREBOOT ] && echo "SUCCESS" && rm -f $TEMP_FOLDER/NOREBOOT
    else
       logger "UPDATEFAILED"
       echo "UPDATEFAILED"
       _Exit 1
    fi

    _Exit 0
}

if update_in_progress; then
   echo "Warning!!! Update in progress..."
   vlog0 "Warning!!! Update in progress..."
   #rm -f $TEMP_FOLDER/*.tar.gz $TEMP_FOLDER/*.vidyo $TEMP_FOLDER/*.hpvc   2>/dev/null
   #rm -rf $TEMP_FOLDER/Products 2>/dev/null
   exit 1
fi

if [ "$1" = "continue" ]; then
   do_install
fi

if [ ! -f "$1" ]; then
   echo "UPDATEFAILED 1"
   vlog0 "$1 not found !!!"
   echo "File not found !" > $UPDATE_FAILED
   _Exit 1
fi

SFILE="$1"
BASE=$(basename $SFILE)
mv -f $SFILE $TEMP_FOLDER/
SFILE=$TEMP_FOLDER/$BASE
## check whether the signed file is compressed or not
if gunzip --test $SFILE 2>&1 > /dev/null; then
   vlog0 "Uncompressed signed file..."
   gunzip --suffix=".vidyo" $SFILE
   SFILE=${SFILE%\.vidyo}
else
   vlog0 "Signed file is not compressed..."
fi


### verify the signature
vlog0 "Verifying signature...[$SFILE]"
/opt/vidyo/bin/signcheck.sh "$SFILE"
if [ $? != 0  ]; then
   echo "UPDATEFAILED 1"
   vlog0 "Failed to verify signature!!!"
   echo "Failed to verify signature!" > $UPDATE_FAILED
   _Exit 1
fi

cd $TEMP_FOLDER
rm -rf $TEMP_FOLDER/Products

tar -zxf ${SFILE}.verified 1>/dev/null
#verify tar file is valid archive
if [ $? != 0  ]; then
   echo "UPDATEFAILED 2"
   vlog0 "Failed to untar ${SFILE}.verified!!!"
   _Exit 1
fi

# We don't allow users to accidentaly downgrade the portal 
if [ -f /opt/vidyo/VC2_VERSION ] ; then
   currentVer=`cat /opt/vidyo/VC2_VERSION | sed -e s/TAG_VC// | sed -e s/_/\ /g | awk '{ print $1 $2 $3 $4 }' | tr -cd "[:digit:]"`
else
  echo "UPDATEFAILED 2"
  _Exit 1
fi

if [ -f $TEMP_FOLDER/Products/VC2/install/scripts/production/VC2_VERSION ] ; then
	upgradeVer=`cat $TEMP_FOLDER/Products/VC2/install/scripts/production/VC2_VERSION | sed -e s/TAG_VC// | sed -e s/_/\ /g | awk '{ print $1 $2 $3 $4 }' | tr -cd "[:digit:]"`
fi

if [ "$upgradeVer" == ""  ] ; then
   if [[ ! "$1" =~ "Update" ]] ; then
      echo "UPDATEFAILED 2"
      _Exit 1
   fi
elif [ $upgradeVer -lt $currentVer ] ; then
   vlog0 "Failed! Downgrade is not supported"
   echo "UPDATEFAILED 2"
  _Exit 1
fi

#verify if the tar has right path
cd $TEMP_FOLDER/Products/HunterInstall/hunter 2>/dev/null
if [ $? != 0  ]; then
  vlog0 "UPDATEFAILED 3"
  echo "UPDATEFAILED 3"
  _Exit 1
fi

#if portal_preinstall.sh exists run it
if [ -f $TEMP_FOLDER/Products/HunterInstall/hunter/portal_preinstall.sh ]
 then
  $TEMP_FOLDER/Products/HunterInstall/hunter/portal_preinstall.sh
  if [ -f $TEMP_FOLDER/PRE_INSTALL_STATUS ]
  then
     vlog0 "UPDATEFAILED 5"
     echo "UPDATEFAILED 5"
     _Exit 1
   fi
fi

do_install

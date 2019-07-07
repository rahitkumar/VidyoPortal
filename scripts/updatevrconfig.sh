#!/bin/bash
###########################################################################
# Filename: updatevrconfig.sh
# Description: Generate a local VR config based on the hardware/cpu type.
#              This should be called during the installation.
# Hardware Codes:
# 0 - Unknown
# 1 - Asus
# 2 - Dell PowerEdge R210
# 3 - Dell PowerEdge R410
# 4 - VMWare/Virtual Machines
# 5 - Dell PowerEdge R420
# 6 - Dell PowerEdge R220
############################################################################

DMI=/usr/sbin/dmidecode
VRCONF=/opt/vidyo/vidyorouter2/localvrconfig

logme()
{
   logger -t "updatevrconfig.sh" "$*"
}

processor_version()
{
   [ -z "$PROC_VER" ] && PROC_VER=$($DMI --string processor-version)
   echo $PROC_VER | sed 's/ //g'
}

dell_210()
{
   # 31240 - R210-II "XL"
   # 1230V2 - R210-II V2
   # 31230 -  Original R210-II
   processor_version | grep -Eq "31240|1230"
   return $?
}

dell_220()
{
   # 1231 - Original R220
   processor_version | grep -q 1231
   return $?
}

dell_230()
{
   if [[ "$ASSET_TAG" =~ ^639|^737|^837 ]]; then
      return 0
   fi
   # 1230V5 - R230
   processor_version | grep -Eq "1230v5|1230v6"
   return $?
}


dell_410()
{
   processor_version | grep -q 5645
   return $?
}

dell_420()
{
   processor_version | grep -Eq "2420|2430|2440"
   return $?
}

dell_430()
{
   processor_version | grep -q 2620
   return $?
}

dell_630()
{
   processor_version | grep -q 2687
   return $?
}

MANUFACTURER=$($DMI  --string system-manufacturer)
PRODUCT_NAME=$($DMI --string system-product-name)
ASSET_TAG=$($DMI --string chassis-asset-tag)

[ "$MANUFACTURER" = QEMU ] && PRODUCT_NAME=QEMU   ### KVM

logme "Manufacturer: [$MANUFACTURER]"
logme "Model: [$PRODUCT_NAME]"
logme "Asset Tag: [$ASSET_TAG]"

#default values
NumTimerThreads=3
RoutingThreadThreshold=25
MaxNumConnections=0
HardwareCode=0

### Newer Dell does not populate the product name on the BIOS.
### Use the asset tag to identify the type of hardware.
NO_SPACE_PRODUCT_NAME=$(echo $PRODUCT_NAME | tr -d [:space:])
if [ -z "$NO_SPACE_PRODUCT_NAME" ]; then
   logme "Identifying product name based on the asset tag..."
   if [[ "$ASSET_TAG" =~ ^731|^732|^733|^734|^735|^631|^632|^634|^635|^637|^736|^638|^737 ]]; then
      PROC_VER=$($DMI --string processor-version)
      if dell_420; then
         PRODUCT_NAME="PowerEdge R420"
      elif dell_430; then
         PRODUCT_NAME="PowerEdge R430"
      elif dell_410; then
         PRODUCT_NAME="PowerEdge R410"
      elif dell_230; then
         PRODUCT_NAME="PowerEdge R230"
      elif dell_210; then
         PRODUCT_NAME="PowerEdge R210"
      elif dell_220; then
         PRODUCT_NAME="PowerEdge R220"
      else
         logme "Warning!!! Unknown processor type..."
      fi
      MANUFACTURER="Dell Inc."
      logme "Processor Version [$PROC_VER]"
   else
      logme "Warning!!! Unknown asset tag detected..."
   fi
   logme "Model: $PRODUCT_NAME"
fi


if [ "$PRODUCT_NAME" = "PowerEdge R410" ]; then
   ## keep a separate tree just incase 410 or 210 will have different values
   ## in the future.
   NumMediaSocketEngines=15
   NumSignalingSocketEngines=5
   NumTimerThreads=8
   NumRoutingThreads=15
   MaxNumConnections=150
   HardwareCode=3
elif [ "$PRODUCT_NAME" = "PowerEdge R420" ]; then
   NumMediaSocketEngines=15
   NumSignalingSocketEngines=5
   NumTimerThreads=8
   NumRoutingThreads=15
   MaxNumConnections=150
   HardwareCode=5
elif [ "$PRODUCT_NAME" = "PowerEdge R430" ]; then
   NumMediaSocketEngines=15
   NumSignalingSocketEngines=5
   NumTimerThreads=8
   NumRoutingThreads=15
   MaxNumConnections=150
   HardwareCode=5
elif [[ "$PRODUCT_NAME" =~ "PowerEdge R210" ]]; then
   NumMediaSocketEngines=5
   NumSignalingSocketEngines=3
   NumRoutingThreads=5
   MaxNumConnections=100
   HardwareCode=2
elif [[ "$PRODUCT_NAME" =~ "PowerEdge R230" ]]; then
   NumMediaSocketEngines=5
   NumSignalingSocketEngines=3
   NumRoutingThreads=5
   MaxNumConnections=100
   HardwareCode=2
elif [[ "$PRODUCT_NAME" =~ "PowerEdge R220" ]]; then
   NumMediaSocketEngines=5
   NumSignalingSocketEngines=3
   NumRoutingThreads=5
   MaxNumConnections=100
   HardwareCode=6
elif [[ "$PRODUCT_NAME" =~ VMware|QEMU ]]; then
   NumMediaSocketEngines=5
   NumSignalingSocketEngines=3
   NumRoutingThreads=5
   MaxNumConnections=100
   HardwareCode=4
else
   NumMediaSocketEngines=3
   NumSignalingSocketEngines=2
   NumRoutingThreads=4
   RoutingThreadThreshold=25
   MaxNumConnections=70
fi

## generic settings for virtual machine of any type
if  grep -iq hypervisor /proc/cpuinfo; then
   NumMediaSocketEngines=5
   NumSignalingSocketEngines=3
   NumRoutingThreads=5
   MaxNumConnections=100
   HardwareCode=4
fi

## override the MaxNumConnections if router is embedded router.
[ -x /opt/vidyo/vm/VidyoManager ] && MaxNumConnections=50

## set MaxNumerConnection based on the type of virtual machine (VR25/VR50/VR100).
[ -f /opt/vidyo/Vendor/VR25 ] && MaxNumConnections=25
[ -f /opt/vidyo/Vendor/VR50 ] && MaxNumConnections=50

## TODO: determine whether license is for VidyoOne only

if [ -f /etc/lsb-release ]; then
   . /etc/lsb-release
else
   DISTRIB_DESCRIPTION=unknown
fi

[ $HardwareCode -eq 0 ] && [ "$MANUFACTURER" = "ASUS" ] && HardwareCode=1

sed -i '/NumMediaSocketEngines\|NumSignalingSocketEngines\|NumTimerThreads\|NumRoutingThreads\|RoutingThreadThreshold\|MaxNumConnections\|HardwareName\|OperatingSystemVersion\|HardwareCode/d' $VRCONF

## append this to localvrconfig
cat << EOF >> $VRCONF
NumMediaSocketEngines=$NumMediaSocketEngines
NumSignalingSocketEngines=$NumSignalingSocketEngines
NumTimerThreads=$NumTimerThreads
NumRoutingThreads=$NumRoutingThreads
RoutingThreadThreshold=$RoutingThreadThreshold
MaxNumConnections=$MaxNumConnections
HardwareName=$PRODUCT_NAME($MANUFACTURER)
OperatingSystemVersion=$DISTRIB_DESCRIPTION
HardwareCode=$HardwareCode
EOF

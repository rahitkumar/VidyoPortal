#!/bin/bash

. /opt/vidyo/NetworkSettings

ifconfig eth0 mtu $MTU

if [ $AUTONEGOTIATION = "0" ] ; then
    ethtool -s eth0 autoneg off speed 100 duplex full
fi

#!/bin/bash


X=0

while [ $X -ne 1000 ]; do
   NAME="TEST_CONF_"${X}_$(date +%s)
   VALUE=${X}$(date +%s)
   mysql -D portal2 -e "INSERT INTO Configuration(ConfigurationName, ConfigurationValue) VALUES ('$NAME', '$$-$VALUE')"
   ((X++))

done

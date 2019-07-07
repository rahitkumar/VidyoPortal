#!/bin/bash
###############################################
# Description: Set the public FQDN on the database and on the /opt/vidyo/conf.d/server.conf

[ -z "$1" ] && exit 1

mysql --defaults-extra-file=/root/.my.cnf -uroot -D portal2 << EOF
UPDATE vidyo_manager_config
SET FQDN="$1";
EOF

for CONFIG in /opt/vidyo/vm/localvmconfig /opt/vidyo/vidyorouter2/localvrconfig ; do
   eval $(grep ConfigServerAddress= $CONFIG)
   if [[ $ConfigServerAddress =~ "http:" ]]; then
      sed -i "s/^ConfigServerAddress.*/ConfigServerAddress=http:\/\/$1/g" $CONFIG
   elif [[ $ConfigServerAddress =~ "https:" ]]; then
      sed -i "s/^ConfigServerAddress.*/ConfigServerAddress=https:\/\/$1/g" $CONFIG
   else
      sed -i "s/^ConfigServerAddress.*/ConfigServerAddress=$1/g" $CONFIG
   fi
done


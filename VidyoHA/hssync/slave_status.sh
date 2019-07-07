#!/bin/bash

MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

mysql $MYSQL_OPTS -E -e "SHOW SLAVE STATUS" | grep -E "Seconds_Behind_Master|Slave_SQL_Running_State|Slave_IO_State|Read_Master_Log_Pos|Last_SQL_Error|Last_SQL_Errno|Last_IO_Errno|Last_IO_Error|Master_Log_File|\
Slave_IO_Running|Slave_SQL_Running|Master_Retry_Count|Connect_Retry" | sed 's/: /=/g; s/^[[:space:]]*//g' | tr -c "[:alnum:]\n\.:=" _

#mysql $MYSQL_OPTS -E -e "SHOW SLAVE STATUS" | grep -E "Seconds_Behind_Master|Slave_SQL_Running_State|Slave_IO_State|Read_Master_Log_Pos|Last_SQL_Error|Last_SQL_Errno|Last_IO_Errno|Last_IO_Error|\
#Slave_IO_Running|Slave_SQL_Running|Master_Retry_Count|Connect_Retry" | sed 's/: /=/g; s/^[[:space:]]*//;s/\(^[[:alnum:]].*=\)/\U\1/g'
#sed 's/\(^[[:alnum:]].*=\)/\U\1/g'


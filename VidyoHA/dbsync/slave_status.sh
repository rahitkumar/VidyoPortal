#!/bin/bash

mysql -E -e "SHOW SLAVE STATUS" | grep -E "Seconds_Behind_Master|Slave_SQL_Running_State|Slave_IO_State|Read_Master_Log_Pos|Last_SQL_Error|Last_SQL_Errno|Last_IO_Errno|Last_IO_Error|\
Slave_IO_Running|Slave_SQL_Running|Master_Retry_Count|Connect_Retry"


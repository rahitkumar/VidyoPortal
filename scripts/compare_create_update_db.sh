#!/bin/bash -e
##########################################################################
# Filename: compare_create_update_db.sh
# Description: Compares the database schema and db version that will be
#              produced by CreatePortal20Db.sh and UpgradePortal2DB.sh.
#              This script will automatically remove the temporary files
#              and database. For debugging, run this script with "NOREMOVE" 
#              parameter and it will not remove the temporary files. 
#
# Date Created: 05/01/2013             
# Note: compares the portal2 DB only.
##########################################################################

PATH=$PATH:/usr/local/mysql/bin
CREATE_PORTALDB=/opt/vidyo/bin/CreatePortal20Db.sh
CREATE_TESTDB=/root/CreateTestPortal.sh
TEST_DB=testportal
PORTAL_DB=portal2
DIFFS=$(mktemp -u -p /root)
MYSQL_OPTS="--defaults-extra-file=/root/.my.cnf -uroot"

dump_schema()
{
   mysql $MYSQL_OPTS  -D information_schema -e \
     "SELECT RPAD(CONCAT(table_name,'-tbl'),25, ' '), \
             RPAD(UPPER(COLUMN_NAME), 25, ' '),  \
             COLUMN_DEFAULT,  \
             IS_NULLABLE,DATA_TYPE,  \
             COLUMN_TYPE, COLUMN_KEY  \
      FROM   columns  \
      WHERE  table_schema=\"$1\" \
      ORDER BY table_name,column_name"
}


get_db_version()
{
   mysql $MYSQL_OPTS -D $1 -N -s  -e "select ConfigurationValue from Configuration where configurationName='DBVersion'"
}

sed "s/$PORTAL_DB\./$TEST_DB\./g; s/=$PORTAL_DB/=$TEST_DB/g" $CREATE_PORTALDB > $CREATE_TESTDB
chmod +x $CREATE_TESTDB

$CREATE_TESTDB

dump_schema $PORTAL_DB > /root/$PORTAL_DB.schema
dump_schema $TEST_DB > /root/$TEST_DB.schema

set +e
diff /root/testportal.schema /root/portal2.schema -y --suppress-common-lines --width=300 > $DIFFS
RC=$?
set -e
if [ $RC -ne 0 ]; then
   echo "================================================================================"
   echo "  WARNING!!! CreatePortalDb.sh and UpgradePortalDb.sh produce different schema  "
   echo "================================================================================"
   cat $DIFFS 
else
   echo
   echo "==============================="
   echo "Database schema is identical..."
   echo "==============================="
fi

PORTAL_DB_VERSION=$(get_db_version $PORTAL_DB)
TEST_DB_VERSION=$(get_db_version $TEST_DB)
if [ "$PORTAL_DB_VERSION" != "$TEST_DB_VERSION" ]; then
   echo "=================================================================================="
   echo " WARNING!!! CreatePortalDb.sh and UpgradePortalDb.sh produce different DB Version "
   echo "=================================================================================="
   echo "UpdatePortalDB sets DB Version to: $PORTAL_DB_VERSION"
   echo "CreatePortalDB sets DB Version to: $TEST_DB_VERSION"
fi

echo -e "\n\n        ************** End of Test ****************"


### remove temp files and DB
if [ "$1" != "NOREMOVE" ]; then
   mysqladmin $MYSQL_OPTS --force drop $TEST_DB  > /dev/null 2>&1
   mysqladmin $MYSQL_OPTS --force drop ${TEST_DB}audit  > /dev/null 2>&1
   rm -f $CREATE_TESTDB
   rm -f $DIFFS
   rm -f /root/$TEST_DB.schema
   rm -f /root/$PORTAL_DB.schema
fi

exit $RC

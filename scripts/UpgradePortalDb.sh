#!/bin/bash
LANG=en_US.UTF-8
export LANG

mkdir -p /opt/vidyo/logs
exec > /opt/vidyo/logs/install/UpgradePortalDb.logs 2>&1

DB_NAME=portal2
DB_AUDIT_NAME=portal2audit
DB_BATCH_NAME=PORTAL_BATCH
MYSQL_OPT="--defaults-extra-file=/root/.my.cnf "

VC_TAG=%%TAG%%
f1=`echo $VC_TAG | sed s/"_"/" "/g | awk '{print $2}'| sed s/VC/"VidyoConferencing-"/g`
f2=`echo $VC_TAG | sed s/"_"/" "/g | awk '{print $3}'`
f3=`echo $VC_TAG | sed s/"_"/" "/g | awk '{print $4}'`
f4=`echo $VC_TAG | sed s/"_"/" "/g | awk '{print $5}'`

if [ -f /opt/vidyo/Vendor/DISA ]
then
    INTERNAL_VER="$f1.$f2.$f3(${f4}D)"
    VER="VidyoConferencing-3.4.16(${f4}D).DBv1910"
else
    INTERNAL_VER="$f1.$f2.$f3(${f4})"
    VER="VidyoConferencing-3.4.16(${f4}).DBv1910"
fi

export PATH=$PATH:/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/usr/local/mysql/bin

#get default IP addr

DATABASE_VERSION_STRING=`mysql $MYSQL_OPT -uroot -D $DB_NAME -e "SELECT configurationValue FROM Configuration WHERE configurationName='DBVersion'"`
DB_VER=`echo $DATABASE_VERSION_STRING | awk '{print $2}'`

TARGET_DB_VER="19.1.0"

logme()
{
   local FN=$(echo $0| tr -d "-")
   logger -t $(basename $FN) -p local0.notice "$*"
   echo "$*"
}

# Database version 2.25 is associated with VidyoPortal VC2_BRANCH_3_3_0.
#
upgradeTo2dot25()
{
    logger "Portal database version is $DB_VER upgrading to 2.25"

    mysql $MYSQL_OPT -u root << EOF

    USE \`$DB_NAME\`;

    alter table Services add column serviceEndpointGuid varchar(64) default null after url;
    alter table Services add column token varchar(64) default null after serviceEndpointGuid;
    alter table Services add column serviceRef varchar(40) default null after token;
    alter table Services add column updateTime timestamp default current_timestamp on update current_timestamp after serviceRef;

    create table GatewayPrefixes (
       prefixID int(10) not null auto_increment primary key,
       serviceID int(10) not null default 0,
       tenantID int(10) not null default 1,
       gatewayID varchar(40) not null default '',
       prefix varchar(32) not null default 'DEFAULT',
       direction int(1) not null default 0,
       updateTime timestamp default current_timestamp on update current_timestamp,
       unique key id_prefix_dir (serviceID, prefix, direction)
    ) ENGINE=MyISAM COLLATE utf8_general_ci ;


    CREATE TABLE TenantConfiguration (
       tenantID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
       endpointPrivateChat int(1) DEFAULT '1' NOT NULL,
       endpointPublicChat int(1) DEFAULT '1' NOT NULL,
       zincUrl varchar(255) DEFAULT NULL,
       zincEnabled int(1) DEFAULT '0' NOT NULL,
       waitingRoomsEnabled tinyint(1) NOT NULL DEFAULT '0',
       waitUntilOwnerJoins tinyint(1) NOT NULL DEFAULT '0',
       lectureModeStrict tinyint(1) NOT NULL DEFAULT '0',
       /* Keys */
      PRIMARY KEY (tenantID)
    ) ENGINE=MyISAM COLLATE utf8_general_ci;

    ALTER TABLE portal2.VirtualEndpoints ADD COLUMN deviceModel VARCHAR(50) DEFAULT NULL;
    ALTER TABLE portal2.VirtualEndpoints ADD COLUMN endpointPublicIPAddress VARCHAR(48) DEFAULT NULL;

    alter table Endpoints add column lectureModeSupport tinyint(1) default 0 not null;

    alter table ConferenceRecord add column updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
    alter table ConferenceRecord add column conferenceCreated tinyint(1) not null default 1;

    COMMIT;

    INSERT TenantIdpAttributeMapping (tenantID, vidyoAttributeName, idpAttributeName, defaultAttributeValue)
    SELECT DISTINCT tiam.tenantID, "Proxy", "", "No Proxy"
    FROM TenantIdpAttributeMapping tiam
    WHERE tiam.tenantID NOT IN (SELECT tiam2.tenantID FROM TenantIdpAttributeMapping tiam2 WHERE tiam2.vidyoAttributeName = "Proxy");

    INSERT TenantIdpAttributeMapping (tenantID, vidyoAttributeName, idpAttributeName, defaultAttributeValue)
    SELECT DISTINCT tiam.tenantID, "UserType", "", "Normal"
    FROM TenantIdpAttributeMapping tiam
    WHERE tiam.tenantID NOT IN (SELECT tiam2.tenantID FROM TenantIdpAttributeMapping tiam2 WHERE tiam2.vidyoAttributeName = "UserType");

    INSERT TenantIdpAttributeMapping (tenantID, vidyoAttributeName, idpAttributeName, defaultAttributeValue)
    SELECT DISTINCT tiam.tenantID, "LocationTag", "", "Default"
    FROM TenantIdpAttributeMapping tiam
    WHERE tiam.tenantID NOT IN (SELECT tiam2.tenantID FROM TenantIdpAttributeMapping tiam2 WHERE tiam2.vidyoAttributeName = "LocationTag");

    INSERT TenantIdpAttributeMapping (tenantID, vidyoAttributeName, idpAttributeName, defaultAttributeValue)
    SELECT DISTINCT tiam.tenantID, "Description", "", "Idp Provisioned User"
    FROM TenantIdpAttributeMapping tiam
    WHERE tiam.tenantID NOT IN (SELECT tiam2.tenantID FROM TenantIdpAttributeMapping tiam2 WHERE tiam2.vidyoAttributeName = "Description");

    INSERT TenantConfiguration (tenantID, endpointPrivateChat, endpointPublicChat)
    SELECT t.tenantID, 1, 1
    FROM Tenant t
    WHERE t.tenantID NOT IN (SELECT tc.tenantID FROM TenantConfiguration tc);

    COMMIT;

    alter table Conferences add column participantID varchar(64) default null after GroupName;
    alter table Conferences add column handRaised int(1) default null after video;
    alter table Conferences add column handRaisedTime datetime default null after handRaised;
    alter table Conferences add column presenter int(1) null default 0 after handRaisedTime;
    alter table Room add column lectureMode int(1) null default 0 after roomVideoSilenced;
    COMMIT;

    INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'chatAvailableFlag', 'true');
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'publicChatEnabledDefaultFlag', 'true');
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'privateChatEnabledDefaultFlag', 'true');
    ALTER TABLE Services MODIFY COLUMN password VARCHAR(128) NOT NULL;

    UPDATE Configuration SET configurationValue = 1 WHERE configurationName = 'FORCE_PASSWORD_CHANGE';

    COMMIT;
    QUIT

EOF

}

upgradeTo2dot26()
{
    logger "Portal database version is $DB_VER upgrading to 2.26"

    mysql $MYSQL_OPT -u root << EOF

    USE \`$DB_NAME\`;
    ALTER TABLE Member MODIFY COLUMN password VARCHAR(256) NOT NULL;
    ALTER TABLE Member MODIFY COLUMN passKey VARCHAR(256);
    ALTER TABLE Member MODIFY COLUMN sak VARCHAR(256);
    ALTER TABLE Guests MODIFY COLUMN sak VARCHAR(256);
    ALTER TABLE MemberPasswordHistory MODIFY COLUMN password VARCHAR(256) NOT NULL;
    ALTER TABLE VirtualEndpoints MODIFY displayExt VARCHAR(200);
  	ALTER TABLE VirtualEndpoints MODIFY displayName VARCHAR(200);
  	UPDATE Configuration SET configurationValue='100', configDate=NOW() WHERE configurationName = 'SCHEDULED_ROOM_INACTIVE_LIMIT';
    QUIT
EOF
}

upgradeTo2dot27()
{
    logger "Portal database version is $DB_VER upgrading to 2.27"

    mysql $MYSQL_OPT -u root << EOF

    USE \`$DB_NAME\`;

    INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'ScheduledRoom2', '1');
    DROP INDEX idx_part_roomName ON Room;
    DROP INDEX idx_part_roomExtNumber ON Room;
    DROP INDEX idx_roomName ON Room;
    DROP INDEX idx_part_memberName ON Member;
    CREATE FULLTEXT INDEX idx_fulltext_membername ON Member(memberName);
    CREATE FULLTEXT INDEX idx_fulltext_roomNameroomExt ON Room(roomName,roomExtNumber);
    UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
    QUIT
EOF
}

upgradeTo2dot28()
{
    logger "Portal database version is $DB_VER upgrading to 2.28"

    mysql $MYSQL_OPT -u root << EOF

    USE \`$DB_NAME\`;
	ALTER TABLE TenantConfiguration ADD COLUMN sessionExpirationPeriod int(11) NOT NULL DEFAULT 0;
	INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SESSION_EXP_PERIOD', '99999');
	UPDATE TenantConfiguration set sessionExpirationPeriod = '99999';
	UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
    QUIT
EOF
}

upgradeTo3dot40()
{
    logger "Portal database version is $DB_VER upgrading to 3.4.0"

    mysql $MYSQL_OPT -u root << EOF
    USE \`$DB_NAME\`;
    ALTER  TABLE rules ADD COLUMN RULE_ORDER INT(10) AFTER RULE_NAME;
    ALTER TABLE ruleset CHANGE COLUMN RULE_ORDER RULESET_ORDER INT(10);
    ALTER TABLE TenantConfiguration ADD COLUMN sessionExpirationPeriod int(11) NOT NULL DEFAULT 0;
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SESSION_EXP_PERIOD', '720');
    INSERT INTO Configuration (tenantID, configurationName, configurationValue,configDate) VALUES (0, 'HEARTBEAT_EXPIRY_SECONDS', '30', CURRENT_TIMESTAMP);

    UPDATE TenantConfiguration set sessionExpirationPeriod = 720;
    ALTER TABLE vidyo_manager_config ADD COLUMN USERNAME varchar(256) DEFAULT NULL;
    ALTER TABLE vidyo_manager_config ADD COLUMN PASSWORD varchar(256) DEFAULT NULL;


    CREATE TABLE vidyo_gateway_config (
      ID int(11) NOT NULL AUTO_INCREMENT,
      COMP_ID int(11) NOT NULL,
      USERNAME varchar(256) DEFAULT NULL,
      PASSWORD varchar(256) DEFAULT NULL,
      CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      UPDATE_TIME timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
      PRIMARY KEY (ID),
      KEY FK_VGC_CID_COMP_ID (COMP_ID),
      CONSTRAINT FK_VGC_CID_COMP_ID FOREIGN KEY (COMP_ID) REFERENCES components (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    CREATE TABLE vidyo_recorder_config (
      ID int(11) NOT NULL AUTO_INCREMENT,
      COMP_ID int(11) NOT NULL,
      USERNAME varchar(256) DEFAULT NULL,
      PASSWORD varchar(256) DEFAULT NULL,
      CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      UPDATE_TIME timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
      PRIMARY KEY (ID),
      KEY FK_VRCC_CID_COMP_ID (COMP_ID),
      CONSTRAINT FK_VRCC_CID_COMP_ID FOREIGN KEY (COMP_ID) REFERENCES components (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    CREATE TABLE vidyo_replay_config (
      ID int(11) NOT NULL AUTO_INCREMENT,
      COMP_ID int(11) NOT NULL,
      USERNAME varchar(256) DEFAULT NULL,
      PASSWORD varchar(256) DEFAULT NULL,
      CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      UPDATE_TIME timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
      PRIMARY KEY (ID),
      KEY FK_VRPC_CID_COMP_ID (COMP_ID),
      CONSTRAINT FK_VRPC_CID_COMP_ID FOREIGN KEY (COMP_ID) REFERENCES components (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    ALTER TABLE vidyo_gateway_config ADD COLUMN SERVICE_ENDPOINT_GUID varchar(64) DEFAULT NULL AFTER PASSWORD, ADD COLUMN TOKEN varchar(64) DEFAULT NULL AFTER SERVICE_ENDPOINT_GUID, ADD COLUMN SERVICE_REF varchar(40) DEFAULT NULL AFTER TOKEN;
    UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
    ALTER TABLE vidyo_gateway_config MODIFY COLUMN PASSWORD VARCHAR(256) DEFAULT NULL;
	ALTER TABLE vidyo_recorder_config MODIFY COLUMN PASSWORD VARCHAR(256) DEFAULT NULL;
	ALTER TABLE vidyo_replay_config MODIFY COLUMN PASSWORD VARCHAR(256) DEFAULT NULL;

	ALTER TABLE Conferences MODIFY COLUMN createdTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE Configuration ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE Endpoints ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE Endpoints MODIFY COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE ExternalLinks MODIFY COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE ExternalLinks MODIFY COLUMN modificationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE GatewayPrefixes ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER direction;

	ALTER TABLE Groups ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE Groups ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE Guests ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE Guests MODIFY COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE IpcConfiguration ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE IpcConfiguration ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE IpcPortalDomainList ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE IpcPortalDomainList ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE IpcTenantDomainList ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE IpcTenantDomainList ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE Locations ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE Locations ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE Member ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE Member ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE RecorderEndpoints ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER prefix;

	ALTER TABLE RecorderEndpoints MODIFY COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE Room ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE Room ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE SpeedDial ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE Tenant ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE Tenant ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE TenantConfiguration ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE TenantConfiguration ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE TenantIdpAttributeMapping ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE TenantIdpAttributeMapping ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE TenantIdpAttributeValueMapping ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE TenantIdpAttributeValueMapping ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE TenantLdapAttributeMapping ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE TenantLdapAttributeMapping ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE TenantLdapAttributeValueMapping ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE TenantLdapAttributeValueMapping ADD COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE TenantXauthRole ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE TenantXcall ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE TenantXlocation ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE TenantXservice ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE VirtualEndpoints ADD COLUMN creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER prefix;

	ALTER TABLE VirtualEndpoints MODIFY COLUMN updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE cloud_config ADD COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE cloud_config ADD COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE components MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE components MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE components_type MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE components_type MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE pool MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE pool MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE pool_priority_map MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE pool_priority_map MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE pool_to_pool MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE pool_to_pool MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE priority_list MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE priority_list MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE router_media_addr_map MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE router_media_addr_map MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE router_pool_map MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE router_pool_map MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE rules MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE rules MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE ruleset MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE ruleset MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE vidyo_gateway_config MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE vidyo_gateway_config MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE vidyo_manager_config MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE vidyo_manager_config MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE vidyo_recorder_config MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE vidyo_recorder_config MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE vidyo_replay_config MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE vidyo_replay_config MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

	ALTER TABLE vidyo_router_config MODIFY COLUMN CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;

	ALTER TABLE vidyo_router_config MODIFY COLUMN UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

        ALTER TABLE Configuration MODIFY COLUMN configurationValue MEDIUMTEXT NOT NULL;

        ALTER TABLE cloud_config ADD COLUMN config_xml MEDIUMTEXT AFTER status;

        ALTER TABLE vidyo_router_config ADD COLUMN PROXY_ENABLED int(1) NOT NULL DEFAULT '1' AFTER SIGNALING_DSCP;

        ALTER TABLE vidyo_router_config ADD COLUMN PROXY_USE_TLS int(1) NOT NULL DEFAULT '0' AFTER PROXY_ENABLED;

        ALTER TABLE components DROP INDEX uc_CompID;

        ALTER TABLE components ADD UNIQUE KEY UK_COMP_ID_COMP_TYPE (COMP_ID, COMP_TYPE_ID);

        ALTER TABLE Configuration ADD COLUMN oldConfigurationValue mediumtext;
        ALTER TABLE Member ADD COLUMN proxyIDOld int(10) unsigned DEFAULT NULL after proxyID;

    QUIT
EOF
}

upgradeTo3dot41()
{
    logger "Portal database version is $DB_VER upgrading to 3.4.1"

    mysql $MYSQL_OPT -u root << EOF
    USE \`$DB_NAME\`;

	ALTER TABLE pool DROP FOREIGN KEY FK_PL_CCID_CC_ID;

	ALTER TABLE pool DROP KEY FK_PL_CCID_CC_ID;

	ALTER TABLE pool MODIFY COLUMN CLOUD_CONFIG_ID int(11) NOT NULL;

	ALTER TABLE pool ADD CONSTRAINT FK_PL_CCID_CC_ID FOREIGN KEY FK_PL_CCID_CC_ID (CLOUD_CONFIG_ID) REFERENCES cloud_config (ID) ON DELETE CASCADE;

	ALTER TABLE pool DROP PRIMARY KEY, ADD PRIMARY KEY (ID, CLOUD_CONFIG_ID);

	ALTER TABLE router_pool_map DROP FOREIGN KEY FK_RPM_PID_PL_ID;

	ALTER TABLE router_pool_map DROP KEY FK_RPM_PID_PL_ID;

	ALTER TABLE router_pool_map DROP FOREIGN KEY FK_RPM_CCID_CC_ID;

	ALTER TABLE router_pool_map DROP KEY FK_RPM_CCID_CC_ID;

	ALTER TABLE router_pool_map ADD CONSTRAINT FK_RPM_PID_PLCCID FOREIGN KEY FK_RPM_PID_PLCCID (POOL_ID, CLOUD_CONFIG_ID) REFERENCES pool (ID,CLOUD_CONFIG_ID) ON DELETE CASCADE;

	ALTER TABLE pool_to_pool ADD COLUMN CLOUD_CONFIG_ID int(11) NOT NULL;

	ALTER TABLE pool_to_pool DROP PRIMARY KEY, DROP COLUMN ID, ADD PRIMARY KEY (POOL1, POOL2, CLOUD_CONFIG_ID);

	ALTER TABLE pool_to_pool DROP FOREIGN KEY FK_PTP_PID1_PL_ID;

	ALTER TABLE pool_to_pool DROP KEY FK_PTP_PID1_PL_ID;

	ALTER TABLE pool_to_pool DROP FOREIGN KEY FK_PTP_PID2_PL_ID;

	ALTER TABLE pool_to_pool DROP KEY FK_PTP_PID2_PL_ID;

	UPDATE pool_to_pool p2p INNER JOIN ( SELECT pl.id, pl.cloud_config_id FROM pool pl GROUP BY pl.id, pl.cloud_config_id) plc ON (p2p.pool1 = plc.id OR p2p.pool2 = plc.id) SET p2p.cloud_config_id = plc.cloud_config_id;

	ALTER TABLE pool_to_pool ADD CONSTRAINT FK_PTP_PID1_PL_ID FOREIGN KEY FK_PTP_PID1_PL_ID (POOL1,CLOUD_CONFIG_ID) REFERENCES pool (ID,CLOUD_CONFIG_ID) ON DELETE CASCADE;

	ALTER TABLE pool_to_pool ADD CONSTRAINT FK_PTP_PID2_PL_ID FOREIGN KEY FK_PTP_PID2_PL_ID (POOL2,CLOUD_CONFIG_ID) REFERENCES pool (ID,CLOUD_CONFIG_ID) ON DELETE CASCADE;

	ALTER TABLE pool_priority_map ADD COLUMN CLOUD_CONFIG_ID int(11) NOT NULL;

	ALTER TABLE pool_priority_map DROP PRIMARY KEY, DROP COLUMN ID, ADD PRIMARY KEY (POOL_ID, PRIORITY_LIST_ID, CLOUD_CONFIG_ID);

	ALTER TABLE pool_priority_map DROP FOREIGN KEY FK_PPM_PLID_PL_ID;

	ALTER TABLE pool_priority_map DROP KEY FK_PPM_PLID_PL_ID;

	UPDATE pool_priority_map ppm INNER JOIN ( SELECT pl.id, pl.cloud_config_id FROM pool pl GROUP BY pl.id, pl.cloud_config_id) plc ON ( ppm.pool_id = plc.id) SET ppm.cloud_config_id = plc.cloud_config_id;

	ALTER TABLE pool_priority_map ADD CONSTRAINT FK_PPM_PLID_CCID FOREIGN KEY FK_PPM_PLID_CCID (POOL_ID,CLOUD_CONFIG_ID) REFERENCES pool (ID,CLOUD_CONFIG_ID) ON DELETE CASCADE;

	ALTER TABLE rules DROP FOREIGN KEY FK_RUL_PPLID_PL_ID;

	ALTER TABLE rules DROP KEY FK_RUL_PPLID_PL_ID;

	ALTER TABLE rules ADD CONSTRAINT FK_RUL_PPLID_PL_ID FOREIGN KEY FK_RUL_PPLID_PL_ID (POOL_PRIORITY_LIST_ID) REFERENCES priority_list (ID) ON DELETE CASCADE;

	ALTER TABLE ruleset DROP FOREIGN KEY FK_RST_RID_RUL_ID;

	ALTER TABLE ruleset DROP KEY FK_RST_RID_RUL_ID;

	ALTER TABLE ruleset ADD CONSTRAINT FK_RST_RID_RUL_ID FOREIGN KEY FK_RST_RID_RUL_ID (RULE_ID) REFERENCES rules (ID) ON DELETE CASCADE;

	UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';

    QUIT
EOF
}

upgradeTo3dot42()
{
    logger "Portal database version is $DB_VER upgrading to 3.4.2"

     mysql $MYSQL_OPT -u root << EOF
     USE \`$DB_NAME\`;
     ALTER TABLE Member DROP INDEX idx_fulltext_membername;
     ALTER TABLE Room DROP INDEX idx_fulltext_roomNameroomExt;
     ALTER TABLE NetworkElementConfiguration MODIFY COLUMN IpAddress varchar(255);
     ALTER TABLE Member MODIFY COLUMN emailAddress varchar(254) NOT NULL;
     ALTER TABLE Room MODIFY COLUMN roomName varchar(150) NOT NULL;
EOF

    mysql $MYSQL_OPT -u root --silent -N -e  "\
      SELECT CONCAT('ALTER TABLE ', table_name, ' ENGINE=InnoDb;') \
      FROM information_schema.tables \
      WHERE table_schema='portal2' AND engine='MyISAM'" | mysql $MYSQL_OPT -u root -D portal2

    mysql $MYSQL_OPT -u root << EOF
    USE \`$DB_NAME\`;
    CREATE FULLTEXT INDEX idx_fulltext_membername on Member(memberName);
    CREATE FULLTEXT INDEX idx_fulltext_roomNameroomExt on Room(roomName,roomExtNumber);
    ALTER TABLE pool MODIFY POOL_NAME VARCHAR(40) NOT NULL;
    ALTER TABLE Member MODIFY COLUMN username VARCHAR(128) NOT NULL;
    ALTER TABLE persistent_logins MODIFY COLUMN username VARCHAR(128) NOT NULL;
    ALTER TABLE ConferenceCall2 MODIFY COLUMN RoomOwner VARCHAR(128) NOT NULL DEFAULT '';

    DROP INDEX idx_partlangID ON Member;
    DROP INDEX idx_memberID ON Room;
    DROP INDEX activeMembers ON Member;
    CREATE INDEX idx_proxyId ON Member (proxyID);
    CREATE INDEX idx_tenantId ON Member (tenantID);
    CREATE INDEX idx_roleId ON Member (roleID);
    CREATE INDEX idx_locationId ON Member (locatiONID);
    CREATE INDEX idx_tenantId ON Groups (tenantID);
    CREATE INDEX idx_memberIDroomTypeIDroomEnabled ON Room (memberID, roomTypeID, roomEnabled);
    CREATE INDEX idx_memberIDactiveallowedToParticipate ON Member (memberID, active, allowedToParticipate);
    CREATE INDEX idx_memberID ON SpeedDial (memberID);
    CREATE INDEX idx_roomID ON SpeedDial (roomID);

    ALTER TABLE vidyo_gateway_config MODIFY USERNAME varchar(255);
    ALTER TABLE vidyo_gateway_config ADD UNIQUE KEY UK_USERNAME  (USERNAME);
    ALTER TABLE vidyo_replay_config MODIFY USERNAME varchar(255);
    ALTER TABLE vidyo_replay_config ADD UNIQUE KEY UK_USERNAME  (USERNAME);
    ALTER TABLE vidyo_recorder_config MODIFY USERNAME varchar(255);
    ALTER TABLE vidyo_recorder_config ADD UNIQUE KEY UK_USERNAME  (USERNAME);

    UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
    COMMIT;
    QUIT
EOF

    mysql $MYSQL_OPT -u root << EOF
    USE \`$DB_AUDIT_NAME\`;
    ALTER TABLE TransactionHistory MODIFY COLUMN userID VARCHAR(128) NOT NULL;
    COMMIT;
    USE mysql;
    DELETE FROM db WHERE db NOT IN ('PORTAL_BATCH','mysql','portal2','portal2audit');
    DELETE FROM db WHERE db = 'portal2' AND host = '%';
    QUIT
EOF


}

upgradeTo3dot43()
{
    logger "Portal database version is $DB_VER upgrading to 3.4.3"

     mysql $MYSQL_OPT -u root << EOF
     USE \`$DB_NAME\`;

     ALTER TABLE Room DROP KEY idx_fulltext_roomNameroomExt;
     ALTER TABLE Room ADD COLUMN displayName varchar(128) NOT NULL;
     UPDATE Room SET displayName = roomName WHERE displayName = '';
     CREATE FULLTEXT INDEX idx_fulltext_roomNameroomExtdisplayName ON Room( roomName,roomExtNumber,displayName);
	  CREATE FULLTEXT INDEX idx_fulltext_Mem_memname_username ON Member(membername, username);

     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'CREATE_PUBLIC_ROOM_FLAG', '1', NOW(), NOW());
     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'MAX_CREATE_PUBLIC_ROOM', '100000', NOW(), NOW());
     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'MAX_CREATE_PUBLIC_ROOM_USER', '5', NOW(), NOW());
     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'SELECTED_MAX_ROOM_EXT_LENGTH', '12', NOW(), NOW());

     ALTER TABLE TenantConfiguration ADD COLUMN createPublicRoomEnable INT(1) NOT NULL DEFAULT 0;
     ALTER TABLE TenantConfiguration ADD COLUMN maxCreatePublicRoomUser int(11) NOT NULL DEFAULT 0;
     ALTER TABLE TenantConfiguration ADD COLUMN maxCreatePublicRoomTenant int(11) NOT NULL DEFAULT -1;

     alter table TenantConfiguration add column minMediaPort smallint unsigned default 0 not null;
     alter table TenantConfiguration add column maxMediaPort smallint unsigned default 0 not null;
     alter table TenantConfiguration add column alwaysUseProxy tinyint(1) default -1 not null;

     alter table TenantConfiguration change column minMediaPort minMediaPort smallint unsigned default 0 not null;
     alter table TenantConfiguration change column maxMediaPort maxMediaPort smallint unsigned default 0 not null;
     alter table TenantConfiguration change column alwaysUseProxy alwaysUseProxy tinyint(1) default -1 not null;

     alter table TenantConfiguration add column lectureModeAllowed tinyint(1) default 1 not null;

     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'SCHEDULED_ROOM_BATCH_LIMIT', '1000', NOW(), NOW());

     ALTER TABLE Room Add COLUMN deleteon TIMESTAMP NULL DEFAULT NULL;
     ALTER TABLE Room ADD INDEX idx_deleteon (deleteon);

     ALTER TABLE Member ADD COLUMN phone1 varchar(64) DEFAULT NULL;
     ALTER TABLE Member ADD COLUMN phone2 varchar(64) DEFAULT NULL;
     ALTER TABLE Member ADD COLUMN phone3 varchar(64) DEFAULT NULL;
     ALTER TABLE Member ADD COLUMN department varchar(128) DEFAULT NULL;
     ALTER TABLE Member ADD COLUMN title varchar(128) DEFAULT NULL;
     ALTER TABLE Member ADD COLUMN instantMessagerID varchar(128) DEFAULT NULL;
     ALTER TABLE Member MODIFY COLUMN location varchar(255) DEFAULT NULL;

     CREATE INDEX idx_RoomName ON Room (roomName);

     UPDATE TenantConfiguration SET maxCreatePublicRoomTenant=100000 WHERE maxCreatePublicRoomTenant=-1;

     UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';

     COMMIT;
     QUIT
EOF
}

upgradeTo3dot44()
{
    logger "Portal database version is $DB_VER upgrading to 3.4.4"

     mysql $MYSQL_OPT -u root << EOF
     USE \`$DB_NAME\`;
     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'USER_IMAGE', '0', NOW(), NOW());
     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'UPLOAD_USER_IMAGE', '0', NOW(), NOW());
     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'MAX_USER_IMAGE_SIZE_KB', '100', NOW(), NOW());

     UPDATE Room SET roomKey = NULL WHERE roomKey = '';
     UPDATE Room SET roomModeratorKey = NULL WHERE roomModeratorKey = '';

     ALTER TABLE TenantConfiguration ADD COLUMN userImage TINYINT(1) NOT NULL DEFAULT 0;
     ALTER TABLE TenantConfiguration ADD COLUMN uploadUserImage TINYINT(1) NOT NULL DEFAULT 0;
     ALTER TABLE Member ADD COLUMN userImageUploaded TINYINT(1) NOT NULL DEFAULT 0;
     ALTER TABLE Member ADD COLUMN userImageAllowed TINYINT(1) NOT NULL DEFAULT 1;
     ALTER TABLE Member ADD COLUMN lastModifiedDateExternal varchar(32) DEFAULT NULL;
     CREATE UNIQUE INDEX idx_roomModeratorKey on Room (roomModeratorKey);
     CREATE UNIQUE INDEX idx_roomKey on Room (roomKey);

     INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'ROOM_KEY_LENGTH', '10');
     INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'ROOM_LINK_FORMAT', 'flex');

     ALTER TABLE Member ADD COLUMN thumbnailUpdateTime TIMESTAMP NULL DEFAULT NULL;
     ALTER TABLE Tenant ADD COLUMN tenantWebRTCURL varchar(128) DEFAULT NULL;

     ALTER TABLE TenantConfiguration ADD COLUMN vidyoNeoWebRTCGuestEnabled TINYINT(1) NOT NULL DEFAULT 1;
     ALTER TABLE TenantConfiguration ADD COLUMN vidyoNeoWebRTCUserEnabled TINYINT(1) NOT NULL DEFAULT 0;

     ALTER TABLE Guests MODIFY COLUMN GUESTNAME VARCHAR(255);

     UPDATE TenantConfiguration SET vidyoNeoWebRTCGuestEnabled = 1;

     CREATE INDEX idx_tenant ON ConferenceCall2(TenantName);
     CREATE INDEX idx_jointime ON ConferenceCall2(JoinTime);
     CREATE INDEX idx_leavetime ON ConferenceCall2(LeaveTime);

     UPDATE Configuration SET tenantID = 0 WHERE configurationName IN ('TLS_PROXY_ENABLED', 'PASSWORD_VALIDITY_DAYS', 'SHOW_CUSTOMIZE_BANNER', 'SHOW_FORGOT_PASSWORD_LINK');

     UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';

     COMMIT;
     QUIT
EOF
}

upgradeTo3dot45()
{
    logger "Portal database version is $DB_VER upgrading to 3.4.5"

    ## Note: Do not mix the PORTAL_BATCH changes to portal2 DB.
    mysql $MYSQL_OPT -u root << EOF
    USE PORTAL_BATCH;
    DROP TABLE IF EXISTS BATCH_JOB_EXECUTION;
    DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_CONTEXT;
    DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_PARAMS;
    DROP TABLE IF EXISTS BATCH_JOB_EXECUTION_SEQ;
    DROP TABLE IF EXISTS BATCH_JOB_INSTANCE;
    DROP TABLE IF EXISTS BATCH_JOB_PARAMS;
    DROP TABLE IF EXISTS BATCH_JOB_SEQ;
    DROP TABLE IF EXISTS BATCH_STEP_EXECUTION;
    DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_CONTEXT;
    DROP TABLE IF EXISTS BATCH_STEP_EXECUTION_SEQ;


    CREATE TABLE BATCH_JOB_INSTANCE  (
       JOB_INSTANCE_ID BIGINT  NOT NULL PRIMARY KEY ,
       VERSION BIGINT ,
       JOB_NAME VARCHAR(100) NOT NULL,
       JOB_KEY VARCHAR(32) NOT NULL,
       constraint JOB_INST_UN unique (JOB_NAME, JOB_KEY)
    ) ENGINE=InnoDB;

    CREATE TABLE BATCH_JOB_EXECUTION  (
       JOB_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
       VERSION BIGINT  ,
       JOB_INSTANCE_ID BIGINT NOT NULL,
       CREATE_TIME DATETIME NOT NULL,
       START_TIME DATETIME DEFAULT NULL ,
       END_TIME DATETIME DEFAULT NULL ,
       STATUS VARCHAR(10) ,
       EXIT_CODE VARCHAR(2500) ,
       EXIT_MESSAGE VARCHAR(2500) ,
       LAST_UPDATED DATETIME,
       JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
       constraint JOB_INST_EXEC_FK foreign key (JOB_INSTANCE_ID)
       references BATCH_JOB_INSTANCE(JOB_INSTANCE_ID)
    ) ENGINE=InnoDB;

    CREATE TABLE BATCH_JOB_EXECUTION_PARAMS  (
       JOB_EXECUTION_ID BIGINT NOT NULL ,
       TYPE_CD VARCHAR(6) NOT NULL ,
       KEY_NAME VARCHAR(100) NOT NULL ,
       STRING_VAL VARCHAR(250) ,
       DATE_VAL DATETIME DEFAULT NULL ,
       LONG_VAL BIGINT ,
       DOUBLE_VAL DOUBLE PRECISION ,
       IDENTIFYING CHAR(1) NOT NULL ,
       constraint JOB_EXEC_PARAMS_FK foreign key (JOB_EXECUTION_ID)
       references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
    ) ENGINE=InnoDB;

    CREATE TABLE BATCH_STEP_EXECUTION  (
       STEP_EXECUTION_ID BIGINT  NOT NULL PRIMARY KEY ,
       VERSION BIGINT NOT NULL,
       STEP_NAME VARCHAR(100) NOT NULL,
       JOB_EXECUTION_ID BIGINT NOT NULL,
       START_TIME DATETIME NOT NULL ,
       END_TIME DATETIME DEFAULT NULL ,
       STATUS VARCHAR(10) ,
       COMMIT_COUNT BIGINT ,
       READ_COUNT BIGINT ,
       FILTER_COUNT BIGINT ,
       WRITE_COUNT BIGINT ,
       READ_SKIP_COUNT BIGINT ,
       WRITE_SKIP_COUNT BIGINT ,
       PROCESS_SKIP_COUNT BIGINT ,
       ROLLBACK_COUNT BIGINT ,
       EXIT_CODE VARCHAR(2500) ,
       EXIT_MESSAGE VARCHAR(2500) ,
       LAST_UPDATED DATETIME,
       constraint JOB_EXEC_STEP_FK foreign key (JOB_EXECUTION_ID)
       references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
    ) ENGINE=InnoDB;

    CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT  (
       STEP_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
       SHORT_CONTEXT VARCHAR(2500) NOT NULL,
       SERIALIZED_CONTEXT TEXT ,
       constraint STEP_EXEC_CTX_FK foreign key (STEP_EXECUTION_ID)
       references BATCH_STEP_EXECUTION(STEP_EXECUTION_ID)
    ) ENGINE=InnoDB;

    CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT  (
       JOB_EXECUTION_ID BIGINT NOT NULL PRIMARY KEY,
       SHORT_CONTEXT VARCHAR(2500) NOT NULL,
       SERIALIZED_CONTEXT TEXT ,
       constraint JOB_EXEC_CTX_FK foreign key (JOB_EXECUTION_ID)
       references BATCH_JOB_EXECUTION(JOB_EXECUTION_ID)
    ) ENGINE=InnoDB;

    CREATE TABLE BATCH_STEP_EXECUTION_SEQ (
       ID BIGINT NOT NULL,
       UNIQUE_KEY CHAR(1) NOT NULL,
       constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
    ) ENGINE=InnoDB;

    INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_STEP_EXECUTION_SEQ);

    CREATE TABLE BATCH_JOB_EXECUTION_SEQ (
       ID BIGINT NOT NULL,
       UNIQUE_KEY CHAR(1) NOT NULL,
       constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
    ) ENGINE=InnoDB;

    INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_EXECUTION_SEQ);

    CREATE TABLE BATCH_JOB_SEQ (
       ID BIGINT NOT NULL,
       UNIQUE_KEY CHAR(1) NOT NULL,
       constraint UNIQUE_KEY_UN unique (UNIQUE_KEY)
    ) ENGINE=InnoDB;

    INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_SEQ);
    COMMIT;
    QUIT
EOF

     mysql $MYSQL_OPT -u root << EOF
     USE \`$DB_NAME\`;

     ALTER TABLE EndpointUpload MODIFY endpointUploadType VARCHAR(3) NOT NULL COMMENT 'M/W32/W64/R/V';
     ALTER TABLE EndpointUpload MODIFY endpointUploadFile varchar(1028) NOT NULL;
     ALTER TABLE EndpointUpload ADD endpointUploadVersion VARCHAR(130) DEFAULT NULL;
     ALTER TABLE TenantConfiguration ADD endpointUploadMode VARCHAR(14) DEFAULT 'VidyoPortal';
     UPDATE TenantConfiguration SET endpointUploadMode = 'VidyoPortal' WHERE endpointUploadMode IS NULL;
     UPDATE Configuration SET tenantID = 0 WHERE configurationName IN ('SuperEmailTo','SuperEmailFrom');
     UPDATE EndpointUpload SET endpointUploadType = CASE WHEN endpointUploadFile like '%win32%' THEN 'W32' ELSE 'W64' END WHERE endpointUploadType = 'W';
     INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'MANAGE_ENDPOINT_UPLOAD_MODE', 'VidyoPortal');
     ALTER TABLE TenantConfiguration ADD COLUMN logAggregation TINYINT(1) NOT NULL DEFAULT 0;
     ALTER TABLE Conferences ADD COLUMN speaker int(1) DEFAULT '1' COMMENT '1-unsilenced, 0 -silenced' AFTER video;
     ALTER TABLE ConferenceRecord ADD COLUMN speaker int(1) DEFAULT '1' COMMENT '1-unsilenced, 0 -silenced' AFTER video;
     ALTER TABLE Room ADD COLUMN roomSpeakerMuted INT(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '0-unsilenced, 1 -silenced' AFTER roomVideoSilenced;
     ALTER TABLE TenantLdapAttributeValueMapping MODIFY ldapValueName VARCHAR(1024) NOT NULL;
     ALTER TABLE TenantIdpAttributeValueMapping MODIFY idpValueName VARCHAR(1024) NOT NULL;
     ALTER IGNORE TABLE ClientInstallations2 ADD UNIQUE KEY UNIQUE_EID (EID);
     UPDATE Room r LEFT JOIN Member m ON r.memberId = m.memberId SET r.displayName = m.memberName WHERE r.RoomTypeID=1;
     CREATE FULLTEXT INDEX idx_fulltext_Mem_memname_emailaddress ON Member(membername, emailaddress);
     INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'CDN_OPTIONAL_PARAMETER', 'response-content-disposition');

     UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
     COMMIT;
     QUIT
EOF
}

upgradeTo3dot46()
{
     logger "Portal database version is $DB_VER upgrading to 3.4.6"
     mysql $MYSQL_OPT -uroot -D portal2 << EOF

     ALTER TABLE Tenant MODIFY COLUMN tenantPrefix varchar(7);
     CREATE INDEX idx_guid ON persistent_logins (endpoint_guid);
     CREATE INDEX idx_tenantid ON persistent_logins (tenant_id);
     CREATE INDEX idx_username ON persistent_logins (username);
     UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
     COMMIT;
     QUIT
EOF
}


upgradeTo3dot50()
{
    echo "**************3.5.0******************** "
	logger "Portal database version is $DB_VER upgrading to 3.5.0"

     mysql $MYSQL_OPT -u root << EOF
     USE \`$DB_NAME\`;

     DROP TABLE IF EXISTS EndpointBehavior;

     CREATE TABLE EndpointBehavior (
              endpointBehaviorID      int(10) AUTO_INCREMENT NOT NULL,
              tenantID            int(10) UNSIGNED NOT NULL,
              endpointBehaviorKey     varchar(64) NOT NULL,
              windowSizeHeight          int(10) DEFAULT NULL,
              windowSizeWidth          int(10) DEFAULT NULL,
              windowPositionTop          int(10) DEFAULT NULL,
              windowPositionBottom          int(10) DEFAULT NULL,
              windowPositionLeft          int(10) DEFAULT NULL,
              windowPositionRight          int(10) DEFAULT NULL,
              welcomePage          int(1) UNSIGNED NOT NULL,
              beautyScreen          int(1) UNSIGNED NOT NULL,
              loginModule          int(1) UNSIGNED NOT NULL,
              publicChat          int(1) UNSIGNED NOT NULL,
              leftPanel          int(1) UNSIGNED NOT NULL,
              inCallSearch          int(1) UNSIGNED NOT NULL,
              inviteParticipants          int(1) UNSIGNED NOT NULL,
              contentSharing          int(1) UNSIGNED NOT NULL,
              shareDialogOnJoin          int(1) UNSIGNED NOT NULL,
              displayLabels          int(1) UNSIGNED NOT NULL,
              remoteContentAccess          int(1) UNSIGNED NOT NULL,
              cameraMuteControl          int(1) UNSIGNED NOT NULL,
              muteCameraOnEntry          int(1) UNSIGNED NOT NULL,
              audioMuteControl          int(1) UNSIGNED NOT NULL,
              muteAudioOnEntry          int(1) UNSIGNED NOT NULL,
              deviceSettings          int(1) UNSIGNED NOT NULL,
              pinnedParticipant          int(1) UNSIGNED NOT NULL,
              recordConference          int(1) UNSIGNED NOT NULL,
              recordingRole         varchar(64) DEFAULT NULL,
              exitOnUserHangup          int(1) UNSIGNED NOT NULL,
              automaticallyUpdate          int(1) UNSIGNED NOT NULL,
              lockUserName          int(1) UNSIGNED NOT NULL,
              enableAutoAnswer          int(1) UNSIGNED NOT NULL,
              participantNotification          int(1) UNSIGNED NOT NULL,
              fullScreenVideo          int(1) UNSIGNED NOT NULL,
              preIframeUrl             varchar(2048) DEFAULT NULL,
              preIframeSize         int(10) DEFAULT NULL,
              topIframeUrl             varchar(2048) DEFAULT NULL,
              topIframeSize         int(10) DEFAULT NULL,
              leftIframeUrl             varchar(2048) DEFAULT NULL,
              leftIframeSize         int(10) DEFAULT NULL,
              rightIframeUrl             varchar(2048) DEFAULT NULL,
              rightIframeSize         int(10) DEFAULT NULL,
              bottomIframeUrl             varchar(2048) DEFAULT NULL,
              bottomIframeSize         int(10) DEFAULT NULL,
              postIframeUrl             varchar(2048) DEFAULT NULL,
              postIframeSize         int(10) DEFAULT NULL,
              createdTime     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
              modifiedTime    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
              /* Keys */
              PRIMARY KEY (endpointBehaviorID),
              UNIQUE KEY idx_endpointBehaviorKey (endpointBehaviorKey),
              FOREIGN KEY FK_EB_tenantID(tenantID) REFERENCES Tenant (tenantID) ON DELETE CASCADE
    ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

     ALTER TABLE TenantConfiguration ADD COLUMN endpointBehavior TINYINT(1) NOT NULL DEFAULT 0;
     INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'OPUS_AUDIO', '1');

     CREATE TABLE IF NOT EXISTS country (
              countryID     int(10) AUTO_INCREMENT NOT NULL,
              iso           varchar(2) NOT NULL,
              name          varchar(80) NOT NULL,
              nick_name      varchar(80) NOT NULL,
              iso_3          varchar(3) DEFAULT NULL,
              num_code       varchar(6) DEFAULT NULL,
              phone_code     int(5) NOT NULL,
              flag_file_name      varchar(128) DEFAULT NULL,
              active        int(1) DEFAULT 0,
              created_time       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
              modified_time      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
              /* Keys */
              PRIMARY KEY (countryID),
              UNIQUE KEY idx_iso (iso),
              UNIQUE KEY idx_iso_3 (iso_3)
     ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

     CREATE TABLE IF NOT EXISTS tenant_country_mapping (
              countryID         int(10) NOT NULL,
              tenantID          int(10) UNSIGNED NOT NULL,
              dialin_label       varchar(30) DEFAULT NULL,
              dialin_number      varchar(64) NOT NULL,
              created_time       timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
              modified_time      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
              /* Keys */
              FOREIGN KEY FK_TDC_tenantID(tenantID) REFERENCES Tenant (tenantID) ON DELETE CASCADE,
              FOREIGN KEY FK_TDC_countryID(countryID) REFERENCES country (countryID) ON DELETE CASCADE
     ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

     INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'COUNTRYFLAG_IMAGE_PATH', '/upload/');

     INSERT INTO Tenant (tenantID, tenantName) VALUES (0, 'Super Tenant');

     INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SDK220', '0');
     UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
     COMMIT;
     QUIT
EOF
    echo "**************3.5.0******************** DONE"
}

upgradeTo3dot51()
{
    echo "**************3.5.1********************"
	logger "Portal database version is $DB_VER upgrading to 3.5.1"

     mysql $MYSQL_OPT -u root << EOF
     USE \`$DB_NAME\`;

     ALTER TABLE persistent_logins MODIFY COLUMN endpoint_guid varchar(64) NULL;
     ALTER TABLE persistent_logins DROP KEY UNIQUE_SERI_USER_TENANT_EID;
     ALTER TABLE persistent_logins ADD UNIQUE KEY UNIQUE_SERI_USER_TENANT_EID (series,username,tenant_id);
     ALTER TABLE TenantConfiguration ADD COLUMN personalRoom TINYINT(1) NOT NULL DEFAULT 0;
     ALTER TABLE TenantConfiguration ADD COLUMN testCall TINYINT(1) NOT NULL DEFAULT 0;

     UPDATE country SET flag_file_name = 'Japan.png' WHERE countryID = 107;
     INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'WEBRTC_TEST_MEDIA_SERVER', '');
     INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'WEBRTC_TEST_CALL_USERNAME', '');

     CREATE TABLE IF NOT EXISTS TEMP_SAML_AUTH_TOKEN (
	  ID int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
	  MEMBER_ID int(10) UNSIGNED DEFAULT NULL,
	  TOKEN varchar(64) NOT NULL,
	  AUTH_TOKEN varchar(256) DEFAULT NULL,
	  PRIMARY KEY (ID),
	  CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	  UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	  UNIQUE KEY UK_IDX_TOKEN (TOKEN),
	  FOREIGN KEY FK_TSAT_MID_MEM_MID(MEMBER_ID) REFERENCES Member (memberID) ON DELETE CASCADE
	 ) ENGINE=InnoDB;

     UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';

     COMMIT;
     QUIT
EOF

    echo "**************3.5.1******************** DONE"
   fix_mobile_login_mode
}

upgradeTo17dot20()
{
   echo start of 17dot20
	logger "Portal database version is $DB_VER upgrading to 17.2.0"

   if [ -f /opt/vidyo/Vendor/VIDYO_CLOUD ]; then
        echo Update Configuration table and set ENABLE_PERSONAL_ROOM_FLAG to 0.
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
        DELETE FROM Configuration WHERE configurationName='ENABLE_PERSONAL_ROOM_FLAG';
     	INSERT INTO  Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'ENABLE_PERSONAL_ROOM_FLAG', 0, now(), now()); 
EOF

   else
        echo Update Configuration table and set ENABLE_PERSONAL_ROOM_FLAG to 1.
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
        DELETE FROM Configuration WHERE configurationName='ENABLE_PERSONAL_ROOM_FLAG';
     	INSERT INTO  Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'ENABLE_PERSONAL_ROOM_FLAG', 1, now(), now()); 
        UPDATE TenantConfiguration SET personalRoom = 1;
EOF
   fi


	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;

	INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (6, 'VidyoAAMicroservice', 'VidyoAAMicroservice', now(), now());
	INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (7, 'VidyoRegistrationMicroservice', 'VidyoRegistrationMicroservice', now(), now());
	INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (8, 'VidyoPairingMicroservice', 'VidyoPairingMicroservice', now(), now());

    CREATE TABLE IF NOT EXISTS user_group (
        user_group_id int(10) unsigned NOT NULL auto_increment,
        tenant_id int(10) unsigned NOT NULL,
		name varchar(128) NOT NULL,
        description text,
        creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        PRIMARY KEY (user_group_id),
        UNIQUE KEY uk_tenant_id_name (tenant_id,name),
        KEY idx_name (name),
        CONSTRAINT fk_ug_tid_tt_id FOREIGN KEY (tenant_id) REFERENCES Tenant (tenantID)
    ) ENGINE=InnoDB COLLATE utf8_general_ci;
                                                                    
    CREATE TABLE IF NOT EXISTS room_user_group (
        room_id int(10) unsigned NOT NULL,
        user_group_id int(10) unsigned NOT NULL,
        PRIMARY KEY (room_id,user_group_id),
        KEY idx_user_group_id (user_group_id),
        CONSTRAINT fk_rug_rid_rm_id FOREIGN KEY (room_id) REFERENCES Room (roomID),
        CONSTRAINT fk_rug_ugid_ug_ugid FOREIGN KEY (user_group_id) REFERENCES user_group (user_group_id)
    )  ENGINE=InnoDB COLLATE utf8_general_ci;
                                                                                                                
    CREATE TABLE IF NOT EXISTS member_user_group (
        member_id int(10) unsigned NOT NULL,
        user_group_id int(10) unsigned NOT NULL,
        PRIMARY KEY (member_id,user_group_id),
        KEY idx_user_group_id (user_group_id),
        CONSTRAINT fk_mug_mid_mem_id FOREIGN KEY (member_id) REFERENCES Member (memberID),
        CONSTRAINT fk_mug_ugid_ug_ugid FOREIGN KEY (user_group_id) REFERENCES user_group (user_group_id)
    ) ENGINE=InnoDB COLLATE utf8_general_ci;

    CREATE INDEX idx_tenant_id ON user_group (tenant_id);
    CREATE INDEX idx_room_id ON room_user_group (room_id);
    CREATE INDEX idx_member_id ON member_user_group (member_id);

	ALTER TABLE Member ADD COLUMN neoRoomPermanentPairingDeviceUser TINYINT(1) NOT NULL DEFAULT 0;

    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'MAX_PERMITTED_USER_GROUPS_COUNT', '1000');
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'MAX_USER_GROUPS_IMPORTED_PER_USER', '100');
    DELETE FROM Configuration WHERE configurationName = 'SDK220';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SDK220', '0');

    DELETE FROM Configuration WHERE configurationName = 'OPUS_AUDIO';
    DELETE FROM EndpointUpload WHERE tenantID NOT IN (SELECT tenantId FROM Tenant);
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'OPUS_AUDIO', '0');

    
    ALTER TABLE room_user_group ADD COLUMN creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
    ALTER TABLE member_user_group ADD COLUMN creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP;
    RENAME TABLE TEMP_SAML_AUTH_TOKEN to temp_saml_auth_token;
   	ALTER TABLE user_group MODIFY COLUMN name varchar(255) NOT NULL;
   	ALTER TABLE user_group MODIFY COLUMN description varchar(255);
    CREATE INDEX idx_bak ON Member (bak);
     UPDATE Tenant set tenantID = 0 WHERE tenantName = 'Super Tenant';
	UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
	COMMIT;
	QUIT
EOF
	echo UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
}

upgradeTo17dot30()
{
   echo start of 17dot30
	logger "Portal database version is $DB_VER upgrading to 17.3.0"
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
	CREATE UNIQUE INDEX idx_tenant_tenanturl ON Tenant (tenantUrl);
	
	DELETE FROM Configuration WHERE configurationName = 'SDK220';
        DELETE FROM EndpointUpload WHERE tenantID NOT IN (SELECT tenantId FROM Tenant);
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SDK220', '0');

    DELETE FROM Configuration WHERE configurationName = 'OPUS_AUDIO';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'OPUS_AUDIO', '0');
    
    UPDATE Member SET locationid = 1 where locationid is NULL;
    
	UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
	COMMIT;
	QUIT
EOF
	echo UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
}

upgradeTo18dot10()
{
   echo start of 18dot10
	logger "Portal database version is $DB_VER upgrading to 18.1.0"
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
	DELETE FROM Configuration WHERE configurationName = 'SDK220';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SDK220', '1');
    DELETE FROM Configuration WHERE configurationName = 'OPUS_AUDIO';
    DELETE FROM EndpointUpload WHERE tenantID NOT IN (SELECT tenantId FROM Tenant);
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'OPUS_AUDIO', '1');
    INSERT INTO Tenant (tenantID, tenantName) VALUES (0, 'Super Tenant') ON DUPLICATE KEY UPDATE tenantID=0;
    UPDATE Tenant set tenantID = 0 WHERE tenantName = 'Super Tenant';

    ALTER TABLE cloud_config ADD INDEX idx_status (status);
    ALTER TABLE VirtualEndpoints ADD INDEX idx_display_ext (displayExt);
    ALTER TABLE VirtualEndpoints ADD INDEX idx_display_name (displayName);
    ALTER TABLE VirtualEndpoints ADD INDEX idx_status (status);
    ALTER TABLE Conferences ADD INDEX idx_guid (GUID);
    ALTER TABLE Conferences ADD INDEX idx_conference_name (conferenceName);
    ALTER TABLE Conferences ADD INDEX idx_conference_type (conferenceType);
    ALTER TABLE ConferenceRecord ADD INDEX idx_conference_name (conferenceName);
    ALTER TABLE ExternalLinks ADD INDEX idx_to_conference_name (toConferenceName);
    ALTER TABLE Conferences ADD INDEX idx_conf_tenant_id (tenantID);
    ALTER TABLE TenantConfiguration add column extnLength int(10) unsigned  NOT NULL DEFAULT 4;
    ALTER TABLE TenantConfiguration add index idx_extnLength (extnLength);
    ALTER TABLE TenantConfiguration add column roomCountThreshold int unsigned NOT NULL DEFAULT 40;

    CREATE TABLE member_bak (
          member_bak_id int(10) unsigned NOT NULL AUTO_INCREMENT,
          member_id int(10) unsigned NOT NULL,
          bak varchar(40) NOT NULL,
          creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
          bak_type varchar(20) NOT NULL,
          PRIMARY KEY (member_bak_id),
          KEY idx_bak (bak),
          KEY idx_bak_type (bak_type),
          KEY idx_member_id (member_id),
          CONSTRAINT member_bak_ibfk_1 FOREIGN KEY (member_id) REFERENCES Member (memberID)
        ) ENGINE=InnoDB COLLATE utf8_general_ci;

    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'MEMBER_BAK_INACTIVE_LIMIT_IN_MINS', 360);

    UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
	COMMIT;
	QUIT
EOF
	FixExtensionLength
	echo UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
}

upgradeTo18dot20()
{
   echo start of 18dot20
	logger "Portal database version is $DB_VER upgrading to 18.2.0"
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'EPIC_INTEGRATION_SUPPORT', 0);
    ALTER TABLE Room ADD COLUMN externalRoomID VARCHAR(128) DEFAULT NULL;
    CREATE UNIQUE INDEX externalRoomID ON Room (externalRoomID);
    ALTER TABLE Endpoints ADD COLUMN extDataType INT(11) DEFAULT '0';
    ALTER TABLE Endpoints ADD COLUMN extData VARCHAR(2048) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN mobileProtocol VARCHAR(64) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN packageName VARCHAR(256) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN iOSBundleId VARCHAR(256) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN iOSAppLink VARCHAR(1024) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN androidAppLink VARCHAR(1024) DEFAULT NULL;
    ALTER TABLE TenantConfiguration CHANGE COLUMN packageName androidPackageName VARCHAR(256) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN externalIntegrationMode int(11) NOT NULL DEFAULT 0;
    ALTER TABLE TenantConfiguration ADD COLUMN extIntegrationSharedSecret VARCHAR(256) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN externalNotificationUrl VARCHAR(2048) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN desktopProtocol VARCHAR(64) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN iOSAppId VARCHAR(256) DEFAULT NULL;
    DELETE FROM Configuration WHERE configurationName = 'SDK220';
    DELETE FROM EndpointUpload WHERE tenantID NOT IN (SELECT tenantId FROM Tenant);
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SDK220', '1');
    DELETE FROM Configuration WHERE configurationName = 'OPUS_AUDIO';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'OPUS_AUDIO', '1');    
    UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
	COMMIT;
	QUIT
EOF
   echo start of cleanup 1820
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
    ALTER TABLE TenantConfiguration DROP COLUMN packageName;
    COMMIT;
	QUIT
EOF
	echo UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
}

upgradeTo18dot21()
{
   echo start of 18dot21
	logger "Portal database version is $DB_VER upgrading to 18.2.1"
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
    ALTER TABLE TenantConfiguration ADD COLUMN externalUsername VARCHAR(256) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN externalPassword VARCHAR(256) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN vidyoNotificationUrl VARCHAR(2048) DEFAULT NULL;    
    ALTER TABLE TenantConfiguration ADD COLUMN vidyoUsername VARCHAR(256) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN vidyoPassword VARCHAR(256) DEFAULT NULL;
    DELETE FROM Configuration WHERE configurationName = 'SDK220';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SDK220', '1');
    DELETE FROM Configuration WHERE configurationName = 'OPUS_AUDIO';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'OPUS_AUDIO', '1');        
    UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
    DROP TABLE IF EXISTS TenantNotification;
    CREATE TABLE ExternalStatusNotification  (
       notificationId int(10) UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
       tenantID int(10) NOT NULL,
       status VARCHAR(10) DEFAULT 'Active',
       url VARCHAR(256) DEFAULT NULL,
       username VARCHAR(256) DEFAULT NULL,
       password VARCHAR(256) DEFAULT NULL,
       data VARCHAR(2048) DEFAULT NULL,
       dataType INT(11) DEFAULT '0',
       creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
    ) ENGINE=InnoDB COLLATE utf8_general_ci;
    ALTER TABLE ExternalStatusNotification ADD COLUMN retry INT(1) DEFAULT 0;
    ALTER TABLE ExternalStatusNotification ADD COLUMN errorMessage VARCHAR(256) DEFAULT NULL;

    ALTER TABLE TenantConfiguration ADD COLUMN tc VARCHAR(2048) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN tcVersion INT(8) DEFAULT 1;
    ALTER TABLE TenantConfiguration ADD COLUMN pp VARCHAR(2048) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN ppVersion INT(8) DEFAULT 1;
    
    DELETE FROM EndpointUpload WHERE tenantID NOT IN (SELECT tenantId FROM Tenant);
	COMMIT;
	QUIT
EOF
	echo UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
}

upgradeTo18dot30()
{
   echo start of 18dot30
	logger "Portal database version is $DB_VER upgrading to 18.3.0"
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
	UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
	COMMIT;
	QUIT
EOF
	echo UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
}

upgradeTo18dot31()
{
   echo start of 18dot31
	logger "Portal database version is $DB_VER upgrading to 18.3.1"
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
    DELETE FROM Configuration WHERE configurationName = 'SDK220';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SDK220', '1');
    DELETE FROM Configuration WHERE configurationName = 'OPUS_AUDIO';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'OPUS_AUDIO', '1');  	
	UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';	
	COMMIT;
	QUIT
EOF
	echo UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
}

upgradeTo18dot40()
{
   echo start of 18dot40
	logger "Portal database version is $DB_VER upgrading to 18.4.0"
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
	INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'TYTOCARE_INTEGRATION_SUPPORT', 0);
	ALTER TABLE TenantConfiguration ADD COLUMN tytoIntegrationMode int(11) NOT NULL DEFAULT 0;
	ALTER TABLE TenantConfiguration ADD COLUMN tytoUrl VARCHAR(2048) DEFAULT NULL;
	ALTER TABLE TenantConfiguration ADD COLUMN tytoUsername VARCHAR(256) DEFAULT NULL;
    ALTER TABLE TenantConfiguration ADD COLUMN tytoPassword VARCHAR(256) DEFAULT NULL;
    DELETE FROM Configuration WHERE configurationName = 'SDK220';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SDK220', '1');
    DELETE FROM Configuration WHERE configurationName = 'OPUS_AUDIO';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'OPUS_AUDIO', '1'); 
	DELETE FROM Configuration WHERE configurationName = 'VP9';
	INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'VP9', '0');

	UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
	COMMIT;
	QUIT
EOF
	echo UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
}

upgradeTo19dot10()
{
   echo start of 19dot10
	logger "Portal database version is $DB_VER upgrading to 19.1.0"
	mysql $MYSQL_OPT -u root << EOF
	USE \`$DB_NAME\`;
    DELETE FROM Configuration WHERE configurationName = 'SDK220';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SDK220', '1');
    DELETE FROM Configuration WHERE configurationName = 'OPUS_AUDIO';
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'OPUS_AUDIO', '1'); 
	DELETE FROM Configuration WHERE configurationName = 'VP9';
	INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'VP9', '0');
	UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
	COMMIT;
	QUIT
EOF
	echo UPDATE Configuration SET configurationValue="$TARGET_DB_VER" WHERE configurationName='DBVersion';
}

insert_countries()
{
  mysql $MYSQL_OPT -uroot -D portal2 << EOF

  INSERT INTO country (countryID, iso, name, nick_name, iso_3, num_code, phone_code, flag_file_name, active) VALUES
    (1, 'AF', 'AFGHANISTAN', 'Afghanistan', 'AFG', 4, 93, NULL, 0),
    (2, 'AL', 'ALBANIA', 'Albania', 'ALB', 8, 355, NULL, 0),
    (3, 'DZ', 'ALGERIA', 'Algeria', 'DZA', 12, 213, NULL, 0),
    (4, 'AS', 'AMERICAN SAMOA', 'American Samoa', 'ASM', 16, 1684, NULL, 0),
    (5, 'AD', 'ANDORRA', 'Andorra', 'AND', 20, 376, NULL, 0),
    (6, 'AO', 'ANGOLA', 'Angola', 'AGO', 24, 244, NULL, 0),
    (7, 'AI', 'ANGUILLA', 'Anguilla', 'AIA', 660, 1264, NULL, 0),
    (8, 'AQ', 'ANTARCTICA', 'Antarctica', NULL, NULL, 0, NULL, 0),
    (9, 'AG', 'ANTIGUA AND BARBUDA', 'Antigua and Barbuda', 'ATG', 28, 1268, NULL, 0),
    (10, 'AR', 'ARGENTINA', 'Argentina', 'ARG', 32, 54, 'Argentina.png', 1),
    (11, 'AM', 'ARMENIA', 'Armenia', 'ARM', 51, 374, NULL, 0),
    (12, 'AW', 'ARUBA', 'Aruba', 'ABW', 533, 297, NULL, 0),
    (13, 'AU', 'AUSTRALIA', 'Australia', 'AUS', 36, 61, 'Australia.png', 1),
    (14, 'AT', 'AUSTRIA', 'Austria', 'AUT', 40, 43, 'Austria.png', 1),
    (15, 'AZ', 'AZERBAIJAN', 'Azerbaijan', 'AZE', 31, 994, NULL, 0),
    (16, 'BS', 'BAHAMAS', 'Bahamas', 'BHS', 44, 1242, NULL, 0),
    (17, 'BH', 'BAHRAIN', 'Bahrain', 'BHR', 48, 973, 'Bahrain.png', 1),
    (18, 'BD', 'BANGLADESH', 'Bangladesh', 'BGD', 50, 880, 'Bangladesh.png', 1),
    (19, 'BB', 'BARBADOS', 'Barbados', 'BRB', 52, 1246, NULL, 0),
    (20, 'BY', 'BELARUS', 'Belarus', 'BLR', 112, 375, NULL, 0),
    (21, 'BE', 'BELGIUM', 'Belgium', 'BEL', 56, 32, 'Belgium.png', 1),
    (22, 'BZ', 'BELIZE', 'Belize', 'BLZ', 84, 501, NULL, 0),
    (23, 'BJ', 'BENIN', 'Benin', 'BEN', 204, 229, NULL, 0),
    (24, 'BM', 'BERMUDA', 'Bermuda', 'BMU', 60, 1441, NULL, 0),
    (25, 'BT', 'BHUTAN', 'Bhutan', 'BTN', 64, 975, NULL, 0),
    (26, 'BO', 'BOLIVIA', 'Bolivia', 'BOL', 68, 591, NULL, 0),
    (27, 'BA', 'BOSNIA AND HERZEGOVINA', 'Bosnia and Herzegovina', 'BIH', 70, 387, NULL, 0),
    (28, 'BW', 'BOTSWANA', 'Botswana', 'BWA', 72, 267, NULL, 0),
    (29, 'BV', 'BOUVET ISLAND', 'Bouvet Island', NULL, NULL, 0, NULL, 0),
    (30, 'BR', 'BRAZIL', 'Brazil', 'BRA', 76, 55, 'Brazil.png', 1),
    (31, 'IO', 'BRITISH INDIAN OCEAN TERRITORY', 'British Indian Ocean Territory', NULL, NULL, 246, NULL, 0),
    (32, 'BN', 'BRUNEI DARUSSALAM', 'Brunei Darussalam', 'BRN', 96, 673, NULL, 0),
    (33, 'BG', 'BULGARIA', 'Bulgaria', 'BGR', 100, 359, 'Bulgaria.png', 1),
    (34, 'BF', 'BURKINA FASO', 'Burkina Faso', 'BFA', 854, 226, NULL, 0),
    (35, 'BI', 'BURUNDI', 'Burundi', 'BDI', 108, 257, NULL, 0),
    (36, 'KH', 'CAMBODIA', 'Cambodia', 'KHM', 116, 855, NULL, 0),
    (37, 'CM', 'CAMEROON', 'Cameroon', 'CMR', 120, 237, NULL, 0),
    (38, 'CA', 'CANADA', 'Canada', 'CAN', 124, 1, 'Canada.png', 1),
    (39, 'CV', 'CAPE VERDE', 'Cape Verde', 'CPV', 132, 238, NULL, 0),
    (40, 'KY', 'CAYMAN ISLANDS', 'Cayman Islands', 'CYM', 136, 1345, NULL, 0),
    (41, 'CF', 'CENTRAL AFRICAN REPUBLIC', 'Central African Republic', 'CAF', 140, 236, NULL, 0),
    (42, 'TD', 'CHAD', 'Chad', 'TCD', 148, 235, NULL, 0),
    (43, 'CL', 'CHILE', 'Chile', 'CHL', 152, 56, 'Chile.png', 1),
    (44, 'CN', 'CHINA', 'China', 'CHN', 156, 86, 'China.png', 1),
    (45, 'CX', 'CHRISTMAS ISLAND', 'Christmas Island', NULL, NULL, 61, NULL, 0),
    (46, 'CC', 'COCOS     (KEELING) ISLANDS', 'Cocos (Keeling) Islands', NULL, NULL, 672, NULL, 0),
    (47, 'CO', 'COLOMBIA', 'Colombia', 'COL', 170, 57, 'Colombia.png', 1),
    (48, 'KM', 'COMOROS', 'Comoros', 'COM', 174, 269, NULL, 0),
    (49, 'CG', 'CONGO', 'Congo', 'COG', 178, 242, NULL, 0),
    (50, 'CD', 'CONGO, THE DEMOCRATIC REPUBLIC OF THE', 'Congo, the Democratic Republic of the', 'COD', 180, 242, NULL, 0),
    (51, 'CK', 'COOK ISLANDS', 'Cook Islands', 'COK', 184, 682, NULL, 0),
    (52, 'CR', 'COSTA RICA', 'Costa Rica', 'CRI', 188, 506, NULL, 0),
    (53, 'CI', 'COTE D''IVOIRE', 'Cote D''Ivoire', 'CIV', 384, 225, NULL, 0),
    (54, 'HR', 'CROATIA', 'Croatia', 'HRV', 191, 385, 'Croatia.png', 1),
    (55, 'CU', 'CUBA', 'Cuba', 'CUB', 192, 53, NULL, 0),
    (56, 'CY', 'CYPRUS', 'Cyprus', 'CYP', 196, 357, 'Cyprus.png', 1),
    (57, 'CZ', 'CZECH REPUBLIC', 'Czech Republic', 'CZE', 203, 420, 'CzechRepublic.png', 1),
    (58, 'DK', 'DENMARK', 'Denmark', 'DNK', 208, 45, 'Denmark.png', 1),
    (59, 'DJ', 'DJIBOUTI', 'Djibouti', 'DJI', 262, 253, NULL, 0),
    (60, 'DM', 'DOMINICA', 'Dominica', 'DMA', 212, 1767, NULL, 0),
    (61, 'DO', 'DOMINICAN REPUBLIC', 'Dominican Republic', 'DOM', 214, 1809, 'DominicanRepublic.png', 1),
    (62, 'EC', 'ECUADOR', 'Ecuador', 'ECU', 218, 593, NULL, 0),
    (63, 'EG', 'EGYPT', 'Egypt', 'EGY', 818, 20, NULL, 0),
    (64, 'SV', 'EL SALVADOR', 'El Salvador', 'SLV', 222, 503, NULL, 0),
    (65, 'GQ', 'EQUATORIAL GUINEA', 'Equatorial Guinea', 'GNQ', 226, 240, NULL, 0),
    (66, 'ER', 'ERITREA', 'Eritrea', 'ERI', 232, 291, NULL, 0),
    (67, 'EE', 'ESTONIA', 'Estonia', 'EST', 233, 372, 'Estonia.png', 1),
    (68, 'ET', 'ETHIOPIA', 'Ethiopia', 'ETH', 231, 251, NULL, 0),
    (69, 'FK', 'FALKLAND ISLANDS     (MALVINAS)', 'Falkland Islands (Malvinas)', 'FLK', 238, 500, NULL, 0),
    (70, 'FO', 'FAROE ISLANDS', 'Faroe Islands', 'FRO', 234, 298, NULL, 0),
    (71, 'FJ', 'FIJI', 'Fiji', 'FJI', 242, 679, NULL, 0),
    (72, 'FI', 'FINLAND', 'Finland', 'FIN', 246, 358, 'Finland.png', 1),
    (73, 'FR', 'FRANCE', 'France', 'FRA', 250, 33, 'France.png', 1),
    (74, 'GF', 'FRENCH GUIANA', 'French Guiana', 'GUF', 254, 594, NULL, 0),
    (75, 'PF', 'FRENCH POLYNESIA', 'French Polynesia', 'PYF', 258, 689, NULL, 0),
    (76, 'TF', 'FRENCH SOUTHERN TERRITORIES', 'French Southern Territories', NULL, NULL, 0, NULL, 0),
    (77, 'GA', 'GABON', 'Gabon', 'GAB', 266, 241, NULL, 0),
    (78, 'GM', 'GAMBIA', 'Gambia', 'GMB', 270, 220, NULL, 0),
    (79, 'GE', 'GEORGIA', 'Georgia', 'GEO', 268, 995, NULL, 0),
    (80, 'DE', 'GERMANY', 'Germany', 'DEU', 276, 49, 'Germany.png', 1),
    (81, 'GH', 'GHANA', 'Ghana', 'GHA', 288, 233, NULL, 0),
    (82, 'GI', 'GIBRALTAR', 'Gibraltar', 'GIB', 292, 350, NULL, 0),
    (83, 'GR', 'GREECE', 'Greece', 'GRC', 300, 30, 'Greece.png', 1),
    (84, 'GL', 'GREENLAND', 'Greenland', 'GRL', 304, 299, NULL, 0),
    (85, 'GD', 'GRENADA', 'Grenada', 'GRD', 308, 1473, NULL, 0),
    (86, 'GP', 'GUADELOUPE', 'Guadeloupe', 'GLP', 312, 590, NULL, 0),
    (87, 'GU', 'GUAM', 'Guam', 'GUM', 316, 1671, NULL, 0),
    (88, 'GT', 'GUATEMALA', 'Guatemala', 'GTM', 320, 502, NULL, 0),
    (89, 'GN', 'GUINEA', 'Guinea', 'GIN', 324, 224, NULL, 0),
    (90, 'GW', 'GUINEA-BISSAU', 'Guinea-Bissau', 'GNB', 624, 245, NULL, 0),
    (91, 'GY', 'GUYANA', 'Guyana', 'GUY', 328, 592, NULL, 0),
    (92, 'HT', 'HAITI', 'Haiti', 'HTI', 332, 509, NULL, 0),
    (93, 'HM', 'HEARD ISLAND AND MCDONALD ISLANDS', 'Heard Island and Mcdonald Islands', NULL, NULL, 0, NULL, 0),
    (94, 'VA', 'HOLY SEE (VATICAN CITY STATE)', 'Holy See (Vatican City State)', 'VAT', 336, 39, NULL, 0),
    (95, 'HN', 'HONDURAS', 'Honduras', 'HND', 340, 504, NULL, 0),
    (96, 'HK', 'HONG KONG', 'Hong Kong', 'HKG', 344, 852, 'HongKong.png', 1),
    (97, 'HU', 'HUNGARY', 'Hungary', 'HUN', 348, 36, 'Hungary.png', 1),
    (98, 'IS', 'ICELAND', 'Iceland', 'ISL', 352, 354, NULL, 0),
    (99, 'IN', 'INDIA', 'India', 'IND', 356, 91, 'India.png', 1),
    (100, 'ID', 'INDONESIA', 'Indonesia', 'IDN', 360, 62, 'Indonesia.png', 1),
    (101, 'IR', 'IRAN, ISLAMIC REPUBLIC OF', 'Iran, Islamic Republic of', 'IRN', 364, 98, NULL, 0),
    (102, 'IQ', 'IRAQ', 'Iraq', 'IRQ', 368, 964, NULL, 0),
    (103, 'IE', 'IRELAND', 'Ireland', 'IRL', 372, 353, 'Ireland.png', 1),
    (104, 'IL', 'ISRAEL', 'Israel', 'ISR', 376, 972, 'Israel.png', 1),
    (105, 'IT', 'ITALY', 'Italy', 'ITA', 380, 39, 'Italy.png', 1),
    (106, 'JM', 'JAMAICA', 'Jamaica', 'JAM', 388, 1876, NULL, 0),
    (107, 'JP', 'JAPAN', 'Japan', 'JPN', 392, 81, 'Japan.png', 1),
    (108, 'JO', 'JORDAN', 'Jordan', 'JOR', 400, 962, NULL, 0),
    (109, 'KZ', 'KAZAKHSTAN', 'Kazakhstan', 'KAZ', 398, 7, NULL, 0),
    (110, 'KE', 'KENYA', 'Kenya', 'KEN', 404, 254, NULL, 0),
    (111, 'KI', 'KIRIBATI', 'Kiribati', 'KIR', 296, 686, NULL, 0),
    (112, 'KP', 'KOREA, DEMOCRATIC PEOPLE''S REPUBLIC OF', 'Korea, Democratic People''s Republic of', 'PRK', 408, 850, NULL, 0),
    (113, 'KR', 'SOUTH KOREA', 'South Korea', 'KOR', 410, 82, 'SouthKorea.png', 1),
    (114, 'KW', 'KUWAIT', 'Kuwait', 'KWT', 414, 965, NULL, 0),
    (115, 'KG', 'KYRGYZSTAN', 'Kyrgyzstan', 'KGZ', 417, 996, NULL, 0),
    (116, 'LA', 'LAO PEOPLE''S DEMOCRATIC REPUBLIC', 'Lao People''s Democratic Republic', 'LAO', 418, 856, NULL, 0),
    (117, 'LV', 'LATVIA', 'Latvia', 'LVA', 428, 371, 'Latvia.png', 1),
    (118, 'LB', 'LEBANON', 'Lebanon', 'LBN', 422, 961, NULL, 0),
    (119, 'LS', 'LESOTHO', 'Lesotho', 'LSO', 426, 266, NULL, 0),
    (120, 'LR', 'LIBERIA', 'Liberia', 'LBR', 430, 231, NULL, 0),
    (121, 'LY', 'LIBYAN ARAB JAMAHIRIYA', 'Libyan Arab Jamahiriya', 'LBY', 434, 218, NULL, 0),
    (122, 'LI', 'LIECHTENSTEIN', 'Liechtenstein', 'LIE', 438, 423, NULL, 0),
    (123, 'LT', 'LITHUANIA', 'Lithuania', 'LTU', 440, 370, 'Lithuania.png', 1),
    (124, 'LU', 'LUXEMBOURG', 'Luxembourg', 'LUX', 442, 352, 'Luxembourg.png', 1),
    (125, 'MO', 'MACAO', 'Macao', 'MAC', 446, 853, NULL, 0),
    (126, 'MK', 'MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF', 'Macedonia, the Former Yugoslav Republic of', 'MKD', 807, 389, NULL, 0),
    (127, 'MG', 'MADAGASCAR', 'Madagascar', 'MDG', 450, 261, NULL, 0),
    (128, 'MW', 'MALAWI', 'Malawi', 'MWI', 454, 265, NULL, 0),
    (129, 'MY', 'MALAYSIA', 'Malaysia', 'MYS', 458, 60, 'Malaysia.png', 1),
    (130, 'MV', 'MALDIVES', 'Maldives', 'MDV', 462, 960, NULL, 0),
    (131, 'ML', 'MALI', 'Mali', 'MLI', 466, 223, NULL, 0),
    (132, 'MT', 'MALTA', 'Malta', 'MLT', 470, 356, 'Malta.png', 1),
    (133, 'MH', 'MARSHALL ISLANDS', 'Marshall Islands', 'MHL', 584, 692, NULL, 0),
    (134, 'MQ', 'MARTINIQUE', 'Martinique', 'MTQ', 474, 596, NULL, 0),
    (135, 'MR', 'MAURITANIA', 'Mauritania', 'MRT', 478, 222, NULL, 0),
    (136, 'MU', 'MAURITIUS', 'Mauritius', 'MUS', 480, 230, NULL, 0),
    (137, 'YT', 'MAYOTTE', 'Mayotte', NULL, NULL, 269, NULL, 0),
    (138, 'MX', 'MEXICO', 'Mexico', 'MEX', 484, 52, 'Mexico.png', 1),
    (139, 'FM', 'MICRONESIA, FEDERATED STATES OF', 'Micronesia, Federated States of', 'FSM', 583, 691, NULL, 0),
    (140, 'MD', 'MOLDOVA, REPUBLIC OF', 'Moldova, Republic of', 'MDA', 498, 373, NULL, 0),
    (141, 'MC', 'MONACO', 'Monaco', 'MCO', 492, 377, NULL, 0),
    (142, 'MN', 'MONGOLIA', 'Mongolia', 'MNG', 496, 976, NULL, 0),
    (143, 'MS', 'MONTSERRAT', 'Montserrat', 'MSR', 500, 1664, NULL, 0),
    (144, 'MA', 'MOROCCO', 'Morocco', 'MAR', 504, 212, NULL, 0),
    (145, 'MZ', 'MOZAMBIQUE', 'Mozambique', 'MOZ', 508, 258, NULL, 0),
    (146, 'MM', 'MYANMAR', 'Myanmar', 'MMR', 104, 95, NULL, 0),
    (147, 'NA', 'NAMIBIA', 'Namibia', 'NAM', 516, 264, NULL, 0),
    (148, 'NR', 'NAURU', 'Nauru', 'NRU', 520, 674, NULL, 0),
    (149, 'NP', 'NEPAL', 'Nepal', 'NPL', 524, 977, NULL, 0),
    (150, 'NL', 'NETHERLANDS', 'Netherlands', 'NLD', 528, 31, 'Netherlands.png', 1),
    (151, 'AN', 'NETHERLANDS ANTILLES', 'Netherlands Antilles', 'ANT', 530, 599, NULL, 0),
    (152, 'NC', 'NEW CALEDONIA', 'New Caledonia', 'NCL', 540, 687, NULL, 0),
    (153, 'NZ', 'NEW ZEALAND', 'New Zealand', 'NZL', 554, 64, 'NewZealand.png', 1),
    (154, 'NI', 'NICARAGUA', 'Nicaragua', 'NIC', 558, 505, NULL, 0),
    (155, 'NE', 'NIGER', 'Niger', 'NER', 562, 227, NULL, 0),
    (156, 'NG', 'NIGERIA', 'Nigeria', 'NGA', 566, 234, NULL, 0),
    (157, 'NU', 'NIUE', 'Niue', 'NIU', 570, 683, NULL, 0),
    (158, 'NF', 'NORFOLK ISLAND', 'Norfolk Island', 'NFK', 574, 672, NULL, 0),
    (159, 'MP', 'NORTHERN MARIANA ISLANDS', 'Northern Mariana Islands', 'MNP', 580, 1670, NULL, 0),
    (160, 'NO', 'NORWAY', 'Norway', 'NOR', 578, 47, 'Norway.png', 1),
    (161, 'OM', 'OMAN', 'Oman', 'OMN', 512, 968, 'Oman.png', 1),
    (162, 'PK', 'PAKISTAN', 'Pakistan', 'PAK', 586, 92, 'Pakistan.png', 1),
    (163, 'PW', 'PALAU', 'Palau', 'PLW', 585, 680, NULL, 0),
    (164, 'PS', 'PALESTINIAN TERRITORY, OCCUPIED', 'Palestinian Territory, Occupied', NULL, NULL, 970, NULL, 0),
    (165, 'PA', 'PANAMA', 'Panama', 'PAN', 591, 507, 'Panama.png', 1),
    (166, 'PG', 'PAPUA NEW GUINEA', 'Papua New Guinea', 'PNG', 598, 675, NULL, 0),
    (167, 'PY', 'PARAGUAY', 'Paraguay', 'PRY', 600, 595, NULL, 0),
    (168, 'PE', 'PERU', 'Peru', 'PER', 604, 51, 'Peru.png', 1),
    (169, 'PH', 'PHILIPPINES', 'Philippines', 'PHL', 608, 63, 'Philippines.png', 1),
    (170, 'PN', 'PITCAIRN', 'Pitcairn', 'PCN', 612, 0, NULL, 0),
    (171, 'PL', 'POLAND', 'Poland', 'POL', 616, 48, 'Poland.png', 1),
    (172, 'PT', 'PORTUGAL', 'Portugal', 'PRT', 620, 351, 'Portugal.png', 1),
    (173, 'PR', 'PUERTO RICO', 'Puerto Rico', 'PRI', 630, 1787, 'PuertoRico.png', 1),
    (174, 'QA', 'QATAR', 'Qatar', 'QAT', 634, 974, 'Qatar.png', 1),
    (175, 'RE', 'REUNION', 'Reunion', 'REU', 638, 262, NULL, 0),
    (176, 'RO', 'ROMANIA', 'Romania', 'ROM', 642, 40, 'Romania.png', 1),
    (177, 'RU', 'RUSSIA', 'Russia', 'RUS', 643, 70, 'Russia.png', 1),
    (178, 'RW', 'RWANDA', 'Rwanda', 'RWA', 646, 250, NULL, 0),
    (179, 'SH', 'SAINT HELENA', 'Saint Helena', 'SHN', 654, 290, NULL, 0),
    (180, 'KN', 'SAINT KITTS AND NEVIS', 'Saint Kitts and Nevis', 'KNA', 659, 1869, NULL, 0),
    (181, 'LC', 'SAINT LUCIA', 'Saint Lucia', 'LCA', 662, 1758, NULL, 0),
    (182, 'PM', 'SAINT PIERRE AND MIQUELON', 'Saint Pierre and Miquelon', 'SPM', 666, 508, NULL, 0),
    (183, 'VC', 'SAINT VINCENT AND THE GRENADINES', 'Saint Vincent and the Grenadines', 'VCT', 670, 1784, NULL, 0),
    (184, 'WS', 'SAMOA', 'Samoa', 'WSM', 882, 684, NULL, 0),
    (185, 'SM', 'SAN MARINO', 'San Marino', 'SMR', 674, 378, NULL, 0),
    (186, 'ST', 'SAO TOME AND PRINCIPE', 'Sao Tome and Principe', 'STP', 678, 239, NULL, 0),
    (187, 'SA', 'SAUDI ARABIA', 'Saudi Arabia', 'SAU', 682, 966, NULL, 0),
    (188, 'SN', 'SENEGAL', 'Senegal', 'SEN', 686, 221, NULL, 0),
    (189, 'CS', 'SERBIA AND MONTENEGRO', 'Serbia and Montenegro', NULL, NULL, 381, NULL, 0),
    (190, 'SC', 'SEYCHELLES', 'Seychelles', 'SYC', 690, 248, NULL, 0),
    (191, 'SL', 'SIERRA LEONE', 'Sierra Leone', 'SLE', 694, 232, NULL, 0),
    (192, 'SG', 'SINGAPORE', 'Singapore', 'SGP', 702, 65, 'Singapore.png', 1),
    (193, 'SK', 'SLOVAKIA', 'Slovakia', 'SVK', 703, 421, NULL, 0),
    (194, 'SI', 'SLOVENIA', 'Slovenia', 'SVN', 705, 386, 'Slovenia.png', 1),
    (195, 'SB', 'SOLOMON ISLANDS', 'Solomon Islands', 'SLB', 90, 677, NULL, 0),
    (196, 'SO', 'SOMALIA', 'Somalia', 'SOM', 706, 252, NULL, 0),
    (197, 'ZA', 'SOUTH AFRICA', 'South Africa', 'ZAF', 710, 27, 'SouthAfrica.png', 1),
    (198, 'GS', 'SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS', 'South Georgia and the South Sandwich Islands', NULL, NULL, 0, NULL, 0),
    (199, 'ES', 'SPAIN', 'Spain', 'ESP', 724, 34, 'Spain.png', 1),
    (200, 'LK', 'SRI LANKA', 'Sri Lanka', 'LKA', 144, 94, NULL, 0),
    (201, 'SD', 'SUDAN', 'Sudan', 'SDN', 736, 249, NULL, 0),
    (202, 'SR', 'SURINAME', 'Suriname', 'SUR', 740, 597, NULL, 0),
    (203, 'SJ', 'SVALBARD AND JAN MAYEN', 'Svalbard and Jan Mayen', 'SJM', 744, 47, NULL, 0),
    (204, 'SZ', 'SWAZILAND', 'Swaziland', 'SWZ', 748, 268, NULL, 0),
    (205, 'SE', 'SWEDEN', 'Sweden', 'SWE', 752, 46, 'Sweden.png', 1),
    (206, 'CH', 'SWITZERLAND', 'Switzerland', 'CHE', 756, 41, 'Switzerland.png', 1),
    (207, 'SY', 'SYRIAN ARAB REPUBLIC', 'Syrian Arab Republic', 'SYR', 760, 963, NULL, 0),
    (208, 'TW', 'TAIWAN', 'Taiwan', 'TWN', 158, 886, 'Taiwan.png', 1),
    (209, 'TJ', 'TAJIKISTAN', 'Tajikistan', 'TJK', 762, 992, NULL, 0),
    (210, 'TZ', 'TANZANIA, UNITED REPUBLIC OF', 'Tanzania, United Republic of', 'TZA', 834, 255, NULL, 0),
    (211, 'TH', 'THAILAND', 'Thailand', 'THA', 764, 66, 'Thailand.png', 1),
    (212, 'TL', 'TIMOR-LESTE', 'Timor-Leste', NULL, NULL, 670, NULL, 0),
    (213, 'TG', 'TOGO', 'Togo', 'TGO', 768, 228, NULL, 0),
    (214, 'TK', 'TOKELAU', 'Tokelau', 'TKL', 772, 690, NULL, 0),
    (215, 'TO', 'TONGA', 'Tonga', 'TON', 776, 676, NULL, 0),
    (216, 'TT', 'TRINIDAD AND TOBAGO', 'Trinidad and Tobago', 'TTO', 780, 1868, NULL, 0),
    (217, 'TN', 'TUNISIA', 'Tunisia', 'TUN', 788, 216, NULL, 0),
    (218, 'TR', 'TURKEY', 'Turkey', 'TUR', 792, 90, 'Turkey.png', 1),
    (219, 'TM', 'TURKMENISTAN', 'Turkmenistan', 'TKM', 795, 7370, NULL, 0),
    (220, 'TC', 'TURKS AND CAICOS ISLANDS', 'Turks and Caicos Islands', 'TCA', 796, 1649, NULL, 0),
    (221, 'TV', 'TUVALU', 'Tuvalu', 'TUV', 798, 688, NULL, 0),
    (222, 'UG', 'UGANDA', 'Uganda', 'UGA', 800, 256, NULL, 0),
    (223, 'UA', 'UKRAINE', 'Ukraine', 'UKR', 804, 380, 'Ukraine.png', 1),
    (224, 'AE', 'UNITED ARAB EMIRATES', 'United Arab Emirates', 'ARE', 784, 971, 'UnitedArabEmirates.png', 1),
    (225, 'GB', 'UNITED KINGDOM', 'United Kingdom', 'GBR', 826, 44, 'UnitedKingdom.png', 1),
    (226, 'US', 'UNITED STATES', 'United States', 'USA', 840, 1, 'UnitedStates.png', 1),
    (227, 'UM', 'UNITED STATES MINOR OUTLYING ISLANDS', 'United States Minor Outlying Islands', NULL, NULL, 1, NULL, 0),
    (228, 'UY', 'URUGUAY', 'Uruguay', 'URY', 858, 598, NULL, 0),
    (229, 'UZ', 'UZBEKISTAN', 'Uzbekistan', 'UZB', 860, 998, NULL, 0),
    (230, 'VU', 'VANUATU', 'Vanuatu', 'VUT', 548, 678, NULL, 0),
    (231, 'VE', 'VENEZUELA', 'Venezuela', 'VEN', 862, 58, 'Venezuela.png', 1),
    (232, 'VN', 'VIETNAM', 'Viet Nam', 'VNM', 704, 84, 'Vietnam.png', 1),
    (233, 'VG', 'VIRGIN ISLANDS, BRITISH', 'Virgin Islands, British', 'VGB', 92, 1284, NULL, 0),
    (234, 'VI', 'VIRGIN ISLANDS, U.S.', 'Virgin Islands, U.s.', 'VIR', 850, 1340, NULL, 0),
    (235, 'WF', 'WALLIS AND FUTUNA', 'Wallis and Futuna', 'WLF', 876, 681, NULL, 0),
    (236, 'EH', 'WESTERN SAHARA', 'Western Sahara', 'ESH', 732, 212, NULL, 0),
    (237, 'YE', 'YEMEN', 'Yemen', 'YEM', 887, 967, NULL, 0),
    (238, 'ZM', 'ZAMBIA', 'Zambia', 'ZMB', 894, 260, NULL, 0),
    (239, 'ZW', 'ZIMBABWE', 'Zimbabwe', 'ZWE', 716, 263, NULL, 0);
    COMMIT;
    QUIT
EOF
}

fix_location_proxy()
{
    logme "Fixing Member table..."
    mysql $MYSQL_OPT -uroot -D portal2 << EOF
	UPDATE Member mem JOIN (SELECT memberID,tenantID, proxyID FROM Member mem WHERE proxyID IS NOT NULL AND proxyID NOT IN (SELECT serviceID FROM TenantXservice txs WHERE txs.tenantID = mem.tenantID) AND proxyID > 0) memTx ON memTx.memberID = mem.memberID AND memTx.tenantID = mem.tenantID AND memTx.proxyID = mem.proxyID SET mem.proxyID = 0;

    UPDATE Member mem JOIN (SELECT memberID,tenantID, locationID FROM Member mem WHERE locationID IS NOT NULL AND locationID NOT IN (SELECT locationID FROM TenantXlocation txs WHERE txs.tenantID = mem.tenantID) AND locationID > 1) memTx ON memTx.memberID = mem.memberID AND memTx.tenantID = mem.tenantID AND memTx.locationID = mem.locationID SET mem.locationID = 1;
    COMMIT;
    QUIT
EOF

}

fix_language_table()
{
    logme "Re-populating Language table..."
    mysql $MYSQL_OPT -uroot -D portal2 << EOF
    TRUNCATE TABLE Language;
    INSERT INTO Language (langID, langCode, langName) VALUES (1, 'en', 'English');
    INSERT INTO Language (langID, langCode, langName) VALUES (2, 'fr', 'Français');
    INSERT INTO Language (langID, langCode, langName) VALUES (3, 'ja', '日本語');
    INSERT INTO Language (langID, langCode, langName) VALUES (4, 'zh_CN', '简体中文');
    INSERT INTO Language (langID, langCode, langName) VALUES (5, 'es', 'Español');
    INSERT INTO Language (langID, langCode, langName) VALUES (6, 'it', 'Italiano');
    INSERT INTO Language (langID, langCode, langName) VALUES (7, 'de', 'Deutsch');
    INSERT INTO Language (langID, langCode, langName) VALUES (8, 'ko', '한국어');
    INSERT INTO Language (langID, langCode, langName) VALUES (9, 'pt', 'Português');
    INSERT INTO Language (langID, langCode, langName) VALUES (10, 'en', 'System Language');
    INSERT INTO Language (langID, langCode, langName) VALUES (11, 'fi', 'Suomi');
    INSERT INTO Language (langID, langCode, langName) VALUES (12, 'pl', 'Polski');
    INSERT INTO Language (langID, langCode, langName) VALUES (13, 'zh_TW', '繁体中文');
    INSERT INTO Language (langID, langCode, langName) VALUES (14, 'th', 'ไทย');
    INSERT INTO Language (langID, langCode, langName) VALUES (15, 'ru', 'Русский');
    INSERT INTO Language (langID, langCode, langName) VALUES (16, 'tr', 'Türkçe');
    COMMIT;
    QUIT
EOF

}

fix_mobile_login_mode()
{
   MOBILE_LOGIN0=0
   MOBILE_LOGIN1=0

   eval $(mysql $MYSQL_OPT -uroot -D $DB_NAME --silent -N -e \
        "SELECT CONCAT('MOBILE_LOGIN', mobileLogin, '=', COUNT(*)) AS total FROM Tenant GROUP BY mobileLogin"
     )

   MOBILE_LOGIN_MODE=0
   if [ $MOBILE_LOGIN0 -gt 0 -a $MOBILE_LOGIN1 -gt 0 ]; then
           MOBILE_LOGIN_MODE=1
   else
       if [ $MOBILE_LOGIN0 -gt 0 ]; then
          MOBILE_LOGIN_MODE=0
       elif [ $MOBILE_LOGIN1 -gt 0 ]; then
          MOBILE_LOGIN_MODE=1
       fi
   fi


   mysql $MYSQL_OPT -u root << EOF
   USE $DB_NAME;
   DELETE FROM Configuration where ConfigurationName = 'MOBILE_LOGIN_MODE';
   INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'MOBILE_LOGIN_MODE', '$MOBILE_LOGIN_MODE');
EOF

}

update_super_tenant_url()
{
   URL_SET_TO_LOCALHOST=$(mysql $MYSQL_OPT -uroot -D $DB_NAME -se "SELECT COUNT(*) FROM Tenant WHERE tenantUrl = '127.0.0.1' AND tenantID != 0")

   if [ $URL_SET_TO_LOCALHOST -eq 0 ]; then
	   mysql $MYSQL_OPT -u root << EOF
	   USE $DB_NAME;
	   UPDATE Tenant SET tenantUrl = '127.0.0.1' WHERE tenantID = 0;
EOF
   fi
   
}


FixExtensionLength()
{

SQL()
{
   mysql $MYSQL_OPT -uroot -D portal2 --silent -N -e "$*"
}



TID=$(SQL \
" SELECT tenantid  \
  FROM TenantIdpAttributeMapping  \
  WHERE vidyoAttributeName = 'Extension' \
  AND idpAttributeName IS NOT NULL \
  AND LENGTH(idpAttributeName) > 0 \
  UNION \
  SELECT tenantid \
  FROM TenantLdapAttributeMapping \
  WHERE vidyoAttributeName = 'Extension' \
  AND ldapAttributeName IS NOT NULL \
  AND LENGTH(ldapAttributeName) > 0 ")


for T in $TID; do
   PREFIX_LEN=$(SQL "SELECT LENGTH(tenantPrefix) FROM Tenant WHERE tenantID = $T")
   MAX_LEN=$(SQL "\
       SELECT MAX(LENGTH(roomExtNumber)) \
       FROM Room r, Member m \
       WHERE r.memberID = m.memberID \
       AND r.roomTypeID = 1 \
       AND m.importedUsed = 1 \
       AND INSTR(r.roomExtNumber, '.') = 0 \
       AND m.tenantID = $T")
   MAX_EXT_LENGTH=$((MAX_LEN - PREFIX_LEN))
   echo Processing TenantID: $T Max Length Personal Room: $MAX_EXT_LENGTH
    ((MAX_EXT_LENGTH++))
   if [[ $MAX_LEN -gt 0 ]]; then
   echo "Updating $T with $MAX_EXT_LENGTH"
   echo "Updating TenantConfiguration $T ->extnLength" : $MAX_EXT_LENGTH
   SQL "\
     UPDATE TenantConfiguration\
     SET extnLength=$MAX_EXT_LENGTH\
     WHERE tenantId = $T"
   fi
done

}

create_member_view()
{
    mysql $MYSQL_OPT -uroot << EOF
use portal2;
CREATE OR REPLACE VIEW MemberLimited_View AS
SELECT Member.memberID,
Member.roleID,
Member.tenantID,
Member.langID,
Member.profileID,
Member.proxyID,
Member.proxyIDOld,
Member.locationID,
Member.modeID,
Member.memberName,
Member.active,
Member.allowedToParticipate,
Member.memberCreated,
Member.location,
Member.description,
Member.passTime,
Member.defaultEndpointGUID,
Member.bindUserRequestID,
Member.importedUsed,
Member.bak_creation_time,
Member.loginFailureCount,
Member.creationTime,
Member.updateTime,
Member.userImageUploaded,
Member.userImageAllowed,
Member.lastModifiedDateExternal,
Member.thumbnailUpdateTime,
Member.neoRoomPermanentPairingDeviceUser
FROM portal2.Member Member;
EOF
}


create_member_view()
{
    mysql $MYSQL_OPT -uroot << EOF
use portal2;
CREATE OR REPLACE VIEW MemberLimited_View AS
SELECT Member.memberID,
Member.roleID,
Member.tenantID,
Member.langID,
Member.profileID,
Member.proxyID,
Member.proxyIDOld,
Member.locationID,
Member.modeID,
Member.memberName,
Member.active,
Member.allowedToParticipate,
Member.memberCreated,
Member.location,
Member.description,
Member.passTime,
Member.defaultEndpointGUID,
Member.bindUserRequestID,
Member.importedUsed,
Member.bak_creation_time,
Member.loginFailureCount,
Member.creationTime,
Member.updateTime,
Member.userImageUploaded,
Member.userImageAllowed,
Member.lastModifiedDateExternal,
Member.thumbnailUpdateTime,
Member.neoRoomPermanentPairingDeviceUser
FROM portal2.Member Member;
EOF

}



run_post_db_upgrade()
{
   echo "running post_db_upgrade... (ignore errors after this line)"
   logme "Running post DB upgrade script..."
   MYSQL_OPT="--defaults-extra-file=/root/.my.cnf --force"
   upgradeTo18dot40
   update_super_tenant_url
   create_member_view
   # note these 2 alters below are needed only for this release to be compatible with older builds.
   mysql $MYSQL_OPT -u root << EOF
   USE $DB_NAME;
EOF

}


####################################################################
# post_db_upgrade() will be introduced in 3.3.  This
# should handle the case where database schema is change more
# than once during the development of a specific release. This will
# avoid QA or customer running beta to manually alter the schema.
#
# Note: The post_db_upgrade should not be executed if you are
#       upgrading from lower DB version.
#
####################################################################
post_db_upgrade()
{
   logme "TARGET DB VERSION: $TARGET_DB_VER,  DB_VER: $DB_VER"
   if [ "$TARGET_DB_VER" = "$DB_VER" ]; then
      run_post_db_upgrade
   else
      logme "Will skip post DB upgrade script..."
   fi
}


### support upgrade only from 2.24
if [ "$DB_VER" = "2.24" ]; then
   upgradeTo2dot25
   upgradeTo2dot26
   upgradeTo2dot27
   upgradeTo3dot40
   upgradeTo3dot41
   upgradeTo3dot42
   upgradeTo3dot43
   upgradeTo3dot44
   upgradeTo3dot45
   upgradeTo3dot46
   upgradeTo3dot50
elif [ "$DB_VER" = "2.25" ]; then
   upgradeTo2dot26
   upgradeTo2dot27
   upgradeTo3dot40
   upgradeTo3dot41
   upgradeTo3dot42
   upgradeTo3dot43
   upgradeTo3dot44
   upgradeTo3dot45
   upgradeTo3dot46
   upgradeTo3dot50
elif [ "$DB_VER" = "2.26" ]; then
   upgradeTo2dot27
   upgradeTo3dot40
   upgradeTo3dot41
   upgradeTo3dot42
   upgradeTo3dot43
   upgradeTo3dot44
   upgradeTo3dot45
   upgradeTo3dot46
   upgradeTo3dot50
elif [ "$DB_VER" = "2.27" ]; then
   upgradeTo3dot40
   upgradeTo3dot41
   upgradeTo3dot42
   upgradeTo3dot43
   upgradeTo3dot44
   upgradeTo3dot45
   upgradeTo3dot46
   upgradeTo3dot50
elif [ "$DB_VER" = "2.28" ]; then
   upgradeTo3dot40
   upgradeTo3dot41
   upgradeTo3dot42
   upgradeTo3dot43
   upgradeTo3dot44
   upgradeTo3dot45
   upgradeTo3dot46
   upgradeTo3dot50
elif [ "$DB_VER" = "3.40" ]; then
   upgradeTo3dot41
   fix_location_proxy
   fix_language_table
   upgradeTo3dot42
   upgradeTo3dot43
   upgradeTo3dot44
   upgradeTo3dot45
   upgradeTo3dot46
   upgradeTo3dot50
   insert_countries
	upgradeTo3dot51
	upgradeTo17dot20
	upgradeTo17dot30
	upgradeTo18dot10	
	upgradeTo18dot20
	upgradeTo18dot21
	upgradeTo18dot30
	upgradeTo18dot31
	upgradeTo18dot40
	upgradeTo19dot10
    create_member_view
elif [ "$DB_VER" = "3.41" ]; then
   fix_language_table
   fix_location_proxy
   upgradeTo3dot42
   upgradeTo3dot43
   upgradeTo3dot44
   upgradeTo3dot45
   upgradeTo3dot46
   upgradeTo3dot50
   insert_countries
	upgradeTo3dot51
	upgradeTo17dot20
	upgradeTo17dot30
	upgradeTo18dot10
	upgradeTo18dot20
	upgradeTo18dot21
	upgradeTo18dot30
	upgradeTo18dot31
	upgradeTo18dot40
	upgradeTo19dot10
    create_member_view
elif [ "$DB_VER" = "3.42" ]; then
   upgradeTo3dot43
   upgradeTo3dot44
   upgradeTo3dot45
   upgradeTo3dot46
   upgradeTo3dot50
   insert_countries
	upgradeTo3dot51
	upgradeTo17dot20
	upgradeTo17dot30
	upgradeTo18dot10
	upgradeTo18dot20
	upgradeTo18dot21
	upgradeTo18dot30
	upgradeTo18dot31
	upgradeTo18dot40
	upgradeTo19dot10
    create_member_view
elif [ "$DB_VER" = "3.43" ]; then
   upgradeTo3dot44
   upgradeTo3dot45
   upgradeTo3dot46
   upgradeTo3dot50
   insert_countries
	upgradeTo3dot51
	upgradeTo17dot20
	upgradeTo17dot30
	upgradeTo18dot10
	upgradeTo18dot20
	upgradeTo18dot21
	upgradeTo18dot30
	upgradeTo18dot31
	upgradeTo18dot40
	upgradeTo19dot10
    create_member_view
   logme "DB version is $VER"
elif [ "$DB_VER" = "3.44" ]; then
   upgradeTo3dot45
   upgradeTo3dot46
   upgradeTo3dot50
   insert_countries
   upgradeTo3dot51
   upgradeTo17dot20
   upgradeTo17dot30
   upgradeTo18dot10
   upgradeTo18dot20
   upgradeTo18dot21
   upgradeTo18dot30
   upgradeTo18dot31
   upgradeTo18dot40
   upgradeTo19dot10
   create_member_view
   logme "DB version is $VER"
elif [ "$DB_VER" = "3.45" ]; then
   upgradeTo3dot46
   upgradeTo3dot50
   insert_countries
   upgradeTo3dot51
   upgradeTo17dot20
   upgradeTo17dot30
   upgradeTo18dot10
   upgradeTo18dot20
   upgradeTo18dot21
   upgradeTo18dot30
   upgradeTo18dot31
   upgradeTo18dot40
   upgradeTo19dot10
   create_member_view
   logme "DB version is $VER"
elif [ "$DB_VER" = "3.46" ]; then
   upgradeTo3dot50
   insert_countries
   upgradeTo3dot51
   upgradeTo17dot20
   upgradeTo17dot30
   upgradeTo18dot10
   upgradeTo18dot20
   upgradeTo18dot21
   upgradeTo18dot30
   upgradeTo18dot31
   upgradeTo18dot40
   upgradeTo19dot10
   create_member_view
   logme "DB version is $VER"
elif [ "$DB_VER" = "3.50" ]; then
	upgradeTo3dot51
	upgradeTo17dot20
	upgradeTo17dot30
	upgradeTo18dot10
	upgradeTo18dot20
	upgradeTo18dot21
	upgradeTo18dot30
	upgradeTo18dot31
    upgradeTo18dot40
    upgradeTo19dot10
    create_member_view
	logme "DB version is $VER"
elif [ "$DB_VER" = "3.51" ]; then
    fix_language_table
	upgradeTo17dot20
	upgradeTo17dot30
	upgradeTo18dot10
	upgradeTo18dot20
	upgradeTo18dot21
	upgradeTo18dot30
	upgradeTo18dot31
	upgradeTo18dot40
	upgradeTo19dot10
    create_member_view
	logme "DB version is $VER"
elif [ "$DB_VER" = "17.2.0" ]; then
    fix_language_table
	upgradeTo17dot30
	upgradeTo18dot10
	upgradeTo18dot20
	upgradeTo18dot21
	upgradeTo18dot30
	upgradeTo18dot31
    upgradeTo18dot40
    upgradeTo19dot10
    create_member_view
	logme "DB version is $VER"
elif [ "$DB_VER" = "17.3.0" ]; then
    fix_language_table
	upgradeTo18dot10
	upgradeTo18dot20
	upgradeTo18dot21
	upgradeTo18dot30
	upgradeTo18dot31
	upgradeTo18dot40
	upgradeTo19dot10
    create_member_view
    logme "DB version is $VER"
elif [ "$DB_VER" = "17.3.1" ]; then
    fix_language_table
    upgradeTo18dot10
    upgradeTo18dot20
    upgradeTo18dot21
    upgradeTo18dot30
    upgradeTo18dot31
    upgradeTo18dot40
    upgradeTo19dot10
    create_member_view
	logme "DB version is $VER"
elif [ "$DB_VER" = "18.1.0" ]; then
    fix_language_table
    upgradeTo18dot20
    upgradeTo18dot21
    upgradeTo18dot30
    upgradeTo18dot31
    upgradeTo18dot40
    upgradeTo19dot10
    create_member_view
	logme "DB version is $VER"
elif [ "$DB_VER" = "18.2.0" ]; then
    fix_language_table
    upgradeTo18dot21
    upgradeTo18dot30
    upgradeTo18dot31
    upgradeTo18dot40
    upgradeTo19dot10
    create_member_view
	logme "DB version is $VER"
elif [ "$DB_VER" = "18.2.1" ]; then
    fix_language_table
    upgradeTo18dot30
    upgradeTo18dot31
    upgradeTo18dot40
    upgradeTo19dot10
    create_member_view
	logme "DB version is $VER"    
elif [ "$DB_VER" = "18.3.0" ]; then
    fix_language_table
    upgradeTo18dot31
    upgradeTo18dot40
    create_member_view
    logme "DB version is $VER"
elif [ "$DB_VER" = "18.3.1" ]; then
    fix_language_table
    upgradeTo18dot40
    upgradeTo19dot10
    create_member_view
    logme "DB version is $VER"
elif [ "$DB_VER" = "18.4.0" ]; then
	fix_language_table
    upgradeTo19dot10
    create_member_view
	logme "DB version is $VER"
elif [ "$DB_VER" = "19.1.0" ]; then
	logme "DB version is $VER"
fi

mysql $MYSQL_OPT -uroot <<EOF
USE \`$DB_NAME\`;
delete from Configuration where configurationName='PortalVersion';
INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (1, 'PortalVersion', '$VER');
delete from Configuration where configurationName = 'INTERNAL_VERSION';
INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'INTERNAL_VERSION', '$INTERNAL_VER');
REVOKE ALL PRIVILEGES, GRANT OPTION FROM 'vidyo'@'localhost';
GRANT  SELECT, INSERT, DELETE, UPDATE, EXECUTE, CREATE TEMPORARY TABLES ON portal2.* TO 'vidyo'@'localhost';
GRANT  SELECT, INSERT, DELETE, UPDATE, CREATE TEMPORARY TABLES ON portal2audit.* TO 'vidyo'@'localhost';
GRANT  SELECT, INSERT, DELETE, UPDATE, CREATE TEMPORARY TABLES ON PORTAL_BATCH.* TO 'vidyo'@'localhost';
QUIT
EOF

###################################################################################
# Note: Force the script to run the whole upgrade script for a particular release.
###################################################################################
post_db_upgrade

## rebuild all the indexes by exporting and importing DB.
logme "Rebuilding all the indexes by exporting and importing DB"
logme "Creating database dump..."
ARCH_DB=/opt/vidyo/archives/database/upgrade_backup_${TARGET_DB_VER}.sql
mysqldump $MYSQL_OPT -uroot --add-drop-database --databases portal2 > $ARCH_DB
if [ $? -eq 0 ]; then
   logme "Restoring database dump..."
   mysql $MYSQL_OPT --force -uroot < $ARCH_DB
else
   logme "Failed to create database dump"
fi

gzip -f $ARCH_DB

logme "Portal software version is upgraded to $VER"
exit

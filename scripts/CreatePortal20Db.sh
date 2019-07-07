#!/bin/bash

DB_NAME=portal2
DB_AUDIT_NAME=portal2audit
VC_TAG=%%TAG%%
DB_VERSION="19.1.0"

PASSWORD=$(echo -n password | openssl enc -aes-256-cbc -a -md sha1 -pass file:/opt/vidyo/etc/ssl/private/misc.pem)

DB_ACCESS=/opt/vidyo/etc/db/access.conf
MYSQL_OPT="--defaults-extra-file=/root/.my.cnf "

if [ ! -f $DB_ACCESS ]; then
   logger -t "CreatePortal20Db.sh" "DB Access file not found!"
   exit 1
fi

. $DB_ACCESS

if [ -z "$VIDYO" ]; then
   logger -t "CreatePortal20Db.sh" "No password configured for vidyo user!"
   exit 1
fi

[ -n "$1" ] && VC_TAG=$1

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

NETWORK="<NetworkConfig><DocumentVersion>0</DocumentVersion></NetworkConfig>"

DEFAULT_INTF=eth0
#get default IP addr

mysql $MYSQL_OPT -uroot << EOF


DROP DATABASE IF EXISTS \`$DB_NAME\`;

CREATE DATABASE IF NOT EXISTS \`$DB_NAME\` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
-- GRANT ALL ON \`$DB_NAME\`.* TO 'vidyo'@'localhost' IDENTIFIED BY '$VIDYO';
-- GRANT FILE ON *.* to 'vidyo'@'localhost';
GRANT  SELECT, INSERT, DELETE, UPDATE, EXECUTE, CREATE TEMPORARY TABLES ON portal2.* TO 'vidyo'@'localhost' IDENTIFIED BY '$VIDYO';

FLUSH PRIVILEGES;

ALTER DATABASE \`$DB_NAME\` CHARACTER SET utf8 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT
COLLATE utf8_general_ci;
COMMIT;


USE \`$DB_NAME\`;
SET NAMES utf8;

-- START database portal2
--
-- Table: Conferences
        CREATE TABLE Conferences (
              conferenceID    int(10) AUTO_INCREMENT NOT NULL,
              conferenceName  varchar(200),
              conferenceType  char COMMENT 'C - Conference, P - P2P',
              roomID          int(10) UNSIGNED NOT NULL,
			  GUID            VARCHAR(64),
              endpointID      int(10) UNSIGNED NOT NULL,
              endpointType    char COMMENT 'D - VidyoDesktop or VidyoRoom, V - Virtual Endpoints (Gateway) ',
              endpointCaller  char DEFAULT 'N' COMMENT 'Y - caller, N - callee',
              tenantID        int(10) UNSIGNED DEFAULT '1',
              audio           int(1) DEFAULT '1' COMMENT '0 - muted, 1 - unmuted',
              video           int(1) DEFAULT '1' COMMENT '0 - stoped, 1- started',
              speaker         int(1) DEFAULT '1' COMMENT '0 - silenced, 1- unsilenced',
              handRaised      int(1) DEFAULT NULL,
              handRaisedTime  datetime DEFAULT NULL,
              presenter       int(1) DEFAULT '0',
              VRID            varchar(128),
              VRName          varchar(128),
              GroupID         varchar(128),
              GroupName       varchar(128),
              participantID   varchar(64) DEFAULT NULL,
              uniqueCallID    varchar(20) NOT NULL,
              createdTime     timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
              modifiedTime    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
              /* Keys */
              PRIMARY KEY (conferenceID),
              INDEX idx_conf_tenant_id (tenantID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

-- Table: Configuration
        CREATE TABLE Configuration (
            configurationID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            tenantID int(10) UNSIGNED DEFAULT '1',
            configurationName varchar(128) NOT NULL,
            configurationValue MEDIUMTEXT NOT NULL,
            oldConfigurationValue MEDIUMTEXT,
            configDate timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
            updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
            /* Keys */
            PRIMARY KEY (configurationID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE UNIQUE INDEX configurationName
            ON Configuration
            (configurationName, tenantID);

-- Table: Endpoints
        CREATE TABLE Endpoints (
              endpointID    int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
              memberID      int(10),
              memberType    varchar(1) COLLATE utf8_general_ci NOT NULL DEFAULT 'R' COMMENT 'R - regular user, G - guest',
              endpointGUID  varchar(64) NOT NULL,
              status        int(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '0-Offline,1-Online,2-Busy,3-Ringing,4-RingAccepted,5-RingRejected,6-RingNoAnswer',
              creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	      	  updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
              ipAddress     VARCHAR(64) DEFAULT '',
              authorized    int(1) UNSIGNED NOT NULL DEFAULT '0',
              sequenceNum   bigint NOT NULL DEFAULT '0',
              consumesLine tinyint(1) NOT NULL DEFAULT '1',
              referenceNumber varchar(64) DEFAULT NULL,
              applicationName varchar(24) DEFAULT NULL,
              applicationVersion varchar(32) DEFAULT NULL,
              applicationOS varchar(24) DEFAULT NULL,
              deviceModel varchar(50) DEFAULT NULL,
              endpointPublicIPAddress varchar(48) DEFAULT NULL,
              endpointUploadType varchar(1) DEFAULT NULL,
              lectureModeSupport tinyint(1) NOT NULL DEFAULT '0',
              extDataType INT(11) DEFAULT '0', 
              extData VARCHAR(2048) DEFAULT NULL,
              /* Keys */
              PRIMARY KEY (endpointID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE UNIQUE INDEX endpointGUID
            ON Endpoints
            (endpointGUID);

-- Table: EndpointUpload
        CREATE TABLE EndpointUpload (
            endpointUploadID int(10) AUTO_INCREMENT NOT NULL,
            tenantID int(10) DEFAULT '1',
            endpointUploadFile varchar(1028) NOT NULL,
            endpointUploadTime int NOT NULL,
            endpointUploadType varchar(3) NOT NULL COMMENT 'M/W32/W64/R/V',
            endpointUploadActive int(1),
            whoUploadFile varchar(1)  DEFAULT 'S' COMMENT 'S - super, A - admin',
            endpointUploadVersion VARCHAR(130) DEFAULT NULL,
            /* Keys */
            PRIMARY KEY (endpointUploadID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

-- Table: Groups
        CREATE TABLE Groups (
            groupID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            tenantID int(10) UNSIGNED NOT NULL DEFAULT '1',
            routerID int(10) UNSIGNED NOT NULL DEFAULT '1',
            secondaryRouterID    int(10) UNSIGNED NOT NULL DEFAULT '1',
            groupName varchar(128) NOT NULL,
            groupDescription varchar(256),
            defaultFlag int(1) NOT NULL DEFAULT '0',
            roomMaxUsers int(10) UNSIGNED NOT NULL DEFAULT '10',
            userMaxBandWidthIn int(10) UNSIGNED NOT NULL DEFAULT '6000',
            userMaxBandWidthOut int(10) UNSIGNED NOT NULL DEFAULT '6000',
			allowRecording       int(1) NOT NULL DEFAULT '0',
			creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
			updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
            /* Keys */
            PRIMARY KEY (groupID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE UNIQUE INDEX tenantGroups
            ON Groups
            (groupName, tenantID);

-- Table: Language
        CREATE TABLE Language (
            langID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            langCode varchar(5) NOT NULL,
            langName varchar(20)NOT NULL,
            /* Keys */
            PRIMARY KEY (langID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

-- Table: Member
        CREATE TABLE Member (
            memberID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            roleID int(10) UNSIGNED,
            tenantID int(10) UNSIGNED,
            langID int(10) UNSIGNED NOT NULL,
            profileID int(10) UNSIGNED,
            proxyID int(10) UNSIGNED,
            proxyIDOld int(10) UNSIGNED,
            locationID int(10) UNSIGNED,
            modeID int(10) DEFAULT '1' COMMENT 'Member mode - Available, Away, Do Not Disturb',
            username varchar(128) NOT NULL,
            password varchar(256) NOT NULL,
            memberName varchar(128),
            active int(1) NOT NULL DEFAULT '1',
	    allowedToParticipate int(1) NOT NULL DEFAULT '1',
            emailAddress varchar(254) NOT NULL,
            memberCreated int NOT NULL,
            location varchar(255),
            description text,
            passKey varchar(256),
            passTime int(10),
            defaultEndpointGUID varchar(64),
            pak varchar(40) COMMENT 'Portal Access Key',
            pak2 varchar(100) COMMENT 'Portal Access Key 2',
            bak varchar(40) COMMENT 'Browser Access Key',
            sak varchar(256) COMMENT 'Service Access Key',
            bindUserRequestID varchar(10),
            importedUsed INT(1) UNSIGNED NULL DEFAULT 0,
            phone1 varchar(64) DEFAULT NULL,
            phone2 varchar(64) DEFAULT NULL,
            phone3 varchar(64) DEFAULT NULL,
            department varchar(128) DEFAULT NULL,
            title varchar(128) DEFAULT NULL,
            instantMessagerID varchar(128) DEFAULT NULL,
            bak_creation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            loginFailureCount INT(11) NOT NULL DEFAULT '0',
            creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
            updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
            userImageUploaded TINYINT(1) NOT NULL DEFAULT 0,
            userImageAllowed TINYINT(1) NOT NULL DEFAULT 1,
            neoRoomPermanentPairingDeviceUser TINYINT(1) NOT NULL DEFAULT 0,
            lastModifiedDateExternal varchar(32) DEFAULT NULL,
            thumbnailUpdateTime TIMESTAMP NULL DEFAULT NULL,
            /* Keys */
            PRIMARY KEY (memberID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE INDEX idx_memberIDactiveallowedToParticipate
            ON Member (memberID, active, allowedToParticipate);

            CREATE UNIQUE INDEX tenantMembers
            ON Member
            (username, tenantID);

-- Table: MemberRole
        CREATE TABLE MemberRole (
            roleID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            roleName varchar(64) NOT NULL,
            roleDescription text NOT NULL,
            /* Keys */
            PRIMARY KEY (roleID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

-- Table: Profile
        CREATE TABLE Profile (
            profileID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            profileName varchar(64) NOT NULL,
            profileDescription text,
            profileXML text,
            /* Keys */
            PRIMARY KEY (profileID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

-- Table: Room
         CREATE TABLE Room (
            roomID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            roomTypeID int(10) UNSIGNED NOT NULL,
            memberID int(10) UNSIGNED NOT NULL,
            groupID int(10) UNSIGNED NOT NULL,
            profileID int(10) UNSIGNED NOT NULL DEFAULT '0',
            roomName varchar(150) NOT NULL,
            roomExtNumber varchar(64) NOT NULL,
            displayName varchar(128) NOT NULL,
            roomDescription text,
            roomPIN varchar(64),
            roomModeratorPIN VARCHAR(64),
            roomLocked int(1) UNSIGNED NOT NULL DEFAULT '0',
            roomEnabled int(1) UNSIGNED NOT NULL DEFAULT '0',
            roomKey varchar(80),
            roomModeratorKey varchar(80) DEFAULT NULL,
            roomMuted int(1) UNSIGNED NOT NULL DEFAULT '0',
            roomSilenced int(1) UNSIGNED NOT NULL DEFAULT '0',
            roomVideoMuted int(1) unsigned NOT NULL DEFAULT '0',
            roomVideoSilenced int(1) unsigned NOT NULL DEFAULT '0',
            roomSpeakerMuted int(1) UNSIGNED NOT NULL DEFAULT '0',
            lectureMode int(1) DEFAULT '0',
            recurring INT DEFAULT 0,
            last_used TIMESTAMP DEFAULT '0000-00-00 00:00:00' NOT NULL,
            deleteon TIMESTAMP NULL DEFAULT NULL,
            creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
            updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
            /* Keys */
            PRIMARY KEY (roomID)
            ) ENGINE = InnoDB
            COLLATE utf8_general_ci;

            CREATE FULLTEXT INDEX idx_fulltext_roomNameroomExtdisplayName ON Room( roomName,roomExtNumber,displayName);
            CREATE UNIQUE INDEX roomExtNumber ON Room (roomExtNumber);
            CREATE INDEX idx_RoomName ON Room (roomName);
            CREATE INDEX idx_deleteon ON Room (deleteon);
            CREATE UNIQUE INDEX idx_roomModeratorKey on Room (roomModeratorKey);
            CREATE UNIQUE INDEX idx_roomKey on Room (roomKey);

-- Table: RoomType
        CREATE TABLE RoomType (
            roomTypeID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            roomType varchar(128) NOT NULL,
            roomTypeDescription text,
            /* Keys */
            PRIMARY KEY (roomTypeID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

-- Table: Routers
        CREATE TABLE Routers (
            routerID     int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            routerName   varchar(128) NOT NULL,
            description  text,
            active       int(1) DEFAULT '1' COMMENT '1 - active, 0 - not active',
            /* Keys */
            PRIMARY KEY (routerID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

-- Table: SpeedDial
        CREATE TABLE SpeedDial (
            speedDialID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            memberID int(10) UNSIGNED NOT NULL,
            roomID int(10) UNSIGNED NOT NULL,
            creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
            /* Keys */
            PRIMARY KEY (speedDialID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE INDEX memberSpeedDial
            ON SpeedDial
            (memberID, roomID);

            CREATE INDEX idx_memberID ON SpeedDial (memberID);
            CREATE INDEX idx_roomID ON SpeedDial (roomID);

-- Table: Tenant
        CREATE TABLE Tenant (
            tenantID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
            tenantName varchar(128) NOT NULL,
            tenantUrl varchar(128),
            description text,
            tenantPrefix varchar(7),
            tenantDialIn  varchar(20),
            tenantReplayUrl  varchar(128),
            tenantWebRTCURL varchar(128) DEFAULT NULL,
            mobileLogin int(1) default '1',
            vidyoGatewayControllerDns VARCHAR(128),
            scheduledRoomEnabled INT(1) NOT NULL DEFAULT '1',
            creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
            updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
            /* Keys */
            PRIMARY KEY (tenantID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE UNIQUE INDEX tenantName
            ON Tenant
            (tenantName);
            
            CREATE UNIQUE INDEX idx_tenant_tenanturl
            ON Tenant
            (tenantUrl);

            CREATE UNIQUE INDEX idx_vidyoGatewayControllerDns
            ON Tenant
            (vidyoGatewayControllerDns);

-- Table: TenantXcall
        CREATE TABLE TenantXcall (
            tenantID int NOT NULL,
            callTo int NOT NULL,
            creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
            /* Keys */
            PRIMARY KEY (tenantID, callTo)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE UNIQUE INDEX TenantCallTo
            ON TenantXcall
            (tenantID, callTo);

-- Table: TenantXrouter
	CREATE TABLE TenantXrouter (
	     tenantID int NOT NULL,
             routerID  int NOT NULL,
             /* Keys */
             PRIMARY KEY (tenantID, routerID)
             ) ENGINE = InnoDB
               COLLATE utf8_general_ci;

	CREATE UNIQUE INDEX TenantRouter
	     ON TenantXrouter
             (tenantID, routerID);

        CREATE TABLE Services (
              serviceID    int(10) AUTO_INCREMENT NOT NULL,
              roleID       int(10) NOT NULL,
              serviceName  varchar(40) NOT NULL,
              user         varchar(40) NOT NULL,
              password     varchar(128) NOT NULL,
              url          text,
	      	  serviceEndpointGuid varchar(64) DEFAULT NULL,
              token varchar(64) DEFAULT NULL,
              serviceRef varchar(40) DEFAULT NULL,
              updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
              /* Keys */
              PRIMARY KEY (serviceID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE INDEX Services_Index01
              ON Services
              (user);

        CREATE TABLE ServicesRole (
              roleID           int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
              roleName         varchar(64) NOT NULL,
              roleDescription  text NOT NULL,
              /* Keys */
              PRIMARY KEY (roleID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

        CREATE TABLE VirtualEndpoints (
              endpointID    int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
              serviceID	    int(10) NOT NULL,
			  tenantID	    int(10),
              gatewayID     varchar(40),
              displayName   varchar(200),
              displayExt    varchar(200),
	   		  entityID	    int(10),
              endpointGUID  varchar(64) NOT NULL,
              status        int(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '0-Offline,1-Online,2-Busy,3-Ringing,4-RingAccepted,5-RingRejected,6-RingNoAnswer',
              direction     int(1) NOT NULL DEFAULT '0' COMMENT '0 - fromLegacy, 1 - toLegacy',
              prefix        varchar(32),
              creationTime  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
              updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
              sequenceNum bigint NOT NULL DEFAULT '0',
              deviceModel varchar(50) DEFAULT NULL,
              endpointPublicIPAddress varchar(48) DEFAULT NULL,
              /* Keys */
              PRIMARY KEY (endpointID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE UNIQUE INDEX endpointGUID
              ON VirtualEndpoints
              (endpointGUID);

            CREATE TABLE Guests (
              guestID    int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
              tenantID   int(10),
              username  varchar(64),
              guestName  varchar(255),
	          pak varchar(40) COMMENT 'Portal Access Key',
	          pak2 varchar(100) COMMENT 'Portal Access Key 2',
              sak        varchar(256) COMMENT 'Service Access Key',
              creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
              updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
			  bindUserRequestID varchar(10),
			  roomID             int(10),
              /* Keys */
              PRIMARY KEY (guestID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE TABLE ClientInstallations (
                userName VARCHAR(128) NOT NULL DEFAULT '',
                tenantName VARCHAR(128) DEFAULT '',
                EID VARCHAR(64) DEFAULT'',
                ipAddress VARCHAR(64) DEFAULT '',
                hostName VARCHAR(64) DEFAULT '',
                roomName VARCHAR(128) DEFAULT '',
                roomOwner VARCHAR(64) DEFAULT '',
                timeInstalled TIMESTAMP DEFAULT NOW()
            ) ENGINE=InnoDB COLLATE utf8_general_ci;

           CREATE TABLE TenantXservice (
              tenantID  int(10) NOT NULL,
              serviceID  int(10) NOT NULL,
              creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
              /* Keys */
              PRIMARY KEY (tenantID, serviceID)
            ) ENGINE = InnoDB
              COLLATE utf8_general_ci;

            CREATE UNIQUE INDEX TenantService
              ON TenantXservice
              (tenantID, serviceID);


            CREATE TABLE PointToPointCall (
              CallID            int AUTO_INCREMENT NOT NULL,
              ConferenceName    varchar(200) NOT NULL,
              CallerName        varchar(200),
              CallerTenantName  varchar(128),
              CallerJoinTime    datetime,
              CallerLeaveTime   datetime,
              CalleeName        varchar(200),
              CalleeTenantName  varchar(128),
              CalleeJoinTime    datetime,
              CalleeLeaveTime   datetime,
              CallState         varchar(80) NOT NULL,
              /* Keys */
              PRIMARY KEY (CallID)
            ) ENGINE = InnoDB
              MAX_ROWS = 1000000
              COLLATE utf8_general_ci;

            CREATE INDEX CallerJoinTime
              ON PointToPointCall
              (CallerJoinTime);

            CREATE TABLE ConferenceCall (
              CallID          int AUTO_INCREMENT NOT NULL,
              ConferenceName  varchar(200) NOT NULL,
              CallerName      varchar(200),
              TenantName      varchar(128),
              JoinTime        datetime,
              LeaveTime       datetime,
              CallState       varchar(80) NOT NULL,
              /* Keys */
              PRIMARY KEY (CallId)
            ) ENGINE = InnoDB
              MAX_ROWS = 1000000
              COLLATE utf8_general_ci;

            CREATE INDEX ConferenceId
              ON ConferenceCall
              (ConferenceName);

            CREATE INDEX JoinTime
              ON ConferenceCall
              (JoinTime);

           CREATE TABLE TenantXauthRole (
               tenantID  int NOT NULL,
               roleID    int NOT NULL,
               creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
               /* Keys */
               PRIMARY KEY (tenantID, roleID)
             ) ENGINE = InnoDB;

           CREATE UNIQUE INDEX TenantAuthRole
               ON TenantXauthRole
               (tenantID, roleID);

			CREATE TABLE NetworkElementConfiguration (
			  Identifier varchar(128) NOT NULL default '',
			  DisplayName varchar(256) NOT NULL default '',
			  ComponentType varchar(32) NOT NULL default '',
			  IpAddress varchar(255) default NULL,
              WebApplicationURL varchar(256) default NULL,
			  Data text,
			  RunningVersion int(8) default 0,
			  Version int(8) default NULL,
			  ExpirySeconds int(8) default '30',
			  Alarm text,
			  SwVer varchar(256) default NULL,
			  Status enum('ACTIVE','INACTIVE','NEW') default NULL,
			  LastModified datetime default NULL,
			  Heartbeat datetime default NULL,
			  PRIMARY KEY  (Identifier),
			  KEY ComponentType (ComponentType,IpAddress)
			) ENGINE=InnoDB DEFAULT CHARSET=utf8;


			CREATE TABLE NetworkConfig (
			  Identifier varchar(128) NOT NULL default '',
			  Data MEDIUMTEXT,
			  Version int(8) default NULL,
			  LastModified datetime default NULL,
			  Status enum('ACTIVE','INACTIVE','INPROGRESS') default 'ACTIVE'
			) ENGINE=InnoDB DEFAULT CHARSET=utf8;

			CREATE TABLE RecorderEndpoints (
				endpointID    int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
				serviceID     int(10) NOT NULL,
				recID         varchar(40),
				description   varchar(64),
				entityID      int(10),
				webcast       int(1) NOT NULL DEFAULT '0',
				endpointGUID  varchar(64) NOT NULL,
				status      int(1) UNSIGNED NOT NULL DEFAULT '0' COMMENT '0-Offline,1-Online,2-Busy,3-Ringing,4-RingAccepted,5-RingRejected,6-RingNoAnswer',
				prefix        varchar(32),
				creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
				updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	     		sequenceNum bigint NOT NULL DEFAULT '0',
				/* Keys */
				PRIMARY KEY (endpointID)
			) ENGINE = InnoDB;

			CREATE UNIQUE INDEX recorderEndpointGUID
				ON RecorderEndpoints
 				(endpointGUID);

             CREATE TABLE Locations (
  	               locationID   int(10) AUTO_INCREMENT NOT NULL,
  	               locationTag  varchar(40) NOT NULL,
  	               creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	               updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  	               /* Keys */
  	               PRIMARY KEY (locationID)
  	             ) ENGINE = InnoDB;

  	             CREATE INDEX Locations_Index01
  	               ON Locations
  	               (locationTag);

  	             CREATE TABLE TenantXlocation (
  	               tenantID    int(10) NOT NULL,
  	               locationID  int(10) NOT NULL,
  	               creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	               /* Keys */
  	               PRIMARY KEY (tenantID, locationID)
  	             ) ENGINE = InnoDB
  	               COLLATE utf8_general_ci;

  	             CREATE UNIQUE INDEX TenantXlocation_Index01
  	               ON TenantXlocation
  	               (tenantID, locationID);

				CREATE TABLE MemberMode (
  					modeID  int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
					modeName  varchar(64) NOT NULL,
					modeDescription  text,
					/* Keys */
					PRIMARY KEY (modeID)
				) ENGINE = InnoDB;

			CREATE TABLE ConferenceRecord (
  	               GUID            varchar(64) NOT NULL,
				   UniqueCallID    varchar(20) NOT NULL,
  	               conferenceName  varchar(200),
  	               conferenceType  char COMMENT 'C - Conference, P - P2P',
  	               roomID          int(10) UNSIGNED NOT NULL,
  	               endpointID      int(10) UNSIGNED NOT NULL,
  	               endpointType    char COMMENT 'D - VidyoDesktop or VidyoRoom, V - Virtual Endpoints (Gateway) ',
  	               endpointCaller  char DEFAULT 'N' COMMENT 'Y - caller, N - callee',
  	               tenantID        int(10) UNSIGNED DEFAULT '1',
  	               audio           int(1) DEFAULT '1' COMMENT '0 - muted, 1 - unmuted',
  	               video           int(1) DEFAULT '1' COMMENT '0 - stoped, 1- started',
  	               speaker         int(1) DEFAULT '1' COMMENT '0 - silenced, 1- unsilenced',
  	               updateTime      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
		       	   conferenceCreated tinyint(1) NOT NULL DEFAULT '1',
  	               /* Keys */
  	               PRIMARY KEY (GUID)
  	             ) ENGINE = InnoDB;

             CREATE TABLE Federations (
               requestID       varchar(64) NOT NULL,
               fromUser        varchar(40),
               toUser          varchar(40),
               externalLinkID  int(10),
               /* Keys */
               PRIMARY KEY (requestID)
             ) ENGINE = InnoDB;

             CREATE TABLE ExternalLinks (
               externalLinkID             int(10) AUTO_INCREMENT NOT NULL,
               requestID                  varchar(64) NOT NULL,
               fromSystemID               varchar(128),
               fromTenantHost             varchar(128),
               fromConferenceName         varchar(200),
               toSystemID                 varchar(128),
               toTenantHost               varchar(128),
               toConferenceName           varchar(200),
               localMediaAddress         text,
               localMediaAdditionalInfo  text,
               remoteMediaAddress         text,
               remoteMediaAdditionalInfo  text,
               status                   int(1) DEFAULT '0' COMMENT '0 - created, 1 - disconnected, 2 - connected',
			   uniqueCallID  varchar(20),
			   creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
			   modificationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
			   secure  varchar(1),
               /* Keys */
               PRIMARY KEY (externalLinkID)
             ) ENGINE = InnoDB;

             CREATE TABLE FederationConferences (
               conferenceID      int(10) AUTO_INCREMENT NOT NULL,
               conferenceName    varchar(200),
               conferenceType    char COMMENT 'C - Conference, P - P2P',
               endpointID        int(10) UNSIGNED NOT NULL,
               endpointGUID      varchar(64) NOT NULL,
               endpointType      char COMMENT 'D - VidyoDesktop or VidyoRoom, V - Virtual Endpoints (Gateway) ',
               endpointCaller    char DEFAULT 'N' COMMENT 'Y - caller, N - callee',
               userNameAtTenant  varchar(64) NOT NULL,
               displayName       varchar(128) NOT NULL,
               extension         varchar(64) NOT NULL,
               dialIn            varchar(20),
               video             int(1) DEFAULT '1' COMMENT '0 - stoped, 1- started',
               audio             int(1) DEFAULT '1' COMMENT '0 - muted, 1 - unmuted',
               /* Keys */
               PRIMARY KEY (conferenceID)
             ) ENGINE = InnoDB;

        	-- Table IPC

        	CREATE TABLE IpcConfiguration (
        	  ipcID int(11) NOT NULL auto_increment,
        	  tenantID int(11) NOT NULL,
        	  inbound int(1) NOT NULL default '1',
        	  outbound int(1) NOT NULL default '1',
        	  inboundLines int(11) NOT NULL default '99999',
        	  outboundLines int(11) NOT NULL default '99999',
        	  allowed int(1) NOT NULL default '0',
        	  creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        	  updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        	  PRIMARY KEY  (ipcID),
        	  UNIQUE KEY idx_tenantID (tenantID)
        	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

        	CREATE TABLE IpcPortalDomainList (
        	  domainID int(11) NOT NULL auto_increment,
        	  domainName varchar(200) NOT NULL,
        	  creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        	  updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        	  PRIMARY KEY  (domainID)
        	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

        	CREATE TABLE IpcTenantDomainList (
        	  ID int(11) NOT NULL auto_increment,
        	  ipcID int(11) NOT NULL,
        	  hostname varchar(200) NOT NULL,
        	  creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        	  updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        	  PRIMARY KEY  (ID)
        	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

			CREATE TABLE ConferenceCall2 (
			  CallID          int AUTO_INCREMENT NOT NULL,
			  UniqueCallID    varchar(20) NOT NULL,
			  ConferenceName  varchar(200) NOT NULL,
			  TenantName      varchar(128) NOT NULL,
			  ConferenceType  char(2) NOT NULL COMMENT 'D - Direct Call (two party), C - Conference Call, ID - Inter portal Direct Call, IC - Inter portal Conference Call',
			  EndpointType    char NOT NULL COMMENT 'R - VidyoRoom, D - VidyoDesktop, G - Guest, L - Call to Legacy via VidyoGateway, C - Call Recorded via VidyoReplay/Recorder',
			  CallerID        varchar(200) NOT NULL,
			  CallerName      varchar(200),
			  JoinTime        datetime NOT NULL,
			  LeaveTime       datetime,
			  CallState       varchar(80) NOT NULL,
			  Direction       char NOT NULL COMMENT 'I - Inbound Call, O - Outbound Call',
			  RouterID        varchar(128),
			  GWID            varchar(40),
			  GWPrefix        varchar(20),
			  ReferenceNumber varchar(64) DEFAULT NULL,
			  ApplicationName varchar(24) DEFAULT NULL,
			  ApplicationVersion varchar(32) DEFAULT NULL,
			  ApplicationOs varchar(24) DEFAULT NULL,
			  DeviceModel varchar(50) DEFAULT NULL,
			  EndpointPublicIPAddress varchar(48) NOT NULL DEFAULT '',
			  CallCompletionCode char(1) NOT NULL DEFAULT '0',
			  Extension varchar(64) DEFAULT NULL,
			  EndpointGUID varchar(64) NOT NULL DEFAULT '',
              AccessType CHAR(1) NOT NULL DEFAULT '' COMMENT 'U-registered user,G-guest,L-legacy,R-recorder',
              RoomType CHAR(1) NOT NULL DEFAULT '' COMMENT 'M-private room of member,P-public room,S-scheduledr room',
              RoomOwner VARCHAR(128) NOT NULL DEFAULT '',
			  /* Keys */
			  PRIMARY KEY (CallID)
			) ENGINE = InnoDB
			  MAX_ROWS = 1000000
			  COLLATE utf8_general_ci;
	    CREATE INDEX idx_UniqueCallID on portal2.ConferenceCall2 (UniqueCallID);
	    CREATE INDEX idx_CallState ON ConferenceCall2(CallState);
	    CREATE INDEX idx_cc_guid on ConferenceCall2(EndpointGUID);
	    CREATE INDEX idx_tenant ON ConferenceCall2(TenantName);
	    CREATE INDEX idx_jointime ON ConferenceCall2(JoinTime);
	    CREATE INDEX idx_leavetime ON ConferenceCall2(LeaveTime);

    	CREATE UNIQUE INDEX idx_ipcc_tenantID on IpcConfiguration(tenantID);

			CREATE TABLE ClientInstallations2 (
					userName       varchar(168) NOT NULL,
					displayName    varchar(128),
					tenantName     varchar(128),
					EID            varchar(64),
					ipAddress      varchar(64),
					hostName       varchar(64),
					roomName       varchar(128),
					roomOwner      varchar(64),
					timeInstalled  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
					UNIQUE KEY UNIQUE_EID (EID)
					) ENGINE = InnoDB;

			CREATE TABLE IF NOT EXISTS HotStandbySecKeys (
				  ID int(11) unsigned NOT NULL auto_increment,
				  pub_key varchar(8196) NOT NULL,
				  creation_date date NOT NULL,
				  PRIMARY KEY  (ID)
				) ENGINE=InnoDB DEFAULT CHARSET=utf8;

	        -- Table: TenantLdapAttributeMapping
	        CREATE TABLE TenantLdapAttributeMapping (
	          mappingID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
	          tenantID int(10) UNSIGNED DEFAULT '1',
	          vidyoAttributeName varchar(128) NOT NULL,
	          ldapAttributeName varchar(128) NOT NULL,
	          defaultAttributeValue text NOT NULL,
	          creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	          updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	        /* Keys */
	        PRIMARY KEY (mappingID)
	        ) ENGINE = InnoDB
	        COLLATE utf8_general_ci;

	        CREATE UNIQUE INDEX TenantLdapAttributeName
	        ON TenantLdapAttributeMapping
	        (tenantID, vidyoAttributeName);

	        -- Table: TenantLdapAttributeValueMapping
	        CREATE TABLE TenantLdapAttributeValueMapping (
	        valueID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
	        mappingID int(10) UNSIGNED,
	        vidyoValueName varchar(128) NOT NULL,
	        ldapValueName VARCHAR(1024) NOT NULL,
	        creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	        updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	        /* Keys */
	        PRIMARY KEY (valueID)
	        ) ENGINE = InnoDB
	        COLLATE utf8_general_ci;

	        CREATE UNIQUE INDEX TenantLdapAttributeValueName
	        ON TenantLdapAttributeValueMapping
	        (valueID, vidyoValueName);

			-- Table: AvailableDevice
			CREATE TABLE AvailableDevice (
			  availableDeviceID    int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
			  endpointGUID  varchar(64) NOT NULL,
			  availabilityTime    timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
			  /* Keys */
			  PRIMARY KEY (availableDeviceID)
			) ENGINE = InnoDB
			  COLLATE utf8_general_ci;

			CREATE UNIQUE INDEX endpointGUID
			  ON AvailableDevice
			  (endpointGUID);

	    	CREATE TABLE persistent_logins (
	    	  series varchar(64) NOT NULL,
	    	  username varchar(128) NOT NULL,
	    	  tenant_id int(11) NOT NULL DEFAULT '0',
	    	  token varchar(64) NOT NULL,
	    	  endpoint_guid varchar(64) NULL,
	    	  validity_secs int(11) NOT NULL DEFAULT '0',
	    	  creation_time timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
	    	  last_used timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	    	  PRIMARY KEY (series),
	    	  UNIQUE KEY UNIQUE_SERI_USER_TENANT_EID (series,username,tenant_id),
			  KEY idx_guid (endpoint_guid),
  			  KEY idx_tenantid (tenant_id),
  			  KEY idx_username (username)
	    	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

            CREATE TABLE TenantIdpAttributeMapping (
              mappingID              int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
              tenantID               int(10) UNSIGNED DEFAULT '1',
              vidyoAttributeName     varchar(128) NOT NULL,
              idpAttributeName      varchar(128) NOT NULL,
              defaultAttributeValue  text NOT NULL,
	          creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	          updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
              /* Keys */
             PRIMARY KEY (mappingID)
           ) ENGINE = InnoDB COLLATE utf8_general_ci;

           CREATE UNIQUE INDEX TenantIdpAttributeName
             ON TenantIdpAttributeMapping
             (tenantID, vidyoAttributeName);

           CREATE TABLE TenantIdpAttributeValueMapping (
             valueID         int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
             mappingID       int(10) UNSIGNED,
             vidyoValueName  varchar(128) NOT NULL,
             idpValueName    VARCHAR(1024) NOT NULL,
	         creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	         updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
             /* Keys */
             PRIMARY KEY (valueID)
           ) ENGINE = InnoDB DEFAULT CHARSET=utf8;

           CREATE UNIQUE INDEX TenantIdpAttributeValueName
             ON TenantIdpAttributeValueMapping
             (valueID, vidyoValueName);

			COMMIT;


            INSERT INTO Configuration (configurationID, tenantID, configurationName, configurationValue,
            configDate) VALUES (3, 1, 'SystemLanguage', 'en', '2009-01-26 12:03:00');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (1, 'DBVersion', "$DB_VERSION");
	    INSERT INTO Configuration (tenantID, configurationName, configurationValue,configDate) VALUES (1, 'NumInstalls', '5', CURRENT_TIMESTAMP);
	    INSERT INTO Configuration (tenantID, configurationName, configurationValue,configDate) VALUES (1, 'NumSeats', '5', CURRENT_TIMESTAMP);
	    INSERT INTO Configuration (tenantID, configurationName, configurationValue,configDate) VALUES (1, 'NumPorts', '2', CURRENT_TIMESTAMP);
            INSERT INTO Configuration (tenantID, configurationName, configurationValue,configDate) VALUES (1, 'GuestGroupID', '1', CURRENT_TIMESTAMP);

            INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'CREATE_PUBLIC_ROOM_FLAG', '1', NOW(), NOW());
            INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'MAX_CREATE_PUBLIC_ROOM', '100000', NOW(), NOW());
            INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'MAX_CREATE_PUBLIC_ROOM_USER', '5', NOW(), NOW());
            INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'SELECTED_MAX_ROOM_EXT_LENGTH', '12', NOW(), NOW());
            INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'SCHEDULED_ROOM_BATCH_LIMIT', '1000', NOW(), NOW());
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'ROOM_KEY_LENGTH', '10');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'ROOM_LINK_FORMAT', 'join');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'MANAGE_ENDPOINT_UPLOAD_MODE', 'VidyoPortal');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'CDN_OPTIONAL_PARAMETER', 'response-content-disposition');
            COMMIT;

            INSERT INTO Tenant (tenantName, tenantUrl, tenantPrefix ) values ('Default', 'Default', '');
            INSERT INTO TenantXcall (tenantID, callTo) VALUES (1, 1);
            INSERT INTO Routers (routerID, routerName, description) VALUES (1, 'Default', 'Default affinity');
	        INSERT INTO TenantXrouter (tenantID, routerID) VALUES (1, 1);
            COMMIT;

            INSERT INTO Groups (groupID, tenantID, routerID, groupName, groupDescription, defaultFlag, roomMaxUsers,
            userMaxBandWidthIn, userMaxBandWidthOut) VALUES (1, 1, 1, 'Default', 'Default Group', 1, 10, 10000, 10000);
            COMMIT;

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

            INSERT INTO MemberRole (roleID, roleName, roleDescription) VALUES (1, 'Admin', 'Admin');
            INSERT INTO MemberRole (roleID, roleName, roleDescription) VALUES (2, 'Operator', 'Operator');
            INSERT INTO MemberRole (roleID, roleName, roleDescription) VALUES (3, 'Normal', 'Normal');
            INSERT INTO MemberRole (roleID, roleName, roleDescription) VALUES (4, 'VidyoRoom', 'VidyoRoom');
            INSERT INTO MemberRole (roleID, roleName, roleDescription) VALUES (5, 'Super', 'Super');
            INSERT INTO MemberRole (roleID, roleName, roleDescription) VALUES (6, 'Legacy', 'Legacy Device');
            INSERT INTO MemberRole (roleID, roleName, roleDescription) VALUES (7, 'Executive', 'Executive');
	    INSERT INTO MemberRole (roleID, roleName, roleDescription) VALUES (8, 'VidyoPanorama', 'VidyoPanorama');
            COMMIT;

            INSERT INTO RoomType (roomTypeID, roomType, roomTypeDescription) VALUES (1, 'Personal', 'personal
            room');
            INSERT INTO RoomType (roomTypeID, roomType, roomTypeDescription) VALUES (2, 'Public', 'public
            room');
            INSERT INTO RoomType (roomTypeID, roomType, roomTypeDescription) VALUES (3, 'Legacy', 'Legacy
            Device');
            COMMIT;
            
            INSERT INTO Locations (locationID, locationTag) VALUES (1, 'Default');
            INSERT INTO TenantXlocation (tenantID, locationID) VALUES (1, 1);            

            COMMIT;

            INSERT INTO Member (memberID, roleID, tenantID, langID, profileID, username, password,
            memberName, active, emailAddress, memberCreated, location, description) VALUES (1, 1, 1, 1, 0,
            'admin', '1000:96b57f70cb37e6c356c98a8f1beaeab08d6e420a51070f622c318644e14f5a01:e6ebc01b90e7f18eb42794a5df4f4b4ce920a2293c3796b89e8da36ad8f1b113', 'AdminFirst AdminLast', 1, 'admin@example.com',
            UNIX_TIMESTAMP(), 1, 'Default Admin');

            INSERT INTO Room (roomID, roomTypeID, memberID, groupID, roomName, roomExtNumber,
                              roomDescription, roomPIN, roomLocked, roomEnabled, roomKey, displayName)
            VALUES (1, 1, 1, 1, 'admin', '1234', 'Admin- Personal Room', NULL, 0, 1, NULL, 'admin');            
            
            COMMIT;

            INSERT INTO Member (memberID, roleID, tenantID, langID, profileID, username, password,
            memberName, active, emailAddress, memberCreated, location, description) VALUES (2, 5, 1, 1, 0,
            'super', '1000:96b57f70cb37e6c356c98a8f1beaeab08d6e420a51070f622c318644e14f5a01:e6ebc01b90e7f18eb42794a5df4f4b4ce920a2293c3796b89e8da36ad8f1b113', 'SuperAdminFirst SuperAdminLast', 1, 'super@example.com',
            UNIX_TIMESTAMP(), NULL, 'Default Super Admin');
            COMMIT;

            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (1, 'PortalVersion', '$VER');
			INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'INTERNAL_VERSION', '$INTERNAL_VER');

            INSERT INTO MemberMode (modeID, modeName, modeDescription) VALUES (1, 'Available', 'Member mode - Available');
            INSERT INTO MemberMode (modeID, modeName, modeDescription) VALUES (2, 'Away', 'Member mode - Away');
            INSERT INTO MemberMode (modeID, modeName, modeDescription) VALUES (3, 'DoNotDisturb', 'Member mode - Do Not Disturb');

            INSERT INTO IpcConfiguration (tenantID) SELECT tenantID FROM Tenant;
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'ipcAllowDomainFlag', '0');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'ipcSuperManaged', '0');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'AUTH_TOKEN_INACTIVITY_LIMIT', '7776000');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate) values (0, 'SCHEDULED_ROOM_INACTIVE_LIMIT', '100',  CURDATE());

            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'chatAvailableFlag', 'true');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'publicChatEnabledDefaultFlag', 'true');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'privateChatEnabledDefaultFlag', 'true');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'ScheduledRoom2', '1');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) values (0, 'MINIMUM_PIN_LENGTH_SUPER', '6');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SESSION_EXP_PERIOD', '720');
            INSERT INTO Configuration (tenantID, configurationName, configurationValue,configDate) VALUES (0, 'HEARTBEAT_EXPIRY_SECONDS', '30', CURRENT_TIMESTAMP);

            INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'USER_IMAGE', '1', NOW(), NOW());
            INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'UPLOAD_USER_IMAGE', '0', NOW(), NOW());
            INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'MAX_USER_IMAGE_SIZE_KB', '100', NOW(), NOW());


            CREATE INDEX idx_memberIDroomTypeIDroomEnabled ON Room (memberID, roomTypeID, roomEnabled);
            CREATE INDEX idx_groupID ON Room(groupID);
            CREATE INDEX idx_roomTypeID ON Room(roomTypeID);
            CREATE INDEX idx_langID ON Member(langID);
            CREATE INDEX idx_modeID ON Member(modeID);
            CREATE INDEX idx_memberID ON Endpoints(memberID);
            CREATE INDEX idx_roomID ON Conferences(roomID);
            CREATE INDEX idx_endpointID ON Conferences(endpointID);
            CREATE INDEX idx_createdTime ON Conferences (createdTime);
            CREATE FULLTEXT INDEX idx_fulltext_membername ON Member(memberName);
	        CREATE FULLTEXT INDEX idx_fulltext_Mem_memname_username ON Member(membername, username);
	        CREATE FULLTEXT INDEX idx_fulltext_Mem_memname_emailaddress ON Member(membername, emailaddress);
            CREATE INDEX idx_proxyId ON Member (proxyID);
            CREATE INDEX idx_tenantId ON Member (tenantID);
            CREATE INDEX idx_roleId ON Member (roleID);
            CREATE INDEX idx_locationId ON Member (locatiONID);
            CREATE INDEX idx_tenantId ON Groups (tenantID);
            CREATE INDEX idx_bak ON Member (bak);
            

    INSERT INTO Profile (profileID, profileName, profileDescription, profileXML) VALUES
					(1, 'NoAudioAndVideo', 'Disable audio and video streaming', '<Profile><AudioEnabled>false</AudioEnabled><VideoEnabled>false</VideoEnabled></Profile>'),
					(2, 'AudioOnly', 'Audio streaming available only', '<Profile><AudioEnabled>true</AudioEnabled><VideoEnabled>false</VideoEnabled></Profile>'),
					(3, 'VideoOnly', 'Video stream available only', '<Profile><AudioEnabled>false</AudioEnabled><VideoEnabled>true</VideoEnabled></Profile>');
    INSERT INTO RoomType (roomType,roomTypeDescription) VALUES ('Scheduled','Scheduled');

    CREATE TABLE IF NOT EXISTS MemberLoginHistory (
          ID int(11) NOT NULL auto_increment,
          memberID int(11) NOT NULL,
          transactionName varchar(20) NOT NULL,
          transactionResult varchar(50) NOT NULL,
          sourceIP varchar(120) NOT NULL,
          transactionTime timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
          failureReason varchar(50) default NULL,
          PRIMARY KEY  (ID),
          KEY idx_mlh_memberID (memberID)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    CREATE TABLE IF NOT EXISTS MemberPasswordHistory (
          memberID    int(10) NOT NULL,
          password    varchar(256) NOT NULL,
          changeTime  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
        ) ENGINE = InnoDB COLLATE utf8_general_ci ;


    CREATE INDEX MemberPasswordHistory_Index01
          ON MemberPasswordHistory
          (memberID);

    CREATE TABLE VidyoProxyAddressMap (
          addrMapID   int(10) AUTO_INCREMENT NOT NULL,
          localAddr   varchar(15) NOT NULL,
          remoteAddr  varchar(15) NOT NULL,
          PRIMARY KEY (addrMapID),
          UNIQUE KEY  id_box_elements (localAddr, remoteAddr)
     ) ENGINE = InnoDB;

    create table GatewayPrefixes (
       prefixID int(10) not null auto_increment primary key,
       serviceID int(10) not null default 0,
       tenantID int(10) not null default 1,
       gatewayID varchar(40) not null default '',
       prefix varchar(32) not null default 'DEFAULT',
       direction int(1) not null default 0,
       creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updateTime timestamp default current_timestamp on update current_timestamp,
       unique key id_prefix_dir (serviceID, prefix, direction)
   ) ENGINE=InnoDB COLLATE utf8_general_ci ;

    CREATE TABLE TenantConfiguration (
       tenantID int(10) UNSIGNED AUTO_INCREMENT NOT NULL,
       endpointPrivateChat int(1) DEFAULT '1' NOT NULL,
       endpointPublicChat int(1) DEFAULT '1' NOT NULL,
       zincUrl varchar(255) DEFAULT NULL,
       zincEnabled int(1) DEFAULT '0' NOT NULL,
       waitingRoomsEnabled tinyint(1) NOT NULL DEFAULT '0',
       waitUntilOwnerJoins tinyint(1) NOT NULL DEFAULT '0',
       lectureModeStrict tinyint(1) NOT NULL DEFAULT '0',
       minPINLength tinyint(1) NOT NULL DEFAULT '3',
       sessionExpirationPeriod int(11) NOT NULL DEFAULT 0,
       creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       updateTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       createPublicRoomEnable INT(1) NOT NULL DEFAULT 0,
       maxCreatePublicRoomUser int(11) NOT NULL DEFAULT 0,
       maxCreatePublicRoomTenant int(11) NOT NULL DEFAULT -1,
       minMediaPort smallint(5) unsigned NOT NULL DEFAULT 0,
       maxMediaPort smallint(5) unsigned NOT NULL DEFAULT 0,
       alwaysUseProxy tinyint(1) NOT NULL DEFAULT -1,
       lectureModeAllowed tinyint(1) NOT NULL DEFAULT 1,
       userImage TINYINT(1) NOT NULL DEFAULT 0,
       uploadUserImage TINYINT(1) NOT NULL DEFAULT 0,
       vidyoNeoWebRTCGuestEnabled TINYINT(1) NOT NULL DEFAULT 1,
       vidyoNeoWebRTCUserEnabled TINYINT(1) NOT NULL DEFAULT 0,
       logAggregation TINYINT(1) NOT NULL DEFAULT 0,
       endpointUploadMode VARCHAR(14) DEFAULT 'VidyoPortal',
       endpointBehavior TINYINT(1) NOT NULL DEFAULT 0,
       personalRoom TINYINT(1) NOT NULL DEFAULT 0,
       testCall TINYINT(1) NOT NULL DEFAULT 0,
       mobileProtocol VARCHAR(64) DEFAULT NULL,
       androidPackageName VARCHAR(256) DEFAULT NULL,
       iOSBundleId VARCHAR(256) DEFAULT NULL,
       iOSAppId VARCHAR(256) DEFAULT NULL,
       iOSAppLink VARCHAR(1024) DEFAULT NULL,
       androidAppLink VARCHAR(1024) DEFAULT NULL,
       externalIntegrationMode int(11) NOT NULL DEFAULT 0,
       extIntegrationSharedSecret VARCHAR(256) DEFAULT NULL,
       externalNotificationUrl VARCHAR(2048) DEFAULT NULL,
       externalUsername VARCHAR(256) DEFAULT NULL,
       externalPassword VARCHAR(256) DEFAULT NULL,
       vidyoNotificationUrl VARCHAR(2048) DEFAULT NULL,
       vidyoUsername VARCHAR(256) DEFAULT NULL,
       vidyoPassword VARCHAR(256) DEFAULT NULL,
       desktopProtocol VARCHAR(64) DEFAULT NULL,
       tc VARCHAR(2048) DEFAULT NULL,
       tcVersion INT(8) DEFAULT 1,
       pp VARCHAR(2048) DEFAULT NULL,
       ppVersion INT(8) DEFAULT 1,
       tytoIntegrationMode int(11) NOT NULL DEFAULT 0,
       tytoUrl VARCHAR(2048) DEFAULT NULL,
       tytoUsername VARCHAR(256) DEFAULT NULL,
       tytoPassword VARCHAR(256) DEFAULT NULL,     
       /* Keys */
      PRIMARY KEY (tenantID)
    ) ENGINE=InnoDB COLLATE utf8_general_ci;

    CREATE TABLE ExternalStatusNotification  (
       notificationId int(10) UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
       tenantID int(10) NOT NULL,
       status VARCHAR(10) DEFAULT 'Active',
       url VARCHAR(256) DEFAULT NULL,
       username VARCHAR(256) DEFAULT NULL,
       password VARCHAR(256) DEFAULT NULL,
       data VARCHAR(2048) DEFAULT NULL,
       dataType INT(11) DEFAULT '0',
       retry INT(1) DEFAULT 0,
       errorMessage VARCHAR(256) DEFAULT NULL,
       creationTime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
    ) ENGINE=InnoDB COLLATE utf8_general_ci;

    INSERT TenantConfiguration (tenantID, endpointPrivateChat, endpointPublicChat, sessionExpirationPeriod, maxCreatePublicRoomTenant)
    SELECT t.tenantID, 1, 1, 720, 100000
    FROM Tenant t
    WHERE t.tenantID NOT IN (SELECT tc.tenantID FROM TenantConfiguration tc);

### added in 3.4.0
    CREATE TABLE cloud_config (
       ID int(11) NOT NULL AUTO_INCREMENT,
       VERSION int(11) NOT NULL,
       STATUS varchar(45) NOT NULL,
       CONFIG_XML mediumtext,
       CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (ID)
    ) ENGINE=InnoDB;

    CREATE TABLE components_type (
       ID int(11) unsigned NOT NULL AUTO_INCREMENT,
       NAME varchar(128) NOT NULL,
       DESCRIPTION varchar(150) DEFAULT NULL,
       CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (ID),
       UNIQUE KEY ukName (NAME)
    ) ENGINE=InnoDB;

    CREATE TABLE components (
       ID int(11) NOT NULL AUTO_INCREMENT,
       COMP_ID varchar(64) NOT NULL,
       COMP_TYPE_ID int(11) unsigned NOT NULL,
       NAME varchar(256) NOT NULL,
       LOCAL_IP varchar(256) NOT NULL,
       CLUSTER_IP varchar(256) DEFAULT NULL,
       MGMT_URL varchar(256) DEFAULT NULL,
       RUNNING_VERSION int(8) DEFAULT NULL,
       CONFIG_VERSION int(8) DEFAULT NULL,
       ALARM text,
       COMP_SOFTWARE_VERSION varchar(256) DEFAULT NULL,
       STATUS enum('ACTIVE','INACTIVE','NEW') DEFAULT NULL,
       HEARTBEAT_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       COMP_PASSWORD varchar(40) DEFAULT NULL,
       CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (ID),
       UNIQUE KEY UK_COMP_ID_COMP_TYPE (COMP_ID, COMP_TYPE_ID),
       KEY FK_COMP_TYID_COMPTY_ID (COMP_TYPE_ID),
       CONSTRAINT FK_COMP_TYID_COMPTY_ID FOREIGN KEY (COMP_TYPE_ID) REFERENCES components_type (ID)
    ) ENGINE=InnoDB;

    CREATE TABLE vidyo_manager_config (
       ID int(11) NOT NULL AUTO_INCREMENT,
       COMP_ID int(11) NOT NULL,
       EMCP_PORT int(11) NOT NULL,
       RMCP_PORT int(11) NOT NULL,
       FQDN varchar(256) DEFAULT NULL,
       SOAP_PORT int(11) NOT NULL,
       DSCP int(11) NOT NULL,
       USERNAME varchar(256) DEFAULT NULL,
       PASSWORD varchar(256) DEFAULT NULL,
       CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (ID),
       KEY FK_VMC_CID_COMP_ID (COMP_ID),
       CONSTRAINT FK_VMC_CID_COMP_ID FOREIGN KEY (COMP_ID) REFERENCES components (ID)
    ) ENGINE=InnoDB;

    CREATE TABLE vidyo_router_config (
       ID int(11) NOT NULL AUTO_INCREMENT,
       COMP_ID int(11) NOT NULL,
       SCIP_FQDN varchar(256) DEFAULT NULL,
       SCIP_PORT int(11) NOT NULL,
       MEDIA_PORT_START int(11) NOT NULL,
       MEDIA_PORT_END int(11) NOT NULL,
       STUN_FQDN_IP varchar(256) DEFAULT NULL,
       STUN_PORT int(11) DEFAULT NULL,
       DSCP_VIDEO int(11) DEFAULT NULL,
       AUDIO_DSCP int(11) DEFAULT NULL,
       CONTENT_DSCP int(11) DEFAULT NULL,
       SIGNALING_DSCP int(11) DEFAULT NULL,
       PROXY_ENABLED int(1) NOT NULL DEFAULT '1',
       PROXY_USE_TLS int(1) NOT NULL DEFAULT '0',
       CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (ID),
       KEY FK_VRC_CID_COMP_ID (COMP_ID),
       CONSTRAINT FK_VRC_CID_COMP_ID FOREIGN KEY (COMP_ID) REFERENCES components (ID)
    ) ENGINE=InnoDB;

    CREATE TABLE vidyo_gateway_config (
	  ID int(11) NOT NULL AUTO_INCREMENT,
	  COMP_ID int(11) NOT NULL,
	  USERNAME varchar(255) DEFAULT NULL,
	  PASSWORD varchar(256) DEFAULT NULL,
	  SERVICE_ENDPOINT_GUID varchar(64) DEFAULT NULL,
	  TOKEN varchar(64) DEFAULT NULL,
	  SERVICE_REF varchar(40) DEFAULT NULL,
      CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	  PRIMARY KEY (ID),
     UNIQUE KEY UK_USERNAME ( USERNAME ),
	  KEY FK_VGC_CID_COMP_ID (COMP_ID),
	  CONSTRAINT FK_VGC_CID_COMP_ID FOREIGN KEY (COMP_ID) REFERENCES components (ID)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    CREATE TABLE vidyo_recorder_config (
      ID int(11) NOT NULL AUTO_INCREMENT,
      COMP_ID int(11) NOT NULL,
      USERNAME varchar(255) DEFAULT NULL,
      PASSWORD varchar(256) DEFAULT NULL,
      CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (ID),
     UNIQUE KEY UK_USERNAME ( USERNAME ),
      KEY FK_VRCC_CID_COMP_ID (COMP_ID),
      CONSTRAINT FK_VRCC_CID_COMP_ID FOREIGN KEY (COMP_ID) REFERENCES components (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;

    CREATE TABLE vidyo_replay_config (
      ID int(11) NOT NULL AUTO_INCREMENT,
      COMP_ID int(11) NOT NULL,
      USERNAME varchar(255) DEFAULT NULL,
      PASSWORD varchar(256) DEFAULT NULL,
      CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      PRIMARY KEY (ID),
      UNIQUE KEY UK_USERNAME ( USERNAME ),
      KEY FK_VRPC_CID_COMP_ID (COMP_ID),
      CONSTRAINT FK_VRPC_CID_COMP_ID FOREIGN KEY (COMP_ID) REFERENCES components (ID)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;


    CREATE TABLE router_media_addr_map (
       ID int(11) unsigned NOT NULL AUTO_INCREMENT,
       ROUTER_CONFIG_ID int(128) NOT NULL,
       LOCAL_IP varchar(128) NOT NULL,
       REMOTE_IP varchar(128) NOT NULL,
       CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (ID),
       KEY FK_RMAM_RID_VRC_ID (ROUTER_CONFIG_ID),
       CONSTRAINT FK_RMAM_RID_VRC_ID FOREIGN KEY (ROUTER_CONFIG_ID) REFERENCES vidyo_router_config (ID)
    ) ENGINE=InnoDB ;

    CREATE TABLE pool (
	  ID int(11) NOT NULL AUTO_INCREMENT,
	  POOL_NAME varchar(40) NOT NULL,
	  X int(11) NOT NULL,
	  Y int(11) NOT NULL,
	  CLOUD_CONFIG_ID int(11) NOT NULL,
	  CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	  UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	  PRIMARY KEY (ID,CLOUD_CONFIG_ID),
	  KEY FK_PL_CCID_CC_ID (CLOUD_CONFIG_ID),
	  CONSTRAINT FK_PL_CCID_CC_ID FOREIGN KEY (CLOUD_CONFIG_ID) REFERENCES cloud_config (ID) ON DELETE CASCADE
) ENGINE=InnoDB;

    CREATE TABLE priority_list (
       ID int(11) NOT NULL AUTO_INCREMENT,
       PRIORITY_LIST_NAME varchar(128) NOT NULL,
       CLOUD_CONFIG_ID int(11) DEFAULT NULL,
       CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
       UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       PRIMARY KEY (ID),
       KEY FK_PPl_CCID_CC_ID (CLOUD_CONFIG_ID),
       CONSTRAINT FK_PPl_CCID_CC_ID FOREIGN KEY (CLOUD_CONFIG_ID) REFERENCES cloud_config (ID)
    ) ENGINE=InnoDB;

    CREATE TABLE pool_priority_map (
	  PRIORITY_LIST_ID int(128) NOT NULL,
	  POOL_ORDER int(11) NOT NULL,
	  POOL_ID int(11) NOT NULL,
	  CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	  UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	  CLOUD_CONFIG_ID int(11) NOT NULL,
	  PRIMARY KEY (POOL_ID,PRIORITY_LIST_ID,CLOUD_CONFIG_ID),
	  KEY FK_PPM_PRID_PPL_ID (PRIORITY_LIST_ID),
	  KEY FK_PPM_PLID_CCID (POOL_ID,CLOUD_CONFIG_ID),
	  CONSTRAINT FK_PPM_PLID_CCID FOREIGN KEY (POOL_ID, CLOUD_CONFIG_ID) REFERENCES pool (ID, CLOUD_CONFIG_ID) ON DELETE CASCADE,
	  CONSTRAINT FK_PPM_PRID_PPL_ID FOREIGN KEY (PRIORITY_LIST_ID) REFERENCES priority_list (ID)
	) ENGINE=InnoDB;

    CREATE TABLE pool_to_pool (
	  POOL1 int(11) NOT NULL,
	  POOL2 int(11) NOT NULL,
	  WEIGHT int(11) NOT NULL,
	  DIRECTION int(11) NOT NULL,
	  CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	  UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	  CLOUD_CONFIG_ID int(11) NOT NULL,
	  PRIMARY KEY (POOL1,POOL2,CLOUD_CONFIG_ID),
	  KEY FK_PTP_PID1_PL_ID (POOL1,CLOUD_CONFIG_ID),
	  KEY FK_PTP_PID2_PL_ID (POOL2,CLOUD_CONFIG_ID),
	  CONSTRAINT FK_PTP_PID1_PL_ID FOREIGN KEY (POOL1, CLOUD_CONFIG_ID) REFERENCES pool (ID, CLOUD_CONFIG_ID) ON DELETE CASCADE,
	  CONSTRAINT FK_PTP_PID2_PL_ID FOREIGN KEY (POOL2, CLOUD_CONFIG_ID) REFERENCES pool (ID, CLOUD_CONFIG_ID) ON DELETE CASCADE
	) ENGINE=InnoDB;

    CREATE TABLE router_pool_map (
	  ID int(11) NOT NULL AUTO_INCREMENT,
	  POOL_ID int(11) NOT NULL,
	  VR_ID int(128) NOT NULL,
	  CLOUD_CONFIG_ID int(11) NOT NULL,
	  CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	  UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	  PRIMARY KEY (ID),
	  KEY FK_RPM_VRID_VRC_ID (VR_ID),
	  KEY FK_RPM_PID_PLCCID (POOL_ID,CLOUD_CONFIG_ID),
	  CONSTRAINT FK_RPM_PID_PLCCID FOREIGN KEY (POOL_ID, CLOUD_CONFIG_ID) REFERENCES pool (ID, CLOUD_CONFIG_ID) ON DELETE CASCADE,
	  CONSTRAINT FK_RPM_VRID_VRC_ID FOREIGN KEY (VR_ID) REFERENCES vidyo_router_config (ID)
	) ENGINE=InnoDB;

    CREATE TABLE rules (
	  ID int(10) unsigned NOT NULL AUTO_INCREMENT,
	  POOL_PRIORITY_LIST_ID int(11) NOT NULL,
	  RULE_NAME varchar(128) NOT NULL,
	  RULE_ORDER int(10) NOT NULL,
	  CLOUD_CONFIG_ID int(11) DEFAULT NULL,
	  CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	  UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	  PRIMARY KEY (ID),
	  KEY FK_RUL_CCID_CC_ID (CLOUD_CONFIG_ID),
	  KEY FK_RUL_PPLID_PL_ID (POOL_PRIORITY_LIST_ID),
	  CONSTRAINT FK_RUL_CCID_CC_ID FOREIGN KEY (CLOUD_CONFIG_ID) REFERENCES cloud_config (ID),
	  CONSTRAINT FK_RUL_PPLID_PL_ID FOREIGN KEY (POOL_PRIORITY_LIST_ID) REFERENCES priority_list (ID) ON DELETE CASCADE
	) ENGINE=InnoDB;


    CREATE TABLE ruleset (
	  ID int(10) unsigned NOT NULL AUTO_INCREMENT,
	  RULE_ID int(10) unsigned NOT NULL,
	  RULESET_ORDER int(10) DEFAULT NULL,
	  PRIVATE_IP varchar(20) DEFAULT NULL,
	  PRIVATE_IP_CIDR int(11) DEFAULT NULL,
	  PUBLIC_IP varchar(20) DEFAULT NULL,
	  PUBLIC_IP_CIDR int(11) DEFAULT NULL,
	  LOCATION_TAG_ID int(11) DEFAULT NULL,
	  ENDPOINT_ID varchar(150) DEFAULT NULL,
	  CREATION_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
	  UPDATE_TIME timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	  PRIMARY KEY (ID),
	  KEY FK_RST_RID_RUL_ID (RULE_ID),
	  CONSTRAINT FK_RST_RID_RUL_ID FOREIGN KEY (RULE_ID) REFERENCES rules (ID) ON DELETE CASCADE
	) ENGINE=InnoDB;

#### Added Start 3.5.0

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
     ) ENGINE = InnoDB COLLATE utf8_general_ci;

    CREATE TABLE IF NOT EXISTS temp_saml_auth_token (
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

     INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'OPUS_AUDIO', '1');

     INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'COUNTRYFLAG_IMAGE_PATH', '/upload/');

     INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'WEBRTC_TEST_MEDIA_SERVER', '');
     INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'WEBRTC_TEST_CALL_USERNAME', '');

     INSERT INTO Tenant (tenantID, tenantName) VALUES (0, 'Super Tenant');

     UPDATE Tenant set tenantID = 0 WHERE tenantName = 'Super Tenant';


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

	 INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'SDK220', '1');
	 INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'VP9', '0');
	 INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'MOBILE_LOGIN_MODE', '1');

    INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (1, 'VidyoManager','VidyoManager',now(), now());
    INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (2, 'VidyoRouter','VidyoRouter',now(), now());
    INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (3, 'VidyoRecorder','VidyoRecorder',now(), now());
    INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (4, 'VidyoReplay','VidyoReplay',now(), now());
    INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (5, 'VidyoGateway','VidyoGateway',now(), now());
    INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (6, 'VidyoAAMicroservice', 'VidyoAAMicroservice', now(), now());
	 INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (7, 'VidyoRegistrationMicroservice', 'VidyoRegistrationMicroservice', now(), now());
	 INSERT INTO components_type (id,name,description,creation_time, update_time) VALUES (8, 'VidyoPairingMicroservice', 'VidyoPairingMicroservice', now(), now());
    INSERT INTO cloud_config (VERSION, STATUS, CREATION_TIME) VALUES (0,'ACTIVE',now());

    CREATE TABLE IF NOT EXISTS user_group (
        user_group_id int(10) unsigned NOT NULL auto_increment,
        tenant_id int(10) unsigned NOT NULL,
        name varchar(255) NOT NULL,
        description varchar(255),
        creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        update_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        PRIMARY KEY (user_group_id),
        UNIQUE KEY uk_tenant_id_name (tenant_id,name),
        KEY idx_name (name),
        KEY idx_tenant_id (tenant_id),
        CONSTRAINT fk_ug_tid_tt_id FOREIGN KEY (tenant_id) REFERENCES Tenant (tenantID)
    ) ENGINE=InnoDB COLLATE utf8_general_ci;
                                                                    
    CREATE TABLE IF NOT EXISTS room_user_group (
        room_id int(10) unsigned NOT NULL,
        user_group_id int(10) unsigned NOT NULL,
        creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (room_id,user_group_id),
        KEY idx_user_group_id (user_group_id),
        KEY idx_room_id (room_id),
        CONSTRAINT fk_rug_rid_rm_id FOREIGN KEY (room_id) REFERENCES Room (roomID),
        CONSTRAINT fk_rug_ugid_ug_ugid FOREIGN KEY (user_group_id) REFERENCES user_group (user_group_id)
    )  ENGINE=InnoDB COLLATE utf8_general_ci;
                                                                                                                
    CREATE TABLE IF NOT EXISTS member_user_group (
        member_id int(10) unsigned NOT NULL,
        user_group_id int(10) unsigned NOT NULL,
        creation_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (member_id,user_group_id),
        KEY idx_user_group_id (user_group_id),
        KEY idx_member_id (member_id),
        CONSTRAINT fk_mug_mid_mem_id FOREIGN KEY (member_id) REFERENCES Member (memberID),
        CONSTRAINT fk_mug_ugid_ug_ugid FOREIGN KEY (user_group_id) REFERENCES user_group (user_group_id)
    ) ENGINE=InnoDB COLLATE utf8_general_ci;

    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'MAX_PERMITTED_USER_GROUPS_COUNT', '1000');
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'MAX_USER_GROUPS_IMPORTED_PER_USER', '100');
    
    ALTER TABLE cloud_config ADD INDEX idx_status (status);
    ALTER TABLE VirtualEndpoints ADD INDEX idx_display_ext (displayExt);
    ALTER TABLE VirtualEndpoints ADD INDEX idx_display_name (displayName);
    ALTER TABLE VirtualEndpoints ADD INDEX idx_status (status);
    ALTER TABLE Conferences ADD INDEX idx_guid (GUID);
    ALTER TABLE Conferences ADD INDEX idx_conference_name (conferenceName);
    ALTER TABLE Conferences ADD INDEX idx_conference_type (conferenceType);
    ALTER TABLE ConferenceRecord ADD INDEX idx_conference_name (conferenceName);
    ALTER TABLE ExternalLinks ADD INDEX idx_to_conference_name (toConferenceName);
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
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'EPIC_INTEGRATION_SUPPORT', 0);
    INSERT INTO Configuration (tenantID, configurationName, configurationValue) VALUES (0, 'TYTOCARE_INTEGRATION_SUPPORT', 0);

    ALTER TABLE Room ADD COLUMN externalRoomID VARCHAR(128) DEFAULT NULL;
    CREATE UNIQUE INDEX externalRoomID ON Room (externalRoomID);

    COMMIT;

     -- -----------------------------------------------------
     -- END database portal2
     --
     -- START database portal2audit
     -- ----------------------------------------------------
			USE mysql ;
			CREATE DATABASE IF NOT EXISTS \`$DB_AUDIT_NAME\` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
			GRANT  SELECT, INSERT, DELETE, UPDATE, CREATE TEMPORARY TABLES ON portal2audit.* TO 'vidyo'@'localhost' IDENTIFIED BY '$VIDYO';
			--GRANT ALL ON \`$DB_AUDIT_NAME\`.* TO 'vidyo'@'localhost' IDENTIFIED BY '$VIDYO';
			FLUSH PRIVILEGES;

			ALTER DATABASE \`$DB_AUDIT_NAME\` CHARACTER SET utf8 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT
			COLLATE utf8_general_ci;
			COMMIT;

			USE \`$DB_AUDIT_NAME\`;
			SET NAMES utf8;

			CREATE TABLE IF NOT EXISTS TransactionHistory (
			  transactionID int(11) NOT NULL auto_increment,
			  userID varchar(128) NOT NULL,
			  tenantName varchar(128) NOT NULL,
			  transactionName varchar(200) NOT NULL,
			  transactionResult varchar(50) NOT NULL,
			  transactionTime timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
			  sourceIP varchar(45) NOT NULL,
			  transactionParams varchar(2000) NOT NULL,
			  PRIMARY KEY  (transactionID)
			) ENGINE=MyISAM DEFAULT CHARSET=utf8;
    -- -------------------------------------------------------
    -- END database portal2audit


QUIT
EOF

   if [ -f /opt/vidyo/Vendor/VIDYO_CLOUD ]; then
        mysql $MYSQL_OPT -u root << EOF
        USE \`$DB_NAME\`;
        DELETE FROM Configuration WHERE configurationName='ENABLE_PERSONAL_ROOM_FLAG';
        INSERT INTO  Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'ENABLE_PERSONAL_ROOM_FLAG', 0, now(), now()); 
EOF

   else
        mysql $MYSQL_OPT -u root << EOF
        USE \`$DB_NAME\`;
        DELETE FROM Configuration WHERE configurationName='ENABLE_PERSONAL_ROOM_FLAG';
        INSERT INTO  Configuration (tenantID, configurationName, configurationValue, configDate, updateTime) VALUES (0, 'ENABLE_PERSONAL_ROOM_FLAG', 1, now(), now()); 
        UPDATE TenantConfiguration SET personalRoom = 1;
EOF
   fi


update_2dot23_disa()
{

   mysql $MYSQL_OPT -uroot << EOF

   USE \`$DB_NAME\`;
   SET NAMES utf8;

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (1, 'FORCE_PASSWORD_CHANGE', 1, CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (0, 'PASSWORD_VALIDITY_DAYS', 60, CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (1, 'INACTIVE_DAYS_LIMIT', 35, CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (1, 'USER_LOCKOUT_TIME_LIMT_SECS', 60, CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (1, 'LOGIN_FAILURE_COUNT', 3, CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (0, 'SHOW_FORGOT_PASSWORD_LINK', 'NEVER', CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (1, 'MEMBER_PASSWORD_RULE', 'DISA', CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (1, 'ShowLoginBanner', 'Yes', CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (1, 'ShowWelcomeBanner', 'Yes', CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (1, 'LOGIN_HISTORY_COUNT', 5, CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES(1, 'LoginBannerInfo', ' You are accessing a U.S. Government
        (USG) Information System (IS) that is provided for USG-authorized use only.<br><br>By using this IS (which includes any device attached to this IS), you
        consent to the following conditions:<br><br>The USG routinely intercepts and monitors communications on this IS for purposes including, but not limited to,
        penetration testing, COMSEC monitoring, network operations and defense, personnel misconduct (PM), law enforcement (LE), and counterintelligence (CI)
        investigations.<br><br>At any time, the USG may inspect and seize data stored on this IS.<br><br>Communications using, or data stored on, this IS are not
        private, are subject to routine monitoring, interception, and search, and may be disclosed or used for any USG-authorized purpose.<br><br>This IS includes
        security measures (e.g., authentication and access controls) to protect USG interests - not for your personal benefit or privacy.<br><br>Notwithstanding the
        above, using this IS does not constitute consent to PM, LE, or CI investigative searching or monitoring of the content of privileged communications, or work
        product, related to personal representation or services by attorneys, psychotherapists, or clergy, and their assistants. Such communications and work product
        are private and confidential.<br><br>See User Agreement for details.', CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES(1, 'WelcomeBannerInfo', ' Welcome to VidyoConferencing.', CURDATE());

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (0, 'SHOW_CUSTOMIZE_BANNER', 1, CURDATE());

   COMMIT;

   QUIT
EOF

}

update_2dot23_commercial()
{
    mysql $MYSQL_OPT -uroot << EOF

    USE \`$DB_NAME\`;
    SET NAMES utf8;

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (1, 'FORCE_PASSWORD_CHANGE', 1, CURDATE());

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (0, 'PASSWORD_VALIDITY_DAYS', 0, CURDATE());

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (1, 'INACTIVE_DAYS_LIMIT', 0, CURDATE());

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (1, 'USER_LOCKOUT_TIME_LIMT_SECS', 60, CURDATE());

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (1, 'LOGIN_FAILURE_COUNT', 0, CURDATE());

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (0, 'SHOW_FORGOT_PASSWORD_LINK', 'YES', CURDATE());

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (1, 'MEMBER_PASSWORD_RULE', 'NO_PASSWORD_RULE', CURDATE());

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (1, 'ShowLoginBanner', 'No', CURDATE());

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (1, 'ShowWelcomeBanner', 'No', CURDATE());

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (1, 'LOGIN_HISTORY_COUNT', 5, CURDATE());

     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
            VALUES (0, 'SHOW_CUSTOMIZE_BANNER', 1, CURDATE());

     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
            VALUES(1, 'LoginBannerInfo', ' ', CURDATE());

     INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
            VALUES(1, 'WelcomeBannerInfo', ' ', CURDATE());

     COMMIT;

     QUIT
EOF

}

update_2dot24_disa()
{

   mysql $MYSQL_OPT -uroot << EOF

   USE \`$DB_NAME\`;
   SET NAMES utf8;

   INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
          VALUES (1, 'USER_DEFAULT_PASSWORD', '!1Vidyo2Admin3?', CURDATE());

   COMMIT;

   QUIT
EOF

}

update_2dot24_commercial()
{
    mysql $MYSQL_OPT -uroot << EOF

    USE \`$DB_NAME\`;
    SET NAMES utf8;

    INSERT INTO Configuration (tenantID, configurationName, configurationValue, configDate)
           VALUES (1, 'USER_DEFAULT_PASSWORD', 'password', CURDATE());

    COMMIT;

    QUIT
EOF

}

create_portal_batch_db()
{
    mysql $MYSQL_OPT -uroot << EOF

    DROP DATABASE IF EXISTS PORTAL_BATCH;

    CREATE DATABASE IF NOT EXISTS PORTAL_BATCH DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
    -- GRANT ALL ON PORTAL_BATCH.* TO 'vidyo'@'localhost' IDENTIFIED BY '$VIDYO';
    -- GRANT FILE ON *.* to 'vidyo'@'localhost';
    GRANT  SELECT, INSERT, DELETE, UPDATE, CREATE TEMPORARY TABLES ON PORTAL_BATCH.* TO 'vidyo'@'localhost' IDENTIFIED BY '$VIDYO';

    FLUSH PRIVILEGES;

    ALTER DATABASE PORTAL_BATCH CHARACTER SET utf8 DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT
    COLLATE utf8_general_ci;
    COMMIT;

    USE PORTAL_BATCH;

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


}

create_member_view()
{
    mysql $MYSQL_OPT -uroot << EOF
USE \`$DB_NAME\`;
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

if [ -f /opt/vidyo/Vendor/DISA ]; then
   update_2dot23_disa
   update_2dot24_disa
else
   update_2dot23_commercial
   update_2dot24_commercial
fi

create_member_view
create_portal_batch_db

/opt/vidyo/bin/run_portal_sql.sh

exit 0

DELIMITER $$
SET NAMES utf8;
DROP PROCEDURE IF EXISTS portal2.searchRoomIdsAndTotalCount $$
CREATE PROCEDURE portal2.searchRoomIdsAndTotalCount (
 IN startRec INTEGER,
 IN limitRec INTEGER,
 IN tenantIds VARCHAR(4096),
 IN roomType INTEGER,
 IN includeRoomType INTEGER,
 IN active INTEGER,
 IN allowed INTEGER,
 IN roomEnabled INTEGER,
 IN query VARCHAR(200),
 IN sortOrder VARCHAR(64),
 IN memberId INTEGER)
 BEGIN
 SET @startRec := startRec;
 SET @limitRec := limitRec;
 SET @tenantIds := tenantIds;
 SET @roomType := roomType;
 SET @includeRoomType := includeRoomType;
 SET @active := active;
 SET @allowed := allowed;
 SET @roomEnabled := roomEnabled;
 SET @query := query;
 SET @sortOrder := sortOrder;
 SET @memberId := memberId;
 
 DROP TABLE IF EXISTS search_member_room;
 
 CREATE TEMPORARY TABLE IF NOT EXISTS search_member_room (roomID INT(10), roomExtNumber varchar(64), Name varchar(128), PRIMARY KEY (roomID)) engine=MyISAM;
 
 SET @PART1 = 'INSERT IGNORE INTO search_member_room SELECT roomID, roomExtNumber, CASE WHEN roomTypeID = 1 OR roomTypeID = 3 THEN memberName ELSE roomName END as MName FROM (SELECT rm.roomID, rm.roomTypeID, rm.roomName, Mem.memberName, rm.roomExtNumber FROM Room rm, (select memberID, memberName, active, allowedToParticipate, tenantID from Member WHERE MATCH(memberName) AGAINST(? in boolean mode)) Mem WHERE Mem.active = ? AND Mem.allowedToParticipate = ? AND FIND_IN_SET (Mem.tenantID, ?)';
 IF(includeRoomType = 1) THEN
   SET @PART2 = ' AND roomTypeID = ?';
  ELSE 
   SET @PART2 = ' AND roomTypeID != ?';
 END IF;
 IF(roomEnabled = 1) THEN
   SET @PART3 = ' AND roomEnabled = ?';
 END IF;
 SET @PART4 = ' AND rm.memberID = Mem.memberID';
 
 SET @PART5 = 'INSERT IGNORE INTO search_member_room SELECT roomID, roomExtNumber, CASE WHEN roomTypeID = 1 OR roomTypeID = 3 THEN memberName ELSE roomName END as MName FROM (SELECT rm.roomID, rm.roomTypeID, rm.roomName, Mem.memberName, rm.roomExtNumber FROM Member Mem, (SELECT roomID, roomTypeID, roomName, roomEnabled, roomExtNumber, memberID FROM Room WHERE MATCH(roomName, roomExtNumber, displayName) AGAINST(? in boolean mode)';
 
 SET @PART6 = ') rm WHERE Mem.active = ? AND Mem.allowedToParticipate = ? AND FIND_IN_SET (Mem.tenantID, ?) AND Mem.memberID = rm.memberID';
 
 SET @PART7 = ' AND rm.roomId NOT IN (SELECT DISTINCT(rug.room_id) FROM room_user_group rug LEFT JOIN member_user_group mug on rug.user_group_id=mug.user_group_id WHERE (mug.user_group_id is null OR mug.user_group_id <> ?) AND rug.room_id not in (select DISTINCT(rug1.room_id) FROM room_user_group rug1 INNER JOIN member_user_group mug1 on rug1.user_group_id=mug1.user_group_id WHERE mug1.member_id=?))';
 
 SET @PART4 = CONCAT(@PART4, @PART7, ') as S1');
 SET @PART6 = CONCAT(@PART6, @PART7, ') as S2');
 
 IF(roomEnabled = 1) THEN
   SET @finalQuery1 = CONCAT(@PART1, @PART2, @PART3, @PART4);
   SET @finalQuery2 = CONCAT(@PART5, @PART2, @PART3, @PART6);
 ELSE
   SET @finalQuery1 = CONCAT(@PART1, @PART2, @PART4);
   SET @finalQuery2 = CONCAT(@PART5, @PART2, @PART6);
 END IF;
 
 PREPARE stmt FROM @finalQuery1;
 PREPARE stmt1 FROM @finalQuery2;
 IF(roomEnabled = 1) THEN
  EXECUTE stmt USING @query, @active, @allowed, @tenantIds, @roomType, @roomEnabled, @memberId, @memberId;
  EXECUTE stmt1 USING @query, @roomType, @roomEnabled, @active, @allowed, @tenantIds, @memberId, @memberId;
 ELSE
  EXECUTE stmt USING @query, @active, @allowed, @tenantIds, @roomType, @memberId, @memberId;
  EXECUTE stmt1 USING @query, @roomType, @active, @allowed, @tenantIds, @memberId, @memberId;
 END IF;
 
 SELECT roomID, roomExtNumber, name from search_member_room ORDER BY sortOrder LIMIT startRec, limitRec;
 
 SELECT count(*) AS totalCount from search_member_room;

 DEALLOCATE PREPARE stmt;
 DEALLOCATE PREPARE stmt1;
 
END $$
DELIMITER ;


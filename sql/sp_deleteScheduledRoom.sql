DELIMITER $$
DROP PROCEDURE IF EXISTS portal2.deleteScheduledRoom $$
CREATE PROCEDURE portal2.deleteScheduledRoom (
 IN limitRec INTEGER)
 BEGIN
   DECLARE totalRecs INT;
   SET @limitRec = limitRec;
   
   SET @countQuery = 'SELECT COUNT(*) FROM Room WHERE DATE_FORMAT(deleteon, "%Y-%m-%d") <= DATE_FORMAT(NOW(), "%Y-%m-%d") INTO @countRecs';
   
   SET @deleteQuery = 'DELETE FROM Room WHERE DATE_FORMAT(deleteon, "%Y-%m-%d") <= DATE_FORMAT(NOW(), "%Y-%m-%d") LIMIT ?';
   
   SET @deleteWithoutLimitQuery = 'DELETE FROM Room WHERE DATE_FORMAT(deleteon, "%Y-%m-%d") <= DATE_FORMAT(NOW(), "%Y-%m-%d")';
   
   PREPARE countStmt FROM @countQuery;
   
   PREPARE deleteStmt FROM @deleteQuery;
   
   PREPARE deleteWithOutLimitStmt FROM @deleteWithoutLimitQuery;
   
   EXECUTE countStmt;
   
   SET @totalRecs = @countRecs;
  
  if @limitRec > 0 THEN 
	  While @countRecs > @limitRec DO
		   EXECUTE deleteStmt USING @limitRec;
		   commit;
		   SET @countRecs = @countRecs - @limitRec;
		   Do SLEEP(10);
	   END WHILE;
	END IF;
	
   if @countRecs > 0 THEN
       EXECUTE deleteWithOutLimitStmt;
   END IF;
   
   SELECT @totalRecs as TotalRecords;
   
   DEALLOCATE PREPARE countStmt;
   
   DEALLOCATE PREPARE deleteStmt;
   
   DEALLOCATE PREPARE deleteWithOutLimitStmt;
END $$
DELIMITER ;
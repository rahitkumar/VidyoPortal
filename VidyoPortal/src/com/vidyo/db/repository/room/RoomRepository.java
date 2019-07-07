package com.vidyo.db.repository.room;

import com.vidyo.bo.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    Room findByRoomID(Integer roomId);

    /** This method will return the Room matching the roomId and the tenantId passed.  */
    @Query("select room from Room room where room.roomID=:roomID and room.member.tenantID=:tenantID")
    Room findRoomByRoomIdAndTenant(@Param("roomID") Integer roomID, @Param("tenantID")Integer tenantID);
}

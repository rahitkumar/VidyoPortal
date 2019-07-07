package com.vidyo.db.repository.usergroup;


import com.vidyo.bo.usergroup.UserGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Integer>{

    Long countByTenantID(Integer tenantId);

    UserGroup findByUserGroupIdAndTenantID(Integer userGroupId, Integer tenantId);

    UserGroup findByNameAndTenantID(String name, Integer tenantId);

    /** This method returns the List of UserGroup instances based on the filter params. Options params are name and description. If name and/or description are passed, the query will filter the results. */
    @Query("select u from UserGroup u where (u.tenantID=:tenantId and ((:name is null or u.name=:name) and  (:description is null or u.description=:description)))")
    List<UserGroup> findUserGroups(@Param("tenantId") int tenantId, @Param("name") String name,  @Param("description") String description, Pageable pageable);


    List<UserGroup> findByUserGroupIdInAndTenantID(Set<Integer> userGroupIds, Integer tenantId);

    Set<UserGroup> findByNameInAndTenantID(Set<String> groupNames, Integer tenantId);
    /**  Returns the count of Rooms for a given roomId from UserGroups**/
    public long countByRooms_roomID(Integer roomId);

    @Query(value = "select count(rg.user_group_id) from room_user_group rg inner join member_user_group mg on rg.user_group_id=mg.user_group_id where rg.room_id=:roomId and mg.member_id=:memberId",nativeQuery = true)
    public int getCountOfCommonUserGroupsForRoomAndMember(@Param("roomId")int roomId, @Param("memberId") int memberId);
}

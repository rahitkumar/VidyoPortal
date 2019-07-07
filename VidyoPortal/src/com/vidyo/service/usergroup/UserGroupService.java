package com.vidyo.service.usergroup;

import com.vidyo.bo.usergroup.UserGroup;

import java.util.List;
import java.util.Set;

/**
 * The service interface for UserGroup API.
 */
public interface UserGroupService {

    public enum Operation{
        UPDATE,
        DELETE
    }

    UserGroup createUserGroup(UserGroup userGroup);

    UserGroup updateUserGroup(Integer id, UserGroup userGroup);

    void deleteUserGroup(Integer userGroupId);

    List<UserGroup> fetchUserGroups(String name, String description, int offset, int limit, String sortDirection, String[] properties);

    Set<UserGroup> fetchUserGroupsByName(Set<String> groupNames);

    UserGroup fetchUserGroupById(Integer userGroupId);

    void updateGroupsForRoom(Integer roomId, Set<Integer> userGroupIds, Operation operation);

	void updateGroupsForMember(Integer memberId, Set<Integer> userGroupIds, Operation operation);

	void setGroupsForMember(Integer memberId, Set<String> userGroupNames);

    void deleteUserGroups(Set<Integer> userGroupIds);

    public boolean canMemberAccessRoom(Integer roomId, Integer memberId);

    public boolean canGuestAccessRoom(Integer roomId);

    public Set<UserGroup> fetchUserGroupsAttachedToRoom(Integer roomId);

    public Set<UserGroup> fetchUserGroupsAttachedToMember(Integer memberId);
}


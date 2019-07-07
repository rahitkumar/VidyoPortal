package com.vidyo.service;

import com.vidyo.bo.Group;
import com.vidyo.bo.GroupFilter;
import com.vidyo.bo.Room;
import com.vidyo.service.group.request.DeleteGroupRequest;
import com.vidyo.service.group.response.UpdateGroupResponse;

import java.util.List;

public interface IGroupService {
	
	public List<Group> getGroups(GroupFilter filter);

	public Long getCountGroups(GroupFilter filter);

	public Group getGroup(int groupID);

	public Group getDefaultGroup();

	public Group getGroupByName(String groupName);

	public int updateGroup(int groupID, Group group);

	public int insertGroup(Group group);

	public int deleteGroup(int groupID);

	public boolean isGroupExistForGroupName(String groupName, int groupID);

	public List<Room> getRoomsForGroup(int groupID);

	public boolean hasReplayComponent();

	/**
	 * Deletes a group by groupId and updates the rooms assigned to the
	 * "to be deleted" group to Default group. Also validates if the group
	 * belongs to the same tenant.
	 * 
	 * @param deleteGroupRequest
	 *            DTO containing tenantId and groupId
	 * @return Response containing status code and message
	 */
	public UpdateGroupResponse deleteGroup(DeleteGroupRequest deleteGroupRequest);
}

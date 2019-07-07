package com.vidyo.db;

import com.vidyo.bo.Group;
import com.vidyo.bo.GroupFilter;
import com.vidyo.bo.Room;

import java.util.List;

public interface IGroupDao {
    public List<Group> getGroups(int tenant, GroupFilter filter);
    public Long getCountGroups(int tenant, GroupFilter filter);
    public Group getGroup(int tenant, int groupID);
    public Group getDefaultGroup(int tenant);    
    public Group getGroupByName(int tenant, String groupName);
    public int updateGroup(int tenant, int groupID, Group group);
    public int insertGroup(int tenant, Group group);
    public int deleteGroup(int tenant, int groupID);
    public boolean isGroupExistForGroupName(int tenant, String groupName, int groupID);
    public List<Room> getRoomsForGroup(int tenant, int groupID);
}

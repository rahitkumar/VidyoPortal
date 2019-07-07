package com.vidyo.service;

import com.vidyo.bo.Group;
import com.vidyo.bo.GroupFilter;
import com.vidyo.bo.Guestconf;
import com.vidyo.bo.Room;
import com.vidyo.db.IGroupDao;
import com.vidyo.db.idp.ITenantIdpAttributesMappingDao;
import com.vidyo.db.ldap.ITenantLdapAttributesMappingDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.group.request.DeleteGroupRequest;
import com.vidyo.service.group.response.UpdateGroupResponse;
import com.vidyo.service.room.RoomUpdateResponse;
import com.vidyo.service.room.request.RoomUpdateRequest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GroupServiceImpl implements IGroupService {
	
	protected static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    private IGroupDao dao;
    private ITenantLdapAttributesMappingDao tenantLdapAttributesMappingDao;
    private ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao;
    private ITenantService tenantService;
	private ISystemService systemService;
    
    private IRoomService room;

    public void setDao(IGroupDao dao) {
        this.dao = dao;
    }

    public void setRoom(IRoomService room) {
        this.room = room;
    }

    public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }
    
    public void setTenantLdapAttributesMappingDao(ITenantLdapAttributesMappingDao tenantLdapAttributesMappingDao) {
    	this.tenantLdapAttributesMappingDao = tenantLdapAttributesMappingDao;
    }
    
    public void setTenantIdpAttributesMappingDao(ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao) {
    	this.tenantIdpAttributesMappingDao = tenantIdpAttributesMappingDao;
    }

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

    public List<Group> getGroups(GroupFilter filter) {
        List<Group> list = this.dao.getGroups(TenantContext.getTenantId(), filter);
        return list;
    }

    public Long getCountGroups(GroupFilter filter) {
        Long number = this.dao.getCountGroups(TenantContext.getTenantId(), filter);
        return number;                                              
    }

    public Group getGroup(int groupID) {
        Group group = this.dao.getGroup(TenantContext.getTenantId(), groupID);
        return group;
    }

    public Group getDefaultGroup() {
        Group group = this.dao.getDefaultGroup(TenantContext.getTenantId());
        return group;
    }
    
    public Group getGroupByName(String groupName) {
        Group group = this.dao.getGroupByName(TenantContext.getTenantId(), groupName);
        return group;
    }

    public List<Room> getRoomsForGroup(int groupID) {
        List<Room> list = this.dao.getRoomsForGroup(TenantContext.getTenantId(), groupID);
        return list;
    }
    
    public int updateGroup(int groupID, Group group) {
    	Group existingGroup = dao.getGroup(TenantContext.getTenantId(), groupID);
    	int rc = this.dao.updateGroup(TenantContext.getTenantId(), groupID, group);
    	
    	if(!existingGroup.getGroupName().equalsIgnoreCase(group.getGroupName())) {
    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(TenantContext.getTenantId(),
    				"Group", existingGroup.getGroupName(), group.getGroupName());
    		tenantLdapAttributesMappingDao.updateTenantLdapAttributeValueMappingVidyoValueName(TenantContext.getTenantId(),
    				"Group", existingGroup.getGroupName(), group.getGroupName());
    		
    		tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(TenantContext.getTenantId(),
    				"Group", existingGroup.getGroupName(), group.getGroupName());
    		tenantIdpAttributesMappingDao.updateTenantIdpAttributeValueMappingVidyoValueName(TenantContext.getTenantId(),
    				"Group", existingGroup.getGroupName(), group.getGroupName());
    	}
        return rc;
    }

    public int insertGroup(Group group) {
        int rc = this.dao.insertGroup(TenantContext.getTenantId(), group);
        return rc;
    }

    /**
     * Deprecated, please use the other deleteGroup API
     */
    @Deprecated
    public int deleteGroup(int groupID) {
        Group defaultGroup = this.getDefaultGroup();
        
        List<Room> roomsAffected = this.getRoomsForGroup(groupID);
        for(Room room: roomsAffected){
        	room.setGroupID(defaultGroup.getGroupID());
        	room.setGroupName(defaultGroup.getGroupName());
        	
        	this.room.updateRoom(room.getRoomID(), room);
        }
        
        // Update LDAP and IdP mappings
        Group group = getGroup(groupID);
 		tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(TenantContext.getTenantId(),
				"Group", group.getGroupName(), defaultGroup.getGroupName());
 		tenantLdapAttributesMappingDao.removeTenantLdapAttributeValueMappingRecords(TenantContext.getTenantId(),
				"Group", group.getGroupName());
 		
 		tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(TenantContext.getTenantId(),
				"Group", group.getGroupName(), defaultGroup.getGroupName());
 		tenantIdpAttributesMappingDao.removeTenantIdpAttributeValueMappingRecords(TenantContext.getTenantId(),
 				"Group", group.getGroupName());
    	
        int rc = this.dao.deleteGroup(TenantContext.getTenantId(), groupID);

		// update guest settings
		Guestconf guestConf = systemService.getGuestConf();
		int usedGroupId= guestConf.getGroupID();
		if (usedGroupId == groupID) {
			guestConf.setGroupID(defaultGroup.getGroupID());
			systemService.saveGuestConf(guestConf);
		}

        return rc;
    }
    
	/**
	 * Deletes a group by groupId and updates the rooms assigned to the
	 * "to be deleted" group to Default group. Also validates if the group
	 * belongs to the same tenant.
	 * 
	 * @param deleteGroupRequest
	 *            DTO containing tenantId and groupId
	 * @return Response containing status code and message
	 */
	public UpdateGroupResponse deleteGroup(DeleteGroupRequest deleteGroupRequest) {
		// TODO - Refactor to return only groups belonging to tenant
		Group group = null;
		try {
			group = dao.getGroup(deleteGroupRequest.getTenantId(), deleteGroupRequest.getGroupId());
		} catch (Exception e1) {
			logger.error("Attempt to delete a non-existent group -{}", deleteGroupRequest.getGroupId());
		}
		UpdateGroupResponse updateGroupResponse = new UpdateGroupResponse();

		// Group validity and Cross tenant check
		if (group == null || group.getTenantID() != deleteGroupRequest.getTenantId()) {
			updateGroupResponse.setStatus(UpdateGroupResponse.INVALID_GROUP);
			updateGroupResponse.setMessage("invalid.group");
			return updateGroupResponse;
		}

		// Check if Default
		if (group.getDefaultFlag() == 1) {
			updateGroupResponse.setStatus(UpdateGroupResponse.CANNOT_DELETE_DEFAULT_GROUP);
			updateGroupResponse.setMessage("cannot.delete.default.group");
			return updateGroupResponse;
		}

		// Update the rooms belonging to the "to be deleted" group to Default
		Group defaultGroup = getDefaultGroup();
		if (defaultGroup == null) {
			updateGroupResponse.setStatus(UpdateGroupResponse.DEFAULT_GROUP_NOT_FOUND);
			updateGroupResponse.setMessage("cant.delete.group.no.default.group");
			return updateGroupResponse;
		}

		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();
		roomUpdateRequest.setOldGroupId(group.getGroupID());
		roomUpdateRequest.setDefaultGroupId(defaultGroup.getGroupID());
		roomUpdateRequest.setTenantId(deleteGroupRequest.getTenantId());

		RoomUpdateResponse roomUpdateResponse = room.updateGroupForRooms(roomUpdateRequest);

		if (roomUpdateResponse.getStatus() != RoomUpdateResponse.SUCCESS) {
			updateGroupResponse.setStatus(roomUpdateResponse.getStatus());
			return updateGroupResponse;
		}
		
		// Update LDAP and IdP mappings
		tenantLdapAttributesMappingDao.updateTenantLdapAttributeMappingDefaultAttributeValue(deleteGroupRequest.getTenantId(), 
				"Group", group.getGroupName(), defaultGroup.getGroupName());
		tenantLdapAttributesMappingDao.removeTenantLdapAttributeValueMappingRecords(deleteGroupRequest.getTenantId(), 
				"Group", group.getGroupName());
		
		tenantIdpAttributesMappingDao.updateTenantIdpAttributeMappingDefaultAttributeValue(deleteGroupRequest.getTenantId(), 
				"Group", group.getGroupName(), defaultGroup.getGroupName());
		tenantIdpAttributesMappingDao.removeTenantIdpAttributeValueMappingRecords(deleteGroupRequest.getTenantId(), 
				"Group", group.getGroupName());

		// update guest settings
		Guestconf guestConf = systemService.getGuestConf();
		int usedGroupId= guestConf.getGroupID();
		if (usedGroupId == deleteGroupRequest.getGroupId()) {
			guestConf.setGroupID(defaultGroup.getGroupID());
			systemService.saveGuestConf(guestConf);
		}

		// Proceed to delete the group
		int count = 0;
		try {
			count = dao.deleteGroup(deleteGroupRequest.getTenantId(), deleteGroupRequest.getGroupId());
		} catch (Exception e) {
			logger.error("Exception while deleting group id - {}, error - {} ", deleteGroupRequest.getGroupId(),
					e.getMessage());
		}

		if (count <= 0) {
			updateGroupResponse.setStatus(UpdateGroupResponse.GROUP_NOT_DELETED);
		} else {
			updateGroupResponse.setStatus(UpdateGroupResponse.SUCCESS);
		}

		return updateGroupResponse;
	}

    public boolean isGroupExistForGroupName(String groupName, int groupID) {
        boolean rc = this.dao.isGroupExistForGroupName(TenantContext.getTenantId(), groupName, groupID);
        return rc;
    }

    public boolean hasReplayComponent() {
        boolean rc = this.tenantService.hasTenantReplayComponent(TenantContext.getTenantId());
        return rc;
    }
}

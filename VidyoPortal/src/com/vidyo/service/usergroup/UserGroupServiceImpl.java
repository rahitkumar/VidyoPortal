package com.vidyo.service.usergroup;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.usergroup.UserGroup;
import com.vidyo.db.repository.member.MemberRepository;
import com.vidyo.db.repository.room.RoomRepository;
import com.vidyo.db.repository.usergroup.UserGroupRepository;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.service.exceptions.DataValidationException;
import com.vidyo.service.exceptions.ResourceNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.apache.commons.lang.StringUtils.isBlank;
import static org.apache.commons.lang.StringUtils.isNumeric;

/**
 * This class implements {@link UserGroupService} interface for all operations of UserGroup
 *
 *
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberRepository memberRepository;


    @Autowired
    private ISystemService systemService;


    public void setUserGroupRepository(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public ISystemService getSystemService() {
        return systemService;
    }

    public void setSystemService(ISystemService systemService) {
        this.systemService = systemService;
    }


    /**
     * This method creates UserGroup instance
     *
     * @param userGroup
     * @return UserGroup instance
     */
    @Override
    public UserGroup createUserGroup(UserGroup userGroup) {
        return createUserGroup(userGroup, null);
    }


    /**
     * This method updates the {@link UserGroup} instance.
     *
     * @param id
     * @param userGroup
     * @return - Updated {@link UserGroup} instance
     */
    @Override
    public UserGroup updateUserGroup(Integer id, UserGroup userGroup) {
        Integer tenantId = TenantContext.getTenantId();
        logger.debug("Entering the updateUserGroup method with params : " + userGroup + " for tenant :" + tenantId);
        String userGroupName = StringUtils.trim(userGroup.getName());
        UserGroup fetchedUserGroup = userGroupRepository.findByUserGroupIdAndTenantID(id, tenantId);

        if (fetchedUserGroup == null) {
            logger.error("No matching usergroup found for the tenant : usergroup name : " + userGroupName + " tenant: " + tenantId);
            throw new DataValidationException(("No matching usergroup found"));
        }

        UserGroup returnedUserGroup = userGroupRepository.findByNameAndTenantID(userGroupName, tenantId);
        if (returnedUserGroup != null && returnedUserGroup.getUserGroupId() != id) {
            logger.error("usergroup with passed name already present : usergroup name: " + returnedUserGroup.getName() + " id: " + id);
            throw new DataValidationException(("usergroup with given name already exsits."));
        }
        fetchedUserGroup.setName(userGroup.getName());
        fetchedUserGroup.setDescription(userGroup.getDescription());
        UserGroup savedUserGroup = userGroupRepository.save(fetchedUserGroup);
        systemService.auditLogTransaction("update user-group", "name:: " + userGroup.getName() + " description : " + userGroup.getDescription()+" tenantID: "+tenantId, "SUCCESS");
        logger.info("Updated the usergroup instance : " + savedUserGroup.getUserGroupId());
        return savedUserGroup;
    }


    /**
     * This method deletes the {@link UserGroup} insrance for the userGroupId passed
     *
     * @param userGroupId
     */
    @Override
    @Transactional
    public void deleteUserGroup(Integer userGroupId) {
        gracefulDeletionOfUserGroups(userGroupId, true);
    }

    /**
     * This method is used to fetch the userGroupsList based on the filter conditions
     *
     * @param name
     * @param description
     * @param page
     * @param size
     * @param direction
     * @param properties
     * @return
     */
    @Override
    public List<UserGroup> fetchUserGroups(String name, String description, int page, int size, String direction, String[] properties) {
        Integer tenantId = TenantContext.getTenantId();
        logger.debug("Entering the fetchUserGroups with params : name " + name + " description : " + description + " page " + page + " size " + size + " direction " + direction + " properties " + Arrays.asList(properties));
        Sort.Direction dir = (direction.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        List<UserGroup> userGroupsList = userGroupRepository.findUserGroups(tenantId, name, description, gotoPage(page, size, dir, properties));
        return userGroupsList;
    }

    @Override
    public Set<UserGroup> fetchUserGroupsByName(Set<String> groupNames) {
        Integer tenantId = TenantContext.getTenantId();
        logger.debug("Entering the fetchUserGroupsByName method for tenant : " + tenantId);
        if (CollectionUtils.isEmpty(groupNames)) {
            return new HashSet<>();
        }
        return userGroupRepository.findByNameInAndTenantID(groupNames, tenantId);
    }

    /**
     * This method will fetch a single instance of {@link UserGroup}
     *
     * @param userGroupId
     * @return UserGroup instance matching the {@link UserGroup#userGroupId}
     */
    @Override
    public UserGroup fetchUserGroupById(Integer userGroupId) {
        Integer tenantId = TenantContext.getTenantId();
        logger.debug("Entering the fetchUserGroup method : usergroupId " + userGroupId + " for tenant : " + tenantId);
        UserGroup userGroup = userGroupRepository.findByUserGroupIdAndTenantID(userGroupId, TenantContext.getTenantId());
        if (userGroup == null) {
            logger.error("No maching usergroup found for id " + userGroupId + " for tenant " + tenantId);
            throw new ResourceNotFoundException("No maching usergroup found.");
        }
        return userGroup;
    }

    /**
     * This method attaches a list of userGroup ids to the room
     *
     * @param roomId
     * @param userGroupIds
     */
    @Override
    @Transactional
    public void updateGroupsForRoom(Integer roomId, Set<Integer> userGroupIds, Operation operation) {
        Integer tenantId = TenantContext.getTenantId();
        logger.debug("Entering the updateGroupsForRoom method : roomId " + roomId + "usergroups ids: " + userGroupIds + " for tenant : " + tenantId + " operation: " + operation);
        Room room = null;
        if (userGroupIds.isEmpty()) {
            logger.error("Empty set of userGroupIds passed.");
            throw new DataValidationException("UserGroupdId list cannot be empty");
        }
        room = roomRepository.findRoomByRoomIdAndTenant(roomId, tenantId);
        if (room == null) {
            logger.error("Invalid room Id assignGroupsToRoom " + roomId + " in assignGroupsToRoom method");
            throw new DataValidationException("Invalid roomId");
        }
        logger.debug("Room is " + room.getRoomID() + " room name " + room.getDisplayName());
        //fetch the groupIds for the room
        List<UserGroup> userGroupList = userGroupRepository.findByUserGroupIdInAndTenantID(userGroupIds, tenantId);
        logger.debug("uniqueUserGroupIds to be updated  : " + userGroupList + " for room : " + roomId);
        if (userGroupList.isEmpty()) {
            logger.error("Invalid list of UserGroupIds passed");
            throw new DataValidationException("Invalid set of UserGroupIds passed");
        }
        for (UserGroup userGroup : userGroupList) {
            if (operation == Operation.UPDATE) {
                userGroup.addRoom(room);
            } else if (operation == Operation.DELETE) {
                userGroup.removeRoom(room);
            }
            userGroupRepository.save(userGroup);
        }
        systemService.auditLogTransaction("update user groups for room", "userGroupIds: " + userGroupIds + " : " + "room :" + roomId + " operation : " + operation.toString()+" tenantID: "+tenantId, "SUCCESS");
    }


    /**
     * This method will attach the usergroups to the member
     *
     * @param memberId
     * @param userGroupIds
     */
    @Override
    @Transactional
    public void updateGroupsForMember(Integer memberId, Set<Integer> userGroupIds, Operation operation) {
        Integer tenantId = TenantContext.getTenantId();
        logger.debug("Entering the updateGroupsForMember method : userId " + memberId + " usergroups ids: " + userGroupIds + " for tenant : " + tenantId + " operation : " + operation);
        if (userGroupIds.isEmpty()) {
            logger.error("Empty set of userGroupIds passed for updateGroupsForMember ");
            throw new DataValidationException("UserGroupId list cannot be empty.");
        }
        Member member = memberRepository.findByMemberIDAndTenantID(memberId, tenantId);
        if (member == null) {
            logger.error("Invalid user Id passed " + memberId + " in updateGroupsForMember method");
            throw new DataValidationException("Invalid memberId");
        }
        logger.debug("Member is " + member.getMemberID() + " member name " + member.getMemberName());
        //fetch unique userGroupIds for the given member
        List<UserGroup> userGroupList = userGroupRepository.findByUserGroupIdInAndTenantID(userGroupIds, tenantId);
        logger.debug("uniqueUserGroupIds to be updated  : " + userGroupList + " for member : " + memberId);
        if (userGroupList.isEmpty()) {
            logger.error("Invalid set of UserGroupIds passed");
            throw new DataValidationException("Invalid set of UserGroupIds passed");
        }
        for (UserGroup userGroup : userGroupList) {
            if (operation == Operation.UPDATE) {
                userGroup.addMember(member);
            } else if (operation == Operation.DELETE) {
                userGroup.removeMember(member);
            }
            userGroupRepository.save(userGroup);
        }
        systemService.auditLogTransaction("update groups for member ", "userGroupIds: " + userGroupIds + " : " + "member :" + memberId + " operation : " + operation.toString()+" tenantID: "+tenantId, "SUCCESS");
    }

    /**
     * This method will set the usergroups for a member
     *
     * @param memberId
     * @param userGroupNames
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public void setGroupsForMember(Integer memberId, Set<String> userGroupNames) {
        Integer tenantId = TenantContext.getTenantId();
        logger.debug("Entering the setGroupsForMember method : userId " + memberId + " usergroups names: "
                + userGroupNames + " for tenant : " + tenantId);

        Member member = memberRepository.findByMemberIDAndTenantID(memberId, tenantId);
        if (member == null) {
            logger.error("Invalid member Id passed " + memberId + " in setGroupsForMember method");
            throw new DataValidationException("Invalid memberId");
        }
        logger.debug("Member is " + member.getMemberID() + " member name " + member.getMemberName());

        if (CollectionUtils.isEmpty(userGroupNames) && CollectionUtils.isEmpty(member.getUserGroups())) {
            return;
        }

        Set<Integer> userGroupsCurrentlyAttached = new HashSet<Integer>();
        Set<Integer> userGroupsToBeAttached = new HashSet<Integer>();

        if (CollectionUtils.isNotEmpty(member.getUserGroups())) {
            for (UserGroup userGroup : member.getUserGroups()) {
                userGroupsCurrentlyAttached.add(userGroup.getUserGroupId());
            }
        }

        // Fetch the existing user groups by name
        // If not present, then create new user groups
        Set<UserGroup> userGroups = new HashSet<UserGroup>();
        if (CollectionUtils.isNotEmpty(userGroupNames)) {
            userGroups = fetchUserGroupsByName(userGroupNames);
            if (userGroups.size() != userGroupNames.size()) {
                Set<String> userGroupsToBeCreated = new HashSet<String>(userGroupNames);
                for (UserGroup userGroup : userGroups) {
                    userGroupsToBeCreated.remove(userGroup.getName());
                }
                userGroups.addAll(createUserGroups(userGroupsToBeCreated, member.getUsername()));
            }

            for (UserGroup userGroup : userGroups) {
                userGroupsToBeAttached.add(userGroup.getUserGroupId());
            }
        }

        // Find the user groups that need to be newly attached or detached from
        // the user
        Collection<Integer> userGroupsToBeAdded = CollectionUtils.subtract(userGroupsToBeAttached,
                userGroupsCurrentlyAttached);
        Collection<Integer> userGroupsToBeRemoved = CollectionUtils.subtract(userGroupsCurrentlyAttached,
                userGroupsToBeAttached);

        // Attach/detach member from the user groups
        if (CollectionUtils.isNotEmpty(userGroupsToBeRemoved)) {
            for (UserGroup userGroup : member.getUserGroups()) {
                if (!userGroupsToBeRemoved.contains(userGroup.getUserGroupId())) {
                    continue;
                }
                userGroup.removeMember(member);
                userGroupRepository.save(userGroup);
            }
        }
        if (CollectionUtils.isNotEmpty(userGroupsToBeAdded)) {
            for (UserGroup userGroup : userGroups) {
                if (!(userGroupsToBeAdded.contains(userGroup.getUserGroupId()))) {
                    continue;
                }
                userGroup.addMember(member);
                userGroupRepository.save(userGroup);
            }
        }
        systemService.auditLogTransaction("setting user groups for member ", "memberId: " + memberId + " : " + "groupNames :" + userGroups.stream()
                .map(UserGroup::getName)
                .collect(Collectors.toList())+" tenantID: "+tenantId, member.getUsername(), "SUCCESS");
    }

    private List<UserGroup> createUserGroups(Set<String> userGroupsToBeCreated, String username) {
        List<UserGroup> userGroups = new ArrayList<UserGroup>();
        if (CollectionUtils.isEmpty(userGroupsToBeCreated)) {
            return userGroups;
        }

        for (String groupName : userGroupsToBeCreated) {
            if (StringUtils.isBlank(groupName)) {
                continue;
            }
            UserGroup userGroup = new UserGroup();
            userGroup.setName(groupName);
            userGroup.setDescription(groupName);
            try {
                userGroups.add(createUserGroup(userGroup, username));
            } catch (Exception exception){
                logger.warn("exception while creating user group :  "+groupName);
                logger.warn("exception message : "+exception.getMessage());
                continue;
            }
        }

        return userGroups;
    }

    /**
     * This method deletes the set of usergroup ids passed
     *
     * @param userGroupIds
     */
    @Override
    @Transactional
    public void deleteUserGroups(Set<Integer> userGroupIds) {
        logger.debug("Entering the deleteUserGroups method with params :" + userGroupIds);
        if (userGroupIds.isEmpty()) {
            logger.error("UserGroupIds list cannot be empty");
            throw new DataValidationException("UserGroupId list cannot be empty");
        }
        userGroupIds.stream().forEach(userGroupId -> gracefulDeletionOfUserGroups(userGroupId, userGroupIds.size() == 1 ? true : false));
        systemService.auditLogTransaction("deleting user groups ", "userGroupIds :" + userGroupIds, "SUCCESS");
    }

    /**
     * This method checks if the memberId passed has access to the room.
     *
     * @param roomId
     * @param memberId
     * @return
     */
    @Override
    public boolean canMemberAccessRoom(Integer roomId, Integer memberId) {
        logger.debug("Entering the canMemberAccessRoom method with params :" + " roomId : " + roomId + " memberId : " + memberId);
        long count = userGroupRepository.countByRooms_roomID(roomId);
        if (count <= 0 || userGroupRepository.getCountOfCommonUserGroupsForRoomAndMember(roomId, memberId) > 0) {
            return true;
        }
        return false;
    }

    /**
     * This method checks if the guest passed has access to the room.
     *
     * @param roomId
     * @return
     */
    @Override
    public boolean canGuestAccessRoom(Integer roomId) {
        logger.debug("Entering the canGuestAccessRoom method with params :" + " roomId : " + roomId);
        long count = userGroupRepository.countByRooms_roomID(roomId);
        if (count <= 0) {
            return true;
        }
        return false;
    }

    private PageRequest gotoPage(int page, int size, Sort.Direction direction, String... properties) {
        PageRequest request = new PageRequest(page, size, new Sort(direction, properties));
        return request;
    }

    /**
     * Method to fetch the user groups attached to a room
     */
    public Set<UserGroup> fetchUserGroupsAttachedToRoom(Integer roomId) {
        Room room = roomRepository.findByRoomID(roomId);
        if (room == null) {
            return new HashSet<UserGroup>();
        }
        return room.getUserGroups();
    }

    /**
     * Method to fetch the user groups attached to a member
     */
    public Set<UserGroup> fetchUserGroupsAttachedToMember(Integer memberId) {
        Integer tenantId = TenantContext.getTenantId();
        Member member = memberRepository.findByMemberIDAndTenantID(memberId, tenantId);
        if (member == null) {
            return new HashSet<UserGroup>();
        }
        return member.getUserGroups();
    }

    private void gracefulDeletionOfUserGroups(Integer userGroupId, boolean flag) {
        Integer tenantId = TenantContext.getTenantId();
        logger.debug("Entering the gracefulDeletionOfUserGroups method with params : " + userGroupId + " for tenant :" + tenantId);
        UserGroup fetchedUserGroup = userGroupRepository.findByUserGroupIdAndTenantID(userGroupId, tenantId);
        if (fetchedUserGroup == null) {
            if (flag) {
                logger.error("No Usergroup with " + userGroupId + " for deletion.");
                throw new DataValidationException("Invalid userGroupId passed");
            } else {
                logger.info("No Usergroup with " + userGroupId + " for deletion.");
            }
        } else {
            logger.debug("deleting the userGroupId : " + userGroupId);
            fetchedUserGroup.removeAllMembers();
            fetchedUserGroup.removeAllRooms();
            userGroupRepository.delete(fetchedUserGroup);
            systemService.auditLogTransaction("delete user-group", "name: " + fetchedUserGroup.getName() + " description:" + fetchedUserGroup.getDescription()+" tenantID: "+tenantId, "SUCCESS");
            logger.info("Deleted the usergroup instance : " + userGroupId + "for tenant :" + tenantId);
        }
    }

    /**
     * Overloaded method to create usergroup with username parameter to be called in SAML/LDAP flow.
     *
     * @param userGroup
     * @param username
     * @return created usergroup instance
     */

    private UserGroup createUserGroup(UserGroup userGroup, String username) {
        Integer tenantId = TenantContext.getTenantId();
        logger.debug("Entering the createUserGroup method with params " + userGroup + " for tenant :" + tenantId +" username: "+username);

        Configuration maxUserGroupsCount = systemService.getConfiguration("MAX_PERMITTED_USER_GROUPS_COUNT");

        // fetch the max permitted user groups from the db.
        long maxGroupsCount = ((maxUserGroupsCount == null)
                || isBlank(maxUserGroupsCount.getConfigurationValue())
                || !isNumeric(maxUserGroupsCount.getConfigurationValue()) ? 1000 : Long.parseLong(maxUserGroupsCount.getConfigurationValue()));

        long userGroupsCount = userGroupRepository.countByTenantID(tenantId);

        if ( userGroupsCount >= maxGroupsCount) {
            logger.error("usergroups count exceeds the max allowed : userGroupsCount " + userGroupsCount);
            if (StringUtils.isBlank(username)) {
                systemService.auditLogTransaction("create user-group failed -  user groups exceeds max configured", "count: "+String.valueOf(userGroupsCount)+" tenantId: "+tenantId, "FAILURE");
            } else {
                systemService.auditLogTransaction("create user-group failed -  user groups exceeds max configured", "count: "+String.valueOf(userGroupsCount)+" tenantId: "+tenantId, username, "FAILURE");
            }
            throw new IndexOutOfBoundsException("exceeds the count of max allowed user groups.");
        }
        userGroup.setName(StringUtils.trim(userGroup.getName()));
        UserGroup fetchedUserGroup = userGroupRepository.findByNameAndTenantID(userGroup.getName(), tenantId);

        if (fetchedUserGroup != null) {
            logger.error("usergroup already exists : " + userGroup + " for tenant " + tenantId);
            throw new DataValidationException("usergroup with given name already exsits.");
        }
        userGroup.setTenantID(tenantId);
        UserGroup savedUserGroup = userGroupRepository.save(userGroup);
        if (StringUtils.isBlank(username)) {
            systemService.auditLogTransaction("create user-group", "name: " + savedUserGroup.getName() + ":: description: " + savedUserGroup.getDescription()+" tenantID: "+tenantId, "SUCCESS");
        } else {
            systemService.auditLogTransaction("create user-group", "name: " + savedUserGroup.getName() + ":: description: " + savedUserGroup.getDescription()+"tenantID: "+tenantId, username, "SUCCESS");
        }
        logger.info("Saved the usergroup instance : " + userGroup.getUserGroupId());
        return savedUserGroup;
    }
}

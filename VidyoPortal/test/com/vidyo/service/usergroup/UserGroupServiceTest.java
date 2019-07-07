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
import org.apache.commons.lang.math.RandomUtils;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.testng.Assert.*;


@PrepareForTest({TenantContext.class})
public class UserGroupServiceTest extends PowerMockTestCase {

    private UserGroupServiceImpl userGroupService;
    private UserGroupRepository userGroupRepository;
    private RoomRepository roomRepository;
    private MemberRepository memberRepository;
    private UserGroup mockUserGroup;
    private ISystemService mockSystemService;
    private Configuration mockConfiguration;
    ;
    private Room mockRoom;
    private Member mockMember;

    private static final String USER_EXISTS = "usergroup with given name already exsits.";
    private static final String NO_MATCHING_USER = "No matching usergroup found";
    private static final int TENANT_ID = 77;

    private String name = "test";
    private String description = "test-description";

    private Set<Room> roomList = new HashSet<>();
    private Set<Member> memberList = new HashSet<>();

    @BeforeMethod
    private void setup() {
        {
            mockStatic(TenantContext.class);
            for (int i = 0; i < 15; i++) {
                Room room = mock(Room.class);
                roomList.add(room);
                Member member = mock(Member.class);
                memberList.add(member);
            }
            when(TenantContext.getTenantId()).thenReturn(TENANT_ID);
            userGroupService = new UserGroupServiceImpl();
            mockSystemService = mock(ISystemService.class);
            mockConfiguration = mock(Configuration.class);

            //Injecting the repository dependencies.
            userGroupRepository = mock(UserGroupRepository.class);
            roomRepository = mock(RoomRepository.class);
            memberRepository = mock(MemberRepository.class);
            userGroupService.setMemberRepository(memberRepository);
            userGroupService.setRoomRepository(roomRepository);
            userGroupService.setUserGroupRepository(userGroupRepository);
            userGroupService.setSystemService(mockSystemService);

            mockUserGroup = mock(UserGroup.class);
            mockRoom = mock(Room.class);
            mockMember = mock(Member.class);

            when(mockUserGroup.getTenantID()).thenReturn(TENANT_ID);
            when(mockUserGroup.getUserGroupId()).thenReturn(RandomUtils.nextInt());
            when(mockUserGroup.getName()).thenReturn(name);
            when(mockUserGroup.getDescription()).thenReturn(description);
            when(mockUserGroup.getRooms()).thenReturn(roomList);
            ;
        }
    }

    //Tests for create UserGroup Service

    @Test
    public void createWithNullConfiguration() {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(description);
        when(mockConfiguration.getConfigurationValue()).thenReturn(null);
        when(userGroupRepository.countByTenantID(77)).thenReturn(100l);
        when(mockSystemService.getConfiguration(any(String.class))).thenReturn(mockConfiguration);
        when(userGroupRepository.findByNameAndTenantID(name, TENANT_ID)).thenReturn(userGroup);
        try {
            userGroupService.createUserGroup(userGroup);
        } catch (DataValidationException exception) {
            Assert.assertTrue(exception.getMessage().equals(USER_EXISTS));
        }
    }

    @Test
    public void createWithBlankInput() {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(description);
        when(mockConfiguration.getConfigurationValue()).thenReturn(" ");
        when(userGroupRepository.countByTenantID(77)).thenReturn(10l);
        when(mockSystemService.getConfiguration(any(String.class))).thenReturn(mockConfiguration);
        when(userGroupRepository.findByNameAndTenantID(name, TENANT_ID)).thenReturn(userGroup);
        try {
            userGroupService.createUserGroup(userGroup);
        } catch (DataValidationException exception) {
            Assert.assertTrue(exception.getMessage().equals(USER_EXISTS));
        }
    }

    @Test
    public void createWithInvalidInput() {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(description);
        when(mockConfiguration.getConfigurationValue()).thenReturn("sdfsdf ");
        when(userGroupRepository.countByTenantID(77)).thenReturn(10l);
        when(mockSystemService.getConfiguration(any(String.class))).thenReturn(mockConfiguration);
        when(userGroupRepository.findByNameAndTenantID(name, TENANT_ID)).thenReturn(userGroup);
        try {
            userGroupService.createUserGroup(userGroup);
        } catch (DataValidationException exception) {
            Assert.assertTrue(exception.getMessage().equals(USER_EXISTS));
        }
    }

    @Test
    public void createWithIndexOutOfBounds() {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(description);
        when(mockConfiguration.getConfigurationValue()).thenReturn("1000");
        when(userGroupRepository.countByTenantID(77)).thenReturn(1001l);
        when(mockSystemService.getConfiguration(any(String.class))).thenReturn(mockConfiguration);
        when(userGroupRepository.findByNameAndTenantID(name, TENANT_ID)).thenReturn(userGroup);
        try {
            userGroupService.createUserGroup(userGroup);
        } catch (Exception exception) {
            Assert.assertTrue(exception instanceof IndexOutOfBoundsException);
        }
    }

    @Test
    public void createWithValidInput() {
        String name = "new-user-group";
        String description = "test-description";
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(description);

        UserGroup mockUserGroup = mock(UserGroup.class);

        when(mockUserGroup.getTenantID()).thenReturn(TENANT_ID);
        when(mockUserGroup.getUserGroupId()).thenReturn(RandomUtils.nextInt());
        when(mockUserGroup.getName()).thenReturn(name);
        when(mockUserGroup.getDescription()).thenReturn(description);
        when(mockConfiguration.getConfigurationValue()).thenReturn("1000");
        when(userGroupRepository.countByTenantID(77)).thenReturn(5l);
        when(userGroupRepository.findByNameAndTenantID(name, TENANT_ID)).thenReturn(null);
        when(userGroupRepository.save(userGroup)).thenReturn(mockUserGroup);
        UserGroup savedUserGroup = null;
        try {
            savedUserGroup = userGroupService.createUserGroup(userGroup);
        } catch (DataValidationException exception) {
            fail("Should not throw exception when valid usergoup is saved");
        }
        assertNotNull(savedUserGroup);
        assertEquals(savedUserGroup.getTenantID(), TENANT_ID);
        assertTrue(savedUserGroup.getUserGroupId() != 0);
        assertEquals(savedUserGroup.getName(), name);
        assertEquals(savedUserGroup.getDescription(), description);
    }

    @Test
    public void updateWithInvalidGroupId() {
        String name = "test";
        String description = "invalid groupId";
        Integer userGroupId = 34;
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(description);

        when(userGroupRepository.findByUserGroupIdAndTenantID(userGroupId, TENANT_ID)).thenReturn(null);
        try {
            userGroupService.updateUserGroup(userGroupId, userGroup);
            fail("The createUserGroup should throw DataValidationException exception");
        } catch (DataValidationException exception) {
            assertTrue(exception.getMessage().equals(NO_MATCHING_USER));
        }
    }

    @Test
    public void updateWithInvalidGroupName() {
        String name = "test";
        String description = "invalid groupId";
        Integer userGroupId = 34;
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(description);


        when(userGroupRepository.findByUserGroupIdAndTenantID(userGroupId, TENANT_ID)).thenReturn(mockUserGroup);

        when(userGroupRepository.findByNameAndTenantID(name, TENANT_ID)).thenReturn(mockUserGroup);
        try {
            userGroupService.updateUserGroup(userGroupId, userGroup);
            fail("The createUserGroup should throw DataValidationException exception");
        } catch (DataValidationException exception) {
            Assert.assertTrue(exception.getMessage().equals(USER_EXISTS));
        }
    }

    @Test
    public void updateWithValidUserGroup() {
        String name = "test";
        String description = "invalid groupId";
        Integer userGroupId = 34;
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(description);


        when(userGroupRepository.findByUserGroupIdAndTenantID(userGroupId, TENANT_ID)).thenReturn(mockUserGroup);

        when(userGroupRepository.findByNameAndTenantID(name, TENANT_ID)).thenReturn(null);

        when(userGroupRepository.save(mockUserGroup)).thenReturn(mockUserGroup);
        try {
            UserGroup savedUserGroup = userGroupService.updateUserGroup(userGroupId, userGroup);
            assertNotNull(savedUserGroup);
            assertTrue(savedUserGroup.getUserGroupId() != 0);
        } catch (Exception exception) {
            fail("Cannot throw an exception in case of valid user group.");
        }
    }

    @Test
    public void delete_single_user_group_with_invalid_user_group_id() {
        Integer userGroupId = 94;
        when(userGroupRepository.findByUserGroupIdAndTenantID(userGroupId, TENANT_ID)).thenReturn(null);

        try {
            userGroupService.deleteUserGroup(userGroupId);
            fail("This method is bound to throw DatavalidationException");
        } catch (DataValidationException exception) {
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
    }

    @Test
    public void delete_multiple_user_user_groups_with_empty_user_group_id() {
        when(userGroupRepository.findByNameAndTenantID(anyString(), anyInt())).thenReturn(null);
        Set<Integer> userGroupIds = new HashSet();
        try {
            userGroupService.deleteUserGroups(userGroupIds);
            fail("This method is bound to throw DataValidationException");
        } catch (Exception exception) {
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
    }

    @Test
    public void delete_multiple_user_user_groups_with_single_invalid_user_group_id(){
        when(userGroupRepository.findByNameAndTenantID(anyString(), anyInt())).thenReturn(null);
        Set<Integer> userGroupIds = new HashSet();
        userGroupIds.add(4);

        System.out.println(userGroupIds);
        try {
            userGroupService.deleteUserGroups(userGroupIds);
            fail("This method is bound to throw DataValidationException");
        }catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
    }

    @Test
    public void delete_multiple_user_user_groups_with_multiple_user_group_id() {
        when(userGroupRepository.findByNameAndTenantID(anyString(), anyInt())).thenReturn(null);
        Set<Integer> userGroupIds = new HashSet();
        userGroupIds.add(67);
        userGroupIds.add(88);
        userGroupIds.add(87);
        userGroupIds.add(22);

        try {
            userGroupService.deleteUserGroups(userGroupIds);
        } catch (Exception exception) {
            fail("This method does not throw any exception in case of invalid user group id.");
        }
    }
    @Test
    public void deleteUserGroupValidTestCase() {
        Integer userGroupId = 94;
        when(userGroupRepository.findByUserGroupIdAndTenantID(userGroupId, TENANT_ID)).thenReturn(mockUserGroup);

        try {
            userGroupService.deleteUserGroup(userGroupId);
        } catch (Exception exception) {
            fail("This test case should not throw ResourceNotFoundException.");
        }
    }

    @Test
    public void deleteMultipleUserGroupValidTestCase() {
        Set<Integer> userGroupIds = new HashSet<>();
        userGroupIds.add(40);
        userGroupIds.add(41);
        userGroupIds.add(42);
        userGroupIds.add(43);
        userGroupIds.add(44);

        when(userGroupRepository.findByUserGroupIdAndTenantID(40, TENANT_ID)).thenReturn(mockUserGroup);
        when(userGroupRepository.findByUserGroupIdAndTenantID(41, TENANT_ID)).thenReturn(mockUserGroup);
        when(userGroupRepository.findByUserGroupIdAndTenantID(42, TENANT_ID)).thenReturn(mockUserGroup);
        when(userGroupRepository.findByUserGroupIdAndTenantID(43, TENANT_ID)).thenReturn(mockUserGroup);
        when(userGroupRepository.findByUserGroupIdAndTenantID(44, TENANT_ID)).thenReturn(mockUserGroup);

        try {
            userGroupService.deleteUserGroups(userGroupIds);
        } catch (Exception exception) {
            fail("This test case should not throw ResourceNotFoundException.");
        }
    }

    @Test
    public void fetchEmptyUserGroups() {

        PageRequest pageRequest = new PageRequest(10, 40, Sort.Direction.ASC, new String[]{"name"});
        when(userGroupRepository.findUserGroups(TENANT_ID, "not-available", "invalid-group", pageRequest)).thenReturn(new ArrayList<>());
        try {
            List<UserGroup> userGroupList = userGroupService.fetchUserGroups("not-available", "invalid-group", 10, 40, "ASC", new String[]{"name"});
            assertNotNull(userGroupList);
            assertTrue(userGroupList.isEmpty());
        } catch (Exception exception) {
            fail("This test case should not throw Exception.");
        }
    }

    @Test
    public void fetchUserGroupsWithDescription() {

        PageRequest pageRequest = new PageRequest(10, 40, Sort.Direction.ASC, new String[]{"description"});

        List<UserGroup> userGroupsList = new ArrayList<>();
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());

        when(userGroupRepository.findUserGroups(TENANT_ID, null, "description", pageRequest)).thenReturn(userGroupsList);

        try {
            List<UserGroup> userGroupList = userGroupService.fetchUserGroups(null, "description", 10, 40, "ASC", new String[]{"description"});
            assertNotNull(userGroupList);
            assertTrue(userGroupList.size() == 6);
        } catch (Exception exception) {
            fail("This test case should not throw Exception.");
        }
    }

    @Test
    public void fetchUserGroupsWithName() {
        PageRequest pageRequest = new PageRequest(10, 40, Sort.Direction.ASC, new String[]{"name"});

        List<UserGroup> userGroupsList = new ArrayList<>();
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());

        when(userGroupRepository.findUserGroups(TENANT_ID, "group-name", null, pageRequest)).thenReturn(userGroupsList);

        try {
            List<UserGroup> userGroupList = userGroupService.fetchUserGroups("group-name", null, 10, 40, "ASC", new String[]{"name"});
            assertNotNull(userGroupList);
            assertTrue(userGroupList.size() == 5);
        } catch (Exception exception) {
            fail("This test case should not throw Exception.");
        }
    }

    @Test
    public void fetchUserGroupsWithNameAnDescription() {
        PageRequest pageRequest = new PageRequest(10, 40, Sort.Direction.ASC, new String[]{"name", "description"});

        List<UserGroup> userGroupsList = new ArrayList<>();
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());

        when(userGroupRepository.findUserGroups(TENANT_ID, "group-name", "test-description", pageRequest)).thenReturn(userGroupsList);

        try {
            List<UserGroup> userGroupList = userGroupService.fetchUserGroups("group-name", "test-description", 10, 40, "ASC", new String[]{"name", "description"});
            assertNotNull(userGroupList);
            assertTrue(userGroupList.size() == 7);
        } catch (Exception exception) {
            fail("This test case should not throw Exception.");
        }
    }

    @Test
    public void fetchUserGroupsWithNoParams() {
        PageRequest pageRequest = new PageRequest(10, 40, Sort.Direction.ASC, new String[]{"name", "description"});

        List<UserGroup> userGroupsList = new ArrayList<>();
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());
        userGroupsList.add(new UserGroup());

        when(userGroupRepository.findUserGroups(TENANT_ID, null, null, pageRequest)).thenReturn(userGroupsList);

        try {
            List<UserGroup> userGroupList = userGroupService.fetchUserGroups(null, null, 10, 40, "ASC", new String[]{"name", "description"});
            assertNotNull(userGroupList);
            assertTrue(userGroupList.size() == 7);
        } catch (Exception exception) {
            fail("This test case should not throw Exception.");
        }
    }

    @Test
    public void fetchInValidUserGroupById() {
        when(userGroupRepository.findByUserGroupIdAndTenantID(54, TENANT_ID)).thenReturn(null);
        try {
            userGroupService.fetchUserGroupById(54);
            fail("This test case should  throw Exception.");
        } catch (Exception exception) {
            assertTrue(exception instanceof ResourceNotFoundException);
        }
    }

    @Test
    public void fetchValidUserGroupById() {
        UserGroup userGroup = new UserGroup();
        userGroup.setName(name);
        userGroup.setDescription(description);

        when(userGroupRepository.findByUserGroupIdAndTenantID(60, TENANT_ID)).thenReturn(userGroup);
        try {
            UserGroup fetchedUserGroup = userGroupService.fetchUserGroupById(60);
            assertNotNull(fetchedUserGroup);
            assertEquals(fetchedUserGroup.getName(), name);
            assertEquals(fetchedUserGroup.getDescription(), description);
        } catch (Exception exception) {
            fail("This test case should  throw Exception.");
        }
    }

    @Test
    public void assignEmptyGroupsToRoom() {
        Set<Integer> userGroupSet = new HashSet<>();
        try {
            userGroupService.updateGroupsForRoom(3, userGroupSet, UserGroupService.Operation.UPDATE);
            fail("This method is bound to throw DataValidationException");
        } catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
        assertTrue(userGroupSet.isEmpty());
        verify(roomRepository, never()).findRoomByRoomIdAndTenant(3, TENANT_ID);
        verify(userGroupRepository, never()).findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID);
    }

    @Test(expectedExceptions = com.vidyo.service.exceptions.DataValidationException.class)
    public void assignGroupsToInvalidRoom() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        when(roomRepository.findRoomByRoomIdAndTenant(3, TENANT_ID)).thenReturn(null);
        verify(userGroupRepository, Mockito.times(0)).findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID);
        userGroupService.updateGroupsForRoom(3, userGroupSet, UserGroupService.Operation.UPDATE);
    }


    @Test
    public void assignGroupsToValidRoom() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        when(roomRepository.findRoomByRoomIdAndTenant(3, TENANT_ID)).thenReturn(mockRoom);
        when(userGroupRepository.findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID)).thenReturn(new ArrayList<>());
        try {
            userGroupService.updateGroupsForRoom(3, userGroupSet, UserGroupService.Operation.UPDATE);
            fail("This method is bound to throw DataValidationException");
        } catch (Exception exception) {
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
        verify(userGroupRepository, never()).save(any(UserGroup.class));
    }


    @Test
    public void assignValidGroupsToValidRoom() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        List<UserGroup> userGroupsList = new ArrayList<>();
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);


        when(roomRepository.findRoomByRoomIdAndTenant(3, TENANT_ID)).thenReturn(mockRoom);
        when(userGroupRepository.findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID)).thenReturn(userGroupsList);
        userGroupService.updateGroupsForRoom(3, userGroupSet, UserGroupService.Operation.UPDATE);
        verify(userGroupRepository, Mockito.times(userGroupsList.size())).save(any(UserGroup.class));
        verify(mockUserGroup, Mockito.times(userGroupsList.size())).addRoom(any(Room.class));
    }

    @Test
    public void detachEmptyGroupsToRoom() {
        Set<Integer> userGroupSet = new HashSet<>();
        try {
            userGroupService.updateGroupsForRoom(3, userGroupSet, UserGroupService.Operation.DELETE);
            fail("This method is bound to throw DataValidationException");
        }catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
        assertTrue(userGroupSet.isEmpty());
        verify(roomRepository, never()).findRoomByRoomIdAndTenant(3, TENANT_ID);
        verify(userGroupRepository, never()).findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID);
    }

    @Test(expectedExceptions = com.vidyo.service.exceptions.DataValidationException.class)
    public void detachGroupsToInvalidRoom() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        when(roomRepository.findRoomByRoomIdAndTenant(3, TENANT_ID)).thenReturn(null);
        verify(userGroupRepository, Mockito.times(0)).findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID);
        userGroupService.updateGroupsForRoom(3, userGroupSet, UserGroupService.Operation.DELETE);
    }


    @Test
    public void detachGroupsToValidRoom() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        when(roomRepository.findRoomByRoomIdAndTenant(3, TENANT_ID)).thenReturn(mockRoom);
        when(userGroupRepository.findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID)).thenReturn(new ArrayList<>());
        try {
            userGroupService.updateGroupsForRoom(3, userGroupSet, UserGroupService.Operation.DELETE);
            fail("This method is bound to throw DataValidationException");
        } catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
        verify(userGroupRepository, never()).save(any(UserGroup.class));
    }


    @Test
    public void detachValidGroupsToValidRoom() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        List<UserGroup> userGroupsList = new ArrayList<>();
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);

        when(roomRepository.findRoomByRoomIdAndTenant(3, TENANT_ID)).thenReturn(mockRoom);
        when(userGroupRepository.findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID)).thenReturn(userGroupsList);
        userGroupService.updateGroupsForRoom(3, userGroupSet, UserGroupService.Operation.DELETE);
        verify(userGroupRepository, Mockito.times(userGroupsList.size())).save(any(UserGroup.class));
        verify(mockUserGroup, Mockito.times(userGroupsList.size())).removeRoom(any(Room.class));
    }

    @Test
    public void assignEmptyGroupsToMember() {
        Set<Integer> userGroupSet = new HashSet<>();
        try {
            userGroupService.updateGroupsForMember(3, userGroupSet, UserGroupService.Operation.UPDATE);
            fail("This is bound to throw DataValidationException ");
        }catch(Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
        assertTrue(userGroupSet.isEmpty());
        verify(memberRepository, never()).findByMemberIDAndTenantID(3, TENANT_ID);
        verify(userGroupRepository, never()).findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID);
    }

    @Test(expectedExceptions = com.vidyo.service.exceptions.DataValidationException.class)
    public void assignGroupsToInvalidMember() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        when(memberRepository.findByMemberIDAndTenantID(3, TENANT_ID)).thenReturn(null);
        verify(userGroupRepository, Mockito.times(0)).findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID);
        userGroupService.updateGroupsForMember(3, userGroupSet, UserGroupService.Operation.UPDATE);
    }


    @Test
    public void assignGroupsToValidMember() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        when(memberRepository.findByMemberIDAndTenantID(3, TENANT_ID)).thenReturn(mockMember);
        when(userGroupRepository.findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID)).thenReturn(new ArrayList<>());
        try {
            userGroupService.updateGroupsForMember(3, userGroupSet, UserGroupService.Operation.UPDATE);
            fail("This method is bound to throw DataValidationException");
        } catch(Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
        verify(userGroupRepository, never()).save(any(UserGroup.class));
    }


    @Test
    public void assignValidGroupsToValidMember() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        List<UserGroup> userGroupsList = new ArrayList<>();
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);


        when(memberRepository.findByMemberIDAndTenantID(3, TENANT_ID)).thenReturn(mockMember);
        when(userGroupRepository.findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID)).thenReturn(userGroupsList);
        userGroupService.updateGroupsForMember(3, userGroupSet, UserGroupService.Operation.UPDATE);
        verify(userGroupRepository, Mockito.times(userGroupsList.size())).save(any(UserGroup.class));
        verify(mockUserGroup, Mockito.times(userGroupsList.size())).addMember(any(Member.class));
    }


    @Test
    public void detachEmptyGroupsToMember() {
        Set<Integer> userGroupSet = new HashSet<>();
        try {
            userGroupService.updateGroupsForMember(3, userGroupSet, UserGroupService.Operation.DELETE);
            fail("This method is bound to throw DataValidationException");
        } catch (Exception exception){
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
        assertTrue(userGroupSet.isEmpty());
        verify(memberRepository, never()).findByMemberIDAndTenantID(3, TENANT_ID);
        verify(userGroupRepository, never()).findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID);
    }


    @Test(expectedExceptions = com.vidyo.service.exceptions.DataValidationException.class)
    public void detachGroupsToInvalidMember() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        when(memberRepository.findByMemberIDAndTenantID(3, TENANT_ID)).thenReturn(null);
        verify(userGroupRepository, never()).findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID);
        userGroupService.updateGroupsForRoom(3, userGroupSet, UserGroupService.Operation.DELETE);
    }


    @Test
    public void detachGroupsToValidMember() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);
        when(memberRepository.findByMemberIDAndTenantID(3, TENANT_ID)).thenReturn(mockMember);
        when(userGroupRepository.findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID)).thenReturn(new ArrayList<>());
        try {
            userGroupService.updateGroupsForMember(3, userGroupSet, UserGroupService.Operation.DELETE);
            fail("This method is bound to throw DataValidationException");
        } catch (Exception exception) {
            assertNotNull(exception);
            assertTrue(exception instanceof DataValidationException);
        }
        verify(userGroupRepository, never()).save(any(UserGroup.class));
    }


    @Test
    public void detachValidGroupsToValidMember() {
        Set<Integer> userGroupSet = new HashSet<>();
        userGroupSet.add(2);
        userGroupSet.add(3);
        userGroupSet.add(4);
        userGroupSet.add(5);

        List<UserGroup> userGroupsList = new ArrayList<>();
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);
        userGroupsList.add(mockUserGroup);

        when(memberRepository.findByMemberIDAndTenantID(3, TENANT_ID)).thenReturn(mockMember);
        when(userGroupRepository.findByUserGroupIdInAndTenantID(userGroupSet, TENANT_ID)).thenReturn(userGroupsList);
        userGroupService.updateGroupsForMember(3, userGroupSet, UserGroupService.Operation.DELETE);
        verify(userGroupRepository, Mockito.times(userGroupsList.size())).save(any(UserGroup.class));
        verify(mockUserGroup, Mockito.times(userGroupsList.size())).removeMember(any(Member.class));
    }


    @Test
    public void can_member_access_room_room_without_usergoups() {
        when(userGroupRepository.countByRooms_roomID(anyInt())).thenReturn(0l);
        boolean flag = userGroupService.canMemberAccessRoom(5, 4);
        assertTrue(flag);
        verify(userGroupRepository, never()).getCountOfCommonUserGroupsForRoomAndMember(anyInt(), anyInt());
    }

    @Test
    public void can_member_access_room_room_with_no_common_usergoups() {
        when(userGroupRepository.countByRooms_roomID(anyInt())).thenReturn(4l);
        when(userGroupRepository.getCountOfCommonUserGroupsForRoomAndMember(anyInt(), anyInt())).thenReturn(0);
        boolean flag = userGroupService.canMemberAccessRoom(5, 4);
        assertFalse(flag);
        verify(userGroupRepository, Mockito.times(1)).getCountOfCommonUserGroupsForRoomAndMember(anyInt(), anyInt());
    }

    @Test
    public void can_member_access_room_room_with_usergoups() {
        when(userGroupRepository.countByRooms_roomID(anyInt())).thenReturn(4l);
        when(userGroupRepository.getCountOfCommonUserGroupsForRoomAndMember(anyInt(), anyInt())).thenReturn(5);
        boolean flag = userGroupService.canMemberAccessRoom(5, 4);
        assertTrue(flag);
        verify(userGroupRepository, Mockito.times(1)).getCountOfCommonUserGroupsForRoomAndMember(anyInt(), anyInt());
    }

    @Test
    public void can_guest_access_room_with_groups_attached() {
        when(userGroupRepository.countByRooms_roomID(anyInt())).thenReturn(0l);
        boolean flag = userGroupService.canGuestAccessRoom(5);
        assertTrue(flag);
    }

    @Test
    public void can_guest_access_room_with_no_groups_attached() {
        when(userGroupRepository.countByRooms_roomID(anyInt())).thenReturn(14l);
        boolean flag = userGroupService.canGuestAccessRoom(4);
        assertFalse(flag);
    }

    @Test
    public void testFetchUserGroupsAttachedToRoomForInvalidRoomId() {
        when(roomRepository.findByRoomID(112)).thenReturn(null);

        Set<UserGroup> result = userGroupService.fetchUserGroupsAttachedToRoom(112);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 0);
        verify(roomRepository, Mockito.times(1)).findByRoomID(112);
    }

    @Test
    public void testFetchUserGroupsAttachedToRoomForValidRoomId() {
        Set<UserGroup> userGroups = new HashSet<UserGroup>();
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setName("development-group");
        userGroup1.setDescription("development-group");
        userGroups.add(userGroup1);

        Room room = new Room();
        room.setUserGroups(userGroups);

        when(roomRepository.findByRoomID(12)).thenReturn(room);

        Set<UserGroup> result = userGroupService.fetchUserGroupsAttachedToRoom(12);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1);
        verify(roomRepository, Mockito.times(1)).findByRoomID(12);
    }

    @Test
    public void testFetchUserGroupsAttachedToMemberForInvalidMemberId() {
        when(memberRepository.findByMemberIDAndTenantID(112, TENANT_ID)).thenReturn(null);

        Set<UserGroup> result = userGroupService.fetchUserGroupsAttachedToMember(112);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 0);
        verify(memberRepository, Mockito.times(1)).findByMemberIDAndTenantID(112, TENANT_ID);
    }

    @Test
    public void testFetchUserGroupsAttachedToMemberForValidMemberId() {
        Set<UserGroup> userGroups = new HashSet<UserGroup>();
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setName("development-group");
        userGroup1.setDescription("development-group");
        userGroups.add(userGroup1);

        Member member = new Member();
        member.setUserGroups(userGroups);

        when(memberRepository.findByMemberIDAndTenantID(12, TENANT_ID)).thenReturn(member);

        Set<UserGroup> result = userGroupService.fetchUserGroupsAttachedToMember(12);
        Assert.assertNotNull(result);
        Assert.assertEquals(result.size(), 1);
        verify(memberRepository, Mockito.times(1)).findByMemberIDAndTenantID(12, TENANT_ID);
    }

    @Test(expectedExceptions = DataValidationException.class, expectedExceptionsMessageRegExp = "Invalid memberId")
    public void testSetGroupsForMemberWithInvalidMemberId() {
        when(memberRepository.findByMemberIDAndTenantID(4, TENANT_ID)).thenReturn(null);

        userGroupService.setGroupsForMember(4, new HashSet<String>());

        verify(memberRepository, Mockito.times(1)).findByMemberIDAndTenantID(4, TENANT_ID);
    }

    @Test
    public void testSetGroupsForMemberWithNoGroups() {
        Set<UserGroup> userGroups = new HashSet<UserGroup>();
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setUserGroupId(1);
        userGroup1.setName("development-group");
        userGroup1.setDescription("development-group");
        userGroups.add(userGroup1);

        UserGroup userGroup2 = new UserGroup();
        userGroup2.setUserGroupId(2);
        userGroup2.setName("qa-group");
        userGroup2.setDescription("qa-group");
        userGroups.add(userGroup2);

        Member member = new Member();
        member.setUserGroups(userGroups);
        when(memberRepository.findByMemberIDAndTenantID(4, TENANT_ID)).thenReturn(member);

        when(userGroupRepository.save(any(UserGroup.class))).thenReturn(null);

        userGroupService.setGroupsForMember(4, new HashSet<String>());

        verify(memberRepository, Mockito.times(1)).findByMemberIDAndTenantID(4, TENANT_ID);
        verify(userGroupRepository, Mockito.times(2)).save(any(UserGroup.class));
    }

    @Test
    public void testSetGroupsForMemberWithNoGroupsCurrently() {
        Member member = new Member();
        when(memberRepository.findByMemberIDAndTenantID(4, TENANT_ID)).thenReturn(member);

        Set<String> userGroupNames = new HashSet<String>();
        userGroupNames.add("development-group");
        userGroupNames.add("qa-group");

        Set<UserGroup> userGroups = new HashSet<UserGroup>();
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setUserGroupId(1);
        userGroup1.setName("development-group");
        userGroup1.setDescription("development-group");
        userGroups.add(userGroup1);

        UserGroup userGroup2 = new UserGroup();
        userGroup2.setUserGroupId(2);
        userGroup2.setName("qa-group");
        userGroup2.setDescription("qa-group");
        userGroups.add(userGroup2);

        when(userGroupRepository.findByNameInAndTenantID(userGroupNames, TENANT_ID)).thenReturn(userGroups);

        when(userGroupRepository.save(any(UserGroup.class))).thenReturn(null);

        userGroupService.setGroupsForMember(4, userGroupNames);

        verify(memberRepository, Mockito.times(1)).findByMemberIDAndTenantID(4, TENANT_ID);
        verify(userGroupRepository, Mockito.times(1)).findByNameInAndTenantID(userGroupNames, TENANT_ID);
        verify(userGroupRepository, Mockito.times(2)).save(any(UserGroup.class));
    }

    @Test
    public void testSetGroupsForMemberWithNewGroups() {
        Set<UserGroup> userGroups = new HashSet<UserGroup>();
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setUserGroupId(1);
        userGroup1.setName("development-group");
        userGroup1.setDescription("development-group");
        userGroups.add(userGroup1);

        UserGroup userGroup2 = new UserGroup();
        userGroup2.setUserGroupId(2);
        userGroup2.setName("qa-group");
        userGroup2.setDescription("qa-group");
        userGroups.add(userGroup2);

        Member member = new Member();
        member.setUserGroups(userGroups);
        when(memberRepository.findByMemberIDAndTenantID(4, TENANT_ID)).thenReturn(member);

        Set<String> userGroupNames = new HashSet<String>();
        userGroupNames.add("development-group");
        userGroupNames.add("qa-group");
        userGroupNames.add("blr-group");

        when(userGroupRepository.findByNameInAndTenantID(userGroupNames, TENANT_ID)).thenReturn(userGroups);

        when(userGroupRepository.save(any(UserGroup.class))).thenReturn(new UserGroup());

        Configuration config = new Configuration();
        config.setConfigurationName("MAX_PERMITTED_USER_GROUPS_COUNT");
        config.setConfigurationValue("1000");
        when(mockSystemService.getConfiguration("MAX_PERMITTED_USER_GROUPS_COUNT")).thenReturn(config);

        when(userGroupRepository.countByTenantID(TENANT_ID)).thenReturn(10L);

        when(userGroupRepository.findByNameAndTenantID("blr-group", TENANT_ID)).thenReturn(null);

        userGroupService.setGroupsForMember(4, userGroupNames);

        verify(memberRepository, Mockito.times(1)).findByMemberIDAndTenantID(4, TENANT_ID);
        verify(userGroupRepository, Mockito.times(1)).findByNameInAndTenantID(userGroupNames, TENANT_ID);
        verify(mockSystemService, Mockito.times(1)).getConfiguration("MAX_PERMITTED_USER_GROUPS_COUNT");
        verify(userGroupRepository, Mockito.times(1)).countByTenantID(TENANT_ID);
        verify(userGroupRepository, Mockito.times(1)).findByNameAndTenantID("blr-group", TENANT_ID);
        verify(userGroupRepository, Mockito.times(2)).save(any(UserGroup.class));
    }

    @Test
    public void testSetGroupsForMemberWithDifferentGroups() {
        Set<UserGroup> userGroups = new HashSet<UserGroup>();
        UserGroup userGroup1 = new UserGroup();
        userGroup1.setUserGroupId(1);
        userGroup1.setName("development-group");
        userGroup1.setDescription("development-group");
        userGroups.add(userGroup1);

        UserGroup userGroup2 = new UserGroup();
        userGroup2.setUserGroupId(2);
        userGroup2.setName("qa-group");
        userGroup2.setDescription("qa-group");
        userGroups.add(userGroup2);

        Member member = new Member();
        member.setUserGroups(userGroups);
        when(memberRepository.findByMemberIDAndTenantID(4, TENANT_ID)).thenReturn(member);

        Set<String> userGroupNames = new HashSet<String>();
        userGroupNames.add("portal-group");
        userGroupNames.add("blr-group");

        Set<UserGroup> newUserGroups = new HashSet<UserGroup>();
        UserGroup userGroup3 = new UserGroup();
        userGroup3.setUserGroupId(3);
        userGroup3.setName("portal-group");
        userGroup3.setDescription("portal-group");
        newUserGroups.add(userGroup3);

        UserGroup userGroup4 = new UserGroup();
        userGroup4.setUserGroupId(4);
        userGroup4.setName("blr-group");
        userGroup4.setDescription("blr-group");
        newUserGroups.add(userGroup4);

        when(userGroupRepository.findByNameInAndTenantID(userGroupNames, TENANT_ID)).thenReturn(newUserGroups);

        when(userGroupRepository.save(any(UserGroup.class))).thenReturn(new UserGroup());

        userGroupService.setGroupsForMember(4, userGroupNames);

        verify(memberRepository, Mockito.times(1)).findByMemberIDAndTenantID(4, TENANT_ID);
        verify(userGroupRepository, Mockito.times(1)).findByNameInAndTenantID(userGroupNames, TENANT_ID);
        verify(userGroupRepository, Mockito.times(4)).save(any(UserGroup.class));
    }
}

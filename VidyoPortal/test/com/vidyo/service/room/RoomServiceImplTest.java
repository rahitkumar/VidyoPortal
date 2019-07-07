/**
 *
 */
package com.vidyo.service.room;

import static org.mockito.Mockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.bo.Browse;
import com.vidyo.bo.BrowseFilter;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.Entity;
import com.vidyo.bo.EntityFilter;
import com.vidyo.bo.Room;
import com.vidyo.bo.RoomFilter;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.profile.RoomProfile;
import com.vidyo.bo.room.RoomMini;
import com.vidyo.bo.search.simple.RoomIdSearchResult;
import com.vidyo.db.IRoomDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.RoomServiceImpl;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.room.request.RoomUpdateRequest;
import com.vidyo.service.system.SystemService;
import com.vidyo.utils.Generator;

/**
 * @author ganesh
 *
 */
public class RoomServiceImplTest {

	@InjectMocks
	private IRoomService roomService = new RoomServiceImpl(); // Class to be tested

	@Mock
	private IRoomDao roomDaoMock; // class to be mocked
    @Mock
    private ITenantService tenantServiceMock;
    @Mock
	private Room roomMock;
    @Mock
	private IRoomService roomServiceMock;
    @Mock
	private MemberService memberServiceMock;
    @Mock
	private ISystemService IsystemServiceMock;
    @Mock
	private SystemService systemServiceMock;
    @Mock
	private Generator generatorMock;


	@BeforeMethod(alwaysRun = true)
	public void injectDoubles() {
		MockitoAnnotations.initMocks(this); // This could be pulled up into a shared base class
	}

	/**
	 * List<Room> getRooms(RoomFilter filter)
	 */
	@Test(groups = { "roomService"})
	public void getRoomsValidRoomFilter() {
		RoomFilter roomFilter = new RoomFilter();
		TenantContext.setTenantId(1);
		List<Room> list = new ArrayList<>();

		Room room1 = new Room();
		Room room2 = new Room();

		list.add(room1);
		list.add(room2);

		when(roomDaoMock.getRooms(TenantContext.getTenantId(),roomFilter)).thenReturn(list);
		List<Room> returnVal = roomService.getRooms(roomFilter);

		assertEquals(returnVal,list);
	}

	@Test(groups = {"roomService"})
	public void getRoomsInvalidRoomFilter() {
		RoomFilter roomFilter = null;
		TenantContext.setTenantId(1);

		when(roomDaoMock.getRooms(1,roomFilter)).thenReturn(null);
		List<Room> returnVal = roomService.getRooms(roomFilter);

		assertEquals(returnVal,null);
	}

	/**
	 * Long getCountRooms(RoomFilter filter)
	 */
	@Test(groups = { "roomService"})
	public void getCountRoomsValidRoomFilter(){
		RoomFilter roomFilter = new RoomFilter();
		Long number = new Long(100);
		TenantContext.setTenantId(1);

		when(roomDaoMock.getCountRooms(1,roomFilter)).thenReturn(number);
		Long returnVal = roomService.getCountRooms(roomFilter);

		assertEquals(returnVal,number);
	}

	@Test(groups = { "roomService"})
	public void getCountRoomsInvalidRoomFilter(){
		RoomFilter roomFilter = new RoomFilter();
		TenantContext.setTenantId(1);

		when(roomDaoMock.getCountRooms(1,roomFilter)).thenReturn(null);
		Long returnVal = roomService.getCountRooms(roomFilter);

		assertEquals(returnVal,null);
	}

	/**
	 * Room getRoom(int roomID)
	 */

	@Test(groups = {"roomService"})
	public void getRoomWithValidRoomId() {
		int validRoomId = 10;
		Room room = new Room();
		room.setRoomID(10);
		TenantContext.setTenantId(1);
		when(roomDaoMock.getRoom(1,10)).thenReturn(room);
		Room returnVal = roomService.getRoom(validRoomId);
		assertEquals(returnVal,room);
	}

	@Test(groups = {"roomService"})
	public void getRoomWithInvalidRoomId() {
		int roomId = -10;
		TenantContext.setTenantId(1);

		when(roomDaoMock.getRoom(1,-10)).thenReturn(null);
		Room returnVal = roomService.getRoom(roomId);
		assertEquals(returnVal,null);
	}

	/**
	 * Room getRoom(String nameExt)
	 */
	@Test(groups = {"roomService"})
	public void getRoomWithValidNameExt() {
		String nameExt = "abc";
		TenantContext.setTenantId(1);
		Room room = new Room();

		when(roomDaoMock.getRoom(1,"abc")).thenReturn(room);
		Room returnVal = roomService.getRoom(nameExt);

		assertEquals(returnVal,room);
	}

	@Test(groups = {"roomService"})
	public void getRoomWithInvalidNameExt() {
		String nameExt = "";
		TenantContext.setTenantId(1);

		when(roomDaoMock.getRoom(1, "")).thenReturn(null);
		Room returnVal = roomService.getRoom(nameExt);

		assertEquals(returnVal, null);
	}

	/**
	 * Room getRoom(String nameExt, String tenant)
	 */
	@Test(groups = {"roomService"})
    public void getRoomWithValidExtAndEmptyTenant() {
	    String nameExt = new String("test");
	    Room room = new Room();

	    when(roomDaoMock.getRoom(1,nameExt)).thenReturn(room);
	    Room returnVal = roomService.getRoom(nameExt,null);

	    assertEquals(returnVal,room);
    }

	@Test(groups = {"roomService"})
	public void getRoomWithInvalidExtAndEmptyTenant() {
		String nameExt = new String("");

		when(roomDaoMock.getRoom(1,nameExt)).thenReturn(null);
		Room returnVal = roomService.getRoom("",null);

		assertEquals(returnVal,null);
	}

    @Test(expectedExceptions = NullPointerException.class,groups = {"roomService"})
    public void getRoomWithValidExtAndInvalidTenant() {
        String tenant = "abc";
        String nameExt = "test";
        when(tenantServiceMock.getTenant(tenant)).thenReturn(null);
        Room expectedRoom = null;
		expectedRoom = roomService.getRoom(nameExt,tenant);
    }

    @Test(groups = {"roomService"})
    public void getRoomeWithInvalidNameExtAndValidTanent() {
		String nameExt = new String("");
		String tenant = new String("abc");

		Tenant t = new Tenant();
		t.setTenantID(1);

		when(tenantServiceMock.getTenant(tenant)).thenReturn(t);
		when(roomDaoMock.getRoom(1,"")).thenReturn(null);

		Room returnVal = roomService.getRoom(nameExt,tenant);
		assertEquals(returnVal,null);
    }

	@Test(groups = {"roomService"})
	public void getRoomeWithValidNameExtAndValidTanent() {
		String nameExt = new String("");
		String tenant = new String("abc");

		Room room = new Room();
		Tenant t = new Tenant();
		t.setTenantID(1);

		when(tenantServiceMock.getTenant(tenant)).thenReturn(t);
		when(roomDaoMock.getRoom(1,"")).thenReturn(room);

		Room returnVal = roomService.getRoom(nameExt,tenant);
		assertEquals(returnVal,room);
	}

	/**
	 *
	 * Room getRoomByNameAndTenantName(String nameExt, String tenantName)
	 */

	@Test(groups = {"roomService"})
	public void getRoomByNameAndTenantNameWithValidExtAndValidTenantName() {
		String nameExt = new String("abc");
		String tenantName = new String("test");

		Room room = new Room();

		Tenant t = new Tenant();
		t.setTenantID(1);

		when(tenantServiceMock.getTenant("test")).thenReturn(t);
		when(roomDaoMock.getRoomByNameAndTenantId(1,"abc")).thenReturn(room);

		Room retrurnVal = roomService.getRoomByNameAndTenantName(nameExt,tenantName);
		assertEquals(retrurnVal,room);
	}

	@Test(groups = {"roomService"},expectedExceptions = NullPointerException.class)
	public void getRoomByNameAndTenantNameWithValidExtAndInvalidTenantName() {
		String nameExt = new String("abc");
		String tenantName = new String("");

		Tenant t = null;
		when(tenantServiceMock.getTenant(tenantName)).thenReturn(t);
		roomService.getRoomByNameAndTenantName(nameExt,tenantName);
	}

	@Test(groups = {"roomService"})
	public void getRoomByNameAndTenantNameWithInvalidExtAndValidTenantName() {
		String nameExt = "";
		String tenantName = "tenantName";

		Tenant t = new Tenant();
		t.setTenantID(1);

		String msg = "msg";
		DataAccessException e = new DataAccessException(msg) {};

		when(tenantServiceMock.getTenant(tenantName)).thenReturn(t);
		when(roomDaoMock.getRoomByNameAndTenantId(1,"")).thenThrow(e);

		assertEquals(roomService.getRoomByNameAndTenantName(nameExt,tenantName),null);
	}

	@Test(groups = {"roomService"},expectedExceptions = NullPointerException.class)
	public void getRoomByNameAndTeanantNameWithInvalidExtAndInvalidTeanantName() {
		String nameExt = new String("");
		String tenantName = new String("");

		Tenant t = null;
		when(tenantServiceMock.getTenant(tenantName)).thenReturn(t);

		roomService.getRoomByNameAndTenantName(nameExt, tenantName);
	}

	/**
	 *
	 * Room getRoomForKey(String key)
	 */
	@Test (groups = {"roomService"})
	public void getRoomForKeyWithValidKey(){
		String key = "key";

		TenantContext.setTenantId(1);
		Room room = new Room();
		room.setRoomID(2);

		when(roomDaoMock.getRoomForKey(1,"key")).thenReturn(room);

		assertEquals(roomService.getRoomForKey(key).getRoomID(),2);
	}

	@Test(groups = {"roomService"})
	public void getRoomForKeyWithInvalidKey() {
		String key = "";

		TenantContext.setTenantId(1);

		when(roomDaoMock.getRoomForKey(1,"")).thenReturn(null);

		assertEquals(roomService.getRoomForKey(key),null);
	}

	/**
	 * Room getRoomForModeratorKey(String key)
	 */
	@Test(groups = {"roomService"})
	public void getRoomForModeratorKeyWithValidKey(){
		String Moderatorkey = "Moderatorkey";

		TenantContext.setTenantId(1);
		Room room = new Room();
		room.setRoomID(2);

		when(roomDaoMock.getRoomForModeratorKey(1,"Moderatorkey")).thenReturn(room);

		assertEquals(roomService.getRoomForModeratorKey(Moderatorkey).getRoomID(),2);
	}

	@Test(groups = {"roomService"})
	public void getRoomForModeratorKeyWithInvalidKey() {
		String Moderatorkey = "";

		TenantContext.setTenantId(1);

		when(roomDaoMock.getRoomForModeratorKey(1,"")).thenReturn(null);

		assertEquals(roomService.getRoomForModeratorKey(Moderatorkey),null);
	}

	/**
	 * int updateRoom(int roomID, Room room)
	 */

	/**
	 *
	 * updatePublicRoomDescription(int roomID, String roomDescription)
	 */
	@Test(groups = {"roomService"})
	public void updatePublicRoomDescriptionWithValidIdAndValidDes(){
		int roomId = 1;
		String roomDescreption = "roomDescription";

		doReturn(3).when(roomDaoMock).updatePublicRoomDescription(1,"roomDescription");

		assertEquals(roomService.updatePublicRoomDescription(roomId,roomDescreption),3);
	}

	@Test(groups = {"roomService"})
	public void updatePublicRoomDescriptionWithInvalidId(){
		int roomId = -1;
		String roomDescreption = "roomDescription";

		doReturn(-5).when(roomDaoMock).updatePublicRoomDescription(-1,"roomDescription");

		assertEquals(roomService.updatePublicRoomDescription(roomId,roomDescreption),-5);
	}

	@Test(groups = {"roomService"})
	public void updatePublicRoomDescriptionWithInvalidDes(){
		int roomId = 1;
		String roomDescreption = "";

		doReturn(-5).when(roomDaoMock).updatePublicRoomDescription(1,"");

		assertEquals(roomService.updatePublicRoomDescription(roomId,roomDescreption),-5);
	}

	/**
	 *
	 * int insertRoom(Room room)
	 */

	/**
	 * int lockRoom(int roomID)
	 */
	@Test(groups = { "roomService" })
	public void lockRoomValidID() {
		int validRoomId = 1;
		TenantContext.setTenantId(1);
		when(roomDaoMock.lockRoom(1, validRoomId)).thenReturn(100);
		int returnVal = roomService.lockRoom(validRoomId);
		assertEquals(returnVal, 100);
	}

	@Test(groups = { "roomService" })
	public void lockRoomInvalidID() {
		int invalidRoomId = -100;
		when(roomDaoMock.lockRoom(1, -100)).thenReturn(0);
		TenantContext.setTenantId(1);
		int returnVal = roomService.lockRoom(invalidRoomId);
		assertEquals(returnVal, 0);
	}

	/**
	 *
	 * int unlockRoom(int roomID)
	 */
	@Test(groups = { "roomService"})
	public void unlockRoomValidID(){
		int ID = 5;
		TenantContext.setTenantId(1);

		when(roomDaoMock.unlockRoom(1,5)).thenReturn(1);

		assertEquals(roomService.unlockRoom(ID),1);
	}

	@Test(groups = { "roomService"})
	public void unlockRoomInvalidID() {
		int ID = -10;
		TenantContext.setTenantId(1);

		when(roomDaoMock.unlockRoom(1,-10)).thenReturn(0);

		assertEquals(roomService.unlockRoom(ID),0);
	}

	/**
	 *
	 * int muteRoom(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void muteRoomWithValidID(){
		int ID = 1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.muteRoom(1,1)).thenReturn(1);

		assertEquals(roomService.muteRoom(ID),1);
	}

	@Test(groups = {"roomService"})
	public void muteRoomWithInvalidID(){
		int ID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.muteRoom(1,-1)).thenReturn(-1);

		assertEquals(roomService.muteRoom(ID),-1);
	}

	/**
	 *
	 * int unmuteRoom(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void unmuteRoomWithValidID(){
		int ID = 1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.unmuteRoom(1,1)).thenReturn(1);

		assertEquals(roomService.unmuteRoom(ID),1);
	}

	@Test(groups = {"roomService"})
	public void unmuteRoomWithInvalidID() {
		int ID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.unmuteRoom(1,-1)).thenReturn(-1);

		assertEquals(roomService.unmuteRoom(ID),-1);
	}

	/**
	 *
	 * silenceRoom(int roomID)
	 */

	@Test(groups = {"roomService"})
	public void silenceRoomWithValidID(){
		int ID = 1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.silenceRoom(1,1)).thenReturn(1);

		assertEquals(roomService.silenceRoom(ID),1);
	}

	@Test(groups = {"roomService"})
	public void silenceRoomWithInvalidID() {
		int ID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.silenceRoom(1,-1)).thenReturn(-1);

		assertEquals(roomService.silenceRoom(ID),-1);
	}

	/**
	 *
	 * unsilenceRoom(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void unsilenceRoomWithValidID(){
		int ID = 1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.unsilenceRoom(1,1)).thenReturn(1);

		assertEquals(roomService.unsilenceRoom(ID),1);
	}

	@Test(groups = {"roomService"})
	public void unsilenceRoomWithInvalidID() {
		int ID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.unsilenceRoom(1,-1)).thenReturn(-1);

		assertEquals(roomService.unsilenceRoom(ID),-1);
	}

	/**
	 *
	 * muteRoomVideo(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void muteRoomVideoWithValidID(){
		int ID = 1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.muteRoomVideo(1,1)).thenReturn(1);

		assertEquals(roomService.muteRoomVideo(ID),1);
	}

	@Test(groups = {"roomService"})
	public void muteRoomVideoWithInvalidID() {
		int ID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.muteRoomVideo(1,-1)).thenReturn(-1);

		assertEquals(roomService.muteRoomVideo(ID),-1);
	}

	/**
	 *
	 * unmuteRoomVideo(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void unmuteRoomVideoWithValidID(){
		int ID = 1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.unmuteRoomVideo(1,1)).thenReturn(1);

		assertEquals(roomService.unmuteRoomVideo(ID),1);
	}

	@Test(groups = {"roomService"})
	public void unmuteRoomVideoWithInvalidID() {
		int ID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.unmuteRoomVideo(1,-1)).thenReturn(-1);

		assertEquals(roomService.unmuteRoomVideo(ID),-1);
	}

	/**
	 *
	 * silenceRoomVideo(int roomID
	 */
	@Test(groups = {"roomService"})
	public void silenceRoomVideoWithValidID(){
		int ID = 1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.silenceRoomVideo(1,1)).thenReturn(1);

		assertEquals(roomService.silenceRoomVideo(ID),1);
	}

	@Test(groups = {"roomService"})
	public void silenceRoomVideoWithInvalidID() {
		int ID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.silenceRoomVideo(1,-1)).thenReturn(-1);

		assertEquals(roomService.silenceRoomVideo(ID),-1);
	}

	/**
	 *
	 * unsilenceRoomVideo(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void unsilenceRoomVideoWithValidID(){
		int ID = 1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.unsilenceRoomVideo(1,1)).thenReturn(1);

		assertEquals(roomService.unsilenceRoomVideo(ID),1);
	}

	@Test(groups = {"roomService"})
	public void unsilenceRoomVideoWithInvalidID() {
		int ID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.unsilenceRoomVideo(1,-1)).thenReturn(-1);

		assertEquals(roomService.unsilenceRoomVideo(ID),-1);
	}

	/**
	 *
	 * resetRoomState(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void resetRoomStateWithValidID(){
		int ID = 1;

		when(roomDaoMock.resetRoomState(1)).thenReturn(1);

		assertEquals(roomService.resetRoomState(ID),1);
	}

	@Test(groups = {"roomService"})
	public void resetRoomStateWithInvalidID() {
		int ID = -1;

		when(roomDaoMock.resetRoomState(-1)).thenReturn(-1);

		assertEquals(roomService.resetRoomState(-1),-1);
	}

	/**
	 *
	 * boolean isRoomExistForRoomName(String roomName, int roomID)
	 */
	@Test(groups = {"roomService"})
	public void isRoomExistForRoomNameWithValidNameAndValidID() {
		String roomName = "roomName";
		int ID = 1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.isRoomExistForRoomName(1,"roomName",1)).thenReturn(true);

		assert(roomService.isRoomExistForRoomName(roomName,ID));
	}

	@Test(groups = {"roomService"})
	public void isRoomExistForRoomNameWithInvalidName() {
		String roomName = "";
		int ID = 1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.isRoomExistForRoomName(1,"",1)).thenReturn(false);

		assert(!roomService.isRoomExistForRoomName(roomName,ID));
	}

	@Test(groups = {"roomService"})
	public void isRoomExistForRoomNameWithInvalidID() {
		String roomName = "roomName";
		int ID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.isRoomExistForRoomName(-1,"",1)).thenReturn(false);

		assert(!roomService.isRoomExistForRoomName(roomName,ID));
	}

	/**
	 *
	 * boolean isRoomExistForRoomExtNumber(String roomExtNumber, int roomID)
	 */
	@Test(groups = {"roomService"})
	public void isRoomExistForRoomExtNumberWithValidExtNumberAndValidID(){
		String ExtNumber = "ExtNumber";
		int roomID = 1;

		when(roomDaoMock.isRoomExistForRoomExtNumber("ExtNumber",1)).thenReturn(true);

		assert roomService.isRoomExistForRoomExtNumber(ExtNumber,roomID);
	}

	@Test(groups = {"roomService"})
	public void isRoomExistForRoomExtNumberWithInvalidExtNumber(){
		String ExtNumber = "";
		int roomID = 1;

		when(roomDaoMock.isRoomExistForRoomExtNumber("",1)).thenReturn(false);

		assert !roomService.isRoomExistForRoomExtNumber(ExtNumber,roomID);
	}

	@Test(groups = {"roomService"})
	public void isRoomExistForRoomExtNumberWithInvalidRoomID() {
		String ExtNumber = "ExtNumber";
		int roomID = -1;

		when(roomDaoMock.isRoomExistForRoomExtNumber("ExtNumber",-1)).thenReturn(false);

		assert !roomService.isRoomExistForRoomExtNumber(ExtNumber,roomID);
	}

	/**
	 *
	 * boolean isExtensionExistForLegacyExtNumber(String roomExtNumber, int roomID)
	 */
	@Test(groups = {"roomService"})
	public void isExtensionExistForLegacyExtNumberWithValidExtNumberAndValidRoomID() {
		String ExtNumber = "ExtNumber";
		int RoomID = 1;

		when(roomDaoMock.isExtensionExistForLegacyExtNumber("ExtNumber",1)).thenReturn(true);

		assert roomService.isExtensionExistForLegacyExtNumber(ExtNumber,RoomID);
	}

	@Test(groups = {"roomService"})
	public void isExtensionExistForLegacyExtNumberWithInvalidExtNumber() {
		String ExtNumber = "";
		int RoomID = 1;

		when(roomDaoMock.isExtensionExistForLegacyExtNumber("",1)).thenReturn(false);

		assert !roomService.isExtensionExistForLegacyExtNumber(ExtNumber,RoomID);
	}

	@Test(groups = {"roomService"})
	public void isExtensionExistForLegacyExtNumberWithInvalidRoomID() {
		String ExtNumber = "ExtNumber";
		int RoomID = -1;

		when(roomDaoMock.isExtensionExistForLegacyExtNumber("ExtNumber",-1)).thenReturn(false);

		assert !roomService.isExtensionExistForLegacyExtNumber(ExtNumber,RoomID);
	}

	/**
	 *
	 * List<Long> getRoomsForOwnerID(int ownerID)
	 */
	@Test(groups = {"roomService"})
	public void getRoomsForOwnerIDWithValidID(){
		int ownerID = 1;
		TenantContext.setTenantId(1);

		Long rc1 = new Long(1);
		Long rc2 = new Long(2);
		List<Long> rc = new ArrayList<Long>();
		rc.add(rc1);
		rc.add(rc2);

		when(roomDaoMock.getRoomsForOwnerID(1,1)).thenReturn(rc);

		assertEquals(roomService.getRoomsForOwnerID(ownerID),rc);
	}

	@Test(groups = {"roomService"})
	public void getRoomsForOwenerIDWithInvalidID() {
		int ownerID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.getRoomsForOwnerID(1,-1)).thenReturn(null);

		assertEquals(roomService.getRoomsForOwnerID(ownerID),null);
	}

	/**
	 *
	 * Room getPersonalRoomForOwnerID(int ownerID)
	 */
	@Test(groups = {"roomService"})
	public void getPersonalRoomForOwnerIDWithValidOwnerID(){
		int ownerID = 1;
		TenantContext.setTenantId(1);

		Room room = new Room();

		when(roomDaoMock.getPersonalRoomForOwnerID(1,1)).thenReturn(room);

		assertEquals(roomService.getPersonalRoomForOwnerID(ownerID),room);
	}

	@Test(groups = {"roomService"})
	public void getPersonalRoomForOwnerIDWithInvalidOwnerID(){
		int ownerID = -1;
		TenantContext.setTenantId(1);

		when(roomDaoMock.getPersonalRoomForOwnerID(1,-1)).thenReturn(null);

		assertEquals(roomService.getPersonalRoomForOwnerID(ownerID),null);
	}

	/**
	 *
	 * getPersonalOrLegacyRoomForOwnerID(int tenant, int ownerID)
	 */
	@Test(groups = {"roomService"})
	public void getPersonalOrLegacyRoomForOwnerIDWithValidTeanatAndValidOwnerID() {
		int tenant = 1;
		int ownerID = 2;

		Room room = new Room();

		when(roomDaoMock.getPersonalOrLegacyRoomForOwnerID(1,2)).thenReturn(room);

		assertEquals(roomService.getPersonalOrLegacyRoomForOwnerID(tenant,ownerID),room);
	}

	@Test(groups = {"roomService"})
	public void getPersonalOrLegacyRoomForOwnerIDWithInvalidTenant() {
		int tenant = -1;
		int ownerID = 2;

		String msg = "msg";
		DataAccessException e = new DataAccessException(msg) {};

		when(roomDaoMock.getPersonalOrLegacyRoomForOwnerID(-1,2)).thenThrow(e);

		assertEquals(roomService.getPersonalOrLegacyRoomForOwnerID(tenant,ownerID),null);
	}

	@Test(groups = {"roomService"})
	public void getPersonalOrLegacyRoomForOwnerIDWithInvalidOwnerID() {
		int tenant = 1;
		int ownerID = -2;

		String msg = "msg";
		DataAccessException e = new DataAccessException(msg) {};

		when(roomDaoMock.getPersonalOrLegacyRoomForOwnerID(1,-2)).thenThrow(e);

		assertEquals(roomService.getPersonalOrLegacyRoomForOwnerID(tenant,ownerID),null);
	}

	/**
	 *
	 * List<Browse> getBrowse(BrowseFilter filter, User user)
	 */
	/*@Test(groups = {"roomService"})
	public void getBrowseWithValidFilterAndValidUser() {
		BrowseFilter filter = new BrowseFilter();
		User user = new User();
		filter.setDir("dir");
		user.setLangID(1);

		Browse b1 = new Browse();
		Browse b2 = new Browse();
		List<Browse> list = new ArrayList<>();
		list.add(b1);
		list.add(b2);

		TenantContext.setTenantId(1);
		filter.setDir("Dir");
		user.setLangID(1);

		when(roomDaoMock.getBrowse(1,filter,user)).thenReturn(list);

		assertEquals(roomService.getBrowse(filter,user),list);
	}

	@Test(groups = {"roomService"})
	public void getBrowseWithInvalidFilter() {
		BrowseFilter filter = new BrowseFilter();
		User user = new User();
		user.setLangID(1);

		TenantContext.setTenantId(1);
		user.setLangID(1);

		when(roomDaoMock.getBrowse(1,filter,user)).thenReturn(null);

		assertEquals(roomService.getBrowse(filter,user),null);
	}

	@Test(groups = {"roomService"})
	public void getBrowseWithInvalidUser(){
		BrowseFilter filter = new BrowseFilter();
		User user = new User();

		filter.setDir("dir");

		TenantContext.setTenantId(1);
		filter.setDir("Dir");

		when(roomDaoMock.getBrowse(1,filter,user)).thenReturn(null);

		assertEquals(roomService.getBrowse(filter,user),null);
	}

	@Test(groups = {"roomService"})
	public void getCountBrowseWithValidFilterAndValidUser() {
		BrowseFilter filter = new BrowseFilter();
		User user = new User();
		filter.setDir("dir");
		user.setLangID(1);

		TenantContext.setTenantId(1);
		Long number = new Long(1);

		when(roomDaoMock.getCountBrowse(1,filter,user)).thenReturn(number);

		assertEquals(roomService.getCountBrowse(filter,user),number);
	}

	@Test(groups = {"roomService"})
	public void getCountBrowseWithInvalidFilter(){
		BrowseFilter filter = new BrowseFilter();
		User user = new User();

		user.setLangID(1);

		TenantContext.setTenantId(1);

		when(roomDaoMock.getCountBrowse(1,filter,user)).thenReturn(null);

		assertEquals(roomService.getCountBrowse(filter,user),null);
	}

	@Test(groups = {"roomService"})
	public void getCountBrowseWithInvalidUser() {
		BrowseFilter filter = new BrowseFilter();
		User user = new User();

		filter.setDir("dir");

		TenantContext.setTenantId(1);

		when(roomDaoMock.getCountBrowse(1,filter,user)).thenReturn(null);

		assertEquals(roomService.getCountBrowse(filter,user),null);
	}*/

	/**
	 *
	 * updateRoomPIN(Room room)
	 */
	@Test(groups = {"roomService"})
	public void updateRoomPINWithValidRoom(){
		Room room = new Room();
		room.setRoomID(1);
		TenantContext.setTenantId(1);

		when(roomDaoMock.updateRoomPIN(1,room)).thenReturn(1);

		assertEquals(roomService.updateRoomPIN(room),1);
	}

	@Test(groups = {"roomService"})
	public void updateRoomPINWithInvalidRoom() {
		Room room = new Room();
		TenantContext.setTenantId(1);

		when(roomDaoMock.updateRoomPIN(1,room)).thenReturn(-1);

		assertEquals(roomService.updateRoomPIN(room),-1);
	}

	/**
	 *
	 * int generateRoomKey(Room room)
	 */
	@Test(groups = {"roomService"})
	public void generateRoomKeyWithValidRoom() {
		Room room = new Room();
		room.setRoomID(1);

		TenantContext.setTenantId(1);

		when(roomDaoMock.updateRoomKey(1,room)).thenReturn(1);

		assertEquals(roomService.generateRoomKey(room),1);
	}

	@Test(groups = {"roomService"})
	public void generateRoomKeyWithInvalidRoom() {
		Room room = new Room();
		TenantContext.setTenantId(1);

		when(roomDaoMock.updateRoomKey(1,room)).thenReturn(-1);

		assertEquals(roomService.generateRoomKey(room),-1);
	}

    /**
     *
     * int removeRoomKey(Room room)
     */
    @Test(groups = {"roomService"})
    public void removeRoomKeyWithValidRoom() {
        Room room = new Room();
        room.setRoomID(1);
        room.setRoomKey("key");
        TenantContext.setTenantId(1);

        when(roomDaoMock.updateRoomKey(1,room)).thenReturn(1);

        assertEquals(roomService.removeRoomKey(room),1);
    }

    @Test(groups = {"roomService"})
    public void removeRoomKeyWithInvalidRoom() {
        Room room = new Room();
        TenantContext.setTenantId(1);

        when(roomDaoMock.updateRoomKey(1,room)).thenReturn(-1);

        assertEquals(roomService.removeRoomKey(room),-1);
    }

	/**
	 *
	 * int generateModeratorKey(Room room)
	 */
	@Test(groups = {"roomService"})
	public void generateModeratorKeyWithValidRoom() {
		Room room = new Room();

		room.setRoomID(1);
		TenantContext.setTenantId(1);

		when(roomDaoMock.updateModeratorKey(1,room)).thenReturn(1);

		assertEquals(roomService.generateModeratorKey(room),1);
	}

	@Test(groups = {"roomService"})
	public void generateModeratorKeyWithInvalidRoom() {
		Room room = new Room();

		room.setRoomID(1);
		TenantContext.setTenantId(1);

		when(roomDaoMock.updateModeratorKey(1,room)).thenReturn(-1);

		assertEquals(roomService.generateModeratorKey(room),-1);
	}

	/**
	 *
     * int removeModeratorKey(Room room)
     */
    @Test(groups = {"roomService"})
    public void removeModeratorKeyWithValidRoom() {
        Room room = new Room();
        room.setRoomID(1);
        room.setRoomModeratorKey("key");
        TenantContext.setTenantId(1);

        when(roomDaoMock.updateModeratorKey(1,room)).thenReturn(1);

        assertEquals(roomService.removeModeratorKey(room),1);
    }

    @Test(groups = {"roomService"})
    public void removeModeratorKeyWithInvalidRoom() {
        Room room = new Room();
        TenantContext.setTenantId(1);

        when(roomDaoMock.updateModeratorKey(1,room)).thenReturn(-1);

        assertEquals(roomService.removeModeratorKey(room),-1);
    }

    /**
     *
     * List<Entity> getEntity(EntityFilter filter, User user)
     */
    @Test(groups = {"roomService"})
    public void getEntityWithValidFilter() {
        EntityFilter filter = new EntityFilter();
        User user = new User();
        filter.setDir("dir");
        user.setLangID(1);
        TenantContext.setTenantId(1);

        Entity e1 = new Entity();
        Entity e2 = new Entity();
        List<Entity> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);

        when(roomService.getEntity(filter,user,false)).thenReturn(list);
        assertEquals(roomService.getEntity(filter,user),list);
    }

    @Test(groups = {"roomService"})
    public void getEntityWithInvalidFilter() {
        EntityFilter filter = new EntityFilter();
        User user = new User();
        TenantContext.setTenantId(1);
        user.setLangID(1);

        Entity e1 = new Entity();
        Entity e2 = new Entity();
        List<Entity> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);

        when(roomService.getEntity(filter,user,false)).thenReturn(null);

        assertEquals(roomService.getEntity(filter,user),null);
    }

    @Test(groups = {"roomService"})
    public void getEntityWithInvalidUser() {
        EntityFilter filter = new EntityFilter();
        User user = new User();
        TenantContext.setTenantId(1);
        filter.setDir("dir");


        Entity e1 = new Entity();
        Entity e2 = new Entity();
        List<Entity> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);

        when(roomService.getEntity(filter,user,false)).thenReturn(null);

        assertEquals(roomService.getEntity(filter,user),null);
    }

    /**
     *
     * Long getCountEntity(EntityFilter filter, User user)
     */

    /**
     *
     * List<Entity> getEntityByOwnerID(EntityFilter filter, User user, int ownerID)
     */

    @Test(groups = {"roomService"})
    public void getEntityByOwnerIDWithValidFilterAndValidUserAndValidOwenerID() {
        EntityFilter filter = new EntityFilter();
        User user = new User();
        int ownerID = 1;
        TenantContext.setTenantId(1);

        filter.setDir("dit");
        user.setLangID(1);
        Entity e1 = new Entity();
        Entity e2 = new Entity();
        List<Entity> list = new ArrayList<>();
        list.add(e1);
        list.add(e2);

        when(roomDaoMock.getEntityByOwnerID(1,filter,user,1)).thenReturn(list);

        assertEquals(roomService.getEntityByOwnerID(filter,user,ownerID),list);
    }

    @Test(groups = {"roomService"})
	public void getEntityByOwnerIDWithInvalidFilter(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		int ownerID = 1;
		TenantContext.setTenantId(1);

		user.setLangID(1);

		when(roomDaoMock.getEntityByOwnerID(1,filter,user,1)).thenReturn(null);

		assertEquals(roomService.getEntityByOwnerID(filter,user,ownerID),null);
	}

	@Test(groups = {"roomService"})
	public void getEntityByOwnerIDWithInvalidUser(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		int ownerID = 1;
		TenantContext.setTenantId(1);

		filter.setDir("dir");

		when(roomDaoMock.getEntityByOwnerID(1,filter,user,1)).thenReturn(null);

		assertEquals(roomService.getEntityByOwnerID(filter,user,ownerID),null);
	}

	@Test(groups = {"roomService"})
	public void getEntityByOwnerIDWithInvalidOwenerID() {
		EntityFilter filter = new EntityFilter();
		User user = new User();
		int ownerID = -1;
		TenantContext.setTenantId(1);

		filter.setDir("dit");
		user.setLangID(1);

		when(roomDaoMock.getEntityByOwnerID(1,filter,user,-1)).thenReturn(null);

		assertEquals(roomService.getEntityByOwnerID(filter,user,ownerID),null);
	}

	/**
	 *
	 * Long getCountEntityByOwnerID(EntityFilter filter, User user, int ownerID
	 */
	@Test(groups = {"roomService"})
	public void getCountEntityByOwnerIDWithValidEntityFilterAndValidUserAndValidOwnerID(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		int ownerID = 1;
		TenantContext.setTenantId(1);

		filter.setDir("dit");
		user.setLangID(1);
		Long number = new Long(1);

		when(roomDaoMock.getCountEntityByOwnerID(1,filter,user,1)).thenReturn(number);

		assertEquals(roomService.getCountEntityByOwnerID(filter,user,ownerID),number);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityByOwnerIDWithInvalidEntityFilter(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		int ownerID = 1;

		TenantContext.setTenantId(1);
		user.setLangID(1);

		when(roomDaoMock.getCountEntityByOwnerID(1,filter,user,1)).thenReturn(null);

		assertEquals(roomService.getCountEntityByOwnerID(filter,user,ownerID),null);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityByOwnerIDWithInvalidUser(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		int ownerID = 1;

		TenantContext.setTenantId(1);
		filter.setDir("dit");

		when(roomDaoMock.getCountEntityByOwnerID(1,filter,user,1)).thenReturn(null);

		assertEquals(roomService.getCountEntityByOwnerID(filter,user,ownerID),null);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityByOwnerIDWithInvalidOwnerID(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		int ownerID = -1;

		TenantContext.setTenantId(1);
		filter.setDir("dir");
		user.setLangID(1);

		when(roomDaoMock.getCountEntityByOwnerID(1,filter,user,-1)).thenReturn(null);

		assertEquals(roomService.getCountEntityByOwnerID(filter,user,ownerID),null);
	}

	/**
	 *
	 * List<Entity> getEntityByEmail(EntityFilter filter, User user, String emailAddress)
	 */
	@Test(groups = {"roomService"})
	public void getEntityByEmailWithValidEntityFilterAndValidUserAndValidEmailAddress(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		String email = "email";

		TenantContext.setTenantId(1);
		filter.setDir("dit");
		user.setLangID(1);
		Entity e1 = new Entity();
		Entity e2 = new Entity();
		List<Entity> list = new ArrayList<>();
		list.add(e1);
		list.add(e2);


		when(roomDaoMock.getEntityByEmail(1,filter,user,"email")).thenReturn(list);

		assertEquals(roomService.getEntityByEmail(filter,user,email),list);
	}

	@Test(groups = {"roomService"})
	public void getEntityByEmailWithInvalidEntityFilter(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		String email = "email";

		TenantContext.setTenantId(1);
		user.setLangID(1);

		when(roomDaoMock.getEntityByEmail(1,filter,user,"email")).thenReturn(null);

		assertEquals(roomService.getEntityByEmail(filter,user,email),null);
	}

	@Test(groups = {"roomService"})
	public void getEntityByEmailWithInvalidUser(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		String email = "email";

		TenantContext.setTenantId(1);
		filter.setDir("dir");

		when(roomDaoMock.getEntityByEmail(1,filter,user,"email")).thenReturn(null);

		assertEquals(roomService.getEntityByEmail(filter,user,email),null);
	}

	@Test(groups = {"roomService"})
	public void getEntityByEmailWithInvalidEmail(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		String email = "";

		TenantContext.setTenantId(1);
		filter.setDir("dir");
		user.setLangID(1);

		when(roomDaoMock.getEntityByEmail(1,filter,user,"")).thenReturn(null);

		assertEquals(roomService.getEntityByEmail(filter,user,email),null);
	}

	/**
	 *
	 * Long getCountEntityByEmail(EntityFilter filter, User user, String emailAddress)
	 */
	@Test(groups = {"roomService"})
	public void getCountEntityByEmailWithValidEntityFilterAndValidUserAndValidEmailAddress(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		String email = "email";

		TenantContext.setTenantId(1);
		filter.setDir("dir");
		user.setLangID(1);
		Long num = new Long(1);

		TenantContext.setTenantId(1);

		when(roomDaoMock.getCountEntityByEmail(1,filter,user,"email")).thenReturn(num);

		assertEquals(roomService.getCountEntityByEmail(filter,user,email),num);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityByEmailWithInvalidUser() {
		EntityFilter filter = new EntityFilter();
		User user = new User();
		String email = "email";

		TenantContext.setTenantId(1);
		filter.setDir("dir");
		Long num = new Long(0);

		when(roomDaoMock.getCountEntityByEmail(1,filter,user,"email")).thenReturn(num);

		assertEquals(roomService.getCountEntityByEmail(filter,user,email),num);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityByEmailWithInvalidEntityFilter() {
		EntityFilter filter = new EntityFilter();
		User user = new User();
		String email = "email";

		TenantContext.setTenantId(1);
		user.setLangID(1);
		Long num = new Long(0);

		when(roomDaoMock.getCountEntityByEmail(1,filter,user,"email")).thenReturn(num);

		assertEquals(roomService.getCountEntityByEmail(filter,user,email),num);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityByEmailWithInvalidEmail() {
		EntityFilter filter = new EntityFilter();
		User user = new User();
		String email = "";

		TenantContext.setTenantId(1);
		user.setLangID(1);
		filter.setDir("dir");
		Long num = new Long(0);

		when(roomDaoMock.getCountEntityByEmail(1,filter,user,"")).thenReturn(num);

		assertEquals(roomService.getCountEntityByEmail(filter,user,email),num);
	}

	/**
	 *
	 * List<Entity> getEntity(EntityFilter filter, User user, boolean excludeRooms)
	 */
	@Test(groups = {"roomService"})
	public void getCountEntityByEmailWithValidFilterAndValidUserAndDoExcludeRooms() {
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		TenantContext.setTenantId(1);
		user.setLangID(1);
		filter.setDir("dir");
		Entity e1 = new Entity();
		Entity e2 = new Entity();
		List<Entity> list = new ArrayList<>();
		list.add(e1);
		list.add(e2);

		when(roomDaoMock.getEntities(1,filter,user,true,null,0)).thenReturn(list);

		List<Entity> returnVal = roomService.getEntity(filter,user,excludeRooms);
		assertEquals(returnVal,list);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityByEmailWithInvalidFilter() {
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		TenantContext.setTenantId(1);
		user.setLangID(1);

		when(roomDaoMock.getEntities(1,filter,user,true,null,0)).thenReturn(null);

		List<Entity> returnVal = roomService.getEntity(filter,user,excludeRooms);
		assertEquals(returnVal,null);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityByEmailWithInvalidUserAndDoExcludeRooms() {
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		TenantContext.setTenantId(1);
		filter.setDir("dir");

		when(roomDaoMock.getEntities(1,filter,user,true,null,0)).thenReturn(null);

		List<Entity> returnVal = roomService.getEntity(filter,user,excludeRooms);
		assertEquals(returnVal,null);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityByEmailNotExcludeRooms() {
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = false;

		TenantContext.setTenantId(1);
		filter.setDir("dir");
		user.setLangID(1);

		when(roomDaoMock.getEntities(1,filter,user,false,null,0)).thenReturn(null);

		List<Entity> returnVal = roomService.getEntity(filter,user,excludeRooms);
		assertEquals(returnVal,null);
	}

	/**
	 *
	 * Long getCountEntity(EntityFilter filter, User user, boolean excludeRooms)
	 */
	@Test(groups = {"roomService"})
	public void getCountEntityWithValidFilterAndValidUserAndDoExculdeRooms(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		TenantContext.setTenantId(1);
		filter.setDir("dir");
		user.setLangID(1);

		Long num = new Long(1);

		when(roomDaoMock.getCountEntity(1,filter,user,true,null,0)).thenReturn(num);

		assertEquals(roomService.getCountEntity(filter,user,excludeRooms),num);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityWithInvalidFilter(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		TenantContext.setTenantId(1);
		user.setLangID(1);

		Long num = new Long(0);

		when(roomDaoMock.getCountEntity(1,filter,user,true,null,0)).thenReturn(num);

		assertEquals(roomService.getCountEntity(filter,user,excludeRooms),num);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityWithInvalidUser(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		TenantContext.setTenantId(1);
		filter.setDir("dir");
		Long num = new Long(0);

		when(roomDaoMock.getCountEntity(1,filter,user,true,null,0)).thenReturn(num);

		assertEquals(roomService.getCountEntity(filter,user,excludeRooms),num);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityNotExcludeRooms(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = false;

		TenantContext.setTenantId(1);
		filter.setDir("dir");
		user.setLangID(1);
		Long num = new Long(0);

		when(roomDaoMock.getCountEntity(1,filter,user,false,null,0)).thenReturn(num);

		assertEquals(roomService.getCountEntity(filter,user,excludeRooms),num);
	}

	/**
	 *
	 * List<Entity> getRoomEntities(int ownerID)
	 */
	@Test(groups = {"roomService"})
	public void getRoomEntitiesWithValidOwenerIDAndRoomScheduled(){
		int ownerID = 1;

		Entity e1 = new Entity();
		Entity e2 = new Entity();
		Entity e3 = new Entity();
		e1.setRoomType("Scheduled");
		e2.setRoomType("Unscheduled");

		List<Entity> list = new ArrayList<>();
		list.add(e1);
		list.add(e2);

		TenantContext.setTenantId(1);

		when(roomDaoMock.getRoomEntitiesForOwnerID(1,1)).thenReturn(list);
		when(roomServiceMock.overrideScheduledRoomProperties(e1)).thenReturn(e3);

		assertEquals(roomService.getRoomEntities(ownerID),list);
	}

	@Test(groups = {"roomService"})
	public void getRoomEntitiesWithValidOwenerIDAndRoomUnScheduled(){
		int ownerID = 1;

		Entity e1 = new Entity();
		Entity e2 = new Entity();
		Entity e3 = new Entity();
		e1.setRoomType("Unscheduled");
		e2.setRoomType("Unscheduled");

		List<Entity> list = new ArrayList<>();
		list.add(e1);
		list.add(e2);

		TenantContext.setTenantId(1);

		when(roomDaoMock.getRoomEntitiesForOwnerID(1,1)).thenReturn(list);
		when(roomServiceMock.overrideScheduledRoomProperties(e2)).thenReturn(e2);

		assertEquals(roomService.getRoomEntities(ownerID),list);
	}

	@Test(groups = {"roomService"},expectedExceptions = NullPointerException.class)
	public void getRoomEntitiesWithInvalidOwenerID(){
		int ownerID = -1;

		TenantContext.setTenantId(1);

		when(roomDaoMock.getRoomEntitiesForOwnerID(1,-1)).thenReturn(null);

		roomService.getRoomEntities(ownerID);
	}
	/**
	 *
	 * String getTenantPrefix()
	 */
	@Test(groups = {"roomService"})
	public void getTenantPrefixWithSuccessfulReturn(){
		Tenant tenant = new Tenant();
		tenant.setTenantPrefix("prefix");
		TenantContext.setTenantId(1);

		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		assertEquals(roomService.getTenantPrefix(),"prefix");
	}

	/**
	 *
	 * String getTenantDialIn()
	 */
	@Test(groups = {"roomService"})
	public void getTenantDailInWithSuccessfulReturn(){
		Tenant tenant = new Tenant();

		tenant.setTenantDialIn("DialIn");
		TenantContext.setTenantId(1);

		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		assertEquals(roomService.getTenantDialIn(),"DialIn");
	}

	/**
	 *
	 * boolean areGuestAllowedToThisRoom()
	 */
	@Test(groups = {"roomService"})
	public void areGuestAllowedToThisRoomWithSuccessfulReturn(){
		Tenant tenant = new Tenant();

		tenant.setGuestLogin(1);
		TenantContext.setTenantId(1);

		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		assertEquals(roomService.areGuestAllowedToThisRoom(),true);
	}

	/**
	 *
	 * Entity getOneEntity(int entityID, User user)
	 */
	@Test(groups = {"roomService"})
	public void getOneEntityWithValidEntityIDAndValidUserAndScheduledRoom(){
		int entityID = 1;
		User user = new User();

		TenantContext.setTenantId(1);
		user.setLangID(1);
		Entity entity = new Entity();
		entity.setRoomType("scheduled");

		when(roomDaoMock.getOneEntity(1,1,user)).thenReturn(entity);

		assertEquals(roomService.getOneEntity(entityID,user),entity);
	}

	/**
	 *
	 * List<Integer> getEntityIDs(EntityFilter filter, User user)
	 */

	/**
	 *
	 * int getCountEntityIDs(EntityFilter filter, User user)
	 */

	/**
	 *
	 * List<Integer> getEntityIDs(EntityFilter filter, User user, boolean excludeRooms)
	 */
	/*@Test(groups = {"roomService"})
	public void getEntityIDsWithValidFilterAndValidUserAndDoExvuldeRooms(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		filter.setDir("dir");
		user.setLangID(1);
		TenantContext.setTenantId(1);
		List<Integer> tenantIds = new ArrayList<>();
		List<Integer> list = new ArrayList<>();

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.getEntityIDs(1,filter,user,true,tenantIds,true)).thenReturn(list);

		assertEquals(roomService.getEntityIDs(filter,user,excludeRooms),list);
	}

	@Test(groups = {"roomService"})
	public void getEntityIDsWithInvalidFilter(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		user.setLangID(1);
		TenantContext.setTenantId(1);
		List<Integer> tenantIds = new ArrayList<>();

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.getEntityIDs(1,filter,user,true,tenantIds,true)).thenReturn(null);

		assertEquals(roomService.getEntityIDs(filter,user,excludeRooms),null);
	}

	@Test(groups = {"roomService"})
	public void getEntityIDsWithInvalidUser(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		filter.setDir("dir");
		TenantContext.setTenantId(1);
		List<Integer> tenantIds = new ArrayList<>();

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.getEntityIDs(1,filter,user,true,tenantIds,true)).thenReturn(null);

		assertEquals(roomService.getEntityIDs(filter,user,excludeRooms),null);
	}

	@Test(groups = {"roomService"})
	public void getEntityIDsNotExvuldeRooms(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = false;

		filter.setDir("dir");
		user.setLangID(1);
		TenantContext.setTenantId(1);
		List<Integer> tenantIds = new ArrayList<>();
		List<Integer> list = new ArrayList<>();

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.getEntityIDs(1,filter,user,false,tenantIds,true)).thenReturn(list);

		assertEquals(roomService.getEntityIDs(filter,user,excludeRooms),list);
	}*/

	/**
	 *
	 * List<RoomIdSearchResult> searchRoomIds(EntityFilter filter, User user)
	 */
	@Test(groups = {"roomService"})
	public void getRoomIdSearchResultWithValidFilterAndValidUser(){
		EntityFilter filter = new EntityFilter();
		User user = new User();

		filter.setDir("dir");
		user.setLangID(1);
		TenantContext.setTenantId(1);
		List<Integer> tenantIds = new ArrayList<>();
		List<RoomIdSearchResult> RoomIdSearchResult = new ArrayList<>();

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.searchRoomIds(1,filter,user,tenantIds,true)).thenReturn(RoomIdSearchResult);

		assertEquals(roomService.searchRoomIds(filter,user),RoomIdSearchResult);
	}

	@Test(groups = {"roomService"})
	public void getRoomIdSearchResultWithInvalidFilter(){
		EntityFilter filter = new EntityFilter();
		User user = new User();

		user.setLangID(1);
		TenantContext.setTenantId(1);
		List<Integer> tenantIds = new ArrayList<>();

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.searchRoomIds(1,filter,user,tenantIds,true)).thenReturn(null);

		assertEquals(roomService.searchRoomIds(filter,user),null);
	}

	@Test(groups = {"roomService"})
	public void getRoomIdSearchResultWithInvalidUser(){
		EntityFilter filter = new EntityFilter();
		User user = new User();

		filter.setDir("dir");
		TenantContext.setTenantId(1);
		List<Integer> tenantIds = new ArrayList<>();

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.searchRoomIds(1,filter,user,tenantIds,true)).thenReturn(null);

		assertEquals(roomService.searchRoomIds(filter,user),null);
	}

	/**
	 *
	 * int findRoomsCount(String query, String queryField, List<Integer> roomTypes)
	 */
	@Test(groups = {"roomService"})
	public void findRoomsCountWithValidQueryAndValidQueryFieldAndValidRoomTypes(){
		String query = "query";
		String queryField = "queryField";
		List<Integer> roomTypes = new ArrayList<>();

		roomTypes.add(1);
		roomTypes.add(2);
		List<Integer> allowedTenantIds = new ArrayList<Integer>();
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(allowedTenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.findRoomsCount(1, query,queryField,allowedTenantIds,roomTypes,true)).thenReturn(1);

		assertEquals(roomService.findRoomsCount(1, query,queryField,roomTypes),1);
	}

	@Test(groups = {"roomService"})
	public void findRoomsCountWithInvalidQuery(){
		String query = "";
		String queryField = "queryField";
		List<Integer> roomTypes = new ArrayList<>();

		roomTypes.add(1);
		roomTypes.add(2);
		List<Integer> allowedTenantIds = new ArrayList<Integer>();
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(allowedTenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.findRoomsCount(1, query,queryField,allowedTenantIds,roomTypes,true)).thenReturn(0);

		assertEquals(roomService.findRoomsCount(1, query,queryField,roomTypes),0);
	}

	@Test(groups = {"roomService"})
	public void findRoomsCountWithInvalidQueryField(){
		String query = "query";
		String queryField = "";
		List<Integer> roomTypes = new ArrayList<>();

		roomTypes.add(1);
		roomTypes.add(2);
		List<Integer> allowedTenantIds = new ArrayList<Integer>();
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(allowedTenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.findRoomsCount(1, query,queryField,allowedTenantIds,roomTypes,true)).thenReturn(0);

		assertEquals(roomService.findRoomsCount(1, query,queryField,roomTypes),0);
	}

	@Test(groups = {"roomService"})
	public void findRoomsCountWithInvalidRoomTypes(){
		String query = "query";
		String queryField = "";
		List<Integer> roomTypes = new ArrayList<>();

		roomTypes.add(1);
		roomTypes.add(2);
		List<Integer> allowedTenantIds = new ArrayList<Integer>();
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(allowedTenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.findRoomsCount(1, query,queryField,allowedTenantIds,roomTypes,true)).thenReturn(0);

		assertEquals(roomService.findRoomsCount(1, query,queryField,roomTypes),0);
	}

	/**
	 *
	 * List<RoomMini> findRooms(int thisUserMemberID, String query, String queryField, List<Integer> roomTypes, int start, int limit, String sortBy, String sortDir)
	 */
	@Test(groups = {"roomService"})
	public void RoomMiniWithValidInput() {
		int thisUserMemberID = 0;
		String query = "query";
		String queryField = "queryField";
		List<Integer> roomTypes = new ArrayList<>();
		int start = 1;
		int limit = 5;
		String sortBy = "sortBy" ;
		String sortDir = "sortDir";

		List<Integer> allowedTenantIds = new ArrayList<>();
		List<RoomMini> roomMini = new ArrayList<>();
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(allowedTenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.findRooms(thisUserMemberID, query, queryField, allowedTenantIds, roomTypes, start, limit, sortBy, sortDir, true)).thenReturn(roomMini);

		assertEquals(roomService.findRooms(thisUserMemberID, query, queryField, roomTypes, start, limit, sortBy, sortDir),roomMini);
	}

	@Test(groups = {"roomService"})
	public void RoomMiniWithInvalidInput() {
		int thisUserMemberID = 0;
		String query = "";
		String queryField = "";
		List<Integer> roomTypes = new ArrayList<>();
		int start = 0;
		int limit = 0;
		String sortBy = "" ;
		String sortDir = "";

		List<Integer> allowedTenantIds = new ArrayList<>();
		List<RoomMini> roomMini = new ArrayList<>();
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(allowedTenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(true);
		when(roomDaoMock.findRooms(thisUserMemberID, query, queryField, allowedTenantIds, roomTypes, start, limit, sortBy, sortDir, true)).thenReturn(null);

		assertEquals(roomService.findRooms(thisUserMemberID, query, queryField, roomTypes, start, limit, sortBy, sortDir),null);
	}

	/**
	 *
	 * int getMemberIDForRoomIDIfAllowed(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void getMemberIDForRoomIDIfAllowedWithValidID() {
		int roomID = 1;

		List<Integer> allowedTenantIds = new ArrayList<>();
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(allowedTenantIds);
		when(roomDaoMock.getMemberIDForRoomID(1, allowedTenantIds)).thenReturn(1);

		assertEquals(roomService.getMemberIDForRoomIDIfAllowed(roomID),1);
	}

	@Test(groups = {"roomService"})
	public void getMemberIDForRoomIDIfAllowedWithInvalidID() {
		int roomID = -1;

		List<Integer> allowedTenantIds = new ArrayList<>();
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(allowedTenantIds);
		when(roomDaoMock.getMemberIDForRoomID(-1, allowedTenantIds)).thenReturn(-1);

		assertEquals(roomService.getMemberIDForRoomIDIfAllowed(roomID),-1);
	}

	/**
	 *
	 * int getCountEntityIDs(EntityFilter filter, User user, boolean excludeRooms)
	 */
	/*@Test(groups = {"roomService"})
	public void getCountEntityIDsWithValidInputAndDoExcludeRooms(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		filter.setDir("dir");
		user.setLangID(1);
		List<Integer> tenantIds = new ArrayList<>();
		boolean showDisabledRooms = true;
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(showDisabledRooms);
		when(roomDaoMock.getCountEntityIDs(1,filter,user,excludeRooms,tenantIds,showDisabledRooms)).thenReturn(5);

		assertEquals(roomService.getCountEntityIDs(filter,user,excludeRooms),5);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityIDsWithInvalidFilter(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		user.setLangID(1);
		List<Integer> tenantIds = new ArrayList<>();
		boolean showDisabledRooms = true;
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(showDisabledRooms);
		when(roomDaoMock.getCountEntityIDs(1,filter,user,excludeRooms,tenantIds,showDisabledRooms)).thenReturn(0);

		assertEquals(roomService.getCountEntityIDs(filter,user,excludeRooms),0);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityIDsWithInvalidUser(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = true;

		filter.setDir("dir");
		List<Integer> tenantIds = new ArrayList<>();
		boolean showDisabledRooms = true;
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(showDisabledRooms);
		when(roomDaoMock.getCountEntityIDs(1,filter,user,excludeRooms,tenantIds,showDisabledRooms)).thenReturn(0);

		assertEquals(roomService.getCountEntityIDs(filter,user,excludeRooms),0);
	}

	@Test(groups = {"roomService"})
	public void getCountEntityIDsNotExclude(){
		EntityFilter filter = new EntityFilter();
		User user = new User();
		boolean excludeRooms = false;

		filter.setDir("dir");
		user.setLangID(1);
		List<Integer> tenantIds = new ArrayList<>();
		boolean showDisabledRooms = true;
		TenantContext.setTenantId(1);

		when(tenantServiceMock.canCallToTenantIds(1)).thenReturn(tenantIds);
		when(IsystemServiceMock.isShowDisabledRooms()).thenReturn(showDisabledRooms);
		when(roomDaoMock.getCountEntityIDs(1,filter,user,excludeRooms,tenantIds,showDisabledRooms)).thenReturn(0);

		assertEquals(roomService.getCountEntityIDs(filter,user,excludeRooms),0);
	}*/

	/**
	 *
	 * List<RoomProfile> getRoomProfiles()
	 */
	@Test(groups = "roomService")
	public void getRoomProfile(){
		List<RoomProfile> list = new ArrayList<>();

		when(roomDaoMock.getRoomProfiles()).thenReturn(list);

		assertEquals(roomService.getRoomProfiles(),list);
	}

	/**
	 *
	 * RoomProfile getRoomProfile(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void getRoomProfileWithValidID(){
		int roomID = 1;

		RoomProfile rc = new RoomProfile();
		when(roomDaoMock.getRoomProfile(roomID)).thenReturn(rc);

		assertEquals(roomService.getRoomProfile(roomID),rc);
	}

	@Test(groups = {"roomService"})
	public void getRoomProfileWithInvalidID(){
		int roomID = -1;

		RoomProfile rc = new RoomProfile();
		when(roomDaoMock.getRoomProfile(roomID)).thenReturn(null);

		assertEquals(roomService.getRoomProfile(roomID),null);
	}

	/**
	 *
	 * int updateRoomProfile(int roomID, String profileName)
	 */
	@Test(groups = {"roomService"})
	public void updateRoomProfieWithValidInput(){
		int roomID = 1;
		String profileName = "profileName";

		int rc = 10;

		when(roomDaoMock.updateRoomProfile(1,"profileName")).thenReturn(10);

		assertEquals(roomService.updateRoomProfile(roomID,profileName),10);
	}

	@Test(groups = {"roomService"})
	public void updateRoomProfieWithInvalidRoomID(){
		int roomID = -1;
		String profileName = "profileName";

		int rc = 0;

		when(roomDaoMock.updateRoomProfile(-1,"profileName")).thenReturn(-1);

		assertEquals(roomService.updateRoomProfile(roomID,profileName),-1);
	}
	@Test(groups = {"roomService"})
	public void updateRoomProfieWithInvalidProfileName(){
		int roomID = 1;
		String profileName = "";

		int rc = 0;

		when(roomDaoMock.updateRoomProfile(1,"profileName")).thenReturn(0);

		assertEquals(roomService.updateRoomProfile(roomID,profileName),0);
	}

	/**
	 *
	 * int removeRoomProfile(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void removeRoomProfileWithValidRoomID(){
		int roomID = 1;

		int rc = 1;

		when(roomDaoMock.removeRoomProfile(1)).thenReturn(1);

		assertEquals(roomService.removeRoomProfile(roomID),1);
	}

	@Test(groups = {"roomService"})
	public void removeRoomProfileWithInvalidRoomID(){
		int roomID = -1;

		int rc = -1;

		when(roomDaoMock.removeRoomProfile(-1)).thenReturn(-1);

		assertEquals(roomService.removeRoomProfile(roomID),-1);
	}

	/**
	 *
	 * List<Entity> getEntities(List<Integer> roomIDs, User user, EntityFilter filter)
	 */

	/**
	 *
	 * Room getRoomDetailsForConference(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void getRoomDetailsForConferenceWithValidID(){
		int roomID = 1;

		TenantContext.setTenantId(1);
		Room room = new Room();

		when(roomDaoMock.getRoomDetailsForConference(1,1)).thenReturn(room);

		assertEquals(roomService.getRoomDetailsForConference(roomID),room);
	}

	@Test(groups = {"roomService"})
	public void getRoomDetailsForConferenceWithInvalidID(){
		int roomID = -1;

		TenantContext.setTenantId(1);
		Room room = new Room();
		DataAccessException exception = new DataAccessException("msg"){};

		when(roomDaoMock.getRoomDetailsForConference(-1,1)).thenThrow(exception);

		assertEquals(roomService.getRoomDetailsForConference(roomID),null);
	}

	/**
	 *
	 * Room getRoomDetailsForConference(String roomExt, String vidyoGatewayControllerDns)
	 */
	@Test(groups = {"roomService"})
	public void getRoomDetailsForConferenceWithValidInput(){
		String roomExt = "roomExt";
		String vidyoGatewayControllerDns = "vidyoGatewayControllerDns";

		TenantContext.setTenantId(1);
		Room room = new Room();

		when(roomDaoMock.getRoomDetailsForConference("roomExt","vidyoGatewayControllerDns")).thenReturn(room);

		assertEquals(roomService.getRoomDetailsForConference(roomExt,vidyoGatewayControllerDns),room);
	}

	@Test(groups = {"roomService"})
	public void getRoomDetailsForConferenceWithInvalidRoomExt(){
		String roomExt = "";
		String vidyoGatewayControllerDns = "vidyoGatewayControllerDns";

		Room room = new Room();

		when(roomDaoMock.getRoomDetailsForConference("","vidyoGatewayControllerDns")).thenReturn(null);

		assertEquals(roomService.getRoomDetailsForConference(roomExt,vidyoGatewayControllerDns),null);
	}

	@Test(groups = {"roomService"})
	public void getRoomDetailsForConferenceWithInvalidDns(){
		String roomExt = "roomExt";
		String vidyoGatewayControllerDns = "";

		Room room = new Room();

		when(roomDaoMock.getRoomDetailsForConference("roomExt","")).thenReturn(null);

		assertEquals(roomService.getRoomDetailsForConference(roomExt,vidyoGatewayControllerDns),null);
	}

	/**
	 *
	 * Room getRoomDetailsForConference(String roomNameExt, int tenantId)
	 */
	@Test(groups = {"roomService"})
	public void getRoomDetailsForConferenceWithValidRoomExtAndValidTeanatID(){
		String roomExt = "roomExt";
		int tenantId = 1;

		Room room = new Room();

		when(roomDaoMock.getRoomDetailsForConference("roomExt",1)).thenReturn(room);

		assertEquals(roomService.getRoomDetailsForConference(roomExt,tenantId),room);
	}

	@Test(groups = {"roomService"})
	public void getRoomDetailsForConferenceWithInvalidRoomExtAndValidTeanantID(){
		String roomExt = "";
		int tenantId = 1;

		Room room = new Room();

		when(roomDaoMock.getRoomDetailsForConference("",1)).thenReturn(null);

		assertEquals(roomService.getRoomDetailsForConference(roomExt,tenantId),null);
	}

	@Test(groups = {"roomService"})
	public void getRoomDetailsForConferenceWithInvalidTeanantID(){
		String roomExt = "roomExt";
		int tenantId = -1;

		Room room = new Room();

		when(roomDaoMock.getRoomDetailsForConference("roomExt",-1)).thenReturn(null);

		assertEquals(roomService.getRoomDetailsForConference(roomExt,tenantId),null);
	}

	/**
	 *
	 * Room getRoomDetailsForWebcast(int roomID)
	 */
	@Test(groups = {"roomService"})
	public void getRoomDetailsForWebcastWithValidRoomID(){
		int roomID = 1;

		TenantContext.setTenantId(1);
		Room room = new Room();

		when(roomDaoMock.getRoomDetailsForWebcast(1,1)).thenReturn(room);

		assertEquals(roomService.getRoomDetailsForWebcast(roomID),room);
	}

	@Test(groups = {"roomService"})
	public void getRoomDetailsForWebcastWithInvalidRoomID(){
		int roomID = -1;

		TenantContext.setTenantId(1);
		Room room = new Room();

		when(roomDaoMock.getRoomDetailsForWebcast(1,1)).thenReturn(null);

		assertEquals(roomService.getRoomDetailsForWebcast(roomID),null);
	}

	/**
	 *
	 * int updateRoomModeratorPIN(Room room)
	 */
	@Test(groups = "roomService")
	public void updateRoomModeratorPINWithValidRoom(){
		Room room = new Room();

		TenantContext.setTenantId(1);
		room.setRoomID(1);
		int rc = 1;

		when(roomDaoMock.updateRoomModeratorPIN(1,room)).thenReturn(rc);

		assertEquals(roomService.updateRoomModeratorPIN(room),1);
	}

	@Test(groups = "roomService")
	public void updateRoomModeratorPINWithInvalidRoom(){
		Room room = new Room();

		TenantContext.setTenantId(1);
		room.setRoomID(-1);
		int rc = 1;

		when(roomDaoMock.updateRoomModeratorPIN(1,room)).thenReturn(-1);

		assertEquals(roomService.updateRoomModeratorPIN(room),-1);
	}

	/**
	 *
	 * Room getRoom(String nameExt, int tenantId)
	 */
	@Test(groups = "roomService")
	public void getRoomWithValidInput(){
		String nameExt = "nameExt";
		int tenantId = 1;

		Room room = new Room();

		when(roomDaoMock.getRoom(1,"nameExt")).thenReturn(room);

		assertEquals(roomService.getRoom(nameExt,tenantId),room);
	}

	@Test(groups = "roomService")
	public void getRoomWithInvalidTeanantID(){
		String nameExt = "nameExt";
		int tenantId = -1;

		DataAccessException dae = new DataAccessException("msg") {};

		when(roomDaoMock.getRoom(-1,"nameExt")).thenThrow(dae);

		assertEquals(roomService.getRoom(nameExt,tenantId),null);
	}

	@Test(groups = "roomService")
	public void getRoomWithInvalidNameExtAndValidTenantID(){
		String nameExt = "";
		int tenantId = 1;

		DataAccessException dae = new DataAccessException("msg") {};

		when(roomDaoMock.getRoom(-1,"nameExt")).thenThrow(dae);

		assertEquals(roomService.getRoom(nameExt,tenantId),null);
	}

	/**
	 *
	 * String generateRoomExt(String tenantPrefix)
	 */
	/*@Test(groups = "roomService",expectedExceptions = RuntimeException.class)
	public void generateRoomExtWithValidTenantPrefixValidAndRoomDoseExist(){
		String tenantPrefix = "tenantPrefix";

		Long getCountAllSeats = new Long(5);
		when(memberServiceMock.getCountAllSeats()).thenReturn(getCountAllSeats);

		when(generatorMock.generateRandom()).thenReturn(9);
		when(generatorMock.generateSchRoomExtnWithoutPin(5,9)).thenReturn("Random");
		when(roomDaoMock.isRoomExistForRoomExtNumber("tenantPrefixRandom")).thenReturn(true);

		roomService.generateRoomExt(tenantPrefix);
	}*/

	/*@Test(groups = "roomService")
	public void generateRoomExtWithInvalidTenantPrefix(){
		String tenantPrefix = "";

		Long getCountAllSeats = new Long(5);
		when(memberServiceMock.getCountAllSeats()).thenReturn(getCountAllSeats);

		when(generatorMock.generateRandom()).thenReturn(9);
		when(generatorMock.generateSchRoomExtnWithoutPin(5,9)).thenReturn("Random");
		when(roomDaoMock.isRoomExistForRoomExtNumber("Random")).thenReturn(false);

		assertEquals(roomService.generateRoomExt(tenantPrefix),"Random");
	}*/

	/**
	 *
	 * int deleteScheduledRooms(String prefix)
	 */
	@Test(groups = "roomService")
	public void deleteScheduledRoomsWithValidPrefix(){
		String prefix = "prefix";

		when(roomDaoMock.deleteScheduledRooms(prefix)).thenReturn(1);

		assertEquals(roomService.deleteScheduledRooms("prefix"),1);
	}

	@Test(groups = "roomService")
	public void deleteScheduledRoomsWithInvalidPrefix(){
		String prefix = "";

		when(roomDaoMock.deleteScheduledRooms("")).thenReturn(0);

		assertEquals(roomService.deleteScheduledRooms(prefix),0);
	}

	/**
	 *
	 * int deleteScheduledRoomByRoomKey(String roomKey)
	 */
	@Test(groups = "roomService")
	public void deleteScheduledRoomByRoomKeyWithValidRoomKey(){
		String roomKey = "roomKey";

		when(roomDaoMock.deleteScheduledRoomByRoomKey("roomKey")).thenReturn(1);

		assertEquals(roomService.deleteScheduledRoomByRoomKey(roomKey),1);
	}

	@Test(groups = "roomService")
	public void deleteScheduledRoomByRoomKeyWithInvalidRoomKey(){
		String roomKey = "";

		when(roomDaoMock.deleteScheduledRoomByRoomKey("")).thenReturn(0);

		assertEquals(roomService.deleteScheduledRoomByRoomKey(roomKey),0);
	}

	/**
	 *
	 * RoomCreationResponse createScheduledRoom(SchRoomCreationRequest schRoomCreationRequest)
	 */
	@Test(groups = "roomService")
	public void createScheduledRoomWithEmptyConfiguration(){
		SchRoomCreationRequest schRoomCreationRequest = new SchRoomCreationRequest();

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(null);

		RoomCreationResponse returnVal = roomService.createScheduledRoom(schRoomCreationRequest);

		assertEquals(returnVal.getMessage(),"Scheduled Room Feature not available");
	}

	@Test(groups = "roomService")
	public void createScheduledRoomWithEmptyConfigurationTrimLength(){
		SchRoomCreationRequest schRoomCreationRequest = new SchRoomCreationRequest();

		Configuration scheduledRoomconfig = new Configuration();
		scheduledRoomconfig.setConfigurationValue("");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(scheduledRoomconfig);

		RoomCreationResponse returnVal = roomService.createScheduledRoom(schRoomCreationRequest);

		assertEquals(returnVal.getMessage(),"Scheduled Room Feature not available");
	}

	@Test(groups = "roomService")
	public void createScheduledRoomWithEmptyTenant(){
		SchRoomCreationRequest schRoomCreationRequest = new SchRoomCreationRequest();
		schRoomCreationRequest.setTenantId(1);

		Configuration scheduledRoomconfig = new Configuration();
		scheduledRoomconfig.setConfigurationValue("configValue");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(scheduledRoomconfig);

		Tenant tenant = null;
		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		RoomCreationResponse returnVal = roomService.createScheduledRoom(schRoomCreationRequest);

		assertEquals(returnVal.getMessage(),"Scheduled Room Feature not available");
	}

	@Test(groups = "roomService")
	public void createScheduledRoomWithPinRequiredAndRoomExists(){
		SchRoomCreationRequest schRoomCreationRequest = new SchRoomCreationRequest();
		schRoomCreationRequest.setTenantId(1);
		schRoomCreationRequest.setMemberId(2);
		schRoomCreationRequest.setPinRequired(true);

		Configuration scheduledRoomconfig = new Configuration();
		scheduledRoomconfig.setConfigurationValue("configValue");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(scheduledRoomconfig);

		Tenant tenant = new Tenant();
		tenant.setScheduledRoomEnabled(1);
		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		when(generatorMock.generateRandom()).thenReturn(5);

		String roomExtn = "configValue25";
		when(roomDaoMock.isRoomExistForRoomExtNumber(roomExtn)).thenReturn(true);


		RoomCreationResponse returnVal = roomService.createScheduledRoom(schRoomCreationRequest);

		assertEquals(returnVal.getMessage(),"Scheduled Room Creation Failed");
	}

	@Test(groups = "roomService")
	public void createScheduledRoomWithPinRequiredAndRoomNotExistsAndRecurringMoreThanZero(){
		SchRoomCreationRequest schRoomCreationRequest = new SchRoomCreationRequest();
		schRoomCreationRequest.setTenantId(1);
		schRoomCreationRequest.setMemberId(2);
		schRoomCreationRequest.setPinRequired(true);
		schRoomCreationRequest.setGroupId(1);
		schRoomCreationRequest.setRecurring(10);

		Configuration scheduledRoomconfig = new Configuration();
		scheduledRoomconfig.setConfigurationValue("configValue");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(scheduledRoomconfig);

		Tenant tenant = new Tenant();
		tenant.setScheduledRoomEnabled(1);
		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		when(generatorMock.generateRandom()).thenReturn(5);

		String roomExtn = "configValue25";
		when(roomDaoMock.isRoomExistForRoomExtNumber(roomExtn)).thenReturn(false);

		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setWaitingRoomsEnabled(1);


		RoomCreationResponse returnVal = roomService.createScheduledRoom(schRoomCreationRequest);

		assertEquals(returnVal.getMessage(),"Scheduled Room Creation Failed");
	}


	/**
	 *
	 * ScheduledRoomResponse validateScheduledRoom(String roomExt, String pin, int tenantId)
	 */
	@Test(groups = "roomService")
	public void validateScheduledRoomWithEmptyConfig(){
		String roomExt = "roomExt";
		String pin = "pin";
		int tenantId = 1;

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		Configuration config = null;

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(config);

		assertEquals(roomService.validateScheduledRoom(roomExt,pin,tenantId).getMessageId(),"Room");
	}

	@Test(groups = "roomService")
	public void validateScheduledRoomWithRoomExtShorterThanschRoomPrefix(){
		String roomExt = "roomExt";
		String pin = "pin";
		int tenantId = 1;

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		Configuration config = new Configuration();
		config.setConfigurationValue("ConfigurationValue");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(config);

		assertEquals(roomService.validateScheduledRoom(roomExt,pin,tenantId).getMessageId(),"Room");
	}

	@Test(groups = "roomService")
	public void validateScheduledRoomWithRoomExtPrefixNotEqualsSchRoomPrefix(){
		String roomExt = "roomExt";
		String pin = "pin";
		int tenantId = 1;

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		Configuration config = new Configuration();
		config.setConfigurationValue("Value");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(config);

		assertEquals(roomService.validateScheduledRoom(roomExt,pin,tenantId).getMessageId(),"Room");
	}

	@Test(groups = "roomService")
	public void validateScheduledRoomWithEmptyRoomExt(){
		String roomExt = "";
		String pin = "pin";
		int tenantId = 1;

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		Configuration config = new Configuration();
		config.setConfigurationValue("Value");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(config);

		assertEquals(roomService.validateScheduledRoom(roomExt,pin,tenantId).getMessageId(),"Room");
	}

	@Test(groups = "roomService")
	public void validateScheduledRoomWithRoomExtPrefixEqualsSchRoomPrefixAndResponseStatusIsNotZero(){
		String roomExt = "ConfigurationValue";
		String pin = "pin";
		int tenantId = 1;

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		Configuration config = new Configuration();
		config.setConfigurationValue("Configuration");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(config);

		ScheduledRoomResponse response = new ScheduledRoomResponse();
		response.setStatus(1);
		when(generatorMock.decryptShceduledRoom("Value",pin)).thenReturn(response);

		assertEquals(roomService.validateScheduledRoom(roomExt,pin,tenantId).getMessageId(),null);
	}

	@Test(groups = "roomService")
	public void validateScheduledRoomWithRoomExtPrefixEqualsSchRoomPrefixAndResponsePinIsNotZeroAndRoomNotFound(){
		String roomExt = "ConfigurationValue";
		String pin = "pin";
		int tenantId = 1;

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		Configuration config = new Configuration();
		config.setConfigurationValue("Configuration");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(config);

		ScheduledRoomResponse response = new ScheduledRoomResponse();
		response.setPin(1);
		when(generatorMock.decryptShceduledRoom("Value",pin)).thenReturn(response);

		assertEquals(roomService.validateScheduledRoom(roomExt,pin,tenantId).getMessageId(),null);
	}

	@Test(groups = "roomService")
	public void validateScheduledRoomWithRoomExtPrefixEqualsSchRoomPrefixAndResponsePinIsNotZeroAndRoomFound(){
		String roomExt = "ConfigurationValue";
		String pin = "55";
		int tenantId = 1;

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		Configuration config = new Configuration();
		config.setConfigurationValue("Configuration");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(config);

		ScheduledRoomResponse response = new ScheduledRoomResponse();
		response.setPin(1);
		response.setRoomExtn("Ext");
		when(generatorMock.decryptShceduledRoom("Value",pin)).thenReturn(response);

		Room room = new Room();
		when(roomDaoMock.getRoomDetailsForConference("ConfigurationExt1",1)).thenReturn(room);
		response.setStatus(0);

		assertEquals(roomService.validateScheduledRoom(roomExt,pin,tenantId).getStatus(),0);
	}

	@Test(groups = "roomService")
	public void validateScheduledRoomWithRoomExtPrefixEqualsSchRoomPrefixAndResponseResponsePinIsZeroAndRoomFound(){
		String roomExt = "ConfigurationValue";
		String pin = "55";
		int tenantId = 1;

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		Configuration config = new Configuration();
		config.setConfigurationValue("Configuration");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(config);

		ScheduledRoomResponse response = new ScheduledRoomResponse();
		response.setPin(0);
		response.setRoomExtn("Ext");
		when(generatorMock.decryptShceduledRoom("Value",pin)).thenReturn(response);

		Room room = new Room();
		when(roomDaoMock.getRoomDetailsForConference("ConfigurationExt",1)).thenReturn(room);
		response.setStatus(0);

		assertEquals(roomService.validateScheduledRoom(roomExt,pin,tenantId).getStatus(),0);
	}
	@Test(groups = "roomService")
	public void validateScheduledRoomWithRoomExtPrefixEqualsSchRoomPrefixAndResponsePinIsNotZeroAndGetRoomThrowException(){
		String roomExt = "ConfigurationValue";
		String pin = "55";
		int tenantId = 1;

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		Configuration config = new Configuration();
		config.setConfigurationValue("Configuration");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(config);

		ScheduledRoomResponse response = new ScheduledRoomResponse();
		response.setPin(1);
		response.setRoomExtn("Ext");
		when(generatorMock.decryptShceduledRoom("Value",pin)).thenReturn(response);

		Room room = new Room();
		DataAccessException dae = new DataAccessException("msg") {};
		when(roomDaoMock.getRoomDetailsForConference("ConfigurationExt1",1)).thenThrow(dae);
		response.setStatus(0);

		assertEquals(roomService.validateScheduledRoom(roomExt,pin,tenantId).getStatus(),4002);
	}

	@Test(groups = "roomService")
	public void validateScheduledRoomWithRoomExtPrefixEqualsSchRoomPrefixAndResponsePinIsNotZeroAndSetPinThrowException(){
		String roomExt = "ConfigurationValue";
		String pin = "abc";
		int tenantId = 1;

		ScheduledRoomResponse scheduledRoomResponse = new ScheduledRoomResponse();
		Configuration config = new Configuration();
		config.setConfigurationValue("Configuration");

		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(config);

		ScheduledRoomResponse response = new ScheduledRoomResponse();
		response.setPin(0);
		response.setRoomExtn("Ext");
		when(generatorMock.decryptShceduledRoom("Value",pin)).thenReturn(response);

		Room room = new Room();
		when(roomDaoMock.getRoomDetailsForConference("ConfigurationExt",1)).thenReturn(room);
		response.setStatus(0);

		assertEquals(roomService.validateScheduledRoom(roomExt,pin,tenantId).getStatus(),4001);
	}

	/**
	 *
	 * ScheduledRoomResponse generateSchRoomExtPin(int memberId, int randNumb)
	 */
	@Test(groups = {"roomService"})
	public void generateSchRoomExtPinExtnLengthWithInvalidInput(){
		int memberId = -1;
		int randNumb = -1;

		String encryptedExtPin = "encryptedExtPin";
		when(generatorMock.generateSchRoomExtnWithPin(memberId,randNumb)).thenReturn(encryptedExtPin);

		assertEquals(roomService.generateSchRoomExtPin(memberId,randNumb).getStatus(),4002);
	}

	@Test(groups = {"roomService"})
	public void generateSchRoomExtPinExtnLengthEqualsTwoAndEmptyExtn(){
		int memberId = 1;
		int randNumb = 1;

		String encryptedExtPin = "encrypted-1";
		when(generatorMock.generateSchRoomExtnWithPin(memberId,randNumb)).thenReturn(encryptedExtPin);

		Configuration configuration = new Configuration();
		configuration.setConfigurationValue("");
		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(configuration);

		assertEquals(roomService.generateSchRoomExtPin(memberId,randNumb).getPin(),1);
	}

	@Test(groups = {"roomService"})
	public void generateSchRoomExtPinExtnLengthEqualsTwoAndEValidExtn(){
		int memberId = 1;
		int randNumb = 1;

		String encryptedExtPin = "encrypted-1";
		when(generatorMock.generateSchRoomExtnWithPin(memberId,randNumb)).thenReturn(encryptedExtPin);

		Configuration configuration = new Configuration();
		configuration.setConfigurationValue("cofig");
		when(systemServiceMock.getConfiguration("SCHEDULED_ROOM_PREFIX")).thenReturn(configuration);

		assertEquals(roomService.generateSchRoomExtPin(memberId,randNumb).getPin(),1);
	}

	/**
	 *
	 * RoomControlResponse lockRoom(int loggedMemberID, int lockRoomID, String moderatorPIN)
	 */

	/**
	 *
	 * RoomControlResponse unlockRoom(int loggedMemberID, int unlockRoomID, String moderatorPIN)
	 */

	/**
	 *
	 * RoomUpdateResponse updateRoomPrefix(String oldPrefix, int tenantId, String newPrefix)
	 */
	@Test(groups = {"roomService"},expectedExceptions = NullPointerException.class)
	public void updateRoomPrefixWithInvalidTenantId(){
		int tenantId = -1;
		String oldPrefix = "oldPrefix";
		String newPrefix = "newPrefix";

		when(tenantServiceMock.getTenant(tenantId)).thenReturn(null);

		roomService.updateRoomPrefix(oldPrefix,tenantId,newPrefix);
	}

	@Test(groups = {"roomService"})
	public void updateRoomPrefixWithOldPrefixNotValid(){
		int tenantId = 1;
		String oldPrefix = "oldPrefix";
		String newPrefix = "newPrefix";

		Tenant tenant = new Tenant();
		tenant.setTenantPrefix("Prefix");

		when(tenantServiceMock.getTenant(tenantId)).thenReturn(tenant);

		assertEquals(roomService.updateRoomPrefix(oldPrefix,tenantId,newPrefix).getStatus(),11001);
	}

	@Test(groups = {"roomService"})
	public void updateRoomPrefixWithDataException(){
		int tenantId = 1;
		String oldPrefix = "oldPrefix";
		String newPrefix = "newPrefix";

		Tenant tenant = new Tenant();
		tenant.setTenantPrefix("oldPrefix");

		DataAccessException dae = new DataAccessException("msg") {};
		when(tenantServiceMock.getTenant(tenantId)).thenReturn(tenant);
		when(roomDaoMock.updateRoomExtensionPrefix(newPrefix,tenantId,oldPrefix)).thenThrow(dae);

		assertEquals(roomService.updateRoomPrefix(oldPrefix,tenantId,newPrefix).getStatus(),11003);
	}

	@Test(groups = {"roomService"})
	public void updateRoomPrefixWithNegativeCount(){
		int tenantId = 1;
		String oldPrefix = "oldPrefix";
		String newPrefix = "newPrefix";

		Tenant tenant = new Tenant();
		tenant.setTenantPrefix("oldPrefix");

		when(tenantServiceMock.getTenant(tenantId)).thenReturn(tenant);
		when(roomDaoMock.updateRoomExtensionPrefix(newPrefix,tenantId,oldPrefix)).thenReturn(0);

		assertEquals(roomService.updateRoomPrefix(oldPrefix,tenantId,newPrefix).getStatus(),11003);
	}

	@Test(groups = {"roomService"})
	public void updateRoomPrefixWithPositiveCount(){
		int tenantId = 1;
		String oldPrefix = "oldPrefix";
		String newPrefix = "newPrefix";

		Tenant tenant = new Tenant();
		tenant.setTenantPrefix("oldPrefix");

		when(tenantServiceMock.getTenant(tenantId)).thenReturn(tenant);
		when(roomDaoMock.updateRoomExtensionPrefix(newPrefix,tenantId,oldPrefix)).thenReturn(5);

		assertEquals(roomService.updateRoomPrefix(oldPrefix,tenantId,newPrefix).getStatus(),0);
	}

	/**
	 *
	 * int isPrefixExisting(String tenantPrefix, int tenantId)
	 */
	@Test(groups = {"roomService"})
	public void isPrefixExistingWithValidInput(){
		String tenantPrefix = "tenantPrefix";
		int tenantId = 1;

		when(roomDaoMock.getRoomsCountMatchingPrefix(tenantId,tenantPrefix)).thenReturn(5);

		assertEquals(roomService.isPrefixExisting(tenantPrefix,tenantId),5);
	}
	@Test(groups = {"roomService"})
	public void isPrefixExistingWithInvalidInput(){
		String tenantPrefix = "";
		int tenantId = 0;

		when(roomDaoMock.getRoomsCountMatchingPrefix(tenantId,tenantPrefix)).thenReturn(0);

		assertEquals(roomService.isPrefixExisting(tenantPrefix,tenantId),0);
	}

	/**
	 *
	 * RoomUpdateResponse updateGroupForRooms(RoomUpdateRequest roomUpdateRequest)
	 */
	@Test(groups = {"roomService"})
	public void updateGroupForRoomsWithValidRoomUpdateRequest(){
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();

		when(roomDaoMock.updateGroupForRooms(roomUpdateRequest)).thenReturn(5);

		assertEquals(roomService.updateGroupForRooms(roomUpdateRequest).getStatus(),0);
	}

	@Test(groups = {"roomService"})
	public void updateGroupForRoomsWithInvalidRoomUpdateRequest(){
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();

		DataAccessException dae = new DataAccessException("dae") {};
		when(roomDaoMock.updateGroupForRooms(roomUpdateRequest)).thenThrow(dae);

		assertEquals(roomService.updateGroupForRooms(roomUpdateRequest).getStatus(),500);
	}

	/**
	 *
	 * RoomAccessDetailsResponse getRoomAccessOptions(int memberId, int roomId, int tenantId)
	 */
	@Test(groups = {"roomService"})
	public void getRoomAccessOptionsNotReturnRoom(){
		int memberId = 1;
		int roomId = -1;
		int tenantId = -1;

		DataAccessException dae = new DataAccessException("dae") {};
		when(roomDaoMock.getRoom(roomId,tenantId)).thenThrow(dae);

		assertEquals(roomService.getRoomAccessOptions(memberId,roomId,tenantId).getMessageId(),"Room");
	}

	@Test(groups = {"roomService"})
	public void getRoomAccessOptionsWithValidInputAndNotScheduledRoomAndNotNullTenant(){
		int memberId = 1;
		int roomId = 1;
		int tenantId = 1;

		Room room = new Room();
		room.setMemberID(1);
		room.setRoomKey("key");
		room.setRoomPIN("111111");
		room.setRoomExtNumber("1234");
		room.setRoomType("Unscheduled");
		when(roomDaoMock.getRoom(roomId,tenantId)).thenReturn(room);

		Tenant tenant = new Tenant();
		when(tenantServiceMock.getTenant(tenantId)).thenReturn(tenant);
		assertEquals(roomService.getRoomAccessOptions(memberId,roomId,tenantId).getRoomPin(),"111111");
	}

	@Test(groups = {"roomService"})
	public void getRoomAccessOptionsWithValidInputAndNotScheduledRoomAndNullTenant(){
		int memberId = 1;
		int roomId = 1;
		int tenantId = 1;

		Room room = new Room();
		room.setMemberID(1);
		room.setRoomKey("key");
		room.setRoomPIN("111111");
		room.setRoomExtNumber("1234");
		room.setRoomType("Unscheduled");
		when(roomDaoMock.getRoom(roomId,tenantId)).thenReturn(room);

		when(tenantServiceMock.getTenant(tenantId)).thenReturn(null);
		assertEquals(roomService.getRoomAccessOptions(memberId,roomId,tenantId).getRoomPin(),"111111");
	}


	/**
	 *
	 * int deleteInactiveSchRooms(int inactiveDays)
	 */
	@Test(groups = "roomService")
	public void deleteInactiveSchRoomsWithValidInput(){
		int inactiveDays = 2;
		when(roomDaoMock.deleteInactiveSchRooms(2)).thenReturn(1);

		assertEquals(roomService.deleteInactiveSchRooms(inactiveDays),1);
	}

	@Test(groups = "roomService")
	public void deleteInactiveSchRoomsWithInvalidInput(){
		int inactiveDays = -2;
		when(roomDaoMock.deleteInactiveSchRooms(-2)).thenReturn(0);

		assertEquals(roomService.deleteInactiveSchRooms(inactiveDays),0);
	}

	/**
	 *
	 * int deleteScheduledRoomsbyRecurring (int limit)
	 */
	@Test(groups = {"roomService"})
	public void deleteScheduledRoomsbyRecurringWithValidLimit(){
		int limit = 1;
		when(roomDaoMock.deleteScheduledRoomsbyRecurring(limit)).thenReturn(1);

		assertEquals(roomService.deleteScheduledRoomsbyRecurring(limit),1);
	}

	@Test(groups = {"roomService"})
	public void deleteScheduledRoomsbyRecurringWithInvalidLimit(){
		int limit = -1;
		when(roomDaoMock.deleteScheduledRoomsbyRecurring(-1)).thenReturn(-1);

		assertEquals(roomService.deleteScheduledRoomsbyRecurring(limit),-1);
	}

	/**
	 *
	 * Entity overrideScheduledRoomProperties(Entity entity)
	 */

	/**
	 *
	 * updateGroupByMember(RoomUpdateRequest roomUpdateRequest)
	 */
	@Test(groups = {"roomService"})
	public void updateGroupByMemberWithValidInput(){
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();

		when(roomDaoMock.updateGroupByMember(roomUpdateRequest)).thenReturn(5);

		assertEquals(roomService.updateGroupByMember(roomUpdateRequest),5);
	}

	@Test(groups = {"roomService"})
	public void updateGroupByMemberWithInvalidInput(){
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();

		DataAccessException dae = new DataAccessException("dae") {};

		when(roomDaoMock.updateGroupByMember(roomUpdateRequest)).thenThrow(dae);

		assertEquals(roomService.updateGroupByMember(roomUpdateRequest),0);
	}

	/**
	 *
	 * Room overrideScheduledRoomProperties(Room room)
	 */

	/**
	 *
	 * Room getAccessibleRoomDetails(int roomId, List<Integer> canCallTenantIds)
	 */
	@Test(groups = {"roomService"})
	public void getAccessibleRoomDetailsWithValidRoomIdAndValidCanCallTenantIds() {
		int roomId = 1;
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);

		Room room = new Room();

		when(roomDaoMock.getAccessibleRoomDetails(roomId,list)).thenReturn(room);

		assertEquals(roomService.getAccessibleRoomDetails(roomId,list),room);
	}

	@Test(groups = {"roomService"})
	public void getAccessibleRoomDetailsWithInvalidRoomId() {
		int roomId = -1;
		List<Integer> list = new ArrayList<>();
		list.add(1);
		list.add(2);

		Room room = new Room();
		DataAccessException dae = new DataAccessException("dae") {};

		when(roomDaoMock.getAccessibleRoomDetails(roomId,list)).thenThrow(dae);

		assertEquals(roomService.getAccessibleRoomDetails(roomId,list),null);
	}

	@Test(groups = {"roomService"})
	public void getAccessibleRoomDetailsWithInvalidCanCallTenantIds() {
		int roomId = 1;
		List<Integer> list = new ArrayList<>();

		Room room = new Room();
		DataAccessException dae = new DataAccessException("dae") {};

		when(roomDaoMock.getAccessibleRoomDetails(roomId,list)).thenThrow(dae);

		assertEquals(roomService.getAccessibleRoomDetails(roomId,list),null);
	}

	/**
	 *
	 * Room getRoomDetailsForDisconnectParticipant(int roomId, int tenantId)
	 */
	@Test(groups = {"roomService"})
	public void getRoomDetailsForDisconnectParticipantWithValidInput() {
		int roomId = 1;
		int tenantId = 1;

		Room room = new Room();
		when(roomDaoMock.getRoomDetailsForDisconnectParticipant(roomId,tenantId)).thenReturn(room);

		assertEquals(roomService.getRoomDetailsForDisconnectParticipant(roomId,tenantId),room);
	}

	@Test(groups = {"roomService"})
	public void getRoomDetailsForDisconnectParticipantWithInvalidRoomId() {
		int roomId = -1;
		int tenantId = 1;

		Room room = new Room();
		DataAccessException dae = new DataAccessException("dae") {};
		when(roomDaoMock.getRoomDetailsForDisconnectParticipant(roomId,tenantId)).thenThrow(dae);

		assertEquals(roomService.getRoomDetailsForDisconnectParticipant(roomId,tenantId),null);
	}

	@Test(groups = {"roomService"})
	public void getRoomDetailsForDisconnectParticipantWithInvalidTenantId() {
		int roomId = 1;
		int tenantId = -1;

		Room room = new Room();
		DataAccessException dae = new DataAccessException("dae") {};
		when(roomDaoMock.getRoomDetailsForDisconnectParticipant(roomId,tenantId)).thenThrow(dae);

		assertEquals(roomService.getRoomDetailsForDisconnectParticipant(roomId,tenantId),null);
	}

	/**
	 *
	 * getRoomIdByExt(int tenantId, String roomExt, String pin)
	 */

	/**
	 *
	 * getUserCreatePublicRoomAllowed(int tenantId)
	 */
	@Test(groups = {"roomService"})
	public void getUserCreatePublicRoomAllowedWithValidTenantIdAndRoomEnableMoreThanZero(){
		int tenantId = 1;

		TenantConfiguration configuration = new TenantConfiguration();
		when(tenantServiceMock.getTenantConfiguration(tenantId)).thenReturn(configuration);

		configuration.setCreatePublicRoomEnable(1);
		configuration.setMaxCreatePublicRoomUser(5);

		assertEquals(roomService.getUserCreatePublicRoomAllowed(tenantId),5);
	}

	@Test(groups = {"roomService"})
	public void getUserCreatePublicRoomAllowedWithValidTenantIdAndRoomEnableLessThanZero(){
		int tenantId = 1;

		TenantConfiguration configuration = new TenantConfiguration();
		when(tenantServiceMock.getTenantConfiguration(tenantId)).thenReturn(configuration);

		configuration.setCreatePublicRoomEnable(-1);
		configuration.setMaxCreatePublicRoomUser(5);

		assertEquals(roomService.getUserCreatePublicRoomAllowed(tenantId),0);
	}

	@Test(groups = {"roomService"},expectedExceptions = NullPointerException.class)
	public void getUserCreatePublicRoomAllowedWithInvalidTenantId(){
		int tenantId = -1;

		when(tenantServiceMock.getTenantConfiguration(tenantId)).thenReturn(null);

		roomService.getUserCreatePublicRoomAllowed(tenantId);

	}

	/**
	 *
	 * getUserCreatePublicRoomCount(int tenantId, int ownerId)
	 */
	@Test(groups = {"roomService"})
	public void getUserCreatePublicRoomCountWithValidTenantIdAndVlaidOwnerId(){
		int tenantId = 1;
		int ownerId = 1;

		int roomExt = 5;
		when(roomDaoMock.getPublicRoomCountForOwnerID(tenantId,ownerId)).thenReturn(5);

		assertEquals(roomService.getUserCreatePublicRoomCount(tenantId,ownerId),5);
	}

	@Test(groups = {"roomService"})
	public void getUserCreatePublicRoomCountWithInvalidTenantId(){
		int tenantId = -1;
		int ownerId = 1;

		when(roomDaoMock.getPublicRoomCountForOwnerID(tenantId,ownerId)).thenReturn(0);

		assertEquals(roomService.getUserCreatePublicRoomCount(tenantId,ownerId),0);
	}

	@Test(groups = {"roomService"})
	public void getUserCreatePublicRoomCountWithInvalidOwnerId(){
		int tenantId = 1;
		int ownerId = -1;

		when(roomDaoMock.getPublicRoomCountForOwnerID(tenantId,ownerId)).thenReturn(0);

		assertEquals(roomService.getUserCreatePublicRoomCount(tenantId,ownerId),0);
	}

	/**
	 *
	 * long getPublicRoomCountForTenentID(int tenantId)
	 */
	@Test(groups = {"roomService"})
	public void getPublicRoomCountForTenentIDWithValidTenantId(){
		int tenantId = 1;

		long num = 5;
		when(roomDaoMock.getPublicRoomCountForTenentID(tenantId)).thenReturn(5);

		assertEquals(roomService.getPublicRoomCountForTenentID(tenantId),num);
	}

	@Test(groups = {"roomService"})
	public void getPublicRoomCountForTenentIDWithInvalidTenantId(){
		int tenantId = -1;

		long num = 0;
		when(roomDaoMock.getPublicRoomCountForTenentID(tenantId)).thenReturn(0);

		assertEquals(roomService.getPublicRoomCountForTenentID(tenantId),num);
	}

	/**
	 *
	 * long getTenantCreatePublicRoomRemainCount(int tenantId)
	 */
	@Test(groups = {"roomService"})
	public void getTenantCreatePublicRoomRemainCountWithValidTenantId() {
		int tenantId = 1;

		when(roomDaoMock.getPublicRoomCountForTenentID(tenantId)).thenReturn(5);

		TenantConfiguration tc = new TenantConfiguration();
		tc.setMaxCreatePublicRoomTenant(10);
		when(tenantServiceMock.getTenantConfiguration(tenantId)).thenReturn(tc);

		assertEquals(roomService.getTenantCreatePublicRoomRemainCount(tenantId),5);
	}

	@Test(groups = {"roomService"},expectedExceptions = DataAccessException.class)
	public void getTenantCreatePublicRoomRemainCountWithInvalidTenantId() {
		int tenantId = 1;

		DataAccessException dae = new DataAccessException("dae") {};
		when(roomDaoMock.getPublicRoomCountForTenentID(tenantId)).thenThrow(dae);

		roomService.getTenantCreatePublicRoomRemainCount(tenantId);
	}

	/**
	 *
	 * long getSystemCreatePublicRoomRemainCount()
	 */
	@Test(groups = {"roomService"})
	public void getSystemCreatePublicRoomRemainCount() {
		Configuration configuration = new Configuration();
		configuration.setConfigurationValue("25");
		when(systemServiceMock.getConfiguration("MAX_CREATE_PUBLIC_ROOM")).thenReturn(configuration);

		long num = 5;
		when(roomDaoMock.getPublicRoomCount()).thenReturn(num);

		assertEquals(roomService.getSystemCreatePublicRoomRemainCount(),20);
	}

	/**
	 *
	 * int getDisplayNameCount(String displayName, int tenant)
	 */
	@Test(groups = {"roomService"})
	public void getDisplayNameCountWithValidDisplayNameAndValidTenant(){
		int tenant = 1;
		String displayName = "displayName";

		int result = 5;
		when(roomDaoMock.getDisplayNameCount(displayName,tenant)).thenReturn(5);

		assertEquals(roomService.getDisplayNameCount(displayName,tenant),result);
	}

	@Test(groups = {"roomService"},expectedExceptions = DataAccessException.class)
	public void getDisplayNameCountWithInvalidInput(){
		int tenant = 1;
		String displayName = "displayName";

		DataAccessException dae = new DataAccessException("dae") {};
		when(roomDaoMock.getDisplayNameCount(displayName,tenant)).thenThrow(dae);

		roomService.getDisplayNameCount(displayName,tenant);
	}

	/**
	 *
	 * RoomCreationResponse createPublicRoom(PublicRoomCreateRequest publicRoomCreateRequest)
	 */
	@Test(groups = {"roomService"})
	public void createPublicRoomWithEmptyRoomConfig(){
		PublicRoomCreateRequest publicRoomCreateRequest = new PublicRoomCreateRequest();

		Configuration roomExtnLength = new Configuration();
		when(systemServiceMock.getConfiguration("CREATE_PUBLIC_ROOM_FLAG")).thenReturn(null);
		when(systemServiceMock.getConfiguration("SELECTED_MAX_ROOM_EXT_LENGTH")).thenReturn(roomExtnLength);

		assertEquals(roomService.createPublicRoom(publicRoomCreateRequest).getMessage(),"Public Room Feature not available");
	}

	@Test(groups = {"roomService"})
	public void createPublicRoomWithEmptyTenantConfig(){
		PublicRoomCreateRequest publicRoomCreateRequest = new PublicRoomCreateRequest();

		publicRoomCreateRequest.setTenantId(1);
		Configuration roomExtnLength = new Configuration();
		Configuration publicRoomconfig = new Configuration();
		publicRoomconfig.setConfigurationValue("value");

		when(systemServiceMock.getConfiguration("CREATE_PUBLIC_ROOM_FLAG")).thenReturn(publicRoomconfig);
		when(systemServiceMock.getConfiguration("SELECTED_MAX_ROOM_EXT_LENGTH")).thenReturn(roomExtnLength);

		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		when(tenantServiceMock.getTenantConfiguration(1)).thenReturn(null);

		assertEquals(roomService.createPublicRoom(publicRoomCreateRequest).getMessage(),"Public Room Feature not available");
	}

	/*@Test(groups = {"roomService"})
	public void createPublicRoomWithTenantPrefixNotEmptyAndRoomExist(){
		PublicRoomCreateRequest publicRoomCreateRequest = new PublicRoomCreateRequest();

		publicRoomCreateRequest.setTenantId(1);
		Configuration roomExtnLength = new Configuration();
		Configuration publicRoomconfig = new Configuration();
		publicRoomconfig.setConfigurationValue("value");
		roomExtnLength.setConfigurationValue("10");

		when(systemServiceMock.getConfiguration("CREATE_PUBLIC_ROOM_FLAG")).thenReturn(publicRoomconfig);
		when(systemServiceMock.getConfiguration("SELECTED_MAX_ROOM_EXT_LENGTH")).thenReturn(roomExtnLength);

		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setCreatePublicRoomEnable(1);

		when(tenantServiceMock.getTenantConfiguration(1)).thenReturn(tenantConfiguration);

		Tenant tenant = new Tenant();
		tenant.setTenantPrefix("tp");

		when(generatorMock.generateRandomRoomExtn(11, "")).thenReturn("5");
		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		when(roomDaoMock.isRoomExistForRoomExtNumber("tp5")).thenReturn(true);

		assertEquals(roomService.createPublicRoom(publicRoomCreateRequest).getMessage(),"Public Room Creation Failed");
	}*/

	/*@Test(groups = {"roomService"})
	public void createPublicRoomWithTenantPrefixNotEmptyAndRoomNotExistAndPinRequiredAndNotEmptyDescriptionAndRoomNotLockByDefaultAndLocked(){
		PublicRoomCreateRequest publicRoomCreateRequest = new PublicRoomCreateRequest();

		publicRoomCreateRequest.setTenantId(1);
		Configuration roomExtnLength = new Configuration();
		Configuration publicRoomconfig = new Configuration();
		publicRoomconfig.setConfigurationValue("value");
		roomExtnLength.setConfigurationValue("10");

		when(systemServiceMock.getConfiguration("CREATE_PUBLIC_ROOM_FLAG")).thenReturn(publicRoomconfig);
		when(systemServiceMock.getConfiguration("SELECTED_MAX_ROOM_EXT_LENGTH")).thenReturn(roomExtnLength);

		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setCreatePublicRoomEnable(1);

		when(tenantServiceMock.getTenantConfiguration(1)).thenReturn(tenantConfiguration);

		Tenant tenant = new Tenant();
		tenant.setTenantPrefix("tp");

		when(generatorMock.generateRandomRoomExtn(11, "")).thenReturn("5");
		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		when(roomDaoMock.isRoomExistForRoomExtNumber("tp5")).thenReturn(false);
		publicRoomCreateRequest.setPinRequired(true);
		publicRoomCreateRequest.setPIN("abc");
		publicRoomCreateRequest.setDescription("description");
		publicRoomCreateRequest.setLocked(true);

		Room room = new Room();
		when(roomDaoMock.insertRoom(1,room)).thenReturn(1);


		assertEquals(roomService.createPublicRoom(publicRoomCreateRequest).getMessage(),null);
	}*/

	/*@Test(groups = {"roomService"})
	public void createPublicRoomWithTenantPrefixEmptyAndRoomNotExistAndPinRequiredAndEmptyDescriptionAndRoomNotLock(){
		PublicRoomCreateRequest publicRoomCreateRequest = new PublicRoomCreateRequest();

		publicRoomCreateRequest.setTenantId(1);
		Configuration roomExtnLength = new Configuration();
		Configuration publicRoomconfig = new Configuration();
		publicRoomconfig.setConfigurationValue("value");
		roomExtnLength.setConfigurationValue("10");

		when(systemServiceMock.getConfiguration("CREATE_PUBLIC_ROOM_FLAG")).thenReturn(publicRoomconfig);
		when(systemServiceMock.getConfiguration("SELECTED_MAX_ROOM_EXT_LENGTH")).thenReturn(roomExtnLength);

		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setCreatePublicRoomEnable(1);

		when(tenantServiceMock.getTenantConfiguration(1)).thenReturn(tenantConfiguration);

		Tenant tenant = new Tenant();

		when(generatorMock.generateRandomRoomExtn(13, "")).thenReturn("5");
		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		when(roomDaoMock.isRoomExistForRoomExtNumber("tp5")).thenReturn(false);
		publicRoomCreateRequest.setPinRequired(true);
		publicRoomCreateRequest.setPIN("abc");
		publicRoomCreateRequest.setLocked(false);

		Room room = new Room();
		when(roomDaoMock.insertRoom(1,room)).thenReturn(1);

		assertEquals(roomService.createPublicRoom(publicRoomCreateRequest).getMessage(),null);
	}*/

	/*@Test(groups = {"roomService"})
	public void createPublicRoomWithTenantPrefixEmptyAndRoomNotExistAndPinRequiredAndEmptyDescriptionAndRoomNotLockThrowException(){
		PublicRoomCreateRequest publicRoomCreateRequest = new PublicRoomCreateRequest();

		publicRoomCreateRequest.setTenantId(1);
		Configuration roomExtnLength = new Configuration();
		Configuration publicRoomconfig = new Configuration();
		publicRoomconfig.setConfigurationValue("value");
		roomExtnLength.setConfigurationValue("10");

		when(systemServiceMock.getConfiguration("CREATE_PUBLIC_ROOM_FLAG")).thenReturn(publicRoomconfig);
		when(systemServiceMock.getConfiguration("SELECTED_MAX_ROOM_EXT_LENGTH")).thenReturn(roomExtnLength);

		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setCreatePublicRoomEnable(1);

		when(tenantServiceMock.getTenantConfiguration(1)).thenReturn(tenantConfiguration);

		Tenant tenant = new Tenant();

		when(generatorMock.generateRandomRoomExtn(13, "")).thenReturn("5");
		when(tenantServiceMock.getTenant(1)).thenReturn(tenant);

		when(roomDaoMock.isRoomExistForRoomExtNumber("tp5")).thenReturn(false);
		publicRoomCreateRequest.setPinRequired(true);
		publicRoomCreateRequest.setPIN("abc");
		publicRoomCreateRequest.setLocked(false);

		Room room = new Room();
		NullPointerException e = new NullPointerException();
		when(roomDaoMock.insertRoom(1,room)).thenThrow(e);

		assertEquals(roomService.createPublicRoom(publicRoomCreateRequest).getMessage(), "Public Room Creation Failed");
	}*/

	/**
	 *
	 * int isPrefixExistingInDefaultTenant(String tenantPrefix)
	 */
	@Test(groups = {"roomService"})
	public void isPrefixExistingInDefaultTenantWithValidTenantPrefix(){
		String tenantPrefix = "Prefix";
		when(roomDaoMock.getDefaultTenantRoomsCountMatchingPrefix(tenantPrefix)).thenReturn(10);

		assertEquals(roomService.isPrefixExistingInDefaultTenant(tenantPrefix),10);
	}

	@Test(groups = {"roomService"})
	public void isPrefixExistingInDefaultTenantWithInvalidTenantPrefix(){
		String tenantPrefix = "";
		when(roomDaoMock.getDefaultTenantRoomsCountMatchingPrefix(tenantPrefix)).thenReturn(0);

		assertEquals(roomService.isPrefixExistingInDefaultTenant(tenantPrefix),0);
	}

	/**
	 *
	 * Entity getOneEntityDetails(int entityID, User user)
	 */

	/**
	 *
	 * int getDisplayNameCount(String displayName, int roomId, int tenant)
	 */
	@Test(groups = {"roomService"})
	public void getDisplayNameCountWithValidInput(){
		String displayName = "Name";
		int roomId = 1;
		int tenant = 1;

		when(roomDaoMock.getDisplayNameCount("Name",1,1)).thenReturn(5);

		assertEquals(roomService.getDisplayNameCount(displayName,roomId,tenant),5);
	}

	@Test(groups = {"roomService"})
	public void getDisplayNameCountWithInvalidDisplayname(){
		String displayName = "";
		int roomId = 1;
		int tenant = 1;

		when(roomDaoMock.getDisplayNameCount("",1,1)).thenReturn(0);

		assertEquals(roomService.getDisplayNameCount(displayName,roomId,tenant),0);
	}

	@Test(groups = {"roomService"})
	public void getDisplayNameCountWithInvalidRoomId(){
		String displayName = "Name";
		int roomId = 0;
		int tenant = 1;

		when(roomDaoMock.getDisplayNameCount("Name",0,1)).thenReturn(0);

		assertEquals(roomService.getDisplayNameCount(displayName,roomId,tenant),0);
	}

	@Test(groups = {"roomService"})
	public void getDisplayNameCountWithInvalidTenant(){
		String displayName = "Name";
		int roomId = 1;
		int tenant = 0;

		when(roomDaoMock.getDisplayNameCount("Name",1,0)).thenReturn(0);

		assertEquals(roomService.getDisplayNameCount(displayName,roomId,tenant),0);
	}

	/**
	 *
	 * Room getRoomDetailsByRoomKey(String roomKey, int tenantId)
	 */
	@Test(groups = {"roomService"})
	public void getRoomDetailsByRoomKeyWithValidInput(){
		String roomKey = "roomKey";
		int tenantId = 1;

		Room room = new Room();
		when(roomDaoMock.getRoomDetailsByRoomKey(roomKey,tenantId)).thenReturn(room);

		assertEquals(roomService.getRoomDetailsByRoomKey(roomKey,tenantId),room);
	}

	@Test(groups = {"roomService"})
	public void getRoomDetailsByRoomKeyWithInvalidKey(){
		String roomKey = "";
		int tenantId = 1;

		DataAccessException dae = new DataAccessException("dae") {};
		when(roomDaoMock.getRoomDetailsByRoomKey(roomKey,tenantId)).thenThrow(dae);

		assertEquals(roomService.getRoomDetailsByRoomKey(roomKey,tenantId),null);
	}

	@Test(groups = {"roomService"})
	public void getRoomDetailsByRoomKeyWithInvalidId(){
		String roomKey = "Key";
		int tenantId = 0;

		DataAccessException dae = new DataAccessException("dae") {};
		when(roomDaoMock.getRoomDetailsByRoomKey(roomKey,tenantId)).thenThrow(dae);

		assertEquals(roomService.getRoomDetailsByRoomKey(roomKey,tenantId),null);
	}

	/**
	 *
	 * String getRoomURL(ISystemService system, String scheme, String host, String roomKey)
	 */
	public void getRoomURLWithValidInput(){
		ISystemService system = new SystemServiceImpl();
		String scheme = "scheme";
		String host = "host";
		String roomKey = "roomKey";
	}

	/**
	 *
	 * int getRoomKeyLength()
	 */
	public void getRoomKeyLengthWithEmptyConfigValue() {
		Configuration roomKeyConfig = new Configuration();
		roomKeyConfig.setConfigurationValue("");

		when(systemServiceMock.getConfiguration("ROOM_KEY_LENGTH")).thenReturn(roomKeyConfig);

	}

	/**
	 *
	 * boolean isPublicRoomIdExistsForMemberId(int roomId, int memberId, int tenantId)
	 */
	@Test(groups = {"roomService"})
	public void isPublicRoomIdExistsForMemberIdWithValidInput(){
		int roomId = 1;
		int memberId = 2;
		int tenantId = 3;
		when(roomDaoMock.isPublicRoomIdExistsForMemberId(1,2,3)).thenReturn(true);

		assertEquals(roomService.isPublicRoomIdExistsForMemberId(roomId,memberId,tenantId),true);
	}

	@Test(groups = {"roomService"})
	public void isPublicRoomIdExistsForMemberIdWithInvalidRoomId(){
		int roomId = 0;
		int memberId = 2;
		int tenantId = 3;
		when(roomDaoMock.isPublicRoomIdExistsForMemberId(0,2,3)).thenReturn(false);

		assertEquals(roomService.isPublicRoomIdExistsForMemberId(roomId,memberId,tenantId),false);
	}

	@Test(groups = {"roomService"})
	public void isPublicRoomIdExistsForMemberIdWithInvalidMemberId(){
		int roomId = 1;
		int memberId = 0;
		int tenantId = 3;
		when(roomDaoMock.isPublicRoomIdExistsForMemberId(1,0,3)).thenReturn(false);

		assertEquals(roomService.isPublicRoomIdExistsForMemberId(roomId,memberId,tenantId),false);
	}

	@Test(groups = {"roomService"})
	public void isPublicRoomIdExistsForMemberIdWithInvalidTenantId(){
		int roomId = 1;
		int memberId = 2;
		int tenantId = 0;
		when(roomDaoMock.isPublicRoomIdExistsForMemberId(1,2,0)).thenReturn(false);

		assertEquals(roomService.isPublicRoomIdExistsForMemberId(roomId,memberId,tenantId),false);
	}

	/**
	 *
	 * int silenceSpeakerForRoomServer(int roomId, int flag)
	 */
	@Test(groups = {"roomService"})
	public void silenceSpeakerForRoomServerWithValidInput(){
		int roomId = 1;
		int flag = 2;

		when(roomDaoMock.silenceSpeakerForRoomServer(roomId,flag)).thenReturn(1);

		assertEquals(roomService.silenceSpeakerForRoomServer(roomId,flag),1);
	}

	@Test(groups = {"roomService"})
	public void silenceSpeakerForRoomServerWithInvlaidRoomId(){
		int roomId = 0;
		int flag = 2;

		when(roomDaoMock.silenceSpeakerForRoomServer(roomId,flag)).thenReturn(0);

		assertEquals(roomService.silenceSpeakerForRoomServer(roomId,flag),0);
	}

	@Test(groups = {"roomService"})
	public void silenceSpeakerForRoomServerWithInvlaidFlag(){
		int roomId = 1;
		int flag = -1;

		when(roomDaoMock.silenceSpeakerForRoomServer(roomId,flag)).thenReturn(0);

		assertEquals(roomService.silenceSpeakerForRoomServer(roomId,flag),0);
	}

	/**
	 *
	 * List<Room> getRoomStatusAndNumOfParticipants(List<Room> roomsToCheck)
	 */
	@Test(groups = {"roomService"})
	public void getRoomStatusAndNumOfParticipantsWithValidRoomToCheckAndEmptyConferenceRoom(){
		List<Room> roomsToCheck = new ArrayList<Room>();

		Room r1 = new Room();
		r1.setRoomID(1);
		roomsToCheck.add(r1);

		List<Integer> roomIds = roomsToCheck.stream().map(Room::getRoomID).collect(Collectors.toList());
		List<Room> conferenceStatusRooms = new ArrayList<>();
		conferenceStatusRooms.add(r1);

		when(roomDaoMock.findRoomStatusAndNumOfParticipants(roomIds)).thenReturn(conferenceStatusRooms);

		assertEquals(roomService.getRoomStatusAndNumOfParticipants(roomsToCheck),roomsToCheck);
	}

	@Test(groups = {"roomService"})
	public void getRoomStatusAndNumOfParticipantsWithEmptyRoomToCheck(){
		List<Room> roomsToCheck = new ArrayList<Room>();

		assertEquals(roomService.getRoomStatusAndNumOfParticipants(roomsToCheck),roomsToCheck);
	}

	@Test(groups = {"roomService"})
	public void getRoomStatusAndNumOfParticipantsWithEmptyConferenceStatusRooms(){
		List<Room> roomsToCheck = new ArrayList<Room>();

		Room r1 = new Room();
		r1.setRoomID(1);
		roomsToCheck.add(r1);

		List<Integer> roomIds = roomsToCheck.stream().map(Room::getRoomID).collect(Collectors.toList());

		when(roomDaoMock.findRoomStatusAndNumOfParticipants(roomIds)).thenReturn(null);

		assertEquals(roomService.getRoomStatusAndNumOfParticipants(roomsToCheck),roomsToCheck);
	}

	@Test(groups = {"roomService"},expectedExceptions = NoSuchElementException.class)
	public void getRoomStatusAndNumOfParticipantsWithValidRoomToCheckAndEmptyRoomResult(){
		List<Room> roomsToCheck = new ArrayList<Room>();

		Room r1 = new Room();
		r1.setRoomID(1);
		roomsToCheck.add(r1);

		List<Integer> roomIds = roomsToCheck.stream().map(Room::getRoomID).collect(Collectors.toList());
		List<Room> conferenceStatusRooms = new ArrayList<>();
		Room r2 = new Room();
		r2.setRoomID(2);
		conferenceStatusRooms.add(r2);

		when(roomDaoMock.findRoomStatusAndNumOfParticipants(roomIds)).thenReturn(conferenceStatusRooms);

		roomService.getRoomStatusAndNumOfParticipants(roomsToCheck);
	}
}
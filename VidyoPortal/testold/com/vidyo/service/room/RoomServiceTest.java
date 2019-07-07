/**
 * 
 */
package com.vidyo.service.room;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.RecoverableDataAccessException;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Tenant;
import com.vidyo.db.IRoomDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.RoomServiceImpl;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.room.RoomCreationResponse;
import com.vidyo.service.room.SchRoomCreationRequest;
import com.vidyo.service.room.request.RoomUpdateRequest;
import com.vidyo.service.system.SystemService;
import com.vidyo.utils.Generator;
import com.vidyo.utils.room.RoomUtils;

import java.util.Arrays;

/**
 * @author Ganesh
 * 
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class RoomServiceTest {

	/**
	 * 
	 */
	@SpringBeanByType
	private IRoomService roomService;

	/**
	 * 
	 */
	private Mock<IRoomDao> mockRoomDao;

	/**
	 * 
	 */
	private Mock<Generator> mockGenerator;

	/**
	 *
	 */
	//private Mock<TenantHolder> mockTenantHolder;

	/**
	 * 
	 */
	private Mock<SystemService> mockSystemService;
	
	/**
	 * 
	 */
	private Mock<ITenantService> mockTenantService;
	
	/**
	 * 
	 */
	private Mock<MemberService> mockMemberService;

	/**
	 * 
	 */
	@Before
	public void initialize() {
		((RoomServiceImpl) roomService).setDao(mockRoomDao.getMock());
		((RoomServiceImpl) roomService).setGenerator(mockGenerator.getMock());
		//((RoomServiceImpl) roomService).setTenant(mockTenantHolder.getMock());
		((RoomServiceImpl) roomService).setSystemService(mockSystemService.getMock());
		((RoomServiceImpl) roomService).setTenantService(mockTenantService.getMock());
		((RoomServiceImpl)(roomService)).setMemberService(mockMemberService.getMock());
	}

	/**
	 * 
	 */
	@Test
	public void testCreateScheduledRoomFailure() {
		mockGenerator.returns(234).generateRandom();
		mockRoomDao.returns(true).isRoomExistForRoomExtNumber("777" + 100 + 234);
		mockRoomDao.returns(1).insertRoom(1, null);
		//mockTenantHolder.returns(1).getValue();
		SchRoomCreationRequest creationRequest = new SchRoomCreationRequest();
		creationRequest.setTenantId(1);
		creationRequest.setMemberId(100);
		creationRequest.setGroupId(1);
		creationRequest.setMemberName("ganesh");
		creationRequest.setRecurring(0);		
		RoomCreationResponse roomCreationResponse = roomService.createScheduledRoom(creationRequest);
		Assert.assertNull(roomCreationResponse.getRoom());		
	}
	
	/**
	 * 
	 */
	@Test
	public void testCreateScheduledRoomInsertFailure() {
		mockGenerator.returns(234).generateRandom();
		mockRoomDao.returns(true).isRoomExistForRoomExtNumber("777" + 100 + 234);
		mockRoomDao.raises(new Exception()).insertRoom(1, null);
		//mockTenantHolder.returns(1).getValue();
		SchRoomCreationRequest creationRequest = new SchRoomCreationRequest();
		creationRequest.setTenantId(1);
		creationRequest.setMemberId(100);
		creationRequest.setGroupId(1);
		creationRequest.setMemberName("ganesh");
		creationRequest.setRecurring(0);
		RoomCreationResponse roomCreationResponse = roomService.createScheduledRoom(creationRequest);
		Assert.assertNull(roomCreationResponse.getRoom());		
	}	
	
	/**
	 * 
	 */
	@Test
	public void testCreateScheduledRoomSuccess() {
		mockGenerator.returns(234).generateRandom();
		mockRoomDao.returns(false).isRoomExistForRoomExtNumber("777" + 100 + 234);
		mockRoomDao.returns(1).insertRoom(1, null);
		//mockTenantHolder.returns(1).getValue();
		Configuration scheduledRoomconfig = new Configuration();
		scheduledRoomconfig.setConfigurationValue("777");
		mockSystemService.returns(scheduledRoomconfig).getConfiguration("SCHEDULED_ROOM_PREFIX");
		Tenant tenant = new Tenant();
		tenant.setScheduledRoomEnabled(1);
		mockTenantService.returns(tenant).getTenant(1);
		mockGenerator.returns("100-234").generateSchRoomExtnWithPin(100, 234);
		SchRoomCreationRequest creationRequest = new SchRoomCreationRequest();
		creationRequest.setTenantId(1);
		creationRequest.setMemberId(100);
		creationRequest.setGroupId(1);
		creationRequest.setMemberName("ganesh");
		creationRequest.setRecurring(0);		
		RoomCreationResponse roomCreationResponse = roomService.createScheduledRoom(creationRequest);
		Assert.assertNotNull(roomCreationResponse.getRoom());
		Assert.assertEquals("777100", roomCreationResponse.getRoom().getRoomExtNumber());
	}
	
	/**
	 * 
	 */
	@Test
	public void testStringSplit() {
		String v = "43865435454";
		String[] vArr = v.split(":");
		System.out.println(Arrays.toString(vArr));
	}
	
	/**
	 * Update Room's group by MemberId and RoomType.
	 * Success case
	 */
	@Test
	public void testUpdateGroupByMember() {
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();
		roomUpdateRequest.setMemberId(20);
		roomUpdateRequest.setNewGroupId(10);
		roomUpdateRequest.setRoomType("Scheduled");
		mockRoomDao.returns(1).updateGroupByMember(roomUpdateRequest);
		int actual = roomService.updateGroupByMember(roomUpdateRequest);
		Assert.assertEquals(1, actual);
	}
	
	/**
	 * Update Room's group by MemberId and RoomType.
	 * Failure case when db exception is thrown.
	 * Service method should be able to handle the exception.
	 */
	@Test
	public void testUpdateGroupByMemberFail() {
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();
		roomUpdateRequest.setMemberId(20);
		roomUpdateRequest.setNewGroupId(10);
		roomUpdateRequest.setRoomType("Scheduled");
		//DataAccessException is an abstract class, so mocking it with a subclass of its
		mockRoomDao.raises(RecoverableDataAccessException.class).updateGroupByMember(roomUpdateRequest);
		int actual = roomService.updateGroupByMember(roomUpdateRequest);
		Assert.assertEquals(0, actual);
	}
	
	/**
	 * 
	 */
	@Test
	public void testValidExtensionByNonEmptyPrefix() {
		boolean val = RoomUtils.isValidExtension("123456", "123");
		Assert.assertTrue(val);
	}

	/**
	 * 
	 */
	@Test
	public void testValidExtensionByEmptyPrefix() {
		boolean val = RoomUtils.isValidExtension("123456", "");
		Assert.assertTrue(val);
	}

	/**
	 * 
	 */
	@Test
	public void testValidExtensionByNullPrefix() {
		boolean val = RoomUtils.isValidExtension("123456", null);
		Assert.assertTrue(val);
	}

	/**
	 * 
	 */
	@Test
	public void testInValidExtensionByNonEmptyPrefix() {
		boolean val = RoomUtils.isValidExtension("1123456", "123");
		Assert.assertFalse(val);
	}
	
	/**
	 * 
	 */
	@Test
	public void testGenerateRoomExtension() {
		mockMemberService.returns(100l).getCountAllSeats();
		TenantContext.setTenantId(10);
		mockGenerator.returns(1000).generateRandom();
		mockRoomDao.returns(Boolean.TRUE).isRoomExistForRoomExtNumber("101001000");
		mockGenerator.returns("1001000").generateSchRoomExtnWithoutPin(100, 1000);
		String extn = roomService.generateRoomExt("10");
		System.out.println(extn);
	
	}

}

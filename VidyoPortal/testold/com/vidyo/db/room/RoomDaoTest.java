/**
 * 
 */
package com.vidyo.db.room;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;

import com.vidyo.bo.EntityFilter;
import com.vidyo.bo.Room;
import com.vidyo.db.BaseDAOTest;
import com.vidyo.db.IRoomDao;
import com.vidyo.db.RoomDaoJdbcImpl;
import com.vidyo.service.room.request.RoomUpdateRequest;

/**
 * @author ganesh
 * 
 */
@DataSet(loadStrategy = CleanInsertLoadStrategy.class)
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class RoomDaoTest extends BaseDAOTest {

	private IRoomDao roomDao;

	@Before
	public void initializeDao() {
		roomDao = new RoomDaoJdbcImpl();
		((RoomDaoJdbcImpl) roomDao).setDataSource(getDataSource());
	}

	/**
	 * Success Test case for updating scheduled room's group
	 */
	@Test
	public void testUpdateRoomGroupDetailByValidMember() {
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();
		roomUpdateRequest.setMemberId(20);
		roomUpdateRequest.setRoomType("Scheduled");
		roomUpdateRequest.setNewGroupId(20);
		int updateCount = roomDao.updateGroupByMember(roomUpdateRequest);
		// Only one scheduled room row present in the db
		Assert.assertEquals(1, updateCount);
	}

	/**
	 * Failure Test case for updating scheduled room's group
	 */
	@Test
	public void testUpdateRoomGroupDetailByInvalidMember() {
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();
		// Remove the memberid from the query and zero rows should be updated
		// roomUpdateRequest.setMemberId(20);
		roomUpdateRequest.setRoomType("Scheduled");
		roomUpdateRequest.setNewGroupId(20);
		int updateCount = roomDao.updateGroupByMember(roomUpdateRequest);
		Assert.assertEquals(0, updateCount);
	}

	/**
	 * Failure Test case for updating scheduled room's group
	 */
	@Test
	public void testUpdateRoomGroupDetailByInvalidRoomType() {
		RoomUpdateRequest roomUpdateRequest = new RoomUpdateRequest();
		// Invalid Room Type and zero rows should be updated
		roomUpdateRequest.setMemberId(20);
		roomUpdateRequest.setRoomType("InvalidRoomType");
		roomUpdateRequest.setNewGroupId(20);
		int updateCount = roomDao.updateGroupByMember(roomUpdateRequest);
		Assert.assertEquals(0, updateCount);
	}

	/**
	 * Test case to get online entities by OwnerId/MemberId
	 */
	@Test
	public void testGetOnlineCountForSearchByEntityId() {
		EntityFilter entityFilter = new EntityFilter();
		roomDao.getCountEntityByOwnerID(0, null, null, 1);
	}

	/**
	 * 
	 */
	@Test
	public void testGetAccessibleRoomDetailsByNonExistingId() {
		int roomId = -100;
		List<Integer> canCallTenantIds = new ArrayList<Integer>();
		canCallTenantIds.add(1);
		Room room = roomDao.getAccessibleRoomDetails(roomId, canCallTenantIds);
		Assert.assertNull(room);
	}

	/**
	 * 
	 */
	@Test
	public void testGetAccessibleRoomDetailsByScheduledRoomId() {
		int roomId = 30128;
		List<Integer> canCallTenantIds = new ArrayList<Integer>();
		canCallTenantIds.add(1);
		Room room = roomDao.getAccessibleRoomDetails(roomId, canCallTenantIds);
		Assert.assertNull(room);
	}

	/**
	 * 
	 */
	@Test
	public void testGetAccessibleRoomDetailsByPersonalRoomId() {
		int roomId = 30125;
		List<Integer> canCallTenantIds = new ArrayList<Integer>();
		canCallTenantIds.add(1);
		Room room = roomDao.getAccessibleRoomDetails(roomId, canCallTenantIds);
		Assert.assertNotNull(room);
		Assert.assertEquals(30125, room.getRoomID());
		Assert.assertEquals(3, room.getMemberID());
	}
	
	/**
	 * 
	 */
	@Test
	public void testGetAccessibleRoomDetailsByPublicRoomId() {
		int roomId = 30126;
		List<Integer> canCallTenantIds = new ArrayList<Integer>();
		canCallTenantIds.add(1);
		Room room = roomDao.getAccessibleRoomDetails(roomId, canCallTenantIds);
		Assert.assertNotNull(room);
		Assert.assertEquals(30126, room.getRoomID());
		Assert.assertEquals(3, room.getMemberID());
	}	

}

package com.vidyo.db.member;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;

import com.vidyo.bo.MemberRoles;
import com.vidyo.db.BaseDAOTest;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.MemberDaoJdbcImpl;

@DataSet(loadStrategy = CleanInsertLoadStrategy.class)
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class MemberDaoJdbcImplTest extends BaseDAOTest {
	
	private IMemberDao memberDao;
	
	@Before
	public void initializeDao() {
		memberDao = new MemberDaoJdbcImpl();
		((MemberDaoJdbcImpl)memberDao).setDataSource(getDataSource());
	}
	
	@Test
	public void getSamlMemberRolesSuccess() {
		List<MemberRoles> roles = memberDao.getSamlMemberRoles();
		
		assertNotNull(roles);
		assertEquals(2, roles.size());
		assertEquals("Normal", roles.get(0).getRoleName());
		assertEquals("Executive", roles.get(1).getRoleName());
	}
	
	@Test
	public void getCountSamlMemberRolesSuccess() {
		Long count = memberDao.getCountSamlMemberRoles();
		
		assertEquals(new Long(2), count);
	}

}

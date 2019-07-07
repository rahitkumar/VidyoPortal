package com.vidyo.db.routerpools;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.vidyo.superapp.routerpools.bo.Pool;
import com.vidyo.superapp.routerpools.bo.PoolPriorityList;
import com.vidyo.superapp.routerpools.db.RouterPoolsDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class RouterPoolsDaoJpaImplTests {
	
	@Autowired
	private RouterPoolsDAO routerPoolsDAO;
	
	@Test
	@DatabaseSetup("sampleData.xml")
	public void testFindPools() {
		List<Pool> pools = routerPoolsDAO.findAllPools(1);
		Assert.assertEquals(16, pools.get(0).getPoolKey().getId());
	}
	
	
	@Test
	@DatabaseSetup("sampleData.xml")
	public void testGetPriorityList() {
		List<PoolPriorityList> poolPriorityLists = routerPoolsDAO.getAllPriorityLists(1);		
	}
	
	@Test
	@DatabaseSetup("sampleData.xml")
	public void testGetUnassignedPools() {
		List<Pool> pools = routerPoolsDAO.getUnassignedPools(1, 1);
	}
	
	@Test
	@DatabaseSetup("sampleData.xml")
	public void testGetAssignedPools() {
		List<Pool> pools = routerPoolsDAO.getAssignedPools(10, 1);
		System.out.println("pools size" + pools.size());
		System.out.println("pools "+ pools);
		for(Pool pool : pools) {
			System.out.println("Pool" + pool.getOrder());
		}
	}
}

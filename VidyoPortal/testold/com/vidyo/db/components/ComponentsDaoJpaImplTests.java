package com.vidyo.db.components;

import java.util.ArrayList;
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
import com.vidyo.superapp.components.bo.Component;
import com.vidyo.superapp.components.bo.VidyoManager;
import com.vidyo.superapp.components.bo.VidyoRouter;
import com.vidyo.superapp.components.db.ComponentsDAO;
import com.vidyo.superapp.components.repository.VidyoRouterRepository;
import com.vidyo.superapp.components.service.ComponentsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DbUnitTestExecutionListener.class })
public class ComponentsDaoJpaImplTests {

	@Autowired
	private ComponentsDAO componentsDAO;
	
	@Autowired
	private ComponentsService componentsService;
	
	@Autowired
	private VidyoRouterRepository routerRepository;

	@Test
	@DatabaseSetup("sampleData.xml")
	public void testFindCompById() {
		Component component = componentsDAO.findComponentByID(1);
		Assert.assertEquals(1, component.getId());
		Assert.assertEquals("VM0001", component.getCompID());
	}

	@Test
	@DatabaseSetup("sampleData.xml")
	public void testGetVidyoManagerById() {
		VidyoManager vidyoManager = componentsDAO.getVidyoManagerById(1);
		Assert.assertEquals(1, vidyoManager.getId());
	}

	@Test
	@DatabaseSetup("sampleData.xml")
	public void testUpdateVidyoManager() {
		VidyoManager vidyoManager = componentsDAO.getVidyoManagerById(1);
		Assert.assertEquals(1, vidyoManager.getId());
		vidyoManager.setDscpvalue(100);
		componentsDAO.updateVidyoManager(vidyoManager);
	}

	@Test
	@DatabaseSetup("sampleData.xml")
	public void testGetComponentsByType() {
		List<Component> comps = componentsDAO.findAllComponents("", "", "", 0);
		Assert.assertNotNull(comps);
		Assert.assertEquals(1, comps.size());
	}
	
	@Test
	@DatabaseSetup("sampleData.xml")
	public void testGetEmbeddedRouters() {
		routerRepository.getRouterByIpAddress("172.16.42.216");
	}
	
	@Test
	@DatabaseSetup("sampleData.xml")
	public void testGetVidyoRouterById() {
		VidyoRouter vidyoManager = componentsService.getVidyoRouterById(2);
		Assert.assertEquals(2, vidyoManager.getId());
	}

}

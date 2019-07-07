package com.vidyo.db.components.gateway;

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
import com.vidyo.superapp.components.bo.VidyoGateway;
import com.vidyo.superapp.components.repository.gateway.GatewayPrefixRepository;
import com.vidyo.superapp.components.repository.gateway.GatewayRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class })
public class GatewayCompJpaImplTests {
	
	@Autowired
	private GatewayRepository gatewayRepository;
	
	@Autowired
	private GatewayPrefixRepository gatewayPrefixRepository;
	
	@Test
	@DatabaseSetup("sampleData.xml")
	public void testFindGatewayById() {
		VidyoGateway gateway = gatewayRepository.findGatewayByCompId(1);
		Assert.assertEquals(1, gateway.getId());
	}


}

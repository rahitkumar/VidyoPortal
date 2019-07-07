package com.vidyo.service.idp;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;

import static org.unitils.mock.ArgumentMatchers.*;

import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.GroupFilter;
import com.vidyo.bo.Proxy;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;
import com.vidyo.db.IGroupDao;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.IServiceDao;
import com.vidyo.db.idp.ITenantIdpAttributesMappingDao;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TenantIdpAttributesMappingImplTest {
	
	@SpringBeanByType
	private TenantIdpAttributesMapping tenantIdpAttributesMapping;
	
	private Mock<ITenantIdpAttributesMappingDao> mockTenantIdpAttributesMappingDao;
	private Mock<IGroupDao> mokeGroupDao;
	private Mock<IMemberDao> mockMemberDao;
	private Mock<IServiceDao> mockServiceDao;
	
	@Before
	public void initialize() {
		((TenantIdpAttributesMappingImpl) tenantIdpAttributesMapping).setGroupDao(mokeGroupDao.getMock());
		((TenantIdpAttributesMappingImpl) tenantIdpAttributesMapping).setMemberDao(mockMemberDao.getMock());
		((TenantIdpAttributesMappingImpl) tenantIdpAttributesMapping).setTenantIdpAttributesMappingDao(mockTenantIdpAttributesMappingDao.getMock());
	}
	
	@Test
	public void getTenantIdpAttributeValueMappingForEditProxyTestSuccess1() {
		TenantIdpAttributeValueMapping tenantIdpAttributeValueMapping = new TenantIdpAttributeValueMapping();
		tenantIdpAttributeValueMapping.setValueID(1);
		tenantIdpAttributeValueMapping.setMappingID(6);
		tenantIdpAttributeValueMapping.setVidyoValueName("VP");
		tenantIdpAttributeValueMapping.setIdpValueName("");
		
		List<TenantIdpAttributeValueMapping> tenantIdpAttributeValueMappings = new ArrayList<>();
		tenantIdpAttributeValueMappings.add(tenantIdpAttributeValueMapping);
		mockTenantIdpAttributesMappingDao.returns(tenantIdpAttributeValueMappings).getTenantIdpAttributeValueMapping(1, 6);
		
		
		TenantIdpAttributeMapping tenantIdpAttributeMapping = new TenantIdpAttributeMapping();
		tenantIdpAttributeMapping.setTenantID(1);
		tenantIdpAttributeMapping.setMappingID(6);
		tenantIdpAttributeMapping.setVidyoAttributeName("Proxy");
		tenantIdpAttributeMapping.setIdpAttributeName("aaa:bbb:333");
		tenantIdpAttributeMapping.setDefaultAttributeValue("No Proxy");
		
		mockTenantIdpAttributesMappingDao.returns(tenantIdpAttributeMapping).getTenantIdpAttributeMappingRecord(1, 6);
		
		Proxy proxy = new Proxy();
		proxy.setProxyID(1);
		proxy.setProxyName("VP");
		List<Proxy> proxies = new ArrayList<>();
		proxies.add(proxy);
		
		mockServiceDao.returns(proxies).getProxies(1);
		
		List<TenantIdpAttributeValueMapping> result = tenantIdpAttributesMapping.getTenantIdpAttributeValueMappingForEdit(1, 6);
		assertEquals(1, result.size());
		assertEquals(tenantIdpAttributeValueMapping.getValueID(), result.get(0).getValueID());
		assertEquals(tenantIdpAttributeValueMapping.getMappingID(), result.get(0).getMappingID());
		assertEquals(tenantIdpAttributeValueMapping.getVidyoValueName(), result.get(0).getVidyoValueName());
		assertEquals(tenantIdpAttributeValueMapping.getIdpValueName(), result.get(0).getIdpValueName());
		
		mockTenantIdpAttributesMappingDao.assertInvoked().getTenantIdpAttributeValueMapping(1, 6);
		mockTenantIdpAttributesMappingDao.assertNotInvoked().saveTenantIdpAttributeValueMappingRecord(anyInt(),anyInt(), anyInt(), any(String.class), any(String.class));
		mockTenantIdpAttributesMappingDao.assertInvoked().getTenantIdpAttributeValueMapping(1, 6);
		mockTenantIdpAttributesMappingDao.assertNotInvoked().getTenantIdpAttributeValueMapping(1, 6);
		mokeGroupDao.assertNotInvoked().getGroups(anyInt(), any(GroupFilter.class));
	}
	
	@Test
	public void getTenantIdpAttributeValueMappingForEditProxyTestSuccess2() {
		TenantIdpAttributeValueMapping tenantIdpAttributeValueMapping = new TenantIdpAttributeValueMapping();
		tenantIdpAttributeValueMapping.setValueID(1);
		tenantIdpAttributeValueMapping.setMappingID(6);
		tenantIdpAttributeValueMapping.setVidyoValueName("VP");
		tenantIdpAttributeValueMapping.setIdpValueName("");
		
		List<TenantIdpAttributeValueMapping> tenantIdpAttributeValueMappings = new ArrayList<>();
		tenantIdpAttributeValueMappings.add(tenantIdpAttributeValueMapping);
		mockTenantIdpAttributesMappingDao.returns(tenantIdpAttributeValueMappings).getTenantIdpAttributeValueMapping(1, 6);
		
		
		TenantIdpAttributeMapping tenantIdpAttributeMapping = new TenantIdpAttributeMapping();
		tenantIdpAttributeMapping.setTenantID(1);
		tenantIdpAttributeMapping.setMappingID(6);
		tenantIdpAttributeMapping.setVidyoAttributeName("Proxy");
		tenantIdpAttributeMapping.setIdpAttributeName("aaa:bbb:333");
		tenantIdpAttributeMapping.setDefaultAttributeValue("No Proxy");
		
		mockTenantIdpAttributesMappingDao.returns(tenantIdpAttributeMapping).getTenantIdpAttributeMappingRecord(1, 6);
		
		Proxy proxy = new Proxy();
		proxy.setProxyID(2);
		proxy.setProxyName("VP2");
		List<Proxy> proxies = new ArrayList<>();
		proxies.add(proxy);
		
		mockServiceDao.returns(proxies).getProxies(1);
		
		List<TenantIdpAttributeValueMapping> result = tenantIdpAttributesMapping.getTenantIdpAttributeValueMappingForEdit(1, 6);
		assertEquals(1, result.size());
		assertEquals(tenantIdpAttributeValueMapping.getValueID(), result.get(0).getValueID());
		assertEquals(tenantIdpAttributeValueMapping.getMappingID(), result.get(0).getMappingID());
		assertEquals(tenantIdpAttributeValueMapping.getVidyoValueName(), result.get(0).getVidyoValueName());
		assertEquals(tenantIdpAttributeValueMapping.getIdpValueName(), result.get(0).getIdpValueName());
		
		mockTenantIdpAttributesMappingDao.assertInvoked().getTenantIdpAttributeValueMapping(1, 6);
		mockTenantIdpAttributesMappingDao.assertInvoked().saveTenantIdpAttributeValueMappingRecord(anyInt(),anyInt(), anyInt(), any(String.class), any(String.class));
		mockTenantIdpAttributesMappingDao.assertInvoked().getTenantIdpAttributeValueMapping(1, 6);
		mockTenantIdpAttributesMappingDao.assertNotInvoked().getTenantIdpAttributeValueMapping(1, 6);
		mokeGroupDao.assertNotInvoked().getGroups(anyInt(), any(GroupFilter.class));
	}
	
	@Test
	public void getTenantIdpAttributeValueMappingForEditProxyTestSuccess3() {
		TenantIdpAttributeValueMapping tenantIdpAttributeValueMapping = new TenantIdpAttributeValueMapping();
		tenantIdpAttributeValueMapping.setValueID(1);
		tenantIdpAttributeValueMapping.setMappingID(6);
		tenantIdpAttributeValueMapping.setVidyoValueName("VP");
		tenantIdpAttributeValueMapping.setIdpValueName("");
		
		List<TenantIdpAttributeValueMapping> tenantIdpAttributeValueMappings = new ArrayList<>();
		tenantIdpAttributeValueMappings.add(tenantIdpAttributeValueMapping);
		mockTenantIdpAttributesMappingDao.returns(tenantIdpAttributeValueMappings).getTenantIdpAttributeValueMapping(1, 6);
		
		mockTenantIdpAttributesMappingDao.returns(null).getTenantIdpAttributeMappingRecord(1, 6);
		
		List<TenantIdpAttributeValueMapping> result = tenantIdpAttributesMapping.getTenantIdpAttributeValueMappingForEdit(1, 6);
		assertEquals(1, result.size());
		assertEquals(tenantIdpAttributeValueMapping.getValueID(), result.get(0).getValueID());
		assertEquals(tenantIdpAttributeValueMapping.getMappingID(), result.get(0).getMappingID());
		assertEquals(tenantIdpAttributeValueMapping.getVidyoValueName(), result.get(0).getVidyoValueName());
		assertEquals(tenantIdpAttributeValueMapping.getIdpValueName(), result.get(0).getIdpValueName());
		
		mockTenantIdpAttributesMappingDao.assertInvoked().getTenantIdpAttributeValueMapping(1, 6);
		mockTenantIdpAttributesMappingDao.assertNotInvoked().saveTenantIdpAttributeValueMappingRecord(anyInt(),anyInt(), anyInt(), any(String.class), any(String.class));
		mockServiceDao.assertNotInvoked().getProxies(anyInt());
		mockTenantIdpAttributesMappingDao.assertNotInvoked().getTenantIdpAttributeValueMapping(1, 6);
		mokeGroupDao.assertNotInvoked().getGroups(anyInt(), any(GroupFilter.class));
	}

}

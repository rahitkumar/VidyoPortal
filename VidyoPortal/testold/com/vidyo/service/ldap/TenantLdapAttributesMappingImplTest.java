package com.vidyo.service.ldap;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.db.IGroupDao;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.ldap.ITenantLdapAttributesMappingDao;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TenantLdapAttributesMappingImplTest {
	
	@SpringBeanByType
	private ITenantLdapAttributesMapping tenantLdapAttributesMapping;
	
	private Mock<ITenantLdapAttributesMappingDao> mockDao;
	private Mock<IMemberDao> mockMemberDao;
	private Mock<IGroupDao> mockGroupDao;
	
	@Before
	public void initialize() {
		((TenantLdapAttributesMappingImpl) tenantLdapAttributesMapping).setGroupDao(mockGroupDao.getMock());
		((TenantLdapAttributesMappingImpl) tenantLdapAttributesMapping).setMemberDao(mockMemberDao.getMock());
		((TenantLdapAttributesMappingImpl) tenantLdapAttributesMapping).setDao(mockDao.getMock());
	}

}

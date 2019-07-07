package com.vidyo.db.ldap;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.RowMapper;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;

import com.vidyo.bo.ldap.TenantLdapAttributeValueMapping;
import com.vidyo.db.BaseDAOTest;

@DataSet(loadStrategy = CleanInsertLoadStrategy.class)
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TenantLdapAttributesMappingDaoJdbcImplTest extends BaseDAOTest {
	
	private ITenantLdapAttributesMappingDao tenantLdapAttributesMappingDao;
	
	@Before
	public void initializeDao() {
		tenantLdapAttributesMappingDao = new TenantLdapAttributesMappingDaoJdbcImpl();
		((TenantLdapAttributesMappingDaoJdbcImpl) tenantLdapAttributesMappingDao).setDataSource(getDataSource());
	}
	
	@Test
	public void removeTenantLdapAttributeValueMappingRecordSuccess() {
		int result = tenantLdapAttributesMappingDao.removeTenantLdapAttributeValueMappingRecord(1, 2);
		
		Assert.assertEquals(1, result);
		
		final String sqlstm = " SELECT" +
				"  valueID," +
				"  mappingID," +
				"  vidyoValueName," +
				"  ldapValueName" +
				" FROM" +
				"  TenantLdapAttributeValueMapping" +
				" WHERE" +
				"  valueID = :valueID";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("valueID", 2);
		
		List<TenantLdapAttributeValueMapping> rc = ((TenantLdapAttributesMappingDaoJdbcImpl) tenantLdapAttributesMappingDao).getNamedParameterJdbcTemplate().query(sqlstm, namedParams, new RowMapper<TenantLdapAttributeValueMapping>() {
				@Override
				public TenantLdapAttributeValueMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
					TenantLdapAttributeValueMapping tenantLdapAttributevalueMapping = new TenantLdapAttributeValueMapping();
					
					tenantLdapAttributevalueMapping.setValueID(rs.getInt(1));
					tenantLdapAttributevalueMapping.setMappingID(rs.getInt(2));
					tenantLdapAttributevalueMapping.setVidyoValueName(rs.getString(3));
					tenantLdapAttributevalueMapping.setLdapValueName(rs.getString(4));
	
					return tenantLdapAttributevalueMapping;
				}
			});
		
		Assert.assertEquals(0, rc.size());
	}

}

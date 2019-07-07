package com.vidyo.db.idp;

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

import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;
import com.vidyo.db.BaseDAOTest;

@DataSet(loadStrategy = CleanInsertLoadStrategy.class)
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TenantIdpAttributesMappingDaoJdbcImplTest extends BaseDAOTest {
	
	private ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao;
	
	@Before
	public void initializeDao() {
		tenantIdpAttributesMappingDao = new TenantIdpAttributesMappingDaoJdbcImpl();
		((TenantIdpAttributesMappingDaoJdbcImpl) tenantIdpAttributesMappingDao).setDataSource(getDataSource());
	}
	
	@Test
	public void removeTenantIdpAttributeValueMappingRecordSuccess() {
		int result = tenantIdpAttributesMappingDao.removeTenantIdpAttributeValueMappingRecord(1, 2);
		
		Assert.assertEquals(1, result);
		
		final String sqlstm = " SELECT" +
				"  valueID," +
				"  mappingID," +
				"  vidyoValueName," +
				"  idpValueName" +
				" FROM" +
				"  TenantIdpAttributeValueMapping" +
				" WHERE" +
				"  valueID = :valueID";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("valueID", 2);
		
		List<TenantIdpAttributeValueMapping> rc = ((TenantIdpAttributesMappingDaoJdbcImpl) tenantIdpAttributesMappingDao).getNamedParameterJdbcTemplate().query(sqlstm, namedParams, new RowMapper<TenantIdpAttributeValueMapping>() {
				@Override
				public TenantIdpAttributeValueMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
					TenantIdpAttributeValueMapping tenantIdpAttributevalueMapping = new TenantIdpAttributeValueMapping();
					
					tenantIdpAttributevalueMapping.setValueID(rs.getInt(1));
					tenantIdpAttributevalueMapping.setMappingID(rs.getInt(2));
					tenantIdpAttributevalueMapping.setVidyoValueName(rs.getString(3));
					tenantIdpAttributevalueMapping.setIdpValueName(rs.getString(4));
	
					return tenantIdpAttributevalueMapping;
				}
			});
		
		Assert.assertEquals(0, rc.size());
	}

}

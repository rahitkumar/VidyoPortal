/**
 * 
 */
package com.vidyo.db.tenant;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.dbunit.annotation.DataSet;
import org.unitils.dbunit.datasetloadstrategy.impl.CleanInsertLoadStrategy;

import com.vidyo.bo.TenantConfiguration;
import com.vidyo.db.BaseDAOTest;
import com.vidyo.db.TenantConfigurationDao;
import com.vidyo.db.TenantConfigurationDaoJdbcImpl;

/**
 * @author
 * 
 */
@DataSet(loadStrategy = CleanInsertLoadStrategy.class)
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class TenantConfigurationDaoTest extends BaseDAOTest {

	private TenantConfigurationDao tenantConfigurationDao;

	@Before
	public void initializeDao() {
		tenantConfigurationDao = new TenantConfigurationDaoJdbcImpl();
		((TenantConfigurationDaoJdbcImpl) tenantConfigurationDao).setDataSource(getDataSource());
	}

	@Test
	public void testUpdateEndpointChatsStatuses() {
		TenantConfiguration tenConfiguration = new TenantConfiguration();
		tenConfiguration.setTenantId(191);
		tenConfiguration.setEndpointPrivateChat(1);
		tenConfiguration.setEndpointPublicChat(1);
		
		tenantConfigurationDao.updateTenantConfiguration(191, tenConfiguration);
		
		String sqlstm = "Select tenantID, endpointPrivateChat, endpointPublicChat from TenantConfiguration Where tenantID = :tenantID";
		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("tenantID", 191);
		
		NamedParameterJdbcDaoSupport dao = new NamedParameterJdbcDaoSupport();
		dao.setDataSource(getDataSource());
		
		List<TenantConfiguration> rc = dao.getNamedParameterJdbcTemplate().query(sqlstm, namedParams, new RowMapper<TenantConfiguration>() {
			@Override
			public TenantConfiguration mapRow(ResultSet rs, int rowNum) throws SQLException {
				TenantConfiguration tenantConfiguration = new TenantConfiguration();
				
				tenantConfiguration.setTenantId(rs.getInt(1));
				tenantConfiguration.setEndpointPrivateChat(rs.getInt(2));
				tenantConfiguration.setEndpointPublicChat(rs.getInt(3));

				return tenantConfiguration;
			}
		});
		
		assertEquals(1, rc.size());
		assertEquals(1, rc.get(0).getEndpointPrivateChat());
		assertEquals(1, rc.get(0).getEndpointPublicChat());
	}

}

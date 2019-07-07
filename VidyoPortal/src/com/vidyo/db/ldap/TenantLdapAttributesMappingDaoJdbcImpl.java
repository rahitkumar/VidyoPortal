package com.vidyo.db.ldap;

import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.ldap.TenantLdapAttributeMapping;
import com.vidyo.bo.ldap.TenantLdapAttributeValueMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TenantLdapAttributesMappingDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements ITenantLdapAttributesMappingDao {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(TenantLdapAttributesMappingDaoJdbcImpl.class.getName());

	public List<TenantLdapAttributeMapping> getTenantLdapAttributeMapping(int tenantID) {
		List<TenantLdapAttributeMapping> rc;
		if (logger.isDebugEnabled()) {
			logger.debug("Get records from TenantLdapAttributeMapping for tenant = " + tenantID);
		}

		final String sqlstm = " SELECT" +
							"  mappingID," +
							"  tenantID," +
							"  vidyoAttributeName," +
							"  ldapAttributeName," +
							"  defaultAttributeValue" +
							" FROM" +
							"  TenantLdapAttributeMapping" +
							" WHERE" +
							"  tenantID = :tenantID" +
							" ORDER BY mappingID ASC";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("tenantID", tenantID);

		rc = getNamedParameterJdbcTemplate().query(sqlstm, namedParams, new RowMapper<TenantLdapAttributeMapping>() {
			@Override
			public TenantLdapAttributeMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
				TenantLdapAttributeMapping tenantLdapAttributeMapping = new TenantLdapAttributeMapping();

				tenantLdapAttributeMapping.setMappingID(rs.getInt(1));
				tenantLdapAttributeMapping.setTenantID(rs.getInt(2));
				tenantLdapAttributeMapping.setVidyoAttributeName(rs.getString(3));
				tenantLdapAttributeMapping.setLdapAttributeName(rs.getString(4));
				tenantLdapAttributeMapping.setDefaultAttributeValue(rs.getString(5));

				return tenantLdapAttributeMapping;
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("Get Result Set Size " + rc.size());
		}

		return rc;
	}

	public TenantLdapAttributeMapping getTenantLdapAttributeMappingRecord(int tenantID, int mappingID) {
		TenantLdapAttributeMapping rc = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Get record from TenantLdapAttributeMapping for mappingID = " + mappingID);
		}

		final String sqlstm = " SELECT" +
							"  mappingID," +
							"  tenantID," +
							"  vidyoAttributeName," +
							"  ldapAttributeName," +
							"  defaultAttributeValue" +
							" FROM" +
							"  TenantLdapAttributeMapping" +
							" WHERE" +
							"  mappingID = :mappingID" +
							" AND" +
							"  tenantID = :tenantID";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("mappingID", mappingID);
		namedParams.put("tenantID", tenantID);

		try {
			rc = getNamedParameterJdbcTemplate().queryForObject(sqlstm, namedParams, new RowMapper<TenantLdapAttributeMapping>() {
				@Override
				public TenantLdapAttributeMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
					TenantLdapAttributeMapping tenantLdapAttributeMapping = new TenantLdapAttributeMapping();

					tenantLdapAttributeMapping.setMappingID(rs.getInt(1));
					tenantLdapAttributeMapping.setTenantID(rs.getInt(2));
					tenantLdapAttributeMapping.setVidyoAttributeName(rs.getString(3));
					tenantLdapAttributeMapping.setLdapAttributeName(rs.getString(4));
					tenantLdapAttributeMapping.setDefaultAttributeValue(rs.getString(5));

					return tenantLdapAttributeMapping;
				}
			});
		} catch (Exception e) {
			logger.info("TenantLdapAttributeMapping record is not found", e.getMessage());
		}

		return rc;
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int saveTenantLdapAttributeMappingRecord(@PartialCacheKey int tenantID, String vidyoAttributeName, String ldapAttributeName, String defaultAttributeValue) {
		final String sqlstm = " SELECT" +
							"  mappingID" +
							" FROM" +
							"  TenantLdapAttributeMapping" +
							" WHERE" +
							"  vidyoAttributeName = :vidyoAttributeName" +
							" AND" +
							"  tenantID = :tenantID";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("tenantID", tenantID);
		namedParams.put("vidyoAttributeName", vidyoAttributeName);

		List<Integer> li = getNamedParameterJdbcTemplate().query(sqlstm, namedParams, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer retVal = rs.getInt(1);

				return retVal;
			}
		});

		int mappingID = li.size() > 0 ? li.get(0) : 0;

		if (mappingID == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("Add record into TenantLdapAttributeMapping->" + vidyoAttributeName +
				" for tenant = " + tenantID + " : " + ldapAttributeName + " : " + defaultAttributeValue);
			}

			SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
				.withTableName("TenantLdapAttributeMapping")
				.usingGeneratedKeyColumns("mappingID");

			SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("tenantID", tenantID)
				.addValue("vidyoAttributeName", vidyoAttributeName)
				.addValue("ldapAttributeName", ldapAttributeName)
				.addValue("defaultAttributeValue", defaultAttributeValue);

			Number newID = insert.executeAndReturnKey(namedParameters);
			if (logger.isDebugEnabled()) {
				logger.debug("New configurationID = " + newID.intValue());
			}
			return newID.intValue();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Update record in TenantLdapAttributeMapping->" + vidyoAttributeName +
				" for tenant = " + tenantID + " and mappingID = " + mappingID + " : " + ldapAttributeName + " : " + defaultAttributeValue);
			}

			final String sqlstm_upd = " UPDATE" +
									"  TenantLdapAttributeMapping" +
									" SET" +
									"  ldapAttributeName = :ldapAttributeName," +
									"  defaultAttributeValue = :defaultAttributeValue," +
									" WHERE" +
									"  vidyoAttributeName = :vidyoAttributeName" +
									" AND" +
									"  tenantID = :tenantID";

			SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("tenantID", tenantID)
				.addValue("vidyoAttributeName", vidyoAttributeName)
				.addValue("ldapAttributeName", ldapAttributeName)
				.addValue("defaultAttributeValue", defaultAttributeValue);

			int affected = getNamedParameterJdbcTemplate().update(sqlstm_upd, namedParameters);
			if (logger.isDebugEnabled()) {
				logger.debug("Rows affected: " + affected);
			}
			return mappingID;
		}
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantLdapAttributeMappingRecord(int tenantID, TenantLdapAttributeMapping record) {
		if (logger.isDebugEnabled()) {
			logger.debug("Update record in TenantLdapAttributeMapping for mappingID = "+record.getMappingID()+" : "+record.getLdapAttributeName()+" : "+record.getDefaultAttributeValue());
		}

		final String sqlstm_upd = " UPDATE" +
								"  TenantLdapAttributeMapping" +
								" SET" +
								"  ldapAttributeName = :ldapAttributeName," +
								"  defaultAttributeValue = :defaultAttributeValue" +
								" WHERE" +
								"  mappingID = :mappingID" +
								" AND" +
								"  tenantID = :tenantID";

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("ldapAttributeName", record.getLdapAttributeName())
				.addValue("defaultAttributeValue", record.getDefaultAttributeValue())
				.addValue("mappingID", record.getMappingID())
				.addValue("tenantID", tenantID);

		int affected = getNamedParameterJdbcTemplate().update(sqlstm_upd, namedParameters);
		if (logger.isDebugEnabled()) {
			logger.debug("Rows affected: " + affected);
		}
		return affected;
	}

	@Override
	public List<TenantLdapAttributeValueMapping> getTenantLdapAttributeValueMapping(int tenantID, int mappingID) {
		List<TenantLdapAttributeValueMapping> rc;
		if (logger.isDebugEnabled()) {
			logger.debug("Get records from TenantLdapAttributeValueMapping for tenantID = " + tenantID + " and mappingID = " + mappingID);
		}

		final String sqlstm = " SELECT" +
							"  tlavm.valueID," +
							"  tlavm.mappingID," +
							"  tlavm.vidyoValueName," +
							"  tlavm.ldapValueName" +
							" FROM" +
							"  TenantLdapAttributeValueMapping tlavm LEFT JOIN" +
							"    TenantLdapAttributeMapping as tlam on(tlavm.mappingID = tlam.mappingID)" +
							" WHERE" +
							"  tlavm.mappingID = :mappingID" +
							" AND" +
							"  tlam.tenantID = :tenantID" +
							" ORDER BY vidyoValueName ASC";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("mappingID", mappingID);
		namedParams.put("tenantID", tenantID);

		rc = getNamedParameterJdbcTemplate().query(sqlstm, namedParams, new RowMapper<TenantLdapAttributeValueMapping>() {
			@Override
			public TenantLdapAttributeValueMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
				TenantLdapAttributeValueMapping tenantLdapAttributeValueMapping = new TenantLdapAttributeValueMapping();

				tenantLdapAttributeValueMapping.setValueID(rs.getInt(1));
				tenantLdapAttributeValueMapping.setMappingID(rs.getInt(2));
				tenantLdapAttributeValueMapping.setVidyoValueName(rs.getString(3));
				tenantLdapAttributeValueMapping.setLdapValueName(rs.getString(4));

				return tenantLdapAttributeValueMapping;
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("Get Result Set Size " + rc.size());
		}

		return rc;
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int saveTenantLdapAttributeValueMappingRecord(@PartialCacheKey int tenantID, int valueID, int mappingID, String vidyoValueName, String ldapValueName) {
		final String sqlstm = " SELECT" +
							"  tlavm.valueID" +
							" FROM" +
							"  TenantLdapAttributeValueMapping tlavm LEFT JOIN" +
							"    TenantLdapAttributeMapping as tlam on(tlavm.mappingID = tlam.mappingID)" +
							" WHERE" +
							"  tlavm.vidyoValueName = :vidyoValueName" +
							" AND" +
							"  tlavm.valueID = :valueID" +
							" AND" +
							"  tlavm.mappingID = :mappingID" +
							" AND" +
							"  tlam.tenantID = :tenantID";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("vidyoValueName", vidyoValueName);
		namedParams.put("valueID", valueID);
		namedParams.put("mappingID", mappingID);
		namedParams.put("tenantID", tenantID);

		List<Integer> li = getNamedParameterJdbcTemplate().query(sqlstm, namedParams, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer retVal = rs.getInt(1);

				return retVal;
			}
		});

		int recValueID = li.size() > 0 ? li.get(0) : 0;

		if (recValueID == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug("Add record into TenantLdapAttributeValueMapping->" + vidyoValueName +
				" for mappingID = " + mappingID + " : " + ldapValueName);
			}

			SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
					.withTableName("TenantLdapAttributeValueMapping")
					.usingGeneratedKeyColumns("valueID");

			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("mappingID", mappingID)
					.addValue("vidyoValueName", vidyoValueName)
					.addValue("ldapValueName", ldapValueName);

			Number newID = insert.executeAndReturnKey(namedParameters);
			if (logger.isDebugEnabled()) {
				logger.debug("New configurationID = " + newID.intValue());
			}
			return newID.intValue();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Update record in TenantLdapAttributeValueMapping->" + vidyoValueName +
				" for mappingID = " + mappingID + " : " + ldapValueName);
			}

			final String sqlstm_upd = " UPDATE" +
									"  TenantLdapAttributeValueMapping tlavm LEFT JOIN" +
									"    TenantLdapAttributeMapping as tlam on(tlavm.mappingID = tlam.mappingID)" +
									" SET" +
									"  tlavm.ldapValueName = :ldapValueName" +
									" WHERE" +
									"  tlavm.vidyoValueName = :vidyoValueName" +
									" AND" +
									"  tlavm.valueID = :valueID" +
									" AND" +
									"  tlavm.mappingID = :mappingID" +
									" AND" +
									"  tlam.tenantID = :tenantID";

			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("valueID", valueID)
					.addValue("mappingID", mappingID)
					.addValue("vidyoValueName", vidyoValueName)
					.addValue("ldapValueName", ldapValueName)
					.addValue("tenantID", tenantID);

			int affected = getNamedParameterJdbcTemplate().update(sqlstm_upd, namedParameters);
			if (logger.isDebugEnabled()) {
				logger.debug("Rows affected: " + affected);
			}
			return valueID;
		}
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int removeTenantLdapAttributeValueMappingRecord(@PartialCacheKey int tenantID, int valueID) {
		if (logger.isDebugEnabled()) {
			logger.debug("Remove record from TenantLdapAttributeValueMapping->" + valueID);
		}

		String sqlstm = " DELETE tlavm" +
			" FROM" +
			"  TenantLdapAttributeValueMapping tlavm LEFT JOIN" +
			"    TenantLdapAttributeMapping as tlam on(tlavm.mappingID = tlam.mappingID)" +
			" WHERE" +
			"  tlavm.valueID = :valueID" +
			" AND" +
			"  tlam.tenantID = :tenantID";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("valueID", valueID);
		namedParams.put("tenantID", tenantID);

		int affected = getNamedParameterJdbcTemplate().update(sqlstm, namedParams);
		logger.debug("Rows affected: " + affected);

		return affected;
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantLdapAttributeMappingDefaultAttributeValue(@PartialCacheKey int tenantID, String vidyoAttributeName,
			String oldDefaultAttributeValue, String newDefaultAttributeValue) {
		if (logger.isDebugEnabled()) {
			logger.debug("Update defaultAttribteValue in TenantLdapAttributeMapping for tenantID = " + tenantID + " : " +
				vidyoAttributeName + " : " + oldDefaultAttributeValue + ":" + newDefaultAttributeValue);
		}

		StringBuffer sqlstm_upd = new StringBuffer(
			" UPDATE" +
			"  TenantLdapAttributeMapping" +
			" SET" +
			"  defaultAttributeValue = :newDefaultAttributeValue" +
			" WHERE");

		if(tenantID > 0) {
			sqlstm_upd.append("  tenantID = :tenantID AND ");
		}

		sqlstm_upd.append(
			"  vidyoAttributeName = :vidyoAttributeName AND " +
			"  defaultAttributeValue = :oldDefaultAttributeValue"
		);

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("newDefaultAttributeValue", newDefaultAttributeValue)
				.addValue("oldDefaultAttributeValue", oldDefaultAttributeValue)
				.addValue("vidyoAttributeName", vidyoAttributeName);

		if(tenantID > 0) {
			((MapSqlParameterSource)namedParameters).addValue("tenantID", tenantID);
		}

		int affected = getNamedParameterJdbcTemplate().update(sqlstm_upd.toString(), namedParameters);
		if (logger.isDebugEnabled()) {
			logger.debug("Rows affected: " + affected);
		}
		return affected;
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantLdapAttributeValueMappingVidyoValueName(@PartialCacheKey int tenantID, String vidyoAttributeName,
			String oldVidyoValueName, String newVidyoValueName) {
		if (logger.isDebugEnabled()) {
			logger.debug("Update vidyoValueName in TenantLdapAttributeValueMapping for tenantID = " + tenantID + " : " +
				vidyoAttributeName + " : " + oldVidyoValueName + ":" + newVidyoValueName);
		}

		StringBuffer sqlstm_upd = new StringBuffer(
				" UPDATE" +
				"  TenantLdapAttributeMapping tlam LEFT JOIN TenantLdapAttributeValueMapping tlavm ON tlam.mappingID = tlavm.mappingID" +
				" SET" +
				"  vidyoValueName = :newVidyoValueName" +
				" WHERE");

		if(tenantID > 0) {
			sqlstm_upd.append("  tenantID = :tenantID AND ");
		}

		sqlstm_upd.append(
				"  vidyoAttributeName = :vidyoAttributeName AND " +
				"  vidyoValueName = :oldVidyoValueName"
		);

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("newVidyoValueName", newVidyoValueName)
				.addValue("oldVidyoValueName", oldVidyoValueName)
				.addValue("vidyoAttributeName", vidyoAttributeName);

		if(tenantID > 0) {
			((MapSqlParameterSource)namedParameters).addValue("tenantID", tenantID);
		}

		int affected = getNamedParameterJdbcTemplate().update(sqlstm_upd.toString(), namedParameters);
		if (logger.isDebugEnabled()) {
			logger.debug("Rows affected: " + affected);
		}
		return affected;
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int removeTenantLdapAttributeValueMappingRecords(@PartialCacheKey int tenantID, String vidyoAttributeName, String vidyoValueName) {

		if (logger.isDebugEnabled()) {
			logger.debug("Remove record from TenantLdapAttributeValueMapping for tenantID = " + tenantID + " : " +
				vidyoAttributeName + " : " + vidyoValueName);
		}

		StringBuffer sqlstm_upd = new StringBuffer(
				" DELETE" +
				"  FROM TenantLdapAttributeValueMapping" +
				" WHERE" +
				"  mappingID in (SELECT mappingID FROM TenantLdapAttributeMapping" +
				" WHERE");

		if(tenantID > 0) {
			sqlstm_upd.append("  tenantID = :tenantID AND ");
		}

		sqlstm_upd.append(
				"  vidyoAttributeName = :vidyoAttributeName) AND " +
				"  vidyoValueName = :vidyoValueName"
		);

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("vidyoValueName", vidyoValueName)
				.addValue("vidyoAttributeName", vidyoAttributeName);

		if(tenantID > 0) {
			((MapSqlParameterSource)namedParameters).addValue("tenantID", tenantID);
		}

		int affected = getNamedParameterJdbcTemplate().update(sqlstm_upd.toString(), namedParameters);
		if (logger.isDebugEnabled()) {
			logger.debug("Rows affected: " + affected);
		}
		return affected;
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int removeAllTenantLdapMappingAttributesWithValues(@PartialCacheKey int tenantID) {
		if (logger.isDebugEnabled()) {
			logger.debug("Remove all records from TenantLdapAttributeValueMapping for tenantID = " + tenantID);
		}

		String sqlstm = " DELETE tlavm" +
			" FROM" +
			"  TenantLdapAttributeValueMapping tlavm LEFT JOIN" +
			"    TenantLdapAttributeMapping as tlam on(tlavm.mappingID = tlam.mappingID)" +
			" WHERE" +
			"  tlam.tenantID = :tenantID";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("tenantID", tenantID);

		int affected = getNamedParameterJdbcTemplate().update(sqlstm, namedParams);

		sqlstm = " DELETE" +
				" FROM" +
				"  TenantLdapAttributeMapping" +
				" WHERE" +
				"  tenantID = :tenantID";

		affected += getNamedParameterJdbcTemplate().update(sqlstm, namedParams);

		logger.debug("Rows affected: " + affected);

		return affected;
	}

}
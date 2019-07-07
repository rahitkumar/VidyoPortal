package com.vidyo.db.idp;

import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;

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

public class TenantIdpAttributesMappingDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements ITenantIdpAttributesMappingDao {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(TenantIdpAttributesMappingDaoJdbcImpl.class.getName());

	@Override
	public List<TenantIdpAttributeMapping> getTenantIdpAttributeMapping(int tenantID) {
		List<TenantIdpAttributeMapping> rc;
		if (logger.isDebugEnabled()) {
			logger.debug("Get records from TenantIdpAttributeMapping for tenant = " + tenantID);
		}

		final String sqlstm = " SELECT" +
							"  mappingID," +
							"  tenantID," +
							"  vidyoAttributeName," +
							"  idpAttributeName," +
							"  defaultAttributeValue" +
							" FROM" +
							"  TenantIdpAttributeMapping" +
							" WHERE" +
							"  tenantID = :tenantID" +
							" ORDER BY mappingID ASC";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("tenantID", tenantID);

		rc = getNamedParameterJdbcTemplate().query(sqlstm, namedParams, new RowMapper<TenantIdpAttributeMapping>() {
			@Override
			public TenantIdpAttributeMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
				TenantIdpAttributeMapping tenantIdpAttributeMapping = new TenantIdpAttributeMapping();

				tenantIdpAttributeMapping.setMappingID(rs.getInt(1));
				tenantIdpAttributeMapping.setTenantID(rs.getInt(2));
				tenantIdpAttributeMapping.setVidyoAttributeName(rs.getString(3));
				tenantIdpAttributeMapping.setIdpAttributeName(rs.getString(4));
				tenantIdpAttributeMapping.setDefaultAttributeValue(rs.getString(5));

				return tenantIdpAttributeMapping;
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("Get Result Set Size " + rc.size());
		}

		return rc;
	}

	@Override
	public TenantIdpAttributeMapping getTenantIdpAttributeMappingRecord(int tenantID, int mappingID) {
		TenantIdpAttributeMapping rc = null;
		if (logger.isDebugEnabled()) {
			logger.debug("Get record from TenantIdpAttributeMapping for mappingID = " + mappingID);
		}

		final String sqlstm = " SELECT" +
							"  mappingID," +
							"  tenantID," +
							"  vidyoAttributeName," +
							"  idpAttributeName," +
							"  defaultAttributeValue" +
							" FROM" +
							"  TenantIdpAttributeMapping" +
							" WHERE" +
							"  mappingID = :mappingID" +
							" AND" +
							"  tenantID = :tenantID";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("mappingID", mappingID);
		namedParams.put("tenantID", tenantID);


		try {
			rc = getNamedParameterJdbcTemplate().queryForObject(sqlstm, namedParams, new RowMapper<TenantIdpAttributeMapping>() {
				@Override
				public TenantIdpAttributeMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
					TenantIdpAttributeMapping tenantIdpAttributeMapping = new TenantIdpAttributeMapping();

					tenantIdpAttributeMapping.setMappingID(rs.getInt(1));
					tenantIdpAttributeMapping.setTenantID(rs.getInt(2));
					tenantIdpAttributeMapping.setVidyoAttributeName(rs.getString(3));
					tenantIdpAttributeMapping.setIdpAttributeName(rs.getString(4));
					tenantIdpAttributeMapping.setDefaultAttributeValue(rs.getString(5));

					return tenantIdpAttributeMapping;
				}
			});
		} catch (Exception e) {
			logger.info("TenantIdpAttributeMapping record is not found", e.getMessage());
		}

		return rc;
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int saveTenantIdpAttributeMappingRecord(@PartialCacheKey int tenantID, String vidyoAttributeName, String idpAttributeName, String defaultAttributeValue) {
		final String sqlstm = " SELECT" +
							"  mappingID" +
							" FROM" +
							"  TenantIdpAttributeMapping" +
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
				logger.debug("Add record into TenantIdpAttributeMapping->" + vidyoAttributeName +
				" for tenant = " + tenantID + " : " + idpAttributeName + " : " + defaultAttributeValue);
			}

			SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
				.withTableName("TenantIdpAttributeMapping")
				.usingGeneratedKeyColumns("mappingID");

			SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("tenantID", tenantID)
				.addValue("vidyoAttributeName", vidyoAttributeName)
				.addValue("idpAttributeName", idpAttributeName)
				.addValue("defaultAttributeValue", defaultAttributeValue);

			Number newID = insert.executeAndReturnKey(namedParameters);
			if (logger.isDebugEnabled()) {
				logger.debug("New configurationID = " + newID.intValue());
			}
			return newID.intValue();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Update record in TenantIdpAttributeMapping->" + vidyoAttributeName +
				" for tenant = " + tenantID + " and mappingID = " + mappingID + " : " + idpAttributeName + " : " + defaultAttributeValue);
			}

			NamedParameterJdbcTemplate updateTemplate = getNamedParameterJdbcTemplate();

			final String sqlstm_upd = " UPDATE" +
									"  TenantIdpAttributeMapping" +
									" SET" +
									"  idpAttributeName = :idpAttributeName," +
									"  defaultAttributeValue = :defaultAttributeValue," +
									" WHERE" +
									"  vidyoAttributeName = :vidyoAttributeName" +
									" AND" +
									"  tenantID = :tenantID";

			SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("tenantID", tenantID)
				.addValue("vidyoAttributeName", vidyoAttributeName)
				.addValue("idpAttributeName", idpAttributeName)
				.addValue("defaultAttributeValue", defaultAttributeValue);

			int affected = updateTemplate.update(sqlstm_upd, namedParameters);
			if (logger.isDebugEnabled()) {
				logger.debug("Rows affected: " + affected);
			}
			return mappingID;
		}
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantIdpAttributeMappingRecord(@PartialCacheKey int tenantID, TenantIdpAttributeMapping record) {
		if (logger.isDebugEnabled()) {
			logger.debug("Update record in TenantIdpAttributeMapping for mappingID = "+record.getMappingID()+" : "+record.getIdpAttributeName()+" : "+record.getDefaultAttributeValue());
		}

		NamedParameterJdbcTemplate updateTemplate = getNamedParameterJdbcTemplate();

		final String sqlstm_upd = " UPDATE" +
								"  TenantIdpAttributeMapping" +
								" SET" +
								"  idpAttributeName = :idpAttributeName," +
								"  defaultAttributeValue = :defaultAttributeValue" +
								" WHERE" +
								"  mappingID = :mappingID" +
								" AND" +
								"  tenantID = :tenantID";

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("idpAttributeName", record.getIdpAttributeName())
				.addValue("defaultAttributeValue", record.getDefaultAttributeValue())
				.addValue("mappingID", record.getMappingID())
				.addValue("tenantID", tenantID);

		int affected = updateTemplate.update(sqlstm_upd, namedParameters);
		if (logger.isDebugEnabled()) {
			logger.debug("Rows affected: " + affected);
		}
		return affected;
	}

	@Override
	public List<TenantIdpAttributeValueMapping> getTenantIdpAttributeValueMapping(int tenantID, int mappingID) {
		List<TenantIdpAttributeValueMapping> rc;
		if (logger.isDebugEnabled()) {
			logger.debug("Get records from TenantIdpAttributeValueMapping for tenantID = " + tenantID + " and mappingID = " + mappingID);
		}

		final String sqlstm = " SELECT" +
							"  tiavm.valueID," +
							"  tiavm.mappingID," +
							"  tiavm.vidyoValueName," +
							"  tiavm.idpValueName" +
							" FROM" +
							"  TenantIdpAttributeValueMapping tiavm LEFT JOIN" +
							"    TenantIdpAttributeMapping as tiam on(tiavm.mappingID = tiam.mappingID)" +
							" WHERE" +
							"  tiavm.mappingID = :mappingID" +
							" AND" +
							"  tiam.tenantID = :tenantID" +
							" ORDER BY vidyoValueName ASC";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("mappingID", mappingID);
		namedParams.put("tenantID", tenantID);

		rc = getNamedParameterJdbcTemplate().query(sqlstm, namedParams, new RowMapper<TenantIdpAttributeValueMapping>() {
			@Override
			public TenantIdpAttributeValueMapping mapRow(ResultSet rs, int rowNum) throws SQLException {
				TenantIdpAttributeValueMapping tenantIdpAttributeValueMapping = new TenantIdpAttributeValueMapping();

				tenantIdpAttributeValueMapping.setValueID(rs.getInt(1));
				tenantIdpAttributeValueMapping.setMappingID(rs.getInt(2));
				tenantIdpAttributeValueMapping.setVidyoValueName(rs.getString(3));
				tenantIdpAttributeValueMapping.setIdpValueName(rs.getString(4));

				return tenantIdpAttributeValueMapping;
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("Get Result Set Size " + rc.size());
		}

		return rc;
	}


	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int saveTenantIdpAttributeValueMappingRecord(@PartialCacheKey int tenantID, int valueID, int mappingID, String vidyoValueName, String idpValueName) {
		final String sqlstm = " SELECT" +
				"  tiavm.valueID" +
				" FROM" +
				"  TenantIdpAttributeValueMapping tiavm LEFT JOIN" +
				"    TenantIdpAttributeMapping as tiam on(tiavm.mappingID = tiam.mappingID)" +
				" WHERE" +
				"  tiavm.vidyoValueName = :vidyoValueName" +
				" AND" +
				"  tiavm.valueID = :valueID" +
				" AND" +
				"  tiavm.mappingID = :mappingID" +
				" AND" +
				"  tiam.tenantID = :tenantID";

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
				logger.debug("Add record into TenantIdpAttributeValueMapping->" + vidyoValueName +
				" for mappingID = " + mappingID + " : " + idpValueName);
			}

			SimpleJdbcInsert insert = new SimpleJdbcInsert(this.getDataSource())
					.withTableName("TenantIdpAttributeValueMapping")
					.usingGeneratedKeyColumns("valueID");

			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("mappingID", mappingID)
					.addValue("vidyoValueName", vidyoValueName)
					.addValue("idpValueName", idpValueName);

			Number newID = insert.executeAndReturnKey(namedParameters);
			if (logger.isDebugEnabled()) {
				logger.debug("New configurationID = " + newID.intValue());
			}
			return newID.intValue();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Update record in TenantIdpAttributeValueMapping->" + vidyoValueName +
				" for mappingID = " + mappingID + " : " + idpValueName);
			}

			NamedParameterJdbcTemplate updateTemplate = getNamedParameterJdbcTemplate();

			final String sqlstm_upd = " UPDATE" +
					"  TenantIdpAttributeValueMapping tiavm LEFT JOIN" +
					"    TenantIdpAttributeMapping as tiam on(tiavm.mappingID = tiam.mappingID)" +
					" SET" +
					"  tiavm.idpValueName = :idpValueName" +
					" WHERE" +
					"  tiavm.vidyoValueName = :vidyoValueName" +
					" AND" +
					"  tiavm.valueID = :valueID" +
					" AND" +
					"  tiavm.mappingID = :mappingID" +
					" AND" +
					"  tiam.tenantID = :tenantID";

			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("valueID", valueID)
					.addValue("mappingID", mappingID)
					.addValue("vidyoValueName", vidyoValueName)
					.addValue("idpValueName", idpValueName)
					.addValue("tenantID", tenantID);

			int affected = updateTemplate.update(sqlstm_upd, namedParameters);
			if (logger.isDebugEnabled()) {
				logger.debug("Rows affected: " + affected);
			}
			return valueID;
		}
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int removeTenantIdpAttributeValueMappingRecord(@PartialCacheKey int tenantID, int valueID) {
		if (logger.isDebugEnabled()) {
			logger.debug("Remove record from TenantIdpAttributeValueMapping->" + valueID);
		}

		String sqlstm = " DELETE tiavm" +
						" FROM" +
						"  TenantIdpAttributeValueMapping tiavm LEFT JOIN" +
						"    TenantIdpAttributeMapping as tiam on(tiavm.mappingID = tiam.mappingID)" +
						" WHERE" +
						"  tiavm.valueID = :valueID" +
						" AND" +
						"  tiam.tenantID = :tenantID";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("valueID", valueID);
		namedParams.put("tenantID", tenantID);

		int affected = getNamedParameterJdbcTemplate().update(sqlstm, namedParams);
		logger.debug("Rows affected: " + affected);

		return affected;
	}

	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantIdpAttributeMappingDefaultAttributeValue(@PartialCacheKey int tenantID, String vidyoAttributeName,
			String oldDefaultAttributeValue, String newDefaultAttributeValue) {
		if (logger.isDebugEnabled()) {
			logger.debug("Update defaultAttribteValue in TenantIdpAttributeMapping for tenantID = " + tenantID + " : " +
				vidyoAttributeName + " : " + oldDefaultAttributeValue + ":" + newDefaultAttributeValue);
		}

		StringBuffer sqlstm_upd = new StringBuffer(
			" UPDATE" +
			"  TenantIdpAttributeMapping" +
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
	public int updateTenantIdpAttributeValueMappingVidyoValueName(@PartialCacheKey int tenantID, String vidyoAttributeName,
			String oldVidyoValueName, String newVidyoValueName) {
		if (logger.isDebugEnabled()) {
			logger.debug("Update vidyoValueName in TenantIdppAttributeValueMapping for tenantID = " + tenantID + " : " +
				vidyoAttributeName + " : " + oldVidyoValueName + ":" + newVidyoValueName);
		}

		StringBuffer sqlstm_upd = new StringBuffer(
				" UPDATE" +
				"  TenantIdpAttributeMapping tlam LEFT JOIN TenantIdpAttributeValueMapping tlavm ON tlam.mappingID = tlavm.mappingID" +
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
	public int removeTenantIdpAttributeValueMappingRecords(@PartialCacheKey int tenantID, String vidyoAttributeName, String vidyoValueName) {

		if (logger.isDebugEnabled()) {
			logger.debug("Remove record from TenantIdpAttributeValueMapping for tenantID = " + tenantID + " : " +
				vidyoAttributeName + " : " + vidyoValueName);
		}

		StringBuffer sqlstm_upd = new StringBuffer(
				" DELETE" +
				"  FROM TenantIdpAttributeValueMapping" +
				" WHERE" +
				"  mappingID in (SELECT mappingID FROM TenantIdpAttributeMapping" +
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
	public int removeAllTenantIdpMappingAttributesWithValues(@PartialCacheKey int tenantID) {
		if (logger.isDebugEnabled()) {
			logger.debug("Remove all records from TenantIdpAttributeValueMapping for tenantID = " + tenantID);
		}

		String sqlstm = " DELETE tiavm" +
			" FROM" +
			"  TenantIdpAttributeValueMapping tiavm LEFT JOIN" +
			"    TenantIdpAttributeMapping as tiam on(tiavm.mappingID = tiam.mappingID)" +
			" WHERE" +
			"  tiam.tenantID = :tenantID";

		Map<String, Object> namedParams = new HashMap<String, Object>();
		namedParams.put("tenantID", tenantID);

		int affected = getNamedParameterJdbcTemplate().update(sqlstm, namedParams);

		sqlstm = " DELETE" +
				" FROM" +
				"  TenantIdpAttributeMapping" +
				" WHERE" +
				"  tenantID = :tenantID";

		affected += getNamedParameterJdbcTemplate().update(sqlstm, namedParams);

		logger.debug("Rows affected: " + affected);

		return affected;
	}
	
	@Override
	@TriggersRemove(cacheName = "tenantAuthConfigCache", keyGenerator = @KeyGenerator(name = "HashCodeCacheKeyGenerator", properties = @Property(name = "includeMethod", value = "false")))
	public int updateTenantIdpAttributeValueMappingRecord(int tenantID, int valueID, int mappingID,
			String vidyoValueName, String idpValueName) {

		if (logger.isDebugEnabled()) {
			logger.debug("Update record in TenantIdpAttributeValueMapping->" + vidyoValueName +
			" for mappingID = " + mappingID + " : " + idpValueName);
		}

		NamedParameterJdbcTemplate updateTemplate = getNamedParameterJdbcTemplate();

		final String sqlstm_upd = " UPDATE" +
				"  TenantIdpAttributeValueMapping tiavm LEFT JOIN" +
				"    TenantIdpAttributeMapping as tiam on(tiavm.mappingID = tiam.mappingID)" +
				" SET" +
				"  tiavm.idpValueName = :idpValueName" +
				" WHERE" +
				"  tiavm.vidyoValueName = :vidyoValueName" +
				" AND" +
				"  tiavm.valueID = :valueID" +
				" AND" +
				"  tiavm.mappingID = :mappingID" +
				" AND" +
				"  tiam.tenantID = :tenantID";

		SqlParameterSource namedParameters = new MapSqlParameterSource()
				.addValue("valueID", valueID)
				.addValue("mappingID", mappingID)
				.addValue("vidyoValueName", vidyoValueName)
				.addValue("idpValueName", idpValueName)
				.addValue("tenantID", tenantID);

		int affected = updateTemplate.update(sqlstm_upd, namedParameters);
		if (logger.isDebugEnabled()) {
			logger.debug("Rows affected: " + affected);
		}
		return valueID;
	
	}

}
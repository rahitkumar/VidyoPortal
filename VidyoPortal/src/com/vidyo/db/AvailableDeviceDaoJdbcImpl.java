package com.vidyo.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.vidyo.bo.availabledevice.AvailableDevice;

public class AvailableDeviceDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements IAvailableDeviceDao {

	/**
	 * Logger
	 */
	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(AvailableDeviceDaoJdbcImpl.class.getName());
	
	@Override
	public void addAvailableDevice(String endpointGUID, Timestamp availabilityTime) {
		if(logger.isDebugEnabled()) {
			logger.debug("Entering addAvailableDevice() of AvailableDeviceDaoJdbcImpl. endpointGUID = " + endpointGUID + 
					" availabilityTime = " + availabilityTime);
		}
		
		String sql = "INSERT INTO AvailableDevice (endpointGUID, availabilityTime) " +
				"VALUES (:endpointGUID, :availabilityTime) " +
				"ON DUPLICATE KEY UPDATE " +
				"availabilityTime=VALUES(availabilityTime)";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("endpointGUID", endpointGUID);
		params.put("availabilityTime", availabilityTime);
		
		getNamedParameterJdbcTemplate().execute(sql, params, new PreparedStatementCallback<Integer>() {
			@Override
			public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				return ps.executeUpdate();
			}
		});
		
		if(logger.isDebugEnabled()) {
			logger.debug("Exiting addAvailableDevice() of AvailableDeviceDaoJdbcImpl.");
		}
	}

	@Override
	public boolean isAvailableDeviceExist(String endpointGUID, Timestamp availabilityTime, int timeRange) {
		if(logger.isDebugEnabled()) {
			logger.debug("Entering isAvailableDeviceExist() of AvailableDeviceDaoJdbcImpl. endpointGUID = " + endpointGUID + 
					" availabilityTime = " + availabilityTime + " timeRange = " + timeRange);
		}
		
		String sql = "SELECT availableDeviceID " +
				"FROM AvailableDevice " +
				"WHERE endpointGUID = :endpointGUID AND TIMESTAMPDIFF(SECOND, availabilityTime, :availabilityTime) <= :timeRange";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("endpointGUID", endpointGUID);
		params.put("availabilityTime", availabilityTime);
		params.put("timeRange", timeRange);
		
		List<Integer> availableDeviceIDs = getNamedParameterJdbcTemplate().query(sql, params, new RowMapper<Integer>() {
			@Override
			public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getInt(1);
			}
		});
		
		if(logger.isDebugEnabled()) {
			logger.debug("Exiting isAvailableDeviceExist() of AvailableDeviceDaoJdbcImpl. Returning value is" + 
							(availableDeviceIDs.size() > 0));
		}
		
		return availableDeviceIDs.size() > 0;
	}

	@Override
	public List<AvailableDevice> getAvailableDeviceList(int tenantID, Timestamp availabilityTime, int timeRange) {
		if(logger.isDebugEnabled()) {
			logger.debug("Entering getAvailableDeviceList() of AvailableDeviceDaoJdbcImpl. availabilityTime = " + 
							availabilityTime + " timeRange = " + timeRange);
		}
		
		String sql = "SELECT ad.availableDeviceID availableDeviceID, ad.endpointGUID, m.memberName, e.endpointID endpointID, m.memberID " +
				"FROM AvailableDevice ad, Endpoints e LEFT JOIN Member m ON (e.memberID = m.memberID AND e.memberType = 'R') " +
				"WHERE ad.endpointGUID = e.endpointGUID AND m.tenantID = :tenantID AND " +
				"TIMESTAMPDIFF(SECOND, ad.availabilityTime, :availabilityTime) <= :timeRange";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tenantID", tenantID);
		params.put("availabilityTime", availabilityTime);
		params.put("timeRange", timeRange);
		
		List<AvailableDevice> adList = getNamedParameterJdbcTemplate().query(sql, params, new RowMapper<AvailableDevice>() {
			@Override
			public AvailableDevice mapRow(ResultSet rs, int rowNum) throws SQLException {
				AvailableDevice ad = new AvailableDevice();
				ad.setAvailableDeviceID(rs.getInt(1));
				ad.setEndpointGUID(rs.getString(2));
				ad.setEndpointDisplayName(rs.getString(3));
				ad.setEndpointID(rs.getInt(4));
				ad.setMemberID(rs.getInt(5));
				return ad;
			}
		});
		
		if(logger.isDebugEnabled()) {
			logger.debug("Exiting getAvailableDeviceList() of AvailableDeviceDaoJdbcImpl.");
		}
		
		return adList;
	}

	@Override
	public AvailableDevice getAvailableDevice(int tenantID, int availableDeviceID, Timestamp availabilityTime, int timeRange) {
		if(logger.isDebugEnabled()) {
			logger.debug("Entering getAvailableDevice() of AvailableDeviceDaoJdbcImpl. availableDeviceID = " + availableDeviceID + 
					" availabilityTime = " + availabilityTime + " timeRange = " + timeRange);
		}
		
		String sql = "SELECT ad.availableDeviceID availableDeviceID, ad.endpointGUID, m.memberName, e.endpointID endpointID, m.memberID " +
				"FROM AvailableDevice ad, Endpoints e LEFT JOIN Member m ON (e.memberID = m.memberID AND e.memberType = 'R') " +
				"WHERE ad.endpointGUID = e.endpointGUID AND m.tenantID = :tenantID AND " +
				"ad.availableDeviceID = :availableDeviceID AND TIMESTAMPDIFF(SECOND, availabilityTime, :availabilityTime) <= :timeRange";
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tenantID", tenantID);
		params.put("availableDeviceID", availableDeviceID);
		params.put("availabilityTime", availabilityTime);
		params.put("timeRange", timeRange);
		
		List<AvailableDevice> adList = getNamedParameterJdbcTemplate().query(sql, params, new RowMapper<AvailableDevice>() {
			@Override
			public AvailableDevice mapRow(ResultSet rs, int rowNum) throws SQLException {
				AvailableDevice ad = new AvailableDevice();
				ad.setAvailableDeviceID(rs.getInt(1));
				ad.setEndpointGUID(rs.getString(2));
				ad.setEndpointDisplayName(rs.getString(3));
				ad.setEndpointID(rs.getInt(4));
				ad.setMemberID(rs.getInt(5));
				return ad;
			}
		});
		
		AvailableDevice retValue = adList.size() > 0 ? adList.get(0) : null;
		if(logger.isDebugEnabled()) {
			logger.debug("Exiting getAvailableDevice() of AvailableDeviceDaoJdbcImpl.");
		}
		
		return retValue;
	}

}

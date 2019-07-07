/**
 *
 */
package com.vidyo.db;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vidyo.bo.TenantIpc;

/**
 * @author Ganesh
 *
 */
public class TenantIpcRowMapper implements RowMapper<TenantIpc> {

	/**
	 *
	 */
	@Override
	public TenantIpc mapRow(ResultSet rs, int rowNum) throws SQLException {
		TenantIpc tenantIpc = new TenantIpc();
		tenantIpc.setHostName(rs.getString("hostname"));
		tenantIpc.setTenantID(rs.getInt("tenantID"));
		tenantIpc.setWhiteList(rs.getInt("allowed"));
		tenantIpc.setIpcWhiteListId(rs.getInt("ipcWhiteListId"));
		tenantIpc.setIpcID(rs.getInt("ipcID"));
		tenantIpc.setInbound(rs.getInt("inbound"));
		tenantIpc.setOutbound(rs.getInt("outbound"));
		return tenantIpc;
	}

}

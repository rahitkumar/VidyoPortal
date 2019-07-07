/**
 *
 */
package com.vidyo.db.ipcdomain;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vidyo.bo.ipcdomain.IpcDomain;

/**
 * @author Garik
 *
 */
public class IpcDomainRowMapper implements RowMapper<IpcDomain> {

	/**
	 *
	 */
	@Override
	public IpcDomain mapRow(ResultSet rs, int rowNum) throws SQLException {
		IpcDomain ipcDomain = new IpcDomain();
		ipcDomain.setDomainID(rs.getInt("domainID"));
		ipcDomain.setDomainName(rs.getString("domainName"));
		return ipcDomain;
	}

}

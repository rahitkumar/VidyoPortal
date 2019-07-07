/**
 * 
 */
package com.vidyo.db.externallink;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vidyo.bo.ExternalLink;

/**
 * @author Ganesh
 * 
 */
public class ExternalLinkRowMapper implements RowMapper {

	/**
	 * 
	 */
	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ExternalLink externalLink = new ExternalLink();
		externalLink.setExternalLinkID(rs.getInt("externalLinkID"));
		externalLink.setFromConferenceName(rs.getString("fromConferenceName"));
		externalLink.setFromSystemID(rs.getString("fromSystemID"));
		externalLink.setFromTenantHost(rs.getString("fromTenantHost"));
		externalLink.setToSystemID(rs.getString("toSystemID"));
		externalLink.setToConferenceName(rs.getString("toConferenceName"));
		externalLink.setStatus(rs.getInt("status"));
		return externalLink;
	}

}

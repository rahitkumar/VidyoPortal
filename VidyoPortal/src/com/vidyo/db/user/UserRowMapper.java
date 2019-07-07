/**
 *
 */
package com.vidyo.db.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vidyo.bo.User;

/**
 * RowMapper for User object
 * @author Ganesh
 *
 */
public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setSak(rs.getString("sak"));
		user.setBindUserRequestID(rs.getLong("bindUserRequestID"));
		user.setUserRole(rs.getString("userRole"));
		return user;
	}

}

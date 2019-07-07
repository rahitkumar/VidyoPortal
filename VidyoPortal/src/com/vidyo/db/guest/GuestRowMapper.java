/**
 *
 */
package com.vidyo.db.guest;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.vidyo.bo.Guest;

/**
 * @author ganesh
 *
 */
public class GuestRowMapper implements RowMapper<Guest> {

	@Override
	public Guest mapRow(ResultSet rs, int rowNum) throws SQLException {
		Guest guest = new Guest();
		guest.setGuestName(rs.getString("guestName"));
		guest.setGuestID(rs.getInt("guestID"));
		return guest;
	}

}

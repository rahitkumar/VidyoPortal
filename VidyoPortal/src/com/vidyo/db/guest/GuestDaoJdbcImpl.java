/**
 *
 */
package com.vidyo.db.guest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;

import com.vidyo.bo.Guest;

/**
 * @author ganesh
 *
 */
public class GuestDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements
		GuestDao {

	private static final String GET_GUEST_DETAILS = "select guestID, guestName from Guests where guestID =:guestId and tenantID =:tenantId";

	/**
	 *
	 */
	@Override
	public Guest getGuest(int id, int tenantId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guestId", id);
		paramMap.put("tenantId", tenantId);
		List<Guest> guests = getNamedParameterJdbcTemplate().query(
				GET_GUEST_DETAILS, paramMap, new GuestRowMapper());
		return guests.isEmpty() ? null : guests.get(0);
	}

	private static final String DELETE_GUEST_PAK = "update Guests set pak = NULL where guestID =:guestId and tenantID =:tenantId";

	/**
	 *
	 * @param id
	 * @param tenantId
	 * @return
	 */
	@Override
	public int deletePak(int id, int tenantId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guestId", id);
		paramMap.put("tenantId", tenantId);
		return getNamedParameterJdbcTemplate().update(DELETE_GUEST_PAK,
				paramMap);
	}

	private static final String DELETE_GUEST_PAK2 = "update Guests set pak2 = NULL where guestID =:guestId and tenantID =:tenantId";

	/**
	 *
	 * @param id
	 * @param tenantId
	 * @return
	 */
	@Override
	public int deletePak2(int id, int tenantId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("guestId", id);
		paramMap.put("tenantId", tenantId);
		return getNamedParameterJdbcTemplate().update(DELETE_GUEST_PAK2,
				paramMap);
	}

}

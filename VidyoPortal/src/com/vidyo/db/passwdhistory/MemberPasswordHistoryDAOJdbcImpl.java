/**
 *
 */
package com.vidyo.db.passwdhistory;

import com.vidyo.bo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author Ganesh
 *
 */
public class MemberPasswordHistoryDAOJdbcImpl extends
        NamedParameterJdbcDaoSupport implements IMemberPasswordHistoryDAO {

    /** Logger for this class and subclasses */
        protected final Logger logger = LoggerFactory.getLogger(MemberPasswordHistoryDAOJdbcImpl.class.getName());


	/**
	 * Returns the number of times password has been changed by the user
	 *
	 * @param memberID
	 * @return
	 */
	//@Override
	public int getPasswordChangeCount(int memberID) {
		String GET_PASSWD_CHANGE_COUNT = "SELECT COUNT(1) FROM MemberPasswordHistory where memberID =:memberID";
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("memberID", memberID);
		return getNamedParameterJdbcTemplate().queryForObject(
				GET_PASSWD_CHANGE_COUNT, paramMap, Integer.class);
	}

	/**
	 *
	 * @param memberID
	 * @return
	 */
	//@Override
	public Date getLatestPasswordChangeDate(int memberID) {
		String LATEST_PASSWD_CHANGE_DATE = "select max(changeTime) from MemberPasswordHistory where memberID =:memberID";
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("memberID", memberID);
		return (Date) getNamedParameterJdbcTemplate().queryForObject(
				LATEST_PASSWD_CHANGE_DATE, paramMap, Date.class);
	}

	/**
	 *
	 * @param daysCount
	 * @return
	 */
	@Override
	public List<Member> getPasswordExpiryingMembers(int daysCount) {

        logger.debug("getPasswordExpiryingMembers");

        String GET_PASSWORD_EXPIRING_MEMBERS = "SELECT m.memberName, m.memberID, m.emailAddress, t.tenantID,"
				+ " t.tenantUrl, mr.roleName from Member m, MemberPasswordHistory mph, MemberRole mr, Tenant t "
				+ "where m.memberID = mph.memberID and m.tenantID = t.tenantID and m.roleID = mr.roleID and "
				+ "mr.roleDescription in (:roles) GROUP BY m.memberID HAVING DATE(max(mph.changeTime)) = "
				+ "ADDDATE(CURDATE(), interval - :daysCount day)";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<String> roles = new ArrayList<String>();
		roles.add("Admin");
		roles.add("Super");
		paramMap.put("daysCount", daysCount);
		paramMap.put("roles", roles);
		List<Member> members = getNamedParameterJdbcTemplate().query(
				GET_PASSWORD_EXPIRING_MEMBERS, paramMap,
				new RowMapper<Member>() {
					public Member mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						Member member = new Member();
						member.setMemberName(rs.getString(1));
						member.setMemberID(rs.getInt(2));
						member.setEmailAddress(rs.getString(3));
						member.setTenantID(rs.getInt(4));
						//member.setTenantUrl(rs.getString(5));
						member.setRoleName(rs.getString(6));
						return member;
					}
				});
		return members;
	}

	@Override
	public void cleanMemberPasswordHistory() {
		String sqlStr = "TRUNCATE TABLE MemberPasswordHistory";
		Map<String, Object> paramMap = new HashMap<String, Object>();

		getNamedParameterJdbcTemplate().update(sqlStr, paramMap);
	}

}

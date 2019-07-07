package com.vidyo.db;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.vidyo.bo.ForgotPassword;

public class LoginDaoJdbcImpl extends NamedParameterJdbcDaoSupport implements ILoginDao {

    /** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(LoginDaoJdbcImpl.class.getName());

    public List<ForgotPassword> getMembersForEmail(String email, int tenantID, List<String> userTypes) {
        logger.debug("Get records from Member for email address - " + email);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  memberID," +
            "  memberName," +
            "  username," +
            "  emailAddress," +
            "  langID" +
            " FROM" +
            "  Member mem, MemberRole mr" +
            " WHERE" +
            "  mem.emailAddress = :email AND active = :active" +
            " AND" +
            " tenantID = :tenantID AND mr.roleName in (:roleNames) and mr.roleID = mem.roleID"
        );
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("email", email)
                .addValue("tenantID", tenantID)
                .addValue("roleNames", userTypes)
                .addValue("active", 1);

        return getNamedParameterJdbcTemplate().query(sqlstm.toString(), namedParameters,
                BeanPropertyRowMapper.newInstance(ForgotPassword.class));
    }

    public int updateMemberPassKey(int memberID, String passKey) {
        logger.debug("Update record in Member for memberID = " + memberID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member" +
            " SET" +
            "  passKey = :passKey," +
            "  passTime = :passTime" +
            " WHERE" +
            "  memberID = :memberID"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("passKey", passKey)
                .addValue("passTime", (int)(new Date().getTime() * .001 + 3600))
                .addValue("memberID", memberID);

        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

    public List<ForgotPassword> getMemberForPassKey(String passKey) {
        logger.debug("Get record from Member for passKey - " + passKey);

        StringBuffer sqlstm = new StringBuffer(
            " SELECT " +
            "  memberID," +
            "  memberName," +
            "  username," +
            "  emailAddress," +
            "  langID" +
            " FROM" +
            "  Member" +
            " WHERE" +
            "  passKey = ?"
        );

        return getJdbcTemplate().query(sqlstm.toString(),
                BeanPropertyRowMapper.newInstance(ForgotPassword.class), passKey);
    }

    public int updateMemberPassword(int memberID, String newPassword) {
        logger.debug("Update record in Member for memberID = " + memberID);

        StringBuffer sqlstm = new StringBuffer(
            " UPDATE" +
            "  Member" +
            " SET" +
            "  password = :password," +
            "  passKey = NULL," +
            "  passTime = NULL" +
            " WHERE" +
            "  memberID = :memberID"
        );

        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("password", newPassword)
                .addValue("memberID", memberID);


        int affected = getNamedParameterJdbcTemplate().update(sqlstm.toString(), namedParameters);
        logger.debug("Rows affected: " + affected);

        return affected;
    }

}

package com.vidyo.framework.security.authentication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContextException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import com.vidyo.framework.context.TenantContext;

public class UserGuestDetailsService extends JdbcDaoImpl {

    private String authoritiesByUsernameQuery;
    private String usersByUsernameQuery;

    private MappingSqlQuery authoritiesByUsernameMapping;
    private MappingSqlQuery usersByUsernameMapping;

    private String rolePrefix = "";

    public void setAuthoritiesByUsernameQuery(String authoritiesByUsernameQuery) {
        this.authoritiesByUsernameQuery = authoritiesByUsernameQuery;
    }

    public void setUsersByUsernameQuery(String usersByUsernameQuery) {
        this.usersByUsernameQuery = usersByUsernameQuery;
    }

    protected void initDao() throws ApplicationContextException {
        initMappingSqlQueries();
    }

    private void initMappingSqlQueries() {
        this.usersByUsernameMapping = new UsersByUsernameMapping(getDataSource());
        this.authoritiesByUsernameMapping = new AuthoritiesByUsernameMapping(getDataSource());
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        List users = loadUsersByUsername(username, TenantContext.getTenantId());

        if (users.size() == 0) {
            throw new UsernameNotFoundException(
                    messages.getMessage("UserGuestsDetailsService.notFound", new Object[]{username}, "Username {0} not found"));
        }

        VidyoUserGuestDetails user = (VidyoUserGuestDetails) users.get(0); // contains no GrantedAuthority[]

        Set dbAuthsSet = new HashSet();

        dbAuthsSet.addAll(loadUserAuthorities(user.getUsername(), TenantContext.getTenantId()));

        List dbAuths = new ArrayList(dbAuthsSet);

        addCustomAuthorities(user.getUsername(), dbAuths);

        if (dbAuths.size() == 0) {
            throw new UsernameNotFoundException(
                    messages.getMessage("JdbcDaoImpl.noAuthority", new Object[] {username}, "User {0} has no GrantedAuthority"));
        }

        GrantedAuthority[] arrayAuths = (GrantedAuthority[]) dbAuths.toArray(new GrantedAuthority[dbAuths.size()]);

        return new VidyoUserGuestDetails(username, user.getPassword(), user.getPak(), user.getPak2(), user.getBak(), user.isEnabled(), user.isAllowedToParticipate(), arrayAuths);
    }

    protected List loadUsersByUsername(String username, int tenantID) {
        Object[] params = {username, String.valueOf(tenantID), username, String.valueOf(tenantID)};
        return usersByUsernameMapping.execute(params);
    }

    protected List loadUserAuthorities(String username, int tenantID) {
        Object[] params = {username, String.valueOf(tenantID), username, String.valueOf(tenantID)};
        return authoritiesByUsernameMapping.execute(params);
    }

    /**
     * Query object to look up a user.
     */
    private class UsersByUsernameMapping extends MappingSqlQuery {
        protected UsersByUsernameMapping(DataSource ds) {
            super(ds, usersByUsernameQuery);
            SqlParameter[] types = new SqlParameter[] {new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER),
                                                       new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER)};
            setParameters(types);
            compile();
        }

        protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
            String username = rs.getString(1);
            String password = rs.getString(2);
            String pak = rs.getString(3);
            String pak2 = rs.getString(4);
            String bak = rs.getString(5);
            boolean enabled = rs.getBoolean(6);
            boolean allowedToParticipate = rs.getBoolean(7); 
            UserDetails user = new VidyoUserGuestDetails(username, password, pak, pak2, bak, enabled, allowedToParticipate,
                    new GrantedAuthority[] {new SimpleGrantedAuthority("HOLDER")});

            return user;
        }
    }

    /**
     * Query object to look up a user's authorities.
     */
    private class AuthoritiesByUsernameMapping extends MappingSqlQuery {
        protected AuthoritiesByUsernameMapping(DataSource ds) {
            super(ds, authoritiesByUsernameQuery);
            SqlParameter[] types = new SqlParameter[] {new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER),
                                                       new SqlParameter(Types.VARCHAR), new SqlParameter(Types.INTEGER)};
            setParameters(types);
            compile();
        }

        protected Object mapRow(ResultSet rs, int rownum) throws SQLException {
            String roleName = rolePrefix + rs.getString(2);
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);

            return authority;
        }
    }

}
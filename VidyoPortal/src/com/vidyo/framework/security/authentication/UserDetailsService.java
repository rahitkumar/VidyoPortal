package com.vidyo.framework.security.authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vidyo.bo.Member;
import com.vidyo.bo.authentication.Authentication;
import com.vidyo.bo.authentication.LdapAuthentication;
import com.vidyo.db.authentication.UserAuthDao;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.utils.PasswordHash;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ldap.ILdapUserToMemberAttributesMapper;
import com.vidyo.service.ldap.LdapService;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.usergroup.UserGroupService;
import com.vidyo.utils.SecureRandomUtils;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    protected static final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);

    //new implementation for ldap
    @Autowired private LdapService ldapService;
    @Autowired private LdapContextSource ldapContextSource;
    private ILdapUserToMemberAttributesMapper ldapAttributesMapper;
    private MemberService memberService;

    public void setLdapAttributesMapper(ILdapUserToMemberAttributesMapper ldapAttributesMapper) {
        this.ldapAttributesMapper = ldapAttributesMapper;
    }

    public void setLdapService(LdapService ldapService) {
        this.ldapService = ldapService;
    }

    private ISystemService system;

    private UserAuthDao userAuthDao;

    private List<String> ldapImportRoles = new ArrayList<>();

    @Autowired
    private UserGroupService userGroupService;

    /**
     * @param userAuthDao
     *            the userAuthDao to set
     */
    public void setUserAuthDao(UserAuthDao userAuthDao) {
        this.userAuthDao = userAuthDao;

    }

    public void setSystem(ISystemService system) {
        this.system = system;
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public void setUserGroupService(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

	/**
     *
     * @param ldapImportRoles
     */
    public void setLdapImportRoles(String ldapImportRoles) {
        if (ldapImportRoles != null && !ldapImportRoles.isEmpty()) {
            String[] roles = ldapImportRoles.split(",");
            this.ldapImportRoles.addAll(Arrays.asList(roles));
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        logger.debug("Entering loadUserByUsername(String username : {}) of UserDetailsService", username);
        Authentication authConfig = system.getAuthenticationConfig(TenantContext.getTenantId()).toAuthentication();

        UserDetails userDetails = userAuthDao.loadUserByUsername(TenantContext.getTenantId(), username != null ? username.toLowerCase(Locale.ENGLISH) : null, authConfig);

        if (userDetails != null && userDetails instanceof VidyoUserDetails) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            ((VidyoUserDetails) userDetails).setSourceIP(request.getRemoteAddr());
        }

        userDetails = updateUserDetails(userDetails, username);
        logger.debug("Exiting loadUserByUsername(String username : {}) of UserDetailsService", username);
        return userDetails;
    }

    /**
     * Deprecated in favor of loadUserByUsername(String username). Would have to
     * be deleted after checking the requirements of the VidyoReplay
     * webservices.
     *
     * @param username
     * @param tenantID
     * @return
     * @throws UsernameNotFoundException
     * @throws DataAccessException
     */
    @Deprecated
    public UserDetails loadUserByUsername(String username, int tenantID) throws UsernameNotFoundException,
            DataAccessException {
        Authentication authConfig = system.getAuthenticationConfig(tenantID).toAuthentication();

        UserDetails userDetails = userAuthDao.loadUserByUsername(tenantID, username != null ? username.toLowerCase(Locale.ENGLISH) : null, authConfig);
        if (userDetails != null && userDetails instanceof VidyoUserDetails) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                    .getRequest();
            ((VidyoUserDetails) userDetails).setSourceIP(request.getRemoteAddr());
        }

        return userDetails;
    }


    private UserDetails updateUserDetails(UserDetails loadedUser, String username) {
        logger.debug("Entering updateUserDetails(UserDetails loadedUser, String username) of UserDetailsService");
        if(loadedUser != null && !((VidyoUserDetails) loadedUser).isImportedUser()) {
            return loadedUser;
        }
        Authentication authConfig = system.getAuthenticationConfig(TenantContext.getTenantId()).toAuthentication();
        if (authConfig instanceof LdapAuthentication) {

            if (loadedUser != null
                    && ((VidyoUserDetails) loadedUser).getLastRefreshTime() != null
                    && TimeUnit.MILLISECONDS.toMinutes(Calendar.getInstance().getTime().getTime()
                            - ((VidyoUserDetails) loadedUser).getLastRefreshTime().getTime()) < VidyoUserDetails.REFRESH_INTERVAL_MINUTES) {
                logger.debug("Loaded user is within the refresh time limit, not going to LDAP"
                        + TimeUnit.MILLISECONDS.toMinutes(Calendar.getInstance().getTime().getTime()
                                - ((VidyoUserDetails) loadedUser).getLastRefreshTime().getTime()));
                return loadedUser;
            }
            LdapAuthentication ldapAuth = (LdapAuthentication) authConfig;
            logger.debug("starting ldap authentication call. "+ System.currentTimeMillis());
            if (ldapAuth.isLdapmappingflag()) {
                DirContext ctx = null;
                String userId = null;
                int tenantID = TenantContext.getTenantId();
                String ldapAttributes[] = null;
                Member ldapMember = null;
                Map<String, String> portalToADAttributeMappings = null;
                try {
                    ldapContextSource.setUrl(ldapAuth.getLdapurl());
                    ldapContextSource.setUserDn(ldapAuth.getLdapusername());
                    ldapContextSource.setPassword(ldapAuth.getLdappassword());
                    ldapContextSource.setAnonymousReadOnly(true);
                    ldapContextSource.setPooled(false);
                    ldapContextSource.setCacheEnvironmentProperties(false);
                    ldapContextSource.afterPropertiesSet();
                    ctx = ldapContextSource.getContext(ldapAuth.getLdapusername(), ldapAuth.getLdappassword());
                    userId = loadedUser == null ? username : loadedUser.getUsername();
                    // for new user not in the database
                    if (loadedUser == null) {
                        portalToADAttributeMappings = ldapAttributesMapper.getPortalToADAttributeMappings(username, tenantID);
                        portalToADAttributeMappings.put("modifytimestamp", "modifytimestamp");
                        ldapAttributes = portalToADAttributeMappings.values().toArray(new String[0]);
                        Attributes attrs = ldapService.getLdapUsersAttributes(userId, ldapAuth, ctx, ldapAttributes);
                        Attribute modifyTimeStamp = attrs != null ? attrs.get("modifytimestamp") : null;
                        ldapMember = ldapAttributesMapper.getMemberFromAttributes(tenantID, attrs);
                        if (modifyTimeStamp != null && modifyTimeStamp.get() != null) {
                            ldapMember.setLastModifiedDateExternal((String) modifyTimeStamp.get());
                        }
                    } else {// existing users - query only for modifyTimestamp to determine if the user is updated in AD
                        String[] ldapAttr = { "modifytimestamp" };
                        Attributes attrs = ldapService.getLdapUsersAttributes(userId, ldapAuth, ctx, ldapAttr);
                        if (attrs != null && attrs.get("modifytimestamp") != null && attrs.get("modifytimestamp").get() != null) {
                            String modifyTimeStampStr = (String) attrs.get("modifytimestamp").get();
                            // If modifiedTimestamp is present and different, fetch the ldap attributes for the user
                            if (!modifyTimeStampStr.equalsIgnoreCase(((VidyoUserDetails) loadedUser).getLastModifiedDateExternal())) {
                                // If the last modification timestamp has changed, query for other AD attributes
                                portalToADAttributeMappings = ldapAttributesMapper.getPortalToADAttributeMappings(username, tenantID);
                                ldapAttributes = portalToADAttributeMappings.values().toArray(new String[0]);
                                attrs = ldapService.getLdapUsersAttributes(userId, ldapAuth, ctx, ldapAttributes);
                                ldapMember = ldapAttributesMapper.getMemberFromAttributes(tenantID, attrs);
                                ldapMember.setLastModifiedDateExternal(modifyTimeStampStr);
                            } else {
                                // To cover the case where modifytimestamp is not modified when users are added/removed from groups,
                                // we fetch the LDAP values, excluding the Thumbnail Photo
                                portalToADAttributeMappings = ldapAttributesMapper.getPortalToADAttributeMappings(username, tenantID);
                                if(portalToADAttributeMappings.containsKey("User Groups")
                                        && StringUtils.isNotBlank(portalToADAttributeMappings.get("User Groups"))) {
                                    portalToADAttributeMappings.remove("Thumbnail Photo");
                                    ldapAttributes = portalToADAttributeMappings.values().toArray(new String[0]);
                                    attrs = ldapService.getLdapUsersAttributes(userId, ldapAuth, ctx, ldapAttributes);
                                    ldapMember = ldapAttributesMapper.getMemberFromAttributes(tenantID, attrs);
                                }
                            }
                        }
                    }

                    //TODO The above can be re-factored as a common API

                    if (ldapMember != null && ldapMember.getUsername() != null
                            && !ldapMember.getUsername().trim().isEmpty()) {
                        String ldapMemberRole = "ROLE_" + ldapMember.getRoleName().toUpperCase();
                        boolean importLdapMember = false;
                        for (String ldapImportRole : ldapImportRoles) {
                            if (ldapImportRole.equals(ldapMemberRole)) {
                                importLdapMember = true;
                                break;
                            }
                        }
                        if (ldapMember != null && importLdapMember) {
                            ldapMember.setPassword(PasswordHash.createHash(SecureRandomUtils.generateHashKey(24)));
                            int memberId = ldapAttributesMapper.importLdapMember(ldapMember);
                            logger.debug("ended ldap authentication call. " + System.currentTimeMillis());

                            if (ldapMember.getThumbNailImage() != null && ldapMember.getThumbNailImage().length > 0) {


                                    ldapService.writeImagetoFileSystemAsync(String.valueOf(memberId),
                                            TenantContext.getTenantId(), ldapMember);
                                    logger.debug(memberId
                                            + "-member id,image is available to write a file. THe size of the image is "
                                            + ldapMember.getThumbNailImage().length + "background process is being called");
                            }
                        }

						// Manage user groups
						// 1. Create groups if not already present
						// 2. Assign the groups to the member if not already done
						if (ldapMember.isUserGroupsUpdated()) {
							Member member = memberService.getMemberByName(ldapMember.getUsername(), tenantID);
							if (member != null) {
								userGroupService.setGroupsForMember(member.getMemberID(),
										ldapMember.getUserGroupsFromAuthProvider());
							}
						}
					}

                    loadedUser = userAuthDao.loadUserByUsername(TenantContext.getTenantId(),
                            userId != null ? userId.toLowerCase(Locale.ENGLISH) : null, authConfig);
                    logger.debug("Updating the refresh time to current time stamp {}",
                            Calendar.getInstance().getTime());

                    ((VidyoUserDetails) loadedUser).setLastRefreshTime(Calendar.getInstance().getTime());

                } catch (Exception e) {
                    logger.error("LDAP communcation thew exception - retrieve user {}", e + " User -" + userId);
                    throw new BadCredentialsException("Bad credentials");
                } finally {
                    // It is imperative that the created DirContext instance is
                    // always closed
                    LdapUtils.closeContext(ctx);
                    logger.debug("dircontext object " + ctx.toString());
                }
            }
        }
        logger.debug("Exiting updateUserDetails(UserDetails loadedUser, String username) of UserDetailsService");
        return loadedUser;
    }

}
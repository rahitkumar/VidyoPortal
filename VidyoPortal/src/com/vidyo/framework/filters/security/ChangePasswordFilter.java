/**
 * 
 */
package com.vidyo.framework.filters.security;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.authentication.InternalAuthentication;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IMemberService;
import com.vidyo.service.ISystemService;
import com.vidyo.framework.security.authentication.VidyoUserDetails;
import com.vidyo.service.loginhistory.MemberLoginHistoryService;
import com.vidyo.service.passwdhistory.MemberPasswordHistoryService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This Filter checks if the user is logging in for the first time and redirects
 * him to the change password page. But if it is an LDAP account, there won't be
 * any force password change.
 * 
 * @author Ganesh
 * 
 */
public class ChangePasswordFilter extends OncePerRequestFilter implements
        Filter, InitializingBean {

	protected final String ERRORS_KEY = "errors";
	protected String changePasswordKey = "user.must.change.password";

	private Logger logger = Logger.getLogger(ChangePasswordFilter.class
			.getName());

	/**
	 * 
	 */
	private String changePasswordUrl = null;

	/**
	 * 
	 */
	private IMemberService memberService;

	/**
	 * 
	 */
	private ISystemService systemService;

	/**
	 * 
	 */
	private MemberPasswordHistoryService memberPasswordHistoryService;

	/**
	 * 
	 */
	private MemberLoginHistoryService memberLoginHistoryService;

	/**
	 * @param memberLoginHistoryService
	 *            the memberLoginHistoryService to set
	 */
	public void setMemberLoginHistoryService(
			MemberLoginHistoryService memberLoginHistoryService) {
		this.memberLoginHistoryService = memberLoginHistoryService;
	}

	/**
	 * @param changePasswordUrl
	 *            the changePasswordUrl to set
	 */
	public void setChangePasswordUrl(String changePasswordUrl) {
		this.changePasswordUrl = changePasswordUrl;
	}

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param systemService
	 *            the systemService to set
	 */
	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	/**
	 * @param memberPasswordHistoryService
	 *            the memberPasswordHistoryService to set
	 */
	public void setMemberPasswordHistoryService(
			MemberPasswordHistoryService memberPasswordHistoryService) {
		this.memberPasswordHistoryService = memberPasswordHistoryService;
	}

	/**
	 * 
	 * 
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		UserDetails userDetails = null;
		String requestURL = request.getRequestURL().toString();
		if (requestURL.endsWith(".html")) {
			logger.fine("changepasswordfilter URL: " + request.getRequestURL());
			logger.fine("changepasswordfilter URI: " + request.getRequestURI());
			try {
				Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if (obj instanceof UserDetails) {
					userDetails = (UserDetails) obj;
				}
				
		        List<MemberRoles> authForMemberRoles = systemService.getToRoles();
		        List<String> authForRoles = new LinkedList<String>();
		        for (MemberRoles r : authForMemberRoles) {
		            authForRoles.add("ROLE_" + r.getRoleName().toUpperCase());
		        }
		        boolean externalAuth = false;
		        if(userDetails != null) {
			         Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
			        for (GrantedAuthority role : roles) {
			            if (authForRoles.contains(role.getAuthority())) {
			            	externalAuth = true;
			                break;
			            }
			        }			        	
		        }

				boolean passwdExpired = false;
				if (userDetails != null) {
					int memberID = 0;
					try {
						//memberID = memberService.getMemberID(userDetails.getUsername(),	TenantContext.get());
                        memberID = memberService.getMemberID(userDetails.getUsername(), ((VidyoUserDetails)userDetails).getTenantID());
					} catch (Exception e) {
						logger.log(	Level.SEVERE, "No Member Found for UserName ->" + userDetails.getUsername()
										+ " TenantID ->"
										+ TenantContext.getTenantId());
					}

					if (memberID == 0) {
						// Assume the user as Super and try to use the Default
						// TenantID
						memberID = memberService.getMemberID(userDetails.getUsername(), 1);
					}
					int countPasswordChange = memberPasswordHistoryService.getPasswordChangeCount(memberID);
					// Use default for FORCE_PASSWORD_CHANGE - not customized
					// per tenant
					Configuration configuration = systemService.getForcePasswordChangeConfig();  //getConfiguration("FORCE_PASSWORD_CHANGE");
					// If force password change and 1st time login redirect to
					// change password
					boolean forcePasswdChange = false;
					if (configuration != null && configuration.getConfigurationValue().equals("1")) {
						forcePasswdChange = true;
					}
					int tenantID = TenantContext.getTenantId();
					if (tenantID == 0) {
						// Fall back Default Tenant ID
						tenantID = 1;
					}

					// Use default for PASSWORD_VALIDITY_DAYS - not customized
					// per tenant
                    Date lastChangeDate = null;
                    long lastPasswordChangedDaysDiff = 0;
					Configuration passwdValidityConfig = systemService.getPasswordValidityDaysConfig(); //.getConfiguration("PASSWORD_VALIDITY_DAYS");
					if (passwdValidityConfig != null) {
						String days = passwdValidityConfig.getConfigurationValue();
						int daysCount = Integer.parseInt(days.trim());
						try {
							lastChangeDate = memberPasswordHistoryService.getLatestPasswordChangeDate(memberID);
						} catch (Exception e) {
							logger.log(Level.SEVERE, "No password history");
						}
						if (lastChangeDate != null) {
							long d = Calendar.getInstance().getTimeInMillis() - lastChangeDate.getTime();
                            lastPasswordChangedDaysDiff = d / (24 * 60 * 60 * 1000);
							if (lastPasswordChangedDaysDiff >= daysCount && daysCount > 0) {
								passwdExpired = true;
							}
						}
					}
					// Use default for INACTIVE_DAYS_LIMIT - not customized per
					// tenant
					Configuration userInactivityLimitConfig = systemService.getInactiveDaysLimitConfig(); //.getConfiguration("INACTIVE_DAYS_LIMIT");
					boolean userExpired = false;
					if (userInactivityLimitConfig != null) {
						String days = userInactivityLimitConfig.getConfigurationValue();
						int daysCount = Integer.parseInt(days.trim());
						Date lastLoginDate = memberLoginHistoryService.getLastLoginTime(memberID, tenantID);
						if (lastLoginDate != null) {
							long d = Calendar.getInstance().getTimeInMillis() - lastLoginDate.getTime();
							long daysDiff = d / (24 * 60 * 60 * 1000);
							if (daysDiff >= daysCount && daysCount > 0 && lastPasswordChangedDaysDiff>0) {
								userExpired = true;
							}
						}

					}
					Integer tenantId = TenantContext.getTenantId();
                    com.vidyo.bo.authentication.Authentication authType = systemService.getAuthenticationConfig(tenantId).toAuthentication();
                    boolean isInternalAuth = authType instanceof InternalAuthentication;
					boolean externalAuthenticationFlag = false;
					if (!isInternalAuth) {
						//Combine the Tenant level LDAP authentication with UserTypes 
						externalAuthenticationFlag = (true && externalAuth);
					}
					// If super user, ignore the ldapflag
					SecurityContextHolderAwareRequestWrapper sc = new SecurityContextHolderAwareRequestWrapper(request, null);
					if (sc.isUserInRole("ROLE_SUPER")) {
						externalAuthenticationFlag = false;
					}

					if (countPasswordChange <= 0 && forcePasswdChange && !externalAuthenticationFlag) {
						logger.fine("credentials expired - sending to changepassword page.");
						int pos = requestURL.indexOf("changepassword");
						int pos1 = requestURL.indexOf("login");
						if (pos == -1 && pos1 == -1) {
							saveError(request, changePasswordKey);
							logger.fine("redirection here");
							sendRedirect(request, response, changePasswordUrl);
							return;
						}
					}

					if (passwdExpired && !externalAuthenticationFlag) {
						logger.fine("Password expired - sending to changepassword page.");
						int pos = requestURL.indexOf("changepassword");
						int pos1 = requestURL.indexOf("login");
						if (pos == -1 && pos1 == -1) {
							saveError(request, changePasswordKey);
							logger.fine("redirection here");
							sendRedirect(request, response, changePasswordUrl);
							return;
						}
					}

					if (userExpired && !externalAuthenticationFlag) {
						logger.fine("User expired - sending to changepassword page.");
						int pos = requestURL.indexOf("changepassword");
						int pos1 = requestURL.indexOf("login");
						if (pos == -1 && pos1 == -1) {
							saveError(request, changePasswordKey);
							logger.fine("redirection here");
							sendRedirect(request, response, changePasswordUrl);
							return;
						}
					}

				}
			} catch (Exception e) {
				logger.log(Level.SEVERE, e.getMessage(), e);
			}
		}
		filterChain.doFilter(request, response);

	}

	/**
	 * Allow subclasses to modify the redirection message.
	 * 
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param url
	 *            the URL to redirect to
	 * 
	 * @throws java.io.IOException
	 *             in the event of any failure
	 */
	protected void sendRedirect(HttpServletRequest request,
			HttpServletResponse response, String url) throws IOException {
		if (!url.startsWith("http://") && !url.startsWith("https://")) {
			url = request.getContextPath() + url;
		}
		logger.fine("Redirect URL ->" + response.encodeRedirectURL(url));
		response.sendRedirect(response.encodeRedirectURL(url));
	}

	/**
	 * 
	 * @param request
	 * @param msg
	 */
	@SuppressWarnings("unchecked")
	public void saveError(HttpServletRequest request, String msg) {
		Set<Object> errors = (Set<Object>) request.getSession().getAttribute(
				ERRORS_KEY);

		if (errors == null) {
			errors = new HashSet<Object>();
		}

		errors.add(msg);
		request.getSession().setAttribute(ERRORS_KEY, errors);
	}

}

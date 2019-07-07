/**
 * 
 */
package com.vidyo.interceptors.transaction;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.vidyo.framework.context.TenantContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.RememberMeAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.authentication.AuthenticationType;
import com.vidyo.bo.loginhistory.MemberLoginHistory;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.service.IMemberService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.framework.security.authentication.VidyoUserDetails;
import com.vidyo.service.loginhistory.MemberLoginHistoryService;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.utils.HtmlUtils;

/**
 * Intercepts the authenticate API of AuthenticationProvider interface <br>
 * and based on the invocation result, logs the events in TransactionHistory<br>
 * and MemberLoginHistory tables.
 * 
 * @author Ganesh
 * 
 */
public class AuthenticationTransactionInterceptor implements MethodInterceptor {

	/** Logger for this class and subclasses */
	protected final static Logger logger = LoggerFactory.getLogger(TransactionInterceptor.class.getName());

	/**
	 * 
	 */
	private Map<String, String> methodNameTransactionMap;

	/**
	 * 
	 */
	private ITenantService tenantService;

	/**
	 * 
	 */
	private TransactionService transactionService;

	/**
	 *
	 */
	private IMemberService memberService;

	/**
	 *
	 */
	private MemberLoginHistoryService memberLoginHistoryService;

	/**
	 *
	 */
	private ISystemService systemService;

    private UserDetailsService userDetailsService;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

	/**
	 * @param methodNameTransactionMap
	 *            the methodNameTransactionMap to set
	 */
	public void setMethodNameTransactionMap(Map<String, String> methodNameTransactionMap) {
		this.methodNameTransactionMap = methodNameTransactionMap;
	}

	/**
	 * @param tenantService
	 *            the tenantService to set
	 */
	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * @param memberLoginHistoryService
	 *            the memberLoginHistoryService to set
	 */
	public void setMemberLoginHistoryService(MemberLoginHistoryService memberLoginHistoryService) {
		this.memberLoginHistoryService = memberLoginHistoryService;
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
	 * The invoke logic handles the below cases.
	 * 
	 * 1. Regular Member Login - User Portal [Success & Timeouts & Auth Failure to be logged till account gets locked]
	 * 2. Regular Member Login - User Webservices [Success & Auth Failure to be logged till account gets locked] 3.
	 * Admin & Super Login - Admin Portal [Success & Auth Failure to be logged till account gets locked & Timeouts] 4.
	 * Super Login - Super Portal [Success & Auth Failure to be logged till account gets locked & Timeouts] 5. Admin
	 * User - Admin Webservices [Auth Failure to be logged till account gets locked] 6. VidyoManager, Proxy, Gateway,
	 * Recorder & Replay [No logging of any Auth events] 7. User not exist in DB - should be logged only in audit table.
	 * 
	 */
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		logger.debug("Entering invoke() of AuthenticationTransactionInterceptor");
		Object retVal = null;
		boolean operSuccess = true;
		boolean acctLocked = false;
		Exception ex = null;

		Authentication authentication = null;
		for (Object obj : methodInvocation.getArguments()) {
			if (obj instanceof Authentication) {
				authentication = (Authentication) obj;
			}
		}

		try {
			retVal = methodInvocation.proceed();
		} catch (Exception ae) {
			operSuccess = false;
			/*logger.error("Exception while doing operation -" + methodInvocation.getMethod().getName() + " Error -"
					+ ae.getMessage());*/
			ex = ae;

		}

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
				.getRequest();
		String requestPath = request.getRequestURL().toString();
		boolean servicesInvocation = requestPath.contains("/services/") || requestPath.contains("/gatewayService/");

		// Do not create member login history for VidyoGatewayService, VidyoPortalService, VidyoReplayService, VidyoRecorderService, ConfigurationService
		
		/*
		 * The createMemberLoginHistory API sets memberId to zero if the user is not found in Member/LDAP.
		 * If the memberId is zero, the memberloginhistory table is never populated. 
		 * So this processing logic is unnecessary for the services listed in the comment above.
		 */
		
		MemberLoginHistory memberLoginHistory = null;
		boolean userServiceInvocation = requestPath.contains("/VidyoPortalUserService");
		boolean componentServicesInvocation = requestPath.contains("/VidyoGatewayService") || requestPath.contains("/VidyoPortalService") || requestPath.contains("/VidyoReplayService")
				 || requestPath.contains("/VidyoRecorderService") || requestPath.contains("/ConfigurationService");
		
		TransactionHistory transactionHistory = createTransactionHistory(
				(Authentication) (retVal == null ? authentication : retVal), operSuccess);
		
		if ( !componentServicesInvocation) {
			memberLoginHistory = createMemberLoginHistory(
				(Authentication) (retVal == null ? authentication : retVal), operSuccess);
		}	
	
			// Login success - check all possible parameters to assert the success condition
			if (retVal != null && operSuccess && ex == null) {
				// Override the MemberId in success case from Auth object
				logger.debug("Auth Success {}", ToStringBuilder.reflectionToString(retVal, ToStringStyle.DEFAULT_STYLE));
				if (memberLoginHistory != null && ((Authentication) retVal).getPrincipal() instanceof VidyoUserDetails) {
					memberLoginHistory.setMemberID(((VidyoUserDetails) ((Authentication) retVal).getPrincipal())
							.getMemberId());
				}
				if(!(retVal instanceof RememberMeAuthenticationToken) &&  isCACEnabled() && userServiceInvocation) {
					logger.warn("Not allowed since tenant authentication is CAC based ");
					throw new BadCredentialsException("Bad credentials");
				}
				handleAuthSuccess(memberLoginHistory, transactionHistory, !servicesInvocation);
			} else {
				acctLocked = handleAuthFailure(memberLoginHistory, transactionHistory);
				if (retVal == null && !operSuccess && ex != null && ex instanceof AuthenticationException) {
					if (acctLocked) {
						LockedException lockedException = new LockedException("Account Locked out", ex);
						throw lockedException;
					}
					throw ex;
				}
			}
		//} 

		return retVal;
	}
	private boolean isCACEnabled() {
		com.vidyo.bo.authentication.Authentication authConfig = systemService.getAuthenticationConfig(TenantContext.getTenantId()).toAuthentication();
	       AuthenticationType authType =  authConfig.getAuthenticationType();
	       
      if (authType == AuthenticationType.CAC) {
     	 return true;
      }
      return false;
	}


	protected boolean handleAuthFailure(MemberLoginHistory memberLoginHistory, TransactionHistory transactionHistory) {
		boolean acctLocked = false;
		logger.debug("Auth Failure {}",
				ToStringBuilder.reflectionToString(memberLoginHistory, ToStringStyle.DEFAULT_STYLE));
		int failureCount = 0;
		int allowedLoginFailureCount = 0;
        if(memberLoginHistory == null || (memberLoginHistory != null && memberLoginHistory.getMemberID()<= 0)){
            saveTransactionHistory(transactionHistory);
            return acctLocked;
        }
		// Get the Allowed failed logins count
		Configuration loginConfiguration = systemService.getLoginFalureCountConfig();
		if (loginConfiguration != null && loginConfiguration.getConfigurationValue() != null) {
			try {
				allowedLoginFailureCount = Integer.valueOf(loginConfiguration.getConfigurationValue().trim());
			} catch (NumberFormatException nfe) {
				logger.error("Invalid Login Failure Count Value {}", loginConfiguration.getConfigurationValue());
			}

		}
		// Login FAILURE computing START
		if (memberLoginHistory != null && allowedLoginFailureCount > 0) {
			Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
			failureCount = memberService.getMemberLoginFailureCount(transactionHistory.getUserID(),
					tenant.getTenantID());

			// Condition when the locked out user tries to login
			if (failureCount >= allowedLoginFailureCount) {
				logger.debug("Failure Count reached {}", failureCount);
				transactionHistory.setTransactionResult("FAILURE: ACCOUNT LOCKED - LOGIN ATTEMPT");
				memberService.updateUserAsLocked(memberLoginHistory.getMemberID(), tenant.getTenantID(),  transactionHistory.getUserID());
                acctLocked = true;
			}
			if (failureCount == (allowedLoginFailureCount - 1)) {
				logger.debug("Failure Count is one less than allowed limit {}" + failureCount);
				List<MemberLoginHistory> failedLogins = memberLoginHistoryService.getFailedLoginDetails(
						memberLoginHistory.getMemberID(), tenant.getTenantID());
				logger.debug("Failed logins count {}", failedLogins.size());
				int failedLoginIdx = 0;
				if (allowedLoginFailureCount == 1) {
					failedLoginIdx = 0;
				} else {
					failedLoginIdx = (allowedLoginFailureCount - 2);
				}
				MemberLoginHistory failedLoginHistory = null;
				if (!failedLogins.isEmpty() && ((failedLogins.size() - 1) >= failedLoginIdx)) {
					failedLoginHistory = failedLogins.get(failedLoginIdx);
					long currTime = Calendar.getInstance().getTimeInMillis();
					long firstFailedLoginTime = failedLoginHistory.getTransactionTime().getTime();
					long diff = currTime - firstFailedLoginTime;
					if (logger.isDebugEnabled()) {
						logger.debug("Time difference ->" + (diff / 1000) + "<- ID" + failedLoginHistory.getID()
								+ "Time-->" + failedLoginHistory.getTransactionTime());
					}
					Configuration configuration = systemService.getUserLockoutTimeLimitinSecConfig();
					int lockTimeLimitSecs = 60;
					if (configuration != null && configuration.getConfigurationValue() != null) {
						logger.debug("configuration.getConfigurationValue() = " + configuration.getConfigurationValue());
						try {
							lockTimeLimitSecs = Integer.valueOf(configuration.getConfigurationValue().trim());
						} catch (Exception e1) {
							logger.error("Exception while parsing the Configuration Value->"
									+ configuration.getConfigurationValue());
							lockTimeLimitSecs = 60;
						}
					}
					logger.debug("lockTimeLimitSecs = " + lockTimeLimitSecs);
					if ((diff / 1000) <= lockTimeLimitSecs) {
						if (logger.isDebugEnabled()) {
							logger.debug("Update the User as Locked" + (diff / 1000));
						}
                        memberService.updateUserAsLocked(memberLoginHistory.getMemberID(), tenant.getTenantID(),  transactionHistory.getUserID());
						memberLoginHistory.setTransactionResult("FAILURE: ACCOUNT LOCKED");
						transactionHistory.setTransactionResult("FAILURE: ACCOUNT LOCKED");
						acctLocked = true;
					} else {
						// Reset the login failure count
						if (logger.isDebugEnabled()) {
							logger.debug("Time difference is not within 60 seconds ->" + (diff / 1000)
									+ "<- so reset the failure count to zero");
						}
						memberService.resetLoginFailureCount(memberLoginHistory.getMemberID());
						failureCount = 0;
					}
				} else {
                    memberService.updateUserAsLocked(memberLoginHistory.getMemberID(), tenant.getTenantID(),  transactionHistory.getUserID());
					acctLocked = true;
				}
			}

			if (failureCount <= (allowedLoginFailureCount - 1) && failureCount >= 0) {
				memberService.incrementLoginFailureCount(memberLoginHistory.getMemberID());
			}
		}
		// TODO - do not log the transaction when the account is already locked
		saveMemberLoginHistory(memberLoginHistory);
		saveTransactionHistory(transactionHistory);
		return acctLocked;
	}

	/**
	 * 
	 * @param memberLoginHistory
	 * @param transactionHistory
	 * @param logTransaction
	 */
	protected void handleAuthSuccess(MemberLoginHistory memberLoginHistory, TransactionHistory transactionHistory,
			boolean logTransaction) {
		logger.debug("Auth Success {}",
				ToStringBuilder.reflectionToString(memberLoginHistory, ToStringStyle.DEFAULT_STYLE));

        // nothing to reset if the user is not found in database
        if (memberLoginHistory != null && memberLoginHistory.getMemberID() != 0) {
            memberService.resetLoginFailureCount(memberLoginHistory.getMemberID());
        }

		if (logTransaction) {
			saveMemberLoginHistory(memberLoginHistory);
			saveTransactionHistory(transactionHistory);
		}

	}

	/**
	 * 
	 * @param authentication
	 * @param loginSuccess
	 * @return
	 */
	protected MemberLoginHistory createMemberLoginHistory(Authentication authentication, boolean loginSuccess) {
		MemberLoginHistory memberLoginHistory = new MemberLoginHistory();
		int memberId = 0;
		if (!loginSuccess) {
			memberId = identifyMember(authentication.getName());
		}
		memberLoginHistory.setMemberID(memberId);
		memberLoginHistory.setTransactionName("Login");
		memberLoginHistory.setTransactionResult(loginSuccess ? "SUCCESS" : "FAILURE");
		if(authentication.getDetails() != null && authentication.getDetails() instanceof WebAuthenticationDetails) {
			memberLoginHistory.setSourceIP(((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress());
		} else if(authentication.getDetails() != null && authentication.getDetails() instanceof VidyoUserDetails) {
			memberLoginHistory.setSourceIP(((VidyoUserDetails) authentication.getDetails()).getSourceIP());
		}
		memberLoginHistory.setTransactionTime(Calendar.getInstance().getTime());
		return memberLoginHistory;
	}

	/**
	 * 
	 * @param authentication
	 * @param loginSuccess
	 * @return
	 */
	protected TransactionHistory createTransactionHistory(Authentication authentication, boolean loginSuccess) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setUserID(authentication.getName());
		transactionHistory.setTransactionName("Login");
		transactionHistory.setTransactionResult(loginSuccess ? "SUCCESS" : "FAILURE");
		if(authentication.getDetails() != null && authentication.getDetails() instanceof WebAuthenticationDetails) {
			transactionHistory.setSourceIP(((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress());
		} else if(authentication.getDetails() != null && authentication.getDetails() instanceof VidyoUserDetails) {
			transactionHistory.setSourceIP(((VidyoUserDetails) authentication.getDetails()).getSourceIP());
		}
		transactionHistory.setTransactionTime(Calendar.getInstance().getTime());
		Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
		transactionHistory.setTenantName(tenant != null ? tenant.getTenantName() : "Unknown");
		String replaced = HtmlUtils.replaceNewlineAndTabCharacters(authentication.getName(), '_');
		String sanitized = "";
		if (StringUtils.isNotBlank(replaced)) {
			sanitized = ESAPI.encoder().encodeForHTML(replaced);
		}
		transactionHistory.setTransactionParams("Username: " + sanitized);
		return transactionHistory;
	}

	/**
	 * Return the memberid for the username and tenant combination
	 * 
	 * @param username
	 * @return
	 */
    protected int identifyMember(String username) {
        int memberId = 0;
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
            if(null != userDetails) memberId = ((VidyoUserDetails)userDetails).getMemberId();
        } catch (Exception e) {
            logger.error("Exception when getting the memberid by username {}, tenantId {}", username,
                    TenantContext.getTenantId());
        }
        return memberId;
    }

	/**
	 * 
	 * @param memberLoginHistory
	 * @return
	 */
	protected int saveMemberLoginHistory(MemberLoginHistory memberLoginHistory) {
		// Do not log in to MemberLoginHistory Table if the user is not found in database
		if (memberLoginHistory == null || (memberLoginHistory != null && memberLoginHistory.getMemberID() == 0)) {
			return 0;
		}
		
		int loginHistoryId = 0;
		loginHistoryId = memberLoginHistoryService.addMemberLoginHistory(memberLoginHistory);
		return loginHistoryId;
	}

	/**
	 * 
	 * @param transactionHistory
	 * @return
	 */
	protected int saveTransactionHistory(TransactionHistory transactionHistory) {
		int tranHistoryId = 0;
		tranHistoryId = transactionService.addTransactionHistory(transactionHistory);
		return tranHistoryId;
	}

}
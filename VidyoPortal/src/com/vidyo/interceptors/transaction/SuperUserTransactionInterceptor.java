/**
 * 
 */
package com.vidyo.interceptors.transaction;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.Member;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.service.ITenantService;
import com.vidyo.service.member.MemberService;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.utils.HtmlUtils;

/**
 * @author ganesh
 *
 */
public class SuperUserTransactionInterceptor extends TransactionInterceptor{

	/**
	 * 
	 */
	protected final static Logger logger = LoggerFactory
			.getLogger(SuperUserTransactionInterceptor.class);

	/**
	 * 
	 */
	private TransactionService transactionService;

	/**
	 * 
	 */
	private ITenantService tenantService;

	/**
	 * 
	 */
	private MemberService memberService1;

	/**
	 * 
	 */
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Object retVal = null;
		Member superMember = null;
		Integer memberId = null;
		// Member object will be present as per the method interface
		for (Object obj : methodInvocation.getArguments()) {
			if (obj instanceof Member) {
				superMember = (Member) obj;
			}
			if (obj instanceof Integer) {
				memberId = (Integer) obj;
			}
		}
		if (methodInvocation.getMethod().getName()
				.equalsIgnoreCase("deleteSuper")) {
			try {
				superMember = memberService1.getSuper(memberId);
			} catch (Exception e) {
				logger.error("Super User does not exist {}", memberId);
			}
		}

		try {
			retVal = methodInvocation.proceed();
		} catch (Exception e) {
			logger.error("Error while insering/updating of the super user "
					+ superMember != null ? superMember.getUsername() : null);
			retVal = 0;
		}

		TransactionHistory transactionHistory = new TransactionHistory();
		// Super is always on the default tenant 1
		Tenant tenant = tenantService.getTenant(1);
		if (tenant != null) {
			transactionHistory.setTenantName(tenant.getTenantName());
		}
		String transactionParams = null;
		if (methodInvocation.getMethod().getName()
				.equalsIgnoreCase("insertSuper")) {
			transactionHistory.setTransactionName("Add Super");
			transactionParams = createMemberTransactionParams(methodInvocation.getMethod().getName(), methodInvocation.getArguments(), retVal);
		} else if (methodInvocation.getMethod().getName()
				.equalsIgnoreCase("updateSuper")) {
			transactionHistory.setTransactionName("Modify Super");
			transactionParams = createMemberTransactionParams(methodInvocation.getMethod().getName(), methodInvocation.getArguments(), retVal);
		} else if (methodInvocation.getMethod().getName()
				.equalsIgnoreCase("deleteSuper")) {
			transactionHistory.setTransactionName("Delete Super");
			String superUserName = superMember != null ? superMember
					.getUsername() : "Unknown";
			String replaced = HtmlUtils.replaceNewlineAndTabCharacters(superUserName, '_');
			String sanitized = "";
			if (StringUtils.isNotBlank(replaced)) {
				sanitized = ESAPI.encoder().encodeForHTML(replaced);
			}						
			transactionParams = "Username: " + sanitized;
			transactionParams = transactionParams.concat(";MemberId: " + memberId);
		}
		transactionHistory.setTransactionParams(transactionParams);

		if (retVal instanceof Integer && ((Integer) retVal) > 0) {
			transactionHistory.setTransactionResult("SUCCESS");
		} else {
			transactionHistory.setTransactionResult("FAILURE");
		}
		transactionService
				.addTransactionHistoryWithUserLookup(transactionHistory);
		return retVal;
	}

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * @param tenantService
	 *            the tenantService to set
	 */
	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService1(MemberService memberService) {
		this.memberService1 = memberService;
	}

}

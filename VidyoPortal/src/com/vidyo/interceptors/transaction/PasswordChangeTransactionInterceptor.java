/**
 * 
 */
package com.vidyo.interceptors.transaction;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.Member;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ITenantService;
import com.vidyo.service.transaction.TransactionService;
import com.vidyo.utils.HtmlUtils;

/**
 * @author ganesh
 *
 */
public class PasswordChangeTransactionInterceptor implements MethodInterceptor {

	/**
	 * 
	 */
	protected final static Logger logger = LoggerFactory
			.getLogger(PasswordChangeTransactionInterceptor.class);

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
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		Object retVal = null;
		Member member = null;
		for (Object obj : methodInvocation.getArguments()) {
			if (obj instanceof Member) {
				member = (Member) obj;
			}
		}
		try {
			retVal = methodInvocation.proceed();
		} catch (Exception e) {
			logger.error("Error while updating password of the member "
					+ member != null ? member.getUsername() + " Id "
					+ member.getMemberID() : null);
		}
		if (member == null) {
			member = new Member();
		}
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTenantName(tenantService.getTenant(
				TenantContext.getTenantId()).getTenantName());
		transactionHistory.setTransactionName("Password Change");
		String replaced = HtmlUtils.replaceNewlineAndTabCharacters(member.getUsername(), '_');
		String sanitized = "";
		if (StringUtils.isNotBlank(replaced)) {
			sanitized = ESAPI.encoder().encodeForHTML(replaced);
		}
		//MemberID cannot be forged as it is system generated
		transactionHistory.setTransactionParams("username-"
				+ sanitized + ";MemberId-" + member.getMemberID());
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

}

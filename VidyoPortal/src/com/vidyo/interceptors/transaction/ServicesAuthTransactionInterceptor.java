/**
 * 
 */
package com.vidyo.interceptors.transaction;

import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vidyo.bo.loginhistory.MemberLoginHistory;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.framework.security.authentication.VidyoUserDetails;

/**
 * Extends the @link {@link AuthenticationTransactionInterceptor} and intercepts only the login and logout API of the<br>
 * UserService and logs them in the transaction history/member login history tables.<br>
 * 
 * @author ganesh
 * 
 */
public class ServicesAuthTransactionInterceptor extends AuthenticationTransactionInterceptor {

	/**
	 * Map holding package to exception class mapping
	 */
	private Map<String, String> packageExceptionMap;

	/**
	 * Intercepts the login/logout methods and if there is any exception in the<br>
	 * flow, returns GeneralFault based on the package intercepted.
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object retVal = null;

		try {
			retVal = invocation.proceed();
		} catch (Exception ex) {
			logger.error("Unexpected Error while doing login operation of user service {}", ex.getMessage());
			String exceptionClassName = packageExceptionMap.get(invocation.getMethod().getDeclaringClass().getPackage()
					.getName());
			Class<?> exceptionClass = Class.forName(exceptionClassName);
			Object exceptionObj = exceptionClass.newInstance();
			if (exceptionObj instanceof Exception) {
				throw ((Exception) exceptionObj);
			}
		}

		// Log the success transaction
		if (retVal != null) {
			MemberLoginHistory memberLoginHistory = createMemberLoginHistory(SecurityContextHolder.getContext()
					.getAuthentication(), true);
			TransactionHistory transactionHistory = createTransactionHistory(SecurityContextHolder.getContext()
					.getAuthentication(), true);
			if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof VidyoUserDetails) {
				memberLoginHistory.setMemberID(((VidyoUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal()).getMemberId());
			}
			// If the method intercepted is logout, override the transaction name as Logout
			if (invocation.getMethod().getName().contains("logOut")) {
				memberLoginHistory.setTransactionName("Logout");
				transactionHistory.setTransactionName("Logout");
			}
			// logout should not go to MemberLoginHistory
			if (!memberLoginHistory.getTransactionName().contains("Logout")) {
				saveMemberLoginHistory(memberLoginHistory);
			}
			saveTransactionHistory(transactionHistory);
		}

		return retVal;
	}

	/**
	 * @param packageExceptionMap
	 *            the packageExceptionMap to set
	 */
	public void setPackageExceptionMap(Map<String, String> packageExceptionMap) {
		this.packageExceptionMap = packageExceptionMap;
	}

}

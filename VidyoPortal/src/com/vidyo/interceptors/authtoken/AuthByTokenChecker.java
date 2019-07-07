/**
 * 
 */
package com.vidyo.interceptors.authtoken;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.vidyo.portal.user.v1_1.GeneralFault;
import com.vidyo.portal.user.v1_1.GeneralFaultException;

/**
 * Intercepts specific web service calls and if the user is authenticated using
 * token, prevents the specific operation and throws an error back.
 * 
 * @author Ganesh
 * 
 */
public class AuthByTokenChecker implements MethodInterceptor {

	/**
	 * 
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object authUsingToken = RequestContextHolder.getRequestAttributes()
				.getAttribute("AUTH_USING_TOKEN",
						RequestAttributes.SCOPE_REQUEST);
		if (authUsingToken != null && authUsingToken instanceof Boolean) {
			if (Boolean.valueOf(authUsingToken.toString())) {
				GeneralFault fault = new GeneralFault();
				fault.setErrorMessage("Username and Password required to perform this operation");
				GeneralFaultException exception = new GeneralFaultException(
						fault.getErrorMessage());
				exception.setFaultMessage(fault);
				throw exception;
			}
		}

		return invocation.proceed();
	}

}

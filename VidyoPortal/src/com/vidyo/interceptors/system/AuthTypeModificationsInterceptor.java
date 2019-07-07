/**
 * 
 */
package com.vidyo.interceptors.system;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.db.security.token.VidyoPersistentTokenRepository;
import com.vidyo.framework.context.TenantContext;

/**
 * @author ganesh
 *
 */
public class AuthTypeModificationsInterceptor implements MethodInterceptor{
	
	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory.getLogger(AuthTypeModificationsInterceptor.class);
	
	/**
	 * 
	 */
	private VidyoPersistentTokenRepository persistentTokenRepository;

	/**
	 * @param persistentTokenRepository the persistentTokenRepository to set
	 */
	public void setPersistentTokenRepository(
			VidyoPersistentTokenRepository persistentTokenRepository) {
		this.persistentTokenRepository = persistentTokenRepository;
	}

	/**
	 * 
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object object = null;
		try {
			object = invocation.proceed();
			if(object != null && object instanceof Integer && ((Integer)object) == 1) {
				purgeAuthTokensForTenant();
			}
		} catch(Exception e) {
			logger.error("Exception while invoking auth modification in system service", e);
		}
		return object;
	}
	
	/**
	 * 
	 * @return
	 */
	private int purgeAuthTokensForTenant() {
		int deleteCount = persistentTokenRepository.removeUserTokensByTenant(TenantContext.getTenantId());
		logger.debug("Number of Auth Tokens deleted - "+ deleteCount + " for Tenant " +TenantContext.getTenantId());
		return deleteCount;
	}

}

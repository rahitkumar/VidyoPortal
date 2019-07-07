/**
 * 
 */
package com.vidyo.interceptors.authtoken.delete;

import java.lang.reflect.Method;

import com.vidyo.framework.context.TenantContext;
import org.springframework.aop.MethodBeforeAdvice;

import com.vidyo.bo.Member;
import com.vidyo.db.security.token.VidyoPersistentTokenRepository;

/**
 * @author Ganesh
 * 
 */
public class DeleteAuthTokensAdvice implements MethodBeforeAdvice {

	/**
	 * 
	 */
	private VidyoPersistentTokenRepository vidyoPersistentTokenRepository;

	/**
	 * @param vidyoPersistentTokenRepository
	 *            the vidyoPersistentTokenRepository to set
	 */
	public void setVidyoPersistentTokenRepository(VidyoPersistentTokenRepository vidyoPersistentTokenRepository) {
		this.vidyoPersistentTokenRepository = vidyoPersistentTokenRepository;
	}

	/**
	 * 
	 */
	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		String username = null;
		// Get the member object based on the method being intercepted
		if (method.getName().equalsIgnoreCase("updateMember")) {
			// Populate username only if password is present
			if (args[1] instanceof Member) {
				if (((Member) args[1]).getPassword() != null) {
					username = ((Member) args[1]).getUsername();
				}
			} else if (args[2] instanceof Member) {
				if (((Member) args[2]).getPassword() != null) {
					username = ((Member) args[2]).getUsername();
				}
			}
		} else if (method.getName().equalsIgnoreCase("updateMemberPassword")) {
			username = ((Member) args[0]).getUsername();
		}
		Integer tenant = TenantContext.getTenantId();
		if (username != null && tenant != null) {
			vidyoPersistentTokenRepository.removeUserTokens(username, tenant, null);
		}
	}

}

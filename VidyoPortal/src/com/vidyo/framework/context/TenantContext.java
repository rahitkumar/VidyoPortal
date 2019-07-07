/**
 * 
 */
package com.vidyo.framework.context;

/**
 * @author ganesh
 * 
 */
public class TenantContext {

	private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();

	public static void setTenantId(Integer tenantId) {
		threadLocal.set(tenantId);
	}

	public static Integer getTenantId() {
		return threadLocal.get();
	}

	public static void clearTenantId() {
		threadLocal.remove();
	}

}

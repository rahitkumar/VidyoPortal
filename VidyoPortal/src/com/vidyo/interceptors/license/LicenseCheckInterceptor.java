/**
 * 
 */
package com.vidyo.interceptors.license;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import com.vidyo.utils.UserAgentUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.TransportHeaders;

import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.service.LicensingService;


public class LicenseCheckInterceptor implements MethodBeforeAdvice {

	/** Logger for this class and subclasses */
	protected final static Logger logger = LoggerFactory.getLogger(LicenseCheckInterceptor.class.getName());

	/**
	 * 
	 */
	private Map<String, String> apiPackageLicenseNameMap;
	
	/**
	 * 
	 */
	private Map<String, String> apiPackageNoLicenseExceptionMap;
	
	/**
	 * 
	 */
	private LicensingService licenseService;

	/**
	 * @param methodNameTransactionMap
	 *            the methodNameTransactionMap to set
	 */
	public void setApiPackageLicenseNameMap(Map<String, String> apiPackageLicenseNameMap) {
		this.apiPackageLicenseNameMap = apiPackageLicenseNameMap;
	}
	
	/**
	 * @param apiPackageNoLicenseExceptionMap the apiPackageNoLicenseExceptionMap to set
	 */
	public void setApiPackageNoLicenseExceptionMap(
			Map<String, String> apiPackageNoLicenseExceptionMap) {
		this.apiPackageNoLicenseExceptionMap = apiPackageNoLicenseExceptionMap;
	}
	
	/** 
	 * @param licenseService the licenseService to set
	 */
	public void setLicenseService(LicensingService licenseService) {
		this.licenseService = licenseService;
	}

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		
		if(logger.isDebugEnabled()) {
			logger.debug("Entering before() of LicenseCheckInterceptor");
		}
		
		MessageContext context = MessageContext.getCurrentMessageContext();
		TransportHeaders headers = (TransportHeaders) context.getProperty(MessageContext.TRANSPORT_HEADERS);
		String user_agent = headers.get("user-agent");
		if (user_agent == null) {
			user_agent = "";
		}

		if (!UserAgentUtils.isVidyoEndpoint(user_agent)) {
			String apiPackage = arg0.getDeclaringClass().getPackage().getName();
			String lic = apiPackageLicenseNameMap.get(apiPackage);
			SystemLicenseFeature licensed = this.licenseService.getSystemLicenseFeature(lic);
			if (licensed == null || licensed.getLicensedValue().equalsIgnoreCase("false")) {
				
				try {
					String exceptionClassName = apiPackageNoLicenseExceptionMap.get(apiPackage);
					Class<?> exceptionClass = Class.forName(exceptionClassName);
					Constructor<?> exceptionClassConstructor = exceptionClass.getConstructor(String.class);
					Object exceptionObj = exceptionClassConstructor.newInstance("Operation is not licensed");

					throw (Exception)exceptionObj;
				} catch (IllegalAccessException iacEx) {
					logger.error("Error: " + iacEx.getMessage(), iacEx);
					throw new Exception("Internal error");
				} catch (IllegalArgumentException iarEx) {
					logger.error("Error: " + iarEx.getMessage(), iarEx);
					throw new Exception("Internal error");
				} catch (InvocationTargetException itEx) {
					logger.error("Error: " + itEx.getMessage(), itEx);
					throw new Exception("Internal error");
				} catch (ExceptionInInitializerError e) {
					logger.error("Error: " + e.getMessage(), e);
					throw new Exception("Internal error");
				}
			}
		}
		
	}

}

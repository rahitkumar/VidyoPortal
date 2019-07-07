/**
 * 
 */
package com.vidyo.interceptors.exception;

import com.vidyo.service.LicensingServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.dao.DataAccessException;

import com.vidyo.bo.SystemLicenseFeature;
import com.vidyo.framework.service.ServiceException;
import com.vidyo.service.LicensingService;
import com.vidyo.service.exceptions.OutOfPortsException;

/**
 * Method Interceptor which Intercepts all the configured methods in the service
 * layer and catches any RuntimeException raised by those methods. This class
 * determines whether to throw the Exception as it is with the complete trace or
 * suppress the trace and throw it as ServiceException. Special Case: If
 * OutofPortsException is thrown, the error message is decided based on the
 * License Version and thrown to the caller.
 * 
 * @author Ganesh
 * 
 */
public class ExceptionInterceptor implements MethodInterceptor {

	/**
	 * Logger
	 */
	/** Logger for this class and subclasses */
	protected final static Logger logger = LoggerFactory.getLogger(ExceptionInterceptor.class.getName());

	/**
	 * Boolean flag to throw/suppress the Exception Trace
	 */
	private boolean suppressTrace;

	/**
	 * 
	 */
	private LicensingService licensingService;

	/**
	 * 
	 */
	private String licenseVersion = null;

	/**
	 * @param suppressTrace
	 *            the suppressTrace to set
	 */
	public void setSuppressTrace(boolean suppressTrace) {
		this.suppressTrace = suppressTrace;
	}

	/**
	 * @param licensingService
	 *            the licensingService to set
	 */
	public void setLicensingService(LicensingService licensingService) {
		this.licensingService = licensingService;
	}

	/**
	 * Intercepts all the Service API methods, processes the uncaught Exception
	 * and rethrows them as ServiceException
	 */
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		logger.debug("Entering invoke() of ExceptionInterceptor");
		Object rval = null;
		try {
			rval = methodInvocation.proceed();
		} catch (Exception e) {
			// Handling Specific Exceptions here
			if (e instanceof OutOfPortsException) {
				String errorMessage = getErrorMessageForOutOfPortsException();
				logger.error("OutOfPortsException raised by the ServiceLayer-" + errorMessage);
				throw new OutOfPortsException(errorMessage);
			}

			if (suppressTrace) {
				// Catch all Service and DAO layer Exceptions here
				//logger.error("Exception in Service Layer", e);
				if (e instanceof DataAccessException
						|| e instanceof RuntimeException) {
					// If it is DataAccessException or RuntimeException,
					// suppress the trace and send
					// only a message
					logger.error("Exception Trace Suppress Flag is on, Throwing the Checked Exception to the caller", e);
					throw new ServiceException("Unable to process request");
				} else {
					logger.error("Exception Trace Suppress Flag is on, Throwing the Checked Exception to the caller", e);
					throw e;
				}

			} else {
				// Throw the Exception as it is
				logger.error("Exception Trace Suppress Flag is off, Throwing the trace", e);
				throw e;
			}
		}
		logger.debug("Exiting invoke() of ExceptionInterceptor");
		return rval;
	}

	/**
	 * Returns the License Version
	 * 
	 * @return
	 */
	public String getErrorMessageForOutOfPortsException() {

		if (licenseVersion == null) {
			try {
				SystemLicenseFeature f = licensingService
						.getSystemLicenseFeature("Version");
				if (f != null) {
					licenseVersion = f.getLicensedValue();
				}
			} catch (Exception e) {
				logger.error("Exception while retrieving the System License Version", e);
			}

		}

		String message = "All Lines in use, please try later";
		if (licenseVersion != null && licenseVersion.equalsIgnoreCase(LicensingServiceImpl.LIC_VERSION_20)) {
			message = "All Ports in use, please try later";
		}

		return message;

	}

}
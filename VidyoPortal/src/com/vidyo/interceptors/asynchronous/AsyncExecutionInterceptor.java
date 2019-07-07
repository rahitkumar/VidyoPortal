/**
 * 
 */
package com.vidyo.interceptors.asynchronous;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

/**
 * Intercepts the methods which are to be executed Asynchronously and uses the
 * thread pool to execute them in a new thread
 * 
 * @author Ganesh
 * 
 */
public class AsyncExecutionInterceptor implements MethodInterceptor {

	/**
	 * 
	 */
	private static final Logger logger = Logger
			.getLogger(AsyncExecutionInterceptor.class);

	/**
	 * 
	 */
	private ExecutorService executorService;

	/**
	 * @param executorService the executorService to set
	 */
	public void setExecutorService(ExecutorService executorService) {
		this.executorService = executorService;
	}

	/**
	 * 
	 */
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		logger.info("method intercepted ->"
				+ methodInvocation.getMethod().getName());
		Future<Object> future = executorService.submit(new AsyncCallable(
				methodInvocation));
		return null;
	}

	/**
	 * 
	 * @author Ganesh
	 * 
	 */
	class AsyncCallable implements Callable<Object> {

		/**
		 * 
		 */
		private MethodInvocation methodInvocation;

		/**
		 * 
		 * @param methodInvocation
		 */
		public AsyncCallable(MethodInvocation methodInvocation) {
			this.methodInvocation = methodInvocation;
		}

		/**
		 * @param methodInvocation
		 *            the methodInvocation to set
		 */
		public void setMethodInvocation(MethodInvocation methodInvocation) {
			this.methodInvocation = methodInvocation;
		}

		@Override
		public Object call() throws Exception {
			Object retVal = null;
			try {
				logger.info("method proceed ->");
				retVal = methodInvocation.proceed();
			} catch (Throwable e) {
				throw new Exception(e);
			}
			logger.info("method returned ->" + retVal);
			return retVal;
		}

	}

}

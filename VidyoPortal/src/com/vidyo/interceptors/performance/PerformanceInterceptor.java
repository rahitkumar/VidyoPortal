/**
 * 
 */
package com.vidyo.interceptors.performance;

import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ganesh
 * 
 */
public class PerformanceInterceptor implements MethodInterceptor {
	/**
	 * 
	 */
	protected static final Logger logger = LoggerFactory
			.getLogger(PerformanceInterceptor.class.getName());

	/**
	 * 
	 */
	private static ConcurrentHashMap<String, MethodStats> methodStats = new ConcurrentHashMap<String, MethodStats>();

	/**
	 * 
	 */
	private static long statLogFrequency = 10;

	/**
	 * 
	 */
	private static long methodWarningThreshold = 1000;

	/**
	 * 
	 */
	@Override
	public Object invoke(MethodInvocation method) throws Throwable {

		long start = System.currentTimeMillis();

		try {
			return method.proceed();
		} finally {
			updateStats(method.getMethod().getDeclaringClass().getName() + "."
					+ method.getMethod().getName(),
					(System.currentTimeMillis() - start));
		}
	}

	/**
	 * 
	 * @param methodName
	 * @param elapsedTime
	 */
	private void updateStats(String methodName, long elapsedTime) {

		MethodStats stats = methodStats.get(methodName);

		if (stats == null) {
			stats = new MethodStats(methodName);
			methodStats.put(methodName, stats);
		}

		stats.count++;
		stats.totalTime += elapsedTime;

		if (elapsedTime > stats.maxTime) {
			stats.maxTime = elapsedTime;
		}

		if (elapsedTime > methodWarningThreshold) {
			if (logger.isWarnEnabled()) {
				logger.warn("method warning: " + methodName + "(), cnt = "
						+ stats.count + ", lastTime = " + elapsedTime
						+ ", maxTime = " + stats.maxTime);
			}
		}

		if (stats.count % statLogFrequency == 0) {
			long avgTime = stats.totalTime / stats.count;
			long runningAvg = (stats.totalTime - stats.lastTotalTime)
					/ statLogFrequency;

			if (logger.isDebugEnabled()) {
				logger.debug("method: " + methodName + "(), cnt = "
						+ stats.count + ", lastTime = " + elapsedTime
						+ ", avgTime = " + avgTime + ", runningAvg = "
						+ runningAvg + ", maxTime = " + stats.maxTime);
			}
			// reset the last total time
			stats.lastTotalTime = stats.totalTime;
		}
	}

	/**
	 * 
	 * @author Ganesh
	 * 
	 */
	class MethodStats {

		/**
		 * 
		 */
		public String methodName;

		/**
		 * 
		 */
		public long count;

		/**
		 * 
		 */
		public long totalTime;

		/**
		 * 
		 */
		public long lastTotalTime;

		/**
		 * 
		 */
		public long maxTime;

		/**
		 * 
		 * @param methodName
		 */
		public MethodStats(String methodName) {
			this.methodName = methodName;
		}

	}
}
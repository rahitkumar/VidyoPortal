/**
 * 
 */
package com.vidyo.interceptors.performance;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Execution context of a single service call. Includes the detailed execution
 * trace as well as environment and performance data.
 * 
 * @author Ganesh
 * 
 */
public class ExecutionContext {

	/**
	 * 
	 */
	private static final Logger logger = LoggerFactory
			.getLogger(ExecutionContext.class);

	/**
	 * 
	 */
	private static Map<String, String> appLayers;

	static {
		appLayers = new HashMap<String, String>();
		appLayers.put("SERVICE-LAYER", null);
		appLayers.put("DAO-LAYER", "SERVICE-LAYER");
	}

	public ExecutionContext(String constructorFqn) {

		contextCreator = constructorFqn;
	}

	/**
	 * whether this context is closed or open
	 */
	private boolean contextClosed;

	/**
	 * 
	 */
	private int connectionsRequired = 0;

	/**
	 * 
	 */
	private int connectionCount = 0;

	/**
	 * The method to which this context belongs
	 */
	private String contextCreator;

	/**
	 * 
	 */
	private LinkedList connectionActivity = new LinkedList();

	/**
	 * 
	 */
	private LinkedHashSet connectionTracker = new LinkedHashSet();

	/**
	 * Map of method name to its list of detailed execution info objects. Note
	 * that one method can be invoked many times.
	 */
	private Map methodInvocationMap = new LinkedHashMap();

	/**
	 * list of method invocations in this context.
	 */
	private LinkedList methodInvocations = new LinkedList();

	/**
	 * counter to maintain the current level.
	 */
	private int level = 0;

	/**
	 * IP Address of the host.
	 */
	private String ipAddress;

	/**
	 * host name
	 */
	private String hostName;

	/**
	 * Total execution time
	 */
	private long totalExecutionTime = Long.MIN_VALUE;

}

/**
 * 
 */
package com.vidyo.framework.dao;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all database filter criteria.
 * 
 * @author Ganesh
 * 
 */
public class BaseQueryFilter {

	public static final int FILTER_MAX_LIMIT = 200;
	public static final int FILTER_DEFAULT_LIMIT = 40;
	
	/**
	 * 
	 */
	private static List<String> dirs = new ArrayList<String>();

	/**
	 * 
	 */
	static {
		dirs.add("ASC");
		dirs.add("DESC");
	}

	/**
	 * 
	 */
	private String dir;

	/**
	 * 
	 */
	private int start;

	/**
	 * 
	 */
	protected int limit;

	/**
	 * @return the dir
	 */
	public String getDir() {
		if (dir == null || !dirs.contains(dir.toUpperCase())) {
			return "ASC";
		}
		return dir;
	}

	/**
	 * @param dir
	 *            the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}

	/**
	 * @return the start
	 */
	public int getStart() {
		if(start <= 0) {
			return 0;
		}
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart(int start) {
		this.start = start;
	}

	/**
	 * @return the limit
	 */
	public int getLimit() {
		if(limit <= 0) {
			return 25;
		}
		return limit;
	}

	/**
	 * @param limit
	 *            the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}

}

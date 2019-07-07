package com.vidyo.bo;

import java.util.ArrayList;
import java.util.List;

import com.vidyo.framework.dao.BaseQueryFilter;

public class GroupFilter extends BaseQueryFilter {
	private String query = "";
	private String groupName = "";

	private String sort = "groupName";

	private static List<String> sortList = new ArrayList<String>();

	static {
		sortList.add("groupID");
		sortList.add("groupName");
		sortList.add("roomMaxUsers");
		sortList.add("userMaxBandWidthIn");
		sortList.add("userMaxBandWidthOut");
	}
	
	private String filter;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSort() {
		if (sort == null || !sortList.contains(sort)) {
			return "groupName";
		}
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

}

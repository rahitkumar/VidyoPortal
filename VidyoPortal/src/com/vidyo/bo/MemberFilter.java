package com.vidyo.bo;

import java.util.ArrayList;
import java.util.List;

import com.vidyo.framework.dao.BaseQueryFilter;

public class MemberFilter extends BaseQueryFilter {
	private String query = "";
	private String memberName = "";
	private String ext = "";
	private String type = "All";
	private String groupName = "";
	private String userStatus = "All";

	private String sort = "memberName";
	
    private String filter;
    
	private static List<String> sortList = new ArrayList<String>();

	static {
		sortList.add("memberID");
		sortList.add("memberName");
		sortList.add("username");
		sortList.add("roomExtNumber");
		sortList.add("roleName");
		sortList.add("groupName");
		sortList.add("memberCreated");
		sortList.add("enable");
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getSort() {
		if(sort == null || !sortList.contains(sort)) {
			return "memberName";
		}
		if(sort.equalsIgnoreCase("enable")) {
			return "active";
		}
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Override
	public int getLimit() {
		if(limit <= 0) {
			return 25;
		}
		
		return limit;
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

	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

}

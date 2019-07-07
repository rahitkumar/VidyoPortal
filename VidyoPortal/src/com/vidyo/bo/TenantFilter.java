package com.vidyo.bo;

import java.util.ArrayList;
import java.util.List;

import com.vidyo.framework.dao.BaseQueryFilter;

public class TenantFilter extends BaseQueryFilter {
	
	private static List<String> sortList = new ArrayList<String>();

	static {
		sortList.add("tenantName");
		sortList.add("tenantURL");
		sortList.add("description");
		sortList.add("tenantPrefix");
	}
	
    private String tenantName = "";
    private String tenantURL = "";
    
    private String filter;

    private String sort = "tenantName";
    
    public TenantFilter() {
        limit = 25;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getTenantURL() {
        return tenantURL;
    }

    public void setTenantURL(String tenantURL) {
        this.tenantURL = tenantURL;
    }
    
    public String getSortProperty() {
    	return sort;
    }

    public String getSort() {
		if(sort == null || !sortList.contains(sort)) {
			return "tenantName";
		}
		return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public int getLimit() {
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
}
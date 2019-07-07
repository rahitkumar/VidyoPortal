package com.vidyo.bo;

import java.util.ArrayList;
import java.util.List;

import com.vidyo.framework.dao.BaseQueryFilter;

public class EntityFilter extends BaseQueryFilter{
    private String query = "";
    private String entityType = "All";

    private String sortBy = "name";
    
    private static List<String> sortList = new ArrayList<String>();
    
    static {
    	sortList.add("name");
    	sortList.add("memberStatusText");
    	sortList.add("roomID");
    	sortList.add("ext");
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getSortBy() {
		if(sortBy == null || !sortList.contains(sortBy)) {
			return "name";
		}    	
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

}
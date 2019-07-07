package com.vidyo.bo;

import java.util.ArrayList;
import java.util.List;

import com.vidyo.framework.dao.BaseQueryFilter;

public class RecorderEndpointFilter extends BaseQueryFilter{
	
	private static List<String> sortList = new ArrayList<String>();

	static {
		sortList.add("prefix");
		sortList.add("status");
	}
	
    String sort = "prefix";
    String dir = "ASC";

    int limit = 25;
    int start = 0;

    public String getSort() {
		if(sort == null || !sortList.contains(sort)) {
			return "prefix";
		}
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

}
package com.vidyo.bo.gateway;

import com.vidyo.framework.dao.BaseQueryFilter;

import java.util.ArrayList;
import java.util.List;

public class GatewayPrefixFilter extends BaseQueryFilter {
	private static List<String> sortList = new ArrayList<String>();

	static {
		sortList.add("gatewayID");
		sortList.add("prefix");
		sortList.add("direction");
	}

	private String sort = "prefix";

	public GatewayPrefixFilter() {
		setDir("ASC");
		setLimit(10);
		setStart(0);
	}


	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		if (sort == null) {
			this.sort=  "prefix";
		} else if ("directionText".equals(sort)) {
			this.sort = "direction";
		} else if (!sortList.contains(sort)) {
			this.sort=  "prefix";
		} else {
			this.sort = sort;
		}
	}

}

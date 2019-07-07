package com.vidyo.bo;

import com.vidyo.framework.dao.BaseQueryFilter;

public class ServiceFilter extends BaseQueryFilter {
    private String serviceName = "";
    private String type = "";

    private String sort = "serviceName";

    public ServiceFilter() {
        limit = 25;
    }
    
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }


    @Override
    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
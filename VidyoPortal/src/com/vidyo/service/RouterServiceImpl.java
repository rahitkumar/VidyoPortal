package com.vidyo.service;

import com.vidyo.bo.Router;
import com.vidyo.db.IRouterDao;
import com.vidyo.framework.context.TenantContext;

import java.util.List;

public class RouterServiceImpl implements IRouterService {

    private IRouterDao dao;

    public void setDao(IRouterDao dao) {
        this.dao = dao;
    }

    public List<Router> getRouters() {
        List<Router> list = this.dao.getRouters(TenantContext.getTenantId());
        return list;
    }

    public Long getCountRouters() {
        Long number = this.dao.getCountRouters(TenantContext.getTenantId());
        return number;
    }

    public Router getRouter(int routerID) {
        Router router = this.dao.getRouter(TenantContext.getTenantId(), routerID);
        return router;
    }

    public Router getRouterByName(String routerName) {
        Router router = this.dao.getRouterByName(TenantContext.getTenantId(), routerName);
        return router;
    }

    public boolean isRouterExistForRouterName(String routerName, int routerID) {
        boolean rc = this.dao.isRouterExistForRouterName(TenantContext.getTenantId(), routerName, routerID);
        return rc;
    }

    public boolean replaceRouter(String toBeDeleteServiceName, String replacementServiceName){
        boolean rc = this.dao.replaceRouter(TenantContext.getTenantId(), toBeDeleteServiceName, replacementServiceName);
        return rc;
    }
}
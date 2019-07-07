package com.vidyo.db;

import com.vidyo.bo.Router;

import java.util.List;

public interface IRouterDao {
    public List<Router> getRouters(int tenant);
    public Long getCountRouters(int tenant);
    public Router getRouter(int tenant, int routerID);
    public Router getRouterByName(int tenant, String routerName);
    public boolean isRouterExistForRouterName(int tenant, String routerName, int routerID);
    
    public boolean replaceRouter(int tenant, String toBeDeleteServiceName, String replacementServiceName);
}

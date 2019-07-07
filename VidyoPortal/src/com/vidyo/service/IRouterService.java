package com.vidyo.service;

import com.vidyo.bo.Router;

import java.util.List;

public interface IRouterService {
    public List<Router> getRouters();
    public Long getCountRouters();
    public Router getRouter(int routerID);
    public Router getRouterByName(String routerName);
    public boolean isRouterExistForRouterName(String routerName, int routerID);
    
    public boolean replaceRouter(String toBeDeleteServiceName, String replacementServiceName);
}

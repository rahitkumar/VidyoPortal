package com.vidyo.bo;

import java.io.Serializable;

public class Router implements Serializable {
    int routerID;
    String routerName;
    String description;
    int active = 1; // active by default

    public int getRouterID() {
        return routerID;
    }

    public void setRouterID(int routerID) {
        this.routerID = routerID;
    }

    public String getRouterName() {
        return routerName;
    }

    public void setRouterName(String routerName) {
        this.routerName = routerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
package com.vidyo.bo;

import java.io.Serializable;

public class DBProperties implements Serializable {

    String hostport = "localhost:3306";
    String username = "vidyo";
    String password = "v1dy03x";

    public String getHostport() {
        return hostport;
    }

    public void setHostport(String hostport) {
        this.hostport = hostport;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
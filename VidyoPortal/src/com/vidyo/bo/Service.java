package com.vidyo.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Service implements Serializable {

	private static final long serialVersionUID = 1L;
	
	int serviceID;
	int roleID;
	String roleName;
	String serviceName;
	String user;
	String password;
	String url;
	String serviceEndpointGuid;
	String token;
	String serviceRef; // e.g. gatewayID

	public int getServiceID() {
		return serviceID;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
				.toString();
	}

	public void setServiceID(int serviceID) {
		this.serviceID = serviceID;
	}

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getServiceRef() {
		return serviceRef;
	}

	public void setServiceRef(String serviceRef) {
		this.serviceRef = serviceRef;
	}

	public String getServiceEndpointGuid() {
		return serviceEndpointGuid;
	}

	public void setServiceEndpointGuid(String serviceEndpointGuid) {
		this.serviceEndpointGuid = serviceEndpointGuid;
	}
}
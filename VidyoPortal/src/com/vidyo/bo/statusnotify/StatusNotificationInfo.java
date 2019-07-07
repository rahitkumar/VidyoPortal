package com.vidyo.bo.statusnotify;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class StatusNotificationInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7869671058200568313L;
	
	String UserName;
    String TenantName;
    String UserType;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getTenantName() {
        return TenantName;
    }

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	public void setTenantName(String tenantName) {
        TenantName = tenantName;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
}
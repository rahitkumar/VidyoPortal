package com.vidyo.framework.security.authentication;

import java.util.Arrays;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;

public class VidyoUserGuestDetails extends User implements org.springframework.security.core.userdetails.UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4817668649753900205L;
	
	private String bak;
    boolean allowedToParticipate;
    
	private int memberId;
	
	private String memberName;
	
	private int tenantID;
	
	private String pak;
	
	private String pak2;
    
    /**
	 * @return the pak
	 */
	public String getPak() {
		return pak;
	}

	/**
	 * @param pak the pak to set
	 */
	public void setPak(String pak) {
		this.pak = pak;
	}

	/**
	 * @return the pak2
	 */
	public String getPak2() {
		return pak2;
	}

	/**
	 * @param pak2 the pak2 to set
	 */
	public void setPak2(String pak2) {
		this.pak2 = pak2;
	}

	/**
	 * @return the memberId
	 */
	public int getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId the memberId to set
	 */
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberName
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName the memberName to set
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	/**
	 * @return the tenantID
	 */
	public int getTenantID() {
		return tenantID;
	}

	/**
	 * @param tenantID the tenantID to set
	 */
	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}

	public String getBak() {
        return bak;
    }

    public boolean isAllowedToParticipate(){
    	return allowedToParticipate;
    }
    
    public VidyoUserGuestDetails(String username, String password, String pak, String pak2, String bak, boolean enabled, boolean allowedToParticipate, GrantedAuthority[] authorities)
            throws IllegalArgumentException {
        super(username, password, enabled, true, true, true, Arrays.asList(authorities));
        this.pak = pak;
        this.pak2 = pak2;
        this.bak = bak;
        this.allowedToParticipate = allowedToParticipate;
    }

    public boolean equals(Object rhs) {
        if (!(rhs instanceof VidyoUserGuestDetails) || (rhs == null)) {
            return false;
        }

        VidyoUserGuestDetails user = (VidyoUserGuestDetails) rhs;

        // We rely on constructor to guarantee any User has non-null and >0
        // authorities
        if (user.getAuthorities().size() != this.getAuthorities().size()) {
            return false;
        }

        if(!this.getAuthorities().containsAll(user.getAuthorities())) {
        	return false;
        }

        // We rely on constructor to guarantee non-null username and password
        return ((this.getPassword().equals(user.getPassword()) || this.getBak().equals(user.getBak()))
                && this.getUsername().equals(user.getUsername())
                && (this.isAccountNonExpired() == user.isAccountNonExpired())
                && (this.isAccountNonLocked() == user.isAccountNonLocked())
                && (this.isCredentialsNonExpired() == user.isCredentialsNonExpired())
                && (this.isEnabled() == user.isEnabled()));
    }

    public int hashCode() {
        int code = 9792;

        if (this.getAuthorities() != null) {
        	for(GrantedAuthority grantedAuthority : this.getAuthorities()) {
        		code = code * (grantedAuthority.hashCode() % 7);
        	}
        }

        if (this.getBak() != null) {
            code = code * (this.getBak().hashCode() % 7);
        }

        if (this.getPassword() != null) {
            code = code * (this.getPassword().hashCode() % 7);
        }

        if (this.getUsername() != null) {
            code = code * (this.getUsername().hashCode() % 7);
        }

        if (this.isAccountNonExpired()) {
            code = code * -2;
        }

        if (this.isAccountNonLocked()) {
            code = code * -3;
        }

        if (this.isCredentialsNonExpired()) {
            code = code * -5;
        }

        if (this.isEnabled()) {
            code = code * -7;
        }

        return code;
    }

}

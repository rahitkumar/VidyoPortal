/**
 * 
 */
package com.vidyo.bo.member;

import com.vidyo.bo.Entity;

/**
 * @author Ganesh
 *
 */
public class MemberEntity extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String memberName;
	
	/**
	 * 
	 */
	private String roleName;
	
	/**
	 * 
	 */
	private String pak;
	
	/**
	 * 
	 */
	private String pak2;

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
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

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

}

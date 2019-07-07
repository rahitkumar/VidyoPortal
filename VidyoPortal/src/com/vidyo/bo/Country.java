/**
 * 
 */
package com.vidyo.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This table will going to contain the countries list supported by portal for dial in numbers. 
 * @author ysakurikar
 *
 */
@Entity
@Table (name="country")
public class Country implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int countryID;
	
	private String iso;
	private String name;
	
	@Column (name="nick_name")
	private String nickName;
	
	@Column (name = "iso_3")
	private String countryCodeLong;
	
	@Column (name="num_code")
	private String numCode;
	
	@Column (name="phone_code")
	private int phoneCode;
	
	@Column (name="flag_file_name")
	private String flagFileName;
	
	private int active;
	/**
	 * @return the countryID
	 */
	public int getCountryID() {
		return countryID;
	}
	/**
	 * @param countryID the countryID to set
	 */
	public void setCountryID(int countryID) {
		this.countryID = countryID;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the iso
	 */
	public String getIso() {
		return iso;
	}
	/**
	 * @param iso the iso to set
	 */
	public void setIso(String iso) {
		this.iso = iso;
	}
	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}
	/**
	 * @param niceName the niceName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * @return the numCode
	 */
	public String getNumCode() {
		return numCode;
	}
	/**
	 * @param numCode the numCode to set
	 */
	public void setNumCode(String numCode) {
		this.numCode = numCode;
	}
	/**
	 * @return the phoneCode
	 */
	public int getPhoneCode() {
		return phoneCode;
	}
	/**
	 * @param phoneCode the phoneCode to set
	 */
	public void setPhoneCode(int phoneCode) {
		this.phoneCode = phoneCode;
	}
	/**
	 * @return the active
	 */
	public int getActive() {
		return active;
	}
	/**
	 * @param active the active to set
	 */
	public void setActive(int active) {
		this.active = active;
	}
	/**
	 * @return the countryCodeLong
	 */
	public String getCountryCodeLong() {
		return countryCodeLong;
	}
	/**
	 * @param countryCodeLong the countryCodeLong to set
	 */
	public void setCountryCodeLong(String countryCodeLong) {
		this.countryCodeLong = countryCodeLong;
	}
	/**
	 * @return the flagFileName
	 */
	public String getFlagFileName() {
		return flagFileName;
	}
	/**
	 * @param flagFileName the flagFileName to set
	 */
	public void setFlagFileName(String flagFileName) {
		this.flagFileName = flagFileName;
	}
	/**
	 * 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + countryID;
		return result;
	}
	/**
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Country)) {
			return false;
		}
		Country other = (Country) obj;
		if (countryID != other.countryID) {
			return false;
		}
		return true;
	}
	
	
}

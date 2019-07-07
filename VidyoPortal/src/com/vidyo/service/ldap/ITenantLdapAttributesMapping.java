package com.vidyo.service.ldap;

import com.vidyo.bo.ldap.TenantLdapAttributeMapping;
import com.vidyo.bo.ldap.TenantLdapAttributeValueMapping;

import java.util.List;

public interface ITenantLdapAttributesMapping {
	/**
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public List<TenantLdapAttributeMapping> getTenantLdapAttributeMapping();
	public List<TenantLdapAttributeMapping> getTenantLdapAttributeMapping(int tenantID);
	public TenantLdapAttributeMapping getTenantLdapAttributeMappingRecord(int tenantID, int mappingID);
	/**
	 * @deprecated
	 * @param vidyoAttributeName
	 * @param ldapAttributeName
	 * @param defaultAttributeValue
	 * @return
	 */
	@Deprecated
	public int saveTenantLdapAttributeMappingRecord(String vidyoAttributeName, String ldapAttributeName, String defaultAttributeValue);
	public int saveTenantLdapAttributeMappingRecord(int tenantID, String vidyoAttributeName, String ldapAttributeName, String defaultAttributeValue);
	public int updateTenantLdapAttributeMappingRecord(int tenantID, TenantLdapAttributeMapping record);

	List<TenantLdapAttributeValueMapping> getTenantLdapAttributeValueMapping(int tenantID, int mappingID);
	List<TenantLdapAttributeValueMapping> getTenantLdapAttributeValueMappingForEdit(int tenantID, int mappingID);
	public int saveTenantLdapAttributeValueMappingRecord(int tenantID, int valueID, int mappingID, String vidyoValueName, String ldapValueName);
	public int saveTenantLdapAttributeValueMappingRecord(int tenantID, TenantLdapAttributeValueMapping mapping);
	public int removeTenantLdapAttributeValueMappingRecord(int tenantID, int valueID);
}
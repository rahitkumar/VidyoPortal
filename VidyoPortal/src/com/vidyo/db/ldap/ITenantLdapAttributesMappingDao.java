package com.vidyo.db.ldap;

import com.vidyo.bo.ldap.TenantLdapAttributeMapping;
import com.vidyo.bo.ldap.TenantLdapAttributeValueMapping;

import java.util.List;

public interface ITenantLdapAttributesMappingDao {
	public List<TenantLdapAttributeMapping> getTenantLdapAttributeMapping(int tenantID);
	public TenantLdapAttributeMapping getTenantLdapAttributeMappingRecord(int tenantID, int mappingID);
	public int saveTenantLdapAttributeMappingRecord(int tenantID, String vidyoAttributeName, String ldapAttributeName, String defaultAttributeValue);
	public int updateTenantLdapAttributeMappingRecord(int tenantID, TenantLdapAttributeMapping record);
	public int updateTenantLdapAttributeMappingDefaultAttributeValue(int tenantID, String vidyoAttributeName, 
			String oldDefaultAttributeValue, String newDefaultAttributeValue);

	public List<TenantLdapAttributeValueMapping> getTenantLdapAttributeValueMapping(int tenantID, int mappingID);
	public int saveTenantLdapAttributeValueMappingRecord(int tenantID, int valueID, int mappingID, String vidyoValueName, String ldapValueName);
	public int removeTenantLdapAttributeValueMappingRecord(int tenantID, int valueID);
	public int updateTenantLdapAttributeValueMappingVidyoValueName(int tenantID, String vidyoAttributeName, 
			String oldVidyoValueName, String newVidyoValueName);
	public int removeTenantLdapAttributeValueMappingRecords(int tenantID, String vidyoAttributeName, String vidyoValueName);
	
	public int removeAllTenantLdapMappingAttributesWithValues(int tenantID);
}
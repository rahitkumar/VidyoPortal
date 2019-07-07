package com.vidyo.db.idp;

import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;

import java.util.List;

public interface ITenantIdpAttributesMappingDao {
	public List<TenantIdpAttributeMapping> getTenantIdpAttributeMapping(int tenantID);
	public TenantIdpAttributeMapping getTenantIdpAttributeMappingRecord(int tenantID, int mappingID);
	public int saveTenantIdpAttributeMappingRecord(int tenantID, String vidyoAttributeName, String idpAttributeName, String defaultAttributeValue);
	public int updateTenantIdpAttributeMappingRecord(int tenantID, TenantIdpAttributeMapping record);
	
	public List<TenantIdpAttributeValueMapping> getTenantIdpAttributeValueMapping(int tenantID, int mappingID);
	public int saveTenantIdpAttributeValueMappingRecord(int tenantID, int valueID, int mappingID, String vidyoValueName, String idpValueName);
	public int removeTenantIdpAttributeValueMappingRecord(int tenantID, int valueID);
	
	public int updateTenantIdpAttributeMappingDefaultAttributeValue(int tenantID, String vidyoAttributeName,
			String oldDefaultAttributeValue, String newDefaultAttributeValue);
	public int updateTenantIdpAttributeValueMappingVidyoValueName(int tenantID, String vidyoAttributeName, 
			String oldVidyoValueName, String newVidyoValueName);
	public int removeTenantIdpAttributeValueMappingRecords(int tenantID, String vidyoAttributeName, String vidyoValueName);
	
	public int removeAllTenantIdpMappingAttributesWithValues(int tenantID);
	public int updateTenantIdpAttributeValueMappingRecord(int tenantID, int valueID, int mappingID,
			String vidyoValueName, String idpValueName);
}
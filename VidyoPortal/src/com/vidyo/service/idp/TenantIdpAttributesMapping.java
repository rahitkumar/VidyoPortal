package com.vidyo.service.idp;

import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;

import java.util.List;

public interface TenantIdpAttributesMapping {
	/**
	 * @deprecated
	 * @return
	 */
	@Deprecated
	public List<TenantIdpAttributeMapping> getTenantIdpAttributeMapping();
	public List<TenantIdpAttributeMapping> getTenantIdpAttributeMapping(int tenantID);
	public TenantIdpAttributeMapping getTenantIdpAttributeMappingRecord(int tenantID, int mappingID);
	public int saveTenantIdpAttributeMappingRecord(int tenantID, String vidyoAttributeName, String idpAttributeName, String defaultAttributeValue);
	public int updateTenantIdpAttributeMappingRecord(int tenantId, TenantIdpAttributeMapping record);
	
	List<TenantIdpAttributeValueMapping> getTenantIdpAttributeValueMapping(int tenantID, int mappingID);
	List<TenantIdpAttributeValueMapping> getTenantIdpAttributeValueMappingForEdit(int tenantID, int mappingID);
	public int saveTenantIdpAttributeValueMappingRecord(int tenantID, int valueID, int mappingID, String vidyoValueName, String idpValueName);
	public int updateTenantIdpAttributeValueMappingRecord(int tenantID, int valueID, int mappingID, String vidyoValueName, String idpValueName);
	public int saveTenantIdpAttributeValueMappingRecord(int tenantID, TenantIdpAttributeValueMapping mapping);
	public int removeTenantIdpAttributeValueMappingRecord(int tenantID, int valueID);
}
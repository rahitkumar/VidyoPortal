package com.vidyo.service.idp;

import java.util.List;

import com.vidyo.bo.Member;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;

import org.opensaml.saml2.core.Attribute;

public interface IdpUserToMemberAttributesMapper {
	public Member getMemberFromAttributes(int tenantID, List<Attribute> attributes);
	public String getIdpAttributeValue(List<Attribute> attributes, TenantIdpAttributeMapping attributeMapping);
}
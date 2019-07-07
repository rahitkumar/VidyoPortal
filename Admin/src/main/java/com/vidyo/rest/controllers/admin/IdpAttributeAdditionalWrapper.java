package com.vidyo.rest.controllers.admin;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;

public class IdpAttributeAdditionalWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3282134325233856868L;
	@Size(max=100)
	private List<TenantIdpAttributeValueMapping>  idpvaluemappinglist;
	public List<TenantIdpAttributeValueMapping> getIdpvaluemappinglist() {
		return idpvaluemappinglist;
	}
	public void setIdpvaluemappinglist(List<TenantIdpAttributeValueMapping> idpvaluemappinglist) {
		this.idpvaluemappinglist = idpvaluemappinglist;
	}
  
}

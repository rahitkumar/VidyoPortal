package com.vidyo.rest.controllers.admin;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.Size;

import com.vidyo.bo.idp.TenantIdpAttributeMapping;

public class IdpAttributeWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3282134325233856868L;
	@Size(max=100)
	private List<TenantIdpAttributeMapping>  idpmappinglist;
	public List<TenantIdpAttributeMapping> getIdpmappinglist() {
		return idpmappinglist;
	}
	public void setIdpmappinglist(List<TenantIdpAttributeMapping> idpmappinglist) {
		this.idpmappinglist = idpmappinglist;
	}
	
	


	

  
}

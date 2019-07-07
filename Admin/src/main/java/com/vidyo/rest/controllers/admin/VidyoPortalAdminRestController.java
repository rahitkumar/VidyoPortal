package com.vidyo.rest.controllers.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.core.Context;

import org.apache.commons.validator.routines.UrlValidator;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.vidyo.bo.MemberRoles;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.bo.authentication.SamlProvisionType;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.bo.idp.TenantIdpAttributeValueMapping;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.htmlcleaner.AntiSamyHtmlCleaner;
import com.vidyo.rest.base.RestResponse;
import com.vidyo.rest.base.RestService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.idp.TenantIdpAttributesMapping;
import com.vidyo.utils.PortalUtils;

@RestController
@RequestMapping(consumes = { "application/json", "application/xml" }, produces = { "application/json",
		"application/xml" })
public class VidyoPortalAdminRestController extends RestService {
	protected final Logger logger = LoggerFactory.getLogger((String) VidyoPortalAdminRestController.class.getName());

	@Autowired
	private ISystemService system;
	@Autowired
	private IMemberService service;
	@Autowired
	private ITenantService tenantService;
	@Autowired
	private TenantIdpAttributesMapping idpAttributesMapping;;

	/**
	 * A simple api return back the saml metadata object. Useful for user to get
	 * know the saml metadata object.
	 * 
	 * @return SamlMetadata
	 */
	@RequestMapping(value = { "/service/samlMetadata" }, method = { RequestMethod.GET })
	@ResponseBody
	public RestResponse<SamlMetadata> getSamlMetadata() {

		AuthenticationConfig authenticationConfig = system.getAuthenticationConfig(TenantContext.getTenantId());
		SamlMetadata samlMetadata = new SamlMetadata();
		samlMetadata.setBase64EncodedMetadata(PortalUtils.encodeBase64(authenticationConfig.getSamlIdpMetadata()));
		samlMetadata.setEntityId(authenticationConfig.getSamlSpEntityId());
		samlMetadata.setSecurityProfile(authenticationConfig.getSamlSecurityProfile());
		samlMetadata.setSslSecurityProfile(authenticationConfig.getSamlSSLProfile());
		samlMetadata.setSignMetadata(authenticationConfig.getSamlSignMetadata() == "YES" ? true : false);
		RestResponse<SamlMetadata> response = new RestResponse<SamlMetadata>();
		response.setStatus("200", "OK");
		response.setData(samlMetadata);
		return response;
	}

	@RequestMapping(value = "/service/samlMetadata", method = RequestMethod.POST)
	public @ResponseBody RestResponse<SamlMetadata> setupSaml(@Context HttpServletRequest request,
			@Valid @RequestBody SamlMetadata samlObj) {
		RestResponse<SamlMetadata> response = new RestResponse<SamlMetadata>();
		AuthenticationConfig config=system.getAuthenticationConfig(TenantContext.getTenantId());
		if(config.isSamlflag()){
		
			response.setStatus("405", "saml is already configured. Please use PUT method.");
			return response;
		}
		if(!("PKIX".equalsIgnoreCase(samlObj.getSecurityProfile().toUpperCase())|| "METAIOP".equalsIgnoreCase(samlObj.getSecurityProfile().toUpperCase()))){
			response.setStatus("400", "SecurityProfile must be PKIX or METAIOP");
			return response;
		}
		if(!("PKIX".equalsIgnoreCase(samlObj.getSslSecurityProfile().toUpperCase())|| "METAIOP".equalsIgnoreCase(samlObj.getSslSecurityProfile().toUpperCase()))){
			response.setStatus("400", "SslSecurityProfile must be PKIX or METAIOP");
			return response;
		}
		AuthenticationConfig authenticationConfig = new AuthenticationConfig();

		authenticationConfig.setSamlflag(true);
		// currently we support only SAML provision type
		authenticationConfig.setSamlAuthProvisionType(SamlProvisionType.SAML);
		authenticationConfig.setSamlIdpMetadata(PortalUtils.decodeBase64(samlObj.getBase64EncodedMetadata()));
		authenticationConfig.setSamlSignMetadata(samlObj.isSignMetadata() ? "YES" : "NO");
		authenticationConfig.setSamlSpEntityId(samlObj.getEntityId());
		authenticationConfig.setHttpsRequest(request.getScheme() == "https");
		authenticationConfig.setSamlSSLProfile(samlObj.getSslSecurityProfile().toUpperCase());
		authenticationConfig.setSamlSecurityProfile(samlObj.getSecurityProfile().toUpperCase());
		String baseURL = request.getScheme() + "://" + request.getServerName();
		int result = system.saveAuthenticationConfig(TenantContext.getTenantId(), authenticationConfig, baseURL);
		throwErrorValidationError(result);
		response.setStatus("200", "OK");
		response.setData(samlObj);
		return response;
	}

	private void throwErrorValidationError(int result) {
		switch (result) {
		case -1:
			throw new RuntimeException("Failed to save");

		case -2:
			throw new RuntimeException("Saml identity provider metadata xml is invalid");
		case -3:
			throw new RuntimeException("IDP attribute for user name is not defined");
		case -4:
			throw new RuntimeException("entity id is not unique");
		}

	}

	@RequestMapping(value = "/service/samlMetadata", method = RequestMethod.PUT)
	public @ResponseBody RestResponse<SamlMetadata> updateSaml(@Context HttpServletRequest request,
			@Valid @RequestBody SamlMetadata samlObj) {
		RestResponse<SamlMetadata> response = new RestResponse<SamlMetadata>();
		AuthenticationConfig authenticationConfig=system.getAuthenticationConfig(TenantContext.getTenantId());
		if(!authenticationConfig.isSamlflag()){
		
			response.setStatus("405", "saml is not configured. Please use POST method.");
			return response;
		}
		
		if(!("PKIX".equalsIgnoreCase(samlObj.getSecurityProfile().toUpperCase())|| "METAIOP".equalsIgnoreCase(samlObj.getSecurityProfile().toUpperCase()))){
			response.setStatus("400", "SecurityProfile must be PKIX or METAIOP");
			return response;
		}
		if(!("PKIX".equalsIgnoreCase(samlObj.getSslSecurityProfile().toUpperCase())|| "METAIOP".equalsIgnoreCase(samlObj.getSslSecurityProfile().toUpperCase()))){
			response.setStatus("400", "SSLSecurityProfile must be PKIX or METAIOP");
			return response;
		}
		//purposefully doing --or we need to reset values since object is retrieved from db.
		authenticationConfig=new AuthenticationConfig();
		authenticationConfig.setSamlflag(true);
		// currently we support only SAML provision type
		authenticationConfig.setSamlAuthProvisionType(SamlProvisionType.SAML);
		//TODO this has to escape from xss..
		authenticationConfig.setSamlIdpMetadata(PortalUtils.decodeBase64(samlObj.getBase64EncodedMetadata()));
		authenticationConfig.setSamlSignMetadata(samlObj.isSignMetadata() ? "YES" : "NO");
		authenticationConfig.setSamlSpEntityId(samlObj.getEntityId());
		authenticationConfig.setHttpsRequest(request.getScheme() == "https");
		authenticationConfig.setSamlSSLProfile(samlObj.getSslSecurityProfile().toUpperCase());
		authenticationConfig.setSamlSecurityProfile(samlObj.getSecurityProfile().toUpperCase());
		String baseURL = request.getScheme() + "://" + request.getServerName();
		int result = system.saveAuthenticationConfig(TenantContext.getTenantId(), authenticationConfig, baseURL);
		throwErrorValidationError(result);
		response.setStatus("200", "OK");
		response.setData(samlObj);
		return response;
	}

	@RequestMapping(value = "/service/samlAttributeMappings", method = RequestMethod.GET)
	public @ResponseBody RestResponse<IdpAttributeWrapper> getSamlAttributeMappings() {
		Integer tenantID = TenantContext.getTenantId();
		List<TenantIdpAttributeMapping> list = idpAttributesMapping.getTenantIdpAttributeMapping(tenantID);
		TenantConfiguration configuration = tenantService.getTenantConfiguration(tenantID);
		if (configuration.getUserImage() != 1) {
			ListIterator<TenantIdpAttributeMapping> iter = list.listIterator();
			while (iter.hasNext()) {
				if (iter.next().getVidyoAttributeName().equalsIgnoreCase("Thumbnail Photo")) {
					iter.remove();
				}
			}
		}
		IdpAttributeWrapper attributeWrapper = new IdpAttributeWrapper();
		attributeWrapper.setIdpmappinglist(list);
		RestResponse<IdpAttributeWrapper> response = new RestResponse<IdpAttributeWrapper>();
		response.setStatus("200", "OK");
		response.setData(attributeWrapper);
		return response;
	}

	/**
	 * 
	 * @param request
	 * @param attributeWrapper
	 * @return
	 */
	@RequestMapping(value = "/service/samlAttributeMappings", method = RequestMethod.PUT)
	public @ResponseBody RestResponse<IdpAttributeWrapper> updateSamlAttributeMappings(
			@Context HttpServletRequest request, @Valid @RequestBody IdpAttributeWrapper attributeWrapper) {
		List<TenantIdpAttributeMapping> requestlist = attributeWrapper.getIdpmappinglist();
		List<TenantIdpAttributeMapping> responseList = new ArrayList<TenantIdpAttributeMapping>();
        if(requestlist==null){
        	throw new RuntimeException("Invalid data");
        }
		ListIterator<TenantIdpAttributeMapping> iter = requestlist.listIterator();
		int i = 0;
		TenantIdpAttributeMapping tmpIdpAttribute = null;
		while (iter.hasNext()) {
			tmpIdpAttribute = iter.next();
			if(!IsValidObject(tmpIdpAttribute)){
				continue;//if object is not valid , we are skipping the update for the object...
			}
		
			tmpIdpAttribute.setIdpAttributeName(tmpIdpAttribute.getIdpAttributeName());
			tmpIdpAttribute.setDefaultAttributeValue(tmpIdpAttribute.getDefaultAttributeValue());
			
			i = idpAttributesMapping.updateTenantIdpAttributeMappingRecord(TenantContext.getTenantId(),
					tmpIdpAttribute);
			logger.error("return the status from db" + i);
			if (i == 0) {
				// if any error happened ,printing it and not return back to
				// client so user can understand it is not saved
				logger.error("Failed to update/create tenantIDP attribute for id " + tmpIdpAttribute.getMappingID()
						+ "attribute name " + tmpIdpAttribute.getIdpAttributeName() + "VidyoAttributeName "
						+ tmpIdpAttribute.getVidyoAttributeName() + " Idp Attribute Name "
						+ tmpIdpAttribute.getIdpAttributeName());
			} else {
				responseList.add(tmpIdpAttribute);
			}

		}
		attributeWrapper.setIdpmappinglist(responseList);
		RestResponse<IdpAttributeWrapper> response = new RestResponse<IdpAttributeWrapper>();
		response.setStatus("200", "OK");
		response.setData(attributeWrapper);
		return response;
	}

	private boolean IsValidObject(TenantIdpAttributeMapping tmpIdpAttribute) {
		if(tmpIdpAttribute.getIdpAttributeName()==null){
			logger.error("IdpAttributeName shouldn't be null");
			return false;
		}
		if(tmpIdpAttribute.getDefaultAttributeValue()==null){
			logger.error("DefaultAttributeValue shouldn't be null");
			return false;
		}
		if(tmpIdpAttribute.getVidyoAttributeName()==null){
			logger.error("VidyoAttributeName shouldn't be null");
			return false;
		}
		return true;
	}

	@RequestMapping(value = "/service/samlAdditionalAttributeMappings", method = RequestMethod.PUT)
	public @ResponseBody RestResponse<IdpAttributeAdditionalWrapper> updateSamlAdditionalAttributeMappings(
			@Context HttpServletRequest request, @Valid @RequestBody IdpAttributeAdditionalWrapper attributeWrapper) {
		List<TenantIdpAttributeValueMapping> requestlist = attributeWrapper.getIdpvaluemappinglist();
		List<TenantIdpAttributeValueMapping> responseList = new ArrayList<TenantIdpAttributeValueMapping>();
		if(requestlist==null){
	        	throw new RuntimeException("Invalid data");
	    }
		ListIterator<TenantIdpAttributeValueMapping> iter = requestlist.listIterator();
		int i = 0;
		TenantIdpAttributeValueMapping tmpIdpAttribute = null;
		while (iter.hasNext()) {
			tmpIdpAttribute = iter.next();
			
			tmpIdpAttribute.setIdpValueName(tmpIdpAttribute.getIdpValueName());
			
			i = idpAttributesMapping.updateTenantIdpAttributeValueMappingRecord(TenantContext.getTenantId(),
					tmpIdpAttribute.getValueID(), tmpIdpAttribute.getMappingID(), tmpIdpAttribute.getVidyoValueName(), tmpIdpAttribute.getIdpValueName());
			logger.error("return the status from db" + i);
			if (i == 0) {
				// if any error happened ,printing it and not return back to
				// client so user can understand it is not saved
				logger.error("Failed to update/create tenantIDP attribute for id " + tmpIdpAttribute.getMappingID()
						+ "attribute name " + tmpIdpAttribute.getIdpValueName() + "VidyoAttributeName "
						+ tmpIdpAttribute.getVidyoValueName());
			} else {
				responseList.add(tmpIdpAttribute);
			}

		}
		attributeWrapper.setIdpvaluemappinglist(responseList);
		RestResponse<IdpAttributeAdditionalWrapper> response = new RestResponse<IdpAttributeAdditionalWrapper>();
		response.setStatus("200", "OK");
		response.setData(attributeWrapper);
		return response;
	}


	/**
	 * Method will return additional mapping values for Group, User Type etc. The portal logic is defined as if no values exist, portal will add the necessary values to the database.This is the way it is implemented. This method re-use 
	 * existing implementation.
	 * @param mappingID
	 * @return
	 */
	
	@RequestMapping(value = "/service/samlAdditionalAttributeMappings/{mappingID}", method = RequestMethod.GET)
	public @ResponseBody RestResponse<IdpAttributeAdditionalWrapper> getSamlAdditionalAttributeMappings(@PathVariable  int mappingID) {
		RestResponse<IdpAttributeAdditionalWrapper> response = new RestResponse<IdpAttributeAdditionalWrapper>();
		Integer tenantId = TenantContext.getTenantId();
		List<TenantIdpAttributeValueMapping> list = this.idpAttributesMapping
				.getTenantIdpAttributeValueMappingForEdit(tenantId, mappingID);
		if (!list.isEmpty()) {
			IdpAttributeAdditionalWrapper attributeWrapper = new IdpAttributeAdditionalWrapper();
			attributeWrapper.setIdpvaluemappinglist(list);

			response.setStatus("200", "OK");
			response.setData(attributeWrapper);

		} else {

			response.setStatus("404", "Resource not found");

		}
		return response;
	}

	@RequestMapping(value = "/service/samlAdditionalAttributeMappings/{valueID}", method = RequestMethod.DELETE)
	public @ResponseBody RestResponse  deleteSamlAdditionalAttributeMappings(
			@PathVariable  int valueID) {
		    RestResponse response = new RestResponse();
        	Integer tenantId = TenantContext.getTenantId();
            if (this.idpAttributesMapping.removeTenantIdpAttributeValueMappingRecord(tenantId, valueID) <= 0) {
                 throw new RuntimeException("Delete failed.Please see value id "+valueID+" exist");
            }
            response.setStatus("200", "Deleted Successfully");
         return  response; 
    }
  
	/*Removed all the uses of this method as per the suggestion by Ganesh since encoding should happen from 	 */
	private String escapeXSS(String input){
		try {
			CleanResults htmlEscape = AntiSamyHtmlCleaner.cleanHtml(input);
			return  htmlEscape.getCleanHTML();
		} catch (ScanException se) {
			logger.error(se.getMessage());
		} catch (PolicyException pe) {
			logger.error(pe.getMessage());
		}
		return "";
	}
	
	
	/**
	 * API to get REST based authentication details . 
	 * POST only to create
	 * @param request
	 * @param attributeWrapper
	 * @return
	 */
	
	@RequestMapping(value = "/service/restAuthentication", method = RequestMethod.GET)
	public @ResponseBody RestResponse<WSRestWrapper> getRestAuthentication() {
		RestResponse<WSRestWrapper> response = new RestResponse<>();
		AuthenticationConfig config =system.getAuthenticationConfig(TenantContext.getTenantId());
		WSRestWrapper wsRestAuthentication=new WSRestWrapper();
		wsRestAuthentication.setRestAuthConfigured(config.isRestFlag());
		wsRestAuthentication.setRestUrl(config.getRestUrl());
		wsRestAuthentication.setAssignedRoles(system.getToRoles());
		response.setData(wsRestAuthentication);
		return response;
	}
	
	/**
	 * API to setup REST based authentication .Expecting user to input the rest url. 
	 * POST only to create
	 * @param request
	 * @param attributeWrapper
	 * @return
	 * @throws IOException 
	 */
	
	@RequestMapping(value = "/service/restAuthentication", method = RequestMethod.POST)
	public @ResponseBody RestResponse<WSRestWrapper> setUpRestAuthentication(@Context HttpServletRequest request,@Context HttpServletResponse httpResponse,@Valid @RequestBody WSRestWrapper restRequst) throws IOException {
		RestResponse<WSRestWrapper> response = new RestResponse<>();
		AuthenticationConfig authenticationConfig=system.getAuthenticationConfig(TenantContext.getTenantId());

		if(authenticationConfig.isRestFlag()){		
			httpResponse.sendError(405);
			response.setStatus("405", "REST Web Service is already configured. Please use PUT method to udpate");
			return response;
		}
		if(restRequst.getAssignedRoles().isEmpty()){
			httpResponse.sendError(400);
			response.setStatus("400", "User Type/Role must not be empty");
			return response;
		}
		String[] schemes = {"http","https"};
		//
        UrlValidator urlValidator=new UrlValidator(schemes);
        if(!urlValidator.isValid(restRequst.getRestUrl())){
        		httpResponse.sendError(400,"Bad Request. URL missing or invalid");
    			response.setStatus("400", "Bad Request. URL missing or invalid");
    			return response;
        }
		authenticationConfig=new AuthenticationConfig();		
		authenticationConfig.setRestFlag(true);
		authenticationConfig.setRestUrl(restRequst.getRestUrl());
				List<MemberRoles> list=restRequst.getAssignedRoles();
		Iterator<MemberRoles> iterator=list.iterator();
		MemberRoles role=null;
		StringBuffer strBuffer=new StringBuffer();
		while(iterator.hasNext()){
			role= iterator.next();
			strBuffer.append(role.getRoleID());
			if(iterator.hasNext()){
				strBuffer.append(",");
			}
		}
		authenticationConfig.setAuthFor(strBuffer.toString());
		String baseURL = request.getScheme() + "://" + request.getServerName();
		int result = system.saveAuthenticationConfig(TenantContext.getTenantId(), authenticationConfig, baseURL);
		throwErrorValidationError(result);
		restRequst.setRestAuthConfigured(true);//assume that it went successful.avoiding unnecessary db trip
		response.setStatus("200", "OK");
		response.setData(restRequst);
		return response;
	}
	
	@RequestMapping(value = "/service/restAuthentication", method = RequestMethod.PUT)
	public @ResponseBody RestResponse<WSRestWrapper> updateRestAuthentication(@Context HttpServletRequest request,@Context HttpServletResponse httpResponse,@Valid @RequestBody WSRestWrapper restRequst) throws IOException {
		RestResponse<WSRestWrapper> response = new RestResponse<>();
		AuthenticationConfig authenticationConfig=system.getAuthenticationConfig(TenantContext.getTenantId());
		if(!authenticationConfig.isRestFlag()){		
			httpResponse.sendError(405);
			response.setStatus("405", "REST Web Service is not configured. Please use POST method to create");
			return response;
		}
		if(restRequst.getAssignedRoles().isEmpty()){
			httpResponse.sendError(400);
			response.setStatus("400", "User Type/Role must not be empty");
			return response;
		}	
		String[] schemes = {"http","https"};
        UrlValidator urlValidator=new UrlValidator(schemes);
        if(!urlValidator.isValid(restRequst.getRestUrl())){
        		httpResponse.sendError(400,"Bad Request. URL missing or invalid");
    			response.setStatus("400", "Bad Request. URL missing or invalid");
    			return response;
        }
		authenticationConfig=new AuthenticationConfig();	
		authenticationConfig.setRestFlag(true);
		authenticationConfig.setRestUrl(restRequst.getRestUrl());
		List<MemberRoles> list=restRequst.getAssignedRoles();
		Iterator<MemberRoles> iterator=list.iterator();
		MemberRoles role=null;
		StringBuffer strBuffer=new StringBuffer();
		while(iterator.hasNext()){
			role= iterator.next();
			strBuffer.append(role.getRoleID());
			if(iterator.hasNext()){
				strBuffer.append(",");
			}
		}
		authenticationConfig.setAuthFor(strBuffer.toString());
		String baseURL = request.getScheme() + "://" + request.getServerName();
		int result = system.saveAuthenticationConfig(TenantContext.getTenantId(), authenticationConfig, baseURL);
		throwErrorValidationError(result);
		restRequst.setAssignedRoles(system.getToRoles());//This way response will have correct data as same as db.Due to validation , system may skip invalid roles , so we cant reutrn the request back as object.
		restRequst.setRestAuthConfigured(true);
		response.setStatus("200", "OK");
		response.setData(restRequst);
		return response;
	}
	
	/*** API to get List of roles . 
	 **/
	
	@RequestMapping(value = "/service/availableUserTypes", method = RequestMethod.GET)
	public @ResponseBody RestResponse<List<MemberRoles>> getAvailableUserTypes() {
		List<MemberRoles> list = service.getMemberRoles();
		RestResponse<List<MemberRoles> > response = new RestResponse<>();
		response.setStatus("200", "OK");
		response.setData(list);
		return response;
	}
	
	
	/**
	 * API to save authentication type as LOCAL. 
	 * Only post available
	 */
	
	@RequestMapping(value = "/service/localAuthentication", method = RequestMethod.POST)
	public @ResponseBody RestResponse setUpLocal() throws IOException {
		RestResponse response = new RestResponse();
		AuthenticationConfig authenticationConfig=system.getAuthenticationConfig(TenantContext.getTenantId());
		authenticationConfig=new AuthenticationConfig();	
		int result = system.saveAuthenticationConfig(TenantContext.getTenantId(), authenticationConfig, "");//baseURL doesnt need it
		throwErrorValidationError(result);
		response.setStatus("200", "OK");	
		return response;
	}
}

package com.vidyo.framework.security.saml.context;

import javax.xml.namespace.QName;

import com.vidyo.framework.context.TenantContext;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.transport.http.HTTPInTransport;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.saml.context.SAMLContextProviderImpl;
import org.springframework.security.saml.context.SAMLMessageContext;

import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.service.authentication.saml.SamlAuthenticationService;

public class SAMLContextProviderVidyoImpl extends SAMLContextProviderImpl {

    private SamlAuthenticationService samlAuthenticationService;
    
    public void setSamlAuthenticationService(SamlAuthenticationService samlAuthenticationService) {
        this.samlAuthenticationService = samlAuthenticationService;
    }

	/**
     * First tries to find pre-configured IDP from the request attribute. If not found
     * loads the IDP_PARAMETER from the request and if it is not null verifies whether IDP with this value is valid
     * IDP in our circle of trust. Processing fails when IDP is not valid. IDP is set as PeerEntityId in the context.
     * <p/>
     * If request parameter is null tries to find out IDP from DB for localEntityID. If not found in DB the default IDP is returned.
     *
     * @param context context to populate ID for
     * @throws MetadataProviderException in case provided IDP value is invalid
     */
    @Override
    protected void populatePeerEntityId(SAMLMessageContext context) throws MetadataProviderException {

        HTTPInTransport inTransport = (HTTPInTransport) context.getInboundMessageTransport();
        String entityId;

        entityId = (String) inTransport.getAttribute(org.springframework.security.saml.SAMLConstants.PEER_ENTITY_ID);
        if (entityId != null) { // Pre-configured entity Id
            logger.debug("Using protocol specified IDP {}", entityId);
        } else {
            entityId = inTransport.getParameterValue(SAMLEntryPoint.IDP_PARAMETER);
            if (entityId != null) { // IDP from request
                logger.debug("Using user specified IDP {} from request", entityId);
                context.setPeerUserSelected(true);
            } else { // Default IDP
                SamlAuthentication samlAuthentication = samlAuthenticationService.getSamlAuthenticationForTenant(TenantContext.getTenantId());
                if(samlAuthentication != null) {
                    entityId = samlAuthenticationService.getEntityIdFromMetadataXml(samlAuthentication.getIdentityProviderMetadata());
                }
                
                
                if(entityId == null || entityId.isEmpty()) {
                	String errMsg = "No IDP specified for tenant " + TenantContext.getTenantId();
					logger.error(errMsg);
					throw new MetadataProviderException(errMsg);                	
                }
                
                logger.debug("No IDP specified, using default {}", entityId);
                context.setPeerUserSelected(false);
            }
        }

        context.setPeerEntityId(entityId);
        context.setPeerEntityRole(IDPSSODescriptor.DEFAULT_ELEMENT_NAME);

    }
    
    /**
     * Method tries to load localEntityAlias and localEntityRole from the request path. Path is supposed to be in format:
     * https(s)://server:port/application/saml/filterName/alias/aliasName/idp|sp?query. In case alias is missing from
     * the path defaults are used. Otherwise localEntityId and sp or idp localEntityRole is entered into the context.
     * <p/>
     * In case alias entity id isn't found an exception is raised.
     *
     * @param context     context to populate fields localEntityId and localEntityRole for
     * @param requestURI context path to parse entityId and entityRole from
     * @throws MetadataProviderException in case entityId can't be populated
     */
    protected void populateLocalEntityId(SAMLMessageContext context, String requestURI) throws MetadataProviderException {

        String entityId;
        HTTPInTransport inTransport = (HTTPInTransport) context.getInboundMessageTransport();

        // Pre-configured entity Id
        entityId = (String) inTransport.getAttribute(org.springframework.security.saml.SAMLConstants.LOCAL_ENTITY_ID);
        if (entityId != null) {
            logger.debug("Using protocol specified SP {}", entityId);
            context.setLocalEntityId(entityId);
            context.setLocalEntityRole(SPSSODescriptor.DEFAULT_ELEMENT_NAME);
            return;
        }

        if (requestURI == null) {
            requestURI = "";
        }

        int filterIndex = requestURI.indexOf("/alias/");
        if (filterIndex != -1) { // EntityId from URL alias

            String localAlias = requestURI.substring(filterIndex + 7);
            QName localEntityRole;

            int entityTypePosition = localAlias.lastIndexOf('/');
            if (entityTypePosition != -1) {
                String entityRole = localAlias.substring(entityTypePosition + 1);
                if ("idp".equalsIgnoreCase(entityRole)) {
                    localEntityRole = IDPSSODescriptor.DEFAULT_ELEMENT_NAME;
                } else {
                    localEntityRole = SPSSODescriptor.DEFAULT_ELEMENT_NAME;
                }
                localAlias = localAlias.substring(0, entityTypePosition);
            } else {
                localEntityRole = SPSSODescriptor.DEFAULT_ELEMENT_NAME;
            }


            // Populate entityId
            entityId = metadata.getEntityIdForAlias(localAlias);

            if (entityId == null) {
                logger.error("No local entity found for alias " + localAlias + ", verify your configuration.");
            	throw new MetadataProviderException("No local entity found for alias " + localAlias + ", verify your configuration.");
            } else {
                logger.debug("Using SP {} specified in request with alias {}", entityId, localAlias);
            }

            context.setLocalEntityId(entityId);
            context.setLocalEntityRole(localEntityRole);

        } else { // Defaults

        	SamlAuthentication samlAuthentication = samlAuthenticationService.getSamlAuthenticationForTenant(TenantContext.getTenantId());
            if(samlAuthentication != null) {
            	
            	EntityDescriptor spMetadataEntityDescriptor = null;
            	try {
            		spMetadataEntityDescriptor = samlAuthenticationService.generateServiceProviderMetadataAsEntityDescriptor(TenantContext.getTenantId(), samlAuthentication);
				} catch (Exception e) {
					String errMsg = "Failed to get SP metadata for tenant " + TenantContext.getTenantId();
					logger.error(errMsg, e);
					throw new MetadataProviderException(errMsg, e);
				}
            	
            	if(spMetadataEntityDescriptor != null) {
            		entityId = spMetadataEntityDescriptor.getEntityID();
            		
            		context.setLocalEntityId(entityId);
                    context.setLocalEntityRole(SPSSODescriptor.DEFAULT_ELEMENT_NAME);
            	}
            }
            
            if(entityId == null || entityId.isEmpty()) {
            	String errMsg = "No local entity found for tenant " + TenantContext.getTenantId() + ", verify your configuration.";
            	logger.error(errMsg);
            	throw new MetadataProviderException(errMsg);
            }
            
        }

    }

}

package com.vidyo.service.authentication.saml;

import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.service.exceptions.InvalidArgumentException;
import com.vidyo.service.exceptions.MetadataProviderCreationException;

import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.xml.io.MarshallingException;
import org.w3c.dom.Element;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SamlAuthenticationService {

    public Map<Integer, SamlAuthentication> getSamlAuthenticationByTenant();
    public SamlAuthentication getSamlAuthenticationForTenant(int tenantId);
    public String generateServiceProviderMetadata(int tenantID, SamlAuthentication samlAuthentication) 
            throws InvalidArgumentException, IOException, MarshallingException;
    public EntityDescriptor generateServiceProviderMetadataAsEntityDescriptor(int tenantID, SamlAuthentication samlAuthentication) 
            throws InvalidArgumentException, IOException, MarshallingException;

    public boolean isSAMLAuthenticationEnabled(int tenantID);
    public void enableSAMLAuthentication(int tenantID, SamlAuthentication samlAuth);
    public void disableSAMLAuthentication(int tenantID);

    public boolean isValidIdpXML(String idpXML);
    public String getEntityIdFromMetadataXml(String metadataXml);
    public String getEntityIdFromMetadataDocumentElement(Element element);
    
    public List<MetadataProvider> createProvidersForSPandIPD(int tenantId, SamlAuthentication samlAuthentication) 
    		throws MetadataProviderCreationException;
    
    public boolean isProviderExisting(String entityId);
}
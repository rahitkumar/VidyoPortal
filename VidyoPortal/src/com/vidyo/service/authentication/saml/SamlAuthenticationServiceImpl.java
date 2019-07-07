package com.vidyo.service.authentication.saml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.opensaml.Configuration;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml2.metadata.ContactPerson;
import org.opensaml.saml2.metadata.ContactPersonTypeEnumeration;
import org.opensaml.saml2.metadata.EmailAddress;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.LocalizedString;
import org.opensaml.saml2.metadata.RequestedAttribute;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.ServiceDescription;
import org.opensaml.saml2.metadata.ServiceName;
import org.opensaml.saml2.metadata.SurName;
import org.opensaml.saml2.metadata.impl.AttributeConsumingServiceBuilder;
import org.opensaml.saml2.metadata.impl.ContactPersonBuilder;
import org.opensaml.saml2.metadata.impl.EmailAddressBuilder;
import org.opensaml.saml2.metadata.impl.RequestedAttributeBuilder;
import org.opensaml.saml2.metadata.impl.ServiceDescriptionBuilder;
import org.opensaml.saml2.metadata.impl.ServiceNameBuilder;
import org.opensaml.saml2.metadata.impl.SurNameBuilder;
import org.opensaml.saml2.metadata.provider.DOMMetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.xml.io.Marshaller;
import org.opensaml.xml.io.MarshallerFactory;
import org.opensaml.xml.io.MarshallingException;
import org.opensaml.xml.io.Unmarshaller;
import org.opensaml.xml.io.UnmarshallerFactory;
import org.opensaml.xml.parse.ParserPool;
import org.opensaml.xml.security.credential.Credential;
import org.opensaml.xml.util.XMLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.saml.key.JKSKeyManager;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.metadata.ExtendedMetadata;
import org.springframework.security.saml.metadata.ExtendedMetadataDelegate;
import org.springframework.security.saml.metadata.MetadataGenerator;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.security.saml.metadata.MetadataMemoryProvider;
import org.springframework.security.saml.util.SAMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.vidyo.bo.Member;
import com.vidyo.bo.MemberFilter;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.authentication.SAMLNameID;
import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.bo.authentication.SamlProvisionType;
import com.vidyo.bo.idp.TenantIdpAttributeMapping;
import com.vidyo.db.ISystemDao;
import com.vidyo.db.authentication.saml.ISamlDao;
import com.vidyo.db.idp.ITenantIdpAttributesMappingDao;
import com.vidyo.framework.context.ApplicationContextProvider;
import com.vidyo.framework.propertyconfig.OpenPropertyPlaceholderConfigurer;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.exceptions.InvalidArgumentException;
import com.vidyo.service.exceptions.MetadataProviderCreationException;
import com.vidyo.service.member.MemberService;

public class SamlAuthenticationServiceImpl implements SamlAuthenticationService {

    private static final String SAML_AUTH_FLAG = "SAML_AUTH_FLAG";
    private static final String SAML_IDP_METADATA = "SAML_IDP_METADATA";
    private static final String SAML_SP_SECURITY_PROFILE = "SAML_SP_SECURITY_PROFILE";
    private static final String SAML_SP_SSL_PROFILE = "SAML_SP_SSL_PROFILE";
    private static final String SAML_SP_SIGN_METADATA = "SAML_SP_SIGN_METADATA";
    private static final String SAML_SP_PROTOCOL = "SAML_SP_PROTOCOL";
    private static final String SAML_AUTH_PROVISION_TYPE = "SAML_AUTH_PROVISION_TYPE";
    private static final String SAML_IDP_ATTRIBUTE_FOR_USERNAME = "SAML_IDP_ATTRIBUTE_FOR_USERNAME";
    private static final String SAML_SP_ENTITY_ID = "SAML_SP_ENTITY_ID";

    @Autowired(required = false)
    JKSKeyManager keyManager;

    @Autowired(required = false)
    MetadataManager metadataManager;

    protected final Logger logger = LoggerFactory.getLogger(SamlAuthenticationServiceImpl.class.getName());

    private ISystemDao systemDao;

    private ITenantService tenantService;

    private OpenPropertyPlaceholderConfigurer propertyPlaceholderConfigurer;

    private ParserPool parserPool;

    private ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao;

    private MemberService memberService1;

    private ISystemService systemService;

    public void setSystemDao(ISystemDao systemDao) {
		this.systemDao = systemDao;
	}

	public void setTenantService(ITenantService tenantService) {
        this.tenantService = tenantService;
    }

	public void setPropertyPlaceholderConfigurer(OpenPropertyPlaceholderConfigurer propertyPlaceholderConfigurer) {
		this.propertyPlaceholderConfigurer = propertyPlaceholderConfigurer;
	}

	public void setParserPool(ParserPool parserPool) {
		this.parserPool = parserPool;
	}

	public void setTenantIdpAttributesMappingDao(ITenantIdpAttributesMappingDao tenantIdpAttributesMappingDao) {
		this.tenantIdpAttributesMappingDao = tenantIdpAttributesMappingDao;
	}

	public void setMemberService1(MemberService memberService1) {
		this.memberService1 = memberService1;
	}

	public void setSystemService(ISystemService systemService) {
		this.systemService = systemService;
	}

	@Override
    public Map<Integer, SamlAuthentication> getSamlAuthenticationByTenant() {
        Map<Integer, SamlAuthentication> result = new HashMap<Integer, SamlAuthentication>();
        List<Integer> tenantIds = tenantService.getAllTenantIds();
        SamlAuthentication samlAuth = null;
        if (tenantIds != null && tenantIds.size() > 0) {
            for (Integer tenantId : tenantIds) {
                samlAuth = getSamlAuthenticationForTenant(tenantId);
                if (samlAuth != null) {
                    result.put(tenantId, samlAuth);
                }
            }
        }
        return result;
    }

    @Override
    public SamlAuthentication getSamlAuthenticationForTenant(int tenantId) {
    	SamlAuthentication samlAuth = null;
    	com.vidyo.bo.Configuration config = systemDao.getConfiguration(tenantId, SAML_AUTH_FLAG);
    	if(config != null && config.getConfigurationValue() != null && "YES".equalsIgnoreCase(config.getConfigurationValue())) {
    		samlAuth = new SamlAuthentication();
    		samlAuth.setIdentityProviderMetadata(getConfigValue(tenantId, SAML_IDP_METADATA));
            samlAuth.setSpEntityId(getConfigValue(tenantId, SAML_SP_ENTITY_ID));
            samlAuth.setSecurityProfile(getConfigValue(tenantId, SAML_SP_SECURITY_PROFILE));
            samlAuth.setSslTlsProfile(getConfigValue(tenantId, SAML_SP_SSL_PROFILE));
            samlAuth.setSignMetadata("YES".equals(getConfigValue(tenantId, SAML_SP_SIGN_METADATA)));
            samlAuth.setHttps("YES".equalsIgnoreCase(getConfigValue(tenantId, SAML_SP_PROTOCOL)));

            String authProvisionTypeStr = getConfigValue(tenantId, SAML_AUTH_PROVISION_TYPE);
            if(authProvisionTypeStr == null || authProvisionTypeStr.isEmpty()) {
                samlAuth.setAuthProvisionType(SamlProvisionType.SAML);
            } else {
                SamlProvisionType samlProvisionType = SamlProvisionType.get(Integer.parseInt(authProvisionTypeStr));
                samlAuth.setAuthProvisionType(samlProvisionType);
                if(samlProvisionType == SamlProvisionType.LOCAL) {
                    samlAuth.setIdpAttributeForUsername(getConfigValue(tenantId, SAML_IDP_ATTRIBUTE_FOR_USERNAME));
                }
            }
            // As SP entityId we introduced later we will set tenant's url as defaul value.
            
//        	if(samlAuth != null) {
//        		String spEntityId = samlAuth.getSpEntityId();
//        		if(spEntityId == null || spEntityId.isEmpty()) {
//        			Tenant tenant = tenantService.getTenant(tenantId);
//        			samlAuth.setSpEntityId(tenant.getTenantURL());
//        		}
//        	}
            //this will be moved to client side.now we need full url not just fqdn
    	}
    	return samlAuth;
    }

    protected String getConfigValue(int tenantID, String configurationName){
		com.vidyo.bo.Configuration config = systemDao.getConfiguration(tenantID, configurationName);
		if (config == null || StringUtils.isBlank(config.getConfigurationValue())){
			return "";
		}
		return config.getConfigurationValue();
	}

    @Override
    public String generateServiceProviderMetadata(int tenantID, SamlAuthentication samlAuthentication)
            throws InvalidArgumentException, IOException, MarshallingException {

        EntityDescriptor descriptor = generateServiceProviderMetadataAsEntityDescriptor(tenantID, samlAuthentication);
        ExtendedMetadata extendedMetadata =    getCommonExtendedMetadata(tenantID,samlAuthentication);
        // Metadata signing
        extendedMetadata.setLocal(true);//this is needed for sp. this is needed for generating sign meta-data
       return  getMetadataAsString( descriptor, extendedMetadata);

    }
/**
 * This will return extended meta-data object. This method to code re-usability ,since due to old spring saml implementation, we have to set this object many times. This can be avoided by following latest spring sample application
 * @throws IOException
 *
 */
    private ExtendedMetadata getCommonExtendedMetadata(int tenantID,SamlAuthentication samlAuthentication) throws IOException {
    	Tenant tenant = tenantService.getTenant(tenantID);
        ExtendedMetadata extendedMetadata = new ExtendedMetadata();
        // Alias
        extendedMetadata.setAlias(tenant.getTenantName());

        // Security settings--enhancement waiting
//        extendedMetadata.setSecurityProfile(metadata.getSecurityProfile());
//        extendedMetadata.setSslSecurityProfile(metadata.getSslSecurityProfile());
//        extendedMetadata.setRequireLogoutRequestSigned(metadata.isRequireLogoutRequestSigned());
//        extendedMetadata.setRequireLogoutResponseSigned(metadata.isRequireLogoutResponseSigned());
//        extendedMetadata.setRequireArtifactResolveSigned(metadata.isRequireArtifactResolveSigned());
//        extendedMetadata.setSslHostnameVerification(metadata.getSslHostnameVerification());

    	   //keys
        extendedMetadata.setSigningKey(propertyPlaceholderConfigurer.getMergedProperties().getProperty("saml.signing.key"));
        extendedMetadata.setEncryptionKey(propertyPlaceholderConfigurer.getMergedProperties().getProperty("saml.encryption.key"));
        // Discovery
        extendedMetadata.setIdpDiscoveryEnabled(false);
     //   extendedMetadata.setSigningAlgorithm("http://www.w3.org/2001/04/xmldsig-more#rsa-sha512");
        extendedMetadata.setSignMetadata(samlAuthentication.isSignMetadata());
        return extendedMetadata;
	}

	private String getMetadataAsString(EntityDescriptor descriptor, ExtendedMetadata extendedMetadata) throws MarshallingException {

        Element element;

        if (extendedMetadata == null) {
            try {
                extendedMetadata = metadataManager.getExtendedMetadata(descriptor.getEntityID());
            } catch (MetadataProviderException e) {
                logger.error("Unable to locate extended metadata", e);
                throw new MarshallingException("Unable to locate extended metadata", e);
            }
        }

        try {
            if (extendedMetadata.isLocal() && extendedMetadata.isSignMetadata()) {
                Credential credential = keyManager.getCredential(extendedMetadata.getSigningKey());
                String signingAlgorithm = extendedMetadata.getSigningAlgorithm();
                String keyGenerator = extendedMetadata.getKeyInfoGeneratorName();
                element = SAMLUtil.marshallAndSignMessage(descriptor, credential, signingAlgorithm, keyGenerator);
            } else {
                element = SAMLUtil.marshallMessage(descriptor);
            }
        } catch (MessageEncodingException e) {
            logger.error("Unable to marshall message", e);
            throw new MarshallingException("Unable to marshall message", e);
        }

        return XMLHelper.prettyPrintXML(element);

    }

    @Override
    public EntityDescriptor generateServiceProviderMetadataAsEntityDescriptor(int tenantID, SamlAuthentication samlAuthentication)
            throws InvalidArgumentException, IOException, MarshallingException {
   	 ExtendedMetadata extendedMetadata = getCommonExtendedMetadata(tenantID,samlAuthentication);

   	 Tenant tenant = tenantService.getTenant(tenantID);
     MetadataGenerator generator = new MetadataGenerator();
     generator.setKeyManager(keyManager);
     generator.setExtendedMetadata(extendedMetadata);
     generator.setEntityId(samlAuthentication.getSpEntityId());
     String protocol = "http://";
     if(samlAuthentication.isHttps()) {
         protocol = "https://";
     }
     generator.setEntityBaseURL(protocol + tenant.getTenantURL());
     String requestSignedConfig = systemService.getConfigValue(tenantID, "REQUEST_SIGNED");
     if(StringUtils.isBlank(requestSignedConfig) || requestSignedConfig.equalsIgnoreCase("0")) {
         generator.setRequestSigned(false);
     }
     String wantAssertionSignedConfig = systemService.getConfigValue(tenantID, "WANT_ASSERTION_ASSIGNED");
     if(StringUtils.isBlank(wantAssertionSignedConfig) || wantAssertionSignedConfig.equalsIgnoreCase("0")) {
         generator.setWantAssertionSigned(false);
     }

     Collection<String> bindingsSSO = new LinkedList<String>();
     Collection<String> bindingsHoKSSO = new LinkedList<String>();

     bindingsSSO.add(SAMLConstants.SAML2_POST_BINDING_URI);
     bindingsSSO.add(SAMLConstants.SAML2_ARTIFACT_BINDING_URI);
     bindingsSSO.add(SAMLConstants.SAML2_PAOS_BINDING_URI);
     bindingsHoKSSO.add(SAMLConstants.SAML2_POST_BINDING_URI);
     bindingsHoKSSO.add(SAMLConstants.SAML2_ARTIFACT_BINDING_URI);

     // Set bindings
     generator.setBindingsSSO(bindingsSSO);
     generator.setBindingsHoKSSO(bindingsHoKSSO);
     generator.setAssertionConsumerIndex(0);

     // Discovery
     generator.setIncludeDiscoveryExtension(false);


     List<String> nameIDs = new ArrayList<String>();
     for(SAMLNameID samlNameID : SAMLNameID.values()) {
         nameIDs.add(samlNameID.getNameID());
     }
     generator.setNameID(nameIDs);

     AttributeConsumingServiceBuilder attributeConsumingServiceBuilder = new AttributeConsumingServiceBuilder();
     AttributeConsumingService attributeConsumingService = attributeConsumingServiceBuilder.buildObject();

     EntityDescriptor descriptor = generator.generateMetadata();


     SPSSODescriptor spSSODescriptor = descriptor.getSPSSODescriptor(SAMLConstants.SAML20P_NS);
     if(spSSODescriptor != null) {
     	ServiceNameBuilder serviceNameBuilder = new ServiceNameBuilder();
     	ServiceName serviceName = serviceNameBuilder.buildObject();
     	LocalizedString name = new LocalizedString(tenant.getTenantName(), "en");
     	serviceName.setName(name);
     	attributeConsumingService.getNames().add(serviceName);

     	if(tenant.getDescription() != null && !tenant.getDescription().isEmpty()) {
     		ServiceDescriptionBuilder serviceDescriptionBuilder = new ServiceDescriptionBuilder();
     		ServiceDescription serviceDescription = serviceDescriptionBuilder.buildObject();
     		LocalizedString description = new LocalizedString(tenant.getDescription(), "en");
     		serviceDescription.setDescription(description);
     		attributeConsumingService.getDescriptions().add(serviceDescription);
     	}

     	RequestedAttributeBuilder requestedAttributeBuilder = new RequestedAttributeBuilder();

     	RequestedAttribute requestedAttribute;
     	if(samlAuthentication.getAuthProvisionType() == SamlProvisionType.LOCAL) {
     		String idpAttributeForUsername = samlAuthentication.getIdpAttributeForUsername();
     		if(idpAttributeForUsername != null && !idpAttributeForUsername.isEmpty()) {
     			requestedAttribute = requestedAttributeBuilder.buildObject();
	        		requestedAttribute.setName(idpAttributeForUsername);
	        		requestedAttribute.setIsRequired(true);

	        		attributeConsumingService.getRequestAttributes().add(requestedAttribute);
     		}
     	} else {
	        	List<TenantIdpAttributeMapping> tenantIdpAttributeMappings = tenantIdpAttributesMappingDao.getTenantIdpAttributeMapping(tenantID);
	        	for(TenantIdpAttributeMapping tenantIdpAttributeMapping : tenantIdpAttributeMappings) {
		        	// User name
		        	if(tenantIdpAttributeMapping.getVidyoAttributeName().equalsIgnoreCase("UserName") &&
		        			tenantIdpAttributeMapping.getIdpAttributeName() != null &&
		        			!tenantIdpAttributeMapping.getIdpAttributeName().isEmpty()) {

		        		requestedAttribute = requestedAttributeBuilder.buildObject();
		        		requestedAttribute.setName(tenantIdpAttributeMapping.getIdpAttributeName());
		        		requestedAttribute.setIsRequired(true);

		        		attributeConsumingService.getRequestAttributes().add(requestedAttribute);
		        	}

		        	// Display name
		        	if(tenantIdpAttributeMapping.getVidyoAttributeName().equalsIgnoreCase("DisplayName") &&
		        			tenantIdpAttributeMapping.getIdpAttributeName() != null &&
		        			!tenantIdpAttributeMapping.getIdpAttributeName().isEmpty()) {

		        		requestedAttribute = requestedAttributeBuilder.buildObject();
		        		requestedAttribute.setName(tenantIdpAttributeMapping.getIdpAttributeName());
		        		requestedAttribute.setIsRequired(false);

		        		attributeConsumingService.getRequestAttributes().add(requestedAttribute);
		        	}

		        	// Email address
		        	if(tenantIdpAttributeMapping.getVidyoAttributeName().equalsIgnoreCase("EmailAddress") &&
		        			tenantIdpAttributeMapping.getIdpAttributeName() != null &&
		        			!tenantIdpAttributeMapping.getIdpAttributeName().isEmpty()) {

		        		requestedAttribute = requestedAttributeBuilder.buildObject();
		        		requestedAttribute.setName(tenantIdpAttributeMapping.getIdpAttributeName());
		        		requestedAttribute.setIsRequired(true);

		        		attributeConsumingService.getRequestAttributes().add(requestedAttribute);
		        	}



	        	}
     	}

     	spSSODescriptor.getAttributeConsumingServices().add(attributeConsumingService);

     	ContactPersonBuilder contactPersonBuilder = new ContactPersonBuilder();
     	ContactPerson contactPerson = contactPersonBuilder.buildObject();
     	MemberFilter memberFilter = new MemberFilter();
     	memberFilter.setType("Admin");
     	// FIXME : VPTL-8179 : Commenting out the call to getMembers as it needs to be optimized
     	// List<Member> adminMembers = memberService1.getMembers(tenantID, memberFilter);
     	List<Member> adminMembers = new ArrayList<>();
     	if(adminMembers.size() > 0) {
     		Member admin = adminMembers.get(0);

     		contactPerson.setType(ContactPersonTypeEnumeration.TECHNICAL);

     		SurNameBuilder surNameBuilder = new SurNameBuilder();
     		SurName surName = surNameBuilder.buildObject();
     		surName.setName(admin.getMemberName());
     		contactPerson.setSurName(surName);

     		EmailAddressBuilder emailAddressBuilder = new EmailAddressBuilder();
     		EmailAddress emailAddress = emailAddressBuilder.buildObject();
     		emailAddress.setAddress(admin.getEmailAddress());
     		contactPerson.getEmailAddresses().add(emailAddress);

     		spSSODescriptor.getContactPersons().add(contactPerson);
     	}
     }

     return descriptor;
 }

    @Override
    public boolean isSAMLAuthenticationEnabled(int tenantID) {
    	return "YES".equals(getConfigValue(tenantID, SAML_AUTH_FLAG));
    }

    @Override
    public void enableSAMLAuthentication(int tenantID, SamlAuthentication samlAuth) {
        systemDao.updateConfiguration(tenantID, SAML_AUTH_FLAG, "YES");
        systemDao.updateConfiguration(tenantID, SAML_IDP_METADATA, samlAuth.getIdentityProviderMetadata());
        systemDao.updateConfiguration(tenantID, SAML_SP_ENTITY_ID, samlAuth.getSpEntityId());
        systemDao.updateConfiguration(tenantID, SAML_SP_SECURITY_PROFILE, samlAuth.getSecurityProfile());
        systemDao.updateConfiguration(tenantID, SAML_SP_SSL_PROFILE, samlAuth.getSslTlsProfile());
        systemDao.updateConfiguration(tenantID, SAML_SP_SIGN_METADATA, (samlAuth.isSignMetadata() ? "YES" : "NO"));
        systemDao.updateConfiguration(tenantID, SAML_SP_PROTOCOL, (samlAuth.isHttps() ? "YES" : "NO"));
        systemDao.updateConfiguration(tenantID, SAML_AUTH_PROVISION_TYPE, String.valueOf(samlAuth.getAuthProvisionType().getValue()));
        if(samlAuth.getAuthProvisionType() == SamlProvisionType.LOCAL) {
        	systemDao.updateConfiguration(tenantID, SAML_IDP_ATTRIBUTE_FOR_USERNAME, samlAuth.getIdpAttributeForUsername());
        }
    }

    @Override
    public void disableSAMLAuthentication(int tenantID) {
    	systemDao.updateConfiguration(tenantID, SAML_AUTH_FLAG, "NO");
    }

    @Override
    public boolean isValidIdpXML(String idpXML) {
        try {
            InputStream in = IOUtils.toInputStream(idpXML);
            Document inCommonMDDoc = parserPool.parse(in);
            Element metadataRoot = inCommonMDDoc.getDocumentElement();
            UnmarshallerFactory unmarshallerFactory = Configuration.getUnmarshallerFactory();
            Unmarshaller unmarshaller = unmarshallerFactory.getUnmarshaller(metadataRoot);
            unmarshaller.unmarshall(metadataRoot);
        } catch (Exception e) {
            logger.info("Could not parse SAML IdP XML: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String getEntityIdFromMetadataXml(String metadataXml) {
    	String entityId = "";
    	try {
            InputStream in = IOUtils.toInputStream(metadataXml);
            Document inCommonMDDoc = parserPool.parse(in);
            Element metadataRoot = inCommonMDDoc.getDocumentElement();
            entityId = metadataRoot.getAttribute("entityID");

        } catch (Exception e) {
            logger.info("Could not parse SAML metadataXml: " + e.getMessage());
        }
        return entityId;
    }

    @Override
    public String getEntityIdFromMetadataDocumentElement(Element element) {
        String entityId = element.getAttribute("entityID");;
        return entityId;
    }

    @Override
    public List<MetadataProvider> createProvidersForSPandIPD(int tenantId, SamlAuthentication samlAuthentication)
    		throws MetadataProviderCreationException {
    	List<MetadataProvider> providers = new ArrayList<MetadataProvider>();

    	ParserPool parserPool = (ParserPool)ApplicationContextProvider.getApplicationContext().getBean("parserPool");

    	Tenant tenant = tenantService.getTenant(tenantId);

		// Configure SP via ExtendedMetadataDelegate
		DOMMetadataProvider domMetadataProvider;
		MetadataMemoryProvider metadataMemoryProvider;
		try {
			EntityDescriptor spMetadata = generateServiceProviderMetadataAsEntityDescriptor(tenantId, samlAuthentication);

			metadataMemoryProvider = new MetadataMemoryProvider(spMetadata);

			metadataMemoryProvider.initialize();

			ExtendedMetadata extendedMetadata = new ExtendedMetadata();
			extendedMetadata.setLocal(true);
			extendedMetadata.setAlias(tenant.getTenantName());
			extendedMetadata.setSecurityProfile(samlAuthentication.getSecurityProfile()); // "metaiop"
			extendedMetadata.setSslSecurityProfile(samlAuthentication.getSslTlsProfile()); // "pkix"
			extendedMetadata.setSigningKey(propertyPlaceholderConfigurer.getMergedProperties().getProperty("saml.signing.key"));
			extendedMetadata.setEncryptionKey(propertyPlaceholderConfigurer.getMergedProperties().getProperty("saml.encryption.key"));
			extendedMetadata.setRequireArtifactResolveSigned(false);
			extendedMetadata.setRequireLogoutRequestSigned(true);
			extendedMetadata.setRequireLogoutResponseSigned(false);
			extendedMetadata.setIdpDiscoveryEnabled(false);
		     extendedMetadata.setLocal(true);


			ExtendedMetadataDelegate provider = new ExtendedMetadataDelegate(metadataMemoryProvider, extendedMetadata);
//			provider.setMetadataTrustCheck(false);

			providers.add(provider);


		} catch (Exception e) {
			String errStr = "Creation of " + tenant.getTenantName() + " sp provider is failed. Skipping this sp.";
			logger.error(errStr, e);
			throw new MetadataProviderCreationException(errStr, e);
		}

		// Configure IDP via ExtendedMetadataDelegate
		try {
			InputStream in = IOUtils.toInputStream(samlAuthentication.getIdentityProviderMetadata());
            Document inCommonMDDoc = parserPool.parse(in);
            Element idpMetadata = inCommonMDDoc.getDocumentElement();
            domMetadataProvider = new DOMMetadataProvider(idpMetadata);
			domMetadataProvider.initialize();

			ExtendedMetadata extendedMetadata = new ExtendedMetadata();

			ExtendedMetadataDelegate provider = new ExtendedMetadataDelegate(domMetadataProvider, extendedMetadata);

			providers.add(provider);


		} catch (Exception e) {
			String errStr = "Creation of idp provider is failed. Skipping this idp.";
			logger.error(errStr, e);
			throw new MetadataProviderCreationException(errStr, e);
		}

		return providers;

	}

    @Override
    public boolean isProviderExisting(String entityId) {
		boolean isProviderExisitng = false;

		List<MetadataProvider> providers = metadataManager.getProviders();

		// It is possible to have a few IDPs with the same entityID. SPs are unique.
		for(MetadataProvider provider : providers) {
			try {
				if(provider.getEntityDescriptor(entityId) != null) {
					isProviderExisitng = true;
					break;
				}
			} catch (MetadataProviderException e) {
				if (logger.isErrorEnabled()) {
					logger.error(e.getMessage());
				}
			}
		}

		return isProviderExisitng;
    }
}

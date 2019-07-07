package com.vidyo.service.authentication.saml;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.saml.metadata.MetadataManager;

import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.bo.authentication.SamlAuthenticationState;
import com.vidyo.bo.authentication.SamlAuthenticationStateChange;
import com.vidyo.service.exceptions.MetadataProviderCreationException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class SamlAuthMessageListener implements MessageListener {

	protected final Logger logger = LoggerFactory.getLogger(SamlAuthMessageListener.class.getName());

	@Autowired
	MetadataManager metadataManager;
	
	private SamlAuthenticationService samlAuthenticationService;

	public void setSamlAuthenticationService(SamlAuthenticationService samlAuthenticationService) {
		this.samlAuthenticationService = samlAuthenticationService;
	}
	
	public void onMessage(Message message) {
		try {
			SamlAuthenticationStateChange m = (SamlAuthenticationStateChange)((ActiveMQObjectMessage) message).getObject();

			logger.debug("Got message - {}", m.toString());

			int tenantID = m.getTenantID();
			SamlAuthentication samlAuthentication = m.getSamlAuthentication();
			String oldSpEntityId = m.getOldSpEntityId();
			
			if (m.getSamlAuthenticationState() == SamlAuthenticationState.ADD) {
				try {
					addMetadataProviders(tenantID, samlAuthentication);
				} catch (Exception e) {
					return;
				} 
				
			} else {
				List<MetadataProvider> providers = metadataManager.getProviders();
				List<MetadataProvider> removingProviders = new ArrayList<MetadataProvider>();

				EntityDescriptor spMetadataEntityDescriptor = null;
				try {
					spMetadataEntityDescriptor = samlAuthenticationService.generateServiceProviderMetadataAsEntityDescriptor(tenantID, samlAuthentication);
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error(e.getMessage());
					}
					return;
				}
				
				String spEntityID = spMetadataEntityDescriptor.getEntityID();
				String idpEntityID = samlAuthenticationService.getEntityIdFromMetadataXml(samlAuthentication.getIdentityProviderMetadata());
				
				// It is possible to have a few IDPs with the same entityID. SPs are unique.
				boolean isIdpEntitySelectedToRemove = false;
				for(MetadataProvider provider : providers) {
					try {
						if(provider.getEntityDescriptor(spEntityID) != null) {
							removingProviders.add(provider);

						}
						
						if(oldSpEntityId != null && provider.getEntityDescriptor(oldSpEntityId) != null) {
							removingProviders.add(provider);
						}
						
						if(provider.getEntityDescriptor(idpEntityID) != null && !isIdpEntitySelectedToRemove) {
							removingProviders.add(provider);
							isIdpEntitySelectedToRemove = true;
						}
					} catch (MetadataProviderException e) {
						if (logger.isErrorEnabled()) {
							logger.error(e.getMessage());
						}
						return;
					}
				}
				
				for(MetadataProvider provider : removingProviders) {
					try {
						metadataManager.removeMetadataProvider(provider);
					} catch (IllegalArgumentException e) {
						if (logger.isErrorEnabled()) {
							logger.error(e.getMessage());
						}
						return;
					}
				}
				
				if (m.getSamlAuthenticationState() == SamlAuthenticationState.UPDATE) {
					try {
						addMetadataProviders(tenantID, samlAuthentication);
					} catch (Exception e) {
						return;
					}
				}
			}
			
			metadataManager.refreshMetadata();
			logger.warn("Tomcat node : " + ManagementFactory.getRuntimeMXBean().getName() + " . SAML metadata is refreshed.");
		} catch (JMSException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
		}
	}
	
	private void addMetadataProviders(int tenantID, SamlAuthentication samlAuthentication) 
			throws MetadataProviderCreationException, MetadataProviderException{
		List<MetadataProvider> createdProviders;
		try {
			createdProviders = samlAuthenticationService.createProvidersForSPandIPD(tenantID, samlAuthentication);
		} catch (MetadataProviderCreationException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
			throw e;
		}
		for(MetadataProvider provider : createdProviders) {
			try {
				metadataManager.addMetadataProvider(provider);
			} catch (MetadataProviderException e) {
				if (logger.isErrorEnabled()) {
					logger.error(e.getMessage());
				}
				throw e;
			}
		}
	}

}

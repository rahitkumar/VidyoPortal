package com.vidyo.framework.listeners;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.saml2.metadata.provider.MetadataProviderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.saml.metadata.MetadataManager;

import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.db.ISystemDao;
import com.vidyo.service.authentication.saml.SamlAuthenticationService;
import com.vidyo.service.exceptions.MetadataProviderCreationException;

public class AppContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

	private final Logger log = LoggerFactory.getLogger(AppContextRefreshedEventListener.class);
	
	@Autowired
	MetadataManager metadataManager;
	
	private SamlAuthenticationService samlAuthenticationService;
	
	public void setSamlAuthenticationService(SamlAuthenticationService samlAuthenticationService) {
		this.samlAuthenticationService = samlAuthenticationService;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent() != null) {
			return;
		}
		
		List<MetadataProvider> providers =  metadataManager.getProviders();

		Map<Integer, SamlAuthentication> samlAuthentications = samlAuthenticationService.getSamlAuthenticationByTenant();
		List<MetadataProvider> createdProviders;
		for(Entry<Integer, SamlAuthentication> samlAuthenticationEntry : samlAuthentications.entrySet()) {
			try {
				createdProviders = samlAuthenticationService.createProvidersForSPandIPD(samlAuthenticationEntry.getKey(), 
						samlAuthenticationEntry.getValue());
			} catch (MetadataProviderCreationException e) {
				log.error(e.getMessage());
				continue;
			}
			providers.addAll(createdProviders);

		}
		
		try {
			metadataManager.setProviders(providers);
		} catch (MetadataProviderException e) {
			log.error("Setting of providers is failed.", e);
			return;
		}
		
		metadataManager.refreshMetadata();
	}

}

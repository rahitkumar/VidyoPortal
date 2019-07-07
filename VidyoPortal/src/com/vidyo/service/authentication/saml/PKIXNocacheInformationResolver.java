/**
 * 
 */
package com.vidyo.service.authentication.saml;

import java.util.Collection;

import org.opensaml.security.MetadataCredentialResolver;
import org.opensaml.xml.security.x509.PKIXValidationInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.saml.key.KeyManager;
import org.springframework.security.saml.metadata.MetadataManager;
import org.springframework.security.saml.trust.PKIXInformationResolver;

/**
 * @author ganesh
 *
 */
public class PKIXNocacheInformationResolver extends PKIXInformationResolver{
	
	protected static final Logger logger = LoggerFactory.getLogger(PKIXNocacheInformationResolver.class);

	/**
	 * 
	 * @param metadataResolver
	 * @param metadataProvider
	 * @param keyManager
	 */
	public PKIXNocacheInformationResolver(MetadataCredentialResolver metadataResolver, MetadataManager metadataProvider,
			KeyManager keyManager) {
		super(metadataResolver, metadataProvider, keyManager);
	}

	/**
	 * 
	 */
	@Override
	protected void cacheCredentials(MetadataCacheKey cacheKey, Collection<PKIXValidationInformation> credentials) {
		logger.warn("No caching of PKIX Crdentials");
	}

}

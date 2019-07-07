/**
 * 
 */
package com.vidyo.service.authentication.saml;

import java.util.Collection;

import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.security.MetadataCredentialResolver;
import org.opensaml.xml.security.credential.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ganesh
 *
 */
public class MetadataNoCacheCredentialResolver extends MetadataCredentialResolver {
	
	protected static final Logger logger = LoggerFactory.getLogger(MetadataNoCacheCredentialResolver.class);

	public MetadataNoCacheCredentialResolver(MetadataProvider metadataProvider) {
		super(metadataProvider);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.opensaml.security.MetadataCredentialResolver#cacheCredentials(org.opensaml.security.MetadataCredentialResolver.MetadataCacheKey, java.util.Collection)
	 */
	@Override
	protected void cacheCredentials(MetadataCacheKey cacheKey, Collection<Credential> credentials) {
		logger.warn("No caching of Metadata Crdentials");
	}
	
	

}

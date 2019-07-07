package com.vidyo.rest.controllers.admin;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * Form able to store UI data related to metadata.
 */
public class SamlMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String SECURITY_PROFILE_METAIOP = "METAIOP";
    public static final String SECURITY_PROFILE_PKIX = "PKIX";
    public static final String SSLTLS_PROFILE_METAIOP = "METAIOP";
    public static final String SSLTLS_PROFILE_PKIX = "PKIX";

    
    
	@JsonIgnore
    private boolean store;
	@NotNull
	@Length(min=1)
    private String entityId;
	@NotNull
	@Length(min=1)
    private String securityProfile;
	@NotNull
	@Length(min=1)
    private String sslSecurityProfile;
    @JsonIgnore
    private String sslHostnameVerification;
    @JsonIgnore
    private String baseURL;
    @JsonIgnore
    private String alias;
    private boolean signMetadata=false;
	@NotNull
	@Length(min=1)
    private String base64EncodedMetadata;
    @JsonIgnore
    private String configuration;
    @JsonIgnore
    private String signingAlgorithm;

  
    @JsonIgnore
    private String signingKey;
    @JsonIgnore
    private String encryptionKey;
    @JsonIgnore
    private String tlsKey;
    @JsonIgnore
    private boolean local;
    @JsonIgnore
    private boolean includeDiscovery = true;
    @JsonIgnore
    private boolean includeDiscoveryExtension = false;
    @JsonIgnore
    private String customDiscoveryURL;
    @JsonIgnore
    private String customDiscoveryResponseURL;

    @JsonIgnore
    private boolean requestSigned = true;
    @JsonIgnore
    private boolean wantAssertionSigned;
    @JsonIgnore
    private boolean requireLogoutRequestSigned;
    @JsonIgnore
    private boolean requireLogoutResponseSigned;
    @JsonIgnore
    private boolean requireArtifactResolveSigned;

    public SamlMetadata() {
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public boolean isSignMetadata() {
        return signMetadata;
    }

    public void setSignMetadata(boolean signMetadata) {
        this.signMetadata = signMetadata;
    }

    public boolean isRequestSigned() {
        return requestSigned;
    }

    public void setRequestSigned(boolean requestSigned) {
        this.requestSigned = requestSigned;
    }

    public boolean isWantAssertionSigned() {
        return wantAssertionSigned;
    }

    public void setWantAssertionSigned(boolean wantAssertionSigned) {
        this.wantAssertionSigned = wantAssertionSigned;
    }

    public boolean isRequireLogoutRequestSigned() {
        return requireLogoutRequestSigned;
    }

    public void setRequireLogoutRequestSigned(boolean requireLogoutRequestSigned) {
        this.requireLogoutRequestSigned = requireLogoutRequestSigned;
    }

    public boolean isRequireLogoutResponseSigned() {
        return requireLogoutResponseSigned;
    }

    public void setRequireLogoutResponseSigned(boolean requireLogoutResponseSigned) {
        this.requireLogoutResponseSigned = requireLogoutResponseSigned;
    }

    public boolean isRequireArtifactResolveSigned() {
        return requireArtifactResolveSigned;
    }

    public void setRequireArtifactResolveSigned(boolean requireArtifactResolveSigned) {
        this.requireArtifactResolveSigned = requireArtifactResolveSigned;
    }

    public boolean isStore() {
        return store;
    }

    public void setStore(boolean store) {
        this.store = store;
    }

    public String getBase64EncodedMetadata() {
		return base64EncodedMetadata;
	}

	public void setBase64EncodedMetadata(String base64EncodedMetadata) {
		this.base64EncodedMetadata = base64EncodedMetadata;
	}

	public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getSecurityProfile() {
        return securityProfile;
    }

    public void setSecurityProfile(String securityProfile) {
        this.securityProfile = securityProfile;
    }

    public String getSslSecurityProfile() {
        return sslSecurityProfile;
    }

    public void setSslSecurityProfile(String sslSecurityProfile) {
        this.sslSecurityProfile = sslSecurityProfile;
    }

    public String getTlsKey() {
        return tlsKey;
    }

    public void setTlsKey(String tlsKey) {
        this.tlsKey = tlsKey;
    }

    public boolean isIncludeDiscovery() {
        return includeDiscovery;
    }

    public void setIncludeDiscovery(boolean includeDiscovery) {
        this.includeDiscovery = includeDiscovery;
    }

    public boolean isIncludeDiscoveryExtension() {
        return includeDiscoveryExtension;
    }

    public void setIncludeDiscoveryExtension(boolean includeDiscoveryExtension) {
        this.includeDiscoveryExtension = includeDiscoveryExtension;
    }

   
    public String getCustomDiscoveryURL() {
        return customDiscoveryURL;
    }

    public void setCustomDiscoveryURL(String customDiscoveryURL) {
        this.customDiscoveryURL = customDiscoveryURL;
    }

    public String getCustomDiscoveryResponseURL() {
        return customDiscoveryResponseURL;
    }

    public void setCustomDiscoveryResponseURL(String customDiscoveryResponseURL) {
        this.customDiscoveryResponseURL = customDiscoveryResponseURL;
    }

    public String getSslHostnameVerification() {
        return sslHostnameVerification;
    }

    public void setSslHostnameVerification(String sslHostnameVerification) {
        this.sslHostnameVerification = sslHostnameVerification;
    }

    public String getSigningAlgorithm() {
        return signingAlgorithm;
    }

    public void setSigningAlgorithm(String signingAlgorithm) {
        this.signingAlgorithm = signingAlgorithm;
    }

}

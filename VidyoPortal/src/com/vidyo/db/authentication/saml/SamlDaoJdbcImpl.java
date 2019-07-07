package com.vidyo.db.authentication.saml;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.authentication.SamlAuthentication;
import com.vidyo.bo.authentication.SamlProvisionType;
import com.vidyo.db.SystemDaoJdbcImpl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SamlDaoJdbcImpl extends SystemDaoJdbcImpl implements ISamlDao {
    private static final String SAML_AUTH_FLAG = "SAML_AUTH_FLAG";
    private static final String SAML_IDP_METADATA = "SAML_IDP_METADATA";
    private static final String SAML_SP_SECURITY_PROFILE = "SAML_SP_SECURITY_PROFILE";
    private static final String SAML_SP_SSL_PROFILE = "SAML_SP_SSL_PROFILE";
    private static final String SAML_SP_SIGN_METADATA = "SAML_SP_SIGN_METADATA";
    private static final String SAML_SP_PROTOCOL = "SAML_SP_PROTOCOL";
    private static final String SAML_AUTH_PROVISION_TYPE = "SAML_AUTH_PROVISION_TYPE";
    private static final String SAML_IDP_ATTRIBUTE_FOR_USERNAME = "SAML_IDP_ATTRIBUTE_FOR_USERNAME";
    private static final String SAML_SP_ENTITY_ID = "SAML_SP_ENTITY_ID";

    protected final Logger logger = LoggerFactory.getLogger(SamlDaoJdbcImpl.class.getName());

    @Override
    public SamlAuthentication getSamlAuthentication(int tenantID) {
        if (isSamlAuthenticationEnabled(tenantID)) {
            SamlAuthentication samlAuth = new SamlAuthentication();
            samlAuth.setIdentityProviderMetadata(getConfigValue(tenantID, SAML_IDP_METADATA));
            samlAuth.setSpEntityId(getConfigValue(tenantID, SAML_SP_ENTITY_ID));
            samlAuth.setSecurityProfile(getConfigValue(tenantID, SAML_SP_SECURITY_PROFILE));
            samlAuth.setSslTlsProfile(getConfigValue(tenantID, SAML_SP_SSL_PROFILE));
            samlAuth.setSignMetadata("YES".equals(getConfigValue(tenantID, SAML_SP_SIGN_METADATA)));
            samlAuth.setHttps("YES".equalsIgnoreCase(getConfigValue(tenantID, SAML_SP_PROTOCOL)));
            
            String authProvisionTypeStr = getConfigValue(tenantID, SAML_AUTH_PROVISION_TYPE);
            if(authProvisionTypeStr == null || authProvisionTypeStr.isEmpty()) {
                samlAuth.setAuthProvisionType(SamlProvisionType.SAML);
            } else {
                SamlProvisionType samlProvisionType = SamlProvisionType.get(Integer.parseInt(authProvisionTypeStr));
                samlAuth.setAuthProvisionType(samlProvisionType);
                if(samlProvisionType == SamlProvisionType.LOCAL) {
                    samlAuth.setIdpAttributeForUsername(getConfigValue(tenantID, SAML_IDP_ATTRIBUTE_FOR_USERNAME));
                }
            }
            return samlAuth;
        }
        return null;
    }

    @Override
    public void saveSamlAuthentication(int tenantID, SamlAuthentication samlAuth) {
        updateConfiguration(tenantID, SAML_IDP_METADATA, samlAuth.getIdentityProviderMetadata());
        updateConfiguration(tenantID, SAML_SP_ENTITY_ID, samlAuth.getSpEntityId());
        updateConfiguration(tenantID, SAML_SP_SECURITY_PROFILE, samlAuth.getSecurityProfile());
        updateConfiguration(tenantID, SAML_SP_SSL_PROFILE, samlAuth.getSslTlsProfile());
        updateConfiguration(tenantID, SAML_SP_SIGN_METADATA, (samlAuth.isSignMetadata() ? "YES" : "NO"));
        updateConfiguration(tenantID, SAML_SP_PROTOCOL, (samlAuth.isHttps() ? "YES" : "NO"));
        updateConfiguration(tenantID, SAML_AUTH_PROVISION_TYPE, String.valueOf(samlAuth.getAuthProvisionType().getValue()));
        if(samlAuth.getAuthProvisionType() == SamlProvisionType.LOCAL) {
            updateConfiguration(tenantID, SAML_IDP_ATTRIBUTE_FOR_USERNAME, samlAuth.getIdpAttributeForUsername());
        }
    }

    @Override
    public boolean isSamlAuthenticationEnabled(int tenantID) {
        return "YES".equals(getConfigValue(tenantID, SAML_AUTH_FLAG));
    }

    @Override
    public void saveSamlAuthenticationFlag(int tenantID, boolean flag) {
        if (flag) {
            updateConfiguration(tenantID, SAML_AUTH_FLAG, "YES");
        } else {
            updateConfiguration(tenantID, SAML_AUTH_FLAG, "NO");
        }
    }
    
    private String getConfigValue(int tenantID, String configurationName){
		Configuration config = getConfiguration(tenantID, configurationName);
		if (config == null || StringUtils.isBlank(config.getConfigurationValue())){
			return "";
		}
		return config.getConfigurationValue();
		
	}
}

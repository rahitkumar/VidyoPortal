package com.vidyo.db.authentication.saml;


import com.vidyo.bo.authentication.SamlAuthentication;

public interface ISamlDao {

    public SamlAuthentication getSamlAuthentication(int tenantID);
    public void saveSamlAuthentication(int tenantID, SamlAuthentication samlAuth);

    public boolean isSamlAuthenticationEnabled(int tenantID);
    public void saveSamlAuthenticationFlag(int tenantID, boolean flag);

}

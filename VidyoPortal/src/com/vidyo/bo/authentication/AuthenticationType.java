package com.vidyo.bo.authentication;


public enum AuthenticationType {
    INTERNAL, // portal db
    LDAP,
    WS, // a client that implements a web service whose interface we defined
    SAML_BROWSER, // SAML auth via a web browser
    CAC,//added for cac,
    WS_REST//added for cac
}

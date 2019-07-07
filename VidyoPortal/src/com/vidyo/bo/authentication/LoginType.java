package com.vidyo.bo.authentication;


public enum LoginType {
    PORTAL, // username and password accepted by portal and possibly relayed elsewhere
    EXTERNAL // username and password not accepted by portal, but portal receives result of authentication
}

package com.vidyo.service.ldap;

import com.vidyo.bo.Member;
import com.vidyo.bo.ldap.TenantLdapAttributeMapping;
import com.vidyo.service.exceptions.AccessRestrictedException;
import com.vidyo.service.exceptions.ExtensionExistException;
import com.vidyo.service.exceptions.InvalidUserNameException;
import com.vidyo.service.exceptions.SeatLicenseExpiredException;
import com.vidyo.service.exceptions.UserExistException;

import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

public interface ILdapUserToMemberAttributesMapper {
    public int importLdapMember (Member member) throws UserExistException, ExtensionExistException,
        SeatLicenseExpiredException, InvalidUserNameException, AccessRestrictedException;
    /**
     * @deprecated
     * @param attributes
     * @return
     * @throws NamingException
     */
    @Deprecated
    public Member getMemberFromAttributes(Attributes attributes) throws NamingException;
    public Member getMemberFromAttributes(int tenantID, Attributes attributes) throws NamingException;

    /**
     * @deprecated
     * @return
     */
    @Deprecated
    public String[] getReturnedAttributes();
    public Map<String, String> getPortalToADAttributeMappings(String userName,int tenantID);
    public String[] getReturnedAttributes( int tenantID);
    public String getLdapAttributeValue(Attributes attributes, TenantLdapAttributeMapping attributeMapping);
}
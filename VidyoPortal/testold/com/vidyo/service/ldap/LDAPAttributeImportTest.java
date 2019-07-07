package com.vidyo.service.ldap;

import static org.junit.Assert.*;

import java.util.Arrays;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.LdapName;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.db.IGroupDao;
import com.vidyo.db.IMemberDao;
import com.vidyo.db.ldap.ITenantLdapAttributesMappingDao;
import com.vidyo.framework.context.TenantContext;

public class LDAPAttributeImportTest {
	 String ldapAdServer = "";
     String ldapSearchBase = "";
    
     String ldapUsername = "";
     String ldapPassword = "";
    
     String ldapAccountToLookup = "";
    Attributes rc=null;
    String dn = null;
	LdapName baseName = null;
	LdapContextSource contextSource = null;
	DirContext ctx =null;
	@Before
	public void before(){
		 ldapAdServer = "ldap://172.16.42.201:389";
		     ldapSearchBase = "DC=vidyoqa,DC=lo";
		    
		 ldapUsername = "CN=ldapbind,OU=Service Accounts,DC=vidyoqa,DC=lo";
		    ldapPassword = "1qaz@WSX3edc$RFV";
		    ldapAccountToLookup = "myOtherLdapUsername";
			contextSource = new LdapContextSource();
			contextSource.setUrl(ldapAdServer);
			contextSource.setUserDn(ldapUsername);
			contextSource.setPassword(ldapPassword);
			contextSource.setAnonymousReadOnly(true);
			contextSource.setPooled(false);
			contextSource.setCacheEnvironmentProperties(false);
			contextSource.afterPropertiesSet();
			 ctx = contextSource.getContext(ldapUsername,
					ldapPassword);
			
	}
	
	@Test
   public  void validate_modifytimestamp_attr() throws NamingException{
	
	String[] ldapAttr = { "modifytimestamp" };

	
	
	
		baseName = LdapUtils.newLdapName(ldapSearchBase);
	
	String filter ="(|(userPrincipalName=<>)(samaccountname=<>))";
	if (filter.contains("<>")) {
		filter = filter.replace("<>".subSequence(0, "<>".length()), "qa1");
	}
	SearchControls ctls = new SearchControls();
	ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
	ctls.setReturningAttributes(ldapAttr);
	NamingEnumeration<SearchResult> result = ctx.search(baseName, filter, ctls);
	// copying the existing code. not sure why there is a loop.
	while (result.hasMoreElements()) {
		SearchResult searchResult = result.nextElement();
		dn = searchResult.getNameInNamespace();
		 rc = searchResult.getAttributes();

	}
	assertNotNull(rc);
	Attribute modifytimestamp = rc.get("modifytimestamp");
	
	assertNotNull(modifytimestamp);
	
	
	
	}
	@Test
	   public  void validate_some_never_exist_attr() throws NamingException{
		
		String[] ldapAttr = { "blabla" };

		
		
		
			baseName = LdapUtils.newLdapName(ldapSearchBase);
		
		String filter ="(|(userPrincipalName=<>)(samaccountname=<>))";
		if (filter.contains("<>")) {
			filter = filter.replace("<>".subSequence(0, "<>".length()), "qa1");
		}
		SearchControls ctls = new SearchControls();
		ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
		ctls.setReturningAttributes(ldapAttr);
		NamingEnumeration<SearchResult> result = ctx.search(baseName, filter, ctls);
		// copying the existing code. not sure why there is a loop.
		while (result.hasMoreElements()) {
			SearchResult searchResult = result.nextElement();
			dn = searchResult.getNameInNamespace();
			 rc = searchResult.getAttributes();

		}
		assertNotNull(rc);
		Attribute modifytimestamp = rc.get("blabla");
		
		assertNull(modifytimestamp);
		
		
		
		}


}

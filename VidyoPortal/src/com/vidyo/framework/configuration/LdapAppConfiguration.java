package com.vidyo.framework.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.scheduling.annotation.EnableAsync;

import com.vidyo.service.ldap.LdapService;

@Configuration
@PropertySource(value="file:/usr/local/tomcat/repo/vidyoportal/portal.properties")
@ComponentScan("com.vidyo.service.ldap")
@EnableAsync
public class LdapAppConfiguration {

@Bean
public LdapService ldapService(){
	LdapService ldapService=new LdapService();

	return  ldapService;
}
@Bean
public static PropertySourcesPlaceholderConfigurer getPropertyConfig() {
	return new PropertySourcesPlaceholderConfigurer();
}

@Bean
public LdapContextSource contextSource () {

	LdapContextSource contextSource = new LdapContextSource();
	contextSource.setUrl("ldap://");
    contextSource.setBase("");
    contextSource.setUserDn("");
    contextSource.setPassword("");
	
       return contextSource;
}
/**
 *  return the LdapContextSource object. Need to pass the ldapAuthentication object with all connection properties like url,password etc.
 * @return contextSource
 */

//@Bean
//public LdapContextSource contextSource (LdapAuthentication ldapAuth) {
//
//	LdapContextSource contextSource = new LdapContextSource();
//	contextSource.setUrl(ldapAuth.getLdapurl());
//	contextSource.setUserDn(ldapAuth.getLdapusername());
//	contextSource.setPassword(ldapAuth.getLdappassword());
//	contextSource.setAnonymousReadOnly(true);
//	contextSource.setPooled(false);
//	contextSource.setCacheEnvironmentProperties(false);
//	contextSource.afterPropertiesSet();
//       return contextSource;
//}
//
///**
// * 
// * @param contextSource.Need to pass the ldapAuthentication object with all connection properties like url,password etc.
// * @return
// */
//@Bean
//public LdapTemplate ldapTemplate(LdapAuthentication ldapAuth) {
//    return new LdapTemplate(contextSource(ldapAuth));        
//}

}

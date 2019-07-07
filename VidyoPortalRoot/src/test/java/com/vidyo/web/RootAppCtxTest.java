package com.vidyo.web;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("file:web") // root web folder
@ContextConfiguration(
		value = {
			"file:web/WEB-INF/vidyo-portal-root-app-service.xml",
			"file:web/WEB-INF/vidyo-portal-root-service.xml",
			"file:web/WEB-INF/vidyo-portal-root-servlet.xml",
			"file:web/WEB-INF/vidyo-portal-root-interceptors.xml",
			"file:web/WEB-INF/vidyo-portal-root-web-services-v11.xml",
			"file:web/WEB-INF/saml-servlet.xml",
			"file:web/WEB-INF/vidyo-portal-root-security.xml"
		},
		initializers = { RootAppCtxTest.class }
)
@Ignore
public class RootAppCtxTest implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Autowired
	private ConfigurableApplicationContext applicationContext;
	
	public RootAppCtxTest() throws Exception {
		setup();
	}

	public void setup() throws Exception {
		 SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
		 builder.bind("java:comp/env/jdbc/portal2", Mockito.mock(DataSource.class));
		 builder.bind("java:comp/env/jdbc/portal2audit", Mockito.mock(DataSource.class));
		 builder.activate();
	}

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		//applicationContext.addApplicationListener(this);
		applicationContext.addBeanFactoryPostProcessor(new BeanFactoryPostProcessor() {
			public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
				String listenerName = "appContextRefreshedEventListener";
				((org.springframework.beans.factory.support.DefaultListableBeanFactory)beanFactory).removeBeanDefinition(listenerName);
			}
		});
	}
	
	@Test(expected = NoSuchBeanDefinitionException.class) 
	public void testBeanNotFound() {
		applicationContext.getBean("test");
	}
	
	@Test
	public void testApplicationBeans() {
		Assert.assertNotNull(applicationContext.getBean("dataSource"));
		Assert.assertNotNull(applicationContext.getBean("auditDataSource"));
		Assert.assertNotNull(applicationContext.getBean("propertyConfigurer"));
		Assert.assertNotNull(applicationContext.getBean("messageSource"));
		Assert.assertNotNull(applicationContext.getBean("localeResolver"));
		Assert.assertNotNull(applicationContext.getBean("conferenceAppService"));
		Assert.assertNotNull(applicationContext.getBean("namedParamJdbcTemplate"));
		Assert.assertNotNull(applicationContext.getBean("jdbcTemplate"));
		Assert.assertNotNull(applicationContext.getBean("auditJdbcTemplate"));
		Assert.assertNotNull(applicationContext.getBean("applicationContextProvder"));
		Assert.assertNotNull(applicationContext.getBean("activeMQConnectionFactory"));
		Assert.assertNotNull(applicationContext.getBean("jmsTemplate"));
		Assert.assertNotNull(applicationContext.getBean("jmsProducerTemplate"));
		Assert.assertNotNull(applicationContext.getBean("emailService"));
		Assert.assertNotNull(applicationContext.getBean("loginDao"));
		Assert.assertNotNull(applicationContext.getBean("loginService"));
		Assert.assertNotNull(applicationContext.getBean("userDao"));
		Assert.assertNotNull(applicationContext.getBean("userService"));
		Assert.assertNotNull(applicationContext.getBean("memberDao"));
		Assert.assertNotNull(applicationContext.getBean("memberService"));
		Assert.assertNotNull(applicationContext.getBean("roomDao"));
		Assert.assertNotNull(applicationContext.getBean("roomService"));
		Assert.assertNotNull(applicationContext.getBean("securityService"));
		Assert.assertNotNull(applicationContext.getBean("groupDao"));
		Assert.assertNotNull(applicationContext.getBean("groupService"));
		Assert.assertNotNull(applicationContext.getBean("licenseDao"));
		Assert.assertNotNull(applicationContext.getBean("licensingService"));
		Assert.assertNotNull(applicationContext.getBean("endpointUploadDao"));
		Assert.assertNotNull(applicationContext.getBean("endpointUploadService"));
		Assert.assertNotNull(applicationContext.getBean("conferenceDao"));
		Assert.assertNotNull(applicationContext.getBean("conferenceService"));
		Assert.assertNotNull(applicationContext.getBean("restTemplate"));
		Assert.assertNotNull(applicationContext.getBean("connectionManager"));
		Assert.assertNotNull(applicationContext.getBean("systemDao"));
		Assert.assertNotNull(applicationContext.getBean("systemService"));
		Assert.assertNotNull(applicationContext.getBean("tenantDao"));
		Assert.assertNotNull(applicationContext.getBean("tenantService"));
		Assert.assertNotNull(applicationContext.getBean("tenantConfigurationDao"));
		Assert.assertNotNull(applicationContext.getBean("endpointDao"));
		Assert.assertNotNull(applicationContext.getBean("endpointService"));
		Assert.assertNotNull(applicationContext.getBean("samlAuthenticationService"));
		Assert.assertNotNull(applicationContext.getBean("appEventListener"));
		Assert.assertNotNull(applicationContext.getBean("statusNotificationDao"));
		Assert.assertNotNull(applicationContext.getBean("statusNotificationService"));
		Assert.assertNotNull(applicationContext.getBean("memberLoginHistoryDao"));
		Assert.assertNotNull(applicationContext.getBean("memberLoginHistoryService"));
		Assert.assertNotNull(applicationContext.getBean("userAuthDao"));
		Assert.assertNotNull(applicationContext.getBean("entityManagerFactory"));
		Assert.assertNotNull(applicationContext.getBean("transactionManager"));
		
		Assert.assertNotNull(applicationContext.getBean("testCallService"));
		Assert.assertNotNull(applicationContext.getBean("statusService"));
		Assert.assertNotNull(applicationContext.getBean("vidyoPortalGuestServiceEndPoint"));
		Assert.assertNotNull(applicationContext.getBean("vidyoRecorderServiceEndPoint"));
		Assert.assertNotNull(applicationContext.getBean("vidyoReplayServiceEndPoint"));
		Assert.assertNotNull(applicationContext.getBean("configurationApiServiceEndPoint"));
		Assert.assertNotNull(applicationContext.getBean("configurationServiceEndPoint"));
		Assert.assertNotNull(applicationContext.getBean("vidyoLicenseServiceEndPoint"));
		Assert.assertNotNull(applicationContext.getBean("vidyoDesktopServiceEndPoint"));
		Assert.assertNotNull(applicationContext.getBean("vidyoPortalUserServiceEndPoint"));
		Assert.assertNotNull(applicationContext.getBean("vidyoPortalAdminServiceEndPoint"));
		Assert.assertNotNull(applicationContext.getBean("vidyoPortalServiceEndPoint"));
		Assert.assertNotNull(applicationContext.getBean("portalWSController"));
	}
}

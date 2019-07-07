package com.vidyo.framework.listeners;

import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.List;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import com.vidyo.framework.security.authentication.VidyoUserDetails;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class SuperDeleteMessageListener implements MessageListener {

	protected final Logger logger = LoggerFactory.getLogger(SuperDeleteMessageListener.class.getName());

	private SessionRegistry sessionRegistry;

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
	
	public void onMessage(Message message) {
		try {
			String superName = (String)((ActiveMQObjectMessage) message).getObject();
			
			logger.debug("Got message - {}", superName);
			
			List<SessionInformation> sessionInfoList = null;
			List<Object> principals = sessionRegistry.getAllPrincipals();
			for(Object principal : principals) {
				if(((VidyoUserDetails)principal).getUsername().equals(superName)) {
					boolean isSuperUser = false;
					Collection<GrantedAuthority> authorities = ((VidyoUserDetails)principal).getAuthorities();
					for(GrantedAuthority authority : authorities) {
						if(authority.getAuthority().equalsIgnoreCase("ROLE_SUPER")) {
							isSuperUser = true;
							break;
						}
					}
					if(isSuperUser) {
						sessionInfoList = sessionRegistry.getAllSessions(principal, false);
						break;
					}
				}
			}
			
			if(sessionInfoList != null) {
				for(SessionInformation sessionInfo : sessionInfoList) {
					sessionInfo.expireNow();
				}
			}

			logger.warn("Tomcat node : " + ManagementFactory.getRuntimeMXBean().getName() + " . Deleted super session is expired.");
		} catch (JMSException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
		}
	}
}

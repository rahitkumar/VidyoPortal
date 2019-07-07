package com.vidyo.framework.listeners;

import java.lang.management.ManagementFactory;
import java.util.List;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;

import com.vidyo.bo.MemberRoleEnum;
import com.vidyo.bo.member.MemberKey;
import com.vidyo.framework.security.authentication.VidyoUserDetails;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class AdminOrOperatorDeleteMessageListener implements MessageListener {

	protected final Logger logger = LoggerFactory.getLogger(AdminOrOperatorDeleteMessageListener.class.getName());

	private SessionRegistry sessionRegistry;

	public void setSessionRegistry(SessionRegistry sessionRegistry) {
		this.sessionRegistry = sessionRegistry;
	}
	
	public void onMessage(Message message) {
		try {
			MemberKey memberKey = (MemberKey)((ActiveMQObjectMessage) message).getObject();
			
			logger.debug("Got message - {}, {}", memberKey.getTenantID(), memberKey.getMemberID());
			
			if(memberKey.getRoleID() != MemberRoleEnum.ADMIN.getMemberRoleID() && 
					memberKey.getRoleID() != MemberRoleEnum.OPERATOR.getMemberRoleID()) {
				logger.info("Member {} has not role admin or operator. The session will not be expired ADMIN application", memberKey.getMemberID());
				return;
			}
			
			List<SessionInformation> sessionInfoList = null;
			List<Object> principals = sessionRegistry.getAllPrincipals();
			for(Object principal : principals) {
				if(((VidyoUserDetails)principal).getMemberId() == memberKey.getMemberID()) {
					sessionInfoList = sessionRegistry.getAllSessions(principal, false);
					break;
				}
			}
			
			if(sessionInfoList != null) {
				for(SessionInformation sessionInfo : sessionInfoList) {
					sessionInfo.expireNow();
				}
			}

			logger.warn("Tomcat node : " + ManagementFactory.getRuntimeMXBean().getName() + " . Deleted admin or operator session is expired.");
		} catch (JMSException e) {
			if (logger.isErrorEnabled()) {
				logger.error(e.getMessage());
			}
		}
	}
}

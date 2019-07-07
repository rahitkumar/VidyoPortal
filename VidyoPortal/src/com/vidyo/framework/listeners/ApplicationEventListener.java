/**
 * 
 */
package com.vidyo.framework.listeners;

import java.util.Calendar;

import com.vidyo.utils.VendorUtils;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

import com.vidyo.bo.Tenant;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.service.ITenantService;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.framework.security.authentication.VidyoUserDetails;
import com.vidyo.service.transaction.TransactionService;

/**
 * Event Listener class to listen for application events.
 * 
 * @author Ganesh
 * 
 */
public class ApplicationEventListener implements
		ApplicationListener {

	/**
	 * 
	 */
	protected Logger logger = Logger.getLogger(ApplicationEventListener.class);

	/**
	 * 
	 */
	private TransactionService transactionService;

	/**
	 * 
	 */
	private ITenantService tenantService;

	/**
	 * @param tenantService
	 *            the tenantService to set
	 */
	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * 
	 */
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		// Listen for HttpSession events
		/*
		 * If the HttpSession is invalidated/destroyed, the user details are
		 * captured and logged in the TransactionHistory table
		 */
		if (event instanceof HttpSessionDestroyedEvent) {
			HttpSessionDestroyedEvent sessionDestroyedEvent = (HttpSessionDestroyedEvent) event;
			SecurityContext securityContext = (SecurityContext) sessionDestroyedEvent
					.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			if (securityContext != null
					&& securityContext.getAuthentication() != null
					&& securityContext.getAuthentication().getDetails() != null) {
				TransactionHistory transactionHistory = new TransactionHistory();
				if(securityContext.getAuthentication().getDetails() != null && 
						securityContext.getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
					transactionHistory
						.setSourceIP(((WebAuthenticationDetails) securityContext
								.getAuthentication().getDetails())
								.getRemoteAddress());
				} else if(securityContext.getAuthentication().getDetails() != null && 
						securityContext.getAuthentication().getDetails() instanceof VidyoUserDetails) {
					transactionHistory.setSourceIP(((VidyoUserDetails) securityContext
							.getAuthentication().getDetails()).getSourceIP());
				}
				
				transactionHistory.setTransactionName("Logout");
				transactionHistory.setTransactionParams("Username="
						+ securityContext.getAuthentication().getName());
				transactionHistory.setTransactionResult("SUCCESS");
				transactionHistory.setTransactionTime(Calendar.getInstance()
						.getTime());
				transactionHistory.setUserID(securityContext
						.getAuthentication().getName());
				// Get the Tenant Details as it is now set as a part of
				// VidyoUserDetails
				if (securityContext.getAuthentication().getPrincipal() instanceof VidyoUserDetails) {
                    Integer tenantId = TenantContext.getTenantId();
					Tenant tenant = tenantService.getTenant((tenantId == null ? 1 : tenantId ));
					transactionHistory.setTenantName(tenant.getTenantName());
				} else {
					logger.error("Principal not an instance of VidyoUserDetails ->"
							+ securityContext.getAuthentication()
									.getPrincipal());
					transactionHistory.setTenantName("Unknown");
				}

				transactionService.addTransactionHistory(transactionHistory);
				if (logger.isInfoEnabled()) {
					logger.info(transactionHistory);
				}
			}
		}
        if (event instanceof HttpSessionCreatedEvent) {
            if(VendorUtils.isDISA()){
                HttpSessionCreatedEvent sessionCreatedEvent = (HttpSessionCreatedEvent) event;
                sessionCreatedEvent.getSession().setMaxInactiveInterval(5*60); //in seconds
                // For logging
                if (logger.isInfoEnabled()) {
                    logger.info("DISA Session Created event: SessionId=" + sessionCreatedEvent.getSession().getId() + " inactive interval -"+ sessionCreatedEvent.getSession().getMaxInactiveInterval() );
                }

            }
        }

	}

}


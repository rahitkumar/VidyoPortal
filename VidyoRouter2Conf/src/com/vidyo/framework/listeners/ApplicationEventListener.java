package com.vidyo.framework.listeners;

import com.vidyo.service.IRouterConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;

public class ApplicationEventListener implements ApplicationListener {

	protected Logger logger = LoggerFactory.getLogger(ApplicationEventListener.class);

    private IRouterConfigService routerConfigService;

    public void setRouterConfigService(IRouterConfigService routerConfigService) {
        this.routerConfigService = routerConfigService;
    }

    @Override
	public void onApplicationEvent(ApplicationEvent event) {

		if (event instanceof HttpSessionDestroyedEvent) {
			HttpSessionDestroyedEvent sessionDestroyedEvent = (HttpSessionDestroyedEvent) event;
			SecurityContext securityContext = (SecurityContext) sessionDestroyedEvent
					.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
			if (securityContext != null
					&& securityContext.getAuthentication() != null
					&& securityContext.getAuthentication().getDetails() != null) {
				String ip = "?";
				if(securityContext.getAuthentication().getDetails() != null && 
						securityContext.getAuthentication().getDetails() instanceof WebAuthenticationDetails) {
					ip =  ((WebAuthenticationDetails) securityContext
								.getAuthentication().getDetails())
								.getRemoteAddress();
				}

                routerConfigService.writeAuditHistory(securityContext.getAuthentication().getName(), ip, "Logout");


			}
		}

	}

}


package com.vidyo.framework.listeners;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.vidyo.service.LicensingService;
import com.vidyo.service.system.SystemService;

public class SuperContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(SuperContextRefreshedListener.class);
    private LicensingService licenseService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("preemptively reading license so user/admin to create /opt/vidyo/portal2/license/license.txt");
        licenseService.getSystemLicenseDetails();

        try {
			// Initializing VM Credentials during startup
			ApplicationContext applicationContext = event.getApplicationContext();
			SystemService systemService = applicationContext.getBean("systemService1", SystemService.class);
			systemService.updateVidyoManagerCredentials();
		} catch (Exception e) {
			logger.error("Cannot initialize VM access credentials as the VM has not registered yet - ", e.getMessage());
		}
    }

    public void setLicenseService(LicensingService licenseService) {
        this.licenseService = licenseService;
    }

    public LicensingService getLicenseService() {
        return licenseService;
    }
}

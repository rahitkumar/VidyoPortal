package com.vidyo.framework.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.*;
import org.springframework.context.event.*;

public class ApplicationRestartListener implements ApplicationListener {
	private long serverRestartedTimestamp = 0L;
	
	/** Logger for this class and subclasses */
    protected final Logger logger = LoggerFactory.getLogger(ApplicationRestartListener.class.getName());
	
    public ApplicationRestartListener() {
		super();
	}
	
	public void onApplicationEvent(ApplicationEvent ae) {		
		if (ae instanceof ContextRefreshedEvent) {
			serverRestartedTimestamp = ae.getTimestamp();
			
			if (logger.isInfoEnabled())
				logger.info("Application started at " + ae.getTimestamp());
		}
	}
	
	public long getServerRestartedTimestamp(){
		return serverRestartedTimestamp;
	}
}

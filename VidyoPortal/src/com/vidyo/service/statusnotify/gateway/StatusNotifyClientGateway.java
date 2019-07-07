package com.vidyo.service.statusnotify.gateway;

import org.springframework.integration.annotation.Gateway;

import com.vidyo.service.statusnotify.types.NotifyUserStatus;


public interface StatusNotifyClientGateway {
	
	@Gateway
	public void invoke(NotifyUserStatus notifyUserStatus);

}

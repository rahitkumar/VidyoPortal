/**
 *
 */
package com.vidyo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vidyo.bo.eventsnotify.EventsNotificationServers;
import com.vidyo.service.ISystemService;

/**
 * @author ganesh
 *
 */
@RestController
public class SystemSettingsController {

	@Autowired
	private ISystemService systemService;

	@RequestMapping(value="/geteventsnotificationservers.ajax", method = RequestMethod.GET)
	public EventsNotificationServers getEventNotificationServers() {
		return systemService.getEventNotificationServers();
	}

	@RequestMapping(value = "/saveeventsnotificationservers.ajax", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<?> saveEventNotificationServers(
			@RequestBody EventsNotificationServers eventsNotificationServers) {
		systemService.saveEventNotificationServers(eventsNotificationServers);
		HttpHeaders httpHeaders = new HttpHeaders();
		return new ResponseEntity<>(eventsNotificationServers, httpHeaders, HttpStatus.CREATED);
	}

}

/**
 *
 */
package com.vidyo.bo.eventsnotify;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author ganesh
 *
 */
public class EventsNotificationServers {

	/**
	 *
	 */
	private String eventsNotificationEnabled = "off";

	/**
	 *
	 */
	private String primaryServer = "";

	/**
	 *
	 */
	private String primaryServerPort = "";

	/**
	 *
	 */
	private String secondaryServer = "";

	/**
	 *
	 */
	private String secondaryServerPort = "";

	/**
	 * @return the primaryServer
	 */
	public String getPrimaryServer() {
		return primaryServer;
	}

	/**
	 * @param primaryServer
	 *            the primaryServer to set
	 */
	public void setPrimaryServer(String primaryServer) {
		this.primaryServer = primaryServer;
	}

	/**
	 * @return the primaryServerPort
	 */
	public String getPrimaryServerPort() {
		return primaryServerPort;
	}

	/**
	 * @param primaryServerPort
	 *            the primaryServerPort to set
	 */
	public void setPrimaryServerPort(String primaryServerPort) {
		this.primaryServerPort = primaryServerPort;
	}

	/**
	 * @return the secondaryServer
	 */
	public String getSecondaryServer() {
		return secondaryServer;
	}

	/**
	 * @param secondaryServer
	 *            the secondaryServer to set
	 */
	public void setSecondaryServer(String secondaryServer) {
		this.secondaryServer = secondaryServer;
	}

	/**
	 * @return the secondaryServerPort
	 */
	public String getSecondaryServerPort() {
		return secondaryServerPort;
	}

	/**
	 * @param secondaryServerPort
	 *            the secondaryServerPort to set
	 */
	public void setSecondaryServerPort(String secondaryServerPort) {
		this.secondaryServerPort = secondaryServerPort;
	}

	/**
	 * @return the eventsNotificationEnabled
	 */
	public String getEventsNotificationEnabled() {
		return eventsNotificationEnabled;
	}

	/**
	 * @param eventsNotificationEnabled
	 *            the eventsNotificationEnabled to set
	 */
	public void setEventsNotificationEnabled(String eventsNotificationEnabled) {
		this.eventsNotificationEnabled = eventsNotificationEnabled;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}

}

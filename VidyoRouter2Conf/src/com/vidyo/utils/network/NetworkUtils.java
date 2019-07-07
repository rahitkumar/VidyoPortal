/**
 * 
 */
package com.vidyo.utils.network;

import java.io.IOException;
import java.net.ServerSocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Ganesh
 * 
 */
public class NetworkUtils {

	protected static final Logger logger = LoggerFactory.getLogger(NetworkUtils.class);

	/**
	 * Checks if the port is available for use
	 * 
	 * @param port integer
	 * @return
	 */
	public static boolean isPortInUse(int port) {
		if (port <= 0) {
			return false;
		}
		boolean portTaken = false;
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(port);
		} catch (IOException e) {
			portTaken = true;
		} finally {
			portTaken = (socket == null);
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					logger.error("Error while closing the socket ", e.getMessage());
				}
			}
		}
		return portTaken;
	}
}

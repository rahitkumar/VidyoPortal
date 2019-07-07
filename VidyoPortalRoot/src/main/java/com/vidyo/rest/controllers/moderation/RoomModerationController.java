/**
 * 
 */
package com.vidyo.rest.controllers.moderation;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller which handles Room Moderation. All APIs in this class require
 * either Admin/Operator role or a room owner or a normal user with Moderator
 * PIN credential.
 * 
 * @author ganesh
 *
 */
@RestController("RoomModerationControllerV1")
@RequestMapping(path = "/api/conference/moderation/v1/")
public class RoomModerationController {

	/**
	 * Returns the participants of the Room. Validates the room based on the
	 * TenantId
	 * 
	 * @param roomId
	 * @return
	 */
	@GetMapping(path = "/participants/{confId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String getParticipants(@PathVariable("confId") String roomId) {
		return null;
	}

	/**
	 * 
	 * @return
	 */
	@PutMapping(path = "/muteAudioServer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public String muteAudioServer() {
		return null;
	}

}

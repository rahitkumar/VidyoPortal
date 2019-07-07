/**
 * 
 */
package com.vidyo.service.conference;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.service.conference.request.InviteToConferenceRequest;
import com.vidyo.service.conference.response.JoinConferenceResponse;

/**
 * @author Ganesh
 * 
 */
@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class ConferenceAppServiceTests {

	@SpringBeanByType
	private ConferenceAppService conferenceAppService;

	/**
	 * 
	 */
	@Test
	public void testInviteToConferenceInvalidInviteeAndLegacy() {
		InviteToConferenceRequest inviteToConferenceRequest = new InviteToConferenceRequest();
		inviteToConferenceRequest.setLegacy("");
		JoinConferenceResponse joinConferenceResponse = conferenceAppService
				.inviteParticipantToConference(inviteToConferenceRequest);
		Assert.assertNotNull(joinConferenceResponse);
		Assert.assertEquals(JoinConferenceResponse.REQUIRED_PARAMS_NOT_PRESENT,
				joinConferenceResponse.getStatus());
	}
	
	/**
	 * 
	 */
	@Test
	public void testInviteToConferenceInvalidInviteeId() {
		
	}

}

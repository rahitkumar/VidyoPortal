package com.vidyo.portal.superapi;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

import com.vidyo.bo.PortalChat;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class VidyoPortalSuperServiceSkeletonTest {
	
	@SpringBeanByType
	private VidyoPortalSuperServiceSkeletonInterface vidyoPortalSuperServiceEndPoint;
	
	private Mock<ITenantService> mockTenantService;
	private Mock<ISystemService> mockSystemService;
	
	@Before
	public void initialize() {
		((VidyoPortalSuperServiceSkeleton) vidyoPortalSuperServiceEndPoint).setTenantService(mockTenantService.getMock());
		((VidyoPortalSuperServiceSkeleton) vidyoPortalSuperServiceEndPoint).setSystemService(mockSystemService.getMock());
	}
	
	
	@Test
	public void setChatStateSuperSuccess1() {
		PortalChat portalChat = new PortalChat();
		portalChat.setChatAvailable(true);
		portalChat.setDefaultPrivateChatEnabled(true);
		portalChat.setDefaultPublicChatEnabled(true);
		
		mockSystemService.returns(portalChat).getPortalChat();
		
		ChatState chatState = new ChatState();
		chatState.setChatAvailability(true);
		chatState.setPrivateChatState(true);
		chatState.setPublicChatState(true);
		
		SetChatStateSuperRequest setChatStateSuperRequest = new SetChatStateSuperRequest();
		setChatStateSuperRequest.setChatState(chatState);
		
		SetChatStateSuperResponse setChatStateSuperResponse = null;
		try {
			setChatStateSuperResponse = vidyoPortalSuperServiceEndPoint.setChatStateSuper(setChatStateSuperRequest);
		} catch (GeneralFaultException e) {
			fail("This case should not throw any Exception");
		}
		
		assertNotNull(setChatStateSuperResponse);
		assertEquals(OK_type0.OK, setChatStateSuperResponse.getOK());
		
		mockSystemService.assertInvoked().getPortalChat();
		mockSystemService.assertInvoked().savePortalChat(portalChat);
	}
	
	@Test
	public void setChatStateSuperSuccess2() {
		PortalChat portalChat = new PortalChat();
		portalChat.setChatAvailable(true);
		portalChat.setDefaultPrivateChatEnabled(true);
		portalChat.setDefaultPublicChatEnabled(true);
		
		mockSystemService.returns(portalChat).getPortalChat();
		
		ChatState chatState = new ChatState();
		
		SetChatStateSuperRequest setChatStateSuperRequest = new SetChatStateSuperRequest();
		setChatStateSuperRequest.setChatState(chatState);
		
		SetChatStateSuperResponse setChatStateSuperResponse = null;
		try {
			setChatStateSuperResponse = vidyoPortalSuperServiceEndPoint.setChatStateSuper(setChatStateSuperRequest);
		} catch (GeneralFaultException e) {
			fail("This case should not throw any Exception");
		}
		
		assertNotNull(setChatStateSuperResponse);
		assertEquals(OK_type0.OK, setChatStateSuperResponse.getOK());
		
		mockSystemService.assertInvoked().getPortalChat();
		mockSystemService.assertInvoked().savePortalChat(portalChat);
	}
	
	@Test
	public void setChatStateSuperSuccess3() {
		PortalChat portalChat = new PortalChat();
		portalChat.setChatAvailable(true);
		portalChat.setDefaultPrivateChatEnabled(true);
		portalChat.setDefaultPublicChatEnabled(true);
		
		mockSystemService.returns(portalChat).getPortalChat();
		
		ChatState chatState = new ChatState();
		chatState.setChatAvailability(false);
		chatState.setPrivateChatState(false);
		chatState.setPublicChatState(false);
		
		SetChatStateSuperRequest setChatStateSuperRequest = new SetChatStateSuperRequest();
		setChatStateSuperRequest.setChatState(chatState);
		
		SetChatStateSuperResponse setChatStateSuperResponse = null;
		try {
			setChatStateSuperResponse = vidyoPortalSuperServiceEndPoint.setChatStateSuper(setChatStateSuperRequest);
		} catch (GeneralFaultException e) {
			fail("This case should not throw any Exception");
		}
		
		assertNotNull(setChatStateSuperResponse);
		assertEquals(OK_type0.OK, setChatStateSuperResponse.getOK());
		
		mockSystemService.assertInvoked().getPortalChat();
		mockSystemService.assertInvoked().savePortalChat(portalChat);
		
		assertFalse(portalChat.isChatAvailable());
		assertFalse(portalChat.isDefaultPrivateChatEnabled());
		assertFalse(portalChat.isDefaultPublicChatEnabled());
	}

	@Test
	public void setChatStateSuperSuccess4() {
		PortalChat portalChat = new PortalChat();
		portalChat.setChatAvailable(false);
		portalChat.setDefaultPrivateChatEnabled(false);
		portalChat.setDefaultPublicChatEnabled(false);
		
		mockSystemService.returns(portalChat).getPortalChat();
		
		ChatState chatState = new ChatState();
		chatState.setChatAvailability(true);
		chatState.setPrivateChatState(true);
		chatState.setPublicChatState(true);
		
		SetChatStateSuperRequest setChatStateSuperRequest = new SetChatStateSuperRequest();
		setChatStateSuperRequest.setChatState(chatState);
		
		SetChatStateSuperResponse setChatStateSuperResponse = null;
		try {
			setChatStateSuperResponse = vidyoPortalSuperServiceEndPoint.setChatStateSuper(setChatStateSuperRequest);
		} catch (GeneralFaultException e) {
			fail("This case should not throw any Exception");
		}
		
		assertNotNull(setChatStateSuperResponse);
		assertEquals(OK_type0.OK, setChatStateSuperResponse.getOK());
		
		mockSystemService.assertInvoked().getPortalChat();
		mockSystemService.assertInvoked().savePortalChat(portalChat);
		
		assertTrue(portalChat.isChatAvailable());
		assertTrue(portalChat.isDefaultPrivateChatEnabled());
		assertTrue(portalChat.isDefaultPublicChatEnabled());
	}
	
	@Test
	public void getChatStateSuperSuccess() {
		PortalChat portalChat = new PortalChat();
		portalChat.setChatAvailable(true);
		portalChat.setDefaultPrivateChatEnabled(false);
		portalChat.setDefaultPublicChatEnabled(true);
		
		mockSystemService.returns(portalChat).getPortalChat();
		
		GetChatStateSuperRequest getChatStateSuperRequest = new GetChatStateSuperRequest();
		
		GetChatStateSuperResponse getChatStateSuperResponse = null;
		try {
			getChatStateSuperResponse = vidyoPortalSuperServiceEndPoint.getChatStateSuper(getChatStateSuperRequest);
		} catch (GeneralFaultException e) {
			fail("This case should not throw any Exception");
		}
		
		assertNotNull(getChatStateSuperResponse);
		
		ChatState chatState = getChatStateSuperResponse.getChatState();
		assertTrue(chatState.getChatAvailability());
		assertFalse(chatState.getPrivateChatState());
		assertTrue(chatState.getPublicChatState());
		
		mockSystemService.assertInvoked().getPortalChat();
	}

}

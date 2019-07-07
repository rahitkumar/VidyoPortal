package com.vidyo.portal.guest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.TransportHeaders;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.bo.Banners;
import com.vidyo.bo.Configuration;
import com.vidyo.bo.IpcConfiguration;
import com.vidyo.bo.PortalChat;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.authentication.AuthenticationConfig;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ExtIntegrationService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.interportal.IpcConfigurationService;

@PrepareForTest({ MessageContext.class, TenantContext.class })
public class VidyoPortalGuestServiceSkeletonTest extends PowerMockTestCase {

	@InjectMocks
	private VidyoPortalGuestServiceSkeleton guestService;
	
	@Mock
	private ITenantService tenantService;
	
	@Mock
	private ISystemService systemService;
	
	@Mock
	private IpcConfigurationService ipcConfigurationService;
	
	@Mock
	private ExtIntegrationService extIntegrationService;
	
	@BeforeMethod
	private void setup() {
		MockitoAnnotations.initMocks(this);

		PowerMockito.mockStatic(MessageContext.class);
		MessageContext messageContext = setupMessageContext();
		PowerMockito.when(MessageContext.getCurrentMessageContext()).thenReturn(messageContext);

		PowerMockito.mockStatic(TenantContext.class);
		PowerMockito.when(TenantContext.getTenantId()).thenReturn(1);
	}
	
	private MessageContext setupMessageContext() {
		MessageContext messageContext = new MessageContext();
		TransportHeaders transportHeaders = mock(TransportHeaders.class);
		when(transportHeaders.get("user-agent")).thenReturn(
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36");
		messageContext.setProperty(MessageContext.TRANSPORT_HEADERS, transportHeaders);
		messageContext.setProperty(MessageContext.REMOTE_ADDR, "127.0.0.1");
		return messageContext;
	}
	
	private PortalFeature_type0 getPortalFeatureByName(PortalFeature_type0[] features, String name) {
		for (PortalFeature_type0 feature : features) {
			if (feature.getFeature().getPortalFeatureName().equals(name)) {
				return feature;
			}
		}
		return null;
	}
	
    @Test
    public void getRoomDetailsByRoomKey_with_null_roomkey() throws Exception {
        GetRoomDetailsByRoomKeyRequest roomDetailsByRoomKeyRequest = new GetRoomDetailsByRoomKeyRequest();
        roomDetailsByRoomKeyRequest.setRoomKey(null);
        try{
            guestService.getRoomDetailsByRoomKey(roomDetailsByRoomKeyRequest);
            fail("This method is bound to throw InvalidArgumentFault exception");
        }catch (InvalidArgumentFaultException exception){
            assertNotNull(exception);
        }
    }

    @Test
    public void getRoomDetailsByRoomKey_with_empty_roomkey() throws Exception {
        GetRoomDetailsByRoomKeyRequest roomDetailsByRoomKeyRequest = new GetRoomDetailsByRoomKeyRequest();
        roomDetailsByRoomKeyRequest.setRoomKey("    ");
        try{
            guestService.getRoomDetailsByRoomKey(roomDetailsByRoomKeyRequest);
            fail("This method is bound to throw ResourceNotAvailableFaultException exception");
        }catch (InvalidArgumentFaultException exception){
            assertNotNull(exception);
        }
    }

    @Test
    public void getRoomDetailsByRoomKey_with_invalid_roomkey() throws Exception {
        TenantContext.setTenantId(1);
        GetRoomDetailsByRoomKeyRequest roomDetailsByRoomKeyRequest = new GetRoomDetailsByRoomKeyRequest();
        roomDetailsByRoomKeyRequest.setRoomKey("invalidroomkey");
        IRoomService roomService = mock(IRoomService.class);
        guestService.setRoom(roomService);
        when(roomService.getRoomDetailsByRoomKey(anyString(), anyInt())).thenReturn(null);
        try{
            guestService.getRoomDetailsByRoomKey(roomDetailsByRoomKeyRequest);
            fail("This method is bound to throw ResourceNotAvailableFaultException exception");
        } catch (ResourceNotAvailableFaultException exception){
            assertNotNull(exception);
        }
    }

    @Test
    public void getRoomDetailsByRoomKey_with_valid_roomkey() throws Exception {
        TenantContext.setTenantId(1);
        GetRoomDetailsByRoomKeyRequest roomDetailsByRoomKeyRequest = new GetRoomDetailsByRoomKeyRequest();
        GetRoomDetailsByRoomKeyResponse roomDetailsByRoomKeyResponse;
        EntityID entityID = new EntityID();
        entityID.setEntityID(1234);
        Room room = new Room();
        room.setRoomName("public");
        room.setRoomType("personal");
        room.setRoomPinned(0);
        room.setRoomLocked(0);
        room.setRoomEnabled(0);
        room.setRoomExtNumber("1234");
        room.setRoomDescription("description of room");
        room.setRoomID(1234);
        room.setDisplayName("public display name");

        roomDetailsByRoomKeyRequest.setRoomKey("roomkey");
        IRoomService roomService = mock(IRoomService.class);
        guestService.setRoom(roomService);
        when(roomService.getRoomDetailsByRoomKey(anyString(), anyInt())).thenReturn(room);
        try{
            roomDetailsByRoomKeyResponse = guestService.getRoomDetailsByRoomKey(roomDetailsByRoomKeyRequest);
            assertTrue(roomDetailsByRoomKeyResponse.getEntityID().getEntityID()==1234);
            assertEquals(room.getRoomEnabled(), 0);
            assertEquals(room.getRoomLocked(), 0);
            assertEquals(room.getRoomPinned(), 0);
            assertTrue(roomDetailsByRoomKeyResponse.getEnabled()==false);
            assertTrue(roomDetailsByRoomKeyResponse.getLocked()==false);
            assertTrue(roomDetailsByRoomKeyResponse.getPinned() == false);
            assertTrue(roomDetailsByRoomKeyResponse.getExtension().equals(room.getRoomExtNumber()));
            assertEquals(roomDetailsByRoomKeyResponse.getDisplayName(), room.getDisplayName());
            assertEquals(roomDetailsByRoomKeyResponse.getEntityID().getEntityID(), room.getRoomID());
            assertEquals(roomDetailsByRoomKeyResponse.getName(), room.getRoomName());
            assertEquals(roomDetailsByRoomKeyResponse.getType(), room.getRoomType());
            assertEquals(roomDetailsByRoomKeyResponse.getDescription(),room.getRoomDescription());
        } catch (Exception exception){
            fail("This method is bound to throw ResourceNotAvailableFaultException exception");
            assertNull(exception);
        }
    }
    
	@Test
	public void testPortalFeatures() throws Exception {
		when(extIntegrationService.isTenantTytoCareEnabled()).thenReturn(false);
		when(tenantService.getTenant(any())).thenReturn(new Tenant());
		when(systemService.getConfiguration(any())).thenReturn(new Configuration());
		when(ipcConfigurationService.getIpcConfiguration(anyInt())).thenReturn(new IpcConfiguration());
		when(systemService.getBannersInfo()).thenReturn(new Banners());
		when(systemService.getPortalChat()).thenReturn(new PortalChat());  
		when(systemService.getAuthenticationConfig(anyInt())).thenReturn(new AuthenticationConfig()) ; 

		GetPortalFeaturesRequest portalFeaturesRequest = new GetPortalFeaturesRequest();
		GetPortalFeaturesResponse response = guestService.getPortalFeatures(portalFeaturesRequest);

		Assert.assertNotNull(response);
		PortalFeature_type0 feature = getPortalFeatureByName(response.getPortalFeature(), "TytoCare");
		Assert.assertNotNull(feature);
		Assert.assertEquals(false, feature.getEnable());
	}
}

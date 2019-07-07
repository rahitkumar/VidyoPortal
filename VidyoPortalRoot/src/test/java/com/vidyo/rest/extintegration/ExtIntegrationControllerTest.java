package com.vidyo.rest.extintegration;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Room;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.rest.controllers.ExtIntegrationController;
import com.vidyo.service.*;
import com.vidyo.service.exceptions.DataValidationException;
import com.vidyo.service.exceptions.GeneralException;
import com.vidyo.service.exceptions.ScheduledRoomCreationException;
import com.vidyo.service.room.ScheduledRoomResponse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.testng.PowerMockTestCase;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@PrepareForTest({ TenantContext.class })
public class ExtIntegrationControllerTest extends PowerMockTestCase {

	@Mock
	private ITenantService tenantService;
	
	@Mock
	private IRoomService roomService;
	
	@Mock
	private ISystemService systemService;
	
	@Mock
	private TestCallService testCallService;
	
	@InjectMocks
	private ExtIntegrationServiceImpl extIntegrationService; 
    
	private ExtIntegrationController extIntegrationController = new ExtIntegrationController();
	
	@BeforeMethod
	private void setup() {
		MockitoAnnotations.initMocks(this);
		
		PowerMockito.mockStatic(TenantContext.class);
		PowerMockito.when(TenantContext.getTenantId()).thenReturn(1);

		TenantConfiguration configuration = new TenantConfiguration();
		configuration.setExtIntegrationSharedSecret("T1h9e8c2o3r4r9e6");
		when(tenantService.getTenantConfiguration(1)).thenReturn(configuration);
		
		Room testRoom = new Room();
		testRoom.setRoomKey("4pDmq5fc1Z");
		when(roomService.getRoom(anyInt())).thenReturn(testRoom);
		
		extIntegrationController.setExtIntegrationService(extIntegrationService);
	}
	
	protected void createEpicMocks() {
		Configuration config = new Configuration();
		config.setConfigurationValue("1"); // epic enabled
		when(systemService.getConfiguration("EPIC_INTEGRATION_SUPPORT")).thenReturn(config);
		
		TenantConfiguration tenantConfiguration = new TenantConfiguration();
		tenantConfiguration.setExtIntegrationSharedSecret("T1h9e8c2o3r4r9e6");
		tenantConfiguration.setExternalIntegrationMode(1);
		when(tenantService.getTenantConfiguration(1)).thenReturn(tenantConfiguration);
	}
	
	@Test(expectedExceptions = GeneralException.class)
	public void testRoomKeyEpicNotEnabled() throws Exception {
		Map<String, String> configValues = new HashMap<String, String>();
		configValues.put("extData", "rsz76/DemvyCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+drhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxCW5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==");
		
		boolean isEpicEnabled = extIntegrationService.isTenantEpicEnabled();
		Assert.assertFalse(isEpicEnabled);
		
		String response = extIntegrationController.getRoomKey(configValues);
		Assert.assertNull(response);
	}
	
	@Test(expectedExceptions = DataValidationException.class)
	public void testRoomKeyEmptyExtDataType() throws Exception {
		Map<String, String> configValues = new HashMap<String, String>();
		configValues.put("extData", "rsz76/DemvyCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+drhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxCW5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==");
		
		createEpicMocks();
		
		String response = extIntegrationController.getRoomKey(configValues);
		Assert.assertNull(response);
	}
	
	@Test(expectedExceptions = DataValidationException.class)
	public void testRoomKeyEmptyExtData() throws Exception {
		Map<String, String> configValues = new HashMap<String, String>();
		configValues.put("extDataType", "1");

		createEpicMocks();
		
		String response = extIntegrationController.getRoomKey(configValues);
		Assert.assertNull(response);
	}
	
	@Test(expectedExceptions = DataValidationException.class)
	public void testRoomKeyInvalidExtData() throws Exception {
		Map<String, String> configValues = new HashMap<String, String>();
		configValues.put("extDataType", "1");
		configValues.put("extData", "ttz76/DemvyCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+drhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxCW5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==");

		createEpicMocks();
		
		String response = extIntegrationController.getRoomKey(configValues);
		Assert.assertNull(response);
	}
	
	@Test(expectedExceptions = Exception.class)
	public void testRoomKeyRoomNotFound() throws Exception {
		Map<String, String> configValues = new HashMap<String, String>();
		configValues.put("extDataType", "1");
		configValues.put("extData", "rsz76/DemvyCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+drhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxCW5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==");
		
		createEpicMocks();
		when(roomService.getRoomIdForExternalRoomId(any())).thenReturn(0);
		when(roomService.getRoom(anyInt())).thenReturn(null);
		
		ScheduledRoomResponse scheduledRoom = new ScheduledRoomResponse();
		scheduledRoom.setStatus(1); // failed
		when(testCallService.createScheduledRoomForTestCallOneAttempt(any(), any())).thenReturn(scheduledRoom);

		String response = extIntegrationController.getRoomKey(configValues);
		Assert.assertNull(response);
	}
	
	@Test(expectedExceptions = Exception.class)
	public void testRoomKeyRoomCreationFailed() throws Exception {
		Map<String, String> configValues = new HashMap<String, String>();
		configValues.put("extDataType", "1");
		configValues.put("extData", "rsz76/DemvyCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+drhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxCW5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==");
		
		createEpicMocks();
		when(roomService.getRoomIdForExternalRoomId(any())).thenReturn(0);
		//when(room.getRoom(anyInt())).thenReturn(null);
		when(testCallService.createScheduledRoomForTestCallOneAttempt(any(), any())).thenThrow(new ScheduledRoomCreationException());

		String response = extIntegrationController.getRoomKey(configValues);
		Assert.assertNull(response);
	}
	
	/*@Test
	public void testRoomKeySuccessful() throws Exception {
		Map<String, String> configValues = new HashMap<String, String>();
		configValues.put("extDataType", "1");
		configValues.put("extData", "rsz76/DemvyCDo9sE+1c+LT1iS7o0icon6oiHb8G8l3wO+drhAirTSzMIY1mdEdpRrIj8adT8VmzsMloGRxCW5a+3LbYfNWSUqx5OwD1Z1wj78qoSQf3ag5XFntDIUQXoFvzzAb7GUhhtRL7D17X460rtUEgDq5mdH/oe0MnliSPmzRHH/lNdiL3oQxt5jyJ0FVOFTwSZMWCWJer6fjZDA==");
		
		createEpicMocks();
		when(room.getRoomIdForExternalRoomId(any())).thenReturn(5);
		//when(room.getRoom(anyInt())).thenReturn(new Room());
		
		String response = extIntegrationController.getRoomKey(configValues);
		Assert.assertNotNull(response);
		Assert.assertTrue( response.indexOf("4pDmq5fc1Z") > 0 ); // is room key
	}*/
}

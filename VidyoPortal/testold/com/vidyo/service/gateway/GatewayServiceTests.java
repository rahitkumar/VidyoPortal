package com.vidyo.service.gateway;


import com.vidyo.bo.Service;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.exceptions.NoVidyoManagerException;
import com.vidyo.service.gateway.request.JoinFromLegacyServiceRequest;
import com.vidyo.service.gateway.request.RegisterPrefixesServiceRequest;
import com.vidyo.service.gateway.response.JoinFromLegacyServiceResponse;
import com.vidyo.service.gateway.response.RegisterPrefixesServiceResponse;
import com.vidyo.service.room.ScheduledRoomResponse;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.unitils.UnitilsJUnit4TestClassRunner;
import org.unitils.mock.Mock;
import org.unitils.spring.annotation.SpringApplicationContext;
import org.unitils.spring.annotation.SpringBeanByType;

@SpringApplicationContext({ "classpath:test-config.xml" })
@RunWith(UnitilsJUnit4TestClassRunner.class)
public class GatewayServiceTests {

	@SpringBeanByType
	private GatewayService gatewayService;

	private Mock<IServiceService> serviceServiceMock;
	private Mock<IConferenceService> conferenceServiceMock;
	private Mock<IMemberService> memberServiceMock;
	private Mock<ITenantService> tenantServiceMock;
	private Mock<IRoomService> roomServiceMock;
	private Mock<ConferenceAppService> conferenceAppServiceMock;
	private Mock<EndpointService> endpointServiceMock;


	@Before
	public void initialize() {

		((GatewayServiceImpl) gatewayService).setServicesService(serviceServiceMock.getMock());
		serviceServiceMock.returns(true).isUseNewGatewayServiceInterface();

		((GatewayServiceImpl) gatewayService).setConferenceService(conferenceServiceMock.getMock());
		((GatewayServiceImpl) gatewayService).setMemberService(memberServiceMock.getMock());
		((GatewayServiceImpl) gatewayService).setTenantService(tenantServiceMock.getMock());
		((GatewayServiceImpl) gatewayService).setRoomService(roomServiceMock.getMock());
		((GatewayServiceImpl) gatewayService).setConferenceAppService(conferenceAppServiceMock.getMock());
		((GatewayServiceImpl) gatewayService).setEndpointService(endpointServiceMock.getMock());

	}

	@Test
	public void testRegisterPrefixesSuccess() {
		Service gwService = new Service();
		serviceServiceMock.returns(gwService).getServiceByUserName("test", "VidyoGateway");

		RegisterPrefixesServiceRequest request = new RegisterPrefixesServiceRequest();

		request.setGatewayUserAccount("test");
		RegisterPrefixesServiceResponse response = gatewayService.registerPrefixes(request);
		Assert.assertEquals(RegisterPrefixesServiceResponse.SUCCESS, response.getStatus());
	}

	@Test
	public void testRegisterPrefixesNoVidyoManager() throws NoVidyoManagerException {
		Service gwService = new Service();
		serviceServiceMock.returns(gwService).getServiceByUserName("test", "VidyoGateway");

		conferenceServiceMock.raises(NoVidyoManagerException.class).getVMConnectAddress();

		RegisterPrefixesServiceRequest request = new RegisterPrefixesServiceRequest();
		request.setGatewayUserAccount("test");
		RegisterPrefixesServiceResponse response = gatewayService.registerPrefixes(request);
		Assert.assertEquals(RegisterPrefixesServiceResponse.VIDYO_MANAGER_UNAVAILABLE, response.getStatus());
	}

	@Test
	public void testJoinFromLegacyInvalidRoom() {
		Service gwService = new Service();
		serviceServiceMock.returns(gwService).getServiceByUserName("test", "VidyoGateway");

		roomServiceMock.returns(null).getRoom("testRoom",1);

		conferenceAppServiceMock.returns(new ScheduledRoomResponse()).validateScheduledRoom("testRoom", null);

		JoinFromLegacyServiceRequest request = new JoinFromLegacyServiceRequest();
		request.setGatewayUserAccount("test");
		request.setDialString("testRoom");

		JoinFromLegacyServiceResponse response = gatewayService.joinFromLegacy(request);
		Assert.assertEquals(JoinFromLegacyServiceResponse.INVALID_TO_NAME_EXT, response.getStatus());
	}

}
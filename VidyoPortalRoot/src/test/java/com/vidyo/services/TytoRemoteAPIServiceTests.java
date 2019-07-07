package com.vidyo.services;

import static com.vidyo.TytoConstants.FEATURE_CONFIG_ERROR;
import static com.vidyo.TytoConstants.INVALID_USER_DATA_LENGTH;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.vidyo.exceptions.tyto.TytoCommunicationException;
import com.vidyo.VisitStatus;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.db.endpoints.EndpointDao;
import com.vidyo.dto.ChangeVisitStatusRequest;
import com.vidyo.dto.GetVisitResponse;
import com.vidyo.dto.PairingResponse;
import com.vidyo.dto.StationSaveRequest;
import com.vidyo.dto.StationStatusResponse;
import com.vidyo.dto.VisitRequest;
import com.vidyo.dto.VisitResponse;
import com.vidyo.dto.VisitReviewTytoRequest;
import com.vidyo.dto.VisitReviewTytoResponse;
import com.vidyo.dto.tyto.ClinicianResponse;
import com.vidyo.dto.tyto.TytoCreateClinicianRq;
import com.vidyo.dto.tyto.TytoCreateVisitRequest;
import com.vidyo.exceptions.tyto.TytoProcessingException;
import com.vidyo.exceptions.tyto.TytoInvalidUserDataException;
import com.vidyo.service.CryptService;
import com.vidyo.service.ExtIntegrationService;
import com.vidyo.service.ITenantService;

@RunWith(MockitoJUnitRunner.class)
public class TytoRemoteAPIServiceTests {

	private static final int REF_TENANT_ID = 5;
	private static final String REF_STATION_ID = "vfd-q777";
	private static final String REF_VISIT_ID = "vfd-vis55";
	private static final String REMOTE_IP_ADDRESS = "10.24.24.10";
	private static final String REF_HOST = "http://localhost:8000";
	private static final String CLINICIAN_REMOTE_ADDR = "127.0.0.1";
	private static final String REF_TYTO_USERNAME = "tyto-admin";
	private static final String REF_DESC = "tyto-description";
	private static final String REF_TYTO_ENC_PWD = "encryptedText";
	private static final String REF_TYTO_DEC_PWD = "decrypted-pwd";

	private static final String IDENTIFIER = "visit322999";
	private static final String REF_CLINITIAN_IDENTIFIER = "EA2AEAD3DF92-1218340200";
	private static final String TYTO_IDENTIFIER = "6d066b30868247269e88ec3d17e90eb8";
	private static final String CLINICIAN_URL = "https://<tyto-url-here>/index.htm#/startOnlineVisit/8zNRgwzl/a5ra46ZsYYJaPVo6CqhPr6oiYbcBf2qFDVtCIylKX6w*/true";
	private static final boolean IS_DEVICE_CONNECTED = false;

	@Mock
	private ITenantService tenantService;

	@Mock
	private ExtIntegrationService extIntegrationService;

	@Mock
	private CryptService cryptService;

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private VidyoTytoBridge apiBridge;
	
	@Mock
	private EndpointDao endpointDao;
	
	private TytoRemoteAPIService tytoRemoteAPIService;

	private TenantConfiguration refTenantConfig;

	@Before
	public void setup() {
		tytoRemoteAPIService = new TytoRemoteAPIService(tenantService,
				extIntegrationService,
				cryptService,
				apiBridge,
				restTemplate,
				endpointDao);
		refTenantConfig = new TenantConfiguration();
		refTenantConfig.setTytoUrl(REF_HOST);
		refTenantConfig.setTytoUsername(REF_TYTO_USERNAME);
		refTenantConfig.setTytoPassword(REF_TYTO_ENC_PWD);
	}

	@Test
	public void testGetStationStatusResponseSuccess() throws Exception {
		setupCommonMocks();
		StationStatusResponse response = new StationStatusResponse(REF_STATION_ID,
				REF_DESC, true, true, true);
		ResponseEntity<StationStatusResponse> responseEntity =
				new ResponseEntity<StationStatusResponse>(response, HttpStatus.OK);
		when(restTemplate.exchange(
				ArgumentMatchers.any(),
				eq(HttpMethod.GET),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<StationStatusResponse>>any())).thenReturn(responseEntity);

		StationStatusResponse result = tytoRemoteAPIService.getStationStatus(REF_STATION_ID, REF_TENANT_ID, REMOTE_IP_ADDRESS);
		assertNotNull(result);
		assertEquals(REF_DESC, result.getDescription());
		assertEquals(true, result.getIsActive());
		assertEquals(true, result.getIsPaired());
		assertEquals(true, result.getIsDeviceOnline());
	}

	@Test
	public void testSaveStationResponseSuccess() throws Exception {
		setupCommonMocks();
		ResponseEntity<String> responseEntityPost = new ResponseEntity<String>("", HttpStatus.CREATED);
		when(restTemplate.exchange(
				ArgumentMatchers.any(),
				eq(HttpMethod.POST),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any())).thenReturn(responseEntityPost);

		StationSaveRequest request = new StationSaveRequest(REF_STATION_ID, REF_DESC);
		tytoRemoteAPIService.createStation(request, REF_TENANT_ID);
	}
	
	@Test
	public void testGetVisitResponseSuccess() throws Exception {
		setupCommonMocks();
		GetVisitResponse response = new GetVisitResponse();
		response.setTytoIdentifier(REF_VISIT_ID);
		response.setIsOnline(true);
		response.setIsClinicianOnline(true);
		ResponseEntity<GetVisitResponse> responseEntityGet = 
				new ResponseEntity<GetVisitResponse>(response, HttpStatus.OK);

		when(restTemplate.exchange(
				ArgumentMatchers.any(),
				eq(HttpMethod.GET),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<GetVisitResponse>>any())).thenReturn(responseEntityGet);
		when(apiBridge.cleanUpGetVisitResponse(any()))
				.thenReturn(new GetVisitResponse(response, "someEndpointGuide"));
		
		GetVisitResponse result = tytoRemoteAPIService.getVisit(REF_VISIT_ID, REF_TENANT_ID);
		assertNotNull(result);
		assertEquals(REF_VISIT_ID, result.getTytoIdentifier());
        assertEquals(true, result.getIsOnline());
        assertEquals(true, result.getIsClinicianOnline());
	}

    @Test
    public void testCreateVisitReviewResponseSuccess() throws Exception {
        setupCommonMocks();
        VisitReviewTytoResponse response = new VisitReviewTytoResponse();
        response.setClinicianUrl("http://test.com");
        ResponseEntity<VisitReviewTytoResponse> responseEntityGet =
                new ResponseEntity<VisitReviewTytoResponse>(response, HttpStatus.OK);

        when(restTemplate.exchange(
                ArgumentMatchers.any(),
                eq(HttpMethod.POST),
                ArgumentMatchers.any(),
                ArgumentMatchers.<Class<VisitReviewTytoResponse>>any())).thenReturn(responseEntityGet);

        VisitReviewTytoRequest request = new VisitReviewTytoRequest();
        request.setClinicianIdentifier("12@1");
        request.setClinicianRemoteAddress("127.0.0.1");
        VisitReviewTytoResponse result = tytoRemoteAPIService.createVisitReview(request, "12",
                REF_TENANT_ID);
        assertNotNull(result);
        assertNotNull(result.getClinicianUrl());
    }

    @Test
    public void testPairingResponse_success() throws Exception {
		setupCommonMocks();
        PairingResponse tytoResponse = new PairingResponse(
                "xxx0001",
                "http://localhost:9909");

		when(restTemplate.exchange(
				ArgumentMatchers.any(),
				eq(HttpMethod.POST),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<PairingResponse>>any())).thenReturn(new ResponseEntity<>(tytoResponse, HttpStatus.OK));

		PairingResponse respponse = tytoRemoteAPIService.pairDeviceWithStation(REF_STATION_ID, REF_TENANT_ID);
        assertNotNull(respponse);
    }

	@Test
    public void testCreateVisitRequest_success() {
    	setupCommonMocks();
        VisitRequest visitRequest = new VisitRequest(IDENTIFIER, REF_CLINITIAN_IDENTIFIER, REF_STATION_ID);
        VisitResponse visitResponse = new VisitResponse(TYTO_IDENTIFIER, "http://patientURL", CLINICIAN_URL, IS_DEVICE_CONNECTED);
        ResponseEntity<VisitResponse>visitResponseEntity = new ResponseEntity<>(visitResponse,HttpStatus.CREATED);
        when(restTemplate.exchange(any(),
        		eq(HttpMethod.POST),
        		any(),
        		ArgumentMatchers.<Class<VisitResponse>>any()))
        .thenReturn(visitResponseEntity);
        when(apiBridge.createVisit(eq(REF_TENANT_ID), eq(visitRequest), any())).thenReturn(new TytoCreateVisitRequest(IDENTIFIER,
                "2323@63",
                REF_STATION_ID,
                "9.9.9.9",
				"192.168.1.100"));

        VisitResponse response = tytoRemoteAPIService.createVisit(visitRequest, REF_TENANT_ID, CLINICIAN_REMOTE_ADDR);
        assertNotNull(response);
    }

	@Test(expected = TytoProcessingException.class)
	public void createOrUpdateClinicianFeatureDisabled() {
		tytoRemoteAPIService.createOrUpdateClinician(REF_TENANT_ID,
				REF_CLINITIAN_IDENTIFIER);
	}

    @Test
	public void updateClinicianIfExistsSuccess() {
    	setupCommonMocks();
		when(extIntegrationService.isTenantTytoCareEnabled()).thenReturn(true);
		TytoCreateClinicianRq clinicianRq = new TytoCreateClinicianRq("firstName",
				"lastName", "email", "id" );
		when(apiBridge.createClinicianRq(anyInt(), any(), any())).thenReturn(clinicianRq);
		when(apiBridge.isClinicianModified(anyInt(), any(), any())).thenReturn(true);

		when(restTemplate.exchange(
				ArgumentMatchers.any(),
				eq(HttpMethod.GET),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<ClinicianResponse>>any()))
				.thenReturn(new ResponseEntity<>(new ClinicianResponse(), HttpStatus.OK));
		when(restTemplate.exchange(
				ArgumentMatchers.any(),
				eq(HttpMethod.PUT),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<String>("{}", HttpStatus.OK));
		tytoRemoteAPIService.createOrUpdateClinician(REF_TENANT_ID,
				REF_CLINITIAN_IDENTIFIER);
	}
    
    @Test
	public void updateClinicianIncorrectDataLength() {
    	setupCommonMocks();
		when(extIntegrationService.isTenantTytoCareEnabled()).thenReturn(true);
		TytoCreateClinicianRq clinicianRq = new TytoCreateClinicianRq("firstName",
				"lastName", "email", "id" );
		when(apiBridge.createClinicianRq(anyInt(), any(), any())).thenReturn(clinicianRq);
		when(apiBridge.isClinicianModified(anyInt(), any(), any())).thenReturn(true);

		when(restTemplate.exchange(
				ArgumentMatchers.any(),
				eq(HttpMethod.GET),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<ClinicianResponse>>any()))
				.thenReturn(new ResponseEntity<>(new ClinicianResponse(), HttpStatus.OK));
		when(restTemplate.exchange(
				ArgumentMatchers.any(),
				eq(HttpMethod.PUT),
				ArgumentMatchers.any(),
				ArgumentMatchers.<Class<String>>any()))
				.thenThrow(new TytoCommunicationException(INVALID_USER_DATA_LENGTH, HttpStatus.BAD_REQUEST.value(), null));
		
		try {
			tytoRemoteAPIService.createOrUpdateClinician(REF_TENANT_ID, REF_CLINITIAN_IDENTIFIER);
		} catch (TytoInvalidUserDataException te) {
			assertEquals(INVALID_USER_DATA_LENGTH, te.getMessageCode() );
			assertEquals(BAD_REQUEST.value(), te.getErrorCode() );
		}
	}

    @Test
    public void createClinicianSuccess() {
        setupCommonMocks();
        when(extIntegrationService.isTenantTytoCareEnabled()).thenReturn(true);

        Map<String, Object> m = new HashMap<>();
        m.put("memberID", 5L);
        m.put("endpointPublicIPAddress", "10.10.10.10");
        when(apiBridge.findPublicIpAndMemberId(eq(REF_TENANT_ID), eq(REF_CLINITIAN_IDENTIFIER)))
                .thenReturn(m);

        when(apiBridge.createClinicianRq(eq(5),
                eq(5L),
                any() )).thenReturn(
                        new TytoCreateClinicianRq("test_firstname",
                                "test_lastanem",
                "test@test.com",
                "5@5"));
		when(restTemplate.exchange(any(),
				eq(HttpMethod.GET),
				any(),
				ArgumentMatchers.<Class<String>>any())
			).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));
        when(restTemplate.exchange(any(),
                eq(HttpMethod.POST),
                any(),
                ArgumentMatchers.<Class<String>>any())
        ).thenReturn(new ResponseEntity<>(HttpStatus.CREATED));

        tytoRemoteAPIService.createOrUpdateClinician(REF_TENANT_ID,  REF_CLINITIAN_IDENTIFIER);

        verify(restTemplate).exchange(any(),
                eq(HttpMethod.POST),
                any(),
                ArgumentMatchers.<Class<String>>any());
    }

    @Test
	public void changeVisitStatus_success() throws Exception {
		setupCommonMocks();
		ChangeVisitStatusRequest newState = new ChangeVisitStatusRequest(VisitStatus.ACTIVE);

		when(restTemplate.exchange( any(),
				eq(HttpMethod.PUT),
				any(),
				ArgumentMatchers.<Class<String>>any()))
				.thenReturn(new ResponseEntity<>(HttpStatus.OK));
		tytoRemoteAPIService.changeVisitStatus(REF_TENANT_ID,
				REF_VISIT_ID,
				newState
		);
	}

	@Test
	public void changeVisitStatus_feature_disabled() {
		when(extIntegrationService.isTenantTytoCareEnabled()).thenReturn(false);
		ChangeVisitStatusRequest newState = new ChangeVisitStatusRequest(VisitStatus.ACTIVE);
		try {
			tytoRemoteAPIService.changeVisitStatus(REF_TENANT_ID,
					REF_VISIT_ID,
					newState
			);
		} catch (TytoProcessingException te) {
			assertEquals(FEATURE_CONFIG_ERROR, te.getMessageCode() );
			assertEquals(FORBIDDEN.value(), te.getErrorCode() );
		}
	}

    private void setupCommonMocks(){
		when(extIntegrationService.isTenantTytoCareEnabled()).thenReturn(true);
		when(tenantService.getTenantConfiguration(eq(REF_TENANT_ID))).thenReturn(refTenantConfig);
		when(cryptService.decrypt(eq(REF_TYTO_ENC_PWD))).thenReturn(REF_TYTO_DEC_PWD);
	}
}

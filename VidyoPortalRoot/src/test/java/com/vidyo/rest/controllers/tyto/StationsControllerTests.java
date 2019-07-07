package com.vidyo.rest.controllers.tyto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vidyo.dto.PairingResponse;
import com.vidyo.dto.StationSaveRequest;
import com.vidyo.dto.StationStatusResponse;
import com.vidyo.exceptions.tyto.TytoCommunicationException;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.services.TytoRemoteAPIService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StationsControllerTests {

    private static final String REFERENCE_STATION_ID = "ddf111";
    private static final String REFERENCE_PAIRING_CODE = "iVlFTKHcRZ";
    private static final int REFERENCE_TENANT_ID = 532;
    private static final String REFERENCE_DESCRIPTION = "tytotest12345678";
    private static final String REFERENCE_REMOTE_ADDRESS = "10.24.24.10";

    @Mock
    private TytoRemoteAPIService tytoApi;
    
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(
        		new StationsController(tytoApi))
                .setControllerAdvice(new TytoErrorHandlerController())
                .build();
        TenantContext.setTenantId(REFERENCE_TENANT_ID);
    }

    @Test
    public void getStationStatusSuccess() throws Exception {
        StationStatusResponse response = new StationStatusResponse(REFERENCE_STATION_ID, 
        		REFERENCE_DESCRIPTION, true, true, true);

        when(tytoApi.getStationStatus(eq(REFERENCE_STATION_ID), eq(REFERENCE_TENANT_ID), eq(REFERENCE_REMOTE_ADDRESS)))
                .thenReturn(response);
        //when(v.isValid(eq(REFERENCE_STATION_ID))).thenReturn(true);
        MvcResult r = mockMvc
                .perform(get("/api/extintegration/tyto/v1/stations/ddf111")
                .with(remoteAddr(REFERENCE_REMOTE_ADDRESS))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        JsonNode node = mapper.readTree(r.getResponse().getContentAsString());
        assertEquals(REFERENCE_STATION_ID, node.get("identifier").asText());
        assertEquals(REFERENCE_DESCRIPTION, node.get("description").asText());
        assertEquals("true", node.get("isActive").asText());
        assertEquals("true", node.get("isPaired").asText());
    }

   // @Test
    public void getStationStatusCreateBadRequestResponse() throws Exception {
        //when(v.isValid(eq(REFERENCE_STATION_ID))).thenReturn(false);
        mockMvc.perform(get("/api/extintegration/tyto/v1/stations/" + REFERENCE_STATION_ID)
                .contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
    }

    @Test
    public void getStationStatusNotFoundResponse() throws Exception {
        //when(TytoIdValidator.isValid(eq(REFERENCE_STATION_ID))).thenReturn(true);
        when(tytoApi.getStationStatus(eq(REFERENCE_STATION_ID), eq(REFERENCE_TENANT_ID), eq(REFERENCE_REMOTE_ADDRESS)))
        	.thenThrow(new TytoCommunicationException("exception when getting remote response", 
        			HttpStatus.NOT_FOUND.value(), "{\"code\" : \"ERROR_STATION_NOT_FOUND\"}"));

		mockMvc.perform(get("/api/extintegration/tyto/v1/stations/" + REFERENCE_STATION_ID)
				.with(remoteAddr(REFERENCE_REMOTE_ADDRESS)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
    }

	@Test
	public void getSaveStationSuccess() throws Exception {
		StationSaveRequest request = new StationSaveRequest(REFERENCE_STATION_ID, REFERENCE_DESCRIPTION);
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = objectWriter.writeValueAsString(request);
	    mockMvc
				.perform(post("/api/extintegration/tyto/v1/stations/", request)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(requestJson))
				.andExpect(status().isCreated()).andReturn();
	}

    @Test
    public void getSaveStations_wrong_argument() throws Exception {
        String s ="{ \"identifier\": \"\"}";
        mockMvc
                .perform(post("/api/extintegration/tyto/v1/stations/")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(s))
                .andExpect(status().isBadRequest());
    }

	@Test
	public void getSaveStationCreateBadRequestResponse() throws Exception {
		//when(v.isValid(eq(REFERENCE_STATION_ID))).thenReturn(false);
		mockMvc.perform(post("/api/extintegration/tyto/v1/stations/")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void getSaveStationCreateNotFoundResponse() throws Exception {
		StationSaveRequest request = new StationSaveRequest(REFERENCE_STATION_ID, REFERENCE_DESCRIPTION);
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = objectWriter.writeValueAsString(request);
	    
	    doThrow(new TytoCommunicationException("exception when getting remote response", 
    			HttpStatus.NOT_FOUND.value(), "{\"code\" : \"ERROR_STATION_NOT_FOUND\"}"))
	    	.when(tytoApi).createStation(any(), eq(REFERENCE_TENANT_ID));
		//when(v.isValid(eq(REFERENCE_STATION_ID))).thenReturn(true);

		mockMvc.perform(post("/api/extintegration/tyto/v1/stations/")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(requestJson))
				.andExpect(status().isNotFound());
	}

    @Test
    public void stationDevicePairingRequest_success() throws Exception {
        PairingResponse response = new PairingResponse(
                REFERENCE_PAIRING_CODE,
                "http://locaolhost:2222");

        when(tytoApi.pairDeviceWithStation(eq(REFERENCE_STATION_ID), eq(REFERENCE_TENANT_ID))).thenReturn(response);
        //when(v.isValid(eq(REFERENCE_STATION_ID))).thenReturn(true);
        MvcResult r = mockMvc
                .perform(post("/api/extintegration/tyto/v1/stations/ddf111/pairingRequests")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        JsonNode node = mapper.readTree(r.getResponse().getContentAsString());
        assertEquals(REFERENCE_PAIRING_CODE, node.get("pairingCode").asText());
    }

    @Test
    public void stationDevicePairingRequest_invalidParams() throws Exception {
        mockMvc
                .perform(post("/api/extintegration/tyto/v1/stations/!{}[]1234567890/pairingRequests")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void stationDevicePairingRequest_endpointNotFound() throws Exception {
        when(tytoApi.pairDeviceWithStation(eq(REFERENCE_STATION_ID), eq(REFERENCE_TENANT_ID))).thenThrow(
                new TytoCommunicationException("exception when getting remote response", HttpStatus.NOT_FOUND.value(), "{\"code\" : \"ERROR_STATION_NOT_FOUND\"}")
        );
        mockMvc
                .perform(post("/api/extintegration/tyto/v1/stations/ddf111/pairingRequests")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
    
    /**
     * Utility to set the mock remote ip address
     * @param remoteAddress
     * @return
     */
    private static RequestPostProcessor remoteAddr(String remoteAddress) {
    	return new RequestPostProcessor() {
			
			@Override
			public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
				request.setRemoteAddr(remoteAddress);
				return request;
			}
		};
    }
}

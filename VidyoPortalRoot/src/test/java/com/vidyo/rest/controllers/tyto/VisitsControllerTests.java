package com.vidyo.rest.controllers.tyto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vidyo.dto.*;
import com.vidyo.exceptions.tyto.TytoCommunicationException;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.IUserService;
import com.vidyo.services.TytoRemoteAPIService;
import com.vidyo.services.VidyoTytoBridge;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

import static java.lang.String.format;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.vidyo.TytoConstants.*;

public class VisitsControllerTests {

	private static final String REFERENCE_ENDPOINT_ID = "123";
	private static final String REFERENCE_VISIT_ID = "vvv333";
	private static final int REFERENCE_TENANT_ID = 532;
	private static final String STATION_UUID = "xxxx-yyyy-zzzz";

	private static final String CLINICIAN_IDENTIFIER = "EA2AEAD3DF92-1218340200";
	private static final String STATION_IDENTIFIER = "GUID1";
	private static final String TYTO_IDENTIFIER = "6d066b30868247269e88ec3d17e90eb8";
	private static final String CLINICIAN_URL = "https://<tyto-url-here>/index.htm#/startOnlineVisit/8zNRgwzl/a5ra46ZsYYJaPVo6CqhPr6oiYbcBf2qFDVtCIylKX6w*/true";
	private static final String NOT_VALID_REFERENCE_VISIT_ID = "adsfasdfdsafsadfsajhdflkjsahgfdkjhsagfdgdsafkjhsadgfkjhgsadfjhgasdfkjh"
			+ "gsakjhfjlaskjdklajskldjklwqioeuiuwqeiuqwioeuiqwejioquweo";
	private static final String MANDATORY_FIELDS_EMPTY_OR_INVALID = "MANDATORY_FIELDS_EMPTY_OR_INVALID";

	@Mock
	private TytoRemoteAPIService tytoApi;
	
	@Mock
	private IUserService user;
	
	@Mock
	BindingResult result;

	private MockMvc mockMvc;

	private ObjectMapper mapper = new ObjectMapper();

	@Mock
	private VidyoTytoBridge apiBridge;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(
				new VisitsController(tytoApi,
						apiBridge)
		).setControllerAdvice(new TytoErrorHandlerController()).build();
		TenantContext.setTenantId(REFERENCE_TENANT_ID);
	}

	@Test
	public void visitRequest_success() throws Exception {
		VisitRequest visitRequest = new VisitRequest(REFERENCE_VISIT_ID, CLINICIAN_IDENTIFIER, STATION_IDENTIFIER);
		String requestAsString = mapper.writeValueAsString(visitRequest);
		VisitResponse visitResponse = new VisitResponse(TYTO_IDENTIFIER, "http://<patient_url_here>", CLINICIAN_URL, false);
		String responseAsString = mapper.writeValueAsString(visitResponse);
		when(tytoApi.createVisit(eq(visitRequest), anyInt(), any())).thenReturn(visitResponse);
		MvcResult result = mockMvc
				.perform(
						post("/api/extintegration/tyto/v1/visits/")
						.content(requestAsString)
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						)
				.andExpect(status().isCreated())
				.andExpect(content().string(responseAsString))
				.andReturn();
		JsonNode jsonNode = mapper.readTree(result.getResponse().getContentAsString());
		assertEquals(TYTO_IDENTIFIER, jsonNode.get("tytoIdentifier").asText());
	}

	@Test
	public void getVisitSuccess() throws Exception {
		GetVisitResponse response = new GetVisitResponse();
		response.setIdentifier(REFERENCE_ENDPOINT_ID);
		response.setTytoIdentifier(REFERENCE_VISIT_ID);
		response.setIsOnline(true);
		response.setIsClinicianOnline(true);
		response.setIsPatientOnline(true);

		when(tytoApi.getVisit(eq(REFERENCE_VISIT_ID), eq(REFERENCE_TENANT_ID))).thenReturn(response);
		when(user.getLinkedEndpointGUID(anyInt())).thenReturn(REFERENCE_ENDPOINT_ID);
		MvcResult resp = mockMvc
				.perform(get("/api/extintegration/tyto/v1/visits/" + REFERENCE_VISIT_ID)
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andReturn();

		JsonNode node = mapper.readTree(resp.getResponse().getContentAsString());
		assertEquals(REFERENCE_VISIT_ID, node.get("tytoIdentifier").asText());
		assertEquals("true", node.get("isClinicianOnline").asText());
		assertEquals("true", node.get("isPatientOnline").asText());
		assertEquals("true", node.get("isOnline").asText());
		assertEquals(REFERENCE_ENDPOINT_ID, node.get("identifier").asText());
	}

	@Test
	public void getVisitCreateNotFoundResponse() throws Exception {
		when(tytoApi.getVisit(eq(REFERENCE_VISIT_ID), eq(REFERENCE_TENANT_ID)))
			.thenThrow(new TytoCommunicationException("exception when getting remote response",
    			HttpStatus.NOT_FOUND.value(), "{\"code\" : \"ERROR_VISIT_NOT_FOUND\"}"));

		mockMvc.perform(get("/api/extintegration/tyto/v1/visits/" + REFERENCE_VISIT_ID)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	public void createVisitReviewSuccess() throws Exception {
		VisitReviewTytoRequest tytoRequest = new VisitReviewTytoRequest();
		tytoRequest.setClinicianIdentifier("12@1");
		tytoRequest.setClinicianRemoteAddress("127.0.0.1");
		VisitReviewTytoResponse tytoResponse = new VisitReviewTytoResponse();
		tytoResponse.setClinicianUrl("127.0.0.1");
	    String requestJson =  "{" +
				" \"reviewerIdentifier\" : \"12345\" " +
				"}";


		when(tytoApi.createVisitReview(any(), eq("12"), eq(REFERENCE_TENANT_ID)))
			.thenReturn(tytoResponse);

		when(apiBridge.createVisitReview(anyInt(), any(VisitReviewRequest.class), any())).thenReturn(tytoRequest);

		MvcResult response = mockMvc
				.perform(put("/api/extintegration/tyto/v1/visits/12/reviews", requestJson)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(requestJson))
				.andExpect(status().isOk()).andReturn();

		assertEquals(200, response.getResponse().getStatus());
		JsonNode node = mapper.readTree(response.getResponse().getContentAsString());
		assertEquals("127.0.0.1", node.get("reviewerUrl").asText());
	}

	@Test
	public void createVisitReviewBadRequestResponse() throws Exception {
		mockMvc.perform(put("/api/extintegration/tyto/v1/visits/12/reviews")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void createVisitReviewNotFoundResponse() throws Exception {
		VisitReviewTytoRequest tytoRequest = new VisitReviewTytoRequest();
		tytoRequest.setClinicianIdentifier("12@1");
		tytoRequest.setClinicianRemoteAddress("127.0.0.1");
		VisitReviewTytoResponse tytoResponse = new VisitReviewTytoResponse();
		tytoResponse.setClinicianUrl("127.0.0.1");

		VisitReviewRequest request = new VisitReviewRequest("12345");
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson = objectWriter.writeValueAsString(request);
		when(apiBridge.createVisitReview(anyInt(), any(VisitReviewRequest.class), any())).thenReturn(tytoRequest);
		doThrow(new TytoCommunicationException("exception when getting remote response",
    			HttpStatus.NOT_FOUND.value(), "{\"code\" : \"ERROR_VISIT_NOT_FOUND\"}")).when(tytoApi)
				.createVisitReview(any(), any(), eq(REFERENCE_TENANT_ID));

		mockMvc.perform(put("/api/extintegration/tyto/v1/visits/12/reviews")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(requestJson))
				.andExpect(status().isNotFound());
	}

	@Test
	public void visitAlreadyExist()throws Exception{
		VisitRequest visitRequest = new VisitRequest(REFERENCE_VISIT_ID, CLINICIAN_IDENTIFIER, STATION_IDENTIFIER);
		String requestAsString = mapper.writeValueAsString(visitRequest);
		when(tytoApi.createVisit(eq(visitRequest), anyInt(), any())).thenThrow(new TytoCommunicationException("visit already exist",
				HttpStatus.CONFLICT.value(), "{\"code\" : \"VISIT_ALREADY_EXISTS\"}"));
		mockMvc.perform(post("/api/extintegration/tyto/v1/visits/").content(requestAsString)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isConflict());
	}

	@Test
	public void createVisitBadTytoId()throws Exception{
		String visitRequest = "{\n" +
				format("\"identifier\" : \"%s\", " , REFERENCE_VISIT_ID) +
				format("\"clinicianIdentifier\" : \"%s\",", CLINICIAN_IDENTIFIER) +
				format("\"stationIdentifier\" : \"%s\" ", STATION_IDENTIFIER) +
				format("\"stationUUID\" : \"%s\" ",  STATION_UUID) +
				"}";
		mockMvc.perform(post("/api/extintegration/tyto/v1/visits/").content(visitRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public void createVisitBadRequestBody()throws Exception{
		String visitRequest = "{\n" +
				format("\"identifier\" : \"%s\", " , REFERENCE_VISIT_ID) +
				format("\"clinicianIdentifier\" : \"%s\",", CLINICIAN_IDENTIFIER) +
				"\"stationIdentifier\" : null " +
				"}";
		mockMvc.perform(post("/api/extintegration/tyto/v1/visits/").content(visitRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest());
	}

	@Test
	public void testChangeVisitStatus() throws Exception {
	    String requestBody = "{" +
                "  \"status\"              : \"COMPLETED\" "  +
                "}";
        mockMvc.perform(put("/api/extintegration/tyto/v1/visits/"+
                REFERENCE_VISIT_ID + "/status")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
	}
	@Test
	public void createVisitNotValidIdentifier() throws Exception {
		VisitRequest visitRequest = new VisitRequest(NOT_VALID_REFERENCE_VISIT_ID, CLINICIAN_IDENTIFIER, STATION_IDENTIFIER);
		String requestAsString = mapper.writeValueAsString(visitRequest);
		MvcResult response = mockMvc.perform(post("/api/extintegration/tyto/v1/visits/").content(requestAsString)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isBadRequest()).andReturn();
		JsonNode node = mapper.readTree(response.getResponse().getContentAsString());
		assertEquals(MANDATORY_FIELDS_EMPTY_OR_INVALID, node.get(0).asText());
	}
	@Test
	public void testInvalidChangeVisitStatus() throws Exception {
		String requestBody = "{" +
                " \"status\"              : \"@#\" "  +
                "}";		
        MvcResult result = mockMvc.perform(put("/api/extintegration/tyto/v1/visits/"+
                REFERENCE_VISIT_ID + "/status")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andReturn();
        JsonNode jsonNode = mapper.readTree(result.getResponse().getContentAsString());
		assertEquals(INVALID_VISIT_STATUS,jsonNode.get(0).asText());
	}
}

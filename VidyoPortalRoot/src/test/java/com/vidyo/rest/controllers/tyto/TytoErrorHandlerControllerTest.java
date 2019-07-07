package com.vidyo.rest.controllers.tyto;

import com.vidyo.exceptions.tyto.TytoCommunicationException;
import com.vidyo.exceptions.tyto.TytoProcessingException;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.services.TytoRemoteAPIService;
import com.vidyo.services.VidyoTytoBridge;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.ResourceAccessException;

import java.net.UnknownHostException;
import java.util.List;

import static com.vidyo.TytoConstants.FEATURE_CONFIG_ERROR;
import static com.vidyo.TytoConstants.STATION_DOES_NOT_EXIST;
import static com.vidyo.TytoConstants.TytoErrorCodes.ERROR_STATION_NOT_FOUND;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class TytoErrorHandlerControllerTest {
	

    private TytoErrorHandlerController errorHandler;
	private static final String REFERENCE_VISIT_ID = "vvv333";
	private static final int REFERENCE_TENANT_ID = 532;
    
	@Mock
	private VidyoTytoBridge apiBridge;
	@Mock
	private TytoRemoteAPIService tytoApi;
    
    MockMvc mockMvc;

    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
        errorHandler = new TytoErrorHandlerController();
        mockMvc = MockMvcBuilders.standaloneSetup(
				new VisitsController(tytoApi,
						apiBridge)
		).setControllerAdvice(new TytoErrorHandlerController()).build();
    }

    @Test
    public void station_not_found_response() throws Exception {
        TytoCommunicationException e = new TytoCommunicationException("test message",
                NOT_FOUND.value(),
                "{ \"code\" : \"" + ERROR_STATION_NOT_FOUND + "\"}");

        ResponseEntity<?> errorResponse = errorHandler.handleProcessingException(e);
        assertEquals(NOT_FOUND.value(), errorResponse.getStatusCodeValue());
        assertNotNull(errorResponse.getBody());
        List<String> l = (List<String>) errorResponse.getBody();
        assertEquals(STATION_DOES_NOT_EXIST, l.get(0));

    }

    @Test
    public void tyto_authentication_error() {
        TytoCommunicationException e = new TytoCommunicationException("test message",
                UNAUTHORIZED.value(),
                "");
        ResponseEntity<?> errorResponse = errorHandler.handleProcessingException(e);
        assertEquals(INTERNAL_SERVER_ERROR.value(), errorResponse.getStatusCodeValue());
        List<String> l = (List<String>) errorResponse.getBody();
        assertEquals(FEATURE_CONFIG_ERROR, l.get(0));
    }

    @Test
    public void tyto_processing_error() {
        TytoProcessingException e = new TytoProcessingException(FEATURE_CONFIG_ERROR,
                INTERNAL_SERVER_ERROR.value());
        ResponseEntity<?> errorResponse = errorHandler.handleProcessingException(e);
        assertEquals(INTERNAL_SERVER_ERROR.value(), errorResponse.getStatusCodeValue());
        List<String> l = (List<String>) errorResponse.getBody();
        assertEquals(FEATURE_CONFIG_ERROR, l.get(0));
    }
    
    @Test
    public void handle_wrong_address_errors() throws Exception {
    	ResourceAccessException re = new ResourceAccessException("test me ", new UnknownHostException());
    	when(tytoApi.getVisit(eq(REFERENCE_VISIT_ID), eq(REFERENCE_TENANT_ID)))
		.thenThrow(re);
    	TenantContext.setTenantId(REFERENCE_TENANT_ID);
    	mockMvc.perform(get("/api/extintegration/tyto/v1/visits/" + REFERENCE_VISIT_ID)
			.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isInternalServerError());
    }
}

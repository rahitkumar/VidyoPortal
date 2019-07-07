package com.vidyo.rest.controllers.tyto;

import com.vidyo.exceptions.tyto.TytoCommunicationException;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.services.TytoRemoteAPIService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CliniciansControllerTest {

    private static final int REFERENCE_TENANT_ID = 335;

    @Mock
    private TytoRemoteAPIService tytoApi;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(
                new CliniciansController(tytoApi))
                .setControllerAdvice(new TytoErrorHandlerController())
                .build();
        TenantContext.setTenantId(REFERENCE_TENANT_ID);
    }

    @Test
    public void testClinicianCreate() throws Exception {
        doNothing().when(tytoApi).createOrUpdateClinician(anyInt(), eq("endpointID335"));
        mockMvc
                .perform(put("/api/extintegration/tyto/v1/clinicians/endpointID335")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNoContent())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void testClinicianCreateInvalidDataLength() throws Exception {
    	doThrow(new TytoCommunicationException("exception when getting remote response", HttpStatus.BAD_REQUEST.value(), 
    			"{\"code\" : \"INVALID_USER_DATA_LENGTH\"}")).when(tytoApi).createOrUpdateClinician(anyInt(), eq("endpointID335"));
        mockMvc
                .perform(put("/api/extintegration/tyto/v1/clinicians/endpointID335")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
}

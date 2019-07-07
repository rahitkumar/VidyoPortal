package com.vidyo.rest.controllers.tyto;

import com.vidyo.exceptions.tyto.TytoInvalidUserDataException;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.services.TytoRemoteAPIService;
import static com.vidyo.TytoConstants.INVALID_USER_DATA_LENGTH;
import static java.util.Collections.singletonList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/extintegration/tyto/v1/clinicians")
public class CliniciansController {

    private static final Logger logger = LoggerFactory.getLogger(CliniciansController.class);

    private final TytoRemoteAPIService tytoApi;

    public CliniciansController(TytoRemoteAPIService tytoRemoteService) {
        this.tytoApi = tytoRemoteService;
    }

    @PutMapping("/{endpointId}")
    public HttpEntity<?> createClinician(@PathVariable ("endpointId") String endpointId) {
    	logger.debug("Entering createClinician of CliniciansController");
    	HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		
    	try {
    		tytoApi.createOrUpdateClinician(TenantContext.getTenantId(),
                endpointId);
    		logger.debug("Exiting createClinician of CliniciansController");
    		return new ResponseEntity<>(requestHeaders, HttpStatus.NO_CONTENT);
    	 } catch (TytoInvalidUserDataException e) {
    		 return new ResponseEntity<>(singletonList(INVALID_USER_DATA_LENGTH), HttpStatus.BAD_REQUEST);
         }
    }
}

package com.vidyo.services;

import static com.vidyo.TytoConstants.FEATURE_CONFIG_ERROR;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vidyo.validators.TytoIdValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.endpoints.Endpoint;
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
import com.vidyo.exceptions.tyto.TytoCommunicationException;
import com.vidyo.exceptions.tyto.TytoProcessingException;
import com.vidyo.exceptions.tyto.TytoInvalidUserDataException;
import com.vidyo.service.CryptService;
import com.vidyo.service.ExtIntegrationService;
import com.vidyo.service.ITenantService;

@Service
public class TytoRemoteAPIService {

    private static final Logger logger = LoggerFactory.getLogger(TytoRemoteAPIService.class);

    private static final String STATION_STATUS_URL = "/api/v1/integration/stations/{poc_id}";

    private static final String STATION_SAVE_URL = "/api/v1/integration/stations";
    private static final String VISIT_GET_URL = "/api/v1/integration/visits/{tyto_id}";
    private static final String VISIT_REVIEW_URL = "/api/v1/integration/visits/{tyto_id}/reviews";
    private static final String CLINICIAN_BASE_URL = "/api/v1/integration/clinicians/";
    private static final String VISIT_STATUS_UPDATE = VISIT_GET_URL + "/status";
    private static final String PAIRING_REQUEST_URL = STATION_STATUS_URL + "/pairingRequests";


    private static final String CREATE_VISIT_REQUEST_URL = "/api/v1/integration/visits";

    private final ITenantService tenantService;

    private final ExtIntegrationService extIntegrationService;

    private final CryptService cryptService;

    private final VidyoTytoBridge apiBridge;

    private final RestTemplate restTemplate;
    
    private final EndpointDao endpointDao;

    @Autowired
    public TytoRemoteAPIService(ITenantService tenantService,
                                ExtIntegrationService extIntegrationService,
                                CryptService cryptService,
                                VidyoTytoBridge vidyoTytoBridge,
                                RestTemplate restTemplate,
                                EndpointDao endpointDao) {
        this.tenantService = tenantService;
        this.extIntegrationService = extIntegrationService;
        this.cryptService = cryptService;
        this.apiBridge = vidyoTytoBridge;
        this.restTemplate = restTemplate;
        this.endpointDao = endpointDao;
    }

    /**
     * Returns the status of the station by the Patient's EndpointGUID.
     * The Endpoint GUID will be trimmed to 40 characters before sending the request to Tyto Server API.
     * The response contains details of the station's pairing, active and online status.
     * @param pocId endpointGUID of the patient
     * @param tenantId Id of the Tenant in which Tyto Integration is enabled
     * @return response received from tyto services
     */
    public StationStatusResponse getStationStatus(String pocId, int tenantId, String remoteIPAddress) {
    	// Tenant Data is cached, so not expensive to invoke this method to retrieve Tenant details
    	Tenant tenant = tenantService.getTenant(tenantId);
        if (extIntegrationService.isTenantTytoCareEnabled()) {
            TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);
			// Update the Endpoint's public IpAddress if it is available and changed
			List<Map<String, Object>> results = endpointDao.findPublicIpAndMemberId(tenantId, Arrays.asList(pocId));
			Stream<EndpointDetails> endpointDetails = results.stream()
					.map(mapObject -> new EndpointDetails((String) mapObject.get("endpointGUID"),
							(String) mapObject.get("endpointPublicIPAddress"), (Long) mapObject.get("memberID"),
							(String) mapObject.get("memberType")));
			Map<String, EndpointDetails> endpointData = new HashMap<String, EndpointDetails>();
			endpointDetails.forEach(endpointDetail -> endpointData.put(endpointDetail.endpointGUID, endpointDetail));
			// Update the Clinician's Endpoint public IP Address in Endpoints properties table
			// if it has changed after login or disconnect/reconnect
			if (endpointData.get(pocId) != null
					&& !remoteIPAddress.equalsIgnoreCase(endpointData.get(pocId).publicIPAddress)) {
				int updatedCount = this.endpointDao.updatePublicIPAddress(pocId, remoteIPAddress);
				logger.info("Updated row count {} while updating PublicIpAddress of Station EndpointGUID {}",
						updatedCount, pocId);
			} else {
				logger.error("Unexpected Error - Station Endpoint Data for EndpointGUID {} is not available in Portal",
						pocId);
			}

            Map<String, String> uriParams = new HashMap<>();
            // POC ID is the EndpointGUID of the Patient/Station. Trim to <= 40 chars length
            uriParams.put("poc_id", TytoIdValidator.truncateStationGUID(pocId));
            URI tytoRemoteURI = UriComponentsBuilder
                    .fromHttpUrl(tenantConfig.getTytoUrl())
                    .path(STATION_STATUS_URL)
                    .buildAndExpand(uriParams)
                    .toUri();
            HttpEntity<?> requestEntity = prepareTytoRequest(tenantConfig, Optional.empty());
            ResponseEntity<StationStatusResponse> r = performCommunication(tytoRemoteURI,
                    GET,
                    requestEntity,
                    StationStatusResponse.class);
            return r.getBody();
        } else {
        	logger.error("Tytocare Integration is not enabled for the Tenant {} ", tenant != null ? tenant.getTenantURL() : tenantId);
            throw new TytoProcessingException(FEATURE_CONFIG_ERROR, FORBIDDEN.value());
        }
    }

    public void createStation(StationSaveRequest request, int tenantId) {
    	Tenant tenant = tenantService.getTenant(tenantId);
        if (extIntegrationService.isTenantTytoCareEnabled()) {
            TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);
            URI tytoRemoteURI = UriComponentsBuilder
                    .fromHttpUrl(tenantConfig.getTytoUrl())
                    .path(STATION_SAVE_URL)
                    .buildAndExpand(new HashMap<String, String>())
                    .toUri();
            // The identifier in the request is the POC ID (Patient's EndpointGUID). Trim to max 40 chars
            request.setIdentifier(TytoIdValidator.truncateStationGUID(request.getIdentifier()));
            HttpEntity<?> requestEntity = prepareTytoRequest(tenantConfig, Optional.of(request));
            performCommunication(tytoRemoteURI,
                    POST,
                    requestEntity,
                    String.class);
            logger.debug("Create Station for EndpointGUID {} ", request.getIdentifier());
        } else {
        	logger.error("Tytocare Integration is not enabled for the Tenant {} ", tenant != null ? tenant.getTenantURL() : tenantId);
            throw new TytoProcessingException(FEATURE_CONFIG_ERROR, FORBIDDEN.value());
        }
    }

    public VisitResponse createVisit(VisitRequest requestBody, int tenantId, String remoteAddress) {
    	Tenant tenant = tenantService.getTenant(tenantId);
        if (extIntegrationService.isTenantTytoCareEnabled()) {
            TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);
            URI createVisitURI = UriComponentsBuilder
                    .fromHttpUrl(tenantConfig.getTytoUrl())
                    .path(CREATE_VISIT_REQUEST_URL)
                    .build()
                    .toUri();
            TytoCreateVisitRequest tytoRequest = apiBridge.createVisit(tenantId, requestBody, remoteAddress);
            HttpEntity<?> requestEntity = prepareTytoRequest(tenantConfig, Optional.of(tytoRequest));
            ResponseEntity<VisitResponse> visitResponse = performCommunication(createVisitURI,
                    POST,
                    requestEntity,
                    VisitResponse.class);
            return visitResponse.getBody();
        } else {
            logger.error("Tytocare Integration is not enabled for the Tenant {} ", tenant != null ? tenant.getTenantURL() : tenantId);
            throw new TytoProcessingException(FEATURE_CONFIG_ERROR, FORBIDDEN.value());
        }
    }

    public PairingResponse pairDeviceWithStation(String pocId, int tenantId) {
    	Tenant tenant = tenantService.getTenant(tenantId);
        if (extIntegrationService.isTenantTytoCareEnabled()) {
            TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);
            Map<String, String> uriParams = new HashMap<>();
            uriParams.put("poc_id", TytoIdValidator.truncateStationGUID(pocId));
            URI tytoRemoteURI = UriComponentsBuilder
                    .fromHttpUrl(tenantConfig.getTytoUrl())
                    .path(PAIRING_REQUEST_URL)
                    .buildAndExpand(uriParams)
                    .toUri();
            HttpEntity<?> requestEntity = prepareTytoRequest(tenantConfig, Optional.empty());
            logger.debug("sending new pairing request to tyto services pocId: {}, remoteURI: {} ", pocId,
                    tytoRemoteURI.toString());
            ResponseEntity<PairingResponse> r = performCommunication(tytoRemoteURI,
                    POST,
                    requestEntity,
                    PairingResponse.class);
            return r.getBody();
        } else {
        	logger.error("Tytocare Integration is not enabled for the Tenant {} ", tenant != null ? tenant.getTenantURL() : tenantId);
            throw new TytoProcessingException(FEATURE_CONFIG_ERROR, FORBIDDEN.value());
        }
    }

    public GetVisitResponse getVisit(String tytoId, int tenantId) {
    	Tenant tenant = tenantService.getTenant(tenantId);
        if (extIntegrationService.isTenantTytoCareEnabled()) {
            TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);

            Map<String, String> uriParams = new HashMap<>();
            uriParams.put("tyto_id", tytoId);
            URI tytoRemoteURI = UriComponentsBuilder
                    .fromHttpUrl(tenantConfig.getTytoUrl())
                    .path(VISIT_GET_URL)
                    .buildAndExpand(uriParams)
                    .toUri();
            //TODO: code smell - use optional instead
            HttpEntity<?> requestEntity = prepareTytoRequest(tenantConfig, Optional.empty());
            ResponseEntity<GetVisitResponse> r = performCommunication(tytoRemoteURI,
                    GET,
                    requestEntity,
                    GetVisitResponse.class);
            return apiBridge.cleanUpGetVisitResponse(r.getBody());
        } else {
        	logger.error("Tytocare Integration is not enabled for the Tenant {} ", tenant != null ? tenant.getTenantURL() : tenantId);
            throw new TytoProcessingException(FEATURE_CONFIG_ERROR, FORBIDDEN.value());
        }
    }

    public void changeVisitStatus(int tenantId,
                                  String visitId,
                                  ChangeVisitStatusRequest changeStateRequest) {
        Tenant tenant = tenantService.getTenant(tenantId);
        logger.debug("changing status for visit with id {}, tenant ID: {} to: {} ",
                visitId,
                tenantId,
                changeStateRequest.getStatus());
        if (extIntegrationService.isTenantTytoCareEnabled()) {
            TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);
            Map<String, String> uriParams = new HashMap<>();
            uriParams.put("tyto_id", visitId);
            URI targetAddress = UriComponentsBuilder
                    .fromHttpUrl(tenantConfig.getTytoUrl())
                    .path(VISIT_STATUS_UPDATE)
                    .buildAndExpand(uriParams)
                    .toUri();
            HttpEntity<?> requestEntity = prepareTytoRequest(tenantConfig, Optional.of(changeStateRequest));
            ResponseEntity<String> r = performCommunication(targetAddress,
                    PUT,
                    requestEntity,
                    String.class);
            logger.debug("changing status of visit id {}, response form Tyto Services: {}", visitId, r.getStatusCodeValue());
        } else {
            logger.info("Tytocare Integration is not enabled for the Tenant {} ", tenant != null ? tenant.getTenantURL() : tenantId);
            throw new TytoProcessingException(FEATURE_CONFIG_ERROR, FORBIDDEN.value());
        }
    }

    public VisitReviewTytoResponse createVisitReview(VisitReviewTytoRequest request, String tytoId,
    		int tenantId) {
    	Tenant tenant = tenantService.getTenant(tenantId);
    	if (extIntegrationService.isTenantTytoCareEnabled()) {
            TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);

            Map<String, String> uriParams = new HashMap<>();
            uriParams.put("tyto_id", tytoId);
            URI tytoRemoteURI = UriComponentsBuilder
                    .fromHttpUrl(tenantConfig.getTytoUrl())
                    .path(VISIT_REVIEW_URL)
                    .buildAndExpand(uriParams)
                    .toUri();
            HttpEntity<?> requestEntity = prepareTytoRequest(tenantConfig, Optional.of(request));
            logger.debug("sending create visit review request to tyto services visitID: {}, remoteURI: {} ",
            		tytoId, tytoRemoteURI.toString());

            HttpEntity<VisitReviewTytoResponse> response = performCommunication(tytoRemoteURI,
                    POST,
                    requestEntity,
                    VisitReviewTytoResponse.class);
            return response.getBody();
        } else {
        	logger.error("Tytocare Integration is not enabled for the Tenant {} ", tenant != null ? tenant.getTenantURL() : tenantId);
            throw new TytoProcessingException(FEATURE_CONFIG_ERROR, FORBIDDEN.value());
        }
    }

    public void createOrUpdateClinician(int tenantId, String endpointId) {
    	Tenant tenant = tenantService.getTenant(tenantId);
        if (extIntegrationService.isTenantTytoCareEnabled()) {
        	// In this API, EndpointGUID need not be truncated
            TenantConfiguration tenantConfig = tenantService.getTenantConfiguration(tenantId);
            Map<String, Object> userData = apiBridge.findPublicIpAndMemberId(tenantId, endpointId);
            Long memberID = (Long) userData.get("memberID");
            String memberType = (String) userData.get("memberType");
            //Translate the EndpointGUID to MemberID@TenantId_MemberType
            String clinicianID = apiBridge.translateVidyoToTytoId(tenantId, memberID, memberType);
            
            performCreateOrUpdateClinician(tenantId, memberID, clinicianID, tenantConfig);  
        } else {
        	logger.error("Tytocare Integration is not enabled for the Tenant {} ", tenant != null ? tenant.getTenantURL() : tenantId);
            throw new TytoProcessingException(FEATURE_CONFIG_ERROR, FORBIDDEN.value());
        }
    }

    private HttpEntity<?> prepareTytoRequest(TenantConfiguration tenantConfig,
                                          Optional<?> dataToSend) {
        String decryptedPassword = cryptService.decrypt(tenantConfig.getTytoPassword());
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.set("Cache-Control", "no-cache");
        requestHeaders.set("Pragma", "no-cache");
        String usernamePassword = tenantConfig.getTytoUsername() + ":" + decryptedPassword;
        byte[] encodedAuth = Base64.getEncoder().encode(
                usernamePassword.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );
        requestHeaders.set( "Authorization", authHeader );
        requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        requestHeaders.setExpires(0);
        return dataToSend.map(body -> new HttpEntity<>(body, requestHeaders))
                .orElseGet(() -> new HttpEntity<>(requestHeaders));
    }

    private <T> ResponseEntity<T> performCommunication(URI tytoRemoteURI,
                                                   HttpMethod method,
                                                   HttpEntity<?> requestEntity,
                                                   Class<T> clazz) {
        try {
        	logger.debug("sending request to tyto services, uri {} , method", tytoRemoteURI, method);
            ResponseEntity<T> response = restTemplate.exchange(tytoRemoteURI, method, requestEntity, clazz);
            logger.debug("trying sending request to tyto: {} , responseCode: ", tytoRemoteURI.toString(), response.getStatusCode());
            return response;
        } catch (RestClientResponseException e) {
            logger.error("Received an error from tyto services \n:" +
                            ", URI: {} \n" +
                            ", method: {} \n " +
                            ", requestBody: {} \n" +
                            ", received response status: {}, http code: {} \n" +
                            ", responseBody: {}",
                    tytoRemoteURI.toString(),
                    method.toString(),
                    requestEntity.getBody() != null ? requestEntity.getBody().toString() : " None ,",
                    e.getStatusText(),
                    e.getRawStatusCode(),
                    e.getResponseBodyAsString()
            );
            throw new TytoCommunicationException(e.getMessage(),
                    e.getRawStatusCode(),
                    e.getResponseBodyAsString()
            );
        }
    }

    private void performCreateOrUpdateClinician(int tenantId, Long memberId, 
    		String tytoClinicianId, TenantConfiguration tenantConfig) {
    	boolean clinicianExist;
    	
        try {
            logger.debug("checking whether clinician with id: {} exists in Tyto system", tytoClinicianId);
            HttpEntity<?> e = prepareTytoRequest(tenantConfig, Optional.empty());
            URI tytoUri = UriComponentsBuilder
                    .fromHttpUrl(tenantConfig.getTytoUrl())
                    .path(CLINICIAN_BASE_URL)
                    .path(tytoClinicianId)
                    .build()
                    .toUri();
            ResponseEntity<ClinicianResponse> response = performCommunication(tytoUri,
                    HttpMethod.GET,
                    e,
                    ClinicianResponse.class);
            clinicianExist = true;
            
            // clinician exist and modified - update
            if (this.apiBridge.isClinicianModified(tenantId, memberId, response.getBody())) {
            	logger.info("clinician with id: {} modified in tyto system, will update one",  tytoClinicianId);
            	updateClinician(tenantId, memberId, tytoClinicianId, tenantConfig);
            }
        } catch (TytoCommunicationException te) {
            if (te.getErrorCode() == NOT_FOUND.value()) {
                logger.warn("clinician with id: {} doesn't exist in tyto system", tytoClinicianId);
                clinicianExist = false;
            } else if (te.getErrorCode() == BAD_REQUEST.value()) {
            	logger.warn("clinician user data with id: {} are invalid", tytoClinicianId);
                throw new TytoInvalidUserDataException(te.getMessage(), te.getErrorCode());
            } else {
                throw te;
            }
        }
        
        try {
        	if (!clinicianExist) {
        		logger.debug("clinician with id: {} doesn't exist in tyto system, will create one",  
        				tytoClinicianId);
        		createClinician(tenantId, memberId, tytoClinicianId, tenantConfig);
        	}
        } catch (TytoCommunicationException te) {
        	if (te.getErrorCode() == BAD_REQUEST.value()) {
            	logger.warn("clinician user data with id: {} are invalid", tytoClinicianId);
                throw new TytoInvalidUserDataException(te.getMessage(), te.getErrorCode());
            } else {
                throw te;
            }
        }
    }

    private void createClinician(int tenantId,
                                 Long memberId,
                                 String clinicianID,
                                 TenantConfiguration tenantConfig) {
        URI createURI = UriComponentsBuilder
                .fromHttpUrl(tenantConfig.getTytoUrl())
                .path(CLINICIAN_BASE_URL)
                .build()
                .toUri();
        TytoCreateClinicianRq clinicianRq = apiBridge.createClinicianRq(tenantId,
                memberId,
                clinicianID);
        HttpEntity<?> requestEntity = prepareTytoRequest(tenantConfig, Optional.of(clinicianRq));
        ResponseEntity<String> response = performCommunication(createURI,
                HttpMethod.POST,
                requestEntity,
                String.class);
        logger.debug("created new clinician in Tyto system, clinicianId: {}, response from tyto {}  ",
                clinicianID,
                response.getBody());
    }
 
    private void updateClinician(int tenantId, Long memberId, String clinicianID, 
    		TenantConfiguration tenantConfig) {
    	URI createURI = UriComponentsBuilder
    			.fromHttpUrl(tenantConfig.getTytoUrl())
    			.path(CLINICIAN_BASE_URL)
    			.path(clinicianID)
    			.build()
    			.toUri();
    	TytoCreateClinicianRq clinicianRq = apiBridge.createClinicianRq(tenantId, memberId, null); // clinicianID
    	HttpEntity<?> requestEntity = prepareTytoRequest(tenantConfig, Optional.of(clinicianRq));
    	ResponseEntity<String> response = performCommunication(createURI,
    			HttpMethod.PUT,
    			requestEntity,
    			String.class);
    	logger.info("updated clinician in Tyto system, clinicianId: {}, response from tyto {}  ",
    			clinicianID,
    			response.getBody());
    }
    
	private static class EndpointDetails {
		private final String publicIPAddress;
		private final Long memberID;
		private final String memberType;
		private String endpointGUID;

		EndpointDetails(String endpointGUID, String publicIPAddress, Long memberID, String memberType) {
			this.endpointGUID = endpointGUID;
			this.publicIPAddress = publicIPAddress;
			this.memberID = memberID;
			this.memberType = memberType;
		}
	}
}

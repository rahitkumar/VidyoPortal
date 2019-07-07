package com.vidyo.rest.controllers.tyto;

import com.vidyo.dto.PairingResponse;
import com.vidyo.dto.StationSaveRequest;
import com.vidyo.dto.StationStatusResponse;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.services.TytoRemoteAPIService;
import com.vidyo.utils.LogUtils;
import com.vidyo.validators.TytoIdValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static com.vidyo.TytoConstants.INVALID_STATION_ID;
import static java.util.Collections.singletonList;

@RestController
@RequestMapping(value = "/api/extintegration/tyto/v1/stations")
@Validated
public class StationsController {

    private static final Logger log = LoggerFactory.getLogger(StationsController.class);

	private final TytoRemoteAPIService tytoApi;

	@Autowired
	public StationsController(TytoRemoteAPIService tytoRemoteService) {
		this.tytoApi = tytoRemoteService;
	}

	@GetMapping(value = "/{pocId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getStationStatus(@PathVariable("pocId") String pocId, HttpServletRequest request) {
			if (TytoIdValidator.isValid(pocId)) {
				StationStatusResponse tytoResponse = tytoApi.getStationStatus(pocId,
                        TenantContext.getTenantId(), request.getRemoteAddr());
				return new ResponseEntity<>(tytoResponse, HttpStatus.OK);
			} else {
				LogUtils.logValidationError(pocId, request, null);
				return new ResponseEntity<>(singletonList(INVALID_STATION_ID), 
						HttpStatus.BAD_REQUEST);
			}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createStation(@Valid @RequestBody StationSaveRequest request, HttpServletRequest req) {
			if (TytoIdValidator.isValid(request.getIdentifier())) {
				tytoApi.createStation(request, TenantContext.getTenantId());
				return new ResponseEntity<>(HttpStatus.CREATED);
			} else {
				LogUtils.logValidationError(request.getIdentifier(), req, (request != null) ? request.toString() : null);
				return new ResponseEntity<>(singletonList(INVALID_STATION_ID),
						HttpStatus.BAD_REQUEST);
			}
	}

    @PostMapping(value = "/{pocId}/pairingRequests",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> pairDeviceWithStation(
            @PathVariable("pocId") String pocId, HttpServletRequest request) {
        if (TytoIdValidator.isValid(pocId)) {
            PairingResponse tytoResponse = tytoApi.pairDeviceWithStation(pocId,
                    TenantContext.getTenantId());
            return new ResponseEntity<>(tytoResponse, HttpStatus.CREATED);
        } else {
        	LogUtils.logValidationError(pocId, request, null);
            return new ResponseEntity<>(singletonList(INVALID_STATION_ID), 
            		HttpStatus.BAD_REQUEST);
        }
    }
}

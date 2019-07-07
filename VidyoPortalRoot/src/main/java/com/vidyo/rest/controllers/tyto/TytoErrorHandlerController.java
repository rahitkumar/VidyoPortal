package com.vidyo.rest.controllers.tyto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidyo.exceptions.tyto.TytoCommunicationException;
import com.vidyo.exceptions.tyto.TytoProcessingException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

import static com.vidyo.TytoConstants.*;
import static java.util.Collections.singletonList;

@ControllerAdvice(value = "com.vidyo.rest.controllers.tyto")
public class TytoErrorHandlerController {

    private static final Logger logger = LoggerFactory.getLogger(StationsController.class);

    private ObjectMapper mapper = new ObjectMapper();

    @ExceptionHandler(value = {TytoCommunicationException.class})
    @ResponseBody
    public ResponseEntity<?> handleProcessingException(final TytoCommunicationException e) {
        ResponseTuple tuple = translateTytoResponseCode(e.getExtData(), e.getErrorCode());
        HttpStatus httpStatus = HttpStatus.valueOf(tuple.getCode());
        logger.error("processed tyto communication exception, returning status: {} and error body to client {}",
                tuple.getCode(), tuple.getMessage());
        return new ResponseEntity<>(singletonList(tuple.getMessage()), prepareHeaders(), httpStatus);
    }

    @ExceptionHandler(value = {TytoProcessingException.class})
    @ResponseBody
    public ResponseEntity<?> handleProcessingException(final TytoProcessingException e) {
        logger.error(" Caught processing exception:, errorCode: {}, statusCode: {}. This is an exception on Portal side" +
                        "no outbound connections to Tyto Services  were made", e.getMessageCode(), e.getErrorCode());
        logger.error("printing detailed trace: ", e);
        HttpStatus httpStatus = HttpStatus.valueOf(e.getErrorCode());
        return new ResponseEntity<>(singletonList(e.getMessageCode()), prepareHeaders(), httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<String> validationError(MethodArgumentNotValidException ex) {
        logger.error("received invalid object as input, errorMessage : {}", ex.getMessage());
        return singletonList(INVALID_STATION_ID);
    }
    
    @ExceptionHandler({UnknownHostException.class,SocketException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public List<String> handleWrongAddressErrors(IOException e) {
        logger.error("processing an exception : {}",e.getMessage(),e);
        return singletonList(FEATURE_CONFIG_ERROR);
    }
    
    @ExceptionHandler(HttpMessageConversionException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<String> validationError(HttpMessageConversionException ex) {
		
		if (ex != null && ex.getCause().getMessage().contains("VisitStatus")) {
			logger.error("received invalid enum : {}", ex.getMessage());
			return singletonList(INVALID_VISIT_STATUS);
		} else {
			// general error. Status Bad request , message
			logger.error("bad request: {}", ex.getMessage());
			return singletonList(INVALID_STATION_ID);
		}
	}
    
    private HttpHeaders prepareHeaders() {
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_JSON);
        return h;
    }

    private ResponseTuple translateTytoResponseCode(String input, int tytoErrorCode) {
        ResponseTuple returnedResult;
        if (HttpStatus.UNAUTHORIZED.value() == tytoErrorCode) {
            returnedResult = new ResponseTuple(FEATURE_CONFIG_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else if (HttpStatus.FORBIDDEN.value() == tytoErrorCode) {
            returnedResult = new ResponseTuple(FEATURE_CONFIG_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        } else if (StringUtils.isNotBlank(input)) {
            try {
                JsonNode node = mapper.readTree(input);
                String receivedErrorCode = node.get("code").asText();
                if (null == receivedErrorCode) {
                    returnedResult = new ResponseTuple(UNKNOWN_ERROR, tytoErrorCode);
                }  else if(receivedErrorCode.startsWith("ERROR_")) {
                    returnedResult = new ResponseTuple(receivedErrorCode.substring(("ERROR_").length()), tytoErrorCode);
                } else {
                    returnedResult = new ResponseTuple(receivedErrorCode, tytoErrorCode);
                }
            } catch (IOException ioe) {
                returnedResult = new ResponseTuple(TYTO_PARSE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());

            }
        } else {
            returnedResult = new ResponseTuple(UNKNOWN_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return returnedResult;
    }

    private static class ResponseTuple {

        private final String message;
        private final int code;

        private ResponseTuple(String message, int code) {
            this.message = message;
            this.code = code;
        }

        String getMessage() {
            return message;
        }

        int getCode() {
            return code;
        }
    }
}

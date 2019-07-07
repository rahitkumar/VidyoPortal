package com.vidyo.superapp.exceptions;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.vidyo.framework.service.ServiceException;


@ControllerAdvice
public class VidyoSuperExceptionHandler extends ResponseEntityExceptionHandler {
	
	protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(VidyoSuperExceptionHandler.class);
	
	@Autowired
    private MessageSource messageSource;

    @ExceptionHandler({ InvalidRequestException.class })
    protected ResponseEntity<Object> handleInvalidRequest(RuntimeException e, WebRequest request) {
    	logger.error("InvalidRequestException Occured::", e);
    	
        InvalidRequestException ire = (InvalidRequestException) e;
        List<FieldErrorResource> fieldErrorResources = new ArrayList<>();

        List<FieldError> fieldErrors = ire.getErrors().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            FieldErrorResource fieldErrorResource = new FieldErrorResource();
            fieldErrorResource.setResource(fieldError.getObjectName());
            fieldErrorResource.setField(fieldError.getField());
            fieldErrorResource.setCode(fieldError.getCode());
            fieldErrorResource.setMessage(fieldError.getDefaultMessage());
            fieldErrorResources.add(fieldErrorResource);
        }

        ErrorResource error = new ErrorResource("InvalidRequest", ire.getMessage());
        error.setFieldErrors(fieldErrorResources);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return handleExceptionInternal(e, error, headers, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }
    
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorInfo handleInvalidDataAccessApiUsageException(HttpServletRequest req, InvalidDataAccessApiUsageException ex) {
    	Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("super.user.general.error", null, locale);
         
        String errorURL = req.getRequestURL().toString();
        
        logger.error("InvalidDataAccessApiUsageException Occured:: URL="+ req.getRequestURL(), ex);
         
        return new ErrorInfo(errorURL, errorMessage);
    }
    
    
    @ExceptionHandler(ServiceException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorInfo handleVidyoServiceException(HttpServletRequest req, ServiceException ex) {
		Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("super.user.general.error", null, locale);
         
        String errorURL = req.getRequestURL().toString();
        
        logger.error("ServiceException Occured:: URL="+ req.getRequestURL(), ex);
         
        return new ErrorInfo(errorURL, errorMessage);
    }
	
	@ExceptionHandler(SQLException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorInfo handleSQLException(HttpServletRequest req, SQLException ex) {
		Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("super.user.general.error", null, locale);
         
        String errorURL = req.getRequestURL().toString();
        
        logger.error("SQLException Occured:: URL="+ req.getRequestURL(), ex);
         
        return new ErrorInfo(errorURL, errorMessage); 
    }
	
	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorInfo handleNumberFormatException(HttpServletRequest req, NumberFormatException ex) {
		Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("super.user.general.error", null, locale);
         
        String errorURL = req.getRequestURL().toString();
        
        logger.error("NumberFormatException Occured:: URL="+ req.getRequestURL(), ex);
         
        return new ErrorInfo(errorURL, errorMessage); 
    }
	
	@ExceptionHandler(IOException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorInfo handleIOException(HttpServletRequest req, IOException ex) {
		Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("super.user.general.error", null, locale);
         
        String errorURL = req.getRequestURL().toString();
        
        logger.error("IOException Occured:: URL="+ req.getRequestURL(), ex);
         
        return new ErrorInfo(errorURL, errorMessage); 
    }
	
	@ExceptionHandler(JsonParseException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorInfo handleJsonParseException(HttpServletRequest req, JsonParseException ex) {
		Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("super.user.general.error", null, locale);
         
        String errorURL = req.getRequestURL().toString();
        
        logger.error("JsonParseException Occured:: URL="+ req.getRequestURL(), ex);
         
        return new ErrorInfo(errorURL, errorMessage); 
    }
	
	@ExceptionHandler(JsonMappingException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorInfo handleJsonMappingException(HttpServletRequest req, JsonMappingException ex) {
		Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("super.user.general.error", null, locale);
         
        String errorURL = req.getRequestURL().toString();
        
        logger.error("JsonMappingException Occured:: URL="+ req.getRequestURL(), ex);
         
        return new ErrorInfo(errorURL, errorMessage); 
    }
	@ExceptionHandler(SQLGrammarException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody ErrorInfo handleSQLGrammarException(HttpServletRequest req, SQLGrammarException ex) {
		Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage("super.user.general.error", null, locale);
         
        String errorURL = req.getRequestURL().toString();
        
        logger.error("SQLGrammarException Occured:: URL="+ req.getRequestURL(), ex);
         
        return new ErrorInfo(errorURL, errorMessage); 
    }
}
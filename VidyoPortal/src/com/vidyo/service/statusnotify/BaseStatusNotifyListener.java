package com.vidyo.service.statusnotify;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.ExternalStatusNotification;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.service.ExternalStatusNotificationService;

/**
 * 
 * @author volodymyr
 *
 */
public class BaseStatusNotifyListener {

	protected final Logger logger = LoggerFactory.getLogger(BaseStatusNotifyListener.class.getName());

	protected StatusNotificationService statusNotificationService;

	protected ExternalStatusNotificationService tenantNotificationsService;
	
	protected RestTemplate restTemplate;
	
	protected RetryTemplate retryTemplate;

	/**
	 * @param retryTemplate the retryTemplate to set
	 */
	public void setRetryTemplate(RetryTemplate retryTemplate) {
		this.retryTemplate = retryTemplate;
	}

	/**
	 * @return the retryTemplate
	 */
	public RetryTemplate getRetryTemplate() {
		return retryTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setTenantNotificationsService(ExternalStatusNotificationService tenantNotificationsService) {
		this.tenantNotificationsService = tenantNotificationsService;
	}

	public void setStatusNotificationService(StatusNotificationService statusNotificationService) {
		this.statusNotificationService = statusNotificationService;
	}

	public StatusNotificationService getStatusNotificationService() {
		return statusNotificationService;
	}
	
	protected class RetryResult {
		
		private boolean isRetry;
		private String message;
		private boolean isSuccess;
		
		public RetryResult(boolean isRetry, String message, boolean isSuccess) {
			this.isRetry = isRetry;
			this.message = message;
			this.isSuccess = isSuccess;
		}

		public boolean isRetry() {
			return isRetry;
		}
		
		public String getMessage() {
			return message;
		}

		/**
		 * @param message the message to set
		 */
		public void setMessage(String message) {
			this.message = message;
		}

		public boolean isSuccess() {
			return isSuccess;
		}
	}

	// return true - if successful, false - if failed and no retry, exception - if failed and retry 
	protected RetryResult callExternalRestService(NotificationInfo notificationInfo) {
		CDRinfo2 info = notificationInfo.getUserNotification();
		String url = buildServiceUrl(notificationInfo, info);
		HttpHeaders headers = buildHttpHeaders(notificationInfo);
		if(logger.isDebugEnabled()) {
			logger.debug("External url->" + url + " Notification Info->" + notificationInfo);
		}
		// Retry template 
		ResponseEntity<String> response = retryTemplate.execute(context -> {
			System.out.println("Inside Retry" + context.getRetryCount());
			ResponseEntity<String> responseInternal = null;
			
			try {
				responseInternal = restTemplate.exchange(url, HttpMethod.GET,
					new HttpEntity<>(headers), String.class);
				
				System.out.println("responseInternal.getStatusCode() " + responseInternal.getStatusCode());
				if(!responseInternal.getStatusCode().is2xxSuccessful()) {
					HttpStatus status = responseInternal.getStatusCode();
					System.out.println("NON HTTP 200: " + status.value() + " " + status.getReasonPhrase());
					RetryResult retryResult = isRetryRequired(responseInternal.getBody());
					if (retryResult.isRetry()) {
						throw new RestClientException("Non HTTP 200 Status: " + status.value() + " " + status.getReasonPhrase());
					}
				}
			} catch (HttpClientErrorException exc) {
				System.out.println("Response body: " + exc.getResponseBodyAsString());
				RetryResult retryResult = isRetryRequired(exc.getResponseBodyAsString());
				if (retryResult.isRetry()) {
					throw new RestClientException("Non HTTP 200 Status: " + exc.getStatusCode() + " " + exc.getStatusText());
				} else {
					responseInternal = new ResponseEntity<String>(exc.getStatusText(), exc.getStatusCode());
				}
			} 
			
			return responseInternal;
		});
		
		boolean isSendSuccessful = response.getStatusCode().is2xxSuccessful();
		if (isSendSuccessful) {
			logger.debug("send nofitication was successful");
			// No retries required here
			return new RetryResult(false, response.getStatusCode().toString(), true); // success = true
		} else {
			logger.error("error respose from external server -> " + response.getStatusCode());
			// Not success,  No retries required here  
			return new RetryResult(false, "Non HTTP 200 Status: " + response.getStatusCode().toString() + " " + 
					response.getStatusCode().getReasonPhrase(), false); // response.getBody()
		}
	}
	
	/**
	 * Default implementation which is retry for all errors.
	 * @param response
	 * @return
	 */
	protected RetryResult isRetryRequired(String content) {
		return new RetryResult(true, "", false); // retry for all
	}
	
	protected void storeFailedNotification(NotificationInfo notificationInfo, RetryResult result) {
		ExternalStatusNotification notification = new ExternalStatusNotification();
		notification.setTenantID(notificationInfo.getTenantId());
		notification.setStatus("Failed");
		notification.setData(notificationInfo.getUserNotification().getExtData());
		notification.setDataType(notificationInfo.getUserNotification().getExtDataType());
		notification.setUrl(notificationInfo.getExternalStatusNotificationUrl());
		notification.setUsername(notificationInfo.getExternalUsername());
		notification.setPassword(notificationInfo.getExternalPassword());
		
		notification.setRetry(result.isRetry() ? 1 : 0);
		notification.setErrorMessage(result.getMessage());
		
		tenantNotificationsService.createNotification(notification);
	}

	public static HttpHeaders buildHttpHeaders(NotificationInfo notificationInfo) {
		HttpHeaders headers = new HttpHeaders();
		String auth = notificationInfo.getExternalUsername() + ":" + notificationInfo.getPlainTextExternalPassword();
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
		String authHeader = "Basic " + new String(encodedAuth);
		headers.add("Authorization", authHeader);

		return headers;
	}

	public static String buildServiceUrl(NotificationInfo notificationInfo, CDRinfo2 info) {
		UriBuilder uriBuilder = UriBuilder.fromPath(notificationInfo.getExternalStatusNotificationUrl());
		Map<String, String> parameters = getParametersFromExternalData(info.getExtData());

		uriBuilder.queryParam("ConferenceID", 
				(parameters.get("ConferenceID") != null) ? parameters.get("ConferenceID") : "");
		uriBuilder.queryParam("ExternalID", 
				(parameters.get("ExternalID") != null) ? parameters.get("ExternalID") : "");
		uriBuilder.queryParam("ExternalIDType", 
				(parameters.get("ExternalIDType") != null) ? parameters.get("ExternalIDType") : "");
		uriBuilder.queryParam("VendorName", "Vidyo");
		uriBuilder.queryParam("ConnectionStatus", 
				(parameters.get("ConnectionStatus") != null) ? parameters.get("ConnectionStatus") : "");

		return uriBuilder.build().toString();
	}

	public static Map<String, String> getParametersFromExternalData(String externalData) {
		Map<String, String> parametersFromExternalData = new HashMap<String, String>();
		String[] parts = externalData.split("&");

		if ((parts != null) && (parts.length > 0)) {
			for (String part : parts) {
				String[] nameValue = part.split("=");
				if ((nameValue != null) && (nameValue.length == 2)) {
					parametersFromExternalData.put(nameValue[0], nameValue[1]);
				}
			}
		}

		return parametersFromExternalData;
	}
}

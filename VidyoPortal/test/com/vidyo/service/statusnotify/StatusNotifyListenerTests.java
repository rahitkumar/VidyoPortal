package com.vidyo.service.statusnotify;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.vidyo.bo.CDRinfo2;
import com.vidyo.bo.statusnotify.NotificationInfo;
import com.vidyo.service.statusnotify.BaseStatusNotifyListener.RetryResult;

public class StatusNotifyListenerTests {

	@InjectMocks
	private StatusNotifyListener statusNotifyListener;

	@Mock
	private RestTemplate restTemplate;

	@BeforeMethod
	private void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@SuppressWarnings(value = "unchecked")
	public void testCallExternalRestServiceWithNoRetry() {
		NotificationInfo notificationInfo = new NotificationInfo();
		CDRinfo2 cdRinfo2 = new CDRinfo2();
		cdRinfo2.setExtData("ConferenceID=26699&ExternalID=FAMMD&ExternalIDType=1&VendorName=Vidyo&ConnectionStatus=1");
		notificationInfo.setExternalStatusNotificationUrl("https://external.com/");
		notificationInfo.setExternalUsername("user");
		notificationInfo.setPlainTextExternalPassword("password");
		notificationInfo.setUserNotification(cdRinfo2);
		String jsonResponse = "{\"Message\":\"An error has occurred.\",\"ExceptionMessage\":\"An error occurred while executing the command: INVALID-USER-ID.\",\"ExceptionType\":\"Epic.ServiceModel.Internal.ServiceCommandException\",\"StackTrace\":\"   at Epic.Clinical.Generated.Services.Epic_Clinical_PatientController.v2018_SETEXTERNALCONNECTIONSTATUS(String ConferenceID, String ExternalID, String ExternalIDType, String VendorName, Int32 ConnectionStatus)\\r\\n   at lambda_method(Closure , Object , Object[] )\\r\\n   at System.Web.Http.Controllers.ReflectedHttpActionDescriptor.ActionExecutor.<>c__DisplayClass10.<GetExecutor>b__9(Object instance, Object[] methodParameters)\\r\\n   at System.Web.Http.Controllers.ReflectedHttpActionDescriptor.ExecuteAsync(HttpControllerContext controllerContext, IDictionary`2 arguments, CancellationToken cancellationToken)\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Tracing.ITraceWriterExtensions.<TraceBeginEndAsyncCore>d__18`1.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Controllers.ApiControllerActionInvoker.<InvokeActionAsyncCore>d__0.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Tracing.ITraceWriterExtensions.<TraceBeginEndAsyncCore>d__18`1.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Filters.ActionFilterAttribute.<CallOnActionExecutedAsync>d__5.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Web.Http.Filters.ActionFilterAttribute.<CallOnActionExecutedAsync>d__5.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Filters.ActionFilterAttribute.<ExecuteActionFilterAsyncCore>d__0.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Controllers.ActionFilterResult.<ExecuteAsync>d__2.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Filters.AuthorizationFilterAttribute.<ExecuteAuthorizationFilterAsyncCore>d__2.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Filters.AuthorizationFilterAttribute.<ExecuteAuthorizationFilterAsyncCore>d__2.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Controllers.ExceptionFilterResult.<ExecuteAsync>d__0.MoveNext()\",\"InnerException\":{\"Message\":\"An error has occurred.\",\"ExceptionMessage\":\"An error occurred while executing the command: INVALID-USER-ID.\",\"ExceptionType\":\"Epic.Core.Communication.EcfCommandException\",\"StackTrace\":\"   at Epic.Core.Communication.Internal.EcfConnection.HandleErrorPacket(Byte[] response, Int32 packetLength, Int32 startIndex, Command command, Int64 endTime, INetworkStream networkStream)\\r\\n   at Epic.Core.Communication.Internal.EcfConnection.BuildResponseFromPacket(Int32 packetLength, Byte[] response, Command command, INetworkStream networkStream, Int64 endTime, Boolean responseExpected, ProcessState& state, String& pauseMessage)\\r\\n   at Epic.Core.Communication.Internal.EcfConnection.Execute(Command command, String instrumentationHeader)\\r\\n   at Epic.Core.Communication.Connection.Execute(Command command, Int32 lockAcquireTimeout)\\r\\n   at Epic.Core.Communication.Command.Execute(Int32 lockAcquireTimeout, EventHandler`1 asyncExecuteCompletedHandler)\\r\\n   at Epic.Clinical.Generated.Services.Epic_Clinical_PatientController.v2018_SETEXTERNALCONNECTIONSTATUS(String ConferenceID, String ExternalID, String ExternalIDType, String VendorName, Int32 ConnectionStatus)\"}}";
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(jsonResponse, HttpStatus.BAD_REQUEST);
		statusNotifyListener.setRetryTemplate(retryTemplate());

		when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
				.thenReturn(responseEntity);
		RetryResult retryResult = null;
		Exception e = null;
		try {
			retryResult = statusNotifyListener.callExternalRestService(notificationInfo);
		} catch (Exception rce) {
			System.out.println("Exception->" + rce.getMessage());
			e = rce;
		}
		
		Assert.assertNull(e);
		Assert.assertNotNull(retryResult);
		Assert.assertEquals(retryResult.isRetry(), false);
		Assert.assertEquals(retryResult.isSuccess(), false);
		Assert.assertEquals(retryResult.getMessage(), "Non HTTP 200 Status: 400 BAD_REQUEST Bad Request");
	}

	@Test
	@SuppressWarnings(value = "unchecked")
	public void testCallExternalRestServiceThrowingException() {
		NotificationInfo notificationInfo = new NotificationInfo();
		CDRinfo2 cdRinfo2 = new CDRinfo2();
		cdRinfo2.setExtData("ConferenceID=26699&ExternalID=FAMMD&ExternalIDType=1&VendorName=Vidyo&ConnectionStatus=1");
		notificationInfo.setExternalStatusNotificationUrl("https://external.com/");
		notificationInfo.setExternalUsername("user");
		notificationInfo.setPlainTextExternalPassword("password");
		notificationInfo.setUserNotification(cdRinfo2);
		String jsonResponse = "{\"Message\":\"An error has occurred.\",\"ExceptionMessage\":\"An error occurred while executing the command: INVALID-USER-ID.\",\"ExceptionType\":\"Epic.ServiceModel.Internal.ServiceCommandException\",\"StackTrace\":\"   at Epic.Clinical.Generated.Services.Epic_Clinical_PatientController.v2018_SETEXTERNALCONNECTIONSTATUS(String ConferenceID, String ExternalID, String ExternalIDType, String VendorName, Int32 ConnectionStatus)\\r\\n   at lambda_method(Closure , Object , Object[] )\\r\\n   at System.Web.Http.Controllers.ReflectedHttpActionDescriptor.ActionExecutor.<>c__DisplayClass10.<GetExecutor>b__9(Object instance, Object[] methodParameters)\\r\\n   at System.Web.Http.Controllers.ReflectedHttpActionDescriptor.ExecuteAsync(HttpControllerContext controllerContext, IDictionary`2 arguments, CancellationToken cancellationToken)\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Tracing.ITraceWriterExtensions.<TraceBeginEndAsyncCore>d__18`1.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Controllers.ApiControllerActionInvoker.<InvokeActionAsyncCore>d__0.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Tracing.ITraceWriterExtensions.<TraceBeginEndAsyncCore>d__18`1.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Filters.ActionFilterAttribute.<CallOnActionExecutedAsync>d__5.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Web.Http.Filters.ActionFilterAttribute.<CallOnActionExecutedAsync>d__5.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Filters.ActionFilterAttribute.<ExecuteActionFilterAsyncCore>d__0.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Controllers.ActionFilterResult.<ExecuteAsync>d__2.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Filters.AuthorizationFilterAttribute.<ExecuteAuthorizationFilterAsyncCore>d__2.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Filters.AuthorizationFilterAttribute.<ExecuteAuthorizationFilterAsyncCore>d__2.MoveNext()\\r\\n--- End of stack trace from previous location where exception was thrown ---\\r\\n   at System.Runtime.ExceptionServices.ExceptionDispatchInfo.Throw()\\r\\n   at System.Runtime.CompilerServices.TaskAwaiter.HandleNonSuccessAndDebuggerNotification(Task task)\\r\\n   at System.Web.Http.Controllers.ExceptionFilterResult.<ExecuteAsync>d__0.MoveNext()\",\"InnerException\":{\"Message\":\"An error has occurred.\",\"ExceptionMessage\":\"An error occurred while executing the command: INVALID-USER-ID.\",\"ExceptionType\":\"Epic.Core.Communication.EcfCommandException\",\"StackTrace\":\"   at Epic.Core.Communication.Internal.EcfConnection.HandleErrorPacket(Byte[] response, Int32 packetLength, Int32 startIndex, Command command, Int64 endTime, INetworkStream networkStream)\\r\\n   at Epic.Core.Communication.Internal.EcfConnection.BuildResponseFromPacket(Int32 packetLength, Byte[] response, Command command, INetworkStream networkStream, Int64 endTime, Boolean responseExpected, ProcessState& state, String& pauseMessage)\\r\\n   at Epic.Core.Communication.Internal.EcfConnection.Execute(Command command, String instrumentationHeader)\\r\\n   at Epic.Core.Communication.Connection.Execute(Command command, Int32 lockAcquireTimeout)\\r\\n   at Epic.Core.Communication.Command.Execute(Int32 lockAcquireTimeout, EventHandler`1 asyncExecuteCompletedHandler)\\r\\n   at Epic.Clinical.Generated.Services.Epic_Clinical_PatientController.v2018_SETEXTERNALCONNECTIONSTATUS(String ConferenceID, String ExternalID, String ExternalIDType, String VendorName, Int32 ConnectionStatus)\"}}";
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(jsonResponse, HttpStatus.BAD_REQUEST);
		statusNotifyListener.setRetryTemplate(retryTemplate());
		
		when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
				.thenThrow(new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE));
		
		RetryResult retryResult = null;
		Exception e = null;
		try {
			retryResult = statusNotifyListener.callExternalRestService(notificationInfo);
		} catch (Exception rce) {
			System.out.println("Exception->" + rce);
			e = rce;
		}
		Assert.assertNull(retryResult);
		Assert.assertNotNull(e);
		Assert.assertTrue(e instanceof HttpServerErrorException);
		Assert.assertEquals(e.getMessage(), "503 SERVICE_UNAVAILABLE");
	}

	@Test
	@SuppressWarnings(value = "unchecked")
	public void testCallExternalRestServiceSuccess() {
		NotificationInfo notificationInfo = new NotificationInfo();
		CDRinfo2 cdRinfo2 = new CDRinfo2();
		cdRinfo2.setExtData("ConferenceID=26699&ExternalID=FAMMD&ExternalIDType=1&VendorName=Vidyo&ConnectionStatus=1");
		notificationInfo.setExternalStatusNotificationUrl("https://external.com/");
		notificationInfo.setExternalUsername("user");
		notificationInfo.setPlainTextExternalPassword("password");
		notificationInfo.setUserNotification(cdRinfo2);
		String jsonResponse = "{\"Status\":\"1\"}";
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(jsonResponse, HttpStatus.OK);
		statusNotifyListener.setRetryTemplate(retryTemplate());
		
		when(restTemplate.exchange(any(String.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
				.thenReturn(responseEntity);
		RetryResult retryResult = null;
		Exception e = null;
		try {
			retryResult = statusNotifyListener.callExternalRestService(notificationInfo);
		} catch (Exception rce) {
			System.out.println("Exception->" + rce);
			e = rce;
		}
		Assert.assertNotNull(retryResult);
		Assert.assertNull(e);
		Assert.assertEquals(retryResult.isRetry(), false);
		Assert.assertEquals(retryResult.isSuccess(), true);
		Assert.assertEquals(retryResult.getMessage(), "200 OK");
	}

	public RetryTemplate retryTemplate() {
		int maxAttempt = 3;
		int retryTimeInterval = 100;
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
		retryPolicy.setMaxAttempts(maxAttempt);
		FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(retryTimeInterval);
		RetryTemplate template = new RetryTemplate();
		template.setRetryPolicy(retryPolicy);
		template.setBackOffPolicy(backOffPolicy);
		return template;
	}
}

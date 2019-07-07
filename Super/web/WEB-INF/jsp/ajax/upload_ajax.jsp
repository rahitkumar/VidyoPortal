<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="endpoint">
		<row>
			<endpointUploadID><c:out value="${endpoint.endpointUploadID}"/></endpointUploadID>
			<tenantID><c:out value="${endpoint.tenantID}"/></tenantID>
			<endpointUploadFile><c:out value="${endpoint.endpointUploadFile}"/></endpointUploadFile>
			<endpointUploadTime><c:out value="${endpoint.endpointUploadTime}"/></endpointUploadTime>
			<endpointUploadType><c:out value="${endpoint.endpointUploadType}"/></endpointUploadType>
			<endpointUploadActive><c:out value="${endpoint.endpointUploadActive}"/></endpointUploadActive>
			<endpointUploadVersion><c:out value="${endpoint.endpointUploadVersion}"/></endpointUploadVersion>
		</row>
	</c:forEach>
</dataset>
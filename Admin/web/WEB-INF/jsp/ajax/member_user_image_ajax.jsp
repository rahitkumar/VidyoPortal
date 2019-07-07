<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>
<message success="<c:out value="${model.success}"/>">
<dataset>
	<results>1</results>
	<row>
		
        <thumbnailImageB64><c:out value="${model.thumbnailImageB64}"/></thumbnailImageB64>
       </row>
</dataset>
</message>
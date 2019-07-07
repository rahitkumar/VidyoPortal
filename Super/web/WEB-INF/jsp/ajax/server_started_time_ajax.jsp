<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<serverStarted><c:out value="${model.serverStarted}"/></serverStarted>
		<serverStartedTimestamp><c:out value="${model.serverStartedTimestamp}"/></serverStartedTimestamp>
	</row>
</dataset>
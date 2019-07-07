<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<privilegedMode><c:out value="${model.privilegedMode}"/></privilegedMode>
		<showStatusNotifyTab><c:out value="${model.showStatusNotifyTab}"/></showStatusNotifyTab>
		<showExtDbTab><c:out value="${model.showExtDbTab}"/></showExtDbTab>
		<showSyslog><c:out value="${model.showSyslog}"/></showSyslog>
		<gmailPluginInstalled><c:out value="${model.gmailPluginInstalled}"/></gmailPluginInstalled>
		<showConfigBanner><c:out value="${model.showConfigBanner}"/></showConfigBanner>
		<maintMode><c:out value="${model.maintMode}"/></maintMode>
	</row>
</dataset>
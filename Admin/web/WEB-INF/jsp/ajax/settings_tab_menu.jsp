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
		<isIpcSuperManaged><c:out value="${model.isIpcSuperManaged}"/></isIpcSuperManaged>
		<vidyoWebAvailable><c:out value="${model.vidyoWebAvailable}"/></vidyoWebAvailable>
		<vidyoChatAvailable><c:out value="${model.vidyoChatAvailable}"/></vidyoChatAvailable>
		<vidyoSchdRoomAvailable><c:out value="${model.vidyoSchdRoomAvailable}"/></vidyoSchdRoomAvailable>
		<showUserAttributePage><c:out value="${model.showUserAttributePage}"/></showUserAttributePage>
		<showVidyoNeoWebRTC><c:out value="${model.showVidyoNeoWebRTC}"/></showVidyoNeoWebRTC>
		<showEpicIntegration><c:out value="${model.showEpicIntegration}"/></showEpicIntegration>
		<showTytoCareIntegration><c:out value="${model.showTytoCareIntegration}"/></showTytoCareIntegration>
	</row>
</dataset>
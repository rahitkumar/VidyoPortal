<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results>1</results>
	<row>
		<invitationEmailContent><c:out value="${model.invitationEmailContent}"/></invitationEmailContent>
		<invitationEmailContentHtml><c:out value="${model.invitationEmailContentHtml}"/></invitationEmailContentHtml>
		<voiceOnlyContent><c:out value="${model.voiceOnlyContent}"/></voiceOnlyContent>
		<webcastContent><c:out value="${model.webcastContent}"/></webcastContent>
		<invitationEmailSubject><c:out value="${model.invitationEmailSubject}"/></invitationEmailSubject>
	</row>
</dataset>
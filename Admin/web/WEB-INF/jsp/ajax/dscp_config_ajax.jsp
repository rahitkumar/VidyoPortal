<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results>1</results>
    <row>
    	<c:if test="${model.dscpValueSet != null}">
	        <signaling><c:out value="${model.dscpValueSet.signaling}"/></signaling>
	  		<mediavideo><c:out value="${model.dscpValueSet.mediaVideo}"/></mediavideo>
	  		<mediaaudio><c:out value="${model.dscpValueSet.mediaAudio}"/></mediaaudio>
	  		<mediadata><c:out value="${model.dscpValueSet.mediaData}"/></mediadata>
	  		<oam><c:out value="${model.dscpValueSet.OAM}"/></oam>
  		</c:if>
    </row>
</dataset>
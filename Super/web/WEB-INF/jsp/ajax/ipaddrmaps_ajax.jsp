<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="include_ajax.jsp" %>

<dataset>

	<results>
	<c:out value="${model.size}"/></results>
	<c:forEach items="${model.vpAddnlAddrMaps}" var="addrMap">
		<row>
            <addrMapID><c:out value="${addrMap.addrMapID}"/></addrMapID>
			<localAddr><c:out value="${addrMap.localAddr}"/></localAddr>
            <remoteAddr><c:out value="${addrMap.remoteAddr}"/></remoteAddr>
		</row>
	</c:forEach>

</dataset>
<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.count}"/></results>
	
	<c:forEach items="${model.list}" var="ctl">
		<row>
			<c:if test="${not empty  ctl.subjectCN}">			
				<certificateName><c:out value="${ctl.subjectCN}"/></certificateName>
			</c:if>
			<c:if test="${empty  ctl.subjectCN}">	
					<c:if test="${not empty  ctl.subjectOU}">		
						<certificateName>if<c:out value="${ctl.subjectOU}"/></certificateName>
					</c:if>
					<c:if test="${empty  ctl.subjectOU}">		
						<certificateName>if<c:out value="${ctl.subjectO}"/></certificateName>
					</c:if>
			</c:if>
			<serialNo><c:out value="${ctl.serial}"/></serialNo>
			<!-- for testing purpose keeping below three -->
		
			<subjectOU><c:out value="${ctl.subjectOU}"/></subjectOU>
			<subjectO><c:out value="${ctl.subjectO}"/></subjectO>
			<!-- end -->
			<notBefore><c:out value="${ctl.notBefore}"/></notBefore>
			<notAfter><c:out value="${ctl.notAfter}"/></notAfter>
	  </row>
	</c:forEach>
</dataset>
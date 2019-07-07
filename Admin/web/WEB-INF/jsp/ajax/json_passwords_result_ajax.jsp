<%@ page contentType="application/json" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

{success: true,
	valid:<c:out value="${model.success}"/>
<c:if test="${not empty model.fields}">
	<c:forEach items="${model.fields}" var="f">
		, reason:'<c:out value="${f.defaultMessage}"/>'
	</c:forEach>
</c:if>
<c:if test="${not empty model.maxSessionExp}">
	 ,maxSessionExp:'<c:out value="${model.maxSessionExp}"/>'
</c:if>
}
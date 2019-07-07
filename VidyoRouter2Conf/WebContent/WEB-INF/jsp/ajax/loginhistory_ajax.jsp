<%@ page contentType="text/xml"%>
<%@ page pageEncoding="UTF-8"%>
<%@ include file="include_ajax.jsp"%>

<dataset>

	<c:forEach items="${model.loginHistories}" var="loginHistory">
		<row>
			<loginHistoryID><c:out value="${loginHistory.ID}" /></loginHistoryID>
			<transactionName><c:out value="${loginHistory.transactionName}" /></transactionName>
			<transactionResult><c:out value="${loginHistory.transactionResult}" /></transactionResult>
			<sourceIP><c:out value="${loginHistory.sourceIP}" /></sourceIP>
			<transactionTime><c:out value="${loginHistory.transactionTime}" /></transactionTime>
		</row>
	</c:forEach>
</dataset>
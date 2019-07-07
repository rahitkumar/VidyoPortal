<%@ page session="false"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld"%>
<%@ page import="java.util.Locale"%>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
	Locale locale = LocaleContextHolder.getLocale();


%>
<c:set var="systemlang"><%=locale.toString()%></c:set>

<!DOCTYPE html>
<html>
<head>
<style>
.tabletdborder {
	border: 1px solid #ddd;
}
/* DivTable.com */
.divTable {
	display: table;
	width: 100%;
	background-color: white;
}

.divTableRow {
	display: table-row;
}

.divTableHeading {
	background-color: #EEE;
	display: table-header-group;
}

.divTableCell, .divTableHead {
	border: 3px solid #f6f6f6;
	display: table-cell;
	padding: 15px;
	width: 250px;
}

.divTableHeading {
	background-color: #EEE;
	display: table-header-group;
	font-weight: bold;
}

.divTableFoot {
	background-color: #EEE;
	display: table-footer-group;
	font-weight: bold;
}

.divTableBody {
	display: table-row-group;
}
</style>
<title></title>
<link rel="stylesheet" href="/themes/vidyo/common.css?v=2">
</head>
<body>
	<script type="text/javascript">

	</script>
	<img id="upArrow" src="/themes/vidyo/i/guest/arrow.svg"
		style="display: none; position: fixed; top: 20px; right: 50px; -webkit-transform: rotate(200deg); transform: rotate(200deg);" />
	<div
		style="width: 100%; background-color: #555555; height: 35px; padding: 20px 0px; text-align: center;">
		<c:if test="${model.logoUrl != ''}">
			<img id="customLogo" src="<c:url value="${model.logoUrl}"/>"
				border="0" />
		</c:if>
		<c:if test="${model.logoUrl == ''}">
			<div id="neologo"></div>
		</c:if>
	</div>
	<div>
		<c:out value="${rowValue}" />
	</div>
	<iframe id="iframe" style="visibility: hidden" width="0" height="0"></iframe>
	<div id="dialincontainer"
		style="text-align: center; padding: 50px; font-size: 14px; max-width: 910px; min-width: 910px; width: 910px; margin: auto;">

		<div style="text-align: center; color: #5d5d5d;">
			<div id="downloadContainer"
				style="background-color: #f6f6f6; margin: 0px 0px 0px 0px;">
				<div style="clear: both">
				<div style="font-size: 22px; padding:  25px 00px 0px 0px;"><c:out value="${model.roomName}" /></div>
					<div style="font-size: 18px; padding: 5px;">Meeting ID
						<c:out value="${model.meetingId}" /></div>
					<c:if test="${not empty model.pin}">
						<div style="font-size: 18px; padding: 5px;">PIN
							<c:out value="${model.pin}" /></div>
					</c:if>
				</div>

				<div style="font-size: 20px;padding: 32px 00px 35px 0px;"><img id="phone" width="20" height="20" src="/themes/vidyo/i/phone_icon.png" style="vertical-align: middle;"/>&nbsp;<spring:message javaScriptEscape="true" code="call.into.the.vidyomeeting.via.phone"/></div>
				</div>
				<c:if test="${empty model.list}">
					<div class="divTable" style="width: 100%;"><spring:message javaScriptEscape="true" code="there.are.no.international.dial.in.numbers.configured"/></div>
				</c:if>
				<c:if test="${not empty model.list}">
					<div class="divTable" style="width: 100%;">
						<div class="divTableBody">
							<c:forEach var="obj" items="${model.list}" varStatus="rowCounter">
								<c:if test="${rowCounter.index mod 4 eq 0}">
									<div class="divTableRow">
										<!-- flag that tells row painted -->
										<c:set var="rowStarted" value="true" />
								</c:if>
								<div class="divTableCell">
									<div>
										<img alt="" src="<c:out value="${obj.countryFlagPath}"/>" />
									</div>
									<div>
										<c:out value="${obj.countryName}" />
									</div>
									<c:forEach var="dialInNoAndLabel" items="${obj.dialinLabelToNumberMap}"
										varStatus="rowCounterSecondLoop">
										
											<div>
												<c:out value="${dialInNoAndLabel.value}" /><c:if test="${not empty dialInNoAndLabel.value}"> - </c:if><a href='tel:${dialInNoAndLabel.key},,${model.meetingId}#<c:if test="${not empty model.pin}">,,<c:out value="${model.pin}" />#</c:if>'>+<c:out value="${dialInNoAndLabel.key}" /></a>
											</div>
							
									</c:forEach>

								</div>
								<c:if test="${((rowCounter.index)+1) mod 4 eq 0}">
									<!-- flag turn off - tells row painted is completed -->
									<c:set var="rowStarted" value="false" />
						</div>
				</c:if>
				</c:forEach>
				<!-- row is painted but not finished we need to close it after the loop -->
				<c:if test="${rowStarted eq true}">
			</div>
			</c:if>
		</div>
	</div>

	</c:if>
	<div style="background-color: #f6f6f6;font-size: 14px; padding: 70px 00px 20px 0px;"></div>
	</div>
	</div>


	</div>



</body>

</html>
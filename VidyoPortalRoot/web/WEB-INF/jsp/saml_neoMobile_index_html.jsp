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
<title></title>
<link rel="stylesheet" href="/themes/vidyo/common.css?v=2">
</head>
<body onload="callNeoPortalHandler();">
	<script type="text/javascript">
		function callNeoPortalHandler() {

			var href = '<c:url value="${model.url}"></c:url>';
			var ifr = document.getElementById("iframe");
			window.setTimeout(function() {
				try {
					ifr.contentWindow.location = href;
				} catch (e) {
					//window.location = "/index.html?roomKey=<c:out value="${model.key}"/>";
				}
			}, 300);
		}

	    function doDownload() {
	        window.location = '<c:url value="${model.installer}"/>';
	    }
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
	<iframe id="iframe" style="visibility: hidden" width="0" height="0"></iframe>
 <div id="neocontainer" style="text-align: center; padding: 50px; font-size: 14px; max-width: 910px; min-width: 910px; width: 910px; margin: auto;">

    <div style="text-align: center; color: #5d5d5d;">
        <div id="downloadContainer" style="background-color: #f6f6f6; margin: 0px 0px 30px 0px;">
            <div style="text-align: center;font-weight: 800; width: 50%; float: left; padding-top: 20px">
				<div style="float: left; width: 100%; padding: 40px 0px 20px 0px;">
					<img src="/themes/vidyo/i/login/checkmark.svg">
				</div>
				<div style="font-size: 20px; font-weight: 800; padding: 20px 0px 20px 0px; color: #19A600; opacity: 100;">
					<spring:message code="login.successful" htmlEscape="true" />
				</div>
				<div style="font-size: 16px; padding: 25px; text-align: center;">
					<spring:message code="Vidyo.app" htmlEscape="true"/>
				</div>
            </div>
            <div style="text-align: center;font-weight: 800; width: 50%; float:right;">
				<div style="font-size: 22px; font-weight: 800; padding: 20px 0px 20px 0px; text-align: center;">
                	<spring:message code="don.t.have.the.app" htmlEscape="true"/>
                </div>
                <div style="border-left: 1px solid lightgrey; overflow: hidden;">
                    <div style="overflow: hidden; margin:auto;">
                        <div style="width: 350px; margin: auto;">
                            <div style="padding-top: 20px"><img src="/themes/vidyo/i/guest/download.svg" style="cursor: pointer" onclick="javascript:doDownload()"/></div>
                            <div style="font-size: 16px; text-align: center; padding-top: 20px; padding-left:50px; width: 250px"><spring:message code="after.downloading.the.installer.open.it.to.install.the.app" htmlEscape="true"/></div>
                            <div style="clear: both; padding-top: 30px;">
                                <a href="javascript:doDownload()" style="font-size: 18px; text-decoration: none; background-color: #83C36D; display: block; padding: 14px 0px 14px 0px; color: white; font-weight: normal; width: 200px; margin: auto; border-radius: 5px; "><spring:message code="download" htmlEscape="true"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div style="clear: both; padding: 20px;"></div>
        </div>

		<div id="downloadContainerLegal"
			style="padding-top: 30px; font-size: 14px; color: #888888; clear: both;">

			<spring:message
				code="saml.neo.legal.wording.by.using.our.products.you.agree.to.our"
				htmlEscape="true" />
			<a target="_blank" style="color: #6a6a6a;" href="/terms_content.html"><spring:message
					code="end.user.license.agreement" htmlEscape="true" /></a> & <a
				target="_blank" style="color: #6a6a6a;"
				href="http://www.vidyo.com/privacy-policy/"><spring:message
					code="privacy.policy" htmlEscape="true" /></a>


		</div>
    </div>
</div>




</body>

</html>
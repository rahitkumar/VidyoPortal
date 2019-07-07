<%@ page session="false" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    Locale locale = LocaleContextHolder.getLocale();
%>
<c:set var="systemlang"><%= locale.toString()%></c:set>
<!DOCTYPE html>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="/themes/vidyo/common.css?v=2">
</head>
<body>
<script type="text/javascript">
    function doDownload() {
        <c:if test="${model.browser == 'safari'}">
        if(document.getElementById("upArrow")) {
            document.getElementById("upArrow").style.display = "";
        }
        </c:if>
        <c:if test="${model.browser == 'chrome'}">
        if (document.getElementById("downArrow")) {
            document.getElementById("downArrow").style.display = "";
        }
        </c:if>
        window.location = '<c:out value="${model.installer}"/>';
    }

</script>

<c:choose>
    <c:when test="${model.installer == 'no_installer'}">
        <div id="main">
            <div id="container">
                <div class="row-header">
                    <div id="header">
                        <c:if test="${model.logoUrl != ''}">
                            <img id="customLogo" src="<c:url value="${model.logoUrl}"/>" border="0"/>
                        </c:if>
                        <c:if test="${model.logoUrl == ''}">
                            <div id="logo"></div>
                        </c:if>
                    </div>
                </div>
                <div id="row-title">
                    <div id="infoIconDownload"></div>
                </div>
                <div id="row-main">
                    <h2 style="text-align: center;"><vidyo:replaceString from="\\\\'" to="'"><spring:message code="vidyodesktoptrade.installer.is.not.available"/></vidyo:replaceString></h2>
                    <p style="font-size: 12px; color: gray; text-align: center;">
                        <vidyo:replaceString from="\\\\'" to="'"><spring:message code="vidyodesktoptrade.installer.has.not.been.set.up.correctly"/></vidyo:replaceString><br>
                        <vidyo:replaceString from="\\\\'" to="'"><spring:message code="please.contact.administrator.to.resolve.the.problem"/></vidyo:replaceString>
                    </p>
                </div>
                <div id="row-help">
                    <div id="helpText">
                        <vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="need.help"/></vidyo:replaceString> <a href="<c:url value='/contact.html'/>" style="font-size: 14px;font-weight: 300;color:#7EACDF;text-decoration: inherit;" target="_blank"><vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="contact.support"/></vidyo:replaceString></a>
                    </div>
                </div>
                <div id="copyright">&copy;2008-2019 Vidyo</div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <img id="upArrow" src="/themes/vidyo/i/guest/arrow.svg" style="display: none; position: fixed; top: 20px; right: 50px; -webkit-transform: rotate(200deg); transform: rotate(200deg);"/>
        <div style=" width: 100%;background-color: #555555;height: 35px; padding: 20px 0px; text-align: center;">
            <c:if test="${model.logoUrl != ''}">
                <img id="customLogo" src="<c:url value="${model.logoUrl}"/>" border="0"/>
            </c:if>
            <c:if test="${model.logoUrl == ''}">
                <div id="neologo"></div>
            </c:if>
        </div>
        <div id="neocontainer" style="text-align: center; padding: 20px; font-size: 14px; max-width: 910px; min-width: 910px; width: 910px; margin: auto;">
            <c:if test="${not model.forceNeoFlag}">
                <div style="text-align: center; color: #5d5d5d;">
                    <div id="downloadContainer" style="background-color: #f6f6f6; margin: 50px 0px;">
                        <div style="font-size: 24px; padding: 50px; margin: auto auto;"><spring:message code="download.the.installer" htmlEscape="true"/></div>
                        <img src="/themes/vidyo/i/guest/download.svg" style="cursor: pointer" onclick="javascript:doDownload()"/>
                        <div style="margin: auto auto; padding: 50px; width: 200px;"><spring:message code="after.downloading.the.installer.open.it.to.install.the.app" htmlEscape="true"/></div>
                        <a href="javascript:doDownload()" style="font-size: 18px; text-decoration: none; background-color: #83C36D; display: block; padding: 14px 0px 14px 0px; color: white; font-weight: normal; width: 200px; margin: auto; border-radius: 5px; "><spring:message code="download" htmlEscape="true"/></a>

                        <div style="padding: 25px;"></div>
                    </div>
                    <div id="downloadContainerLegal" style="padding-top: 30px; font-size: 14px; color: #888888; clear: both;">
                         <spring:message code="by.clicking.download.you.agree.to.our" htmlEscape="true"/> <a target="_blank" style="color: #6a6a6a;" href="/terms_content.html"><spring:message code="end.user.license.agreement" htmlEscape="true"/></a> & <a target="_blank" style="color: #6a6a6a;" href="<c:url value="${model.privacyUrl}"></c:url>"><spring:message code="privacy.policy" htmlEscape="true"/></a>.
                    </div>
                    <img id="downArrow"  src="/themes/vidyo/i/guest/arrow.svg" style="display: none; position: absolute; bottom: 0px; left: 50px;  transform: rotate(30deg);"/>
					<div id="row-help1">
				        <div id="helpText">
				           <vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="need.help"/></vidyo:replaceString> <a href="<c:url value='/contact.html'/>" style="font-size: 14px;font-weight: 300;color:#7EACDF;text-decoration: inherit;" target="_blank"><vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="contact.support"/></vidyo:replaceString></a>
				        </div>
				    </div>
				    <div id="copyright">&copy;2008-2019 Vidyo</div>
                </div>
            </c:if>
            <c:if test="${model.forceNeoFlag}">
                <jsp:include page="forceAppDownload.jsp" />
            </c:if>
        </div>
    </c:otherwise>
</c:choose>
</body>
</html>
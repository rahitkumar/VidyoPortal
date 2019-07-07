<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page session="false" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>

<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>

<%
Locale locale = LocaleContextHolder.getLocale();
%>
<c:set var="systemlang"><%= locale.toString()%></c:set>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    
    <title><spring:message javaScriptEscape="true" code="oops"/></title>
    
    <link rel="stylesheet" href="themes/vidyo/common.css">

    <link rel="shortcut icon" href="favicon.ico">
</head>


<body>
    <script type="text/javascript">
        function vdInstalled() {
        	window.location = "/closetab.html";
        }
        function vdNotInstalled() {
        	document.getElementById("main").style.display = "block";
        	document.getElementById("infoMsg").innerHTML = '<spring:message javaScriptEscape="true" code="oops"/>';
        	document.getElementById("infoMsgSub").style.display = "block";
            setTimeout(function() {
                //document.getElementById("testImage").src = "http://127.0.0.1:63457/dummy?<c:out value="${model.url}"/>&id=" + ((new Date()).getMilliseconds());
                document.getElementById("testImage").src = "http://127.0.0.1:63457/dummy?<c:out value="${model.url}" escapeXml="false" />&id=<%= System.nanoTime() %>";
            }, 10000);


        }
    </script>
    
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
            <div id="infoIconExclamation"></div>
            <div id="infoMsg"><spring:message javaScriptEscape="true" code="oops"/></div>
            <div id="infoMsgSub"><spring:message javaScriptEscape="true" code="it.looks.like.you.need.to.complete.one.ormore.of.the.following.actions"/></div>
        </div>
        
        <div id="row-main">
            <div id="runVDTable" class="samlIndexTable">
                <div id="runVDTableRow" class="samlIndexTableRow">
                    <div class="samlIndexTableRowImgCell">
                        <div id="samlIndexTableStep1"></div>
                    </div>
                    <div class="samlIndexTableRowImgCell">
                        <div id="samlIndexTableStep2"></div>
                    </div>
                    <div class="samlIndexTableRowImgCell">
                        <div id="samlIndexTableStep3"></div>
                    </div>
                </div>
                <div id="runVDTableRow" class="samlIndexTableRow">
                    <div class="samlIndexTableRowCell">
                        <c:out value="${model.step1Txt}" escapeXml="false"/>
                    </div>
                    <div class="samlIndexTableRowCell">
                        <c:out value="${model.step2Txt}" escapeXml="false"/>
                    </div>
                    <div class="samlIndexTableRowCell">
                        <c:out value="${model.step3Txt}" escapeXml="false"/>
                    </div>
                </div>
            </div>
        </div>
        
        <div id="row-help">
            <div id="helpText">
            <spring:message javaScriptEscape="true" code="need.help"/> <a href="<c:url value='/contact.html'/>" style="color:#8888CC" target="_blank"><spring:message javaScriptEscape="true" code="contact.support"/></a>
            </div>
        </div>
    </div>
    </div>
    
    <script type="text/javascript">
        document.getElementById("infoMsg").innerHTML = '<spring:message javaScriptEscape="true" code="connecting.please.wait"/>';
        document.getElementById("main").style.display = "none";
        document.getElementById("infoMsgSub").style.display = "none";
    </script>
    
    <img id="testImage"
         style="visibility: hidden"
         src="http://127.0.0.1:63457/dummy?<c:out value="${model.url}"/>&id=<%= System.nanoTime() %>"
         onload="javascript: vdInstalled();"
         onerror="javascript: vdNotInstalled();"/>
</body>

</html>
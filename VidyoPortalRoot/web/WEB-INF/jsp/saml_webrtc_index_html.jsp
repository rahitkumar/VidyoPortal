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
<body onload="document.forms[0].submit()">

    <form action='<c:url value="${model.webRTCUrl}"></c:url>' method="post">
            <div>
                <c:choose>
                    <c:when test="${null != model.directDial}">
                        <input type="hidden" name="directDial" value="<c:url value="${model.directDial}"></c:url>"/>
                        <input type="hidden" name="ddDisplayName" value="<c:url value="${model.ddDisplayName}"></c:url>"/>
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="roomKey" value="<c:url value="${model.roomKey}"></c:url>"/>
                    </c:otherwise>
                </c:choose>

                <input type="hidden" name="key" value="<c:url value="${model.key}"></c:url>"/>   
                                
            </div>
            <noscript>
                <div>
                    <input type="submit" value="Continue"/>
                </div>
            </noscript>
    </form>
      
    <img id="upArrow" src="/themes/vidyo/i/guest/arrow.svg" style="display: none; position: fixed; top: 20px; right: 50px; -webkit-transform: rotate(200deg); transform: rotate(200deg);"/>
<div style=" width: 100%;background-color: #555555;height: 35px; padding: 20px 0px; text-align: center;">
    <c:if test="${model.logoUrl != ''}">
        <img id="customLogo" src="<c:url value="${model.logoUrl}"/>" border="0"/>
    </c:if>
    <c:if test="${model.logoUrl == ''}">
        <div id="neologo"></div>
    </c:if>
</div>
<iframe id="iframe" style="visibility: hidden" width="0" height="0"></iframe>
<div id="neocontainer" style="text-align: center; padding: 50px; font-size: 14px; max-width: 910px; min-width: 910px; width: 910px; margin: auto;">
    <div style="text-align: center; color: #5d5d5d;">
    <div id="successContainer" style="background-color: #f6f6f6; margin: 0px 0px 30px 0px;">
 <div style="font-size: 24px; padding: 0px;">
                     <div style="float: center; width: 100%; padding:64px 0px 0px 0px;"><img src="/themes/vidyo/i/login/checkmark.svg"></div>
                <div style="font-size: 24px; padding: 50px;color:#19A600; opacity:100;">Please wait..connecting to webrtc</div>
              
   				<div style="padding: 57px;"></div>
            </div>

        
          </div>
          </div>
          
          
              <div id="downloadContainerLegal" style="padding-top: 30px; font-size: 14px; color: #888888; clear: both;">
         
           <spring:message code="saml.neo.legal.wording.by.using.our.products.you.agree.to.our" htmlEscape="true"/> <a target="_blank" style="color: #6a6a6a;" href="/terms_content.html"><spring:message code="end.user.license.agreement" htmlEscape="true"/></a> &  <a target="_blank" style="color: #6a6a6a;" href="<c:url value="${model.privacyUrl}"></c:url>"><spring:message code="privacy.policy" htmlEscape="true"/></a>
        

        </div>
          </div>
   
    
    

</body>

</html>
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
<body onload="preferenceCheck();">
    <c:set var = "desktopProtocol" value = "${model.appProtocol}"/>
    
<script type="text/javascript">

    function joinViaApp() {
        if (document.getElementById("choiceContainer")) {
            document.getElementById("choiceContainer").style.display = "none";
        }
        if(document.getElementById("choiceContainerLegal")) {
            document.getElementById("choiceContainerLegal").style.display = "none";
        }
        if(document.getElementById("downloadContainer")) {
            document.getElementById("downloadContainer").style.display = "";
        }
        if(document.getElementById("downloadContainerLegal")) {
            document.getElementById("downloadContainerLegal").style.display = "";
        }
        testNeo();
        setCookie("joinViaApp", "1", 365);
    }

    function testNeo() {
        <%--VPTL-7796 - Embedding the portal features inside the join link.--%>
        var href = '<spring:url value="${desktopProtocol}://join"><spring:param name="portal" value="${model.host}" /><spring:param name="f" value="${model.portalFeatures}" /><spring:param name="roomKey" value="${model.key}" /><spring:param name="pin" value="${model.pinned}" /><spring:param name="extData" value="${model.extData}" /><spring:param name="extDataType" value="${model.extDataType}"/><spring:param name="dispName" value="${model.dispName}"/><spring:param name="directDial" value="${model.directDial}"/><spring:param name="ddDisplayName" value="${model.ddDisplayName}"/></spring:url>';
        //var href = '<c:url value="${desktopProtocol}://join"><c:param name="portal" value="${model.host}"/>"/><c:param name="f" value="${model.portalFeatures}"/><c:param name="roomKey" value="${model.key}"/><c:param name="pin" value="${model.pinned}"/><c:param name="extData" value="${model.extData}"/><c:param name="extDataType" value="${model.extDataType}"/><c:param name="dispName" value="${model.firstName}${' '}${model.lastName}"/><c:param name="dispName" value="${model.dispName}"/>"</c:url>';
        var ifr = document.getElementById("iframe");
        window.setTimeout(function () {
            try {
                ifr.contentWindow.location = href;
            } catch (e) {
                //TODO: Need to look into the below logic as the comment will not work and error should be handled gracefully.
                //window.location = "/index.html?roomKey=<c:out value="${model.key}"/>";
            }
        }, 300);
    }

    function doDownload() {
        <c:if test="${model.browser == 'safari'}">
        if(document.getElementById("upArrow")) {
            document.getElementById("upArrow").style.display = "";
        }
        </c:if>
        <c:if test="${model.browser == 'chrome'}">
        if(document.getElementById("downArrow")) {
            document.getElementById("downArrow").style.display = "";
        }
        </c:if>
        window.location = '<c:out value="${model.installer}"/>';
    }

    function setCookie(cname, cvalue, exdays) {
        var d = new Date();
        d.setTime(d.getTime() + (exdays*24*60*60*1000));
        var expires = "expires="+d.toUTCString();
        document.cookie = cname + "=" + cvalue + "; " + expires;
    }

    function getCookie(cname) {
        var name = cname + "=";
        var ca = document.cookie.split(';');
        for(var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) == ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) == 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    function uncookie() {
        setCookie("joinViaApp", "0", 365);
    }

    function preferenceCheck() {
        if (getCookie("joinViaApp") == 1) {
            joinViaApp();
        } else {
            <c:choose>
                <c:when test="${model.neoWebRTCAvailable || (model.vidyoWebEnabled && model.forceNeoVidyoWeb)}">
                  if(document.getElementById("choiceContainer")) {
                      document.getElementById("choiceContainer").style.display = "";
                  }
                  if(document.getElementById("choiceContainerLegal")) {
                      document.getElementById("choiceContainerLegal").style.display = "";
                  }
                </c:when>
                <c:otherwise>
                 if(document.getElementById("downloadContainer")) {
                    document.getElementById("downloadContainer").style.display = "";
                 }
                 if(document.getElementById("downloadContainerLegal")) {
                     document.getElementById("downloadContainerLegal").style.display = "";
                 }
                  joinViaApp();
                </c:otherwise>
            </c:choose>
        }
    }

</script>
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
    <c:if test="${not model.forceNeoFlag}">
        <div style="text-align: center; color: #5d5d5d;">
            <div id="downloadContainer" style="display: none; background-color: #f6f6f6; margin: 0px 0px 30px 0px;">
                <div style="font-size: 24px; padding: 50px;"><spring:message code="attempting.to.join.the.call" htmlEscape="true"/></div>

                <div style="text-align: center;font-weight: 800; width: 50%; float: left;">
                    <spring:message code="using.the.app.for.the.first.time" htmlEscape="true"/>
                    <div style="overflow: hidden;">
                        <div style="width: 350px; margin: auto;">
                            <div style="overflow: hidden;  margin: 50px; margin-left: auto; margin-right: auto; ">
                                <div style="float: left; width: 30%;"><img src="/themes/vidyo/i/guest/modal.svg"/></div>
                                <div style="float: right; width: 65%; font-weight: normal; font-size: 14px; text-align: left;"><spring:message code="your.browser.might.ask.for.permission.to.launch.our.app" htmlEscape="true"/><br/><br/><spring:message code="please.say.yes" htmlEscape="true"/></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="text-align: center;font-weight: 800; width: 50%; float:right;">
                    <spring:message code="don.t.have.the.app" htmlEscape="true"/>
                    <div style="border-left: 1px solid lightgrey; overflow: hidden;">
                        <div style="overflow: hidden;  margin: 50px 50px 20px 50px;margin-left: auto; margin-right: auto;">
                            <div style="width: 350px; margin: auto;">
                                <div style="float: left; width: 30%;"><img src="/themes/vidyo/i/guest/download.svg" style="cursor: pointer" onclick="javascript:doDownload()"/></div>
                                <div style="float: right; width: 65%; font-weight: normal; font-size: 14px; text-align: left;"><spring:message code="you.can.download.and.install.it.now.when.you.open.it.you.ll.automatically.join.the.call" htmlEscape="true"/></div>
                                <div style="clear: both; padding-top: 30px;">
                                    <a href="javascript:doDownload()" style="font-size: 18px; text-decoration: none; background-color: #83C36D; display: block; padding: 14px 0px 14px 0px; color: white; font-weight: normal; width: 200px; margin: auto; border-radius: 5px; "><spring:message code="download" htmlEscape="true"/></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div style="clear: both; padding: 20px 0px;"></div>
                <c:choose>
                    <c:when test="${model.neoWebRTCAvailable}">
                        <div style="padding: 0px 0px 20px 0px; font-weight: 400; font-size: 14px; color: #5d5d5d; ">
                            <spring:message code="having.trouble.with.the.desktop.app.you.can" htmlEscape="true"/> <a onclick="javascript:uncookie();" href="<c:out value="${model.neoWebRTCUrl}"/>" style="color: #5d5d5d;" ><spring:message code="join.via.your.browser.instead" htmlEscape="true"/></a>.
                        </div>
                    </c:when>
                    <c:when test="${model.vidyoWebEnabled && model.forceNeoVidyoWeb}">
                    <div style="padding: 0px 0px 20px 0px; font-weight: 400; sfont-size: 14px; color: #5d5d5d; ">
                        <spring:message code="having.trouble.with.the.desktop.app.you.can" htmlEscape="true"/> <a  onclick="javascript:uncookie();" href="/web/index.html?loginType=guest&portalUri=<c:out value="${model.host}"/>&roomKey=<c:out value="${model.key}"/>&zincUrl=<c:out value="${model.zincUrl}"/>&id=<%= System.nanoTime() %>" style="color: #5d5d5d;" ><spring:message code="join.via.your.browser.instead" htmlEscape="true"/></a>.
                    </div>
                </c:when>
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="choiceContainer" style="display: none; background-color: #f6f6f6; margin: 0px 0px 30px 0px;">
                <div style="font-size: 24px; padding: 50px;"><spring:message code="how.would.you.prefer.to.join.the.call" htmlEscape="true"/></div>

                <div style="text-align: center;font-weight: 300; width: 50%; float: left;">

                    <div style="overflow: hidden;">
                        <div style="width: 350px; margin: auto;">
                            <img src="/themes/vidyo/i/guest/desktop.svg" style="cursor: pointer" onclick="javascript:joinViaApp()"/>
                            <div style="overflow: hidden;  margin-bottom: 20px; margin-left: auto; margin-right: auto; ">


                                <div style="text-align: left; padding: 20px 10px;">
                                <div style="padding: 5px;"><img src="/themes/vidyo/i/guest/checkmark.svg" style="vertical-align: middle;"/>&nbsp;&nbsp;<spring:message code="join.meetings.faster.with.fewer.clicks" htmlEscape="true"/></div>
                                    <div style="padding: 5px;"><img src="/themes/vidyo/i/guest/checkmark.svg" style="vertical-align: middle;"/>&nbsp;&nbsp;<spring:message code="share.seamlessly.without.extensions" htmlEscape="true"/></div>
                                        <div style="padding: 5px;"><img src="/themes/vidyo/i/guest/checkmark.svg" style="vertical-align: middle;"/>&nbsp;&nbsp;<spring:message code="see.and.hear.participants.in.the.best.quality" htmlEscape="true"/></div>
                                    <div style="padding: 10px;"></div>
                                    <a href="javascript:joinViaApp()" style="font-size: 18px; text-align: center; text-decoration: none; background-color: #83C36D; display: block; padding: 14px 0px 14px 0px; color: white; font-weight: normal; width: 200px; margin: auto; border-radius: 5px; "><spring:message code="join.via.the.app" htmlEscape="true"/></a>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>
                <div style="text-align: center;font-weight: 300; width: 50%; float:right;">
                    <div style="border-left: 1px solid lightgrey; overflow: hidden;">
                        <div style="width: 350px; margin: auto;">
                            <img src="/themes/vidyo/i/guest/web.svg" style="cursor: pointer" onclick="javascript:location.href=document.getElementById('browserLink').href"/>
                            <div style="overflow: hidden;  margin-bottom: 20px; margin-left: auto; margin-right: auto; ">


                                <div style="text-align: left; padding: 20px 0px;">
                                    <div style="padding-left: 60px;">
                                    <div style="padding: 5px;"><img src="/themes/vidyo/i/guest/checkmark.svg" style="vertical-align: middle;"/>&nbsp;&nbsp;<spring:message code="join.the.call.immediately" htmlEscape="true"/></div>
                                    <div style="padding: 5px;"><img src="/themes/vidyo/i/guest/checkmark.svg" style="vertical-align: middle;"/>&nbsp;&nbsp;<spring:message code="no.downloads.or.installations" htmlEscape="true"/></div>
                                    <div style="padding: 5px;">&nbsp;</div>
                                    </div>
                                    <div style="padding: 10px;"></div>
                                    <c:choose>
                                        <c:when test="${model.neoWebRTCAvailable}">
                                            <a id="browserLink" href="<c:out value="${model.neoWebRTCUrl}"/>" style="font-size: 18px; text-align: center; text-decoration: none; background-color: #83C36D; display: block; padding: 14px 0px 14px 0px; color: white; font-weight: normal; width: 200px; margin: auto; border-radius: 5px; "><spring:message code="join.via.the.browser" htmlEscape="true"/></a>
                                        </c:when>
                                        <c:when test="${model.vidyoWebEnabled && model.forceNeoVidyoWeb}">
                                            <a id="browserLink" href="/web/index.html?loginType=guest&portalUri=<c:out value="${model.host}"/>&roomKey=<c:out value="${model.key}"/>&zincUrl=<c:out value="${model.zincUrl}"/>&id=<%= System.nanoTime() %>" style="font-size: 18px; text-align: center; text-decoration: none; background-color: #83C36D; display: block; padding: 14px 0px 14px 0px; color: white; font-weight: normal; width: 200px; margin: auto; border-radius: 5px; "><spring:message code="join.via.the.browser" htmlEscape="true"/></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a id="browserLink" href="#undefined" style="font-size: 18px; text-align: center; text-decoration: none; background-color: #83C36D; display: block; padding: 14px 0px 14px 0px; color: white; font-weight: normal; width: 200px; margin: auto; border-radius: 5px; "><spring:message code="join.via.the.browser" htmlEscape="true"/></a>
                                        </c:otherwise>
                                    </c:choose>

                                </div>


                            </div>
                        </div>
                    </div>
                </div>
                <div style="clear: both; padding: 20px 0px;"></div>
            </div>
            <div id="downloadContainerLegal" style="display: none; padding-top: 30px; font-size: 14px; color: #888888; clear: both;">
                 <spring:message code="by.clicking.download.you.agree.to.our" htmlEscape="true"/> <a target="_blank" style="color: #6a6a6a;" href="/terms_content.html"><spring:message code="end.user.license.agreement" htmlEscape="true"/></a> & <a target="_blank" style="color: #6a6a6a;" href="<c:url value="${model.privacyUrl}"></c:url>"><spring:message code="privacy.policy" htmlEscape="true"/></a>.
            </div>
            <div id="choiceContainerLegal" style="display: none; padding-top: 30px; font-size: 14px; color: #888888; clear: both;">
                 <spring:message code="by.downloading.or.using.our.products.you.agree.to.our" htmlEscape="true"/> <a target="_blank" style="color: #6a6a6a;" href="<c:url value="${model.privacyUrl}"></c:url>"><spring:message code="privacy.policy" htmlEscape="true"/></a>.
            </div>
            <img id="downArrow"  src="/themes/vidyo/i/guest/arrow.svg" style="display: none; position: absolute; bottom: 0px; left: 50px;  transform: rotate(30deg);"/>
        </div>
    </c:if>
    <c:if test="${model.forceNeoFlag}">
        <jsp:include page="forceAppDownload.jsp" />
    </c:if>
    <div id="row-help1">
        <div id="helpText">
        <vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="need.help"/></vidyo:replaceString> <a href="<c:url value='/contact.html'/>" style="font-size: 14px;font-weight: 300;color:#7EACDF;text-decoration: inherit;" target="_blank"><vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="contact.support"/></vidyo:replaceString></a>
        </div>
    </div>
    <div id="copyright">&copy;2008-2019 Vidyo</div>    
</div>
</body>

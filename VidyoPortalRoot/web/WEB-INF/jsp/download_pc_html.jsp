<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page session="false" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>

<%
    Locale locale = LocaleContextHolder.getLocale();
    String country = locale.getCountry();
    String html_lang = locale.getLanguage();
    html_lang += country.equalsIgnoreCase("") ? "" : "-"+locale.getCountry();
%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%= html_lang%>" lang="<%= html_lang%>">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title><vidyo:replaceString from="\\\\'" to="'"><spring:message code="download.vidyodesktop"/></vidyo:replaceString></title>
    <link rel="stylesheet" href="themes/vidyo/download.css">

    <link rel="shortcut icon" href="favicon.ico">

    <script type="text/javascript">
       <c:if test="${model.key != ''}">
       function vdInstalled() {
           setTimeout(function() {
                window.location.href = "<c:url value="${model.roomURLFormated}"/>&t=" + ((new Date()).getMilliseconds());
           }, 3000);

       }
       function vdNotInstalled() {
           setTimeout(function() {
               document.getElementById("testImage").src = "http://127.0.0.1:63457/dummy?url=<c:out value="${model.host}"/>/blank.png?id=" + ((new Date()).getMilliseconds());
           }, 5000);
       }
       </c:if>
    </script>

</head>

<body>

<c:if test="${model.installer == 'no_installer'}">
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
</c:if>

<c:if test="${model.installer != 'no_installer'}">

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
            <div id="infoDownload"><spring:message javaScriptEscape="true" code="click.button.then.follow.the.directions.below"/></div>
            <div id="downloadBtn">
                    <div class='download-item'>
                        <a id="downloadButton" href="#" onclick="window.location.href='<c:out value="${model.installer}"/>'" style="color: inherit; text-decoration: inherit;"><spring:message javaScriptEscape="true" code="download.vidyodesktop"/></a>
                    </div>
                </div>
        </div>

        <div id="row-main">
            <div id="runVDTable" class="downloadTable">
                <div id="runVDTableRow" class="downloadTableRow">
                    <div class="downloadTableRowImgCell">
                        <div id="downloadPcTableStep1"></div>
                    </div>
                    <div class="downloadTableRowImgCell">
                        <div id="downloadPcTableStep2"></div>
                    </div>
                    <div class="downloadTableRowImgCell">
                        <div id="downloadPcTableStep3"></div>
                    </div>
                </div>
                <div id="runVDTableRow" class="downloadTableRow">
                    <div class="downloadTableRowCell1">
                        1. <spring:message htmlEscape="true" code="run.the.downloaded.file"/>
                    </div>
                    <div class="downloadTableRowCell1">
                        2. <vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="start.installing"/></vidyo:replaceString>
                    </div>
                    <div class="downloadTableRowCell1">
                        3. <spring:message htmlEscape="true" code="ready.to.go"/>
                    </div>
                </div>
                <div id="runVDTableRow" class="downloadTableRow">
                    <div class="downloadTableRowCell2">
                        <spring:message htmlEscape="true" code="run.the.exe.file.from.the.browser.s.download.prompt"/>
                    </div>
                    <div class="downloadTableRowCell2">
                        <spring:message htmlEscape="true" code="select.next.on.the.vidyodesktop.installer"/>
                    </div>
                    <div class="downloadTableRowCell2">
                        <spring:message htmlEscape="true" code="log.in.or.click.on.a.vidyo.link.to.start.a.conference.windows"/>
                    </div>
                </div>
            </div>
        </div>

        <div id="row-help">
            <div id="helpText">
            <vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="need.help"/></vidyo:replaceString> <a href="<c:url value='/contact.html'/>" style="font-size: 14px;font-weight: 300;color:#7EACDF;text-decoration: inherit;" target="_blank"><vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="contact.support"/></vidyo:replaceString></a>
            </div>
        </div>

        <div id="copyright">&copy;2008-2019 Vidyo</div>
    </div>
    </div>

    <div class="hidden">
        <script type="text/javascript">
            var images = new Array()
            function preload() {
                for (i = 0; i < preload.arguments.length; i++) {
                    images[i] = new Image();
                    images[i].src = preload.arguments[i];
                }
            }
            if(window.devicePixelRatio >= 2) {
                preload(
                        "themes/vidyo/i/download/btn_green_tap@2x.png"
                );
            } else {
                preload(
                        "themes/vidyo/i/download/btn_green_tap.png"
                );
            }
        </script>
    </div>
</c:if>
<c:if test="${model.key != ''}">
    <img id="testImage"
         style="visibility: hidden"
         src="http://127.0.0.1:63457/dummy?url=<c:out value="${model.host}"/>/blank.png?id=<%= System.nanoTime() %>"
         onload="javascript: vdInstalled();"
         onerror="javascript: vdNotInstalled();"/>
</c:if>
</body>
</html>
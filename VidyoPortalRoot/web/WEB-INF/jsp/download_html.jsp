<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
        %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
        %><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"
        %><%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld"
        %><!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title><vidyo:replaceString from="\\\\'" to="'"><spring:message code="title"/></vidyo:replaceString></title>
    <style>
        body {
            font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, tahoma, verdana, sans-serif;
            background: url('<c:url value="/themes/vidyo/i/login/bg.png"/>');
        }
        .content {
            margin: 50px auto;
            text-align: center;
        }
        .filler {
            padding: 100px;
        }
        .buttonContainer {
            width: 100%;
            padding-top: 25px;
        }
        .button {
            margin: 0px auto;
            font-weight: lighter;
            color: white;
            border: 0px;
            border-style: none;
            font-size: 1.5em;
            background-image: url(/themes/vidyo/i/login/grn_btn.png);
            height: 62px;
            width: 473px;
            cursor: pointer;
        }
    </style>

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

<div class="content">
    <c:if test="${model.logoUrl != ''}">
        <img src="<c:url value="${model.logoUrl}"/>" border="0"/>
    </c:if>
    <c:if test="${model.logoUrl == ''}">
        <div class="filler"></div>
    </c:if>
    <c:if test="${model.installer == 'no_installer'}">
        <h2><vidyo:replaceString from="\\\\'" to="'"><spring:message code="vidyodesktoptrade.installer.is.not.available"/></vidyo:replaceString></h2>
        <p style="font-size: 12px; color: gray; text-align: center;">
            <vidyo:replaceString from="\\\\'" to="'"><spring:message code="vidyodesktoptrade.installer.has.not.been.set.up.correctly"/></vidyo:replaceString><br>
            <vidyo:replaceString from="\\\\'" to="'"><spring:message code="please.contact.administrator.to.resolve.the.problem"/></vidyo:replaceString>
        <br/><br/><br/>
        <vidyo:replaceString from="\\\\'" to="'"><spring:message code="click.back.button"/></vidyo:replaceString>
        </p>
    </c:if>
    <c:if test="${model.installer != 'no_installer'}">
        <div class="buttonContainer">
            <div class='button' onclick="window.location.href='<c:out value="${model.installer}"/>'">
                <div style="padding-top: 19px;"><vidyo:replaceString from="\\\\'" to="'"><spring:message code="download.vidyodesktop" /></div></vidyo:replaceString>
            </div>
        </div>
        <br/><br/><br/>
        <vidyo:replaceString from="\\\\'" to="'"><spring:message code="when.the.download.completes.run.the.installer.and.close.this.window" /></vidyo:replaceString>
    </c:if>
</div>

<c:if test="${model.key != ''}">
<img id="testImage"
     style="visibility: hidden"
     src="http://127.0.0.1:63457/dummy?url=<c:out value="${model.host}"/>/blank.png?id=<%= System.nanoTime() %>"
     onload="javascript: vdInstalled();"
     onerror="javascript: vdNotInstalled();"/>
</c:if>
</body>
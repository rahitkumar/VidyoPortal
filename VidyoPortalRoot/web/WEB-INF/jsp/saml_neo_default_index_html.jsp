
<%@ page session="false" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="<c:out value="${model.htmlLang}"/>">
<head>
    <title></title>
    <link rel="stylesheet" href="/themes/vidyo/common.css?v=2">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="apple-itunes-app" content="app-id=1103823278, app-argument=<c:url value="${model.joinLink}"/>">
    <style type="text/css">
        body {
            background-color: #f8f8f8;
            font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
            font-weight: 300;
            font-size: 1.3em;
            color: #535353;
            text-align: center;
        }
        #neologo {
            background-image: url('/themes/vidyo/i/controlmeeting/logo_vidyo.png');
            background-image: -webkit-image-set(
                    url('/themes/vidyo/i/controlmeeting/logo_vidyo.png') 1x,
                    url('/themes/vidyo/i/controlmeeting/logo_vidyo@2x.png') 2x);
            background-position: center;
            background-repeat: no-repeat;
            height: 100%;
        }
        #customLogo {
            height: 39px;
        }
        #actions {
            margin: 20% auto;
        }
        #androidDownloadButton {
            background-image: url('/themes/vidyo/i/guest/android_download.png');
            background-position: center;
            background-repeat: no-repeat;
            width: 129px;
            height: 45px;
            margin: auto auto;
            margin-top: 10%;
        }
        #iosDownloadButton {
            background-image: url('/themes/vidyo/i/guest/ios_download.svg');
            background-position: center;
            background-repeat: no-repeat;
            width: 129px;
            height: 45px;
            margin: auto auto;
            margin-top: 10%;
        }
    </style>
</head>
<body onload="callNeoPortalHandler()">
<div style="width: 100%;background-color: #555555;height: 3em; padding: 2% 0%; text-align: center;">
    <c:if test="${not empty model.logoUrl}">
        <img id="customLogo" src="<c:url value="${model.logoUrl}"/>" border="0"/>
    </c:if>
    <c:if test="${empty model.logoUrl}">
      <div id="neologo"></div>
    </c:if>
</div>
<div id="actions">
        <div id="joinConference" class="action" onclick="javascript:join();">
            <c:if test="${fn:startsWith(model.joinLink, 'intent://')}">
            	<spring:message htmlEscape="true" code="attempting.to.open.neo"/><br /><br />
            	<div style="width:90%; margin-left:auto;margin-right:auto;">
            	    <span style="font-size:0.7em;"><spring:message htmlEscape="false" code="tap.to.open.button.message"/></span>
            	</div>
            </c:if>
            <c:if test="${not fn:startsWith(model.joinLink, 'intent://')}">
            	<div style="width:90%; margin-left:auto;margin-right:auto;">
            	    <span style="font-size:0.7em;"><spring:message htmlEscape="false" code="tap.to.open.the.app.button.message"/></span>
            	</div>
            </c:if>
            <div style="text-align: left; padding: 20px 10px;margin-top:10%;">
                <a href="#" style="font-size: 1em; text-align: center; text-decoration: none; background-color: #83C36D; display: block; padding: 4% 8%; color: white; font-weight: normal; width:75%; max-width: 300px; margin: auto; border-radius: 5px; "><spring:message htmlEscape="true" code="open.button.label"/></a>
            </div>
        </div>
        <div style="margin-top:10%;">
            <span style="font-size:0.8em;"><spring:message htmlEscape="true" code="don.t.have.the.app"/></span><br />
            <c:if test="${fn:startsWith(model.joinLink, 'intent://')}">
                <span style="font-size:0.6em;"><spring:message code="download.app.android"/></span><br />
            </c:if>
            <c:if test="${not fn:startsWith(model.joinLink, 'intent://')}">
                <span style="font-size:0.6em;"><spring:message code="download.app.ios"/></span><br />
            </c:if>
        </div>
        <c:if test="${fn:startsWith(model.joinLink, 'intent://')}">
            <div id="androidDownloadButton" onclick="javascript:download();"></div>
        </c:if>
        <c:if test="${not fn:startsWith(model.joinLink, 'intent://')}">
            <div id="iosDownloadButton" onclick="javascript:download();"></div>
        </c:if>
</div>
<script type="text/javascript">
    //VPTL-7673 - changing to vidyo instead of vidyoMobile.
    var joinLink = '<c:url value="${model.joinLink}"/>';
    var downloadLink = '<c:url value="${model.neoMobileDownloadLink}"/>';

    function join() {
        window.location = joinLink;
    }
   function callNeoPortalHandler() {
       if (joinLink.startsWith('intent://')) {
           window.location=joinLink;
       }
   }
    function download() {
        window.location = downloadLink;
    }
</script>
</body>
</html>
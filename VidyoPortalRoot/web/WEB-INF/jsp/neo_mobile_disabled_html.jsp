<%@ page session="false" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html lang="<c:out value="${model.htmlLang}"/>">
<head>
    <title></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
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
        #divider {
            background-image: url('/themes/vidyo/i/guest/phone/divider.png');
            background-image: -webkit-image-set(
                    url('/themes/vidyo/i/guest/phone/divider.png') 1x,
                    url('/themes/vidyo/i/guest/phone/divider@2x.png') 2x);
            background-position: center;
            background-repeat: no-repeat;
            width: 251px;
            height: 1px;
            margin: auto auto;
        }
        #customLogo {
            height: 39px;
        }
        #actions {
            margin: 20% auto;
        }
        .actionText {
            padding-top: 10px;
        }
        #joinConferenceButton {
            background-image: url('/themes/vidyo/i/guest/phone/join.png');
            background-image: -webkit-image-set(
                    url('/themes/vidyo/i/guest/phone/join.png') 1x,
                    url('/themes/vidyo/i/guest/phone/join@2x.png') 2x);
            background-position: center;
            background-repeat: no-repeat;
            width: 62px;
            height: 62px;
            margin: auto auto;
        }
        #roomInfo {
            font-size: 15px;
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
<body>
    <div style="width: 100%;background-color: #555555;height: 3em; padding: 2% 0%; text-align: center;">
        <c:if test="${not empty model.logoUrl}">
            <img id="customLogo" src="<c:url value="${model.logoUrl}"/>" border="0"/>
        </c:if>
        <c:if test="${empty model.logoUrl}">
            <div id="neologo"></div>
        </c:if>
    </div>
    <div id="actions">
            <span style="font-size: 0.9em;"><spring:message code="mobile.not.supported"/></span><br />
    </div>
</body>
</html>
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
                font-size: 17px;
                color: #535353;
                text-align: center;
            }
            #vidyoLogo {
                background-image: url('/themes/vidyo/i/guest/phone/logo.png');
                background-image: -webkit-image-set(
                    url('/themes/vidyo/i/guest/phone/logo.png') 1x,
                    url('/themes/vidyo/i/guest/phone/logo@2x.png') 2x);
                background-position: center;
                background-repeat: no-repeat;
                width: 221px;
                height: 77px;
                margin: 30px auto;
            }
            #customLogo{
                background-position:center;
                background-repeat: no-repeat;
                width:221px;
                height:77px;
                margin:30px auto;
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
            #actions {
                margin: 30px auto;
            }
            .action {

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
            }
            #iosDownloadButton {
                background-image: url('/themes/vidyo/i/guest/ios_download.svg');
                background-position: center;
                background-repeat: no-repeat;
                width: 129px;
                height: 45px;
                margin: auto auto;
            }
        </style>
    </head>
    <body>
        <c:if test="${not empty model.logoUrl}">
          <img id="customLogo" src="<c:url value="${model.logoUrl}"/>" border="0"/>
        </c:if>
        <c:if test="${empty model.logoUrl}">
          <div id="vidyoLogo"></div>
        </c:if>
        <div id="divider"></div>
        <div id="actions">
            <div id="joinConference" class="action" onclick="javascript:join();">
                <spring:message htmlEscape="true" code="have.vidyomobile.installed"/><br /><br />
                <div id="joinConferenceButton"></div>
                <div class="actionText"><spring:message htmlEscape="true" code="join.conference"/></div>
            </div>
            <div style="padding: 10px;">&nbsp;</div>
                <spring:message htmlEscape="true" code="don.t.have.vidyomobile"/><br />
                <c:if test="${model.isAndroid}">
                    <div id="androidDownloadButton" onclick="javascript:download();"></div>
                </c:if>
                <c:if test="${!model.isAndroid}">
                    <div id="iosDownloadButton" onclick="javascript:download();"></div>
                </c:if>
                <c:if test="${model.isAndroid}">
                <div class="actions2">
                    <c:if test="${model.zincLink != ''}">
                    <div style="margin-top: 50px;" onclick="javascript:redirectToZinc()"/>
                        <spring:message htmlEscape="true" code="can.t.download.vidyomobile.click"/> <a href="javascript:redirectToZinc()"><spring:message htmlEscape="true" code="here"/></a>
                    </div>
                    </c:if>
                </div>
                </c:if>
        </div>
        <script type="text/javascript">

            var joinLink = 'vidyomobile://<c:out value="${model.hostUrl}"/>';

            <c:if test="${model.isAndroid}">
            var downloadLink = '<c:out value="${model.vidyoMobileDownloadLink}"/>';
            
                <c:if test="${model.zincLink != ''}">
                    var zincLink = '<c:out value="${model.zincLink}" escapeXml="false"/>';
                    function redirectToZinc() {
                    	window.location = zincLink;
                    	return false;
                    }
                </c:if>
            </c:if>

            <c:if test="${!model.isAndroid}">
            var downloadLink = '<c:out value="${model.vidyoMobileDownloadLink}"/>';
            </c:if>


            function join() {
                window.location = joinLink;
            }

            function download() {
                window.location = downloadLink;
            }

        </script>
    </body>
</html>
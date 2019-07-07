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
            background-image: url('/themes/vidyo/i/guest/tablet/logo.png');
            background-image: -webkit-image-set(
                url('/themes/vidyo/i/guest/tablet/logo.png') 1x,
                url('/themes/vidyo/i/guest/tablet/logo@2x.png') 2x);
            background-position: center;
            background-repeat: no-repeat;
            width: 366px;
            height: 128px;
            margin: 30px auto;
        }
        #customLogo{
            background-position:center;
            background-repeat: no-repeat;
            width:366px;
            height:128px;
            margin:30px auto;
        }
        #divider {
            background-image: url('/themes/vidyo/i/guest/tablet/divider.png');
            background-image: -webkit-image-set(
                url('/themes/vidyo/i/guest/tablet/divider.png') 1x,
                url('/themes/vidyo/i/guest/tablet/divider@2x.png') 2x);
            background-position: center;
            background-repeat: no-repeat;
            width: 600px;
            height: 2px;
            margin: auto auto;
        }
        .instruction {
            width: 175px;
            height: 50px;
        }
        .actions {
            width: 800px;
            margin: 30px auto;
        }
        .actions2 {
            width: 800px;
            margin: 10px auto;
        }
        .action {
            width: 175px;
            display: inline-block;
            margin: 20px 30px;
        }
        .actionText {
            padding-top: 20px;
        }
        #joinConferenceButton {
            background-image: url('/themes/vidyo/i/guest/tablet/join.png');
            background-image: -webkit-image-set(
                url('/themes/vidyo/i/guest/tablet/join.png') 1x,
                url('/themes/vidyo/i/guest/tablet/join@2x.png') 2x);
            background-position: center;
            background-repeat: no-repeat;
            width: 124px;
            height: 124px;
            margin: auto auto;
        }
        #shareAnnotateButton {
            background-image: url('/themes/vidyo/i/guest/tablet/share.png');
            background-image: -webkit-image-set(
                url('/themes/vidyo/i/guest/tablet/share.png') 1x,
                url('/themes/vidyo/i/guest/tablet/share@2x.png') 2x);
            background-position: center;
            background-repeat: no-repeat;
            width: 124px;
            height: 124px;
            margin: auto auto;
        }
        #moderateConferenceButton {
            background-image: url('/themes/vidyo/i/guest/tablet/manage.png');
            background-image: -webkit-image-set(
                url('/themes/vidyo/i/guest/tablet/manage.png') 1x,
                url('/themes/vidyo/i/guest/tablet/manage@2x.png') 2x);
            background-position: center;
            background-repeat: no-repeat;
            width: 124px;
            height: 124px;
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
<div class="actions">
    <div id="joinConference" class="action" onclick="javascript:join();">
        <div class="instruction"><spring:message htmlEscape="true" code="have.vidyomobile.installed"/></div>
        <div id="joinConferenceButton"></div>
        <div class="actionText"><spring:message htmlEscape="true"  code="join.conference"/></div>
    </div>
    <div id="shareAnnotate" class="action" onclick="javascript:share();">
        <div class="instruction"><spring:message htmlEscape="true" code="have.vidyoslate.installed"/></div>
        <div id="shareAnnotateButton"></div>
        <div class="actionText"><spring:message htmlEscape="true"  code="share.and.annotate"/></div>
    </div>
    <div id="moderateConference" class="action" onclick="javascript:moderate();">
        <div class="instruction">&nbsp;</div>
        <div id="moderateConferenceButton"></div>
        <div class="actionText"><spring:message htmlEscape="true" code="manage.conference"/></div>
    </div>
</div>
<div class="actions2">
    <div id="downloadMobile" class="action" onclick="javascript:downloadMobile();">
        <spring:message code="don.t.have.br.vidyomobile.click"/> <a href="javascript:downloadMobile();"><spring:message htmlEscape="true" code="here"/></a> <spring:message htmlEscape="true" code="to.download"/>
    </div>
    <div id="downloadSlate" class="action" onclick="javascript:downloadSlate();">
        <spring:message code="don.t.have.br.vidyoslate.click"/> <a href="javascript:downloadSlate();"><spring:message htmlEscape="true" code="here"/></a> <spring:message htmlEscape="true" code="to.download"/>
    </div>
    <div id="zincLink" class="action">

    </div>
</div>

<c:if test="${model.isAndroid}">
    <c:if test="${model.zincLink != ''}">
        <div class="actions2">
            <div class="action" onclick="javascript:redirectToZinc()"/>
                <spring:message code="can.t.download.br.vidyomobile.click"/> <a href="javascript:redirectToZinc()"><spring:message htmlEscape="true" code="here"/></a>
            </div>
            <div class="action">
                &nbsp;
            </div>
            <div class="action">
                &nbsp;
            </div>
        </div>
    </c:if>
</c:if>

<script type="text/javascript">

    <c:if test="${model.isAndroid}">
    var mobileStoreLink = '<c:out value="${model.vidyoMobileDownloadLink}"/>';
    var slateStoreLink = '<c:out value="${model.vidyoSlateDownloadLink}"/>';
    
    <c:if test="${model.zincLink != ''}">
    var zincLink = '<c:out value="${model.zincLink}" escapeXml="false"/>';
    function redirectToZinc() {
    	window.location = zincLink;
    	return false;
    }
    </c:if>
    </c:if>

    <c:if test="${!model.isAndroid}">
    var mobileStoreLink = '<c:out value="${model.vidyoMobileDownloadLink}"/>';
    var slateStoreLink = '<c:out value="${model.vidyoSlateDownloadLink}"/>';
    </c:if>


    function join() {
        window.location = 'vidyomobile://<c:out value="${model.hostUrl}"/>';
    }

    function downloadMobile() {
        window.location = mobileStoreLink;
    }

    function downloadSlate() {
        window.location = slateStoreLink;
    }

    function share() {
        window.location = 'vidyoslate://<c:out value="${model.hostUrl}"/>';
    }

    function moderate() {
            window.location = '<c:out value="${model.moderateLink}"/>';
    }


</script>
</body>
</html>
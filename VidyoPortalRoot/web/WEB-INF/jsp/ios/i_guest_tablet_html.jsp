<%@ page session="false" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%
    Locale locale = LocaleContextHolder.getLocale();
    String country = locale.getCountry();
    String html_lang = locale.getLanguage();
    html_lang += country.equalsIgnoreCase("") ? "" : "-"+locale.getCountry();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%= html_lang%>" lang="<%= html_lang%>">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <script type="text/javascript">

        function join() {
            //document.getElementById('join-btn').className ='joinTappedBtn';
            window.location = 'vidyomobile://<c:out value="${model.hostUrl}"/>';
            var clickedAt = +new Date;
            // During tests on 3g/3gs this timeout fires immediately if less than 500ms.
            setTimeout(function(){
                // To avoid failing on return to MobileSafari, ensure freshness!
                if (+new Date - clickedAt < 2000){
                    window.location = "itms-app" +
                            "s://itunes.apple.com/us/app/vidyomobile/id444062464?mt=8";
                }
            }, 1900);
        }

        function share() {
            //document.getElementById('share-btn').className ='shareTappedBtn';
            window.location = 'vidyoslate://<c:out value="${model.hostUrl}"/>';
            var clickedAt = +new Date;
            // During tests on 3g/3gs this timeout fires immediately if less than 500ms.
            setTimeout(function(){
                // To avoid failing on return to MobileSafari, ensure freshness!
                if (+new Date - clickedAt < 2000){
                    window.location = "itms-app" +
                            "s://itunes.apple.com/us/app/vidyoslate/id660477058?ls=1&mt=8";
                }
            }, 1900);
        }

        function download() {
                    window.location = "itms-app" +
                            "s://itunes.apple.com/us/app/vidyomobile/id444062464?mt=8";
        }

        if (document.images) {
            preload = new Image();
            preload.src = "/themes/vidyo/i/guest/ios/tablet/btn_join_tapped.png";
            preload2 = new Image();
            preload2.src = "/themes/vidyo/i/guest/ios/tablet/btn_share_tapped.png";
        }
    </script>
    <style>
        .joinBtn {
            <c:if test="${model.guestJoin}">
            background-image:url('/themes/vidyo/i/guest/ios/tablet/btn_join.png');
            background-size: 460px 66px;
            width: 460px;
            height: 66px;
            </c:if>
            <c:if test="${not model.guestJoin}">
            background-image:url('/themes/vidyo/i/guest/ios/tablet/btn_join_d.png');
            background-size: 362px 98px;
            width: 362px;
            height: 98px;
            </c:if>
            background-repeat: no-repeat;
            margin: 0 auto;
        }
        .joinTappedBtn {
            background-image:url('/themes/vidyo/i/guest/ios/tablet/btn_join_tapped.png');
            background-repeat: no-repeat;
            background-size: 362px 98px;
            width: 362px;
            height: 98px;
            margin: 0 auto;
        }
        .shareBtn {
            background-image:url('/themes/vidyo/i/guest/ios/tablet/btn_share.png');
            background-repeat: no-repeat;
            background-size: 460px 66px;
            width: 460px;
            height: 66px;
            margin: 0 auto;
        }
        .shareTappedBtn {
            background-image:url('/themes/vidyo/i/guest/ios/tablet/btn_share_tapped.png');
            background-repeat: no-repeat;
            background-size: 362px 98px;
            width: 362px;
            height: 98px;
            margin: 0 auto;
        }
    </style>
</head>
<body style="background-image:url('/themes/vidyo/i/guest/ios/tablet/bg_texture.png'); background-repeat: repeat;">
<div style="width: 723px; height: 723px; margin: 30px auto 0px auto;">
    <div style="text-align: center;">
        <c:if test="${not empty model.logoUrl}">
            <img src="<c:url value="${model.logoUrl}"/>" width="343" height="316" alt="Vidyo" />
        </c:if>
        <c:if test="${empty model.logoUrl}">
            <img src="<c:url value="/themes/vidyo/i/guest/ios/tablet/vidyo_logo.png"/>" alt="Vidyo" width="343" height="316" />
        </c:if>
    </div>
    <div style="margin-top: 19px; text-align: center;">
        <c:if test="${model.guestJoin}">
            <div id="join-btn" class="joinBtn" onclick="javascript:join();">
                <div style="padding: 20px 20px 20px 100px; font: normal 24px Helvetica, sans-serif; color: white;">
                    <spring:message code="join.conference"/>
         </c:if>
         <c:if test="${not model.guestJoin}">
            <div id="join-btn" class="joinBtn" onclick="javascript:download();">
                <div style="padding: 34px 20px 20px 20px; font: bold 24px Helvetica, sans-serif; color: white;">
                    <spring:message code="download.vidyomobile" />
            </c:if>
            </div>
        </div>
    </div>
    <c:if test="${model.guestJoin}">
    <div style="margin-top: 28px; text-align: center;">
        <div id="share-btn" class="shareBtn" onclick="javascript:share();">
            <div style="padding: 20px 20px 20px 100px; font: normal 24px Helvetica, sans-serif; color: white;"><spring:message code="share.and.annotate"/></div>
        </div>
    </div>
    </c:if>
    <c:if test="${model.guestJoin}">
    <div style="text-align: center; font: 1.0em arial, sans-serif; padding-top: 10px;">In room: <c:out value="${model.roomName}"/></div>
    </c:if>
</div>
</body>
</html>
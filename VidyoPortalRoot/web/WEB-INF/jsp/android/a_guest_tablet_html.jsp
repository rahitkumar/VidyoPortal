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
    <meta name="viewport" content="width=device-width" />
    <script type="text/javascript">

        var guest_link = 'vidyomobile://<c:out value="${model.hostUrl}"/>';
        var play_store = 'https://play.google.com/store/apps/details?id=com.vidyo.VidyoClient';

        function defaultBrowser() {
            document.write("opening vidyo client <iframe id='vidwin' src='"+guest_link+"' width='100' height='100' style='visibility:hidden;position:absolute;left:0;top:0;'></iframe>");
            var clickedAt = +new Date;
            setTimeout(function(){
                var iframe= document.getElementById('vidwin');
                if (iframe.contentWindow.document ==  undefined) {
                    // To avoid failing on return to browser, ensure freshness!
                    if (+new Date - clickedAt < 2000){
                        window.location = play_store;
                    }
                }
            }, 500);
        }

        function chromeAndFirefoxBrowsers() {
            var w;
            try {
                w = window.open( guest_link, '_blank');
            } catch(e) {}

            if (w) {
                w.location = play_store;
            } else {
                window.location = play_store;
            }
        }

        function OperaBrowser() {
            window.location = guest_link;
            var clickedAt = +new Date;
            // During tests on 3g/3gs this timeout fires immediately if less than 500ms.
            setTimeout(function() {
                // To avoid failing on return to browser, ensure freshness!
                if (+new Date - clickedAt < 2000){
                    window.location = play_store;
                }
            }, 500);
        }

        function join() {
            guest_link = 'vidyomobile://<c:out value="${model.hostUrl}"/>';
            play_store = 'https://play.google.com/store/apps/details?id=com.vidyo.VidyoClient';
            //document.getElementById('join-btn').className ='joinTappedBtn';
            if (navigator.userAgent.match(/Chrome/i)) {
                chromeAndFirefoxBrowsers();
            } else if (navigator.userAgent.match(/opera/i)) {
                OperaBrowser();
            } else if (navigator.userAgent.match(/firefox/i)) {
                chromeAndFirefoxBrowsers();
            } else{
                defaultBrowser();
            }
        }

        function share() {
            guest_link = 'vidyoslate://<c:out value="${model.hostUrl}"/>';
            play_store = 'https://play.google.com/store/apps/details?id=com.vidyo.vidyoslate';
            if (navigator.userAgent.match(/Chrome/i)) {
                chromeAndFirefoxBrowsers();
            } else if (navigator.userAgent.match(/opera/i)) {
                OperaBrowser();
            } else if (navigator.userAgent.match(/firefox/i)) {
                chromeAndFirefoxBrowsers();
            } else{
                defaultBrowser();
            }
        }

        function download() {
            window.location = play_store;
        }

        if (document.images) {
            preload = new Image();
            preload.src = "/themes/vidyo/i/guest/android/tablet/btn_join_tapped.png";
        }
    </script>

    <style>
        .joinBtn {
            <c:if test="${model.guestJoin}">
            background-image:url('/themes/vidyo/i/guest/android/tablet/btn_join_normal.png');
            background-repeat: no-repeat;
            background-size: 444px 105px;
            width: 444px;
            height: 105px;
            </c:if>
            <c:if test="${not model.guestJoin}">
            background-image:url('/themes/vidyo/i/guest/android/tablet/btn_join_normal_d.png');
            background-repeat: no-repeat;
            background-size: 362px 98px;
            width: 362px;
            height: 98px;
            </c:if>
            margin: 0 auto;
        }
        .joinTappedBtn {
            background-image:url('/themes/vidyo/i/guest/android/tablet/btn_join_tapped.png');
            background-repeat: no-repeat;
            background-size: 362px 98px;
            width: 362px;
            height: 98px;
            margin: 0 auto;
        }
        .shareBtn {
            background-image:url('/themes/vidyo/i/guest/android/tablet/btn_share.png');
            background-repeat: no-repeat;
            background-size: 444px 105px;
            width: 444px;
            height: 105px;
            margin: 0 auto;
        }
    </style>
</head>
<body style="background-image:url('/themes/vidyo/i/guest/android/tablet/bg_texture.png'); background-repeat: repeat;">
<div style="margin: 50px auto 0px auto;">
    <div style="text-align: center;">
        <c:if test="${not empty model.logoUrl}">
            <img src="<c:url value="${model.logoUrl}"/>" width="367" height="339" alt="Vidyo" />
        </c:if>
        <c:if test="${empty model.logoUrl}">
            <img src="<c:url value="/themes/vidyo/i/guest/android/tablet/vidyo_logo.png"/>" alt="Vidyo" width="367" height="339" />
        </c:if>
    </div>
    <div style="margin-top: 40px; text-align: center;">
        <c:if test="${model.guestJoin}">
            <div id="join-btn" class="joinBtn" onclick="javascript:join();">
                <div style="padding: 33px 20px 20px 105px; font: normal 32px Droid Sans, sans-serif; color: white;">
                    <spring:message code="join.conference"/>
        </c:if>
        <c:if test="${not model.guestJoin}">
                    <div id="join-btn" class="joinBtn" onclick="javascript:download();">
                <div style="padding: 33px 20px 20px 25px; font: normal 26px Droid Sans, sans-serif; color: #4A6A27;">
                    <spring:message code="download.vidyomobile" />
        </c:if>
            </div>
        </div>
    </div>
                <c:if test="${model.guestJoin}">
                    <div style="margin-top: 28px; text-align: center;">
                        <div id="share-btn" class="shareBtn" onclick="javascript:share();">
                            <div style="padding: 33px 20px 20px 105px; font: normal 32px Droid Sans, sans-serif; color: white;"><spring:message code="share.and.annotate"/></div>
                        </div>
                    </div>
                </c:if>
    <c:if test="${model.guestJoin}">
    <div style="text-align: center; font: 1.0em arial, sans-serif; padding-top: 10px;">In room: <c:out value="${model.roomName}"/></div>
    </c:if>
</div>
</body>
</html>
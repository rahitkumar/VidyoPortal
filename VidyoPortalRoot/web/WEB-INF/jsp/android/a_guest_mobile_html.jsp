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

            function download() {
                window.location = play_store;
            }

            if (document.images) {
                preload = new Image();
                preload.src = "/themes/vidyo/i/guest/android/mobile/btn_join_tapped.png";
            }
    </script>

    <style>
        .joinBtn {
            <c:if test="${model.guestJoin}">
            background-image:url('/themes/vidyo/i/guest/android/mobile/btn_join_normal.png');
            background-size: 246px 58px;
            width: 246px;
            height: 58px;
            margin: 0px auto;
            </c:if>
            <c:if test="${not model.guestJoin}">
            background-image:url('/themes/vidyo/i/guest/android/mobile/btn_join_normal_d.png');
            background-size: 300px 83px;
            width: 300px;
            height: 83px;
            </c:if>
            background-repeat: no-repeat;
        }
        .joinTappedBtn {
            background-image:url('/themes/vidyo/i/guest/android/mobile/btn_join_tapped.png');
            background-repeat: no-repeat;
            background-size: 300px 83px;
            width: 300px;
            height: 82px;
        }
    </style>
</head>
<body style="background-image:url('/themes/vidyo/i/guest/android/mobile/bg_texture.png'); background-repeat: repeat;">
<div style="width: 300px; height: 250px; margin: 80px auto;">
    <div style="text-align: center;">
        <c:if test="${not empty model.logoUrl}">
            <img src="<c:url value="${model.logoUrl}"/>" width="206" height="191" alt="Vidyo" />
        </c:if>
        <c:if test="${empty model.logoUrl}">
            <img src="<c:url value="/themes/vidyo/i/guest/android/mobile/vidyo_logo.png"/>" width="206" height="191" alt="Vidyo"/>
        </c:if>
    </div>
    <div style="margin-top: 53px;">
        <c:if test="${model.guestJoin}">
            <div id="join-btn" class="joinBtn" onclick="javascript:join();">
                <div style="padding: 17px 0px 10px 80px; font: normal 20px Droid Sans, sans-serif; color: white;">
                    <spring:message code="join.conference"/>
            </c:if>
        <c:if test="${not model.guestJoin}">
                <div id="join-btn" class="joinBtn" onclick="javascript:download();">
                <div style="padding: 30px 20px 20px 45px; font: normal 20px Droid Sans, sans-serif; color: #4A6A27;">
                    <spring:message code="download.vidyomobile" />
             </c:if>
            </div>
        </div>
    </div>
    <c:if test="${model.guestJoin}">
    <div style="text-align: center; font: 1.0em arial, sans-serif; padding-top: 30px;">In room: <c:out value="${model.roomName}"/></div>
    </c:if>
</div>
</body>
</html>
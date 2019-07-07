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
    <meta name="viewport" content="initial-scale = 1.0,maximum-scale = 1.0" />
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

                    function download() {
                                window.location = "itms-app" +
                                        "s://itunes.apple.com/us/app/vidyomobile/id444062464?mt=8";
                    }

                    if (document.images) {
                        preload = new Image();
                        preload.src = "/themes/vidyo/i/guest/ios/mobile/btn_join_tapped.png";
                    }
    </script>
    <style>
        .joinBtn {
            <c:if test="${model.guestJoin}">
            background-image:url('/themes/vidyo/i/guest/ios/mobile/btn_join.png');
            background-size: 272px 39px;
            width: 272px;
            height: 39px;
            </c:if>
            <c:if test="${not model.guestJoin}">
            background-image:url('/themes/vidyo/i/guest/ios/mobile/btn_join_d.png');
            background-repeat: no-repeat;
            background-size: 250px 69px;
            width: 250px;
            height: 69px;
            </c:if>
        }
        .joinTappedBtn {
            background-image:url('/themes/vidyo/i/guest/ios/mobile/btn_join_tapped.png');
            background-repeat: no-repeat;
            background-size: 250px 69px;
            width: 250px;
            height: 69px;
        }
    </style>
</head>
<body style="background-image:url('/themes/vidyo/i/guest/ios/mobile/bg_texture.png'); background-repeat: repeat;">
<div style="width: 272px; height: 250px; margin: 30px auto;">
    <div style="text-align: center;">
        <c:if test="${not empty model.logoUrl}">
            <img src="<c:url value="${model.logoUrl}"/>" width="206" height="190" alt="Vidyo" />
        </c:if>
        <c:if test="${empty model.logoUrl}">
            <img src="<c:url value="/themes/vidyo/i/guest/ios/mobile/vidyo_logo.png"/>" width="206" height="190" alt="Vidyo" />
        </c:if>
    </div>
    <div style="margin-top: 20px;">
        <c:if test="${model.guestJoin}">
            <div id="join-btn" class="joinBtn" onclick="javascript:join();">
                <div style="padding: 10px 20px 20px 105px; font: normal 16px Helvetica, sans-serif; color: white;">
                    <spring:message code="join.conference"/>
        </c:if>
        <c:if test="${not model.guestJoin}">
            <div id="join-btn" class="joinBtn" onclick="javascript:download();">
                <div style="padding: 25px 20px 20px 30px; font: bold 16px Helvetica, sans-serif; color: white;">
                    <spring:message code="download.vidyomobile" />
            </c:if>
            </div>
        </div>
    </div>
    <c:if test="${model.guestJoin}">
    <div style="text-align: center; font: 1.0em arial, sans-serif; padding-top: 10px;">In room: <c:out value="${model.roomName}"/></div>
    </c:if>
</div>
</body>
</html>
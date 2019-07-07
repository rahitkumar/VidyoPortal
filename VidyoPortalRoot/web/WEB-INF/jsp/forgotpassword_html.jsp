<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page session="false" %>

<%@ page pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld" %>

<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>

<%
Locale locale = LocaleContextHolder.getLocale();
%>
<c:set var="systemlang"><%= locale.toString()%></c:set>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="_csrf" content="<c:out value="${_csrf.token}"/>"/>
    <meta name="_csrf_header" content="<c:out value="${_csrf.headerName}"/>"/>
    
    <title><spring:message javaScriptEscape="true" code="forgot.password"/></title>
    
    <link rel="stylesheet" href="themes/vidyo/common.css">

    <link rel="shortcut icon" href="favicon.ico">

    <script type="text/javascript"  src="js/jquery.js"></script>
    <script type="text/javascript"  src="js/jquery-migrate.js"></script>

    <script type="text/javascript">
        $(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function(e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        });
    </script>
    
</head>


<body> 
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
            <div id="infoIconSecure"></div>
            <div id="infoMsg"><spring:message javaScriptEscape="true" code="forgot.your.password"/></div>
        </div>
        
        <div id="row-main">
            <div id="forgotPwd">
                <form id="frm-forgotpassword">
                    <div class='password-form-item'>
                        <input type="email" id="emailAddress" class="password" placeholder='<spring:message javaScriptEscape="true" code="enter.your.email"/>' required/>
                    </div>
                    <div id="emailAddress-error" class='password-form-item-error'>
                    </div>
                    <div class='forgot-email-submit-button-item'>
                        <input type="button" id="submitEmail" value='<spring:message code="submit"/>' class="submit-password"/>
                    </div>
                </form>
            </div>
        </div>
        
        <div id="row-help">
            <div id="helpText">
            <vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="need.help"/></vidyo:replaceString> <a href='<c:url value="/contact.html"/>' style="color:#7EACDF;text-decoration: inherit;" target="_blank"><vidyo:replaceString from="\\\\'" to="'"><spring:message javaScriptEscape="false" code="contact.support"/></vidyo:replaceString></a>
            </div>
        </div>
    </div>
      
    <script type="text/javascript">

    $().ready( function() {
    	$("#emailAddress").keyup(function() {
    		if($("#emailAddress").val() == null || $("#emailAddress").val().trim() == "") {
    			$("#emailAddress-error").text('<spring:message code="please.provide.your.e.mail.address.to.recover.your.password"/>');
    		} else {
    			$("#emailAddress-error").text('');
    		}
    	});
    	
    	$("#submitEmail").on("click", function() {
    		if($("#emailAddress").val() == null || $("#emailAddress").val().trim() == "") {
    			$("#emailAddress-error").text('<spring:message code="please.provide.your.e.mail.address.to.recover.your.password"/>');
    			return;
    		}
    		if (!validEmail($("#emailAddress").val().trim())) {
    			$("#emailAddress-error").text('<spring:message code="email.address.does.not.match.to.domain.format"/>');
    			return;
    		}
    		$.ajax( {
                 method: 'POST',
                 url : '<c:url value="/ui/forgotpassword.ajax"/>',
                 data : { "email": $("#emailAddress").val().trim()}
            }).success( function(data) {
//            	console.log("In success");
            	if ($(data).find("message").attr("success") == "false") {
//            		console.log("got error");
            		var errs = $(data).find("msg");
                    var errMsg = "";
                    for (var i = 0; i < errs.length - 1; i++) {
                    	errMsg += $(errs[i]).text() + "\n";
                    }
                    
                    errMsg += $(errs[errs.length - 1]).text()
                    
                    alert(errMsg);
                } else {
//                    console.log("without error");
                    if (!window.location.origin) {
                        window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
                    }
                    
                    window.location.href = window.location.origin + '<c:url value="/closeforgotpassword.html"/>';
                }
             }).error(function( event, jqxhr, settings, thrownError){
//                 console.log("in error");
                 alert(thrownError);
            });
        });
    });
    
    function validEmail(v) {
        var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
        return filter.test(v);
    }
    
    </script>
 
</body>

</html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page session="false" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

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

    <title><spring:message javaScriptEscape="true" code="create.new.password"/></title>

    <link rel="stylesheet" href="themes/vidyo/common.css">

    <link rel="stylesheet" href="themes/vidyo/jqueryui/css/jquery.ui.all.css">
    <link rel="shortcut icon" href="favicon.ico">

    <script type="text/javascript"  src="js/jquery.js"></script>
    <script type="text/javascript"  src="js/jquery-migrate.js"></script>
    <script type="text/javascript"  src="js/jquery-ui.js"></script>

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
            <div id="infoMsg"><spring:message javaScriptEscape="true" code="create.new.password"/></div>
        </div>

        <div id="row-main">
            <div id="changePwd">
                <form class="change-password-frm">
                    <div class='password-form-item'>
                        <input type="password" id="currentpassword" placeholder='<spring:message javaScriptEscape="true" code="current.password"/>' class="password" required/>
                    </div>
                    <div id="currentpassword-error" class='password-form-item-error'>
                    </div>
                    <div class='password-form-item'>
                        <input type="password" id="newpassword" placeholder='<spring:message javaScriptEscape="true" code="new.password"/>' class="password" required/>
                    </div>
                    <div id="newpassword-error" class='password-form-item-error'>
                    </div>
                    <div class='password-form-item'>
                        <input type="password" id="reenternewpassword" placeholder='<spring:message javaScriptEscape="true" code="reenter.new.password"/>' class="password" required/>
                    </div>
                    <div id="reenternewpassword-error" class='password-form-item-error'>
                    </div>
                    <div class='password-form-item'>
                        <div class='forget-form-item'>
                        <spring:message javaScriptEscape="true" code="forgot.password"/> <a id="forgotpasswordlink" href="#" style="color:#8888CC"><spring:message javaScriptEscape="true" code="click.here"/></a>
                        </div>
                    </div>
                    <div class='password-form-item'>
                        <input type="button" id="submit" value='<spring:message code="submit"/>' class="submit-password"/>
                    </div>
                </form>
            </div>
            <div id="window-forgotpassword" class="dialog_window" title='<spring:message javaScriptEscape="true" code="forgot.password"/>'>
                <form id="frm-forgotpassword">
                    <div class='password-form-item'>
                        <label for="emailAddress"><spring:message javaScriptEscape="true" code="e.mail.address"/></label>
                        <input type="email" id="emailAddress" class="password" required/>
                    </div>
                    <div id="emailAddress-error" class='password-form-item-error'>
                    </div>
                    <div class='email-submit-button-item'>
                        <input type="button" id="submitEmail" value='<spring:message code="submit"/>' class="submit-password"/>
                    </div>
                </form>
            </div>
        </div>

        <div id="row-help">
            <div id="helpText">
            <vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="need.help"/></vidyo:replaceString> <a href='<c:url value="/contact.html"/>' style="color:#8888CC" target="_blank"><vidyo:replaceString from="\\\\'" to="'"><spring:message javaScriptEscape="false" code="contact.support"/></vidyo:replaceString></a>
            </div>
        </div>
    </div>

    <script type="text/javascript">

    var authenticated = <c:out value="${model.authenticated}"/>;
    if(authenticated == null || authenticated == false) {
//    	console.log("Not authenticated");
    	if (!window.location.origin) {
            window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
        }

    	window.location.href = window.location.origin + '<c:url value="/closechangepassword.html?isUpdated=false"/>';
    }

    $().ready( function() {
    	$("#currentpassword").keyup(function() {
    		if($("#currentpassword").val() == null || $("#currentpassword").val().trim() == "") {
    			$("#currentpassword-error").text('<spring:message code="please.provide.current.password"/>');
    		} else {
    			$("#currentpassword-error").text('');
    		}
    	});
    	$("#newpassword").keyup(function() {
    		if($("#newpassword").val() == null || $("#newpassword").val().trim() == "") {
    			$("#newpassword-error").text('<spring:message code="please.provide.new.password"/>');
    		} else if($("#reenternewpassword").val() != null && $("#reenternewpassword").val().trim() != "" &&
    				$("#newpassword").val().trim() != $("#reenternewpassword").val().trim()) {
    			$("#newpassword-error").text('<spring:message code="password.not.match"/>');
    		} else {
    			$("#newpassword-error").text('');
    			$("#reenternewpassword-error").text('');
    		}
    	});
    	$("#reenternewpassword").keyup(function() {
    		if($("#reenternewpassword").val() == null || $("#reenternewpassword").val().trim() == "") {
    			$("#reenternewpassword-error").text('<spring:message code="please.reenter.new.password"/>');
    		} else if($("#newpassword").val() != null && $("#newpassword").val().trim() != "" &&
    				$("#newpassword").val().trim() != $("#reenternewpassword").val().trim()) {
    			$("#reenternewpassword-error").text('<spring:message code="password.not.match"/>');
    		} else {
    			$("#reenternewpassword-error").text('');
    			$("#newpassword-error").text('');
    		}
    	});
    	$("#submit").on("click", function() {
    		if($("#currentpassword").val() == null || $("#currentpassword").val().trim() == "") {
    			$("#currentpassword-error").text('<spring:message code="please.provide.current.password"/>');
    			return;
    		}
    		if($("#newpassword").val() == null || $("#newpassword").val().trim() == "") {
    			$("#newpassword-error").text('<spring:message code="please.provide.new.password"/>');
    			return;
    		}
    		if($("#reenternewpassword").val() == null || $("#reenternewpassword").val().trim() == "") {
    			$("#reenternewpassword-error").text('<spring:message code="please.reenter.new.password"/>');
    			return;
    		}
    		if($("#newpassword").val().trim() != $("#reenternewpassword").val().trim()) {
//    			console.log('<spring:message code="password.not.match"/>');
    			$("#reenternewpassword-error").text('<spring:message code="password.not.match"/>');
    			return;
    		}
    		if($("#newpassword").val().trim() == $("#currentpassword").val().trim()) {
//    			console.log('<spring:message code="password.not.match"/>');
    			$("#newpassword-error").text('<spring:message code="password.cannot.be.same"/>');
    			return;
    		}
    		$.ajax( {
                 method: 'POST',
                 url : '<c:url value="/ui/changepassword.ajax"/>',
                 data : { "currentpassword": $("#currentpassword").val(), "newpassword": $("#newpassword").val(), "reenternewpassword": $("#reenternewpassword").val()}
            }).success( function(data) {
//            	console.log("In success");
            	if ($(data).find("message").attr("success") == "false") {
            		var errs = $(data).find("msg").text();
            		$("<div></div>").dialog({
                	    width: 'auto',
                	    height: 'auto',
                	    resizable: false,
            			modal: true
            		}).html(errs);
                } else {
                    if (!window.location.origin) {
                        window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ':' + window.location.port: '');
                    }
                    window.location.href = window.location.origin + '<c:url value="/closechangepassword.html?isUpdated=true"/>';
                }
             }).error(function( event, jqxhr, settings, thrownError){
                 var errMsg = event.status + " - " + event.statusText;
                 if(thrownError) {
                     errMsg += ". " + thrownError;
                 }
                 alert(errMsg);
            });
        });

    	$('#window-forgotpassword').dialog({
    	    width: 'auto',
    	    height: 'auto',
    	    autoOpen : false,
    	    modal: true,
    	    resizable: false,
    	    close: function( event, ui ) {$("#emailAddress-error").text('');$("#emailAddress").val('');}
    	});

    	$("#forgotpasswordlink").on("click", function() {
    		var forgotpassword_dialog = $("#window-forgotpassword");

    		forgotpassword_dialog.dialog("open");
    	});

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
//                console.log("In success");
                if ($(data).find("message").attr("success") == "false") {
//                    console.log("got error");
                    var errs = $(data).find("msg");
                    var errMsg = "";
                    for (var i = 0; i < errs.length - 1; i++) {
                    	errMsg += $(errs[i]).text() + "\n";
                    }

                    errMsg += $(errs[errs.length - 1]).text()

                    alert(errMsg);
                } else {
//                    console.log("without error");
                    alert("<spring:message javaScriptEscape="true" code="please.check.your.e.mail.account.for.instructions.on.completing.the.password.reset.process"/>");
                    $("#window-forgotpassword").dialog("close");
                }
             }).error(function( event, jqxhr, settings, thrownError){
//                 console.log("in error");
                 var errMsg = event.status + " - " + event.statusText;
                 if(thrownError) {
                     errMsg += ". " + thrownError;
                 }
                 alert(errMsg);
            });
        });
    });

    function validEmail(v) {
        var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,}|[0-9]{1,3})(\]?)$/;
        return filter.test(v);
    }

    </script>

</body>

</html>
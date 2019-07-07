<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page import="java.util.Locale" %>

<%@ include file="include_html.jsp" %>

<%
	String country = locale.getCountry();
	String html_lang = locale.getLanguage();
	html_lang += country.equalsIgnoreCase("") ? "" : "-"+locale.getCountry();
%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%= html_lang%>" lang="<%= html_lang%>">
<c:set var="admin_help"><spring:theme code="admin_help"/></c:set>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<title><vidyo:replaceString from="\\\\'" to="'"><spring:message code="title"/></vidyo:replaceString></title>
	</head>


	<body>

		<div id="container">
			<div id="header">
				<div id="logo"></div>

				<security:authorize ifNotGranted="ROLE_ANONYMOUS">
					<c:set var="nav"><c:out value="${model.nav}"/></c:set>
					<ul id="nav">
						<li <c:if test="${nav == 'members'}">class="selected"</c:if>><div id="members-tab"></div></li>
						<li <c:if test="${nav == 'rooms'}">class="selected"</c:if>><div id="rooms-tab"></div></li>
						<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER')">
							<li <c:if test="${nav == 'groups'}">class="selected"</c:if>><div id="groups-tab"></div></li>
							<li <c:if test="${nav == 'settings'}">class="selected"</c:if>><div id="settings-tab"></div></li>
						</security:authorize>
					</ul>

					<ul id="siteaids">
						<li><span><vidyo:replaceString from="\\\\'" to="'"><spring:message code="welcome"/></vidyo:replaceString>&nbsp;<strong><c:out value="${model.user.memberName}"/></strong></span>
						</li>
						<li>|</li>
						<li><a href='<c:url value="logout.html"/>'><vidyo:replaceString from="\\\\'" to="'"><spring:message code="logout"/></vidyo:replaceString></a></li>
					</ul>
				</security:authorize>

			</div>

<security:authorize access="hasRole('ROLE_ANONYMOUS')">
	<script type="text/javascript">
		Ext.onReady(function(){
			Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';

			Ext.get('logo').load({
			  url: '<c:url value="customizedlogoinmarkup.ajax"/>',
			  text: ''
			});
		});
	</script>
</security:authorize>

<security:authorize ifNotGranted="ROLE_ANONYMOUS">
	<script type="text/javascript">
		Ext.onReady(function(){
			Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';

			Ext.get('logo').load({
			  url: '<c:url value="customizedlogoinmarkup.ajax"/>',
			  text: ''
			});

			new Ext.Button({
				text: '<spring:message code="users"/>',
				renderTo: 'members-tab',
				ctCls: 'tabs',
				minWidth: 120,
				handler: function(){
					window.location = '<c:url value="members.html"/>';
				}
			});

			new Ext.Button({
				text: '<spring:message code="meeting.rooms"/>',
				renderTo: 'rooms-tab',
				ctCls: 'tabs',
				minWidth: 120,
				handler: function(){
					window.location = '<c:url value="rooms.html"/>';
				}
			});

			<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER')">
				new Ext.Button({
					text: '<spring:message code="groups"/>',
					renderTo: 'groups-tab',
					ctCls: 'tabs',
					minWidth: 120,
					handler: function(){
						window.location = '<c:url value="groups.html"/>';
					}
				});

				new Ext.Button({
					text: '<spring:message code="settings"/>',
					renderTo: 'settings-tab',
					ctCls: 'tabs',
					minWidth: 120,
					handler: function(){
						window.location = '<c:url value="settings.html"/>';
					}
				});
			</security:authorize>
		});
	</script>
</security:authorize>


<%@ page session="false" %>

<%@ page pageEncoding="iso-8859-1" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%
	response.setContentType("text/javascript");
	String portal_addr = request.getScheme()+"://"+request.getHeader("host")+request.getContextPath();
%>

<script type="text/javascript">

	var VDNotRunning = function() {
		Ext.getDom('testImage').src="red.png" ;

		Ext.Msg.show({
			<c:if test="${model.installer == 'no_installer'}">
				title: '<spring:message code="vidyodesktoptrade.installer.is.not.available"/>',
				msg: '<spring:message code="vidyodesktoptrade.installer.is.not.available1"/><br>' +
				'<spring:message code="please.contact.administrator.to.resolve.the.problem"/>',
				buttons: {
					cancel: '<spring:message code="logout"/>'
				},
				fn: function(btn, text){
					if (btn == 'cancel') {
					    logoutSuper();
					}
				},
			</c:if>
			<c:if test="${model.installer != 'no_installer'}">
				title: '<spring:message code="vidyodesktoptrade.software.is.not.detected"/>',
				msg: '<spring:message code="vidyodesktoptrade.software.is.not.runned.or.installed"/><br>' +
				'<spring:message code="click.on.download.and.install.to.download.and.run.the.installer.program"/><br>' +
				'<spring:message code="or.run.vidyodesktoptrade.software.and.click.on.try.again"/>',
				buttons: {
					ok: '<spring:message code="download.and.install"/>',
					cancel: '<spring:message code="try.again"/>'
				},
				fn: function(btn, text){
					if (btn == 'ok') {
						window.location='download.html';
					}
					if (btn == 'cancel') {
						window.location='<c:out value="${model.referer}"/>';
					}
				},
			</c:if>
			width: 400,
			icon: Ext.Msg.ERROR
		});
	};

	<c:if test="${empty model.noindicator}">
		Ext.onReady(function(){
			Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';

			<c:if test="${empty model.vmconnect}">
				Ext.Msg.show({
					title: '<spring:message code="vidyomanagertrade.software.is.not.detected"/>',
					msg: '<spring:message code="vidyomanagertrade.software.has.not.been.set.up.correctly"/><br>' +
						'<spring:message code="please.contact.administrator.to.resolve.the.problem"/>',
					buttons: Ext.Msg.OK,
					fn: function(btn, text){
						    logoutSuper();
						    },
					width: 600,
					icon: Ext.Msg.ERROR
				});
			</c:if>
			<c:if test="${not empty model.vmconnect}">
				var dh = Ext.DomHelper;
				dh.append('indicator', '<img id="testImage" name="testImage" src="http://127.0.0.1:63457/dummy?<c:out value="${model.vmconnect}"/>" alt="" onerror="VDNotRunning();"/>');
			</c:if>
		});
	</c:if>
</script>

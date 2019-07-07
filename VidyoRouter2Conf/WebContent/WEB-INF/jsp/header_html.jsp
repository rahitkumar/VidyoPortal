<%@ include file="include_html.jsp" %>
	<body>

		<div id="container">
			<div id="header">
				<div id="logo"><img src="themes/vidyo/i/logo.gif" border="0"></div>

				<security:authorize access="!hasRole('ROLE_ANONYMOUS')">
					<c:set var="nav"><c:out value="${model.nav}"/></c:set>
					<ul id="nav">
						<li <c:if test="${nav == 'settings'}">class="selected"</c:if>><div id="settings-tab"></div></li>
					</ul>

					<ul id="siteaids">
						<li><span><vidyo:replaceString from="\\\\'" to="'"><spring:message code="welcome"/></vidyo:replaceString>&nbsp;<strong><c:out value="${model.userName}"/> [<c:out value="${pageContext.request.remoteHost}"/>]</strong></span>
						</li>
						<li>|</li>
						<li><a href="javascript:logout();"><vidyo:replaceString from="\\\\'" to="'"><spring:message code="logout"/></vidyo:replaceString></a></li>

					</ul>
                    <form method="POST" action="<c:url value='logout.html'/>" style="display: none;" id="logoutForm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    </form>
				</security:authorize>

			</div>

            <script type="text/javascript">
                function logout() {
                    document.getElementById("logoutForm").submit();
                }
            </script>

<security:authorize access="hasRole('ROLE_ANONYMOUS')">
<script type="text/javascript">
	Ext.onReady(function(){
		Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';
	});
	</script>
</security:authorize>

<security:authorize access="!hasRole('ROLE_ANONYMOUS')">
<script type="text/javascript">
	Ext.onReady(function(){
		Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';

		new Ext.Button({
			text: '<spring:message code="settings"/>',
			renderTo: 'settings-tab',
			ctCls: 'tabs',
			minWidth: 120,
			handler: function(){
				window.location = '<c:url value="maintenance.html"/>';
			}
		});
	});
</script>
</security:authorize>

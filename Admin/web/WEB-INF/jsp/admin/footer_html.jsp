<%@ page import="java.text.SimpleDateFormat" %>
<%
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
	java.util.Date currentDate = new java.util.Date();
	String present_year = formatter.format(currentDate);
%>

<c:set var="filename"><%= request.getRequestURI().split("/")[request.getRequestURI().split("/").length - 1]%></c:set>

		<div id="footer">
			<ul>
				<li><a href='<c:url value="about.html"/>'><vidyo:replaceString from="\\\\'" to="'"><spring:message code="about_us"/></vidyo:replaceString></a> | </li>
				<li><a href='<c:url value="contact.html"/>'><vidyo:replaceString from="\\\\'" to="'"><spring:message code="contact_us"/></vidyo:replaceString></a> | </li>

			<c:if test="${filename == 'about_html.jsp'}">
				<li><a href='<c:url value="terms.html"/>'><vidyo:replaceString from="\\\\'" to="'"><spring:message code="terms_of_services"/></vidyo:replaceString></a> | </li>
			</ul>
            <p>&copy; Vidyo 2008-<%= present_year%></p>
			</c:if>

			<c:if test="${filename != 'about_html.jsp'}">
				<li><a href='<c:url value="terms.html"/>'><vidyo:replaceString from="\\\\'" to="'"><spring:message code="terms_of_services"/></vidyo:replaceString></a></li>
			</ul>
			</c:if>
		</div>

	</div>
  </body>
</html>
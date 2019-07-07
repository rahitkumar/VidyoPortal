<%@ page session="false" %><%@ page contentType="application/xml" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><c:out value="${model.output}" escapeXml="false"/>
<%
HttpServletResponse httpResponse = (HttpServletResponse)response;

httpResponse.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); 
response.addHeader("Cache-Control", "post-check=0, pre-check=0");
httpResponse.setHeader("Pragma","no-cache"); 
httpResponse.setDateHeader ("Expires", 0); 
httpResponse.setHeader("Connection", "close");
%>
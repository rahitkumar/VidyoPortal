<%@ page import="org.springframework.http.HttpStatus" %>
<%@ page session="false" pageEncoding="utf-8" contentType="text/html; charset=UTF-8" %>
    <%
        final Integer errorCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if((Integer) request.getAttribute("javax.servlet.error.status_code")==401)  {
            response.setContentType("application/json");
    %>
        {"status":{"code":"<%= errorCode%>","message":"UnAuthorized"}}
    <%
        } else if(request.getContentType() != null && request.getContentType().equalsIgnoreCase("application/json"))  {
            response.setContentType("application/json");
            String msg = "";
            try {
                msg = HttpStatus.valueOf(errorCode).getReasonPhrase();
            } catch (Exception e ) {
                msg = "Unknown";
            }
    %>

     {"status":{"code":<%= errorCode%>,"message":"<%=msg%>"}}

    <%
      } else{
 	%>
    	  <!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title><%= errorCode %></title>
  <style>
    body {
      margin: 20px;
      text-align: center;
    }
    h1 {
      font: bold 36px sans-serif;
    }
    h2 {
      font: italic 16px sans-serif;
    }
  </style>
</head>
<body>
<h1><%= errorCode %></h1>
<h2>Unable to process this request.<br />Please check your request and try again.</h2>
</body>
</html>
 <%
      }
      %>



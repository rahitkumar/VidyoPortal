<%@ page session="false" pageEncoding="utf-8" contentType="text/html; charset=UTF-8"
        %><% final String errorCode = "" + (Integer) request.getAttribute("javax.servlet.error.status_code");
%><!doctype html>
<html>
<head>
  <meta charset="utf-8">
  <title><%= errorCode %></title>
  <link rel="shortcut icon" href="<%=request.getContextPath()%>%>/favicon.ico" />
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
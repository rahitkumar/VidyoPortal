<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results>1</results>
    <row>
        <chatAvailable><c:out value="${model.chatAvailable}" /></chatAvailable>
        <publicChatEnabled><c:out value="${model.publicChatEnabled}" /></publicChatEnabled>
        <privateChatEnabled><c:out value="${model.privateChatEnabled}" /></privateChatEnabled>
    </row>
</dataset>
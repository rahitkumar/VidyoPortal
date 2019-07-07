<?xml version="1.0" encoding="UTF-8"?>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<dataset>
 <results>1</results>
    <row>
        <subject><c:out value="${model.subject}" /></subject>
        <content><c:out value="${model.content}" /></content>
    </row>
</dataset>


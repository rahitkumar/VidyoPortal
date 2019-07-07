<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
 <results><c:out value="${model.num}"/></results>
    <c:forEach items="${model.list}" var="member">
        <security:authorize access="hasRole('ROLE_SUPER')">
        <row>
            <memberID><c:out value="${member.memberID}"/></memberID>
            <username><c:out value="${member.username}"/></username>
            <memberName><c:out value="${member.memberName}"/></memberName>
            <emailAddress><c:out value="${member.emailAddress}"/></emailAddress>
            <langID><c:out value="${member.langID}"/></langID>
            <description><c:out value="${model.member.description}"/></description>
            <memberCreated><c:out value="${member.memberCreated}"/></memberCreated>
            <enable><c:out value="${member.active}"/></enable>
            <c:if test="${member.memberID == model.user.memberID}">
                 <isDefault>true</isDefault>
            </c:if>
            <c:if test="${member.memberID != model.user.memberID}">
                 <isDefault>false</isDefault>
            </c:if>
        </row>
        </security:authorize>
    </c:forEach>
</dataset>
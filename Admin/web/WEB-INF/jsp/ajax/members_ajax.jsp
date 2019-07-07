<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.num}"/></results>
	<c:forEach items="${model.list}" var="member">
		<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_SUPER')">
		<row>
			<memberID><c:out value="${member.memberID}"/></memberID>
			<roleID><c:out value="${member.roleID}"/></roleID>
			<roleName><c:out value="${member.roleName}"/></roleName>
			<tenantID><c:out value="${member.tenantID}"/></tenantID>
			<groupID><c:out value="${member.groupID}"/></groupID>
			<groupName><c:out value="${member.groupName}"/></groupName>
			<langID><c:out value="${member.langID}"/></langID>
			<profileID><c:out value="${member.profileID}"/></profileID>
			<username><c:out value="${member.username}"/></username>
			<memberName><c:out value="${member.memberName}"/></memberName>
			<enable><c:out value="${member.active}"/></enable>
			<emailAddress><c:out value="${member.emailAddress}"/></emailAddress>
			<memberCreated><c:out value="${member.memberCreated}"/></memberCreated>
			<roomID><c:out value="${member.roomID}"/></roomID>
			<roomTypeID><c:out value="${member.roomTypeID}"/></roomTypeID>
			<roomType><c:out value="${member.roomType}"/></roomType>
			<roomName><c:out value="${member.roomName}"/></roomName>
			<roomExtNumber><c:out value="${member.roomExtNumber}"/></roomExtNumber>
            <importedUsed><c:out value="${member.importedUsed}"/></importedUsed>
            <userMemberId><c:out value="${model.user.memberID}"/></userMemberId>
		</row>
		</security:authorize>
		<security:authorize access="!hasAnyRole('ROLE_ADMIN','ROLE_SUPER')">
		<c:if test="${member.roleID != '1'}">
		<row>
			<memberID><c:out value="${member.memberID}"/></memberID>
			<roleID><c:out value="${member.roleID}"/></roleID>
			<roleName><c:out value="${member.roleName}"/></roleName>
			<tenantID><c:out value="${member.tenantID}"/></tenantID>
			<groupID><c:out value="${member.groupID}"/></groupID>
			<groupName><c:out value="${member.groupName}"/></groupName>
			<langID><c:out value="${member.langID}"/></langID>
			<profileID><c:out value="${member.profileID}"/></profileID>
			<username><c:out value="${member.username}"/></username>
			<memberName><c:out value="${member.memberName}"/></memberName>
			<enable><c:out value="${member.active}"/></enable>
			<emailAddress><c:out value="${member.emailAddress}"/></emailAddress>
			<memberCreated><c:out value="${member.memberCreated}"/></memberCreated>
			<roomID><c:out value="${member.roomID}"/></roomID>
			<roomTypeID><c:out value="${member.roomTypeID}"/></roomTypeID>
			<roomType><c:out value="${member.roomType}"/></roomType>
			<roomName><c:out value="${member.roomName}"/></roomName>
			<roomExtNumber><c:out value="${member.roomExtNumber}"/></roomExtNumber>
            <importedUsed><c:out value="${member.importedUsed}"/></importedUsed>
            <userMemberId><c:out value="${model.user.memberID}"/></userMemberId>
        </row>
		</c:if>
		</security:authorize>
	</c:forEach>
</dataset>
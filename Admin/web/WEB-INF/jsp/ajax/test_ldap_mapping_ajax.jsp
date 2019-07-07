<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<message success="<c:out value="${model.success}"/>">
    <row>
        <field>
            <id><spring:message code="user.name"/></id>
            <msg><c:out value="${model.member.username}"/></msg>
        </field>
    </row>
    <row>
        <field>
            <id><spring:message code="user.type"/></id>
            <msg><c:out value="${model.member.roleName}"/></msg>
        </field>
    </row>
    <row>
        <field>
            <id><spring:message code="display.name"/></id>
            <msg><c:out value="${model.member.memberName}"/></msg>
        </field>
    </row>
    <row>
        <field>
            <id><spring:message code="e.mail.address"/></id>
            <msg><c:out value="${model.member.emailAddress}"/></msg>
        </field>
    </row>
    <row>
        <field>
            <id><spring:message code="extension"/></id>
            <msg><c:out value="${model.member.roomExtNumber}"/></msg>
        </field>
    </row>
    <row>
        <field>
            <id><spring:message code="group"/></id>
            <msg><c:out value="${model.member.groupName}"/></msg>
        </field>
    </row>
    <row>
        <field>
            <id><spring:message code="description"/></id>
            <msg><c:out value="${model.member.description}"/></msg>
        </field>
    </row>
     <row>
        <field>
            <id><spring:message code="proxy"/></id>
            <msg><c:out value="${model.member.proxyName}"/></msg>
        </field>
    </row>
    <row>
        <field>
            <id><spring:message code="location.tag"/></id>
            <msg><c:out value="${model.member.locationTag}"/></msg>
        </field>
    </row>
        <row>
        <field>
            <id><spring:message code="phoneNumber.1"/></id>
            <msg><c:out value="${model.member.phone1}"/></msg>
        </field>
    </row>
      <row>
        <field>
            <id><spring:message code="phoneNumber.2"/></id>
            <msg><c:out value="${model.member.phone2}"/></msg>
        </field>
    </row>
      <row>
        <field>
            <id><spring:message code="phoneNumber.3"/></id>
            <msg><c:out value="${model.member.phone3}"/></msg>
        </field>
    </row>
     <row>
        <field>
            <id><spring:message code="user.department"/></id>
            <msg><c:out value="${model.member.department}"/></msg>
        </field>
    </row>
      <row>
        <field>
            <id><spring:message code="user.title"/></id>
            <msg><c:out value="${model.member.title}"/></msg>
        </field>
    </row>
     <row>
        <field>
            <id><spring:message code="user.IM"/></id>
            <msg><c:out value="${model.member.instantMessagerID}"/></msg>
        </field>
    </row>
       <row>
        <field>
            <id><spring:message code="user.location"/></id>
            <msg><c:out value="${model.member.location}"/></msg>
        </field>
    </row>
      <row>
        <field>
            <id><spring:message code="thumbnail.photo"/></id>
             <key>thumbNail</key>
            <msg><c:out value="${model.thumbNailImage}"/></msg>
        </field>
    </row>
    <row>
        <field>
            <id><spring:message code="user.usergroups"/></id>
            <msg><c:out value="${model.member.userGroupsFromAuthProvider}"/></msg>
        </field>
    </row>
</message>
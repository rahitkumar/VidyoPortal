<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results><c:out value="${model.num}"/></results>
    <c:forEach items="${model.list}" var="ldap">
        <row>
            <mappingID><c:out value="${ldap.mappingID}"/></mappingID>
            <tenantID><c:out value="${ldap.tenantID}"/></tenantID>
            <vidyoAttributeName><c:out value="${ldap.vidyoAttributeName}"/></vidyoAttributeName>
            <vidyoAttributeDisplayName>
                <c:if test="${ldap.vidyoAttributeName == 'UserName'}">
                    <spring:message code="user.name"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'UserType'}">
                    <spring:message code="user.type"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'DisplayName'}">
                    <spring:message code="display.name"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'EmailAddress'}">
                    <spring:message code="e.mail.address"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'Extension'}">
                    <spring:message code="extension"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'Group'}">
                    <spring:message code="group"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'Description'}">
                    <spring:message code="description"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'Proxy'}">
                    <spring:message code="proxy"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'LocationTag'}">
                    <spring:message code="location.tag"/>
                </c:if>
                 <c:if test="${ldap.vidyoAttributeName == 'PhoneNumber1'}">
                    <spring:message code="phoneNumber.1"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'PhoneNumber2'}">
                    <spring:message code="phoneNumber.2"/>
                </c:if>
                  <c:if test="${ldap.vidyoAttributeName == 'PhoneNumber3'}">
                    <spring:message code="phoneNumber.3"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'Thumbnail Photo'}">
                    <spring:message code="thumbnail.photo"/>
                </c:if>
                  <c:if test="${ldap.vidyoAttributeName == 'Department'}">
                    <spring:message code="user.department"/>
                </c:if>
                  <c:if test="${ldap.vidyoAttributeName == 'Title'}">
                    <spring:message code="user.title"/>
                </c:if>
                  <c:if test="${ldap.vidyoAttributeName == 'IM'}">
                    <spring:message code="user.IM"/>
                </c:if>
                  <c:if test="${ldap.vidyoAttributeName == 'Location'}">
                    <spring:message code="user.location"/>
                </c:if>
                <c:if test="${ldap.vidyoAttributeName == 'User Groups'}">
                    <spring:message code="user.usergroups"/>
                </c:if>
            </vidyoAttributeDisplayName>
            <ldapAttributeName><c:out value="${ldap.ldapAttributeName}"/></ldapAttributeName>
            <defaultAttributeValue><c:out value="${ldap.defaultAttributeValue}"/></defaultAttributeValue>
            <c:if test="${ldap.vidyoAttributeName == 'UserName'}">
                <attrValueMapping/>
                <qtipAttrValueMapping/>
            </c:if>
            <c:if test="${ldap.vidyoAttributeName == 'UserType'}">
                <attrValueMapping>icon-value-map</attrValueMapping>
                <qtipAttrValueMapping>Click to edit attribute's value mapping</qtipAttrValueMapping>
            </c:if>
            <c:if test="${ldap.vidyoAttributeName == 'DisplayName'}">
                <attrValueMapping/>
                <qtipAttrValueMapping/>
            </c:if>
            <c:if test="${ldap.vidyoAttributeName == 'EmailAddress'}">
                <attrValueMapping/>
                <qtipAttrValueMapping/>
            </c:if>
            <c:if test="${ldap.vidyoAttributeName == 'Extension'}">
                <attrValueMapping/>
                <qtipAttrValueMapping/>
            </c:if>
            <c:if test="${ldap.vidyoAttributeName == 'Group'}">
                <attrValueMapping>icon-value-map</attrValueMapping>
                <qtipAttrValueMapping>Click to edit attribute's value mapping</qtipAttrValueMapping>
            </c:if>
            <c:if test="${ldap.vidyoAttributeName == 'Description'}">
                <attrValueMapping/>
                <qtipAttrValueMapping/>
            </c:if>
            <c:if test="${ldap.vidyoAttributeName == 'Proxy'}">
                <attrValueMapping>icon-value-map</attrValueMapping>
                <qtipAttrValueMapping>Click to edit attribute's value mapping</qtipAttrValueMapping>
            </c:if>
            <c:if test="${ldap.vidyoAttributeName == 'LocationTag'}">
                <attrValueMapping>icon-value-map</attrValueMapping>
                <qtipAttrValueMapping>Click to edit attribute's value mapping</qtipAttrValueMapping>
            </c:if>
        </row>
    </c:forEach>
</dataset>
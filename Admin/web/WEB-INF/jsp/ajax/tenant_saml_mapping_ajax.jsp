<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
    <results><c:out value="${model.num}"/></results>
    <c:forEach items="${model.list}" var="saml">
        <row>
            <mappingID><c:out value="${saml.mappingID}"/></mappingID>
            <tenantID><c:out value="${saml.tenantID}"/></tenantID>
            <vidyoAttributeName><c:out value="${saml.vidyoAttributeName}"/></vidyoAttributeName>
            <vidyoAttributeDisplayName>
                <c:if test="${saml.vidyoAttributeName == 'UserName'}">
                    <spring:message code="user.name"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'UserType'}">
                    <spring:message code="user.type"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'DisplayName'}">
                    <spring:message code="display.name"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'EmailAddress'}">
                    <spring:message code="e.mail.address"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'Extension'}">
                    <spring:message code="extension"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'Group'}">
                    <spring:message code="group"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'Description'}">
                    <spring:message code="description"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'Proxy'}">
                    <spring:message code="proxy"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'LocationTag'}">
                    <spring:message code="location.tag"/>
                </c:if>
                      <c:if test="${saml.vidyoAttributeName == 'PhoneNumber1'}">
                    <spring:message code="phoneNumber.1"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'PhoneNumber2'}">
                    <spring:message code="phoneNumber.2"/>
                </c:if>
                  <c:if test="${saml.vidyoAttributeName == 'PhoneNumber3'}">
                    <spring:message code="phoneNumber.3"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'Thumbnail Photo'}">
                    <spring:message code="thumbnail.photo"/>
                </c:if>
                  <c:if test="${saml.vidyoAttributeName == 'Department'}">
                    <spring:message code="user.department"/>
                </c:if>
                  <c:if test="${saml.vidyoAttributeName == 'Title'}">
                    <spring:message code="user.title"/>
                </c:if>
                  <c:if test="${saml.vidyoAttributeName == 'IM'}">
                    <spring:message code="user.IM"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'Location'}">
                    <spring:message code="user.location"/>
                </c:if>
                <c:if test="${saml.vidyoAttributeName == 'User Groups'}">
                    <spring:message code="user.usergroups"/>
                </c:if>
            </vidyoAttributeDisplayName>
            <idpAttributeName><c:out value="${saml.idpAttributeName}"/></idpAttributeName>
            <defaultAttributeValue><c:out value="${saml.defaultAttributeValue}"/></defaultAttributeValue>
            <c:if test="${saml.vidyoAttributeName == 'UserName'}">
                <attrValueMapping/>
                <qtipAttrValueMapping/>
            </c:if>
            <c:if test="${saml.vidyoAttributeName == 'UserType'}">
                <attrValueMapping>icon-value-map</attrValueMapping>
                <qtipAttrValueMapping>Click to edit attribute's value mapping</qtipAttrValueMapping>
            </c:if>
            <c:if test="${saml.vidyoAttributeName == 'DisplayName'}">
                <attrValueMapping/>
                <qtipAttrValueMapping/>
            </c:if>
            <c:if test="${saml.vidyoAttributeName == 'EmailAddress'}">
                <attrValueMapping/>
                <qtipAttrValueMapping/>
            </c:if>
            <c:if test="${saml.vidyoAttributeName == 'Extension'}">
                <attrValueMapping/>
                <qtipAttrValueMapping/>
            </c:if>
            <c:if test="${saml.vidyoAttributeName == 'Group'}">
                <attrValueMapping>icon-value-map</attrValueMapping>
                <qtipAttrValueMapping>Click to edit attribute's value mapping</qtipAttrValueMapping>
            </c:if>
            <c:if test="${ldap.vidyoAttributeName == 'Description'}">
                <attrValueMapping/>
                <qtipAttrValueMapping/>
            </c:if>
            <c:if test="${saml.vidyoAttributeName == 'Proxy'}">
                <attrValueMapping>icon-value-map</attrValueMapping>
                <qtipAttrValueMapping>Click to edit attribute's value mapping</qtipAttrValueMapping>
            </c:if>
            <c:if test="${saml.vidyoAttributeName == 'LocationTag'}">
                <attrValueMapping>icon-value-map</attrValueMapping>
                <qtipAttrValueMapping>Click to edit attribute's value mapping</qtipAttrValueMapping>
            </c:if>
        </row>
    </c:forEach>
</dataset>
<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results>1</results>
	<row>
		<memberID><c:out value="${model.member.memberID}"/></memberID>
		<roleID><c:out value="${model.member.roleID}"/></roleID>
		<roleName><c:out value="${model.member.roleName}"/></roleName>
		<tenantID><c:out value="${model.member.tenantID}"/></tenantID>
		<groupID><c:out value="${model.member.groupID}"/></groupID>
		<groupName><c:out value="${model.member.groupName}"/></groupName>
		<langID><c:out value="${model.member.langID}"/></langID>
		<profileID><c:out value="${model.member.profileID}"/></profileID>
		<username><c:out value="${model.member.username}"/></username>
		<memberName><c:out value="${model.member.memberName}"/></memberName>
		<enable><c:out value="${model.member.active}"/></enable>
		<allowedToParticipateHtml><c:out value="${model.member.allowedToParticipate}"/></allowedToParticipateHtml>
		<emailAddress><c:out value="${model.member.emailAddress}"/></emailAddress>
		<memberCreated><c:out value="${model.member.memberCreated}"/></memberCreated>
		<location><c:out value="${model.member.location}"/></location>
		<description><c:out value="${model.member.description}"/></description>
		<roomID><c:out value="${model.member.roomID}"/></roomID>
		<roomTypeID><c:out value="${model.member.roomTypeID}"/></roomTypeID>
		<roomType><c:out value="${model.member.roomType}"/></roomType>
		<roomName><c:out value="${model.member.roomName}"/></roomName>
		<roomExtNumber><c:out value="${model.member.roomExtNumber}"/></roomExtNumber>
		<tenantPrefix><c:out value="${model.member.tenantPrefix}"/></tenantPrefix>
		<tenantName><c:out value="${model.member.tenantName}"/></tenantName>
		<proxyID><c:out value="${model.member.proxyID}"/></proxyID>
		<locationID><c:out value="${model.member.locationID}"/></locationID>
        <importedUsed><c:out value="${model.member.importedUsed}"/></importedUsed>
        <ldap><c:out value="${model.ldap}"/></ldap>
        <phone1><c:out value="${model.member.phone1}"/></phone1>
        <phone2><c:out value="${model.member.phone2}"/></phone2>
        <phone3><c:out value="${model.member.phone3}"/></phone3>
        <title><c:out value="${model.member.title}"/></title>
        <department><c:out value="${model.member.department}"/></department>
        <instantMessagerID><c:out value="${model.member.instantMessagerID}"/></instantMessagerID>
        <thumbNailImage><c:out value="${model.thumbNailImage}"/></thumbNailImage>
        <hideImageDelete><c:out value="${model.hideImageDelete}"/></hideImageDelete>
        <userImageAllowed><c:out value="${model.member.userImageAllowed}"/></userImageAllowed>
        <isUsrImgEnbledAdmin><c:out value="${model.isUsrImgEnbledAdmin}"/></isUsrImgEnbledAdmin>
        <isUsrImgUpldEnbledAdm><c:out value="${model.isUsrImgUpldEnbledAdm}"/></isUsrImgUpldEnbledAdm>
        <neoRoomPermanentPairingDeviceUser><c:out value="${model.neoRoomPermanentPairingDeviceUser}"/></neoRoomPermanentPairingDeviceUser>
    </row>
</dataset>
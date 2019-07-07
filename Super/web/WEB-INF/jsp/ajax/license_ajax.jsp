<?xml version="1.0" encoding="UTF-8"?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="include_ajax.jsp" %>

<dataset>
	<results><c:out value="${model.num}"/></results>
	<row>
         <c:if test="${not empty model.FQDN}">
            <FQDN><c:out value="${model.FQDN.licensedValue}"/></FQDN>
        </c:if>
		<c:if test="${model.Version.licensedValue == '20'}">
			<feature><spring:message code="SuperSystemLicense.port.start.date"/></feature>
		</c:if>
		<c:if test="${model.Version.licensedValue == '21' || model.Version.licensedValue == '22'}">
			<feature><spring:message code="license.line.start.date"/></feature>
		</c:if>
		<license><c:out value="${model.StartDate.licensedValue}"/></license>
		<inuse><c:out value="${model.StartDate.currentValue}"/></inuse>
	</row>
	<row>
		<c:if test="${model.Version.licensedValue == '20'}">
			<feature><spring:message code="SuperSystemLicense.port.expiry.date"/></feature>
		</c:if>
		<c:if test="${model.Version.licensedValue == '21' || model.Version.licensedValue == '22'}">
			<feature><spring:message code="license.line.expiration.date"/></feature>
		</c:if>
		<license><c:out value="${model.ExpiryDate.licensedValue}"/></license>
		<inuse><c:out value="${model.ExpiryDate.currentValue}"/></inuse>
	</row>

	<c:if test="${model.Version.licensedValue == '20'}">
		<row>
			<feature><spring:message code="SuperSystemLicense.seat.start.date"/></feature>
			<license><c:out value="${model.SeatStartDate.licensedValue}"/></license>
			<inuse><c:out value="${model.SeatStartDate.currentValue}"/></inuse>
		</row>
		<row>
			<feature><spring:message code="SuperSystemLicense.seat.expiry.date"/></feature>
			<license><c:out value="${model.SeatExpiry.licensedValue}"/></license>
			<inuse><c:out value="${model.SeatExpiry.currentValue}"/></inuse>
		</row>
	</c:if>

	<c:if test="${not empty model.EventLicenseExpiry}">
		<row>
			<feature/>
			<license/>
			<inuse/>
		</row>
		<row>
			<feature><spring:message code="event.license.ports"/></feature>
			<license><c:out value="${model.EventLicensePorts.licensedValue}"/></license>
			<inuse><c:out value="${model.EventLicensePorts.currentValue}"/></inuse>
		</row>
		<row>
			<feature><spring:message code="event.license.expiry.date"/></feature>
			<license><c:out value="${model.EventLicenseExpiry.licensedValue}"/></license>
			<inuse><c:out value="${model.EventLicenseExpiry.currentValue}"/></inuse>
		</row>
	</c:if>

	<row>
		<feature/>
		<license/>
		<inuse/>
	</row>
	<row>
		<feature><spring:message code="SuperSystemLicense.license.key"/></feature>
		<license><c:out value="${model.VMSoapUser.licensedValue}"/></license>
		<inuse><c:out value="${model.VMSoapUser.currentValue}"/></inuse>
	</row>
	<row>
		<feature><spring:message code="SuperSystemLicense.license.token"/></feature>
		<license><c:out value="${model.VMSoapPass.licensedValue}"/></license>
		<inuse><c:out value="${model.VMSoapPass.currentValue}"/></inuse>
	</row>

	<row>
		<feature/>
		<license/>
		<inuse/>
	</row>
	<row>
		<feature><spring:message code="SuperSystemLicense.num.of.seats"/></feature>
		<license><c:out value="${model.Seats.currentValue}"/>/<c:out value="${model.Seats.licensedValue}"/> ( <spring:message code="SuperSystemLicense.used.licensed"/> )</license>
		<inuse><c:out value="${model.Seats.currentValue}"/></inuse>
	</row>
	<row>
		<c:if test="${model.Version.licensedValue == '20'}">
			<feature><spring:message code="SuperSystemLicense.num.of.ports"/></feature>
		</c:if>
		<c:if test="${model.Version.licensedValue == '21'}">
			<feature><spring:message code="SuperSystemLicense.number.of.lines"/></feature>
		</c:if>
		<c:if test="${model.Version.licensedValue == '22'}">
			<feature><spring:message code="SuperSystemLicense.number.of.lines"/> (UVL)</feature>
		</c:if>
		<license><c:out value="${model.Ports.currentValue}"/>/<c:out value="${model.Ports.licensedValue}"/> ( <spring:message code="SuperSystemLicense.used.licensed"/> )</license>
		<inuse><c:out value="${model.Ports.currentValue}"/></inuse>
	</row>
	<row>
		<feature><spring:message code="SuperSystemLicense.num.of.installs"/></feature>
		<license><c:out value="${model.Installs.currentValue}"/>/<c:out value="${model.Installs.licensedValue}"/> ( <spring:message code="SuperSystemLicense.used.licensed"/> )</license>
		<inuse><c:out value="${model.Installs.currentValue}"/></inuse>
	</row>

	<c:if test="${model.Version.licensedValue == '21' || model.Version.licensedValue == '22'}">
		<row>
			<feature><spring:message code="SuperSystemLicense.number.of.executive.systems"/></feature>
			<license><c:out value="${model.LimitTypeExecutiveSystem.currentValue}"/>/<c:out value="${model.LimitTypeExecutiveSystem.licensedValue}"/> ( <spring:message code="SuperSystemLicense.used.licensed"/> )</license>
			<inuse><c:out value="${model.LimitTypeExecutiveSystem.currentValue}"/></inuse>
		</row>
		<row>
			<feature><spring:message code="number.of.panorama.systems"/></feature>
			<license><c:out value="${model.LimitTypePanoramaSystem.currentValue}"/>/<c:out value="${model.LimitTypePanoramaSystem.licensedValue}"/> ( <spring:message code="SuperSystemLicense.used.licensed"/> )</license>
			<inuse><c:out value="${model.LimitTypePanoramaSystem.currentValue}"/></inuse>
		</row>
	</c:if>

	<c:if test="${model.AllowUserAPIs.licensedValue == 'true' || model.AllowPortalAPIs.licensedValue == 'true' || model.AllowExtDB.licensedValue == 'true' || model.BackupLic.licensedValue == 'true'}">
		<row>
			<feature/>
			<license/>
			<inuse/>
		</row>
		<c:if test="${model.AllowUserAPIs.licensedValue == 'true'}">
		<row>
			<feature><spring:message code="SuperSystemLicense.user.api.access"/></feature>
			<c:if test="${model.AllowUserAPIs.licensedValue == 'true'}">
				<license><spring:message code="SuperSystemLicense.enable"/></license>
			</c:if>
			<c:if test="${model.AllowUserAPIs.licensedValue == 'false'}">
				<license><spring:message code="SuperSystemLicense.disable"/></license>
			</c:if>
			<inuse><c:out value="${model.AllowUserAPIs.currentValue}"/></inuse>
		</row>
		</c:if>
		<c:if test="${model.AllowPortalAPIs.licensedValue == 'true'}">
			<row>
				<feature><spring:message code="SuperSystemLicense.admin.api.access"/></feature>
				<c:if test="${model.AllowPortalAPIs.licensedValue == 'true'}">
					<license><spring:message code="SuperSystemLicense.enable"/></license>
				</c:if>
				<c:if test="${model.AllowPortalAPIs.licensedValue == 'false'}">
					<license><spring:message code="SuperSystemLicense.disable"/></license>
				</c:if>
				<inuse><c:out value="${model.AllowPortalAPIs.currentValue}"/></inuse>
			</row>
		</c:if>
		<c:if test="${model.AllowExtDB.licensedValue == 'true'}">
			<row>
				<feature><spring:message code="allow.external.database"/></feature>
				<c:if test="${model.AllowExtDB.licensedValue == 'true'}">
					<license><spring:message code="SuperSystemLicense.enable"/></license>
				</c:if>
				<c:if test="${model.AllowExtDB.licensedValue == 'false'}">
					<license><spring:message code="SuperSystemLicense.disable"/></license>
				</c:if>
				<inuse><c:out value="${model.AllowExtDB.currentValue}"/></inuse>
			</row>
		</c:if>

        <c:if test="${model.BackupLic.licensedValue == 'true'}">
            <row>
                <feature><spring:message code="highavail.label"/></feature>
                <c:if test="${model.BackupLic.licensedValue == 'true'}">
                    <license><spring:message code="SuperSystemLicense.enable"/></license>
                </c:if>
                <c:if test="${model.BackupLic.licensedValue == 'false'}">
                    <license><spring:message code="SuperSystemLicense.disable"/></license>
                </c:if>
                <inuse><c:out value="${model.BackupLic.currentValue}"/></inuse>
            </row>
        </c:if>
	</c:if>

	<row>
		<feature/>
		<license/>
		<inuse/>
	</row>
	<row>
		<feature><spring:message code="SuperSystemLicense.encryption"/></feature>
		<c:if test="${model.Encryption.licensedValue == '128bits'}">
			<license><spring:message code="SuperSystemLicense.128bits"/></license>
		</c:if>
		<c:if test="${model.Encryption.licensedValue != '128bits'}">
			<license><spring:message code="SuperSystemLicense.none"/></license>
		</c:if>
		<inuse><c:out value="${model.Encryption.currentValue}"/></inuse>
	</row>
	<row>
		<feature><spring:message code="SuperSystemLicense.mt"/></feature>
		<c:if test="${model.MultiTenant.licensedValue == 'true'}">
			<license><spring:message code="SuperSystemLicense.enable"/></license>
		</c:if>
		<c:if test="${model.MultiTenant.licensedValue == 'false'}">
			<license><spring:message code="SuperSystemLicense.disable"/></license>
		</c:if>
		<inuse><c:out value="${model.MultiTenant.currentValue}"/></inuse>
	</row>
	<row>
		<feature><spring:message code="allow.uc.clients"/></feature>
		<c:if test="${model.AllowOCS.licensedValue == 'true'}">
			<license><spring:message code="SuperSystemLicense.enable"/></license>
		</c:if>
		<c:if test="${model.AllowOCS.licensedValue == 'false'}">
			<license><spring:message code="SuperSystemLicense.disable"/></license>
		</c:if>
		<inuse><c:out value="${model.AllowOCS.currentValue}"/></inuse>
	</row>

	<row>
		<feature/>
		<license/>
		<inuse/>
	</row>
	<row>
		<feature><spring:message code="SuperSystemLicense.sn"/></feature>
		<license><c:out value="${model.SN.licensedValue}"/></license>
		<inuse><c:out value="${model.SN.currentValue}"/></inuse>
	</row>

</dataset>
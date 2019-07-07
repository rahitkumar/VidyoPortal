<?xml version="1.0" encoding="UTF-8"
?>

<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<%@ include file="../ajax/include_ajax.jsp" %>

<dataset>
    <result>
        1
    </result>
    <row>
        <c:forEach items="${model.tenantIpcDetails}" var="tenantIpcDetail">
            <tenantIpcDetail>
                <tenantId>
                    <c:out value="${tenantIpcDetail.tenantID}"/>
                </tenantId>
                <ipcId>
                    <c:out value="${tenantIpcDetail.ipcID}"/>
                    </ipcId>
                        <HostName>
                            <c:out value="${tenantIpcDetail.hostName}"/>
                        </HostName>
                        <IpcWhiteListId>
                            <c:out value="${tenantIpcDetail.ipcWhiteListId}"/>
                        </IpcWhiteListId>
                        <WhiteList>
                            <c:out value="${tenantIpcDetail.whiteList}"/>
                        </WhiteList>
            </tenantIpcDetail>
    	</c:forEach>
	</row>
</dataset>
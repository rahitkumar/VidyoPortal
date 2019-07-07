<?xml version="1.0" encoding="UTF-8"
?>
<%@ include file="../ajax/include_ajax.jsp" %>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<dataset>
    <results>
        1
    </results>
    <row>
        <portalversion>
            <c:out value="${model.portalversion}"/>
        </portalversion>
        <allowUpgrade>
            <c:out value="${model.allowUpgrade}"/>
        </allowUpgrade>
        <backupLic>
            <c:out value="${model.backupLic}"/>
        </backupLic>
        <peerStatus>
            <c:out value="${model.peerStatus}"/>
        </peerStatus>
         <pkiNotification>
            <c:out value="${model.pkiNotification}"/>
        </pkiNotification>
        <pkiCertReviewPending>
             <c:out value="${model.pkiCertReviewPending}"/>
        </pkiCertReviewPending>
        
    </row>
</dataset>
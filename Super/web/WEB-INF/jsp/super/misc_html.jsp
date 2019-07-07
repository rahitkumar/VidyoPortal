<?xml version="1.0" encoding="UTF-8"?>
<%@ include file="../ajax/include_ajax.jsp" %>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<dataset>
    <results>1</results>
    
    <row>
    <c:if test="${not empty param.settingstype && param.settingstype == 'vidyoweb'}">
        <vidyoweb>
            <vidyoWebVersion><c:out value="${model.vidyoWebVersion}"/></vidyoWebVersion>
            <vidyoWebAvailable><c:out value="${model.vidyoWebAvailable}"/></vidyoWebAvailable>
            <vidyoWebEnabled><c:out value="${model.vidyoWebEnabled}"/></vidyoWebEnabled>
            <vidyoWebAvailableTitle><spring:message javaScriptEscape="true" code="vidyoweb.super.available"/></vidyoWebAvailableTitle>
            <vidyoWebAvailableDesc><spring:message javaScriptEscape="true" code="vidyoweb.super.available.desc"/></vidyoWebAvailableDesc>
            <vidyoWebEnabledTitle><spring:message javaScriptEscape="true" code="vidyoweb.super.enable"/></vidyoWebEnabledTitle>
            <vidyoWebEnabledDesc><spring:message javaScriptEscape="true" code="vidyoweb.super.enable.desc"/></vidyoWebEnabledDesc>
        </vidyoweb>
    </c:if>
    <c:if test="${not empty param.settingstype && param.settingstype == 'vidyomobile'}">
        <vidyomobile>
            <mobileLogin><c:out value="${model.mobileLogin}"/></mobileLogin>
            <vidyoMobileTitle><spring:message code="mobiaccess.default.tenant.settings.label"/></vidyoMobileTitle>
            <vidyoMobileSaveConfirmBoxTitle><spring:message code="mobiaccess.confirm.messagebox.title"/></vidyoMobileSaveConfirmBoxTitle>
            <vidyoMobileSaveConfirmBoxDesc1><spring:message code="mobiaccess.confirm.messagebox.cfmmsg1"/></vidyoMobileSaveConfirmBoxDesc1>
            <vidyoMobileSaveConfirmBoxDesc2><spring:message code="mobiaccess.confirm.messagebox.cfmmsg2"/></vidyoMobileSaveConfirmBoxDesc2>
        </vidyomobile>
    </c:if>
    <c:if test="${not empty param.settingstype && param.settingstype == 'vidyodesktop'}">
        <VidyoDesktop>
            <tiles16Available><c:out value="${model.tiles16Available}"/></tiles16Available>
        </VidyoDesktop>
    </c:if>
    <c:if test="${not empty param.settingstype && param.settingstype == 'searchoptions'}">
        <searchOptions>
            <showDisabledRoomsEnabled><c:out value="${model.showDisabledRoomsEnabled}"/></showDisabledRoomsEnabled>
        </searchOptions>
    </c:if>
    <c:if test="${not empty param.settingstype && param.settingstype == 'ipc'}">
        <ipc>
            <AccessControl>
                <AccessControlFieldLabel><spring:message code="ipc.access.control.label"/></AccessControlFieldLabel>
                <AccessControlAdminBoxLabel><spring:message code="ipc.access.control.label.admin"/></AccessControlAdminBoxLabel>
                <AccessControlSuperBoxLabel><spring:message code="ipc.access.control.label.super"/></AccessControlSuperBoxLabel>
            </AccessControl>
            <superManaged><c:out value="${model.superManaged}"/></superManaged>
            <adminManaged><c:out value="${model.adminManaged}"/></adminManaged>
             <accessControlMode><c:out value="${model.accessControlMode}"/></accessControlMode>
              <routerPool><c:out value="${model.routerPool}"/></routerPool>
            <AdminControl>
                <AdminFieldLabel><spring:message code="ipc.allow.block.list.label"/></AdminFieldLabel>
                <AdminAllowBoxLabel><spring:message code="ipc.allowed.list.label"/></AdminAllowBoxLabel>
                <AdminBlockBoxLabel><spring:message code="ipc.blocked.list.label"/></AdminBlockBoxLabel>
            </AdminControl>
            <DomainList>
                <DomainGridTitle><spring:message code="ipc.grid.title.label"/></DomainGridTitle>
                <DomainGridAllowTitle><spring:message code="ipc.grid.title.allow.label"/></DomainGridAllowTitle>
                <DomainGridBlockTitle><spring:message code="ipc.grid.title.block.label"/></DomainGridBlockTitle>
                <DomainGridDuplicateAlertTitle></DomainGridDuplicateAlertTitle>
                <DomainGridDuplicateAlertDescription></DomainGridDuplicateAlertDescription>
                <DomainGridAdd>
                    <AddConfirmBoxTitle><spring:message code="ipc.msgbox.addaddress.title"/></AddConfirmBoxTitle>
                    <AddConfirmBoxMessage><spring:message code="ipc.msgbox.prompt.message"/></AddConfirmBoxMessage>
                </DomainGridAdd>
                <DomainGridDelete>
                    <DeleteConfirmBoxTitle><spring:message code="ipc.msgbox.delete.confirm.title"/></DeleteConfirmBoxTitle>
                    <DeleteConfirmBoxMessage><spring:message code="ipc.msgbox.delete.confirm.messg"/></DeleteConfirmBoxMessage>
                </DomainGridDelete>
                <DomainGridError>
                    <ErrorConfirmBoxTitle><spring:message code="ipc.msgbox.delete.error"/></ErrorConfirmBoxTitle>
                    <ErrorConfirmBoxMessage><spring:message code="ipc.msgbox.delete.msg"/></ErrorConfirmBoxMessage>
                </DomainGridError>
                
            </DomainList>
            <guideLoc><c:out value="${model.guideLoc}"/></guideLoc>
            
        </ipc>
    </c:if>
    <c:if test="${not empty param.settingstype && param.settingstype == 'tls'}">
        <tls>
            <tlsProxyLabel><spring:message code="super.misc.tls.tab.form.field.tls.label"/></tlsProxyLabel>
            <tlsProxyFeatureEnabled><c:out value="${model.tlsProxyFeatureEnabled}"/></tlsProxyFeatureEnabled>
            <tlsProxyEnabled><c:out value="${model.tlsProxyEnabled}"/></tlsProxyEnabled>
            <tlsProxySaveAlertBoxMessage><spring:message javaScriptEscape="true" code="super.misc.tls.tab.form.save.confirm.msg"/></tlsProxySaveAlertBoxMessage>
        </tls>
    </c:if>
    <c:if test="${not empty param.settingstype && param.settingstype == 'gmail'}">
        <gmailPlugin>
            <gmailPluginVersion><c:out value="${model.gmailPluginVersion}"/></gmailPluginVersion>
            <gmailPluginInstalled><c:out value="${model.gmailPluginInstalled}"/></gmailPluginInstalled>
            <betaFeatureEnabled><c:out value="${model.betaFeatureEnabled}"/></betaFeatureEnabled>
            <gmailPluginDeleteLabel><spring:message javaScriptEscape="true" code="delete.gmail.plug.in"/></gmailPluginDeleteLabel>
            <gmailPluginSaveMsgBoxDesc><spring:message javaScriptEscape="true" code="deleted.gmail.plug.in"/></gmailPluginSaveMsgBoxDesc>
        </gmailPlugin>
    </c:if>
    <c:if test="${not empty param.settingstype && param.settingstype == 'chat'}">
         <chat>
            <chatVidyoPortalLabel><spring:message javaScriptEscape="true" code="chat.super.available"/></chatVidyoPortalLabel>
            <chatVidyoPortalLabelDesc><spring:message javaScriptEscape="true" code="chat.super.available.desc"/></chatVidyoPortalLabelDesc>
            <chatPublicDefaultLabel><spring:message javaScriptEscape="true" code="public.chat.default.option"/></chatPublicDefaultLabel>
            <chatPublicDefaultLabelDesc><spring:message javaScriptEscape="true" code="public.chat.default.option.desc"/></chatPublicDefaultLabelDesc>
            <chatPrivateDefaultLabel><spring:message javaScriptEscape="true" code="private.chat.default.option"/></chatPrivateDefaultLabel>
            <chatPrivateDefaultLabelDesc><spring:message javaScriptEscape="true" code="private.chat.default.option.desc"/></chatPrivateDefaultLabelDesc>
            <chatVidyoPortalAvailable><c:out value="${model.chatAvailable}"/></chatVidyoPortalAvailable>
            <chatDefaultPublicStatus><c:out value="${model.dafaultPublicChatEnabled}"/></chatDefaultPublicStatus>
            <chatDefaultPrivateStatus><c:out value="${model.defaultPrivateChatEnabled}"/></chatDefaultPrivateStatus>
        </chat>
    </c:if>
        
    </row>
</dataset>
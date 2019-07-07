Ext.define('SuperApp.model.settings.IPC',{
    extend :'Ext.data.Model',
     
    fields :[{
        name :'AccessControlFieldLabel',
        mapping :'ipc > AccessControl > AccessControlFieldLabel'
    },{
        name :'AccessControlAdminBoxLabel',
        mapping :'ipc > AccessControl AccessControlAdminBoxLabel'
    },{
        name :'AccessControlSuperBoxLabel',
        mapping :'ipc > AccessControl > AccessControlSuperBoxLabel'
    },{
        name :'superManaged',
        mapping :'ipc > superManaged',
        type :'boolean'
    }
    ,{
        name :'accessControlMode',
        mapping :'ipc > accessControlMode',
        type :'boolean'
    }
    ,{
        name :'routerPool',
        mapping :'ipc > routerPool'
       
    }
    
    ,{
        name :'adminManaged',
        mapping :'ipc > adminManaged',
        type :'boolean'
    },{
        name :'AdminFieldLabel',
        mapping :'ipc > AdminControl > AdminFieldLabel'
    },{
        name :'AdminAllowBoxLabel',
        mapping :'ipc > AdminControl > AdminAllowBoxLabel'
    },{
        name :'AdminBlockBoxLabel',
        mapping :'ipc > AdminControl > AdminBlockBoxLabel'
    },{
        name :'DomainGridTitle',
        mapping :'ipc > DomainList > DomainGridTitle'
    },{
        name :'DomainGridAllowTitle',
        mapping :'ipc > DomainList > DomainGridAllowTitle'
    },{
        name :'DomainGridBlockTitle',
        mapping :'ipc > DomainList > DomainGridBlockTitle'
    },{
        name :'DomainGridDuplicateAlertTitle',
        mapping :'ipc > DomainList > DomainGridDuplicateAlertTitle'
    },{
        name :'DomainGridDuplicateAlertDescription',
        mapping :'ipc > DomainList > DomainGridDuplicateAlertDescription'
    },{
        name :'AddConfirmBoxTitle',
        mapping :'ipc > DomainList > DomainGridAdd > AddConfirmBoxTitle'
    },{
        name :'AddConfirmBoxMessage',
        mapping :'ipc > DomainList > DomainGridAdd > AddConfirmBoxMessage'
    },{
        name :'ErrorConfirmBoxTitle',
        mapping :'ipc > DomainList > DomainGridError > ErrorConfirmBoxTitle'
    },{
        name :'ErrorConfirmBoxMessage',
        mapping :'ipc > DomainList > DomainGridError > ErrorConfirmBoxMessage'
    },{
        name :'DeleteConfirmBoxTitle',
        mapping :'ipc > DomainList > DomainGridDelete > DeleteConfirmBoxTitle'
    },{
        name :'DeleteConfirmBoxMessage',
        mapping :'ipc > DomainList > DomainGridDelete > DeleteConfirmBoxMessage'
    },{
        name :'guideLoc',
        mapping :'ipc > guideLoc'
    },{
        name :'tlsProxyEnabled',
        mapping :'ipc > tlsProxyEnabled'
    }]
});

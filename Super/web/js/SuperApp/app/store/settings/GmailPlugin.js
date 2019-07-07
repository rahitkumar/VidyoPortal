Ext.define('SuperApp.store.settings.GmailPlugin',{
    extend :'Ext.data.Store',
    storeId :'gmailplugin',
    requires :['SuperApp.model.settings.GmailPlugin'],
    
    model :'SuperApp.model.settings.GmailPlugin',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'misc.html?settingstype=gmail',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});
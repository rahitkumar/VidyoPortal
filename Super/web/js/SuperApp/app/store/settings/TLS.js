Ext.define('SuperApp.store.settings.TLS',{
    extend :'Ext.data.Store',
    storeId :'tls',
    requires :['SuperApp.model.settings.TLS'],
    
    model :'SuperApp.model.settings.TLS',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'misc.html?settingstype=tls',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});
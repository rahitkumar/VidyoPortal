Ext.define('SuperApp.store.settings.PortalDomain',{
    extend :'Ext.data.Store',
    storeId :'portaldomain',
    requires :['SuperApp.model.settings.PortalDomain'],
    
    model :'SuperApp.model.settings.PortalDomain',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'ipcportaldomains.ajax',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});

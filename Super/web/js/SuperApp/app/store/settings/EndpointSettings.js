Ext.define('SuperApp.store.settings.EndpointSettings',{
    extend :'Ext.data.Store',
    storeId :'endpointsettings',
    requires :['SuperApp.model.settings.EndpointSettings'],
    
    model :'SuperApp.model.settings.EndpointSettings',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'getEndpointSettings.ajax',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});
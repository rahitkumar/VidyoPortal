Ext.define('SuperApp.store.settings.EnableEpicIntegration',{
    extend :'Ext.data.Store',
    storeId :'enableEpicIntegration',
    requires :['SuperApp.model.settings.EnableEpicIntegration'],
    
    model :'SuperApp.model.settings.EnableEpicIntegration',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'epicintegration.html',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
});
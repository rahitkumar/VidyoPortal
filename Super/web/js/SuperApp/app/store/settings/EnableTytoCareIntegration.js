Ext.define('SuperApp.store.settings.EnableTytoCareIntegration',{
    extend :'Ext.data.Store',
    storeId :'enableTytoCareIntegration',
    requires :['SuperApp.model.settings.EnableTytoCareIntegration'],
    
    model :'SuperApp.model.settings.EnableTytoCareIntegration',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'tytocareintegration.html',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
});
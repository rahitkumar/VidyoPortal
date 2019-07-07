Ext.define('SuperApp.store.settings.RouterPools',{
    extend :'Ext.data.Store',
    storeId :'routerpools',
    requires :['SuperApp.model.settings.RouterPools'],
    
    model :'SuperApp.model.settings.RouterPools',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'routerpools.ajax',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});

Ext.define('SuperApp.store.settings.Chat',{
    extend :'Ext.data.Store',
    storeId :'chat',
    requires :['SuperApp.model.settings.Chat'],
    
    model :'SuperApp.model.settings.Chat',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'misc.html?settingstype=chat',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});
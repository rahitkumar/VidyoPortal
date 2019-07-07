Ext.define('SuperApp.store.settings.SearchOptions',{
    extend :'Ext.data.Store',
    storeId :'searchoptions',
    requires :['SuperApp.model.settings.SearchOptions'],
    
    model :'SuperApp.model.settings.SearchOptions',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'misc.html?settingstype=searchoptions',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});

Ext.define('SuperApp.store.settings.VidyoWeb',{
    extend :'Ext.data.Store',
    storeId :'vidyoweb',
    requires :['SuperApp.model.settings.VidyoWeb'],
    
    model :'SuperApp.model.settings.VidyoWeb',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'misc.html?settingstype=vidyoweb',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});

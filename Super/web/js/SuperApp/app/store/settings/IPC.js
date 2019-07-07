Ext.define('SuperApp.store.settings.IPC',{
    extend :'Ext.data.Store',
    storeId :'ipc',
    requires :['SuperApp.model.settings.IPC'],
    
    model :'SuperApp.model.settings.IPC',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'misc.html?settingstype=ipc',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});
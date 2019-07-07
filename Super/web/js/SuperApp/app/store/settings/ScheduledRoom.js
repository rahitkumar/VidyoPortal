Ext.define('SuperApp.store.settings.ScheduledRoom',{
    extend :'Ext.data.Store',
    storeId :'scheduledroom',
    requires :['SuperApp.model.settings.ScheduledRoom'],
    
    model :'SuperApp.model.settings.ScheduledRoom',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'scheduledroom.html',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});
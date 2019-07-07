Ext.define('SuperApp.store.settings.RoomLink',{
    extend :'Ext.data.Store',
    requires :['SuperApp.model.settings.RoomLink'],
    
    model :'SuperApp.model.settings.RoomLink',
    
    proxy: {
        type: 'ajax',
        url:'roomlinksetting.ajax',
        actionMethods:  {create: "POST", read: "POST", update: "POST", destroy: "POST"},
        reader: {
            type: 'xml',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});

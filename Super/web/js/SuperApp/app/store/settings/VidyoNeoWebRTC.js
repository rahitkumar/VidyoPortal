Ext.define('SuperApp.store.settings.VidyoNeoWebRTC',{
    extend :'Ext.data.Store',
    requires :['SuperApp.model.settings.VidyoNeoWebRTC'],
    
    model :'SuperApp.model.settings.VidyoNeoWebRTC',
    
    proxy: {
        type: 'ajax',
        url:'vidyoneowebrtcsetting.ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        reader: {
            type: 'xml',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});
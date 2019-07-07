Ext.define('SuperApp.store.settings.VidyoDesktop',{
    extend :'Ext.data.Store',
    storeId :'vidyodesktop',
    requires :['SuperApp.model.settings.VidyoDesktop'],
    
    model :'SuperApp.model.settings.VidyoDesktop',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'misc.html?settingstype=vidyodesktop',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});

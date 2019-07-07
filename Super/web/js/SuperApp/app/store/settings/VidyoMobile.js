Ext.define('SuperApp.store.settings.VidyoMobile',{
    extend :'Ext.data.Store',
    storeId :'vidyomobile',
    requires :['SuperApp.model.settings.VidyoMobile'],
    
    model :'SuperApp.model.settings.VidyoMobile',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'misc.html?settingstype=vidyomobile',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});

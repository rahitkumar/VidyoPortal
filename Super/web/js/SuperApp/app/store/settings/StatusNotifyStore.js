Ext.define('SuperApp.store.settings.StatusNotifyStore',{
    extend :'Ext.data.Store',
    storeId :'statusNotifyStore',
    requires :['SuperApp.view.settings.maintenance.StatusNotify'],
    
    model :'SuperApp.model.settings.StatusNotifyModel',
    
    proxy :{
        type :'ajax',
        actionMethods : {read: "GET"},
        url :'securedmaint/statusnotification.ajax',
        reader : {
            type : 'xml',
            totalRecords : 'results',
            record : 'row',
            rootProperty :'dataset'
        }
    }
    
});
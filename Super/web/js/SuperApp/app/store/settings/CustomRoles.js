Ext.define('SuperApp.store.settings.CustomRoles',{
    extend :'Ext.data.Store',
    storeId :'customRoles',
    requires :['SuperApp.model.settings.CustomRole'],
    
    model :'SuperApp.model.settings.CustomRole',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'customrole.html',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});
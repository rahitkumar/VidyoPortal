Ext.define('SuperApp.store.settings.UserAttributes',{
    extend :'Ext.data.Store',
    storeId :'userAttributes',
    requires :['SuperApp.model.settings.UserAttribute'],
    
    model :'SuperApp.model.settings.UserAttribute',
    
    proxy :{
        type :'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url :'userattribute.html',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
    
});
Ext.define('AdminApp.model.settings.EpicIntegrationModel',{
    extend :'Ext.data.Model',
     
    fields :[{
            name :'enableEpicIntegration',
            type : 'integer'
    },{
        name :'sharedSecret',
        type : 'string'
    },{
        name :'notificationUrl',
        type : 'string'
    },{
        name :'notificationUser',
        type : 'string'
    },{
        name :'notificationPassword',
        type : 'string'
    }]
});

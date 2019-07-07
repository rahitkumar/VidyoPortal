Ext.define('AdminApp.model.settings.TytoCareIntegrationModel',{
    extend :'Ext.data.Model',
     
    fields :[{
            name :'enableTytoCareIntegration',
            type : 'integer'
    },{
        name :'tytoUrl',
        type : 'string'
    },{
        name :'tytoUsername',
        type : 'string'
    },{
        name :'tytoPassword',
        type : 'string'
    }]
});

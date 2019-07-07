Ext.define('SuperApp.model.settings.StatusNotifyModel',{
    extend :'Ext.data.Model',
     
    fields :[{
        name :'flag',
        type :'boolean'
    },{
        name :'url',
        type : 'string'
    },{
        name :'username',
        type : 'string'
    },{
        name :'password',
        type : 'string'
    }]
});

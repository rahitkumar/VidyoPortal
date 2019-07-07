Ext.define('SuperApp.model.settings.SysLogModel',{
    extend :'Ext.data.Model',
     
    fields :[{
        name :'remote_logging',
        type :'string'
    },{
        name :'stunnel',
        type : 'string'
    },{
        name :'protocol',
        type : 'string'
    },{
        name :'ip_address',
        type : 'string'
    },{
        name :'port',
        type : 'string'
    }]
});

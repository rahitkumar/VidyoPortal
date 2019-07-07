Ext.define('SuperApp.model.settings.VidyoWeb',{
    extend :'Ext.data.Model',
     
    fields :[{
        name : 'vidyoWebVersion',
        mapping : 'vidyoweb > vidyoWebVersion'
    }, {
        name : 'vidyoWebAvailable',
        mapping : 'vidyoweb > vidyoWebAvailable',
        type : 'boolean'
    }, {
        name :'vidyoWebAvailableTitle',
        mapping :'vidyoweb > vidyoWebAvailableTitle'
    },{
        name :'vidyoWebAvailableDesc',
        mapping :'vidyoweb > vidyoWebAvailableDesc'
    },{
        name :'vidyoWebEnabledTitle',
        mapping :'vidyoweb > vidyoWebEnabledTitle'
    },{
        name :'vidyoWebEnabledDesc',
        mapping :'vidyoweb > vidyoWebEnabledDesc'
    },{
        name : 'vidyoWebEnabled',
        mapping : 'vidyoweb > vidyoWebEnabled',
        type : 'boolean'
    }]
    
});

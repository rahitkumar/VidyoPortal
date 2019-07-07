Ext.define('SuperApp.model.settings.PortalDomain',{
    extend :'Ext.data.Model',
     
    fields :[{
        name :'domainID'
    },{
        name :'domainName'
    },{
        name :'whiteList'
    },
    {
        name :'routerPoolKey'
    }]
});

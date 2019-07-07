Ext.define('SuperApp.model.settings.TLS',{
    extend :'Ext.data.Model',
     
    fields :[{
        name :'tlsProxyLabel',
        mapping :'tls > tlsProxyLabel'
    },{
        name :'tlsProxyFeatureEnabled',
        mapping :'tls > tlsProxyFeatureEnabled',
        type :'boolean'
    },{
        name :'tlsProxyEnabled',
        mapping :'tls > tlsProxyEnabled',
        type :'boolean'
    },{
        name :'tlsProxySaveAlertBoxMessage',
        mapping : 'tls > tlsProxySaveAlertBoxMessage'
    }]
});

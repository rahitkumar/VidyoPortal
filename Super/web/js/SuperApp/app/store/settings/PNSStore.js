Ext.define('SuperApp.store.settings.PNSStore', {
    extend : 'Ext.data.Store',
    model : 'SuperApp.model.settings.PNSmodel',
    storeId : 'pnsstore',
    proxy : {
        type : 'ajax',
        actionMethods : {
            create : "POST",
            read : "GET",
            update : "POST",
            destroy : "POST"
        },
        url : "network.ajax",
        reader : {
            type : 'xml',
            totalRecords : 'results',
            record : 'row',
            rootProperty : 'dataset'
        }
    },
    autoLoad : false
}); 
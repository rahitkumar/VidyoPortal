Ext.define('SuperApp.store.settings.FQDNLog',{
    extend :'Ext.data.Store',
    storeId :'fqdnlog',
    requires :['SuperApp.model.settings.FQDNLog'],

    model :'SuperApp.model.settings.FQDNLog',
    
    proxy :{
        type :'ajax',
        actionMethods:  { read: "GET"},
        url :'securedmaint/maintenance_logfqdn.ajax',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    },
        autoLoad : false
});
Ext.define('SuperApp.store.settings.SLStore', {
    extend : 'Ext.data.Store',
    storeId : 'slstore',
    model : 'SuperApp.model.settings.SLmodel',
    proxy : {
        type : 'ajax',
        url : "license.ajax",
        reader : {
            type : 'xml',
            totalRecords : 'results',
            record : 'row',
            rootProperty : 'dataset'
        }
    }
}); 
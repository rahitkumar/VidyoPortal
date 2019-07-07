Ext.define('SuperApp.store.cloud.RouterPoolStore', {
    extend: 'Ext.data.JsonStore',
	
	model: 'SuperApp.model.cloud.RouterPoolModel',
	   
    proxy: {
        type: 'ajax',
        url: appFolder + '/data/cloud/poolList.json',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    },
    
    autoLoad: true
});

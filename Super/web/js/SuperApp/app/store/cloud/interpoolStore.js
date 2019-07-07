Ext.define('SuperApp.store.cloud.interpoolStore', {
    extend: 'Ext.data.JsonStore',
	
	model: 'SuperApp.model.cloud.interpoolModel',
	   
    proxy: {
        type: 'ajax',
        url: appFolder + '/data/cloud/interpool.json',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    },
    
    autoLoad: true
});

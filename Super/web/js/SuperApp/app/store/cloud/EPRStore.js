Ext.define('SuperApp.store.cloud.EPRStore', {
    extend: 'Ext.data.JsonStore',
	
	model: 'SuperApp.model.cloud.EPRModel',
	   
    proxy: {
        type: 'ajax',
        url: appFolder + '/data/cloud/EPR.json',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    },
    
    autoLoad: true
});

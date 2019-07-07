Ext.define('SuperApp.store.cloud.LocationTagsStore', {
    extend: 'Ext.data.JsonStore',
	
	model: 'SuperApp.model.cloud.LocationTagsModel',
	   
    proxy: {
        type: 'ajax',
        url: 'getlocations.ajax?start=0&limit=500&sort=locationTag',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
    },
    
    autoLoad: true
});

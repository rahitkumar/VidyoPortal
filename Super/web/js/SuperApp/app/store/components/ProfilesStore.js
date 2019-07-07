Ext.define('SuperApp.store.components.ProfilesStore',{
	extend: 'Ext.data.JsonStore',
	model:'SuperApp.model.components.ProfileModel',
	proxy: {
	    	type: 'ajax',
	        actionMethods:  {create: 'POST', read: 'GET', update: 'POST', destroy: 'POST'},
	        url:'getrecorderendpoints.ajax?',
	        reader: {
	            type: 'json',
	            rootProperty: 'items'
	        }
	}
});
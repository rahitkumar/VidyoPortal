Ext.define('SuperApp.store.components.MainStore', {
    extend: 'Ext.data.JsonStore',
	model: 'SuperApp.model.components.MainModel',
    proxy: {
	    	type: 'ajax',
	        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
	        url:"getcomponents.ajax",
	        reader: {
	            type: 'json',
	            rootProperty: 'items'
	        }
    },
    autoLoad: false
});
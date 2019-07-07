Ext.define('SuperApp.model.components.ReplayControllerModel', {
    extend: 'Ext.data.Model',
    fields: ['compID','name','mgmtUrl','id'],
    proxy: {
	     type: 'ajax',
	     url: 'getcomponentbyid.ajax?',
	     reader: {
	     	type: 'json',
	     	rootProperty: 'items'
	     }
	 }         
});
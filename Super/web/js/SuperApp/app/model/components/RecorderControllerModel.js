Ext.define('SuperApp.model.components.RecorderControllerModel', {
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
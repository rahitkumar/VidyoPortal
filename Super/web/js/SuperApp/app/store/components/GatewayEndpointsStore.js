Ext.define('SuperApp.store.components.GatewayEndpointsStore',{
	extend: 'Ext.data.JsonStore',
	model:'SuperApp.model.components.EndPointsModel',
	proxy: {
    	type: 'ajax',
        actionMethods:  {create: 'POST', read: 'GET', update: 'POST', destroy: 'POST'},
        url:'getvirtualendpoints.ajax?',
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
	}
});
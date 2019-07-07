Ext.define('SuperApp.store.components.MediaAddressStore',{
	extend: 'Ext.data.Store',
	alias: 'store.mediaAddrMapStore',
	model:'SuperApp.model.components.MediAddressModel',
	proxy: {
    	type: 'ajax',
        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
        url:"getmediaaddressmap.ajax",
        reader: {
            type: 'json',
            rootProperty: 'items'
        }
	},
	autoLoad: false
});
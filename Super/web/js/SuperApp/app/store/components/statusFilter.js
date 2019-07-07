// Store for the Combo box in Main Page which displays all Status
Ext.define('SuperApp.store.components.statusFilter',{
	extend: 'Ext.data.Store',
	fields: ['id', 'name'],
	 data : [
				{"id":"All", "name":"All"},
				{"id":"ACTIVE", "name":"Active"},
				{"id":"INACTIVE", "name":"Inactive"},
				{"id":"NEW", "name":"New"},
				{"id":"ALARMs", "name":"Alarms"}
			],
    autoLoad: true
});
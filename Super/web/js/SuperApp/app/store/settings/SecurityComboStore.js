Ext.define('SuperApp.store.settings.SecurityComboStore', {
	extend:'Ext.data.Store',
    fields: ['name', 'value'],
    data : [
        {"name":"STARTTLS", "value":"STARTTLS"},
        {"name":"SSL/TLS", "value":"SSL/TLS"}
    ],
    autoLoad:true
});
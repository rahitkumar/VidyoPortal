Ext.define('SuperApp.model.settings.PNSmodel', {
    extend: 'Ext.data.Model',
    fields: ['ipAddress', 'subnetMask', 'gateway','dns1','dns2','MACAddress','SystemID','ipAddress2','subnetMask2','fqdn1','fqdn2','SystemTimeZone']
});
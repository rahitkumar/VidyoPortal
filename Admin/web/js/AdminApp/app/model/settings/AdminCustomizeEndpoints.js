Ext.define('AdminApp.model.settings.AdminCustomizeEndpoints', {
    extend : 'Ext.data.Model',
    
    fields : [{
        name : 'adminCustomizeDesktopExists'

    }, {
        name : 'allowTenantOverride',
        type :'boolean'
    }]
});

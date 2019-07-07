Ext.define('SuperApp.view.settings.maintenance.MaintenanceView', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.maintenanceview',

    requires : [
        'SuperApp.view.settings.maintenance.SystemRestart',
         'SuperApp.view.settings.maintenance.PKITenantsCertApproval',
        'SuperApp.view.settings.maintenance.Diagnostics',
        'SuperApp.view.settings.maintenance.Database',
        'SuperApp.view.settings.maintenance.SystemUpgrade',
        'SuperApp.view.settings.maintenance.StatusNotify',
        'SuperApp.view.settings.maintenance.EventsNotify',
        'SuperApp.view.settings.maintenance.ExternalDatabase',
        'SuperApp.view.settings.maintenance.MaintenanceViewModel',
        'SuperApp.view.settings.maintenance.MaintenanceController'
    ],

    viewModel : {
        type : 'MaintenanceViewModel'
    },

    controller : 'MaintenanceController',

    border : false,

    layout : {
        type : 'card',
        align : 'stretch'
    },

    initComponent : function() {
        var me = this;
        me.items = [];

        me.callParent(arguments);
    }
});
Ext.define('SuperApp.view.settings.customization.CustomizationView', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.customizationview',

    requires : [
        'SuperApp.view.settings.customization.AboutInfo',
        'SuperApp.view.settings.customization.SupportInfo',
        'SuperApp.view.settings.customization.InviteText',
        'SuperApp.view.settings.customization.Notification',
        'SuperApp.view.settings.customization.CustomizeLogo',
        'SuperApp.view.settings.customization.CustomizeEndpoints',
        'SuperApp.view.settings.customization.GuideProperties',
        'SuperApp.view.settings.customization.Banners',
        'SuperApp.view.settings.customization.RoomLink',
        'SuperApp.view.settings.customization.CustomizationViewModel',
        'SuperApp.view.settings.customization.CustomizationController',
        'SuperApp.model.settings.CustomizeLogos',
        'SuperApp.model.settings.SuperCustomizeEndpoints',
        'SuperApp.model.settings.DialNo'
    ],

    viewModel : {
        type : 'CustomizationViewModel'
    },

    controller :'CustomizationController',

    layout : {
        type : 'card',
        align : 'stretch'
    },

    border  : false,

    initComponent : function() {
        var me = this;

        me.items = [];

        me.callParent(arguments);
    }
});

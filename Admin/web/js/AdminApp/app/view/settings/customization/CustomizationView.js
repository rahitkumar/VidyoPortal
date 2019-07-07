Ext.define('AdminApp.view.settings.customization.CustomizationView', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.customizationview',

    requires : [
        'AdminApp.view.settings.customization.AboutInfo',
        'AdminApp.view.settings.customization.SupportInfo',
        'AdminApp.view.settings.customization.InviteText',
        'AdminApp.view.settings.customization.Notification',
        'AdminApp.view.settings.customization.CustomizeLogo',
        'AdminApp.view.settings.customization.CustomizationViewModel',
        'AdminApp.view.settings.customization.CustomizationController',
        'AdminApp.model.settings.CustomizeLogos',
        'AdminApp.view.settings.customization.NeoEndpoints',
        'AdminApp.model.settings.AdminCustomizeEndpoints',
        'AdminApp.model.settings.DialNo'
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

        me.tools = [{
            tooltip : l10n('help1'),
            tooltipType : 'qtip',
            type : 'help',
            callback : function() {
                window.open("http://www.vidyo.com/services-support/technical-support/product-documentation/administrator-guides/#SA_SettingsPlatformNetworkSettings", "_blank");
            }
        }];

        me.items = [];

        me.callParent(arguments);
    }
});

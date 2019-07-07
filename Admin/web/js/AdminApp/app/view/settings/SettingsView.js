/***
 * @Class SettingsView
 */
Ext.define('AdminApp.view.settings.SettingsView', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.settingsview',

    requires : ['AdminApp.view.settings.SettingsViewModel', 'AdminApp.view.settings.security.Passwords', 'AdminApp.view.settings.customization.CustomizationView', 'AdminApp.view.settings.SettingsController', 'AdminApp.view.settings.systemlanguage.SystemLanguage', 'AdminApp.view.settings.license.LicenseView', 'AdminApp.view.settings.guest.GuestView', 'AdminApp.view.settings.ues.UploadEndpointView', 'AdminApp.view.settings.locationtag.LocationTags', 'AdminApp.view.settings.ipc.InterPortalView', 'AdminApp.view.settings.cdr.CDRAccess', 'AdminApp.view.settings.qos.QualityService', 'AdminApp.view.settings.scheduleroom.ScheduledRoom', 'AdminApp.view.settings.featuresettings.VidyoWeb', 'AdminApp.view.settings.featuresettings.ChatView', 'AdminApp.view.settings.auth.AuthenticationView', 'AdminApp.view.settings.featuresettings.RoomAttribute','AdminApp.view.settings.featuresettings.UserAttribute','AdminApp.view.settings.featuresettings.VidyoNeoWebRTC','AdminApp.view.settings.featuresettings.EpicIntegration','AdminApp.view.settings.featuresettings.TytoCareIntegration'],

    controller : 'SettingsController',

    viewModel : {
        type : 'SettingsViewModel'
    },



    layout : 'hbox',
    border:false,
    minHeight:800,
    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'treepanel',
            singleExpand : true,
            width : 257,
            rootVisible : false,
            scrollable : true,
           
          
            reference : 'settingsTreePanel',
            bind : {
                store : '{settignsMenuStore}'
            },
            listeners : {
                itemclick : 'onClickSettingsMenu'
            }
        }, {
            xtype : 'panel',
            flex : 1,
            margin : 5,
     
            reference : 'settingsitems',
            layout : {
                    type : 'card',
                    align : 'stretch'
            },
            items : [{
                    xtype : 'licenseview',
                    flex : 1
            }]
           
        }];

        me.callParent(arguments);

    },
    listeners : {
        beforerender : 'onBeforeRenderSettingsView',
        render : 'onRenderSettingsView'
    }
});

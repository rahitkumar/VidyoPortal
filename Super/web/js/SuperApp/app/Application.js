/**
 * The main application class. An instance of this class is created by app.js when it calls
 * Ext.application(). This is the ideal place to handle application launch and initialization
 * details.
 */
Ext.define('SuperApp.Application', {
    extend: 'Ext.app.Application',
    appFolder: appFolder,
    name: 'SuperApp',
    requires : [
                'Ext.form.action.StandardSubmit',
                'Ext.window.Toast',
                'SuperApp.utils.RegExValidator'
            ],
    stores: [
        // TODO: add global / shared stores here
        'settings.VidyoMobile',
        'settings.VidyoDesktop',
        'settings.VidyoWeb',
        'settings.SearchOptions',
        'settings.RouterPools',
        'settings.IPC',
        'settings.EndpointSettings',
        'settings.PortalDomain',
        'settings.GlobalFeatureSettings'
    ],
    
    views :['settings.PNS'],
    
    launch: function () {
        // TODO - Launch the application
    }
});

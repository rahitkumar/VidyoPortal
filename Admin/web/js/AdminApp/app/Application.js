/**
 * The main application class. An instance of this class is created by app.js when it calls
 * Ext.application(). This is the ideal place to handle application launch and initialization
 * details.
 */
Ext.define('AdminApp.Application', {
    extend: 'Ext.app.Application',
    appFolder: appFolder,
    name: 'AdminApp',
    
    requires : [
        'Ext.ux.form.ItemSelector', 'Ext.form.action.StandardSubmit',
        'AdminApp.utils.RegExValidator'
    ],
    
    stores: [
        // TODO: add global / shared stores here
    ],
    
    launch: function () {
        // TODO - Launch the application
    }
});

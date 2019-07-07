/**
 * @class SystemLanguageController
 */
Ext.define('AdminApp.view.settings.license.LicenseController',{
    extend : 'Ext.app.ViewController',
    alias : 'controller.LicenseController',
    
    getLicenseData : function() {
        var me = this,
            viewModel = me.getViewModel();
            
        viewModel.getStore('licenseStore').load();
    }
    
});
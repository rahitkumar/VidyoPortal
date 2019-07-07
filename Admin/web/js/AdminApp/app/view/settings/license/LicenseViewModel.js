/**
 * @class LicenseViewModel
 */
Ext.define('AdminApp.view.settings.license.LicenseViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.LicenseViewModel',

    stores : {
        licenseStore : {
            fields : [{
                name : 'feature'
            }, {
                name : 'license'
            }, {
                name : 'inuse'
            }],
            proxy : {
                type : 'ajax',
                url : 'license.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }

        }
    }
}); 
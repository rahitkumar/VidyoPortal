/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('AdminApp.view.main.MainModel', {
    extend : 'Ext.app.ViewModel',

    alias : 'viewmodel.main',

    data : {
        name : 'AdminApp',
        callItemClick : false
    },

    stores : {
        loginHistoryStore : {
            proxy : {
                type : 'ajax',
                url : 'loginhistory.ajax',
                reader : {
                    type : 'xml',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
            fields : ['transactionResult', 'sourceIP', 'transactionTime']
        }
    }

    //TODO - add data, formulas and/or methods to support your view
}); 
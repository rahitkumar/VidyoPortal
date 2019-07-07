/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('SuperApp.view.main.MainModel', {
    extend : 'Ext.app.ViewModel',

    alias : 'viewmodel.superMain',

    data : {
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
});

Ext.define('SuperApp.view.cloud.main.MainModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.cloudMain',

    data: {
        isModified: false,
        cloudConfig: null,
        responseXML: null,
        isModifiedPoolsAvailable : true
    }

});
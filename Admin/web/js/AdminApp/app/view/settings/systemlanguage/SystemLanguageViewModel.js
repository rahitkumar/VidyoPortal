//
Ext.define('AdminApp.view.settings.systemlanguage.SystemLanguageViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.SystemLanguageViewModel',

    stores : {
        systemLanguage : {
            fields : [{
                name : 'langID'
            }, {
                name : 'langCode'
            }, {
                name : 'langName'
            }, {
                name : 'langFlag'
            }],
            proxy : {
                type : 'ajax',
                url : 'langs.ajax',
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

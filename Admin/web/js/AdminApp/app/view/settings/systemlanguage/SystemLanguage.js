/**
 * @class SystemLanguage
 */
Ext.define('AdminApp.view.settings.systemlanguage.SystemLanguage', {
    extend : 'Ext.form.Panel',
    alias : 'widget.systemlanguage',
    
    reference : 'systemlanguage',
    
    requires : [
        'AdminApp.view.settings.systemlanguage.SystemLanguageController',
        'AdminApp.view.settings.systemlanguage.SystemLanguageViewModel',
    ],
   
     controller : 'SystemLanguageController',

    viewModel : {
        type : 'SystemLanguageViewModel'
    },
    title : {
        text : '<span class="header-title">' + l10n('system-language') + '</span>',
        textAlign : 'center'
    },
  height : 800,
   
 
     items : [
            {
                xtype : 'fieldset',
                height : '100%',
                  
                layout : {
                    type : 'vbox',
                    align : 'center',
                    pack : 'center',
            },
                items : [{
                xtype : 'combo',
                fieldLabel : '<span class="red-label">*</span>' + l10n('default-system-language'),
                bind : {
                  store : '{systemLanguage}'
                },
                labelWidth : 200,
                margin : '20',
                name : 'langCode',
                reference : 'comboSystemLanguage',
                value : 'en',
                valueField : 'langCode',
                displayField : 'langName',
                triggerAction : 'all',
                allowBlank : true,
                editable : false,
                resizable : false,
                minChars : 1
            }]
        }],
            buttonAlign : 'center',
                buttons : [{
                    xtype : 'button',
                    text : l10n('save'),
                    formBind : true,
                    handler :'onClickSaveSystemLanguage'
               

                },
            {
                xtype : 'button',
                text : l10n('cancel'),
                formBind : true,
                handler :'getSystemLanguageData'
               

            }]
        
  
  
});

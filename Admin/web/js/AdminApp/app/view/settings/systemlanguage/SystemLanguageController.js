/**
 * @class SystemLanguageController
 */
Ext.define('AdminApp.view.settings.systemlanguage.SystemLanguageController',{
    extend : 'Ext.app.ViewController',
    alias : 'controller.SystemLanguageController',
    
    /***
     * @function getSystemLanguageData
     */
    getSystemLanguageData : function() {
        var me = this,
            viewModel = me.getViewModel();
        
        viewModel.getStore('systemLanguage').load({
            callback : function() {
                me.getSelectedSystemLanguage();
            }
        });
    },
    
    /***
     * @function getSelectedSystemLanguage
     */
    getSelectedSystemLanguage : function() {
        var me = this,
            sysLangCombo = me.lookupReference('comboSystemLanguage');
        Ext.Ajax.request({
            url : 'systemlang.ajax',
            method : 'GET',
            success : function(res) {
                var xmlResponse = res.responseXML;
                sysLangCombo.setValue(Ext.DomQuery.selectValue('langCode', xmlResponse));
            },
            failure : function(res) {
                
            }
        });
    },
    
    /***
     * @function onClickSaveSystemLanguage
     */
    onClickSaveSystemLanguage : function(btn) {
        var me = this,
            form = btn.up('systemlanguage'),
            callback = function() {
                me.getSelectedSystemLanguage();
            };
            
        if(form) {
            var values = form.getForm().getValues();
            Ext.Ajax.request({
                url : 'savesystemlang.ajax',
                params : values,
                method : 'POST',
                success : function(res) {
                    var xmlResponse = res.responseXML;
                    var success = Ext.DomQuery.selectValue('message @success',xmlResponse);
                    if(success == "true") {
                        Ext.Msg.alert(l10n('message'),l10n('saved'));
                        callback();
                    }
                }, 
                failure : function(res) {
                    //TODO alert
                }
            });
        }
    }
    
});
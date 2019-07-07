/**
 * @author QualityServiceController
 */
Ext.define('AdminApp.view.settings.qos.QualityServiceController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.QualityServiceController',

    /***
     * @function getQualityServiceData
     */
    getQualityServiceData: function() {
         var me = this,
            form = me.view;

        var record = Ext.create('AdminApp.model.settings.QualityOfService');
        record.load({
             success: function(rec) {
               form.getForm().loadRecord(rec);
             }
        }); 

        
    },
    /***
     * @function onclickSaveQualityService
     */
    onclickSaveQualityService: function() {
        var me = this,
            form = me.view;

        if (form) {
            Ext.Msg.confirm(l10n('confirmation'), l10n('admin-dscpconfig-are-you-sure'), function(res) {
                if (res == 'yes') {
                    Ext.Ajax.request({
                        url: 'saveEndpointSettings.ajax',
                        params: form.getForm().getValues(),
                        method : 'POST',
                        success : function(res) {
                            var xmlResponse = res.responseXML;
                            var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                            if (success == "true") {
                                Ext.Msg.alert(l10n('message'), l10n('saved') + l10n('admin-dscpconfig-endpoints-must-signin-again'));
                            } else {
                                Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                            }
                        },
                        failure : function() {
                            Ext.Msg.alert(l10n('error'), l10n('error'));
                        }
                    });
                }
            });
        }
    },

    onclickDefaultQualityService: function() {
        this.view.getForm().reset();  
        this.getQualityServiceData();
    },

    onChangeDSCPValue : function(text, value) {
        var me = this,
            viewModel = me.getViewModel();
        if (value == null) {
            return;
        }
        if (value > 63) {
            text.setValue(63);
            value = 63;
        }

        me.setDSCPHexValue(text.labelValue, value);
    },

    setDSCPHexValue : function(viewModelName, value) {
        var me = this,
            viewModel = me.getViewModel(),
            decimalToHexString = function(number) {
                if (number < 0) {
                    number = 0xFFFFFFFF + number + 1;
                }
                return number.toString(16).toUpperCase();
            };

        viewModel.set(viewModelName, '0x' + decimalToHexString(value));
    }

});
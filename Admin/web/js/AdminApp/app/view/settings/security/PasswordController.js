Ext.define('AdminApp.view.settings.security.PasswordController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.PasswordController',

    onRenderPasswordView: function() {

        var me = this,
            form = me.view;


        var record = Ext.create('AdminApp.model.settings.Password');
        record.load({
            success: function(rec) {
                form.getForm().loadRecord(rec);
            }
        });



    },
    clickCancelPasswords: function(btn) {

        this.view.getForm().reset();
        this.onRenderPasswordView();

    },
    clickSavePasswords: function() {
        var me = this,
            form = me.view;

        if (form && form.isValid()) {
            Ext.Ajax.request({
                url: 'savepasswordconfigvalsadmin.ajax',
                params: form.getValues(),
                success: function(res) {
                    var result = res.responseXML;
                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                        Ext.Msg.alert(l10n('message'), l10n('saved'));
                    } else {
                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                    }
                    me.getPasswordData();
                }
            });
        } else {
            Ext.Msg.alert(l10n('message'), l10n('error'));
        }
    },
    remoteValidateSEP: function(textfield, newValue, url, param) {
        var scope = this;
        Ext.Ajax.request({
            url: 'validatesessionexpperiod.ajax',
            params: {
                sessionExpPeriod: newValue
            },
            scope: textfield,
            success: function(response, request) {
                var result = Ext.JSON.decode(response.responseText);
                if (result.success && !result.valid) {
                    this.validFlag = l10n('allowed-range-is-between') + " 1 - " + result.maxSessionExp + ". <br>" + l10n('please-change-the-value');

                    textfield.setMaxValue(result.maxSessionExp);
                    textfield.markInvalid();
                } else if (result.success && result.valid) {
                    this.validFlag = true;
                    this.validate();
                }
            }
        });
    },
});
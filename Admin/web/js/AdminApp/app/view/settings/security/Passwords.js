Ext.define('AdminApp.view.settings.security.Passwords', {
    extend: 'Ext.form.Panel',
    alias: 'widget.passwords',
    height: 800,
    requires: ['AdminApp.view.settings.security.PasswordViewModel', 'AdminApp.view.settings.security.PasswordController'],
    controller: 'PasswordController',
    viewModel: {
        type: 'PasswordViewModel'
    },
    title: {
        text: '<span class="header-title">' + l10n('passwords-tablabel') + '</span>',
        textAlign: 'center'
    },
    items: [{
        xtype: 'fieldset',
        height: '100%',
        layout: {
            type: 'vbox',
            align: 'center',
            pack: 'center',
        },
        items: [{
            xtype: 'numberfield',
            minValue: 3,
            maxValue: 12,
            labelWidth: 250,
            allowExponential: false,
            name: 'minPINLength',
            allowBlank: false,
            fieldLabel: l10n('min-pin-length')
        }, {
            xtype: 'numberfield',
            minValue: 1,
            name: 'sessionExpPeriod',
            fieldLabel: '<span class="red-label">*</span>' + l10n('session-exp-period'),
            labelWidth: 250,
            allowExponential: false,
            maxValue: 99999,
            allowBlank: false,



            listeners: {
                change: 'remoteValidateSEP'
            }
        }]
    }],
    buttonAlign: 'center',
    buttons: [{
        text: l10n('save'),
        itemId: 'savePasswdBtn',
        disabled: true,
        formBind: true,
        handler: 'clickSavePasswords'
    }, {
        text: l10n('cancel'),
        handler: 'clickCancelPasswords'
    }]
});
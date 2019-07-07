Ext.define('SuperApp.view.settings.globalsettings.EnableTytoCareIntegration', {
    extend: 'Ext.form.Panel',
    alias: 'widget.enableTytoCareIntegration',

    title: {
        text: '<span class="header-title">'  +'TytoCare Integration' + '</span>',
        textAlign: 'center'
    },
    height: '100%',
    requires: ['SuperApp.view.settings.globalsettings.GlobalFeatureSettingsController'],

    controller: "GlobalFeatureSettingsController",
    trackResetOnLoad: true,
    buttonAlign: 'center',
    initComponent: function() {
        var me = this,
            rec = this.fieldRec;

        me.items = [{
            xtype: 'fieldset',
            width: '90%',
            bodyStyle: 'padding: 10px',

            border: false,
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'left',
            },
            items: [{
                xtype: 'checkbox',
                columns: 1,
                reference: 'enableTytoCareIntegration',
                name: 'enableTytoCareIntegration',
                labelWidth: 260,
                //margin:'0,20,0,0',
                width: 400,
                inputValue: '1',
                uncheckedValue: '0',
               fieldLabel: 'Enable TytoCare Integration',
            }]
        }];

        me.buttons = [{
            text: l10n('save'),
            id: 'enableTytoCare-save-button',
            formBind: true,
            listeners: {
                click: 'onClickEnableTytoCareSave'
            }
        }, {
            text: l10n('cancel'),
            handler: function() {
                this.up('form').getForm().reset();
            }
        }];
        me.callParent(arguments);
    },
});
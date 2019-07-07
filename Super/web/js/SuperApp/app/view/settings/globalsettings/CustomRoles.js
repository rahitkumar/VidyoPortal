Ext.define('SuperApp.view.settings.globalsettings.CustomRoles', {
    extend: 'Ext.form.Panel',
    alias: 'widget.customRoles',

    title: {
        text: '<span class="header-title">'  +'Custom Roles' + '</span>',
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
                reference: 'customRole',
                name: 'customRole',
                labelWidth: 400,
                //margin:'0,20,0,0',
                width: 500,
                inputValue: '1',
                uncheckedValue: '0',
               fieldLabel: 'Enable Custom Role',



            }]
        }];

        me.buttons = [{
            text: l10n('save'),
            id: 'customRole-save-button',
            formBind: true,
            listeners: {
                click: 'onClickCustomRoleSave'
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
Ext.define('SuperApp.view.settings.globalsettings.UserAttributes', {
    extend: 'Ext.form.Panel',
    alias: 'widget.userAttributes',

    title: {
        text: '<span class="header-title">'  + l10n('user-attributes') + '</span>',
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
                reference: 'enableUserImage',
                name: 'enableUserImage',
                labelWidth: 400,
                //margin:'0,20,0,0',
                width: 500,
                inputValue: '1',
                uncheckedValue: '0',
               fieldLabel: l10n('enable-thumbnail-photos'),



            }, {
                xtype: 'checkbox',
                reference: 'enableUserImageUpload',
                name: 'enableUserImageUpload',
                labelWidth: 400,

                width: 500,
                inputValue: '1',
                uncheckedValue: '0',

                bind: {
                    disabled: '{!enableUserImage.checked}'
                },
              fieldLabel: l10n('allow-users-to-upload-their-own-image'),

                listeners: {
                    afterrender: function(c) {
                        Ext.QuickTips.register({
                            target: c.getEl(),
                            text: l10n('user-thumbnail-tooltip')
                        });
                    }
                }

            }, {
                xtype: 'numberfield',
                // margin:'0,10,0,0',
                name: 'maxImageSize',
                fieldLabel: l10n('max-image-size-in-KB'),
                minValue: 10,
                maxValue: 1000,
                labelWidth: 400,
                allowBlank: false,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false,
                allowDecimals:false,
                width: 500,
                labelAlign: 'left',
                listeners: {
                    change: function(field, value) {
                        value = Ext.String.htmlEncode(value);
                        field.setValue(value);
                    }
                },
                bind: {
                    disabled: '{!enableUserImage.checked}'

                }

            }]
        }];

        me.buttons = [{
            text: l10n('save'),
            id: 'userAtrbt-save-button',
            formBind: true,
            listeners: {
                click: 'onClickUserAttributeSave'
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
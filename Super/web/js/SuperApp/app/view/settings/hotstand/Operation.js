Ext.define('SuperApp.view.settings.hotstand.Operation', {
    extend: 'Ext.form.Panel',
    alias: 'widget.hotstandoperation',

    requires: ['SuperApp.view.settings.hotstand.HotstandController', 'SuperApp.view.settings.hotstand.HotstandViewModel'],

    errorReader: new Ext.data.XmlReader({
        successProperty: '@success',
        record: 'field'
    }, ['id', 'msg']),

    viewModel: {
        type: 'HotstandViewModel'
    },

    controller: 'HotstandController',

    monitorValid: true,

    autoWidth: true,

    autoHeight: true,

    title: {
        text: '<span class="header-title">' + l10n('label-operation') + '</span>',
        textAlign: 'center'
    },

    bodyStyle: "padding:5px",

    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    defaults: {
        margin: 5
    },

    initComponent: function() {
        var me = this;

        me.items = [{

            xtype: 'panel',
            defaults: {
                margin: 5
            },
            layout: {
                type: 'hbox'
            },
            items: [{

                xtype: 'textarea',
                name: 'seckey',
                id: 'seckeyId',
                fieldLabel: '<span class="red-label">*</span>' + l10n('label-security-key'),
                allowBlank: false,
                width: 500,
                margin: 5,
                height: 100,
                bind: {
                    hidden: '{showRemoveMaintLink}'
                }
            }, {
                xtype: 'button',
                text: l10n('label-import'),
                margin: 5,
                tooltip: l10n('label-import'),
                handler: 'onClickImport',
                formBind: true,
                bind: {
                    hidden: '{showRemoveMaintLink}'
                }
            }]
        }, {

            xtype: 'panel',
            defaults: {
                margin: 5
            },
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            items: [{
                xtype: 'button',
                text: l10n('label-switch-to-maintenance'),
                tooltip: l10n('label-switch-to-maintenance'),
                handler: 'onClickSwitchToMaintenance',
                bind: {
                    hidden: '{!showToMaintLink}'
                }
            }, {
                xtype: 'button',
                text: l10n('label-remove-from-maintenance'),
                tooltip: l10n('label-remove-from-maintenance'),
                handler: 'onClickRemoveToMaintenace',
                bind: {
                    hidden: '{!showRemoveMaintLink}'
                }
            }, {
                xtype: 'button',
                text: l10n('label-force-standby'),
                tooltip: l10n('label-force-standby'),
                handler: 'onClickForceStandby',
                bind: {
                    hidden: '{showRemoveMaintLink}'
                }
            }]
        }];
     
        me.callParent(arguments);
    }
});
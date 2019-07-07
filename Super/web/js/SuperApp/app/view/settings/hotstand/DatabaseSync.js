Ext.define('SuperApp.view.settings.hotstand.DatabaseSync', {
    extend: 'Ext.form.Panel',
    alias: 'widget.databasesync',

    reference: 'databasesync',

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

        height : '100%',
    buttonAlign : 'center',
    title: {
        text: '<span class="header-title">' + l10n('label-dbsync') + '</span>',
        textAlign: 'center'
    },

    bodyStyle: "padding:5px",

    initComponent: function() {
        var me = this;

        me.items = [{
            xtype: 'fieldset',
            border: 1,
            width: '100%',
             height : '100%',
                      margin: 5,
            padding: 0,
            defaults: {
                margin: 5,
                padding: 0
            },
            layout: {
                type: 'vbox',
                align: 'center'
            },
            items: [{
                xtype: 'combo',
                id: 'startTime',
                fieldLabel: 'Start Time',
                allowBlank: false,
                editable: false,
                triggerAction: 'all',
                typeAhead: false,
                mode: 'local',
                name: 'start_time',
                displayField: 'displayFieldName', // what the user sees in the dropdown
                valueField: 'dataFieldName',
                bind: {
                    store: '{timeStore}',
                    value: '{startTime}'
                },
                listeners: {
                    change: 'onChangeStartTime'
                }
            }, {
                xtype: 'combo',
                fieldLabel: 'End Time',
                allowBlank: false,
                editable: false,
                triggerAction: 'all',
                typeAhead: false,
                mode: 'local',
                name: 'end_time',
                displayField: 'displayFieldName', // what the user sees in the dropdown
                valueField: 'dataFieldName',
                bind: {
                    store: '{time2Store}',
                    value: '{endTime}'
                },
                listeners: {
                    change: 'onChangeEndTime'
                }
            }, {
                xtype: 'radiogroup',
                fieldLabel: 'Frequency',
                reference: 'frequencyGroup',
                name: 'frequencyGroup',
                margin: 8,
                horizontal: true,
                bind: {
                    value: '{frequency}'
                },
                items: [{
                    boxLabel: '30 Minutes',
                    name: 'frequencyGroup',
                    inputValue: '30',
                    initial: 'no'
                }, {
                    boxLabel: '1 Hour',
                    name: 'frequencyGroup',
                    inputValue: '60',
                    initial: 'no'
                }, {
                    boxLabel: '2 Hours',
                    name: 'frequencyGroup',
                    inputValue: '120',
                    initial: 'no'
                }, {
                    boxLabel: '3 Hours',
                    name: 'frequencyGroup',
                    inputValue: '180',
                    initial: 'no'
                }],
                listeners: {
                    change: function(item, state) {
                        if (state) {
                            if (state.frequencyGroup == '30') {
                                if (state.initial && state.initial == 'no') {
                                    //frequencySelectWarning(state.inputValue);
                                }
                            }
                        }
                    }
                }
            }]
        }];

        //me.bbar = [];
        me.buttons = [{
            text: l10n('apply'),
            tooltip: l10n('apply'),
            formBind: true,
            actiontype: 'n',
            listeners: {
                click: 'onClickApplyDBSync'
            }
        }, {
            text: l10n('label-sync-now'),
            tooltip: l10n('label-sync-now'),
            actiontype: 'y',
            listeners: {
                click: 'onClickSyncNowDBSync'
            }
        }];
        me.callParent(arguments);
    }
});
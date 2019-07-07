Ext.define('AdminApp.view.settings.ipc.InterPortalView', {
    extend : 'Ext.form.Panel',
    alias : 'widget.interportalview',

    requires : ['AdminApp.view.settings.ipc.InterPortalViewModel', 'AdminApp.view.settings.ipc.InterPortalViewController'],
    viewModel : {
        type : 'InterPortalViewModel'
    },

    layout : {
        type : 'vbox',
        align : 'center'
    },
  title : {
        text : '<span class="header-title">' + l10n('ipc-label') + '</span>',
        textAlign : 'center'
    },

    controller : 'InterPortalViewController',
   buttonAlign:'center',
    border : false,
    errorReader : {
        type : 'xml',
        record : 'field',
        model : 'AdminApp.model.Field',
        success : '@success'
    },
    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'radiogroup',
            fieldLabel : l10n('ipc-allow-block-list-label'),
            name : 'ipcControlMode',
            reference : 'ipcControlMode',
            width : 400,
            labelStyle : 'font-weight:bold',
            columns : 2,
            labelWidth : 150,
            vertical : true,
            items : [{
                name : 'allowBlockGroup',
                boxLabel : l10n('ipc-allowed-list-label'),
                reference : 'allowedList',
                inputValue : 'allow'
            }, {
                name : 'allowBlockGroup',
                boxLabel : l10n('ipc-blocked-list-label'),
                reference : 'blockedList',
                inputValue : 'block'
            }],
            listeners : {
                change : 'onChangeIPCControlMode'
            }
        }, {
            xtype : 'fieldset',
            width : '100%',
            border : 1,
            margin : 5,
            padding : 0,
            items : [{
                xtype : 'grid',
                multiSelect : false,
                reference : 'ipcDomainGrid',
                title : l10n('ipc-grid-title-label'),
                titleAlign : 'center',             
                minHeight : 150,
                frame : false,
                 bind : {
                    store : '{ipclistgridStore}'
                },
                width : '100%',
                columns : [{
                    text : l10n('ipc-grid-domaincolumn-label'),
                    dataIndex : 'HostName',
                    flex : 1
                }],
                bbar : [{
                    text : l10n('add'),
                       iconCls : 'x-fa fa-plus-circle',
                       listeners : {
                        click : 'onClickDomainGridAdd'
                    }
                }, {
                    text : l10n('delete'),
                    iconCls : 'x-fa fa-minus-circle',
                    listeners : {
                        click : 'onClickDomainGridRemove'
                    }
                }],
                buttonAlign : 'left',
                selModel : {
                    selType : 'rowmodel',
                    mode : 'SINGLE'
                }
            }]
        }, {
            xtype : 'hiddenfield',
            name : 'deleteIds',
            reference : 'deletedIds',
            value : ''
        }, {
            xtype : 'hiddenfield',
            name : 'prevAllowFlag'
        }];
        me.buttons= [{
                disabled : true,
                reference : 'ipcSaveBtn',
                text : l10n('save'),
                listeners : {
                    click : 'onClickIPCSave'
                }
            }, {
                text : l10n('cancel'),
                listeners : {
                     click : 'getIPCData'
                }
            }];

        me.callParent(arguments);
    }
});

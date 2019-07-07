Ext.define('SuperApp.view.settings.globalsettings.IPC', {
    extend : 'Ext.form.Panel',
    xtype : 'ipcform',
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
	bodyStyle: 'background:#F6F6F6',
    title : {
        text : '<span class="header-title">'+l10n('ipc-enable-diable-label')+'</span>',
        textAlign : 'center'
    },
    requires : ['SuperApp.view.settings.globalsettings.GlobalFeatureSettingsController', 'SuperApp.view.settings.globalsettings.GlobalFeatureSettingsModel'],
    controller : "GlobalFeatureSettingsController",
    viewModel : {
        type : 'GlobalFeatureSettingsModel'
    },
    defaults : {
        margin : 5
    },
    buttonAlign:'center',
    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'fieldset',
            width : '100%',
            padding : 0,
            margin : 5,
            layout : {
                type : 'vbox',
                align : 'center'
            },
            items : [{
                xtype : 'form',
                reference : 'ipcForm',
                bodyStyle: 'background:#F6F6F6',
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'radiogroup',
                    fieldLabel : l10n('ipc-access-control-label'),
                    labelWidth : 160,
                    width : 320,
                    name : 'ipcContorlLevel',
                    bind : {
                        value : '{accessvalue}',
                    },
                    columns : 2,
                    items : [{
                        name : 'accessControlGroup',
                        boxLabel : l10n('ipc-access-control-label-admin'),
                        inputValue : 'admin',
                        bind : {
                            value : '{accessvalue}'
                        }
                    }, {
                        name : 'accessControlGroup',
                        boxLabel : l10n('ipc-access-control-label-super'),
                        inputValue : 'super',
                        boxLabelWidth : 100,
                        bind : {
                            value : '{accessvalue}'
                        }
                    }],
                    listeners : {
                        change : 'onChangeIPCControlLevel'
                    }
                }, {
                    xtype : 'combo',
                    fieldLabel : l10n('router-pool'),
                    name : 'routerPoolID',
                    reference : 'routerPoolID',
                    editable : false,
                    width : 300,
                    bind : {
                        store : '{routerPoolsStore}'
                    },
                    labelWidth : 150,
                    labelStyle : 'font-weight:bold',
                    queryMode : 'local',
                    displayField : 'name',
                    valueField : 'id',
                    emptyText : l10n('no-active-pools-available'),
                    listeners : {
                        change : 'onChangeIPCRouterPool'
                    }
                }, {
                    xtype : 'radiogroup',
                    fieldLabel : l10n('ipc-allow-block-list-label'),
                    name : 'ipcControlMode',
                    reference : 'ipcControlMode',
                    labelStyle : 'font-weight:bold',
                    columns : 2,
                    labelWidth : 200,
                    width : 400,
                    bind : {
                        hidden : '{adminManaged}',
                        value : '{ipccontrolmode}'
                    },
                    vertical : true,
                    items : [{
                        name : 'allowBlockGroup',
                        boxLabel : l10n('ipc-allowed-list-label'),
                        inputValue : 'allow',
                        boxLabelwidth : 150,
                        bind : {
                            value : '{ipccontrolmode}'
                        }
                    }, {
                        name : 'allowBlockGroup',
                        boxLabel : l10n('ipc-blocked-list-label'),
                        inputValue : 'block',
                        boxLabelwidth : 150,
                        bind : {
                            value : '{ipccontrolmode}'
                        }
                    }],
                    listeners : {
                        change : 'onChangeIPCControlMode'
                    }
                }, {
                    xtype : 'hiddenfield',
                    name : 'deleteIds',
                    value : ''
                }, {
                    xtype : 'hiddenfield',
                    name : 'prevAllowFlag',
                    bind : {
                        value : '{prevAllowFlag}'
                    }
                }]
            }, {
                xtype : 'grid',
                multiSelect : false,
                title : {
                	title : l10n('ipc-grid-title-allow-label'),
                	titleAlign : 'left',	
                },
                emptyText : l10n('no-data-for-allowed-domain-addresses'),
                padding : 0,
                reference : 'ipcDomainGrid',
                width : '100%',
                columns : [{
                    text : l10n('ipc-grid-domaincolumn-label'),
                    dataIndex : 'domainName',
                    flex : 1
                }],
                bind : {
                    hidden : '{adminManaged}',
                    store : '{portalDomainStore}'
                },
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
                frame : false,
                selModel : {
                    selType : 'rowmodel',
                    mode : 'SINGLE'
                }
            }]
        }];
        me.buttons= [{
            text : l10n('save'),
            disabled : true,
            reference : 'ipcSave',
            listeners : {
                click : 'onClickIPCSave'
            }
        }, {
            text : l10n('cancel'),
            listeners : {
                click : 'getIPCData'
            }
        }];
        	
        /*       me.items = [{
         xtype : 'fieldset',
         width : '100%',
         padding : 0,
         margin : 5,
         layout : {
         type : 'vbox',
         align : 'center'
         },
         items : [{
         xtype : 'toolbar',
         width : '100%',
         border : 0,
         margin : 0,
         layout : {
         align : 'center'
         },
         items : ['->', {
         xtype : 'title',
         text : '<span class="header-title">Inter-Portal Communication</span>',
         textAlign : 'center'
         }, '->']
         },  {
         xtype : 'radiogroup',
         fieldLabel : 'Access Control Level',
         labelWidth : 160,
         width : 320,
         name : 'ipcContorlLevel',
         bind : {
         value : '{accessvalue}',
         },
         columns : 2,
         items : [{
         name : 'accessControlGroup',
         boxLabel : 'Tenant',
         inputValue : 'admin',
         bind : {
         value : '{accessvalue}'
         }
         }, {
         name : 'accessControlGroup',
         boxLabel : 'System',
         inputValue : 'super',
         boxLabelWidth : 100,
         bind : {
         value : '{accessvalue}'
         }
         }],
         listeners : {
         change : 'onChangeIPCControlLevel'
         }
         }, {
         xtype : 'combo',
         fieldLabel : 'Router Pool:',
         name : 'routerPoolID',
         editable : false,
         width : 300,
         bind : {
         store : '{routerPoolsStore}'
         },
         labelWidth : 150,
         labelStyle : 'font-weight:bold',
         queryMode : 'local',
         displayField : 'name',
         valueField : 'id',
         emptyText : 'No active pools available',
         listeners : {
         change : 'onChangeIPCRouterPool'
         }
         }, {
         xtype : 'radiogroup',
         fieldLabel : 'Access Control Mode',
         name : 'ipcControlMode',
         labelStyle : 'font-weight:bold',
         columns : 2,
         labelWidth : 200,
         width : 400,
         bind : {
         hidden : '{adminManaged}',
         value : '{ipccontrolmode}'
         },
         vertical : true,
         items : [{
         name : 'allowBlockGroup',
         boxLabel : 'Allowed List',
         inputValue : 'allow',
         boxLabelwidth : 150,
         bind : {
         value : '{ipccontrolmode}'
         }
         }, {
         name : 'allowBlockGroup',
         boxLabel : 'Blocked List',
         inputValue : 'block',
         boxLabelwidth : 150,
         bind : {
         value : '{ipccontrolmode}'
         }
         }],
         listeners : {
         change : 'onChangeIPCControlMode'
         }
         }, {
         xtype : 'hiddenfield',
         name : 'deleteIds',
         value : ''
         }, {
         xtype : 'hiddenfield',
         name : 'prevAllowFlag',
         bind : {
         value : '{prevAllowFlag}'
         }
         }, {
         xtype : 'panel',
         border : 0,
         defaults : {
         margin : 5,
         xtype : 'button'
         },
         layout : {
         layout : 'hbox',
         align : 'stretch'
         },
         items : [{
         text : 'Save',
         disabled : true,
         listeners : {
         click : 'onClickIPCSave'
         }
         }, {
         text : 'Reset',
         listeners : {
         click : 'getIPCData'
         }
         }]
         }]
         }, ];*/

        me.callParent(arguments);
    }
});

Ext.define('SuperApp.view.components.VidyoRouterController', {
    extend : 'Ext.form.Panel',

    reference : 'vidyoroutereditor',

    border : false,
    requires : ['Ext.panel.Panel', 'Ext.toolbar.Toolbar', 'SuperApp.view.components.StunAddress'],
    xtype : 'VidyoRouterClass',

    cls : 'white-footer',

    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    width : '100%',

    margin : 0,

    padding : 0,

    initComponent : function() {
        Ext.apply(Ext.form.field.VTypes, {
            IPAddress : function(v) {
                return /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(v);
            },
            IPAddressText : 'Must be a numeric IP address',
            IPAddressMask : /[\d\.]/i
        });
        this.items = [{
            xtype : 'hiddenfield',
            name : 'routerpkID'
        }, {
            xtype : 'hiddenfield',
            name : 'componentpkId'
        }, {
            xtype : 'fieldset',
            width : '100%',
            margin : 5,
            border : 1,
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
                    layout : 'vbox',
                    align : 'center'
                },
                ui : 'footer',
                cls : 'white-header-footer',
                items : [{
                    text : l10n('back-to-components'),
                    icon : 'js/SuperApp/resources/images/control_rewind.png',
                    cls : 'back-button',
                    handler : 'backToComponents'
                }, '->', '->', {
                    xtype : 'title',
                    text : '<span class="header-title">' + l10n('vidyo-router') + '</span>',
                    textAlign : 'center'
                }, '->', '->', '->']
            }, {
                xtype : 'textfield',
                name : 'compID',
                fieldLabel : l10n('id'),
                fieldCls : 'customtextreadcls',
                labelAlign : 'right',
                labelWidth : 30,
                width : 450,
                id : 'routerid',
                readOnly : true
            }, {
                xtype : 'textfield',
                name : 'name',
                labelAlign : 'right',
                width : 560,
                labelWidth : 85,
                allowBlank : false,
                maxLength : 256,
                fieldLabel : l10n('display-name')
            }, {
                xtype : 'textfield',
                name : 'mgmtUrl',
                width : 560,
                labelWidth : 85,
                labelAlign : 'right',
                allowBlank : true,
                maskRe: /[^ ]/,
                maxLength : 256,
                fieldLabel : l10n('management-url')
            }, {
                xtype : 'panel',
                title : 'SCIP',
                titleAlign : 'center',
                border : 1,
                margin : 0,
                padding : 0,
                width : 600,
                cls : 'white-header-footer',
                defaults : {
                    margin : 5
                },
                layout : {
                    type : 'hbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'tbfill'
                }, {
                    xtype : 'textfield',
                    allowBlank : true,
                    maskRe: /[^ ]/,
                    labelWidth : 110,
                    width : 180,
                    labelWidth : 90,
                    labelAlign : 'right',
                    name : 'scipFqdn',
                    maxLength : 256,
                    fieldLabel : l10n('fqdn'),
                }, {
                    xtype : 'numberfield',
                    fieldLabel : l10n('port'),
                    labelWidth : 60,
                    labelAlign : 'right',
                    hideTrigger : true,
                    width : 120,
                    name : 'scipPort',
                    allowBlank : false,
                    readOnly : false,
                    minValue : 0,
                    maxValue : 65535
                }, {
                    xtype : 'tbfill'
                }]
            }, {
                xtype : 'panel',
                title : 'Media Port Range',
                titleAlign : 'center',
                border : 1,
                margin : 5,
                width : 600,
                cls : 'white-header-footer',
                padding : 0,
                defaults : {
                    margin : 5
                },
                layout : {
                    type : 'hbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'tbfill'
                }, {
                    xtype : 'numberfield',
                    fieldLabel : l10n('start'),
                    margin : 0,
                    width : 180,
                    labelWidth : 90,
                    labelAlign : 'right',
                    name : 'mediaPortStart',
                    hideTrigger : true,
                    minValue : 0,
                    maxValue : 65535
                }, {
                    xtype : 'numberfield',
                    fieldLabel : l10n('end'),
                    labelAlign : 'right',
                    name : 'mediaPortEnd',
                    hideTrigger : true,
                    minValue : 0,
                    maxValue : 65535,
                    labelWidth : 60,
                    width : 120
                }, {
                    xtype : 'tbfill'
                }]
            }, {
                xtype : 'panel',
                title : '<span style="font-weight:bold;">' + l10n('qos-values') + '</span>',
                titleAlign : 'center',
                margin : 5,
                border : 1,
                cls : 'white-header-footer',
                width : 610,
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                defaults : {
                    border : 0,
                    margin : 5,
                    padding : 0
                },
                items : [{
                    xtype : 'panel',
                    layout : {
                        type : 'hbox',
                        align : 'stretch'
                    },
                    items : [{
                        xtype : 'checkboxfield',
                        reference : 'videodscprouter',
                        fieldLabel : l10n('video-dscp'),
                        labelWidth : 150,
                        handler : 'videoDscp'
                    }, {
                        xtype : 'numberfield',
                        reference : 'videodscpid',
                        margin : '0 10 0 10',
                        name : 'dscpVidyo',
                        allowDecimals : false,
                        hideTrigger : true,
                        labelValue : 'hexVideoDSCP',
                        maxValue : 63,
                        minValue : 0,
                        disabled : true,
                        listeners : {
                            change : 'onChangeDSCPValue'
                        }
                    }, {
                        xtype : 'label',
                        bind : {
                            html : '{hexVideoDSCP}'
                        }
                    }]
                }, {
                    xtype : 'panel',
                    layout : {
                        type : 'hbox',
                        align : 'stretch'
                    },
                    items : [{
                        xtype : 'checkboxfield',
                        reference : 'audiodscprouter',
                        fieldLabel : l10n('audio-dscp'),
                        labelWidth : 150,
                        handler : 'audioDscp'
                    }, {
                        xtype : 'numberfield',
                        reference : 'audiodscpid',
                        margin : '0 10 0 10',
                        labelValue : 'hexAudioDSCP',
                        name : 'audioDscp',
                        allowDecimals : false,
                        hideTrigger : true,
                        maxValue : 65,
                        minValue : 0,
                        disabled : true,
                        listeners : {
                            change : 'onChangeDSCPValue'
                        }

                    }, {
                        xtype : 'label',
                        bind : {
                            html : '{hexAudioDSCP}'
                        }

                    }]
                }, {
                    xtype : 'panel',
                    layout : {
                        type : 'hbox',
                        align : 'stretch'
                    },
                    items : [{
                        xtype : 'checkboxfield',
                        reference : 'contentdscprouter',
                        fieldLabel : l10n('content-dscp'),
                        labelWidth : 150,
                        handler : 'contentDscp'
                    }, {
                        xtype : 'numberfield',
                        reference : 'contentdscpid',
                        labelValue : 'hexContentDSCP',
                        margin : '0 10 0 10',
                        name : 'contentDscp',
                        allowDecimals : false,
                        hideTrigger : true,
                        maxValue : 65,
                        minValue : 0,
                        disabled : true,
                        listeners : {
                            change : 'onChangeDSCPValue'
                        }
                    }, {
                        xtype : 'label',
                        bind : {
                            html : '{hexContentDSCP}'
                        }

                    }]
                }, {
                    xtype : 'panel',
                    layout : {
                        type : 'hbox',
                        align : 'stretch'
                    },
                    items : [{
                        xtype : 'checkboxfield',
                        reference : 'signaldscprouter',
                        fieldLabel : l10n('signaling-dscp'),
                        labelWidth : 150,
                        handler : 'signalingDscp'
                    }, {
                        xtype : 'numberfield',
                        reference : 'signaldscpid',
                        margin : '0 10 0 10',
                        name : 'singnalingDscp',
                        labelValue : 'hexSignalingDSCP',
                        allowDecimals : false,
                        hideTrigger : true,
                        maxValue : 65,
                        minValue : 0,
                        disabled : true,
                        listeners : {
                            change : 'onChangeDSCPValue'
                        }

                    }, {
                        xtype : 'label',
                        bind : {
                            html : '{hexSignalingDSCP}'
                        }

                    }]
                }]
            }, {
                xtype : 'fieldset',
                margin : 5,
                border : 1,
                padding : 0,
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'toolbar',
                    border : 0,
                    margin : 0,
                    layout : {
                        layout : 'vbox',
                        align : 'center'
                    },
                    items : ['->', {
                        xtype : 'title',
                        text : '<span style="font-weight:bold;">' + l10n('nat-firewall') + '</span>',
                        textAlign : 'center'
                    }, '->']
                }, {
                    xtype : 'stunaddress'
                }, {
                    xtype : 'grid',
                    title : '',
                    reference : 'mediaaddressmap',
                    width : 600,
                    title : 'Media Address Map',
                    titleAlign : 'center',
                    cls : 'white-header-footer',
                    minHeight : 200,
                    margin : 5,
                    selModel : {
                        checkOnly : true,
                        injectCheckbox : 0,
                        showHeaderCheckbox : false,
                        mode : 'SINGLE'
                    },
                    bind : {
                        store : '{MediaAddressStore}'
                    },
                    columns : [{
                        text : l10n('local-ip-address'),
                        dataIndex : 'localIP',
                        flex : 1,
                        editor : {
                            xtype : 'textfield',
                            vtype : 'IPv4orIPv6',
                            allowBlank : false
                        }
                    }, {
                        text : l10n('remote-ip-address'),
                        dataIndex : 'remoteIP',
                        flex : 1,
                        editor : {
                            xtype : 'textfield',
                            vtype : 'IPv4orIPv6',
                            allowBlank : false
                        }
                    }],
                    selType : 'rowmodel',
                    plugins : {
                        ptype : 'rowediting',
                        clicksToEdit : 2,
                        autoCancel : false,
                        pluginId : 'routerMediaRowEditId',
                        listeners : {
                            edit : 'updateRouterMediaRecord',
                            canceledit : 'deleteRouterMediaRecord',
                            beforeedit : "checkIfBlankRecord"
                        }
                    },
                    bbar : [{
                        xtype : 'button',
                        text : l10n('add'),
                        handler : 'onClickAddMediaAddressMap'
                    }, {
                        xtype : 'button',
                        text : l10n('remove'),
                        handler : 'onClickRemoveMediaAddressMap'
                    }, '->']
                }]
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
                    text : l10n('save'),
                    handler : 'routerSave',
                    formBind : true,
                    disabled : true
                }, {
                    text : l10n('reset'),
                    handler : 'vidyoRouterReset'
                }, {
                    text : l10n('restore-default'),
                    listeners : {
                        click : 'onClickRestoreDefaultVR'
                    }
                }]
            }]
        }];
        this.callParent();
    }
});

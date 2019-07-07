Ext.define('SuperApp.view.components.RouterView', {
	extend : 'Ext.window.Window',
	alias : 'widget.routerView',
	border : false,
	width : 700,
	modal : true,
	frame : true,
	constrain : true,
	closeAction : 'destroy',
	closable : true,
	reference : 'routerWin',
	resizeable : false,
	title : {
		text : l10n('vidyo-router'),
		textAlign : 'center'
	},
	initComponent : function() {
		var me = this;

		me.items = [ {
			xtype : 'form',
			reference : 'routerEditform',
			border : false,
			padding : 0,
			margin : 5,
			trackResetOnLoad : true,
			standardSubmit : false,
			requires : [ 'SuperApp.model.components.RouterModel'],
			layout : {
				type : 'vbox',
				align : 'center'
			},
			defaults : {
				width : 600,
				labelWidth : 125
			},
            items : [{
	            xtype : 'fieldset',
	            width : '100%',
	            layout : {
	                type : 'vbox',
	                align : 'center'
	            },
				defaults : {
					width : 600,
					labelWidth : 125
				}, items : [{
	                xtype : 'textfield',
	                name : 'compID',
	                fieldLabel : l10n('id'),
	                labelAlign : 'right',
	                id : 'routerid',
	                readOnly : true
	            }, {
	                xtype : 'textfield',
	                name : 'name',
	                labelAlign : 'right',
	                allowBlank : false,
	                maxLength : 256,
	                fieldLabel : l10n('display-name')
	            }, {
	                xtype : 'textfield',
	                name : 'mgmtUrl',
	                labelAlign : 'right',
	                allowBlank : true,
	                maskRe: /[^ ]/,
	                maxLength : 256,
	                vtype : 'URLValidate',
	                fieldLabel : l10n('management-url')
	            }]}, {
                xtype : 'fieldset',
                title : 'SCIP',
                width : '100%',
                layout : {
                    type : 'hbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'textfield',
                    allowBlank : true,
                    maskRe: /[^ ]/,
                    labelAlign : 'right',
                    name : 'scipFqdn',
                    maxLength : 256,
                    fieldLabel : l10n('fqdn'),
                }, {
                    xtype : 'numberfield',
                    fieldLabel : l10n('port'),
                    labelAlign : 'right',
                    hideTrigger : true,
                    name : 'scipPort',
                    allowBlank : false,
                    readOnly : false,
                    minValue : 0,
                    maxValue : 65535
                }]
            }, {
                xtype : 'fieldset',
                title : 'Media Port Range',
                titleAlign : 'center',
                width : '100%',
                layout : {
                    type : 'hbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'numberfield',
                    fieldLabel : l10n('start'),
                    margin : 0,
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
                    maxValue : 65535
                }]
            }, {
                xtype : 'fieldset',
                title : l10n('qos-values'),
                width : '100%',
                items : [{
                	xtype : 'fieldset',
                	layout: 'hbox',
                	border : false,
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
                	xtype : 'fieldset',
                	layout: 'hbox',
                	border : false,
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
                	xtype : 'fieldset',
                	layout: 'hbox',
                	border : false,
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
                	xtype : 'fieldset',
                	layout: 'hbox',
                	border : false,
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
                width : '100%',
                title : l10n('nat-firewall'),
                items : [
					{
						xtype : 'fieldset',
						width : '100%',
						title : l10n('stun-server-address'),
		                layout : {
		                    type : 'hbox',
		                    align : 'center'
		                },
						items : [{
		                    xtype : 'textfield',
		                    fieldLabel : l10n('ip-fqdn'),
		                    name : 'stunFqdn',
		                    labelAlign : 'right',
		                    maxLength : 256,
		                    maskRe: /[^ ]/
		                }, {
		                    xtype : 'numberfield',
		                    fieldLabel : l10n('port'),
		                    name : 'stunPort',
		                    labelAlign : 'right',
		                    allowDecimals : false,
		                    hideTrigger : true,
		                    minValue : 0,
		                    maxValue : 65535
		                }]

					},
					{
		                xtype : 'fieldset',
		                name : 'mediaAddrMapFS',
		                title : l10n('mediaaddressmap'),
		                items : [{
		                    xtype : 'grid',
		                    title : '',
		                    name : 'mediaAddrMapGrid',
		                    reference : 'mediaaddressmap',
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
		            }
                ]
            }],
			buttons : [ '->', {
				text : l10n('save'),
				reference : 'routerSave',
				formBind : true,
				disabled : true,
				handler : 'routerSave'
			}, {
				text : l10n('cancel'),
				reference : 'comp_detail_reset',
				handler : 'vidyoRouterReset'
			}, {
                text : l10n('restore-default'),
                listeners : {
                    click : 'onClickRestoreDefaultVR'
                }
            }, '->' ]
		}];
		this.callParent(arguments);
	}
});

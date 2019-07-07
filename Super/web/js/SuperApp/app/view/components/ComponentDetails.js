Ext.define('SuperApp.view.components.ComponentDetails', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.componentdetails',

    margin : 0,
    padding : 0,
    id : 'componentPanelId',
    viewtype : '',

    initComponent : function() {
        var me = this,
            viewtype = me.viewtype;

        Ext.apply(Ext.form.VTypes, {
            componentPasswordMatch : function(val, field) {
            	var cmp = Ext.getCmp('componentPanelId');
                if (field.initialPassField && cmp.rendered) {
                    var pwd = me.down('textfield[itemId=' + field.initialPassField + ']');
                    return (val == pwd.getValue());
                }
                return true;
            },
            componentPasswordMatchText : l10n('password-not-match')
        });

        me.items = [{
            xtype : 'fieldset',
            margin : 5,
            border : 1,
            padding : 0,
            layout : {
                type : 'vbox',
                align : 'stretch'
            },
            items : [{
                xtype : 'toolbar',
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
                    bind : {
                        text : '<span class="header-title">{compoentsTitle}</span>',
                    },
                    textAlign : 'center'
                }, '->', '->', '->']
            }, {
                xtype : 'form',
                reference : 'componentdetailform',
                border : false,
                padding : 0,
                margin : 5,
                cls : 'white-header-footer',
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'hidden',
                    name : 'compUserId',
                    bind : {
                        value : '{compUserId}'
                    }
                }, {
                    xtype : 'textfield',
                    fieldLabel : l10n('id'),
                    name : 'compID',
                    allowBlank : false,
                    labelWidth : 214,
                    width : 500,
                    fieldCls : 'custom-compdetails-textreadcls',
                    readOnly : true
                }, {
                    xtype : 'textfield',
                    name : 'name',
                    labelWidth : 214,
                    allowBlank : false,
                    width : 500,
                    maxLength : 256,
                    fieldLabel : '<span class="red-label">*</span>' + l10n('display-name')
                }, {
                    xtype : 'textfield',
                    name : 'mgmtUrl',
                    labelWidth : 214,
                    width : 500,
                    allowBlank : true,
                    maskRe: /[^ ]/,
                    maxLength : 256,
                    fieldLabel : l10n('management-url'),
                    value : ''
                }, {
                    xtype : 'textfield',
                    name : 'username',
                    id : 'usrname',
                    fieldLabel : '<span class="red-label">*</span>' + l10n('user-name'),
                    labelWidth : 214,
                    width : 500,
                    allowBlank : false,
                    maxLength : 128,
                    bind : {
                        value : '{compusername}'
                    }
                }, {
                    xtype : 'panel',
                    border : false,
                    reference : 'componentpassword',
                    margin : '0 0 20 0',
                    layout : {
                        type : 'vbox',
                        align : 'center'
                    },
                    items : [{
                        xtype : 'textfield',
                        reference : 'pwdid',
                        itemId : 'componentPassword',
                        name : 'pswd',
                        id : 'pswd',
                        labelWidth : 214,
                        width : 500,
                        minLength : 2,
                        maxLength : 158,
                        allowBlank : false,
                        fieldLabel : '<span class="red-label">*</span>' + l10n('password'),
                        inputType : 'password',
                        bind : {
                            value : '{comppassword}'
                        }
                    }, {
                        xtype : 'textfield',
                        reference : 'vpwdid',
                        name : 'confirmpswd',
                        labelWidth : 214,
                        width : 500,
                        itemId : 'confirmComponentPassword',
                        fieldLabel : '<span class="red-label">*</span>' + l10n('verify-password'),
                        inputType : 'password',
                        msgTarget : 'under',
                        bind : {
                            value : '{compconfirmpassword}'
                        },
                        //vtype : 'componentPasswordMatch',
                        initialPassField : 'componentPassword',
                        validator : function() {
                            if (this.activeError === undefined) {
                                return true;
                            } else
                                return l10n('password-not-match');
                        },
                        listeners: {
                            change: function(field) {
                            	setTimeout( function() {
	                                if (field.initialPassField) {
	                                    var passwd = field.up('form').down('textfield[itemId=' + field.initialPassField + ']');
	                                    var result = ( field.value == passwd.getValue());
	                                    if (result)
	                                        field.clearInvalid();
	                                    else {
	                                        field.markInvalid ();
	                                    }
	                                    field.validate();
	                                }
	                                return true;
	                            }, 800);
                            }
                        }
                    }]
                }, {
                    xtype : 'hiddenfield',
                    name : 'id'
                }],
                buttons : ['->', {
                    text : l10n('save'),
                    reference : 'comp_detail_save',
                    formBind : true,
                    handler : 'componentDetailSave',
                    viewtype : viewtype
                }, {
                    text : l10n('reset'),
                    reference : 'comp_detail_reset',
                    handler : 'componentDetailReset',
                    viewtype : viewtype
                }, '->']
            }]
        }, {
            xtype : 'grid',
            reference : 'prefixgrid',
            title : l10n('gateway-prefix'),
            titleAlign : 'center',
            margin : 5,
            padding : 0,
            border : 1,
            cls : 'white-header-footer',
            bind : {
                store : '{GatewayPrefixStore}',
                hidden : '{hideGatewayGrid}'
            },
            columns : [{
                text : l10n('gateway-id'),
                dataIndex : 'gatewayID',
                flex : 1
            }, {
                text : l10n('prefix'),
                dataIndex : 'prefix',
                flex : 1
            }, {
                text : l10n('direction'),
                dataIndex : 'direction',
                flex : 1
            }],
            dockedItems : [{
                xtype : 'pagingtoolbar',
                cls : 'white-footer',
                bind : {
                    store : '{GatewayPrefixStore}'
                },
                dock : 'bottom',
                displayInfo : true,
                afterrender : function(pagingbar) {
                    pagingbar.down('button[itemId=refresh]').destroy();
                }
            }]
        }, {
            xtype : 'grid',
            reference : 'profilegrid',
            title : l10n('recorder-endpoints'),
            titleAlign : 'center',
            margin : 5,
            padding : 0,
            bind : {
                store : '{recorderEndPointStore}',
                hidden : '{hideRecorderGrid}'
            },
            border : 1,
            cls : 'white-header-footer',
            //store : Ext.create('SuperApp.store.components.ProfilesStore'),
            columns : [{
                text : l10n('super-components-rec-html-recorderid'),
                dataIndex : 'recID',
                flex : 1
            }, {
                text : l10n('endpoint-guid'),
                dataIndex : 'endpointGUID',
                flex : 1
            }, {
                text : l10n('prefix'),
                dataIndex : 'prefix',
                flex : 1
            }, {
                text : l10n('super-components-rec-html-info'),
                dataIndex : 'description',
                flex : 1
            }, {
                text : l10n('status'),
                dataIndex : 'status',
                flex : 1
            }],
            dockedItems : [{
                xtype : 'pagingtoolbar',
                cls : 'white-footer',
                bind : {
                    store : '{recorderEndPointStore}'
                },
                dock : 'bottom',
                displayInfo : true,
                afterrender : function(pagingbar) {
                    pagingbar.down('button[itemId=refresh]').destroy();
                }
            }]
        }];
        me.callParent(arguments);
    }
});

/**
 * @class LDAPOverlay
 */
Ext.define('AdminApp.view.settings.auth.LDAPOverlay', {
    extend: 'Ext.window.Window',
    alias: 'widget.ldapoverlay',

    requires: ['AdminApp.view.settings.auth.SamlAttrMappingGrid'],

    modal: true,

    border: false,
    closeAction: 'destroy',
    viewModel: {
        type: 'AuthenticationViewModel'
    },

    controller: 'AuthenticationViewController',

    errorReader: {
        type: 'xml',
        record: 'field',
        model: 'AdminApp.model.Field',
        success: '@success'
    },
    bind: {
        title: '{windowTitle}'
    },
    initComponent: function() {
        var me = this;
        switch (me.viewtype) {
            case 'webserver':

                me.items = [{
                    xtype: 'form',
                    border: false,
                    reference: 'ldapform',
                    viewVals: me.viewVals,
                    items: [{
                        xtype: 'textfield',
                        name: 'username',
                        fieldLabel: l10n('username'),
                        allowBlank: false,
                        padding: 10,
                        msgTarget: 'under'
                    }, {
                        xtype: 'textfield',
                        name: 'password',
                        inputType: 'password',
                        fieldLabel: l10n('password'),
                        allowBlank: false,
                        padding: 10,
                        msgTarget: 'under'
                    }],
                    buttonAlign: 'center',
                    buttons: ['->', {
                        text: l10n('submit'),
                        formBind: true,
                        viewtype: me.viewtype,
                        listeners: {
                            click: 'onClickSaveOverlay'
                        }
                    }, '->']
                }];
                break;
                 case 'restwebserver':

                me.items = [{
                    xtype: 'form',
                    border: false,
                    reference: 'ldapform',
                    viewVals: me.viewVals,
                    items: [{
                        xtype: 'textfield',
                        name: 'username',
                        fieldLabel: l10n('username'),
                        allowBlank: false,
                        padding: 10,
                        msgTarget: 'under'
                    }, {
                        xtype: 'textfield',
                        name: 'password',
                        inputType: 'password',
                        fieldLabel: l10n('password'),
                        allowBlank: false,
                        padding: 10,
                        msgTarget: 'under'
                    }],
                    buttonAlign: 'center',
                    buttons: ['->', {
                        text: l10n('submit'),
                        formBind: true,
                        viewtype: me.viewtype,
                        listeners: {
                            click: 'onClickSaveOverlay'
                        }
                    }, '->']
                }];
                break;
            case 'ldap':

                me.items = [{
                    xtype: 'form',
                    border: false,
                    reference: 'ldapform',
                    viewVals: me.viewVals,
                    items: [{
                        xtype: 'textfield',
                        name: 'username',
                        fieldLabel: l10n('username'),
                        allowBlank: false,
                        padding: 10,
                        msgTarget: 'under'
                    }, {
                        xtype: 'textfield',
                        name: 'password',
                        inputType: 'password',
                        fieldLabel: l10n('password'),
                        allowBlank: false,
                        padding: 10,
                        msgTarget: 'under'
                    }],
                    buttonAlign: 'center',
                    buttons: ['->', {
                        text: l10n('submit'),
                        disabled: true,
                        formBind: true,
                        viewtype: me.viewtype,
                        listeners: {
                            click: 'onClickSaveOverlay'
                        }
                    }, '->']
                }];
                break;
            case 'testattribute':
                me.items = [{
                    xtype: 'form',
                    border: false,
                    reference: 'ldapform',
                    viewVals: me.viewVals,
                    items: [{
                        xtype: 'textfield',
                        name: 'username',
                        fieldLabel: l10n('username'),
                        allowBlank: false,
                        padding: 10,
                        msgTarget: 'under'
                    }, {
                        xtype: 'textfield',
                        name: 'password',
                        inputType: 'password',
                        fieldLabel: l10n('password'),
                        allowBlank: false,
                        padding: 10,
                        msgTarget: 'under'
                    }],
                    buttonAlign: 'center',
                    buttons: ['->', {
                        text: l10n('submit'),
                        formBind: true,
                        viewtype: me.viewtype,
                        listeners: {
                            click: 'onClickSaveOverlay'
                        }
                    }, '->']
                }];
                break;
            case 'editLDAPattribute':
                me.getViewModel().getStore('ldapMappingStore').load();
                me.items = [{
                    xtype: 'form',
                    frame: true,
                    border: false,
                    items: [{

                        xtype: 'grid',
                        width: 600,
                        minHeight: 300,
                        reference: 'ldapmappingGrid',
                        bind: {
                            store: '{ldapMappingStore}'
                        },
                        plugins: {
                            ptype: 'rowediting',
                            clicksToEdit: 2,
                            autoCancel: false,
                            pluginId: 'ldapRowEditId',
                        },
                        columns: [{
                            text: 'ID',
                            dataIndex: 'mappingID',
                            hidden: true
                        }, {
                            text: "tenant",
                            dataIndex: 'tenantID',
                            hidden: true
                        }, {
                            text: 'attribute',
                            dataIndex: 'vidyoAttributeName',
                            hidden: true,
                            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                                var scope = me;
                                var idpAttributeValue = scope.lookupReference("ldapmappingGrid").columns[4];
                                var idpAttribute = Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(record.get('ldapAttributeName')));
                                idpAttributeValue.setEditor({
                                        xtype: 'textfield',
                                        allowBlank: true,
                                        value: idpAttribute 
                                });          
                                
                                var defaultAttributeValue = scope.lookupReference("ldapmappingGrid").columns[5];
                                var defaultAttribute = Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(record.get('defaultAttributeValue')));
                                if (value == "Group") {
                                    defaultAttributeValue.setEditor({
                                        xtype: 'combo',
                                        bind: {
                                            store: '{groupds}'
                                        },                            
                                        defaultValue: defaultAttribute,                                      
                                        valueField: 'groupName',
                                        displayField: 'groupName',
                                        editable: false,
                                        value:defaultAttribute
                                       
                                    });
                                } else if (value == 'UserType') {
                                    defaultAttributeValue.setEditor({
                                        xtype: 'combo',
                                        bind: {
                                            store: '{roleds}'
                                        },
                                        defaultValue: defaultAttribute,
                                        valueField: 'roleName',
                                        displayField: 'roleName',
                                        editable: false,
                                        value:defaultAttribute
                                        
                                    });
                                } else if (value == 'Proxy') {
                                    defaultAttributeValue.setEditor({
                                        xtype: 'combo',
                                        bind: {
                                            store: '{proxyds}'
                                        },
                                        defaultValue: defaultAttribute,
                                        valueField: 'proxyName',
                                        displayField: 'proxyName',
                                        editable: false,
                                        value:defaultAttribute
                                    });
                                } else if (value == 'LocationTag') {
                                    defaultAttributeValue.setEditor({
                                        xtype: 'combo',
                                        bind: {
                                            store: '{locationTagds}'
                                        },
                                        defaultValue: defaultAttribute,
                                        valueField: 'locationTag',
                                        displayField: 'locationTag',
                                        editable: false,                                     
                                        value:defaultAttribute
                                    });
                                } else if (value == "UserName" || value == "Extension" || value=="Thumbnail Photo" || value=="User Groups") {
                                    defaultAttributeValue.setEditor(null);
                                } else {
                                    defaultAttributeValue.setEditor({
                                        xtype: 'textfield',
                                        allowBlank: true,
                                        value: Ext.util.Format.stripTags(record.get('defaultAttributeValue'))
                                    })
                                }
                            }
                        }, {
                            text: l10n('portal-attribute-name'),
                            dataIndex: 'vidyoAttributeDisplayName',
                            width: '26%',
                            sortable: false,
                            menuDisabled: true
                        }, {
                            text: l10n('ldap-attribute-name'),
                            dataIndex: 'ldapAttributeName',
                            width: '26%',
                            sortable: false,
                            menuDisabled: true,
                            editable: true,
                            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                                return Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(value));
                            }
                        }, {
                            text: l10n('default-value'),
                            dataIndex: 'defaultAttributeValue',
                            width: '25%',
                            sortable: false,
                            menuDisabled: true,
                            editable: true,
                            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                                if (record.data.vidyoAttributeName == "UserName" || record.data.vidyoAttributeName == "Extension" || record.data.vidyoAttributeName == "Thumbnail Photo" || record.data.vidyoAttributeName == "User Groups") {
                                    metaData.css = 'red-row';
                                    return Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(value));
                                } else {
                                    return Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(value));
                                }
                            }
                        }, {
                            xtype: 'actioncolumn',
                            width: '20%',
                            text: l10n('value-mapping'),
                            align: 'center',
                            items: [{
                                icon: '',
                                handler: function(grid, rowIndex, colIndex, btn, e) {
                                    var rec = grid.getStore().getAt(rowIndex);
                                    if (e.target.classList.contains('icon-value-map')) {
                                        var overlay = Ext.widget('ldapAttributemappingWin', {
                                            records: rec
                                        });
                                        if (overlay) {
                                            overlay.show();
                                        }
                                    }
                                },
                                getClass: function(value, meta, record, rowIndex, colIndex, store) {
                                    this.items[0].tooltip = record.data.qtipAttrValueMapping;

                                    return record.data.attrValueMapping; // No special class needed in this case. Or... 
                                }
                            }]
                        }]



                    }],
                    buttonAlign: 'center',
                    buttons: [{
                        xtype: 'button',
                        text: l10n('save'),
                        popupWindow: 'ldap',
                        handler: 'onClickSaveAttributeGrid'
                    }, {
                        xtype: 'button',
                        text: l10n('close'),
                        popupWindow: 'ldap',
                        handler: 'onClickCloseAttributeGrid'
                    }]

                }];
                break;
            case 'editSAMLAttribute':
                me.getViewModel().getStore('samlMappingStore').load();
                me.items = [{
                    xtype: 'form',
                    frame: true,
                    border: false,
                    items: [{
                        xtype: 'grid',
                        width: 600,
                        minHeight: 300,
                        reference: 'samlgrid',
                        bind: {
                            store: '{samlMappingStore}'
                        },
                        plugins: {
                            ptype: 'rowediting',
                            clicksToEdit: 2,
                            autoCancel: false,
                            pluginId: 'samlRowEditId',
                        },
                        columns: [{
                            text: 'ID',
                            dataIndex: 'mappingID',
                            hidden: true
                        }, {
                            text: "tenant",
                            dataIndex: 'tenantID',
                            hidden: true
                        }, {
                            text: 'attribute',
                            dataIndex: 'vidyoAttributeName',
                            hidden: true,
                            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                                var scope = me;
                                var idpAttributeValue = scope.lookupReference("samlgrid").columns[4];
                                var idpAttribute = Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(record.get('idpAttributeName')));
                                idpAttributeValue.setEditor({
                                        xtype: 'textfield',
                                        allowBlank: true,
                                        value: idpAttribute 
                                });
                                
                                var defaultAttributeValue = scope.lookupReference("samlgrid").columns[5];
                                var defaultAttribute = Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(record.get('defaultAttributeValue')));
                                if (value == "Group") {
                                    defaultAttributeValue.setEditor({
                                        xtype: 'combo',
                                        bind: {
                                            store: '{groupds}'
                                        },                                       
                                        defaultValue: defaultAttribute,                                       
                                        valueField: 'groupName',
                                        displayField: 'groupName',
                                        value:defaultAttribute,
                                        editable: false                                
                                       
                                    });
                                } else if (value == 'UserType') {
                                    defaultAttributeValue.setEditor({
                                        xtype: 'combo',
                                        bind: {
                                            store: '{samlroleds}'
                                        },
                                        defaultValue: defaultAttribute,                                       
                                        valueField: 'roleName',
                                        displayField: 'roleName',
                                        value:defaultAttribute,
                                        editable: false                                       
                                       
                                    });
                                } else if (value == 'Proxy') {
                                    defaultAttributeValue.setEditor({
                                        xtype: 'combo',
                                        bind: {
                                            store: '{proxyds}'
                                        },
                                        defaultValue: defaultAttribute,
                                        value:defaultAttribute,
                                        triggerAction: 'all',
                                        valueField: 'proxyName',
                                        displayField: 'proxyName',
                                        editable: false 
                                    });
                                } else if (value == 'LocationTag') {
                                    defaultAttributeValue.setEditor({
                                        xtype: 'combo',
                                        bind: {
                                            store: '{locationTagds}'
                                        },                                       
                                        defaultValue: defaultAttribute,
                                        value:defaultAttribute,
                                        valueField: 'locationTag',
                                        displayField: 'locationTag',
                                        editable: false  
                                    });
                                } else if (value == "UserName" || value == "Extension" || value == "Thumbnail Photo" || value == "User Groups") {
                                    defaultAttributeValue.setEditor(null);
                                } else {
                                    defaultAttributeValue.setEditor({
                                        xtype: 'textfield',
                                        allowBlank: true,
                                        value: Ext.util.Format.stripTags(record.get('defaultAttributeValue'))
                                    })
                                }
                            }
                        }, {
                            text: l10n('portal-attribute-name'),
                            dataIndex: 'vidyoAttributeDisplayName',
                            sortable: false,
                            flex: 1,
                            menuDisabled: true
                        }, {
                            text: l10n('saml-idp-attribute-name'),
                            dataIndex: 'idpAttributeName',
                            sortable: false,
                            menuDisabled: true,
                            flex: 1,
                            editable: true,
                            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                                return Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(value));
                            }
                        }, {
                            text: l10n('default-value'),
                            dataIndex: 'defaultAttributeValue',
                            sortable: false,
                            menuDisabled: true,
                            flex: 1,
                            editable: true,
                            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                                if (record.data.vidyoAttributeName == "UserName" || record.data.vidyoAttributeName == "Extension" || record.data.vidyoAttributeName == "User Groups") {
                                    metaData.css = 'red-row';
                                    return Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(value));
                                } else {
                                    return Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(value));
                                }
                            }
                        }, {
                            xtype: 'actioncolumn',
                            width: 50,
                            text: l10n('value-mapping'),
                            align: 'center',
                            items: [{
                                icon: '',
                                handler: function(grid, rowIndex, colIndex, btn, e) {
                                    if (e.target.classList.contains('icon-value-map')) {
                                        var record = grid.getStore().getAt(rowIndex);
                                        var samlAttrMappingGrid = Ext.create('AdminApp.view.settings.auth.SamlAttrMappingGrid', {
                                            record: record
                                        });
                                        samlAttrMappingGrid.show();
                                    }
                                },
                                getClass: function(value, meta, record, rowIndex, colIndex, store) {
                                    this.items[0].tooltip = record.data.qtipAttrValueMapping;

                                    return record.data.attrValueMapping; // No special class needed in this case. Or... 
                                }
                            }]
                        }]



                    }],
                    buttonAlign: 'center',
                    buttons: [{
                        xtype: 'button',
                        text: l10n('save'),
                        popupWindow: 'saml',
                        handler: 'onClickSaveAttributeGrid'
                    }, {
                        xtype: 'button',
                        text: l10n('close'),
                        popupWindow: 'saml',
                        handler: 'onClickCloseAttributeGrid'
                    }]

                }];
                break
        }

        me.callParent(arguments);
    },
    listeners: {
        render: 'windowOnRender'
    }
});
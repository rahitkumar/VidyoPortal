Ext.define('SuperApp.view.cloud.endPointRules.RuleInfoForm', {
    extend : 'Ext.window.Window',
    xtype : 'ruleInfoForm',
    requires : ['SuperApp.view.cloud.endPointRules.EPRController', 'SuperApp.view.cloud.endPointRules.EPRModel'],
    title : l10n('add-ruleset'),
    modal : true,
    width : 600,
    closeAction : 'destroy',
    ruleSetGrid : null,
    layout : 'fit',
    initComponent : function() {
        var me = this;
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
                border : 1,
                margin : 0,
                layout : {
                    layout : 'vbox',
                    align : 'center'
                },
                items : ['->', {
                    xtype : 'title',
                    bind : {
                        text : '<span class="header-title">' + l10n('ruleset') + '</span>',
                    },
                    textAlign : 'center'
                }, '->']
            }, {
                xtype : 'form',
                border : false,
                margin : 0,
                padding : 0,
                defaults : {
                    padding : 10,
                },
                items : [{
                    xtype : 'hidden',
                    name : 'id'
                }, {
                    xtype : 'hidden',
                    name : 'ruleSetOrder'
                }, {
                    xtype : 'container',
                    layout : {
                        type : 'hbox',
                        align : 'stretch'
                    },
                    items : [{
                        xtype : 'radiofield',
                        name : 'ruleType',
                        boxLabel : l10n('ip-rule'),
                        inputValue : 'ipRule',
                        flex : 1,
                        listeners : {
                            change : 'onRadioSelectionChange'
                        }
                    }, {
                        xtype : 'container',
                        disabled : true,
                        flex : 3,
                        items : [{
                            xtype : 'checkbox',
                            name : 'localIPCheckbox',
                            boxLabel : l10n('specify-local-ip-range'),
                            listeners : {
                                change : 'localIPRangeChange'
                            }
                        }, {
                            xtype : 'container',
                            disabled : true,
                            reference : 'localIPInfo',
                            layout : {
                                type : 'hbox',
                                align : 'stretch'
                            },
                            items : [{
                                xtype : 'textfield',
                                name : 'localIP',
                                vtype : 'IPv4orIPv6',
                                flex : 2,
                                maxLength : 20,
                                listeners : {
                                    specialkey : 'localIPTabPressed'
                                }
                            }, {
                                xtype : 'label',
                                html : '/'
                            }, {
                                xtype : 'textfield',
                                name : 'localIPAdditional',
                                regex:/^[0-9]{0,3}$/i,
                                
                                flex : 1
                            }]
                        }, {
                            xtype : 'checkbox',
                            name : 'externalIPCheckbox',
                            boxLabel : l10n('specify-external-ip-range'),
                            listeners : {
                                change : 'externalIPRangeChange'
                            }
                        }, {
                            xtype : 'container',
                            disabled : true,
                            reference : 'externalIPInfo',
                            layout : {
                                type : 'hbox',
                                align : 'stretch'
                            },
                            items : [{
                                xtype : 'textfield',
                                name : 'externalIP',
                                vtype : 'IPv4orIPv6',
                                flex : 2,
                                maxLength : 20,
                                listeners : {
                                    specialkey : 'externalIPTabPressed'
                                }
                            }, {
                                xtype : 'label',
                                html : '/'
                            }, {
                                xtype : 'textfield',
                                name : 'externalIPAdditional',
                                regex:/^[0-9]{0,3}$/i,
                                  flex : 1
                            }]
                        }]
                    }]
                }, {
                    xtype : 'container',
                    layout : {
                        type : 'hbox',
                        align : 'stretch'
                    },
                    items : [{
                        xtype : 'radiofield',
                        boxLabel : l10n('user-location-tag'),
                        name : 'ruleType',
                        inputValue : 'locationTag',
                        flex : 1,
                        listeners : {
                            change : 'onRadioSelectionChange'
                        }
                    }, {
                        xtype : 'combo',
                        editable : false,
                        name : 'locationTagCombo',
                        tpl: Ext.create('Ext.XTemplate',
                            '<ul class="x-list-plain"><tpl for=".">',
                            '<li role="option" class="x-boundlist-item">{locationTag:htmlEncode}</li>',
                            '</tpl></ul>'
                        ),
                        bind : {
                            store : '{ruleLocations}'
                        },
                        displayField : 'locationTag',
                        valueField : 'locationID',
                        queryMode : 'local',
                        disabled : true,
                        flex : 3
                    }]
                }, {
                    xtype : 'container',
                    layout : {
                        type : 'hbox',
                        align : 'stretch'
                    },
                    items : [{
                        xtype : 'radiofield',
                        boxLabel : l10n('endpoint-id'),
                        name : 'ruleType',
                        inputValue : 'endpointID',
                        flex : 1,
                        listeners : {
                            change : 'onRadioSelectionChange'
                        }
                    }, {
                        xtype : 'textfield',
                        name : 'endPointID',
                        disabled : true,
                        maxLength : 150,
                        vtype: 'nohtml',
                        msgTarget : 'under',
                        flex : 3
                    }]
                }, {
                    xtype : 'toolbar',
                    border : 1,
                    margin : 0,
                    layout : {
                        layout : 'vbox',
                        align : 'center'
                    },
                    items : ['->', {
                        formBind : true,
                        text : l10n('save'),
                        margin : '10 0 0 0',
                        handler : 'saveRuleset'
                    }, '->']
                }]
            }]
        }];
        me.callParent(arguments);
    }
});

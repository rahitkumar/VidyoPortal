Ext.define('SuperApp.view.cloud.endPointRules.EPR', {
    extend : 'Ext.panel.Panel',

    xtype : 'epr',

    requires : ['SuperApp.view.cloud.endPointRules.EPRController', 'SuperApp.view.cloud.endPointRules.EPRModel'],

    reference : 'endPointRulesPanel',
    cls : 'pools-grid',

    border : 1,

    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    items : [{
        xtype : 'container',
        html : 'Endpoint Rules'
    }],

    initComponent : function() {
        var me = this;

        var topToolbar = Ext.create('Ext.toolbar.Toolbar', {
            cls : 'white-header-footer',
            border : 0,
            items : [{
                xtype : 'button',
                iconCls : 'x-fa fa-refresh',
                handler : 'refreshGrid'
            }, {
                xtype : 'tbseparator',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'button',
                text : l10n('add-rule'),
                iconCls : 'x-fa fa-plus-circle',
                handler : 'addRule',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'tbseparator',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'button',
                text : l10n('delete-rule'),
                iconCls : 'x-fa fa-minus-circle',
                handler : 'deleteRule',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'tbfill'
            }]

        });

        var rowEditing = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit : 2
        });

        var gridPanelModified = Ext.create('Ext.grid.Panel', {
            bind : {
                hidden : '{!isModified}',
                store : '{eprSummary}'
            },
            border : true,
            style : {
                'border-top' : '1px solid #CCC',
                'border-bottom' : '1px solid #CCCCCC'
            },
            selModel : Ext.create('Ext.selection.CheckboxModel', {
                showHeaderCheckbox : true,
                injectCheckbox : 'first',
                mode : 'SIMPLE',
                checkOnly : true
            }),
            reference : 'rulesGrid',
            listeners : {
                edit : 'editModifiedGrid',
                validateedit : 'validateEditModifiedGrid'
            },
            plugins : [{
                ptype : 'subtable',
                selectRowOnExpand : true,
                expandOnDblClick : false,
                columns : [/*{
                    text : l10n('order'),
                    dataIndex : 'ruleSetOrder',
                    hidden : true,
                    width : 100,
                    style : {
                        'text-align' : 'center',
                        'align' : 'center'
                    }
                },*/ {
                    text : l10n('ruleset'),
                    dataIndex : 'ruleSet',
                    style : {
                        'text-align' : 'center',
                        'align' : 'right'
                    },
                    renderer : function(value, mataData, record, rowIndex, colIndex, store, view) {
                        var returnString = "";
                        if (record.get('privateIP')) {
                            returnString += l10n('local-ip') + " : " + record.get('privateIP') + "/" + record.get('privateIpCIDR');
                        }

                        if (record.get('publicIP')) {
                            if (returnString != "") {
                                returnString += "; ";
                            }
                            returnString += l10n('external-ip') + " : " + record.get('publicIP') + "/" + record.get('publicIpCIDR');
                        }

                        if (record.get('locationTagID')) {
                            returnString += l10n('user-location-tag') + " : " + Ext.util.Format.htmlEncode(me.lookupViewModel().storeInfo.ruleLocations.findRecord('locationID', record.get('locationTagID')).get('locationTag'));
                        }

                        if (record.get('endpointID')) {
                            returnString += l10n('endpoint-id') + " : " + record.get('endpointID');
                        }

                        return returnString;
                    }
                }],
                getAssociatedRecords : function(rec) {
                    var result = [],
                        records = rec.get('ruleSet');

                    records.sort(function(a, b) {
                        return a.ruleSetOrder > b.ruleSetOrder;
                    });
                    Ext.each(records, function(data) {
                        result.push(Ext.create('Ext.data.Record', data));
                    });
                    return result;
                }
            }, rowEditing],
            columns : [{
                dataIndex : 'ruleOrder',
                text : l10n('order'),
                flex : 1,
                editor : {
                    xtype : 'numberfield',
                    allowBlank : false
                }
            }, {
                dataIndex : 'ruleName',
                text : l10n('rule-name'),
                flex : 4,
                renderer : Ext.util.Format.htmlEncode
            }, {
                dataIndex : 'poolPriorityList',
                text : l10n('priority'),
                flex : 2,
                renderer : function(value, mataData, record, rowIndex, colIndex, store, view) {
                    if (value && value.priorityListName) {
                        return '<span>' + Ext.util.Format.htmlEncode(value.priorityListName) + '</span><span><img id="disclosure-image-rule-' + record.get("id") + '" style="float:right; cursor:pointer;" data-disclosure=true src="js/SuperApp/resources/images/edit-icon.png"/></span>';
                    } else {
                        return '<span><img id="disclosure-image-rule-' + record.get("id") + '" style="float:right; cursor:pointer;" data-disclosure=true src="js/SuperApp/resources/images/edit-icon.png"/></span>';
                    }
                }
            }]

        });

        gridPanelModified.on('rowClick', 'erpRowClick');

        var gridPanelActive = Ext.create('Ext.grid.Panel', {
            bind : {
                hidden : '{isModified}',
                store : '{eprSummary}'
            },
            border : true,
            style : {
                'border-top' : '1px solid #CCCCCC',
                'border-bottom' : '1px solid #CCCCCC'
            },
            listeners : {
                edit : 'onRuleEdit',
                canceledit : 'onRuleCancelEdit',
                validateedit : 'onRuleValidateEdit'
            },
            plugins : [{
                ptype : 'subtable',
                selectRowOnExpand : true,
                expandOnDblClick : false,
                columns : [/*{
                    text : l10n('order'),
                    dataIndex : 'ruleSetOrder',
                    width : 100,
                    hidden : true,
                    style : {
                        'text-align' : 'center',
                        'align' : 'center'
                    }
                },*/ {
                    text : l10n('ruleset'),
                    dataIndex : 'ruleSet',
                    style : {
                        'text-align' : 'center',
                        'align' : 'right'
                    },
                    renderer : function(value, mataData, record, rowIndex, colIndex, store, view) {
                        var returnString = "";
                        if (record.get('privateIP')) {
                            returnString += l10n('local-ip') + " : " + record.get('privateIP') + "/" + record.get('privateIpCIDR');
                        }

                        if (record.get('publicIP')) {
                            if (returnString != "") {
                                returnString += "; ";
                            }
                            returnString += l10n('external-ip') + " : " + record.get('publicIP') + "/" + record.get('publicIpCIDR');
                        }

                        if (record.get('locationTagID')) {
                            returnString += l10n('user-location-tag') + " : " + me.lookupViewModel().storeInfo.ruleLocations.findRecord('locationID', record.get('locationTagID')).get('locationTag');
                        }

                        if (record.get('endpointID')) {
                            returnString += l10n('endpoint-id') + " : " + record.get('endpointID');
                        }

                        return returnString;
                    }
                }],
                getAssociatedRecords : function(rec) {
                    var result = [],
                        records = rec.get('ruleSet');

                    records.sort(function(a, b) {
                        return a.ruleSetOrder > b.ruleSetOrder;
                    });
                    Ext.each(records, function(data) {
                        result.push(Ext.create('Ext.data.Record', data));
                    });
                    return result;
                }
            }],
            columns : [{
                dataIndex : 'ruleOrder',
                text : l10n('order'),
                flex : 1
            }, {
                dataIndex : 'ruleName',
                text : l10n('rule-name'),
                flex : 4
            }, {
                dataIndex : 'poolPriorityList',
                text : l10n('priority'),
                flex : 2,
                renderer : function(value, mataData, record, rowIndex, colIndex, store, view) {
                    if (value && value.priorityListName) {
                        return '<span>' + Ext.util.Format.htmlEncode(value.priorityListName) + '</span>';
                    } else {
                        return '<span></span>';
                    }
                }
            }]

        });

        var bottomToolbar = Ext.create('Ext.toolbar.Toolbar', {
            cls : 'white-header-footer',
            docked : 'bottom',
            border : 0,
            items : [{
                xtype : 'button',
                iconCls : 'x-fa fa-refresh',
                handler : 'refreshGrid'
            }, {
                xtype : 'tbseparator',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'button',
                text : l10n('add-rule'),
                iconCls : 'x-fa fa-plus-circle',
                handler : 'addRule',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'tbseparator',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'button',
                text : l10n('delete-rule'),
                iconCls : 'x-fa fa-minus-circle',
                handler : 'deleteRule',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'tbfill'
            }]

        });

        var bottomToolbar2 = Ext.create('Ext.toolbar.Toolbar', {
            cls : 'white-header-footer',
            docked : 'bottom',
            border : 0,
            style : {
                'border' : '0px',
                'border-top' : '1px solid #CCCCCC',
            },
            items : [{
                xtype : 'tbfill'
            }, {
                xtype : 'button',
                bind : {
                    disabled : '{!isPoolsActivate}'
                },
                text : l10n('activate'),
                handler : 'activatePool'
            }, {
                xtype : 'button',
                bind : {
                    disabled : '{!isPoolsActivate}'
                },
                text : l10n('discard'),
                handler : 'onClickDiscardPools',
            }]

        });

        this.items = [topToolbar, gridPanelModified, gridPanelActive, bottomToolbar, bottomToolbar2];

        this.callParent();
    }
});

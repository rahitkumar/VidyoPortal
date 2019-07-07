Ext.define('SuperApp.view.cloud.poolList.PoolList', {
    extend : 'Ext.panel.Panel',

    xtype : 'poolList',

    requires : ['Ext.ux.grid.SubTable'],
    cls : 'pools-grid',
    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    border : 1,

    reference : 'priorityGrid',

    initComponent : function() {

        var topToolbar = Ext.create('Ext.toolbar.Toolbar', {
          //  cls : 'white-header-footer',
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
                text : l10n('add-priority-list'),
                iconCls : 'x-fa fa-plus-circle',
                handler : 'addPriorityList',
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
                text : l10n('delete-priority-list'),
                iconCls : 'x-fa fa-minus-circle',
                handler : 'deletePriorityList',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'tbfill'
            }]
        });

        var rowExpander = Ext.create('Ext.grid.plugin.RowExpander', {
            expandOnDblClick : false,
            rowBodyTpl : new Ext.XTemplate('<table cellspacing=0 style="border-left:1px solid #c1c1c1;border-top:1px solid #c1c1c1;margin-left:35%; width: 65%;">', '{poolPriorityMap:this.formatChange}', '</table>', {
                formatChange : function(pools) {
                    var returnValue = '<tr><td style="width:30%;border-right:1px solid #c1c1c1;border-bottom:1px solid #c1c1c1;">Priority</td><td style="width:70%;border-right:1px solid #c1c1c1;border-bottom:1px solid #c1c1c1;">' + l10n('pools') + '</td></tr>';
                    if (pools.length) {
                        var priorityPools = [];

                        pools.forEach(function(element, index, array) {
                            if (!priorityPools[element.order] || priorityPools[element.order].length <= 0) {
                                priorityPools[element.order] = [];
                            }
                            priorityPools[element.order].push(element.pool.name);
                        });

                        priorityPools.forEach(function(element, index, array) {
                            element.forEach(function(pool, innerIndex, pools) {
                                returnValue += '<tr>';
                                if (innerIndex == 0) {
                                    returnValue += '<td style="width:30%;border-right:1px solid #c1c1c1;border-bottom:1px solid #c1c1c1;" rowspan=' + pools.length + '>' + index + '</td>';
                                }

                                returnValue += '<td style="width:70%;border-right:1px solid #c1c1c1;border-bottom:1px solid #c1c1c1;">' + pool + '</td>';
                                returnValue += '</tr>';
                            });
                        });
                        return returnValue;
                    }
                    return returnValue + '<td style="width:100%;border-right:1px solid #c1c1c1;border-bottom:1px solid #c1c1c1;" colspan=2>' + l10n('no-pools-in-this-priority-list') + '</td>';
                }
            })
        });

        var gridPanelModified = Ext.create('Ext.grid.Panel', {
            bind : {
                hidden : '{!isModified}',
                store : '{poolListSummary}'
            },
            border : 1,
            style : {
                'border' :'1px',
                'border-top' : '1px solid #CCC',
                'border-bottom' : '1px solid #CCCCCC'
            },
            reference : 'priorityListGrid',
            plugins : [{
                ptype : 'subtable',
                selectRowOnExpand : true,
                columns : [{
                    text : l10n('priority'),
                    dataIndex : 'order',
                    width : 100,
                    style : {
                        'text-align' : 'center',
                        'align' : 'center'
                    }
                }, {
                    text : l10n('pools'),
                    dataIndex : 'pool',
                    style : {
                        'text-align' : 'center',
                        'align' : 'right'
                    },
                    renderer : function(val) {
                        return val.name;
                    }
                }],
                getAssociatedRecords : function(rec) {
                    var result = [];
                    Ext.each(rec.get('poolPriorityMap'), function(data) {
                        result.push(Ext.create('Ext.data.Record', data));
                    });
                    return result;
                }
            }],
            columns : [{
                dataIndex : 'priorityListName',
                text : l10n('pool-priority-list'),
                flex : 1,
                editor : {
                    allowBlank : false
                },
                renderer : function(value, metadata, record, rowIndex, ColIndex, store, view) {                	
                    return '<span>' +  value + '</span><img id="disclosure-image-priority-' + record.get("id") + '" style="float:right; cursor:pointer;" data-disclosure=true src="js/SuperApp/resources/images/edit-icon.png"/>';
                }
            }]

        });

        gridPanelModified.on('rowClick', 'priorityListClick');

        var gridPanelActive = Ext.create('Ext.grid.Panel', {
            bind : {
                hidden : '{isModified}',
                store : '{poolListSummary}'
            },
            border : 1,
            style : {
                'border' :'1px',
                'border-top' : '1px solid #CCC',
                'border-bottom' : '1px solid #CCCCCC'
            },
            plugins : [{
                ptype : 'subtable',
                association : 'poolPriorityMap',
                selectRowOnExpand : true,
                columns : [{
                    text : l10n('priority'),
                    dataIndex : 'order',
                    width : 100
                }, {
                    text : l10n('pools'),
                    dataIndex : 'pool',
                    renderer : function(val) {
                        return val.name;
                    }
                }],
                getAssociatedRecords : function(rec) {
                    var result = [];
                    Ext.each(rec.get('poolPriorityMap'), function(data) {
                        result.push(Ext.create('Ext.data.Record', data));
                    });
                    return result;
                }
            }],
            columns : [{
                dataIndex : 'priorityListName',
                text : l10n('pool-priority-list'),
                flex : 1,
                renderer: Ext.util.Format.htmlEncode
            }]
        });

        var bottomToolbar = Ext.create('Ext.toolbar.Toolbar', {
          //  cls : 'white-header-footer',
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
                text : l10n('add-priority-list'),
                iconCls : 'x-fa fa-plus-circle',
                handler : 'addPriorityList',
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
                text : l10n('delete-priority-list'),
                iconCls : 'x-fa fa-minus-circle',
                handler : 'deletePriorityList',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'tbfill'
            }]
        });

        var bottomToolbar2 = Ext.create('Ext.toolbar.Toolbar', {
            //cls : 'white-header-footer',
            docked : 'bottom',
            style : {
                'border' :'0px',
                'border-top' : '1px solid #CCC'
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
                handler : 'onClickDiscardPools'
            }]

        });

        this.items = [topToolbar, gridPanelModified, gridPanelActive, bottomToolbar, bottomToolbar2];

        this.callParent();
    }
});

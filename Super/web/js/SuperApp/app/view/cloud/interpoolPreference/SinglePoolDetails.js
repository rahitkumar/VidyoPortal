Ext.define('SuperApp.view.cloud.interpoolPreference.SinglePoolDetails', {
    extend : 'Ext.container.Container',

    xtype : 'singlePoolDetails',
    
    reference : 'singlepooldetails',

    requires : ['Ext.container.Container', 'Ext.grid.Panel', 'Ext.layout.container.HBox'],

    layout : {
        type : 'hbox',
        align : 'stretch'
    },

    border : 1,
    
    deleteIds : [],

    initComponent : function() {
    	var me = this;
        var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToMoveEditor : 1,
            errorSummary : false,
            autoCancel : false
        });

        var dirStore = Ext.create('Ext.data.Store', {
            fields : ['dir', 'dirId'],
            data : [{
                dir : l10n('recieve-from'),
                dirId : 2
            }, {
                dir : l10n('connect-to'),
                dirId : 1
            }, {
                dir : l10n('dual-direction-sign'),
                dirId : 0
            }]
        });
        
        var items = [{
            xtype : 'container',
            flex : 1,
            border : 0,
            layout : {
                type : 'hbox',
                align : 'stretch'
            },
            items : [{
                xtype : 'container',
                flex : 1,
                border : 0,
                layout : {
                    type : 'vbox',
                    align : 'stretch'
                },
                items : [{
                    xtype : 'container',
                    flex : 1,
                    border : 0,
                    layout : {
                        type : 'hbox',
                        align : 'stretch'
                    },
                    items : [{
                        xtype : 'grid',
                        margin : 5,
                        reference : 'freeRoutersGrid',
                        flex : 1,
                        multiSelect : true,
                        viewConfig : {
                            plugins : {
                                ptype : 'gridviewdragdrop',
                                dragGroup : 'group2',
                                dropGroup : 'group1'
                            },
                            listeners : {
                                drop : 'onRecordDropFreeGrid'
                            }
                        },
                        bind : {
                            store : '{availableRoutersStore}'
                        },
                        columns : [{
                            text : l10n('available-routers'),
                            flex : 1,
                            sortable : true,
                            dataIndex : 'components',
                            renderer : function(record) {
                            	var icon = '';
                            	if (record.status === 'ACTIVE') {
                                    if (record.compStatus == "UP") {
                                        icon = '<img id="pool-routers-status-icon-' + record.id + '" src="js/SuperApp/resources/icons/up.png">&nbsp;';
                                    } else if (record.compStatus == "DOWN") {
                                        icon = '<img id="pool-routers-status-icon-' + record.id + '" src="js/SuperApp/resources/icons/down.png">&nbsp;';
                                    }
                                } else if (record.status === 'INACTIVE') {
                                    icon = '<img id="pool-routers-status-icon-' + record.id + '" src="js/SuperApp/resources/icons/disabledicon.png">&nbsp;';
                                } else {
                                    if (record.compStatus == "UP") {
                                        icon = '<img id="pool-routers-status-icon-' + record.id + '" src="js/SuperApp/resources/icons/newup.png">&nbsp;';
                                    } else if (record.compStatus == "DOWN") {
                                        icon = '<img id="pool-routers-status-icon-' + record.id + '" src="js/SuperApp/resources/icons/newdown.png">&nbsp;';
                                    }
                                }
                                return icon + record.name;
                            }
                        }]
                    }, {
                        xtype : 'grid',
                        reference : 'associatedRoutersGrid',
                        margin : 5,
                        flex : 1,
                        multiSelect : true,
                        viewConfig : {
                            plugins : {
                                ptype : 'gridviewdragdrop',
                                dragGroup : 'group1',
                                dropGroup : 'group2'
                            },
                            listeners : {
                                drop : 'onRecordDropAssociatedGrid'
                            }
                        },
                        bind : {
                            store : '{associatedRoutersStore}'
                        },
                        columns : [{
                            text : l10n('assigned-routers'),
                            flex : 1,
                            sortable : true,
                            dataIndex : 'components',
                            renderer : function(record) {
                            	var icon = '';
                            	if (record.status === 'ACTIVE') {
                                    if (record.compStatus == "UP") {
                                        icon = '<img id="pool-routers-status-icon-' + record.id + '" src="js/SuperApp/resources/icons/up.png">&nbsp;';
                                    } else if (record.compStatus == "DOWN") {
                                        icon = '<img id="pool-routers-status-icon-' + record.id + '" src="js/SuperApp/resources/icons/down.png">&nbsp;';
                                    }
                                } else if (record.status === 'INACTIVE') {
                                    icon = '<img id="pool-routers-status-icon-' + record.id + '" src="js/SuperApp/resources/icons/disabledicon.png">&nbsp;';
                                } else {
                                    if (record.compStatus == "UP") {
                                        icon = '<img id="pool-routers-status-icon-' + record.id + '" src="js/SuperApp/resources/icons/newup.png">&nbsp;';
                                    } else if (record.compStatus == "DOWN") {
                                        icon = '<img id="pool-routers-status-icon-' + record.id + '" src="js/SuperApp/resources/icons/newdown.png">&nbsp;';
                                    }
                                }
                                return icon + record.name;
                            }
                        }]
                    }]
                }, {
                    xtype : 'toolbar',
                    docked : 'bottom',
                    border : 1,
                    items : [{
                        xtype : 'button',
                        text : l10n('save-pool'),
                        handler : 'saveRoutersToPool'
                    }],
                    bind : {
                        hidden : '{!isModified}'
                    }
                }]
            }]
        }, {
            xtype : 'container',
            flex : 1,
            style : {
                'border' :'0px',
                'border-left' : '1px solid #ccc'
            },
            layout : {
                type : 'vbox',
                align : 'stretch'
            },
            items : [{
                xtype : 'grid',
                reference : 'connectionsGrid',
                margin : 5,
                flex : 1,
                plugins : [rowEditing],
                bind : {
                    store : '{poolConnections}'
                },
                listeners : {
                    edit : 'onConnectionListEdit',
                    canceledit : 'onConnectionListCancelEdit',
        		    beforeedit : 'onConnectionListBeforeEdit',
        		    rowdblclick: 'editConnection'
                },
                columns : [{
                    text : l10n('direction'),
                    flex : 2,
                    dataIndex : 'direction',
                    renderer: function(value) {
                    	return dirStore.findRecord('dirId', value).get('dir');
                    },
                    editor : {
                        xtype : 'combo',
                        queryMode : "local",
                        displayField : 'dir',
                        valueField: 'dirId',
                        store : dirStore,
                        editable : false
                    }
                }, {
                	text : l10n('pool-connection'),
                    flex : 4,
                    dataIndex : 'pool2',
                    renderer: function(value) {
                    	return me.lookupViewModel().storeInfo.poolsList.getById(value).get('name');
                    },
                    editor : {
                        xtype : 'combo',
                        displayField : 'name',
                        valueField: 'id',
                        queryMode : "local",
                        bind : {
                            store : '{availablePoolsStore}'
                        },
                        editable : false
                    }
                }, {
                    text : l10n('weight'),
                    flex : 1,
                    dataIndex : 'weight',
                    editor : {
                    	xtype: 'numberfield', 
        				allowExponential: false,
    	                allowBlank: false,
    	                minValue: 1,
	                	maxValue: 100
                    }
                }]
            }, {
                xtype : 'toolbar',
                border : 1,
                docked : 'bottom',
               items : [
                {
                    xtype : 'hidden',
                    name:'addCancelFlag',
                    id:'addCancelFlag',
                    reference : 'addCancelFlag',
                    value:false
                },
                {
                    xtype : 'button',
                    iconCls : 'x-fa fa-plus-circle',
                    text : l10n('add-connection'),
                    handler : 'addConnection'
                }, {
                    xtype : 'button',
                     iconCls : 'x-fa fa-edit',
                    text : l10n('edit-connection'),
                    handler : 'editConnection'
                }, {
                    xtype : 'button',
                     iconCls : 'x-fa fa-minus-circle',
                    text : l10n('delete-connection'),
                    handler : 'deleteConnection'
                }],
                bind : {
                    hidden : '{!isModified}'
                }
            }]
        }];

        this.items = items;

        this.callParent();
    }
});
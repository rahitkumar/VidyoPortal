Ext.define('SuperApp.view.cloud.locationTags.Location', {
    extend : 'Ext.panel.Panel',

    xtype : 'location',
    cls : 'pools-grid',
    requires : ['SuperApp.view.cloud.locationTags.LocationModel', 'SuperApp.view.cloud.locationTags.LocationController', 'Ext.container.Container'],

    controller : 'location',

    viewModel : {
        type : 'location'
    },

    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    border:1,
    items : [],

    initComponent : function() {
        var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToMoveEditor : 1,
            autoCancel : false,
            errorSummary:false
        });

        var topToolbar,
            gridPanel,
            bottomToolbar;

        topToolbar = Ext.create('Ext.toolbar.Toolbar', {
            //cls: 'vdo-toolbar',
            
            style : {
                'border' : '1px',
                'border-bottom' : '1px solid #CCC'
            },
            docked : 'bottom',
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
                text : l10n('add'),
                iconCls : 'x-fa fa-plus-circle',
                handler : 'addLocation',
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
                text : l10n('edit'),
                iconCls : 'x-fa fa-edit',
                handler : 'editLocation',
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
                text : l10n('delete'),
                iconCls : 'x-fa fa-minus-circle',
                handler : 'deleteLocation',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'tbfill'
            }]
        });

        gridPanel = Ext.create('Ext.grid.Panel', {
            reference : 'locationGrid',
            border : true,
            style : {
                'border' : '1px',
            },
            bind : {
                store : '{locationTagsStore}'
            },
            listeners : {
                edit : 'onLocationListEdit',
                canceledit : 'onLocationListCancelEdit',
                validateedit : 'onLocationListValidateEdit'
            },
            columns : [{
                dataIndex : 'locationTag',
                text : l10n('location-tags'),
                flex : 1,
                editor : {
                    maxLength : 40,
                    emptyText : l10n('please-specify-location-tag'),
                    vtype: 'nohtml',
                    allowBlank : false
                }
            }],
            plugins : [rowEditing]
           
        });

        bottomToolbar = Ext.create('Ext.toolbar.Toolbar', {
            cls : 'white-header-footer',
            docked : 'bottom',
            style : {
                'border' : '1px',
                'border-top' : '1px solid #CCC'
            },
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
                text : l10n('add'),
                iconCls : 'x-fa fa-plus-circle',
                handler : 'addLocation',
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
                text : l10n('edit'),
                iconCls : 'x-fa fa-edit',
                handler : 'editLocation',
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
                text : l10n('delete'),
                iconCls : 'x-fa fa-minus-circle',
                handler : 'deleteLocation',
                bind : {
                    disabled : '{!isModified}'
                }
            }, {
                xtype : 'tbfill'
            }]

        });

        this.items = [topToolbar, gridPanel, bottomToolbar];

        this.callParent();
    }
});

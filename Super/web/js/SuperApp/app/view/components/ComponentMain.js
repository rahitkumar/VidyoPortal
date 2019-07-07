Ext.define('SuperApp.view.components.ComponentMain', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.componentmain',
    requires : ['SuperApp.view.components.ComponentController', 'SuperApp.model.components.MainModel', 'SuperApp.view.components.ComponentModel', 'SuperApp.store.components.MainStore', 'SuperApp.store.components.ComponentFilter', 'SuperApp.store.components.statusFilter'],
    controller : 'Component',
    viewModel : {
        type : 'Components'
    },

    border : false,
    frame:false,

    layout : {
        type : 'card',
        align : 'stretch'
    },

    plugins : 'responsive',

    listeners : {
        afterrender : 'onComponentsActivate'
    },

    initComponent : function() {
        var topToolbar,
            upperToolbar,
            componentsGrid,
            overlayPanel,
            bottomToolbar;

        topToolbar = Ext.create('Ext.form.Panel', {
            border : false,
            docked : 'top',
            reference : 'topToolbar',
            margin : '5 5 5 20',
            layout : {
                type : 'hbox',
                align : 'center'
            },
            defaults : {
                margin : 5,
                labelWidth : 40
            },
            items : [{
                xtype : 'textfield',
                fieldLabel : l10n('component-name'),
                enableKeyEvents : true,
                labelWidth : 120,
                name : 'name',
                listeners : {
                    change : 'componentsFilterChanged'
                }
            }, {
                xtype : 'combobox',
                fieldLabel : l10n('type'),
                queryMode : 'local',
                displayField : 'name',
                editable : false,
                valueField : 'name',
                value : 'All',
                name : 'type',
                listeners : {
                    change : 'componentsFilterChanged'
                },
                store : Ext.create('SuperApp.store.components.ComponentFilter')
            }, {
                xtype : 'combobox',
                fieldLabel : l10n('status'),
                queryMode : 'local',
                displayField : 'name',
                editable : false,
                valueField : 'id',
                value : 'All',
                name : 'status',
                listeners : {
                    change : 'componentsFilterChanged'
                },
                store : Ext.create('SuperApp.store.components.statusFilter')
            }]
        });

        componentsGrid = Ext.create('Ext.grid.Panel', {
            margin : 5,
            cls : 'manage-tenant-grid',
            reference : 'componentsGrid',
            selType : 'checkboxmodel',
            allowDeselect : true,
            minHeight : 600,
            selModel : Ext.create('Ext.selection.CheckboxModel', {
                injectCheckbox : 'first',
                showHeaderCheckbox : false,
                mode : 'SINGLE',
                checkOnly : true,
                allowDeselect : true,
                renderer : function(value, metaData, record) {
                    return '<div class="' + Ext.baseCSSPrefix + 'grid-row-checker">&#160;</div>';
                }
            }),
            flex : 1,
            listeners : {
                cellclick : 'gridCellClick'
            },
            columns : [{
                dataIndex : 'status',
                text : l10n('status'),
                width : '10%',
                height : 30,
                sortable: true,
                getSortParam: function() {
                    return 'statusSorter';
                },
                renderer : function(value, metadata, record) {
                    var icon = "";
                    if (record.get('status') === 'ACTIVE') {
                        if (record.get('compStatus') == "UP") {
                            icon = '<img id="components-status-icon-' + record.get('id') + '" src="js/SuperApp/resources/icons/up.png">&nbsp;';
                        } else if (record.get('compStatus') == "DOWN") {
                            icon = '<img id="components-status-icon-' + record.get('id') + '" src="js/SuperApp/resources/icons/down.png">&nbsp;';
                        }
                    } else if (record.get('status') === 'INACTIVE') {
                        icon = '<img id="components-status-icon-' + record.get('id') + '" src="js/SuperApp/resources/icons/disabledicon.png">&nbsp;';
                    } else {
                        if (record.get('compStatus') == "UP") {
                            icon = '<img id="components-status-icon-' + record.get('id') + '" src="js/SuperApp/resources/icons/newup.png">&nbsp;';
                        } else if (record.get('compStatus') == "DOWN") {
                            icon = '<img id="components-status-icon-' + record.get('id') + '" src="js/SuperApp/resources/icons/newdown.png">&nbsp;';
                        }
                    }
                    if (record.get('alarm') && record.get('alarm').length > 0) {
                        icon += '<img id="components-alarm-icon-' + record.get('id') + '" style="height:10px;margin-left:8px" src="js/SuperApp/resources/icons/icon-warning.gif"/>';
                    }
                    return icon;
                }
            }, {
                dataIndex : 'name',
                text : l10n('name'),
                width : '15%',
                flex : 1,
                tdCls : 'columnwithclickevent'/*,
                renderer : function(value, metadata, record) {
                    var myToolTipText = "<b>" + l10n('name') + "</b>";
                    myToolTipText = myToolTipText + "<br/>" + record.get('name');
                    metadata.tdAttr = 'data-qtip="' + myToolTipText + '"';
                    return value;
                }*/
            }, {
                dataIndex : 'compTypeName',
                text : l10n('type'),
                sortable : true,
                width : '10%'/*,
                renderer : function(value, metadata, record) {
                    var myToolTipText = "<b>" + l10n('type') + "</b>";
                    myToolTipText = myToolTipText + "<br/>" + value;
                    metadata.tdAttr = 'data-qtip="' + myToolTipText + '"';
                    return value;
                }*/
            }, {
                dataIndex : 'localIP',
                text : l10n('ip'),
                width : '15%'/*,
                renderer : function(value, metadata, record) {
                    var myToolTipText = "<b>" + l10n('ip') + "</b>";
                    myToolTipText = myToolTipText + "<br/>" + record.get('localIP');
                    metadata.tdAttr = 'data-qtip="' + myToolTipText + '"';
                    return value;
                }*/
            }, {
                dataIndex : 'clusterIP',
                text : l10n('shared-ip'),
                width : '8%',
                hidden : true/*,
                renderer : function(value, metadata, record) {
                    var myToolTipText = "<b>" + l10n('shared-ip') + "</b>";
                    myToolTipText = myToolTipText + "<br/>" + record.get('clusterIP');
                    metadata.tdAttr = 'data-qtip="' + myToolTipText + '"';
                    return value;
                }*/
            }, {
                dataIndex : 'compSoftwareVersion',
                text : l10n('version'),
                width : '10%'/*,
                renderer : function(value, metadata, record) {
                    var myToolTipText = "<b>" + l10n('super-security-ssl-certificate-version') + "</b>";
                    myToolTipText = myToolTipText + "<br/>" + record.get('compSoftwareVersion');
                    metadata.tdAttr = 'data-qtip="' + myToolTipText + '"';
                    return value;
                }*/
            }, {
                dataIndex : 'configVersion',
                text : l10n('config-version'),
                width : '15%',
                renderer : function(value, metadata, record) {
                    if (record.get('compType').name == "VidyoGateway" || record.get('compType').name == "VidyoReplay" || record.get('compType').name == "VidyoRecorder") {
                        return "";
                    }
                    //var myToolTipText = "<b>" + l10n('config-version') + "</b>";
                    //myToolTipText = myToolTipText + "<br/>" + record.get('runningVersion') + " / " + record.get('configVersion');
                    //metadata.tdAttr = 'data-qtip="' + myToolTipText + '"';
                    return record.get('runningVersion') + " / " + record.get('configVersion');
                }
            }, {
                dataIndex : 'compID',
                text : l10n('id'),
                width : '10%'/*,
                renderer : function(value, metadata, record) {
                    var myToolTipText = "<b>" + l10n('id') + "</b>";
                    myToolTipText = myToolTipText + "<br/>" + record.get('compID');
                    metadata.tdAttr = 'data-qtip="' + myToolTipText + '"';
                    return value;

                }*/
            }, {
                dataIndex : 'mgmtUrl',
                text : l10n('management-url'),
                width : '15%',
                xtype : 'templatecolumn',
                tdCls : 'columnwithclickevent',
                tpl : '<a target="_blank">{mgmtUrl}</a>'/*,
                renderer : function(value, metadata, record) {
                    var myToolTipText = "<b>" + l10n('management-url') + "</b>";
                    myToolTipText = myToolTipText + "<br/>" + record.get('mgmtUrl');
                    metadata.tdAttr = 'data-qtip="' + myToolTipText + '"';
                    return value;
                }*/
            }],
            bind : {
                store : '{componentStore}'
            }
        });

        upperToolbar = Ext.create('Ext.toolbar.Toolbar', {
            docked : 'bottom',
            reference : 'upperToolbar',
            //ui : 'footer',
            border: 0,
            cls : 'white-header-footer',
            margin : 5,
            items : [{
                xtype : 'button',
                iconCls : 'x-fa fa-refresh',
                handler : 'refreshGrid'
            }, {
                xtype : 'tbseparator'
            }, {
                xtype : 'button',
                text : l10n('delete'),
                iconCls : 'x-fa fa-minus-circle',
                handler : 'deleteRecord'
            }, {
                xtype : 'tbseparator'
            }, {
                xtype : 'button',
                text : l10n('enable1'),
                iconCls : 'x-fa fa-check-circle',
                handler : 'enableRecordConfirmation'
            }, {
                xtype : 'tbseparator'
            }, {
                xtype : 'button',
                text : l10n('SuperSystemLicense-disable'),
                 iconCls : 'x-fa fa-ban',
                handler : 'disableRecordConfirmation'
            }]

        });

        bottomToolbar = Ext.create('Ext.toolbar.Toolbar', {
            docked : 'bottom',
            reference : 'bottomToolbar',
            margin : 5,
            border: 0,
            cls : 'white-header-footer',
            items : [{
                xtype : 'button',
                iconCls : 'x-fa fa-refresh',
                handler : 'refreshGrid'
            }, {
                xtype : 'tbseparator'
            }, {
                xtype : 'button',
                text : l10n('delete'),
                  iconCls : 'x-fa fa-minus-circle',
                handler : 'deleteRecord'
            }, {
                xtype : 'tbseparator'
            }, {
                xtype : 'button',
                text : l10n('enable1'),
               iconCls : 'x-fa fa-check-circle',
                handler : 'enableRecordConfirmation'
            }, {
                xtype : 'tbseparator'
            }, {
                xtype : 'button',
                text : l10n('SuperSystemLicense-disable'),
                iconCls : 'x-fa fa-ban',
                handler : 'disableRecordConfirmation'
            }
            ]

        });

        this.items = [{
            xtype : 'panel',
            reference : 'componentpanel',
            layout : {
                type : 'vbox',
                align : 'stretch'
            },
            border : 0,
            margin : 0,
            padding : 0,
            width : '100%',
            defaults : {
                flex : 1
            },
            items : [{
                xtype : 'statusbar',
                border : 0,
                margin : 5,
                padding : 0,
                reference: 'modifiedAvailable',
                text : '<div style="color:red;font-weight:bold;text-align:center"> **** '+ l10n('routerpools-activate-routerpools') +' **** </div>',
                hidden : true
            }, topToolbar, upperToolbar, componentsGrid, bottomToolbar]
        }];

        this.callParent();
    }
});

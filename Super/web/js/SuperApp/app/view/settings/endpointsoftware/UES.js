Ext.define('SuperApp.view.settings.endpointsoftware.UES', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.endpointsoftware',
    border : false,
    requires: ['SuperApp.view.settings.endpointsoftware.EndpointSoftwareController', 'SuperApp.view.settings.endpointsoftware.EndpointSoftwareViewModel'],

    viewModel: {
        type: 'EndpointSoftwareViewModel'
    },

    controller: 'EndpointSoftwareController',
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    margin : 0,
    padding : 0,
    title: {
        text: '<span class="header-title">'+l10n('manage-endpoint-software-submenu-endpointversions')+'</span>',
        textAlign :'center'
    },
    initComponent : function() {
    	var me = this;
        var bottomToolbar,
            topToolbar,
            bottomPanel;
        
 	        topToolbar = [{
	            text : l10n('activate'),
	            iconCls : 'x-fa fa-check-circle',
	            handler : 'validateUESActivate'
	        }, {
	            xtype : 'tbseparator'
	        }, {
	            text : l10n('delete'),
	            iconCls : 'x-fa fa-minus-circle',
	            handler : 'validateUESDelete'
	        }];
	
	        bottomPanel = Ext.create('Ext.panel.Panel', {
	            border : true,
	            width : '100%',
	            margin : 0,
	            padding : 0,
	            collapsible : false,
	            items : [{
	                xtype : 'gridpanel',
	                border : false,
	                margin : 0,
	                padding : 0,
	                title : '<span class="header-title">'+l10n('uploaded-endpoint-software')+'</span>',
	                titleAlign : 'center',
	                reference : 'uesGrid',
	                tbar : topToolbar,
	                bbar : topToolbar,
	                features : [{
	                    ftype : 'grouping',
	                    groupHeaderTpl : '{name} ({rows.length} ' + ('{values.rows.length}' > 1 ? 'Items)' : 'Item)'),
	                    enableGroupingMenu : false
	                }],
	                selType : 'checkboxmodel',
	                selModel : Ext.create('Ext.selection.CheckboxModel', {
	                    injectCheckbox : 'first',
	                    showHeaderCheckbox : false,
	                    checkOnly : true,
	                    mode : 'SIMPLE'
	                }),
	                columns : [{
	                    text : 'type',
	                    width : 120,
	                    sortable : false,
	                    hidden : true,
	                    dataIndex : 'endpointUploadType'
	                }, {
	                    text : l10n('file-name'),
	                    flex : 1,
	                    sortable : false,
	                    menuDisabled : true,
	                    dataIndex : 'endpointUploadFile',
	                    renderer : function(value, p, record) {
	                        var href = '../upload/' + encodeURIComponent(value);
	                        return "<a href='" + href + "'>" + value + "</a>";
	                    }
	                }, {
	                    text : l10n('uploaded'),
	                    width : 110,
	                    sortable : false,
	                    dataIndex : 'endpointUploadTime',
	                    renderer : function(value) {
	                        return value ? Ext.util.Format.date(value, Ext.grid.PropertyColumnModel.dateFormat) : '';
	                    }
	                }],
	                bind : {
	                    store : '{vidyoPortalEndpointSoftwareStore}'
	                },
	                viewConfig : {
	                    getRowClass : function(record, index) {
	                        if (!record) {
	                            return '';
	                        }
	                        if (record.data.endpointUploadActive == 1) {
	                            return 'active-row';
	                        }
	                    },
	                    forceFit : true
	                }
	            }]
	        });
	
	        this.items = [{
	            xtype : 'form',
	            layout : {
	                type : 'vbox',
	                align : 'center',
	                pack: 'center'
	            },
	            bodyPadding: 15,
	            reference : 'UESUploadForm',
	            errorReader : Ext.create('Ext.data.XmlReader', {
	                record : 'field',
	                model : Ext.create("SuperApp.model.settings.Field"),
	                success : '@success'
	            }),
	            items : [{
	                xtype : 'filefield',
	                fieldLabel : l10n('upload-endpoint-file-lbl'),
	                margin : 0,
	                padding : 0,
	                width : 500,
	                msgTarget : 'side',
	                allowBlank : false,
	                blankText : l10n('select-a-file'),
	                labelAlign : 'right',
	                buttonConfig : {
	                    text : 'Browse',
	                },
	                reference : 'uesFileUploadField',
	                name : 'client-path'
	            }],
	            buttonAlign: 'center',
	            buttons : [
	               {
	                   text : l10n('upload'),
	                   disabled : true,
	                   formBind : true,
	                   reference : 'uesUploadBtn',
	                   handler : 'uesUploadBtn'
	               }
	            ]
	        }, {
	            xtype : 'fieldset',
	            padding : 0,
	            width : '100%',
	            bind : {
	                hidden : '{hideUESGrid}'
	            },
	            margin : 0,
	            padding : 0,
	            items : [bottomPanel]
	        }];
        

        this.callParent();

    }
});

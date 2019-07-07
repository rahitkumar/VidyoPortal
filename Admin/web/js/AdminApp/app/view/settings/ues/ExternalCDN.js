Ext.define('AdminApp.view.settings.ues.ExternalCDN', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.externalcdn',
    border : false,
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    requires: ['AdminApp.view.settings.ues.UploadEndpointViewModel', 'AdminApp.view.settings.ues.UploadEndpointController'],

    viewModel : {
        type : 'UploadEndpointViewModel'
    },
    controller : 'UploadEndpointController',
    margin : 0,
    padding : 0,
    trackResetOnLoad: true,
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
                handler : 'validatCDNActivate'
            }, {
                xtype : 'tbseparator'
            }, {
                text : l10n('delete'),
                iconCls : 'x-fa fa-minus-circle',
                handler : 'validateCDNDelete'
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
                    title : '<span class="header-title">'+l10n('addded-endpoint-software')+'</span>',
                    titleAlign : 'center',
                    reference : 'externalCDNGrid',
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
                        width : '5%',
                        sortable : false,
                        hidden : true,
                        dataIndex : 'endpointUploadType'
                    }, {
                        text : l10n('external-url'),
                        width : '45%',
                        sortable : false,
                        menuDisabled : true,
                        dataIndex : 'endpointUploadFile',
                        renderer : function(value, p, record) {
                        	return "<a href='" + value + "' target='_blank'>" + value + "</a>";
                        }
                    }, {
                        text : l10n('added'),
                        width : '20%',
                        sortable : false,
                        dataIndex : 'endpointUploadTime',
                        renderer : function(value) {
                            return value ? Ext.util.Format.date(value, Ext.grid.PropertyColumnModel.dateFormat) : '';
                        }
                    }, {
                        text : l10n('external-version'),
                        width : '30%',
                        sortable : false,
                        menuDisabled : true,
                        dataIndex : 'endpointUploadVersion'
                    }],
                    bind : {
                        store : '{uploadEndPointStore}'
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
                trackResetOnLoad: true,
                layout : {
                    type : 'vbox',
                    align : 'center',
                    pack: 'center'
                },
                bodyPadding: 15,
                errorReader : Ext.create('Ext.data.XmlReader', {
                    record : 'field',
                    model : Ext.create("AdminApp.model.Field"),
                    success : '@success'
                }),
                reference : 'ExternalCDNUploadForm',
                items : [{
                    xtype: 'combo',
                    id: 'platform',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('platform'),
                    allowBlank: false,
                    editable: false,
                    triggerAction: 'all',
                    typeAhead: false,
                    mode: 'local',
                    minWidth: 500,
                    name: 'endpointUploadType',
                    reference: 'endpointUploadType',
                    displayField: 'displayFieldName', // what the user sees in the dropdown
                    valueField: 'dataFieldName',
                    bind: {
                        store: '{platformStore}'
                    },
                    listeners: {
                        beforerender: 'platformComboLoad'
                    }
                },{
                    xtype : 'textfield',
                    fieldLabel : '<span class="red-label">*</span>' + l10n('external-cdn-url'),
                    vtype : 'URLRegExValidate',
                    msgTarget : 'side',
                    minWidth: 500,
                    maxLength : 1024,
                    allowBlank: false,
                    name : 'endpointUploadFile',
                    reference : 'endpointUploadFile'
                }, {
                    xtype : 'textfield',
                    minWidth: 500,
                    maxLength : 128,
                    allowBlank: false,
                    fieldLabel : '<span class="red-label">*</span>' + l10n('external-version'),
                    name : 'endpointUploadVersion',
                    maskRe: /[A-Za-z0-9._]/,
                    reference : 'endpointUploadVersion'
                }],
                buttonAlign: 'center',
                buttons : [
                   {
                       text : l10n('save'),
                       disabled : true,
                       formBind : true,
                       reference : 'externalSaveBtn',
                       handler : 'externalCDNSaveBtn'
                   }, {
                       text: l10n('cancel'),
                       handler: function() {
                           this.up('form').getForm().reset();
                       }
                   }
                ]
            }, {
                xtype : 'fieldset',
                padding : 0,
                width : '100%',
                bind : {
                    hidden : '{hideExternalCDNGrid}'
                },
                margin : 0,
                padding : 0,
                items : [bottomPanel]
            }];

        this.callParent();

    }
});

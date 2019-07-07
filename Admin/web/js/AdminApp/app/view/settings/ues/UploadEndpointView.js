/**
 * @class UploadEndpointView
 */
Ext.define('AdminApp.view.settings.ues.UploadEndpointView', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.uploadendpointview',

    requires : ['AdminApp.view.settings.ues.UploadEndpointViewModel', 'AdminApp.view.settings.ues.UploadEndpointController'],

    layout : {
        type : 'vbox',
        align : 'stretch'
    },


   height:800,
    title : {
        text : '<span class="header-title">' + l10n('manage-endpoint-software') + '</span>',
        textAlign : 'center'
    },
    viewModel : {
        type : 'UploadEndpointViewModel'
    },
    controller : 'UploadEndpointController',

    border : false,
    scrollable : true,
    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'form',
            reference : 'UESUploadForm',
            border :false,
             bodyPadding: 15,
            errorReader : Ext.create('Ext.data.XmlReader', {
                record : 'field',
                model : Ext.create("AdminApp.model.Field"),
                success : '@success'
            }),
            layout : {
                type : 'vbox',
                align : 'center',
                pack: 'center'
            },

            items : [{
                xtype : 'filefield',
                margin : 0,
                width : 500,
                fieldLabel : l10n('upload-endpoint-file-lbl'),
                msgTarget : 'side',
                allowBlank : false,
                labelAlign : 'right',
                emptyText : l10n('select-a-file'),
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
                   handler : 'uesUploadBtnClick'
               }
            ]
        }, {
            xtype : 'fieldset',
            border : 1,
            padding : 0,
            width : '100%',
            margin : 5,
            layout : {
                type : 'vbox',
                align : 'center'
            },
            items : [{
                xtype : 'gridpanel',
                width : '100%',
                border : false,
                title : l10n('uploaded-endpoint-software'),
                titleAlign : 'center',

                reference : 'endpointsoftgrid',
                bind : {
                    store : '{uploadEndPointStore}',
                    hidden : '{hideGrid}'
                },
                tbar : {
                    xtype : 'toolbar',
                    docked : 'bottom',
                    reference : 'topToolbar',
                    items : []

                },
                bbar : [{
                    xtype : 'button',
                    text : l10n('super-security-ssl-manage-cert-activate'),
                    iconCls : 'x-fa fa-check-circle',
                    handler : 'onActivateEndPoint'
                }, '-', {
                    xtype : 'button',
                    text : l10n('delete'),
                    iconCls : 'x-fa fa-minus-circle',
                    handler : 'onDeleteEndPoint'
                }],
                tbar : [{
                    xtype : 'button',
                    text : l10n('super-security-ssl-manage-cert-activate'),
                    iconCls : 'x-fa fa-check-circle',
                    handler : 'onActivateEndPoint'
                }, '-', {
                    xtype : 'button',
                    text : l10n('delete'),
                    iconCls : 'x-fa fa-minus-circle',
                    handler : 'onDeleteEndPoint'
                }],
                features : [{
                    ftype : 'grouping',
                    groupHeaderTpl : '{name} ({rows.length} ' + ('{values.rows.length}' > 1 ? 'Items)' : 'Item)'),
                }],
                selType : 'checkboxmodel',
                selModel : Ext.create('Ext.selection.CheckboxModel', {
                    injectCheckbox : 'first',
                    showHeaderCheckbox : false,
                    checkOnly : true,
                    mode : 'SIMPLE'
                }),
                columns : [{
                    id : 'endpointUploadID',
                    text : "id",
                    width : 30,
                    sortable : false,
                    hidden : true,
                    dataIndex : 'endpointUploadID'
                }, {
                    text : l10n('type'),
                    width : 100,
                    sortable : false,
                    hidden : true,
                    dataIndex : 'endpointUploadType'
                }, {
                    text : l10n('file-name'),
                    flex : 1,
                    sortable : false,
                    dataIndex : 'endpointUploadFile',
                    renderer : function(value, p, record) {
                        var href = '../upload/' + encodeURIComponent(value);
                        return "<a href='" + href + "'>" + value + "</a>";
                    }
                }, {
                    text : l10n('uploaded'),
                    width : 100,
                    sortable : false,
                    dataIndex : 'endpointUploadTime',
                    renderer : function(value) {
                        return value ? Ext.util.Format.date(value, Ext.grid.PropertyColumnModel.dateFormat) : '';
                    }
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
        }];

        me.callParent(arguments);
    }
});

Ext.define('SuperApp.view.tenants.ManageTenants', {
    extend : 'Ext.container.Container',
    xtype : 'manageTenants',
    requires : [],

    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'grid',
            minHeight : 700,
            cls : 'manage-tenant-grid',
            reference : 'tenantsGrid',
            flex : 1,
            emptyText: l10n('no-tenants-to-display'),
            selModel : Ext.create('Ext.selection.CheckboxModel', {
                showHeaderCheckbox : true,
                injectCheckbox : 'first',
                checkOnly : true,
                mode : 'SIMPLE',
                renderer : function(value, metaData, record) {
                    if (record.get('tenantID') == 1) {
                        return '';
                    } else {
                        return '<div class="' + Ext.baseCSSPrefix + 'grid-row-checker">&#160;</div>';
                    }
                }
            }),
            columns : [{
                header : l10n('tenant-name'),
                dataIndex : 'tenantName',
                flex : 2,
                tdCls : 'columnwithclickevent',
                renderer : function(value) {
                    return "<span class='selectable-tenant'>" + Ext.String.htmlEncode(value) + "</span>";
                }
            }, {
                header : l10n('tenant-url'),
                dataIndex : 'tenantURL',
                flex : 2,
                tdCls : 'columnwithclickevent',
                renderer : function(value) {
                    return "<span class='selectable-tenant-url'>" + Ext.String.htmlEncode(value) + "</span>";
                }
            }, {
                header : l10n('extension'),
                dataIndex : 'tenantPrefix',
                flex : 1,
                renderer :function(value) {
                	return Ext.String.htmlEncode(value);
                }
            }, {
                header : l10n('description'),
                dataIndex : 'description',
                flex : 3,
                renderer :function(value) {
                	return Ext.String.htmlEncode(value);
                } 
            }],
            bind : {
                store : '{manageTenantsStore}'
            },
            tbar: [{
                xtype : 'textfield',
                reference : 'tenantNameFilter',
                fieldLabel : l10n('tenant-name'),
                listeners: {
                    change: 'onTenantNameFilterChange'
                }
            },
            {
                xtype : 'textfield',
                reference : 'tenantUrlFilter',
                fieldLabel : l10n('tenant-url'),
                listeners: {
                    change: 'onTenantUrlFilterChange'
                }
            }],
            dockedItems : [{
                xtype : 'pagingtoolbar',
                cls : 'white-footer',
                beforePageText : l10n('AdminLicense-page'),
                afterPageText : l10n('pagination-of'),
                emptyMsg: l10n('no-tenants-to-display'),
                bind : {
                    store : '{manageTenantsStore}'
                },
                dock : 'bottom',
                displayInfo : true,
                displayMsg : l10n('displaying-tenants-0-1-of-2'),
                items : ['-', {
                    xtype : 'button',
                	text: l10n('add'),
                	iconCls : 'x-fa fa-plus-circle',
                	handler: 'addTenantBtnClick'
                }, {
                    xtype : 'button',
                    text : l10n('delete'),
                    iconCls : 'x-fa fa-minus-circle',
                    handler : 'deleteTenants'
                }]
            }],

            listeners : {
                sortchange : 'onTenantsSortChange',
                rowclick : 'onManageTenantsRowClick'
            }

        }];

        me.callParent(arguments);
    }
});

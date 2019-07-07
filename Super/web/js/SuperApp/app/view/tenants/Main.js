Ext.define('SuperApp.view.tenants.Main', {
    extend : 'Ext.panel.Panel',
    xtype : 'tenantsMain',
    requires : ['SuperApp.view.tenants.MainController', 'SuperApp.view.tenants.MainModel'],
    controller : 'tenantMain',

    viewModel : {
        type : 'tenantMain'
    },
   border: false,
    frame: false,
    layout : {
        type : 'hbox',
        align : 'stretch'
    },

    items : [/*{
        xtype : 'grid',
        border : false,
        cls : 'tenants-nav-grid',
        width : 180,
        reference : 'tenantsNavGrid',
        hideHeaders : true,
        columns : [{
            renderer : function(value) {
                return '<span class="cursor-pointer">' + value + '</span>';
            },
            dataIndex : 'tenantSection',
            flex : 1
        }],
        bind : {
            store : '{tenantsMenuStore}'
        },
        listeners : {
            rowclick : 'tenantsRowClick'
        }
    }, */{
        xtype : 'container',
        flex : 3,
        cls : 'tenants-center-container',
        reference : 'tenantCenterContainer',
        layout : {
            type : 'card',
            align : 'stretch'
        },
        items:[{
        	xtype:'manageTenants'
        }]
    }]
});

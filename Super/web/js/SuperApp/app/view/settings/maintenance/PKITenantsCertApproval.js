Ext.define('SuperApp.view.settings.maintenance.PKITenantsCertApproval', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.tenantsCrtPendingAppr',
    width: '100%',
    border: false,
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    title: {
        text: '<span class="header-title">' + 'Tenants Certificate Pending Approvals' + '</span>',
        textAlign: 'center'
    },
    initComponent: function () {
        var me = this;
        me.items = [{
            xtype: 'grid',
            width: '100%',
            reference: 'tenantGrid',
            emptyText: 'No approvals required now',
            minHeight: 100,
            border: '1 0 1 0',
            margin: 0,
            padding: 0,

            bind: {
                store: '{pkiTenantsPageStore}'
            },

            columns: [{ xtype: 'rownumberer' }, {
                text: l10n('tenant-name'),
                dataIndex: 'tenantName',
                align: 'left',
                flex: 1
            }, {
                    text: l10n('tenant-url'),
                    dataIndex: 'tenantURL',
                    tdCls: 'columnwithclickevent',
                    align: 'left',
                    sortable: false,
                    flex: 1,
                    renderer: function (value, metadata, record) {
                        var myToolTipText = "<b>" + l10n('tenant-url') + "</b>";
                        myToolTipText = myToolTipText + "<br/>" + record.get('tenantURL');
                        metadata.tdAttr = 'data-qtip="' + myToolTipText + '"';

                        return "<span class='selectable-tenant-url'>" + value + "</span>";

                    }
                },
                {
                    width: 120,

                    // This is our Widget column
                    xtype: 'widgetcolumn',
               
                    // This is the widget definition for each cell.
                    // Its "value" setting is taken from the column's dataIndex
                    widget: {
                        text:'Approve',
                        xtype: 'button',
                      


                        iconCls: 'x-fa fa-thumbs-up',
                        handler: 'onClickApproveCert',
                        tooltip: 'Approve',

                        scale: 'small',
                        iconAlign: 'left'
                    }
                }
            ],
            listeners: {

                rowclick: 'onManageTenantsRowClick'
            }
        }];
        this.callParent();
    }

});

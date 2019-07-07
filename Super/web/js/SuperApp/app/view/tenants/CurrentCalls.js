Ext.define('SuperApp.view.tenants.CurrentCalls', {
    extend: 'Ext.panel.Panel',
    xtype: 'currentCalls',
    //   requires : ['SuperApp.view.tenants.MainController', 'SuperApp.view.tenants.MainModel'],
    layout: {
        type: 'fit',
        align: 'stretch'
    },

    border: false,
    frame: false,
    controller: 'tenantMain',

    viewModel: {
        type: 'tenantMain'
    },

    items: [{
        xtype: 'fieldset',
        border: 1,
        width: '100%',
        margin: 5,
        padding: 0,
        layout: {
            type: 'vbox',
            align: 'stretch'
        },
        items: [{
            xtype: 'toolbar',
            width: '100%',
            margin: 0,
            layout: {
                align: 'center'
            },
            items: ['->', {
                xtype: 'title',
                text: '<span class="header-title">' + l10n('current-calls') + '</span>',
                textAlign: 'center'
            }, '->']
        }, {
            xtype: 'grid',
            cls: 'manage-call-grid',
            emptyText: l10n('no-calls-to-display'),
            minHeight: 700,
            margin: 0,
            padding: 0,
            flex: 1,
            features: [{
                ftype: 'grouping'
            }],
            columns: [{
                header: l10n('conference-name'),
                dataIndex: 'conferenceName',
                flex: 1,
                renderer: function(value, p, record) {
                    value = Ext.String.htmlEncode(value);
                    return value;
                 }
            }, {
                header: l10n('tenant-name'),
                dataIndex: 'tenantName',
                menuDisabled: true,
                flex: 1,
                renderer: function(value, p, record) {
                    value = Ext.String.htmlEncode(value);
                    return value;
                 }
            }, {
                header: l10n('name'),
                dataIndex: 'name',
                menuDisabled: true,
                flex: 1,
                renderer: function(value, p, record) {
                    value = Ext.String.htmlEncode(value);
                    return value;
                 }
            }, {
                header: l10n('extension'),
                dataIndex: 'ext',
                menuDisabled: true,
                flex: 1,
                renderer: function(value, p, record) {
                    value = Ext.String.htmlEncode(value);
                    return value;
                 }
            }, {
                header: l10n('vidyorouter-name'),
                dataIndex: 'vrName',
                menuDisabled: true,
                flex: 1,
                renderer: function(value, p, record) {
                    value = Ext.String.htmlEncode(value);
                    return value;
                 }
            }, {
                header: l10n('vidyorouter-pool'),
                dataIndex: 'groupName',
                menuDisabled: true,
                flex: 1,
                renderer: function(value, p, record) {
                    value = Ext.String.htmlEncode(value);
                    return value;
                 }
            }],
            bind: {
                store: '{currentCallsStore}'
            },
            dockedItems: [{
                xtype: 'pagingtoolbar',
                cls: 'white-footer',
                beforePageText: l10n('AdminLicense-page'),
                afterPageText: l10n('pagination-of'),
                emptyMsg: l10n('no-calls-to-display'),
                displayInfo: true,
                displayMsg: l10n('displaying-current-calls-0-1-of-2'),
                bind: {
                    store: '{currentCallsStore}'
                },
                dock: 'bottom',
                displayInfo: true
            }],

            listeners: {
                sortchange: 'onCurrentCallsSortChange'
            }
        }]
    }]
});
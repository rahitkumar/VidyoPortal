Ext.define('SuperApp.view.components.RouterMediaAddress', {
    extend : 'Ext.form.Panel',
    alias : 'widget.routermediaaddress',

    border : false,

    margin : 0,

    padding : 0,

    title : l10n('mediaaddressmap'),
    
    hidden : true,
    
    border : 0,
    
    reference : 'mediapartold',
    
    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    initComponent : function() {
        this.items = [{
            xtype : 'grid',
            title : '',
            header : false,
            reference : 'routerMediaGrid',
            selModel : {
                checkOnly : true,
                injectCheckbox : 0,
                showHeaderCheckbox : false,
                mode : 'SIMPLE'
            },
            store : mediastore = Ext.create('SuperApp.store.components.MediaAddressStore'),
            columns : [{
                text : l10n('local-ip-address'),
                dataIndex : 'localIP',
                flex : 1,
                editor : {
                    xtype : 'textfield',
                    allowBlank : false
                }
            }, {
                text : l10n('remote-ip-address'),
                dataIndex : 'remoteIP',
                flex : 1,
                editor : {
                    xtype : 'textfield',
                    allowBlank : false
                }
            }],
            selType : 'rowmodel',
            plugins : {
                ptype : 'rowediting',
                clicksToEdit : 2,
                autoCancel : false,
                pluginId : 'routerMediaRowEditId',
                listeners : {
                    edit : 'updateRouterMediaRecord',
                    canceledit : 'deleteRouterMediaRecord',
                    beforeedit : "checkIfBlankRecord"
                }
            },
            minHeight : 150,
            bbar : [{
                xtype : 'button',
                text : l10n('add'),
                handler : 'rmaAdd'
            }, {
                xtype : 'tbspacer',
                width : 100
            }, {
                xtype : 'button',
                text : l10n('remove'),
                handler : 'rmaRemove'
            }]
        }];
        this.callParent(arguments);
    }
});

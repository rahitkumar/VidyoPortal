Ext.define('SuperApp.view.components.ReplaceOverlay', {
    extend : 'Ext.window.Window',
    alias : 'widget.replaceoverlay',
    requires : ['Ext.grid.Panel'],
    
    reference : 'replaceoverlay',
    

    layout : {
        type : 'vbox',
        align : 'center'
    },

    title : l10n('confirmation'),

    margin : 0,

    padding : 0,

    modal : true,

    border : 0,

    cls : 'white-footer',

    closeAction : 'hide',

    width : 600,

    componentId : 0,


    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'label',
            html : l10n('delete-selected-network-component-replacement'),
            width : '90%',
            margin : 5
        }, {
            xtype : 'component',
            reference : 'componentdetail',
            tpl : new Ext.XTemplate('<tpl>', '<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+l10n('type')+': &nbsp;&nbsp;<b>{componentType}</b></div>', '<div>'+l10n('component-name')+': &nbsp;&nbsp;<b>{componentName}</b></div>', '<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+l10n('status')+': &nbsp;&nbsp;<b>{status}</b></div>', '</div>', '</tpl>')
        }, {
            xtype : 'panel',
            border : 0,
            style : {
                'margin-left' : '40px !important'
            },
            layout : {
                type : 'hbox',
                align : 'center',
                pack : 'start'
            },
            items : [{
                xtype : 'label',
                html : l10n('choose-replacement')+':&nbsp;&nbsp;'
            }, {
                xtype : 'grid',
                width : 200,
                reference : 'replacecomponentgrid',
                //selModel : 'SINGLE',
                minHeight : 100,
                scrollable : 'vertical',
                columns : [{
                    dataIndex : 'name',
                    flex : 1.5,
                    sortable : false,
                    hideable : false,
                    text : ''
                }, {
                    dataIndex : 'status',
                    flex : 1,
                    sortable : false,
                    hideable : false,
                    text : l10n('status')
                }],
                listeners : {
                    select : 'onSelectReplaceGridRecord',
                    deselect : 'onDeSelectReplaceGridRecord'
                }
            }]
        }];

        me.buttons = [{
            text : l10n('replace'),
            component : me.component,
            bind : {
                disabled : '{isDisableReplace}'
            },
            listeners : {
                click : 'onClickReplaceDelete'
            }
        }, {
            text : l10n('close'),
            listeners : {
                click : function() {
                    me.close();
                }
            }
        }];
        me.buttonAlign = 'center';

        me.callParent(arguments);
    }
});

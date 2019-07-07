Ext.define('SuperApp.view.cloud.interpoolPreference.Pools', {
    extend : 'Ext.panel.Panel',

    xtype : 'pools',

    requires : ['SuperApp.view.cloud.interpoolPreference.SinglePoolDetails'],

    cls : 'pools-grid',

    title : l10n('pools-list'),

    border : 1,

    margin : 5,

    layout : {
        type : 'card',
        animation : {
            type : 'slide',
        }
    },

    reference : 'poolsCardView',

    tools : [{
        xtype : 'button',
        cls : 'poolsback-tool',
        icon : 'js/SuperApp/resources/images/control_rewind.png',
        tooltip : 'Back To Pools',
        bind : {
            hidden : '{backHidden}'
        },
        handler : function() {
            this.up('panel').fireEvent('goBackToPools', true);
        }
    }],

    items : [],

    initComponent : function() {
        var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToMoveEditor : 1,
            errorSummary : false,
            autoCancel : false
        });

        Ext.apply(Ext.form.VTypes, {
            nohtml: function(val, field) {
                var re = /[&><'"]+/g;
                return !re.test(val);
            },
            nohtmlText: "<, >, &, \", ' characters not permitted"
        });

        //rowEditing.disable();

        this.items = [{
            xtype : 'container',
            layout : {
                type : 'vbox',
                align : 'stretch'
            },
            border : 1,
            items : [{
                xtype : 'toolbar',
                docked : 'top',
                border : 1,
                        items : [{
                    xtype : 'button',
                    iconCls : 'x-fa fa-refresh',
                    handler : 'refreshPoolsGrid'
                }, {
                    xtype : 'tbseparator',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'button',
                    text : l10n('add-pool'),
                    iconCls : 'x-fa fa-plus-circle',
                    handler : 'addPool',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'tbseparator',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'button',
                    text : l10n('edit-pool'),
                    iconCls : 'x-fa fa-edit',
                    handler : 'editPool',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'tbseparator',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'button',
                    text : l10n('delete-pool'),
                    iconCls : 'x-fa fa-minus-circle',
                    handler : 'deletePool',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'tbfill'
                }]
            }, {
                xtype : 'grid',
         		border : false,
                flex : 1,
                reference : 'poolsListGrid',
                bind : {
                    store : '{poolsList}'
                },
                listeners : {
                    edit : 'onPoolsListEdit',
                    canceledit : 'onPoolsListCancelEdit',
                    validateedit : 'onPoolListValidateEdit'
                },
                columns : [{
                    text : l10n('pool-name'),
                    flex : 1,
                    dataIndex : 'name',
                    renderer : function(value, metadata, record, rowIndex, ColIndex, store, view) {
                        return '<span>' + value + '</span><img id="disclosure-image-' + record.get("id") + '" style="float:right; cursor:pointer;" data-disclosure=true src="js/SuperApp/resources/images/edit-icon.png"/>';
                    },
                    editor : {
                        maxLength : 40,
                        allowBlank : false,
                        vtype: 'nohtml',
                        emptyText : l10n('specify-pool-name')
                    }
                }],
                plugins : [rowEditing]
            }, {
                xtype : 'toolbar',
                docked : 'top',
                style : {
                    'border' : '1px',
                    'border-top' : '1px solid #CCC'
                },
                cls : 'white-header-footer',
                items : [{
                    xtype : 'button',
                    iconCls : 'x-fa fa-refresh',
                    handler : 'refreshPoolsGrid'
                }, {
                    xtype : 'tbseparator',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'button',
                    text : l10n('add-pool'),
                    iconCls : 'x-fa fa-plus-circle',
                    handler : 'addPool',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'tbseparator',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'button',
                    text : l10n('edit-pool'),
                    iconCls : 'x-fa fa-edit',
                    handler : 'editPool',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'tbseparator',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'button',
                    text : l10n('delete-pool'),
                    iconCls : 'x-fa fa-minus-circle',
                    handler : 'deletePool',
                    bind : {
                        disabled : '{!isModified}'
                    }
                }, {
                    xtype : 'tbfill'
                }]
            }, {
                xtype : 'toolbar',
              
                style : {
                    'border' : '1px',
                    'border-top' : '1px solid #CCC'
                },
                docked : 'bottom',
                cls : 'white-footer',
                items : [{
                    xtype : 'tbfill'
                }, {
                    xtype : 'button',
                    bind : {
                        disabled : '{!isPoolsActivate}'
                    },
                    text : l10n('activate'),
                    handler : 'activatePool'
                }, {
                    xtype : 'button',
                    bind : {
                        disabled : '{!isPoolsActivate}'
                    },
                    text : l10n('discard'),
                    handler : 'onClickDiscardPools'
                }]
            }]
        }, {
            xtype : 'singlePoolDetails',
            border : false
        }];
        this.callParent();
    }
});

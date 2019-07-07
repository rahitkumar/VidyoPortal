Ext.define('SuperApp.view.settings.superaccounts.SuperAccounts', {
    extend : 'Ext.panel.Panel',

    alias : 'widget.superaccounts',
    reference : 'superaccounts',
    requires : ['SuperApp.view.settings.superaccounts.SuperAccountsViewModel', 'SuperApp.model.settings.SuperAccounts', 'SuperApp.view.settings.superaccounts.SuperAccountsController', 'SuperApp.model.settings.SuperAccounts'],
    controller : 'SuperAccountsController',
    viewModel : {
        type : 'SuperAccountsViewModel'
    },
    title : {
        text : '<span class="header-title">' + l10n('super-accounts') + '</span>',
        textAlign : 'center'
    },
    autoDestroy : true,
    border : false,
    layout : {
        type : 'card',
        align : 'stretch'
    },
    activeItem : 0,
    initComponent : function() {
        var me = this;

        Ext.apply(Ext.form.VTypes, {
            passwordMatch : function(val, field) {
                if (field.initialPassField) {
                    var pwd = me.down('textfield[itemId=' + field.initialPassField + ']');
                    return (val == pwd.getValue());
                }
                return true;
            },
            passwordMatchText : l10n('password-not-match'),

            oldPasswordMatch : function(val, field) {
                var oldpassword = me.down('textfield[name=oldpassword]').getValue(),
                    params = {
                    field : 'oldpassword',
                    value : oldpassword
                };
                Ext.Ajax.request({
                    url : "validatesuperpassword.ajax",
                    params : params,
                    success : function(response, request) {
                        var passwordMatch = Ext.JSON.decode(response.responseText);
                        if (!passwordMatch['valid']) {
                            field.markInvalid(passwordMatch["reason"]);
                            me.down('button[name=savesuperaccounts]').setDisabled(true);
                        }
                    }
                });
                return true;
            },
            oldPasswordMatchText : l10n('old-password-do-not-match')
        });

        me.items = [{
            xtype : 'fieldset',
            padding : 0,
            margin : 5,
            reference : 'superaccountsgridpanel',
            layout : {
                type : 'vbox',
                align : 'stretch'
            },
            items : [{
                xtype : 'panel',
                layout : {
                    type : 'hbox',
                    align : 'stretch'
                },
                border : 0,
                margin : 5,
                defaults : {
                    margin : 5,
                    padding : 0
                },
                items : [{
                    xtype : 'textfield',
                    fieldLabel : l10n('name'),
                    itemId : 'memberName',
                    name : 'memberName',
                    enableKeyEvents : true,
                    labelWidth : 50,
                    labelAlign : 'right',
                    labelStyle : 'font-weight:bold;padding-top:7px;',
                    width : 300,
                    listeners : {
                    	change : 'manageSuperAccountsFilter'
                    }
                }, /*{
                    xtype : 'checkboxfield',
                    boxLabel : l10n('enabled'),
                    itemId : 'enable',
                    reference : 'superaccountsEnableCheck',
                    boxLabelAlign : 'before',
                    boxLabelStyle : 'font-weight:bold;padding-top:7px;',
                    checked : true,
                    listeners : {
                        change : 'manageSuperAccountsFilter'
                    }
                }*/
                {
                    xtype: 'combo',
                    name: 'enable',
                     anchor: '-15',
                    labelAlign: 'left',
                    fieldLabel: l10n('status'),
                    reference: 'enableStatus',
                    queryMode: 'local',
                    displayField: 'name',
                    editable: false,
                    valueField: 'type',
                    value: 'All',
                    bind: {
                        store: '{statusFilterStore}'
                    },
                    listeners: {
                        select: 'manageSuperAccountsFilter'
                    }
                }]
            }, {
                xtype : 'grid',
                width : '100%',
                minHeight: 550,
                reference : 'superaccountsgrid',
                layout : {
                    type : 'hbox',
                    align : 'stretch'
                },
                emptyText : l10n('no-super-accounts-found'),
                bind : {
                    store : '{superAccountsStore}'
                },
                border : false,
                selModel : Ext.create('Ext.selection.CheckboxModel', {
                    injectCheckbox : 'first',
                    showHeaderCheckbox : true,
                    checkOnly : true,
                    mode : 'SIMPLE',
                    allowDeselect: true,
                    renderer : function(value, metaData, record) {
                        if (record.data.memberName == username) {
                            return '';
                        } else {
                            return '<div class="' + Ext.baseCSSPrefix + 'grid-row-checker">&#160;</div>';
                        }

                    },
                    selectAll: function(){
                        //will prevent to select all rows
                    	var grid = this.view;
                    	var store = grid.getStore();
                    	var sm = grid.getSelectionModel();
                    	store.each(function(item, idx) {
	                    	if (item.get('memberName') == superusername) {
	                    		sm.deselect(idx, true);
	                    	} else {
	                    		sm.select(idx, true);
	                    	}
                    	});
                    }
                }),
                columns : [{
                    text : l10n('member-name'),
                    dataIndex : 'username',
                    tdCls : 'columnwithclickevent',
                    flex : 2,
                    renderer: Ext.util.Format.htmlEncode
                }, {
                    text : l10n('date-joined'),
                    dataIndex : 'memberCreated',
                    flex : 1,
                    renderer : function(value) {
                        return value ? Ext.util.Format.date(value, Ext.grid.PropertyColumnModel.dateFormat) : '';
                    }
                }, {
                    text : l10n('enabled'),
                    dataIndex : 'enable',
                    flex : 1,
                }],
                listeners : {
                    cellClick : 'onCellClickSuperAccountsGrid'
                },
                dockedItems : [{
                    xtype : 'pagingtoolbar',
                    bind : {
                        store : '{superAccountsStore}'
                    },
                    dock : 'bottom',
                    displayInfo : true,
                    displayMsg: l10n('displaying-members-0-1-of-2'),
                    emptyMsg: l10n('no-members-to-display'),
                    items : ['-', {
                        xtype : 'button',
                        text : l10n('add'),
                       iconCls : 'x-fa fa-plus-circle',
                        listeners : {
                            click : 'onClickGridAddSA'
                        }
                    }, {
                        xtype : 'button',
                        text : l10n('delete'),
                       iconCls : 'x-fa fa-minus-circle',
                        listeners : {
                            click : 'onClickGridDeleteSA'
                        }
                    }]
                }]
            }]
        }];
        me.callParent(arguments);
    },

    setValidationTypes : function(form) {
        var me = this;

        form.down('textfield[itemId=password1]').disable();
        form.down('textfield[itemId=password1]').hide();
        form.down('textfield[itemId=password2]').disable();
        form.down('textfield[itemId=password2]').hide();
    },

    setAddVTypes : function(form) {

        form.down('textfield[itemId=password1]').enable();
        form.down('textfield[itemId=password1]').show();
        form.down('textfield[itemId=password2]').enable();
        form.down('textfield[itemId=password2]').show();
        form.down('textfield[itemId=oldpassword]').disable();
        form.down('textfield[itemId=oldpassword2]').disable();
        form.down('textfield[itemId=oldpassword1]').disable();

    },
    remoteValidate : function() {
        if (this.textValid) {
            return true;
        } else {
            return this.textInvalid;
        }
    }
});

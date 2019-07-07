Ext.define('SuperApp.view.components.GatewayView', {
	extend : 'Ext.window.Window',
	alias : 'widget.gatewayView',
	border : false,
	width : 700,
	modal : true,
	frame : true,
	constrain : true,
	closeAction : 'destroy',
	closable : true,
	reference : 'gatewayWin',
	resizeable : false,
	title : {
		text : l10n('vidyogateway'),
		textAlign : 'center'
	},
	initComponent : function() {
		Ext.apply(Ext.form.VTypes,
				{
					password : function(val, field) {
						if (field.initialPassField) {
							var pwd = field.up('form').down(
									'textfield[itemId='
											+ field.initialPassField + ']');
							return (val == pwd.getValue());
						}
						return true;
					},
					passwordText : l10n('password-not-match')
				});
		var me = this;

		me.items = [ {
			xtype : 'form',
			reference : 'gatewayEditform',
			border : false,
			padding : 0,
			margin : 5,
			trackResetOnLoad : true,
			standardSubmit : false,
			requires : [ 'SuperApp.model.components.GatewayModel'],
			layout : {
				type : 'vbox',
				align : 'center'
			},
			defaults : {
				width : 600,
				labelWidth : 125
			},
			items : [
					{
						xtype : 'hidden',
						name : 'compUserId',
						bind : {
							value : '{compUserId}'
						}
					},
					{
						xtype : 'textfield',
						fieldLabel : l10n('id'),
						name : 'compuniqueid',
						allowBlank : false,
						readOnly : true
					},
					{
						xtype : 'textfield',
						name : 'name',
						allowBlank : false,
						maxLength : 256,
						fieldLabel : '<span class="red-label">*</span>'
								+ l10n('display-name')
					},
					{
						xtype : 'textfield',
						name : 'mgmtUrl',
						allowBlank : true,
						maskRe : /[^ ]/,
						maxLength : 256,
						fieldLabel : l10n('management-url'),
						vtype : 'URLValidate',
						value : ''
					},
					{
						xtype : 'textfield',
						name : 'userName',
						fieldLabel : '<span class="red-label">*</span>'
								+ l10n('user-name'),
						allowBlank : false,
						maxLength : 128
					},
					{
						xtype : 'textfield',
						reference : 'replayPassword',
						itemId : 'componentPassword',
						name : 'password',
						minLength : 2,
						maxLength : 158,
						allowBlank : false,
						fieldLabel : '<span class="red-label">*</span>'
								+ l10n('password'),
						inputType : 'password'
					},
					{
						xtype : 'textfield',
						reference : 'replayConfirmPassword',
						name : 'confirmPassword',
						itemId : 'confirmComponentPassword',
						fieldLabel : '<span class="red-label">*</span>'
								+ l10n('verify-password'),
						inputType : 'password',
						initialPassField : 'componentPassword',
						vtype : 'password'
					}, {
						xtype : 'hiddenfield',
						name : 'id'
					}, {
						xtype : 'hiddenfield',
						name : 'componentId'
					}],
			buttons : [ '->', {
				text : l10n('save'),
				reference : 'comp_detail_save',
				formBind : true,
				disabled : true,
				handler : 'componentDetailSave'
			}, {
				text : l10n('cancel'),
				reference : 'comp_detail_reset',
				handler : 'componentDetailReset'
			}, '->' ]
		}, {
            xtype : 'grid',
            reference : 'prefixgrid',
            title : l10n('gateway-prefix'),
            titleAlign : 'center',
            bind : {
                store : '{GatewayPrefixStore}'
            },
            columns : [{
                text : l10n('gateway-id'),
                dataIndex : 'gatewayID',
                flex : 1,
                hidden : true
            }, {
                text : l10n('prefix'),
                dataIndex : 'prefix',
                flex : 1
            }, {
                text : l10n('direction'),
                dataIndex : 'direction',
                flex : 1,
                renderer: function(value) {
                    return value == '1' ? 'OUTBOUND' : 'INBOUND';
                }
            }],
            dockedItems : [{
                xtype : 'pagingtoolbar',
                cls : 'white-footer',
                bind : {
                    store : '{GatewayPrefixStore}'
                },
                dock : 'bottom',
                displayInfo : true,
                afterrender : function(pagingbar) {
                    pagingbar.down('button[itemId=refresh]').destroy();
                }
            }]
        }];
		this.callParent(arguments);
	}
});

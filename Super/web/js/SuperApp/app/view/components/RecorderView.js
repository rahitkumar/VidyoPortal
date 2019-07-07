Ext.define('SuperApp.view.components.RecorderView', {
	extend : 'Ext.window.Window',
	alias : 'widget.recorderView',
	border : false,
	width : 700,
	modal : true,
	frame : true,
	constrain : true,
	closeAction : 'destroy',
	closable : true,
	reference : 'recorderWin',
	resizeable : false,
	title : {
		text : l10n('vidyorecorder'),
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
			reference : 'replayEditform',
			border : false,
			padding : 0,
			margin : 5,
			trackResetOnLoad : true,
			standardSubmit : false,
			requires : [ 'SuperApp.model.components.RecorderModel'],
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
            reference : 'profilegrid',
            title : l10n('recorder-endpoints'),
            titleAlign : 'center',
            margin : 5,
            padding : 0,
            bind : {
                store : '{recorderEndPointStore}',
            },
            border : 1,
            columns : [{
                text : l10n('prefix'),
                dataIndex : 'prefix',
                flex : 1
            }, {
                text : l10n('super-components-rec-html-info'),
                dataIndex : 'description',
                flex : 1
            }, {
                text : l10n('status'),
                dataIndex : 'status',
                flex : 1,
                renderer: function(value) {
                	switch (value) {
                    case 0: 
                        return 'OFFLINE'; 
                        break;
                    case 1: 
                        return 'ONLINE'; 
                        break;
                    case 2: 
                        return 'BUSY'; 
                        break;
                    case 3: 
                        return 'RINGING'; 
                        break;
                    case 4: 
                        return 'RING ACCEPTED'; 
                        break;
                    case 5: 
                        return 'RING REJECTED'; 
                        break;
                    case 6: 
                        return 'RING NO ANSWER'; 
                        break;                        
                    default: 
                        return 'OFFLINE';
                        break;
                };  
                }
                
            }],
            dockedItems : [{
                xtype : 'pagingtoolbar',
                bind : {
                    store : '{recorderEndPointStore}'
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

Ext.define('SuperApp.view.components.VidyoManagerView', {
	extend : 'Ext.window.Window',
	alias : 'widget.vidyoManagerView',
	border : false,
	width : 700,
	modal : true,
	frame : true,
	constrain : true,
	closeAction : 'destroy',
	closable : true,
	reference : 'vidyoManagerWin',
	resizeable : false,
	title : {
		text :  l10n('vidyo-manager'),
		textAlign : 'center'
	},

	initComponent : function() {
		var me = this;

		me.items = [{
			xtype : 'form',
			width : '100%',
			border : false,
			reference : 'editVidyoManagerForm',
			trackResetOnLoad: true,
			standardSubmit : false,
			requires: [
		   		'SuperApp.model.components.VidyoManagerModel'
			],			
			items : [{
				xtype : 'fieldset',
				width : '100%',
				height : '100%',
				layout : {
					type : 'vbox',
					align : 'center'
				},
				defaults : {
	                 width: 600,
 	                 labelWidth: 125
				},
				items : [{
					xtype : 'textfield',
					name : 'compuniqueid',
					reference : 'compuniqueid',
					fieldLabel : l10n('id'),
					readOnly : true,
				}, {
					xtype : 'textfield',
					name : 'name',
					allowBlank : false,
					maxLength : 256,
					fieldLabel : l10n('display-name'),
				}, {
					xtype : 'textfield',
					name : 'mgmtUrl',
					allowBlank : true,
					vtype : 'url',
					maxLength : 256,
					fieldLabel : l10n('management-url'),
					vtype : 'URLValidate'
				}, {
					xtype : 'numberfield',
					name : 'emcpport',
					fieldLabel : l10n('emcp-port'),
					allowBlank : false,
					readOnly : false,
					minValue : 1,
					maxValue : 65535
				}, {
					xtype : 'numberfield',
					name : 'soapport',
					fieldLabel : l10n('soap-port'),
					allowBlank : false,
					readOnly : false,
					minValue : 1,
					maxValue : 65535
				}, {
					xtype : 'numberfield',
					name : 'rmcpport',
					fieldLabel : l10n('rmcp-port'),
					allowBlank : false,
					readOnly : false,
					minValue : 1,
					maxValue : 65535
				}, {
					xtype : 'textfield',
					name : 'fqdn',
					fieldLabel : l10n('fqdn'),
					allowBlank : true,
					maskRe : /[^ ]/,
					maxLength : 256,
					readOnly : true
				}, {
					xtype : 'fieldset',
					layout : {
						type : 'column'
					},
					bodyPadding: 20,
					border : true,
					items : [{
						xtype : 'checkboxfield',
						reference : 'dscpcheckmanager',
						fieldLabel : l10n('dscp-value'),
						handler : 'vmDscpCheckbox'
					}, {
						xtype : 'numberfield',
						reference : 'dscpid',
						name : 'dscpvalue',
						width : 50,
						allowDecimals : false,
						labelValue : 'hexDSCP',
						hideTrigger : true,
						maxValue : 63,
						minValue : 0,
						disabled : true,
						listeners : {
							change : 'onChangeDSCPValue'
						}
					}, {
						xtype : 'label',
						bind : {
							html : '{hexDSCP}'
						}
					}]
				}, {
					xtype : 'hiddenfield',
					name : 'compid'
				}, {
					xtype : 'hiddenfield',
					name : 'id'
				}, {
					xtype : 'hiddenfield',
					name : 'componentId'
				}]
			}],
			buttonAlign : 'center',
			buttons : [{
					text : l10n('save'),
					id: 'save-button',
					formBind : true,
					disabled: true,
					handler : 'vmSave'
				}, {
					text : l10n('cancel'),
					handler : 'vidyoManagerReset'
				}, {
					text : l10n('restore-default'),
					handler : 'onClickRestoreDefaultVM'
				}],
	       listeners: {
	           dirtychange: function(form,isDirty) {
	               //bug in ext js when you combine formbind and dirtycheck so that we manually checking dirty and validation check
	               if(isDirty && form.isValid()){
	                   Ext.getCmp('save-button').enable();
	               }else{
	                  Ext.getCmp('save-button').disable(); 
	               }
	            },
	    
	           afterRender: function() {
	               //bug in ext js.. if you enable formbind=true, it enables the button when you load page.
	               //without a delay, you cant never disable it. this is acceptable as per the ext js doc.
		              setTimeout(function() {
		               Ext.getCmp('save-button').disable();
		           }, 100);
	        	}
	         }
			}];
		this.callParent(arguments);
	}
});

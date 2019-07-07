Ext.define('SuperApp.view.settings.maintenance.StatusNotify', {
	extend : 'Ext.form.Panel',
	alias : 'widget.statusnotify',
	reference : 'statusnotifyForm',
	border : false,
	height : '100%',	 
	title : {
		text : '<span class="header-title">' + l10n('status-notify')
				+ '</span>',
		textAlign : 'center'
	},
	requires : [ 'SuperApp.view.settings.maintenance.MaintenanceController',
			'SuperApp.model.settings.StatusNotifyModel' ],
	controller : 'MaintenanceController',
	// configure how to read the XML data, using an instance
	reader : {
		type : 'xml',
		model : 'SuperApp.model.settings.StatusNotifyModel',
		totalRecords : 'results',
		record : 'row',
		rootProperty : 'dataset'
	},
	// configure how to read the XML error, using a config
	errorReader : {
		type : 'xml',
		model : 'SuperApp.model.settings.StatusNotifyModel',
		record : 'row',
		successProperty : '@success'
	},
    trackResetOnLoad: true,
	items : [ {
		xtype : 'fieldset',
		border : 0,
		height : '100%',
		bodyStyle : 'padding: 10px',
		layout : {
			type : 'vbox',
			align : 'center',
			pack : 'center',
		},
		items : [
				{
					xtype : 'checkbox',
					name : 'flag',
					width : 300,
					allowBlank : false,
					boxLabel : l10n('enabled'),
					fieldLabel : l10n('status-notify'),
					listeners : {
						change : function(cb, checked) {
							var fieldset = cb.ownerCt;
							Ext.Array.forEach(fieldset.query('textfield'),
									function(field) {
										field.setDisabled(!checked);
										field.el.animate({
											opacity : !checked ? 0.3 : 1
										});
									});
						}
					}
				},
				{
					xtype : 'textfield',
					name : 'url',
					vtype : 'url',
					fieldLabel : '<span class="red">*</span>' + l10n('url'),
					allowBlank : false,
					width : 300,
					disabled : true,
					maxLength: 2000,
					enforceMaxLength: true
				},
				{
					xtype : 'textfield',
					id : 'username',
					name : 'username',
					maxLength : 200,
					fieldLabel : '<span class="red">*</span>'
							+ l10n('username'),
					allowBlank : false,
					width : 300,
					maskRe : /[^ ]/,
					disabled : true
				},
				{
					xtype : 'textfield',
					name : 'password',
					maxLength : 200,
					fieldLabel : '<span class="red">*</span>'
							+ l10n('password'),
					allowBlank : false,
					inputType : 'password',
					width : 300,
					maskRe : /[^ ]/,
					disabled : true
				} ]

	} ],
	buttonAlign : 'center',
	buttons : [{
		text : l10n('save'),
		formBind : true,
		disabled : true,
		handler : 'onSaveStatusNotify'
	}, {
		text : l10n('cancel'),
		handler : 'cancelStatusNotify'
	}]

});

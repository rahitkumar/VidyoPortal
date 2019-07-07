Ext.apply(Ext.form.field.VTypes, {
	IPAddress : function(v) {
		return /^\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}$/.test(v);
	}
});

Ext.define('SuperApp.view.settings.maintenance.Syslog', {
	extend : 'Ext.form.Panel',
	alias : 'widget.syslog',
	autoDestroy : true,
	reference : 'sysLogForm',
	border : 0,
	frame : true,
	title : {
		text : '<span class="header-title">'
				+ l10n('syslog') + '</span>',
		textAlign : 'center'
	},
	requires : [ 'SuperApp.view.settings.maintenance.MaintenanceController',
	 			'SuperApp.model.settings.SysLogModel' ],
	controller : 'MaintenanceController',	 			
	// configure how to read the XML data, using an instance
	reader : {
		type : 'xml',
		model : 'SuperApp.model.settings.SysLogModel',
		totalRecords : 'results',
		record : 'row',
		rootProperty : 'dataset'
	},
	errorReader : Ext.create('Ext.data.XmlReader', {
		record : 'field',
		model : Ext.create("SuperApp.model.settings.Field"),
		success : '@success'
	}),
	trackResetOnLoad: true,
	items : [ {
		xtype : 'fieldset',
		reference : 'syslogConfigFieldset',
		width : '100%',
		height: '100%',
		layout : {
			type : 'vbox',
			align : 'center',
			pack : 'center'
		},
		items : [{
			xtype : 'checkbox',
			width : 300,
			labelWidth : 150,
			allowBlank : false,
			fieldLabel : l10n('enabled'),
			name : 'remote_logging',
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
					Ext.Array.forEach(fieldset.query('checkbox[name=stunnel]'),
							function(field) {
						field.setDisabled(!checked);
						field.el.animate({
							opacity : !checked ? 0.3 : 1
						});
					});
				}
			}
		}, {
			xtype : 'checkboxfield',
			name : 'stunnel',
			fieldLabel : l10n('use-stunnel'),
			inputValue : 'on',
			width : 300,
			labelWidth : 150,
			reference : 'stunnelConfig',
			disabled : true
		}, {
			xtype : 'textfield',
			name : 'ip_address',
			fieldLabel : '*' + l10n('remote-ipaddress'),
			allowBlank : false,
			vtype : 'IPAddress',
			vtypeText : l10n('invalid-ipaddress'),
			reference : 'ipaddress',
			labelAlign : 'left',
			width : 300,
			labelWidth : 150,
			disabled : true
		}, {
			xtype : 'numberfield',
			name : 'port',
			fieldLabel : '*' + l10n('remote-port'),
			reference : 'remoteport',
			allowBlank : false,
			minValue : 1,
			maxValue : 65535,
			hideTrigger : true,
			labelAlign : 'left',
			width : 300,
			labelWidth : 150,
			disabled : true,
            listeners: {
                change: function(field, value) {
                    value = Ext.String.htmlEncode(value);
                    field.setValue(value);
                }
            }
		} ]
	} ],
	buttonAlign : 'center',
	buttons : [{
		text : l10n('save'),
		formBind : true,
		disabled : true,
		reference : 'syslogSave',
		handler : 'sysLogSaveForm'
	}, {
		text : l10n('cancel'),
		handler : 'cancelSyslog'
	}]
});

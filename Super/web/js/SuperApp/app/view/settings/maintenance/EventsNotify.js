Ext.define('SuperApp.view.settings.maintenance.EventsNotify', {
	extend : 'Ext.form.Panel',
	alias : 'widget.eventsnotify',
	reference : 'eventsNotificationServerForm',
	border : false,
	height : '100%',
	title : {
		text : '<span class="header-title">' + 'Events Notification Server'
				+ '</span>',
		textAlign : 'center'
	},
	requires : [ 'SuperApp.view.settings.maintenance.MaintenanceController',
			'SuperApp.model.settings.EventsNotifyServerModel' ],
	controller : 'MaintenanceController',
	// configure how to read the XML data, using an instance
	reader : {
		type : 'json',
		model : 'SuperApp.model.settings.EventsNotifyServerModel'
	},
	// configure how to read the XML error, using a config
	errorReader : {
		type : 'json',
		model : 'SuperApp.model.settings.EventsNotifyServerModel'
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
					name : 'eventsNotificationEnabled',
					width : 180,
					allowBlank : false,
					boxLabelAlign : 'after',
					boxLabel : 'Enable Events Notification',
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
					vtype : 'FQDNValidate',
					name : 'primaryServer',
					fieldLabel : '<span class="red">*</span>' + 'Primary Server',
					allowBlank : false,
					width : 400,
					disabled : true,
					maxLength: 2000,
					enforceMaxLength: true
				},
				{
					xtype : 'numberfield',
					id : 'primaryServerPort',
					name : 'primaryServerPort',
					maxLength : 5,
					fieldLabel : '<span class="red">*</span>'
							+ 'Port',
					allowBlank : false,
					width : 400,
					maskRe : /[^ ]/,
					disabled : true,
					allowDecimals : false,
					allowExponential : false,
                    hideTrigger : true,
                    minValue : 0,
                    maxValue : 65535,
                    listeners: {
                        change: function(field, value) {
                            value = Ext.String.htmlEncode(value);
                            field.setValue(value);
                        }
                    }
				},{
					xtype : 'textfield',
					vtype : 'FQDNValidate',
					name : 'secondaryServer',
					fieldLabel : 'Secondary Server',
					width : 400,
					disabled : true,
					maxLength: 2000,
					enforceMaxLength: true
				},
				{
					xtype : 'numberfield',
					id : 'secondaryServerPort',
					name : 'secondaryServerPort',
					maxLength : 5,
					fieldLabel : 'Port',
					width : 400,
					maskRe : /[^ ]/,
					disabled : true,
					allowDecimals : false,
					allowExponential : false,
                    hideTrigger : true,
                    minValue : 0,
                    maxValue : 65535,
                    listeners: {
                        change: function(field, value) {
                            value = Ext.String.htmlEncode(value);
                            field.setValue(value);
                        }
                    }
				}]

	} ],
	buttonAlign : 'center',
	buttons : [{
		text : l10n('save'),
		formBind : true,
		disabled : true,
		handler : 'saveEventsNotifyServer'
	}, {
		text : l10n('cancel'),
		handler : 'cancelEventsNotifyServer'
	}]

});

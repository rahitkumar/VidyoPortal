 /**
 * @class EpicIntegration
 */
Ext.define('AdminApp.view.settings.featuresettings.EpicIntegration', {
        extend: 'Ext.form.Panel',
        alias: 'widget.epicintegration',
        reference: 'epicintegration',
        border: false,
        height: 800,
        requires: ['AdminApp.view.settings.featuresettings.FeatureSettingsController', 'AdminApp.view.settings.featuresettings.FeatureSettingsViewModel',
        	'AdminApp.model.settings.EpicIntegrationModel'],
        controller: 'FeatureSettingsController',
        viewModel: {
            type: 'FeatureSettingsViewModel'
        },
        title: {
            text: '<span class="header-title">' + l10n('epic-integration') + '</span>',
            textAlign: 'center'
        },
        reader : {
    		type : 'xml',
    		model : 'AdminApp.model.settings.EpicIntegrationModel',
    		totalRecords : 'results',
    		record : 'row',
    		rootProperty : 'dataset'
    	},
    	errorReader : {
    		type : 'xml',
    		model : 'AdminApp.model.settings.EpicIntegrationModel',
    		record : 'row',
    		successProperty : '@success'
    	},
        items: [{
            xtype: 'fieldset',
            height: '100%',
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'center',
            },
            items: [{
                xtype: 'checkbox',
                name: 'enableEpicIntegration',
                reference:'enableEpic',
                margin: 20,
                columns: 1,
                width: 500,
                labelWidth: 300,
                inputValue: '1',
            	uncheckedValue: '0',
                fieldLabel: l10n('enable-epic'),
                // checked : (enableEpicIntegration == 1 ? true : false),
                bind:'{enableEpicIntegration}',
                listeners: {
					change: function(checkbox, newValue, oldValue) {
						var form = checkbox.up('form').getForm();
                		var sharedSecret = form.findField('sharedSecret');
						var notificationUrl = form.findField('notificationUrl');
						var notificationUser = form.findField('notificationUser');
						var notificationPassword = form.findField('notificationPassword');
						var saveEpicIntegration = form.findField('saveEpicIntegration');
						var cancelEpicIntegration = form.findField('cancelEpicIntegration');

						if (newValue) { // checkbox is checked
							var cryptRegex = /^[a-zA-Z0-9-_]+$/;
                			var cryptRegexResult = cryptRegex.test(sharedSecret.value);
                			if (!cryptRegexResult) {
                				var message = l10n('epic-shared-secret-missed');
								sharedSecret.setValidation(message);
								sharedSecret.validate(); // force to disable submit button
							}
							var urlRegex = /^(http|https):\/\/[a-zA-Z0-9.\-:\/]+$/;
							var urlRegexResult = urlRegex.test(notificationUrl.value);
                			if (!urlRegexResult) {
                				var message = l10n('epic-notification-url-missed');
								notificationUrl.setValidation(message);
								notificationUrl.validate(); // force to disable submit button
							}
     
							if (!cryptRegexResult|!urlRegexResult) return;
						}
						sharedSecret.clearInvalid();
						sharedSecret.setValidation(false);
						notificationUrl.clearInvalid();
						notificationUrl.setValidation(false);
					}
		  		}
            }, {
                xtype : 'textfield',
		  		name : 'sharedSecret',
		  		width: 400,
                labelWidth: 100,
		  		fieldLabel : l10n('epic-shared-secret'),
		  		msgType : 'under',
		  		bind:{
                	value:'{sharedSecret}',
                	disabled: '{!enableEpic.checked}'
                	},
		  		listeners: {
					change: function(textfield, newValue, oldValue) {
						var cryptRegex = /^[a-zA-Z0-9-_]+$/;
                		var form = textfield.up('form').getForm();
                		var epicCheckbox = form.findField('enableEpicIntegration');
                		var sharedSecret = form.findField('sharedSecret');
                		
                		if (epicCheckbox.checked) {
                			var result = cryptRegex.test(sharedSecret.value);
                			if (!result) {
								var message = l10n('epic-shared-secret-missed');
								sharedSecret.setValidation(message); 
								return;
							}
                		}
                		sharedSecret.clearInvalid();
                		sharedSecret.setValidation(false);
		       		}
		  		}
	    	}, {
                xtype : 'textfield',
				name : 'notificationUrl',
				width: 400,
                labelWidth: 100,
				maxLength : 2048,
				fieldLabel : l10n('epic-notification-url'),
				msgType : 'under',
				bind:{
                	value:'{notificationUrl}',
                	disabled: '{!enableEpic.checked}'
                	},
				listeners: {
					change: function(textfield, newValue, oldValue) {
						var urlRegex = /^(http|https):\/\/[a-zA-Z0-9.\-:\/]+$/;
                		var form = textfield.up('form').getForm();
                		var epicCheckbox = form.findField('enableEpicIntegration');
                		
						if (epicCheckbox.checked) {
                			var result = urlRegex.test(newValue);
                			if (!result) {
								var message = l10n('epic-notification-url-missed');
								textfield.setValidation(message); 
								return;
							}
                		}
						textfield.clearInvalid();
                		textfield.setValidation(false);
					}
				}
	    	}, {
                xtype : 'textfield',
				name : 'notificationUser',
				width: 400,
                labelWidth: 100,
				maxLength : 256,
				fieldLabel : l10n('epic-notification-user'),
				bind:{
                	value:'{notificationUser}',
                	disabled: '{!enableEpic.checked}'
                	},
                listeners: {
    					change: function(textfield, newValue, oldValue) {
                    		var form = textfield.up('form').getForm();
                    		var epicCheckbox = form.findField('enableEpicIntegration');
                    		var notificationPassword = form.findField('notificationPassword');
                    		var notificationUser = form.findField('notificationUser');
                    		var passwordValue = notificationPassword.value;
                    		var userValue = notificationUser.value;
    						if (epicCheckbox.checked) {
    							if(passwordValue!="" && userValue=="") {
    								notificationUser.allowBlank = false;
    								notificationPassword.allowBlank = true;
    								notificationUser.validate();
    	
    							} 
    							if(userValue!="" && passwordValue==""){
    								notificationUser.allowBlank = true;
    								notificationPassword.allowBlank = false;
    								notificationPassword.validate();
    						
    							} 
    							if(userValue=="" && passwordValue==""){
    								notificationUser.allowBlank = true;
    								notificationPassword.allowBlank = true;
    								notificationUser.clearInvalid();
    								notificationUser.setValidation(false);
    								notificationPassword.clearInvalid();
    								notificationPassword.setValidation(false);
    								notificationUser.reset();
    								notificationPassword.reset();
    							}
                    		}
    						
    					}
    				}
				
	    	}, {
                xtype : 'textfield',
                inputType : 'password',
				name : 'notificationPassword',
				width: 400,
                labelWidth: 100,
				maxLength : 256,
				maskRe : /[^ ]/,
				fieldLabel : l10n('epic-notification-password'),
				bind:{
                	value:'{notificationPassword}',
                	disabled: '{!enableEpic.checked}'
                	},
                listeners: {
    					change: function(textfield, newValue, oldValue) {
                    		var form = textfield.up('form').getForm();
                    		var epicCheckbox = form.findField('enableEpicIntegration');
                    		var notificationUser = form.findField('notificationUser');
                    		var notificationPassword = form.findField('notificationPassword');
                    		var passwordValue = notificationPassword.value;
                    		var userValue = notificationUser.value;
    						if (epicCheckbox.checked) {
    							if(passwordValue!="" && userValue=="") {
    								notificationUser.allowBlank = false;
    								notificationPassword.allowBlank = true;
    								notificationUser.validate();
    							
    							}
    							if(userValue!="" && passwordValue==""){
    								notificationUser.allowBlank = true;
    								notificationPassword.allowBlank = false;
    								notificationPassword.validate();
    		
    							}
    							if(userValue=="" && passwordValue==""){
    								notificationUser.allowBlank = true;
    								notificationPassword.allowBlank = true;
    								notificationUser.clearInvalid();
    								notificationUser.setValidation(false);
    								notificationPassword.clearInvalid();
    								notificationPassword.setValidation(false);
    								notificationUser.reset();
    								notificationPassword.reset();
    							}
                    		}
    					}
    				}
            }]
    }],
    buttonAlign: 'center',
    buttons: [{
        text: l10n('save'),
        name : 'saveEpicIntegration',
        id : 'epic-save-button',
        formBind:true,
        listeners: {
        	click: 'onClickSaveEpicIntegration'
        }
    },{
        text: l10n('cancel'),
        name : 'cancelEpicIntegration',
        bind: {
            disabled: '{!enableEpic.checked}'
        },
        listeners: {
        	 click: 'onClickCancelEpicIntegration'
        }
    }]
        
        
});
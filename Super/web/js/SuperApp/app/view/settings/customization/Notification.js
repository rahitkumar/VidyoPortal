Ext.define('SuperApp.view.settings.customization.Notification', {
    extend : 'Ext.panel.Panel',
    autoDestroy : true,
    border : false,
    alias : 'widget.notification',
    title : {
        text : '<span class="header-title">'+l10n('notification')+'</span>',
        textAlign : 'center'
    },
    
    viewModel : {
        type : 'CustomizationViewModel'
    },

    initComponent : function() {
        var me = this;

        me.items = [
				{
				    xtype : 'checkbox',
				    itemId : 'notificationChkBox',
				    name : 'notificationFlag',
				    reference : 'notificationFlagRef',
				    allowBlank : false,
				    bind : {
				        value : '{notificationFlag}'
				    },
				    layout: {
				        type: 'vbox',
				        pack: 'center',
				    },
				    labelWidth : 250,
				    boxLabel : l10n('enabled'),
				    fieldLabel : l10n('enable-email-notifications'),
				    listeners : {
				        change : function(cb, checked) {				        	
				        	me.enableDisableFields(checked);
				        }
				    }
				},
				{
                xtype : 'form',
                itemId : 'emailNotificationSettingsForm',
                reference : 'notificationdetails',
                border : false,
                defaults : {
                    labelWidth : 150,
                    margin : 5,
                    msgTarget : 'under'
                },
			    layout: {
			        type: 'vbox',
			        pack: 'center',
			    },
                items : [{
                    xtype : 'hiddenfield',
                    name : 'tenantID',
                    bind : {
                        value : '{tenantID}'
                    }
                }, {
                    xtype : 'textfield',
                    name : 'fromEmail',
                    vtype : 'vidyoEmail',
                    width:500,
                    allowBlank : false,
                    fieldLabel : l10n('email-from'),
                    bind : {
                        value : '{fromEmail}'
                    }
                }, {
                    xtype : 'textfield',
                    name : 'toEmail',
                    vtype : 'vidyoEmail',
                    width:500,
                    allowBlank : false,
                    fieldLabel : l10n('email-to'),
                    bind : {
                        value : '{toEmail}'
                    }
                }, {
                    xtype : 'textfield',
                    name : 'smtpHost',
                    width:500,
                    fieldLabel : l10n('smtp-hostname'),
                    bind : {
                        value : '{smtpHost}'
                    }
                }, {
                    xtype : 'numberfield',
                    name : 'smtpPort',
                    minValue : 1,
                    hideTrigger : true,
                    fieldLabel : l10n('smtp-port'),
                    bind : {
                        value : '{smtpPort}'
                    },
                    listeners: {
                        change: function(field, value) {
                            value = Ext.String.htmlEncode(value);
                            field.setValue(value);
                        }
                    }
                }, {
                    xtype : 'combo',
                    name : "smtpSecurity",
                    allowBlank : false,
                    typeAhead : false,
                    triggerAction : "all",
                    editable : false,
                    bind : {
                        value : '{smtpSecurity}'
                    },
                    store : [['NONE', 'NONE'], ['STARTTLS', 'STARTTLS'], ['SSL_TLS', 'SSL/TLS']],
                    fieldLabel : l10n('security')
                }, {
                    xtype : 'checkbox',
                    name : "smtpTrustAllCerts",
                    fieldLabel : l10n('trust-all-certs'),
                    bind : {
                        value : '{smtpTrustAllCerts}' == "true" ? true : false
                    }
                }, {
                    xtype : 'textfield',
                    name : 'smtpUsername',
                    maxLength : 128,
                    fieldLabel : l10n('smtp-username'),
                    bind : {
                        value : '{smtpUsername}'
                    }
                }, {
                    xtype : 'textfield',
                    name : 'smtpPassword',
                    inputType : 'password',
                    fieldLabel : l10n('smtp-password'),
                    bind : {
                        value : '{smtpPassword}'
                    },
                    maskRe: /[^ ]/
                }],
                buttons : ['->', {
                    text : l10n('test'),
                    name : 'NotificationTest',
                    formBind : true,
                    listeners : {
                        click : 'onClickTestNotification'
                    }
                }, {
                    text : l10n('default'),
                    name : 'NotificationDefault',
                    listeners : {
                        click : 'onClickDefaultNotification'
                    }
                }, {
                    text : l10n('save'),
                    formBind : true,
                    listeners : {
                        click : 'onClickSaveNotification'
                    }
                }, {
                    text : l10n('cancel'),
                    listeners : {
                        click : 'onClickResetNotification'
                    }
                }, '->']
            }
        ];

        me.callParent(arguments);
        Ext.apply(Ext.form.VTypes, {
            vidyoEmail : function(val) {
                var reg = /^([A-Za-z0-9_\-\.])+@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,})$/;
                return reg.test(val);
            },
            vidyoEmailText : l10n('must-be-a-valid-e-mail-address'),

            password : function(val, field) {
                if (field.initialPassField) {
                    var pwd = Ext.getCmp(field.initialPassField);
                    return (val == pwd.getValue());
                }
                return true;
            },
            passwordText : l10n('keystore-password-does-notmatch'),

        });
    },

    /***
     * @function showNotificationForm
     *
     * @desc set value of radio group so that we can show/hide the
     *  form using the change event of radiogroup.
     */
    showNotificationForm : function(flag) {
        var me = this;
        cb = me.down('[itemId=notificationChkBox]');
        cb.setValue(flag);
        me.enableDisableFields(flag);
    },
    
    enableDisableFields : function(flag) {
    	var me = this;
        var emailNotificationSettingsForm = me.down('[itemId=emailNotificationSettingsForm]');
        fromEmail = me.down('textfield[name=fromEmail]'),
        toEmail = me.down('textfield[name=toEmail]'),
        smtpHost = me.down('textfield[name=smtpHost]');
        smtpPort = me.down('numberfield[name=smtpPort]');
        smtpSecurity = me.down('combo[name=smtpSecurity]');
        smtpTrustAllCerts = me.down('checkbox[name=smtpTrustAllCerts]');
        smtpUsername = me.down('textfield[name=smtpUsername]');
        smtpPassword = me.down('textfield[name=smtpPassword]');
        if (!flag || flag == 'false') {
        	fromEmail.disable();
        	toEmail.disable();
        	smtpHost.disable();
        	smtpPort.disable();
        	smtpSecurity.disable();
        	smtpTrustAllCerts.disable();
        	smtpUsername.disable();
        	smtpPassword.disable();
        	emailNotificationSettingsForm.getForm().isValid();
            return;
        }
	    fromEmail.enable();
	    toEmail.enable();
	    smtpHost.enable();
	    smtpPort.enable();
    	smtpSecurity.enable();
    	smtpTrustAllCerts.enable();
    	smtpUsername.enable();
    	smtpPassword.enable();
	    emailNotificationSettingsForm.getForm().isValid(); 
    }
});


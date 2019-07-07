Ext.define('AdminApp.view.settings.customization.Notification', {
    extend : 'Ext.form.Panel',
    autoDestroy : true,
    alias : 'widget.notification',
    height:800,
    requires : ['AdminApp.view.settings.customization.CustomizationViewModel', 'AdminApp.view.settings.customization.CustomizationController'],

    viewModel : {
        type : 'CustomizationViewModel'
    },

    controller : 'CustomizationController',

    title : {
        text : '<span class="header-title">' + l10n('notification') + '</span>',
        textAlign : 'center'
    },

    items :[
    {
        xtype : 'fieldset',
        height : '100%',
        width:'100%',
              
        layout : {
            type : 'vbox',
            align : 'center',
           
        },
        items : [
        {
            xtype : 'hiddenfield',
            name : 'tenantID'
        }, {
            xtype : 'textfield',
            fieldLabel : l10n('email-from'),
            labelWidth : 125,
           margin : '10,0,0,0',
            width:'50%',
            vtype : 'email',
            vtypeText: l10n('must-be-a-valid-e-mail-address'),
            msgTarget:'under',
            name : 'fromEmail',
            bind : {
                value : '{fromEmail}'
            }
        }, {
            xtype : 'textfield',
              margin : '10,0,0,0',
            fieldLabel : l10n('email-to'),
            vtype : 'email',
            vtypeText: l10n('must-be-a-valid-e-mail-address'),
            labelWidth : 125,
           
         width:'50%',
            name : 'toEmail',
            msgTarget:'under',
            bind : {
                value : '{toEmail}'
            }
        }, {
            xtype : 'checkbox',
            name : 'enableNewAccountNotification',
            fieldLabel : l10n('new-account-notification'),
            labelWidth : 227,
              margin : '20,0,0,0',
               width:'70%',
            inputValue : 'on',
            bind : {
                value : '{enableNewAccountNotification}'
            }
        }
        ]
    }],
    buttonAlign : 'center',
    buttons : [
        {
            text : l10n('save'),
            formBind:true,
             listeners : {
                    click : 'onClickSaveNotification'
                }
        }, 
        {
                text : l10n('cancel'),
                listeners : {
                    click : 'onClickDefaultNotification'
                }
        }
    ]
     
});


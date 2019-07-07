/***
 * @class AuthenticationView
 */
Ext.define('AdminApp.view.settings.auth.AuthenticationView', {
    extend: 'Ext.form.Panel',
    alias: 'widget.authenticationview',
    trackResetOnLoad: true,
    requires: ['AdminApp.view.settings.auth.LDAPConnection','AdminApp.view.settings.auth.LDAPMapping', 'AdminApp.view.settings.auth.SAML','AdminApp.view.settings.auth.Webservice','AdminApp.view.settings.auth.RestWebservice',
                'AdminApp.view.settings.auth.UserType','AdminApp.view.settings.auth.LDAP','AdminApp.view.settings.auth.PKI','AdminApp.view.settings.auth.AuthenticationViewModel', 'AdminApp.view.settings.auth.LDAPOverlay', 'AdminApp.view.settings.auth.LDAPAttributeMappingGrid', 'AdminApp.view.settings.auth.AuthenticationViewController', 'AdminApp.model.settings.Roles', 'AdminApp.model.Field','AdminApp.view.settings.auth.PKICertificateUpload'],


    viewModel: {
        type: 'AuthenticationViewModel'
    },

    controller: 'AuthenticationViewController',
    frame: false,
    minHeight: 700,

    title: {
        text: '<span class="header-title">' + l10n('authentication') + '</span>',
        textAlign: 'center'
    },

    errorReader: {
        type: 'xml',
        record: 'field',
        model: 'AdminApp.model.Field',
        success: '@success'
    },
    
    reference: 'authenticationForm',
    initComponent: function () {
        var me =this;
me.items=[{
            xtype: 'fieldset',      
            minHeight: 700,          
            layout: {
                type: 'vbox',
                align:'center'
              },
            items: [{
                xtype: 'combo',
                bind: {
                    store: '{authTypeStore}'
                },
                fieldLabel: l10n('authentication') + l10n('type'),
                value: 'NORMAL',
                name: 'authType',
                reference: 'authType',
                allowBlank: false,
                editable: false,            
                labelWidth: 160,
                valueField: 'type',
                displayField: 'name',
               listeners: {
                    change: 'onChangeAuthType'
                }
            }, {xtype:'container',
                   width: '100%',
                reference:'authpanel',
                
            }]
            }];
             me.callParent();
    },
       
    buttonAlign: 'center',
    buttons: [{
        text: l10n('save'),
        formBind: false,
        name: 'authSave',
        id:'authsave',
        reference: 'authsave',
        disabled: true, //only enable when test connection is success
        listeners: {
            click: 'onClickSaveAuthentication'
        }
    }, {
        text: l10n('cancel'),
        listeners: {
            click: 'getAuthenticationData'
        }
    }],
       listeners: {
        render: 'authFormRender'
    }
 
});
Ext.define('AdminApp.view.settings.auth.LDAPConnection', {
    extend: 'Ext.form.Panel',
    alias: 'widget.ldapconnection',
    bodyPadding: 20,     
    width:'100%',     
        reference: 'ldapconnectionform',
        trackResetOnLoad: true,
        bodyStyle: 'background:#F6F6F6;',
        items: [  {
                xtype: 'hiddenfield',
                name: 'isldapconnectiontestsuccess',
                id:'isldapconnectiontestsuccess'
            },
            {
                xtype: 'fieldset',
                title: 'LDAP Connection details',   
                     layout: {
                type: 'vbox',
                align:'center'
              },
                           
                items: [{
                    xtype: 'textfield',
                    name: 'ldapurl',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('url'),
                    allowBlank: false,
                    width: 500
                }, {
                        xtype: 'textfield',
                        name: 'ldapusername',
                        fieldLabel: '<span class="red-label">*</span>' + l10n('bind-dn-or-username'),
                        allowBlank: false,
                        width: 500
                    }, {
                        xtype: 'textfield',
                        name: 'ldappassword',
                        fieldLabel: '<span class="red-label">*</span>' + l10n('bind-password'),
                        inputType: 'password',
                        allowBlank: false,
                        width: 500
                    }, {
                        xtype: 'textfield',
                        name: 'ldapbase',
                        fieldLabel: l10n('search-base'),
                        allowBlank: true,
                        width: 500
                    }, {
                        xtype: 'textfield',
                        name: 'ldapfilter',
                        fieldLabel: '<span class="red-label">*</span>' + l10n('filter-template'),
                        helpText: 'use <> for username substitution',
                        allowBlank: false,
                        width: 500
                    }, {
                        xtype: 'radiogroup',
                        width: 500,
                        fieldLabel: '<span class="red-label">*</span>' + l10n('scope'),
                        vertical: false,
                        name: 'ldapscopeGroup',
                        items: [{
                            name: 'ldapscope',
                            inputValue: 0,
                            boxLabel: l10n('object'),
                            checked: true
                        }, {
                                name: 'ldapscope',
                                inputValue: 1,
                                boxLabel: l10n('one-level')
                            }, {
                                name: 'ldapscope',
                                inputValue: 2,
                                boxLabel: l10n('subtree')
                            }]
                    }]
            }],

        buttonAlign: 'center',
        buttons: [{

            text: l10n('connection-test'),
            id:'connectiontestbutton',
            tooltip: l10n('check-connection-to-ldap-server-and-user-authentication-before-saving'),
            viewtype: 'ldap',
             testLdap: true,
            listeners: {
                click: 'onClickConnectionTest'
            }
        }],
        listeners: {
            dirtychange: function (form, isDirty) {
                //bug in ext js when you combine formbind and dirtycheck so that we manually checking dirty and validation check
                if (isDirty && form.isValid()) {
                   Ext.getCmp('isldapconnectiontestsuccess').setValue(false); 
                    Ext.getCmp('authsave').disable();
                }
            },
            validitychange: function (obj, isValid) {
                       if(!isValid){
                          Ext.getCmp('isldapconnectiontestsuccess').setValue(false); 
                           Ext.getCmp('authsave').disable();
                       }
            }

        }
        

 

});
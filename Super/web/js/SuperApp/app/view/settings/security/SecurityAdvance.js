/**
 * @class SecurityAdvance
 */
Ext.define('SuperApp.view.settings.security.SecurityAdvance', {
    extend : 'Ext.form.Panel',
    alias : 'widget.securityadvance',

    requires : ['SuperApp.view.settings.security.SecurityOverlay', 'SuperApp.view.settings.security.SecurityViewModel', 'SuperApp.view.settings.security.SecurityController'],
    title : {
    	text: l10n('advanced'),
    	textAlign : 'center'
    },

    reference : 'securityadvance',

    controller : 'SecurityController',
    
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    buttonAlign : 'center',
    initComponent : function() {
        var me = this;
        me.items = [{
            xtype : 'fieldset',
            margin : 5,
            padding : 5,
            title : l10n('actions'),
            width : '100%',
            autoHeight : true,
            layout : {
            	type : 'vbox',
                align : 'left'
            },
            defaults : {
                margin : 3,
                padding : 1
            },
            items : [{
                xtype : 'button',
                text : l10n('super-ssl-advanced-button-import-certificate-bundle'),
                tooltip : l10n('super-security-ssl-upload-from-a-p7b-pfx-or-vidyo-file'),
                viewtype : 'securitysettings',
                bind : {
                    disabled : '{sslOptionsFlag}'
                },
                listeners : {
                    click : 'showUploadWindow'
                }
            }, {
                xtype : 'button',
                text : l10n('super-ssl-advanced-button-export-security-bundle'),
                tooltip : l10n('super-security-ssl-export-vidyo-file'),
                viewtype : 'passwordWindow',
                listeners : {
                    click : 'onClickSAExport'
                }
            }, {
                xtype : 'button',
                text : l10n('super-ssl-advanced-button-import-client-ca-certificates'),
                tooltip : l10n('super-security-ssl-upload-client-ca-root-certificate'),
                viewtype : 'rootcertificate',
                listeners : {
                    click : 'showUploadWindow'
                }
            }, {
                xtype : 'button',
                text : l10n('configure-client-ca-certificates'),
                tooltip : l10n('enable-disable-use-of-default-client-ca-root-certificates'),
                viewtype : 'clientcacert',
                listeners : {
                    click : 'showUploadWindow'
                }
            }, {
                xtype : 'button',
                text : l10n('super-ssl-advanced-button-reset-security'),
                tooltip : l10n('super-security-ssl-reset-all-security-settings-to-factory-defaults'),
                bind : {
                    disabled : '{sslOptionsFlag}'
                },
                listeners : {
                    click : 'onClickSAReset'
                }
            }]
        }, {
            xtype : 'fieldset',
            title : 'OCSP',
            margin : 5,
            reference : 'ocspfieldset',
            padding : 5,
            width : '100%',
            layout : {
                align : 'center'
            },
            defaults : {
                margin : 3,
                padding : 1
            },
            items : [{
                xtype : 'label',
                margin : 0,
                padding : 0,
                width : '100%',
                text : l10n('super-security-private-ocsp-notes')
            }, {
                xtype : 'checkbox',
                name : 'ocspEnable',
                reference : 'ocspEnable',
                inputValue : 'on',
                bind : {
                    value : "{ocspEnabledFlag}"
                },
                fieldLabel : l10n('super-security-private-ocsp-enable'),
                labelWidth : 400,
                labelAlign : 'right',
                listeners : {
                    change : 'onChangeCheckOSCPEnable'
                }
            }, {
                xtype : 'checkbox',
                reference : 'overrideResponder',
                inputValue : 'on',
                name : 'overrideResponder',
                bind : {
                    value : "{ocspOverrideFlag}"
                },
                fieldLabel : l10n('super-security-private-ocsp-override-responder'),
                labelWidth : 400,
                labelAlign : 'right',
                listeners : {
                    change : 'onChangeCheckOSCPOverride'
                }
            }, {
                xtype : 'textfield',
                reference : 'defaultResponder',
                name : 'defaultResponder',
                fieldLabel : l10n('responder-url'),
                bind : {
                    value : "{ocspDefaultResponder}",
                    disabled : "{ocspTextflag}"
                },
                allowBlank : false,
                labelWidth : 400,
                labelAlign : 'right'
            }]
        }];
        me.buttons = ['->', {
            text : l10n('save-ocsp-settings'),
            listeners : {
                click : 'onClickSaveOCSPSettings'
            }
        }, '->'];
        me.callParent(arguments);
    }
});

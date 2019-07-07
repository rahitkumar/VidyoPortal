/**
 * @class SecurityView
 */
Ext.define('SuperApp.view.settings.security.SecurityView', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.securityview',
    requires : ['SuperApp.view.settings.security.SecurityPrivateKey', 'Ext.ux.statusbar.StatusBar', 'SuperApp.view.settings.security.SecurityCSR', 'SuperApp.view.settings.security.ServerCertificate', 'SuperApp.view.settings.security.CACertificate', 'SuperApp.view.settings.security.SecurityApplications', 'SuperApp.view.settings.security.SecurityAdvance', 'SuperApp.view.settings.security.Passwords', 'SuperApp.view.settings.security.SecurityViewModel', 'SuperApp.view.settings.security.SecurityController'],
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    title : {
    	text: '<span class="header-title">'+l10n('security')+'</span>',
    	textAlign: 'center'
    },
    viewModel : {
        type : 'SecurityViewModel'
    },
    border : false,

    width : '100%',

    controller : 'SecurityController',

    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'fieldset',
            padding : 5,
            bind : {
                hidden : '{isPasswordView}'
            },
            layout : {
                type : 'vbox',
                align : 'center'
            },
            items : [
                {
                    xtype : 'button',
                    bind : {
                        text : '{sslText}'
                    },
                    listeners : {
                        click : 'onClickSSLEnableToggle'
                    }
                }, {
                    xtype : 'checkbox',
                    boxLabel : l10n('super-security-forced-https'),
                    boxLabelAlign: 'after',
                    reference : 'httpsonly',
                    disabled : true,
                    state : 1,
                    bind : {
                        disabled : '{sslEnabledFlag}',
                        value : '{httpsOnlyFlag}'
                    },
                    listeners : {
                    	change : 'onChangeHttpsOnly'
                    }
                }, {
                    xtype : 'checkbox',
                    boxLabel : l10n('disable-http-to-https-redirect'),
                    boxLabelAlign: 'after',
                    name : 'disablehttps',
                    bind : {
                        value : '{httpsOnlyFlagNoRedirect}',
                        disabled : '{redirectHttp}'
                    },
                    listeners : {
                        change : 'onChangeDisableHttps'
                    }
                }, {
                    xtype : 'button',
                    bind : {
                        text : '{encryption}',
                        disabled : '{isEncryption}'
                    },
                    listeners : {
                        click : 'onClickSecurityEncryption'
                    }
                }
            ]
        }, {
            xtype : 'panel',
            reference : 'securityviewpanel',
            width : '100%',
            border : false,
            layout : {
                type : 'card',
                align : 'stretch'
            }
        }, {
            xtype : 'statusbar',
            border : 0,
            padding : 0,
            text : '<div style="color:red">'+l10n('super-security-ssl-restart-is-pending-settings-maintenance-system-restart-reboot')+'</div>',
            bind : {
                hidden : '{isRestartPending}'
            }
        }];

        me.callParent(arguments);
    },

    listeners : {
        beforerender : 'getSecurityViewData',
        afterrender : 'checkForRebootMessage'
    }
});

/**
 * @Class ServerCertificate
 */
Ext.define('SuperApp.view.settings.security.ServerCertificate', {
    extend : 'Ext.form.Panel',
    alias : 'widget.servercertificate',
    requires : ['SuperApp.view.settings.security.ServerFileUploadOverlay', 'SuperApp.view.settings.security.SecurityViewModel', 'SuperApp.view.settings.security.SecurityController'],
    title : {
        text : l10n('super-security-ssl-certificate-tab'),
        textAlign : 'center'
    },

    reference : 'servercertificate',

    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    border : 0,
    buttonAlign : 'center',
    initComponent : function() {
        var me = this;

        me.items = [{
                xtype : 'fieldset',
                title : l10n('security-super-ssl-contents'),
                padding : 5,
                width : '100%',
                items : [{
                    xtype : 'label',
                    text : l10n('super-security-private-cert-notes')
                }, {
                    xtype : 'component',
                    bind : {
                        html : '{ServerCertificateDesc}'
                    }
                }]
            }, {
                xtype : 'fieldset',
                width : '100%',
                padding : 5,
                title : l10n('security-super-ssl-super-security-ssl-certificate-tab'),
                items : [{
                    xtype : 'label',
                    name : 'servercertnotes',
                    text : l10n('super-security-ssl-manage-cert-note')
                }, {
                    xtype : 'textarea',
                    itemId : 'sckey',
                    fieldCls : 'key-text',
                    height : 250,
                    width : '100%',
                    bind : {
                        value : '{sckey}'
                    },
                    style : "font: normal 12px monospace, Courier",
                    listeners : {
                        focus : 'onFocusHashKey'
                    }
                }]
            }, {
                xtype : 'label',
                text : '',
                bind : {
                    hidden : '{keyMatch}'
                }
            },{
                xtype : 'serverfileupload',
                hidden : true
            }];
        me.buttons = [
		{
			xtype: 'button',
		    text : l10n('super-security-ssl-regenerate-self-signed'),
		    align : 'center',
		    listeners : {
		       click : 'onClickServerCertRegenerateSS'
		    }
		},                      
        {
            text : l10n('reset'),
            itemId : 'reset',
            formBind : true,
            disabled: true,
            listeners : {
                click : 'loadServerCert'
            }
        }, {
            text : l10n('apply'),
            itemId : 'apply',
            formBind : true,
            disabled: true,
            listeners : {
                click : 'onClickApplyServerCert'
            }
        }, {
            text : l10n('upload'),
            itemId : 'upload',
            listeners : {
                click : 'onClikcUploadServerCert'
            }
        }];

        me.callParent(arguments);
    }

});

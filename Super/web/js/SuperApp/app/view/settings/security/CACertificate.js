Ext.define('SuperApp.view.settings.security.CACertificate', {
    extend : 'Ext.form.Panel',

    alias : 'widget.cacertificate',

    requires : ['SuperApp.view.settings.security.SecurityOverlay', 'SuperApp.view.settings.security.SecurityViewModel', 'SuperApp.view.settings.security.SecurityController'],

    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    
    title : {
    	text : l10n('security-super-ssl-server-ca-certificate-s'),
    	textAlign : 'center'
    },

    reference : 'cacertificate',

    initComponent : function() {
        var me = this;
        me.items = [{
            xtype : 'fieldset',
            title : l10n('security-super-ssl-contents'),
            width : '100%',
            padding : 5,
            items : [{
                xtype : "label",
                text : l10n('super-security-private-certint-notes'),
            }, {
                xtype : 'component',
                height : 250,
                width : '100%',
                name : 'cacontent',
                html : 'test',
                bind : {
                    html : '{cacontent}'
                }

            }]
        }, {
            xtype : 'fieldset',
            width : '100%',
            title : l10n('security-super-ssl-server-ca-certificate-s'),
            padding : 5,
            items : [{
                xtype : 'label',
                text : l10n('super-security-ssl-manage-cert-int-note')
            }, {
                xtype : 'textarea',
                readOnly: true,
                height : 200,
                width : '100%',
                fieldCls : 'key-text',
                bind : {
                    value : '{cacert}'
                }
            }]
        }];
        me.buttons = ['->', {
            text : l10n('upload'),
            viewtype : 'cacertview',
            listeners : {
                click : 'showUploadWindow'
            }
        }, '->']

        me.callParent(arguments);
    }
});

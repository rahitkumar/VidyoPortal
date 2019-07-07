Ext.define('SuperApp.view.settings.security.SecurityPrivateKey', {
    extend : 'Ext.form.Panel',
    alias : 'widget.privatekey',

    requires : ['SuperApp.view.settings.security.FileUploadOverlay', 'SuperApp.view.settings.security.SecurityViewModel', 'SuperApp.view.settings.security.SecurityController'],

    title : {
    	 text : '<span class="header-title">' + l10n('ssl-private-key') + '</span>',
    	 textAlign : 'center'
    },
    border : false,
    reference : 'privatekey',

    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    margin : '5, 0, 0, 0',
    bodyPadding: 5,
    initComponent : function() {
        var me = this;
        me.items = [{
            xtype : 'fieldset',
            width : '100%',
            title : l10n('super-security-ssl-key-information'),
            layout : {
                type :'vbox',
                align : 'center'
            },
            padding : 5,
            items : [{
                xtype : 'label',
                text : l10n('super-security-private-key-notes'),
                width : '100%'
            }, {
                xtype : 'combo',
                name : "bits",
                padding : 5,
                bind : {
                    value : '{KeyInfoSize}',
                    disabled : '{sslOptionsFlag}'
                },
                typeAhead : false,
                triggerAction : "all",
                editable : false,
                store : [['1024', '1024'], ['2048', '2048'], ['4096', '4096']],
                fieldLabel : l10n('key-size') + "(bits)"
            }, 
            {
                xtype : 'button',
                bind : {
                    disabled : '{sslOptionsFlag}'
                },
                text : l10n('super-security-ssl-manage-key-regenerate'),
                listeners : {
                    click : 'onClickRegenerate'
                }
            }]
        }, {
            xtype : 'fieldset',
            padding : 5,
            width : '100%',
            layout : {
                type :'vbox',
                align : 'center'
            },
            title : l10n('super-security-ssl-private-key'),
            items : [{
                xtype : 'label',
                text : l10n('super-security-ssl-manage-key-note'),
                width : '100%',
            }, {
                xtype : 'textfield',
                fieldLabel : l10n('SHA256'),
                labelWidth : 80,
                width : '50%',
                labelSeparator :':',
                padding : 0,
                bind : {
                    value : '{PrivateHashKey}',
                    editable : '{sslOptionsFlag}',
                    readOnly : '{sslOptionsFlag}'
                },
                style : "font: normal 12px monospace, Courier",
                listeners : {
                    focus : 'onFocusHashKey'
                }
            }, {
                xtype : 'panel',
                layout : {
                    layout : 'hbox',
                    align : 'stretch'
                },
                items : [{
                    xtype : 'button',
                    text : l10n('import-private-key'),
                    bind : {
                        disabled : '{sslOptionsFlag}'
                    },
                    listeners : {
                        click : 'onClikcUpload'
                    }
                }, {
                    xtype : 'button',
                    text : l10n('export-private-key'),
                    hidden : true,
                    listeners : {
                        click : 'onClickExportPrivateKey'
                    }
                }]
            }]
        }];
        me.callParent(arguments);
    },

    /*    onFocusHashKey : function(txtarea) {
     var me = this,
     viewModel = me.getViewModel();

     if(!viewModel.getData()["sslEnabledFlag"]) {
     me.down('button[text=Reset]').enable();
     me.down('button[text=Apply]').enable();
     me.down('textareafield').setReadOnly(true);
     me.down('textareafield').setEditable(true);
     }
     },*/

    disableResetApply : function() {
        var me = this;

        me.down('button[text='+l10n('reset')+']').disable();
        me.down('button[text='+l10n('apply')+']').disable();
    }
});

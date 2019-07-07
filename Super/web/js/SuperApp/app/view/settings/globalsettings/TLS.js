Ext.define('SuperApp.view.settings.globalsettings.TLS', {
    extend : 'Ext.form.Panel',
    alias : 'widget.tlsform',
    requires : ['SuperApp.view.settings.globalsettings.GlobalFeatureSettingsController','SuperApp.view.settings.globalsettings.GlobalFeatureSettingsModel'],
    controller : 'GlobalFeatureSettingsController',
    viewModel : {
        type : 'GlobalFeatureSettingsModel'
    },
    title : {
        text : '<span class="header-title">'+l10n('vidyoproxy')+'</span>',
        textAlign : 'center'
    },
    height : '100%',
    buttonAlign : 'center',
    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'fieldset',
            width : '100%',
    		height : '100%',
    		bodyStyle : 'padding: 10px',
    		layout : {
    			type : 'vbox',
    			align : 'center',
    			pack : 'center',
    		},
            items : [{
                xtype : 'radiogroup',
                columns : 1,
                fieldLabel : l10n('super-misc-tls-tab-form-field-tls-label'),
                labelStyle : 'font-weight:bold',
                labelWidth : 100,
                width : 200,
                margin : 5,
                vertical : true,
                items : [{
                    name : 'tlsProxyGroup',
                    boxLabel : l10n('enabled'),
                    inputValue : 'enabled',
                    bind : {
                        disabled : '{isSSLNotEnabled}'
                    }
                }, {
                    name : 'tlsProxyGroup',
                    boxLabel : l10n('disabled'),
                    inputValue : 'disabled',
                    bind : {
                        disabled : '{isSSLNotEnabled}'
                    }
                }]
            }]
        }];
        me.buttons = [
			{
			    text : l10n('save'),
			    bind : {
			        disabled : '{isSSLNotEnabled}'
			    },
			    listeners : {
			        click : 'onClickTLSSave'
			    }
			},{
			    text : l10n('cancel'),
			    listeners : {
			        click : 'resetProxyData'
			    },
			    bind : {
			        disabled : '{isSSLNotEnabled}'
			    }
			}        
        ];
        me.callParent();
    },

    loadRecord : function(rec) {
        var me = this;
        me.down('radiogroup').setValue({
            'tlsProxyGroup' : rec.get('tlsProxyEnabled') ? "enabled" : "disabled"
        });
    }
});

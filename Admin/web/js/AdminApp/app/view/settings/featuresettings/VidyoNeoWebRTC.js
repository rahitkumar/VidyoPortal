Ext.define('AdminApp.view.settings.featuresettings.VidyoNeoWebRTC', {
    extend : 'Ext.form.Panel',
    alias : 'widget.vidyoneowebrtc',
    requires: ['AdminApp.view.settings.featuresettings.FeatureSettingsController', 'AdminApp.view.settings.featuresettings.FeatureSettingsViewModel'],
    controller: 'FeatureSettingsController',
    viewModel: {
        type: 'FeatureSettingsViewModel'
    },
    border : true,
    height : '100%',
    trackResetOnLoad: true,
    buttonAlign : 'center',
    title : {
        text : l10n('settings-featuers-vidyoneo-webrtc'),
        textAlign : 'center'
    },

    initComponent : function() {
        var me = this,
        	viewModel = me.getViewModel();
        
        me.items = [{
            xtype : 'fieldset',
            width : '100%',
            height : '100%',
            bodyStyle : 'padding: 10px',
            layout : {
                  type : 'vbox',
                  align : 'center'
          },
           items: [{xtype: 'checkbox',
	           columns: 1,
	           reference: 'enableVidyoNeoWebRTCGuestAdmin',
	           name: 'enableVidyoNeoWebRTCGuestAdmin',
	           labelWidth: 400,
	           width: 500,
	           inputValue: '1',
	           uncheckedValue: '0',
	           bind: {
	               hidden: '{!isVidyoNeoWebRTCGuestEnabled}'
	           },
	           fieldLabel: l10n('settings-featuers-vidyoneo-webrtc-guests')
	       }, {
	           xtype: 'checkbox',
	           columns: 1,
	           reference: 'enableVidyoNeoWebRTCUserAdmin',
	           name: 'enableVidyoNeoWebRTCUserAdmin',
	           labelWidth: 400,
	           width: 500,
	           inputValue: '1',
	           uncheckedValue: '0',
	           bind: {
	               hidden: '{!isVidyoNeoWebRTCUserEnabled}'
	           },
	           fieldLabel: l10n('settings-featuers-vidyoneo-webrtc-users')
	       	}]
        }];

          
        me.buttons = [{
            text: l10n('save'),
            id: 'webrtc-save',
            formBind: true,
            listeners: {
                click: 'onClickVidyoNeoWebRTCSave'
            }
        }, {
            text: l10n('cancel'),
            handler: function() {
                this.up('form').getForm().reset();
            }
        }];
        
        me.listeners= {
            dirtychange: function(form, isDirty) {
                if (isDirty && form.isValid()) {
                    Ext.getCmp('webrtc-save').enable();
                } else {
                    Ext.getCmp('webrtc-save').disable();
                }
            }
        };
            
         me.callParent(arguments);
    }
});


Ext.define('SuperApp.view.settings.globalsettings.VidyoNeoWebRTC', {
    extend : 'Ext.form.Panel',
    alias : 'widget.vidyoneowebrtc',
    requires :['SuperApp.store.settings.VidyoNeoWebRTC'],
    controller: 'GlobalFeatureSettingsController',
    border : true,
    height : '100%',
    trackResetOnLoad: true,
    buttonAlign : 'center',
    title : {
        text : l10n('settings-featuers-vidyoneo-webrtc'),
        textAlign : 'center'
    },

    initComponent : function() {
        var me = this;

            me.items = [
             {
            xtype : 'fieldset',
            width : '100%',
                height : '100%',
                bodyStyle : 'padding: 10px',
                layout : {
                        type : 'vbox',
                        align : 'center'
                },
            items : [{
                xtype: 'checkbox',
                columns: 1,
                reference: 'enableVidyoNeoWebRTCGuest',
                name: 'enableVidyoNeoWebRTCGuest',
                labelWidth: 400,
                width: 500,
                inputValue: '1',
                uncheckedValue: '0',
                fieldLabel: l10n('settings-featuers-vidyoneo-webrtc-guests')
            }, {
                xtype: 'checkbox',
                columns: 1,
                reference: 'enableVidyoNeoWebRTCUser',
                name: 'enableVidyoNeoWebRTCUser',
                labelWidth: 400,
                width: 500,
                inputValue: '1',
                uncheckedValue: '0',
                fieldLabel: l10n('settings-featuers-vidyoneo-webrtc-users')
            }]
            }];
            me.buttons = [{
                text : l10n('save'),
                listeners : {
                    click : 'onClickVidyoNeoWebRTCSave'
                },
                formBind: true,
                disabled : true
            },
            {
                text : l10n('cancel'),
                handler : function() {
                    this.up('form').getForm().reset();
                }
            }];
                me.callParent(arguments);
        }
});


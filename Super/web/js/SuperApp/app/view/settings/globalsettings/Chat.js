Ext.define('SuperApp.view.settings.globalsettings.Chat', {
    extend : 'Ext.form.Panel',
    xtype : 'chat',
    title : {
        text : '<span class="header-title">'+l10n('chat')+'</span>',
        textAlign : 'center'
    },

    requires : ['SuperApp.view.settings.globalsettings.GlobalFeatureSettingsController', 'SuperApp.view.settings.globalsettings.GlobalFeatureSettingsModel'],

    controller : "GlobalFeatureSettingsController",

    viewModel : {
        type : 'GlobalFeatureSettingsModel'
    },
    buttonAlign : 'center',
    height: '100%',
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
            xtype : 'checkbox',
            columns : 1,
            flex : 2,
            name : 'chatAvailableGroup',
            fieldLabel : '<b>'+l10n('chat-super-available')+'</b><div style="font-size:10px;color:#666;">'+l10n('chat-super-available-desc')+'</div>',
            labelWidth : 350,
            width : 700,
            labelSeparator : '',
            vertical : true,
            bind : {
            	value : '{chatVidyoPortalAvailable}'
            },
            inputValue: 'available',
            uncheckedValue: 'disabled',
            listeners : {
            	change: function(cb, checked) {
            		var fieldset = cb.ownerCt;
            		Ext.Array.forEach(fieldset.query('checkbox[reference = defaultPublicChat]'), function(field) {
                        field.setDisabled(!checked);
                        field.el.animate({opacity: !checked ? 0.3 : 1});
                    });
            		Ext.Array.forEach(fieldset.query('checkbox[reference = defaultPrivateChat]'), function(field) {
                        field.setDisabled(!checked);
                        field.el.animate({opacity: !checked ? 0.3 : 1});
                    });
            	}
            }
        }, {
            xtype : 'checkbox',
            columns : 1,
            flex : 2,
            labelSeparator : '',
            name : 'publicChatEnabledGroup',
            labelWidth : 350,
            width : 700,
            fieldLabel : '<b>'+l10n('public-chat-default-option')+'</b><div style=\"font-size:10px;color:#666;\">'+l10n('public-chat-default-option-desc')+'</div>',
            layout : {
                type : 'vbox'
            },
            inputValue: 'enabled',
            uncheckedValue: 'disabled',
            reference : 'defaultPublicChat',
            bind : {
            	value : '{chatDefaultPublicStatus}'
            },
            disabled : true
        }, {
            xtype : 'checkbox',
            columns : 1,
            flex : 2,
            reference : 'defaultPrivateChat',
            name : 'privateChatEnabledGroup',
            labelSeparator : '',
            labelWidth : 350,
            width : 700,
            fieldLabel : '<b>'+l10n('private-chat-default-option')+'</b><div style=\"font-size:10px;color:#666;\">'+l10n('private-chat-default-option-desc')+'</div>',
            layout : {
                type : 'vbox'
            },
            inputValue: 'enabled',
            uncheckedValue: 'disabled',
            bind : {
            	value : '{chatDefaultPrivateStatus}'
            },
            disabled : true
        }]
		}];
        me.buttons = [
            {
                text : l10n('save'),
                listeners : {
                    click : 'onClickChatSave'
                },
                formBind : true,
                disabled: true
            }, 
            {
                text : l10n('cancel'),
                handler : 'getChatData'
            }
        ];
        me.callParent(arguments);
    }
});

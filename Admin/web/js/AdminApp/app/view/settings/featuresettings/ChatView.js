/**
 * @class ChatView
 */
Ext.define('AdminApp.view.settings.featuresettings.ChatView', {
    extend: 'Ext.form.Panel',
    alias: 'widget.chatview',

    reference: 'vidyoweb',
    height: 800,
    border: false,

    requires: ['AdminApp.view.settings.featuresettings.FeatureSettingsController', 'AdminApp.view.settings.featuresettings.FeatureSettingsViewModel'],

    controller: 'FeatureSettingsController',

    viewModel: {
        type: 'FeatureSettingsViewModel'
    },

    title: {
        text: '<span class="header-title">' + l10n('chat') + '</span>',
        textAlign: 'center'
    },

    items: [{
        xtype: 'fieldset',
        height: '100%',
        layout: {
            type: 'vbox',
            align: 'center',
            pack: 'center',
        },
        items: [{
            xtype: 'label',
            bind: {
                hidden: '{isChatHiddenLabel}'
            },
            html: '<b><span>' + l10n('chat-feature-is-turned-off') + '</span></b>'
        }, {
            xtype: 'checkbox',
            margin: 20,
            name: 'publicChatEnabledGroup',
            reference: 'publicChatEnabledGroup',
            style: {
                'margin-left': '-5em !important'
            },
            columns: 1,
            width: 300,
            labelWidth: 150,
            fieldLabel: l10n('public-chat'),
            inputValue: 'enabled',
            uncheckedValue: 'disabled',
            bind: {
                disabled: '{isChatDisabled}'
            }
        }, {
            xtype: 'checkbox',
            name: 'privateChatEnabledGroup',
            reference: 'privateChatEnabledGroup',
            columns: 1,
            style: {
                'margin-left': '-5em !important'
            },
            fieldLabel: l10n('private-chat'),
            width: 300,
            labelWidth: 150,
            inputValue: 'enabled',
            uncheckedValue: 'disabled',
            bind: {
                disabled: '{isChatDisabled}'
            }

        }]
    }],

    buttonAlign: 'center',
    buttons: [{
        text: l10n('save'),
        bind: {
            hidden: '{isChatDisabled}'
        },
        listeners: {
            click: 'onClickSaveChat'
        }
    },
    {
        text: l10n('cancel'),
        listeners: {
            click: 'onClickCancelChat'
        }
    }]



});
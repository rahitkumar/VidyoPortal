 /**
 * @class VidyoWeb
 */
Ext.define('AdminApp.view.settings.featuresettings.VidyoWeb', {
        extend: 'Ext.form.Panel',
        alias: 'widget.vidyoweb',

        reference: 'vidyoweb',

        border: false,
        height: 800,
        requires: ['AdminApp.view.settings.featuresettings.FeatureSettingsController', 'AdminApp.view.settings.featuresettings.FeatureSettingsViewModel'],

        controller: 'FeatureSettingsController',

        viewModel: {
            type: 'FeatureSettingsViewModel'
        },
        title: {
            text: '<span class="header-title">' + l10n('vidyoweb') + '</span>',
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

                xtype: 'checkbox',
                name: 'enableVidyoWeb',
                reference:'enableVidyoWebRef',
                margin: 20,
                columns: 1,
                width: 600,
                labelWidth: 300,
                inputValue: 'enabled',
                uncheckedValue: 'disabled',
                fieldLabel: l10n('vidyoweb-admin-enable'),
                bind:'{enableVidyoWeb}'
                
            }, {
                xtype: 'checkbox',
                name: 'zincEnabled',
                columns: 1,
                width: 600,
                labelWidth: 300,
                reference: 'zincEnabled',
                fieldLabel: l10n('enable-zinc-server-for-guests'),
                inputValue: 'enabled',
                uncheckedValue: 'disabled',
                bind: {
                    value: '{enableWebRtc}',
                    disabled: '{!enableVidyoWebRef.checked}',

                }
               
                 
             
            },{
                xtype: 'textfield',
                name: 'zincServer',
                reference: 'zincServer',
                width: 600,
                labelWidth: 300,
            allowBlank : false,
                fieldLabel: '<span class="red-label">*</span>'+l10n('zinc-server'),
                bind: {
                    value: '{zincServer}',
                    disabled: '{!zinServerDisabled}'
                }
            }]
       
    }],
    buttonAlign: 'center',
    buttons: [{
        text: l10n('save'),
      formBind:true,
        listeners: {
            click: 'onClickSaveVidyoWeb'
        }
    },{
        text: l10n('cancel'),
        listeners: {
            click: 'onClickCancelVidyoWeb'
        }
    }]
});
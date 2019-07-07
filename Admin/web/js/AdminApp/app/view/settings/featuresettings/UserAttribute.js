Ext.define('AdminApp.view.settings.featuresettings.UserAttribute', {
    extend: 'Ext.form.Panel',
    alias: 'widget.userAttribute',

    title: {
        text: '<span class="header-title">'  + l10n('user-attributes') + '</span>',
        textAlign: 'center'
    },
    height: 800,
    requires: ['AdminApp.view.settings.featuresettings.FeatureSettingsController', 'AdminApp.view.settings.featuresettings.FeatureSettingsViewModel'],

    controller: "FeatureSettingsController",
    viewModel: {
        type: 'FeatureSettingsViewModel'
    },
    trackResetOnLoad: true,
    buttonAlign: 'center',
    items: [{
        xtype: 'fieldset',
        width: '90%',
        height: '100%',
        bodyStyle: 'padding: 10px',

        border: false,
        layout: {
            type: 'vbox',
            align: 'center',
            pack: 'left',
        },
        items: [{
            xtype: 'checkbox',
            columns: 1,
            reference: 'enableUserImage',
            name: 'enableUserImage',
            labelWidth: 400,
            //margin:'0,20,0,0',
            width: 500,
            inputValue: '1',
            uncheckedValue: '0',
            fieldLabel: l10n('enable-thumbnail-photos'),



        }, {
            xtype: 'checkbox',
            reference: 'enableUserImageUpload',
            name: 'enableUserImageUpload',
            labelWidth: 400,
            cls:'override-for-tooltip',
            width: 500,
            inputValue: '1',
            uncheckedValue: '0',


            bind: {
                hidden: '{!isUserImageUploadEnabled}'
            },

            fieldLabel: l10n('allow-users-to-upload-their-own-image'),
            

        }]
    }],

    buttons: [{
        text: l10n('save'),
        id: 'usrattr-save-button',
        formBind: true,
        listeners: {
            click: 'onClickUserAttributeSave'
        }
    }, {
        text: l10n('cancel'),
        handler: function() {
            this.up('form').getForm().reset();
        }
    }],
    listeners: {
        dirtychange: function(form, isDirty) {
            //bug in ext js when you combine formbind and dirtycheck so that we manually checking dirty and validation check
            if (isDirty && form.isValid()) {
                Ext.getCmp('usrattr-save-button').enable();
            } else {
                Ext.getCmp('usrattr-save-button').disable();
            }
        },

    }
});
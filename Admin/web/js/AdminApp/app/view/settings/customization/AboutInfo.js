Ext.define('AdminApp.view.settings.customization.AboutInfo', {
    extend: 'Ext.form.Panel',
    width: '100%',
    alias: 'widget.aboutinfo',
    reference: 'aboutinfo',
    frame: false,
    height: 800,
    requires: [
        'AdminApp.view.settings.customization.CustomizationViewModel',
        'AdminApp.view.settings.customization.CustomizationController'
    ],
    viewModel: {
        type: 'CustomizationViewModel'
    },

    controller: 'CustomizationController',
    title: {
        text: '<span class="header-title">' + l10n('about-info') + '</span>',
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
              xtype : 'htmleditor',
              id: 'about-info',
         	enableFont: false,
         	enableLinks: false,
            fieldLabel: '',
            width: '100%',
            name: 'aboutInfo',
            bind: {
                value: '{aboutInfo}'
            },
            growMin: 300,
             height : 400,
            maxLength: 65535,
            msgTarget: 'under',
            listeners: {
                initialize: function(thisEditor) {
                        thisEditor.toggleSourceEdit();
                        Ext.getCmp('about-info').getToolbar().hide();
                }
              }
        }]
    }],
    buttonAlign: 'center',
    buttons: [{
        text: l10n('save'),
        listeners: {
            click: 'onClickSaveAboutInfo'
        },
        formBind: true,
        disabled: true
    }, {
        text: l10n('cancel'),
        listeners: {
            click: 'getAboutInfoData'
        }
    }, {
        text: l10n('default'),
        type: 'default',
        listeners: {
            click: 'onClickSaveAboutInfo'
        }
    }]
});
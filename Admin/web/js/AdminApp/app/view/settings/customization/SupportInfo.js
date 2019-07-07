Ext.define('AdminApp.view.settings.customization.SupportInfo', {
    extend : 'Ext.form.Panel',
    width : '100%',
    alias : 'widget.supportinfo',
    autoDestroy : true,
    reference : 'supportinfo',
    height:800,
    requires : ['AdminApp.view.settings.customization.CustomizationViewModel', 'AdminApp.view.settings.customization.CustomizationController'],

    viewModel : {
        type : 'CustomizationViewModel'
    },

    controller : 'CustomizationController',

    title : {
        text : '<span class="header-title">' + l10n('contact-info1') + '</span>',
        textAlign : 'center'
    },

    items:[{
            xtype : 'fieldset',
            height : '100%',
            layout : {
                type : 'vbox',
                align : 'center',
                pack : 'center',
            },
            items : [{
                   xtype : 'htmleditor',
        			enableFont: false,
        			enableLinks: false,
        			id:'support-info',
                fieldLabel : '',
                width:'100%',
                name : 'contactInfo',
                bind : {
                    value : '{contactInfo}'
                }, 
                grow : true,
                growMin : 300,
                 height : 400,
                maxLength: 65535,
                msgTarget:'under',
                listeners: {
                    initialize: function(thisEditor) {
                            thisEditor.toggleSourceEdit();
                            Ext.getCmp('support-info').getToolbar().hide();
                    }
                  }
            }]
    }],
    buttonAlign : 'center',
    buttons : [{
        text : l10n('save'),
        listeners : {
            click : 'onClickSaveSupportInfo'
        },
        formBind:true,
        disabled:true
    }, {
        text : l10n('cancel'),
        listeners : {
            click : 'getSupportInfoData'
        }
    }, {
        text : l10n('default'),
        type : "default",
        listeners : {
            click : 'onClickSaveSupportInfo'
        }
    }]
});

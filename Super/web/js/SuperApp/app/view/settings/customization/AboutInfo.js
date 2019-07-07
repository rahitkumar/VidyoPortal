Ext.define('SuperApp.view.settings.customization.AboutInfo', {
    extend : 'Ext.form.Panel',
    width : '100%',
    alias : 'widget.aboutinfo',
    border : false,
    reference : 'aboutinfo',

    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    title : {
        text : '<span class="header-title">'+l10n('about-info')+'</span>',
        textAlign : 'center'
    },

    items : [{
            xtype : 'htmleditor',
            enableFont: false,
            enableLinks: true,
            fieldLabel : '',
            id:'about-info',
            name : 'aboutInfo',
            bind : {
                value : '{aboutInfo}'
            },
            width : '100%',
            grow : true,
            growMin : 300,
            height : 400,
            maxLength: 65535,
            msgTarget:'under',
            listeners: {
                initialize: function(thisEditor) {
                        thisEditor.toggleSourceEdit();
                        Ext.getCmp('about-info').getToolbar().hide();
                }
              }
        }],
        buttonAlign : 'center',
        buttons : [{
            text : l10n('save'),
            listeners : {
                click : 'onClickSaveAboutInfo'
            },
            formBind:true,
            disabled:true
        }, {
            text : l10n('default'),
            type : 'default',
            listeners : {
                click : 'onClickSaveAboutInfo'
            }
        }]
});

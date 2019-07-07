Ext.define('SuperApp.view.settings.customization.SupportInfo', {
    extend : 'Ext.form.Panel',
    width : '100%',
    alias : 'widget.supportinfo',
    autoDestroy : true,
    border : false,
    reference : 'supportinfo',
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    title : {
        text : '<span class="header-title">'+l10n('contact-info1')+'</span>',
        textAlign : 'center'
    },
    items : [{
        xtype : 'htmleditor',
        enableFont: false,
        enableLinks: false,
        id:'support-info',
        fieldLabel : '',
        name : 'contactInfo',
        bind : {
            value : '{contactInfo}'
        },
        width : '100%',
        height : 400,
        border: false,
        grow : true,
        growMin : 300,
        maxLength: 65535,
        listeners: {
            initialize: function(thisEditor) {
                    thisEditor.toggleSourceEdit();
                    Ext.getCmp('support-info').getToolbar().hide();
            }
          }
     
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
        text : l10n('default'),
        type : "default",
        listeners : {
            click : 'onClickSaveSupportInfo'
        }
    }]
});

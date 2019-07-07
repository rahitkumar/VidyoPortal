/**
 * @class SuperApp.view.settings.customization.Banners
 */
Ext.define('SuperApp.view.settings.customization.Banners', {
    extend : 'Ext.form.Panel',
    alias : 'widget.banners',
    reference : 'banners',
    border : false,
    autoScroll : true,
    width : '100%',
    title : {
    	text : '<span class="header-title">'+l10n('banners-tablabel')+'</span>',
    	textAlign : 'center'
    },
   items : [{
            xtype : 'checkbox',
            fieldLabel : l10n('super-customize-bannersform-loginbanner-label'),
            name : 'showLoginBanner',
            labelWidth : 120,
            width : 240,
            margin : 5,
            reference : 'showLoginBanner',
            inputValue : true
        }, {
            xtype : 'htmleditor',
            enableColors : false,
            id:'login-banner',
            width : '100%',
            enableFont : false,
            border : false,
            enableAlignments : false,
            enableLinks : false,
            enableSourceEdit : true,
            enableLists : false,
            name : 'loginBanner',
            reference:'loginBanner',
            labelSeparator : '',
            height : 200,
            bind : {
                value : '{loginBanner}'
            },
            listeners: {
                initialize: function(thisEditor) {
                        thisEditor.toggleSourceEdit();
                        Ext.getCmp('login-banner').getToolbar().hide();
                }
              }
        }, {
            xtype : 'checkbox',
            fieldLabel : l10n('super-customize-bannersform-welcomebanner-label'),
            labelWidth : 120,
            width : 240,
            margin : 5,
            name : 'showWelcomeBanner',
            reference : 'showWelcomeBanner',
            inputValue : true
        }, {
            xtype : 'htmleditor',
            enableColors : false,
            border : false,
            id:'welcome-banner',
            enableFont : false,
            width : '100%',
            enableAlignments : false,
            enableLinks : false,
            enableSourceEdit : true,
            enableLists : false,
            name : 'welcomeBanner',
            reference : 'welcomeBanner',
            labelSeparator : '',
            height : 200,
            bind : {
                value : '{welcomeBanner}'
            },
            listeners: {
                initialize: function(thisEditor) {
                        thisEditor.toggleSourceEdit();
                        Ext.getCmp('welcome-banner').getToolbar().hide();
                }
              }
        }],
        buttonAlign : 'center',
        buttons : [
               {
                text : l10n('save'),
                listeners : {
                    click : 'onClickSaveBanners'
               }
        }]
});

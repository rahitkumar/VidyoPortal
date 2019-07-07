/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('SuperApp.view.main.Main', {
    extend : 'Ext.container.Viewport',
    requires : ['SuperApp.view.main.VidyoTabs', 'SuperApp.view.main.MainController', 'SuperApp.view.main.MainModel', 'SuperApp.utils.IdleTimer'],

    layout : {
        type : 'auto',
        align : 'center'
    },
    style: {
        'margin-left': '10px',
        'margin-right': '10px',

    },
    xtype : 'app-main',

    controller : 'superMain',

    scrollable : true,


    viewModel : {
        type : 'superMain'
    },

    listeners : {
        resize : 'contentsResize'
    },

    initComponent : function() {
        var me = this,
            viewModel = me.getViewModel(),
            clientHeight = document.body.clientHeight;

        Ext.Ajax.request({
            url : 'maintenance.html',
            success : function(response) {
                var result = response.responseXML,
                    portalversion = Ext.DomQuery.selectValue('portalversion', result),
                    allowUpgrade = Ext.DomQuery.selectValue('allowUpgrade', result),
                    peerStatus = Ext.DomQuery.selectValue('peerStatus', result),
                    backupLic = Ext.DomQuery.selectValue('backupLic', result);

	                viewModel.set('portalversion', Ext.String.htmlEncode(portalversion));
	                viewModel.set('allowUpgrade', allowUpgrade);
	                viewModel.set('peerStatus', peerStatus);
	                viewModel.set('backupLic', backupLic);
		        }
        });

        me.items = [{
            xtype : 'panel',
            itemId : 'mainPanel',
             layout : {
                type : 'card',
                align : 'center'
            },
            items: [{
                		xtype : 'vidyotabs',

                              }]

        }, {
            xtype : 'panel',
            layout : {
                type : 'hbox',
                align : 'stretch'
            },
            border : false,
            items : [{
                xtype : 'panel',
                flex : 8,
                minWidth : 960,
                layout : {
                    type : 'hbox',
                    align : 'left'
                },
                items : [{
                    xtype : 'button',
                    text : l10n('about_us'),
                    cls : 'footerlinks',
                    handler : 'aboutuslinkclick'
                }, {
                    xtype : 'button',
                    text : l10n('contact_us'),
                    cls : 'footerlinks',
                    handler : 'supportlinkclick'
                }, {
                    xtype : 'button',
                    text : l10n('terms_of_services'),
                    cls : 'footerlinks',
                    handler : 'termslinkclick'
                }, {
                    xtype : 'button',
                    text : l10n('help1'),
                    cls : 'footerlinks',
                    handler : 'helplinkclick'
                }, {
                    xtype : 'button',
                    cls : 'copyright',
                    html : '&copy; Vidyo 2008-2019'
                }]
            }]
        }];

        me.listeners = {
            beforerender : function() {
                var me = this,
                    viewModel = me.getViewModel();
                if (Ext.get('loading-ind-div')) {
                    Ext.get('loading-ind-div').remove();
                }
               if (showWelcomeBanner == "true") {
            	   var welcomePanel = Ext.create('SuperApp.view.main.WelcomeWindow', {
                       floatParent: this.viewshow
                   });

                   welcomePanel.show();
                   viewModel.getStore('loginHistoryStore').load();

                   Ext.Ajax.request({
                        url : 'loginhistory.ajax',
                        success : function(response) {
                        var result = response.responseXML;
                       var expiryInfo= Ext.DomQuery.selectValue('passwdExpDate', result);
                       if (!(typeof expiryInfo === "undefined" || expiryInfo==null || expiryInfo=="null")) {
                    	   var passwordExpiryLabelVar=  welcomePanel.down('label[id=passwordExpiryLabel]');
                    	   passwordExpiryLabelVar.setText(expiryInfo);
                         }
                        }
                });

              }
           }
      };
        me.callParent(arguments);
    },

    activateComponent : function(isComp) {
        var me = this;

        me.down('panel[itemId=mainPanel]').getLayout().setActiveItem(1);
        if (isComp) {
            var activeItem = me.down('panel[itemId=mainPanel]').getLayout().getActiveItem();
            activeItem.down('componentmain').getController().getComponentGridData();
        }
    },

});
var timer = Ext.create('SuperApp.utils.IdleTimer', {
    listeners: {
        idle: function(){

            Ext.Msg.alert(l10n('timeout'), l10n('inactivity-forced-logout'), function() {
            	logoutSuper();
            });
        },
        active: function(){

        }
    }
});
timer.start();

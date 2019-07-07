/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */

Ext.define('AdminApp.view.main.Main', {
    extend: 'Ext.container.Viewport',
    requires: ['AdminApp.view.main.MainController', 'AdminApp.view.main.MainModel', 'AdminApp.view.main.AdminTabs', 'AdminApp.utils.IdleTimer'],

    xtype: 'app-main',


    controller: 'main',

    viewModel: {
        type: 'main'
    },
    style: {
                'background-color': '#FFFFFF'
            },
    //scrollable: true,
    layout: 'border',


    initComponent: function() {
        var me = this;

        me.items = [{
            xtype: 'container',
            itemId: 'mainPanel',
            region: 'center',
            margin: '0 10 0 10',

            scrollable: true,
            layout: {
                type: 'vbox',
                align: 'stretch'
            },

            items: [{
                xtype: 'admintabpanel',

            }

            , {
            xtype: 'panel',
            layout: {
                type: 'hbox',
                align: 'stretch'
            },
            border: false,

            style: {
                'background-color': '#FFFFFF'
            },
            items: [{
                xtype: 'panel',



                layout: {
                    type: 'hbox',
                    align: 'left'
                },
                items: [{
                    xtype: 'button',
                    text: l10n('about_us'),
                    cls: 'footerlinks',
                    handler: 'aboutuslinkclick'
                }, {
                    xtype: 'button',
                    text: l10n('contact_us'),
                    cls: 'footerlinks',
                    handler: 'supportlinkclick'
                }, {
                    xtype: 'button',
                    text: l10n('terms_of_services'),
                    cls: 'footerlinks',
                    handler: 'termslinkclick'
                }, {
                    xtype: 'button',
                    text: l10n('help1'),
                    cls: 'footerlinks',
                    handler: 'helplinkclick'
                }, {
                    xtype: 'button',
                    cls: 'copyright',
                    html: '&copy; Vidyo 2008-2019'
                }]
            }]
        }
        ]}];

        me.listeners = {
            beforerender: function() {
                var me = this,
                    viewModel = me.getViewModel();
                if (Ext.get('loading-ind-div')) {
                    Ext.get('loading-ind-div').remove();
                }
                if (showWelcomeBanner == "true") {



                    var welcomePanel = Ext.create('AdminApp.view.main.WelcomeWindow', {
                        floatParent: this.view
                    });

                    welcomePanel.show();
                    viewModel.getStore('loginHistoryStore').load();
                    Ext.Ajax.request({
                        url: 'loginhistory.ajax',
                        success: function(response) {
                            var result = response.responseXML;
                            var expiryInfo = Ext.DomQuery.selectValue('passwdExpDate', result);
                            if (!(typeof expiryInfo === "undefined" || expiryInfo==null || expiryInfo=="null")) {
                                var passwordExpiryLabelVar = welcomePanel.down('label[id=passwordExpiryLabel]');
                                passwordExpiryLabelVar.setText(expiryInfo);
                            }
                        }
                    });
                }
            }
        };

        me.callParent(arguments);
    },


});
var timer = Ext.create('AdminApp.utils.IdleTimer', {
    listeners: {
        idle: function() {

            Ext.Msg.alert(l10n('timeout'), l10n('inactivity-forced-logout'), function() {
                logoutAdmin();
            });
        },
        active: function() {

        }
    }
});
timer.start();
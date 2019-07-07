/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('SuperApp.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    requires: ['Ext.MessageBox'],

    alias: 'controller.superMain',

    control: {
        'vidyotabs': {
            tabchange: 'onTabChange',
            afterrender: 'afterTabPanelRender'
        },
        'tenantsMain': {
            activate: 'onTenantActivate'
        }
    },

    onTabChange: function(tabPanel, newTab, oldTab, eOpts) {
        var tab = newTab.cls;
        if (tab != 'aboutus' && tab != 'support' && tab != 'terms') {
            var tabs = [],
                ownerCt = tabPanel.ownerCt,
                tokenDelimiter = ':',
                oldToken, newToken;

            tabs.push(newTab.id);
            tabs.push(tabPanel.id);

            while (ownerCt && ownerCt.is('tabpanel')) {
                tabs.push(ownerCt.id);
                ownerCt = ownerCt.ownerCt;
            }

            newToken = tabs.reverse().join(tokenDelimiter);

            oldToken = Ext.History.getToken();

            if (oldToken === null || oldToken.search(newToken) === -1) {
                Ext.History.add(newToken);
            }
        }
    },

    afterTabPanelRender: function(tabPanel) {
        var me = this;



        Ext.History.on('change', function(token) {
            if (me.getViewModel().get('callItemClick')) {
                var parts, length, i;
                if (token) {
                    var tokenDelimiter = ':';

                    parts = token.split(tokenDelimiter);
                    length = parts.length;

                    // setActiveTab in all nested tabs
                    var i = 0;
                    if (parts.length > 1 && Ext.getCmp(parts[i]) && Ext.getCmp(parts[i + 1])) {
                        Ext.getCmp(parts[i]).setActiveTab(Ext.getCmp(parts[i + 1]));
                        i = 2;
                        if (parts.length > 3 && parts[i] && Ext.getCmp(parts[i])) {

                            // This is for Settings tab 
                            Ext.defer(function() {
                                if (parts[i].search('treepanel') !== -1 && Ext.getCmp(parts[i]) && Ext.getCmp(parts[i]).getStore() && Ext.getCmp(parts[i]).getStore().getNodeById(parts[i + 1])) {
                                    var nodePath = Ext.getCmp(parts[i]).getStore().getNodeById(parts[i + 1]).getPath();
                                    Ext.getCmp(parts[i]).selectPath(nodePath);

                                    Ext.getCmp(parts[i]).fireEvent('itemclick', null, parts[i + 1]);

                                    // This is for router tab
                                } else if (parts[i].search('tabpanel') !== -1 && Ext.getCmp(parts[i]) && Ext.getCmp(parts[i + 1])) {
                                    Ext.getCmp(parts[i]).setActiveTab(Ext.getCmp(parts[i + 1]));
                                }
                            }, 10);
                        }

                    }

                }
            }
            me.getViewModel().set('callItemClick', true);
        });
        Ext.each(tabPanel.getTabBar().items.getRange(), function(tbar) {
            tbar.el.on('click', function() {
                var activeTab = tabPanel.getActiveTab(),
                    tabItems = tabPanel.items.getRange();
                switch (tbar.title) {
                    case l10n('components'):
                        me.refreshTabBar(tabPanel, 0, 'componentmain');
                        break;
                    case l10n('current-calls'):
                        me.refreshTabBar(tabPanel, 1, 'currentCalls');
                        break;
                    case l10n('tenants'):
                        me.refreshTabBar(tabPanel, 2, 'tenantsMain');
                        break;
                    case l10n('router-pools'):
                        me.refreshTabBar(tabPanel, 3, 'cloudmain');
                        break;
                    case l10n('settings'):
                        me.refreshTabBar(tabPanel, 4, 'settingsMainView');
                        break;
                }
            });
        });
        me.refreshTabBar(tabPanel, 0, 'componentmain');

        var activeTab = tabPanel.getActiveTab();
        this.onTabChange(tabPanel, activeTab);
    },

    refreshTabBar: function(tabPanel, pos, xtype) {
        var me = this,
            tab,
            tabItems = tabPanel.items.getRange();


        if (tabPanel.down(xtype)) {
            tab = tabPanel.down(xtype);
            tab.fireEvent('beforerender');
            tab.fireEvent('afterrender');
        } else {
            tab = tabPanel.down(xtype) || tabItems[pos].add({
                xtype: xtype
            });
            // tabPanel.setActiveTab(tab);
        }
        if (tab.getLayout().type == "card") {
            tab.getLayout().setActiveItem(0);
        }
        Ext.Ajax.request({
            url: 'maintenance.html',
            success: function(response) {
                var result = response.responseXML,
                    peerStatus = Ext.DomQuery.selectValue('peerStatus', result),
                    pkiNotification = Ext.DomQuery.selectValue('pkiNotification', result),
                pkiCertReviewPending = Ext.DomQuery.selectValue('pkiCertReviewPending', result);              
                me.getViewModel().set('peerStatus', peerStatus);
                if(pkiNotification && pkiNotification!=null && pkiNotification.trim()!=""){
                  var rightpaneltab = me.lookupReference('rightpaneltab');
               	  var restartnotification = me.lookupReference('restartnotification');
               	  if (!restartnotification) {
                     rightpaneltab.insert(4, [{
                             xtype: 'button',
                             reference: 'restartnotification',
                             pkiCertReviewPendingFlag:pkiCertReviewPending,                         
       
                             iconCls : 'x-fa fa-exclamation-triangle',
                             text: pkiNotification,
                             cls:'red-label-button-text',
                             scale: 'small',
                             iconAlign: 'left'
                         }]);
               	  }
                }else{
                	    	 var rightpaneltab = me.lookupReference('rightpaneltab');
                	    	  var restartnotification = me.lookupReference('restartnotification');
                	    	  if (restartnotification) {
                                  rightpaneltab.remove(restartnotification);
                              }

                       
                }                
                if (peerStatus == 0) {
                    //inserting warning panel dynamically in order to avoid tall header.
                    var rightpaneltab = me.lookupReference('rightpaneltab');
                    var peerAvailable = me.lookupReference('peerAvailable');
                    if (!peerAvailable) {
                        rightpaneltab.insert(3, [{
                            xtype: 'label',
                            reference: 'peerAvailable',
                            html: '<span style="color:red;font-size:10pt;font-weight:bold;;padding:1px">' + l10n('hot-stand-by-warning') + '</span>'
                        }]);
                    }
                } else {
                    var rightpaneltab = me.lookupReference('rightpaneltab');
                    var peerAvailable = me.lookupReference('peerAvailable');
                    if (peerAvailable) {
                        rightpaneltab.remove(peerAvailable);
                    }
                }
            }
        });
        //me.onTabChange(tabPanel, tab);
    },
    
    loadWebServerRestartRequests:function ( ob , records , successful , operation , eOpts ) {
    	
    	var me =this;
    
          
      
       	if(successful && records &&  records.length>0){
      	 
       	}
    },

    onTenantActivate: function(panel) {
        if (panel.up('tabpanel').items.getAt(1).items.length === 0) {
            //panel.up('tabpanel').items.getAt(1).add(Ext.create('SuperApp.view.components.ComponentMain'));
            //panel.up('tabpanel').items.getAt(2).add(Ext.create('SuperApp.view.cloud.main.Main'));
            //panel.up('tabpanel').items.getAt(3).add(Ext.create('SuperApp.view.settings.SettingsTab'));

            if (initialHistoryToken) {
                Ext.History.back();
            }
        }
    },
    aboutuslinkclick: function() {
        if (!aboutUsPanel) {
            var aboutUsPanel = Ext.create('Ext.window.Window', {
                closeAction: 'hide',
                title: l10n('about_us'),
                modal: true,
                animateTarget: 'about',
                animCollapse: true,
                header: true,
                width: 600,
                height: 485,
                border: false,
                closable: true,
                shadowOffset: 30,
                layout: 'fit',
                constrain: true,
                resizable: false,
                items: [{
                    xtype: 'form',
                    border: false,
                    margin: 10,
                    id: 'aboutForm',
                    overflowY: 'auto',
                    errorReader: new Ext.data.XmlReader({
                        successProperty: '@success',
                        record: 'field'
                    }, ['id', 'msg'])
                }],
                buttonAlign: 'center',
                buttons: [{
                    text: l10n('close'),
                    handler: function() {
                        aboutUsPanel.hide();
                    }
                }],
                listeners: {
                    render: function() {
                        var form = Ext.getCmp('aboutForm');
                        Ext.Ajax.request({
                            url: 'about_content.html',
                            method: "GET",
                            params: form.getForm().getValues(),
                            success: function(res) {
                                var aboutInfo = res.responseText;
                                if (aboutInfo && aboutInfo != null && aboutInfo != '') {
                                    form.setHtml(aboutInfo);
                                } else {
                                    form.setHtml(l10n('about-us-full'));
                                }
                            },
                            failure: function() {
                                Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                            }
                        });
                    }
                }
            }).show();
        }
    },
    supportlinkclick: function() {
        if (!supportPanel) {
            var supportPanel = Ext.create('Ext.window.Window', {
                closeAction: 'hide',
                title: l10n('contact_us'),
                modal: true,
                animateTarget: 'support',
                animCollapse: true,
                header: true,
                width: 560,
                height: 350,
                border: false,
                closable: true,
                shadowOffset: 30,
                layout: 'fit',
                constrain: true,
                resizable: false,
                items: [{
                    xtype: 'form',
                    border: false,
                    margin: 10,
                    id: 'supportForm',
                    overflowY: 'auto',
                    errorReader: new Ext.data.XmlReader({
                        successProperty: '@success',
                        record: 'field'
                    }, ['id', 'msg'])
                }],
                buttonAlign: 'center',
                buttons: [{
                    text: l10n('close'),
                    handler: function() {
                        supportPanel.hide();
                    }
                }],
                listeners: {
                    render: function() {
                        var form = Ext.getCmp('supportForm');
                        Ext.Ajax.request({
                            url: 'contact_content.html',
                            method: 'GET',
                            params: form.getForm().getValues(),
                            success: function(res) {
                                var xmlResponse = res.responseText;
                                form.setHtml(xmlResponse);
                            },
                            failure: function() {
                                Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                            }
                        });
                    }
                }
            }).show();
        }
    },
    termslinkclick: function() {
        if (!termsPanel) {
            var termsPanel = Ext.create('Ext.window.Window', {
                closeAction: 'hide',
                title: l10n('terms_of_services'),
                modal: true,
                animateTarget: 'termsofservices',
                animCollapse: true,
                header: true,
                width: 800,
                height: 470,
                border: false,
                closable: true,
                shadowOffset: 30,
                layout: 'fit',
                constrain: true,
                resizable: false,
                items: [{
                    xtype: 'form',
                    border: false,
                    margin: 10,
                    id: 'termsForm',
                    overflowY: 'auto',
                    errorReader: new Ext.data.XmlReader({
                        successProperty: '@success',
                        record: 'field'
                    }, ['id', 'msg'])
                }],
                buttonAlign: 'center',
                buttons: [{
                    text: l10n('close'),
                    handler: function() {
                        termsPanel.hide();
                    }
                }],
                listeners: {
                    render: function() {
                        var form = Ext.getCmp('termsForm');
                        Ext.Ajax.request({
                            url: 'terms_content.html',
                            method: 'GET',
                            params: form.getForm().getValues(),
                            success: function(res) {
                                var xmlResponse = res.responseText;
                                form.setHtml(xmlResponse);
                            },
                            failure: function() {
                                Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                            }
                        });
                    }
                }
            }).show();
        }
    },

    logoutSuper: function(){
    	logoutSuper();
    },
    
    contentsResize: function(viewport, width, height, oldWidth, oldHeight, eOpts) {
        if (!Ext.browser.is.IE) {
            var viewportHeight = document.body.clientHeight;
            var vidyoTabsHeight = this.getView().getEl().dom.firstChild.clientHeight;
        }
    },
    helplinkclick: function() {
        var scope = this;
        Ext.Ajax.request({
            url: 'guidelocation.ajax',
            success: function(res) {
                var xmlResponse = res.responseText;
                window.open(xmlResponse);
            },
            failure: function() {
                Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
            }
        });
    }
});
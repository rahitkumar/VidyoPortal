Ext.define('SuperApp.view.cloud.main.MainController', {
    extend : 'Ext.app.ViewController',

    alias : 'controller.cloudMain',

    onModifiedClick : function(radio, newValue, oldValue, eOpts) {
        var me = this;
        if (newValue) {
            this.getViewModel().set('isModified', true);
            Ext.Ajax.request({
                url : 'copyactivateconfigasmodified.ajax',
                method : 'POST',
                success : function(req) {
                    if(req.responseText) {
	                	me.getViewModel().set('cloudConfig', Ext.decode(req.responseText));
	                    me.onModifiedActiveChange();
                    } else {
                    	Ext.Msg.alert(l10n('error'), l10n('no-configuration-found'));
                    }
                },
                failure : function(req) {
                    Ext.Msg.alert(l10n('error'), l10n('failure'));
                }
            });
        }
    },

    onActiveClick : function(radio, newValue, oldValue, eOpts) {
        if (newValue) {
            this.getViewModel().set('isModified', false);
            this.onModifiedActiveChange();
        }
    },

    onModifiedActiveChange : function() {
        var activeTab = this.lookupReference('cloudTabPanel').getActiveTab();
        activeTab.fireEvent('reloadInfo', this.getViewModel().get('isModified'));
        this.lookupReference('modifiedAvailable').show();
        //this.getNetworkConfig(activeTab);
    },

    getNetworkConfig : function(activeTab) {
        var me = this;
        /*Ext.Ajax.request({
         url: url,
         params: {
         version :-1,
         status: this.getViewModel().get('isModified') ? "INPROGRESS" : "ACTIVE"
         },
         success: function(response){
         me.lookupReference('cloudTabPanel').getActiveTab().fireEvent('changeToModified');
         var resp = response.responseXML;
         var respXML = parseStringToXml(resp.getElementsByTagName("Data")[0].textContent);
         activeTab.fireEvent('reloadInfo');
         }
         });*/
    },

    onPoolsTabChange : function(tabPanel, newTab, oldTab, eOpts) {
        this.getNetworkConfig(newTab);

        //This is to avoid adding history when the Pools page gets activated the first time.
        if (oldTab) {
            var tabs = [],
                ownerCt = tabPanel.ownerCt,
                tokenDelimiter = ':',
                oldToken,
                newToken;

            tabs.push(newTab.id);
            tabs.push(tabPanel.id);

            while (ownerCt && ownerCt.is('tabpanel')) {
                tabs.push(ownerCt.id);
                ownerCt = ownerCt.ownerCt;
            }

            newToken = tabs.reverse().join(tokenDelimiter);

            oldToken = Ext.History.getToken();

            if (oldToken === null || oldToken.search(newToken) === -1) {
                if (tabPanel) {
                    var oldToken = Ext.History.getToken();
                    var newToken = '';
                    if (oldToken.indexOf(':tabpanel') !== -1) {
                        newToken = oldToken.substring(0, oldToken.indexOf(':tabpanel'));
                    } else {
                        newToken = oldToken;
                    }
                    newToken += ":" + tabPanel.id + ":" + newTab.id;
                    this.getView().lookupController(true).getViewModel().set('callItemClick', false);
                    Ext.History.add(newToken);
                }
            }
        }
    },

    onPoolsTabRender : function(tabPanel, eOpts) {
        var me = this,
            activeTab = tabPanel.getActiveTab();
        this.getNetworkConfig(activeTab);
        Ext.each(tabPanel.getTabBar().items.getRange(), function(tbar) {
            tbar.on('click', function() {
                tabPanel.getActiveTab().fireEvent('reloadInfo', me.getViewModel().get('isModified'));
            });
        });
        this.onPoolsTabChange(tabPanel, tabPanel.getActiveTab());
    },

    activatePool : function(record) {
        var me = this;
        Ext.Msg.confirm(l10n('confirmation'), l10n('activating-modified-vidyocloud-confirmation'), function(btn) {
        	if(btn == "yes") {
		        Ext.Ajax.request({
		            url : 'activaterouterpools.ajax',
		            method : 'POST',
		            success : function(res) {
		            	if(res.responseText == "true") {
			                Ext.Msg.alert(l10n('activated'), l10n('pool-configuration-activated'), function() {
			                	me.getView().lookupReference('radioFieldActive').setValue(true);
			                	me.getView().lookupReference('modifiedAvailable').hide();
			                });
		            	} else {
		            		Ext.Msg.alert(l10n('error'), Ext.decode(res.responseText).message);
		            	}
		            },
		            failure : function(res) {
		                Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
		                });
		            }
		        });
        	}
        });
    },
    
    discardPool : function(id) {
        var me = this;
        
        Ext.Msg.confirm(l10n('confirmation'), l10n('do-you-want-to-discard-the-modified-vidyocloud'), function(res) {
            if(res == "yes") {
                Ext.Ajax.request({
                    url : 'discardmodifiedcloud.ajax',
                    method : 'POST',
                  
                    success : function(response) {
                    	
                    	 var obj = Ext.decode(response.responseText);
                         if (obj.success) {
                         
                            Ext.Msg.alert(l10n('discard'), l10n('pool-configuration-discarded'), function() {
                                me.getView().lookupReference('radioFieldActive').setValue(true);
                                me.getView().lookupReference('modifiedAvailable').hide();
                            });
                        } else {
                            Ext.Msg.alert(l10n('error'), obj.message);
                        }
                    }
                });
            }
        });
            
    },

    addToHistory : function() {

    },

    onRenderCloudMain : function() {
        var me = this,
            viewModel = me.getViewModel(),
            tabPanel = me.lookupReference('cloudTabPanel'),
            activeTab = tabPanel.getActiveTab();

        if (activeTab.xtype !== "interpool") {
            tabPanel.setActiveTab(0);
            activeTab = tabPanel.getActiveTab();
        }
        activeTab.fireEvent('reloadInfo', me.getViewModel().get('isModified'));
        Ext.Ajax.request({
            url : 'ismodifiedpresent.ajax',
            method : 'GET',
            success : function(response) {
                var result = Ext.JSON.decode(response.responseText);
                
                if(result.success) {
                	me.getView().lookupReference('modifiedAvailable').show();
                    return;
                }
                me.getView().lookupReference('modifiedAvailable').hide();
            }
        });
    }
});

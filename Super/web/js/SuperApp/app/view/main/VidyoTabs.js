Ext.define('SuperApp.view.main.VidyoTabs', {
    extend : 'Ext.tab.Panel',
    alias : 'widget.vidyotabs',
    requires : ['SuperApp.view.tenants.Main', 'SuperApp.view.components.ComponentMain', 'SuperApp.view.cloud.main.Main', 'SuperApp.view.settings.SettingsTab'],
    tabPosition : 'top',
    cls:'headertabcss',
   

    items : [{
      	xtype : 'componentmain',
        title : l10n('components')      
    }, {
    	// xtype : 'currentCalls',
        title : l10n('current-calls'),
    }, {
    	// xtype : 'tenantsMain',
        title : l10n('tenants'),
    }, {
    	// xtype : 'cloudmain',
        title : l10n('router-pools'),
    }, {
    //	 xtype : 'settingsMainView',
        title : l10n('settings'),
    }],
    activeTab : 0,
    border:true,
    listeners: {
  
        beforerender: function(panel) {
            var bar = panel.tabBar;
            bar.insert(0, [{

                xtype: 'panel',
               
                width: 200,
            
                  
                bodyStyle: {
                        'background-color': 'inherit',
                        //verflow': 'hidden'
                       

                    },
                items: [{
                   
                    

                    xtype: 'form',
                    style: {
                        'background-color': 'inherit',
                    },
                    bodyStyle: {
                        'background-color': 'inherit',
                        //verflow': 'hidden'


                    },
                    cls: 'logoform',
                    // maxWidth : 145,
                    loader: {
                        url: 'customizedlogoinmarkup.ajax',
                        autoLoad: true
                    }
                }]
            }]);
            bar.insert(9, [{

                    xtype: 'component',

                    flex: 1,
                    
                },

                {
                    xtype: 'panel',
                    reference:'rightpaneltab',

                    bodyStyle: {
                       'background-color': 'inherit',
                        'color': '#FFFFFF',
                        
                    },
                    
                    layout: {
                        type: 'vbox'
                    },
                    items: [{
                        xtype: 'label',
                           style: {
                                'margin-top': '10px', 
                           
                            },
                         html :  superusername + ipAddress + ' ' + ' <a style="font-weight: bold;" href="javascript:void(0)" onclick="logoutSuper()">' + l10n('logout') + '</a> '

                    },
                    {
                        xtype : 'label',
                        bind : {
                            html : '<span style="font-weight:normal;padding:1px">' + l10n('software-version') + ':</span>&nbsp{portalversion}'
                        }
                    }
                   
                ]
            },

                 {
                    xtype: 'component',
                    width:20
                  
                }
                                   


                


            ]);

        }
    }
});

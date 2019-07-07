Ext.define('SuperApp.view.settings.SettingsTab', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.settingsMainView',
    requires : ['SuperApp.view.settings.SettingsController', 'SuperApp.view.settings.hotstand.Operation', 'SuperApp.view.settings.hotstand.DatabaseSync', 'SuperApp.view.settings.hotstand.Status', 'SuperApp.view.settings.maintenance.SystemRestart', 'SuperApp.view.settings.SettingsViewModel', 'SuperApp.view.settings.PNS', 'SuperApp.store.settings.MenuTree', 'SuperApp.store.settings.PNSStore', 'SuperApp.view.settings.endpointsoftware.UES', 'SuperApp.model.settings.AboutInfoModel', 'SuperApp.model.settings.SupportInfoModel', 'SuperApp.model.settings.InviteTextModel', 'SuperApp.model.settings.SLFormModel', 'SuperApp.model.settings.Field'],

    viewModel : {
        type : 'SettingsViewModel'
    },

    layout : {
        type : 'hbox',
        align : 'stretch'
    },

    xtype : 'settingsMainView',

    flex : 1,

    border : false,

    controller : 'SettingsCtrl',

    initComponent : function() {
        var me = this,
            pnsStore = Ext.create('SuperApp.store.settings.PNSStore');

        //Initially load pns store by default.
        /* pnsStore.load({
         callback : function() {
         me.down('pnsform').getForm().loadRecord(pnsStore.getAt(0));

         }
         });*/

        me.items = [{
            xtype : 'treepanel',
            singleExpand : true,
            bind : {
                store : '{menuStore}'
            },
            style : {
                'border-right' : '1px solid #ccc',
            },
            //flex : 1.2,
            rootVisible : false,
            border : false,
            width : 290,
            autoScroll : true,
            reference : 'settingsTreePanel',
            listeners : {
                'render' : 'settingsTreePanelRender',
                'itemclick' : 'onClickSettingsItem'
            }
        }, {
            xtype : 'panel',
            flex : 1,
            reference : 'settingsDescription',
            border : false,
            margin : '5px,0px,0px,0px',
            defaults : {
                border : false
            },
            layout : {
                type : 'card',
                align : 'stretch'
            },
            items : [{
                xtype : 'systemlicense'
            }]
        }];

        me.listeners = {
            afterrender : function() {
                me.down('treepanel').getSelectionModel().select(0);
                me.lookupReference('settingsDescription').setActiveItem(me.down('systemlicense'));
                var tempStore=me.lookupReference('sysLicenseGrid').getStore();

             //it is a bad practice to reload store when there is no data.Also it will break in testing mode.
             if(tempStore.getCount()!=0){

                 tempStore.reload();
               }
            },
            beforerender : function() {
                var viewModel = me.getViewModel();

                viewModel.getStore('settingsTabLeftMenuStore').removeAll();
                viewModel.getStore('settingsTabLeftMenuStore').load({
                    callback : function(recs) {
                        if (recs.length) {
                            var rec = recs[0],
                                index = 0,
                                menuStore = viewModel.getStore('menuStore');

                            viewModel.set('privilegedMode', rec.get('privilegedMode'));

                            if (rec.get('privilegedMode') == false) {
                                me.down('panel[reference="settingsDescription"]').setDisabled(true);
                            }

                            menuStore.each(function(node, i) {
                                switch(node.id) {
                                case 'maintenance' :
                                    node.eachChild(function(child) {
                                        switch (child.id) {
                                        case 'extdb' :
                                            if (rec.get('showExtDbTab')) {
                                                child.set('visible', true);
                                            } else {
                                                child.set('visible', false);
                                            }
                                            break;
                                        case 'syslog' :
                                            if (rec.get('showSyslog')) {
                                                child.set('visible', true);
                                            } else {
                                                child.set('visible', false);
                                            }
                                            break;
                                        case 'statusnotify' :
                                            if (rec.get('showStatusNotifyTab')) {
                                                child.set('visible', true);
                                            } else {
                                                child.set('visible', false);
                                            }
                                            break;
                                        case 'pkiTenants' :
                                            var notificationObj=Ext.ComponentQuery.query('button[reference=restartnotification]')[0];
                                            if(notificationObj && notificationObj.pkiCertReviewPendingFlag.trim()=='true'){



                                                child.set('visible', true);
                                            } else {
                                                child.set('visible', false);
                                            }
                                            break;
                                        case 'eventsnotify' :
                                            if (rec.get('showEventsNotifyTab')) {
                                                child.set('visible', true);
                                            } else {
                                                child.set('visible', false);
                                            }
                                            break;
                                        }
                                    });
                                    break;
                                case 'gfs' :
                                    node.eachChild(function(child) {
                                        if (child.id == "gmail") {
                                            if (rec.get('gmailPluginInstalled')) {
                                                child.set('visible', true);
                                            } else {
                                                child.set('visible', false);
                                            }
                                        }
                                    });
                                    break;
                                case 'hotstand' :
                                    if (node.get('cls') != 'x-node-hide') {
                                        node.set('cls', 'x-node-hide');
                                    }
                                    if (rec.get('maintMode')) {
                                        index = i;
                                    }
                                    break;
                                }
                            });
                            if(index) {
                                menuStore.getAt(index).set('cls', 'x-node-show');
                            }
                        }
                    }
                });
            }
        };

        me.callParent(arguments);
        me.down('treepanel').getSelectionModel().select(0);
    }
});

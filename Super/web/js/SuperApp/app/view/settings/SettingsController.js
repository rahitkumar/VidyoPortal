Ext.define('SuperApp.view.settings.SettingsController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.SettingsCtrl',

    requires : ['SuperApp.store.settings.PNSStore', 'SuperApp.view.settings.PNS', 'SuperApp.view.settings.SystemLicense', 'SuperApp.view.settings.endpointsoftware.UES', 'SuperApp.view.settings.endpointsoftware.EndpointSoftwareViewModel', 'SuperApp.view.settings.endpointsoftware.FileServer', 'SuperApp.view.settings.endpointsoftware.ExternalCDN', 'SuperApp.view.settings.CDRaccess', 'SuperApp.view.settings.maintenance.Syslog', 'SuperApp.view.settings.ScheduledRoom', 'SuperApp.view.settings.maintenance.MaintenanceView', 'SuperApp.view.settings.superaccounts.SuperAccounts', 'SuperApp.view.settings.customization.CustomizationView', 'SuperApp.store.settings.ScheduledRoom', 'SuperApp.store.settings.VidyoWeb', 'SuperApp.view.settings.globalsettings.VidyoWeb', 'SuperApp.store.settings.VidyoMobile', 'SuperApp.view.settings.globalsettings.VidyoMobile', 'SuperApp.store.settings.VidyoDesktop', 'SuperApp.view.settings.globalsettings.VidyoDesktop', 'SuperApp.store.settings.SearchOptions', 'SuperApp.view.settings.globalsettings.SearchOptions', 'SuperApp.store.settings.IPC', 'SuperApp.view.settings.globalsettings.EndpointSettings', 'SuperApp.store.settings.EndpointSettings', 'SuperApp.view.settings.globalsettings.IPC', 'SuperApp.store.settings.TLS', 'SuperApp.view.settings.globalsettings.TLS', 'SuperApp.view.settings.globalsettings.Chat', 'SuperApp.store.settings.GmailPlugin', 'SuperApp.view.settings.globalsettings.GmailPlugin', 'SuperApp.view.settings.security.SecurityView', 'SuperApp.view.settings.maintenance.SystemLogs', 'SuperApp.view.settings.CDRAccess.CDRExportPurge','SuperApp.store.settings.UserAttributes','SuperApp.store.settings.CustomRoles','SuperApp.store.settings.EnableEpicIntegration','SuperApp.store.settings.EnableTytoCareIntegration'],

    control : {
    },

    /***
     * onClickSettingsItem : function
     * @description : when user click on any of the entity from the treepanel items relevent to
     * the particular is shown in the right side panel.
     *
     * @param {Object} tree
     * @param {Object} record
     * @param {Object} item
     * @param {Object} index
     */
    onClickSettingsItem : function(tree, record, item, index) {
        var me = this,
            settingsView = me.view,
            viewModel = me.getViewModel(),
            descView = me.lookupReference('settingsDescription'),
            xtype;

        var recordId = "";
        if ( typeof record == "string") {
            recordId = record;
        } else {
            recordId = record.getId();
        }

        if (this.getViewModel().get('privilegedMode') == false) {
            descView.setDisabled(true);
        }

        if (tree) {
            var oldToken = Ext.History.getToken();
            var newToken = '';
            if (oldToken.indexOf(':treepanel') !== -1) {
                newToken = oldToken.substring(0, oldToken.indexOf(':treepanel'));
            } else {
                newToken = oldToken;
            }
            newToken += ":" + this.getView().down('treepanel').getId() + ":" + this.getView().down('treepanel').getSelection()[0].getId();
            this.getView().lookupController(true).getViewModel().set('callItemClick', false);
            Ext.History.add(newToken);
        }

        switch(recordId) {
        case 'pns':
            if (!descView.down('pnsform')) {
                descView.add({
                    xtype : 'pnsform'
                });
            }
            var view = descView.down('pnsform'),
                store = Ext.getStore('pnsstore') || Ext.create('SuperApp.store.settings.PNSStore');

            descView.getLayout().setActiveItem(view);

            store.load({
                callback : function() {
                    view.getForm().loadRecord(store.getAt(0));
                }
            });
            break;
        case 'sl' :
            if (!descView.down('systemlicense')) {
                descView.add({
                    xtype : 'systemlicense'
                });
            }
            var view = descView.down('systemlicense');
            descView.getLayout().setActiveItem(view);
            me.getSystemLicenseData();
            break;
        case 'ues' :
	        case 'mesfileserver' :
	            var uesPnl = descView.down('fileserver') || Ext.create('SuperApp.view.settings.endpointsoftware.FileServer');
	            descView.getLayout().setActiveItem(uesPnl);
	            break;
	        case 'mesversions' :
	        	var uesPnl;
	        	uesPnl = descView.down('externalcdn');
	        	if (!uesPnl){
	        		uesPnl = descView.down('endpointsoftware')
	        	}
	        	if (uesPnl){
	        		uesPnl.destroy();
	        	}
	        	Ext.Ajax.request({
	        		url : 'uploadmode.ajax',
	                method : 'GET',
	                success : function(response, options) {
                        var xml = response.responseXML;
                        var uploadMode = Ext.DomQuery.selectValue("uploadMode", xml).trim();
                        if (uploadMode != null && uploadMode == 'External') {
                        	uesPnl = descView.down('externalcdn') || Ext.create('SuperApp.view.settings.endpointsoftware.ExternalCDN');
        	        	} else {
        	        		uesPnl = descView.down('endpointsoftware') || Ext.create('SuperApp.view.settings.endpointsoftware.UES');
        	        	}
        	            descView.getLayout().setActiveItem(uesPnl);
        	            uesPnl.getController().getEndPointSoftwareData();
	                },
	                failure : function () {
	                	Ext.Msg.alert(l10n('error'), l10n('no-configuration-found'));
	                }
	        	});
	            break;
        case 'maintenance' :
        case 'db' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('database')) {
                view.add({
                    xtype : 'database',
                });
            }
            view.getLayout().setActiveItem(view.down('database'));
            //view.setTitle('Database');
            descView.getLayout().setActiveItem(view);
            view.getController().getDatabaseData();
            break;
        case 'sysup' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('systemupgrade')) {
                view.add({
                    xtype : 'systemupgrade',
                });
            }
            view.getLayout().setActiveItem(view.down('systemupgrade'));
            descView.getLayout().setActiveItem(view);
            view.getController().getSystemUpgradeData();
            break;
        case 'sysrst' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('systemrestart')) {
                view.add({
                    xtype : 'systemrestart',
                });
            }
            view.getLayout().setActiveItem(view.down('systemrestart'));
            //view.setTitle('System Restart');
            descView.getLayout().setActiveItem(view);
            break;
        case 'cdr' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('cdraccess')) {
                view.add({
                    xtype : 'cdraccess',
                });
            }
            view.getLayout().setActiveItem(view.down('cdraccess'));
            descView.getLayout().setActiveItem(view);
            view.getController().getCdrAccessData();
            break;
        case 'diagnostics' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('diagnostics')) {
                view.add({
                    xtype : 'diagnostics',
                });
            }
            view.getLayout().setActiveItem(view.down('diagnostics'));
            //  view.setTitle('Diagnostics');
            descView.getLayout().setActiveItem(view);
            view.getController().getDiagnosticsData();
            break;
        case 'syslog' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('syslog')) {
                view.add({
                    xtype : 'syslog',
                });
            }
            view.getLayout().setActiveItem(view.down('syslog'));
            descView.getLayout().setActiveItem(view);
            view.getController().getSysLogData();
            break;
        case 'extdb' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('externaldatabase')) {
                view.add({
                    xtype : 'externaldatabase',
                });
            }
            view.getLayout().setActiveItem(view.down('externaldatabase'));
            descView.getLayout().setActiveItem(view);
            view.getController().getExternalDatabaseData();
            break;
        case 'statusnotify' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('statusnotify')) {
                view.add({
                    xtype : 'statusnotify',
                });
            }
            view.getLayout().setActiveItem(view.down('statusnotify'));
            descView.getLayout().setActiveItem(view);
            view.getController().getStatusNotifyData();
            break;
        case 'eventsnotifyserver' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('eventsnotify')) {
                view.add({
                    xtype : 'eventsnotify',
                });
            }
            view.getLayout().setActiveItem(view.down('eventsnotify'));
            descView.getLayout().setActiveItem(view);
            view.getController().getEventsNotifyData();
            break;
        case 'pkiTenants' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('tenantsCrtPendingAppr')) {
                view.add({
                    xtype : 'tenantsCrtPendingAppr',
                });
            }
            view.getLayout().setActiveItem(view.down('tenantsCrtPendingAppr'));
            descView.getLayout().setActiveItem(view);

            break;
        case 'systemlog' :
            var view = descView.down('maintenanceview') || Ext.create('SuperApp.view.settings.maintenance.MaintenanceView', {
                flex : 2,
                border : false
            });
            if (!view.down('systemlogs')) {
                view.add({
                    xtype : 'systemlogs',
                });
            }
            view.getLayout().setActiveItem(view.down('systemlogs'));
            descView.getLayout().setActiveItem(view);
            view.getController().getSystemLogsData();
            break;
        case 'superacc' :
            var superPnl = descView.down('superaccounts') || Ext.create('SuperApp.view.settings.superaccounts.SuperAccounts', {
                flex : 2,
                border : false
            });
            descView.getLayout().setActiveItem(superPnl);
            superPnl.getController().getSuperAccountsData();
            break;
        case 'cust' :
        case 'aboutinfo' :
            var view = descView.down('customizationview') || Ext.create('SuperApp.view.settings.customization.CustomizationView', {
                flex : 2,
                border : false
            });
            if (!view.down('aboutinfo')) {
                view.add({
                    xtype : 'aboutinfo',
                });
            }
            view.getLayout().setActiveItem(view.down('aboutinfo'));
            descView.getLayout().setActiveItem(view);
            view.getController().getAboutInfoData();
            break;
        case 'supportinfo' :
            var view = descView.down('customizationview') || Ext.create('SuperApp.view.settings.customization.CustomizationView', {
                flex : 2,
                border : false
            });
            if (!view.down('supportinfo')) {
                view.add({
                    xtype : 'supportinfo',
                });
            }
            view.getLayout().setActiveItem(view.down('supportinfo'));
            descView.getLayout().setActiveItem(view);
            view.getController().getSupportInfoData();
            break;
        case 'notifications' :
            var view = descView.down('customizationview') || Ext.create('SuperApp.view.settings.customization.CustomizationView', {
                flex : 2,
                border : false
            });
            if (!view.down('notification')) {
                view.add({
                    xtype : 'notification',
                });
            }
            view.getLayout().setActiveItem(view.down('notification'));
            descView.getLayout().setActiveItem(view);
            view.getController().getNotificationData();
            break;
        case 'invitetext' :
            var view = descView.down('customizationview') || Ext.create('SuperApp.view.settings.customization.CustomizationView', {
                flex : 2,
                border : false
            });
            if (!view.down('invitetext')) {
                view.add({
                    xtype : 'invitetext',
                });
            }
            view.getLayout().setActiveItem(view.down('invitetext'));
            descView.getLayout().setActiveItem(view);
            view.getController().getInviteTextData();
            break;
        case 'custlogos' :
            var view = descView.down('customizationview') || Ext.create('SuperApp.view.settings.customization.CustomizationView', {
                flex : 2,
                border : false
            });
            if (!view.down('customizelogo')) {
                view.add({
                    xtype : 'customizelogo',
                });
            }
            view.getLayout().setActiveItem(view.down('customizelogo'));
            descView.getLayout().setActiveItem(view);
            view.getController().getCustomizeLogosData();
            break;
        case 'customizeEndpoints' :
                var view = descView.down('customizationview') || Ext.create('SuperApp.view.settings.customization.CustomizationView', {
                        flex : 2,
                        border : false
                    });
                if (!view.down('customizeEndpoints')) {
                    view.add({
                        xtype : 'customizeEndpoints'
                    });
                }
                view.getLayout().setActiveItem(view.down('customizeEndpoints'));
                descView.getLayout().setActiveItem(view);
                view.getController().getSuperCustomizeEndpointData();
                break;
        case 'guidesprop' :
            var view = descView.down('customizationview') || Ext.create('SuperApp.view.settings.customization.CustomizationView', {
                flex : 2,
                border : false
            });
            if (!view.down('guideproperties')) {
                view.add({
                    xtype : 'guideproperties',
                });
            }
            view.getLayout().setActiveItem(view.down('guideproperties'));
            descView.getLayout().setActiveItem(view);
            view.getController().getGuideLocationData();
            break;
        case 'banners' :
            var view = descView.down('customizationview') || Ext.create('SuperApp.view.settings.customization.CustomizationView', {
                border : false
            });
            if (!view.down('banners')) {
                view.add({
                    xtype : 'banners',
                    border : false
                });
            }
            view.getLayout().setActiveItem(view.down('banners'));
            descView.getLayout().setActiveItem(view);
            view.getController().getBannersData();
            break;
        case 'roomlink' :
            var view = descView.down('customizationview') || Ext.create('SuperApp.view.settings.customization.CustomizationView', {
                flex : 2,
                border : false
            });
            if (!view.down('roomlink')) {
                view.add({
                    xtype : 'roomlink',
                });
            }
            view.getLayout().setActiveItem(view.down('roomlink'));
            descView.getLayout().setActiveItem(view);
            view.getController().getRoomLinknData();
            break;
        case "schedroom" :
            var store = Ext.getStore('scheduledroom') || Ext.create('SuperApp.store.settings.ScheduledRoom');

            store.load({
                callback : function(rec) {
                    var rec = rec[0];
                    var schedPanel = descView.down('scheduledroom') || Ext.create('SuperApp.view.settings.ScheduledRoom', {
                        flex : 2,
                        fieldRec : rec,
                        border : false
                    });
                    descView.getLayout().setActiveItem(schedPanel);
                    schedPanel.loadRecord(rec);
                    settingsView.getViewModel().set('prevScheduledRoom', rec);
                }
            });

            break;
        case 'gfs' :
        case 'vidyoweb' :
            var store = Ext.getStore('vidyoweb') || Ext.create('SuperApp.store.settings.VidyoWeb');
            store.load({
                callback : function(rec) {
                    var rec = rec[0];
                    var vidyoweb = descView.down('vidyoweb') || Ext.create('SuperApp.view.settings.globalsettings.VidyoWeb', {
                        flex : 2,
                        border : false,
                        fieldRec : rec
                    });
                    descView.getLayout().setActiveItem(vidyoweb);
                    vidyoweb.loadRecord(rec);
                }
            });
            break;
        case 'vidyoneowebrtc' :
            var store = Ext.getStore('vidyoneowebrtc') || Ext.create('SuperApp.store.settings.VidyoNeoWebRTC');
            store.load({
                callback : function(rec) {
                    var rec = rec[0];
                    var vidyoNeoWebRTC = descView.down('vidyoneowebrtc') || Ext.create('SuperApp.view.settings.globalsettings.VidyoNeoWebRTC', {
                        flex : 2,
                        border : false,
                        fieldRec : rec
                    });
                    descView.getLayout().setActiveItem(vidyoNeoWebRTC);
                    vidyoNeoWebRTC.loadRecord(rec);
                }
            });
            break;
        case 'vidyomobile' :
            var store = Ext.getStore('vidyomobile') || Ext.create('SuperApp.store.settings.VidyoMobile');
            store.load({
                callback : function(rec) {
                    var rec = rec[0];
                    var vidyoMob = descView.down('vidyomobile') || Ext.create('SuperApp.view.settings.globalsettings.VidyoMobile', {
                        flex : 2,
                        border : false,
                        fieldRec : rec
                    });
                    descView.getLayout().setActiveItem(vidyoMob);
                    vidyoMob.loadRecord(rec);
                }
            });
            break;
        case 'vidyodesktop' :
            var store = Ext.getStore('vidyodesktop') || Ext.create('SuperApp.store.settings.VidyoDesktop');
            store.load({
                callback : function(rec) {
                    var rec = rec[0];
                    var vidyoDesktop = descView.down('vidyodesktop') || Ext.create('SuperApp.view.settings.globalsettings.VidyoDesktop', {
                        flex : 2,
                        border : false
                    });
                    descView.getLayout().setActiveItem(vidyoDesktop);
                    vidyoDesktop.loadRecord(rec);
                }
            });
            break;
        case 'searchoptions' :
            /* var store = Ext.getStore('searchoptions') || Ext.create('SuperApp.store.settings.SearchOptions');
             store.load({
             callback : function(rec) {
             var rec = rec[0];

             searchOptions.loadRecord(rec);
             }
             });*/
            var searchOptions = descView.down('searchoptions') || Ext.create('SuperApp.view.settings.globalsettings.SearchOptions', {
                flex : 2,
                border : false
            });
            descView.getLayout().setActiveItem(searchOptions);
            searchOptions.getController().getSearchOptionsData();
            break;
        case 'ipc' :
            var ipcForm = descView.down('ipcform') || Ext.create('SuperApp.view.settings.globalsettings.IPC', {
                flex : 2,
                border : false
            });
            ipcForm.getController().getIPCData(function() {
                descView.getLayout().setActiveItem(ipcForm);
                ipcForm.down('grid').setMinHeight(100);
            });
            break;
        case 'endpointSettings' :
                var endpointSettingsForm = descView.down('endpointsettingsform') || Ext.create('SuperApp.view.settings.globalsettings.EndpointSettings', {
                        flex : 2,
                        border : false
                    });
                endpointSettingsForm.getController().getEndpointSettingsData(function() {
                    descView.getLayout().setActiveItem(endpointSettingsForm);
                });
                break;
        case 'tls' :
            var tlsForm = descView.down('tlsform') || Ext.create('SuperApp.view.settings.globalsettings.TLS', {
                flex : 2,
                border : false
            });
            descView.getLayout().setActiveItem(tlsForm);
            tlsForm.getController().getTLSData();
            break;
        case 'chat' :
            var chat = descView.down('chat') || Ext.create('SuperApp.view.settings.globalsettings.Chat', {
                flex : 2,
                border : false
            });
            descView.getLayout().setActiveItem(chat);
            chat.getController().getChatData();
            break;
        case 'userAttributes' :
          var store = Ext.getStore('userAttributes') || Ext.create('SuperApp.store.settings.UserAttributes');

            store.load({
                callback : function(rec) {
                    var rec = rec[0];
                    var userAttrPanel = descView.down('userAttributes') || Ext.create('SuperApp.view.settings.globalsettings.UserAttributes', {
                        flex : 2,
                        fieldRec : rec,
                        border : false
                    });
                    descView.getLayout().setActiveItem(userAttrPanel);
                    userAttrPanel.loadRecord(rec);
                   // settingsView.getViewModel().set('prevScheduledRoom', rec);
                }
            });
            break;
        case 'customRoles' :
          var store = Ext.getStore('customRoles') || Ext.create('SuperApp.store.settings.CustomRoles');

            store.load({
                callback : function(rec) {
                    var rec = rec[0];
                    var customAttrPanel = descView.down('customRoles') || Ext.create('SuperApp.view.settings.globalsettings.CustomRoles', {
                        flex : 2,
                        fieldRec : rec,
                        border : false
                    });
                    descView.getLayout().setActiveItem(customAttrPanel);
                    customAttrPanel.loadRecord(rec);
                   // settingsView.getViewModel().set('prevScheduledRoom', rec);
                }
            });
            break;
        case 'enableEpicIntegration' :
          var store = Ext.getStore('enableEpicIntegration') || Ext.create('SuperApp.store.settings.EnableEpicIntegration');

            store.load({
                callback : function(rec) {
                    var rec = rec[0];
                    var epicAttrPanel = descView.down('enableEpicIntegration') || Ext.create('SuperApp.view.settings.globalsettings.EnableEpicIntegration', {
                        flex : 2,
                        fieldRec : rec,
                        border : false
                    });
                    descView.getLayout().setActiveItem(epicAttrPanel);
                    epicAttrPanel.loadRecord(rec);
                   // settingsView.getViewModel().set('prevScheduledRoom', rec);
                }
            });
            break;
        case 'enableTytoCareIntegration' :
          var store = Ext.getStore('enableTytoCareIntegration') || Ext.create('SuperApp.store.settings.EnableTytoCareIntegration');

            store.load({
                callback : function(rec) {
                    var rec = rec[0];
                    var tytoAttrPanel = descView.down('enableTytoCareIntegration') || Ext.create('SuperApp.view.settings.globalsettings.EnableTytoCareIntegration', {
                        flex : 2,
                        fieldRec : rec,
                        border : false
                    });
                    descView.getLayout().setActiveItem(tytoAttrPanel);
                    tytoAttrPanel.loadRecord(rec);
                   // settingsView.getViewModel().set('prevScheduledRoom', rec);
                }
            });
            break;
        case 'gmail' :
            var store = Ext.getStore('gmail') || Ext.create('SuperApp.store.settings.GmailPlugin');
            store.load({
                callback : function(rec) {
                    var rec = rec[0];
                    var view = descView.down('gmailplugin') || Ext.create('SuperApp.view.settings.globalsettings.GmailPlugin', {
                        flex : 2,
                        border : false,
                        fieldRec : rec
                    });
                    descView.getLayout().setActiveItem(view);
                    view.loadRecord(rec);
                }
            });
            break;
        case 'security' :
        case 'sslpk' :
            var view = descView.down('securityview') || Ext.create('SuperApp.view.settings.security.SecurityView');
            if (!view.down('panel[reference=securityviewpanel] privatekey')) {
                view.down('panel[reference=securityviewpanel]').add({
                    xtype : 'privatekey',
                    flex : 2
                });

            } else {
                view.getController().getSecurityViewData();
            }
            view.down('panel[reference=securityviewpanel]').getLayout().setActiveItem(view.down('panel[reference=securityviewpanel] privatekey'));
            descView.getLayout().setActiveItem(view);
            view.getController().getSecurityPkData();
            break;
        case "sslcsr" :
            var view = descView.down('securityview') || Ext.create('SuperApp.view.settings.security.SecurityView', {
                flex : 2,
                border : false
            });
            if (!view.down('panel[reference=securityviewpanel] securitycsr')) {
                view.down('panel[reference=securityviewpanel]').add({
                    xtype : 'securitycsr'
                });

            }
            view.down('panel[reference=securityviewpanel]').getLayout().setActiveItem(view.down('panel[reference=securityviewpanel] securitycsr'));
            Ext.Ajax.request({
                url : 'security/security_csr.ajax',
                success : function(response) {
                    var result = response.responseXML,
                        viewModel = view.getViewModel();

                    viewModel.set("country", Ext.DomQuery.selectValue('country', result));
                    viewModel.set("state", Ext.DomQuery.selectValue('state', result));
                    viewModel.set("city", Ext.DomQuery.selectValue('city', result));
                    viewModel.set("company", Ext.DomQuery.selectValue('company', result));
                    viewModel.set("division", Ext.DomQuery.selectValue('division', result));
                    viewModel.set("domain", Ext.DomQuery.selectValue('domain', result));
                    viewModel.set("email", Ext.DomQuery.selectValue('email', result));
                    viewModel.set("csr", Ext.DomQuery.selectValue('csr', result));
                    viewModel.set('isPasswordView', false);
                }
            });
            descView.getLayout().setActiveItem(view);
            break;
        case "servercert" :
            var view = descView.down('securityview') || Ext.create('SuperApp.view.settings.security.SecurityView', {
                flex : 2,
                border : false
            });
            if (!view.down('panel[reference=securityviewpanel] servercertificate')) {
                view.down('panel[reference=securityviewpanel]').add({
                    xtype : 'servercertificate'
                });
            }
            view.down('panel[reference=securityviewpanel]').getLayout().setActiveItem(view.down('panel[reference=securityviewpanel] servercertificate'));
            view.getController().loadServerCert();
            descView.getLayout().setActiveItem(view);
            break;
        case 'servcacert':
            var view = descView.down('securityview') || Ext.create('SuperApp.view.settings.security.SecurityView', {
                flex : 2,
                border : false
            });
            if (!view.down('panel[reference=securityviewpanel] cacertificate')) {
                view.down('panel[reference=securityviewpanel]').add({
                    xtype : 'cacertificate'
                });
            }
            view.getController().getSecurityCAServerData();
            view.down('panel[reference=securityviewpanel]').getLayout().setActiveItem(view.down('panel[reference=securityviewpanel] cacertificate'));
            descView.getLayout().setActiveItem(view);
            break;
        case 'apps' :
            var view = descView.down('securityview') || Ext.create('SuperApp.view.settings.security.SecurityView', {
                flex : 2,
                border : false
            });

            if (!view.down('panel[reference=securityviewpanel] securityapplication')) {
                view.down('panel[reference=securityviewpanel]').add({
                    xtype : 'securityapplication'
                });
            }
            view.down('panel[reference=securityviewpanel]').getLayout().setActiveItem(view.down('panel[reference=securityviewpanel] securityapplication'));
            view.getController().getSecurityAppsData();
            descView.getLayout().setActiveItem(view);
            break;
        case 'advanced':
            var view = descView.down('securityview') || Ext.create('SuperApp.view.settings.security.SecurityView', {
                flex : 2,
                border : false
            });
            if (!view.down('panel[reference=securityviewpanel] securityadvance')) {
                view.down('panel[reference=securityviewpanel]').add({
                    xtype : 'securityadvance'
                });
            }
            view.getViewModel().set('isPasswordView', false);
            view.down('panel[reference=securityviewpanel]').getLayout().setActiveItem(view.down('panel[reference=securityviewpanel] securityadvance'));
            descView.getLayout().setActiveItem(view);
            break;
        case 'passwords' :
            var view = descView.down('securityview') || Ext.create('SuperApp.view.settings.security.SecurityView', {
                flex : 2,
                border : false
            });
            view.getViewModel().set('isPasswordView', true);
            if (!view.down('panel[reference=securityviewpanel] passwords')) {
                view.down('panel[reference=securityviewpanel]').add({
                    xtype : 'passwords'
                });
            }
            view.down('panel[reference=securityviewpanel]').getLayout().setActiveItem(view.down('panel[reference=securityviewpanel] passwords'));
            descView.getLayout().setActiveItem(view);
            view.getController().getPasswordsData();
            break;
        case 'hotstand':
        case 'hotstandstatus' :
            var view = descView.down('hotstandstatus') || Ext.create('SuperApp.view.settings.hotstand.Status', {
                flex : 2,
                border : false
            });
            descView.getLayout().setActiveItem(view);
            view.getController().getHotStandStatusData();
            break;
        case 'hotstandoperation' :
            var view = descView.down('hotstandoperation') || Ext.create('SuperApp.view.settings.hotstand.Operation', {
                flex : 2,
                border : false
            });
            descView.getLayout().setActiveItem(view);
            view.getController().getHotStandDBSyncData();
            break;

        }
    },

    getUESData : function() {
        var me = this,
            store = me.getViewModel().getStore("uploadEndpointSoftwareStore"),
            view = me.view;
        store.removeAll();
        store.load();
        //somecrazy bug. it is not displaying UI whenever user goes to another page and come back. hack for now. later we will re write it and put in MVVM
     store.removeAll();
    store.load({
        callback : function(recs) {
            view.getViewModel().set('hideUESGrid', recs.length ? false : true);
        }
    });
    },

    sysLogRadioEnable : function(obj, ev, eo) {
        if (obj.inputValue == 'E') {
            this.lookupReference("sysLogPanel").show();
            this.lookupReference("syslogflag").setValue("on");
            this.lookupReference("ipaddress").enable();
            this.lookupReference("remoteport").enable();
        }
    },
    sysLogRadioDisable : function(obj, ev, eo) {
        if (obj.inputValue == 'D') {
            this.lookupReference("ipaddress").disable();
            this.lookupReference("remoteport").disable();
            this.lookupReference("sysLogPanel").hide();
            this.lookupReference("syslogflag").setValue("off");
            this.lookupReference("syslogSave").enable();
        }
    },
    CDRAccessAllow : function(obj, ev, eo) {
        if (obj.inputValue == 'A') {
            this.lookupReference("CDRAccessPanel").enable();
            //this.lookupReference("CDRAccessPanel").enable();
            this.getViewModel().set('allowCDRDAC', false);
            this.getViewModel().set('cdrallowDenyValue', 1);
        }
    },
    CDRAccessDAllow : function(obj, ev, eo) {
        if (obj.inputValue == 'D') {
            this.lookupReference("CDRAccessPanel").disable();
            //this.lookupReference("CDRAccessPanel").disable();
            this.getViewModel().set('allowCDRDAC', true);
            this.getViewModel().set('cdrallowDenyValue', 0);
        }
    },
    emailNotiEnable : function(obj, ev, eo) {
        if (obj.inputValue == 'E') {
            this.lookupReference("notificationPanel").show();
            this.lookupReference("testBtn").show();
            this.lookupReference("defaultBtn").show();
        }
    },
    emailNotiDisable : function(obj, ev, eo) {
        if (obj.inputValue == 'D') {
            this.lookupReference("notificationPanel").hide();
            this.lookupReference("testBtn").hide();
            this.lookupReference("defaultBtn").hide();
        }
    },
    settingsTreePanelRender : function() {
        var me = this;
        setTimeout(function() {
            me.lookupReference("settingsTreePanel").getSelectionModel().select(0);
            me.getSystemLicenseData();
        }, 100);
    },

    onVidyoWebCancel : function() {
        var me = this;
    },

    onClickVidyoDesktopSave : function() {
        var me = this;
    },
    cdrExportPurge : function() {
        var cdrExportWin = Ext.create('SuperApp.view.settings.CDRAccess.CDRExportPurge');
        cdrExportWin.show();

    },
    convertCheckboxToInt : function(o) {
        return (o.checked) ? 1 : 0;
    },
    convertBooleanToInt : function(o) {
        console.log("o.getValue()==" + o.getValue());
        return (o.getValue() == true) ? 1 : 0;
    },
    actionCompleted : function(form, action) {
        var fipsEnabled = this.getViewModel().get("fipsEnabled");
        if (action.type == 'load') {
            if (!fipsEnabled) {
                var allowflag = form.findField('CDRAllowDeny');
                if (allowflag.getValue() == 'false') {
                    this.lookupReference('cdrDisallowRadio').focus();
                    this.lookupReference('cdrDisallowRadio').setRawValue('true');
                    this.lookupReference('cdrAllowRadio').setRawValue('false');
                    this.getViewModel().set('cdrallowDenyValue', 0);
                    this.lookupReference("CDRAllowDelete").setValue(false);
                    this.lookupReference('ip').setValue("");
                    this.lookupReference('password').setValue("");

                } else {
                    this.lookupReference('cdrAllowRadio').focus();
                    this.lookupReference('cdrAllowRadio').setRawValue('true');
                    this.lookupReference('cdrDisallowRadio').setRawValue('false');
                    this.getViewModel().set('cdrallowDenyValue', 1);
                }
            }
        }
    },

    getSystemLicenseData : function() {
        var me = this,
            slGrid = me.lookupReference('sysLicenseGrid'),
            licenseTextField = me.view.down('textfield'),
            viewModel = me.getViewModel();

        viewModel.getStore('systemLicenseGridStore').load({
            callback : function(recs) {
                var rec = recs[0];
                if (!rec.get('FQDN')) {
                    licenseTextField.setFieldLabel(l10n('system-id'));
                    viewModel.getStore('vmIdStore').load({
                    	callback:function(recs){
                    		if (typeof(recs) != 'undefined' && (recs != null)) {
                    			var rec = recs[0];
                    			licenseTextField.setValue(rec.get('vmID'));
                    		}
                        }
                    });

                    //viewModel.set('licenseText', 'System ID');
                } else {
                    // viewModel.set('licenseText', 'FQDN');
                	 licenseTextField.setFieldLabel(l10n('fqdn'));
                }
                Ext.Array.each(recs, function(rec) {
                    if (rec.get('feature') === l10n('SuperSystemLicense-encryption') && rec.get('license') != l10n('SuperSystemLicense-none')) {
                        viewModel.set('isEncryption', false);
                        /***
                         * @Boolean isEncryption
                         * It works in opposite manner when its true the encryption
                         * button is disable and when isEncryption is false
                         * Encryption button in security tab is enable.
                         */
                    }
                    //license
                });
                //viewModel.set('isEncryption',rec.get());
                viewModel.set('fqdn', recs[0].get('FQDN'));
                slGrid.getStore().removeAll(true);
                slGrid.getStore().loadData(recs);
            }
        });
    },

    systemLicenseUpload : function() {
        var scope = this;
        this.lookupReference("sysLicenseForm").getForm().submit({
            url : 'savelicense.ajax',
            waitMsg : l10n('saving'),
            success : function(form, action) {

                var xmlResponse = action.response.responseXML;

                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);

                if (success == "false") {
                    var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                    var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                    Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                } else {
                    Ext.Msg.alert(l10n('success'), l10n('successfully-uploaded'));
                }

                form.reset();
                scope.getSystemLicenseData();
            },
            failure : function(form, action) {
                Ext.Msg.alert(l10n('error'), l10n("request.failed"));
                scope.lookupReference("sysLicenseForm").getForm().reset();
            }
        });
    },
    getAuditParamValue : function(obj) {
        var strReturn = '';
        try {
            strReturn = obj.getValue().trim();
        } catch (err) {
            strReturn = '30';
        }
        return strReturn;
    },

    getVidyoPortalLogUrl : function() {
        var strReturn = 'securedmaint/maintenance_portal_logs_export.ajax?',
            formatDate = function(date) {
            return Ext.Date.format(date, "Y-m-d H:m:s");
        };
        // strReturn += 'startDate=' + this.getAuditParamValue(this.lookupReference('startDate'));
        strReturn += 'startDate=' + formatDate(this.lookupReference('startDate').getValue());
        strReturn += '&endDate=' + formatDate(this.lookupReference('endDate').getValue());
        return strReturn;
    },
    getAuditLogUrl : function() {
        var strReturn = 'securedmaint/maintenance_audit_export.ajax?',
            formatDate = function(date) {
            return Ext.Date.format(date, "Y-m-d H:m:s");
        };
        // strReturn += 'startDate=' + this.getAuditParamValue(this.lookupReference('startDate'));
        strReturn += 'startDate=' + formatDate(this.lookupReference('startDate').getValue());
        strReturn += '&endDate=' + formatDate(this.lookupReference('endDate').getValue());
        return strReturn;
    },
    auditExportBtn : function() {
        try {
            Ext.destroy(scope.lookupReference('downloadAuditLog'));
        } catch(ignored) {
        }
        Ext.DomHelper.append(document.body, {
            tag : 'iframe',
            reference : 'downloadAuditLog',
            frameBorder : 0,
            width : 0,
            height : 0,
            css : 'display:none; visibility:hidden; height:0px;',
            src : this.getAuditLogUrl()
        });
    },
    vidyoPortalLogsExportBtn : function() {
        try {
            Ext.destroy(scope.lookupReference('downloadAuditLog'));
        } catch(ignored) {
        }
        Ext.DomHelper.append(document.body, {
            tag : 'iframe',
            reference : 'downloadAuditLog',
            frameBorder : 0,
            width : 0,
            height : 0,
            css : 'display:none; visibility:hidden; height:0px;',
            src : this.getVidyoPortalLogUrl()
        });
    },

    addToHistory : function(newToken) {
        var selection = this.getView().down('treepanel').getSelection(),
            treeId = selection.length ? selection[0].getId() : 'pns';
        newToken += ":" + this.getView().down('treepanel').getId() + ":" + treeId;
        this.getView().lookupController(true).getViewModel().set('callItemClick', false);
        Ext.History.add(newToken);
    },

    loadVidyoWebData : function() {
        var store = Ext.getStore('vidyoweb') || Ext.create('SuperApp.store.settings.VidyoWeb');
        store.load({
            callback : function(rec) {
                var rec = rec[0];
                var vidyoweb = descView.down('vidyoweb') || Ext.create('SuperApp.view.settings.globalsettings.VidyoWeb', {
                    flex : 2,
                    border : false,
                    fieldRec : rec
                });
                descView.getLayout().setActiveItem(vidyoweb);
                vidyoweb.loadRecord(rec);
            }
        });
    }

});

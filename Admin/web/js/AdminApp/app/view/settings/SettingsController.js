Ext.define('AdminApp.view.settings.SettingsController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.SettingsController',

    onClickSettingsMenu: function(grid, rec, item, index, e) {

        var me = this,
            view = me.view,
             viewModel = me.getViewModel();
            //title = me.lookupReference('settingstitle'),
            settingsview = me.lookupReference('settingsitems'),
            menuGrid = me.lookupReference('menugrid');

        var recordId = "";
        if (typeof rec == "string") {
            recordId = rec;
        } else {
            recordId = rec.get('id');
        }
        if (grid) {
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

        //logos
        // title.up('toolbar').setHeight(42);
        switch (recordId) {
            case 'license':
                if (!settingsview.down('licenseview')) {
                    settingsview.add({
                        xtype: 'licenseview'
                    });
                }
                var view = settingsview.down('licenseview');
                settingsview.getLayout().setActiveItem(view);
                //title.setText('<span class="header-title" style="font-size:15px;">'+l10n('SuperSystemLicense-license')+'</span>');
                view.getController().getLicenseData();
                break;
            case 'ues':
            	var uesPnl;
            	uesPnl = settingsview.down('externalcdn');
        		if (!uesPnl){
	        		uesPnl = settingsview.down('uploadendpointview') 
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
           	        		uesPnl = settingsview.down('externalcdn') || Ext.create('AdminApp.view.settings.ues.ExternalCDN');
           	        	} else {
           	        		uesPnl = settingsview.down('uploadendpointview') || Ext.create('AdminApp.view.settings.ues.UploadEndpointView');
           	        	}
        	            settingsview.getLayout().setActiveItem(uesPnl);
        	            uesPnl.getController().getEndPointSoftwareData();
	                },
	                failure : function () {
	                	Ext.Msg.alert(l10n('error'), l10n('no-configuration-found'));
	                }
	        	});
                break;
            case 'sysLang':
                if (!settingsview.down('systemlanguage')) {
                    settingsview.add({
                        xtype: 'systemlanguage'
                    });
                }
                var view = settingsview.down('systemlanguage');
                settingsview.getLayout().setActiveItem(view);
                //title.setText('<span class="header-title" style="font-size:15px;">'+l10n('system-language')+'</span>');
                view.getController().getSystemLanguageData();
                break;
            case 'guestsettings':
                if (!settingsview.down('guestview')) {
                    settingsview.add({
                        xtype: 'guestview'
                    });
                }
                var view = settingsview.down('guestview');
                settingsview.getLayout().setActiveItem(view);
                //title.setText('<span class="header-title" style="font-size:15px;">'+l10n('guest-s-settings')+'</span>');
                view.getController().getGuestSettingsData();
                break;
            case 'aboutinfo':
            case 'customization':
                if (!settingsview.down('aboutinfo')) {
                    settingsview.add({
                        xtype: 'aboutinfo'
                    });
                }
                var view = settingsview.down('aboutinfo');
                settingsview.getLayout().setActiveItem(view);
                //title.setText('<span class="header-title" style="font-size:15px;">'+l10n('about-info')+'</span>');
                view.getController().getAboutInfoData();
                break;
            case 'authentication':
                if (!settingsview.down('authenticationview')) {
                    settingsview.add({
                        xtype: 'authenticationview'
                    });
                }
                var view = settingsview.down('authenticationview');
                settingsview.getLayout().setActiveItem(view);
                //title.setText('<span class="header-title" style="font-size:15px;">'+l10n('authentication')+'</span>');
               view.getController().getAuthenticationData();
                break;
            case 'supportinfo':
                if (!settingsview.down('supportinfo')) {
                    settingsview.add({
                        xtype: 'supportinfo'
                    });
                }
                var view = settingsview.down('supportinfo');
                settingsview.getLayout().setActiveItem(view);
                // title.setText('<span class="header-title" style="font-size:15px;">'+l10n('contact-info1')+'</span>');
                view.getController().getSupportInfoData();
                break;
            case 'notification':
                if (!settingsview.down('notification')) {
                    settingsview.add({
                        xtype: 'notification'
                    });
                }
                var view = settingsview.down('notification');
                settingsview.getLayout().setActiveItem(view);
                // title.setText('<span class="header-title" style="font-size:15px;">'+l10n('notification')+'</span>');
                view.getController().getNotificationData();
                break;
            case 'invitetext':
                if (!settingsview.down('invitetext')) {
                    settingsview.add({
                        xtype: 'invitetext'
                    });
                }
                var view = settingsview.down('invitetext');
                settingsview.getLayout().setActiveItem(view);
                // title.setText('<span class="header-title" style="font-size:15px;">'+l10n('invite-text')+'</span>');
                view.getController().getInviteTextData();
                break;
            case 'customizelogos':
                if (!settingsview.down('customizelogo')) {
                    settingsview.add({
                        xtype: 'customizelogo'
                    });
                }
                var view = settingsview.down('customizelogo');
                settingsview.getLayout().setActiveItem(view);
                // title.setText('<div class="header-title" style="font-size:13px;margin :0;text-align:center"><div style="font-size:15px">'+l10n('customize-logo')+'</div><div>'+l10n('vidyodesktop-download-page-logo')+' (.jpg, .gif, .png)</div></div>');
                // title.setMargin('-5 15 5 5');
                // title.up('toolbar').setHeight(60);
                // title.el.setTop(0);
                view.getController().getCustomizeLogosData();
                break;
            case 'neoendpoints':
                if (!settingsview.down('neoendpoints')) {
                    settingsview.add({
                        xtype: 'neoendpoints'
                    });
                }
                var view = settingsview.down('neoendpoints');
                settingsview.getLayout().setActiveItem(view);
                view.getController().getAdminCustomizeEndpointData();
                break;
            case 'loctags':
                if (!settingsview.down('locationtags')) {
                    settingsview.add({
                        xtype: 'locationtags'
                    });
                }
                var view = settingsview.down('locationtags');
                settingsview.getLayout().setActiveItem(view);
                //  title.setText('<span class="header-title" style="font-size:15px;">'+l10n('location-tags')+'</span>');
                view.getController().getLocationTagData();
                break;
            case 'ipc':
                if (!settingsview.down('interportalview')) {
                    settingsview.add({
                        xtype: 'interportalview'
                    });
                }
                var view = settingsview.down('interportalview');
                settingsview.getLayout().setActiveItem(view);
                // title.setText('<span class="header-title" style="font-size:15px;">'+l10n('ipc-label')+'</span>');
                view.getController().getIPCData();
                break;
            case 'cdraccess':
                if (!settingsview.down('cdraccess')) {
                    settingsview.add({
                        xtype: 'cdraccess'
                    });
                }
                var view = settingsview.down('cdraccess');
                settingsview.getLayout().setActiveItem(view);
                //title.setText('<span class="header-title" style="font-size:15px;">'+l10n('cdr-access')+'</span>');
                break;
            case 'security':
               if (!settingsview.down('security')) {
                    settingsview.add({
                        xtype: 'passwords'
                    });
                }
                var view = settingsview.down('passwords');
                settingsview.getLayout().setActiveItem(view);

                // title.setText('<span class="header-title" style="font-size:15px;">' + l10n('passwords-tablabel') + '</span>');
                view.getController().onRenderPasswordView();
                break;
            case 'qualityservice':
                if (!settingsview.down('qualityservice')) {
                    settingsview.add({
                        xtype: 'qualityservice'
                    });
                }
                var view = settingsview.down('qualityservice');
                settingsview.getLayout().setActiveItem(view);
                //title.setText('<span class="header-title" style="font-size:15px;">'+l10n('admin-dscpconfig-leftmenu-label')+'</span>');
                view.getController().getQualityServiceData();
                break;
           
            case 'featuresettings':
            case 'chat':
             if(viewModel.get('vidyoChatAvailable')){
                if (!settingsview.down('chatview')) {
                    settingsview.add({
                        xtype: 'chatview'
                    });
                }
                var view = settingsview.down('chatview');
                settingsview.getLayout().setActiveItem(view);
                // title.setText('<span class="header-title" style="font-size:15px;">'+l10n('chat')+'</span>');
                view.getController().getChatViewData();
                break;
            }
          case 'roomattr':
                if (!settingsview.down('roomattribute')) {
                    settingsview.add({
                        xtype: 'roomattribute'
                    });
                }
                var view = settingsview.down('roomattribute');
                settingsview.getLayout().setActiveItem(view);

                //title.setText('<span class="header-title" style="font-size:15px;">'+l10n('room-attributes')+'</span>');
                view.getController().getRoomAttributeData();
                break;
           case 'userattr':
           if(viewModel.get('showUserAttributePage')){
                if (!settingsview.down('userAttribute')) {
                    settingsview.add({
                        xtype: 'userAttribute'
                    });
                }
                var view = settingsview.down('userAttribute');
                settingsview.getLayout().setActiveItem(view);

                //title.setText('<span class="header-title" style="font-size:15px;">'+l10n('room-attributes')+'</span>');
                view.getController().getUserAttributeData();
                break; 
            }
           case 'epicintegration':
           	if(viewModel.get('showEpicIntegration')){
                if (!settingsview.down('epicintegration')) {
                    settingsview.add({
                        xtype: 'epicintegration'
                    });
                }
                var view = settingsview.down('epicintegration');
                settingsview.getLayout().setActiveItem(view);
                view.getController().getEpicIntegrationData();
                break; 
            }
           case 'tytocareintegration':
           	if(viewModel.get('showTytoCareIntegration')){
                if (!settingsview.down('tytocareintegration')) {
                    settingsview.add({
                        xtype: 'tytocareintegration'
                    });
                }
                var view = settingsview.down('tytocareintegration');
                settingsview.getLayout().setActiveItem(view);
                view.getController().getTytoCareIntegrationData();
                break; 
           	}
           case 'vidyoneowebrtc':
               if(viewModel.get('showVidyoNeoWebRTC')){
                    if (!settingsview.down('vidyoneowebrtc')) {
                        settingsview.add({
                            xtype: 'vidyoneowebrtc'
                        });
                    }
                    var view = settingsview.down('vidyoneowebrtc');
                    settingsview.getLayout().setActiveItem(view);
                    view.getController().getVidyoNeoWebRTCData();
                }
               break; 
           case 'vidyoweb':
               if(!viewModel.get('showVidyoNeoWebRTC') && viewModel.get('vidyowebAvaiable')){
                   if (!settingsview.down('vidyoweb')) {
                       settingsview.add({
                           xtype: 'vidyoweb'
                       });
                   }
                   var view = settingsview.down('vidyoweb');
                   settingsview.getLayout().setActiveItem(view);
                   // title.setText('<span class="header-title" style="font-size:15px;">'+l10n('vidyoweb')+'</span>');
                   view.getController().getVidyoWebData();
                   break;
               }
        }
    },

    onRenderSettingsView: function() {
        var me = this,
            view = me.view,
            // title = me.lookupReference('settingstitle'),
            settingsview = me.lookupReference('settingsitems');

        Ext.defer(function() {
            view.down('treepanel').getSelectionModel().select(0);
            //title.setText('<span class="header-title" style="font-size:15px;">'+l10n('SuperSystemLicense-license')+'</span>');
            settingsview.getLayout().setActiveItem(view.down('licenseview'));
        }, 10);
        view.down('licenseview').getController().getLicenseData();
    },

    onBeforeRenderSettingsView: function() {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();


        viewModel.getStore('settingsTabLeftMenuStore').removeAll();
        viewModel.getStore('settingsTabLeftMenuStore').load({
            callback: function(recs) {
                if (recs.length) {
                    var rec = recs[0],
                        tmp,
                        tree = view.down('treepanel'),
                        ipcCount = 0,
                        vidyoWebCount = 0,
                        hideIPCFlag = false,
                        menuStore = viewModel.getStore('settignsMenuStore');

                    menuStore.each(function(child, index) {
                        if (child.id == "ipc") {
                            ipcCount = index;
                        }

                    });
                    index = 0;
                    menuStore.each(function(node, index) {
                        if (node.id == 'featuresettings') {
                            node.eachChild(function(child) {
                                if (child.id == "vidyoweb") {
                                    if (!rec.get('vidyoWebAvailable') || rec.get('showVidyoNeoWebRTC')) {
                                        child.set('visible', false);
                                        viewModel.set('vidyowebAvaiable',false);
                                    } else {
                                        child.set('visible', true);
                                         viewModel.set('vidyowebAvaiable',true);
                                    }    
                                } else if (child.id == "epicintegration") {
                                    if (!rec.get('showEpicIntegration')) {
                                        child.set('visible', false);
                                        viewModel.set('showEpicIntegration',false);
                                    } else {
                                        child.set('visible', true);
                                        viewModel.set('showEpicIntegration',true);
                                    }
                                } else if (child.id == "tytocareintegration") {
                                    if (!rec.get('showTytoCareIntegration')) {
                                        child.set('visible', false);
                                        viewModel.set('showTytoCareIntegration',false);
                                    } else {
                                        child.set('visible', true);
                                        viewModel.set('showTytoCareIntegration',true);
                                    }   
                                } else if (child.id == "vidyoneowebrtc") {
                                        if (!rec.get('showVidyoNeoWebRTC')) {
                                            child.set('visible', false);
                                            viewModel.set('showVidyoNeoWebRTC',false);
                                        } else {
                                            child.set('visible', true);
                                            viewModel.set('showVidyoNeoWebRTC',true);
                                        }
                                }
                            });

                        }
                    });
                    menuStore.each(function(node, index) {
                        if (node.id == 'featuresettings') {
                            node.eachChild(function(child) {
                                if (child.id == "chat") {
                                    if (!rec.get('vidyoChatAvailable')) {
                                        child.set('visible', false);
                                         viewModel.set('vidyoChatAvailable',false);
                                    } else {
                                        child.set('visible', true);
                                         viewModel.set('vidyoChatAvailable',true);
                                    }
                                }


                            });

                        }
                    });
                       menuStore.each(function(node, index) {
                        if (node.id == 'featuresettings') {
                            node.eachChild(function(child) {
                                if (child.id == "userattr") {
                                    if (!rec.get('showUserAttributePage')) {
                                        child.set('visible', false);
                                         viewModel.set('showUserAttributePage',false);
                                    } else {
                                        child.set('visible', true);
                                         viewModel.set('showUserAttributePage',true);
                                    }
                                }


                            });

                        }
                    });
                    menuStore.each(function(node, index) {
                        if (node.id == 'featuresettings') {
                            node.eachChild(function(child) {
                                if (child.id == "schroom") {
                                    if (!rec.get('vidyoSchdRoomAvailable')) {
                                        child.set('visible', false);
                                         viewModel.set('vidyoSchdRoomAvailable',false);
                                    } else {
                                        child.set('visible', true);
                                         viewModel.set('vidyoSchdRoomAvailable',true);
                                    }
                                }

                            });

                        }
                    });

                    if (rec.get('isIpcSuperManaged')) {
                        if (menuStore.getAt(ipcCount) && menuStore.getAt(ipcCount).id == "ipc") {
                            viewModel.set('ipcRecord', menuStore.getAt(ipcCount));
                            menuStore.remove(menuStore.getAt(ipcCount));
                        }
                    } else if (viewModel.get('ipcRecord') && viewModel.get('ipcRecord').id == "ipc" && menuStore.getAt(ipcCount).id != viewModel.get('ipcRecord').id) {
                        menuStore.insert(7, viewModel.get('ipcRecord'));
                    }


                }
            }
        });
    },

    onClickSaveRoomAttr: function() {
        var me = this;
    },

    addToHistory: function(newToken) {
        var treeNodeSelection = this.getView().down('treepanel').getSelection()[0];
        if (treeNodeSelection) {
            newToken += ":" + this.getView().down('treepanel').getId() + ":" + treeNodeSelection.get('id');
            this.getView().lookupController(true).getViewModel().set('callItemClick', false);
            Ext.History.add(newToken);
        } else {
            var me = this;
            Ext.defer(function() {
                newToken += ":" + me.getView().down('treepanel').getId() + ":" + me.getView().down('treepanel').getSelection()[0].get('id');
                me.getView().lookupController(true).getViewModel().set('callItemClick', false);
                Ext.History.add(newToken);
            }, 10);
        }
    }
});
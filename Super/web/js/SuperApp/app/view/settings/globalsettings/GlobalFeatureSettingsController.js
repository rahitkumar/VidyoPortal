Ext.define('SuperApp.view.settings.globalsettings.GlobalFeatureSettingsController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.GlobalFeatureSettingsController',

    requires : ['SuperApp.store.settings.Chat'],

    control : {
    },

    /***
     * @function onClickVidyoWebAvailable
     * @param {Object} btn
     */
    onChangeVidyoWebAvailable : function(rg, newVal) {
    	var form = rg.up('form'),
        vidyoWebEnabledGroup = form.down('checkbox[name=vidyoWebEnabledGroup]');
        if (newVal["vidyoWebAvailableGroup"] == "disabled") {
        	vidyoWebEnabledGroup.setValue({'vidyoWebEnabledGroup':'disabled'});
        	Ext.Array.each(vidyoWebEnabledGroup.items.getRange(), function(item) {
        		item.setDisabled(true);
        	});
 
        }else {
        	Ext.Array.each(vidyoWebEnabledGroup.items.getRange(), function(item) {
        		item.setDisabled(false);
        	});
        }
},
    /***
     * @function onClickVidyoWebSave
     * @param {Object} btn
     */
    onClickVidyoWebSave : function(btn) {
        var form = btn.up('form'),
            params = form.getValues();

        Ext.Ajax.request({
            url : 'savevidyoweb.ajax',
            params : params,
            method : 'POST',
            success : function(res) {
                var xmlResponse = res.responseXML;
                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                if (success == "true") {
                    Ext.Msg.alert('Message', 'Saved VidyoWeb options');
                }
            }
        });
    },
    /***
     * onClickVidyoMobileSave : function
     * @description save/update for vidyo mobile tab.
     * @param {Object} btn
     */
    onClickVidyoMobileSave : function(btn) {
        var form = btn.up('form'),
            values = form.getValues(),
            rec = btn.rec,
            confirmStr = rec.get('vidyoMobileSaveConfirmBoxDesc1') + " ";
            if (values["mobileAccessGroup"] == 1) {
            	confirmStr += l10n('vidyoMobile');
            } else if (values["mobileAccessGroup"] == 2) {
            	confirmStr += l10n('neoMobile');
            }else {
            	confirmStr += l10n('disabled');
            } 
            confirmStr += " " + rec.get('vidyoMobileSaveConfirmBoxDesc2');

        Ext.Msg.confirm(rec.get("vidyoMobileSaveConfirmBoxTitle"), confirmStr, function(response) {
            if (response == "yes") {
                Ext.Ajax.request({
                    url : 'savemobiaccess.ajax',
                    method : 'POST',
                    params : values,
                    success : function(res) {
                        var xmlResponse = res.responseXML;
                        var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                        if (success == "true") {
                            Ext.Msg.alert(l10n('message'), l10n('saved-vidyomobile-options'));
                        }
                    }
                });
            }
        });
    },

    getTLSData : function() {
        var me = this,
            view = me.view,
            store = Ext.getStore('tls') || Ext.create('SuperApp.store.settings.TLS');

        Ext.Ajax.request({
            url : 'security.html',
            success : function(response) {
                var result = response.responseXML;
                view.getViewModel().set('isSSLNotEnabled', Ext.DomQuery.selectValue('sslEnabledFlag', result) == "0" ? true : false);
            }
        });
        store.load({
            callback : function(recs) {
                view.loadRecord(recs[0]);
            }
        });
    },

    getIPCData : function(callback) {
       var me = this,
       viewModel = me.getViewModel();
       viewModel.getStore('portalDomainStore').load({
        callback : function(recs) {
	   if (callback && typeof(callback) == "function") {
                    callback();
                }
            }
        });
        viewModel.getStore('ipcStore').load({
            scope : this,
            callback : function(recs) {
                if (recs.length) {
                    var rec = recs[0];
                    setTimeout(function() {
    				    viewModel.getStore('routerPoolsStore').load({
                            scope : this,
                            callback : function(routers) {
                                var noneRouterPool = Ext.create('SuperApp.model.settings.RouterPools', {
                                    id : 0,
                                    name : 'None'
                                });
                                viewModel.getStore('routerPoolsStore').insert(0, noneRouterPool);
                                
                          }
                        });
                    }, 300);
                    var accessVal = rec.get('adminManaged') ? "admin" : rec.get('superManaged') ? "super" : "admin";
                    var routerpool = rec.get('routerPool') ;
                    me.lookupReference('routerPoolID').setValue(routerpool);
                    viewModel.set('accessvalue', accessVal);
                    me.lookupReference('ipcControlMode').setValue(rec.get('accessControlMode') ? "allow" : "block");
                    var ipcModeValue = rec.get('accessControlMode') ? "allow" : "block";
                    viewModel.set('ipccontrolmode', ipcModeValue);
                    viewModel.set('prevAllowFlag', rec.get('accessControlMode') ? 1 : 0);
                }
            }
        });
    },

    getEndpointSettingsData : function(callback) {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();
        store = Ext.getStore('endpointsettings') || Ext.create('SuperApp.store.settings.EndpointSettings');

        store.load({
            callback : function(recs) {
                view.loadRecord(recs[0]);

                if (callback && typeof(callback) == "function") {
                    callback();
                }
            }
        });
    },

    /***
     * @function onClickVidyoDesktopSave
     * @param {Object} btn
     */
    onClickVidyoDesktopSave : function(btn) {
        var me = this,
            form = btn.up('form'),
            values = form.getValues();

        Ext.Ajax.request({
            url : 'savevidyodesktopoptions.ajax',
            method : 'POST',
            params : values,
            success : function(res) {
                var xmlResponse = res.responseXML,
                    success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                if (success == "true") {
                    Ext.Msg.alert(l10n('message'), l10n('saved-vidyodesktop-options'));
                }
            }
        });
    },

    /***
     * @function onClickSearchOptionsSave
     * @param {Object} btn
     */
    onClickSearchOptionsSave : function(btn) {
        var me = this,
            form = btn.up('form'),
            values = form.getValues();

        Ext.Ajax.request({
            url : 'savesearchoptions.ajax',
            method : 'POST',
            params : values,
            success : function(res) {
                var xmlResponse = res.responseXML,
                    success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                if (success == "true") {
                    Ext.Msg.alert(l10n('message'), l10n('saved-search-options'));
                }
            }
        });
    },

    /***
     * @function onChangeIPCControlLevel
     * @param {Object} rg
     * @param {Object} newValue
     */
    onChangeIPCControlLevel : function(rg, newValue, oldValue) {
        var me = this,
            form = rg.up('form'),
            rgControlMode = form.down('radiogroup[name=ipcControlMode]'),
            domainGrid = me.lookupReference('ipcDomainGrid');

        if (newValue["accessControlGroup"] == "super") {
            rgControlMode.show();
            domainGrid.show();
        } else {
            rgControlMode.hide();
            domainGrid.hide();
        }
        me.lookupReference('ipcSave').enable();
    },

    /****
     * @function onChangeIPCRouterPool
     * @params {object} combo
     * @params {object} newValue
     * @Desc - to enable the save button on IPC form when
     * there is any change in router pool combobox.
     */
    onChangeIPCRouterPool : function(combo, newValue) {
        var me = this,
            form = combo.up('form');

        me.lookupReference('ipcSave').enable();
    },

    /***
     * @function onChangeIPCControlMode
     * @param {Object} rg
     * @param {Object} newValue
     */
    onChangeIPCControlMode : function(rg, newValue) {
        var me = this,
            form = rg.up('form'),
            grid = me.lookupReference('ipcDomainGrid'),
            rec = rg.rec;

        if (newValue["allowBlockGroup"] == "allow") {
            grid.setTitle(l10n('ipc-grid-title-allow-label'));
        } else {
            grid.setTitle(l10n('ipc-grid-title-block-label'));
        }
        me.lookupReference('ipcSave').enable();
    },

    /***
     * @function  onClickDomainGridAdd
     * @param {Object} grid
     */
    onClickDomainGridAdd : function(btn) {
        var me = this,
            viewModel = me.getViewModel(),
            grid = me.lookupReference('ipcDomainGrid'),
            form = grid.up('form');

        Ext.Msg.prompt(l10n('ipc-msgbox-addaddress-title'), l10n('ipc-msgbox-prompt-message'), function(res, text) {
            if (res == "ok") {
                var store = viewModel.get('portalDomainStore'),
                    flag = false,
                    regex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\")).(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;

                if (text.match(regex)) {
                    store.each(function(res) {
                        if (res.get('domainName') == text) {
                            flag = true;
                            return;
                        }
                    });
                    if (!flag) {
                        store.add({
                            'domainID' : '',
                            'domainName' : text,
                            'whiteList' : ''
                        });
                        me.lookupReference('ipcSave').enable();
                    } else {
                        Ext.MessageBox.alert(l10n('dublicate'), l10n('ipc-msgbox-duplicate-alert'));
                    }
                } else {
                    Ext.Msg.alert(l10n('error'), l10n('valid-domain-address'));
                }
            }
        });
    },

    /***
     * @function onClickDomainGridRemove
     * @param {object} btn
     */
    onClickDomainGridRemove : function(btn) {
        var me = this,
            grid = me.lookupReference('ipcDomainGrid'),
            gridStore = grid.getStore(),
            form = me.lookupReference('ipcForm'),
            selected = grid.getSelection(),
            hiddenField = form.down('hiddenfield'),
            selectedId;

        if (selected.length) {
            Ext.Msg.confirm(l10n('confirm'), l10n('ipc-msgbox-delete-confirm-messg'), function(res) {
                if (res == "yes") {
                    selectedId = selected[0].get('domainID');
                    if (hiddenField.getValue()) {
                        hiddenField.setValue(hiddenField.getValue() + ',' + selectedId);
                    } else {
                        hiddenField.setValue(selectedId);
                    }
                    gridStore.remove(selected[0]);
                    me.lookupReference('ipcSave').enable();
                }
            });
        }
    },

    /***
     * @function onClickIPCSave
     * @param {Object} btn
     */
    onClickIPCSave : function(btn) {
        var me = this,
            form = me.lookupReference('ipcForm'),
            params = form.getValues(),
            grid = me.lookupReference('ipcDomainGrid'),
            hiddenField = addedDomainsList = [];

        grid.getStore().each(function(rec) {
            if (rec.get("domainID") == "") {
                addedDomainsList.push(rec.get('domainName'));
            }
        });
        params["addedDomains"] = addedDomainsList.join();
        Ext.Ajax.request({
            url : 'saveipcsettings.ajax',
            method : 'POST',
            params : params,
            success : function(res) {
                var xmlResponse = res.responseXML,
                    success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                if (success == "true") {
                    Ext.Msg.alert(l10n('message'), l10n('ipc-saved-successfully'), function() {
                        grid.getStore().load();
                        form.getForm().findField('deleteIds').setValue('');
                    });
                } else {
                    Ext.Msg.alert(Ext.DomQuery.selectValue('id', xmlResponse), Ext.DomQuery.selectValue('msg', xmlResponse));
                }
            }
        });
    },

    onClickTLSSave : function(btn) {
        var me = this,
            form = btn.up('form'),
            params = form.getValues();

        Ext.Ajax.request({
            url : 'savetlsproxyconfig.ajax',
            method : 'POST',
            params : params,
            success : function(res) {
                var xmlResponse = res.responseXML,
                    success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                if (success == "true") {
                    Ext.Msg.alert(l10n('message'), l10n('super-misc-tls-tab-form-save-confirm-msg'));
                } else {
                    Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                }
                me.getTLSData();
            }
        });
    },

    onChangeChatAvailable : function(rg, newVal) {
        var form = rg.up('form'),
            privateRGroup = form.down('radiogroup[name=publicChatEnabledGroup]'),
            publicRGroup = form.down('radiogroup[name=privateChatEnabledGroup]');

        if (newVal["chatAvailableGroup"] == "unavailable") {
        	Ext.Array.each(privateRGroup.items.getRange(), function(item) {
        		item.setDisabled(true);
        	});
        	Ext.Array.each(publicRGroup.items.getRange(), function(item) {
        		item.setDisabled(true);
        	});
           
        } else {
        	Ext.Array.each(privateRGroup.items.getRange(), function(item) {
        		item.setDisabled(false);
        	});
        	Ext.Array.each(publicRGroup.items.getRange(), function(item) {
        		item.setDisabled(false);
        	});
        }
    },

    onClickChatSave : function(btn) {
        var me = this,
            form = btn.up('form'),
            params = form.getValues();

        Ext.Ajax.request({
            url : 'savechatsuper.ajax',
            method : 'POST',
            params : params,
            success : function(res) {
                var xmlResponse = res.responseXML,
                    success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                if (success == "true") {
                    Ext.Msg.alert(l10n('message'), l10n('saved'));
                }
            }
        });
    },
getUserAttributesData : function() {
        var me = this,
        view = me.view,
        viewModel = me.getViewModel();
        viewModel.getStore('chatStore').load({
            callback : function(recs) {
                var rec= recs[0];
                viewModel.set('chatVidyoPortalAvailable', rec.get('chatVidyoPortalAvailable'));
                viewModel.set('chatDefaultPublicStatus', rec.get('chatDefaultPublicStatus'));
                viewModel.set('chatDefaultPrivateStatus', rec.get('chatDefaultPrivateStatus'));
            }
        });
    },

    getChatData : function() {
        var me = this,
        view = me.view,
        viewModel = me.getViewModel();
        viewModel.getStore('chatStore').load({
            callback : function(recs) {
            	var rec= recs[0];
            	viewModel.set('chatVidyoPortalAvailable', rec.get('chatVidyoPortalAvailable'));
            	viewModel.set('chatDefaultPublicStatus', rec.get('chatDefaultPublicStatus'));
            	viewModel.set('chatDefaultPrivateStatus', rec.get('chatDefaultPrivateStatus'));
            }
        });
    },

    getSearchOptionsData : function() {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.getStore('searchOptionsStore').load({
            callback : function(recs) {
                me.view.loadRecord(recs[0]);
            }
        });
    },
    resetVidyoWebForm : function() {
    	this.getView().getForm().reset();
    },
    resetVidyoMobileForm : function() {
    	this.getView().getForm().reset();
    },
    resetSearchOptForm : function() {
    	this.getView().getForm().reset();
    },
    resetProxyData : function() {
    	this.getView().getForm().reset();
    },
    onClickEndpointSettingsSave : function(btn) {
        var me = this,
            form = btn.up('form'),

            params = form.getValues();


        if (form.getForm().isValid()) {
            Ext.Ajax.request({
                url : 'saveEndpointSettings.ajax',
                params : params,
                method : 'POST',
                success : function(res) {
                    var xmlResponse = res.responseXML;
                    var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                    if (success == "true") {
                        Ext.Msg.alert(l10n('success'), l10n('saved'));
                        Ext.getStore('endpointsettings').load({
                            callback : function(rec) {
                                form.loadRecord(rec[0]);
                            }
                        });
                    } else {
                        Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                    }
                },
                failure : function() {
                    Ext.Msg.alert(l10n('error'), l10n('error'));
                }
            });
        } else {
            Ext.Msg.alert(l10n('error'), "Please enter all required fields.");
        }

    },
    onChangeDSCPValue : function(text, value) {
        var me = this,
            viewModel = me.getViewModel();
        if (value == null) {
            return;
        }
        if (value > 63) {
            text.setValue(63);
            value = 63;
        }

        me.setDSCPHexValue(text.labelValue, value);
    },

    setDSCPHexValue : function(viewModelName, value) {
        var me = this,
            viewModel = me.getViewModel(),
            decimalToHexString = function(number) {
                if (number < 0) {
                    number = 0xFFFFFFFF + number + 1;
                }
                return number.toString(16).toUpperCase();
            };

        viewModel.set(viewModelName, '0x' + decimalToHexString(value));
    },
    
    onClickCustomRoleSave : function(btn) {
        var me = this,
            form = btn.up('form'),
          
            params = form.getValues();
          

        if (form.getForm().isValid()) {
            Ext.Ajax.request({
                        url : 'savecustomrole.ajax',
                        params : params,
                        method : 'POST',
                        success : function(res) {
                            var xmlResponse = res.responseXML;
                            var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                            if (success == "true") {
                                Ext.Msg.alert(l10n('success'), l10n('saved'));
                              
                            } else {
                                Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                            }
                        },
                        failure : function() {
                            Ext.Msg.alert(l10n('error'), l10n('error'));
                        }
                    });
                
           
        } else {
             Ext.Msg.alert(l10n('error'), "Please enter all required fields.");
        }

    },
    
    onClickEnableEpicSave : function(btn) {
        var me = this,
        form = btn.up('form'),
          
        params = form.getValues();
          

        if (form.getForm().isValid()) {
            Ext.Ajax.request({
                        url : 'saveenableepic.ajax',
                        params : params,
                        method : 'POST',
                        success : function(res) {
                            var xmlResponse = res.responseXML;
                            var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                            if (success == "true") {
                                Ext.Msg.alert(l10n('success'), l10n('saved'));
                              
                            } else {
                                Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                            }
                        },
                        failure : function() {
                            Ext.Msg.alert(l10n('error'), l10n('error'));
                        }
                    });
                
           
        } else {
             Ext.Msg.alert(l10n('error'), "Please enter all required fields.");
        }

    },    
    
    onClickEnableTytoCareSave : function(btn) {
        var me = this,
        form = btn.up('form'),
        params = form.getValues();
          
        if (form.getForm().isValid()) {
            Ext.Ajax.request({
                        url : 'saveenabletytocare.ajax',
                        params : params,
                        method : 'POST',
                        success : function(res) {
                            var xmlResponse = res.responseXML;
                            var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                            if (success == "true") {
                                Ext.Msg.alert(l10n('success'), l10n('saved'));
                            } else {
                                Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                            }
                        },
                        failure : function() {
                            Ext.Msg.alert(l10n('error'), l10n('error'));
                        }
                    });
        } else {
             Ext.Msg.alert(l10n('error'), "Please enter all required fields.");
        }
    },    
    
     onClickUserAttributeSave : function(btn) {
        var me = this,
            form = btn.up('form'),
          
            params = form.getValues();
          

        if (form.getForm().isValid()) {
            Ext.Ajax.request({
                        url : 'saveuserattribute.ajax',
                        params : params,
                        method : 'POST',
                        success : function(res) {
                            var xmlResponse = res.responseXML;
                            var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                            if (success == "true") {
                                Ext.Msg.alert(l10n('success'), l10n('saved'));
                              
                            } else {
                                Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                            }
                        },
                        failure : function() {
                            Ext.Msg.alert(l10n('error'), l10n('error'));
                        }
                    });
                
           
        } else {
             Ext.Msg.alert(l10n('error'), "Please enter all required fields.");
        }

    },
    /***
     * @function getVidyoNeoWebRTCData
     */
    getVidyoNeoWebRTCData : function() {
        var me = this,
            view = me.view,
            enableVidyoNeoWebRTCGuest = me.lookupReference('enableVidyoNeoWebRTCGuest'),
            enableVidyoNeoWebRTCUser = me.lookupReference('enableVidyoNeoWebRTCUser');
        
        Ext.Ajax.request({
            url : 'vidyoneowebrtcsetting.ajax',
            method: 'GET',
            success : function(response) {
                var result = response.responseXML,
                    viewModel = view.getViewModel();
                
                viewModel.set('enableVidyoNeoWebRTCGuest', Ext.DomQuery.selectValue('enableVidyoNeoWebRTCGuest', result).trim());
                viewModel.set('enableVidyoNeoWebRTCUser', Ext.DomQuery.selectValue('enableVidyoNeoWebRTCUser', result).trim());
                enableVidyoNeoWebRTCGuest.setValue(viewModel.get('enableVidyoNeoWebRTCGuest'));
                enableVidyoNeoWebRTCUser.setValue(viewModel.get('enableVidyoNeoWebRTCUser'));
            }
        });
    },
    
    /***
     * @function onClickVidyoNeoWebRTCSave
     */
    onClickVidyoNeoWebRTCSave : function(btn) {
    	 var me = this,
         form = btn.up('form'),
       
         params = form.getValues();
    	 if (form.getForm().isValid()) {
    		 Ext.Ajax.request({
                 url : 'savevidyoneowebrtcsetting.ajax',
                 params : params,
                 method : 'POST',
                 success : function(res, params) {
                     var xmlResponse = res.responseXML;
                     var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                     if (success == "true") {
                         if(params.params.enableVidyoNeoWebRTCGuest == '1' || params.params.enableVidyoNeoWebRTCUser == '1') {
                        	 Ext.Msg.alert(l10n('success'), l10n('webrtc-settings-save-confirmation'));
                         } else {
                        	 Ext.Msg.alert(l10n('success'), l10n('saved'));
                         }
                     } else {
                         Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                     }
                 },
                 failure : function() {
                     Ext.Msg.alert(l10n('error'), l10n('error'));
                 }
             });
    	 }
    },
    

});

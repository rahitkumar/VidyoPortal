/**
 * @class FeatureSettingsController
 */
Ext.define('AdminApp.view.settings.featuresettings.FeatureSettingsController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.FeatureSettingsController',

    getEpicIntegrationData: function() {
        var me = this, viewModel = me.getViewModel();

        Ext.Ajax.request({
            url: 'getepicintegration.ajax',
            method: 'GET',
            success: function(res) {
                var result = res.responseXML;
                //me.view.setDisabled(false);
                viewModel.set('enableEpicIntegration', Ext.DomQuery.selectValue('enableEpicIntegration', result));
                viewModel.set('sharedSecret', Ext.DomQuery.selectValue('sharedSecret', result));
                viewModel.set('notificationUrl', Ext.DomQuery.selectValue('notificationUrl', result));
                viewModel.set('notificationUser', Ext.DomQuery.selectValue('notificationUser', result));
                viewModel.set('notificationPassword', Ext.DomQuery.selectValue('notificationPassword', result));
            }
        });
    },
    
    onClickCancelEpicIntegration: function(btn) {
    	this.getView().getForm().load({
            url: 'getepicintegration.ajax',
            method : 'GET'
        });
    },
    onClickSaveEpicIntegration: function(btn) {
        var me = this,
            vidyoform = me.view;

        if (vidyoform) {
            Ext.Ajax.request({
                url: 'saveepicintegration.ajax',
                params: vidyoform.getForm().getValues(),
                scope: me,
                success: function(res) {
                    var xmlResponse = res.responseXML;
                    if (Ext.DomQuery.selectValue('message @success', xmlResponse) == "true") {
                        Ext.Msg.alert(l10n('message'), l10n('saved-epic-options'));
                        me.getEpicIntegrationData();
                    } else {
                        Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                    }
                }
            });
        }
    },
    
    getTytoCareIntegrationData: function() {
        var me = this, viewModel = me.getViewModel();

        Ext.Ajax.request({
            url: 'gettytocareintegration.ajax',
            method: 'GET',
            success: function(res) {
                var result = res.responseXML;
                //me.view.setDisabled(false);
                viewModel.set('enableTytoCareIntegration', Ext.DomQuery.selectValue('enableTytoCareIntegration', result));
                viewModel.set('tytoUrl', Ext.DomQuery.selectValue('tytoUrl', result));
                viewModel.set('tytoUsername', Ext.DomQuery.selectValue('tytoUsername', result));
                viewModel.set('tytoPassword', Ext.DomQuery.selectValue('tytoPassword', result));
            }
        });
    },
    
    onClickCancelTytoCareIntegration: function(btn) {
    	this.getView().getForm().load({
            url: 'gettytocareintegration.ajax',
            method : 'GET'
        });
    },
    onClickSaveTytoCareIntegration: function(btn) {
        var me = this,
            vidyoform = me.view;

        if (vidyoform) {
            Ext.Ajax.request({
                url: 'savetytocareintegration.ajax',
                params: vidyoform.getForm().getValues(),
                scope: me,
                success: function(res) {
                    var xmlResponse = res.responseXML;
                    if (Ext.DomQuery.selectValue('message @success', xmlResponse) == "true") {
                        Ext.Msg.alert(l10n('message'), l10n('saved-tytocare-options'));
                        me.getTytoCareIntegrationData();
                    } else {
                        Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                    }
                }
            });
        }
    },
    
    onClickTytoCareConnectionTest: function (btn) {
        var me = this, view = me.view, viewtype = btn.viewtype;

   		if (view && btn.testrest) {
            var form = Ext.ComponentQuery.query('form[reference=tytocareintegrationform]')[0];
            if (!form.getForm().isValid()) {
                Ext.Msg.alert(l10n('error'), l10n('you-must-fill-in-the-mandatory-fields'));
                return;
            }
            var vals = form.getValues();   
            //vals.restFlag = 'true';

           
            Ext.Ajax.request({
                url: 'testtytocareconnection.ajax',
                method: 'POST',
                params: vals,
                success: function (res) {
                    var result = Ext.JSON.decode(res.responseText);
                    if (result.httpStatus == "200") {
                    	Ext.Msg.alert(l10n('message'), l10n('test-passed'));
                    	Ext.getCmp('istytocareconnectionsuccess').setValue(true);
                    } else {
                        Ext.Msg.alert(l10n('message'), l10n('connection-test-failed') + ". HTTP Status Code - "+ result.httpStatus);
                        Ext.getCmp('istytocareconnectionsuccess').setValue(false);
                    }
                },
                failure: function (res) {
                    Ext.Msg.alert(l10n('error'), l10n('timeout'));
                }
            });
    	}
    },
    
    getVidyoWebData: function() {
        var me = this,
            viewModel = me.getViewModel(),
            zincEnabled = me.lookupReference('zincEnabled'),
            zincServer = me.lookupReference('zincServer'),
            enableVidyoWeb = me.lookupReference('enableVidyoWeb');

        Ext.Ajax.request({
            url: 'getvidyoweb.ajax',
            method: 'GET',
            success: function(res) {
                var result = res.responseXML;



                var notAvailableFlag = Ext.DomQuery.selectValue('available', result) == "false" ? true : false;
                if (notAvailableFlag) {

                    me.view.setDisabled(true);

                } else {
                    me.view.setDisabled(false);

                    viewModel.set('enableVidyoWeb', Ext.DomQuery.selectValue('enabled', result) == "false" ? 'disabled' : 'enabled');
                    viewModel.set('enableWebRtc', Ext.DomQuery.selectValue('zincEnabled', result) == "false" ? 'disabled' : 'enabled');
                    viewModel.set('zincServer', Ext.DomQuery.selectValue('zincServer', result));



                }
            }
        });
    },


    onClickCancelVidyoWeb: function(btn) {
        this.getVidyoWebData();
    },
    onClickSaveVidyoWeb: function(btn) {
        var me = this,
            vidyoform = me.view;

        if (vidyoform) {
            Ext.Ajax.request({
                url: 'savevidyoweb.ajax',
                params: vidyoform.getForm().getValues(),
                scope: me,
                success: function(res) {
                    var xmlResponse = res.responseXML;
                    if (Ext.DomQuery.selectValue('message @success', xmlResponse) == "true") {
                        Ext.Msg.alert(l10n('message'), l10n('saved-vidyoweb-options'));
                        me.getVidyoWebData();
                    } else {
                        Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                    }
                }
            });
        }
    },

    getChatViewData: function() {
        var me = this,
            viewModel = me.getViewModel(),
            publicRG = me.lookupReference('publicChatEnabledGroup'),
            privateRG = me.lookupReference('privateChatEnabledGroup');
        Ext.Ajax.request({
            url: 'getchatadmin.ajax',
            method: 'GET',
            success: function(res) {
                var result = res.responseXML;

                viewModel.set('isChatHiddenLabel', Ext.DomQuery.selectValue('chatAvailable', result) == "true" ? true : false);
                viewModel.set('isChatDisabled', Ext.DomQuery.selectValue('chatAvailable', result) == "true" ? false : true);

                publicRG.setValue(Ext.DomQuery.selectValue('publicChatEnabled', result) == "true" ? "enabled" : "disabled");
                privateRG.setValue(Ext.DomQuery.selectValue('privateChatEnabled', result) == "true" ? "enabled" : "disabled");
            }
        });
    },
    onClickCancelChat: function(btn) {
        this.getChatViewData();
    },

    onClickSaveChat: function(btn) {
        var me = this,
            form = me.view;

        if (form) {
            Ext.Ajax.request({
                url: 'savechatadmin.ajax',
                params: form.getForm().getValues(),
                success: function(res) {
                    var result = res.responseXML;
                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                        Ext.Msg.alert(l10n('message'), l10n('saved'));
                        me.getChatViewData();
                    } else {
                        Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', result));
                    }
                }
            });
        }
    },

    getRoomAttributeData: function() {

        var me = this,
            view = me.view,
            viewModel = me.getViewModel(),
            waitUntilOwnerJoins = me.lookupReference('waitUntilOwnerJoins'),
            publicRoomMaxRoomNoPerUser = me.lookupReference('publicRoomMaxRoomNoPerUser');

        var store = viewModel.getStore('roomAttributeStore');
        store.load({
            callback: function(rec) {
                var rec = rec[0];
                // view.loadRecord(rec);
                //these are for binding
                viewModel.set('publicRoomEnabledGlobal', rec.get('publicRoomEnabledGlobal'));
                viewModel.set('schRoomEnabledSystemLevel', rec.get('schRoomEnabledSystemLevel'));
                publicRoomMaxRoomNoPerUser.setMaxValue(rec.get('publicRoomMaxRoomNoPerUserGlb'));


                //hack- due to a bug in ext js..some reason first page loading not selecting the radio button.
                waitUntilOwnerJoins.setValue(rec.get('waitUntilOwnerJoins') == "1" ? "1" : "0");

                view.loadRecord(rec);
                setTimeout(function() {
                    Ext.getCmp('room-save-button').disable();
                }, 100);

            }
        });
    },
    getUserAttributeData: function() {

        var me = this,
            view = me.view,
            viewModel = me.getViewModel();


        var store = viewModel.getStore('userAttributeStore');
        store.load({
            callback: function(rec) {
                var rec = rec[0];
                view.loadRecord(rec);
                viewModel.set('enableUserImgeUpldGlobal', rec.get('enableUserImgeUpldGlobal'));
                me.displayToolTip(view.lookupReference('enableUserImageUpload'));
                setTimeout(function() {
                    Ext.getCmp('usrattr-save-button').disable();
                }, 100);

            }
        });
    },
    displayToolTip: function(c) {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();
        var flag = viewModel.get('enableUserImgeUpldGlobal');
        if (!flag) {
          //  Ext.QuickTips.register({
           //     target: c.getEl(),
          //      text: 'Super admin has to enable from it Super Page'
            //});
        } else {
            Ext.QuickTips.register({
                target: c.getEl(),
                text: l10n('user-thumbnail-tooltip')
            });
        }
    },
    onClickUserAttributeSave: function() {
        var me = this,
            view = me.view,
            formValues = view.getForm().getValues();
        if (view) {

            Ext.Ajax.request({
                url: 'saveUserAttributesAdmin.ajax',
                params: formValues,
                method: 'POST',
                success: function(res) {
                    var result = res.responseXML;
                    if (Ext.DomQuery.selectValue("message @success", result) == "true") {
                        Ext.Msg.alert(l10n('message'), l10n('saved'));
                        view.getForm().setValues(formValues);
                    } else {
                        var errorsNode = Ext.DomQuery.select('message/errors/field', res.responseXML),
                            errors = '',
                            errorMsg = '';
                        for (var i = 0; i < errorsNode.length; i++) {
                            errors += Ext.DomQuery.selectValue('msg', errorsNode[i]) + '<br>';
                        }
                        errorMsg += '<br>' + errors;
                        Ext.Msg.alert(l10n('message'), errorMsg, function() {});
                    }
                }
            });


        }
    },
    onClickSaveRoomAttribute: function() {
        var me = this,
            view = me.view;

        if (view) {
            Ext.MessageBox.confirm(l10n('save-room-attributes'), l10n('all-current-calls-will-be-disconnected-continue'), function(res) {
                if (res == "yes") {
                    Ext.Ajax.request({
                        url: 'saveRoomAttributes.ajax',
                        params: view.getForm().getValues(),
                        method: 'POST',
                        success: function(res) {
                            var result = res.responseXML;
                            if (Ext.DomQuery.selectValue("message @success", result) == "true") {
                                Ext.Msg.alert(l10n('message'), l10n('saved'));
                                me.getRoomAttributeData();
                            } else {
                                var errorsNode = Ext.DomQuery.select('message/errors/field', res.responseXML),
                                    errors = '',
                                    errorMsg = '';
                                for (var i = 0; i < errorsNode.length; i++) {
                                    errors += Ext.DomQuery.selectValue('msg', errorsNode[i]) + '<br>';
                                }
                                errorMsg += '<br>' + errors;
                                Ext.Msg.alert(l10n('message'), errorMsg, function() {});
                            }
                        }
                    });
                }
            });

        }
    },
    onClickCancelRoomAttribute: function() {
        this.getRoomAttributeData();
    },
    getVidyoNeoWebRTCData: function() {
        var me = this,
        view = me.view,
            viewModel = me.getViewModel();

        var store = viewModel.getStore('vidyoNeoWebRTCStore');
        store.load({
            callback: function(records) {
                var record = records[0];
                view.loadRecord(record);
                viewModel.set('enableVidyoNeoWebRTCGuest', record.get('enableVidyoNeoWebRTCGuest'));
                viewModel.set('enableVidyoNeoWebRTCUser', record.get('enableVidyoNeoWebRTCUser'));
                viewModel.set('enableVidyoNeoWebRTCGuestAdmin', record.get('enableVidyoNeoWebRTCGuestAdmin'));
                viewModel.set('enableVidyoNeoWebRTCUserAdmin', record.get('enableVidyoNeoWebRTCUserAdmin'));
                setTimeout(function() {
                    Ext.getCmp('webrtc-save').disable();
                }, 100);
            }
        });
    },
    
    onClickVidyoNeoWebRTCSave: function(btn) {
    	var me = this,
        form = btn.up('form'),
      
        params = form.getValues();
   	 if (form.getForm().isValid()) {
   		 Ext.Ajax.request({
                url : 'savevidyoneowebrtcsettingadmin.ajax',
                params : params,
                method : 'POST',
                success : function(res) {
                    var xmlResponse = res.responseXML;
                    var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                    if (success == "true") {
                        Ext.Msg.alert(l10n('success'), l10n('saved'));
                        me.getVidyoNeoWebRTCData();
                    } else {
                        Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                    }
                },
                failure : function() {
                    Ext.Msg.alert(l10n('error'), l10n('error'));
                }
            });
   	 }
    }
});
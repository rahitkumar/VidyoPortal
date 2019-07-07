/**
 * @class CustomizationController
 */
Ext.define('SuperApp.view.settings.customization.CustomizationController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.CustomizationController',

    /****** CLICK EVENTS FUNCTIONS ****************/

    /***
     * @function onClickSaveAboutInfo
     */
    onClickSaveAboutInfo : function(btn) {
        var me = this,
            form = me.lookupReference('aboutinfo'),
            params = form.getForm().getValues();

        if (btn.type == "default") {
            params["followDefault"] = true;
            Ext.Msg.confirm(l10n('confirmation'), l10n('you-are-about-to-replace-customized-about-info-with-default-content-continue'), function(res) {
                if (res == "yes") {
                    me.saveView(params, 'saveaboutinfo.ajax', function() {
                        me.getAboutInfoData();
                    });
                }
            });
            return;
        }
        me.saveView(params, 'saveaboutinfo.ajax', function() {
            me.getAboutInfoData();
        });
    },

    /***
     * @function onClickSaveSupportInfo
     */
    onClickSaveSupportInfo : function(btn) {
        var me = this,
            form = me.lookupReference('supportinfo'),
            params = form.getForm().getValues();

        if (btn.type == "default") {
            params["followDefault"] = true;
            Ext.Msg.confirm(l10n('confirmation'), l10n('you-are-about-to-replace-customized-contact-info-with-default-content-continue'), function(res) {
                if (res == "yes") {
                    me.saveView(params, 'savecontactinfo.ajax', function() {
                        me.getSupportInfoData();
                    });
                }
            });
            return;
        }

        me.saveView(params, 'savecontactinfo.ajax', function() {
            me.getSupportInfoData();
        });
    },

    onClickSaveNotification : function(btn) {
        var me = this,
        form = me.lookupReference('notificationdetails'),
        enableNotification = me.lookupReference("notificationFlagRef"),
        params = form.getForm().getValues();

        me.view.setLoading(true);
        if (form && form.getForm().isValid()) {
            delete params["tenantID"];
            params["smtpSecurityValue"] = params["smtpSecurity"];
            delete params["smtpSecurity"];
            if(enableNotification.getValue() == true) {
            	params["notificationFlag"] = 'on';	
            }            
            me.saveView(params, 'securedmaint/savenotification.ajax', function() {
                me.getNotificationData();
                me.view.setLoading(false);
            });
        }
    },

    onClickTestNotification : function(btn) {
        var me = this,
            form = me.lookupReference('notificationdetails');

        if (form && form.getForm().isValid()) {
            Ext.Ajax.request({
                url : 'testsmtpconfig.ajax',
                method : 'POST',
                params : form.getForm().getValues(),
                success : function(res) {
                    if (Ext.DomQuery.selectValue('message @success', res.responseXML) == "true") {
                        Ext.Msg.alert(l10n('success'), l10n('test-email-was-sent-successfully'));
                    } else {
                        Ext.Msg.alert(l10n('failure'), l10n('failed-to-send-test-email-please-verify-settings') + '<br/>' + Ext.DomQuery.selectValue('msg', res.responseXML));
                    }
                }
            });
        }
    },

    onClickSaveInviteText : function(btn) {
        var me = this,
        grid = me.lookupReference('dialInGrid'),
            form = me.lookupReference('invitetext'),
               viewModel = me.getViewModel(),
       gridStore = viewModel.getStore('DialInStore'),
            recs = gridStore,
            successCount = 0,
            recStr="",
            count=recs.getCount(),
            responseCount = 0,isGridValid=1;

     var gridParams = {};
     var gridObj={'dialInList':gridParams};
     if(count || grid.gridDirtyFlag){
           recs.each(function(rec, index) {
        	   if(!rec.get('countryID') ){
					
					isGridValid=0;
					return false ;
        	   }
            recStr += rec.get('countryID') + ":" + rec.get("dialInNumber") +":"+ rec.get("dialInLabel")  ;
            if ((count - 1) !== index) {
                recStr += "|";
            }
           
        });
    }
     if(!isGridValid){
			Ext.Msg
						.alert(
								l10n("error"),
								l10n("invalid-entry-in-the-grid"),
								function() {
								});

								return;

	}
        if (form && form.getForm().isValid()) {
            var params = form.getForm().getValues();

            if(count || grid.gridDirtyFlag){
                params["dialInNumbersGridChanged"] =true;
                params["dialInNumbers"] = recStr;
            }
            if (btn.type == "default") {
                params["followDefault"] = true;
                Ext.Msg.confirm(l10n('confirmation'), l10n('you-are-about-to-replace-customized-invite-text-with-default-content-continue'), function(res) {
                    if (res == "yes") {
                        me.saveView(params, 'saveinvitationsetting.ajax', function() {
                            me.getInviteTextData();
                        });
                    }
                });
                return;
            }
            else{
                var temp=params.invitationEmailContent;
               if( temp!=null && (temp.match(/\[ROOMLINK\]/g) || []).length>1){
                  Ext.Msg.alert(l10n("error"), l10n('roomlink-cannot-be-more-than-once'), function(){});
                  return; 
               }
            }
            me.saveView(params, 'saveinvitationsetting.ajax', function() {
                me.getInviteTextData();
            });
        }
    },

    onClickViewCustomizeLogo : function(btn) {
        var me = this,
            viewModel = me.getViewModel(),
            imageUrl =
            undefined;

        switch (btn.image) {
        case 'sp' :
            imageUrl = viewModel.getData()["splogonameOne"] + "?t=" + (new Date()).getTime();
            break;
        case 'up' :
            imageUrl = viewModel.getData()["uplogonameTwo"] + "?t=" + (new Date()).getTime();
            break;
        case 'vd' :
            imageUrl = viewModel.getData()["vdlogonameThree"] + "?t=" + (new Date()).getTime();
            break;
        }

        window.open(imageUrl, "_blank");
    },

    onClickUploadCustomizeLogo : function(btn) {
        var me = this,
            form = me.lookupReference(btn.formName),
            success = me.getSuperAdminLogoData;

        switch (btn.successUrl) {
        case "sp" :
            success = function() {
                me.getSuperAdminLogoData();
                form.getForm().isValid();
            };
            break;
        case "up" :
            success = function() {
                me.getPortalLogoData();
                form.getForm().isValid();
            };
            break;
        case "vd" :
            success = function() {
                me.getVidyoDesktopData();
                form.getForm().isValid();
            };
            break;
        }

        if (form) {
            form.getForm().submit({
                url : btn.ajaxUrl,
                waitMsg : 'saving',
                success : function(form, action) {
                    var xmlResponse = action.response.responseXML;
                    var issuccess = Ext.DomQuery.selectValue('message @success', xmlResponse);
                    if (issuccess == "false") {
                        var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                        Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                    } else {
                        Ext.Msg.alert(l10n("message"), l10n('the-customized-logo-image-is-successfully-saved'), function() {
                            Ext.Ajax.request({
                                url : btn.reloadUrl,
                                success : success
                            }, me);
                        });
                    }
                },
                failure : function(form, action) {

                }
            });
        }
    },

    onClickRemoveCustomizeLogo : function(btn) {
        var me = this,
            form = me.lookupReference(btn.formName),
            confirmMsg = "",
            success = function() {
            switch (btn.successUrl) {
            case "sp" :
                me.getSuperAdminLogoData();
                confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo-for-super-admin-portal');
                break;
            case "up" :
                me.getPortalLogoData();
                confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo-for-user-portal');
                break;
            case "vd" :
                me.getVidyoDesktopData();
                confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo');
                break;
            }
        };
        switch (btn.successUrl) {
        case "sp" :
            confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo-for-super-admin-portal');
            break;
        case "up" :
            confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo-for-user-portal');
            break;
        case "vd" :
            confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo');
            break;
        }
        Ext.Msg.confirm(l10n('confirmation'), confirmMsg, function(res) {
            if (res == "yes") {
                Ext.Ajax.request({
                    url : btn.removeUrl,
                    method : 'POST',
                    success : function() {
                        Ext.Msg.alert(l10n("message"), l10n('the-customized-logo-image-is-successfully-removed'), function() {
                        }, me);
                        success();
                    },
                    failure : function() {

                    }
                });
            }
        }, me);
    },

    onClickChangeLocationGP : function(btn) {
        var me = this,
            overlay = Ext.widget('guideuploadoverlay', {
            guideType : btn.guideType,
            comboValue : btn.comboValue,
            floatParent : me.view
        });

        if (overlay) {
            overlay.show();
        }
    },

    onChangeLocationGP : function(cb, newValue) {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();
        switch(cb.name) {
        case "vclocation" :
            Ext.Ajax.request({
                url : 'guidelocation.ajax',
                method : 'GET',
                params : {
                    langCode : newValue,
                    guideType : 'admin'
                },
                success : function(response) {
                    var result = response.responseXML,
                        btn = view.down('button[guideType=admin]');
                    viewModel.set("vConfrenceURL", response.responseText.trim());
                    btn.comboValue = newValue;
                }
            });
            break;
        case "vdlocation" :
            Ext.Ajax.request({
                url : 'guidelocation.ajax',
                method : 'GET',
                params : {
                    langCode : newValue,
                    guideType : 'desk'
                },
                success : function(response) {
                    var result = response.responseXML,
                        btn = view.down('button[guideType=desk]');
                    viewModel.set("vDesktopURL", response.responseText.trim());
                    btn.comboValue = newValue;
                }
            });
            break;
        }
    },

    onClickUploadGUOverlay : function(btn) {
        var me = this,
            view = me.lookupReference('guideuploadoverlay'),
            viewModel = me.getViewModel(),
            form = me.lookupReference('storeLocalForm');

        if (form && form.getForm().isValid()) {
            form.getForm().submit({
                url : 'securedmaint/uploadsupportfile.ajax',
                params : {
                    langCode : view.comboValue,
                    guideType : view.guideType
                },
                success : function(form, action) {
                    view.hide();
                    var xmlResponse = action.response.responseXML;

                    var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                    if (success == "false") {
                        var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                        Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                    } else {
                        Ext.Msg.alert(l10n('upload-status'), l10n('upload-complete'));
                        me.getGuideLocationData();
                    }

                },
                failure : function(form, action) {
                    var errors = '';
                    if ((action.result != null) && (!action.result.success)) {
                        for (var i = 0; i < action.result.errors.length; i++) {
                            errors += action.result.errors[i].msg + '<br>';
                        }
                    }
                    if (errors != '') {
                        Ext.Msg.alert(l10n('error'), '<br>' + errors, function() {
                        });
                    }
                }
            });
        }
    },

    onClickSaveGUOverlay : function(btn) {
        var me = this,
            view = me.lookupReference('guideuploadoverlay'),
            viewModel = me.getViewModel(),
            form = me.lookupReference('webServerForm');

        if (form && form.getForm().isValid()) {
            Ext.Ajax.request({
                url : 'securedmaint/updatesupportfileurl.ajax',
                method : 'POST',
                params : {
                    langCode : view.comboValue,
                    guideType : view.guideType,
                    urlValidated : me.getViewModel().get("validateUrl"),
                    url : form.getValues()["urlLocation"].trim()
                },
                waitTitle : l10n('url-update-progress'),
                waitMsg : l10n('url-is-being-updated'),
                success : function(res) {
                    if (Ext.DomQuery.selectValue('message @success', res.responseXML) == "true") {
                        view.hide();
                        Ext.Msg.alert(l10n('guide-properties'), l10n('guide-properties-url-changed'));
                        me.getGuideLocationData();
                    } else {
                        var errorId = Ext.DomQuery.selectValue('id', res.responseXML);
                        var errorMsg = Ext.DomQuery.selectValue('msg', res.responseXML);
                        Ext.MessageBox.confirm(errorId, errorMsg, function(btn) {
                            if (btn == 'yes') {
                                me.getViewModel().set("validateUrl", 'y');
                                me.onClickSaveGUOverlay();
                            } else {
                                if (typeof(parentView) != 'undefined' && (parentView != null)) parentView.setLoading(false);
                                view.setLoading(false);
                            }
                        });
                    }

                },
                failure : function(response) {
                    var errors = '';
                    if ((action.result != null) && (!action.result.success)) {
                        for (var i = 0; i < action.result.errors.length; i++) {
                            errors += action.result.errors[i].msg + '<br>';
                        }
                    }
                    if (errors != '') {
                        Ext.Msg.alert(l10n('error'), '<br>' + errors, function() {
                        });
                    }
                }
            });
        }
    },

    /***
     * @function onClickSaveBanners
     */
    onClickSaveBanners : function() {
        var me = this,
            form = me.lookupReference('banners');

        if (form) {
            me.saveView(form.getValues(), 'savebanners.ajax', function() {
                me.getBannersData();
            });
        }
    },

    /****** SAVE RESPONSE FUNCTIONS ****************/

    /***
     * @function saveAboutInfoAjax
     */

    saveView : function(params, url, callback) {
        Ext.Ajax.request({
            url : url,
            params : params,
            method : 'POST',
            success : function(res) {
                var xmlResponse = res.responseXML;
                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                if (success == "true") {
                    Ext.Msg.alert(l10n('message'), l10n('saved'));
                    callback();
                }else{
                       Ext.Msg.alert(l10n('error'), l10n('save-failed'));             
                }
            },
            failure : function(res) {
                 Ext.Msg.alert(l10n('error'), l10n('save-failed'));
            }
        });
    },

    /****** GET DATA FUNCTIONS ****************/

    /***
     * @function getAboutInfoData
     */
    getAboutInfoData : function() {
        var me = this,
            view = me.view;
        Ext.Ajax.request({
            url : 'aboutinfo.ajax',
            success : function(response) {
                var result = response.responseXML,
                    viewModel = view.getViewModel();

                viewModel.set("aboutInfo", Ext.DomQuery.selectValue('aboutInfo', result));
            }
        });
    },

    /***
     * @function getSupportInfoData
     */
    getSupportInfoData : function() {
        var me = this,
            view = me.view;
        Ext.Ajax.request({
            url : 'contactinfo.ajax',
            success : function(response) {
                var result = response.responseXML,
                    viewModel = view.getViewModel();

                viewModel.set("contactInfo", Ext.DomQuery.selectValue('contactInfo', result));
            }
        });
    },

    /***
     * @function getNotificationData
     */
    getNotificationData : function() {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();

        viewModel.getStore('notificationStore').load({
            callback : function(recs) {
                if (recs && recs.length) {
                    var rec = recs[0],
                    notificationView = view.down('notification'),
                    notificationFlag = rec.get('notificationFlag') == undefined ? false : rec.get('notificationFlag');
                    viewModel.set('notificationFlag', notificationFlag);
                    notificationView.down('form').getForm().loadRecord(rec);
                    notificationView.showNotificationForm(notificationFlag);
                }
            }
        });
    },

    /***
     * @function getInvitationData
     */
    getInviteTextData : function() {
        var me = this,
            view = me.view,
              viewModel = me.getViewModel();
 
        Ext.Ajax.request({
            url : 'invitationsetting.ajax',

            success : function(response) {
                var result = response.responseXML,
                    viewModel = view.getViewModel();

                viewModel.set("invitationEmailContent", Ext.DomQuery.selectValue('invitationEmailContent', result));
                viewModel.set("invitationEmailContentHtml", Ext.DomQuery.selectValue('invitationEmailContentHtml', result));
                viewModel.set("voiceOnlyContent", Ext.DomQuery.selectValue('voiceOnlyContent', result));
                viewModel.set("webcastContent", Ext.DomQuery.selectValue('webcastContent', result));
                viewModel.set("invitationEmailSubject", Ext.DomQuery.selectValue('invitationEmailSubject', result));
                me.onDialGridRender();
            }
        });
    },

    /***
     * @function getRoomLinknData
     */
    getRoomLinknData : function() {
        var me = this,
            view = me.view,
            roomLinkFormatGroup = me.lookupReference('roomLinkFormatGroup');
        
        Ext.Ajax.request({
            url : 'roomlinksetting.ajax',

            success : function(response) {
                var result = response.responseXML,
                    viewModel = view.getViewModel();
                
                me.lookupReference('roomKeyLength').setValue(Ext.DomQuery.selectValue('roomKeyLength', result).trim());
                viewModel.set('roomKeyLength', Ext.DomQuery.selectValue('roomKeyLength', result).trim());
                viewModel.set('roomLinkFormatVal', Ext.DomQuery.selectValue('roomLinkFormat', result).trim());
                roomLinkFormatGroup.setValue({roomLinkFormatGroup : viewModel.get('roomLinkFormatVal')});
            }
        });
    },
    
    resetRoomLinkForm : function() {
    	this.getView().getForm().reset();
    },

    /***
     * @function onClickRoomLinkSave
     * @param {Object} btn
     */
    onClickRoomLinkSave : function(btn) {
        var me = this,
            view = me.view,
            form = btn.up('form'),
            values = form.getValues();


        if (form && form.getForm().isValid()) {
	        Ext.Msg.confirm(l10n('confirmation'), l10n('settings-customization-room-link-save-confirmation'), function(res) {
	            if (res == "yes") {
	                Ext.Ajax.request({
	                    url : 'saveroomlink.ajax',
	                    method : 'POST',
	                    params : values,
	                    success : function(res) {
	                        var xmlResponse = res.responseXML,
	                            success = Ext.DomQuery.selectValue('message @success', xmlResponse);
	                        if (success == "true") {
	                            Ext.Msg.alert(l10n('message'), l10n('settings-customization-room-link-saved'));
	                        } else {
	                        	Ext.Msg.alert(l10n('error'), l10n('settings-customization-room-link-save-error'));
	                        }
	                    }
	                });
	            }
	        });
        } else {
        	Ext.Msg.alert(l10n('error'), l10n('required-values-missing'));
        	return;
        }
        
    },

    getSuperAdminLogoData : function() {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.getStore('AdminLogoStore').load({
            callback : function(recs) {
                if (recs && recs.length) {
                    var rec = recs[0];
                    viewModel.set("splogonameOne", rec.get('splogoname') == undefined ? '' : rec.get('splogoname'));
                    if (rec.get('splogoname')) {
                        viewModel.set('isSPBtnDisabled', false);
                    } else {
                        viewModel.set('isSPBtnDisabled', true);
                    }
                }
            }
        });
    },

    getPortalLogoData : function() {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.getStore('PortalLogoStore').load({
            callback : function(recs) {
                if (recs && recs.length) {
                    var rec = recs[0];
                    viewModel.set("uplogonameTwo", rec.get('uplogoname') == undefined ? '' : rec.get('uplogoname'));
                    if (rec.get('uplogoname')) {
                        viewModel.set('isUPBtnDisabled', false);
                    } else {
                        viewModel.set('isUPBtnDisabled', true);
                    }
                }
            }
        });
    },

    getVidyoDesktopData : function() {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();

        viewModel.getStore('vidyoDesktopStore').load({
            callback : function(recs) {
                if (recs && recs.length) {
                    var rec = recs[0];
                    viewModel.set("vdlogonameThree", rec.get('vdlogoname') == undefined ? '' : rec.get('vdlogoname'));
                    if (rec.get('vdlogoname')) {
                        viewModel.set('isVDBtnDisabled', false);
                    } else {
                        viewModel.set('isVDBtnDisabled', true);
                    }
                }
            }
        });
    },

    getCustomizeLogosData : function() {
        var me = this,
            view = me.view;
        this.lookupReference("SPLogoArchiveForm").getForm().reset();
        this.lookupReference("VDLogoArchiveForm").getForm().reset();
        me.getSuperAdminLogoData();
        me.getPortalLogoData();
        me.getVidyoDesktopData();
    },

    getGuideLocationData : function() {
        var me = this,
            view = me.view,
            viewModel = view.getViewModel();

        viewModel.getStore('sysLocation').load({
            callback : function(rec) {
                var rec = rec[0];
                setTimeout(function() {
                    me.lookupReference('cGuidelocation').select(rec);
                }, 100);
            }
        });
        Ext.Ajax.request({
            url : 'guidelocation.ajax',
            method : 'GET',
            params : {
                langCode : 'en',
                guideType : 'admin'
            },
            success : function(response) {
                var result = response.responseXML;
                viewModel.set("vConfrenceURL", response.responseText.trim());
            }
        });
        Ext.Ajax.request({
            url : 'guidelocation.ajax',
            method : 'GET',
            params : {
                langCode : 'en',
                guideType : 'desk'
            },
            success : function(response) {
                var result = response.responseXML;
                viewModel.set("vDesktopURL", response.responseText.trim());
            }
        });
    },

    getBannersData : function() {
        var me = this,
            view = me.view;

        Ext.Ajax.request({
            url : 'banners.ajax',
            success : function(response) {
                var result = response.responseXML,
                    viewModel = view.getViewModel(),
                    showLogin = Ext.DomQuery.selectValue('showLoginBanner', result).trim() == "true" ? true : false;
                showWelcome = Ext.DomQuery.selectValue('showWelcomeBanner', result).trim() == "true" ? true : false;

                me.lookupReference('showLoginBanner').setValue(showLogin);
                me.lookupReference('showWelcomeBanner').setValue(showWelcome);
                viewModel.set("loginBanner", Ext.DomQuery.selectValue('loginBanner', result));
                viewModel.set("welcomeBanner", Ext.DomQuery.selectValue('welcomeBanner', result));
            }
        });
    },

    onClickDefaultNotification : function(btn) {
        var me = this,
        form = me.lookupReference('notificationdetails');

        if (form) {
            form.getForm().reset();
            form.down('textfield[name=smtpUsername]').setValue('');
            form.down('textfield[name=smtpPassword]').setValue('');
            form.down('combo[name=smtpSecurity]').setValue('NONE');
            form.down('textfield[name=smtpPort]').setValue('25');
            form.down('textfield[name=smtpHost]').setValue('localhost');
        }
    },
    loginDefault : function() {
        var scope = this;
        Ext.MessageBox.confirm(l10n('confirmation'), l10n('alert-replacing-customized-value-with-default-continue'), function showResult(btn) {
            if (btn == "yes") {
                scope.lookupReference('loginBanner').setValue('');
            }
        });
    },
    welcomeDefault : function() {
        var scope = this;
        Ext.MessageBox.confirm(l10n('confirmation'), l10n('alert-replacing-customized-value-with-default-continue'), function showResult(btn) {
            if (btn == "yes") {
                scope.lookupReference('welcomeBanner').setValue('');
            }
        });
    },

    onClickResetNotification : function() {
        this.getNotificationData();
    },
    
    onChangeEmailNotify : function(cb, checked) {
        var me = this;
        var form = me.lookupReference('notificationdetails');
        var fieldset = cb.ownerCt;
        if(checked) {
            Ext.Array.forEach(fieldset.query('textfield'), function(field) {
                field.setDisabled(checked);
                field.el.animate({opacity: checked ? 0.3 : 1});
            });
            fieldset.queryById('smtpTrustAllCerts').setDisabled(!checked);
        }
    },

    onClickUploadCustomizeDesktopEndpoint : function(btn) {
        var me = this,
            form = me.lookupReference(btn.formName);

        if (form) {
            form.getForm().submit({
                url : btn.ajaxUrl,
                waitMsg : 'saving',
                success : function(form, action) {
                    var xmlResponse = action.response.responseXML;
                    var issuccess = Ext.DomQuery.selectValue('message @success', xmlResponse);
                    if (issuccess == "false") {
                        var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                        Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                            me.getSuperCustomizeEndpointData();
                    } else {
                        Ext.Msg.alert(l10n("message"), "Upload succeeded.", function () {
                            me.getSuperCustomizeEndpointData();
                        })
                    }
                },
                failure : function(form, action) {
                    if (action === undefined || action.response === undefined) {
                        Ext.Msg.alert(l10n("message"), "Please choose a file for upload.");
                        return;
                    }
                    var xmlResponse = action.response.responseXML;
                    var issuccess = Ext.DomQuery.selectValue('message @success', xmlResponse);
                    if (issuccess == "false") {
                        var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                        Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                        me.getAdminCustomizeEndpointData();
                    } else {
                        Ext.Msg.alert(l10n("message"), "Upload failed.", function () {
                            me.getSuperCustomizeEndpointData();
                        })
                    }
                }
            });
        }
    },

    getSuperCustomizeEndpointData : function() {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.getStore('SuperCustomizeEndpointStore').load({
            callback : function(recs) {
                if (recs && recs.length) {
                    var rec = recs[0];
                    if (rec.get('superCustomizeDesktopExists') == "true") {
                        viewModel.set('isNeoDesktopCustomization', true);
                    } else {
                        viewModel.set('isNeoDesktopCustomization', false);
                    }
                    if (rec.get('allowTenantOverride') == "1") {
                        viewModel.set('allowTenantOverride', "1");
                    } else {
                        viewModel.set('allowTenantOverride', "0");
                    }
                }
            }
        });
    },

    onClickDownloadCustomizeDesktopEndpoint : function() {
        window.open("downloadSuperDesktopCustomization.html", "_blank");
    },

    onClickRemoveCustomizeDesktopEndpoint : function (btn) {
        var me = this,
            form = me.lookupReference(btn.formName);
        Ext.Ajax.request({
            url : 'removedesktopcustomization.ajax',
            success : function(response) {
                var xmlResponse = response.responseXML;
                var issuccess = Ext.DomQuery.selectValue('message @success', xmlResponse);
                if (issuccess == "false") {
                    var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                    var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                    Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                } else {
                    Ext.Msg.alert(l10n("message"), "Remove succeeded.", function() {
                        me.getSuperCustomizeEndpointData();
                    });
                }
            },
            failure : function(form, action) {

            }
        });
    },

    onClickSaveCustomizeDesktopEndpointOverride : function(btn) {
        var me = this,
            form = me.lookupReference(btn.formName);

        if (form) {
            form.getForm().submit({
                url : btn.ajaxUrl,
                waitMsg : 'saving',
                success : function(form, action) {
                    var xmlResponse = action.response.responseXML;
                    var issuccess = Ext.DomQuery.selectValue('message @success', xmlResponse);
                    if (issuccess == "false") {
                        var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                        Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                    } else {
                        Ext.Msg.alert(l10n("message"), "Save succeeded.", function () {
                            me.getSuperCustomizeEndpointData();
                        })
                    }
                },
                failure : function(form, action) {

                }
            });
        }

    },
      onDialGridRender: function () {
        var me = this,
        viewModel = me.getViewModel();
        var store = viewModel.getStore('DialInStore');
     
        store.load();
        
    },
     onDialGridChange: function ( view , newData) {
       var grid = this.lookupReference('dialInGrid');
       grid.getView().refresh();
    },
    
    onAddClick: function(){
        // Create a model instance
         var me = this,
         viewModel = me.getViewModel(),
         grid = me.lookupReference('dialInGrid'),
         record = grid.getSelection()[0],
         gridEditPlugin = grid.getPlugins()[0],
         gridStore = grid.getStore();
         console.log('gridStore');
  gridStore.each(function(record) {
                        console.log(record.get('countryName'));
                    }   , this);  
        var rec = new SuperApp.model.settings.DialNo({
            countryID: '',
            name:'',
            phoneCode:'',
            dialInNumber: '',
            dialInLabel:''
           
        });
        gridStore.insert(0, rec);
        gridEditPlugin.startEdit(rec,0);
       },

    onRemoveClick: function(grid, rowIndex){
        var grid = this.lookupReference('dialInGrid'),
             record = grid.getSelection()[0],
         gridEditPlugin = grid.getPlugins()[0],
            gridStore = grid.getStore();
           if(record){
             gridStore.remove(record);
             grid.gridDirtyFlag=true;
           }


    }

});


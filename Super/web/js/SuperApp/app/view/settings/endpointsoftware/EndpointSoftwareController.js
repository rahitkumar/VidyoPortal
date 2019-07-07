Ext.define('SuperApp.view.settings.endpointsoftware.EndpointSoftwareController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.EndpointSoftwareController',

    validateUESActivate : function() {
        var scope = this;
        var selections = this.lookupReference("uesGrid").getSelectionModel().getSelection();
        if (selections.length != 1) {
            Ext.MessageBox.alert(l10n('error'), l10n('please-select-only-one-item'));
            return;
        }
        Ext.MessageBox.confirm(l10n('confirmation'), l10n('do-you-really-want-to-set-acive-the-selected-file'), scope.activateUESRecord, scope);
    },
    validateUESDelete : function() {
        var scope = this;
        var selections = this.lookupReference("uesGrid").getSelectionModel().getSelection();
        if (selections.length != 1) {
            Ext.MessageBox.alert(l10n('error'), l10n('please-select-only-one-item'));
            return;
        } 
        Ext.MessageBox.confirm(l10n('confirmation'), l10n('do-you-really-want-to-delete-the-selected-file'), scope.deleteUESRecord, scope);
    },
    actionUESRecord : function(urlAction, btn) {
        var scope = this;
        if (btn == 'yes') {
            var selections = this.lookupReference("uesGrid").getSelectionModel().getSelection();
            Ext.Array.each(selections, function(record)  {
                Ext.Ajax.request({
                    url : urlAction,
                    success : function() {
                        scope.getEndPointSoftwareData();
                    },
                    params : {
                        endpointUploadID : record.get("endpointUploadID")
                    }
                });
            });
        }
    },
    activateUESRecord : function(btn) {
    	var scope = this;
    	scope.actionUESRecord('securedmaint/activateupload.ajax',btn);
    },
    deleteUESRecord : function(btn) {
    	var scope = this;
    	scope.actionUESRecord('securedmaint/deleteupload.ajax',btn);
    },
    uesUploadBtn : function() {
        var scope = this;
        var uploadFileName = this.lookupReference("uesFileUploadField").getValue();
        uploadFileName = uploadFileName.replace(/^.*(\\|\/|\:)/, '');
        Ext.Ajax.request({
            url : 'checkendpointexistence.ajax',
            method : 'GET',
            params : {
                endpointFileName : uploadFileName
            },
            success : function(response, options) {
                var xml = response.responseXML;
                var endpointExist = Ext.DomQuery.selectValue("endpointexist", xml).trim().toLowerCase();
                if (endpointExist == "false") {
                	scope.saveUpload();
                	return;
                }
                Ext.MessageBox.show({
                    title : l10n('confirmation'),
                    msg : l10n('client-installer-exists-replace'),
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.QUESTION,
                    fn : function(btn) {
                        if (btn == 'ok') {
                            scope.saveUpload();
                        }
                    }
                });
            },
            failure : function(form, action) {
                Ext.Msg.alert(l10n('error'), l10n('save-failed'));
            }
        });
    },
    saveUpload : function() {
        var scope = this;
        scope.lookupReference("UESUploadForm").getForm().submit({
            url : 'securedmaint/saveupload.ajax',
            params : {
                _csrf : csrfToken
            },
            waitMsg : l10n('saving'),
            success : function(form, action) {

                var xmlResponse = action.response.responseXML;
                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);

                if (success == "false") {
                    var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                    Ext.Msg.alert(l10n('save-failed'), responseMsg.textContent);
                } else {
                    scope.getEndPointSoftwareData();
                    scope.lookupReference("UESUploadForm").reset();
                }

            },
            failure : function(form, action) {
                Ext.Msg.alert(l10n("error"), l10n('file-upload-failed'));
            }
        });

    },
    
    getEndPointSoftwareData : function() {
        var me = this,
            store = me.getViewModel().getStore("vidyoPortalEndpointSoftwareStore"),
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
    
    getFileServerData : function() {
    	var scope = this;
    	var store = scope.getViewModel().getStore('endpointUploadModeStore');
    	store.removeAll();
    	store.load({
            callback : function(recs) {
            	scope.getViewModel().set('uploadMode', recs != null ? recs[0].get('uploadMode') : 'VidyoPortal');
            }
        });
    },
    
    onClickFileServerSave : function() {
    	var scope = this;
        var form = scope.lookupReference("fileServerForm").getForm();
        params = form.getValues();
    	 if (form.isValid()) {
    		 Ext.Ajax.request({
	            url : 'saveuploadmode.ajax',
	            params : params,
	            waitMsg : l10n('saving'),
	            method : 'POST',
	            success : function(res, params) {
	
	            	var xmlResponse = res.responseXML;
                    var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
	
	                if (success == "false") {
	                    var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
	                    Ext.Msg.alert(l10n('save-failed'), l10n('fileserver-save-error'));
	                } else {
	                	Ext.Msg.alert(l10n('success'), l10n('fileserver-saved'));
	                    scope.getFileServerData();
	                    scope.lookupReference("fileServerForm").reset();
	                }
	
	            },
	            failure : function() {
	                Ext.Msg.alert(l10n("error"), l10n('fileserver-save-error'));
	            }
	        });
       }
    },
    
    externalCDNSaveBtn : function() {
    	var scope = this;
        var externalURLForm = this.lookupReference("ExternalCDNUploadForm").getForm();
        if (externalURLForm.isValid()){
        	var endpointType = this.lookupReference("endpointUploadType").getValue();
        	var endpointFile = this.lookupReference("endpointUploadFile").getValue();
        	var endpointVersion = this.lookupReference("endpointUploadVersion").getValue();
        		 Ext.Ajax.request({
        	            url : 'checkendpointexistence.ajax',
        	            method : 'GET',
        	            params : {
        	                endpointFileName : endpointFile,
        	                endpointUploadType : endpointType,
        	                endpointUploadVersion : endpointVersion
        	            },
        	            success : function(response, options) {
    	                    var xml = response.responseXML;
    	                    var endpointExist = Ext.DomQuery.selectValue("endpointexist", xml).trim().toLowerCase();
    	                    if (endpointExist == "false") {
    	                    	scope.saveExternalCDNURL();
    	                    	return;
    	                    }
	                        Ext.MessageBox.show({
	                            title : l10n('confirmation'),
	                            msg : l10n('client-installer-exists-replace'),
	                            buttons : Ext.Msg.OKCANCEL,
	                            icon : Ext.MessageBox.QUESTION,
	                            fn : function(btn) {
	                                if (btn == 'ok') {
	                                    scope.saveExternalCDNURL();
	                                }
	                            }
	                        });
        	            },
        	            failure : function() {
        	            	Ext.Msg.alert(l10n("error"), l10n('save-failed'));
        	            }
        	        });

        }
    },
    saveExternalCDNURL : function() {
        var scope = this;
        scope.lookupReference("ExternalCDNUploadForm").getForm().submit({
            url : 'securedmaint/saveupload.ajax',
            params : {
                _csrf : csrfToken
            },
            waitMsg : l10n('saving'),
            success : function(form, action) {

                var xmlResponse = action.response.responseXML;
                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);

                if (success == "false") {
                    var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                    Ext.Msg.alert(l10n('save-failed'), responseMsg.textContent);
                } else {
                    scope.getEndPointSoftwareData();
                    scope.lookupReference("ExternalCDNUploadForm").reset();
                }

            },
            failure : function(form, action) {
                Ext.Msg.alert(l10n("error"), l10n('external-url-save-failed'));
            }
        });

    },
    
    activateCDNRecord : function(btn) {
    	var scope = this;
    	scope.actionCDNRecord('securedmaint/activateupload.ajax', btn);
    },

    deleteCDNRecord : function(btn) {
    	var scope = this;
    	scope.actionCDNRecord('securedmaint/deleteupload.ajax', btn);
    },
    
    actionCDNRecord : function(urlAction, btn) {
    	var scope = this;
        if (btn == 'yes') {
            var selections = this.lookupReference("externalCDNGrid").getSelectionModel().getSelection();
            Ext.Array.each(selections, function(record) {
                Ext.Ajax.request({
                    url : urlAction,
                    success : function() {
                        scope.getEndPointSoftwareData();
                    },
                    params : {
                        endpointUploadID : record.get("endpointUploadID")
                    }
                });
            });
        }
    },
    
    validateCDNDeleteRecord : function() {
    	var scope = this;
        var selections = this.lookupReference("externalCDNGrid").getSelectionModel().getSelection();
        if (selections.length != 1) {
            Ext.MessageBox.alert(l10n('error'), l10n('please-select-only-one-url'));
            return;
        }
        Ext.MessageBox.confirm(l10n('confirmation'), l10n('do-you-really-want-to-delete-the-selected-url'), scope.deleteCDNRecord, scope);
    },
    
    validateCDNActivateRecord : function() {
    	var scope = this;
        var selections = this.lookupReference("externalCDNGrid").getSelectionModel().getSelection();
        if (selections.length != 1) {
            Ext.MessageBox.alert(l10n('error'), l10n('please-select-only-one-url'));
            return;
        }
        Ext.MessageBox.confirm(l10n('confirmation'), l10n('do-you-really-want-to-set-active-the-selected-external-url'), scope.activateCDNRecord, scope);
    },
    
    platformComboLoad : function() {
    	var platformComboStore = this.getViewModel().getStore('platformStore');
    	platformComboStore.load();
    }
});
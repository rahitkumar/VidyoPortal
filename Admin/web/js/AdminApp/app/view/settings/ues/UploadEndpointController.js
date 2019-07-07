/**
 * @class UploadEndpointController
 */
Ext.define('AdminApp.view.settings.ues.UploadEndpointController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.UploadEndpointController',

    getEndPointSoftwareData : function() {
        var me = this,
            viewModel = me.getViewModel(),
            store =viewModel.getStore('uploadEndPointStore');
        store.removeAll();
        store.load({
            callback : function(recs) {
                if(store.getCount()) {
                    viewModel.set('hideGrid',false);
                    return;
                }
                viewModel.set('hideGrid',true);
            }
        });
    },
    uesUploadBtnClick : function() {
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
                	scope.onUploadEndPointSoftware();
                	return;
                }
                Ext.MessageBox.show({
                    title : 'Confirmation',
                    msg : 'Client installer already exists. Do you want to replace it?',
                    buttons : Ext.Msg.OKCANCEL,
                    icon : Ext.MessageBox.QUESTION,
                    fn : function(btn) {
                        if (btn == 'ok') {
                        	scope.onUploadEndPointSoftware();
                        }
                    }
                });
            },
            failure : function(form, action) {
                Ext.Msg.alert(l10n('error'), l10n('file-upload-failed'));
            }
        });
    },

    onUploadEndPointSoftware : function() {
        var me = this,
            form = me.lookupReference("UESUploadForm");

        if (form && form.getForm().isValid()) {
            me.uploadEndpointSoftware(form);
        }
    },

    uploadEndpointSoftware : function(form) {
        var me = this,
            viewModel = me.getViewModel();

        if (form) {
            form.getForm().submit({
                url : 'securedmaint/saveupload.ajax',
                params: {_csrf:csrfToken},
                waitMsg : 'Saving',
                success : function(form, action) {

                    var xmlResponse = action.response.responseXML;
                    var success = Ext.DomQuery.selectValue('message @success', xmlResponse);

                    if (success == "false") {
                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                        Ext.Msg.alert(l10n('save-failed'), responseMsg.textContent);
                    } else {
                        me.getEndPointSoftwareData();
                        form.reset();
                    }

                },
                failure : function(form, action) {
                    Ext.Msg.alert(l10n('error'), l10n('file-upload-failed'));
                }
            });
        }
    },

    onDeleteEndPoint : function() {
        var me = this,
            grid = me.lookupReference("endpointsoftgrid"),
            selections = grid.getSelectionModel().getSelection();
            
        if (selections.length != 1) {
            Ext.MessageBox.alert(l10n('error'), l10n('please-select-only-one-item'));
            return
        } 
        Ext.MessageBox.confirm(l10n('confirmation'), l10n('do-you-really-want-to-delete-the-selected-file'), function(res) {
            if (res == 'yes') {
                me.callEndPointAjax('securedmaint/deleteupload.ajax', selections);
            }
        });
    },

    callEndPointAjax : function(url, selection) {
        var me = this;
        Ext.Array.each(selection, function(rec) {
            Ext.Ajax.request({
                url : url,
                method : 'POST',
                scope : me,
                params : {
                    endpointUploadID : rec.get("endpointUploadID")
                },
                success : function() {
                    me.getEndPointSoftwareData();
                },
                failure : function(form, action) {
                    Ext.Msg.alert(l10n('error'), l10n('save-failed'));
                }
            });
        });
    },

    onActivateEndPoint : function() {
        var me = this,
            selections = this.lookupReference("endpointsoftgrid").getSelectionModel().getSelection();

        if (selections.length != 1) {
            Ext.MessageBox.alert(l10n('error'), l10n('please-select-only-one-item'));
            return;
        }
        Ext.MessageBox.confirm(l10n('confirmation'), l10n('do-you-really-want-to-set-acive-the-selected-file'), function(res) {
            if (res == 'yes') {
                me.callEndPointAjax('securedmaint/activateupload.ajax', selections);
            }
        });
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
                        failure : function(form, action) {
                            Ext.Msg.alert(l10n('error'), l10n('save-failed'));
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
            Ext.Array.each(selections, function (record) {
                Ext.Ajax.request({
                    url : urlAction,
                    success : function() {
                    	scope.getEndPointSoftwareData();
                    },
                    params : {
                        endpointUploadID : record.get("endpointUploadID")
                    },
                    failure : function(form, action) {
                        Ext.Msg.alert(l10n('error'), l10n('external-cdn-url-action-failed'));
                    }
                });
            });
        }
    },
    validateCDNDelete : function() {
    	var scope = this;
        var selections = this.lookupReference("externalCDNGrid").getSelectionModel().getSelection();
        if (selections.length != 1) {
            Ext.MessageBox.alert(l10n('error'), l10n('please-select-only-one-url'));
            return;
        }
        Ext.MessageBox.confirm(l10n('confirmation'), l10n('do-you-really-want-to-delete-the-selected-url'), scope.deleteCDNRecord, scope);
    },
    
    validatCDNActivate : function() {
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

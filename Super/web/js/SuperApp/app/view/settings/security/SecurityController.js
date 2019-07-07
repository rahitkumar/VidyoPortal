Ext.define('SuperApp.view.settings.security.SecurityController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.SecurityController',

    getSecurityViewData : function() {
        var me = this,
            viewModel = me.getViewModel(),
            httpsOnly = me.lookupReference('httpsonly');

        Ext.Ajax.request({
            url : 'security.html',
            success : function(response) {
                var result = response.responseXML;

                viewModel.set("ocspEnabledFlag", Ext.DomQuery.selectValue('ocspEnabledFlag', result) == "true" ? "on" : "off");
                viewModel.set("ocspOverrideFlag", Ext.DomQuery.selectValue('ocspOverrideFlag', result) == "true" ? "on" : "off");
                viewModel.set("ocspTextflag", Ext.DomQuery.selectValue('ocspOverrideFlag', result) == "true" ? false : true);
                viewModel.set("ocspDefaultResponder", Ext.DomQuery.selectValue('ocspDefaultResponder', result) == undefined ? '' : Ext.DomQuery.selectValue('ocspDefaultResponder', result));
                viewModel.set('useDefaultRootCerts', Ext.DomQuery.selectValue('useDefaultRootCerts', result) == "true" ? true : false);
                viewModel.set('defaultRoot', Ext.DomQuery.selectValue('useDefaultRootCerts', result) == "true" ? "on" : "off");
                viewModel.set('guideLoc', Ext.DomQuery.selectValue('guideLoc', result));
                viewModel.set('sslEnabledFlag', Ext.DomQuery.selectValue('sslEnabledFlag', result) == "0" ? true : false);
                viewModel.set('sslOptionsFlag', Ext.DomQuery.selectValue('sslEnabledFlag', result) == "0" ? false : true);
                var isSSLEnabled = Ext.DomQuery.selectValue('sslEnabledFlag', result) == "1" ? true : false;
                var isEncryptionEnabledFeature = Ext.DomQuery.selectValue('encryptionEnabledFlag', result) == "true" ;
                if(!isSSLEnabled) {
                	// button disabled, label: enable encryption
                    viewModel.set('isEncryption', true);
                    viewModel.set('encryption', l10n('enable-encryption'));
                } 
                if(isSSLEnabled && !isEncryptionEnabledFeature) {
                	//button enabled, label: enable encryption
                    viewModel.set('isEncryption', false);
                    viewModel.set('encryption',  l10n('enable-encryption'));
                } 
                if(isSSLEnabled && isEncryptionEnabledFeature){
                	//button enabled, label: disable encryption
                	viewModel.set('isEncryption', false);
                    viewModel.set('encryption',  l10n('disable-encryption'));
                }
                viewModel.set('sslText', Ext.DomQuery.selectValue('sslEnabledFlag', result) == "0" ? l10n('super-security-ssl-enable') : l10n('super-security-ssl-disable'));
                viewModel.set('httpsOnlyFlagNoRedirect', Ext.DomQuery.selectValue('httpsOnlyFlagNoRedirect', result) == "0" ? false : true);

                viewModel.set('httpsOnlyFlag', Ext.DomQuery.selectValue('httpsOnlyFlag', result) == "0" ? false : true);
                viewModel.set('redirectHttp', Ext.DomQuery.selectValue('httpsOnlyFlag', result) == "0" ? true : false);
             //   viewModel.set('apacheVersion', Ext.DomQuery.selectValue('apacheVersion', result));
                viewModel.set('ocspSupportFlag', Ext.DomQuery.selectValue('ocspSupportFlag', result) == "0" ? false : true);
                if (!viewModel.get('httpsOnlyFlag')) {
                    httpsOnly.state = 2;
                }
            }
        });
    },

    isEncryptionEnable : function() {
        var me = this,
            parentView = me.view.up('settingsMainView'),
            parentViewModel = parentView.getViewModel(),
            viewModel = me.getViewModel(),
            isEncryption = parentViewModel.get('isEncryption');

        return !isEncryption && viewModel.get('sslOptionsFlag');
    },

    getSecurityPkData : function() {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.set('isPasswordView', false);
        Ext.Ajax.request({
            url : 'security/security_key.ajax',
            success : function(response) {
                var result = response.responseXML;
                var keyHash = Ext.DomQuery.selectValue('keyHash', result),
                    bits = Ext.DomQuery.selectValue('bits', result);
                viewModel.set("KeyInfoSize", bits);
                viewModel.set("PrivateHashKey", keyHash);
            }
        });
    },

    onFocusHashKey : function(txtarea) {
        var me = this,
            form = txtarea.up('form');

        //if (!me.getViewModel().getData()["sslOptionsFlag"]) {
            form.down('#reset').enable();
            form.down('#apply').enable();
            form.down('textareafield').setReadOnly(false);
            form.down('textareafield').setEditable(true);
        //}
    },

    /***
     * @function onClickRegenerate
     * @param {Object} btn
     */
    onClickRegenerate : function(btn) {
        var me = this,
            form = btn.up('form'),
            params = {
            bits : me.getViewModel().get("KeyInfoSize")
        };

        Ext.Msg.confirm(l10n('confirmation'), l10n('super-security-ssl-key-are-you-sure-you-want-to-generate-a-new-private-key-this-will-require-new-ssl-csr-and-server-certificates'), function(res) {
            if (res == 'yes') {
                Ext.Ajax.request({
                    url : 'security/security_generate_key.ajax',
                    method : 'POST',
                    params : params,
                    success : function(res) {
                        var xmlResponse = res.responseXML;
                        var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                        if (success == "true") {
                            me.getSecurityPkData();
                        } else {
                            Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', result));
                        }
                    }
                });
            }
        });

    },

    onClikcUpload : function(btn) {
        var me = this,
            form = btn.up('form'),
            overlay = Ext.widget('fileupload', {
            floatParent : me.view
        });

        overlay.show();
    },

    onClikcUploadServerCert : function(btn) {
        var me = this,
            form = btn.up('form'),
            overlay = Ext.widget('serverfileupload', {
            floatParent : me.view
        });

        overlay.show();
    },

    uploadServerCertificateFile : function(btn) {
        var me = this,
            overlay = btn.up('window'),
            viewModel = me.getViewModel(),
            form = overlay.down('form'),
            callback = function() {
            me.onClickResetServerCert();
        };

        if (overlay && form.getForm().isValid()) {
            form.getForm().submit({
                url : 'security/security_upload_file.ajax',
                method : 'POST',
                scope : me,
                params : {
                    uploadType : "cert"
                },
                waitTitle : 'uploading file',
                waitMsg : l10n('your-file-is-being-uploaded'),
                success : function(form, action) {
                    var result = action.response.responseXML;
                    overlay.hide();
                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                        Ext.Msg.alert(l10n('success'), l10n('server-certificate-applied'), function() {
                            callback();
                        });
                    } else {
                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
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
                    } else {
                        Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
                        });
                    }
                }
            });
        }
    },

    uploadKeyFile : function(btn) {
        var me = this,
            overlay = me.lookupReference('fileupload'),
            viewModel = me.getViewModel(),
            form = overlay.down('form');

        if (overlay && form.getForm().isValid()) {
            form.getForm().submit({
                url : 'security/security_upload_file.ajax',
                method : 'POST',
                params : {
                    uploadType : "key"
                },
                waitTitle : 'uploading file',
                waitMsg : l10n('your-file-is-being-uploaded'),
                success : function(frm, action) {
                    var result = action.response.responseXML;
                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                        overlay.hide();
                        me.getSecurityPkData();
                    } else {
                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                    }
                }
            });
        }
    },

    /***
     * @function onClickCSRReset
     * @param {Object} btn
     */
    onClickCSRReset : function(btn) {
        var me = this,
            viewModel = me.getViewModel(),
            form = me.lookupReference('securitycsr');

        if (form) {
            Ext.Ajax.request({
                url : 'security/security_csr.ajax',
                success : function(response) {
                    var result = response.responseXML;

                    viewModel.set("country", Ext.DomQuery.selectValue('country', result));
                    viewModel.set("state", Ext.DomQuery.selectValue('state', result));
                    viewModel.set("city", Ext.DomQuery.selectValue('city', result));
                    viewModel.set("company", Ext.DomQuery.selectValue('company', result));
                    viewModel.set("division", Ext.DomQuery.selectValue('division', result));
                    viewModel.set("domain", Ext.DomQuery.selectValue('domain', result));
                    viewModel.set("email", Ext.DomQuery.selectValue('email', result));
                    viewModel.set("csr", Ext.DomQuery.selectValue('csr', result));
                }
            });
        }
    },

    /***
     * @function  onClickSCRRegenerate
     * @param {Object} btn
     */
    onClickSCRRegenerate : function(btn) {
        var me = this,
            form = me.lookupReference('securitycsr'),
            viewModel = me.getViewModel(),
            params = form.getForm().getValues();
        delete params["csr"];

        if (form && form.getForm().isValid()) {
            Ext.Ajax.request({
                url : 'security/security_generate_csr.ajax',
                params : params,
                timeout : 120000,
                method : 'POST',
                success : function(response) {
                    var result = response.responseXML;
                    var success = Ext.DomQuery.selectValue('message @success', result);

                    if (success == "true") {
                        Ext.Msg.alert(l10n('success'), l10n('csr-generated'), function() {
                            me.onClickCSRReset();
                        });
                    } else {
                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                    }
                }
            });
        }
    },

    /***
     * @function onClickServerCertRegenerateSS
     * @param {Object} btn
     */
    onClickServerCertRegenerateSS : function(btn) {
        var me = this,
            viewModel = me.getViewModel(),
            form = me.lookupReference('servercertificate');

        Ext.Msg.confirm(l10n('confirmation'), l10n('super-security-ssl-cert-are-you-sure-you-want-to-generate-a-self-signed-certiciate-this-will-remove-any-current-server-certificate'), function(res) {
            if (res == 'yes') {
                form.setLoading(true);
                Ext.Ajax.request({
                    url : 'security/security_generate_cert.ajax',
                    method: "POST",
                    timeout : 180000,
                    success : function(res) {
                        var result = res.responseXML;
                        var success = Ext.DomQuery.selectValue('message @success', result);

                        if (success == "true") {
                            form.setLoading(false);
                            if (location.protocol == 'https:') {
                                Ext.Msg.alert(l10n('success'), l10n('generated-server-certificate'), function() {
                                    me.onClickResetServerCert();
                                });
                            } else {
                                me.onClickResetServerCert();
                            }
                        } else {
                            Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                        }
                    }
                });
            }
        });

    },

    /***
     * @function onClickResetServerCert
     */
    onClickResetServerCert : function(btn) {
        var me = this,
            viewModel = me.getViewModel(),
            form = me.lookupReference('servercertificate');

        Ext.Ajax.request({
            url : 'security/security_server_cert.ajax',
            success : function(response) {
                var result = response.responseXML;
                viewModel.set("ServerCertificateDesc", Ext.DomQuery.selectValue('certParsed', result));
                viewModel.set("keyMatch", Ext.DomQuery.selectValue('crtMatchesKey', result));
                viewModel.set("sckey", Ext.DomQuery.selectValue('cert', result));
                Ext.MessageBox.confirm(l10n('confirmation'), l10n('super-secuirty-ssl-settings-have-been-reset-do-you-want-to-reboot-the-server-now'), function(btn) {
                    if (btn == 'yes') {
                        Ext.MessageBox.alert(l10n('system-rebooting'), l10n('please-refresh-the-page-after-system-restarted'));
                        //doReboot
                        me.rebootServer();
                    } else {
                        viewModel.set('isRestartPending', false);
                    }
                });
                
            }
        });
    },
    
    /***
     * @function onClickResetServerCert
     */
    loadServerCert : function(btn) {
        var me = this,
        viewModel = me.getViewModel(),
        form = me.lookupReference('servercertificate');

        Ext.Ajax.request({
            url : 'security/security_server_cert.ajax',
            success : function(response) {
                var result = response.responseXML;
                viewModel.set("ServerCertificateDesc", Ext.DomQuery.selectValue('certParsed', result));
                viewModel.set("keyMatch", Ext.DomQuery.selectValue('crtMatchesKey', result));
                viewModel.set("sckey", Ext.DomQuery.selectValue('cert', result));
            }
        });
    },

    /***
     * @function onClickApplyServerCert
     */
    onClickApplyServerCert : function(btn) {
        var me = this,
        form = me.lookupReference('servercertificate'),
        params = {
        	cert : form.down('#sckey').getValue()
    	};

        Ext.Msg.confirm(l10n('confirmation'), l10n('security-super-are-you-sure-you-want-to-update-the-server-certificate'), function(res) {
            if (res == 'yes') {
                Ext.Ajax.request({
                    url : 'security/security_server_cert_update.ajax',
                    method : 'POST',
                    params : params,
                    success : function(res) {
                        var result = res.responseXML;
                        var success = Ext.DomQuery.selectValue('message @success', result);

                        if (success == "true") {
                            Ext.Msg.alert(l10n('success'), l10n('server-certificate-applied'), function() {
                                me.onClickResetServerCert();
                            });
                        } else {
                            Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                        }
                    }
                });
            }
        });
    },

    /***
     * @function onCheckChangeApplicationsGrid
     * @params {object} gridview
     * @params {object} rowIndex
     * @params {object} checked
     */
    onCheckChangeApplicationsGrid : function(gridview, rowIndex, checked) {
        var me = this,
            grid = me.lookupReference('applicatoingrid'),
            viewModel = me.getViewModel(),
            gridStore = grid.getStore();

        gridStore.each(function(rec) {
            //set all the checkbox same as selected checkbox.
            rec.set('ocsp', checked);
        });
        viewModel.set('isDisabled', false);
    },

    onChangePortInGrid : function() {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.set('isDisabled', false);
    },

    /***
     * @function onClickResetSecurityApplications
     * @description Click on reset button will clear
     * all the data from grid and networkInterface
     * store and reload the data again.
     */
    onClickResetSecurityApplications : function() {
        var me = this,
            grid = me.lookupReference('applicatoingrid'),
            gridStore = grid.getStore(),
            applicationStore = me.getViewModel().getStore('applicationStore'),
            networkInterfaceStore = me.getViewModel().getStore('networkInterfaceStore');

        //remove all the data load again to reset the
        //application grid store and network store for combobox field.
        applicationStore.removeAll();
        applicationStore.load();
        networkInterfaceStore.removeAll();
        networkInterfaceStore.load();
    },

    getSecurityAppsData : function() {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.getStore('networkInterfaceStore').load();
        viewModel.getStore('applicationStore').load();
        viewModel.set('isDisabled', true);
        viewModel.set('isPasswordView', false);
    },

    /***
     * @function onClickSaveApplicationsGrid
     */
    onClickSaveApplicationsGrid : function() {
        var me = this,
            viewModel = me.getViewModel(),
            isSuper = false,
            grid = me.lookupReference('applicatoingrid'),
            gridStore = grid.getStore(),
            modifiedRecord = gridStore.getModifiedRecords(),
            count = modifiedRecord.length,
            recStr = "",
            params = {};

        if (!count) {
            Ext.Msg.alert(l10n('error'), l10n('no-modifications'));
            return;
        }
        Ext.Array.each(modifiedRecord, function(rec, index) {
            recStr += rec.get('appName') + ":" + rec.get("networkInterface") + ":" + rec.get("unsecurePort") + ":" + rec.get("securePort") + ":" + rec.get("ocsp");
            if ((count - 1) !== index) {
                recStr += "|";
            }
            if (rec.get('appName') == "super") {
                isSuper = true;
            }
        });
        params["appconfig"] = recStr;
        Ext.Ajax.request({
            url : "security/security_applications_update.ajax",
            method : 'POST',
            params : params,
            success : function(res) {
                var result = res.responseXML;
                var success = Ext.DomQuery.selectValue('message @success', result);
                if (success == "true") {
                    Ext.Msg.alert(l10n('success'), l10n('changes-successfully-applied'), function() {
                        if (isSuper) {
                            logoutSuper();
                        }
                        viewModel.set('isDisabled', true);
                        gridStore.reload();
                        grid.reconfigure();
                    });
                } else {
                    Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                }
            }
        });
    },

    /***
     * @function onChangeCheckOSCPEnable
     * @param {Object} cb
     * @param {Object} newValue
     */
    onChangeCheckOSCPEnable : function(cb, newValue) {
        var me = this,
            ocspOverrideCb = me.lookupReference('overrideResponder');

        ocspOverrideCb.setValue(false);
        if (newValue) {
            ocspOverrideCb.enable();
        } else {
            ocspOverrideCb.disable();
        }
    },

    /***
     * @function onChangeCheckOSCPOverride
     */
    onChangeCheckOSCPOverride : function(cb, newValue) {
        var me = this,
            viewModel = me.getViewModel(),
            ocspResponderText = me.lookupReference('defaultResponder');

        viewModel.set("ocspTextflag", !newValue);
    },

    /***
     * @function onClickSaveOCSPSettings
     * @param {Ext.Button} btn
     */
    onClickSaveOCSPSettings : function(btn) {
        var me = this,
            ocspForm = me.lookupReference('ocspfieldset'),
            ocspEnableCb = me.lookupReference('ocspEnable'),
            ocspOverrideCb = me.lookupReference('overrideResponder'),
            ocspResponderText = me.lookupReference('defaultResponder'),
            params = !ocspEnableCb.getValue() ? '' : {
            ocspEnable : 'on',
        };

        if (Ext.isObject(params)) {
            params["overrideResponder"] = ocspOverrideCb.getValue() ? "on" : "off";
            if (ocspResponderText.isValid()) {
                params["defaultResponder"] = ocspResponderText.getValue();
            } else {
                Ext.Msg.alert(l10n('error'), l10n('failed-to-save-ocsp-settings'));
                return false;
            }
        }

        Ext.Ajax.request({
            url : 'security/security_advanced_ocsp.ajax',
            params : params,
            method : 'POST',
            success : function(res) {
                var result = res.responseXML;
                if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                    Ext.Msg.alert(l10n("success"), l10n('super-security-private-ocsp-update-success'));
                } else {
                    Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                }
            }
        });
    },

    /***
     * @function onClickSAExport
     */
    onClickSAExport : function(btn) {
        var me = this,
            overlay = overlay = Ext.widget('securityoverlay', {
            viewtype : btn.viewtype,
            floatParent : me.view
        });

        if (overlay) {
            overlay.show();
        }
    },

    onExportBundle : function(btn) {
        var me = this,
            form = btn.up('form');

        if (form && form.getForm().isValid()) {
            window.open('security/security_advanced_export_bundle.ajax?bundlePassword=' + form.getForm().findField("bundlePassword").getValue());
            form.up('window').destroy();
        }
    },

    /***
     * @function onClickSAReset
     */
    onClickSAReset : function(btn) {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();

        Ext.Msg.confirm(l10n("reset"), l10n('super-security-ssl-reset-all-security-settings-to-factory-defaults-ssl-will-be-disabled-and-a-restart-will-be-necessary'), function(res) {
            if (res == "yes") {
                view.setLoading(true);
                Ext.Ajax.request({
                    url : 'security/security_advanced_factory_reset.ajax',
                    method : 'POST',
                    waitTitle : l10n('super-ssl-advanced-button-reset-security'),
                    waitMsg : l10n('super-security-ssl-resetting-ssl-security-settings'),
                    timeout : 120000,
                    success : function(res) {
                        view.setLoading(false);
                        var result = res.responseXML;
                        if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                            Ext.MessageBox.confirm(l10n('confirmation'), l10n('super-secuirty-ssl-settings-have-been-reset-do-you-want-to-reboot-the-server-now'), function(res) {
                                if (res == "yes") {
                                    Ext.MessageBox.alert(l10n('reboot'), l10n('please-refresh-the-page-after-system-reboot'));
                                    me.rebootServer();
                                } else {
                                    viewModel.set('isRestartPending', false);
                                }
                            });
                        } else {
                            Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                        }
                    }
                });
            }
        });

    },

    /**
     * @function showUploadWindow
     */
    showUploadWindow : function(btn) {
        var me = this,
            viewModel = me.getViewModel(),
            overlay = Ext.widget('securityoverlay', {
            viewtype : btn.viewtype,
            floatParent : me.view
        });

        if (overlay) {
            overlay.show();
            if (me.lookupReference('defaultcarootcert'))
                me.lookupReference('defaultcarootcert').setValue(viewModel.get('defaultRoot'));
        }
    },

    /***
     * @function onClickCertificateBundleUpload
     * @param {Object} btn
     */
    onClickCertificateBundleUpload : function(btn) {
        var me = this,
            overlay = me.lookupReference('securityoverlay'),
            form = me.lookupReference('bundlecertificate');

        if (form.getForm().isValid()) {
            form.getForm().submit({
                timeout : 180000,
                method : 'POST',
                url : "security/security_advanced_upload_bundle.ajax",
                waitTitle : l10n('uploading-file'),
                waitMsg : l10n('your-file-is-being-uploaded'),
                success : function(frm, action) {
                    var result = action.response.responseXML;
                    form.getForm().reset();
                    overlay.hide();
                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                     
                            Ext.Msg.confirm(l10n('confirmation'), l10n('super-security-ssl-settings-reset-reboot-server-now'), function(res) {
                                if (res == "yes") {
                                    Ext.MessageBox.alert(l10n('reboot'), l10n('please-refresh-the-page-after-system-reboot'));
                                    me.rebootServer();
                                } else {
                                    //Show restart warning;
                                    viewModel.set('isRestartPending', false);
                                    Ext.Msg.alert(l10n('restart'), l10n('please-restart-the-system'));
                                }
                            });
                      
                    } else {
                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                    }
                }
            });
        }
    },

    /**
     * @function onClickRootUpload
     * @param {Object} btn
     */
    onClickRootUpload : function(btn) {
        var me = this,
            form = me.lookupReference("rootform"),
            overlay = me.lookupReference('securityoverlay'),
            view = btn.up('securityadvance');

        if (form.getForm().isValid()) {
            form.getForm().submit({
                timeout : 120000,
                method : 'POST',
                url : "security/security_advanced_upload_root.ajax",
                waitTitle : l10n('uploading-file'),
                waitMsg : l10n('your-file-is-being-uploaded'),
                success : function(frm, action) {
                    var result = action.response.responseXML;
                    form.getForm().reset();
                    overlay.hide();
                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                        Ext.Msg.confirm(l10n('confirmation'), l10n('super-security-ssl-settings-reset-reboot-server-now'), function(res) {
                            if (res == "yes") {
                                Ext.MessageBox.alert(l10n("reboot"), l10n('please-refresh-the-page-after-system-reboot'));
                                me.rebootServer();
                            } else {
                                //Show restart warning;
                                viewModel.set('isRestartPending', false);
                                Ext.Msg.alert(l10n('restart'), l10n('please-restart-the-system'));
                            }
                        });
                    } else {
                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                    }
                }
            });
        }
    },

    /***
     * @function onClickCancelClientCert
     * @param {Object} btn
     */
    onClickCancelClientCert : function(btn) {
        var me = this,
            form = me.lookupReference('clientcacert'),
            overlay = me.lookupReference('securityoverlay');

        if (form) {
            form.getForm().reset();
            overlay.hide();
        }
    },

    /***
     * @function onClickSaveClientCert
     */
    onClickSaveClientCert : function(btn) {
        var me = this,
            form = me.lookupReference('clientcacert'),
            viewModel = me.getViewModel(),
            overlay = me.lookupReference('securityoverlay');

        if (form && form.getForm().isValid()) {
            form.getForm().submit({
                timeout : 120000,
                method : 'POST',
                url : "security/security_use_default_root.ajax",
                waitTitle : l10n('saving-settings'),
                waitMsg : l10n('saving-settings'),
                success : function(frm, action) {
                    var result = action.response.responseXML;
                    overlay.hide();
                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                        Ext.MessageBox.confirm(l10n('confirmation'), l10n('configuration-saved-reboot-server-now'), function(btn) {
                            if (btn == 'yes') {
                                Ext.MessageBox.alert(l10n('system-rebooting'), l10n('please-refresh-the-page-after-system-reboot'));
                                me.rebootServer();
                            } else {
                                //Show restart warning;
                                viewModel.set('isRestartPending', false);
                                Ext.Msg.alert(l10n('restart'), l10n('please-restart-the-system'));
                            }
                        });
                    } else {
                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                    }
                },
                failure : function(form, action) {
                    form.getForm().reset();
                    var errors = '';
                    if ((action.result != null) && (!action.result.success)) {
                        for (var i = 0; i < action.result.errors.length; i++) {
                            errors += action.result.errors[i].msg + '<br>';
                        }
                    }
                    if (errors != '') {
                        Ext.Msg.alert(l10n('error'), '<br>' + errors, function() {
                        });
                    } else {
                        Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
                        });
                    }
                }
            });
        }
    },

    /***
     * @function onClickServerCACertUpload
     */
    onClickServerCACertUpload : function() {
        var me = this,
            form = me.lookupReference('cacertview');

        if (form && form.getForm().isValid()) {
            form.getForm().submit({
                url : 'security/security_upload_file.ajax',
                method : 'POST',
                params : {
                    uploadType : "certInt",
                },
                waitTitle : l10n('uploading-file'),
                waitMsg : l10n('your-file-is-being-uploaded'),
                success : function(frm, action) {
                    var result = action.response.responseXML;
                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {

                        if (location.protocol == "https:") {
                            Ext.Msg.alert(l10n('success'), l10n('server-certificate-installed-connection-reset'), function() {
                                me.createCookie("rebootMsg", "1", 1);
                                location.reload();
                            });
                        } else {
                            form.getForm().reset();
                            me.lookupReference('securityoverlay').hide();
                            me.getSecurityCAServerData();
                        }
                    } else {
                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                    }
                },
                failure : function(form, action) {
                    form.getForm().reset();
                    var errors = '';
                    if ((action.result != null) && (!action.result.success)) {
                        for (var i = 0; i < action.result.errors.length; i++) {
                            errors += action.result.errors[i].msg + '<br>';
                        }
                    }
                    if (errors != '') {
                        Ext.Msg.alert(l10n('error'), '<br>' + errors, function() {
                        });
                    } else {
                        Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
                        });
                    }
                }
            });
        }
    },

    getSecurityCAServerData : function() {
        var me = this,
            view = me.getView(),
            viewModel = me.getViewModel();

        view.setLoading(true);
        viewModel.set('isPasswordView', false);
        Ext.Ajax.request({
            url : 'security/security_server_ca_cert.ajax',
            success : function(response) {
                var result = response.responseXML,
                    caCertValue = Ext.DomQuery.selectValue('caCert', result);

                viewModel.set("cacontent", Ext.DomQuery.selectValue('caCertParsed', result));
                if (caCertValue) {
                    viewModel.set("cacert", caCertValue);
                }
                view.setLoading(false);
            }
        });
    },

    /**
     * @function createCookie
     *
     * @param {Object} name
     * @param {Object} value
     * @param {Object} days
     */
    createCookie : function(name, value, days) {
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            var expires = "; expires=" + date.toGMTString();
        } else
            var expires = "";
        document.cookie = name + "=" + value + expires + "; path=/";
    },

    onClickSavePasswords : function(btn) {
        var me = this,
            form = me.lookupReference('passwords');

        if (form && form.getForm().isValid()) {
            var params = form.getForm().getValues();
            Ext.Msg.confirm(l10n('warning'), l10n('passwords-save-warning'), function (btn) {
                if (btn == 'yes') {          
		            Ext.Ajax.request({
		                url : 'savepasswordconfigvals.ajax',
		                params : params,
		                method : 'POST',
		                success : function(res) {
		                    var xmlResponse = res.responseXML;
		                    var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
		                    if (success == "true") {
		                        Ext.Msg.alert(l10n("message"), l10n("saved"));
		                        me.getPasswordsData();
		                    }
		                },
		                failure : function(res) {
		                    Ext.Msg.alert(l10n('error'), res.responseText);
		                }
		            });
                }
            });
        } else {
            Ext.Msg.alert(l10n('error'), l10n('failed-to-save-password-settings'));
            return false;
        }
    },

    getPasswordsData : function() {
        var me = this,
        view = me.view;

        Ext.Ajax.request({
            url : 'getpasswordconfigvals.ajax',
            success : function(response) {
                var result = response.responseXML,
                    viewModel = view.getViewModel();

                viewModel.set("expiryDays", parseInt(Ext.DomQuery.selectValue('expieryDays', result)));
                viewModel.set("inactiveDays", parseInt(Ext.DomQuery.selectValue('inactiveDays', result)));
                viewModel.set("failCount", Ext.DomQuery.selectValue('failCount', result));
                viewModel.set("passwordComplexity", Ext.DomQuery.selectValue('passwordComplexity', result) == "false" ? false : true);
                viewModel.set("disableForgetPasswordSuper", Ext.DomQuery.selectValue('disableForgetPasswordSuper', result) == "false" ? false : true);
                viewModel.set('sessionExpPeriod', Ext.DomQuery.selectValue('sessionExpPeriod', result));
                viewModel.set("minPINLength", parseInt(Ext.DomQuery.selectValue('minPINLength', result)));
                me.lookupReference("passwordComplexity").setValue(viewModel.get("passwordComplexity"));
                me.lookupReference("disableForgetPasswordSuper").setValue(viewModel.get("disableForgetPasswordSuper"));
                viewModel.set('isPasswordView', true);
            }
        });
    },

    passwordsDefault : function() {
        var scope = this,
        view =scope.view;
        var viewModel = view.getViewModel();
        Ext.MessageBox.confirm(l10n('confirmation'), l10n('alert-replacing-customized-value-with-default-continue'), function showResult(btn) {
            if (btn == "yes") {
                scope.lookupReference('expieryDays').setValue('0');
                scope.lookupReference('inactiveDays').setValue('0');
                scope.lookupReference('failCount').setValue('0');
                scope.lookupReference('passwordComplexity').setValue('false');
                viewModel.set("minPINLength", parseInt(6));
                scope.lookupReference('disableForgetPasswordSuper').setValue('false');
            }
        });
    },

    onExportWithPassword : function(btn) {
        var me = this,
            form = btn.up('form');

        if (form && form.getForm().isValid()) {
            form.getForm().submit({
                method : 'POST',
                url : 'security/security_export_key.ajax',
                params : {
                    password : "keyPassword"
                },
                success : function(form, action) {
                    var result = action.response.responseXML;
                    form.up('window').destroy();
                    if (Ext.DomQuery.selectValue('message @success', result) != "true") {
                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
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
                        Ext.Msg.alert(l10n('error'), '<br>' + errors);
                    } else {
                        Ext.Msg.alert(l10n('error'), l10n('failure'));
                    }
                }
            });
        }
    },

    onChangeHttpsOnly : function(checkbox, newVal) {
        var me = this;
        if (checkbox.state == 1) {
            checkbox.state = 2;
            return;
        }
        if (newVal) {
            Ext.Msg.confirm(l10n('super-security-ssl-enable-https-only'), l10n('super-security-ssl-are-you-sure-you-want-to-enable-https-only'), function(res) {
                if (res == 'yes') {
                    Ext.Ajax.request({
                        url : 'security/security_command.ajax',
                        params : {
                            command : "forcedEnable"
                        },
                        success : function(res) {
                            var result = res.responseXML;
                            if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                                me.getSecurityViewData();
                                me.setRebootMessageCookie();
                                Ext.Msg.alert(l10n('success'), l10n('super-security-ssl-https-only-is-on'), me.handleSSLChangeRedirect());
                            } else {
                                Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                            }
                        }
                    });
                } else {
                    checkbox.state = 1;
                    checkbox.setValue(false);
                }
            });
        } else {
            Ext.Msg.confirm(l10n('super-security-ssl-disable-https-only'), l10n('super-security-ssl-are-you-sure-you-want-to-disable-the-https-only-feature'), function(res) {
                if (res == 'yes') {
                    Ext.Ajax.request({
                        url : 'security/security_command.ajax',
                        params : {
                            command : "forcedDisable"
                        },
                        success : function(res) {
                            var result = res.responseXML;
                            if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                                Ext.Msg.alert(l10n('success'), l10n('super-security-ssl-https-only-is-off'), me.handleSSLChangeRedirect());
                            } else {
                                Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                            }
                            me.getSecurityViewData();
                        }
                    });
                } else {
                    checkbox.state = 1;
                    checkbox.setValue(true);
                }
            });
        }
    },

    onClickSSLEnableToggle : function(btn) {
        var me = this,
            viewModel = me.getViewModel(),
            txt = btn.getText() == l10n('super-security-ssl-enable') ? "enable" : "disable",
            confirmMsg = txt == "disable" ? l10n('super-security-ssl-are-you-sure-you-want-to-disable-ssl') : l10n('super-security-ssl-are-you-sure-you-want-to-enable-ssl'),
            confirmTitle = txt == "disable" ? l10n('super-security-ssl-disable') : l10n('super-security-ssl-enable');

        if (me.isEncryptionEnable() && viewModel.get('encryption') == l10n('disable-encryption')) {
            Ext.Msg.alert(l10n('error'), l10n('you-need-to-disable-encryption-first'));
            return;
        }
        Ext.Msg.confirm(confirmTitle, confirmMsg, function(response) {
            if (response == 'yes') {
                Ext.Ajax.request({
                    url : 'security/security_command.ajax',
                    params : {
                        command : txt
                    },
                    success : function(res) {
                        var xmlResponse = res.responseXML;
                        var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                        if (success == "true") {
                            var successAlertMsg = txt == "disable" ? l10n('super-security-ssl-ssl-has-been-disabled') : l10n('super-security-ssl-ssl-has-been-enabled');
                            if (txt == "disable") {
                                me.setRebootMessageCookie();
                            }
                            me.getSecurityViewData();
                            Ext.Msg.alert(l10n('success'), successAlertMsg, me.handleSSLChangeRedirect());
                        } else {
                            Ext.Msg.alert(Ext.DomQuery.selectValue('id', xmlResponse), Ext.DomQuery.selectValue('msg', xmlResponse));
                        }
                    }
                });
            }
        });
    },

    setRebootMessageCookie : function() {
        this.createCookie("rebootMsg", "1", 1);
    },

    checkForRebootMessage : function() {
        var me = this,
            viewModel = me.getViewModel(),
            readCookie = function(name) {
            var nameEQ = name + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ')
                c = c.substring(1, c.length);
                if (c.indexOf(nameEQ) == 0)
                    return c.substring(nameEQ.length, c.length);
            }
            return null;
        },
            eraseCookie = function(name) {
            me.createCookie(name, "", -1);
        };
        if (readCookie("rebootMsg")) {
            eraseCookie("rebootMsg");
            Ext.MessageBox.confirm(l10n('confirmation'), l10n('super-secuirty-ssl-settings-have-been-reset-do-you-want-to-reboot-the-server-now'), function(btn) {
                if (btn == 'yes') {
                    Ext.MessageBox.alert(l10n('system-rebooting'), l10n('please-refresh-the-page-after-system-restarted'));
                    //doReboot
                    me.rebootServer();
                } else {
                    viewModel.set('isRestartPending', false);
                }
            });
        }
    },

    handleSSLChangeRedirect : function() {
    	//buggy
           var httpsOnlyFlag = this.getViewModel().get('httpsOnlyFlag'),
            sslEnabledFlag = this.getViewModel().get('sslEnabledFlag');
        if (location.protocol == 'https:') {
            if (!sslEnabledFlag) {
                var restOfUrl = window.location.href.substr(6);
                window.location = "http:" + restOfUrl;
            } else {
                checkForRebootMessage();
            }
        } else {
            if (httpsOnlyFlag) {
                var restOfUrl = window.location.href.substr(5);
                window.location = "https:" + restOfUrl;
            }
        }
    },

    rebootServer : function() {
        var me = this,
            pageLoadStamp,
            store = Ext.create('Ext.data.Store', {
            fields : ['serverStartedTimestamp', 'serverStarted'],
            storeId : 'serverstartstamp',
            remoteSort : false,
            proxy : {
                type : 'ajax',
                url : 'serverstartedtime.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row'
                }
            },
            autoLoad : true
        }).load({
            callback : function(rec) {
                var rec = rec[0];
                pageLoadStamp = parseInt(rec.get('serverStartedTimestamp'));
            }
        }),
            doRefresh = function() {
            if (store) {
                store.reload();
                var xml = store.getRange()[0];
                var serverStartedTimestampText = xml.get('serverStartedTimestamp');
                if (serverStartedTimestampText != undefined) {
                    var serverStartedTimestamp = parseInt(serverStartedTimestampText.trim());
                    if (pageLoadStamp < serverStartedTimestamp) {
                        Ext.TaskManager.stop(refresher);
                        Ext.Message.alert(l10n("success"), l10n("restarted1"), function() {
                            logoutSuper();
                        });
                    }
                }
            }
        },
            refresher = {
            interval : 5000,
            scope : me,
            run : doRefresh
        };

        Ext.TaskManager.start(refresher);
        Ext.Ajax.request({
            url : 'securedmaint/maintenance_system_restart.ajax',
            method : 'POST',
            success : function(res) {
                Ext.Msg.show({
                    msg : l10n('system-rebooting'),
                    title : l10n('the-system-is-going-down-for-reboot-now'),
                    minWidth : 300,
                    modal : true,
                    icon : Ext.Msg.INFO,
                    fn : function() {
                    }
                });
            },
            failure : function(form, action) {
                Ext.TaskManager.stop(refresher);
                var errors = '';
                if ((action.result != null) && (!action.result.success)) {
                    for (var i = 0; i < action.result.errors.length; i++) {
                        errors += action.result.errors[i].msg + '<br>';
                    }
                }
                if (errors != '') {
                    Ext.Msg.alert(l10n('error'), '<br>' + errors, function() {
                    });
                } else {
                    Ext.Msg.alert(l10n('error'), l10n('failed-to-restart-web-server'), function() {
                    });
                }
            }
        });
    },
    passwordsReset : function() {
        this.getPasswordsData();
    },

    onChangeDisableHttps : function(cb, isChecked) {
        var me = this,
            viewModel = me.getViewModel();
        if (me.getViewModel().get('httpsOnlyFlagNoRedirect') != isChecked) {
            if (isChecked) {
                Ext.MessageBox.show({
                    title : l10n('confirmation'),
                    msg : l10n('are-you-sure-you-want-to-disable-http-to-https-redirect'),
                    buttons : Ext.Msg.YESNO,
                    closable : false,
                    fn : function(res) {
                        switch (res) {
                        case "yes" :
                            Ext.Ajax.request({
                                url : 'security/security_command.ajax',
                                params : {
                                    command : "forcedEnableNoRedirect"
                                },
                                success : function(res) {
                                    var result = res.responseXML;
                                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                                        me.getSecurityViewData();
                                        Ext.Msg.alert(l10n('success'), l10n('http-to-https-redirect-disabled'),"");
                                    } else {
                                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                                    }
                                }
                            });
                            break;
                        case "no" :
                            viewModel.set('httpsOnlyFlagNoRedirect', false);
                            break;
                        }
                    }
                });
            } else {
                Ext.MessageBox.show({
                    title : l10n('confirmation'),
                    msg : l10n('are-you-sure-you-want-to-enable-http-to-https-redirect'),
                    buttons : Ext.Msg.YESNO,
                    closable : false,
                    fn : function(res) {
                        switch (res) {
                        case "yes" :
                            Ext.Ajax.request({
                                url : 'security/security_command.ajax',
                                params : {
                                    command : "forcedEnable"
                                },
                                success : function(res) {
                                    var result = res.responseXML;
                                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                                        me.getSecurityViewData();
                                        me.setRebootMessageCookie();
                                        Ext.Msg.alert(l10n('success'), l10n('http-to-https-redirect-enabled'), me.handleSSLChangeRedirect());
                                    } else {
                                        Ext.Msg.alert(Ext.DomQuery.selectValue('id', result), Ext.DomQuery.selectValue('msg', result));
                                    }
                                }
                            });
                            break;
                        case "no" :
                            viewModel.set('httpsOnlyFlagNoRedirect', true);
                            break;
                        }
                    }
                });
            }
        }
    },

    onClickSecurityEncryption : function() {
        var me = this,
            viewModel = me.getViewModel(),
            isEncryption = viewModel.get('encryption') == l10n('enable-encryption') ? true : false;

        Ext.Msg.confirm(l10n('confirmation'), l10n('vidyomanager-vidyorouter-restart'), function(res) {
            if (res == "yes") {
                Ext.Ajax.request({
                    url : 'security/security_command.ajax',
                    method : 'POST',
                    params : {
                        command : 'componentsEncryption',
                        encryption : isEncryption
                    },
                    success : function(res) {
                        var result = res.responseXML;
                        if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                        	if(isEncryption){
                        	 Ext.Msg.alert(l10n('success'), l10n('encryption-enabled'));
                        	}
                        	else{
                        		 Ext.Msg.alert(l10n('success'), l10n('encryption-disabled'));
                        	}
                        } else {
                        	
                            Ext.Msg.alert(l10n('success'), l10n('encryption-failed'));
                        }
                        me.getSecurityViewData();
                    }
                });
            }
        });
    }
});


/**
 * @author AuthenticationViewController
 */
Ext.define('AdminApp.view.settings.auth.AuthenticationViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.AuthenticationViewController',

    requires: ['AdminApp.model.settings.SamlAttrMapping'],

    getAuthenticationData: function () {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();
        me.getAuthenticationAjaxData();
    },

    getAuthenticationAjaxData: function () {
        // console.log('inside getauth') ;
        var me = this,
            disableSave = true,
            view = me.view,
            viewModel = me.getViewModel(),
            authPanel = view.lookupReference('authpanel'),
            saveBtn = view.lookupReference('authsave'),
            authType = me.lookupReference('authType');
        saveBtn.disable();
        //  console.log('removing auth panel') ;

        //enabling this will cause issue when user click many times in a second...but this will break disabling and enabling save button when form is not dirty and  dirty
        //authType.suspendEvents();
        //  console.log('events supspended') ;
        viewModel.getStore('authenticationStore').load({
            callback: function (recs) {
                authPanel.removeAll();
                   authType.suspendEvents();
                var v = view;
                var rec = recs[0];

                var wsflag = rec.get('wsflag');
                var restFlag = rec.get('restFlag');
                var ldapflag = rec.get('ldapflag');
                var cacflag = rec.get('cacflag');
                var cacldapflag = rec.get('cacldapflag');
                var ldapmappingflag = rec.get('ldapmappingflag');
                var samlflag = rec.get('samlflag');
                var samlmappingflagtmp = rec.get('samlmappingflag');
                if (samlmappingflagtmp == 'undefined') {
                    samlmappingflagtmp = 1;
                }

                if (wsflag == 'true') {
                    authType.setValue("WS");
                    var wsView = Ext.create('AdminApp.view.settings.auth.Webservice');
                    var itemselector = wsView.down('itemselector[name=authFor]');

                    var roleStore = viewModel.getStore('roleStore').load({
                        callback: function () {
                            viewModel.getStore('toRolesStore').load({
                                callback: function (recs) {
                                    var valArray = [];
                                    Ext.each(recs, function (rec) {
                                        valArray.push(rec.get('roleID'));
                                    });
                                    itemselector.setValue(valArray);
                                }
                            });
                        }
                    });
                    itemselector.setStore(roleStore);
                    var form = wsView.down('form[reference=webserviceform]');

                    form.loadRecord(rec);
                    authPanel.add(wsView);
                }
                else if (restFlag == 'true') {
                    authType.setValue("REST");
                    var wsView = Ext.create('AdminApp.view.settings.auth.RestWebservice');
                    var itemselector = wsView.down('itemselector[name=authFor]');

                    var roleStore = viewModel.getStore('roleStore').load({
                        callback: function () {
                            viewModel.getStore('toRolesStore').load({
                                callback: function (recs) {
                                    var valArray = [];
                                    Ext.each(recs, function (rec) {
                                        valArray.push(rec.get('roleID'));
                                    });
                                    itemselector.setValue(valArray);
                                }
                            });
                        }
                    });
                    itemselector.setStore(roleStore);
                

                    wsView.loadRecord(rec);
                    authPanel.add(wsView);
                }
                else if (ldapflag == 'true') {
                    authType.setValue("LDAP");
                    var ldapView = Ext.create('AdminApp.view.settings.auth.LDAP');
                    var itemselector = ldapView.down('itemselector[name=authFor]');
                    var roleStore = viewModel.getStore('roleStore').load({
                        callback: function () {
                            viewModel.getStore('toRolesStore').load({
                                callback: function (recs) {
                                    var valArray = [];
                                    Ext.each(recs, function (rec) {
                                        valArray.push(rec.get('roleID'));
                                    });
                                    itemselector.setValue(valArray);
                                }
                            });
                        }
                    });
                    itemselector.setStore(roleStore);

                    ldapView.loadRecord(rec);
                    var ldapMappingfs = ldapView.down('fieldset[reference=ldapmapping]');
                    if (ldapmappingflag == 'true') {
                        ldapMappingfs.expand();
                    } else {
                        ldapMappingfs.collapse();
                    }
                    authPanel.add(ldapView);
                }
                else if (samlflag == 'true') {
                    authType.setValue("SAML");
                    var samlView = Ext.create('AdminApp.view.settings.auth.SAML');
                    samlView.loadRecord(rec);
                    
                    var samlPrvType = samlView.down('combo[reference=samlProvisionType]');
                    samlPrvType.fireEvent('select', samlPrvType);
                    authPanel.add(samlView);
                }
                else if (cacflag == 'true') {
                    // console.log('inside cac') ;
                    authType.setValue("CAC");
                    var pkiView = Ext.create('AdminApp.view.settings.auth.PKI');
                    // console.log('getting view cac'+pkiView) ;
                    pkiView.loadRecord(rec);
                    //  console.log('updated view with record'+rec) ;
                    var cacflag = pkiView.getForm().findField('cacflag');


                    var pkitabpanel = pkiView.down('tabpanel[reference=pkitabpanel]');
                    if (cacflag && cacflag.getValue() == 'true') {
                        if (pkitabpanel) {


                            pkitabpanel.setHidden(false);
                        }
                    } else {
                        if (pkitabpanel) {

                            pkitabpanel.setHidden(true);

                        }
                    }
                    // console.log('hiding grid form is done if needed') ;
                    var cacAuthType = pkiView.down('combo[reference=cacauthtype]');
                    cacAuthType.fireEvent('change', cacAuthType);
                    authPanel.add(pkiView);
                    var notificationLabel=pkiView.lookupReference('notificationlabel');
                    if (rec.get('pkiCertReviewPending') == "true" || (rec.get('pkiCertReviewPending') == true)) {
                        notificationLabel.setText( l10n('pki-approval-notification'));
                       
                        notificationLabel.setHidden(false);
                    } else {

                           if (rec.get('webServerRestartNeeded') == "true" || (rec.get('webServerRestartNeeded') == true)) {
                                notificationLabel.setText( l10n('web-server-restart-is-pending'));
                                notificationLabel.setHidden(false);
                           }else{
                                  notificationLabel.setHidden(true);
                           }
                     
                    }
                

                } else {

                    authType.setValue("NORMAL");
                    saveBtn.enable();

                }
               authType.resumeEvents(true);

            }
        });



    },
    hideRest: function (view, authTypeView) {
        console.log('inside hide rest');

    },


    onChangeAuthType: function (combo, val) {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel(),
            authPanel = me.lookupReference('authpanel'),
            saveBtn = view.lookupReference('authsave');
        store = viewModel.getStore('authenticationStore');

        if (store) {

            var rec = store.getAt(0);
            if (rec.get('isSSLEnabled') == 'false' && val == 'CAC') {

                Ext.Msg.alert(l10n('message'), "PKI Not allowed without https");
                me.getAuthenticationData();
                return;
            }
        }

        saveBtn.disable();
        authPanel.removeAll();
        switch (val) {
            case "NORMAL":
                saveBtn.enable();

                break;
            case "LDAP":
                {

                    var form = Ext.create('AdminApp.view.settings.auth.LDAP');

                    var itemselector = form.down('itemselector[name=authFor]');
                    var roleStore = viewModel.getStore('roleStore').load({
                        callback: function () {
                            viewModel.getStore('toRolesStore').load({
                                callback: function (recs) {
                                    var valArray = [];
                                    Ext.each(recs, function (rec) {
                                        valArray.push(rec.get('roleID'));
                                    });
                                    itemselector.setValue(valArray);
                                }
                            });
                        }
                    });
                    itemselector.setStore(roleStore);


                    me.loadStore(form);

                    var ldapMappingfs = form.down('fieldset[reference=ldapmapping]');
                    var ldapmappingflag = form.getForm().findField('ldapmappingflag');
                    if (ldapmappingflag && ldapmappingflag.getValue() == 'true') {
                        ldapMappingfs.expand();
                    } else {
                        ldapMappingfs.collapse();
                    }
                    form.getForm().findField('ldapflag').setValue('true');
                    authPanel.add(form);
                    break;
                } case "CAC":
                {
                         console.log('inside cac') ;
                    var authView = Ext.create('AdminApp.view.settings.auth.PKI');
                      console.log('view created') ;
                    authPanel.add(authView);
                    console.log('add view') ;
                    var store = viewModel.getStore('authenticationStore').load();
                    var rec;
                    if (store) {
                        rec=store.getAt(0);
                        authView.loadRecord(rec);
                    }
                    console.log('rec inside') ;
                    var cacflag = authView.getForm().findField('cacflag');
                    var pkitabpanel = authView.down('tabpanel[reference=pkitabpanel]');
                    var cacAuthType = authView.down('combo[reference=cacauthtype]');
                    cacAuthType.fireEvent('change', cacAuthType);
                    if (cacflag && cacflag.getValue() == 'true') {
                        if (pkitabpanel) {

                            pkitabpanel.setHidden(false);
                        }
                    } else {
                        if (pkitabpanel) {

                            var certficateStorePerm = viewModel.getStore('certificateStorePERM');
                            var certficateStore = viewModel.getStore('certificateStore');

                            if ((certficateStore && certficateStore.getCount() > 0) || (certficateStorePerm && certficateStorePerm.getCount() > 0)) {
                                //when user switch from local to pki       and pki already saved in db   

                                pkitabpanel.setHidden(false);
                            } else {
                                pkitabpanel.setHidden(true);
                            }


                        }


                    }
                         console.log('view model set') ;
                 
                  var notificationLabel=authView.lookupReference('notificationlabel');
                    if (rec.get('pkiCertReviewPending') == "true" || (rec.get('pkiCertReviewPending') == true)) {
                        notificationLabel.setText( l10n('pki-approval-notification'));
                       
                        notificationLabel.setHidden(false);
                    } else {

                           if (rec.get('webServerRestartNeeded') == "true" || (rec.get('webServerRestartNeeded') == true)) {
                                notificationLabel.setText("Hi i need restart");
                                notificationLabel.setHidden(false);
                           }else{
                                  notificationLabel.setHidden(true);
                           }
                     
                    }

                    authView.getForm().findField('cacflag').setValue('true');
                    break;
                }
            case "WS":
                var wsView = Ext.create('AdminApp.view.settings.auth.Webservice');
                var itemselector = wsView.down('itemselector[name=authFor]');
                var roleStore = viewModel.getStore('roleStore').load({
                    callback: function () {
                        viewModel.getStore('toRolesStore').load({
                            callback: function (recs) {
                                var valArray = [];
                                Ext.each(recs, function (rec) {
                                    valArray.push(rec.get('roleID'));
                                });
                                itemselector.setValue(valArray);
                            }
                        });
                    }
                });
                itemselector.setStore(roleStore);
                var form = wsView.down('form[reference=webserviceform]');
                form.getForm().findField('wsflag').setValue('true');
                authPanel.add(wsView);
                if (store && store.getAt(0)) {
                    form.loadRecord(store.getAt(0));
                } else {
                    me.loadStore(form);
                }
                break;
            case "REST": 
                    
                    var wsView = Ext.create('AdminApp.view.settings.auth.RestWebservice');
                    var itemselector = wsView.down('itemselector[name=authFor]');

                    var roleStore = viewModel.getStore('roleStore').load({
                        callback: function () {
                            viewModel.getStore('toRolesStore').load({
                                callback: function (recs) {
                                    var valArray = [];
                                    Ext.each(recs, function (rec) {
                                        valArray.push(rec.get('roleID'));
                                    });
                                    itemselector.setValue(valArray);
                                }
                            });
                        }
                    });
                    itemselector.setStore(roleStore);               
                    me.loadStore(wsView);       
                     wsView.getForm().findField('restFlag').setValue('true');            
                    authPanel.add(wsView);
              
                  
                 
                break;
            case "SAML":
                {
                    var samlView = Ext.create('AdminApp.view.settings.auth.SAML');
                    authPanel.add(samlView);
                    if (store && store.getAt(0)) {
                      var rec= store.getAt(0);
                    if(rec.get('samlSpEntityId') =='' || rec.get('samlSpEntityId') =="" || rec.get('samlSpEntityId') ==null){
                            rec.set('samlSpEntityId',window.location.origin );
                    }
                        samlView.loadRecord(rec);
                    } else {
                        me.loadStore(samlView);
                    }
                    samlView.getForm().findField('samlflag').setValue('true');
                    var samlPrvType = samlView.down('combo[reference=samlProvisionType]');
                    samlPrvType.fireEvent('select', samlPrvType);
                    break;
                }

        }
    },
    loadStore: function (form) {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();


        var store = viewModel.getStore('authenticationStore').load();
        if (store) {
            form.loadRecord(store.getAt(0));
        }

    },

    /***
     * @function onClickSaveAuthentication
     */
    onClickSaveAuthentication: function () {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel(),
            form = view.getForm();


       

        if (view && form.isValid()) {
            var values = form.getValues(),
                authType = form.findField('authType').getSubmitValue();
            Ext.Msg.confirm(l10n('confirmation'), l10n('authentication-save-warning-message'), function (res) {
                if (res == "yes") {


                    Ext.Ajax.request({
                        url: 'saveauthentication.ajax  ',
                        params: values,
                        success: function (res) {
                            var xmlResponse = res.responseXML;
                            var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                            if (success == "true") {
                                //this will reset the form with new values ,so form will not be dirty after save. if you copy this,make sure that- trackResetOnLoad: true,
                                form.setValues(values);
                                if (!(authType == "NORMAL")) {
                                    me.lookupReference('authsave').disable();
                                }
                                if (authType == "CAC") {
   var gridTemp=Ext.ComponentQuery.query('grid[reference=certficategridTemp]')[0];
   var gridPerm=Ext.ComponentQuery.query('grid[reference=certficategridPerm]')[0];
        var certficateStore = gridTemp.getStore();
         var certficateStorePERM = gridPerm.getStore();

                                    if (certficateStore && certficateStore.getCount() == 0 && certficateStorePERM && certficateStorePERM.getCount() == 0) {
                                        Ext.MessageBox.alert(l10n('message'), 'Saved. Please upload trusted CA certificates', me.appendCACCertificate, me);
                                        //inorder to make connection test working
                                    } else {

                                        if(certficateStorePERM && certficateStorePERM.getCount() >0){
                                            me.getAuthenticationAjaxData();
                                        }

                                        Ext.Msg.alert(l10n('message'), l10n('saved'));
                                    }
                                }



                                else {
                                    Ext.Msg.alert(l10n('message'), l10n('saved'));
                                    //need to clear PKI cerficate
                                    if (certficateStore) {
                                        certficateStore.removeAll();
                                    }
                                }
                            } else if (success == 'false') {


                                var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                                var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                                Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                            }

                        },
                        failure: function () {
                            var result = res.responseXML;
                            var success = Ext.DomQuery.selectValue('message @success', result);
                            if (success == 'false') {
                                var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                                var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                                Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                            } else {
                                me.lookupReference('authsave').disable();
                            }
                        }
                    });
                }
            });
        } else {
            var invalidItemselector = false;
            Ext.suspendLayouts();
            view.getForm().getFields().filterBy(function (field) {
                if (field.validate()) return;
                if (field.xtype == 'itemselector' && field.wasValid == false)
                    invalidItemselector = true;
            });
            Ext.resumeLayouts(true);
            if (invalidItemselector) {
                Ext.Msg.alert(l10n('error'), l10n('selected-types-not-empty'));
            }
        }
    },

    onClickConnectionTest: function (btn) {
        var me = this,
            view = me.view,
            viewtype = btn.viewtype;



        if (view && btn.testLdap) {
            var form = view.down('form[reference=ldapconnectionform]');
            if (!form.getForm().isValid()) {
                Ext.Msg.alert(l10n('error'), l10n('you-must-fill-in-the-mandatory-fields'));
                return;
            }
            var vals = form.getValues();
            vals.ldapflag = 'true';
            var saveBtn = Ext.getCmp('authsave');

            Ext.Ajax.request({
                url: 'testldapauthentication.ajax',
                method: 'POST',
                params: vals,
                success: function (res) {
                    var result = res.responseXML;
                    if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                        var overlay = Ext.widget('ldapoverlay', {
                            viewtype: viewtype,
                            viewVals: vals,
                            saveBtn: saveBtn
                        });
                        if (overlay) {
                            overlay.show();
                        }




                    } else {
                        Ext.Msg.alert(l10n('message'), l10n('connection-test-failed'));
                        saveBtn.disable();
                        Ext.getCmp('isldapconnectiontestsuccess').setValue(false);
                    }
                },
                failure: function (res) {
                    Ext.Msg.alert(l10n('error'), l10n('timeout'));
                }
            });
        }else if (view && btn.testrest) {
        	var vals = {};
        	var saveBtn = Ext.getCmp('authsave');
        	if (viewtype == 'restwebserver'){
        		var form = view.down('form[reference=restwebserviceconnectionform]');
        		if (!form.getForm().isValid()) {
                    Ext.Msg.alert(l10n('error'), l10n('you-must-fill-in-the-mandatory-fields'));
                    return;
                }
        		vals = form.getValues();
                vals.restFlag = 'true';
        	}
            var overlay = Ext.widget('ldapoverlay', {
                viewtype: viewtype,
                viewVals: vals,
                saveBtn: saveBtn
            });
            if (overlay) {
                overlay.show();
            }
        } else {
        	var vals = {};
        	var saveBtn = Ext.getCmp('authsave');
        	if (viewtype == 'webserver'){
        		var form = view.down('form[reference=webserviceform]');
        		if (!form.getForm().isValid()) {
                    Ext.Msg.alert(l10n('error'), l10n('you-must-fill-in-the-mandatory-fields'));
                    return;
                }
        		vals = form.getValues();
                vals.wsflag = 'true';
        	}
            var overlay = Ext.widget('ldapoverlay', {
                viewtype: viewtype,
                viewVals: vals,
                saveBtn: saveBtn
            });
            if (overlay) {
                overlay.show();
            }
        }
    },

    /****
     * @ 
     * @param {Object} btn
     */
    onClickSaveOverlay: function (btn) {
        var me = this,
            view = me.view,
            form = me.lookupReference('ldapform'),
            viewtype = btn.viewtype,
            url = 'testldapuserauthentication.ajax',
            title = '';

        switch (viewtype) {
            case 'ldap':
                url = 'testldapuserauthentication.ajax';
                break;
            case 'webserver':
                url = 'testwsuserauthentication.ajax';
                break;
            case 'restwebserver':
                url = 'testrestwsuserauthentication.ajax';
                break;
            case 'testattribute':
                url = 'testldapusermapping.ajax';
                break;
        }
        if (form && form.getForm().getValues()) {
            var values = form.getForm().getValues();
            var formVals = form.viewVals;

            for (var value in values) {
                formVals[value] = values[value];
            }

            Ext.Ajax.request({
                url: url,
                method: 'POST',
                params: formVals,
                success: function (submitForm, action) {

                    var connectionformtmp = Ext.ComponentQuery.query('form[reference=ldapconnectionform]')[0];
                    if (connectionformtmp) {
                        formVals.isldapconnectiontestsuccess = 'true';
                        connectionformtmp.getForm().setValues(formVals);
                    }
                  
                    
                    if (viewtype == 'webserver'){
                    	var xmlResponse = submitForm.responseXML;
                        var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                        if (success == 'true') {
                            Ext.Msg.alert(l10n('message'), l10n('test-passed'));
                            connectionformtmp = Ext.ComponentQuery.query('form[reference=webserviceform]')[0];
                            if (connectionformtmp) {
                                formVals.iswsconnectiontestsuccess = 'true';
                                connectionformtmp.getForm().setValues(formVals);
                                view.close();
                                if (view.saveBtn.up('form').down('itemselector').value.length) {
                                    view.saveBtn.enable();
                                }
                           }
                        } else {
                            Ext.Msg.alert(l10n('error'), l10n('test-failer'));
                            view.saveBtn.disable();
                        }
                    } else if (viewtype == 'testattribute') {
                        var xmlResponse = submitForm.responseXML;
                        var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                        if (success == 'true') {
                            var results = '';
                            var fields = submitForm.responseXML.getElementsByTagName('field');
                            results += '<table cellspacing="0" cellpadding="0" border="0"><tbody>';
                            for (var counter = 0; counter < fields.length; counter++) {

                                if (Ext.DomQuery.selectValue('msg', fields[counter]) != null) {
                                    if (Ext.DomQuery.selectValue('key', fields[counter]) == 'thumbNail') {
                                        results += '<tr><td align="right">' + Ext.DomQuery.selectValue('id', fields[counter]) + '</td><td>&nbsp;:&nbsp;</td><td><b><img height="100" width="100" alt="Embedded Image" src="data:image/png;base64,' + Ext.DomQuery.selectValue('msg', fields[counter]) + '"></img></b></td></tr>';
                                    } else {
                                        results += '<tr><td align="right">' + Ext.DomQuery.selectValue('id', fields[counter]) + '</td><td>&nbsp;:&nbsp;</td><td><b>' + Ext.DomQuery.selectValue('msg', fields[counter]) + '</b></td></tr>';
                                    }
                                }
                            };
                            results += '</tbody></table>';
                            Ext.Msg.show({
                                title: l10n('ldap-att-mapping-results'),
                                msg: results,
                                minWidth: 400,
                                minHeight: 400,
                                scrollable: true,
                                modal: true,
                                icon: Ext.Msg.INFO,
                                buttons: Ext.Msg.OK,
                                fn: function () {
                                    view.close();
                                    if (view.saveBtn.up('form').down('itemselector').value.length) {
                                        view.saveBtn.enable();
                                    }
                                }
                            });
                        } else {
                            Ext.Msg.alert(l10n('error'), l10n('test-failer'));
                        }
                    } else {
                        var xmlResponse = submitForm.responseXML;
                        var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                        if (success == 'true') {
                            Ext.Msg.alert(l10n('message'), l10n('test-passed'), function () {
                                view.close();
                                var authForm = view.saveBtn.up('form');
                                var ldapItemSelector = authForm.down('itemselector');
                                if (ldapItemSelector) {
                                    if (ldapItemSelector.value.length) {
                                        view.saveBtn.enable();
                                    } else {
                                        view.saveBtn.disable();
                                    }
                                } else {
                                    //for PKI ldap connection test which dont have item selector

                                    var pkiform = authForm.down('form[reference=pkiform]');
                                    if (pkiform && pkiform.isValid()) {
                                        view.saveBtn.enable();
                                    }
                                }


                            });
                        } else {
                              if (viewtype == 'restwebserver'){
                    	   
                        var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                        Ext.Msg.alert(responseId.textContent, responseMsg.textContent, function () {
                            view.saveBtn.disable();
                        });
                   
                    
                    }else{

                    

                            Ext.Msg.alert(l10n('message'), l10n('test-failer'), function () {
                                view.saveBtn.disable();
                            });
                        }
                        }
                    }
                },
                failure: function (submitForm, action) {
                    var xmlResponse = submitForm.responseXML;
                    var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                    if (success == 'false') {
                        var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                        Ext.Msg.alert(responseId.textContent, responseMsg.textContent, function () {
                            view.saveBtn.disable();
                        });
                    }
                    if (success == 'true') {
                        Ext.Msg.alert(l10n('message'), l10n('test-passed'), function () {
                            view.close();
                            if (view.saveBtn.up('form').down('itemselector').value.length) {
                                view.saveBtn.enable();
                            }
                        });
                    }
                }
            });
        }
    },

    onClickSaveAttributeGrid: function (btn) {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel(),
            popupWindow = btn.popupWindow,
            gridStore = popupWindow === 'ldap' ? viewModel.getStore('ldapMappingStore') : viewModel.getStore('samlMappingStore'),
            recs = gridStore.getModifiedRecords(),
            successCount = 0,
            responseCount = 0;

        if (recs.length) {
            Ext.Array.each(recs, function (rec, index) {
                var params = {
                    mappingID: rec.get('mappingID'),
                    defaultAttributeValue: Ext.util.Format.stripTags(rec.get('defaultAttributeValue'))
                };

                if (popupWindow === 'ldap') {
                    params['ldapAttributeName'] = Ext.util.Format.stripTags(rec.get('ldapAttributeName'));
                } else {
                    params['idpAttributeName'] = Ext.util.Format.stripTags(rec.get('idpAttributeName'));
                }

                Ext.Ajax.request({
                    url: popupWindow === 'ldap' ? 'saveldapmapping.ajax' : 'savesamlmapping.ajax',
                    params: params,
                    success: function (res) {
                        var result = res.responseXML,
                            errorMsg = '',
                            success = Ext.DomQuery.selectValue('message @success', result);

                        responseCount++;
                        if (success == "false") {
                            var errorsNode = Ext.DomQuery.select('message/errors/field', response.responseXML);
                            for (var i = 0; i < errorsNode.length; i++) {
                                errors += Ext.DomQuery.selectValue('id', errorsNode[i]) + ' - ' + Ext.DomQuery.selectValue('msg', errorsNode[i]) + '<br>';
                            }
                            errorMsg += '<br>' + errors;
                            Ext.Msg.alert(l10n('message'), errorMsg, function () { });
                        } else {
                            successCount++;
                        }
                        if (successCount == recs.length && responseCount == successCount) {
                            gridStore.commitChanges();
                            view.close();
                        }

                    }
                });
            });
        } else {
            view.close();
        }
    },
    enableAuthSaveButton: function () {

        //
        Ext.getCmp('authsave').enable();

    },
    disableAuthSaveButton: function () {
        Ext.getCmp('authsave').disable();

    },
    onEnterSaveAttribute: function (textfield, eo) {
        if (eo.getCharCode() == Ext.EventObject.ENTER) {
            var me = this,
                view = me.view,
                viewModel = me.getViewModel(),
                gridStore = viewModel.getStore('ldapMappingStore'),
                recs = gridStore.getModifiedRecords(),
                successCount = 0,
                responseCount = 0;

            if (recs.length) {
                Ext.Array.each(recs, function (rec, index) {
                    Ext.Ajax.request({
                        url: 'saveldapmapping.ajax',
                        params: {
                            mappingID: rec[r].get('mappingID'),
                            ldapAttributeName: rec[r].get('ldapAttributeName'),
                            defaultAttributeValue: rec[r].get('defaultAttributeValue')
                        },
                        success: function (res) {
                            var result = res.responseXML,
                                errorMsg = '',
                                success = Ext.DomQuery.selectValue('message @success', result);

                            responseCount++;
                            if (success == "false") {
                                var errorsNode = Ext.DomQuery.select('message/errors/field', response.responseXML);
                                for (var i = 0; i < errorsNode.length; i++) {
                                    errors += Ext.DomQuery.selectValue('id', errorsNode[i]) + ' - ' + Ext.DomQuery.selectValue('msg', errorsNode[i]) + '<br>';
                                }
                                errorMsg += '<br>' + errors;
                            } else {
                                successCount++;
                            }
                            if (successCount == recs.length && responseCount == successCount) {
                                gridStore.commitChanges();
                                view.hide();
                            } else {
                                Ext.Msg.alert(l10n('message'), errorMsg, function () { });
                            }

                        }
                    });
                });
            }
        }
    },

    authFormRender: function () {
        var scope = this;
        scope.getViewModel().getStore("authTypeStore").load({
            params: {

            },
            callback: function (records, operation, success) {

                if (success == true) {

                }
                if (success == false) {

                }
            }
        });
    },
    samlProvisiontypeComboSelect: function (combo, records, eOpts) {
        if (combo.getValue() == 0) {
            this.lookupReference('samlmappingbtn').hide();

            this.lookupReference('idpAttributeForUsername').show();
            this.lookupReference('idpAttributeForUsername').enable();

            var samlIdpMetadataVal = this.lookupReference('samlIdpMetadata').getValue();
            var idpAttributeForUsernameVal = this.lookupReference('idpAttributeForUsername').getValue();
            var samlSpEntityId = this.lookupReference('samlSpEntityId').getValue();

        } else if (combo.getValue() == 1) {
            this.lookupReference('samlmappingbtn').show();
            this.lookupReference('idpAttributeForUsername').hide();
            this.lookupReference('idpAttributeForUsername').disable();
        }
        //  this.lookupReference('samlmappingflag').setValue(combo.getValue());
    },

    ldapmappingCollapse: function (p) {
        p.items.each(function (i) {
            if (i instanceof Ext.form.Field) {
                i.disable();
            }
        }, this);
        var form = this.view.getForm();
        var ldapmappingflag = form.findField('ldapmappingflag');
        ldapmappingflag.setValue('false');
    },
    ldapmappingExpand: function (p) {
        p.items.each(function (i) {
            if (i instanceof Ext.form.Field) {
                i.enable();
            }
        }, this);
        var form = this.view.getForm();
        var ldapmappingflag = form.findField('ldapmappingflag');
        ldapmappingflag.setValue('true');
    },
    windowOnRender: function () {
        var view = this.getView(),
            viewtype = view.viewtype;
        switch (viewtype) {
            case 'ldap':
                view.getViewModel().set('windowTitle', l10n('test-ldap-user-authentication'));
                break;
            case 'restwebserver':
                view.getViewModel().set('windowTitle', 'Test REST user authentication');
                break;
            case 'webserver':
                view.getViewModel().set('windowTitle', l10n('test-ws-user-authentication'));
                break;
            case 'testattribute':
                view.getViewModel().set('windowTitle', l10n('test-ldap-attributes-mapping'));
                break;
            case 'editLDAPattribute':
                view.getViewModel().set('windowTitle', l10n('ldap-attributes-mapping'));
                break;
            case 'editSAMLAttribute':
                view.getViewModel().set('windowTitle', l10n('saml-idp-attribute-mapping'));
                break;
        }
    },
    ldapAttributemappingLoad: function () {
        var store = this.getViewModel().getStore("ldapAtrributGridStore");
        var records = this.getView().records;
        store.load({
            params: {
                mappingID: records.data.mappingID
            }
        });
    },
    ldapAttrionLoad: function (store) {
        if (store.getCount() == 0) {
            this.lookupReference("ldapAttSave").disable();
        }
    },
    ldapAttrSave: function () {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel(),
            gridStore = viewModel.getStore('ldapAtrributGridStore'),
            recs = gridStore.getModifiedRecords(),
            successCount = 0,
            responseCount = 0;

        if (recs.length) {
            Ext.Array.each(recs, function (rec, index) {
                Ext.Ajax.request({
                    url: 'saveldapvaluemapping.ajax',
                    params: {
                        valueID: rec.get('valueID'),
                        mappingID: rec.get('mappingID'),
                        vidyoValueName: Ext.util.Format.stripTags(rec.get('vidyoValueName')),
                        ldapValueName: Ext.util.Format.stripTags(rec.get('ldapValueName'))
                    },
                    success: function (res) {
                        var result = res.responseXML,
                            errorMsg = '',
                            success = Ext.DomQuery.selectValue('message @success', result);

                        responseCount++;
                        if (success == "false") {
                            var errorsNode = Ext.DomQuery.select('message/errors/field', response.responseXML);
                            for (var i = 0; i < errorsNode.length; i++) {
                                errors += Ext.DomQuery.selectValue('id', errorsNode[i]) + ' - ' + Ext.DomQuery.selectValue('msg', errorsNode[i]) + '<br>';
                            }
                            errorMsg += '<br>' + errors;
                            Ext.Msg.alert(l10n('message'), errorMsg, function () { });
                        } else {
                            successCount++;
                        }
                        if (successCount == recs.length && responseCount == successCount) {
                            gridStore.commitChanges();
                            view.hide();
                        }

                    }
                });
            });
        } else {
            view.hide();
        }
    },
    ldapAttributeCancel: function () {
        this.getView().close();
    },
    ldapAttrGridClick: function (view, td, cellIndex, record, tr, rowIndex, e, eOpts) {
        var dataIndex = view.headerCt.getGridColumns()[cellIndex].dataIndex;
        this.getViewModel().set("rowIndex", rowIndex);
        if (dataIndex == 'vidyoValueName') {
            this.lookupReference("duplicateBtn").enable();
            this.lookupReference("deleteBtn").enable();
        } else {
            this.lookupReference("duplicateBtn").disable();
            this.lookupReference("deleteBtn").disable();
        }
    },
    duplicateldapAttr: function (btn) {
        var me = this,
            grid = btn.up('grid'),
            store = me.getViewModel().getStore("ldapAtrributGridStore"),
            rowIndex = me.getViewModel().get("rowIndex"),
            record = grid.getStore().getAt(rowIndex);
        store.insert(rowIndex + 1, {
            valueID: '0',
            mappingID: record.get('mappingID'),
            vidyoValueName: record.get('vidyoValueName'),
            ldapValueName: ''
        });
    },
    deleteldapAttr: function () {
        var me = this;
        var rowIndex = this.getViewModel().get("rowIndex");
        var record = this.getViewModel().getStore("ldapAtrributGridStore").getAt(rowIndex); //rowIndex
        if (record.get('valueID') != '0') {
            Ext.Ajax.request({
                url: 'removeldapvaluemapping.ajax',
                waitMsg: l10n('saving'),
                params: {
                    valueID: record.get('valueID')
                },
                success: function (form, action) {
                    me.getViewModel().getStore("ldapAtrributGridStore").removeAt(rowIndex);
                },
                failure: function (form, action) {
                    var errorMsg = l10n('save-failed');
                    var errors = '';
                    if (!action.result.success) {
                        for (var i in action.result.errors) {
                            errors += action.result.errors[i].id + ' - ' + action.result.errors[i].msg + '<br>';
                        }
                    }
                    Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + errors, function () { });
                }
            });
        } else {
            this.getViewModel().getStore("ldapAtrributGridStore").removeAt(rowIndex);
        }
    },
    onSamlAttrMappingGridRender: function () {
        var record = this.getView().record;
        var samlAttrMappingGridStore = this.getViewModel().getStore('samlAttrMappingGridStore');
        samlAttrMappingGridStore.load({
            params: {
                mappingID: record.get('mappingID')
            }
        });
    },

    duplicateSamlAttrMapping: function () {
        var grid = this.lookupReference('samlAttrMappingGrid'),
            record = grid.getSelection()[0],
            duplicateRecord = Ext.create('AdminApp.model.settings.SamlAttrMapping', {
                valueID: '0',
                mappingID: record.get('mappingID'),
                vidyoValueName: record.get('vidyoValueName'),
                idpValueName: ''
            }),

            gridEditPlugin = grid.getPlugins()[0],
            gridStore = grid.getStore();

        gridEditPlugin.cancelEdit();
        gridStore.insert(gridStore.indexOf(record) + 1, duplicateRecord);
        gridEditPlugin.startEdit(gridStore.indexOf(record) + 1);
    },

    removeSamlAttrMapping: function () {
        var grid = this.lookupReference('samlAttrMappingGrid'),
            gridEditPlugin = grid.getPlugins()[0],
            record = grid.getSelection()[0],
            gridStore = grid.getStore();

        if (record.get('valueID') != '0') {
            Ext.Ajax.request({
                url: 'removesamlvaluemapping.ajax',
                waitMsg: l10n('saving'),
                params: {
                    valueID: record.get('valueID')
                },
                success: function (form, action) {
                    gridEditPlugin.cancelEdit();
                    gridStore.remove(record);
                },
                failure: function (form, action) {
                    var errorMsg = l10n('save-failed');
                    var errors = '';
                    if (!action.result.success) {
                        for (var i in action.result.errors) {
                            errors += action.result.errors[i].id + ' - ' + action.result.errors[i].msg + '<br>';
                        }
                    }
                    Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + errors, function () { });
                }
            });
        } else {
            gridEditPlugin.cancelEdit();
            gridStore.remove(record);
        }
    },

    onSamlAttrMappingGridCellClick: function (grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
        var fieldName = grid.headerCt.getHeaderAtIndex(cellIndex).dataIndex,
            dublicateAttrBtn = this.lookupReference('dublicateAttrBtn'),
            removeAttrBtn = this.lookupReference('removeAttrBtn');
        if (fieldName == 'vidyoValueName') {
            dublicateAttrBtn.enable();
            removeAttrBtn.enable();
        } else {
            dublicateAttrBtn.disable();
            removeAttrBtn.disable();
        }
    },

    saveSamlAttrMapping: function () {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel(),
            gridStore = viewModel.getStore('samlAttrMappingGridStore'),
            recs = gridStore.getModifiedRecords(),
            successCount = 0,
            responseCount = 0;

        if (recs.length) {
            Ext.Array.each(recs, function (rec, index) {
                var params = {
                    valueID: rec.get('valueID'),
                    mappingID: rec.get('mappingID'),
                    vidyoValueName: Ext.util.Format.stripTags(rec.get('vidyoValueName')),
                    idpValueName: Ext.util.Format.stripTags(rec.get('idpValueName'))
                };

                Ext.Ajax.request({
                    url: 'savesamlvaluemapping.ajax',
                    params: params,
                    success: function (res) {
                        var result = res.responseXML,
                            errorMsg = '',
                            success = Ext.DomQuery.selectValue('message @success', result);

                        responseCount++;
                        if (success == "false") {
                            var errorsNode = Ext.DomQuery.select('message/errors/field', response.responseXML);
                            for (var i = 0; i < errorsNode.length; i++) {
                                errors += Ext.DomQuery.selectValue('id', errorsNode[i]) + ' - ' + Ext.DomQuery.selectValue('msg', errorsNode[i]) + '<br>';
                            }
                            errorMsg += '<br>' + errors;
                        } else {
                            successCount++;
                            errorMsg = "Saved";
                        }
                        if (successCount == recs.length && responseCount == successCount) {
                            gridStore.commitChanges();
                            view.close();
                        } else {
                            Ext.Msg.alert(l10n('message'), errorMsg, function () { });
                        }

                    }
                });
            });
        } else {
            view.close();
        }
    },

    samlSPMetadata: function () {
        var me = this;
        var samlSPMetadataWindow = Ext.create('Ext.window.Window', {
            layout: 'fit',
            width: 900,
            height: 600,
            title: l10n('view-service-provider-sp-metadata-xml'),

            scrollable: true,
            viewController: me,
            closeAction: 'hide',
            reference: 'viewServiceProviderWin',
            items: [
                {
                    xtype: 'textareafield',
                    reference: 'samlSPMetadata',

                }],
            buttonAlign: 'center',
            buttons: [{
                text: 'Close',
                handler: function () {
                    samlSPMetadataWindow.close();
                }
            }],
            listeners: {
                render: function () {
                    var me = this;
                    var authFormValues = this.viewController.getView().getForm().getValues();
                    var params = {
                        samlSecurityProfile: authFormValues['samlSecurityProfile'],
                        samlSSLProfile: authFormValues['samlSSLProfile'],
                        samlSignMetadata: authFormValues['samlSignMetadata'],
                        samlmappingflag: authFormValues['samlmappingflag'],
                        idpAttributeForUsername: authFormValues['idpAttributeForUsername'],
                        samlSpEntityId: authFormValues['samlSpEntityId']
                    };
                    Ext.Ajax.request({
                        //url: 'authentication_saml_view.ajax?samlSecurityProfile='+ authFormValues['samlSecurityProfile'] + '&samlSSLProfile=' + authFormValues['samlSSLProfile'] + '&samlSignMetadata=' + authFormValues['samlSignMetadata'],
                        url: 'authentication_saml_view.ajax',
                        method: 'GET',
                        params: params,
                        success: function (resp, action) {


                            me.down('textareafield[reference=samlSPMetadata]').setValue(Ext.String.htmlDecode(Ext.String.trim(resp.responseText)));
                        },
                        failure: function (resp, action) {
                            var errorMsg = l10n('save-failed');
                            var errors = '';
                            /*if (!action.result) {
                                for (var i in action.result.errors) {
                                    errors += action.result.errors[i].id + ' - ' + action.result.errors[i].msg + '<br>';
                                }
                            }*/
                            Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                        }
                    });
                }
            }
        });
        samlSPMetadataWindow.show();

    },
    cancelSamlAttrMapping: function () {
        this.getView().close();
    },
    onClickCloseAttributeGrid: function () {
        this.getView().close();
    },
    pkiAuthTypeChange: function (authType) {
        var pkildappanel = this.view.lookupReference('pkildappanel');
        if (authType && authType.getValue() == '1') {
            if (!this.view.down('ldapconnection')) {
                var ldapView = Ext.create('AdminApp.view.settings.auth.LDAPConnection');

                pkildappanel.add(ldapView);
            }
            Ext.getCmp('authsave').disable();
        } else {
            pkildappanel.removeAll();
            Ext.getCmp('authsave').enable();
        }
    },
    ocspoverriderspderchk: function (form, newval, old) {
        var ocsprespondertxtfld = this.view.lookupReference('ocspresponder');

        if (newval) {
            ocsprespondertxtfld.setFieldLabel('<span class="red-label">*</span>' + 'Override Responder');
            ocsprespondertxtfld.allowBlank = false;
              Ext.apply(ocsprespondertxtfld, {vtype: 'url'});
            ocsprespondertxtfld.validateValue(ocsprespondertxtfld.getValue());

        } else {
            ocsprespondertxtfld.setFieldLabel('Default Responder(Optional)');
            ocsprespondertxtfld.allowBlank = true;
             Ext.apply(ocsprespondertxtfld, {vtype: ''});
            ocsprespondertxtfld.validateValue(ocsprespondertxtfld.getValue());
        }
    },
    appendCACCertificate: function (btn) {
        var me = this,
            view = me.view;
        var certficategridform = view.down('form[reference=certficategrid]');
        var pkitabpanel = view.down('tabpanel[reference=pkitabpanel]');
        certficategridform.setHidden(false);
        pkitabpanel.setHidden(false);
        this.uploadWind = view.add({
            xtype: 'pkicertificate-upload',
            actionType: 'append',
            viewModel: {
                data: {
                    title: 'Add/Append Certificate'

                }
            },
            session: true

        });
        if (btn && !(btn == "ok")) {
            this.uploadWind.showBy(btn);
        } else {
            this.uploadWind.show(true);
        }

    },
    replaceCACCertificate: function (btn) {
        var me = this,
            view = me.view;
        var certficategridform = view.down('form[reference=certficategrid]');
        var pkitabpanel = view.down('tabpanel[reference=pkitabpanel]');
        certficategridform.setHidden(false);
        pkitabpanel.setHidden(false);
        this.uploadWind = view.add({
            xtype: 'pkicertificate-upload',
            actionType: 'replace',
            viewModel: {
                data: {
                    title: 'Add/Append Certificate'

                }
            },
            session: true
        });

        if (btn) {
            this.uploadWind.showBy(btn);
        } else {
            this.uploadWind.show(true);
        }
    },
    exportCACCerficates: function (btn) {
        if (btn.btnType == 'stage') {
            window.open('exportcaccertificate.ajax?stageFlag=true');
        } else {
            window.open('exportcaccertificate.ajax?stageFlag=false');
        }
    },

    afterCertficatePermLoad: function (ob, records, successful, operation, eOpts) {
        var btn = Ext.ComponentQuery.query('button[reference=exportPerm]')[0];
        if (records.length == 0) {
            if (btn) {
                btn.disable();
            }
        } else {
            if (btn) {
                btn.enable();
            }
        }
    },
    enableDisableExport: function (btn) {
        var me = this,
            viewModel = me.getViewModel();

        if (btn.btnType == 'stage') {
            var certficateStore = viewModel.getStore('certificateStore');
            if (certficateStore && certficateStore.getCount() == 0) {
                btn.disable();
            }
            if (certficateStore && certficateStore.getCount() != 0) {
                btn.enable();
            }
        } else {
            var certficateStore = viewModel.getStore('certificateStorePERM');
            if (certficateStore && certficateStore.getCount() == 0) {
                btn.disable();
            }
            if (certficateStore && certficateStore.getCount() != 0) {
                btn.enable();
            }
        }
    },
    afterCertficateStgLoad: function (ob, records, successful, operation, eOpts) {

        var btn = Ext.ComponentQuery.query('button[reference=exportStg]')[0];
        if (records.length == 0) {
            if (btn) {
                btn.disable();
            }
        } else {
            if (btn) {
                btn.enable();
            }
        }
    },
    uploadCACCertificate: function (btn, v) {
        var me = this,
            viewModel = me.getViewModel(),
            uploadWind = this.uploadWind,
        

            form = this.lookupReference("crtuploadform");
        parameters = form.getForm()
            .findField('certificatefilefield').getValue();

        parameters = parameters.replace(/^.*(\\|\/|\:)/, '');

        if (form.getForm().isValid()) {
            form.getForm().submit({
                url: 'uploadcaccertificate.ajax',
                waitTitle: l10n('uploading-new-server-software-file'),
                waitMsg: l10n('your-server-software-file-is-being-uploaded'),
                params: {
                    actionType: btn.actionType,

                    _csrf: csrfToken
                },

                success: function (form, action) {
                    var xmlResponse = action.response.responseXML;

                    var success = Ext.DomQuery.selectValue('message @success', xmlResponse);

                    if (success == "false") {
                        uploadWind.close();
                        var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                        Ext.Msg.alert(l10n('error'), Ext.String.htmlEncode(responseMsg.textContent));
                    }



                    if (success == "true") {
                        
                      var notificationLabel = Ext.ComponentQuery.query('label[reference=notificationlabel]')[0];
                       notificationLabel.setText( l10n('pki-approval-notification'));
                       
                        notificationLabel.setHidden(false);
                    
                

     
                        uploadWind.close();
                        me.refreshCertificateStore();

                    }





                },
                failure: function (frm, action) {


                    uploadWind.close();




                }

            });
        }
    },



    refreshCertificateStorePerm: function () {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();

            //calling grid store to fix the issue VPTL 6351

         var grid=Ext.ComponentQuery.query('grid[reference=certficategridPerm]')[0];
     grid.getStore().reload({
     callback: function(){
        grid.getView().refresh();
    }
    });
    },

    refreshCertificateStore: function () {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();

     var grid=Ext.ComponentQuery.query('grid[reference=certficategridTemp]')[0];
     grid.getStore().reload({
     callback: function(){
        grid.getView().refresh();
    }
    });
    }

});
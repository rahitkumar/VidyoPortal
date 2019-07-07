Ext.define('AdminApp.view.users.UsersController', {
    extend: 'Ext.app.ViewController',

    alias: 'controller.usersController',

    onUsersRender: function() {
        var me = this,
            view = me.view,
            usersContainer = me.lookupReference('usersContainer');

        Ext.defer(function() {
            usersContainer.getLayout().setActiveItem(view.down('manageusersview'));
        }, 10);
        this.resetFilter();
        this.usersGridLoad();
    },
    onManageUsersRowClick: function(grid, record, tr, rowIndex, e, eOpts) {
        var memberID = record.get("memberID");
        var roleName = record.get("roleName");

        if (e.target.className == "member-name") {
            this.getUsersForEdit(memberID, roleName);
        }
    },
    resetFilter: function() {
        this.lookupReference('memberNameFilter').reset();
        this.lookupReference('extFilter').reset();
        this.lookupReference('typeFilter').reset();
        this.lookupReference('groupNameFilter').reset();
        this.lookupReference('enableStatus').reset();
    },
    addUserView: function() {
        var scope = this;
        var addUsersWin = Ext.create('AdminApp.view.users.AddUsers', {
            floatParent: scope.view
        });
        //.show();
        this.showAddUsersView(addUsersWin, 0, '');
    },
    addLegacyDeviceView: function() {
        var scope = this;
        var addLegacyDeviceWin = Ext.create('AdminApp.view.users.AddLegacyDevice', {
            floatParent: scope.view
        });
        addLegacyDeviceWin.show();
        this.showAddLegacyDeviceView(0, '');
    },
    importUsersView: function() {
        var scope = this;
        var importWin = Ext.create('AdminApp.view.users.ImportUsers', {
            floatParent: scope.view
        });
        importWin.show();
    },
    exportUsersView: function() {
        var scope = this;
        var exportWin = Ext.create('AdminApp.view.users.ExportUsers', {
            floatParent: scope.view
        });
        exportWin.show();
    },
    getUsersForEdit: function(memberID, roleName) {
        if (roleName == "Legacy") {
            var scope = this;
            var addLegacyDeviceWin = Ext.create('AdminApp.view.users.AddLegacyDevice', {
                floatParent: scope.view
            });
            addLegacyDeviceWin.show();
            this.showAddLegacyDeviceView(memberID, roleName);
        } else {
            var scope = this;
            var addUsersWin = Ext.create('AdminApp.view.users.AddUsers', {
                floatParent: scope.view
            });
            this.showAddUsersView(addUsersWin, memberID, roleName);
        }
    },

    showAddLegacyDeviceView: function(memberId, roleName) {
        var scope = this;
        scope.getViewModel().set("memberID", memberId);
        this.lookupReference("addLegacyDeviceForm").getForm().reset();
        if (memberId == '0') {
            scope.getViewModel().set("roomID", 0);
            scope.getViewModel().set("deviceNameReadOnly", false);
            scope.getViewModel().set("title", l10n('add-legacy-device-new-legacy-device'));
        } else {
            scope.getViewModel().set("deviceNameReadOnly", true);
        }
        this.lookupReference("addLegacyDeviceForm").getForm().load({
            url: "legacy.ajax?memberID=" + memberId,
            method: "GET",
            waitMsg: 'Loading...',
            success: function(form, action) {
                var xmlResponse = action.response.responseXML;
                var memberName = Ext.DomQuery.selectNode('memberName', xmlResponse);
                if (memberId == '0') {
                    scope.getViewModel().set("title", l10n('add-legacy-device-new-legacy-device'));
                } else {
                    scope.getViewModel().set("title", l10n('edit-legacy-device') + ": " + Ext.String.htmlEncode(memberName.textContent));
                }
                scope.getViewModel().set('roleName', roleName);
                scope.lookupReference("roomTypeID").setValue(3);
                scope.lookupReference("roleID").setValue(6);
            },
            failure: function() {
                Ext.Msg.alert(l10n('failure'), l10n('unable-to-fetch-data'));
            }
        });
    },
    showAddUsersView: function(view, memberId, roleName) {
        var scope = this,
            addUsersForm = scope.lookupReference("addUsersForm");
        scope.getViewModel().set("memberIdVal", memberId);
        this.lookupReference("addUsersForm").getForm().reset();

        if (memberId == '0') {
            scope.getViewModel().set("title", l10n('add-user') + ': ' + l10n('new-user'));
            scope.getViewModel().set("roomIdVal", 0);
            scope.getViewModel().set("userNameReadOnly", false);
        } else {
            scope.getViewModel().set("userNameReadOnly", true);
        }

        //scope.view.setLoading(true);

        scope.loadUserTypeCombo();
        scope.groupComboLoad();
        scope.proxyStoreLoad();
        scope.locationtagComboLoad();
        scope.langPreferenceStoreLoad();

        Ext.defer(function() {
            loadUserData();
        }, 500);

        var loadUserData = function() {
            addUsersForm.getForm().load({
                url: "member.ajax?memberID=" + memberId,
                method: 'GET',
                success: function(form, action) {
                    var xmlResponse = action.response.responseXML;
                    var tenantPrefix = Ext.DomQuery.selectValue('tenantPrefix', xmlResponse);
                    tenantPrefix = Ext.String.htmlEncode(tenantPrefix);
                    var roomExtNumber = Ext.DomQuery.selectValue('roomExtNumber', xmlResponse);
                    roomExtNumber = Ext.String.htmlEncode(roomExtNumber);
                    var importedUsed = Ext.DomQuery.selectValue('importedUsed', xmlResponse);
                    var memberName = Ext.DomQuery.selectValue('memberName', xmlResponse);
                    memberName = Ext.String.htmlEncode(memberName);
                    var enable = Ext.DomQuery.selectValue('enable', xmlResponse);
                    var roleId = Ext.DomQuery.selectValue('roleID', xmlResponse);
                    var roomID = Ext.DomQuery.selectValue('roomID', xmlResponse);
                    var hideImageDelete=Ext.DomQuery.selectValue('hideImageDelete', xmlResponse);
                    var userImageAllowed = Ext.DomQuery.selectValue('userImageAllowed', xmlResponse);
                    var isLdapEnabled = Ext.DomQuery.selectValue('ldap', xmlResponse);
                    var isUsrImgEnbledAdmin = Ext.DomQuery.selectValue('isUsrImgEnbledAdmin', xmlResponse);
                    var isUsrImgUpldEnbledAdm = Ext.DomQuery.selectValue('isUsrImgUpldEnbledAdm', xmlResponse);
                    var neoRoomPermanentPairingDeviceUser = Ext.DomQuery.selectValue('neoRoomPermanentPairingDeviceUser', xmlResponse);
                    //reversing the true and false since in database 0-not allowed and 1- for allowed
                    scope.lookupReference("disableUserImageUpld").setValue(userImageAllowed == "0" ? true : false);
                    scope.getViewModel().set("hideImageDelete",hideImageDelete == "false" ? false : true);


                    if (isUsrImgEnbledAdmin == "false") {
                        isUsrImgEnbledAdmin = false;
                    }
                    if (isUsrImgEnbledAdmin == "true") {
                        isUsrImgEnbledAdmin = true;
                    }
                    if (isUsrImgUpldEnbledAdm == "false") {
                        isUsrImgUpldEnbledAdm = false;
                    }
                    if (isUsrImgUpldEnbledAdm == "true") {
                        isUsrImgUpldEnbledAdm = true;
                    }
                    scope.getViewModel().set("isUsrImgEnbledAdmin", isUsrImgEnbledAdmin);
                    scope.getViewModel().set("isUsrImgUpldEnbledAdm", isUsrImgUpldEnbledAdm);
                    var base64Image = Ext.DomQuery.selectValue('thumbNailImage', xmlResponse);
                    if (base64Image != null && base64Image != "") {
                        var imageField = scope.lookupReference("thumbnailImage");
                        imageField.src = "data:image/png;base64," + base64Image;
                    }
                    
                    if (neoRoomPermanentPairingDeviceUser == "false") {
                    	neoRoomPermanentPairingDeviceUser = false;
                    }
                    if (neoRoomPermanentPairingDeviceUser == "true") {
                    	neoRoomPermanentPairingDeviceUser = true;
                    }
                    scope.getViewModel().set("neoRoomPermanentPairingDeviceUser", neoRoomPermanentPairingDeviceUser);
                    
                    //this will hide change password panel
                    if( memberId == '0' || importedUsed === "1" ){
                        scope.getViewModel().set("isLdapEnabled", true);
                    }else{
                           scope.getViewModel().set("isLdapEnabled", false);
                    }

                      scope.getViewModel().set("enable", enable);

                    if (tenantPrefix) {
                        scope.getViewModel().set("tenantPrefix", tenantPrefix);
                    }
                    if (roomExtNumber) {
                        scope.getViewModel().set("currRoomExt", roomExtNumber);
                    }
                    scope.getViewModel().set("importedUsed", importedUsed);
                    scope.getViewModel().set("roomID", roomID);
                    scope.getViewModel().set("roomIdVal", roomID);
                    if (importedUsed == '0') {
                        scope.getViewModel().set("isImportedUsed", false);
                        if (memberId != 0) {
                            scope.getViewModel().set("roleId", roleId);
                            scope.getViewModel().set("title", l10n('edit-user') + ": " + Ext.String.htmlEncode(Ext.DomQuery.selectValue('memberName', xmlResponse)));
                            scope.lookupReference("passwordFieldContainer").setDisabled(true);
                            scope.lookupReference("passwordFieldContainer").setVisible(false);
                            scope.lookupReference("changepasswordFieldContainer").setDisabled(false);
                            scope.lookupReference("changepasswordFieldContainer").setVisible(true);
                        } else {
                            scope.getViewModel().set("isUsrImgEnbledAdmin", false);
                            scope.getViewModel().set("title", l10n('add-user') + ': ' + l10n('new-user'));
                            scope.lookupReference("passwordFieldContainer").setDisabled(false);
                            scope.lookupReference("passwordFieldContainer").setVisible(true);
                            scope.lookupReference("changepasswordFieldContainer").setVisible(false);
                            scope.lookupReference("changepasswordFieldContainer").setDisabled(true);
                        }
                    } else if (importedUsed == '1') {

                        var title = '';
                        scope.lookupReference("passwordFieldContainer").setDisabled(true);
                        scope.lookupReference("passwordFieldContainer").setVisible(false);
                        scope.lookupReference("changepasswordFieldContainer").setDisabled(true);
                        scope.lookupReference("changepasswordFieldContainer").setVisible(false);
                        if (memberId != 0) {
                            scope.getViewModel().set("isImportedUsed", true);
                            title = l10n('edit-user') + " : " + Ext.String.htmlEncode(memberName) + ' <small class="red-label">[' + l10n('auto-provisioned-user') + ']</small>';

                        } else {

                            scope.getViewModel().set("isImportedUsed", false);
                            title = l10n('add-user') + ' : ' + l10n('new-user') + ' <small class="red-label">[' + l10n('auto-provisioned-user') + ']</small>';
                        }
                        scope.getViewModel().set("title", title);
                    }

                    scope.getViewModel().set('roleName', roleName);
                    //scope.view.setLoading(false);
                    var tenantPrefix = scope.lookupReference("tenantPrefix").getValue();
                    if (scope.getViewModel().get("roomID")) {
                        if (scope.getViewModel().get("roomID") == 0 && tenantPrefix != '')
                            scope.lookupReference("tenantPrefix").setValue(tenantPrefix);
                    }
                    view.show();
                },
                failure: function() {
                    Ext.Msg.alert(l10n('failure'), l10n('unable-to-fetch-data'));
                }
            });
        };
    },
    displayToolTip: function(c) {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();
        var flag = viewModel.get("isUsrImgUpldEnbledAdm");
        if (!flag || flag == "false") {
            Ext.QuickTips.register({
                target: c.getEl(),
                text: 'Allowing user to upload image is disabled by admin'
            });
        }
    },
    manageUsersDeletRecord: function() {
        var scope = this;
        var selections = this.lookupReference("manageUsersGrid").getSelectionModel().getSelection();
        if (selections.length == 0) {
            Ext.MessageBox.alert(l10n('error'), l10n('please-select-at-least-one-user-to-delete'));
        } else {
            Ext.MessageBox.confirm(l10n('confirmation'), l10n('do-you-really-want-to-delete-the-selected-users'), scope.doDeleteGroup, scope);
        }
    },
    doDeleteGroup: function(btn) {
        var scope = this;
        if (btn == 'yes') {
            var selections = this.lookupReference("manageUsersGrid").getSelectionModel().getSelection();
            for (var i = 0,
                    len = selections.length; i < len; i++) {
                if (selections[i].get("memberName") != adminUserName) {
                    Ext.Ajax.request({
                        url: 'deletemember.ajax',
                        method: 'POST',
                        params: {
                            memberID: selections[i].get("memberID")
                        },
                        success: function() {
                            scope.getViewModel().getStore("manageUsersGridStore").remove(selections[i]);
                        }
                    });
                }
            }
            setTimeout(function() {
                scope.getViewModel().getStore("manageUsersGridStore").load()
            }, 300);
        }
    },
    addLegacyDeviceSave: function() {
        var scope = this;
        this.lookupReference("addLegacyDeviceForm").getForm().submit({
            url: 'savelegacy.ajax',
            waitMsg: 'Saving...',
            success: function(form, action) {
                var xmlResponse = action.response.responseXML;

                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                if (success == "false") {
                    var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                    var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                    Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                } else if (success == undefined || success == "true") {
                    scope.lookupReference("addLegacyDeviceWin").close();
                    scope.usersGridLoad();
                }

            },
            failure: function(form, action) {
                Ext.Msg.alert(l10n('failure'), l10n('unabletofetchdata'));
            }
        });
    },
    legacyDeviceClose: function() {
        this.lookupReference("addLegacyDeviceForm").getForm().reset();
        this.lookupReference("addLegacyDeviceWin").close();

        this.usersGridLoad();
    },
    importMembersSave: function() {
        var scope = this;

        this.lookupReference("importUsersForm").getForm().submit({
            url: 'importmembers.ajax',
            params: {},
            waitMsg: 'Saving',
            success: function(form, action) {
                var xmlResponse = action.response.responseXML;

                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                var userCreated = Ext.DomQuery.selectValue("message@usercreated", xmlResponse);

                if (success == "false") {
                    var fields = Ext.DomQuery.select("field", xmlResponse);
                    var msgText = "";
                    for (i = 0; i < fields.length; i++) {
                        msgText = msgText + Ext.DomQuery.selectValue('id', fields[i]);
                        msgText = msgText + ": " + Ext.DomQuery.selectValue('msg', fields[i]) + "<br />";
                    }
                    Ext.Msg.alert(l10n('message'), msgText + '<br />Number of users imported: ' + userCreated);
                    scope.usersGridLoad();
                } else {
                    Ext.Msg.alert(l10n('success'), "Number of users imported: " + userCreated, function() {
                        scope.lookupReference('importUsersWin').close();
                        scope.usersGridLoad();
                    });
                }

            },
            failure: function(form, action) {
                Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
            }
        });
    },
    importMembersClose: function() {
        this.lookupReference("importUsersWin").close();
    },
    exportMembersClose: function() {
        this.lookupReference("exportUsersWin").close();
    },
    csvFormatSelect: function(obj, ev, eo) {
        if (obj.inputValue == 'csv') {
            this.lookupReference("formatvalue").setValue('csv');
            this.lookupReference("vebPasswordPanel").hide();
            this.lookupReference("vebPasswordPanel").down("#password").setValue("");
            this.lookupReference("vebPasswordPanel").down("#password2").setValue("");
            this.lookupReference("vebPasswordPanel").down("#password").allowBlank = true;
            this.lookupReference("vebPasswordPanel").down("#password2").allowBlank = true;
            this.lookupReference("vebPasswordPanel").down("#password").validate();
            this.lookupReference("vebPasswordPanel").down("#password2").validate();
        }
    },
    vebFormatSelect: function(obj, ev, eo) {
        if (obj.inputValue == 'veb') {
            this.lookupReference("formatvalue").setValue('veb');
            this.lookupReference("vebPasswordPanel").show();
            this.lookupReference("vebPasswordPanel").down("#password").allowBlank = false;
            this.lookupReference("vebPasswordPanel").down("#password2").allowBlank = false;
            this.lookupReference("vebPasswordPanel").down("#password").validate();
            this.lookupReference("vebPasswordPanel").down("#password2").validate();
        }
    },
    exportUsers: function() {
        var scope = this;
        this.lookupReference("exportUsersForm").submit();
    },
    changePassCollapse: function(p) {
        p.items.each(function(i) {
            if (i instanceof Ext.form.Field) {
                i.disable();
            }
        }, this);
    },
    changePassExpand: function(p) {
        p.items.each(function(i) {
            if (i instanceof Ext.form.Field) {
                i.enable();
            }
        }, this);
    },
    changePassRender: function(p) {
        p.items.each(function(i) {
            if (i instanceof Ext.form.Field) {
                i.disable();
            }
        }, this);
    },
    loadUserTypeCombo: function() {
        var store = this.getViewModel().getStore("UserTypeStore");
        store.load();
    },
    groupComboLoad: function() {
        var store = this.getViewModel().getStore("groupComboStore");
        setTimeout(function() {
            store.load();
        }, 50);
    },
    proxyStoreLoad: function() {
        var store = this.getViewModel().getStore("proxyComboStore");
        store.load();
    },
    locationtagComboLoad: function() {
        var store = this.getViewModel().getStore("locationTagComboStore");
        store.load();
    },
    langPreferenceStoreLoad: function() {
        var store = this.getViewModel().getStore("langPreferenceStore");
        store.load();
    },
    userTypeComboChange: function(cb, newValue, oldValue) {
        if (newValue == 1 || newValue == 2) {
            this.lookupReference('allowedToParticipateHtml').enable();
        } else {
            this.lookupReference('allowedToParticipateHtml').disable();
            this.lookupReference('allowedToParticipateHtml').setValue(true);
        }
        roleID = newValue;
    },
    userTypeComboSelect: function(cb, record, index) {
        var scope = this;
        scope.getViewModel().set("roleId", record.data.roleID);
        var memberId = this.lookupReference("memberId").getValue();
        if (record.data.roleID == '7') {
            Ext.Ajax.request({
                url: 'checkLicensesQuantity.ajax',
                method: 'GET',
                success: function(xhr) {
                    var errorMsg = l10n('license-validation');
                    var o;
                    try {
                        o = Ext.decode(xhr.responseText);
                    } catch (e) {}
                    if ('object' === typeof o) {
                        if (true !== o.success) {
                            Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.error, function() {
                                cb.reset();
                            });
                        }
                        if (true !== o.valid) {
                            Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.reason, function() {
                                cb.reset();
                            });
                        }
                    }
                },
                params: {
                    memberID: memberId,
                    role: record.data.roleID
                }
            });
        }
        if (record.data.roleID == '8') {
            Ext.Ajax.request({
                url: 'checkLicensesQuantity.ajax',
                method: 'GET',
                success: function(xhr) {
                    var errorMsg = l10n('license-validation');
                    var o;
                    try {
                        o = Ext.decode(xhr.responseText);
                    } catch (e) {}
                    if ('object' === typeof o) {
                        if (true !== o.success) {
                            Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.error, function() {
                                cb.reset();
                            });
                        }
                        if (true !== o.valid) {
                            Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.reason, function() {
                                cb.reset();
                            });
                        }
                    }
                },
                params: {
                    memberID: memberId,
                    role: record.data.roleID
                }
            });
        }

        if (record.data.roleID == '3') {
            Ext.Ajax.request({
                url: 'checkLicensesQuantity.ajax',
                method: 'GET',
                success: function(xhr) {
                    var errorMsg = l10n('license-validation');
                    var o;
                    try {
                        o = Ext.decode(xhr.responseText);
                    } catch (e) {}
                    if ('object' === typeof o) {
                        if (true !== o.success) {
                            Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.error, function() {
                                cb.reset();
                            });
                        }
                        if (true !== o.valid) {
                            Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.reason, function() {
                                cb.reset();
                            });
                        }
                    }
                },
                params: {
                    memberID: memberId,
                    role: record.data.roleID
                }
            });
            scope.lookupReference('allowedToParticipateHtml').setValue(true);
        }
        if ((record.data.roleID == '1' || record.data.roleID == '2') && scope.lookupReference('allowedToParticipateHtml').getValue()) {
            Ext.Ajax.request({
                url: 'checkLicensesQuantity.ajax',
                method: 'GET',
                success: function(xhr) {
                    var errorMsg = l10n('license-validation');
                    var o;
                    try {
                        o = Ext.decode(xhr.responseText);
                    } catch (e) {}
                    if ('object' === typeof o) {
                        if (true !== o.success) {
                            Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.error, function() {
                                scope.lookupReference('allowedToParticipateHtml').setValue(false);
                            });
                        }
                        if (true !== o.valid) {
                            Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.reason, function() {
                                scope.lookupReference('allowedToParticipateHtml').setValue(false);
                            });
                        }
                    }
                },
                params: {
                    memberID: memberId,
                    role: record.data.roleID
                }
            });
        }
    },
    allowToParticipateCheck: function(obj, newValue, oldValue, eOpts) {
        var scope = this;
        var memberId = scope.getViewModel().get("memberIdVal");
        if (memberId == '0') {
            var roleID = scope.getViewModel().get("roleId");
            if (newValue == true && (roleID == 1 || roleID == 2)) { //Operator and Admin
                Ext.Ajax.request({
                    url: 'checkLicensesQuantity.ajax',
                    method: 'GET',
                    params: {
                        role: roleID
                    },
                    success: function(xhr) {
                        var errorMsg = l10n('license-validation');
                        var o;
                        try {
                            o = Ext.decode(xhr.responseText);
                        } catch (e) {}
                        if ('object' === typeof o) {
                            if (true !== o.success) {
                                Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.error, function() {
                                    cb.setValue(false);
                                });
                            }
                            if (true !== o.valid) {
                                Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.reason, function() {
                                    cb.setValue(false);
                                });
                            }
                        }
                    }
                });
            }
        }
    },
    saveUser: function() {
        var checkPassed = true;
        var scope = this;
        var memberId = scope.getViewModel().get("memberIdVal");
        if (memberId == '0') {
            var roleID = scope.getViewModel().get("roleId");
            if ((roleID == 3) //Normal
                || ((roleID == 1 || roleID == 2) && this.lookupReference('allowedToParticipateHtml').getValue())) { //Admin and Operator
                Ext.Ajax.request({
                    url: 'checkLicensesQuantity.ajax',
                    method: 'GET',
                    params: {
                        role: roleID
                    },
                    success: function(xhr) {
                        var errorMsg = l10n('license-validation');
                        var o;
                        try {
                            o = Ext.decode(xhr.responseText);
                        } catch (e) {}
                        if ('object' === typeof o) {
                            if (true !== o.success) {
                                Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.error, function() {});
                                checkPassed = false;
                            }
                            if (true !== o.valid) {
                                Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + o.reason, function() {});
                                checkPassed = false;
                            }
                        }
                        scope.saveMember(checkPassed);
                    }
                });
            } else {
                scope.saveMember(checkPassed);
            }
        } else {
            scope.saveMember(checkPassed);
        }
    },
    convertBooleanToString: function(o) {
        return (o.checked) ? 'on' : '';
    },
    convertBooleanToInt: function(o) {
        return (o.checked) ? 1 : 0;
    },
    saveMember: function(checkPassed) {
        var scope = this;
        var memberId = scope.getViewModel().get("memberIdVal");
        var importedUsed = scope.getViewModel().get("importedUsed");
        if (checkPassed) {
            if (importedUsed == '1') {
                if (memberId == '0') {
                    this.lookupReference("addUsersForm").getForm().submit({
                        url: 'enableprovisioneduser.ajax',
                        waitMsg: l10n('saving'),
                        params: {
                            active: scope.convertBooleanToInt(this.lookupReference('enable')),
                            memberID: memberId
                        },
                        success: function(form, action) {
                            var errorMsg = l10n('save-failed');
                            var errors = '';
                            if ((action.result != null) && (action.result.success)) {
                                if (action.result.errors != null && action.result.errors.length > 0) {
                                    Ext.Msg.alert(l10n('message'), errorMsg += '<br>' + action.result.errors[0].msg, function() {});
                                } else {
                                    scope.lookupReference("addUsersWin").close();
                                    scope.usersGridLoad();
                                }
                            }
                        },
                        failure: function(form, action) {
                            Ext.Msg.alert(l10n('failure'), l10n('request.failed'));
                        }
                    });
                } else {
                    Ext.Ajax.request({
                        url: 'enableprovisioneduser.ajax',
                        waitMsg: l10n('saving'),
                        params: {
                            active: scope.convertBooleanToInt(scope.lookupReference('enable')),
                            memberID: memberId,
                            enable: scope.convertBooleanToString(scope.lookupReference('enable')),
                            //need to reverse true and false since UI and db values represent complete opposite
                            userImageAllowed: scope.lookupReference('disableUserImageUpld').getValue()==true?0:1
                        },
                        success: function(response) {
                            var success = Ext.DomQuery.selectValue('message/@success', response.responseXML).trim();
                            if (success == 'true') {
                                scope.lookupReference("addUsersWin").close();
                                scope.usersGridLoad();
                            } else {
                                Ext.Msg.alert(l10n('message'), l10n('save-failed'))
                            }
                        },
                        failure: function(response) {
                            Ext.Msg.alert(l10n('failure'), l10n('request.failed'));
                        }
                    });
                }
            } else if (importedUsed == '0') {
                this.lookupReference("addUsersForm").getForm().submit({
                    url: 'savemember.ajax',
                    waitMsg: l10n('saving'),
                    params: {
                        active: scope.convertBooleanToInt(this.lookupReference('enable')),
                        allowedToParticipate: scope.convertBooleanToInt(this.lookupReference('allowedToParticipateHtml')),
                        fullRoomExtNumber: scope.lookupReference('tenantPrefix').getValue() + this.lookupReference('roomExtNumber').getValue() + ''
                    },
                    success: function(form, action) {

                        var errorMsg = l10n('save-failed');
                        var errors = '';
                        if ((action.result != null) && (action.result.success)) {
                            var xmlResponse = action.response.responseXML;
                            var success = Ext.DomQuery.selectValue('message/@success', xmlResponse).trim();
                            if (success == 'true') {
                                scope.lookupReference("addUsersWin").close();
                                scope.usersGridLoad();
                            }else{
                                var msg = Ext.DomQuery.selectValue("msg", xmlResponse);
                                Ext.Msg.alert(l10n('message'), msg);
                            }
                        }
                    },
                    failure: function(form, action) {
                        Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                    }
                });
            }
        }
    },
    closeAddUser: function() {

        this.lookupReference("addUsersWin").close();
        //this.view.setLoading(false);

    },
    manageUsersTooltip: function(event, toolEl, panel) {
        var url = guideLoc + '#Admin_GroupsManageGroupsTable';
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    addUsersTooltip: function(event, toolEl, panel) {
        var memberID = this.getViewModel().get("memberIdVal");
        var url = '';
        if (memberID == 0) {
            url = guideLoc + '#Admin_UsersAddUser';
        } else {
            url = guideLoc + '#Admin_UsersEditUser';
        }
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    addLegacyTooltip: function(event, toolEl, panel) {

        var memberID = this.getViewModel().get("memberIdVal");
        var url = guideLoc + '#Admin_UsersManageUsersTable';
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    importMembersTooltip: function(event, toolEl, panel) {
        var url = guideLoc + '#Admin_ImportUsers';
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    exportMembersTooltip: function(event, toolEl, panel) {
        var url = guideLoc + '#Admin_ImportUsers';
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    onActionComplete: function(form, action) {
        var scope = this;
        if (action.type == 'load') {
            if (scope.getViewModel().get("memberIdVal") == '0' && scope.getViewModel().get("defaultGroupExists") == 'false') {
                Ext.Msg.alert(l10n('message'), l10n('cannot-add-member-no-default-group'), function() {
                    scope.lookupReference("addUsersWin").close();
                    scope.usersGridLoad();
                });
            }
            if (scope.getViewModel().get("roleId") != '1' && scope.getViewModel().get("roleId") != '2') {
                scope.lookupReference('allowedToParticipateHtml').disable();
                scope.lookupReference('allowedToParticipateHtml').setValue(true);
            } else {
                scope.lookupReference('allowedToParticipateHtml').enable();
            }
        }
    },
    usersGridBeforeLoad: function(store) {

        var memberNameFilter = this.lookupReference('memberNameFilter') ? this.lookupReference('memberNameFilter').getValue() : '';
        var extFilter = this.lookupReference('extFilter') ? this.lookupReference('extFilter').getValue() : '';
        var typeFilter = this.lookupReference('typeFilter') ? this.lookupReference('typeFilter').getValue() : '';
        var groupNameFilter = this.lookupReference('groupNameFilter') ? this.lookupReference('groupNameFilter').getValue() : '';
        var enableStatus = this.lookupReference('enableStatus') ? this.lookupReference('enableStatus').getValue() : '';

        var proxy = store.getProxy();
        proxy.setExtraParam('memberName', memberNameFilter);
        proxy.setExtraParam('ext', extFilter);
        proxy.setExtraParam('type', typeFilter);
        proxy.setExtraParam('groupName', groupNameFilter);
        proxy.setExtraParam('userStatus', enableStatus);
        proxy.setExtraParam('sort', this.getViewModel().get('usersSortDataIndex'));
        proxy.setExtraParam('dir', this.getViewModel().get('usersSortDir'));
    },
    usersGridFilterLoad: function(field, rec) {
        var getTextWidth = function(text) {
            var canvas = getTextWidth.canvas || (getTextWidth.canvas = document.createElement("canvas"));
            var context = canvas.getContext("2d");
            context.font = 'helvetica,arial,verdana,sans-serif';
            var metrics = context.measureText(text);
            return metrics.width;
        };
        if (field.xtype == "combo") {
            var width = getTextWidth(rec.get('name')) + (rec.get('type') == "Executive" ? 115 : 100);
            field.setWidth(width);
        }
        var val = field.getValue(),
            store = this.view.getViewModel().getStore("manageUsersGridStore");
        if (val.length === 0) {
            store.clearFilter();
        } else {
            store.filter(field.getName(), val);
        }
    },
    usersGridLoad: function() {
        var store = this.getViewModel().getStore("manageUsersGridStore");
        store.load();
    },
    remoteValidateMemberName: function(textfield, newValue) {
        var scope = this;
        var readonly = scope.getViewModel().get("userNameReadOnly");
        if (readonly) {
            textfield.textValid = true;
            textfield.textInvalid = '';
            textfield.validate();
        } else {
            Ext.Ajax.request({
                url: 'checkmembername.ajax',
                method: 'POST',
                params: {
                    username: newValue
                },
                scope: textfield,
                success: function(response) {
                    var roomExtExists = Ext.DomQuery.selectValue('message/@success', response.responseXML).trim();
                    if (roomExtExists == 'true') {
                        this.textValid = false;
                        this.textInvalid = l10n('username-not-available');
                        this.validate();
                    } else {
                        this.textValid = true;
                        this.textInvalid = '';
                        this.validate();
                    }
                },
                failure: function(response) {
                    Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                }
            });
        }

    },
    remoteValidateExt: function(textfield, newValue) {
        var scope = this;
        Ext.Ajax.request({
            url: 'checkroomextn.ajax',
            params: {
                roomext: newValue
            },
            scope: textfield,
            success: function(response, request) {
                var currRoomExt = scope.getViewModel().get("currRoomExt");
                var roomExtExists = Ext.DomQuery.selectValue('message/@success', response.responseXML).trim();
                if (roomExtExists == 'true' && currRoomExt != newValue) {
                    this.textValid = false;
                    this.textInvalid = l10n('roomextension-not-available');
                    this.validate();
                } else {
                    this.textValid = true;
                    this.textInvalid = '';
                    this.validate();
                }
            }
        });
    },
    onUsersSortChange: function(ct, column, direction, eOpts) {
        this.getViewModel().set('usersSortDataIndex', column.dataIndex);
        this.getViewModel().set('usersSortDir', direction);
        this.usersGridLoad();
    },

    addToHistory: function(newToken) {
        Ext.History.add(newToken);
    },

    imageDelete: function(field, value) {
        var scope = this,
            viewModel = scope.getViewModel(),
            form = scope.lookupReference('imageForm').getForm();
        var memberId = scope.getViewModel().get("memberIdVal");
        Ext.Msg.confirm(l10n('confirmation'), "Are you sure you want to delete the image?This will delete the image permanently", function(res) {
            if (res == "yes") {
                Ext.Ajax.request({
                    url: 'deleteUserImage.ajax',
                    params: {
                        memberId: memberId
                    },
                    method: 'POST',
                    success: function(response) {
                        var xmlResponse = response.responseXML;
                        var success = Ext.DomQuery.selectValue('message @success', xmlResponse);


                        if (success == 'true') {
                              scope.getViewModel().set("hideImageDelete",true );

                            var base64Image = Ext.DomQuery.selectValue('thumbnailImageB64', xmlResponse);
                            if (base64Image != null && base64Image != "") {
                                var imageField = scope.lookupReference("thumbnailImage");
                                // imageField.src="data:image/*;base64,"+base64Image;

                                imageField.getEl().dom.src = "data:image/png;base64," + base64Image;


                            } else {
                                var imageField = scope.lookupReference("thumbnailImage");
                                // imageField.src="data:image/*;base64,"+base64Image;

                                imageField.getEl().dom.src = "";

                            }
                        } else {
                            var errorMsg = '';
                            var errors = '';
                            var errorsNode = Ext.DomQuery.select('message/errors/field', xmlResponse);
                            for (var i = 0; i < errorsNode.length; i++) {
                                errors += Ext.DomQuery.selectValue('id', errorsNode[i]) + ' - ' + Ext.DomQuery.selectValue('msg', errorsNode[i]) + '<br>';
                            }
                            errorMsg += '<br>' + errors;
                            Ext.Msg.alert(l10n('error'), errorMsg, function() {});
                        }


                    }
                });
            }
        }, scope);
    },
    disableUserImgUpldWarning: function(c) {
        Ext.Msg.alert(l10n('warning'), 'Disabling this will result in deleting image uploaded by user.');
    },
    imageUploadAfterRender: function(c) {
        var scope = this;
        //due to another bug in ext js6, we are not able to use binding disabled
       // if (this.getViewModel().get("importedUsed") == "1") {
          //  c.setDisabled(true);
        //} else {
       //     c.setDisabled(false);
       // }


        Ext.QuickTips.register({
            target: c.getEl(),
            text: 'Upload Photo'
        });

        // setTimeout(function() {
        //           scope.view.setLoading(false);
        //   }, 3000);



    },
    userImageUpload: function(field, value) {


        var viewModel = this.getViewModel();
        var form = this.lookupReference('imageForm').getForm();
        var scope = this;
        var memberId = scope.getViewModel().get("memberIdVal");
        Ext.Msg.confirm(l10n('confirmation'), l10n('upload-user-image-confirmation'), function(res) {
            if (res == "yes") {
                if (form.isValid()) {
                    form.submit({
                        url: 'uploadUserImage.ajax',
                        params: {
                            memberId: memberId
                        },
                        success: function(form, action) {
                            var xmlResponse = action.response.responseXML;
                            var success = Ext.DomQuery.selectValue('message @success', xmlResponse);


                            if (success == 'true') {

                                var base64Image = Ext.DomQuery.selectValue('thumbnailImageB64', xmlResponse);

                                if (base64Image != null && base64Image != "") {
                                    var imageField = scope.lookupReference("thumbnailImage");
                                    // imageField.src="data:image/*;base64,"+base64Image;

                                    imageField.getEl().dom.src = "data:image/png;base64," + base64Image;


                                }
                                scope.getViewModel().set("hideImageDelete",false );
                            } else {
                                var errorMsg = '';
                                var errors = '';
                                var errorsNode = Ext.DomQuery.select('message/errors/field', xmlResponse);
                                for (var i = 0; i < errorsNode.length; i++) {
                                    errors += Ext.DomQuery.selectValue('msg', errorsNode[i]) + '<br>';
                                }
                                errorMsg += '<br>' + errors;
                                Ext.Msg.alert(l10n('error'), errorMsg, function() {});
                            }


                        }
                    });
                }
            }
        }, scope);


    }

});
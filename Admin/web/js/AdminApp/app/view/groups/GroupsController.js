Ext.define('AdminApp.view.groups.GroupsController', {
    extend : 'Ext.app.ViewController',

    alias : 'controller.groupsController',

    onGroupsRender : function() {
        var me = this,
            view = me.view,
            groupsContainer = this.lookupReference('groupsContainer');
        Ext.defer(function() {
            groupsContainer.getLayout().setActiveItem(view.down('managegroupsview'));
        }, 10);
        this.lookupReference("groupNameFilter").reset();
        this.manageGroupsGridRender();
    },
    addGroupView : function() {
        var scope = this;
        var addGroupWin = Ext.create('AdminApp.view.groups.AddGroups', {
            floatParent : scope.view
        });
        addGroupWin.show();
        this.showAddGroupsView(0);
    },
    onManageGroupsRowClick : function(grid, record, tr, rowIndex, e, eOpts) {
        var groupId = record.get("groupID");

        if (e.target.className == "group-name") {
            this.getManageGroupsData(groupId);
        }
    },

    getManageGroupsData : function(groupId) {
        var scope = this;
        var addGroupWin = Ext.create('AdminApp.view.groups.AddGroups', {
            floatParent : scope.view
        });
        addGroupWin.show();
        this.showAddGroupsView(groupId);
    },
    manageGroupsBeforeload : function(store) {
        var groupNameFilter = this.lookupReference('groupNameFilter') ? this.lookupReference('groupNameFilter').getValue() : '';

        var proxy = store.getProxy();
       // proxy.setExtraParam('groupName', groupNameFilter);
        proxy.setExtraParam('sort', this.getViewModel().get('groupsSortDataIndex'));
        proxy.setExtraParam('dir', this.getViewModel().get('groupsSortDir'));
    },
    onGroupsSortChange : function(ct, column, direction, eOpts) {
        this.getViewModel().set('groupsSortDataIndex', column.dataIndex);
        this.getViewModel().set('groupsSortDir', direction);
        this.getViewModel().storeInfo.manageGroupsGridStore.load();
    },
    manageGroupsGridRender : function() {
        var store = this.getViewModel().getStore("manageGroupsGridStore");
        store.load();
    },
    groupNameFilterLoad : function(field) {
    	var val = field.getValue(),
        store = this.view.getViewModel().getStore("manageGroupsGridStore");
        if (val.length === 0) {
            store.clearFilter();
        } else {
            store.filter(field.getName(), val);
        }
    },
    showAddGroupsView : function(groupID) {
        var scope = this,
            addForm = this.lookupReference("addGroupForm");
        addForm.getForm().reset();
        this.lookupReference("groupNameText").count = 1;
        //if (groupID != 0) {
        addForm.getForm().load({
            url : "group.ajax",
            method: 'GET',
            params : {
                groupID : groupID
            },
            waitMsg : 'Loading...',
            success : function(form, action) {
                var xmlResponse = action.response.responseXML,
                    groupName = Ext.DomQuery.selectValue('groupName', xmlResponse) == undefined ? "" : Ext.DomQuery.selectValue('groupName', xmlResponse);
                groupName = Ext.util.Format.htmlEncode(groupName);
                if (groupID == '0') {
                    scope.getViewModel().set('title', l10n('add-group') + ": " + l10n('new-group'));
                    scope.lookupReference('maxparticipants').setValue(10);
                    scope.lookupReference('maxreceivebandwidth').setValue(100000);
                    scope.lookupReference('maxtransmitbandwidth').setValue(100000);
                } else {
                    scope.getViewModel().set('title', l10n('edit-group') + ": " + groupName);
                    scope.getViewModel().set("groupIdVal", groupID);
                }
                scope.getViewModel().set("hasReplay", Ext.DomQuery.selectValue('hasReplay', xmlResponse) == "1" ? false : true);
                scope.lookupReference('allowRecordingFlag').setValue(Ext.DomQuery.selectValue('allowRecordingFlag', xmlResponse) == "1" ? true : false);
                scope.getViewModel().set('groupName', groupName);
            },
            failure : function() {
                Ext.Msg.alert(l10n('failure'), l10n('unable-to-fetch-data'));
            }
        });
        //} else {
        //addForm.down('')
        //}
    },
    groupNameFilter : function(obj, e, eOpts) {
        this.getViewModel().getStore('manageGroupsGridStore').filterBy(function groupFilter(record) {
            if (obj.getValue() == '')
                return true;
            var pos = record.get('groupName').toUpperCase().indexOf(obj.getValue().toUpperCase());
            if (pos != -1)
                return true;
            else
                return false;
        });
    },
    manageGroupsDeletRecord : function() {
        var scope = this;
        var selections = this.lookupReference("manageGroupsGrid").getSelectionModel().getSelection();
        if (selections.length == 0) {
            Ext.MessageBox.alert(l10n('error'), l10n('please-select-at-least-one-group-to-delete') + "!");

        } else {
            Ext.MessageBox.confirm(l10n('confirmation'), l10n('do-you-really-want-to-delete-selected-groups'), scope.doDeleteGroup, scope);
        }
    },
    doDeleteGroup : function(btn) {
        var scope = this;
        var deletedGroups = 0;
        if (btn == "yes") {
            var selections = this.lookupReference("manageGroupsGrid").getSelectionModel().getSelection();
            for (var i = 0,
                len = selections.length; i < len; i++) {
                if (selections[i].get("defaultFlag") == 1) {
                    deletedGroups++;
                    Ext.Msg.alert("Message", "Cannot delete default group.");
                } else {
                    Ext.Ajax.request({
                        url : 'deletegroup.ajax',
                        method: 'POST',
                        success : function() {
                            deletedGroups++;
                            if (deletedGroups == selections.length) {
                                scope.getViewModel().getStore("manageGroupsGridStore").load();
                            }
                        },
                        params : {
                            groupID : selections[i].get("groupID")
                        }
                    });
                }

            }
        }
    },

    convertBooleanToInt : function(o) {
        if (o === undefined) {
            return 0;
        } else {
            return (o.checked) ? 1 : 0;
        }
    },

    saveGroup : function() {
        var scope = this,
            params = {},
            allowRecordingFlag = this.lookupReference("allowRecordingFlag"),
            hasreplay = scope.getViewModel().get("hasReplay");

        if (!hasreplay) {
            params = {
                allowRecording : scope.convertBooleanToInt(allowRecordingFlag)
            };
        }
        scope.lookupReference("allowRecordingFlag").setDisabled(true);
        scope.lookupReference("addGroupForm").getForm().submit({
            url : 'savegroup.ajax',
            waitMsg : l10n('saving'),
            params : params,
            success : function(form, action) {
                var xmlResponse = action.response.responseXML;

                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);

                if (success == "false") {
                    var responseId = Ext.DomQuery.selectValue('id', xmlResponse);
                    var responseMsg = Ext.DomQuery.selectValue('msg', xmlResponse);
                    Ext.Msg.alert(responseId, responseMsg);
                } else {
                    scope.lookupReference("addGroupWin").close();
                    scope.manageGroupsGridRender();
                }
            },
            failure : function(form, action) {
                Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
            }
        });
    },

    closeAddGroup : function() {
        this.lookupReference("addGroupWin").close();
    },
    manageGroupsTooltip : function(event, toolEl, panel) {
        var url = guideLoc + '#Admin_GroupsManageGroupsTable';
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    addGroupTooltip : function(event, toolEl, panel) {
        var url = '';
        if (this.getViewModel().get("groupIdVal") == '0') {
            url = guideLoc + '#Admin_GroupsAddGroup';
        } else {
            url = guideLoc + '#Admin_GroupsEditGroup';
        }
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    remoteValidateGroupName : function(textfield, newValue, oldValue, eOpts) {
        var scope = this,
        viewModel = scope.getViewModel(),
        groupName = viewModel.get('groupName');
    	var groupId = this.lookupReference("groupId").getValue();
    	/*if (groupName === newValue) {
            return;
        }

        if (textfield.count == 1) {
            textfield.count = 2;
            textfield.textValid = true;
            textfield.textInvalid = '';
            textfield.validate();
            return;
        }*/
        Ext.Ajax.request({
            url : 'checkgrpname.ajax',
            params : {
                groupname : newValue,
                groupId : groupId
            },
            scope : textfield,
            success : function(response, request) {
                var groupNameExists = Ext.DomQuery.selectValue('message/@success', response.responseXML).trim();
                if (groupNameExists == 'true') {
					 this.validFlag = l10n('groupname-not-available');
		             this.validate();
                } else {
					this.validFlag = true;
	                this.validate();
                }
            },
            failure : function(response) {
                Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
            }
        });

    },

    addToHistory : function(newToken) {
        /*newToken += ":" + this.lookupReference('groupsNavGrid').getId() + ":" + this.lookupReference('groupsNavGrid').getSelectionModel().getSelection()[0].get('recordId');
         this.getView().lookupController(true).getViewModel().set('callItemClick', false);
         Ext.History.add(newToken);*/
    }
});

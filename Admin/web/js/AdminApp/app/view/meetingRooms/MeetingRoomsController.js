Ext.define('AdminApp.view.meetingRooms.MeetingRoomsController', {
    extend : 'Ext.app.ViewController',

    alias : 'controller.meetingRoomsController',

    onMeetingRoomsRender : function() {
        var me = this,
            view = me.view,
            meetingRoomsContainer = this.lookupReference('meetingRoomsContainer');
        Ext.defer(function() {
            meetingRoomsContainer.getLayout().setActiveItem(view.down('managemeetingroomsview'));
        }, 10);
        this.resetFilter();
        this.manageMeetingRoomsLoad();
    },
    onCurrentCallsRender: function(){
    	 var me = this,
         view = me.view;
        Ext.create('AdminApp.view.meetingRooms.CurrentCalls');
        this.currentCallGridLoad();
    },
    resetFilter: function(){
    	if(this.lookupReference('roomNameFilter')) {
    		this.lookupReference('roomNameFilter').reset();	
    	}
    	
    	if(this.lookupReference('extFilter')) {
    		this.lookupReference('extFilter').reset();	
    	}
    	
    	if(this.lookupReference('typeFilter')) {
    		this.lookupReference('typeFilter').reset();	
    	}
    	
    	if(this.lookupReference('enableStatus')) {
    		this.lookupReference('enableStatus').reset();
    	}
    	
    	if(this.lookupReference('cmextension')) {
    		this.lookupReference('cmextension').reset();	
    	}
    	
    },
    addMeetingRoomsView: function(){
    	var scope=this;
    	var addMeetingRoomWin = Ext.create('AdminApp.view.meetingRooms.AddMeetingRoom',{
    		floatParent: scope.view
    	});
    	addMeetingRoomWin.show();
        this.showAddMeetingRoomView(0);
    },

    onManageMeetingRoomsRowClick : function(grid, record, tr, rowIndex, e, eOpts) {
        var me = this,
            roomID = record.get("roomID");

        if (e.target.className == "room-name") {
            me.getManageMeetingRoomData(roomID);
        } else if (e.target.className == "control-meeting") {
            return '<a href="roomcontrol.html" target="_blank"></a>';
        }
    },

    getManageMeetingRoomData : function(roomID) {
    	var scope=this;
    	var addMeetingRoomWin = Ext.create('AdminApp.view.meetingRooms.AddMeetingRoom',{
    		floatParent: scope.view
    	});
    	addMeetingRoomWin.show();
        this.showAddMeetingRoomView(roomID);
    },

    showAddMeetingRoomView : function(roomId) {
        var scope = this;
        scope.lookupReference("roomID").setValue(roomId);
        scope.getViewModel().set("roomId", roomId);
        scope.getViewModel().set("roomNameReadOnly", false);
        scope.lookupReference("addMeetingRoomForm").getForm().reset();

        Ext.Ajax.request({
            url : 'room.ajax',
            method: 'GET',
            params : {
                roomID : roomId
            },
            success : function(res) {
            	
                var xmlResponse = res.responseXML;
                var roomID = Ext.DomQuery.selectNode('roomID', xmlResponse);
                var roomExtNumber = Ext.DomQuery.selectNode('roomExtNumber', xmlResponse);
                roomExtNumber = Ext.util.Format.htmlEncode(roomExtNumber.textContent);
                var displayName = Ext.DomQuery.selectNode('roomDisplayName', xmlResponse);
                displayName = Ext.util.Format.htmlEncode(displayName.textContent);
                var roomName = Ext.DomQuery.selectNode('roomName', xmlResponse);
                roomName = Ext.util.Format.htmlEncode(roomName.textContent);
                var tenantPrefix = Ext.DomQuery.selectNode('tenantPrefix', xmlResponse);
                tenantPrefix = Ext.util.Format.htmlEncode(tenantPrefix.textContent);
                var roomTypePersonal = Ext.DomQuery.selectNode('roomTypePersonal', xmlResponse);
                var roomPIN = Ext.DomQuery.selectNode('roomPinValue', xmlResponse);
                roomPIN = Ext.util.Format.htmlEncode(roomPIN.textContent);
                var roomModeratorPIN = Ext.DomQuery.selectNode('roomModeratorPinValue', xmlResponse);
                roomModeratorPIN = Ext.util.Format.htmlEncode(roomModeratorPIN.textContent);
                var importedUsed = Ext.DomQuery.selectNode('importedUsed', xmlResponse);
                var memberID=Ext.DomQuery.selectNode('memberID', xmlResponse);
                scope.getViewModel().set("roomTypePersonal", roomTypePersonal.textContent);
                scope.getViewModel().set("importedUsed", importedUsed.textContent);

                if (roomId == '0') {
                    scope.getViewModel().set("title", l10n('add-room') + ": " + l10n('new-room'));
                    if (tenantPrefix.textContent != '') {
                        scope.getViewModel().set("tenantPrefixVal", tenantPrefix);
                    }
                    scope.getViewModel().set("currRoomExt", '');
                    scope.getViewModel().set("currRoomName", '');
                } else {
                    scope.getViewModel().set("title", l10n('edit-room') + ": " + roomName);
                    scope.getViewModel().set("currRoomExt", roomExtNumber);
                    scope.getViewModel().set("currRoomName", roomName);
                    scope.lookupReference("roomUrlFieldset").show();
                    scope.lookupReference("roomUrlFieldset").enable();

                    scope.lookupReference("addMeetingRoomForm").getForm().load({
                        url : 'room.ajax?roomID=' + roomId,
                        method: 'GET',
                        waitMsg : 'Loading',
                        success : function(form, action) {
                            var xmlResponse = action.response.responseXML;
                            var memberID = Ext.DomQuery.selectNode('memberID', xmlResponse);
                            scope.lookupReference('memberID').setValue(memberID.textContent);
                            setTimeout(function() {
                                var roomOwnerComboStore = scope.getViewModel().getStore('roomOwnerComboStore');                            	
                                if (roomId != '0') {
                                    roomOwnerComboStore.getProxy().setExtraParams({"roomID":roomId});
                                }
                                roomOwnerComboStore.load();
                                scope.lookupReference('ownerID').setValue(memberID.textContent);
                            }, 300);

                        }
                    });
                    scope.lookupReference("addMeetingRoomForm").getForm().findField('rmDisplayName').setValue(displayName);
                }
                if (roomTypePersonal.textContent == 'true') {
                	scope.getViewModel().set("roomNameReadOnly", true);
                    scope.lookupReference("displayName").show();
                    scope.lookupReference("displayName").enable();
                    scope.lookupReference("ownerID").hide();
                    scope.lookupReference("ownerID").disable();
                    scope.getViewModel().set("memberIdForPersonalRoom", memberID.textContent);
                    scope.lookupReference("memberID").enable();
                    scope.lookupReference("memberID").setValue('0');
                } else {
                     scope.lookupReference("ownerID").show();
                    scope.lookupReference("ownerID").enable();
                    scope.lookupReference("memberID").hide();
                    //scope.lookupReference("ownerID").setValue(memberID.textContent);
                }

                if (roomModeratorPIN == 'yes') {
                    scope.lookupReference("removeModPin").show();
                    scope.lookupReference("removeModPin").enable();
                }
                if (roomPIN == 'yes') {
                    scope.lookupReference("removePin").show();
                    scope.lookupReference("removePin").enable();
                }
                if (roomPIN != '') {
                    scope.getViewModel().set("roomPinTitle", l10n('room-pin') + "&nbsp;<span class='red-label'>(" + l10n('there-is-already-a-pin-set') + ")</span>");
                } else if (roomPIN == '') {
                    scope.getViewModel().set("roomPinTitle", l10n('room-pin') + "&nbsp;<span class='red-label'>(" + l10n('this-room-does-not-have-a-pin') + ")</span>");
                }
                if (roomModeratorPIN != '') {
                    scope.getViewModel().set("roomModPinTitle", l10n('room-moderator-pin') + "&nbsp;<span class='red-label'>(" + l10n('there-is-already-a-pin-set') + ")</span>");
                } else if (roomModeratorPIN == '') {
                    scope.getViewModel().set("roomModPinTitle", l10n('room-moderator-pin') + "&nbsp;<span class='red-label'>(" + l10n('this-room-does-not-have-a-pin') + ")</span>");
                }

            },
            failure : function() {
                Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
            }
        });
    },
    onEnableCheck : function(item, checked) {
        this.getViewModel().getStore('manageMeetingRoomsStore').load();
    },
    meetingRoomsDeletRecord : function() {
        var p = false;
        var scope = this;
        var selections = scope.lookupReference("manageMeetingRoomsGrid").getSelectionModel().getSelection();
        if (selections.length > 0) {
            for (var i = 0; i < selections.length; i++) {
                if (selections[i].get("roomType") == 'Personal') {
                    p = true;
                }
            }
            if (p) {
                Ext.MessageBox.alert(l10n('error'), l10n('cannot-delete-a-personal-room-you-can-delete-user-instead'));
            } else {
                Ext.MessageBox.confirm(l10n('confirmation'), l10n('do-you-really-want-to-delete-the-selected-rooms'), scope.deleteMeetingRoomRecord, scope);
            }
        } else {
            Ext.MessageBox.alert(l10n('error'), l10n('please-select-at-least-one-room-to-delete'));
        }
    },
    deleteMeetingRoomRecord : function(btn) {
        var scope = this;
        if (btn == 'yes') {
            var selections = scope.lookupReference("manageMeetingRoomsGrid").getSelectionModel().getSelection();
            for (var i = 0; i < selections.length; i++) {
                Ext.Ajax.request({
                    url : 'deleteroom.ajax',
                    params : {
                        roomID : selections[i].get("roomID")
                    },
                    success : function() {
                        scope.getViewModel().getStore("manageMeetingRoomsStore").remove(selections[i]);
                    }
                });
            };
            Ext.Function.defer(function() {
                scope.getViewModel().getStore("manageMeetingRoomsStore").load();
            }, 300);
        }
    },
    displayNameFilter : function(obj, e, eOpts) {
        this.getViewModel().getStore('manageVidyoRoomsStore').filterBy(function memberFilter(record) {
            if (obj.getValue() == '')
                return true;
            var pos = record.get('memberName').toUpperCase().indexOf(obj.getValue().toUpperCase());
            if (pos != -1)
                return true;
            else
                return false;
        });
    },
    roomOwnerComboLoad : function() {
    	var roomID = this.getViewModel().get("roomId");
        var roomOwnerComboStore = this.getViewModel().getStore('roomOwnerComboStore');
        if(!roomOwnerComboStore.isLoaded()) {
            if (roomID == '0') {
                roomOwnerComboStore.getProxy().setUrl("members.ajax");
            } else {
                roomOwnerComboStore.getProxy().setUrl('members.ajax?roomID=' + roomID);
            }
            roomOwnerComboStore.load();
        }
    },
    groupComboLoad : function() {
        var groupComboStore = this.getViewModel().getStore('groupComboStore');
        //if (!groupComboStore.isLoaded()) {
            groupComboStore.load();
        //}
    },
    pinSelect : function(obj, ev, eo) {
        if (obj.inputValue == 'enter') {
            this.lookupReference("roomPIN").enable();
            this.lookupReference('roomPIN').setValue('');
        } else {
            this.lookupReference("roomPIN").reset();
            this.lookupReference("roomPIN").disable();
        }
    },
    moderatorPinSelect : function(obj, ev, eo) {
        if (obj.inputValue == 'enter') {
            this.lookupReference("roomModeratorPIN").enable();
            this.lookupReference('roomModeratorPIN').setValue('');
        } else {
            this.lookupReference("roomPIN").reset();
            this.lookupReference("roomModeratorPIN").disable();
        }
    },
    convertBooleanToInt : function(o) {
        return (o.checked) ? 1 : 0;
    },
    saveMeetingroom : function() {
        var scope = this,
        ownerID = this.lookupReference('ownerID').getValue();
        scope.lookupReference("memberID").setValue(ownerID);
        if (this.getViewModel().get("roomTypePersonal") == 'true'){
        	ownerID=this.getViewModel().get("memberIdForPersonalRoom");
        	 scope.lookupReference("memberID").setValue(ownerID);
        }
        var params = {};
        if (this.getViewModel().get("roomTypePersonal") == 'true' && this.getViewModel().get("importedUsed") == '1') {
            params = {
            	displayName : this.lookupReference('roomDisplayName').getValue(),
                roomName : this.lookupReference('roomName').getValue(),
                roomExtNumber : this.lookupReference('roomExtNumber').getValue(),
                groupID : this.lookupReference('roomGroupID').getValue(),
                roomDescription : this.lookupReference('roomDescription').getValue(),
                roomEnabled : this.convertBooleanToInt(this.lookupReference('enabled')),
                roomLocked : this.convertBooleanToInt(this.lookupReference('locked')),
                memberID: ownerID
            }
        } else {
            params = {
                roomEnabled : this.convertBooleanToInt(this.lookupReference('enabled')),
                roomLocked : this.convertBooleanToInt(this.lookupReference('locked')),
                memberID: ownerID
            }
        }

        this.lookupReference("addMeetingRoomForm").getForm().submit({
            url : 'saveroom.ajax',
            waitMsg : 'Saving',
            params : params,
            success : function(form, action) {
                var xmlResponse = action.response.responseXML;

                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);

                if (success == "false") {
                    var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                    var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                    Ext.Msg.alert(l10n('message'), Ext.String.htmlEncode(responseMsg.textContent));
                } else {
                    scope.lookupReference("addMeetingRoomsWin").close();
                    scope.manageMeetingRoomsLoad();
                }
            },
            failure : function(form, action) {
                Ext.Msg.alert(l10n('failure'), l10n('unable-to-fetch-data'));
            }
        });
    },
    closeAddMeetingRoom : function() {
    	this.lookupReference("addMeetingRoomsWin").close();
    },
    generateURL : function() {
        var scope = this;
        var roomID = scope.getViewModel().get("roomId");
        Ext.Ajax.request({
            url : 'generateroomkey.ajax',
            method : 'GET',
            params : {
                roomID : roomID
            },
            success : function() {
                scope.lookupReference("addMeetingRoomForm").getForm().load({
                    url : 'room.ajax?roomID=' + roomID,
                    method: 'GET'
                });
                scope.lookupReference('removeURL').enable();
                scope.lookupReference('generateURL').disable();
            }
        });
    },
    removeURL : function() {
        var scope = this;
        var roomID = scope.getViewModel().get("roomId");
        Ext.Ajax.request({
            url : 'removeroomkey.ajax',
            params : {
                roomID : roomID
            },
            success : function() {
                scope.lookupReference("addMeetingRoomForm").getForm().load({
                    url : 'room.ajax?roomID=' + roomID,
                    method: 'GET'
                });
                scope.lookupReference('removeURL').disable();
                scope.lookupReference('generateURL').enable();
            }
        });
    },
    manageMeetingRoomsLoad : function() {
        var store = this.getViewModel().getStore("manageMeetingRoomsStore");
        store.load();
    },
    manageMeetingRoomsFilterLoad : function(field) {
        var val = field.getValue(),
        store = this.view.getViewModel().getStore("manageMeetingRoomsStore");
        if (val.length === 0) {
            store.clearFilter();
        } else {
            store.filter(field.getItemId(), val);
        }
    },

    manageVidyoRoomsGridLoad : function() {
        this.getViewModel().storeInfo.manageVidyoRoomsStore.load();
    },
 
    currentcallsBeforeLoad : function(store) {
        var proxy = store.getProxy();
        if (this.getViewModel().get('currentcallsSortDataIndex') == '' && this.getViewModel().get('currentcallsSortDir') == '') {
            proxy.setExtraParam('sort', 'conferenceName');
            proxy.setExtraParam('dir', 'ASC');
        } else {
            proxy.setExtraParam('sort', this.getViewModel().get('currentcallsSortDataIndex'));
            proxy.setExtraParam('dir', this.getViewModel().get('currentcallsSortDir'));
        }

    },
    currentCallGridLoad : function() {
        this.getViewModel().getStore("currentCallsGridStore").load();
    },
    currentcallsSortChange : function(ct, column, direction, eOpts) {
        this.getViewModel().set('currentcallsSortDataIndex', column.dataIndex);
        this.getViewModel().set('currentcallsSortDir', direction);
        this.currentCallGridLoad();
    },
    manageMeetingRoomsTooltip : function(event, toolEl, panel) {
        var url = guideLoc + '#Admin_GroupsManageGroupsTable';
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    currentCallTooltip : function(event, toolEl, panel) {
        var url = guideLoc + '#Admin_MeetingRoomsAddMeetingRoom';
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    manageVidyoRoomsTooltip : function(event, toolEl, panel) {
        var url = guideLoc + '#Admin_MeetingRoomsManageMeetingRoomsTable';
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    addMeetingRoomTooltip : function(event, toolEl, panel) {
        var roomId = this.getViewModel().get("roomId");
        var url = '';
        if (roomId == '0') {
            url = guideLoc + '#Admin_MeetingRoomsAddMeetingRoom';
        } else {
            url = guideLoc + '#Admin_MeetingRoomsEditMeetingRoom';
        }
        var wname = 'VidyoPortalHelp';
        var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
        window.open(url, wname, wfeatures);
    },
    addMeetingRoomFormLoad : function(form, action) {
    	var scope = this;
        if (action.type == 'load') {
        	scope.roomOwnerComboLoad();
            if (this.lookupReference("roomURL").getValue() == '') {
                this.lookupReference('removeURL').disable();
                this.lookupReference('generateURL').enable();
            } else {
                this.lookupReference('removeURL').enable();
                this.lookupReference('generateURL').disable();
            }

            if (this.getViewModel().get("roomTypePersonal") == 'true' && this.getViewModel().get("importedUsed") == '1') {
                var title = this.lookupReference("addMeetingRoomForm").title + ' <small class="red-label">[' + l10n('auto-provisioned-user') + ']</small>';
                this.lookupReference("addMeetingRoomForm").setTitle(title);
                this.lookupReference("roomName").editable = false;
                this.lookupReference("displayName").editable = false;
                this.lookupReference("roomExtNumber").editable = false;
                this.lookupReference("roomGroupID").editable = false;
                this.lookupReference("roomDescription").editable = false;
            }
        }
    },
    meetingRoomsBeforeLoad : function(store) {
        var roomNameFilter = this.lookupReference('roomNameFilter') ? this.lookupReference('roomNameFilter').getValue() : '';
        var extFilter = this.lookupReference('extFilter') ? this.lookupReference('extFilter').getValue() : '';
        var typeFilter = this.lookupReference('typeFilter') ? this.lookupReference('typeFilter').getValue() : '';
        var enableChkbox = this.lookupReference('enableStatus') ? this.lookupReference('enableStatus').getValue() : '';
        var proxy = store.getProxy();
        proxy.setExtraParam('displayName', roomNameFilter);
        proxy.setExtraParam('ext', extFilter);
        proxy.setExtraParam('type', typeFilter);
        proxy.setExtraParam('roomStatus', enableChkbox);
        proxy.setExtraParam('sort', this.getViewModel().get('meetingRoomsSortDataIndex'));
        proxy.setExtraParam('dir', this.getViewModel().get('meetingRoomsSortDir'));

    },

    remoteValidateDisplayName : function(textfield, newValue, oldValue, eOpts) {
        var scope = this;
        var view = scope.view;
    	var rmNm = view.lookupReference('roomName');
    	var roomID = scope.getViewModel().get("roomId");
        if (Ext.isEmpty(textfield.getValue()) || Ext.isEmpty(textfield.getValue().trim())) {
            return false;
        } else {
            Ext.Ajax.request({
                url : 'checkdisplayname.ajax',
                method : 'POST',
                params : {
                    displayName : newValue.trim(),
                    roomId : roomID
                },
                scope : textfield,
                success : function(response) {
                    var disPlayNameExists = Ext.DomQuery.selectValue('message/@success', response.responseXML).trim();
                    if (disPlayNameExists == 'false') {
                        this.textValid = false;
                        this.textInvalid = l10n('invalid-display-name')
                        this.validate();
                    } else {
                        this.textValid = true;
                        this.textInvalid = '';
                        this.validate();
                        var rm = newValue.trim().replace(/ +/g, "_");
                        rm = rm.replace(/[~`!@#\$%\^\&*\)\(\[\]=+|\\{}:;"'<>?,\/]/g,"");
                        rmNm.setValue(rm);
                    }
                },
                failure : function(response) {
                    Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                }
            });
        }

    },
    
    remoteValidateRoomName : function(textfield, newValue, oldValue, eOpts) {
        var scope = this;
        if (Ext.isEmpty(textfield.getValue())) {
            return false;
        } else {
            Ext.Ajax.request({
                url : 'checkroomname.ajax',
                method : 'POST',
                params : {
                    roomname : newValue
                },
                scope : textfield,
                success : function(response) {
                    var currRoomName = scope.getViewModel().get("currRoomName");
                    var roomExtExists = Ext.DomQuery.selectValue('message/@success', response.responseXML).trim();
                    if (roomExtExists == 'true' && currRoomName != newValue) {
                        this.textValid = false;
                        this.textInvalid = l10n('roomname-not-available')
                        this.validate();
                    } else {
                        this.textValid = true;
                        this.textInvalid = '';
                        this.validate();
                    }
                },
                failure : function(response) {
                    Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                }
            });
        }

    },
    remoteValidateExt : function(textfield, newValue, url, param) {
        var scope = this;
        Ext.Ajax.request({
            url : 'checkroomextn.ajax',
            params : {
                roomext : newValue
            },
            scope : textfield,
            success : function(response, request) {
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
    meetingRoomsSortChange : function(ct, column, direction, eOpts) {
        this.getViewModel().set('meetingRoomsSortDataIndex', column.dataIndex);
        this.getViewModel().set('meetingRoomsSortDir', direction);
        this.manageMeetingRoomsLoad();
    },

    addToHistory : function(newToken) {
        /*newToken += ":" + this.lookupReference('meetingRoomsNavGrid').getId() + ":" + this.lookupReference('meetingRoomsNavGrid').getSelectionModel().getSelection()[0].get('recordId');
         this.getView().lookupController(true).getViewModel().set('callItemClick', false);
         Ext.History.add(newToken);*/
    },
    getExtensionParamValue : function(obj){
        var strReturn = '';
        try {
            strReturn = obj.getValue().trim();
        }
        catch (err) {
            strReturn = '';
        }
        return strReturn;
    },
    controlMeetingPopup: function(btn,e,eopts,pin){
    	var scope = this;
    	var roomext = scope.getExtensionParamValue(scope.lookupReference('cmextension'));
    	Ext.Ajax.request({
			url: 'getroomidbyext.ajax',
			params: {
				roomext: roomext,
				pin: pin
			},
			success: function(response, options) {
				if (response.status == 200) {
					var xml = response.responseXML;
					var roomId = Ext.DomQuery.selectNumber("room_id", xml);
					if(roomId == -4003 || roomId == -4002 || roomId == -4001) {
		                Ext.MessageBox.prompt(l10n('enter-pin'), l10n('provide-pin-for-scheduled-room'), function(btn, text){
		                    if (btn == 'ok'){
		                    	scope.controlMeetingPopup(btn,e,eopts,text.trim());
		                    }
		                });
						
					} else if (roomId > 0) {
						var win = window.open('roomcontrol.html?roomID=' + roomId, '_blank');
						if(win){
							//Browser has allowed it to be opened
							win.focus();
						} else{
							//Broswer has blocked it
							Ext.Msg.alert(l10n('error'),l10n('please-allow-popups-for-this-site'));
						}
					} else {
						Ext.Msg.alert(l10n('error'),l10n('room') +scope.getExtensionParamValue(scope.lookupReference('extension'))+l10n('does-not-exist'));
					}
				}
			},
			failure: function(response, options) {
				Ext.Msg.alert(l10n('error'),l10n('room') +scope.getExtensionParamValue(scope.lookupReference('extension'))+l10n('does-not-exist'));
			}
		});	
    },
  
    filterUsername: function(combo, e, eOpts){
    	var param={},
    	    scope = this,
    	    viewModel = scope.getViewModel(),
    	    roomOwnerComboStore = viewModel.get('roomOwnerComboStore');
    	if(roomOwnerComboStore){
    		if(combo.getValue() != null){
        		param = {
        			'query': combo.getValue()
        		}
        		roomOwnerComboStore.load({
            		params: param,
            		callback : function(recs) {
            			if(recs.length == '0'){
            				combo.textValid = false;
            				combo.textInvalid = l10n('room-owner')+" "+l10n('not-available');
            				combo.validate();
        				}else{
        					combo.textValid = true;
        					combo.textInvalid = '';
        					combo.validate();
        				}
            		}
            	});
        	} else {
        		roomOwnerComboStore.load();
        	}
    	}
    },
    selectedValidRoomOwner: function(combo){
    	combo.textValid = true;
		combo.textInvalid = '';
		combo.validate();
    }
});

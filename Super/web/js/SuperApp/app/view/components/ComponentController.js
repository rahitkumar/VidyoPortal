/**
 * This class is the main view for the application. It is specified in app.js as
 * the "autoCreateViewport" property. That setting automatically applies the
 * "viewport" plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your
 * application.
 */
Ext.define('SuperApp.view.components.ComponentController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.Component',

    requires : ['SuperApp.view.components.ComponentDetails', 'SuperApp.view.components.ReplaceOverlay'],

    reloadData : function() {
        this.getComponentGridData();
    },

    refreshGrid : function() {
        this.onComponentsActivate();
    },

    checkDuplicateComponentName : function(data) {
        var me = this,
            viewModel = me.getViewModel(),
            componentStore = viewModel.getStore('componentStore'),
            compID = data.compID ? data.compID : data.compuniqueid,
            flag = false;

        componentStore.each(function(rec) {
            if (compID != rec.get('compID') && Ext.util.Format.htmlDecode(rec.get('name')) === Ext.String.trim(data.name)) {
                flag = true;
                return;
                //
            }
        });
        return flag;

    },

    getComponentGridData : function(params) {
        var me = this,
            taskRun,
            count = 1,
            viewModel = me.getViewModel(),
            callComponentData = function() {
            var componentStore = viewModel.getStore('componentStore'),
            filterFormValues = me.lookupReference('topToolbar').getValues();

            componentStore.removeAll();
            componentStore.load({
                params : filterFormValues,
                callback : function(recs) {
                    var componentsGrid = me.lookupReference('componentsGrid'),
                    height = recs.length * 30;
                    componentsGrid.bindStore(componentStore);
                    componentsGrid.reconfigure();
                    componentsGrid.getSelectionModel().deselectAll();

                    recs.forEach(function(record) {
                        if (document.getElementById('components-alarm-icon-' + record.get("id"))) {
                            Ext.create('Ext.tip.ToolTip', {
                                target : 'components-alarm-icon-' + record.get("id"),
                                html : record.get('alarm')
                            });
                        }

                        var myToolTipText = '';
                        if (record.get('status') === 'ACTIVE') {
                        	if (record.get('compStatus') == "UP") {
                            	myToolTipText = l10n('compstatus-up');
                            } else if (record.get('compStatus') == "DOWN") {
                            	myToolTipText = l10n('compstatus-down');
                            }
                        } else if (record.get('status') === 'INACTIVE') {
                        	myToolTipText = l10n('disabled');
                        } else { // NEW
                        	if (record.get('compStatus') == "UP") {
                        		myToolTipText = l10n('new') + ' & ' + l10n('compstatus-ready');
                            } else if (record.get('compStatus') == "DOWN") {
                            	myToolTipText = l10n('new') + ' & ' + l10n('unavailable');
                            }
                        }
                        
                        Ext.create('Ext.tip.ToolTip', {
                            target : 'components-status-icon-' + record.get("id"),
                            html : myToolTipText
                        });
                    });
                }
            });
        },
            getModifiedPresent = function() {
            Ext.Ajax.request({
                url : 'ismodifiedpresent.ajax',
                method : 'GET',
                success : function(response) {
                    var result = Ext.JSON.decode(response.responseText);
                    if (result.success) {
                    	me.getView().lookupReference('modifiedAvailable').show();
                        return;
                    }
                    me.getView().lookupReference('modifiedAvailable').hide();
                }
            });
        };
        callComponentData();
        getModifiedPresent();
        /*var intervalfn = function() {
            if ((!me.view.isHidden() && me.getView().getLayout().getActiveItem().reference == "componentpanel")) {
                callComponentData();
                getModifiedPresent();
            } else {
                Ext.TaskManager.stop(taskRun);
                viewModel.set('taskRun', 0);
            }
            
        };

        if (viewModel.get('taskRun') == 0) {
        taskRun = Ext.TaskManager.start({
            run : intervalfn,
            interval : 45000
        });
            viewModel.set('taskRun', Math.floor(Math.random() * 10) + 1);
        }*/
    },

    deleteRecord : function() {
        var me = this,
            component = me.lookupReference('componentsGrid').getView().getSelectionModel().getSelection()[0],
            componentType = component ? component.get('compType').name : '',
            componentName = component ? component.get('name') : '';
        if (!component) {
            Ext.Msg.alert(l10n('error'), l10n('please-select-at-least-one-component-to-delete'));
            return;
        }
        Ext.Ajax.request({
            url : 'checkneusage.ajax',
            method : 'GET',
            params : {
                type : componentType,
                serviceName : componentName
            },
            success : function(response) {
                var result = response.responseXML,
                    isSuccess = Ext.DomQuery.selectValue('message @success', result);

                if (isSuccess == "false") {
                    Ext.Msg.confirm(l10n('confirmation'), l10n('do-you-really-want-to-delete-the-selected-components'), function(res) {
                        if (res == 'yes') {
                            me.deleteComponents(false, component, 0);
                        }
                    });
                    return;
                }
                me.replaceAndDelete(component, componentType);
            }
        });

    },

    replaceAndDelete : function(component, componentType) {
        var me = this,
        viewModel = me.getViewModel(),
        overlay = Ext.widget('replaceoverlay', {
        	component : component,
            floatParent : me.view
        }),
        componentsStore = me.lookupReference('componentsGrid').getStore(),
            data = {
            'componentType' : componentType,
            'componentName' : component.get('name'),
            'status' : component.get('status')
        };
        
        var store = viewModel.getStore('replacecomponentstore');
        store.removeAll();
        componentsStore.each(function(rec) {
            if (rec.get('compType').name == componentType && component.get('name') !== rec.get('name') && rec.get('status') == "ACTIVE") {
                store.add(new Ext.data.Record({
                    'id' : rec.get('id'),
                    'name' : rec.get('name'),
                    'status' : rec.get('status')
                }));
            }
        });
        
        if(store.getCount() <= 0) {
        	if(componentType == 'VidyoRouter') {
        		Ext.Msg.alert(l10n('failure'), l10n('cannot-delete-router'));	
        	} else {
        		Ext.Msg.alert(l10n('failure'), l10n('cannot-delete-network-component'));
        	}
        	return;
        }
        
        if (overlay) {
            overlay.show();
            overlay.down('component[reference=componentdetail]').setData(data);
            var grid = overlay.down('grid'),
            typeColumn = grid.columns[0];
            typeColumn.setText(componentType);
            grid.bindStore(store);
        }
    },

    onClickReplaceDelete : function(btn) {
        var me = this,
            grid = me.lookupReference('replacecomponentgrid'),
            replaceoverlay = me.lookupReference('replaceoverlay');
        selection = grid.getSelection()[0],
        component = btn.component,
        replaceId = selection.get('id');

        me.deleteComponents(false, component, replaceId, replaceoverlay);

    },

    onSelectReplaceGridRecord : function(grid, record) {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.set('isDisableReplace', false);
    },

    onDeSelectReplaceGridRecord : function(grid, record) {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.set('isDisableReplace', true);
    },

    deleteComponents : function(confirmation, component, replacementId, replaceoverlay) {
        var me = this;
        var componentsStore = me.lookupReference('componentsGrid').getStore();

        Ext.Ajax.request({
            url : 'deletecomponents.ajax',
            method : 'POST',
            jsonData : [confirmation, component.get('id'), replacementId],
            success : function(response) {
                if (response.responseText) {
                    var obj = Ext.decode(response.responseText);
                    if (!confirmation) {
                        if (!obj.length) {
                            Ext.Msg.alert(l10n('success'), l10n('component-successfully-deleted'));
                            if (replaceoverlay) {
                                replaceoverlay.destroy();
                            }
                            me.getComponentGridData();
                        } else {
                            var componentsNames = [];
                            for (var counter = 0; counter < obj.length; counter++) {
                                componentsNames.push(componentsStore.getById(obj[counter]).get('name'));
                            }
                            if (replaceoverlay) {
                                replaceoverlay.destroy();
                            }
                            Ext.Msg.confirm(l10n('confirmation'), componentsNames.join(',') +' '+ l10n('components-associated-with-pools'), function(btn) {
                                if (btn == "yes") {
                                    me.deleteComponents(true, component, replacementId);
                                }
                            }, me);
                        }
                    } else {
                        Ext.Msg.alert(l10n('success'), l10n('component-successfully-deleted'));
                        if (replaceoverlay) {
                            replaceoverlay.destroy();
                        }
                        me.getComponentGridData();
                    }
                } else {
                    Ext.Msg.alert(l10n('success'), l10n('component-successfully-deleted'));
                    me.getComponentGridData();
                    if (replaceoverlay) {
                        replaceoverlay.destroy();
                    }
                }
            },
            failure : function(response, opts) {
                Ext.Msg.alert(l10n('failure'), 'server-side failure with status code ' + response.status);
            }
        });
        //}
    },

    enableRecordConfirmation : function() {
        var scope = this;
        var selComponents = scope.lookupReference('componentsGrid').getView().getSelectionModel().getSelection();
        if (selComponents.length == 0) {
            Ext.Msg.alert(l10n('error'), l10n('please-select-one-component-to-enable'));
            return;
        } else {
            Ext.MessageBox.confirm(l10n('confirmation'), l10n('enable-selected-network-component'), this.enableRecord, scope);
        }
    },
    enableRecord : function(btn) {
        var scope = this;
        if (btn == "yes") {
            var selComponents = scope.lookupReference('componentsGrid').getView().getSelectionModel().getSelection()[0];

            Ext.Ajax.request({
                url : 'enablecomponent.ajax',
                method : 'POST',
                jsonData : selComponents.get("id"),
                success : function(response) {

                    Ext.Msg.alert(l10n('success'), l10n('component-successfully-enabled'), function() {
                        scope.getComponentGridData();
                    });
                },
                failure : function(res) {
                    Ext.Msg.alert(l10n('failure'));
                }
            });
        }
    },
    disableRecordConfirmation : function() {
        var selComponents = this.lookupReference('componentsGrid').getView().getSelectionModel().getSelection();
        if (selComponents.length == 0) {
            Ext.Msg.alert(l10n('error'), l10n('please-select-one-component-to-disable'));
            return;
        } else {
            Ext.MessageBox.confirm(l10n('confirmation'), l10n('disable-selected-network-component'), this.disableRecord, this);
        }
    },
    disableRecord : function(btn) {
        var scope = this;
        if (btn == "yes") {
            var selComponents = this.lookupReference('componentsGrid').getView().getSelectionModel().getSelection()[0];

            Ext.Ajax.request({
                url : 'disablecomponent.ajax',
                jsonData : selComponents.get("id"),
                method : 'POST',
                success : function() {
                    Ext.Msg.alert(l10n('success'), l10n('component-successfully-disabled'), function() {
                        scope.getComponentGridData();
                    });
                },
                failure : function(res) {
                    Ext.Msg.alert(l10n('failure'));
                }
            });
        }

    },
    backfromfloat : function() {
        this.lookupReference('overlayPanel').hide();
        this.lookupReference('topToolbar').show();
        this.lookupReference('componentsGrid').show();
        this.lookupReference('upperToolbar').show();
        this.lookupReference('bottomToolbar').show();
    },

    gridCellClick : function(thisobj, td, cellIndex, record, tr, rowIndex, e, eOpts) {
        var me = this,
            viewModel = me.getViewModel(),
            type = record.get('compType').name,
            id = record.get('id');

        if (cellIndex === 2) {
            switch(type) {
            case 'VidyoManager' :
            	var editVidyoManagerWin = Ext.create('SuperApp.view.components.VidyoManagerView', {
                    floatParent : this.view
                });
                var editForm = editVidyoManagerWin.down('form');
                var model = Ext.create('SuperApp.model.components.VidyoManagerModel');
                model.getProxy().setExtraParam('id', id);
                model.load({
                    scope : this,
                    failure : function(record, operation) {
                        Ext.Msg.alert(l10n('error'), l10n('not-able-to-load-vidyo-manager'));
                    },
                    success : function(record, operation) {
                    	editForm.loadRecord(record);
                    	if(record.get('dscpvalue') && record.get('dscpvalue') != '0') {
                    		this.lookupReference('dscpcheckmanager').setValue(true);
                    	}
                    }
                });
                editVidyoManagerWin.show();
                break;
            case 'VidyoRouter' :
            	var editRouterWin = Ext.create('SuperApp.view.components.RouterView', {
                        floatParent : this.view
            	});
                var editForm = editRouterWin.down('form');
                var model = Ext.create('SuperApp.model.components.RouterModel');
                model.getProxy().setExtraParam('id', id);
                model.load({
                    scope : this,
                    failure : function(record, operation) {
                        Ext.Msg.alert(l10n('error'), l10n('not-able-to-load-vidyo-router'));
                    },
                    success : function(record, operation) {
                        var mediaAddressStore = viewModel.get('MediaAddressStore');
                        mediaAddressStore.removeAll();
                        mediaAddressStore.add(record.get('routerMediaAddrMap'));
                        editForm.getForm().loadRecord(record);
                    	if(record.get('dscpVidyo') && record.get('dscpVidyo') != '0') {
                    		this.lookupReference('videodscprouter').setValue(true);
                    	}
                    	if(record.get('audioDscp') && record.get('audioDscp') != '0') {
                    		this.lookupReference('audiodscprouter').setValue(true);
                    	}
                    	if(record.get('contentDscp') && record.get('contentDscp') != '0') {
                    		this.lookupReference('contentdscprouter').setValue(true);
                    	}
                    	if(record.get('singnalingDscp') && record.get('singnalingDscp') != '0') {
                    		this.lookupReference('signaldscprouter').setValue(true);
                    	}
                    }
                });
                editRouterWin.show();            	
                break;
            case "VidyoGateway":
            	var editGatewayWin = Ext.create('SuperApp.view.components.GatewayView', {
                    floatParent : this.view
            	});
	            var editForm = editGatewayWin.down('form');
	            var model = Ext.create('SuperApp.model.components.GatewayModel');
	            model.getProxy().setExtraParam('id', id);
	            model.load({
	                scope : this,
	                failure : function(record, operation) {
	                    Ext.Msg.alert(l10n('error'), l10n('not-able-to-load-vidyo-gateway'));
	                },
	                success : function(record, operation) {
	                    var gatewayPrefixStore = viewModel.get('GatewayPrefixStore');
	                    gatewayPrefixStore.getProxy().setExtraParam('id', record.get('id'));
	                    gatewayPrefixStore.load();
	                    editForm.getForm().loadRecord(record);
	                }
	            });
	            editGatewayWin.show();      
                break;
            case "VidyoReplay" :
            	var editReplayWin = Ext.create('SuperApp.view.components.ReplayView', {
                    floatParent : this.view
                });
                var editForm = editReplayWin.down('form');
                var model = Ext.create('SuperApp.model.components.ReplayModel');
                model.getProxy().setExtraParam('id', id);
                model.load({
                    scope : this,
                    failure : function(record, operation) {
                        Ext.Msg.alert(l10n('error'), l10n('not-able-to-load-vidyo-replay'));
                    },
                    success : function(record, operation) {
                    	editForm.loadRecord(record);
                    }
                });
                editReplayWin.show();
                break;
            case 'VidyoRecorder' :
            	var editRecorderWin = Ext.create('SuperApp.view.components.RecorderView', {
                    floatParent : this.view
                });
                var editForm = editRecorderWin.down('form');
                var model = Ext.create('SuperApp.model.components.RecorderModel');
                model.getProxy().setExtraParam('id', id);
                model.load({
                    scope : this,
                    failure : function(record, operation) {
                        Ext.Msg.alert(l10n('error'), l10n('not-able-to-load-vidyo-recorder'));
                        return;
                    },
                    success : function(record, operation) {
                    	editForm.loadRecord(record);
                    	var recorderId = record.get('id');
                    	var recorderEndpointsStore = me.getViewModel().get('recorderEndPointStore');
                    	recorderEndpointsStore.getProxy().setExtraParam('id', recorderId);
                    	recorderEndpointsStore.load();
                    }
                });
                editRecorderWin.show();
                break;
            }
        }  else if (cellIndex == 9) {
            if (record.get('mgmtUrl')) {
                window.open(record.get('mgmtUrl'));
            }
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

    checkbox_text_map : function(id_checkbox, id_text) {
        var dscpval = this.lookupReference(id_text).getValue();
        if (dscpval == 0) {
            this.lookupReference(id_checkbox).setValue(0);
            this.lookupReference(id_text).disable();
        } else {
            this.lookupReference(id_checkbox).setValue(1);
            this.lookupReference(id_text).enable();
        }
    },
    componentDetailSave : function(btn) {
        var me = this,
        view = me.view,
        compForm = btn.up('form'),
        data = compForm.getValues();

        if (me.checkDuplicateComponentName(data)) {
            Ext.Msg.alert('Error', 'Component name already exists');
            return;
        }
        
        if (data.password != data.confirmPassword){
            var confirmField = compForm.getForm().findField('confirmPassword');
            confirmField.markInvalid();
            confirmField.focus();
            return;
        }
        if (compForm && compForm.getForm().isValid()) {
            var jsonData = new Object(),
                passwordData = new Object(),
                replayData = new Object();

            var uName = data.userName;
            passwordData = JSON.stringify({
                userName : data.userName,
                password : data.password,
                id : data.id
            });
            jsonData.id = data.componentId;
            jsonData.compID = data.compuniqueid;
            jsonData.compType = {
                name : compForm.getForm().getRecord().get('compType')
            };
            jsonData.mgmtUrl = data.mgmtUrl;
            jsonData.name = data.name;
            jsonData = JSON.stringify(jsonData);
            var data = [];
            data = [jsonData, passwordData];
            Ext.Ajax.request({
                url : 'updatecomponent.ajax',
                method : 'POST',
                headers : {
                    'Content-Type' : 'application/json; charset=utf-8'
                },
                jsonData : data,
                success : function(response) {
                	if (response.responseText.search("true") == -1) {
                		if (response.responseText.search("duplicateuser") == -1) {
                			Ext.Msg.alert(l10n('message'), '"' + Ext.util.Format.htmlEncode(uName) + '" ' + l10n('invalid-username-match-js-message'));
                		} else {
                			Ext.Msg.alert(l10n('message'), '"' + Ext.util.Format.htmlEncode(uName) + '" ' + l10n('component-username-duplicate-error'));
                		}
                	} else {
                    	var window = compForm.up('window');
                    	if(window) {
                    		window.close();
                    		me.refreshGridData('componentsGrid');
                    	}
                	}
                },
                failure : function(response, opts) {
                	Ext.Msg.alert(l10n('Error'), l10n('failed-to-save'));
                }
            });
        }
    },

    componentDetailReset : function(btn) {
        var compForm = btn.up('form'),
    	window = compForm.up('window');
    	if(window) {
    		window.close();
    	}
    },

    onClickAddMediaAddressMap : function() {
        var me = this,
            viewModel = me.getViewModel(),
            mediaGrid = me.lookupReference('mediaaddressmap'),
            mediaStore = viewModel.get('MediaAddressStore'),
            rowEditing;

        mediaStore.add({
            id : '',
            localIP : '0.0.0.0',
            remoteIP : '0.0.0.0'
        });
        rowEditing = mediaGrid.getPlugin('routerMediaRowEditId');
        rowEditing.startEdit(mediaStore.getCount() - 1);
    },

    onClickRemoveMediaAddressMap : function() {
        var me = this,
            viewModel = me.getViewModel(),
            mediaGrid = me.lookupReference('mediaaddressmap'),
            mediaStore = viewModel.get('MediaAddressStore'),
            recCount = mediaStore.getCount(),
            gridHeight = mediaGrid.getHeight();

        if (mediaGrid.getHeight() > 200 && recCount % 2) {
            mediaGrid.setHeight(gridHeight - 50);
        }

        ;
        if (mediaStore.getCount() > 0) {
            var sm = mediaGrid.getSelectionModel(),
                sel = sm.getSelection()[0];
            if (sel) {
                mediaStore.remove(sel);
            } else {
                Ext.Msg.alert(l10n('error'), l10n('please-select-one-mediaaddressmap-to-delete'));
            }
        } else {
            Ext.Msg.alert(l10n('error'), l10n('at-least-one-mediaaddressmap-is-required'));
        }
    },

    vmDscpCheckbox : function(obj, checked) {
        if (checked)
            this.lookupReference('dscpid').enable();
        else {
            this.lookupReference('dscpid').setValue(0);
            this.lookupReference('dscpid').disable();
        }
    },
    vmSave : function(btn) {
        var me = this,
        form = btn.up('form'),
        data = form.getValues();
        if (Ext.String.trim(data.name).length == 0) {
            Ext.Msg.alert('Error', l10n('display-name') + ' ' + l10n('empty'));
            return;
        }
        
        if (this.checkDuplicateComponentName(data)) {
            Ext.Msg.alert('Error', 'Component name already exists');
            return;
        }
        if (form.isValid()) {
            var jsonData = new Object();
            var jsonDataComp = new Object();
            jsonDataComp.id = data.compid;
            jsonDataComp.name = data.name;
            jsonDataComp.mgmtUrl = data.mgmtUrl;
            jsonData.id = parseInt(data.id);
            jsonData.soapport = parseInt(data.soapport);
            jsonData.rmcpport = parseInt(data.rmcpport);
            jsonData.fqdn = data.fqdn;
            jsonData.dscpvalue = data.dscpvalue ? data.dscpvalue : 0;
            jsonData.emcpport = parseInt(data.emcpport);
            jsonData.components = jsonDataComp;
            jsonData = Ext.JSON.encode(jsonData);
            Ext.Msg.confirm(l10n('confirmation'), l10n('vidyomanger-save-changes'), function(res) {
                if (res == "yes") {
                    Ext.Ajax.request({
                        url : 'updatevidyomanager.ajax',
                        method : 'POST',
                        headers : {
                            'Content-Type' : 'application/json; charset=utf-8'
                        },
                        jsonData : jsonData,
                        success : function(response) {
                            var result = Ext.decode(response.responseText);
                            if (result.success) {
                            	var window = form.up('window');
                            	if(window) {
                            		window.close();
                            		me.refreshGridData('componentsGrid');
                            	}
                            } else {
                                Ext.Msg.alert('Failure', result.message);
                            }
                        }
                    });
                }
            });
    		
        }
    },
    routerSave : function(btn) {
        var me = this,
        form = btn.up('form').getForm();
        var mediaAddrMapGrid = this.getView().lookupReference('mediaaddressmap');
        var mediaAddrMapStore = me.getViewModel().get('MediaAddressStore');
        var data = form.getValues();

        if (Ext.String.trim(data.name).length == 0) {
            Ext.Msg.alert('Error', l10n('display-name') + ' ' + l10n('empty'));
            return;
        }
        
        if (this.checkDuplicateComponentName(data)) {
            Ext.Msg.alert('Error', 'Component name already exists');
            return;
        }
        if (form.isValid()) {
            var jsonData = new Object(),
                routerMedia = [];

            Ext.each(mediaAddrMapStore.getRange(), function(rec) {
                routerMedia.push({
                    id : Ext.isNumber(rec.get('id')) ? rec.get('id') : '',
                    localIP : rec.get('localIP'),
                    remoteIP : rec.get('remoteIP')
                });
            });

            jsonData.id = form.getRecord().get('id');
            jsonData.scipFqdn = data.scipFqdn;
            jsonData.scipPort = data.scipPort;
            jsonData.mediaPortStart = data.mediaPortStart;
            jsonData.mediaPortEnd = data.mediaPortEnd;
            jsonData.dscpVidyo = data.dscpVidyo;
            jsonData.audioDscp = data.audioDscp;
            jsonData.contentDscp = data.contentDscp;
            jsonData.routerMediaAddrMap = routerMedia;
            jsonData.singnalingDscp = data.singnalingDscp;
            if ( typeof (data.stunFqdn) != 'undefined') {
                jsonData.stunFqdn = data.stunFqdn;
                jsonData.stunPort = data.stunPort;
            }
            jsonData.components = {};
            jsonData.components.name = data.name;
            jsonData.components.mgmtUrl = data.mgmtUrl;
            jsonData = JSON.stringify(jsonData);
            var me = this;
            var data = jsonData;
            var modifiedAvailable = form.getRecord().get('routerPoolPresent');
            var confrmMessg = '';
            if(modifiedAvailable) {
            	confrmMessg =  l10n('vidyorouter-save-changes-manual-cloud-activation');
            } else {
            	confrmMessg = l10n('vidyorouter-save-changes-automatic-cloud-activation');
            }
            Ext.Msg.confirm(l10n('confirmation'), confrmMessg, function(res) {
                if (res == "yes") {
                    Ext.Ajax.request({
                        url : 'updatevidyorouter.ajax',
                        method : 'POST',
                        headers : {
                            'Content-Type' : 'application/json; charset=utf-8'
                        },
                        jsonData : data,
                        success : function(response) {
                          var result = Ext.decode(response.responseText);
                          if (result.success) {
                            var messg = l10n('saved');
                            if(result.isRouterPoolPresent) {
                            	messg = l10n('cloud-config-activation-confirmation');
                            }
                            Ext.Msg.alert('Success', messg, function() {
                            		if(result.isRouterPoolPresent) {
                            			me.getView().lookupReference('modifiedAvailable').show();
                            		} else {
                            			me.getView().lookupReference('modifiedAvailable').hide();
                            		}
                            		btn.up('window').close();
                            		me.refreshGridData('componentsGrid');
                               	});
                            } else {
                            	Ext.Msg.alert('Failure', result.message);
                            	btn.up('window').close();
                            }
                        }
                    });
                }
            });
        }
    },

    videoDscp : function(obj, checked) {
        if (checked)
            this.lookupReference('videodscpid').enable();
        else {
            this.lookupReference('videodscpid').setValue(0);
            this.lookupReference('videodscpid').disable();
        }
    },
    audioDscp : function(obj, checked) {
        if (checked)
            this.lookupReference('audiodscpid').enable();
        else {
            this.lookupReference('audiodscpid').setValue(0);
            this.lookupReference('audiodscpid').disable();
        }
    },
    contentDscp : function(obj, checked) {
        if (checked)
            this.lookupReference('contentdscpid').enable();
        else {
            this.lookupReference('contentdscpid').setValue(0);
            this.lookupReference('contentdscpid').disable();
        }
    },
    signalingDscp : function(obj, checked) {
        if (checked)
            this.lookupReference('signaldscpid').enable();
        else {
            this.lookupReference('signaldscpid').setValue(0);
            this.lookupReference('signaldscpid').disable();
        }
    },

    stunAdd : function(obj, nv, ov, eobj) {
        var me = this,
            viewModel = me.getViewModel();
        if (nv) {
            this.lookupReference("stunpart").show();
            this.lookupReference("mediaaddressmap").hide();
            viewModel.set('isstunip', false);
            viewModel.set('isstunport', false);
        }
    },
    vidyoManagerReset : function(btn) {
        var compForm = btn.up('form'),
    	window = compForm.up('window');
    	if(window) {
    		window.close();
    	}
    },
    vidyoRouterReset : function(btn) {
        var compForm = btn.up('form'),
    	window = compForm.up('window');
    	if(window) {
    		window.close();
    	}
    },
    vidyoRecReset : function() {
        var rec = this.getViewModel().get("vRecorderRecord");
        this.lookupReference("vidyoRecorderForm").getForm().loadRecord(rec);
        this.lookupReference("pwdid").reset();
        this.lookupReference("vpwdid").reset();
        // this.lookupReference("changePwd").reset();
    },
    vidyoReplayReset : function() {
        var rec = this.getViewModel().get("vReplayRecord");
        this.lookupReference("ReplayForm").getForm().loadRecord(rec);
        this.lookupReference("pwdid").reset();
        this.lookupReference("vpwdid").reset();
        //     this.lookupReference("changePwd").reset();
    },
    vidyoGatewayReset : function() {
        var rec = this.getViewModel().get("vGatewayRecord");
        this.lookupReference("GatewayForm").getForm().loadRecord(rec);
        this.lookupReference("pwdid").reset();
        this.lookupReference("vpwdid").reset();
        //   this.lookupReference("changePwd").reset();
    },
    updateRouterMediaRecord : function(editor, e) {
        /* var me = this;
         var id = e.record.get('id');
         var formData = this.lookupReference("vidyoroutereditor").getValues();
         var routerConfigId = formData.routerpkID;
         var localIp = e.record.get('localIP');
         var remoteIp = e.record.get('remoteIP');
         var params = {};
         var isBlank = this.getViewModel().get("routerMediaAddRecord");
         params = {
         id : isBlank == "blank" ? 0 : id,
         localIP : localIp,
         remoteIP : remoteIp,
         vidyoRouter : {
         id : routerConfigId
         }
         };

         Ext.Ajax.request({
         url : 'updatemediaaddressmap.ajax',
         method : 'POST',
         jsonData : JSON.stringify(params),
         success : function(result) {
         var record = e.record;
         record.set('id', Ext.decode(result.responseText).id);
         Ext.Msg.alert(l10n("saved"), l10n('saved-mediaaddressmap-successfully'));
         me.loadMediaAddressMap('rg', true);
         },
         failure : function() {
         Ext.Msg.alert(l10n("error"), l10n('failure'));
         }
         });*/
    },
    checkIfBlankRecord : function(editor, e) {
        var me = this,
            viewModel = me.getViewModel(),
            MediaAddressStore = viewModel.getStore('MediaAddressStore'),
            recCount = MediaAddressStore.getCount(),
            grid = me.lookupReference('mediaaddressmap'),
            gridHeight = grid.getHeight();

        if (recCount > 1 && recCount % 2) {
            grid.setHeight(gridHeight + 50);
        }
        var record = e.record.get('localIP');
        if (record == "") {
            this.getViewModel().set("routerMediaAddRecord", "blank");
        }
    },
    deleteRouterMediaRecord : function(editor, e) {
        editor.cancelEdit();
        if (e.record.get('localIP') != "0.0.0.0" && e.record.get('remoteIP') == "0.0.0.0") {
            //    e.store.remove(e.record);
        }
    },

    addToHistory : function(newToken) {
        this.getView().lookupController(true).getViewModel().set('callItemClick', false);
        Ext.History.add(newToken);
    },

    componentsFilterChanged : function(field) {
        //var filterFormValues = field.up('form').getValues();
        this.getComponentGridData();
    },

    onComponentsActivate : function() {
        if (arguments.length < 2) {
            var me = this;
            if (me.getView().getLayout().getActiveItem().reference == "componentpanel" 
                && me.view.up('#mainPanel').getLayout().getActiveItem().getItemId() != "welcomebannerpnl")
                me.getComponentGridData();
        }
    },

    onClickRestoreDefaultVM : function(btn) {
        var me = this;
        var form = btn.up('form');
        var id = form.getRecord().get('componentId');
        
        Ext.Msg.confirm(l10n('confirmation'), l10n('do-you-really-want-to-restore-all-data-to-factory-defaults'), function(res) {
            if (res == 'yes') {
                Ext.Ajax.request({
                    url : 'setfactorydefaultforcomponent.ajax',
                    method : 'POST',
                    params : {
                        id : id
                    },
                    success : function(res) {
                        var obj = Ext.decode(res.responseText);
                        if (obj.success == "true") {
                            var model = Ext.create('SuperApp.model.components.VidyoManagerModel');
                            model.getProxy().setExtraParam('id', id);
                            model.load({
                                scope : this,
                                failure : function(record, operation) {
                                    Ext.Msg.alert(l10n('error'), l10n('not-able-to-load-vidyo-manager'));
                                },
                                success : function(record, operation) {
                                	form.loadRecord(record);
                                }
                            });
                        } else {
                        	Ext.Msg.alert(l10n('error'), 'Failed to restore VidyoManager');
                            return;
                        }
                    },
                    failure : function(res) {
                    	Ext.Msg.alert(l10n('error'), 'Failed to restore VidyoManager');
                        return;
                    }
                });
            }
        });
        
    },

    onClickRestoreDefaultVR : function(btn) {
        var me = this;
        var form = btn.up('form');
        var id = form.getRecord().get('componentpkId');
        var mediaAddrMapGrid = this.getView().lookupReference('mediaaddressmap');
        Ext.Msg.confirm(l10n('confirmation'), l10n('do-you-really-want-to-restore-all-data-to-factory-defaults'), function(res) {
            if (res == 'yes') {
		        Ext.Ajax.request({
		            url : 'setfactorydefaultforcomponent.ajax',
		            method : 'POST',
		            params : {
		                id : id
		            },
		            success : function(res) {
		                var obj = Ext.decode(res.responseText);
		                if (obj.success == "true") {
		                	var model = Ext.create('SuperApp.model.components.RouterModel');
                            model.getProxy().setExtraParam('id', id);
                            model.load({
                                scope : this,
                                failure : function(record, operation) {
                                    Ext.Msg.alert(l10n('error'), l10n('not-able-to-load-vidyo-router'));
                                },
                                success : function(record, operation) {
                                	mediaAddrMapGrid.getStore().removeAll();
                                	form.loadRecord(record);
                                }
                            });
		                } else {
                        	Ext.Msg.alert(l10n('error'), 'Failed to restore VidyoRouter');
                            return;
                        }
		            },
                    failure : function(res) {
                    	Ext.Msg.alert(l10n('error'), 'Failed to restore VidyoRouter');
                        return;
                    }
		        });
            }
        });
    },
    refreshGridData : function(gridRef) {
        var grid = this.lookupReference(gridRef);
        if(grid) {
        	grid.getStore().reload({callback: function(){
                grid.getView().refresh();
            }});
        }
    }
    
});


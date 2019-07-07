Ext.define('SuperApp.view.cloud.locationTags.LocationController', {
    extend : 'Ext.app.ViewController',

    alias : 'controller.location',

    control : {
        '#' : {
            activate : 'onLocationActivate',
            reloadInfo : 'onReloadInfo'
        },
        'location grid[reference="locationGrid"]' : {
            rowclick : 'locationRowClick'
        }
    },

    locationRowClick: function(grid, record, tr, rowIndex, e, eOpts) {
    	grid.getSelectionModel().select(record);
    },
    
    onLocationActivate : function() {
        this.onReloadInfo();
    },

    getDataForLocationData : function() {
    	var me = this;
        Ext.Ajax.request({
            url : 'getlocations.ajax',
            method : 'GET',
            params : {
                sort : 'locationTag'
            },
            success : function(res) {
                var locations = Ext.decode(res.responseText);
                me.getViewModel().storeInfo.locationTagsStore.loadData(locations);
                me.setEnableDisableEditingPlugin(me.isModifiedSelected());
            },
            failure : function(res) {
            	Ext.Msg.alert(l10n('error'), l10n('failure'));
            }
        });
    },

    onReloadInfo : function(isModified) {
        this.setEnableDisableEditingPlugin(isModified);
        this.getDataForLocationData();
    },

    setEnableDisableEditingPlugin : function(isModified) {
        var grid = this.lookupReference('locationGrid');
        var plugins = grid.getPlugins();
        var rowEditing = null;
        plugins.forEach(function(plugin) {
            if (plugin.clicksToMoveEditor) {
                rowEditing = plugin;
            }
        });
        if (isModified === true) {
            rowEditing.enable();
        } else {
            rowEditing.cancelEdit();
            rowEditing.disable();
        }
    },

    isModifiedSelected : function() {
        return this.getView().lookupViewModel(true).get('isModified');
    },

    refreshGrid : function() {
        this.onLocationActivate();
    },

    addLocation : function() {
        if (this.isModifiedSelected()) {
            var grid = this.lookupReference('locationGrid');
            var plugins = grid.getPlugins();
            var rowEditing = null;
            plugins.forEach(function(plugin) {
                if (plugin.clicksToMoveEditor) {
                    rowEditing = plugin;
                }
            });

            rowEditing.cancelEdit();

            // Create a model instance
            var r = Ext.create('SuperApp.model.cloud.LocationTagsModel', {
                locationID : null,
                locationTag : ''
            });

            grid.getStore().insert(0, r);
            rowEditing.startEdit(0, 0);
        } else {
        	Ext.Msg.alert(l10n('error'), l10n('select-modified-version'));
        }
    },

    editLocation : function() {
        if (this.isModifiedSelected()) {
            var grid = this.lookupReference('locationGrid');

            var selectedModel = grid.getSelection()[0];

            if (!selectedModel) {
            	Ext.Msg.alert(l10n('error'), l10n('select-a-location-tag'));
            } else {
                var plugins = grid.getPlugins();
                var rowEditing = null;
                plugins.forEach(function(plugin) {
                    if (plugin.clicksToMoveEditor) {
                        rowEditing = plugin;
                    }
                });

                var recordIndex = grid.getStore().indexOf(selectedModel);

                rowEditing.startEdit(recordIndex);
            }
        } else {
        	Ext.Msg.alert(l10n('error'), l10n('select-modified-version'));
        }
    },

    deleteLocation : function() {
        var me = this;
        if (this.isModifiedSelected()) {
            var grid = this.lookupReference('locationGrid');

            var selectedModel = grid.getSelection()[0];

            if (!selectedModel) {
                Ext.Msg.show({
                    title : l10n('error'),
                    message : l10n('select-a-location-tag'),
                    buttons : Ext.Msg.OK
                });
            } else if(selectedModel.get('locationID') == 1) {
            	Ext.Msg.alert(l10n('error'), l10n('cannot-delete-default-location'));
            } else {
                Ext.Msg.show({
                    title : l10n('confirmation'),
                    message : l10n('location-msgbox-delete-confirm-messg') + ': ' + selectedModel.get('locationTag'),
                    buttons : Ext.Msg.YESNO,
                    icon : Ext.Msg.QUESTION,
                    fn : function(btn) {
                        if (btn === 'yes') {
                            var id = new Array();
                            Ext.Array.each(selectedModel, function(obj, index) {
                                id.push(obj.get("locationID") * 1);
                            });
                            Ext.Ajax.request({
                                url : 'deletelocation.ajax',
                                jsonData : id,
                                method : 'POST',
                                success : function(response) {
                                	if(response.responseText.search("true") == -1){
                                		 Ext.Msg.alert(l10n('error'), l10n('locations-used-by-rules-cannot-be-deleted'), function() {
                                             me.onLocationActivate();
                                         });
                                	} else {
                                		 Ext.Msg.alert(l10n('success'), l10n('location-deleted-successfully'), function() {
                                             me.onLocationActivate();
                                         });
                                	}
                                },
                                failure : function(response, opts) {
                                	Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
                                        me.onLocationActivate();
                                    });
                                    console.log('server-side failure with status code ' + response.status);
                                }
                            });
                        }
                    }
                });
            }
        } else {
        	Ext.Msg.alert(l10n('error'), l10n('select-modified-version'));
        }
    },
    
    onLocationListValidateEdit: function(editor, e) {
    	if(Ext.isEmpty(editor.context.newValues) || Ext.isEmpty((editor.context.newValues.locationTag).trim())) {
        	Ext.Msg.alert(l10n('error'), l10n('please-specify-location-tag'));
        	return false;
    	} else if (e.originalValues.locationTag == e.newValues.locationTag) {
            editor.cancelEdit();
            return false;
        } else {
            var newValue = e.newValues.locationTag;
            var existingIndex = this.getViewModel().getStore('locationTagsStore').findRecord('locationTag', newValue.trim(), 0, false, false, true);
            if (existingIndex) {
        		var messg = l10n('duplicate-tag-name');
        		messg = messg.replace("{0}", newValue);
                Ext.Msg.alert(l10n('error'), messg);
                return false;
            } else {
                return true;
            }
        }
    },
    
    onLocationListEdit : function(editor, e) {
        var me = this;
        var record = e.record.getData();
        
        if(record.locationID == 1) {
        	Ext.Msg.alert(l10n('error'), l10n('cannot-edit-default-location'));
        	return;
        }
	        
        delete record.id;

        if (!record.locationID) {
            delete record.locationID;
        }
        
        if(!record.locationID) {
            record.locationID = null;
        }
        
        if(record.locationTag) {
        	record.locationTag = record.locationTag.trim();
        }
      
        var recordString = Ext.JSON.encode(record);

        Ext.Ajax.request({
            url : 'addlocation.ajax',
            jsonData : record,
            method : 'POST',
            success : function(res) {
            	var location = Ext.decode(res.responseText);
            	if(location.locationID ==  '-1') {
            		var messg = l10n('duplicate-tag-name');
            		messg = messg.replace("{0}", record.locationTag);
                    Ext.Msg.alert(l10n('error'), messg);
                    me.onLocationActivate();
                    return;
            	} else {
                    Ext.Msg.alert(l10n('success'), l10n('location-tag-saved'), function() {
                        me.onLocationActivate();
                    });
            	}
            },
            failure : function(res) {
                Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
                    me.onLocationActivate();
                });
            }
        });
    },

    onLocationListCancelEdit : function(editor, e) {
        if (!e.record.get('locationID')) { 
            e.store.remove(e.record);
        }
    },
    
    activatePool: function(record) {
    	this.getView().lookupController(true).activatePool(record);
    }
});

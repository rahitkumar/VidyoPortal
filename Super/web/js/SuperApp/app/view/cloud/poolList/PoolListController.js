Ext.define('SuperApp.view.cloud.poolList.PoolListController', {
    extend : 'Ext.app.ViewController',

    alias : 'controller.poolList',

    control : {
        '#' : {
            reloadInfo : 'onReloadInfo',
            render : 'onRenderView'
        },
        'priorityDetail' : {
            activate : 'onDetailActivate'
        }
    },

    onReloadInfo : function(isModified) {
        var me = this;
        
        me.view.getLayout().setActiveItem(0);
        me.view.setLoading(true);
        this.setEnableDisableEditingPlugin(this.isModifiedSelected());
        this.enableDisableDetailGridPlugin(this.isModifiedSelected());
    },

    onRenderView : function() {
        var me = this,
            isModified = me.isModifiedSelected(),
            status = "ACTIVE";

        if (isModified) {
            status = "MODIFIED";
        }
        me.onReloadInfo();
    },

    getPoolListData : function(status) {
        var me = this,
            viewModel = me.getViewModel();
        viewModel.getStore('poolListSummary').load({
            params : {
                status : status
            },
            callback : function(res) {
                if (res.length) {
                    res.forEach(function(record) {
                        if (document.getElementById('disclosure-image-priority-' + record.get("id"))) {
                            Ext.create('Ext.tip.ToolTip', {
                                target : 'disclosure-image-priority-' + record.get("id"),
                                html : l10n('details-of-priority-list') + record.get('priorityListName')
                            });
                        }
                    });
                }
                me.getView().setLoading(false);
            }
        });
        if (status == "MODIFIED") {
            viewModel.set('isPoolsActivate', true);
        } else {
            viewModel.set('isPoolsActivate', false);
        }
    },

    setEnableDisableEditingPlugin : function(isModified) {
        var me = this,
            status = "ACTIVE";
        if (isModified) {
            status = "MODIFIED";
        }
        me.getPoolListData(status);
    },

    isModifiedSelected : function() {
        return this.getView().lookupViewModel(true).get('isModified');
    },

    addPriorityList : function() {
        this.getView().setActiveItem(1);
        this.loadPriorityListDetails(Ext.create('Ext.data.Model'));
    },

    deletePriorityList : function() {
        var me = this;
        if (this.isModifiedSelected()) {
        	
            var grid = this.lookupReference('priorityListGrid');

            var selectedModel = grid.getSelection()[0];

            if (!selectedModel) {
                Ext.Msg.show({
                    title : l10n('error'),
                    message : l10n('select-priority-list-to-delete'),
                    buttons : Ext.Msg.OK
                });
            } else {
                Ext.Msg.show({
                    title : l10n('confirmation'),
                    message : l10n('priority-list-msgbox-delete-confirm-messg') + ': ' + selectedModel.get('priorityListName'),
                    buttons : Ext.Msg.YESNO,
                    icon : Ext.Msg.QUESTION,
                    recordToDelete : selectedModel,
                    fn : function(btn, text, opt) {
                        if (btn === 'yes') {
                            var id = new Array();
                            id.push(opt.recordToDelete.get('id'));
                        	
                            Ext.Ajax.request({
                                url : 'deletepoolprioritylist.ajax',
                                jsonData : id,
                                method : 'POST',
                                success : function(response) {
                                    if (response.responseText.search("true") == -1) {
                                        Ext.Msg.alert(l10n('error'), l10n('priority-list-used-by-rules-cannot-be-deleted'), function() {
                                            me.refreshGrid();
                                        });
                                    } else {
                                        Ext.Msg.alert(l10n('success'), l10n('priority-list-deleted-successfully'), function() {
                                            me.refreshGrid();
                                        });
                                    }
                                },
                                failure : function() {
                                    Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
                                    });
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

    priorityListClick : function(grid, record, tr, rowIndex, e, eOpts) {
        if (e.target.tagName === "IMG") {
            this.getView().setActiveItem(1);
            this.loadPriorityListDetails(record);
        } else {
            grid.getSelectionModel().select(record);
        }
    },

    backToPriorityLists : function() {
        var me = this;
        me.getView().setActiveItem(0);
        var grid = me.getView().down('grid');
        grid.setSelection(0);
    },

    loadPriorityListDetails : function(record) {
        var me = this,
        status = "MODIFIED";

        var assignedPools = record.get('poolPriorityMap');
        var assignedStore = this.getViewModel().storeInfo.assignedRouterPoolsStore;
        assignedStore.removeAll();

        if (!record.phantom) {
            this.lookupReference('priorityDetailPanel').getForm().setValues({
                id : record.get('id'),
                priorityListName : record.get('priorityListName')
            });
            assignedStore.load({
            	params : {
                    priorityListId : record.get('id'),
                    cloudConfigId : record.get('cloudConfig').id
                },
                callback : function(records, operation, success) {
                	me.getViewModel().set('maxPriorityValue', assignedStore.getCount());
                }
            });
        } else {
            this.lookupReference('priorityDetailPanel').getForm().setValues({
                id : null,
                priorityListName : ''
            });
        }
        if(record.get('id') && record.get('cloudConfig') && record.get('cloudConfig').id) {
            me.getViewModel().storeInfo.availableRouterPoolsStore.load({
            	params : {
            		priorityListId : record.get('id'),
            		cloudConfigId : record.get('cloudConfig').id
            	}
            });        
        } else {
            me.getViewModel().storeInfo.availableRouterPoolsStore.load({
            	params : {
            		cloudConfigStatus : 'MODIFIED'
            	}
            });
        }
    },

    onDetailActivate : function() {
        this.enableDisableDetailGridPlugin(this.isModifiedSelected());
    },

    enableDisableDetailGridPlugin : function(isModified) {
        if (this.lookupReference('freePriorityDetailsGrid')) {
            var grid = this.lookupReference('freePriorityDetailsGrid');
            var plugins = grid.getView().getPlugins();
            plugins.forEach(function(plugin) {
                if (isModified === true) {
                    plugin.enable();
                } else {
                    plugin.disable();
                }
            });
        }

        if (this.lookupReference('relatedPriorityDetailsGrid')) {
            var grid = this.lookupReference('relatedPriorityDetailsGrid');
            var plugins = grid.getPlugins();
            plugins.forEach(function(plugin) {
                if (isModified === true) {
                    plugin.enable();
                } else {
                    if (plugin.cancelEdit && typeof plugin.cancelEdit === "function") {
                        plugin.cancelEdit();
                    }
                    plugin.disable();
                }
            });

            plugins = grid.getView().getPlugins();
            plugins.forEach(function(plugin) {
                if (isModified === true) {
                    plugin.enable();
                } else {
                    plugin.disable();
                }
            });
        }
    },

    refreshGrid : function() {
        this.onReloadInfo();
    },

    savePoolsToPriorityList : function() {
        var orderZeroFlag = false,
            flag = false,
        status = "ACTIVE";

        if (this.isModifiedSelected()) {
            status = "MODIFIED";
        }
        var record = this.lookupReference('priorityDetailPanel').getForm().getValues();
        if (!Ext.isEmpty(record.priorityListName.trim())) {
            if (Ext.isEmpty(record.id)) {
                delete record.id;
            } else {
                var summaryStore = this.getViewModel().getStore('poolListSummary');
                summaryStore.each(function(rec) {
                    if(rec.get('id') != record.id && rec.get('priorityListName').toUpperCase() === record.priorityListName.trim().toUpperCase()) {
                        flag = true;
                    }
                });
                
                if(flag) {
                    Ext.Msg.alert(l10n('error'), l10n('choose-another-priority-list-name'));
                    return;
                }
            }
            
            var summaryStore = this.getViewModel().getStore('poolListSummary');
            var flag = false;
            summaryStore.each(function(rec) {
                if(rec.get('id') != record.id && rec.get('priorityListName').toUpperCase() === record.priorityListName.trim().toUpperCase()) {
                    flag = true;                
                }
            });
            
            if(flag) {
            	Ext.Msg.alert(l10n('error'), l10n('choose-another-priority-list-name'));
            	return;
            }

            record['cloudConfig'] = this.getViewModel().get('cloudConfig');
            if(record.priorityListName) {
            	record.priorityListName = record.priorityListName.trim();
            }

            var selectedPoolsStore = this.lookupReference('relatedPriorityDetailsGrid').getStore();
            var poolPriorityMap = selectedPoolsStore.getRange(0, selectedPoolsStore.getCount());
            var selectedPoolPriorityMap = [];

            for (var counter = 0; counter < poolPriorityMap.length; counter++) {
                var tempPoolPriorityMap = poolPriorityMap[counter];
                selectedPoolPriorityMap[counter] = {};
                if (!tempPoolPriorityMap.get('order')) {
                    orderZeroFlag = true;
                    Ext.Msg.alert(l10n('error'), l10n('enter-valid-priority'), function() {
                        return;
                    });
                } else {
                    selectedPoolPriorityMap[counter]['order'] = parseInt(tempPoolPriorityMap.get('order'));
                    selectedPoolPriorityMap[counter]['pool'] = poolPriorityMap[counter].data;

                    record['poolPriorityMap'] = selectedPoolPriorityMap;
                }
            }

            if (Ext.isEmpty(record['poolPriorityMap'])) {
                Ext.Msg.alert(l10n('error'), l10n('at-least-one-pool-used-by-priority-list'));
            } else {
                if (!orderZeroFlag) {
                    var freePoolsStore = this.lookupReference('freePriorityDetailsGrid').getStore();
                    var deletedPools = [];
                    freePoolsStore.each(function(freePool) {
                        var id = freePool.get('id');
                        deletedPools.push(id);
                    });

                    var data = [Ext.JSON.encode(record), deletedPools.join(","), status];

                    var me = this;
                    Ext.Ajax.request({
                        url : 'savepoolprioritylist.ajax',
                        jsonData : data,
                        method : 'POST',
                        success : function(res) {
                            Ext.Msg.alert(l10n('success'), l10n('priority-list-saved-successfully'), function() {
                                me.refreshGrid();
                                me.backToPriorityLists();
                            });
                        },
                        failure : function() {
                            Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
                            });
                        }
                    });
                }
            }
        } else {
            Ext.Msg.alert(l10n('error'), l10n('specify-priority-list-name'));
        }
    },

    onPriorityEdit : function(editor, e) {
    	var assignedStore = this.lookupReference('relatedPriorityDetailsGrid').getStore();
    	this.getViewModel().set('maxPriorityValue', assignedStore.getCount());
        if (e.newValues && e.newValues.order != e.originalValues.order) {
            var relatedStore = this.lookupReference('relatedPriorityDetailsGrid').getStore();
            relatedStore.getSorters().clear();
            relatedStore.getSorters().beginUpdate();
            relatedStore.getSorters().endUpdate();
            relatedStore.insert(e.newValues.order < e.originalValues.order ? e.newValues.order - 1 : e.newValues.order, e.record);

            for (var counter = 0; counter < relatedStore.getCount(); counter++) {
                relatedStore.getAt(counter).set('order', counter + 1);
                relatedStore.getAt(counter).commit();
            }
        }
    },

    onPoolDrop : function( node, data, overModel, dropPosition, eOpts) {
    	var assignedStore = this.lookupReference('relatedPriorityDetailsGrid').getStore();        
        var dropIdx = 0;
        var dropOrder = 1;
        for (var counter = 0; counter < assignedStore.getCount(); counter++) {
        	if (assignedStore.getAt(counter).get('id') == (overModel && overModel.get('id'))) {
        		dropIdx = counter;
        		dropOrder = assignedStore.getAt(counter).get('order');
        		break;
        	}
        }
        var records = '';
        if(dropPosition == 'before') {
        	records = assignedStore.getRange(dropIdx);
        } else {
        	records = assignedStore.getRange(dropIdx + 1);
        }
        data.records[0].set('order', dropOrder);
        for(var i = 0; i < records.length; i++) {
        	records[i].set('order', records[i].get('order') + 1);
        }
        this.getViewModel().set('maxPriorityValue', assignedStore.getCount());   	
    },
    
    onPoolRemove : function(node, data, overModel, dropPosition, eOpts) {
    	var assignedStore = this.lookupReference('relatedPriorityDetailsGrid').getStore();
    	var i = 1;
    	assignedStore.each(function (record) {
    		record.set('order', i);
            record.commit();
            i++;
        });
    },

    activatePool : function(record) {
        this.getView().lookupController(true).activatePool(record);
    },

    onClickDiscardPools : function() {
        var me = this,
        viewModel = me.getViewModel(),
        poolListSummary = viewModel.getStore('poolListSummary');
        var id = 0;
        me.getView().lookupController(true).discardPool(id);
    }

});

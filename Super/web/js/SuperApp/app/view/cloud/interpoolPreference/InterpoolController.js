Ext.define('SuperApp.view.cloud.interpoolPreference.InterpoolController', {
    extend : 'Ext.app.ViewController',

    alias : 'controller.interpool',

    control : {
        '#' : {
            activate : 'onInterPoolRender',
            reloadInfo : 'onReloadInfo'
        },
        'pools grid[reference="poolsListGrid"]' : {
            rowclick : 'poolsListRowClick'
        },
        'pools' : {
            goBackToPools : 'goBackToPools',
            savePoolCoordinates : 'savePoolCoordinates'
        }
    },

    savePoolCoordinates : function(newX, newY) {
        var me = this,
            view = me.lookupReference('singlepooldetails'),
            record = this.lookupReference('poolsListGrid').getSelection()[0].data;
        if (isNaN(record.id)) {
            Ext.Msg.alert(l10n('error'), l10n('pool-not-save-yet'));
        } else {
	        delete record.px;
	        delete record.py;
	        record.x = newX;
	        record.y = newY;
	        record.cloudConfig = this.lookupReference('poolsListGrid').getSelection()[0].get('cloudConfig');
	        record.name = Ext.String.htmlDecode(record.name);
	        var data = [Ext.JSON.encode(record), ""];
	        Ext.Ajax.request({
	            url : 'savepool.ajax',
	            jsonData : data,
	            method : 'POST',
	            success : function(res) {
	                me.refreshPoolsGrid();
	                view.deleteIds = [];
	            },
	            failure : function() {
	                Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
	                    me.refreshPoolsGrid();
	                });
	            }
	        });
	    }
    },

    onReloadInfo : function(isModified) {
        var me = this;
        var grid = me.lookupReference('poolsListGrid');
        var plugins = grid.getPlugins(),
            status = "ACTIVE";
        var rowEditing = null;
        plugins.forEach(function(plugin) {
            if (plugin.clicksToMoveEditor) {
                rowEditing = plugin;
            }
        });
        if (isModified) {
            rowEditing.enable();
            status = "MODIFIED";
        } else {
            rowEditing.cancelEdit();
            rowEditing.disable();
        }
        if (hasD3GUI()) {
            clearD3GUI();
        }
        showD3RouterPool();

        this.enableDisableDetailGridPlugin(isModified);
    },

    poolsListRowClick : function(grid, record, tr, rowIndex, e, eOpts) {
        var showDetailsFlag = false;
        var storeInfo = this.getViewModel().storeInfo;
        var me = this,
        status = "ACTIVE";
        if (this.isModifiedSelected()) {
                status = "MODIFIED";
        }
        if ( typeof record === "string") {
            storeInfo.poolsList.each(function(pool) {

                // This condition comes when pool in GUI is clicked
                if (pool.get('name') === record) {
                    record = pool;
                    me.lookupReference('poolsListGrid').getSelectionModel().select(record);
                    me.getViewModel().set('selectedPool', pool);
                }
            });
            showDetailsFlag = true;
        } else if (e && ((Ext.browser.is.IE && e.target.getAttribute('data-disclosure') == 'true') || (!Ext.browser.is.IE && e.target.dataset.disclosure == "true"))) {
            showDetailsFlag = true;
        }

        // If typeof record is string. It is Free Router node.
        if ( typeof record !== "string" && !isNaN(record.get('id'))) {
            if (this.lookupReference('poolsCardView') && showDetailsFlag) {
                this.lookupReference('poolsCardView').setTitle(l10n('pool-details') + " - " + record.get('name'));

                var allConnections = storeInfo.connectionsList;
                Ext.Ajax.request({
                    url : 'getconnectionlistbypool.ajax',
                    method : 'GET',
                    params : {
                        poolId : record.get('id'),
                        status : status
                    },
                    success : function(req) {
                        var thisPoolConnections = Ext.decode(req.responseText);
                        storeInfo.poolConnections.removeAll();
                        storeInfo.poolConnections.loadData(thisPoolConnections);

                        var poolsList = me.getViewModel().storeInfo.poolsList;
                        var availablePools = poolsList.getRange();

                        var selectedPool = me.lookupReference('poolsListGrid').getSelection()[0] || this.getViewModel().get('selectedPool');
                        availablePools.splice(availablePools.indexOf(selectedPool), 1);

                        for (var poolCounter = 0; poolCounter < availablePools.length; poolCounter++) {
                            for (var connectionCounter = 0; connectionCounter < thisPoolConnections.length; connectionCounter++) {
                                if (availablePools[poolCounter].get('id') === thisPoolConnections[connectionCounter].pool2) {
                                    availablePools.splice(poolCounter, 1);
                                    poolCounter--;
                                    break;
                                }
                            }
                        }
                        storeInfo.availablePoolsStore.removeAll();
                        storeInfo.availablePoolsStore.loadData(availablePools);
                    },
                    failure : function(req) {
                        Ext.Msg.alert(l10n('error'), l10n('failure'));
                    }
                });

                if (record.get("routerPoolMap").length) {
                    storeInfo.associatedRoutersStore.removeAll();
                    Ext.Array.each(record.get('routerPoolMap'), function(rec) {
                        storeInfo.associatedRoutersStore.add(rec.vidyoRouter);
                    });
                } else {
                    storeInfo.associatedRoutersStore.loadData([]);
                }

                if (!Ext.isEmpty(tr)) {
                    highlight(record, false);
                }

                this.lookupReference('poolsCardView').setActiveItem(1);
                this.getViewModel().set('backHidden', false);
            } else {
                me.lookupReference('poolsListGrid').getSelectionModel().select(record);
            }
        }
    },

    onInterPoolRender : function(callback) {
        var me = this,
            isModified = me.isModifiedSelected();
        var poolsListStore = this.getViewModel().storeInfo.poolsList;
        var availablePools = poolsListStore.getRange();
        this.getViewModel().storeInfo.availablePoolsStore.removeAll();
        this.getViewModel().storeInfo.availablePoolsStore.loadData(availablePools);
        this.onReloadInfo(isModified);
    },

    getInterPoolData : function(status, callback) {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.getStore('availableRoutersStore').load({
            params : {
                'status' : status
            },
            callback : function(result) {
                var routers = result;
                viewModel.getStore('poolsList').load({
                    params : {
                        'status' : status
                    },
                    callback : function(res) {
                        if (res.length) {
                            var pools = res,
                                rec = res[0];
                            nodes = [];
                            nodes = pools;

                            res.forEach(function(record) {
                                if (document.getElementById('disclosure-image-' + record.get("id"))) {
                                    Ext.create('Ext.tip.ToolTip', {
                                        target : 'disclosure-image-' + record.get("id"),
                                        html : l10n('details-of-pool') + record.get('name')
                                    });
                                }
                            });

                            viewModel.getStore('connectionsList').load({
                                params : {
                                    'status' : status
                                },
                                callback : function(response) {
                                    var connections = response;
                                    connections.forEach(function(connection) {
                                        pools.forEach(function(pool) {
                                            if (connection.get('pool1') === pool.get('id')) {
                                                connection.set('source', pool);
                                            }

                                            if (connection.get('pool2') === pool.get('id')) {
                                                connection.set('target', pool);
                                            }
                                        });
                                    });

                                    nodes.push(Ext.create('Ext.data.Record', {
                                        id : 0,
                                        name : l10n('free-routers'),
                                        x : 20,
                                        y : 20,
                                        free : true,
                                        cloudConfig : me.getViewModel().get('cloudConfig'),
                                        routerPoolMap : routers
                                    }));
                                    links = connections;
                                    reloadSVG();
                                    if (callback && typeof callback == "function") {
                                        callback();
                                    }
                                }
                            });
                        } else {
                            nodes.push(Ext.create('Ext.data.Record', {
                                id : 0,
                                name : l10n('free-routers'),
                                x : 20,
                                y : 20,
                                free : true,
                                cloudConfig : me.getViewModel().get('cloudConfig'),
                                routerPoolMap : routers
                            }));
                            reloadSVG();
                        }
                    }
                });
                if (status == "MODIFIED") {
                    viewModel.set('isPoolsActivate', true);
                } else {
                    viewModel.set('isPoolsActivate', false);
                }
                me.goBackToPools();
            }
        });

    },

    goBackToPools : function(doUnhighlight) {
        if (this.lookupReference('poolsCardView')) {
            this.getViewModel().set('backHidden', true);
            this.lookupReference('poolsCardView').setTitle(l10n('pools-list'));
            this.lookupReference('poolsCardView').setActiveItem(0);
            if (doUnhighlight) {
                unhighlight();
            }
        }
    },

    refreshPoolsGrid : function(callback) {
        clearD3GUI();
        this.onReloadInfo(this.isModifiedSelected());
    },

    addPool : function() {
        if (this.isModifiedSelected()) {
            var grid = this.lookupReference('poolsListGrid');
            var rec = grid.getStore().getRange()[0];
            var plugins = grid.getPlugins();
            var rowEditing = null;
            plugins.forEach(function(plugin) {
                if (plugin.clicksToMoveEditor) {
                    rowEditing = plugin;
                }
            });

            rowEditing.cancelEdit();

            // Create a model instance
            var r = Ext.create('SuperApp.model.cloud.PoolsList', {
                name : '',
                x : null,
                y : null,
                cloudConfig : this.getViewModel().get('cloudConfig'),
                routerPoolMap : []
            });

            this.getViewModel().storeInfo.poolsList.insert(0, r);
            addNewPool(r);
            highlight(r, false);
            rowEditing.startEdit(0, 0);
        } else {
            Ext.Msg.alert(l10n('error'), l10n('select-modified-version'));
        }
    },

    editPool : function() {
        if (this.isModifiedSelected()) {
            var grid = this.lookupReference('poolsListGrid');

            var selectedModel = grid.getSelection()[0];

            if (!selectedModel) {
                Ext.Msg.alert(l10n('error'), l10n('select-pool-to-modify'));
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

    deletePool : function() {
        var me = this,
        status = "ACTIVE";
        
        if (this.isModifiedSelected()) {
        	status = "MODIFIED";
            var grid = this.lookupReference('poolsListGrid');

            var selectedModel = grid.getSelection()[0];

            if (!selectedModel) {
                Ext.Msg.show({
                    title : l10n('error'),
                    message : l10n('select-pool-to-delete'),
                    buttons : Ext.Msg.OK
                });
            } else {
                Ext.Msg.show({
                    title : l10n('confirmation'),
                    message : l10n('pool-msgbox-delete-confirm-messg') + ' : ' + selectedModel.get('name'),
                    buttons : Ext.Msg.YESNO,
                    icon : Ext.Msg.QUESTION,
                    fn : function(btn) {
                        if (btn === 'yes') {
                            var id = new Array();
                            Ext.Array.each(selectedModel, function(obj, index) {
                                id.push(obj.get("id") * 1);
                            });
                            
                            var data = [id.join(","), status];
                            Ext.Ajax.request({
                                method : 'POST',
                                jsonData : data,
                                url : 'deletepool.ajax',
                                success : function(response) {
                                    if (response.responseText.search("0") != -1) {
                                        Ext.Msg.alert(l10n('error'), l10n('router-pools-associated-with-rules-cannot-deleted'));
                                    } else  if (response.responseText.search("2") != -1) {
                                        Ext.Msg.alert(l10n('error'), l10n('delete-router-pool-ipc-error'), function() {
                                            me.refreshPoolsGrid();
                                         });
                                    } else {
                                    	Ext.Msg.alert(l10n('success'), l10n('pool-deleted-successfully'), function() {
                                            me.refreshPoolsGrid();
                                        });
                                    }
                                },
                                failure : function() {
                                    Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
                                        me.refreshPoolsGrid();
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

    isModifiedSelected : function() {
        return this.getView().lookupViewModel(true).get('isModified');
    },

    enableDisableDetailGridPlugin : function(isModified) {
        var me = this,
            status = "ACTIVE";

        if (isModified) {
            status = "MODIFIED";
        }
        var grid = this.lookupReference('associatedRoutersGrid');
        var plugins = grid.getView().getPlugins();
        plugins.forEach(function(plugin) {
            if (isModified === true) {
                plugin.enable();
            } else {
                plugin.disable();
            }
        });

        var grid1 = this.lookupReference('freeRoutersGrid');
        var plugins1 = grid1.getView().getPlugins();
        plugins1.forEach(function(plugin) {
            if (isModified === true) {
                plugin.enable();
            } else {
                plugin.disable();
            }
        });

        var grid2 = this.lookupReference('connectionsGrid');
        var plugins2 = grid2.getPlugins();
        var rowEditing = null;
        plugins2.forEach(function(plugin) {
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
        me.getInterPoolData(status, undefined);
    },

    onPoolsListEdit : function(editor, e) {
        var me = this,
            view = me.lookupReference('singlepooldetails'),
            record = null;

        // This is to judge if save is getting called from grid or detail screen.
        // From detail screen, editor will be null.
        if (editor) {
            record = e.record.getData();
        } else {
            record = this.lookupReference('poolsListGrid').getSelection()[0].data;
        }

        if (isNaN(record.id)) {
            delete record.id;
        }

        //Have to delete this. Else will return error from save service.
        delete record.px;
        delete record.py;

        record.x = Math.round(record.x);
        record.y = Math.round(record.y);
        if(record.name) {
        	record.name = record.name.trim();	
        }        
        var data = [Ext.JSON.encode(record), view.deleteIds.join(",")];
        Ext.Ajax.request({
            url : 'savepool.ajax', //?pool='+recordString,
            jsonData : data,
            method : 'POST',
            success : function(res) {
                Ext.Msg.alert(l10n('success'), l10n('pool-saved-successfully'), function() {
                    me.refreshPoolsGrid();
                    me.lookupReference('poolsCardView').setActiveItem(0);
                });
                view.deleteIds = [];
            },
            failure : function() {
                Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
                    me.refreshPoolsGrid();
                });
            }
        });
    },

    onPoolsListCancelEdit : function(editor, e) {
        if (isNaN(e.record.getId())) {
            e.store.remove(e.record);
            removePool(e);
        }
    },

    onRecordDropAssociatedGrid : function(node, data, dropRec, dropPosition) {
        var gridStore = this.lookupReference('associatedRoutersGrid').getStore();
        var selectedPool = this.lookupReference('poolsListGrid').getSelection()[0];
        for (var counter in data.records) {
            var router = data.records[counter].getData();
            selectedPool.get('routerPoolMap').push({
                id : null,
                vidyoRouter : router
            });
        }
        var count = gridStore.getCount();
        changeRouterCount(count);
    },

    onRecordDropFreeGrid : function(node, data, dropRec, dropPosition) {
        var me = this,
            view = me.lookupReference('singlepooldetails'),
            gridStore = this.lookupReference('associatedRoutersGrid').getStore(),
            selectedPool = this.lookupReference('poolsListGrid').getSelection()[0];

        for (var counter in data.records) {
            var router = data.records[counter].getData();
            var index = -1;
            Ext.Array.each(selectedPool.get('routerPoolMap'), function(rec, i) {
                if (rec.vidyoRouter.id === router.id) {
                    index = i;
                }
            });
            if (index >= 0) {
                view.deleteIds.push(selectedPool.get('routerPoolMap')[index].id);
                selectedPool.get('routerPoolMap').splice(index, 1);
            }
        }
    },

    onConnectionListBeforeEdit : function(editor, e) {
        var grid = this.lookupReference('connectionsGrid');
        var plugins = grid.getPlugins();
        var rowEditing = null;
        plugins.forEach(function(plugin) {
            if (plugin.clicksToMoveEditor) {
                rowEditing = plugin;
            }
        });

        this.getViewModel().storeInfo.availablePoolsStore.add(this.getViewModel().storeInfo.poolsList.getById(e.record.get('pool2')));

        // Custom code to fix a bug in ExtJS 5.1.0 where "bind" does not work inside "editor" in the view
        rowEditing.editor.items.items[1].bindStore(this.getViewModel().storeInfo.availablePoolsStore);
    },

    addConnection : function() {
        if (this.isModifiedSelected()) {
            var grid = this.lookupReference('connectionsGrid');
            var plugins = grid.getPlugins();
            var rowEditing = null;
            plugins.forEach(function(plugin) {
                if (plugin.clicksToMoveEditor) {
                    rowEditing = plugin;
                }
            });

            rowEditing.cancelEdit();
          this.lookupReference('addCancelFlag').setValue(true);
            // Create a model instance
            var poolInNewConnection = this.getViewModel().storeInfo.availablePoolsStore.getAt(0);
            if (poolInNewConnection) {
                var r = Ext.create('SuperApp.model.cloud.PoolConnections', {
                    pool2 : poolInNewConnection.get('id'),
                    direction : 2,
                    weight : 50
                });
                this.getViewModel().storeInfo.poolConnections.insert(0, r);

                rowEditing.startEdit(0, 0);
            } else {
                Ext.Msg.alert(l10n('error'), l10n("no-pools-for-connection"));
            }

        } else {
            Ext.Msg.alert(l10n('error'), l10n('select-modified-version'));
        }
    },

    editConnection : function() {
        if (this.isModifiedSelected()) {
            var grid = this.lookupReference('connectionsGrid');
            var plugins = grid.getPlugins();
            var rowEditing = null;
            plugins.forEach(function(plugin) {
                if (plugin.clicksToMoveEditor) {
                    rowEditing = plugin;
                }
            });

            rowEditing.cancelEdit();

            var recordIndex = grid.getStore().indexOf(grid.getSelection()[0]);
            this.getViewModel().set('pool2Edit', grid.getSelection()[0].get('pool2'));
            if (recordIndex >= 0) {
                rowEditing.startEdit(recordIndex);
            } else {
                Ext.Msg.alert(l10n('error'), l10n('select-connection-to-edit'));
            }
        } else {
            Ext.Msg.alert(l10n('error'), l10n('select-modified-version'));
        }
    },

    deleteConnection : function() {
    	var me = this,
          status = "ACTIVE";

    
        if (this.isModifiedSelected()) {
        	status = "MODIFIED";
            var grid = this.lookupReference('connectionsGrid');
            var selectedModel = grid.getSelection()[0];

            var selectedPool = this.lookupReference('poolsListGrid').getSelection()[0] || this.getViewModel().get('selectedPool');

            if (!selectedModel) {
                Ext.Msg.show({
                    title : l10n('error'),
                    message : l10n('select-connection-to-delete'),
                    buttons : Ext.Msg.OK
                });
            } else {
                Ext.Msg.show({
                    title : l10n('confirmation'),
                    message : l10n('connection-msgbox-delete-confirm-messg'),
                    buttons : Ext.Msg.YESNO,
                    icon : Ext.Msg.QUESTION,
                    fn : function(btn) {
                        if (btn === 'yes') {
                        	var data = [selectedPool.get('id'), selectedModel.get('pool2'), status];
                            Ext.Ajax.request({
                                method : 'POST',
                                jsonData : data,
                                url : 'deleteconnection.ajax',
                                success : function(req) {
                                    me.refreshPoolsGrid(function() {
                                        var poolsList = me.lookupReference('poolsListGrid');
                                        me.poolsListRowClick(poolsList, poolsList.getSelectionModel().getSelection()[0].get('name'), "dummyTR");
                                    });
                                },
                                failure : function() {
                                    Ext.Msg.Alert(l10n('error'), l10n('failure'), function() {
                                        me.refreshPoolsGrid();
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

    saveRoutersToPool : function() {
        this.onPoolsListEdit(null, null);
    },

    onConnectionListEdit : function(editor, e) {
        var me = this;
        var record = {};
        var poolsList = this.getViewModel().storeInfo.poolsList;
        var oldPool2 = this.getViewModel().get('pool2Edit');

        if (e.record.get('id') && !isNaN(e.record.get('id'))) {
            record['id'] = e.record.get('id');
        }
        this.lookupReference('addCancelFlag').setValue(false);
        record['weight'] = e.record.get('weight');
        record['direction'] = e.record.get('direction');
        record['pool1'] = this.lookupReference('poolsListGrid').getSelection()[0].get('id');
        record['pool2'] = e.record.get('pool2');
        record['cloudConfigId'] = this.lookupReference('poolsListGrid').getSelection()[0].get('cloudConfig').id;
        
        var data = [Ext.JSON.encode(record), oldPool2];
        this.getViewModel().storeInfo.availablePoolsStore.remove(this.getViewModel().storeInfo.poolsList.getById(e.record.get('pool2')));
        Ext.Ajax.request({
            url : 'addconnection.ajax', //?pooltopool='+recordString,
            jsonData : data,
            method : 'POST',
            success : function(res) {
                Ext.Msg.alert(l10n('success'), l10n('connection-saved-successfully'), function() {
                    me.refreshPoolsGrid(function() {
                        var poolsList = me.lookupReference('poolsListGrid');
                        me.poolsListRowClick(poolsList, poolsList.getSelectionModel().getSelection()[0].get('name'), "dummyTR");
                       });
                });
            },
            failure : function() {
                Ext.Msg.alert(l10n('error'), l10n('failure'), function() {
                    me.refreshPoolsGrid();
                });
            }
        });
    },

    onConnectionListCancelEdit : function(editor, e) {

    	 if(this.lookupReference('addCancelFlag').getValue()=="true"){
    		 if (isNaN(e.record.getId())) {
    			 e.store.remove(e.record);
    			 this.lookupReference('addCancelFlag').setValue(false);
    		 }
    	 }
    },

    onPoolListValidateEdit : function(editor, e) {
        if (Ext.isEmpty(e.newValues.name.trim())) {
            Ext.Msg.alert(l10n('error'), l10n('enter-valid-pool-name'));
            return false;
        } else if (e.originalValues.name == e.newValues.name) {
            editor.cancelEdit();
            return false;
        } else {
            var newValue = e.newValues.name;
            var existingIndex = this.getViewModel().getStore('poolsList').findRecord('name', newValue.trim(), 0, false, false, true);
            if (existingIndex) {
                Ext.Msg.alert(l10n('error'), l10n('choose-another-pool-name'));
                return false;
            } else {
                return true;
            }
        }
        //this.getViewModel().getStore('poolsList')
    },

    activatePool : function(record) {
        this.getView().lookupController(true).activatePool(record);
    },

    onClickDiscardPools : function() {
        var me = this,
        viewModel = me.getViewModel(),
        poolsList = viewModel.getStore('poolsList');
        var id = 0;
        me.getView().lookupController(true).discardPool(id);
    }
});

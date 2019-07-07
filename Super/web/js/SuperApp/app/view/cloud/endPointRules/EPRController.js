Ext.define('SuperApp.view.cloud.endPointRules.EPRController', {
    extend : 'Ext.app.ViewController',

    alias : 'controller.EPR',

    control : {
        '#' : {
            reloadInfo : 'onReloadInfo',
            activate : 'onRenderView'
        },
        'singleRuleDetails grid[reference="ruleSetGrid"]' : {
            rowclick : 'ruleSetGridRowClick'
        }
    },

    onRadioSelectionChange : function(radio, newValue, oldValue, eOpts) {
        if (radio.getValue()) {
            radio.up('container').getComponent(1).setDisabled(false);
        } else {
            radio.up('container').getComponent(1).setDisabled(true);
        }
    },

    localIPRangeChange : function(checkbox, newValue, oldValue, eOpts) {
        this.lookupReference('localIPInfo').setDisabled(oldValue);
    },

    externalIPRangeChange : function(checkbox, newValue, oldValue, eOpts) {
        this.lookupReference('externalIPInfo').setDisabled(oldValue);
    },

    onReloadInfo : function() {
        var me = this;
        me.view.getLayout().setActiveItem(0);
        me.view.setLoading(true);
        var isModified = this.isModifiedSelected();
        this.setEnableDisableEditingPlugin(isModified);
    },

    onRenderView : function() {
        this.getViewModel().getStore('ruleLocations').load({
            params : {
                status : "Modified",
                sort : 'locationTag'
            }
        });

        this.onReloadInfo();
    },

    setEnableDisableEditingPlugin : function(isModified) {
        var me = this,
            viewModel = me.getViewModel();

        if (isModified) {
            viewModel.set('isPoolsActivate', true);
        } else {
            viewModel.set('isPoolsActivate', false);
        }
        if (me.lookupReference('rulesGrid') && isModified) {
            var grid = me.lookupReference('rulesGrid'),
                eprSummary = this.getViewModel().getStore('eprSummary');
            eprSummary.removeAll();
            eprSummary.load({
                params : {
                    status : 'MODIFIED'
                },
                callback : function(res) {
                    if (res.length) {
                        res.forEach(function(record) {
                            if (document.getElementById('disclosure-image-rule-' + record.get("id"))) {
                                Ext.create('Ext.tip.ToolTip', {
                                    target : 'disclosure-image-rule-' + record.get("id"),
                                    html : 'Details of Endpoint Rule ' + record.get('ruleName')
                                });
                            }
                        });
                        //eprSummary.sort('ruleOrder');
                    }
                    me.getView().setLoading(false);
                }
            });
            var plugins = grid.getPlugins();
            var rowEditing = null;
            plugins.forEach(function(plugin) {
                if (plugin.clicksToMoveEditor) {
                    rowEditing = plugin;
                }
            });
            if (rowEditing) {
                if (isModified === true) {
                    rowEditing.enable();
                } else {
                    rowEditing.cancelEdit();
                    rowEditing.disable();
                }
            }
        } else {
            this.getViewModel().getStore('eprSummary').load({
                params : {
                    status : 'ACTIVE'
                },
                callback : function(recs) {
                    me.getView().setLoading(false);
                }
            });
            this.getView().setActiveItem(0);
        }
    },

    isModifiedSelected : function() {
        return this.getView().lookupViewModel(true).get('isModified');
    },

    erpRowClick : function(grid, record, tr, rowIndex, e, eOpts) {
        if (e.target.tagName === "IMG") {
            this.getView().setActiveItem(1);
            this.loadRuleDetails(record);
        }
    },

    validateEditModifiedGrid : function(editor, e) {
        if (e.value <= 0 || e.value > editor.grid.getStore().getCount() ) {
            Ext.Msg.alert(l10n("error"), l10n('invalid-rule-order'));
            return false;
        } else {
            return true;
        }
    },

    editModifiedGrid : function(editor, e) {
        var me = this;
        var params = [];
        params.push(e.record.modified.ruleOrder);
        params.push(e.value);

        Ext.Ajax.request({
            url : 'updateruleorder.ajax',
            method : 'POST',
            jsonData : params,
            success : function() {
                Ext.Msg.alert(l10n('success'), l10n("modified-successfully"), function() {
                    me.onReloadInfo();
                });
            },
            failure : function() {
                Ext.Msg.alert(l10n('error'), l10n('failed'));
            }
        });
    },

    backToRules : function() {
        this.getView().setActiveItem(0);
    },

    refreshGrid : function() {
        this.onReloadInfo();
    },

    addRule : function() {
        var me = this;
        if (this.isModifiedSelected()) {
            this.getView().setActiveItem(1);

            var ruleForm = this.lookupReference('singleRuleDetails').getForm();
            ruleForm.setValues({
                id : null,
                ruleOrder : null,
                ruleName : '',
                priorityList : ''
            });

            this.lookupReference('ruleSetGrid').getStore().removeAll();
            this.getViewModel().getStore('freePriorityListsStore').load({
                params : {
                    status : "Modified"
                }
            });
        } else {
            Ext.Msg.alert(l10n("error"), l10n('select-modified-version'));
        }
    },

    editRule : function() {
        if (this.isModifiedSelected()) {
            var grid = this.lookupReference('rulesGrid');

            var selectedModel = grid.getSelection()[0];

            if (!selectedModel) {
                Ext.Msg.alert(l10n("error"), l10n('select-rule-to-modify'));
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

    deleteRule : function() {
        if (this.isModifiedSelected()) {
            var grid = this.lookupReference('rulesGrid');

            var selectedModel = grid.getSelection();

            if (selectedModel.length == 0) {
                Ext.Msg.show({
                    title : l10n('error'),
                    message : l10n('select-rule-to-delete'),
                    buttons : Ext.Msg.OK
                });
            } else {
                Ext.Msg.show({
                    title : l10n('confirmation'),
                    message : l10n('rule-msgbox-delete-confirm-messg'),
                    buttons : Ext.Msg.YESNO,
                    icon : Ext.Msg.QUESTION,
                    fn : function(btn) {
                        if (btn === 'yes') {
                            var gridStore = grid.getStore();
                            var id = new Array();
                            Ext.Array.each(selectedModel, function(obj, index) {
                                id.push(obj.get("id") * 1);
                            });
                            Ext.Ajax.request({
                                url : 'deleterule.ajax',
                                jsonData : id,
                                success : function(response) {
                                    Ext.Msg.alert(l10n('success'), l10n('rule-deleted-successfully'), function() {
                                        grid.getStore().load({
                                            params : {
                                                status : 'MODIFIED'
                                            }
                                        });
                                    });
                                },
                                failure : function(response, opts) {
                                    console.log('server-side failure with status code ' + response.status);
                                }
                            });
                        }
                    }
                });
            }
        } else {
            Ext.Msg.alert(l10n('error'), l10n('select-rule-to-delete'));
        }
    },

    loadRuleDetails : function(record) {
        var ruleForm = this.lookupReference('singleRuleDetails').getForm();
        var me = this,
            viewModel = me.getViewModel();
        viewModel.getStore('freePriorityListsStore').load({
            params : {
                status : "Modified"
            },
            callback : function(recs) {
                viewModel.getStore('freePriorityListsStore').add(record.get('poolPriorityList'));
                ruleForm.setValues({
                    priorityList : record.get('poolPriorityList').id,
                    id : record.get('id'),
                    ruleOrder : record.get('ruleOrder'),
                    ruleName : record.get('ruleName')
                });
            }
        });

        viewModel.getStore('ruleSetStore').removeAll();
        var ruleSets = record.get('ruleSet');
        var ruleSetModels = ruleSets.map(function(ruleSet) {
            return Ext.create('Ext.data.Model', ruleSet);
        });

        viewModel.getStore('ruleSetStore').loadData(ruleSetModels);

        ruleSetModels.forEach(function(record) {
            if (document.getElementById('disclosure-image-rule-detail-' + record.get("id"))) {
                Ext.create('Ext.tip.ToolTip', {
                    target : 'disclosure-image-rule-detail-' + record.get("id"),
                    html : l10n('details-ruleset')
                });
            }
        });
    },

    addRuleset : function(btn) {
        var ruleSetGrid = this.lookupReference('ruleSetGrid');
        var scope = this;

        var ruleSetWindow = Ext.create('SuperApp.view.cloud.endPointRules.RuleInfoForm', {
            floatParent : scope.view,
            ruleSetGrid : ruleSetGrid
        }).show();

        var formValues = {
            'ruleType' : 'ipRule',
        };
        ruleSetWindow.down('form').getForm().setValues(formValues);
    },

    ruleSetGridRowClick : function(grid, record, tr, rowIndex, e, eOpts) {

        if (e && ((Ext.browser.is.IE && e.target.getAttribute('data-disclosure') == 'true') || (!Ext.browser.is.IE && e.target.dataset.disclosure == "true"))) {
    
            var scope = this;
            var ruleSetWindow = Ext.create('SuperApp.view.cloud.endPointRules.RuleInfoForm', {
                floatParent : scope.view,
                ruleSetGrid : grid
            }).show();

            var formValues = {
                'ruleType' : record.get('endpointID') ? 'endpointID' : (record.get('locationTagID') ? 'locationTag' : 'ipRule'),
                'localIPCheckbox' : record.get('privateIP') ? true : false,
                'localIP' : record.get('privateIP'),
                'localIPAdditional' : record.get('privateIpCIDR'),
                'externalIPCheckbox' : record.get('publicIP') ? true : false,
                'externalIP' : record.get('publicIP'),
                'externalIPAdditional' : record.get('publicIpCIDR'),
                'locationTagCombo' : record.get('locationTagID') || '',
                'endPointID' : record.get('endpointID'),
                'id' : record.get('id'),
                'ruleSetOrder' : record.get('ruleSetOrder')
            };
            ruleSetWindow.down('form').getForm().setValues(formValues);
        }
    },

    onRuleOrderEdit : function(editor, e) {
        if (e.newValues && e.newValues.ruleSetOrder != e.originalValues.ruleSetOrder) {
            var me = this,
                viewModel = me.getViewModel(),
                relatedStore = viewModel.getStore('ruleSetStore');
            relatedStore.getSorters().clear();
            relatedStore.getSorters().beginUpdate();
            relatedStore.getSorters().endUpdate();
            //relatedStore.insert(e.newValues.ruleSetOrder < e.originalValues.ruleSetOrder ? e.newValues.ruleSetOrder - 1 : e.newValues.ruleSetOrder, e.record);
            var isOld = false,
                oldRecIndex = 0,
                isNew = false,
                newRecIndex = 0;
            relatedStore.each(function(rec, index) {
                switch(rec.get('ruleSetOrder')) {
                case e.newValues.ruleSetOrder :
                    oldRecIndex = index;
                    isOld = true;
                    break;
                case e.originalValues.ruleSetOrder :
                    newRecIndex = index;
                    isNew = true;
                    break;
                }
            });

            if (isOld) {
                relatedStore.getAt(oldRecIndex).set('ruleSetOrder', e.originalValues.ruleSetOrder);
            }
            if (isNew) {
                relatedStore.getAt(newRecIndex).set('ruleSetOrder', e.newValues.ruleSetOrder);
            }
            relatedStore.sort('ruleSetOrder');
        }
    },

    editRuleset : function() {
        var ruleSetGrid = this.lookupReference('ruleSetGrid');
        var selectedRulesets = ruleSetGrid.getSelection();
        if (selectedRulesets.length == 1) {
            var scope = this;
            var record = selectedRulesets[0];

            var ruleSetWindow = Ext.create('SuperApp.view.cloud.endPointRules.RuleInfoForm', {
                floatParent : scope.view,
                ruleSetGrid : ruleSetGrid
            }).show();

            var formValues = {
                'ruleType' : record.get('endpointID') ? 'endpointID' : (record.get('locationTagID') ? 'locationTag' : 'ipRule'),
                'localIPCheckbox' : record.get('privateIP') ? true : false,
                'localIP' : record.get('privateIP'),
                'localIPAdditional' : record.get('privateIpCIDR'),
                'externalIPCheckbox' : record.get('publicIP') ? true : false,
                'externalIP' : record.get('publicIP'),
                'externalIPAdditional' : record.get('publicIpCIDR'),
                'locationTagCombo' : record.get('locationTagID') || '',
                'endPointID' : record.get('endpointID'),
                'id' : record.get('id'),
                'ruleSetOrder' : record.get('ruleSetOrder')
            };
            ruleSetWindow.down('form').getForm().setValues(formValues);
        } else {
            Ext.Msg.alert(l10n('error'), l10n('select-ruleset-to-modify'));
        }
    },

    deleteRuleset : function(btn) {
        var me = this;
        var ruleSetGrid = me.lookupReference('ruleSetGrid');
        var selectedRuleSets = ruleSetGrid.getSelection();
        if (selectedRuleSets.length) {
            Ext.Msg.confirm(l10n('confirmation'), l10n('ruleset-msgbox-delete-confirm-messg'), function(btn) {
                if (btn == 'yes') {
                    var deletedRuleSets = me.lookupReference('singleRuleDetails').deletedRuleSets;
                    selectedRuleSets.forEach(function(selected) {
                        if (!selected.phantom) {
                            deletedRuleSets.push(selected.get('id'));
                        }
                    });

                    ruleSetGrid.getStore().remove(selectedRuleSets);
                    var ruleSets = ruleSetGrid.getStore().getRange();
                    for (var counter = 0; counter < ruleSets.length; counter++) {
                        ruleSets[counter].set('ruleSetOrder', counter + 1);
                        ruleSets[counter].commit();
                    }
                }
            });
        } else {
            Ext.Msg.alert(l10n('error'), l10n('select-ruleset-to-delete'));
        }
    },

    saveRuleset : function(btn) {
        var ruleSetGrid = this.lookupReference('ruleSetGrid');
        var formValues = btn.up('form').getForm().getValues();

        if (formValues["ruleType"]) {
            if ((formValues["ruleType"] == "ipRule" && !formValues['localIP'] && !formValues['externalIP']) || (formValues["ruleType"] == "locationTag" && !formValues['locationTagCombo']) || (formValues["ruleType"] == "endpointID" && !formValues['endPointID'])) {
                Ext.Msg.alert(l10n('error'), l10n('set-ruleset-value'));
            } else {
                if (formValues["id"]) {
                    var modifiedRecord = ruleSetGrid.getStore().getById(formValues["id"]);
                    modifiedRecord.set('privateIP', formValues['localIP']);
                    modifiedRecord.set('privateIpCIDR', formValues['localIPAdditional']);
                    modifiedRecord.set('publicIP', formValues['externalIP']);
                    modifiedRecord.set('publicIpCIDR', formValues['externalIPAdditional']);
                    modifiedRecord.set('locationTagID', formValues['locationTagCombo']);
                    modifiedRecord.set('endpointID', formValues['endPointID']);
                    modifiedRecord.commit();

                    btn.up('window').close();
                } else {
                    var recordData = {};
                    recordData['privateIP'] = formValues['localIP'];
                    recordData['privateIpCIDR'] = formValues['localIPAdditional'];
                    recordData['publicIP'] = formValues['externalIP'];
                    recordData['publicIpCIDR'] = formValues['externalIPAdditional'];
                    recordData['locationTagID'] = formValues['locationTagCombo'];
                    recordData['endpointID'] = formValues['endPointID'];
                    recordData['ruleSetOrder'] = ruleSetGrid.getStore().getCount() + 1;

                    var record = Ext.create('Ext.data.Model', recordData);
                    ruleSetGrid.getStore().add(record);

                    btn.up('window').close();
                    if (document.getElementById('disclosure-image-rule-detail-' + record.get("id"))) {
                        Ext.create('Ext.tip.ToolTip', {
                            target : 'disclosure-image-rule-detail-' + record.get("id"),
                            html : l10n('details-ruleset')
                        });
                    }
                }
            }
        } else {
            Ext.Msg.alert(l10n('error'), l10n('select-ruleset-to-save'));
        }
    },

    saveRuleDetails : function(btn) {
        var ruleDetailFormValues = btn.up('form').getForm().getValues();
        var ruleSetsRecords = this.lookupReference('ruleSetGrid').getStore().getRange();
        var selectedPriorityListRecord = this.getViewModel().getStore('freePriorityListsStore').findRecord('id', ruleDetailFormValues['priorityList']);
        if (Ext.isEmpty(ruleDetailFormValues['ruleName'])||Ext.isEmpty(ruleDetailFormValues['ruleName'].trim())){
            Ext.Msg.alert(l10n('error'), l10n('endpointrule-name-cannot-be-blank'));
        } else if (ruleSetsRecords.length == 0) {
            Ext.Msg.alert(l10n('error'), l10n('rules-for-endpoint-rule'));
        } else if (!ruleDetailFormValues['priorityList']) {
            Ext.Msg.alert(l10n('error'), l10n('pool-priority-list-required'));
        } else if (Ext.isEmpty(selectedPriorityListRecord)) {
            Ext.Msg.alert(l10n('error'), l10n('pool-priority-list-required'));
        } else {

        	var record = this.lookupReference('rulesGrid').getStore().findRecord('ruleName', ruleDetailFormValues['ruleName'].trim(), 0, false, false, true);
        	if(record && record.get('id') && record.get('id') != ruleDetailFormValues['id']) {
                Ext.Msg.alert(l10n('error'), l10n('choose-another-rule-name'));
                return false;
        	}

            var ruleSets = ruleSetsRecords.map(function(obj) {
                var returnValue = obj.data;
                if (obj.phantom) {
                    delete returnValue.id;
                }
                return returnValue;
            });

            var ruleSaveParams = {
                'ruleName' : ruleDetailFormValues['ruleName'].trim(),
                'poolPriorityList' : selectedPriorityListRecord.data,
                'ruleSet' : ruleSets,
                'cloudConfig' : this.getViewModel().get('cloudConfig'),
                'ruleOrder' : ruleDetailFormValues['ruleOrder'] ? ruleDetailFormValues['ruleOrder'] : this.lookupReference('rulesGrid').getStore().getCount() + 1
            };

            if (ruleDetailFormValues['id']) {
                ruleSaveParams['id'] = ruleDetailFormValues['id'];
            }

            var deletedRuleSets = this.lookupReference('singleRuleDetails').deletedRuleSets;

            var data = [Ext.JSON.encode(ruleSaveParams), deletedRuleSets.join(",")];
            var me = this;

            Ext.Ajax.request({
                url : 'addrule.ajax',
                method : 'POST',
                jsonData : data,
                success : function(req) {
                    Ext.Msg.alert(l10n('success'), l10n('rule-saved-successfully'), function() {
                        me.onReloadInfo();
                        me.getView().setActiveItem(0);
                    });
                },
                failure : function(req) {
                    Ext.Msg.alert(l10n('error'), l10n('failure'));
                }
            });
        }
    },

    localIPTabPressed : function(field, e) {
        if (e.getKey() == e.TAB) {
            e.preventDefault();
            field.up("form").getForm().findField('localIPAdditional').focus();
        }
    },

    externalIPTabPressed : function(field, e) {
        if (e.getKey() == e.TAB) {
            e.preventDefault();
            field.up("form").getForm().findField('externalIPAdditional').focus();
        }
    },

    activatePool : function(record) {
        this.getView().lookupController(true).activatePool(record);
    },

    onClickDiscardPools : function() {
        var me = this,
        viewModel = me.getViewModel(),
        eprSummary = viewModel.getStore('eprSummary');
        var id = 0;
        me.getView().lookupController(true).discardPool(id);
    }
});

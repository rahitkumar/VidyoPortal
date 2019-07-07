Ext.define('SuperApp.view.settings.hotstand.HotstandController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.HotstandController',

    getHotStandStatusData: function () {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.getStore('statusStore').load();
        
        me.loadHighAvailableStore();
    },

    getHotStandDBSyncData: function () {
        var me = this,
            viewModel = me.getViewModel();

        viewModel.getStore('timeStore').load();
        me.loadHighAvailableStore();
    },

    loadHighAvailableStore: function () {
        var me = this,
            viewModel = me.getViewModel(),
            frequencyGroup = me.lookupReference('frequencyGroup'),
            highAvailableStore = viewModel.getStore('highAvailableStore');

        highAvailableStore.load({
            callback: function (recs) {
                var rec = recs[0];

                viewModel.set('showRemoveMaintLink', rec.get('showRemoveMaintLink'));
                viewModel.set('showToMaintLink', rec.get('showToMaintLink'));
                if (me.view.xtype == "databasesync") {
                    viewModel.set('startTime', rec.get('startTime'));
                    viewModel.set('endTime', rec.get('endTime'));
                    viewModel.set('frequency', rec.get('frequency'));
                    frequencyGroup.setValue({ frequencyGroup: rec.get('frequency') });
                }

            }
        });
    },

    getOperationData: function () {
        var me = this;

        me.loadHighAvailableStore();
    },

    onChangeEndTime: function (combo, value) {
        var me = this,
            viewModel = me.getViewModel(),
            timeStore = viewModel.getStore('timeStore');

        timeStore.clearFilter();
        timeStore.filterBy(function (rec) {
            if (parseInt(rec.get('dataFieldName')) <= parseInt(value)) {
                return rec;
            }
        });
    },

    onChangeStartTime: function (combo, value) {
        var me = this,
            viewModel = me.getViewModel(),
            time2Store = viewModel.getStore('time2Store');

        time2Store.clearFilter();
        time2Store.filterBy(function (rec) {
            if (parseInt(rec.get('dataFieldName')) >= parseInt(value)) {
                return rec;
            }
        });
    },

    callDBSync: function (btn) {
        var me = this,
            dbsyncForm = me.view;

        var boxWait;


        boxWait = Ext.MessageBox.wait('DB snapshot in progress', l10n('please-wait'));

        Ext.Ajax.request({
            url: 'securedmaint/dbsnapshot.ajax',
            method: 'POST',
            timeout: 180000,
            waitMsg: 'Please wait...',

            success: function (response) {
                if (boxWait != undefined)
                    boxWait.hide();
                var result = response.responseXML;
                var success = Ext.DomQuery.selectValue('message @success', result);

                if (success == "true") {

                    Ext.Msg.alert(l10n('success'), 'DB snapshot completed successfully');

                }
                if (success == "false") {
                    var responseId = Ext.DomQuery.selectValue('id', xmlResponse);
                    var responseMsg = Ext.DomQuery.selectValue('msg', xmlResponse);
                    Ext.Msg.alert(responseId, responseMsg);
                }
            },
            failure: function (form, action) {
            }

        });
    },

    initiateDBSnapShot: function (view, cell, cellIndex, record, row, rowIndex, e) {
        var linkClicked = (e.target.tagName == 'A');
        if (linkClicked) {
            var me = this;
            Ext.Msg.confirm(l10n('confirmation'), 'Are you sure you want to initiate DB snapshot?', function (btn) {
                if (btn == 'yes') {
                    me.callDBSync(btn);
                }
            }
            );
        }
    },

    onClickSyncNowDBSync: function (btn) {
        var me = this,
            dbsyncForm = me.view;

        if (dbsyncForm && dbsyncForm.isValid()) {
            Ext.MessageBox.confirm(l10n('confirmation'), l10n('sync-database-now'), function (res) {
                if (res == "yes") {
                    me.callDBSync(btn);
                }
            });
        }
    },

    onClickSwitchToMaintenance: function () {
        var me = this;

        Ext.MessageBox.confirm(l10n('confirmation'), l10n('confirm-switch-to-maintenance'), function (res) {
            if (res == 'yes') {
                Ext.Ajax.request({
                    url: 'securedmaint/switchtomaintmode.ajax',
                    method: 'POST',
                    success: function (response) {
                        var result = response.responseXML;
                        if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                            Ext.Msg.alert(l10n('label-maint-mode'), l10n('messg-switched-to-maint-logout'), function () {
                                logoutSuper();
                            });
                        } else {
                            Ext.Msg.alert(l10n('error'), l10n('label-failed-to-remove-from-maintenance-mode'));
                        }
                    }
                });
            }
        });
    },

    onClickRemoveToMaintenace: function () {
        var me = this;

        Ext.MessageBox.confirm(l10n('confirmation'), l10n('confirm-remove-from-maintenance'), function (res) {
            if (res == 'yes') {
                Ext.Ajax.request({
                    url: 'securedmaint/switchtomaintmode.ajax',
                    params: {
                        maint: 'remove'
                    },
                    method: 'POST',
                    success: function (response) {
                        var result = response.responseXML;
                        if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                            Ext.Msg.alert(l10n('label-maint-mode'), l10n('removed-from-maint-mode'), function () {
                                //logoutSuper();
                            });
                        } else {
                            Ext.Msg.alert(l10n('error'), l10n('label-failed-to-remove-from-maintenance-mode'));
                        }
                    }
                });
            }
        });
    },

    onClickForceStandby: function () {
        var me = this;

        Ext.MessageBox.confirm(l10n('confirmation'), l10n('confirm-switch-to-standby'), function (res) {
            if (res == 'yes') {
                //Ext.MessageBox.alert(l10n('label-force-standby'), l10n('label-force-standby'));

                Ext.Ajax.request({
                    url: 'securedmaint/forcestandby.ajax',
                    method: 'POST',
                    success: function (res) {
                        var result = res.responseXML;
                        if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                            me.startTaskManager();
                            Ext.Msg.alert(l10n('label-force-standby'), l10n('messg-switching-active-to-standby'));
                        } else {
                        	 var responseId = Ext.DomQuery.selectValue('id', result);
                             var responseMsg = Ext.DomQuery.selectValue('msg', result);
                             Ext.Msg.alert(responseId, responseMsg);
                        }
                    }
                });
            }
        });
    },

    onClickImport: function () {
        var me = this;
        var seckey = Ext.JSON.encode(Ext.getCmp('seckeyId').getValue());
        Ext.Ajax.request({
            url: 'securedmaint/importseckey.ajax',
            params: { 'seckey': seckey },
            scope: me,
            method: 'POST',
            success: function (res) {
                var result = res.responseXML;
                if (Ext.DomQuery.selectValue('message @success', result) == "true") {
                    Ext.Msg.alert(l10n('label-import-security-key'), l10n('messg-security-key-import-success'));
                } else {
                    Ext.Msg.alert(l10n('error'), l10n('messg-security-key-import-error'));
                }
            }
        });
    },

    startTaskManager: function () {
        var me = this,
            updateRefresh = {
                run: me.do_update,
                interval: 15000
            };
        me.serverTimeFail = false;
        Ext.TaskManager.start(updateRefresh);
    },

    stopTaskManager: function () {
        var me = this,
            updateRefresh = {
                run: me.do_update,
                interval: 15000
            };
        Ext.TaskManager.stop(updateRefresh);
    },

    do_update: function () {
        var me = this,
            updateRefresh = {
                run: me.do_update,
                interval: 15000
            };
        ;
        Ext.Ajax.request({
            disableCaching: true,
            url: 'serverstartedtime.ajax',
            success: function (response, options) {
                var xml = response.responseXML;
                if ((xml == null || xml == undefined) && me.serverTimeFail == false) {
                    me.serverTimeFail = true;
                    Ext.Msg.show({
                        title: l10n('the-system-is-going-down-for-reboot-now'),
                        msg: l10n('please-refresh-the-page-after-system-reboot'),
                        minWidth: 300,
                        modal: true,
                        icon: Ext.Msg.INFO,
                        fn: function () {
                        }
                    });
                } else if (me.serverTimeFail == true && response.readyState == 4 && response.status == 200) {
                    me.serverTimeFail = false;
                    Ext.TaskMgr.stop(updateRefresh);
                    msg(l10n('success'), l10n('restarted1'), function () {
                        logoutSuper();
                    });
                }
            },
            failure: function (response, options) {
                var xml = response.responseXML;
                if (me.serverTimeFail == false) {
                    me.serverTimeFail = true;
                    Ext.Msg.show({
                        title: '',
                        msg: l10n('update-successful-system-reboot-shortly'),
                        minWidth: 300,
                        modal: true,
                        icon: Ext.Msg.INFO,
                        fn: function () {
                        }
                    });
                }
            }
        });
    },
});


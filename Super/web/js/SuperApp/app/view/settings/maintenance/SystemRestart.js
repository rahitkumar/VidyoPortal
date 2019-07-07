Ext.define('SuperApp.view.settings.maintenance.SystemRestart', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.systemrestart',
    border : false,
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    title : {
        text : '<span class="header-title">'+l10n('system-restart')+'</span>',
        textAlign : 'center'
    },
    frame: true,
    initComponent : function() {
        var me = this;
        me.items = [{
                xtype : 'toolbar',
                border : 0,
                layout: {
                    pack: 'center',
                    type: 'hbox'
                },
                margin : 20,
                padding : 10,
                items : [{
                    text : l10n('restart-web-server'),
                    iconCls : 'x-fa fa-warning',
                    isReboot : false,
                    tooltip : l10n('restart-web-server'),
                    sysUrl : 'securedmaint/maintenance_webserver_restart.ajax',
                    listeners : {
                        click : 'onClickRestartReboot'
                    }
                }, {
                    text : l10n('reboot'),
                    reference : 'rebootsystemrestart',
                    iconCls : 'x-fa fa-warning',
                    tooltip : l10n('do-restart-of-server'),
                    isReboot : true,
                    sysUrl : 'securedmaint/maintenance_system_restart.ajax',
                    listeners : {
                        click : 'onClickRestartReboot'
                    }
                }, {
                    text : l10n('shutdown'),
                    tooltip : l10n('shutdown-the-server'),
                    iconCls : 'x-fa fa-power-off',
                    listeners : {
                        click : me.onClickShutdownSystem
                    }
                }]
            }];
        me.callParent(arguments);
    },

    /***
     *@function onClickRestartWebServer
     */
    onClickRestartWebServer : function(btn) {
        var me = this,
            str = btn.isReboot ? l10n('do-you-want-to-reboot-the-server') : l10n('do-you-want-to-restart-the-web-server');
        Ext.create('Ext.data.Store', {
            fields : ['serverStartedTimestamp', 'serverStarted'],
            storeId : 'serverstartstamp',
            remoteSort : false,
            proxy : {
                type : 'ajax',
                url : 'serverstartedtime.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row'
                }
            },
            autoLoad : true
        }).load({
            callback : function(rec) {
                var rec = rec[0];
                me.pageLoadStamp = rec.get('serverStartedTimestamp');
            }
        });

        me.refresher['run'] = me.doRefresh;
        me.refresher['scope'] = me;

        Ext.MessageBox.confirm(l10n("confirmation"), str, function(res) {
            if (res == 'yes') {
                var headerReboot = btn.isReboot ? l10n('rebooting') : l10n('restarting-web-server'),
                    infoReboot = btn.isReboot ? l10n('please-refresh-page-after-web-server-rebooted') : l10n('please-refresh-page-after-web-server-restarted');
                Ext.MessageBox.alert(headerReboot, infoReboot);
                Ext.TaskManager.start(me.refresher);
                Ext.Ajax.request({
                    url : btn.sysUrl,
                    method : 'POST',
                    success : function(res) {
                        Ext.Msg.show({
                            title : l10n('restarting'),
                            msg : l10n('web-server-is-being-restarted'),
                            minWidth : 300,
                            modal : true,
                            icon : Ext.Msg.INFO,
                            fn : function() {
                            }
                        });
                    },
                    failure : function(form, action) {
                        Ext.TaskManager.stop(me.refresher);
                        var errors = '';
                        if ((action.result != null) && (!action.result.success)) {
                            for (var i = 0; i < action.result.errors.length; i++) {
                                errors += action.result.errors[i].msg + '<br>';
                            }
                        }
                        if (errors != '') {
                            Ext.Msg.alert(l10n('error'), '<br>' + errors, function() {
                            });
                        } else {
                            Ext.Msg.alert(l10n('error'), l10n('failed-to-restart-web-server'), function() {
                            });
                        }
                    }
                });
            }
        });
    },
    
    doRefresh : function() {
        var me = this,
            store = Ext.getStore('serverstartstamp'),
            pageLoadStamp = parseInt(me.pageLoadStamp);

        if (store) {
            store.reload();
            var xml = store.getRange()[0];
            var serverStartedTimestampText = xml.get('serverStartedTimestamp');
            if (serverStartedTimestampText != undefined) {
                var serverStartedTimestamp = parseInt(serverStartedTimestampText.trim());
                if (pageLoadStamp < serverStartedTimestamp) {
                    Ext.TaskManager.stop(me.refresher);
                    Ext.Message.alert(l10n("success"), l10n("restarted1"), function() {
                        logoutSuper();
                    });
                }
            }
        }
    },

    onClickShutdownSystem : function() {
        var me = this;

        Ext.MessageBox.confirm(l10n("confirmation"), l10n('shutdown-server-will-terminate-all-running-processes-shutdown'), function(res) {
            if (res == 'yes') {
                Ext.Ajax.request({
                    url : 'securedmaint/maintenance_system_shutdown.ajax',
                    method : 'POST',
                    success : function(res) {
                        Ext.Msg.alert(l10n("success"), l10n('your-server-is-turned-off'));
                    },
                    failure : function(res) {
                        var errors = '';
                        if ((action.result != null) && (!action.result.success)) {
                            for (var i = 0; i < action.result.errors.length; i++) {
                                errors += action.result.errors[i].msg + '<br>';
                            }
                        }
                        if (errors != '') {
                            Ext.Msg.alert(l10n('error'), '<br>' + errors, function() {
                            });
                        } else {
                            Ext.Msg.alert(l10n('error'), l10n('timeout'), function() {
                            });
                        }
                    }
                });
            }
        });
    }
});

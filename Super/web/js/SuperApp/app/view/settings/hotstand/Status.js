//Status
Ext.define('SuperApp.view.settings.hotstand.Status', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.hotstandstatus',

    requires: ['SuperApp.view.settings.hotstand.HotstandController', 'SuperApp.view.settings.hotstand.HotstandViewModel'],

    viewModel: {
        type: 'HotstandViewModel'
    },
    width: '100%',
    controller: 'HotstandController',
    title: {
        text: '<span class="header-title">' + l10n('label-status') + '</span>',
        textAlign: 'center'
    },


    initComponent: function() {
        var me = this;

        me.items = [{
            xtype: 'grid',
            height: '100%',
            bind: {
                store: '{statusStore}'
            },
            hideHeaders: true,
            columns: [{  align: 'left',
                flex: 1,
                renderer: me.columnChange,
                dataIndex: 'key'
            }, {
         
                flex: 1,
                align: 'left',
     cellWrap: true,
                dataIndex: 'value',
                     renderer: function (val, meta, record) {
                       if ("last.dbsync" == record.data.code && val.indexOf(':-:')) {
                              var list=val.split(':-:');
                              if(list && list.length>1){
                                  if(list[0]=='-1000'){
                                      return  list[1]+  '<a style="font-weight: bold;text-decoration: underline;" href="#">' + ' Generate DB Snapshot' + '</a>';
                                  }
                              }
                             return  val;

                    }
                     
                    return val;
                 }
                   
            } ],

listeners: {
        cellclick: 'initiateDBSnapShot'
    },

            bbar: [{
                xtype: 'button',
                text: l10n('AdminLicense-refresh'),
                iconCls: 'x-fa fa-refresh',
                handler: 'getHotStandStatusData',
                tooltip: l10n('AdminLicense-refresh')
            },{
                xtype: 'button',
                text: l10n('label-switch-to-maintenance'),
                tooltip: l10n('label-switch-to-maintenance'),
                handler: 'onClickSwitchToMaintenance',
                bind: {
                    hidden: '{!showToMaintLink}'
                }
            }, {
                xtype: 'button',
                text: l10n('label-remove-from-maintenance'),
                tooltip: l10n('label-remove-from-maintenance'),
                handler: 'onClickRemoveToMaintenace',
                bind: {
                    hidden: '{!showRemoveMaintLink}'
                }
            }, {
                xtype: 'button',
                text: l10n('label-force-standby'),
                tooltip: l10n('label-force-standby'),
                handler: 'onClickForceStandby',
                bind: {
                    hidden: '{showRemoveMaintLink}'
                }
            }],
        }];
        me.callParent(arguments);
    },

    columnChange: function(value, p, record) {
        if (value == "ONLINE") {
            return '<b style="color: green;">ONLINE</b>';
        } else if (value == "OFFLINE") {
            return '<b style="color: red;">OFFLINE</b>';
        }
        return value;
    }
});
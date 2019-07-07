Ext.define('AdminApp.view.main.AdminTabs', {
    extend: 'Ext.tab.Panel',
    alias: 'widget.admintabpanel',

    requires: ['AdminApp.view.users.UsersMain', 'AdminApp.view.roomSystems.RoomSystemView'],

    tabPosition: 'top',

    cls: 'tabcss',
    border: true,

    //plain : true,
    reference: 'adminTabpanel',
    initComponent: function() {
        var me = this,
            isOperator = false;

        if (userRole == "ROLE_OPERATOR") {
            isOperator = true;
        }


        me.items = [{
            xtype: 'userview',
            title: l10n('users')
        }, {
            xtype: 'meetingRoomsView',
            title: l10n('meeting-rooms')
        }, {
            xtype: 'currentcallsview',
            title: l10n('current-calls')
        }, {
            xtype: 'roomsystems-roomsystemview',
            title: l10n('room-system')
        }, {
            xtype: 'groupsView',
            title: l10n('groups'),
            hidden: isOperator

        }, {
            xtype: 'settingsview',
            title: l10n('settings'),
            hidden: isOperator
        }];
        me.callParent(arguments);
    },
    activeTab: 0,
    listeners: {
        render: function(tabpanel) {
            this.items.each(function(panel) {
                panel.tab.on('click', function() {
                    panel.fireEvent('render');
                    panel.fireEvent('beforerender');
                });

            });
        },


        beforerender: function(panel) {
            var bar = panel.tabBar;
            bar.insert(0, [{

                xtype: 'panel',

                width: 200,


                bodyStyle: {
                    'background-color': 'inherit',
                    //verflow': 'hidden'


                },
                items: [{

                    xtype: 'form',
                    style: {
                        'background-color': 'inherit',
                    },
                    bodyStyle: {
                        'background-color': 'inherit',
                        //verflow': 'hidden'


                    },
                    cls: 'logoform',
                    // maxWidth : 145,
                    loader: {
                        url: 'customizedlogoinmarkup.ajax',
                        autoLoad: true
                    }


                }]
            }]);
            bar.insert(9, [{

                    xtype: 'component',

                    flex: 1,
                    height: 40,
                },

                {
                    xtype: 'panel',
                    bodyStyle: {
                        'background-color': 'inherit',
                        'color': '#FFFFFF',

                    },

                    layout: {
                        type: 'hbox'
                    },
                    items: [{
                            xtype: 'label',
                            style: {
                                'margin-top': '10px',

                            },
                            html: adminUserName + ipAddress + ' ' + ' <a style="font-weight: bold; text-decoration: inherit;" href="javascript:void(0)" onclick="logoutAdmin()">' + l10n('logout') + '</a> '

                        }, {
                            xtype: 'component',
                            width: 20

                        }
                        /*{
                    xtype: 'button',
                    iconCls: 'x-fa fa-sign-out',
                    tooltip: l10n('logout'),
                     listeners : {
                        click : 'logoutAdmin'
                    }
                }*/
                    ]



                },



            ]);

        }
    }


});
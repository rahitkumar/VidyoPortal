Ext.define('AdminApp.view.users.ManageUsers', {
    extend: 'Ext.panel.Panel',
     

    alias: 'widget.manageusersview',
    initComponent: function() {
        var manageUsersPanel,
            topToolbar;

        topToolbar = {
            xtype: 'toolbar',
            docked: 'top',
            width: '100%',
            height: 60,
            scrollable:true,
            layout: {
                type: 'hbox',
                align: 'center'

            },
            defaults: {

                labelWidth: 40,
                bodyPadding: 10
            },
            border: 0,
            reference: 'topToolbar',
            items: [{
                xtype: 'textfield',
                fieldLabel: l10n('member-name'),
                reference: 'memberNameFilter',
                labelWidth: 120,
                name: 'memberName',
                enableKeyEvents: true,
                listeners: {
                    change: 'usersGridFilterLoad'
                }
            }, {
                xtype: 'textfield',
                fieldLabel: l10n('ext'),
                labelWidth: 45,
                labelAlign: 'left',
                width: 100,
                name: 'ext',
                reference: 'extFilter',
                maskRe: /[0-9]/,
                enableKeyEvents: true,
                listeners: {
                    change: 'usersGridFilterLoad'
                }
            }, {
                xtype: 'combo',
                name: 'type',
                labelWidth: 35,
                labelAlign: 'left',
                fieldLabel: l10n('type'),
                reference: 'typeFilter',
                queryMode: 'local',
                displayField: 'name',
                editable: false,
                valueField: 'type',
                value: 'All',
                bind: {
                    store: '{typeFilterStore}'
                },
                listeners: {
                    select: 'usersGridFilterLoad'
                }
            }, {
                xtype: 'textfield',
                labelWidth: 120,
                fieldLabel: l10n('group-name'),
                reference: 'groupNameFilter',
                name: 'groupName',
                labelAlign: 'left',
                enableKeyEvents: true,
                listeners: {
                    change: 'usersGridFilterLoad'
                }
            }, 
            {
                xtype: 'combo',
                name: 'enable',
                 anchor: '-15',
                labelAlign: 'left',
                fieldLabel: l10n('status'),
                reference: 'enableStatus',
                queryMode: 'local',
                displayField: 'name',
                editable: false,
                valueField: 'type',
                value: 'All',
                bind: {
                    store: '{statusFilterStore}'
                },
                listeners: {
                    select: 'usersGridFilterLoad'
                }
            }]
        };

        this.items = [{
            xtype: 'grid',
            reference: 'manageUsersGrid',
        
            margin: 0,
            padding: 0,
            border: false,
            scrollable:true,
           height: 800,
            selModel: Ext.create('Ext.selection.CheckboxModel', {
                allowDeselect: true,
                injectCheckbox: 'first',
                showHeaderCheckbox: true,
                checkOnly: true,
                mode: 'SIMPLE',
                renderer: function(value, metaData, record) {
                        if (record.data.memberID != record.data.userMemberId && record.get('username') != adminUserName) {
                            //  metaData.style='display:none';
                            return '<div class="' + Ext.baseCSSPrefix + 'grid-row-checker">&#160;</div>';
                        } else {
                            return '';
                        }
                    }
                    /*,
                                        selectAll: function(){
                                            //will prevent to select all rows
                                        	var grid = this.view;
                                        	var store = grid.getStore();
                                        	var sm = grid.getSelectionModel();
                                        	store.each(function(item, idx) {
                    	                    	if (item.get('memberName') == adminUserName) {
                    	                    		sm.deselect(idx, true);
                    	                    	} else {
                    	                    		sm.select(idx, true);
                    	                    	}
                                        	});
                                        }*/
            }),
            columns: [{
                text: l10n('member-name'),
                dataIndex: 'memberName',
                width: '30%',
                tdCls: 'columnwithclickevent',
                renderer: function(value, p, record) {
                    var userName = record.data.username;
                    userName = Ext.String.htmlEncode(userName);
                    value = Ext.String.htmlEncode(value);
                    if (record.data.roleName == "Legacy") {
                        return "<span class='member-name'>" + value + "</span>";
                    } else {
                        return "<span class='member-name'>" + value + "[" + userName + "]" + "</span>";
                    }

                }
            }, {
                text: l10n('ext'),
                dataIndex: 'roomExtNumber',
                width: '8%',
                renderer: function(value, p, record) {
                	return Ext.String.htmlEncode(value);
                }
            }, {
                text: l10n('type'),
                dataIndex: 'roleName',
                width: '12%'
            }, {
                text: l10n('group-name'),
                dataIndex: 'groupName',
                width: '17%',
                renderer: function(value, p, record) {
                    if (record.data.roomType == "Legacy") {
                        return '';
                    } else {
                        return Ext.String.htmlEncode(value);
                    }
                }
            }, {
                text: l10n('date-joined'),
                dataIndex: 'memberCreated',
                width: '13.7%',
                renderer: function(value) {
                    return value ? Ext.util.Format.date(value, Ext.grid.PropertyColumnModel.dateFormat) : '';
                }
            }, {
                text: l10n('enabled'),
                dataIndex: 'enable',
                width: '15%',
                renderer: function(value) {
                    return (value == "1") ? "Yes" : "No";
                }
            }],
            bind: {
                store: '{manageUsersGridStore}'
            },
            loadMask: true,

            tbar: topToolbar,
            dockedItems: [{
                xtype: 'pagingtoolbar',
                border: 0,
                 scrollable:true,
                bind: {
                    store: '{manageUsersGridStore}'
                },
                dock: 'bottom',
                displayInfo: true,
                displayMsg: l10n('displaying-members-0-1-of-2'),
                emptyMsg: l10n('no-members-to-display'),
                items: ['-', {
                    xtype: 'button',
                    text: l10n('delete'),
                    iconCls: 'x-fa fa-minus-circle',
                    handler: 'manageUsersDeletRecord'
                }, {
                    text: l10n('add-user'),
                    iconCls: 'x-fa fa-plus-circle',
                    handler: 'addUserView'
                }, {
                    text: l10n('add-legacy-device'),
                    iconCls: 'x-fa fa-plus-circle',
                    handler: 'addLegacyDeviceView'
                }, {
                    text: l10n('import-users'),
                    iconCls: 'x-fa fa-upload',
                    handler: 'importUsersView'
                }, {
                    text: l10n('export-users'),
                    iconCls: 'x-fa fa-download',
                    handler: 'exportUsersView'
                }]
            }],
            listeners: {
                sortchange: 'onUsersSortChange',
                rowclick: 'onManageUsersRowClick',
                render: 'usersGridLoad'
            }

        }];

        this.callParent();
    }
});
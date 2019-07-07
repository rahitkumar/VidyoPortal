Ext.define('AdminApp.view.meetingRooms.ManageMeetingRooms', {
    extend: 'Ext.panel.Panel',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
  
    border: false,
    alias: 'widget.managemeetingroomsview',
    initComponent: function() {
        var me = this;
        var topToolbar;

        topToolbar = {
            xtype: 'toolbar',
            docked: 'top',
            width: '100%',
            height: 60,
            layout: {
                type: 'hbox',
                align: 'center'

            },
            reference: 'topToolbar',
            defaults: {

                labelWidth: 40,
                bodyPadding: 10
            },
        
            items: [{
                xtype: 'textfield',
                fieldLabel: l10n('room-name'),
                itemId: 'roomName',
                reference: 'roomNameFilter',
                labelWidth: 117,
                enableKeyEvents: true,
                listeners: {
                    change: 'manageMeetingRoomsFilterLoad'
                }
            }, {
                xtype: 'textfield',
                fieldLabel: l10n('ext'),
                reference: 'extFilter',
                itemId: 'ext',
                width: 100,
                labelWidth: 45,
                labelAlign: 'left',
                maskRe: /[0-9]/,
                enableKeyEvents: true,
                value: '',
                listeners: {
                    keyup: 'manageMeetingRoomsFilterLoad'
                }
            }, {
                xtype: 'combo',
                fieldLabel: l10n('type'),
                reference: 'typeFilter',
                itemId: 'type',
                labelWidth: 35,
                labelAlign: 'left',
                queryMode: 'local',
                displayField: 'name',
                valueField: 'type',
                value: 'All',
                editable: false,
                bind: {
                    store: '{typeFilterStore}'
                },
                listeners: {
                    select: 'manageMeetingRoomsFilterLoad'
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
                    select: 'manageMeetingRoomsFilterLoad'
                }
            }]
        };

        this.items = [{
            xtype: 'panel',
            layout: {
                type: 'vbox',
                align: 'begin'
            },
            defaults: {

                
                margin: 25
            },
            items: [{
                xtype: 'form',

                layout: {
                    type: 'hbox',
                    align: 'center'
                },
                border: false,
                width: 350,
                ui: 'footer',

                items: [{
                    xtype: 'textfield',
                    fieldLabel: l10n('extension'),
                    reference: 'cmextension',
                    labelWidth: 100,
                    margin: '0 10 0 0',
                   
                    width: 200,
                    maxLength: 64,
                    allowBlank: false,
                    regex: /^[0-9]+$/,
                    value: ''

                }, {
                    xtype: 'button',
                    formBind: true,
                    text: 'Control Meeting',
                    handler: 'controlMeetingPopup'
                }]
            }]


        }, {
            xtype: 'grid',
                 
            reference: 'manageMeetingRoomsGrid',
            enableColumnHide: true,
           title:l10n('search'),
            width: '100%',
     
            scrollable:true,
           height: 720,
            selModel: Ext.create('Ext.selection.CheckboxModel', {
                injectCheckbox: 'first',
                showHeaderCheckbox: true,
                checkOnly: true,
                mode: 'SIMPLE',
                renderer: function(value, metaData, record) {
                    if (record.data.roomType == 'Public') {
                        return '<div class="' + Ext.baseCSSPrefix + 'grid-row-checker">&#160;</div>';
                    } else {
                        return '';
                    }
                }
            }),

            columns: [{
                xtype: 'hiddenfield',
                dataIndex: 'roomID'
            }, {
                text: l10n('room-name'),
                dataIndex: 'displayName',
                width: '30%',
                tdCls: 'columnwithclickevent',
                renderer: function(value, p, record) {
                    if (record.data.roomName != 'No results') {
                    	value = Ext.String.htmlEncode(value);
                        return "<span class='room-name'>" + value + " [" + Ext.String.htmlEncode(record.data.roomName) + "]"+ "</span>";
                    } else {
                        return l10n('noresults');
                    }
                }
            }, {
                text: l10n('ext'),
                dataIndex: 'roomExtNumber',
                width: '6%'
            }, {
                text: l10n('type'),
                dataIndex: 'roomType',
                width: '10%'
            }, {
                text: l10n('enabled'),
                dataIndex: 'roomEnabled',
                align: "center",
                width: '12%',
                sortable: false,
                renderer: function(value) {
                    return (value == "1") ? l10n('yes') : l10n('no');
                }
            }, {
                text: l10n('state'),
                sortable: false,
                width: '10%',
                renderer: function(value, p, record) {
                    var status,
                        lock,
                        pin;
                    if (record.data.roomStatus == 2) { // Full
                        status = '<img src="js/AdminApp/resources/images/admin_icon_full.gif" alt="Full" title="Full" align="left" style="padding-right:5px"/>';
                    } else if (record.data.roomStatus == 1) { // Occupied
                        status = '<img src="js/AdminApp/resources/images/admin_icon_occupied.gif"  alt="Occupied" title="Occupied by ' + record.data.numParticipants + '" align="left" style="padding-right:5px"/>';
                    } else { // Empty
                        status = '<img src="js/AdminApp/resources/images/admin_icon_empty.gif" alt="Empty" title="Empty" align="left" style="padding-right:5px"/>';
                    }

                    if (record.data.roomLocked == 1) {
                        status += '<img src="js/AdminApp/resources/images/admin_icon_lock.gif" alt="Locked" title="Locked" align="left" style="padding-right:5px"/>';
                    } else {
                        status += '';
                    }

                    if (record.data.roomPIN != '') {
                        status += '<img src="js/AdminApp/resources/images/admin_icon_pin.gif" alt="Pin Protected" title="Pin Protected" align="left" style="padding-right:5px"/>';
                    } else {
                        status += '';
                    }

                    return status;

                }
            }, {
                text: l10n('control-meeting'),
                sortable: false,
                width: '20%',
                tdCls: 'columnwithclickevent',
                renderer: function(value, p, record) {
                    if (record.data.roomName != 'No results') {
                        var controlMetingUrl = "roomcontrol.html?roomID=" + record.data.roomID;
                        return '<a target="_blank" href="' + controlMetingUrl + '">' + l10n('control-meeting') + '</a>';
                    } else {
                        return l10n('no-results');
                    }
                }
            }],
            bind: {
                store: '{manageMeetingRoomsStore}'
            },
            loadMask: true,
            tbar: topToolbar,
            dockedItems: [{
                xtype: 'pagingtoolbar',
                flex: 1,
                bind: {
                    store: '{manageMeetingRoomsStore}'
                },
                dock: 'bottom',
                border: false,
                cls: 'white-header-footer',
                displayInfo: true,
                displayMsg: l10n('displaying-rooms-0-1-of-2'),
                emptyMsg: l10n('no-rooms-to-display'),
                items: ['-', {
                    xtype: 'button',
                    text: l10n('delete'),
                    iconCls: 'x-fa fa-minus-circle',
                    handler: 'meetingRoomsDeletRecord'
                }, {
                    text: l10n('add-meeting-room'),
                    iconCls: 'x-fa fa-plus-circle',
                    handler: 'addMeetingRoomsView'
                }
                ]
            }],
            listeners: {
                sortchange: 'meetingRoomsSortChange',
                render: 'manageMeetingRoomsLoad',
                rowclick: 'onManageMeetingRoomsRowClick'
            }

        }];

        this.callParent();
    }
});
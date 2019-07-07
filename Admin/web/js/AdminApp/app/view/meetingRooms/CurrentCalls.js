Ext.define('AdminApp.view.meetingRooms.CurrentCalls', {
    extend : 'Ext.panel.Panel',
    requires : ['AdminApp.view.meetingRooms.MeetingRoomsController', 'AdminApp.view.meetingRooms.MeetingRoomsModel', 'AdminApp.view.meetingRooms.ManageMeetingRooms', 'AdminApp.view.meetingRooms.AddMeetingRoom',   'AdminApp.model.meetingRooms.AddMeetingRoomModel', 'AdminApp.model.Field'],

    controller : 'meetingRoomsController',

    viewModel : {
        type : 'meetingRoomsModel'
    },
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    border:false,
    frame:false,
    alias:'widget.currentcallsview',
    initComponent : function() {

        this.items = [{
                xtype : 'grid',
                reference : 'currentCallsGrid',
                width : '100%',
                scrollable:true,
                height: 800,
        
                features: [{
                    ftype: 'grouping'
                  }],
                columns : [{
                    text : l10n('conference-name'),
                    dataIndex : 'conferenceName',
                    sortable : true,
                    width : '29.5%',
                    minWidth:80,
                    renderer: function(value, p, record) {
                        value = Ext.String.htmlEncode(value);
                        return value;
                    }
                }, {
                    text : l10n('name'),
                    sortable : true,
                    dataIndex : 'name',
                    width : '29.5%',
                    menuDisabled : true,
                    minWidth:80,
                    renderer: function(value, p, record) {
                       value = Ext.String.htmlEncode(value);
                       return value;
                    }
                }, {
                    text : l10n('extension'),
                    sortable : true,
                    dataIndex : 'ext',
                    width : '29.5%',
                    menuDisabled : true,
                    minWidth:90,
                    renderer: function(value, p, record) {
                        value = Ext.String.htmlEncode(value);
                        return value;
                     }
                }],
                bind : {
                    store : '{currentCallsGridStore}'
                },
                loadMask : true,
                dockedItems : [{
                    xtype : 'pagingtoolbar',
               
                    bind : {
                        store : '{currentCallsGridStore}'
                    },
                    dock : 'bottom',
                    displayInfo : true,
                    displayMsg: l10n('displaying-rooms-0-1-of-2'),
                    emptyMsg : l10n('no-calls-to-display')
                }],
                listeners : {
                    render : 'currentCallGridLoad',
                    sortChange:'currentcallsSortChange'
                }
            
        }];
        this.callParent();
    },
    listeners : {
        render : 'onCurrentCallsRender'
     }
});

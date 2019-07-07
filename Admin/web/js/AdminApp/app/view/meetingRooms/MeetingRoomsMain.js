Ext.define('AdminApp.view.meetingRooms.MeetingRoomsMain', {
    extend : 'Ext.container.Container',
    alias : 'widget.meetingRoomsView',
    requires : ['AdminApp.view.meetingRooms.MeetingRoomsController', 'AdminApp.view.meetingRooms.MeetingRoomsModel', 'AdminApp.view.meetingRooms.ManageMeetingRooms', 'AdminApp.view.meetingRooms.AddMeetingRoom',  'AdminApp.view.meetingRooms.CurrentCalls', 'AdminApp.model.meetingRooms.AddMeetingRoomModel', 'AdminApp.model.Field'],

    controller : 'meetingRoomsController',

    viewModel : {
        type : 'meetingRoomsModel'
    },

    layout : {
        type : 'hbox',
        align : 'stretch'
    },

    border : false,

    items : [{
        xtype : 'panel',
        flex : 1,
        border : false,
        reference : 'meetingRoomsContainer',
        layout : {
            type : 'card',
            align : 'stretch'
        },
        items:[{
        	xtype:'managemeetingroomsview',
        }]
    }],
    listeners : {
        render : 'onMeetingRoomsRender'
    }
});

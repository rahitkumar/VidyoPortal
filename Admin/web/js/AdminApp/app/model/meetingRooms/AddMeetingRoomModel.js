Ext.define('AdminApp.model.meetingRooms.AddMeetingRoomModel', {
    extend: 'Ext.data.Model',
    fields: [
		{name: 'roomID', type: 'int'},
		{name: 'roomTypeID', type: 'int'},
		{name: 'memberID', type: 'int'},
		{name: 'ownerName', type: 'string'},
		{name: 'displayName', type: 'string'},
		{name: 'groupID', type: 'int'},
		{name: 'roomName', type: 'string'},
		{name: 'roomExtNumber', type: 'string'},
		{name: 'roomDescription', type: 'string'},
		{name: 'roomType', type: 'string'},
		{name: 'enabled', type: 'boolean', mapping: 'enabled', dateFormat: this.formatEnable},
		{name: 'roomEnabled', type: 'int'},
		{name: 'locked', type: 'boolean', mapping: 'locked', dateFormat: this.formatEnable},
		{name: 'roomLocked', type: 'int'},
		{name: 'roomPIN', type: 'string'},
        {name: 'roomModeratorPIN', type: 'string'},
        {name: 'roomKey', type: 'string', mapping: 'roomKey'},
		{name: 'roomURL', type: 'string'},
		{name: 'tenantPrefix', type: 'string'},
		{name: 'tenantName', type: 'string'}
     ],
    formatEnable: function(value){
    	return (value == 1);
    }
});
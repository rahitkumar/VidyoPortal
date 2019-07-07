Ext.define('AdminApp.model.users.AddLegacyDeviceModel', {
    extend: 'Ext.data.Model',
    fields: [
		{name: 'memberID', type: 'int'},
		{name: 'roleID', type: 'int'},
		{name: 'roleName', type: 'string'},
		{name: 'tenantID', type: 'int'},
		{name: 'groupID', type: 'int'},
		{name: 'groupName', type: 'string'},
		{name: 'langID', type: 'int'},
		{name: 'profileID', type: 'int'},
		{name: 'username', type: 'string'},
		{name: 'memberName', type: 'string'},
		{name: 'enable', type: 'boolean', dateFormat: this.formatEnable},
		{name: 'emailAddress', type: 'string'},
		{name: 'memberCreated', type: 'date', dateFormat: 'timestamp'},
		{name: 'location', type: 'string'},
		{name: 'description', type: 'string'},
		{name: 'roomID', type: 'int'},
		{name: 'roomTypeID', type: 'int'},
		{name: 'roleType', type: 'string'},
		{name: 'roomName', type: 'string'},
		{name: 'roomExtNumber', type: 'string'}
     ],
    formatEnable: function(value){
    	return (value == 1);
    }
});
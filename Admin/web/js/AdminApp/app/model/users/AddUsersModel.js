Ext.define('AdminApp.model.users.AddUsersModel', {
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
		{name: 'enable', type: 'boolean', dateFormat: this.formatEnable},
		{name: 'allowedToParticipateHtml', type: 'boolean', dateFormat: this.formatEnable},
		{name: 'memberCreated', type: 'date', dateFormat: 'timestamp'},
		{name: 'location', type: 'string'},
		{name: 'description', type: 'string'},
		{name: 'roomID', type: 'int'},
		{name: 'roleType', type: 'string'},
		{name: 'roomName', type: 'string'},
		{name: 'tenantPrefix', type: 'string'},
		{name: 'tenantName', type: 'string'},
	//<c:if test="${model.memberID != '0'}">	
		{name: 'username', type: 'string'},
		{name: 'emailAddress', type: 'string'},
		{name: 'memberName', type: 'string'},
		{name: 'roomExtNumber', type: 'string'},
		{name: 'roomTypeID', type: 'int'},
	//</c:if>
		{name: 'proxyID', type: 'int'},
		{name: 'locationID', type: 'int'},
        {name: 'importedUsed', type: 'string'},
        {name: 'phone1', type: 'string'},
        {name: 'phone2', type: 'string'},
        {name: 'phone3', type: 'string'},
        {name: 'title', type: 'string'},
        {name: 'department', type: 'string'},
        {name: 'instantMessagerID', type: 'string'},
        {name: 'userImageAllowed', type: 'int'},
        {name: 'neoRoomPermanentPairingDeviceUser', type: 'boolean', dateFormat: this.formatEnable}

     ],
    formatEnable: function(value){
    	return (value == 1);
    }
});
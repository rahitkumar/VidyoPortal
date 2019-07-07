Ext.define('AdminApp.view.roomSystems.RoomSystemViewModel', {
	extend: 'Ext.app.ViewModel',
	alias: 'viewmodel.roomsystems-roomsystemview',
	data: {
		name: 'AdminApp'
	},
	stores: {
		manageVidyoRoomsStore: {
			fields: [{
				name: 'endpointID',
				type: 'int'
			}, {
				name: 'memberName',
				type: 'string'
			}, {
				name: 'ipAddress',
				type: 'string'
			}, {
				name: 'status',
				type: 'string'
			}],
			remoteSort: true,
			remoteFilter: true,
			pageSize: 100,
			proxy: {
				type: 'ajax',
				url: 'vrooms.ajax',
				method: 'GET',
				reader: {
					type: 'xml',
					totalProperty: 'results',
					record: 'row',
					id: 'endpointID'
				}
			},
            listeners : {
                beforeload : 'roomSystemsBeforeLoad'
            }
		},
	}
});
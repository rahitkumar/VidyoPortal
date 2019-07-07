Ext.define('AdminApp.model.groups.AddGroupModel', {
    extend: 'Ext.data.Model',
    fields: [
				{name: 'groupID', type: 'int'},
				{name: 'groupName', type: 'string'},
				{name: 'groupDescription', type: 'string'},
				{name: 'roomMaxUsers', type: 'int'},
				{name: 'userMaxBandWidthIn', type: 'int'},
				{name: 'userMaxBandWidthOut', type: 'int'},
				{name: 'routerID', type: 'int'},
				{name: 'secondaryRouterID', type: 'int'},
				{name: 'defaultFlag', type: 'int'},
				{name: 'allowRecordingFlag', type: 'boolean', dateFormat: this.formatEnable}
     ],
    formatEnable: function(value){
    	return (value == 1);
    }
});
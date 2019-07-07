Ext.define('AdminApp.view.users.UsersMain', {
    extend : 'Ext.container.Container',
    alias : 'widget.userview',
    requires : ['AdminApp.view.users.UsersController', 'AdminApp.view.users.UsersModel', 'AdminApp.view.users.ManageUsers', 'AdminApp.view.users.AddUsers', 'AdminApp.view.users.ImportUsers', 'AdminApp.view.users.ExportUsers', 'AdminApp.view.users.AddLegacyDevice', 'AdminApp.model.users.AddUsersModel', 'AdminApp.model.users.AddLegacyDeviceModel', 'AdminApp.model.Field'],
    controller : 'usersController',

    viewModel : {
        type : 'usersModel'
    },

    layout : {
        type : 'hbox'
      
    },
    border : false,
    items : [{
        xtype : 'panel',
        border : false,
        flex : 1,
        reference : 'usersContainer',
        layout : {
            type : 'card',
            align : 'stretch'
        },
        items:[{
        	xtype:'manageusersview',
        }]
    }],
    listeners : {
        render : 'onUsersRender'
    }
});

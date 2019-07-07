Ext.define('AdminApp.view.groups.GroupsMain', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.groupsView',
    requires : ['AdminApp.view.groups.GroupsController', 'AdminApp.view.groups.GroupsModel', 'AdminApp.view.groups.ManageGroups', 'AdminApp.view.groups.AddGroups', 'AdminApp.model.groups.AddGroupModel', 'AdminApp.model.Field'],
    controller : 'groupsController',

    viewModel : {
        type : 'groupsModel'
    },

   
   
    border : false,
    items : [{
        xtype : 'panel',
        flex : 1,
        border : false,
        layout : {
            type : 'card',
            align : 'stretch'
        },
        reference : 'groupsContainer',
        items:[{
        	xtype:'managegroupsview'
        }]
    }],
    listeners : {
        render : 'onGroupsRender'
    }
});

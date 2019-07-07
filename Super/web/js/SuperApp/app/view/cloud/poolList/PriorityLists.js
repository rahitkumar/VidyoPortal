Ext.define('SuperApp.view.cloud.poolList.PriorityLists', {
    extend: 'Ext.panel.Panel',
    
    xtype: 'priorityLists',
    
   	requires: [
       	'Ext.container.Container',
       	'SuperApp.view.cloud.poolList.PoolListModel',
   		'SuperApp.view.cloud.poolList.PoolListController',
       	'SuperApp.view.cloud.poolList.PoolList',
       	'SuperApp.view.cloud.poolList.PriorityDetail'
    ],
    
    controller: 'poolList',

	viewModel:{
        type: 'poolList'
    },
    
    reference: 'priorityLists',
    
    layout: {
        type: 'card',
        animation: {
            type: 'slide',
        }
    },		
    
    items: [{
    	xtype: 'poolList'
    }, {
    	xtype: 'priorityDetail'
    }]
});

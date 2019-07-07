Ext.define('Pool',{
        extend: 'Ext.data.Model',
        fields: [
            'name', 'id', 'order'
        ]
});


Ext.define('SuperApp.view.cloud.poolList.PoolListModel', {
    extend : 'Ext.app.ViewModel',

    alias : 'viewmodel.poolList',

    parent : 'main',

    data: {
    	maxPriorityValue: 1
    },
    
    stores : {
        poolListSummary : {
            fields : [{
                name : 'id'
            }, {
                name : 'priorityListName',
            }, {
                name : 'poolPriorityMap',
            }],
            autoLoad : false,
            proxy : {
                type : 'ajax',
                url : 'getprioritylist.ajax',
                reader : {
                    type : 'json',
                    rootProperty : 'pool_priority_list'
                }
            },
            pageSize : 0
        },
        assignedRouterPoolsStore : {
        	model : 'Pool',
            autoLoad : false,
            proxy : {
                type : 'ajax',
                url : 'getassignedpools.ajax',
                reader : {
                    type : 'json',
                    rootProperty : 'pool_list'
                }
            },
            pageSize : 0
        },
        availableRouterPoolsStore : {
        	model : 'Pool',
            autoLoad : false,
            proxy : {
                type : 'ajax',
                url : 'getavailablepools.ajax',
                reader : {
                    type : 'json',
                    rootProperty : 'pool_list'
                }
            },
            pageSize : 0
        }
    }
}); 
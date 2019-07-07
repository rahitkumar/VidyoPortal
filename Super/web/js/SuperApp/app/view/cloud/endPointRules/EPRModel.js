Ext.define('SuperApp.view.cloud.endPointRules.EPRModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.EPR',
    
    requires: ['SuperApp.view.cloud.main.MainModel', 'SuperApp.model.cloud.LocationTagsModel'],

	parent: 'cloudMain',
	
	stores: {
      	eprSummary: {
            fields : ['id', 'ruleName', 'description', 'ruleOrder', {
                name : 'poolprioritylistname',
                mapping : 'poolprioritylist.priorityListName'
            }, 'ruleSet'],
	       autoLoad: false,
	       proxy: {
	       		type: 'ajax',
	       		url : 'getruleslist.ajax',
	        	reader: {
	            	type: 'json',
	            	rootProperty: 'pool_priority_list'
	        	}
    	},    	
            sortOnLoad : true,
            sorters :'ruleOrder'
        },
    	freePriorityListsStore: {
            fields : [{
                name : 'id'
            }, {
                name : 'priorityListName',
            }, {
                name : 'poolPriorityMap',
            }],
	       autoLoad: false,
	       proxy: {
	       		type: 'ajax',
	       		url : 'getpoolprioritylist.ajax',
	       		params : 'modified',
	        	reader: {
	            	type: 'json',
	            	rootProperty: 'priority_lists'
	        	}
	    	}
    	},
        ruleSetStore : {
    		fields: ['privateIP', 'privateIpCIDR', 'publicIP', 'publicIpCIDR', 'locationTagID', 'endpointID', 'ruleOrder', 'id'],
 	       	autoLoad: false,
 	       	proxy: {
 	       		type: 'memory',
 	        	reader: {
 	            	type: 'json',
 	            	rootProperty: 'ruleSets'
 	        	}
 	    	}
    	},
    	ruleLocations: {
    		model: 'SuperApp.model.cloud.LocationTagsModel',
    		autoLoad: false,
			fields: [ {
				name : 'locationTag',
				mapping : 'locationTag',
				convert : function(v) {
					return Ext.util.Format.htmlDecode(v);
				}
			}],
	       	proxy: {
	       		type: 'ajax',
	       		url : 'getlocations.ajax',
	        	reader: {
	            	type: 'json'
	        	}
	    	}
    	}
	}
});
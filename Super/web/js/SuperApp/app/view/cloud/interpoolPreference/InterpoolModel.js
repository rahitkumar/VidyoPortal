Ext.define('SuperApp.view.cloud.interpoolPreference.InterpoolModel', {
    extend: 'Ext.app.ViewModel',
	requires: ['SuperApp.model.cloud.PoolsList', 'SuperApp.model.cloud.PoolConnections'],

    alias: 'viewmodel.interpool',

    parent: 'main',
    
    stores: {
    	poolsList:{
	   		model: 'SuperApp.model.cloud.PoolsList',
	    	autoLoad: false,
	       	proxy: {
	       		type: 'ajax',
	       		url : 'getpoollist.ajax',
	        	reader: {
	            	type: 'json',
	            	rootProperty: 'pools_list'
	        	}
	    	}
	    },
	    connectionsList:{
	   		fields: ['source', 'target', 'left', 'right', 'name', 'id'],
	    	autoLoad: false,
	       	proxy: {
	       		type: 'ajax',
	       		url : 'getconnectionlist.ajax',
	        	reader: {
	            	type: 'json',
	            	rootProperty: 'connections_list'
	        	}
	    	}
	    },
	    poolConnections:{
	   		model: 'SuperApp.model.cloud.PoolConnections',
	    	autoLoad: false,
	       	proxy: {
	       		type: 'memory',
	        	reader: {
	            	type: 'json',
	            	rootProperty: 'connections_list'
	        	}
	    	}
	    },
	    availableRoutersStore:{
	   		fields: ['components'],
	    	autoLoad: false,
	       	proxy: {
	       		type: 'ajax',
	       		url : 'getactiverouters.ajax',
	        	reader: {
	            	type: 'json'
	        	}
	    	}
	    },
	    associatedRoutersStore: {
	   		fields: ['components'],
	    	autoLoad: false,
	       	proxy: {
	       		type: 'memory',
	        	reader: {
	            	type: 'json'
	        	}
	    	}
	    },
	    availablePoolsStore: {
	    	model: 'SuperApp.model.cloud.PoolsList',
	    	autoLoad: false,
	       	proxy: {
	       		type: 'memory',
	        	reader: {
	            	type: 'json'
	        	}
	    	}
	    }
    },
    
    data: {
		backHidden: true,
		pool2Edit: 0,
		selectedPool: null
	}
});
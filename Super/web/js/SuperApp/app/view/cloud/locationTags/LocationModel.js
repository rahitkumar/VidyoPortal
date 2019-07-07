Ext.define('SuperApp.view.cloud.locationTags.LocationModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.location',
    
    requires: ['SuperApp.model.cloud.LocationTagsModel'],

	parent: 'main',
	
	stores:  {
    	locationTagsStore:{
	   		model: 'SuperApp.model.cloud.LocationTagsModel',
	    	autoLoad: false,
	       	proxy: {
	       		type: 'memory',
	        	reader: {
	            	type: 'json',
	            	rootProperty: 'items'
	        	}
	    	}
	    }
	}
});
Ext.define('SuperApp.view.settings.CDRAccess.CDRExportPurgeViewModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.CDRExportPurgeViewModel',
    stores:{
    	tenantds: {
 	       fields: [{name: 'tenantID', type: 'string'},
 	       		    {name: 'tenantName', type: 'string'}
 	               ],
 	       autoLoad: true,
 	       remoteSort: true,
 	       remoteFilter : true,
 	       proxy: {
                url: 'tenants.ajax',
 	       		type: 'ajax',
 	        	reader: {
 	        		type:'xml',
 	        		totalRecords: 'results',
	 	       		record: 'row',
	 	       		id: 'tenantID'
 	        	}
 	    	},
 	    	listeners:{
 	    		load: 'cdrExpPurgeComboLoad'
 	    	}
     	}
    }
});
Ext.define('AdminApp.view.settings.cdr.CDRExportPurgeViewModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.CDRExportPurgeViewModel',
    data:{
    	CDRFormat:'',
        tenantName:''
    },
    stores:{
    	tenantds: {
 	       fields: [{name: 'tenantID', type: 'string'},
 	       		    {name: 'tenantName', type: 'string'}
 	               ],
 	       autoLoad: false,
 	       remoteSort: true,
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
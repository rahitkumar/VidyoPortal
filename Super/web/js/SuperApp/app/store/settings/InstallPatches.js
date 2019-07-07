Ext.define('SuperApp.store.settings.InstallPatches', {
	 extend:'Ext.data.Store',
	 model: 'SuperApp.model.settings.InstallPatches',
	 proxy: {
	        type: 'ajax',
	        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
	        url:"securedmaint/maintenance_installed_patches.ajax",
	        reader: {
	            type: 'xml',
	            totalRecords: 'results',
	            record: 'row',
	            rootProperty:'dataset'
	        }
	    },
	    autoLoad:true
 });
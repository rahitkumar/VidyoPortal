
Ext.define('SuperApp.store.settings.InstallLogList', {
	 extend:'Ext.data.Store',
	 model: 'SuperApp.model.settings.InstallLogModel',
	 proxy: {
	        type: 'ajax',
	        actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
	        url:"securedmaint/maintenance_install_log_list.ajax",
	        reader: {
	            type: 'xml',
	            totalRecords: 'results',
	            record: 'row',
	            rootProperty:'dataset'
	        }
	    },
	    autoLoad:true
 });
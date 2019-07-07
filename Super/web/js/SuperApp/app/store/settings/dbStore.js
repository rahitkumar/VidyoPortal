
Ext.define('SuperApp.store.settings.dbStore', {
	 extend:'Ext.data.Store',
	 model: 'SuperApp.model.settings.dbmodel',
	 proxy: {
	        type: 'ajax',
	        url:'securedmaint/maintenance_db.ajax',
	        reader: {
	            type: 'xml',
	            totalRecords: 'results',
	            record: 'row',
	            rootProperty:'dataset'
	        }
	    },
	    autoLoad:true
 });
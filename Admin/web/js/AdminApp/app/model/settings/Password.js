/**
/**
 * @class Roles
 */
Ext.define('AdminApp.model.settings.Password', {
	extend: 'Ext.data.Model',

	fields: [{
		name: 'minPINLength',
		type: 'int'
	}, {
		name: 'sessionExpPeriod',
		type: 'int'
	}],
	proxy: {
		type: 'ajax',
		url: 'getpasswordconfigvalsadmin.ajax',
		reader: {
			type: 'xml',
			totalRecords: 'results',
			record: 'row',
			rootProperty: 'dataset'
		}
	}
});
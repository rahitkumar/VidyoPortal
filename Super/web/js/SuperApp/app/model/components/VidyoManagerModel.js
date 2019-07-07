Ext.define('SuperApp.model.components.VidyoManagerModel', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'compuniqueid',
		mapping : 'components.compID'
	}, {
		name : 'name',
		mapping : 'components.name',
		convert : function(v) {
			return Ext.util.Format.htmlDecode(v);
		}
	}, {
		name : 'mgmtUrl',
		mapping : 'components.mgmtUrl'
	}, {
		name : 'compType',
		mapping : 'components.compType.name'
	}, 'emcpport', 'soapport', 'rmcpport', 'fqdn', 'dscpvalue', 'id', {
		name : 'componentId',
		mapping : 'components.id'
	},'compID'],
	proxy : {
		type : 'ajax',
		url : 'getvidyomanager.ajax?',
		reader : {
			type : 'json',
			rootProperty : 'items'
		}
	}
});

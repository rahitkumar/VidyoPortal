/**
/**
 * @class Roles
 */
Ext.define('AdminApp.model.settings.QualityOfService', {
	extend: 'Ext.data.Model',

	fields :[{
		name :'mediavideo',
		mapping :'endpointSettings > dscp > video'
	},{
		name :'mediaaudio',
		mapping :'endpointSettings > dscp > audio'
	},{
		name :'mediadata',
		mapping :'endpointSettings > dscp > content'
	},{
		name :'signaling',
		mapping :'endpointSettings > dscp > signaling'
	},{
		name :'minMediaPort',
		mapping :'endpointSettings > mediaPortRange > minPort'
	},{
		name :'maxMediaPort',
		mapping :'endpointSettings > mediaPortRange > maxPort'
	},{
		name :'alwaysUseVidyoProxy',
		mapping :'endpointSettings > vidyoProxy > alwaysUseVidyoProxy'
	}],
	 proxy : {
                type : 'ajax',
                url : 'getEndpointSettings.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
});
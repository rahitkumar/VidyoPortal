Ext.define('SuperApp.model.components.GatewayModel', {
	extend : 'SuperApp.model.components.VidyoManagerModel',
	fields : ['userName', {name : 'confirmPassword', mapping : 'password'}, 'password'],
	proxy : {
		type : 'ajax',
		url : 'getvidyogateway.ajax',
		reader : {
			type : 'json'
		}
	}
});

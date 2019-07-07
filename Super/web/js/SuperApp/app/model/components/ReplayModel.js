Ext.define('SuperApp.model.components.ReplayModel', {
	extend : 'SuperApp.model.components.VidyoManagerModel',
	fields : ['userName', {name : 'confirmPassword', mapping : 'password'}, 'password'],
	proxy : {
		type : 'ajax',
		url : 'getvidyoreplay.ajax?',
		reader : {
			type : 'json'
		}
	}
});

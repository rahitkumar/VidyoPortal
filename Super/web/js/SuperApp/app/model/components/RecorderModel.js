Ext.define('SuperApp.model.components.RecorderModel', {
	extend : 'SuperApp.model.components.VidyoManagerModel',
	fields : ['userName', {name : 'confirmPassword', mapping : 'password'}, 'password'],
	proxy : {
		type : 'ajax',
		url : 'getvidyorecorder.ajax?',
		reader : {
			type : 'json'
		}
	}
});

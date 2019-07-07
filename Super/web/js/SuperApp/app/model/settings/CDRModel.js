

Ext.define('SuperApp.model.settings.CDRModel', {
    extend: 'Ext.data.Model',
    fields: [
            {name: 'enabled', type: 'int'},
     		{name: 'CDREnable', type: 'boolean', dateFormat: this.formatEnable},
    		{name: 'allowdeny', type: 'int'},
    		{name: 'CDRAllowDeny', type: 'boolean', dateFormat: this.formatEnable},
    		{name: 'id', type: 'string'},
    		{name: 'ip', type: 'string'},
    		{name: 'allowdelete', type: 'int'},
    		{name: 'CDRAllowDelete', type: 'boolean', dateFormat: this.formatEnable},
    		{name: 'password', type: 'string'},
    		{name: 'format', type: 'string'}],
    formatEnable: function(value){
    	return (value == 1);
    }

});
Ext.define('SuperApp.model.settings.SuperAccounts', {
    extend : 'Ext.data.Model',
    fields : ['memberID', 'username', 'memberName', 'emailAddress', 'langID', 'description', {
        name : 'memberCreated',
        type : 'date',
        convert : function(value) {
            return new Date(value * 1000);
        }
    }, {
        name : 'enable',
        type : 'string',
        convert : function(value) {
            if (value == '1' || value == 1)
                return 'Yes';
            else
                return 'No';
        }
    },{
        name : 'isDefault',
        type : 'boolean'
    }]
});
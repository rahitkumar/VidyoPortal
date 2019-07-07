/**
 */
Ext.define('SuperApp.view.settings.superaccounts.SuperAccountsViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.SuperAccountsViewModel',
    data:{
          usernameReadOnly:false
    },
    stores : {
        superAccountsStore : {
            fields : [{
                name : 'memberID',
                type : 'int'
            }, 'username', 'memberName', 'emailAddress', 'langID', 'description', {
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
            }, {
                name : 'isDefault',
                type : 'boolean'
            }],
            pageSize : 50,
            remoteSort : true,
            remoteFilter : true,
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "POST",
                    update : "POST",
                    destroy : "POST"
                },
                url : 'superaccounts.ajax',
                reader : {
                    type : 'xml',
                    totalProperty : 'results',
                    record : 'row',
                    id : 'memberID'
                }
            }
        },
        saLocation : {
            fields :[{
                name : 'langID'
            },{
                name: 'langName'
            }],
            autoLoad : true,
            proxy :{
                type : 'ajax',
                url : 'syslang.ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        statusFilterStore : {
            fields : ['type', 'name'],
            data : [{
                "type" : "All",
                "name" : "All"
            }, {
                "type" : "Enabled",
                "name" : "Enabled"
            }, {
                "type" : "Disabled",
                "name" : "Disabled"
            }]
        },
    }
}); 
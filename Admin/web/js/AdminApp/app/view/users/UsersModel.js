Ext.define('AdminApp.view.users.UsersModel', {
    extend : 'Ext.app.ViewModel',

    alias : 'viewmodel.usersModel',
    data : {
        memberID : '',
        title : '',
        tenantPrefix : '',
        roomExtNumber : '',
        memberIdVal : '',
        ldap : '',
        importedUsed : '',
        roleId : '',
        userNameReadOnly : '',
        deviceNameReadOnly : '',
        defaultGroupExists : '',
        roleID : '',
        currRoomExt : '',
        isImportedUsed:false,
        enable:''
    },
    stores : {
        manageUsersGridStore : {
            fields : [{
                name : 'memberID',
                type : 'int'
            }, {
                name : 'roleID',
                type : 'int'
            }, {
                name : 'roleName',
                type : 'string'
            }, {
                name : 'tenantID',
                type : 'int'
            }, {
                name : 'groupID',
                type : 'int'
            }, {
                name : 'groupName',
                type : 'string'
            }, {
                name : 'langID',
                type : 'int'
            }, {
                name : 'profileID',
                type : 'int'
            }, {
                name : 'username',
                type : 'string'
            }, {
                name : 'memberName',
                type : 'string'
            }, {
                name : 'enable',
                type : 'int'
            }, {
                name : 'emailAddress',
                type : 'string'
            }, {
                name : 'memberCreated',
                type : 'date',
                dateFormat : 'timestamp'
            }, {
                name : 'roomID',
                type : 'int'
            }, {
                name : 'roomTypeID',
                type : 'int'
            }, {
                name : 'roleType',
                type : 'string'
            }, {
                name : 'roomName',
                type : 'string'
            }, {
                name : 'roomExtNumber',
                type : 'string'
            }, {
                name : 'importedUsed',
                type : 'string'
            }, {
                name : 'userMemberId',
                type : 'int'
            }],
            remoteSort : true,
            remoteFilter : true,
            pageSize: 50,
            proxy : {
                type : 'ajax',
                url : 'members.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalProperty : 'results',
                    record : 'row',
                    id : 'memberID'
                }
            },
            listeners:{
            	beforeload: 'usersGridBeforeLoad'
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
        typeFilterStore : {
            fields : ['type', 'name'],
            data : [{
                "type" : "All",
                "name" : "All"
            }, {
                "type" : "Admin",
                "name" : "Admin"
            }, {
                "type" : "Operator",
                "name" : "Operator"
            }, {
                "type" : "Normal",
                "name" : "Normal"
            }, {
                "type" : "VidyoRoom",
                "name" : "VidyoRoom"
            }, {
                "type" : "Legacy",
                "name" : "Legacy"
            }, {
                "type" : "Executive",
                "name" : "Executive/VidyoRoom SE"
            }, {
                "type" : "VidyoPanorama",
                "name" : "VidyoPanorama"
            }]
        },
        UserTypeStore : {
            fields : [{
                name : 'roleID',
                type : 'int'
            }, {
                name : 'roleName',
                type : 'string'
            }],
            pageSize:0,
            proxy : {
                type : 'ajax',
                url : 'roles.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    id : 'roleID'
                }
            }
        },
        groupComboStore : {
            fields : [{
                name : 'groupID',
                type : 'int'
            }, {
                name : 'groupName',
                type : 'string'
            }],
            pageSize:0,
            proxy : {
                type : 'ajax',
                url : 'groups.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    id : 'groupID'
                }
            }
        },
        proxyComboStore : {
            fields : [{
                name : 'proxyID',
                type : 'int'
            }, {
                name : 'proxyName',
                type : 'string'
            }],
            pageSize:0,
            proxy : {
                type : 'ajax',
                url : 'proxies.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    id : 'proxyID'
                }
            }
        },
        locationTagComboStore : {
            fields : [{
                name : 'locationID',
                type : 'int'
            }, {
                name : 'locationTag',
                type : 'string'
            }],
            pageSize:0,
            proxy : {
                type : 'ajax',
                url : 'locationtags.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    id : 'locationID'
                }
            }
        },
        langPreferenceStore : {
            fields : [{
                name : 'langID',
                type : 'int'
            }, {
                name : 'langCode',
                type : 'string'
            }, {
                name : 'langName',
                type : 'string'
            }, {
                name : 'langFlag',
                type : 'string'
            }],
            pageSize:0,
            proxy : {
                type : 'ajax',
                url : 'langs.ajax?displaysystemlanguage=on',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    id : 'langID'
                }
            }
        }
    }

}); 
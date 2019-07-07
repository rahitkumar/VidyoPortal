Ext.define('AdminApp.view.groups.GroupsModel', {
    extend : 'Ext.app.ViewModel',

    alias : 'viewmodel.groupsModel',
    data : {
        hasReplay : false,
        allowRecordingFlag : false,
        title : '',
        groupIdVal : 0,
        currGroupName : ''
    },
    stores : {
        manageGroupsGridStore : {
            fields : [{
                name : 'groupID',
                type : 'int'
            }, {
                name : 'groupName',
                type : 'string'
            }, {
                name : 'roomMaxUsers',
                type : 'int'
            }, {
                name : 'userMaxBandWidthOut',
                type : 'int'
            }, {
                name : 'userMaxBandWidthIn',
                type : 'int'
            }, {
                name : 'defaultFlag',
                type : 'int'
            }],
            remoteSort : true,
            remoteFilter : true,
            pageSize : 50,
            proxy : {
                type : 'ajax',
                url : 'groups.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalProperty : 'results',
                    record : 'row',
                    id : 'groupID'
                }
            },
            listeners : {
                beforeload : 'manageGroupsBeforeload'
            }
        }
    }

});

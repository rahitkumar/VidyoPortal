Ext.define('AdminApp.view.meetingRooms.MeetingRoomsModel', {
    extend : 'Ext.app.ViewModel',

    alias : 'viewmodel.meetingRoomsModel',
    data : {
        title : '',
        roomId : 0,
        roomNameReadOnly : '',
        tenantPrefixVal : '',
        roomPinTitle : '',
        roomModPinTitle : '',
        currRoomExt : '',
        currRoomName : '',
        meetingRoomsSortDataIndex : '',
        meetingRoomsSortDir : '',
        vidyoRoomsSortDataIndex:'',
        vidyoRoomsSortDir:'',
        currentcallsSortDataIndex:'',
        currentcallsSortDir:''
    },
    stores : {
        manageMeetingRoomsStore : {
            fields : [{
                name : 'roomID',
                type : 'int'
            }, {
                name : 'tenantID',
                type : 'int'
            }, {
            	name : 'displayName',
            	type : 'string'
            }, {
                name : 'roomName',
                type : 'string'
            }, {
                name : 'roomExtNumber',
                type : 'string'
            }, {
                name : 'roomType',
                type : 'string'
            }, {
                name : 'roomEnabled',
                type : 'int'
            }, {
                name : 'roomLocked',
                type : 'int'
            }, {
                name : 'roomPIN',
                type : 'string'
            }, {
                name : 'numParticipants',
                type : 'int'
            }, {
                name : 'roomStatus',
                type : 'int'
            }],
            remoteSort : true,
            remoteFilter : true,
            pageSize : 50,
            proxy : {
                type : 'ajax',
                url : 'rooms.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalProperty : 'results',
                    record : 'row',
                    id : 'roomID'
                }
            },
            listeners : {
                beforeload : 'meetingRoomsBeforeLoad'
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
                "type" : "all",
                "name" : "All"
            }, {
                "type" : "public",
                "name" : "Public"
            }, {
                "type" : "personal",
                "name" : "Personal"
            }]
        },
        currentCallsGridStore : {
            fields : [{
                name : 'endpointID',
                type : 'int'
            }, {
                name : 'endpointGUID',
                type : 'string'
            }, {
                name : 'name',
                type : 'string'
            }, {
                name : 'ext',
                type : 'string'
            }, {
                name : 'actionAudio',
                type : 'string'
            }, {
                name : 'qtipAudio',
                type : 'string'
            }, {
                name : 'actionVideo',
                type : 'string'
            }, {
                name : 'qtipVideo',
                type : 'string'
            }, {
                name : 'actionConnect',
                type : 'string'
            }, {
                name : 'qtipConnect',
                type : 'string'
            }, {
                name : 'conferenceName',
                type : 'string'
            }, {
                name : 'conferenceType',
                type : 'string'
            }],
            remoteSort : true,
            groupField : 'conferenceName',
            autoLoad: false,
            pageSize : 50,
            proxy : {
                type : 'ajax',
                url : 'currentcalls.ajax',
                method : 'POST',
                 reader : {
                    type : 'xml',
                    totalProperty : 'results',
                    record : 'row'
                }
            },
            listeners:{
            	beforeload:'currentcallsBeforeLoad'
            }
        },
        manageVidyoRoomsStore : {
            fields : [{
                name : 'endpointID',
                type : 'int'
            }, {
                name : 'memberName',
                type : 'string'
            }, {
                name : 'ipAddress',
                type : 'string'
            }, {
                name : 'status',
                type : 'string'
            }],
            remoteSort : true,
            pageSize : 100,
            proxy : {
                type : 'ajax',
                url : 'vrooms.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalProperty : 'results',
                    record : 'row',
                    id : 'endpointID'
                }
            },
            listeners : {
                beforeload : 'vidyoRoomsBeforeload'
            }
        },
        roomOwnerComboStore : {
            fields : [{
                name : 'memberID',
                type : 'int'
            }, {
                name : 'memberName',
                type : 'string'
            }],
            pageSize:0,
            proxy : {
                type : 'ajax',
                method : 'GET',
                url : 'members.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    id : 'memberID'
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
        }
    }

}); 
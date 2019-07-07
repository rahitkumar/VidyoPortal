Ext.define('SuperApp.view.settings.hotstand.HotstandViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.HotstandViewModel',

    data : {
        showRemoveMaintLink : true,
        showToMaintLink : false,
        frequency : 30,
        startTime : 0,
        endTime : 23
    },

    stores : {
        highAvailableStore : {
            fields : [{
                name : 'showRemoveMaintLink',
                type : 'boolean'
            }, {
                name : 'startTime',
                type : 'int'
            }, {
                name : 'endTime',
                type : 'int'
            }, {
                name : 'showToMaintLink',
                type : 'boolean'
            }, {
                name : 'privilegedMode',
                type : 'boolean'
            }, {
                name : 'frequency',
                type : 'int'
            }],
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : "highavailability.ajax",
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        statusStore : {
            fields : ["code","key", "value"],
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : "clusterinfo.ajax",
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        timeStore : {
            fields : ['dataFieldName', 'displayFieldName'],
            data : [['0', '12 AM'], ['1', '1 AM'], ['2', '2 AM'], ['3', '3 AM'], ['4', '4 AM'], ['5', '5 AM'], ['6', '6 AM'], ['7', '7 AM'], ['8', '8 AM'], ['9', '9 AM'], ['10', '10 AM'], ['11', '11 AM'], ['12', '12 PM'], ['13', '1 PM'], ['14', '2 PM'], ['15', '3 PM'], ['16', '4 PM'], ['17', '5 PM'], ['18', '6 PM'], ['19', '7 PM'], ['20', '8 PM'], ['21', '9 PM'], ['22', '10 PM'], ['23', '11 PM']],
            autoLoad : false
        },
        time2Store : {
            fields : ['dataFieldName', 'displayFieldName'],
            data : [['0', '12 AM'], ['1', '1 AM'], ['2', '2 AM'], ['3', '3 AM'], ['4', '4 AM'], ['5', '5 AM'], ['6', '6 AM'], ['7', '7 AM'], ['8', '8 AM'], ['9', '9 AM'], ['10', '10 AM'], ['11', '11 AM'], ['12', '12 PM'], ['13', '1 PM'], ['14', '2 PM'], ['15', '3 PM'], ['16', '4 PM'], ['17', '5 PM'], ['18', '6 PM'], ['19', '7 PM'], ['20', '8 PM'], ['21', '9 PM'], ['22', '10 PM'], ['23', '11 PM']],
            autoLoad : false
        }
    }
});

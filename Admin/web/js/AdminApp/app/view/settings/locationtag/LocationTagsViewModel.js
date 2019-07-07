/**
 * @class LocationTagsViewModel
 */
Ext.define('AdminApp.view.settings.locationtag.LocationTagsViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.LocationTagsViewModel',

    stores : {
        locationtagStore : {
            fields : [{
                name : 'locationID' 
            }, {
                name : 'locationTag'
            }],
            proxy : {
                type : 'ajax',
                url : 'locationtags.ajax',
                pageParam: '',
                startParam: undefined,
                limitParam: undefined,
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        groupStore : {
            fields : [{
                name : 'groupID' 
            }, {
                name : 'tenantID'
            },{
                name : 'routerID' 
            }, {
                name : 'secondaryRouterID'
            },{
                name : 'groupName' 
            }, {
                name : 'groupDescription'
            },{
                name : 'defaultFlag' 
            }, {
                name : 'roomMaxUsers'
            },{
                name : 'userMaxBandWidthIn' 
            }, {
                name : 'userMaxBandWidthOut'
            }],
            proxy : {
                type : 'ajax',
                url : 'groups.ajax',
                pageParam: '',
                startParam: undefined,
                limitParam: undefined,                
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        }
    }
});

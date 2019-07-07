/**
 * @class GuestViewModel
 */
Ext.define('AdminApp.view.settings.guest.GuestViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.GuestViewModel',
    
    stores : {
        groupStore :{
            fields : [{
                name : 'groupID'
            },{
                name : 'tenantID'
            },{
                name : 'routerID'
            },{
                name : 'secondaryRouterID'
            },{
                name : 'groupName'
            },{
                name : 'groupDescription'
            },{
                name : 'defaultFlag'
            },{
                name : 'roomMaxUsers'
            },{
                name : 'userMaxBandWidthIn'
            },{
                name : 'userMaxBandWidthOut'
            }],
            proxy : {
                type : 'ajax',
                url : 'groups.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
            pageSize:0
        },
        proxyStore : {
            fields : [{
                name : 'proxyID'
            },{
                name : 'proxyName'
            }],
            proxy : {
                type : 'ajax',
                url : 'proxies.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
            pageSize:0
        },
        locationTagStore : {
            fields : [{
                name : 'locationID'
            },{
                name : 'locationTag'
            }],
            proxy : {
                type : 'ajax',
                url : 'locationtags.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
            pageSize:0
        }
    }
});

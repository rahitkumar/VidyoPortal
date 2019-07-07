Ext.define('SuperApp.view.components.ComponentModel', {
    extend : 'Ext.app.ViewModel',

    alias : 'viewmodel.Components',

    requires : ['SuperApp.model.components.EndPointsModel', 'SuperApp.model.components.MediAddressModel'],

    data : {
        vmRecord : '',
        vRouterRecord : '',
        vRecorderRecord : '',
        vReplayRecord : '',
        vGatewayRecord : '',
        routerMediaAddRecord : '',
        vManagerCompId : '',
        vRouterCompId : '',
        vRecorderCompId : '',
        vReplayCompId : '',
        vGatewayCompId : '',
        hexDSCP : '0x0',
        hexVideoDSCP : '0x0',
        hexAudioDSCP : '0x0',
        hexContentDSCP : '0x0',
        hexSignalingDSCP : '0x0',
        isDisableReplace : true,
        hideGatewayGrid : true,
        hideRecorderGrid : true,
        hidepaggingtoolbar : true,
        isstunip : true,
        isstunport : true,
        taskRun : 0
    },

    stores : {
        componentStore : {
            fields : ["id", "status", "name", {
                name : 'compTypeName',
                convert : function(val, rec) {
                    return rec.get('compType').name;
                }
            }, "compID", "localIP", "clusterIP", "mgmtUrl", "configVersion", "compSoftwareVersion", "alarm", "runningVersion", 'statusSorter'],
            proxy : {
                type : 'ajax',
                pageParam : undefined,
                startParam: undefined,
                limitParam: undefined,
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : "getcomponents.ajax",
                reader : {
                    type : 'json',
                    rootProperty : 'items'
                }
            }
        },
        replacecomponentstore : {
            fields : [{
                name : 'name'
            }, {
                name : 'status'
            }],
            proxy : {
                type : 'memory',
                reader : {
                    type : 'xml',
                    rootProperty : ''
                }
            }
        },
        GatewayEndpointsStore : {
            model : 'SuperApp.model.components.EndPointsModel',
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : 'POST',
                    read : 'GET',
                    update : 'POST',
                    destroy : 'POST'
                },
                url : 'getvirtualendpoints.ajax',
                reader : {
                    type : 'json',
                    rootProperty : 'items'
                }
            }
        },

        GatewayPrefixStore : {
            fields : [{
                name : 'prefixID'
            }, {
                name : 'serviceID'
            }, {
                name : 'tenantID'
            }, {
                name : 'gatewayID'
            }, {
                name : 'prefix'
            }, {
                name : 'direction'
            }, {
                name : 'updateTime'
            }],
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : 'POST',
                    read : 'GET',
                    update : 'POST',
                    destroy : 'POST'
                },
                url : 'getgatewayprefixes.ajax',
                reader : {
                    type : 'json',
                    rootProperty : 'items'
                }
            }
        },
        recorderEndPointStore : {
            fields : ["recID", "endpointGUID", "prefix", "description", "status"],
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : 'POST',
                    read : 'GET',
                    update : 'POST',
                    destroy : 'POST'
                },
                url : 'getrecorderendpoints.ajax',
                reader : {
                    type : 'json',
                    rootProperty : 'items'
                }
            }
        },
        MediaAddressStore : {
            fields:["id","localIP", "remoteIP"],
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : "getmediaaddressmap.ajax",
                reader : {
                    type : 'json',
                    rootProperty : 'items'
                }
            }
        }
    }

});

Ext.define('SuperApp.view.settings.globalsettings.GlobalFeatureSettingsModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.GlobalFeatureSettingsModel',

    requires : ['SuperApp.model.settings.PortalDomain', 'SuperApp.model.settings.IPC', 'SuperApp.model.settings.RouterPools', 'SuperApp.model.settings.Chat'],

    data : {
        accessvalue :"super",
        ipccontrolmode : "block",
        isSSLNotEnabled : false
    },

    stores : {
        portalDomainStore : {
            fields : [{
                name : 'domainID'
            }, {
                name : 'domainName'
            }],
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : 'ipcportaldomains.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        ipcStore : {
            model : 'SuperApp.model.settings.IPC',
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : 'misc.html?settingstype=ipc',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        routerPoolsStore : {
            model : 'SuperApp.model.settings.RouterPools',
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url :'getpoollist.ajax',
                extraParams: {
                	status: 'ACTIVE'
                },
                reader : {
                    type : 'json',
                    rootProperty : 'items'
                }
            }
        },
        searchOptionsStore : {
            fields : [{
                name : 'showDisabledRoomsEnabled',
                mapping : 'searchOptions > showDisabledRoomsEnabled',
                type : 'boolean'
            }],
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : 'misc.html?settingstype=searchoptions',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        chatStore : {
            model : 'SuperApp.model.settings.Chat',
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : 'misc.html?settingstype=chat',
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

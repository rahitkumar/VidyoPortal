Ext.define('SuperApp.view.settings.maintenance.MaintenanceViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.MaintenanceViewModel',

    data : {
        hostport : '',
        username : '',
        password : '',
        flag : '',
        url : '',
        hideInstallPatchGrid : true,
        hideSysUpgradeLogGrid : true,
        isLocalDB : true,
        ipaddress : '',
        port : '',
        remote_logging : '',
        stunnel : ''
    },
    stores : {
        diagnosticsStore : {
            fields : [{
                name : 'fileName',
                type : 'string'
            }, {
                name : 'timestamp',
                type : 'string'
            }],
            proxy : {
                type : 'ajax',
                method : 'POST',
                url : 'securedmaint/maintenance_diagnostics_list.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    id : 'timestamp'
                }
            },
            pageSize: 0
        },
        installPatchesGridStore : {
            fields : ['patch', 'timestamp'],
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : "securedmaint/maintenance_installed_patches.ajax",
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
            pageSize: 0
        },
        sysUpgradeLogGridStore : {
            fields : ['fileName', 'timestamp'],
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : "securedmaint/maintenance_install_log_list.ajax",
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
            pageSize: 0
        },
        databaseGridStore : {
            fields : ['fileName', 'timestamp', 'timestampMS'],
            proxy : {
                type : 'ajax',
                url : 'securedmaint/maintenance_db.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    id : 'timestamp'
                }
            },
            pageSize: 0,
            autoLoad : true
        },
        sysLogFormStore : {
            fields : [{
                name : 'remote_logging',
                type : 'string'
            }, {
                name : 'stunnel',
                type : 'string'
            }, {
                name : 'ip_address',
                type : 'string'
            }, {
                name : 'port',
                type : 'int'
            }],
            proxy : {
                type : 'ajax',
                url : 'securedmaint/getsyslogconfig.ajax',
                method : 'POST',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row'
                }

            }
        },
        cdrAccessStore : {
            fields : [{
                name : 'enabled',
                type : 'string'
            }, {
                name : 'allowdeny',
                type : 'string'
            }, {
                name : 'id',
                type : 'string'
            }, {
                name : 'password',
                type : 'string'
            }, {
                name : 'ip',
                type : 'string'
            }, {
                name : 'allowdelete',
                type : 'string'
            }, {
                name : 'fipsEnabled',
                type : 'boolean'
            }],
            proxy : {
                type : 'ajax',
                url : 'securedmaint/maintenance_cdr_read.ajax',
                method : 'POST',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }

            }
        },
           pkiTenantsPageStore : {
            proxy : {
                type : 'ajax',
                url : 'webserverrestartrequest.ajax',
                reader : {
                    type : 'xml',
                    record : 'row',
                    rootProperty : 'dataset',                   
                }
            },
            fields : ['tenantID','tenantName', 'tenantURL'],
          
            autoLoad:true
        },
    }

}); 
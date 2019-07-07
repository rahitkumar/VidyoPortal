Ext.define('SuperApp.view.settings.SettingsViewModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.SettingsViewModel',

    data: {
        hideUESGrid: false,
        securityTitle: l10n('ssl-private-key'),
        prevScheduledRoom: '',
        licenseText: l10n('system-id'),
        uploadMode: 'VidyoPortal',
        /***
         * @Boolean isEncryption
         * It works in opposite manner when its true the encryption
         * button is disable and when isEncryption is false
         * Encryption button in security tab is enable.
         */
        isEncryption: true
    },

    stores: {
        systemLicenseGridStore: {
            fields: [{
                name: 'FQDN',
                type: 'string'
            }, {
                name: 'orderid',
                type: 'int'
            }, {
                name: 'feature',
                type: 'string'
            }, {
                name: 'license',
                type: 'string'
            }, {
                name: 'inuse',
                type: 'string'
            }],
            autoLoad: false,
            proxy: {
                type: 'ajax',
                url: "license.ajax",
                reader: {
                    type: 'xml',
                    totalRecords: 'results',
                    FQDN: 'FQDN',
                    record: 'row'
                }
            }
        },
        vmIdStore: {
            fields: ['vmID'],
            proxy: {
                type: 'ajax',
                url: 'vmid.ajax',
                reader: {
                    type: 'xml',
                    totalRecords: 'results',
                    FQDN: 'FQDN',
                    record: 'row'
                }
            }
        },
        auditLogComboStore: {
            fields: ['auditCode', 'auditName'],
            data: [
                ['30', '30'],
                ['60', '60'],
                ['90', '90']
            ],
            autoLoad: true
        },
        uploadEndpointSoftwareStore: {
            fields: [{
                name: 'endpointUploadID',
                type: 'int'
            }, {
                name: 'endpointUploadFile'
            }, {
                name: 'endpointUploadTime',
                type: 'date',
                dateFormat: 'timestamp'
            }, {
                name: 'endpointUploadType',
                convert: function(value, record) {
                    switch (record.data.endpointUploadType) {

                        case 'W32':
                            return l10n('installer-win32-new');
                        case 'W64':
                            return l10n('installer-win64');
                        case 'M':
                            return l10n('installer-macos');
                        case 'R':
                            return l10n('installer-hd50-100-220');
                        case 'V':
                            return l10n('installer-hd200');
                        case 'S':
                            return l10n('installer-sl5')
                        case 'U':
                            return l10n('installer-ubuntu');
                        case 'T':
                            return l10n('installer-sl5-64bit');
                        case 'X':
                            return l10n('installer-ubuntu-64bit');
                        case 'P':
                            return l10n('installer-vp600');
                        case 'N':
                            return l10n('installer-vp600-win64');
                        case 'E':
                            return l10n('installer-panorama-600-linux32');
                        case 'O':
                            return l10n('installer-vp600-linux');
                        case 'Q':
                            return l10n('installer-vr-win32');
                        case 'Y':
                            return l10n('installer-vr-win64');
                        case 'F':
                            return l10n('installer-hd50-100-220');
                        case 'Z':
                            return l10n('installer-vr-linux');
                        case 'B':
                            return l10n('installer-se-win64');
                        case 'C':
                            return l10n('installer-se-linux');
                        case 'D':
                            return l10n('installer-se-osx');
                        default:
                            return l10n('installer-win32-new');

                    }
                }
            }, {
                name: 'endpointUploadActive',
                type: 'int'
            }],
            proxy: {
                type: 'ajax',
                url: 'upload.ajax',
                pageParam: false, //to remove param "page"
                startParam: false, //to remove param "start"
                limitParam: false, //to remove param "limit"
                reader: {
                    type: 'xml',
                    totalRecords: 'results',
                    record: 'row'
                }
            },
            groupField: 'endpointUploadType',
            autoLoad: false
        },

        settingsTabLeftMenuStore: {
            fields: [{
                name: 'privilegedMode',
                type: 'boolean'
            }, {
                name: 'showStatusNotifyTab',
                type: 'boolean'
            }, {
                name: 'showExtDbTab',
                type: 'boolean'
            }, {
                name: 'showSyslog',
                type: 'boolean'
            }, {
                name: 'gmailPluginInstalled',
                type: 'boolean'
            }, {
                name: 'maintMode',
                type: 'boolean'
            }, {
                name: 'showEventsNotifyTab',
                type: 'boolean'
            }],
            proxy: {
                type: 'ajax',
                url: 'getsettingstabmenu.ajax',
                method: 'POST',
                reader: {
                    type: 'xml',
                    totalRecords: 'results',
                    record: 'row'
                }
            },
        },
        menuStore: {
            type: 'tree',
            root: {
                expanded: true,
                children: [{
                    text: l10n('system-license'),
                    id: "sl",
                    leaf: true
                }, {
                    text: l10n('platform-network-settings'),
                    id: "pns",
                    leaf: true
                }, {
                    text: l10n('manage-endpoint-software'),
                    id: "ues",
                    leaf: false,
                    children: [{
                        text: l10n('manage-endpoint-software-submenu-fileserver'),
                        id: "mesfileserver",
                        leaf: true
                    }, {
                        text: l10n('manage-endpoint-software-submenu-endpointversions'),
                        id: "mesversions",
                        leaf: true
                    }]
                }, {
                    text: l10n('maintenance'),
                    id: "maintenance",
                    leaf: false,
                    children: [{
                        text: l10n('database'),
                        id: "db",
                        leaf: true
                    }, {
                        text: l10n('upgrade'),
                        id: "sysup",
                        leaf: true
                    }, {
                        text: l10n('restart'),
                        id: "sysrst",
                        leaf: true
                    }, {
                        text: l10n('cdr-access'),
                        id: "cdr",
                        leaf: true
                    }, {
                        text: l10n('system-logs'),
                        id: "systemlog",
                        leaf: true
                    }, {
                        text: l10n('diagnostics'),
                        id: "diagnostics",
                        leaf: true
                    }, {
                        text: l10n('syslog'),
                        id: "syslog",
                        leaf: true
                    }, {
                        text: l10n('external-database'),
                        id: "extdb",
                        leaf: true
                    }, {
                        text: l10n('status-notify'),
                        id: "statusnotify",
                        leaf: true
                    },
                    {
                        text: l10n('setting-maintenance-pagelink-tenant-ca-approval'),
                        id: "pkiTenants",
                        leaf: true
                    },{
                        text: 'Events Notification Servers',
                        id: "eventsnotifyserver",
                        leaf: true
                    }]
                }, {
                    text: l10n('super-accounts'),
                    id: "superacc",
                    leaf: true
                }, {
                    text: l10n('customize-label'),
                    id: "cust",
                    leaf: false,
                    children: [{
                        text: l10n('about-info1'),
                        id: "aboutinfo",
                        leaf: true
                    }, {
                        text: l10n('contact-info1'),
                        id: "supportinfo",
                        leaf: true
                    }, {
                        text: l10n('notification'),
                        id: "notifications",
                        leaf: true
                    }, {
                        text: l10n('invite-text'),
                        id: "invitetext",
                        leaf: true
                    }, {
                        text: l10n('customize-logo'),
                        id: "custlogos",
                        leaf: true
                    }, {
                        text: "VidyoConnect Endpoints",
                        id: "customizeEndpoints",
                        visible:false,
                        leaf: true
                    },
                        {
                        text: l10n('guidesproperties-label'),
                        id: "guidesprop",
                        leaf: true
                    }, {
                        text: l10n('banners-tablabel'),
                        id: "banners",
                        leaf: true
                    }, {
                        text : l10n('settings-customization-room-link'),
                        id : "roomlink",
                        leaf : true
                    }]
                }, {
                    text: l10n('smtp-security'),
                    id: "security",
                    leaf: false,
                    children: [{
                        text: l10n('super-security-ssl-private-key-tab'),
                        id: "sslpk",
                        leaf: true
                    }, {
                        text: l10n('super-security-ssl-csr-tab'),
                        id: "sslcsr",
                        leaf: true
                    }, {
                        text: l10n('security-super-ssl-super-security-ssl-certificate-tab'),
                        id: "servercert",
                        leaf: true
                    }, {
                        text: l10n('super-security-ssl-certificates-bundle-tab'),
                        id: "servcacert",
                        leaf: true
                    }, {
                        text: l10n('super-security-ssl-applications-tab'),
                        id: "apps",
                        leaf: true
                    }, {
                        text: l10n('super-security-ssl-certificates-advanced-tab'),
                        id: "advanced",
                        leaf: true
                    }, {
                        text: l10n('passwords-tablabel'),
                        id: "passwords",
                        leaf: true
                    }]
                }, {
                    text: l10n('ipc-enable-diable-label'),
                    id: "ipc",
                    leaf: true
                }, {
                    text: l10n('endpoint-network-settings'),
                    id: "endpointSettings",
                    leaf: true
                }, {
                    text: l10n('highavail-label'),
                    id: "hotstand",
                    leaf: false,
                    children: [{
                        text: l10n('label-status'),
                        id: "hotstandstatus",
                        leaf: true
                    }]
                }, {
                    text: l10n('admin-featuresettings-label'),
                    id: "gfs",
                    leaf: false,
                    children: [{
                            text: l10n('vidyoweb'),
                            id: "vidyoweb",
                            leaf: true
                        },{
                        	 text: l10n('settings-featuers-vidyoneo-webrtc'),
                             id: "vidyoneowebrtc",
                             leaf: true
                        }, {
                            text: l10n('mobileaccess'),
                            id: "vidyomobile",
                            leaf: true
                        },
                        /*{
                                            text : "VidyoDesktop",
                                            id : "vidyodesktop",
                                            leaf : true
                                            }, */
                        {
                            text: l10n('search-options'),
                            id: "searchoptions",
                            leaf: true
                        }, {
                            text: l10n('vidyoproxy'),
                            id: "tls",
                            leaf: true
                        }, {
                            text: l10n('chat'),
                            id: "chat",
                            leaf: true
                        }, {
                            text: l10n('installed-gmail-plugin'),
                            id: "gmail",
                            leaf: true
                        }, {
                            text: l10n('room'),
                            id: "schedroom",
                            leaf: true
                        }, {
                            text : l10n('user-attributes'),
                            id: "userAttributes",
                            leaf: true
                        },
                        {
                            text :'Custom Roles',
                            id: "customRoles",
                            leaf: true
                        },
                        {
                            text :'Epic Integration',
                            id: "enableEpicIntegration",
                            leaf: true
                        },
                        {
                            text :'TytoCare Integration',
                            id: "enableTytoCareIntegration",
                            leaf: true
                        }
                    ]
                }]
            }
        }
    }
});
//SettingsViewModel
Ext.define('AdminApp.view.settings.SettingsViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.SettingsViewModel',

    stores : {
        settignsMenuStore : {
            type : "tree",
            root : {
                expanded : true,
                children : [{
                    text : l10n('SuperSystemLicense-license'),
                    id : 'license',
                    leaf : true
                }, {
                    text : l10n('manage-endpoint-software'),
                    id : 'ues',
                    leaf : true
                }, {
                    text : l10n('system-language'),
                    id : 'sysLang',
                    leaf : true
                }, {
                    text : l10n('guest-s-settings'),
                    id : 'guestsettings',
                    leaf : true
                }, {
                    text : l10n('customize-label'),
                    id : 'customization',
                    leaf : false,
                    children : [{
                        text : l10n('about-info'),
                        id : 'aboutinfo',
                        leaf : true
                    }, {
                        text : l10n('contact-info1'),
                        id : 'supportinfo',
                        leaf : true
                    }, {
                        text : l10n('notification'),
                        id : 'notification',
                        leaf : true
                    }, {
                        text : l10n('invite-text'),
                        id : 'invitetext',
                        leaf : true
                    }, {
                        text : l10n('customize-logo'),
                        id : 'customizelogos',
                        leaf : true
                    }, {
                        text : "VidyoConnect Endpoints",
                        id : 'neoendpoints',
                        leaf : true
                    }
                    ]
                }, {
                    text : l10n('authentication'),
                    id : 'authentication',
                    leaf : true
                }, {
                    text : l10n('manage-location-tags'),
                    id : 'loctags',
                    leaf : true
                }, {
                    text : l10n('ipc-label'),
                    id : 'ipc',
                    leaf : true
                }, {
                    text : l10n('cdr-access'),
                    id : 'cdraccess',
                    leaf : true
                }, {
                    text : l10n('smtp-security'),
                    id : "security",
                    leaf : true,
                }, {
                    text : l10n('endpoint-network-settings'),
                    id : 'qualityservice',
                    leaf : true
                }, {
                    text : l10n('admin-featuresettings-leftmenu-label'),
                    id : 'featuresettings',
                    leaf : false,
                    children : [{
                        text : l10n('vidyoweb'),
                        id : 'vidyoweb',
                        leaf : true
                    }, {
                        text : l10n('settings-featuers-vidyoneo-webrtc'),
                        id : 'vidyoneowebrtc',
                        leaf : true
                    },{
                        text : l10n('chat'),
                        id : 'chat',
                        leaf : true
                    }, {
                        text : l10n('room'),
                        id : 'roomattr',
                        leaf : true
                    },
                    {
                        text : l10n('user-attributes'),
                        id : 'userattr',
                        leaf : true
                    },
                    {
                        text : l10n('epic-integration'),
                        id : 'epicintegration',
                        leaf : true
                    },
                    {
                        text : l10n('tytocare-integration'),
                        id : 'tytocareintegration',
                        leaf : true
                    }]
                }]
            }
        },
        settingsTabLeftMenuStore : {
            fields : [{
                name : 'privilegedMode',
                type : 'boolean'
            }, {
                name : 'isIpcSuperManaged',
                type : 'boolean'
            },
            {
                name : 'vidyoWebAvailable',
                type : 'boolean'
            },
            {
                name : 'vidyoChatAvailable',
                type : 'boolean'
            },
            {
                name : 'vidyoSchdRoomAvailable',
                type : 'boolean'
            },
            {
                name : 'showUserAttributePage',
                type : 'boolean'
            },
            {
                name : 'showVidyoNeoWebRTC',
                type : 'boolean'
            },
            {
                name : 'showEpicIntegration',
                type : 'boolean'
            },
            {
                name : 'showTytoCareIntegration',
                type : 'boolean'
            }],
            proxy : {
                type : 'ajax',
                url : 'getsettingstabmenu.ajax',
                method : 'POST',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row'
                }
            },
        }
    }

});

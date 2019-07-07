/**
 * @author FeatureSettingsViewModel
 */
Ext.define('AdminApp.view.settings.featuresettings.FeatureSettingsViewModel', {
    extend: 'Ext.app.ViewModel',
    alias: 'viewmodel.FeatureSettingsViewModel',


    formulas: {
        zinServerDisabled: {
            bind: {
                x: '{enableVidyoWebRef.checked}',
                y: '{zincEnabled.checked}'

            },
            get: function(data) {
                return (data.x && data.y);

            }


        },
        isUserImageUploadEnabled: {
            bind: {
                x: '{enableUserImgeUpldGlobal}',
                y: '{enableUserImage.checked}'

            },
            get: function(data) {
                return (data.x && data.y);

            }


        },
        isEnabled: {
            bind: {
                x: '{publicRoomEnabledGlobal}',
                y: '{publicRoomEnabledTenant.checked}'

            },
            get: function(data) {
                return (data.x && data.y);

            }


        },
        isVidyoNeoWebRTCGuestEnabled: {
            bind: {
                x: '{enableVidyoNeoWebRTCGuest}'

            },
            get: function(data) {
                return (data.x);
            }
        },
        isVidyoNeoWebRTCUserEnabled: {
            bind: {
                x: '{enableVidyoNeoWebRTCUser}'

            },
            get: function(data) {
                return (data.x);
            }
        }
    },
    stores: {
        userAttributeStore: {
            fields: [{
                name: 'enableUserImage',
                type: 'boolean'
            }, {
                name: 'enableUserImageUpload',
                type: 'boolean'
            },{
                name:'enableUserImgeUpldGlobal',
                 type: 'boolean'
            }],
            proxy: {
                type: 'ajax',
                url: 'getuserattribute.ajax',
                reader: {
                    type: 'xml',
                    totalRecords: 'results',
                    record: 'row',
                    rootProperty: 'dataset'
                }
            }

        },
        roomAttributeStore: {


            fields: [{
                name: 'schRoomEnabledTenantLevel',
                type: 'boolean'
            }, {
                name: 'schRoomEnabledSystemLevel',
                type: 'boolean'
            }, {
                name: 'lectureModeAllowed',
                type: 'boolean'
            }, {
                name: 'waitingRoomsEnabled',
                type: 'boolean'
            }, {
                name: 'lectureModeStrict',
                type: 'boolean'
            }, {
                name: 'waitUntilOwnerJoins',
                type: 'boolean'
            }, {
                name: 'publicRoomEnabledGlobal',
                type: 'boolean'
            }, {
                name: 'publicRoomEnabledTenant',
                type: 'boolean'
            }, {
                name: 'publicRoomMaxRoomNoPerUser',
                type: 'int'
            }, {
                name: 'publicRoomMaxRoomNoPerUserGlb',
                type: 'int'
            }],
            proxy: {
                type: 'ajax',
                url: 'getroomattribute.ajax',
                reader: {
                    type: 'xml',
                    totalRecords: 'results',
                    record: 'row',
                    rootProperty: 'dataset'
                }
            }
        },
        vidyoNeoWebRTCStore: {
        	fields :[{
                name :'enableVidyoNeoWebRTCGuest',
                type :'boolean'
            },{
                name :'enableVidyoNeoWebRTCUser',
                type :'boolean'
            },{
                name :'enableVidyoNeoWebRTCGuestAdmin',
                type :'boolean'
            },{
                name :'enableVidyoNeoWebRTCUserAdmin',
                type :'boolean'
            }],
            proxy: {
            	type: 'ajax',
                url:'vidyoneowebrtcsettingadmin.ajax',
                actionMethods:  {create: "POST", read: "GET", update: "POST", destroy: "POST"},
                reader: {
                    type: 'xml',
                    record: 'row',
                    rootProperty:'dataset'
                }
            }
        }
    }
});
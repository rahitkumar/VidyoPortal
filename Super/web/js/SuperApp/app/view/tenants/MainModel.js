Ext.define('SuperApp.view.tenants.MainModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.tenantMain',

    stores: {
        tenantsMenuStore : {
            fields : ['tenantSection'],
            autoLoad: false,
            remoteSort: true,
            data : [{
                tenantSection : l10n('manage-tenants'),
                recordId : 'manage_tenants'
            }, {
                tenantSection : l10n('add-tenant'),
                recordId : 'add_tenant'
            }]
        },
      	manageTenantsStore: {
	       fields: ['tenantID', 'tenantName', 'tenantURL', 'wlEntries', 'tenantPrefix', 'tenantDialIn', 'description'],
	       proxy: {
	       		url: 'tenants.ajax',
	       		actionMethods: {
	       			create: 'POST',
	       			read: 'GET',
	       			update: 'POST',
	       			destroy: 'POST'
	       		},
	       		type: 'ajax',
	        	reader: {
	            	type: 'xml',
	            	rootProperty: 'dataset',
	            	totalProperty: 'results',
	            	record: 'row'
	        	}
	    	},
	    	remoteFilter : true,
	    	remoteSort : true,
	    	pageSize : 50/*,
        	//listeners: {
        	//	'beforeload': 'beforeManageTenantsLoad'
        	//}*/
    	},
    	currentCallsStore: {
    		fields: ['endpointID', 'endpointGUID', 'endpointType', 'dialIn', 'name', 'ext', 'actionVideo', 'qtipVideo', 'actionAudio', 'qtipAudio', 'actionConnect', 'qtipConnect', 'conferenceName', 'conferenceType', 'tenantName', 'vrName', 'groupName'],
    		autoLoad: false,
    		remoteSort: true,
    		groupField : 'conferenceName',
    		proxy: {
    			url: 'currentcalls.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
    		},
    		pageSize : 50,
        	listeners: {
        		'beforeload': 'beforeCurrentCallsLoad'
        	}
    	},
    	availableTenants: {
    		fields: ['tenantID', 'tenantName'],
    		proxy: {
    			url: 'fromtenants.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	selectedTenants: {
    		fields: ['tenantID', 'tenantName'],
    		proxy: {
    			url: 'totenants.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	availableVMS: {
    		fields: ['serviceID', 'roleID', 'roleName', 'serviceName', 'url'],
    		proxy: {
    			url: 'fromvms.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	selectedVMS: {
    		fields: ['serviceID', 'roleID', 'roleName', 'serviceName', 'url'],
    		proxy: {
    			url: 'tovms.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	availableVPS: {
    		fields: ['serviceID', 'roleID', 'roleName', 'serviceName', 'url'],
    		proxy: {
    			url: 'fromvps.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	selectedVPS: {
    		fields: ['serviceID', 'roleID', 'roleName', 'serviceName', 'url'],
    		proxy: {
    			url: 'tovps.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	availableVGS: {
    		fields: ['serviceID', 'roleID', 'roleName', 'serviceName', 'url'],
    		proxy: {
    			url: 'fromvgs.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	selectedVGS: {
    		fields: ['serviceID', 'roleID', 'roleName', 'serviceName', 'url'],
    		proxy: {
    			url: 'tovgs.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	availableRecs: {
    		fields: ['serviceID', 'roleID', 'roleName', 'serviceName', 'url'],
    		proxy: {
    			url: 'fromrecs.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	selectedRecs: {
    		fields: ['serviceID', 'roleID', 'roleName', 'serviceName', 'url'],
    		proxy: {
    			url: 'torecs.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	availableReplays: {
    		fields: ['serviceID', 'roleID', 'roleName', 'serviceName', 'url'],
    		proxy: {
    			url: 'fromreplays.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	selectedReplays: {
    		fields: ['serviceID', 'roleID', 'roleName', 'serviceName', 'url'],
    		proxy: {
    			url: 'toreplays.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	availableLocations: {
    		fields: ['locationID', 'locationTag'],
    		proxy: {
    			url: 'fromlocations.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	selectedLocations: {
    		fields: ['locationID', 'locationTag'],
    		proxy: {
    			url: 'tolocations.ajax',
    			type: 'ajax',
    			reader: {
    				type: 'xml',
    				rootProperty: 'dataset',
    				totalProperty: 'results',
    				record: 'row'
    			}
        	}
    	},
    	addTenantConfig: {
	       fields: ['tenantID', 'tenantName', 'mobileLogin', 'tenantPrefix','inbound','outbound', 'defaultTenantPrefix', 'vidyoGatewayControllerDns', 'guideLoc', 'licenseVersion', 'maxInstalls', 'installsInUse', 'maxSeats', 'seatsInUse', 'maxPorts', 'maxPublicRooms','publicRoomsInUse','multiTenant', 'maxExecutives', 'executivesInUse', 'maxPanoramas', 'panoramasInUse', 'showExecutives', 'showVidyoReplay', 'showVidyoVoice', 'showPanoramas', 'showScheduledRoomConfig', 'showVidyoNeoWebRTC', 'showLogAggr', 'showCustomRole', 'endpointUploadMode', 'superEndpointUploadMode'],
	       proxy: {
	       		url: 'tenant.html',
	       		method: 'GET',
	       		type: 'ajax',
	        	reader: {
	            	type: 'xml',
	            	rootProperty: 'dataset',
	            	record: 'tenant'
	        	}
        	}
    	}
	},

	data: {
		tenantsSortDataIndex: '',
		tenantsSortDir: '',
		currentCallsSortDataIndex: '',
		currentCallsSortDir: '',
		thisTenantID: '0',
        uploadMode: 'VidyoPortal'
    }
});
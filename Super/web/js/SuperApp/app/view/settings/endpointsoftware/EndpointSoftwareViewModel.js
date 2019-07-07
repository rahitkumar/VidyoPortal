Ext.define('SuperApp.view.settings.endpointsoftware.EndpointSoftwareViewModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.EndpointSoftwareViewModel',

    data: {
        hideUESGrid: false,
        hideExternalCDNGrid: false,
        uploadMode: 'VidyoPortal',
        platform: 'W64'
    },
    stores: {
	    endpointUploadModeStore : {
	    	fields: ['uploadMode'],
	    	proxy: {
	            type: 'ajax',
	            url: 'uploadmode.ajax',
	            reader: {
	                type: 'xml',
	                record: 'row',
                    rootProperty : 'dataset'
	            }
	        },
	        autoLoad: false
	    },
	        vidyoPortalEndpointSoftwareStore: {
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
	                            return l10n('installer-vr-linux32');
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
	            	name: 'endpointUploadVersion',
	            	type: 'string'
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
	        platformStore : {
	            fields : ['dataFieldName', 'displayFieldName'],
	            data : [['W32', l10n('installer-win32-new')], ['W64', l10n('installer-win64')], ['M', l10n('installer-macos')], ['R', l10n('installer-hd50-100-220')], ['V', l10n('installer-hd200')], ['S', l10n('installer-sl5')], ['U', l10n('installer-ubuntu')], ['T', l10n('installer-sl5-64bit')], ['X', l10n('installer-ubuntu-64bit')], ['P', l10n('installer-vp600')], ['N', l10n('installer-vp600-win64')], ['E', l10n('installer-panorama-600-linux32')], ['O', l10n('installer-vp600-linux')], ['Q', l10n('installer-vr-win32')], ['Y', l10n('installer-vr-win64')], ['F', l10n('installer-hd50-100-220')], ['Z', l10n('installer-vr-linux')], ['B', l10n('installer-se-win64')], ['C', l10n('installer-se-linux')], ['D', l10n('installer-se-osx')]],
	            autoLoad : false
	        }

    }

});
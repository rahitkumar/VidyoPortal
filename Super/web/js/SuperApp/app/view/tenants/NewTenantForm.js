Ext.define('SuperApp.view.tenants.NewTenantForm', {
    extend : 'Ext.panel.Panel',
    xtype : 'newTenant',
    requires : [],

    layout : {
        type : 'vbox',
        align : 'center'
    },
    items : [],

     initComponent : function() {
        var me = this;
        var tenantConfig = me.tenantConfig;

        Ext.apply(Ext.form.VTypes, {
            'tenantURL' : function() {
                var re = /^[a-zA-Z0-9.\-:]+$/;
                return function(v) {
                    return re.test(v);
                };
            }(),
            'tenantURLText' : l10n('the-format-is-wrong-must-not-contain-any-punctuation-except-hyphen-and-period'),

            'vidyoReplayURL' : function(val, field) {
                var re = /^[a-zA-Z0-9.\-:]+$/;
                var reHTTP = /^http:\/\/[a-zA-Z0-9.\-:]+$/;
                var reHTTPS = /^https:\/\/[a-zA-Z0-9.\-:]+$/;
                return function(v){
                    if (v.match(new RegExp(/^http:\/\//))) {
                        return reHTTP.test(v);
                    } else if (v.match(new RegExp(/^https:\/\//))) {
                        return reHTTPS.test(v);
                    } else {
                        return re.test(v);
                    }
                };
            },
            'vidyoReplayURLText' : l10n('this-field-accpt-vidyoreplayurl'),

            'tenantName' : function() {
                var re = /^[a-zA-Z0-9-]+$/;
                return function(v) {
                    return re.test(v);
                };
            }(),
            'tenantNameText' : l10n('the-format-is-wrong-must-only-contain-alphanumeric-chars-and-dashes'),      
            
            password : function(val, field) {
                if (field.initialPassField) {
                    var pwd = field.up('form').getForm().findField(field.initialPassField);
                    if (pwd.getValue() != '') {
                        if (val == pwd.getValue()) {
                            pwd.clearInvalid();
                            return true;
                        } else {
                            return false;
                        }
                    }
                    return true;
                }
                return true;
            },
            passwordText : l10n('password-not-match'),

            prefixExtNumber : function(val, field) {
                var re = /^[0-9]+$/;
                return re.test(val);
            },
            prefixExtNumberText : l10n('the-format-is-wrong-must-only-contain-numeric-values')
        });

        var items = [{
            xtype : 'hidden',
            name : 'tenantID',
            value : tenantConfig.get('tenantID') ? tenantConfig.get('tenantID') : 0
        }, {
            xtype : 'textfield',
            name : 'tenantName',
            margin : '5 0 5 0',
            maxLength : 128,
            vtype : 'tenantName',
            fieldLabel : '<span class="red-label">*</span>' + l10n('tenant-name'),
            allowBlank : false,
            validateOnChange : false,
            validator : me.remoteValidate
        }, {
            xtype : 'textfield',
            name : 'tenantURL',
            maxLength : 128,
            vtype : 'tenantURL',
            fieldLabel : '<span class="red-label">*</span>' + l10n('tenant-url'),
            allowBlank : false,
            validateOnChange : false,
            validator : me.remoteValidate
        }, {
            xtype : 'textfield',
            vtype : 'prefixExtNumber',
            minLength : 1,
            minValue : 0,
            maxLength : 7,
            maxValue : 9999999,
            fieldLabel : ((Ext.isEmpty(tenantConfig.get('tenantPrefix')) && tenantConfig.get('tenantID') != "0") ? '' : '<span class="red-label">*</span>') + l10n('ext-prefix'),
            name : 'tenantPrefix',
            allowBlank : (Ext.isEmpty(tenantConfig.get('tenantPrefix')) && tenantConfig.get('tenantID') != "0") ? true : false,
            validateOnChange : false,
            validator : me.remoteValidate
        }];
        if (tenantConfig.get('showVidyoVoice') == 'true') {
            items.push({
                xtype : 'textfield',
                maxLength : 20,
                fieldLabel : l10n('dial-in-number'),
                name : 'tenantDialIn'
            });
        }

        if (tenantConfig.get('showVidyoReplay') == 'true') {
            items.push({
                xtype : 'textfield',
                vtype : 'vidyoReplayURL',
                msgType : 'under',
                maxLength : 128,
                fieldLabel : l10n('vidyoreplay-url'),
                name : 'tenantReplayURL',
                validateOnChange : false,
                validator : me.remoteValidate
            });
        }
        if (tenantConfig.get('showVidyoNeoWebRTC') == 'true') {
            items.push({
                xtype : 'textfield',
                msgType : 'under',
                maxLength : 128,
                fieldLabel : l10n('tenant-vidyoneo-webrtc-url'),
                name : 'tenantWebRTCURL',
                validateOnChange : false,
                validator : me.remoteValidate
            });
        }

        items.push({
            xtype : 'textarea',
            fieldLabel : l10n('description'),
            name : 'description',
            maxLength : 65535,
            msgTarget : 'under'
        }, {
            xtype : 'textfield',
            vtype : 'tenantURL',
            fieldLabel : l10n('tenant-vidyoGatewayControllerDns'),
            name : 'vidyoGatewayControllerDns',
            validateOnChange : false,
            validator : me.remoteValidate
        }, {
            xtype : 'hidden',
            name : 'oldVidyoGatewayControllerDns'
        }, {
            xtype : 'numberfield',
            minLength : 1,
            minValue : tenantConfig.get('installsInUse'),
            maxValue : tenantConfig.get('maxInstalls'),
            allowBlank : false,
            fieldLabel : '<span class="red-label">*</span> ' + l10n('of-installs') + ' <br/><span class="red-label">(' + l10n('inuse') + ' : ' + tenantConfig.get('installsInUse') + ', ' + l10n('max') + ' : ' + tenantConfig.get('maxInstalls') + ')</span>',
            name : 'installs'
        }, {
            xtype : 'numberfield',
            minLength : 1,
            minValue : tenantConfig.get('seatsInUse') == 0 ? 1 : tenantConfig.get('seatsInUse'),
            maxValue : tenantConfig.get('maxSeats'),
            allowBlank : false,
            fieldLabel : '<span class="red-label">*</span>' + l10n('of-seats') + ' <br/><span class="red-label">(' + l10n('inuse') + ' : ' + tenantConfig.get('seatsInUse') + ', ' + l10n('max') + ' : ' + tenantConfig.get('maxSeats') + ')</span>',
            name : 'seats'
        },{
            xtype : 'numberfield',
            minLength : 1,
            minValue : tenantConfig.get('publicRoomsInUse'),
            maxValue : tenantConfig.get('maxPublicRooms'),
            allowBlank : false,
            fieldLabel : '<span class="red-label">*</span>' + '# of Public Rooms' + ' <br/><span class="red-label">(' + l10n('inuse') + ' : ' + tenantConfig.get('publicRoomsInUse') + ', ' + l10n('max') + ' : ' + tenantConfig.get('maxPublicRooms') + ')</span>',
            name : 'publicRooms'
        }, {
            xtype : 'numberfield',
            minLength : 1,
            minValue : 0,
            maxValue : tenantConfig.get('maxPorts'),
            allowBlank : false,
            fieldLabel : '<span class="red-label">*</span>' + (tenantConfig.get('licenseVersion') == '20' ? l10n('of-ports') : l10n('of-lines')) + ' <br/><span class="red-label">(' + l10n('max') + ' : ' + tenantConfig.get('maxPorts') + ')</span>',
            name : 'ports'
        });
        
        if (tenantConfig.get('licenseVersion') != '20') {
            if (tenantConfig.get('showExecutives') == 'true') {
                items.push({
                    xtype : 'numberfield',
                    fieldLabel : '<span class="red-label">*</span>' + l10n('of-executives') + ' <br/><span class="red-label">(' + l10n('inuse') + ' : ' + tenantConfig.get('executivesInUse') + ', ' + l10n('max') + ' : ' + tenantConfig.get('maxExecutives') + ')</span>',
                    name : 'executives',
                    minLength : 1,
                    minValue : tenantConfig.get('executivesInUse'),
                    maxValue : tenantConfig.get('maxExecutives'),
                    allowBlank : false,
                    value : 0
                });
            } else {
                items.push({
                    xtype : 'hidden',
                    name : 'executives',
                    value : 0
                });
            }
        }

        if (tenantConfig.get('licenseVersion') != '20') {
            if (tenantConfig.get('showPanoramas') == 'true') {
                items.push({
                    xtype : 'numberfield',
                    fieldLabel : '<span class="red-label">*</span>' + l10n('of-panoramas') + ' <br/><span class="red-label">(' + l10n('inuse') + ' : ' + tenantConfig.get('panoramasInUse') + ', ' + l10n('max') + ' : ' + tenantConfig.get('maxPanoramas') + ')</span>',
                    name : 'panoramas',
                    minLength : 1,
                    minValue : tenantConfig.get('panoramasInUse'),
                    maxValue : tenantConfig.get('maxPanoramas'),
                    allowBlank : false,
                    value : 0
                });
            } else {
                items.push({
                    xtype : 'hidden',
                    name : 'panoramas',
                    value : 0
                });
            }
        }
                
        var isExternal = false;
        if (tenantConfig.get('endpointUploadMode') == 'External' || 
        		(tenantConfig.get('tenantID') == "0" && tenantConfig.get('superEndpointUploadMode') == 'External')) {
        	isExternal = true;
        } 
        
        items.push({
            xtype : 'radiogroup',
            fieldLabel : l10n('fileserver-label'),
            labelWidth : 160,
            width : 520,
            name : 'fileServerMode',
            reference : 'fileServerMode',
            columns : 1,
            flex: 2,
            items : [{
                name : 'endpointUploadMode',
                boxLabel : l10n('fileserver-vidyoportal'),
                inputValue : 'VidyoPortal',
                checked :  !isExternal
            }, {
                name : 'endpointUploadMode',
                boxLabel : l10n('fileserver-externalFileServer'),
                inputValue : 'External',
                boxLabelWidth : 100,
                checked : isExternal
            }]
        });
        
        var mobileLogin = 0;
        if (tenantConfig.get('mobileLogin') ) {
        	mobileLogin = tenantConfig.get('mobileLogin');
        }
        items.push({
            xtype : 'checkbox',
            name : 'guestLoginEnable',
            fieldLabel : l10n('enable-guests-login'),
            boxLabel : 'enabled',
            checked : true
        }, {
            xtype : 'checkbox',
            name : 'scheduledRoomEnable',
            fieldLabel : l10n('super-scheduled-room-enable'),
            boxLabel : 'enabled',
            checked : true
        }, {
            xtype : 'checkbox',
            name : 'logAggregation',
            fieldLabel :l10n('log-aggregation-tenantpage-fieldlabel'),
            boxLabel : 'enabled',
            inputValue: '1',
            uncheckedValue: '0',
            checked : true
        },{
            xtype : 'checkbox',
            name : 'customRole',
            fieldLabel :'Enable Custom Role',
            boxLabel : 'enabled',
            inputValue: '1',
            uncheckedValue: '0',
            checked : false,
            hidden:false,
        },{
            xtype : 'fieldset',
            cls : 'bold-fieldset-header',
            title : l10n('mobiaccess-label'),
            items : [{
                xtype : 'radiogroup',
                name : 'mobileLoginEnable',
                items : [{
                    xtype : 'radiofield',
                    name : 'mobileLoginEnable',
                    inputValue : 1,
                    boxLabel : l10n('vidyoMobile'),
                    checked : (mobileLogin == 1 ? true : false)
                }, {
                    xtype : 'radiofield',
                    name : 'mobileLoginEnable',
                    inputValue : 2, // VPTL-7657 - The neoMobile value should be set to 2.
                    boxLabel : l10n('neoMobile'),
                    checked : (mobileLogin == 2 ? true : false)
                }, {
                    xtype : 'radiofield',
                    name : 'mobileLoginEnable',
                    inputValue : 0,
                    boxLabel : l10n('disabled'),
                    checked :  (mobileLogin == 0 ? true : false)
                }]
            }]
        }, {
            xtype : 'fieldset',
            title : l10n('ipc-enable-diable-label'),
            cls : 'bold-fieldset-header',
            layout : {
                type : 'hbox',
                align : 'stretch'
            },
            items : [{
                xtype : 'checkbox',
                name : 'outbound',
                boxLabel : l10n('ipc-outbound-label'),
                margin : '0 185 0 0',
                checked : true
            }, {
                xtype : 'checkbox',
                name : 'inbound',
                boxLabel : l10n('ipc-inbound-label'),
                checked : true
            }]
        });

        me.items = {
            xtype : 'fieldset',
            border : 1,
            margin : '5 0 0 0',
            layout : {
                type : 'vbox',
                align : 'center'
            },
            defaults : {
                labelWidth : 200,
                width : 600
            },
            items : items
        };

 	// this.initConfig(cfg);
        this.callParent();
    },

    remoteValidate : function() {
        if (this.textValid) {
            return true;
        } else {
            return this.textInvalid;
        }
    }
});

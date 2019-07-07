Ext.define('SuperApp.view.tenants.MainController', {
    extend : 'Ext.app.ViewController',

    alias : 'controller.tenantMain',
    requires : ['SuperApp.view.tenants.CurrentCalls', 'SuperApp.view.tenants.ManageTenants', 'SuperApp.view.tenants.AddTenant', 'SuperApp.view.tenants.NewUserForm'],

    control : {
        'tenantsMain' : {
            afterrender : 'onTenantsRender'
        },
        'currentCalls' : {
            afterrender : 'afterCurrentCallsRender'
        },
        'newTenant textfield[name="tenantName"]' : {
            change : 'remoteValidateTenantName'
        },
        'newTenant textfield[name="tenantURL"]' : {
            change : 'remoteValidateTenantURL'
        },
        'newTenant textfield[name="tenantPrefix"]' : {
            change : 'remoteValidateTenantPrefix'
        },
        'newTenant textfield[name="tenantReplayURL"]' : {
            change : 'remoteValidateTenantReplayURL'
        },
        'newTenant textfield[name="tenantWebRTCURL"]' : {
            change : 'remoteValidateTenantWebRTCURL'
        },
        'newTenant textfield[name="vidyoGatewayControllerDns"]' : {
            change : 'remoteValidateGatewayController'
        },
        'addTenant' : {
            render : 'onAddTenantRender'
        },
        'manageTenants' : {
            afterrender : 'afterManageTenantsRender',
            activate: 'onManageTenantsActivate'
        }
    },
    
    onTenantNameFilterChange: function (field) {
        var val = field.getValue(),
        store = this.view.getViewModel().getStore("manageTenantsStore");
        if (val.length === 0) {
            store.clearFilter();
        } else {
            store.filter('tenantName', val);
        }
    },
    
    onTenantUrlFilterChange: function (field) {
        var val = field.getValue(),
        store = this.view.getViewModel().getStore("manageTenantsStore");
        if (val.length === 0) {
            store.clearFilter();
        } else {
            store.filter('tenantURL', val);
        }
    },

    onManageTenantsActivate: function() {
    	var tenantCenterContainer = this.lookupReference('tenantCenterContainer');
    	if (tenantCenterContainer.down('addTenant')) {
    		tenantCenterContainer.remove(tenantCenterContainer.down('addTenant'), true);
        }
    },

    onTenantsRender : function() {
    	 var me = this,
	         view = me.view,
	         viewModel = me.getViewModel(),
	         tenantCenterContainer = this.lookupReference('tenantCenterContainer');

	     Ext.defer(function() {
	         tenantCenterContainer.getLayout().setActiveItem(view.down('manageTenants'));
	     }, 10);
	     this.loadManageTenantStore();
    },

    addTenantBtnClick: function() {
    	this.showAddTenantView(this.lookupReference('tenantCenterContainer'), 0);
    },

    showManageTenantsView : function(tenantCenterContainer) {
    	if (!tenantCenterContainer.down('manageTenants')) {
    		tenantCenterContainer.add({
                xtype : 'manageTenants'
            });
        }
        var view = tenantCenterContainer.down('manageTenants');
        tenantCenterContainer.getLayout().setActiveItem(view);
        this.loadManageTenantStore();
    },

    showAddTenantView : function(tenantCenterContainer, tenantID) {

        this.getViewModel().storeInfo.addTenantConfig.load({
            scope : this,
            params : {
                tenantID : tenantID,
                sort : 'tenantID',
                dir : 'ASC'
            },
            callback : function(records) {
                var me = this;
                if (records[0].get('tenantID') == '0' && records[0].get('multiTenant') == 'false') {
                    Ext.Msg.alert(l10n('error'), l10n('not-multi-tenant'), function() {
                        me.showManageTenantsView(tenantCenterContainer);
                    });
                } else if (records[0].get('tenantID') == '0' && Ext.isEmpty(records[0].get('defaultTenantPrefix'))) {
                    Ext.Msg.alert(l10n('error'), l10n('assign-prefix-to-default-tenant'), function() {
                        me.showManageTenantsView(tenantCenterContainer);
                    });
                } else {
               		 tenantCenterContainer.add({
                            xtype : 'addTenant',
                             tenantConfig : records[0]
                        });
                	
                    var view = tenantCenterContainer.down('addTenant');
                    tenantCenterContainer.getLayout().setActiveItem(view);

                    var storeInfo = this.getViewModel().storeInfo;

                    storeInfo.availableTenants.load({
                        scope : this,
                        params : {
                            tenantID : tenantID,
                            sort : 'tenantID',
                            dir : 'ASC'
                        },
                        callback : function(recs) {
                            this.lookupReference('availableTenantsGrid').reconfigure(storeInfo.availableTenants);
                        }
                    });
                    storeInfo.selectedTenants.load({
                        scope : this,
                        params : {
                            tenantID : tenantID,
                            sort : 'tenantID',
                            dir : 'ASC'
                        },
                        callback : function(recs) {
                            this.lookupReference('selectedTenantsGrid').reconfigure(storeInfo.selectedTenants);
                        }
                    });

                    storeInfo.availableVMS.load({
                        scope : this,
                        params : {
                            tenantID : tenantID,
                            sort : 'tenantID',
                            dir : 'ASC'
                        }
                    });
                    storeInfo.selectedVMS.load({
                        scope : this,
                        params : {
                            tenantID : tenantID,
                            sort : 'tenantID',
                            dir : 'ASC'
                        }
                    });

                    storeInfo.availableVPS.load({
                        scope : this,
                        params : {
                            tenantID : tenantID,
                            sort : 'tenantID',
                            dir : 'ASC'
                        },
                        callback : function(recs) {
                            this.lookupReference('availableVPSGrid').reconfigure(storeInfo.availableVPS);
                        }
                    });
                    storeInfo.selectedVPS.load({
                        scope : this,
                        params : {
                            tenantID : tenantID,
                            sort : 'tenantID',
                            dir : 'ASC'
                        },
                        callback : function(recs) {
                            this.lookupReference('selectedVPSGrid').reconfigure(storeInfo.selectedVPS);
                        }
                    });

                    storeInfo.availableVGS.load({
                        scope : this,
                        params : {
                            tenantID : tenantID,
                            sort : 'tenantID',
                            dir : 'ASC'
                        },
                        callback : function(recs) {
                            this.lookupReference('availableVGSGrid').reconfigure(storeInfo.availableVGS);
                        }
                    });
                    storeInfo.selectedVGS.load({
                        scope : this,
                        params : {
                            tenantID : tenantID,
                            sort : 'tenantID',
                            dir : 'ASC'
                        },
                        callback : function(recs) {
                            this.lookupReference('selectedVGSGrid').reconfigure(storeInfo.selectedVGS);
                        }
                    });

                    if (records[0].get('showVidyoReplay') == "true") {
                        storeInfo.availableRecs.load({
                            scope : this,
                            params : {
                                tenantID : tenantID,
                                sort : 'tenantID',
                                dir : 'ASC'
                            },
                            callback : function(recs) {
                                this.lookupReference('availableRecsGrid').reconfigure(storeInfo.availableRecs);
                            }
                        });
                        storeInfo.selectedRecs.load({
                            scope : this,
                            params : {
                                tenantID : tenantID,
                                sort : 'tenantID',
                                dir : 'ASC'
                            },
                            callback : function(recs) {
                                this.lookupReference('selectedRecsGrid').reconfigure(storeInfo.selectedRecs);
                            }
                        });

                        storeInfo.availableReplays.load({
                            scope : this,
                            params : {
                                tenantID : tenantID,
                                sort : 'tenantID',
                                dir : 'ASC'
                            },
                            callback : function(recs) {
                                this.lookupReference('availableReplaysGrid').reconfigure(storeInfo.availableReplays);
                            }
                        });
                        storeInfo.selectedReplays.load({
                            scope : this,
                            params : {
                                tenantID : tenantID,
                                sort : 'tenantID',
                                dir : 'ASC'
                            },
                            callback : function(recs) {
                                this.lookupReference('selectedReplaysGrid').reconfigure(storeInfo.selectedReplays);
                            }
                        });
                    }

                    var defaultLocation = null;
                    storeInfo.availableLocations.load({
                        scope : this,
                        params : {
                            tenantID : tenantID,
                            sort : 'tenantID',
                            dir : 'ASC'
                        },
                        callback : function(recs) {
                            defaultLocation = storeInfo.availableLocations.findRecord('locationID', '1', 0, false, true, true); //findRecord('locationID', 1);
                            this.lookupReference('availableLocationsGrid').reconfigure(storeInfo.availableLocations);

                            storeInfo.selectedLocations.load({
                                scope : this,
                                params : {
                                    tenantID : tenantID,
                                    sort : 'tenantID',
                                    dir : 'ASC'
                                },
                                callback : function(recs) {
                                	if(tenantID == 0) {
	                                    storeInfo.availableLocations.remove(defaultLocation);
	                                    storeInfo.selectedLocations.add(defaultLocation);
                                	}
                                    this.lookupReference('selectedLocationsGrid').reconfigure(storeInfo.selectedLocations);
                                }
                            });
                        }
                    });

                    Ext.Ajax.request({
                    	url: 'ipc.ajax',
                    	method: 'POST',
                    	params: {
                    		'tenantID': tenantID
                    	},
                    	scope: this,
                    	errorReader: new Ext.data.XmlReader({
	   		                 successProperty : '@success',
			                 record : 'field'
			             }, ['id', 'msg']),
                    	success: function(res) {
                    		var response = res.responseXML.documentElement;
                    		if(response.getElementsByTagName('results')[0].textContent == '1') {
                    			this.lookupReference('addTenantForm').getForm().findField('outbound').setValue(response.getElementsByTagName('outbound')[0].textContent);
                    			this.lookupReference('addTenantForm').getForm().findField('inbound').setValue(response.getElementsByTagName('inbound')[0].textContent);
                    		}
                    	}, 
                    	failure: function(res) {
                    		Ext.Msg.alert(l10n('error'), l10n('internal.error'));
                    	}
                    });

                    if (tenantID == '0') {
                    	var addTenantAccordionItems = this.lookupReference('addTenantAccordionItems');
                        var newUser = addTenantAccordionItems.down('newuser') || Ext.widget('newuser');
                        addTenantAccordionItems.add(newUser);
                    }
                    if(records[0].get('showLogAggr') == 'false' || records[0].get('showLogAggr') == false){
                    	this.lookupReference('addTenantForm').getForm().findField('logAggregation').disable();
                         if (tenantID == '0') {
                             this.lookupReference('addTenantForm').getForm().findField('logAggregation').setValue(0);
                         }
                    }else{
                    	this.lookupReference('addTenantForm').getForm().findField('logAggregation').enable();
                    }
                      if(records[0].get('showCustomRole') == 'false' || records[0].get('showCustomRole') == false){
                    	this.lookupReference('addTenantForm').getForm().findField('customRole').disable();
                         if (tenantID == '0') {
                             this.lookupReference('addTenantForm').getForm().findField('customRole').setValue(0);
                         }
                    }else{
                    	this.lookupReference('addTenantForm').getForm().findField('customRole').enable();
                    }
                   
                }
            }
        });
    },

    loadManageTenantStore : function() {
         var manageTenantsStore = this.getViewModel().getStore("manageTenantsStore");
         manageTenantsStore.load();
    },

    afterManageTenantsRender : function() {
        var me = this;

        this.getViewModel().set('tenantsSortDataIndex', 'tenantName');
        this.getViewModel().set('tenantsSortDir', 'ASC');
        //setTimeout(function() {
            me.loadManageTenantStore();
        //}, 100);
    },

    afterCurrentCallsRender : function() {
        var me = this;

        this.getViewModel().set('currentCallsSortDataIndex', 'conferenceName');
        this.getViewModel().set('currentCallsSortDir', 'ASC');
        setTimeout(function() {
            me.getViewModel().storeInfo.currentCallsStore.load();
        }, 100);
    },

    beforeManageTenantsLoad : function(store, operation, eOpts) {
        var tenantNameFilterRef = this.lookupReference('tenantNameFilter') ? this.lookupReference('tenantNameFilter').getValue() : '';
        var tenantUrlFilterRef = this.lookupReference('tenantUrlFilter') ? this.lookupReference('tenantUrlFilter').getValue() : '';

        var proxy = store.getProxy();
        proxy.setExtraParam('tenantName', tenantNameFilterRef);
        proxy.setExtraParam('tenantURL', tenantUrlFilterRef);
        proxy.setExtraParam('sort', this.getViewModel().get('tenantsSortDataIndex'));
        proxy.setExtraParam('dir', this.getViewModel().get('tenantsSortDir'));
    },

    beforeCurrentCallsLoad : function(store, operation, eOpts) {
        var proxy = store.getProxy();
        proxy.setExtraParam('sort', this.getViewModel().get('currentCallsSortDataIndex'));
        proxy.setExtraParam('dir', this.getViewModel().get('currentCallsSortDir'));
    },

    onTenantsSortChange : function(ct, column, direction, eOpts) {
        this.getViewModel().set('tenantsSortDataIndex', column.dataIndex);
        this.getViewModel().set('tenantsSortDir', direction);
        this.loadManageTenantStore();
    },

    onCurrentCallsSortChange : function(ct, column, direction, eOpts) {
        this.getViewModel().set('currentCallsSortDataIndex', column.dataIndex);
        this.getViewModel().set('currentCallsSortDir', direction);
        this.getViewModel().storeInfo.currentCallsStore.load();
    },

    deleteTenants : function() {
        var tenantsGrid = this.lookupReference('tenantsGrid');
        var selectedTenants = tenantsGrid.getSelection();
        var deletedTenants = 0;
        if (selectedTenants.length) {
            Ext.Msg.show({
                title : l10n('confirmation'),
                message : l10n('do-you-really-want-to-delete-the-selected-tenants-all-members-and-public-room-will-be-deleted-also'),
                buttons : Ext.Msg.YESNO,
                fn : function(btn) {
                    if (btn == 'yes') {
                        for (var tenantCounter = 0; tenantCounter < selectedTenants.length; tenantCounter++) {
                            var thisSelectedTenant = selectedTenants[tenantCounter];
                            if (thisSelectedTenant.get("tenantID") == 1) {
                                deletedTenants++;
                            } else {
                                Ext.Ajax.request({
                                    url : 'deletetenant.ajax',
                                    params : {
                                        tenantID : thisSelectedTenant.get("tenantID")
                                    },
                                    success : function(response) {
                                        deletedTenants++;
                                        if (deletedTenants == selectedTenants.length) {
                                            tenantsGrid.getStore().load();
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });
        } else {
            Ext.Msg.alert(l10n('error'), l10n('please-select-at-least-one-tenant-to-delete'));
        }
    },

    remoteValidateTenantName : function(textfield, newValue, oldValue, eOpts) {
        this.remoteValidate(textfield, newValue, 'validatetenantname.ajax');
    },

    remoteValidateTenantURL : function(textfield, newValue, oldValue, eOpts) {
        this.remoteValidate(textfield, newValue, 'validatetenanturl.ajax');
    },

    remoteValidateTenantPrefix : function(textfield, newValue, oldValue, eOpts) {
        this.remoteValidate(textfield, newValue, 'validatetenantprefix.ajax');
    },

    remoteValidateTenantReplayURL : function(textfield, newValue, oldValue, eOpts) {
        this.remoteValidate(textfield, newValue, 'validatetenantreplayurl.ajax');
    },

    remoteValidateTenantWebRTCURL : function(textfield, newValue, oldValue, eOpts) {
        this.remoteValidate(textfield, newValue, 'validatetenantwebrtcurl.ajax');
    },

    remoteValidateGatewayController : function(textfield, newValue, oldValue, eOpts) {
        this.remoteValidate(textfield, newValue, 'validatevidyogatewaycontrollerdns.ajax');
    },

    remoteValidate : function(textfield, newValue, url) {
        Ext.Ajax.request({
            url : url,
            params : {
                field : textfield.getName(),
                value : newValue,
                tenantID : textfield.up('form').tenantConfig.get('tenantID')
            },
            method : 'GET',
            scope : textfield,
            success : function(response) {
                var responseText = Ext.decode(response.responseText);
                if (responseText.valid) {
                    this.textValid = true;
                    this.textInvalid = '';
                    this.validate();
                } else {
                    this.textValid = false;
                    this.textInvalid = responseText.reason;
                    this.validate();
                }
            },
            failure : function(response) {
                this.textValid = false;
                this.textInvalid = Ext.decode(response.responseText).reason;
                this.validate();
            }
        });
    },

    saveTenant : function(btn) {
        var me = this;
        this.onNewTenantCollapse(function() {
            var tenantForm = me.lookupReference('addTenantForm');
            var formValues = tenantForm.getForm().getValues();

            formValues['guestLogin'] = formValues['guestLoginEnable'] ? 1 : 0;
            delete formValues['guestLoginEnable'];

            formValues['inbound'] = formValues['inbound'] ? 1 : 0;
            //delete formValues['inbound'];

            formValues['outbound'] = formValues['outbound'] ? 1 : 0;
            //delete formValues['outboundIpc'];

            formValues['scheduledRoomEnabled'] = formValues['scheduledRoomEnable'] ? 1 : 0;
            delete formValues['scheduledRoomEnable'];
            
        
          
            
         
            formValues['mobileLogin'] = formValues['mobileLoginEnable'];

            var ddSelectedValues = me.getDDSelectedValues();
            formValues['services'] = ddSelectedValues;
            delete formValues['oldVidyoGatewayControllerDns'];

            var callTo = '';
            var callToStore = me.getViewModel().storeInfo.selectedTenants;
            var callRecords;
            if (callToStore) {
                callRecords = callToStore.getRange(0, callToStore.getCount());
            }
            for (var recordCounter = 0; recordCounter < callRecords.length; recordCounter++) {
                callTo += callRecords[recordCounter].get('tenantID') + ',';
            }
            callTo = callTo.slice(0, callTo.length - 1);
            formValues['callTo'] = callTo;

            var locations = '';
            var locationsStore = me.getViewModel().storeInfo.selectedLocations;
            var locationsRecords;
            if (locationsStore) {
                locationsRecords = locationsStore.getRange(0, locationsStore.getCount());
            }
            for (var recordCounter = 0; recordCounter < locationsRecords.length; recordCounter++) {
                locations += locationsRecords[recordCounter].get('locationID') + ',';
            }
            locations = locations.slice(0, locations.length - 1);
            formValues['locations'] = locations;

            delete formValues['locationHidden'];
            
            Ext.Ajax.request({
                url : 'savetenant.ajax',
                waitMsg : l10n('saving'),
                timeout : 180000,
                scope : me,
                params : formValues,
                success : function(result, options) {
                    if (result.responseXML.getElementsByTagName("message")[0].getAttribute('success') == "true") {
                        //this.lookupReference('tenantsNavGrid').getSelectionModel().select(0);
                        Ext.Msg.alert(l10n('success'), l10n('tenant-save-success'), function() {
                            me.onTenantsRender();
                        });
                    } else {
                        Ext.Msg.alert(l10n('error'), result.responseXML.getElementsByTagName("msg")[0].textContent);
                    }
                },
                failure : function(result, options) {
                    Ext.Msg.alert(l10n('error'), result.responseXML.getElementsByTagName("msg")[0].textContent);
                }
            });
        });
    },

    getDDSelectedValues : function() {
        var storeInfo = this.getViewModel().storeInfo,
            servicesString = '';

        var ddStores = [storeInfo.availableVMS, storeInfo.selectedVMS, storeInfo.selectedVPS, storeInfo.selectedVGS, storeInfo.selectedRecs, storeInfo.selectedReplays];

        for (var storeCounter = 0; storeCounter < ddStores.length; storeCounter++) {
            var thisStore = ddStores[storeCounter];
            var records;
            if (thisStore) {
                records = thisStore.getRange(0, thisStore.getCount());
            }
            for (var recordCounter = 0; recordCounter < records.length; recordCounter++) {
                servicesString += records[recordCounter].get('serviceID') + ',';
            }
        }

        servicesString = servicesString.slice(0, servicesString.length - 1);

        return servicesString;
    },

    onAddTenantRender : function(formPanel, eOpts) {
	if (formPanel.tenantConfig.get('tenantID') != '0') {
            var store = Ext.create('Ext.data.Store', {
                fields : [{
                    name : 'tenantID',
                    type : 'string'
                }, {
                    name : 'tenantName',
                    type : 'string'
                }, {
                    name : 'tenantURL',
                    type : 'string'
                }, {
                    name : 'tenantPrefix',
                    type : 'string'
                }, {
                    name : 'tenantDialIn',
                    type : 'string'
                }, {
                    name : 'tenantReplayURL',
                    type : 'string'
                }, {
                    name : 'tenantWebRTCURL',
                    type : 'string'
                }, {
                    name : 'description',
                    type : 'string'
                }, {
                    name : 'vidyoGatewayControllerDns',
                    type : 'string'
                }, {
                    name : 'oldVidyoGatewayControllerDns',
                    type : 'string'
                }, {
                    name : 'installs',
                    type : 'int'
                }, {
                    name : 'publicRooms',
                    type : 'int'
                },
                {
                    name : 'seats',
                    type : 'int'
                }, {
                    name : 'ports',
                    type : 'int'
                }, {
                    name : 'guestLogin',
                    type : 'int'
                }, {
                    name : 'guestLoginEnable',
                    type : 'boolean',
                    dateFormat : function(value) {
                        return (value == 1);
                    }
   				}, {
                    name : 'executives',
                    type : 'int'
                }, {
                    name : 'panoramas',
                    type : 'int'
                }, {
                    name : 'scheduledRoomEnabled',
                    type : 'int'
                },{
                    name : 'logAggregation',
                    type : 'int'
                },{
                    name : 'customRole',
                    type : 'int'
                }, {
                    name : 'scheduledRoomEnable',
                    type : 'boolean',
                    dateFormat : function(value) {
                        return (value == 1);
                    }
                }],
                proxy : {
                    type : 'ajax',
                    url : 'tenant.ajax?tenantID=' + formPanel.tenantConfig.get('tenantID'),
                    reader : {
                        type : 'xml',
                        record : 'row',
                        totalProperty : 'results',
                        rootProperty : 'dataset'
                    }
                }
            });

            store.load({
                callback : function(record) {
                    record[0].set('oldVidyoGatewayControllerDns', record[0].get('vidyoGatewayControllerDns'));
                    formPanel.getForm().loadRecord(record[0]);
                    if (Ext.isEmpty(formPanel.getForm().findField('tenantReplayURL').getValue())) {
                        formPanel.getForm().findField('tenantReplayURL').setValue(' ');
                        formPanel.getForm().findField('tenantReplayURL').setValue('');
                    }
                    if (formPanel.tenantConfig.get('showVidyoNeoWebRTC') == 'true' && Ext.isEmpty(formPanel.getForm().findField('tenantWebRTCURL').getValue())) {
                        formPanel.getForm().findField('tenantWebRTCURL').setValue(' ');
                        formPanel.getForm().findField('tenantWebRTCURL').setValue('');
                    }
                    if (Ext.isEmpty(formPanel.getForm().findField('vidyoGatewayControllerDns').getValue())) {
                        formPanel.getForm().findField('vidyoGatewayControllerDns').setValue(' ');
                        formPanel.getForm().findField('vidyoGatewayControllerDns').setValue('');
                    }
                    if (Ext.isEmpty(formPanel.getForm().findField('tenantPrefix').getValue())) {
                        formPanel.getForm().findField('tenantPrefix').setValue(' ');
                        formPanel.getForm().findField('tenantPrefix').setValue('');
                    }
                }
            });
        } else {
			formPanel.getForm().findField('tenantReplayURL').setValue(' ');
            formPanel.getForm().findField('tenantReplayURL').setValue('');
            if (formPanel.tenantConfig.get('showVidyoNeoWebRTC') == 'true') {
	            formPanel.getForm().findField('tenantWebRTCURL').setValue(' ');
	            formPanel.getForm().findField('tenantWebRTCURL').setValue('');
            }
            formPanel.getForm().findField('vidyoGatewayControllerDns').setValue(' ');
            formPanel.getForm().findField('vidyoGatewayControllerDns').setValue('');
        }
      
        Ext.Ajax.request({
            url : 'scheduledroom.ajax',
            
            success : function(result, options) {
         
            var response = result.responseXML.documentElement;
    		if(response.getElementsByTagName('results')[0].textContent == '1') {
    			var isglobalscheduleroomenabled=response.getElementsByTagName('isglobalscheduleroomenabled')[0].textContent;
    			  if(isglobalscheduleroomenabled=='false'){
    				  if(formPanel.tenantConfig.get('tenantID') == '0'){
    					  formPanel.getForm().findField('scheduledRoomEnable').setValue('0');
    				  }
    		        	formPanel.getForm().findField('scheduledRoomEnable').disable();
    		        }
    				        		}},
            failure : function(result, options) {
            	
            }
        });
        
        
    },

    onManageTenantsRowClick : function(grid, record, tr, rowIndex, e, eOpts) {
        if (e.target.className == "selectable-tenant") {
            var tenantCenterContainer = this.lookupReference('tenantCenterContainer');
            
           this.showAddTenantView(tenantCenterContainer, record.get('tenantID'));
            //this.lookupReference('tenantsNavGrid').getSelectionModel().select(1);
        } else if (e.target.className == "selectable-tenant-url") {
            window.open(window.location.protocol + "//" + record.get('tenantURL') + "/admin", '_blank');
        }
    },

    onVidyoReplayCompCollapse : function(panel, event, eOpts) {
        if (panel.getSelectedGrid().getStore().getCount() > 1) {
            Ext.Msg.alert(l10n('error'), l10n('you-can-select-only-one-vidyoreplay-component-for-tenant'), function() {
                panel.expand();
            });
        }
    },

    onNewTenantCollapse : function(callback) {
        var formPanel = this.lookupReference('addTenantForm');
        var formData = formPanel.getForm().getValues();
        if (formPanel.tenantConfig.get('tenantID') != 0 && formData.vidyoGatewayControllerDns != formData.oldVidyoGatewayControllerDns) {
            Ext.Msg.alert(l10n('error'), l10n('existing-invitations-may-not-work'), function() {
                if ( typeof callback == "function") {
                    callback();
                }
            });
        } else if ( typeof callback == "function") {
            callback();
        }
    },

    /***
     * @Function : beforeDropLocations
     * @param : node
     * @param : data
     * @param : dropRec
     * @param : dropPosition
     * @desc : To restrict the default record from moving to available grid.
     */
    beforeDropLocations : function(node, data, dropRec, dropPosition) {
        if(data.records[0].get('locationID') == 1) {
            Ext.Msg.alert(l10n('message'),l10n('default-location-tag-is-required-for-each-tenant'));
            return false;
        }
    },

    ddLocations: function(node, data, overModel, dropPosition, eOpts) {
    	var locationsStore = this.getViewModel().storeInfo.selectedLocations;
    	var tenantForm = this.lookupReference('addTenantForm');
    	tenantForm.getForm().setValues({'locationHidden': locationsStore.getCount() > 0 ? 'LocationsExist' : ''});
    },
    
    onResetTenant: function(btn, e, eOpts) {
    	var tenantID = btn.up('form').getForm().getValues()['tenantID'];
    	var tenantCenterContainer = this.lookupReference('tenantCenterContainer');
        tenantCenterContainer.remove(tenantCenterContainer.down('addTenant'), true);
        this.showAddTenantView(tenantCenterContainer, tenantID);
    },
    
    addToHistory : function(newToken) {
       /* var recordList = this.lookupReference('tenantsNavGrid').getSelectionModel().getSelection(),
            recId = recordList.length ? recordList[0].get('recordId') : '';

        newToken += ":" + this.lookupReference('tenantsNavGrid').getId() + ":" + recId;
        this.getView().lookupController(true).getViewModel().set('callItemClick', false);
        Ext.History.add(newToken);*/
    }
});

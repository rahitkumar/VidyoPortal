Ext.define('SuperApp.view.tenants.AddTenant', {
    extend : 'Ext.form.Panel',
    xtype : 'addTenant',
    reference : 'addTenantForm',
    cls : 'add-tenant-form',
    
    border : 0,
    
    requires : ['SuperApp.view.tenants.NewTenantForm', 'SuperApp.view.tenants.DDTenantDetail'],

    reader : Ext.create('Ext.data.reader.Xml', {
        record : 'row',
        rootProperty : 'dataset',
        successProperty : '@success',
        model : Ext.data.Model.create([{
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
            name : 'seats',
            type : 'int'
        }, {
            name : 'publicRooms',
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
        }, {
            name : 'logAggregation',
            type : 'int'
        }, {
            name : 'scheduledRoomEnable',
            type : 'boolean',
            dateFormat : function(value) {
                return (value == 1);
            }
        }])
    }),

    errorReader : new Ext.data.XmlReader({
        successProperty : '@success',
        record : 'field'
    }, ['id', 'msg']),
    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    width : '100%',
    
    buttonAlign : 'center',
    initComponent: function() {
        var me = this;

        var innerItems = [{
            title : '<span class="red-label">*</span>'+ l10n('tenant-s-settings'),
            tenantConfig : me.tenantConfig ? me.tenantConfig : '0',
            xtype : 'newTenant',
            listeners : {
                collapse : 'onNewTenantCollapse'
            }
        }, {
            title : l10n('can-make-call-to'),
            xtype : 'ddtenantdetail',
            reference : 'addTenantName',
            availableGridTitle : l10n('available-tenants'),
            selectedGridTitle : l10n('selected-tenants'),
            availableGridStore : 'availableTenants',
            selectedGridStore : 'selectedTenants',
            availableDDGroup : 'availableTenantsGrid',
            selectedDDGroup : 'selectedTenantsGrid',
            displayFieldName : 'tenantName'
        }, {
            title : l10n('vidyoproxy-component-s'),
            xtype : 'ddtenantdetail',
            availableGridTitle : l10n('available-vidyoproxy-component-s'),
            selectedGridTitle : l10n('selected-vidyoproxy-component-s'),
            availableGridStore : 'availableVPS',
            selectedGridStore : 'selectedVPS',
            availableDDGroup : 'availableVPSGrid',
            selectedDDGroup : 'selectedVPSGrid',
            displayFieldName : 'serviceName'
        }, {
            title : l10n('available-vidyogateway-component-s'),
            xtype : 'ddtenantdetail',
            availableGridTitle : l10n('available-vidyogateway-component-s'),
            selectedGridTitle : l10n('selected-vidyogateway-component-s'),
            availableGridStore : 'availableVGS',
            selectedGridStore : 'selectedVGS',
            availableDDGroup : 'availableVGSGrid',
            selectedDDGroup : 'selectedVGSGrid',
            displayFieldName : 'serviceName'
        }];

        if (me.tenantConfig.get('showVidyoReplay') == "true") {

            innerItems.push({
                title : l10n('vidyoreplay-recorder-s'),
                xtype : 'ddtenantdetail',
                availableGridTitle : l10n('available-vidyoreplay-recorder-s'),
                selectedGridTitle : l10n('selected-vidyoreplay-recorder-s'),
                availableGridStore : 'availableRecs',
                selectedGridStore : 'selectedRecs',
                availableDDGroup : 'availableRecsGrid',
                selectedDDGroup : 'selectedRecsGrid',
                displayFieldName : 'serviceName'
            }, {
                title : l10n('vidyoreplay-component-s'),
                xtype : 'ddtenantdetail',
                availableGridTitle : l10n('available-vidyoreplay-component-s'),
                selectedGridTitle : l10n('selected-vidyoreplay-component-s'),
                availableGridStore : 'availableReplays',
                selectedGridStore : 'selectedReplays',
                availableDDGroup : 'availableReplaysGrid',
                selectedDDGroup : 'selectedReplaysGrid',
                displayFieldName : 'serviceName',
                listeners : {
                    collapse : 'onVidyoReplayCompCollapse'
                }
            });
        }
        innerItems.push({
            title : l10n('location-tag-s'),
            xtype : 'ddtenantdetail',
            availableGridTitle : l10n('available-location-tag-s'),
            selectedGridTitle : l10n('selected-location-tag-s'),
            availableGridStore : 'availableLocations',
            selectedGridStore : 'selectedLocations',
            availableDDGroup : 'availableLocationsGrid',
            selectedDDGroup : 'selectedLocationsGrid',
            displayFieldName : 'locationTag',
            dDListeners: {
            	drop: 'ddLocations',
            	beforedrop : 'beforeDropLocations'
            }
        });

        me.items = [{
        	xtype: 'textfield',
        	hidden: true,
        	value: '1',
        	name: 'locationHidden',
        	allowBlank: false
        }, {
            xtype : 'container',
            reference : 'addTenantAccordionItems',
            flex : 1,
            margin : 0,
            padding : 0,
            layout : {
                type : 'accordion',
                titleCollapse : true,
                animate : true
            },
            items : innerItems
        }];
        
        me.buttons = [{
            xtype : 'button',
            formBind : true,
            text : l10n('save'),
            handler : 'saveTenant',
            maxWidth : 110
        },{
            xtype : 'button',
            text : l10n('cancel'),
            margin: '0 0 0 20',
            listeners : {
                click : 'onResetTenant'
            }
        }];
    
        me.callParent();
    }
});

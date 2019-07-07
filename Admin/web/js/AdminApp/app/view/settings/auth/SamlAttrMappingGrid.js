/**
 * @class SamlAttrMappingGrid
 */
Ext.define('AdminApp.view.settings.auth.SamlAttrMappingGrid', {
    extend : 'Ext.window.Window',
    alias : 'widget.samlattrmappinggrid',
    reference: 'samlattrmappinggrid',
    modal : true,
	resizable: false,
	width: 400,
	autoHeight: true,
	frame: true,
    border : false,
    record : null,

    listeners: {
    	render: 'onSamlAttrMappingGridRender'
    },
    
    viewModel : {
        type : 'AuthenticationViewModel'
    },

    controller : 'AuthenticationViewController',
    title: l10n('attribute-values-mapping'),
    closeAction:'destroy',
    initComponent : function() {
        var me = this;

        var items = [{
        	xtype: 'toolbar',
        	docked: 'top',
        	items: [{
        		xtype: 'button',
        		text: l10n('dublicate'),
        		iconCls: 'icon-add',
        		reference: 'dublicateAttrBtn',
        		disabled: true,
        		handler: 'duplicateSamlAttrMapping'
        	}, {
        		xtype: 'button',
        		text: l10n('remove'),
        		iconCls: 'icon-remove',
        		reference: 'removeAttrBtn',
        		disabled: true,
        		handler: 'removeSamlAttrMapping'
        	}]
        }, {
        	xtype: 'grid',
        	reference: 'samlAttrMappingGrid',
        	bind: {
        		store: '{samlAttrMappingGridStore}'
        	},
        	plugins: {
      	        ptype: 'cellediting',
      	        clicksToEdit: 2
		    },
        	columns: [{
    			id: 'valueID',
    			text: 'ID',
    			dataIndex: 'valueID',
    			hidden: true
    		},{
    			id: 'mappingID',
    			text: 'mappingID',
    			dataIndex: 'mappingID',
    			hidden: true
    		},{
    			text: l10n('portal-attribute-value'),
    			dataIndex: 'vidyoValueName',
    			flex: 1,
    			sortable: false,
    			menuDisabled: true,
    			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                	return Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(value));
                }
    		},{
    			header: l10n('idp-attribute-value'),
                dataIndex: 'idpValueName',
    			flex: 1,
    			sortable: false,
    			menuDisabled: true,
    			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
    				var scope = me;
    				var idpAttributeValue = scope.lookupReference("samlAttrMappingGrid").columns[3];
                    var idpAttribute = Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(record.get('idpValueName')));
                    idpAttributeValue.setEditor({
                    	xtype: 'textfield',
                        allowBlank: true,
                        maxLength: 1024,
                        value: idpAttribute 
                    });
                    
                	return Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(value));
    			}
    		}],
    		listeners: {
    			cellclick: 'onSamlAttrMappingGridCellClick'
    		}
        }, {
        	xtype: 'toolbar',
        	docked: 'bottom',
        	items: [{
            	xtype: 'tbfill'
            }, {
        		xtype: 'button',
        		align: 'center',
        		text: l10n('save'),
        		handler: 'saveSamlAttrMapping'
        	}, {
        		xtype: 'button',
        		align: 'center',
        		text: l10n('cancel'),
        		handler: 'cancelSamlAttrMapping'
        	}, {
            	xtype: 'tbfill'
            }]
        }];
        
        me.items = items;
        
        me.callParent(arguments);
    }
});

Ext.define('SuperApp.view.cloud.poolList.PriorityDetail', {
    extend: 'Ext.form.Panel',
    
    xtype: 'priorityDetail',
    
    requires : ['Ext.container.Container'],

	reference: 'priorityDetailPanel',	
	 cls : 'pools-grid',
    border : 1,
    
    initComponent: function() {
    	
    	var topToolbar = Ext.create('Ext.toolbar.Toolbar', {
    	    ui : 'footer',
        	//cls: 'white-header-footer',
        	items: [{
	    		xtype: 'button',
	    		iconCls: 'back-button',
	    		icon : 'js/SuperApp/resources/images/control_rewind.png',
	    		handler: 'backToPriorityLists',
	    		tooltip: l10n('back-to-priority-list')
	    	}, {
        		xtype: 'button',
        		text: l10n('save-priority-list'),
        		iconCls: 'x-fa fa-edit',
        		handler: 'savePoolsToPriorityList',
        		formBind:true,
        		bind: {
        			hidden: '{!isModified}'
        		}
	    	}]
	        
    	});
    	
    	var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
	        clicksToMoveEditor: 1,
	        errorSummary : false,
	        autoCancel: false
	    });
    	
    	var gridPanelRelated = Ext.create('Ext.grid.Panel', {
    		title: l10n('associated-pools'),
           // cls : 'white-header-footer',
    		bind: {
    			store: '{assignedRouterPoolsStore}'
    		},
    		flex: 1,
            margin : 5,
	    	multiSelect: true,
    		reference: 'relatedPriorityDetailsGrid',
    		plugins: [rowEditing],
    		viewConfig: {
    			plugins:  {
	    			ptype: 'gridviewdragdrop',
	                dragGroup: 'group1',
	                dropGroup: 'group2'
	            },
                listeners : {
                    drop : 'onPoolDrop'
                }
    		},
            columns: [{
    			text: l10n('priority'),
    			flex: 1,
    			dataIndex: 'order',
    			reference: 'maxPriorityValue',
    			editor: {
    				xtype: 'numberfield', 
    				allowExponential: false,
	                allowBlank: false,
	                minValue: 1,
	                bind: {
	                	maxValue: '{maxPriorityValue}'
	                }
	            }
    		}, {
    			dataIndex: 'name',
    			text: l10n('pool-name'),
    			flex: 3
    		}],
    		
        	listeners : {
                edit : 'onPriorityEdit'
            }
    	});
    	
    	var gridPanelFree = Ext.create('Ext.grid.Panel', {
    		title: l10n('available-pools'),
            margin : 5,
           // cls : 'white-header-footer',
    		bind: {
    			store: '{availableRouterPoolsStore}'
    		},
    		reference: 'freePriorityDetailsGrid',
    		viewConfig: {
    			plugins: {
	    			ptype: 'gridviewdragdrop',
	                dragGroup: 'group2',
	                dropGroup: 'group1'
	    		},
                listeners : {
                    drop : 'onPoolRemove'
                }
	    	},
    		flex: 1,
	    	multiSelect: true,
            columns: [{
    			dataIndex: 'name',
    			text: l10n('pool-name'),
    			flex: 3
    		}]
    	});
    	
    	this.items = [topToolbar, {
    		xtype: 'hidden',
    		name: 'id'
    	}, {
    		xtype: 'textfield',
    		name: 'priorityListName',
    		fieldLabel: l10n('priority-list-name'),
            labelWidth : 110,
            maxLength:128,
            margin : 5,
            allowBlank:false,
			vtype: 'nohtml',
            msgTarget : 'under',
            width : 300
    	}, {
    		xtype: 'container',
	    	flex: 1,
            margin : 0,
	    	layout: {
		        type: 'hbox',
		        align: 'stretch'
		    },
            items : [gridPanelFree, gridPanelRelated]
    	}];
    	
    	this.callParent();
    }
});

Ext.define('SuperApp.view.tenants.DDTenantDetail', {
    extend : 'Ext.panel.Panel',
    xtype : 'ddtenantdetail',
    height : 350,
    config : {
        availableGridTitle : '',
        selectedGridTitle : '',
        availableDDGroup : '',
        selectedDDGroup : '',
        displayFieldName : '',
        selectedGrid : '',
        availableGrid : '',
        dDListeners : {}
    },

    layout : {
        type : 'hbox',
        align : 'stretch'
    },
    items : [],
    initComponent : function() {
        var me = this;

        var availableGrid = Ext.create('Ext.grid.Panel', {
            title : me.getAvailableGridTitle(),
            reference : me.getAvailableDDGroup(),
            height : 300,
            border : false,
            multiSelect: true,
            hideHeaders : true,
            flex : 1,
            columns : [{
                dataIndex : me.getDisplayFieldName(),
                flex : 1,
                renderer: Ext.String.htmlEncode
            }],
            viewConfig : {
                plugins : {
                    ptype : 'gridviewdragdrop',
                    dragGroup : me.getAvailableDDGroup(),
                    dropGroup : me.getSelectedDDGroup()
                },
                listeners : me.getDDListeners()
            }
        });

        var selectedGrid = Ext.create('Ext.grid.Panel', {
            title : me.getSelectedGridTitle(),
            reference : me.getSelectedDDGroup(),
            height : 300,
            border : false,
            hideHeaders : true,
            multiSelect: true,
            flex : 1,
            columns : [{
                dataIndex : me.getDisplayFieldName(),
                flex : 1,
                renderer: Ext.String.htmlEncode
            }],
            viewConfig : {
                plugins : {
                    ptype : 'gridviewdragdrop',
                    dragGroup : me.getSelectedDDGroup(),
                    dropGroup : me.getAvailableDDGroup()
                },
                listeners : me.getDDListeners()
            }
        });

        var centerLine = Ext.create('Ext.panel.Panel', {
            width : 0,
            border : '0 1 0 0',
            cls : 'tenants-center-line',
            items : []
        });

        this.setAvailableGrid(availableGrid);
        this.setSelectedGrid(selectedGrid);

        this.items = [availableGrid, centerLine, selectedGrid];

        this.callParent();
    }
}); 
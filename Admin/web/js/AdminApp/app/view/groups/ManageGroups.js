Ext.define('AdminApp.view.groups.ManageGroups', {
    extend : 'Ext.panel.Panel',

    layout : {
        type : 'vbox',
        align : 'stretch'
    },
 
    border : false,
    alias : 'widget.managegroupsview',
    initComponent : function() {
        var topToolbar;

        topToolbar = {
            xtype : 'toolbar',
            docked : 'top',
            reference : 'topToolbar',
           width : '100%',
              height:60,
            layout : {
                type : 'hbox',
                align : 'center'
              
            },
             defaults : {
              
                 labelWidth : 40,
                bodyPadding: 10
            },
            items : [{
                xtype : 'textfield',
                name : 'groupName',
                labelWidth : 120,
                fieldLabel : l10n('group-name'),
                reference : 'groupNameFilter',
                labelAlign : 'left',
                enableKeyEvents : true,
                listeners : {
                    change : 'groupNameFilterLoad'
                }
            }]
        };

        this.items = [{
                xtype : 'grid',
                reference : 'manageGroupsGrid',
                width : '100%',
                border : false,
               // forceFit:true,
               minHeight : 800,
                selModel : Ext.create('Ext.selection.CheckboxModel', {
                    injectCheckbox : 'first',
                    showHeaderCheckbox : true,
                    checkOnly : true,
                    mode : 'SIMPLE',
                    renderer : function(value, metaData, record) {
                        if (record.data.defaultFlag != 1) {
                            return '<div class="' + Ext.baseCSSPrefix + 'grid-row-checker">&#160;</div>';
                        } else {
                            return '';
                        }
                    }
                }),
                columns : [{
                    text : l10n('group-name'),
                    dataIndex : 'groupName',
                   // width : '23.35%',
                    flex:1,
                    tdCls : 'columnwithclickevent',
                    renderer : function(value) {
                        value=Ext.util.Format.htmlEncode(value);
                        return "<span class='group-name'>" + value + "</span>";
                    }
                }, {
                    text : l10n('max-participants'),
                    dataIndex : 'roomMaxUsers',
                  //  width : '23.35%'
                    flex:1
                }, {
                    text : l10n('max-bandwidth-out'),
                    dataIndex : 'userMaxBandWidthOut',
                   // width : '24.25%'
                    flex:1
                }, {
                    text : l10n('max-bandwidth-in'),
                    dataIndex : 'userMaxBandWidthIn',
                    //width : '24.25%'
                    flex:1
                }],
                bind : {
                    store : '{manageGroupsGridStore}'
                },
                loadMask : true,
                tbar : topToolbar,
                dockedItems : [{
                    xtype : 'pagingtoolbar',
                    bind : {
                        store : '{manageGroupsGridStore}'
                    },
                  //  cls : 'white-header-footer',
                    dock : 'bottom',
                    displayInfo : true,
                    displayMsg: l10n('displaying-groups-0-1-of-2'),
            		emptyMsg: l10n('no-groups-to-display'),
                    border : '0 0 0 1',
                    items : ['-', {
                        xtype : 'button',
                        text : l10n('delete'),
                        iconCls : 'x-fa fa-minus-circle',
                        handler : 'manageGroupsDeletRecord'
                    },{
                    	text:l10n('add-group'),
                    	iconCls : 'x-fa fa-plus-circle',
                    	handler:'addGroupView'
                    }]
                }],
                listeners : {
                    render : 'manageGroupsGridRender',
                    rowclick : 'onManageGroupsRowClick',
                    sortchange : 'onGroupsSortChange'
                }
            
        }];

        this.callParent();
    }
});

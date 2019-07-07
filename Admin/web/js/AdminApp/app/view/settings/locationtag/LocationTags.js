/**
 * @class LocationTags
 */
Ext.define('AdminApp.view.settings.locationtag.LocationTags', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.locationtags',

    requires : ['AdminApp.view.settings.locationtag.LocationTagsViewModel', 'AdminApp.view.settings.locationtag.LocationTagsController'],

    viewModel : {
        type : 'LocationTagsViewModel'
    },
    title : {
        text : '<span class="header-title">' + l10n('location-tags') + '</span>',
        textAlign : 'center'
    },
    controller : 'LocationTagsController',

    layout : {
        type : 'card',
        align : 'stretch'
    },

    border : false,
    height:800,
             
   
    

    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'form',
            border : false,
            height:700,
            reference : 'locationtagform',
            items:[{
                xtype : 'fieldset',
                height : '100%',

                layout : {
                type : 'vbox',
                align : 'center',
                pack : 'center',
                },
            
                items : [{
                    xtype : 'combo',
                    fieldLabel : '<span class="red-label">*</span>'+l10n('default-location-tag'),
                    labelWidth : 250,
                    reference : 'locationcombo',
                    name : 'locationID',
                    margin : 5,
                    valueField : 'locationID',
                    displayField : 'locationTag',
                    bind : {
                     store : '{locationtagStore}'
                    },
                    labelWidth : 200,
                    typeAhead : false,
                    loadingText : l10n('searching'),
                    triggerAction : 'all',
                    emptyText : l10n('select-a-default-location-tag'),
                    allowBlank : true,
                    editable : false,
                    resizable : false,
                    width : 500
                }]
            }],
            buttonAlign : 'center',
            buttons : [{
                text : l10n('advanced'),
                listeners : {
                    click : 'onClickAdvanceLocationTag'
                }
            }, {
                text : l10n('save'),
                listeners : {
                	click : 'onClickSaveLocationTags'
                }
            },{
                text : l10n('cancel'),
                listeners : {
                    click : 'getLocationTagData'
                }
            }]
            
        }, {
            xtype : 'form',
            border : false,
            reference : 'locationadvanceform',
            items : [{
                html : l10n('select-a-location-tag-on-the-left-group-s-on-the-right-then-click-assign'),
                margin : 5,
                border : false
            },{
                xtype :'panel',
                border : false,
                layout : {
                    type :'hbox',
                    align : 'stretch'
                },
                items : [{
                    xtype : 'fieldset',
                    margin : 5,
                    padding : 0,
                    flex :1,
                    title : l10n('available-location-tags'),
                    items :[{
                        xtype : 'grid',
                        hideHeaders: true,
                        border :false,
                        minHeight : 200,
                        mode : 'SINGLE',
                        selModel : {
                            allowDeselect : true,
                            mode : 'SINGLE'
                        },
                        reference : 'locationgrid',
                        bind : {
                            store : '{locationtagStore}'
                        },
                        columns : [{
                            dataIndex : 'locationTag',
                            flex : 1
                        }]
                    }]
                },{
                    xtype : 'fieldset',
                    margin : 5,
                    padding : 0,
                    flex :1,
                    title : l10n('available-groups'),
                    items :[{
                        xtype : 'grid',
                        hideHeaders: true,
                        reference : 'availgroupgrid',
                        border :false,
                        minHeight : 200,
                        selModel : {
                            allowDeselect : true,
                            mode : 'SIMPLE'
                        },
                        bind : {
                            store : '{groupStore}'
                        },
                        columns : [{
                            dataIndex : 'groupName',
                            flex : 1
                        }]
                    }]
                }]
            },{
                    xtype : 'toolbar',
                    border : false,
                    items : ['->','->','->','->',{
                        xtype : 'checkbox',
                        boxLabel : l10n('select-all-groups'),
                        reference : 'selectallgroups',
                        listeners : {
                            change : 'onChangeSelectAllGroup'
                        }
                    },'->','->','->'] 
                }],
            buttons : [{
                text : l10n('assign'),
                listeners : {
                    click : 'onClickAssignLocationGroup'
                }
            }, '-', {
                text : l10n('cancel'),
                listeners : {
                    click : 'onClickAdvanceCancel'
                }
            }],
            buttonAlign : 'center'
        }];

        me.callParent(arguments);
    }
});

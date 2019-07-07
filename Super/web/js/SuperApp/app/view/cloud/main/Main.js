Ext.define('SuperApp.view.cloud.main.Main', {
    extend : 'Ext.container.Container',
    alias : 'widget.cloudmain',
    requires : ['SuperApp.view.cloud.main.MainController', 'SuperApp.view.cloud.endPointRules.EndPointRule', 'SuperApp.view.cloud.poolList.PriorityLists', 'SuperApp.view.cloud.locationTags.Location', 'SuperApp.view.cloud.interpoolPreference.Interpool', 'SuperApp.view.cloud.main.MainModel'],
    controller : 'cloudMain',

    viewModel : {
        type : 'cloudMain'
    },

    config : {
        topToolbar : null,
        tabPanel : null
    },

    layout : {
        type : 'vbox',
        align : 'stretch'
    },
       border: false,
    frame: false,
    items : [],
    
    listeners : {
        afterrender : 'onRenderCloudMain'
    },

    initComponent : function() {
        var topToolbar,
            tabPanel;

        this.setTopToolbar(topToolbar);
        this.setTabPanel(tabPanel);

        topToolbar = Ext.create('Ext.container.Container', {
            docked : 'top',
            cls : 'cloud-tab-panel',
            padding : 10,
            layout : {
                type : 'hbox'
            },
            items : [{
                xtype : 'statusbar',
                border : 0,
                margin : 5,
                padding : 0,
                reference: 'modifiedAvailable',
                text : '<div style="color:red;font-weight:bold;text-align:center"> **** ' + l10n('routerpools-activate-routerpools') + ' **** </div>',
                hidden: true
            }, {
                xtype : 'tbfill'
            }, {
                xtype : 'radiofield',
                fieldLabel : l10n('modified'),
                labelWidth : 60,
                margin : '0 20 0 0',
                name : 'active_modified',
                reference: 'radioFieldModified',
                listeners : {
                    change : 'onModifiedClick'
                }
            }, {
                xtype : 'radiofield',
                fieldLabel : l10n('active'),
                labelWidth : 45,
                value : true,
                name : 'active_modified',
                reference: 'radioFieldActive',
                listeners : {
                    change : 'onActiveClick'
                }
            }, {
                xtype : 'label',
                reference : 'versionLabel',
                width : 50,
                html : ''
            }]

        });

        tabPanel = Ext.create('Ext.tab.Panel', {
            reference : 'cloudTabPanel',
            tabPosition : 'left',
            cls : 'sub-tab-panel',
            tabRotation : 0,
            items : [{
                title : l10n('pools'),
                xtype : 'interpool',
                textAlign: 'left',
                width : 183
            }, {
                title : l10n('location-tags'),
                xtype : 'location',
                textAlign: 'left',
                width : 183
            }, {
                title : l10n('priority-list'),
                xtype : 'priorityLists',
                textAlign: 'left',
                width : 183
            }, {
                title : l10n('endpoint-rules'),
                xtype : 'endPointRule',
                textAlign: 'left',
                width : 183
            }],
            listeners : {
                tabchange: 'onPoolsTabChange',
                afterrender : 'onPoolsTabRender'
            }
        });

        this.items = [topToolbar, tabPanel];

        this.callParent();
    }
});

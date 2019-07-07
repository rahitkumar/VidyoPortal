Ext.define('SuperApp.view.settings.globalsettings.SearchOptions', {
    extend : 'Ext.form.Panel',
    alias : 'widget.searchoptions',
    requires : ['SuperApp.view.settings.globalsettings.GlobalFeatureSettingsController', 'SuperApp.view.settings.globalsettings.GlobalFeatureSettingsModel'],
    controller : "GlobalFeatureSettingsController",
    viewModel : {
        type : 'GlobalFeatureSettingsModel'
    },
    title : {
        text : '<span class="header-title">'+l10n('search-options')+'</span>',
        textAlign : 'center'
    },
    buttonAlign : 'center',
    height : '100%',
    trackResetOnLoad: true,
    initComponent : function() {
        var me = this;
	    me.items = [{
            xtype : 'fieldset',
            width : '100%',
    		height : '100%',
    		bodyStyle : 'padding: 10px',
    		layout : {
    			type : 'vbox',
    			align : 'center',
    			pack : 'center',
    		},
            items :[{
                xtype : 'checkbox',	                    
                name : 'showDisabledRoomsEnabled',
	            fieldLabel : l10n('show-disabled-rooms-in-search-results'),
                padding : '20px',
                labelWidth: 300,
                width: 600
            	}]
	    	}
		];
    	me.buttons = [{
                text : l10n('save'),
                listeners : {
                    click : 'onClickSearchOptionsSave'
                },
                formBind: true,
                disabled : true
            },
            {
                text : l10n('cancel'),
                listeners : {
                    click : 'resetSearchOptForm'
                }
            }];
        	me.callParent(arguments);
    	}
});

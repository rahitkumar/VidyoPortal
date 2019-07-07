Ext.define('SuperApp.view.settings.security.SecurityApplications', {
    extend : 'Ext.grid.Panel',
    alias : 'widget.securityapplication',
    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    width : '100%',
    bind : {
        store : '{applicationStore}',
        title : '<span class="header-title">{securityTitle}</span>'
    },
    title : {
    	titleAlign : 'center',
    },
    flex : 1,

    selType : 'cellmodel',

    reference : 'applicatoingrid',

    plugins : [Ext.create('Ext.grid.plugin.CellEditing', {
        clicksToEdit : 1
    })],

    columns : [{
        text : l10n('super-security-applications-tab'),
        dataIndex : 'appName',
        flex : 1
    }, {
        text : l10n('network-interface'),
        dataIndex : 'networkInterface',
        flex : 1,
        editor : {
            xtype : 'combo',
            displayField : 'interfaceName',
            valueField : 'id',
            bind : {
                store : '{networkInterfaceStore}'
            },
            allowBlank : false,
            triggerAction : 'all',
            mode : 'local',
            lazyRender : true,
            listClass : 'x-combo-list-small',
            editable : false
        }
    }, {
        text : l10n('http'),
        dataIndex : 'unsecurePort',
        flex : 1,
        editor : {
            xtype : 'numberfield',
            allowBlank : false,
            listeners : {
                change : 'onChangePortInGrid'
            }
        }
    }, {
        text : l10n('https'),
        dataIndex : 'securePort',
        flex : 1,
        editor : {
            xtype : 'numberfield',
            allowBlank : false,
            listeners : {
                change : 'onChangePortInGrid'
            }
        }
    }, {
        xtype : 'checkcolumn',
        text : l10n('ocsp'),
        dataIndex : 'ocsp',
        flex : 1,
        listeners : {
            checkchange : 'onCheckChangeApplicationsGrid'
        }
    }],
    buttonAlign : 'center',
    buttons : [{
        text : l10n('save'),
	        listeners : {
	            click : 'onClickSaveApplicationsGrid'
	        }
    	},{
		    text : l10n('cancel'),
			    listeners : {
			        click : 'onClickResetSecurityApplications'
			    }
    	}],

    listeners : {
        afterrender : function(gv) {
            var store = Ext.getStore("applicationstore");
            store.load();
            this.reconfigure();
        }
    }

});

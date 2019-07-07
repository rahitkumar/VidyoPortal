Ext.define('SuperApp.view.settings.maintenance.SystemUpgrade', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.systemupgrade',
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    autoDestroy : true,
    width : '100%',
    frame : true,
    border : false,
    initComponent : function() {
        var me = this,
        installPatchesGrid;

        installPatchesGrid = {
            xtype : 'grid',
            width : '100%',
            title : {
                title : '<span class="header-title">'+l10n('manitenance-installed-patches')+'</h3>',
                textAlign : 'center'
            },
            border : 0,
            style : {
                border : '0 0 0 0'
            },
            bind : {
                store : '{installPatchesGridStore}'
            },
            border : true,
            columns : [{
                text : l10n('patch-name'),
                dataIndex : 'patch',
                flex : 1
            }, {
                text : l10n('creation-date'),
                dataIndex : 'timestamp',
                flex : 1
            }],
            dockedItems: [{
                xtype: 'toolbar',
                dock : 'top',
                items : [{
					xtype: 'button',
				    text : l10n('AdminLicense-refresh'),
				    iconCls: 'icon-refresh',
				    handler : 'getSystemUpgradeData',
				    tooltip : l10n('AdminLicense-refresh')
				}]                
            }]
        };

        me.items = [
                 {
                    xtype : 'form',
                    title: {
                        text: '<span class="header-title">'+l10n('system-upgrade')+'</span>',
                        textAlign :'center'
                    },
                    frame : false,
                    border : false,
                    bodyPadding: 15,
                    layout : {
                        type : 'vbox',
                        align : 'center'
                    },
                    errorReader : Ext.create('Ext.data.XmlReader', {
                        record : 'field',
                        model : Ext.create("SuperApp.model.settings.Field"),
                        success : '@success'
                    }),
                    defaults : {
                        anchor : '95%',
                        allowBlank : false,
                        msgTarget : 'side'
                    },
                    items : [{
                        xtype : 'filefield',
                        name : '',
                        msgTarget : 'side',
                        allowBlank : false,
                        labelWidth : 200,
                        width : 500,
                        name : 'PORTALarchive',
                        reference : 'upgradeFileName',
                        emptyText : l10n('select-a-vidyo-file'),
                        labelAlign : 'right',
                        buttonText : 'Browse...'
                    }, {
                        xtype : 'hidden',
                        name : csrfFormParamName,
                        value : csrfToken
                    }],
                    buttonAlign : 'center',
                    buttons : [{
                        text : l10n('upload'),
                        handler : 'sysUpgradeUpload',
                        formBind : true,
                        disabled : true
                    }]
                },
                {
                xtype : 'fieldset',
                border : 1,
                width : '100%',
                bind : {
                    hidden : '{hideInstallPatchGrid}'
                },
                padding : 0,
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                items : [installPatchesGrid]
            }];
        	me.callParent();
        }
	});

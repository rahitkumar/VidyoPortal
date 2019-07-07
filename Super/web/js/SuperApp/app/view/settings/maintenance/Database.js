Ext.define('SuperApp.view.settings.maintenance.Database', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.database',
    width : '100%',
    autoDestroy : true,
    border : false,
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    title: {
        text: '<span class="header-title">'+l10n('database')+'</span>',
        textAlign :'center'
    },
    frame: true,
    initComponent : function() {
        var me = this;
        me.items = [
               {
                xtype : 'grid',
                width : '100%',
                reference : 'databaseGrid',
                emptyText : l10n('no-db-backups'),
                minHeight : 100,
                border : '1 0 1 0',
                margin : 0,
                padding : 0,
                allowDeselect : true,
                selModel : Ext.create('Ext.selection.CheckboxModel', {
                    injectCheckbox : 'first',
                    showHeaderCheckbox : false,
                    checkOnly : false,
                    mode : 'SINGLE',
                    allowDeselect : true,
                    toggleOnClick : true,
                    onHeaderClick: function (headerCt, header, e) {
                  	  if (header.isCheckerHd) {
                  		  //whenever user click on the header checkbox space if there is no data getting console error.since we don't need header checkbox for select all this fix will work
                  	      e.stopEvent();
                  	  }
                  }
                }),
                bind : {
                    store : '{databaseGridStore}'
                },
                tbar : [
					{
					    text : l10n('backup'),
					    tooltip : l10n('create-a-backup-archive-of-current-db-snapshot'),
					    iconCls : 'x-fa fa-database',
					    handler : 'databaseBackup'
					},{
						xtype: 'button',
					    text : l10n('upload'),
					    handler : 'databaseUploadWin',
                         iconCls : 'x-fa fa-upload',
					    tooltip : l10n('upload-the-local-file-to-server')
					},{
					    text : l10n('factory-defaults'),
					    tooltip : l10n('reset-to-factory-default'),
					    iconCls : 'x-fa fa-warning',
					    handler : 'databaseDefaultsConfrimation'
					},{
						xtype: 'button',
					    text : l10n('AdminLicense-refresh'),
					    iconCls:'x-fa fa-refresh',
					    handler : 'getDatabaseData',
					    tooltip : l10n('AdminLicense-refresh')
					}
                ],
                dockedItems: [{
                    xtype: 'toolbar',
                    dock: 'bottom',
                    items : [{
                    	xtype: 'button',
					    text : l10n('download'),
                         iconCls : 'x-fa fa-download',
					    handler : 'databaseDownload',
					    tooltip : l10n('download-the-selected-file-from-server')
					}, {
					    text : l10n('restore'),
					    iconCls : 'x-fa fa-history',
					    tooltip : l10n('restore-a-db-from-selected-backup-archive'),
					    handler : 'databaseRestoreConfirmation'
					}, {
					    text : l10n("delete"),
					    iconCls : 'x-fa fa-minus-circle',
					    tooltip : l10n('delete-the-selected-file-from-server'),
					    handler : 'databaseDeleteConfirmation'
					}]
                }],
                columns : [{
                    text : l10n('file-name'),
                    dataIndex : 'fileName',
                    align : 'left',
                    flex : 1
                }, {
                    text : l10n('creation-date'),
                    dataIndex : 'timestamp',
                    align : 'left',
                    sortable : false,
                    flex : 1
                }]
            }];
        this.callParent();
    }
});

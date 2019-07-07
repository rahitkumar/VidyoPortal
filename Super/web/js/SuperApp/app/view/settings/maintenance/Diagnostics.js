Ext.define('SuperApp.view.settings.maintenance.Diagnostics', {
    extend : 'Ext.panel.Panel',
    width : '100%',
    autoDestroy : true,
    border : false,
    alias : 'widget.diagnostics',
    defaultType : 'textfield',
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    title : {
    	text : '<span class="header-title">'+l10n('diagnostics')+'</span>',
    	textAlign : 'center'
    },
    frame: true,
    items : [
         {
            xtype : 'grid',
            width : '100%',
            reference : 'diagnosticslist',
            emptyText : l10n('no-data-for-diagnostics'),
            minHeight : 550,
            scrollable : true,
            allowDeselect: true,
            selModel : Ext.create('Ext.selection.CheckboxModel', {
                injectCheckbox : 'first',
                showHeaderCheckbox : false,
                checkOnly : false,
                mode : 'SINGLE',
                allowDeselect : true,
                onHeaderClick: function (headerCt, header, e) {
              	  if (header.isCheckerHd) {
              		  //whenever user click on the header checkbox space if there is no data getting console error.since we don't need header checkbox for select all this fix will work
              	      e.stopEvent();
              	  }
              }
                	
            }),
            border : false,
            bind : {
                store : '{diagnosticsStore}'
            },
            tbar : [
				{
				    text : l10n('run'),
				    tooltip : l10n('run-diagnostics'),
                       iconCls :'x-fa fa-play',
				    handler : 'onClickRunDiagnostics'
				}, {
					text : l10n('view'),
                    iconCls :'x-fa fa-file-text',
		            tooltip : l10n('view-diagnostics'),
				    handler : 'onClickViewDiagnostics'
				}, {
					text : l10n('download'),
                    iconCls :'x-fa fa-download',
		            tooltip : l10n('download-diagnostics'),
				    handler : 'onClickDownloadDiagnostics'
				},
				{
					xtype: 'button',
				    text : l10n('AdminLicense-refresh'),
				    iconCls:'x-fa fa-refresh',
				    handler : 'getDiagnosticsData',
				    tooltip : l10n('AdminLicense-refresh')
				}
            ],
            columns : [{
                text : l10n('file-name'),
                dataIndex : 'fileName',
                flex : 1
            }, {
                text : l10n('creation-date'),
                dataIndex : 'timestamp',
                flex : 1
            }]
      }]
});

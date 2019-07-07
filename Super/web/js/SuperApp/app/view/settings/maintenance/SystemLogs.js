Ext.apply(Ext.form.field.VTypes, {
    daterange: function(val, field) {
        var date = field.parseDate(val);

        if (!date) {
            return false;
        }
        if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
            var start = field.up('form').down('#' + field.startDateField);
            start.setMaxValue(date);
            //start.validate();
            this.dateRangeMax = date;
        }
        else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
            var end = field.up('form').down('#' + field.endDateField);
            end.setMinValue(date);
            //end.validate();
            this.dateRangeMin = date;
        }
        /*
         * Always return true since we're only using this vtype to set the
         * min/max allowed values (these are tested for after the vtype test)
         */
        return true;
    },

    daterangeText: 'Start date must be less than end date'
});


Ext.define('SuperApp.view.settings.maintenance.SystemLogs', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.systemlogs',
    border : false,
    title : {
        text : '<span class="header-title">'+l10n('system-logs')+'</span>',
        textAlign : 'center'
    },
    frame: true,
    items : [],
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    errorReader : Ext.create('Ext.data.XmlReader', {
        record : 'field',
        model : Ext.create("SuperApp.model.settings.Field"),
        success : '@success'
    }),
    initComponent : function() {
        var me = this,
            auditCombo,
            auditExportBtn,
            vidyoPortalLogsExportBtn;

        auditCombo = Ext.create('Ext.form.ComboBox', {
            fieldLabel : l10n('audit-logs-field-label'),
            labelWidth : 200,
            name : 'auditperiod',
            margin : 5,
            reference : 'auditperiod',
            editable : false,
            allowBlank : false,
            bind : {
                store : '{auditLogComboStore}'
            },
            valueField : 'auditCode',
            displayField : 'auditName',
            triggerAction : 'all',
            mode : 'local'
        });

        me.items = [
               {
                xtype : 'form',
                
                layout : {
                    type : 'hbox',
                    pack : 'center'
                },
               
                items : [{
                    xtype : 'datefield',
                    vtype: 'daterange',
                    margin : 10,
                    labelWidth : 65,
                    fieldLabel :l10n('start-date'),
                    allowBlank : false,
                    format : 'Y-m-d',
                    submitFormat : 'Y-m-d',
                    msgTarget : 'qtip',
                    name : 'startDate',
                    itemId: 'startDate',
                    endDateField : 'endDate',
                    reference : 'startDate',
                    value : new Date(),
                    maxValue: new Date(),
                    listeners : {
                        invalid : 'invalidDate',
                        valid : 'validDate'
                    }
                }, {
                    xtype : 'datefield',
                    vtype: 'daterange',
                    margin : 10,
                    labelWidth : 60,
                    fieldLabel :l10n('end-date'),
                    allowBlank : false,
                    format : 'Y-m-d',
                    submitFormat : 'Y-m-d',
                    msgTarget : 'qtip',
                    name : 'endDate',
                    itemId: 'endDate',
                    startDateField : 'startDate',
                    reference : 'endDate',
                    value : new Date(),
                    maxValue: new Date(),
                    listeners : {
                        invalid : 'invalidDate',
                        valid : 'validDate'
                    }
                }],
                buttons : [
					'->', {
					    xtype : 'button',
					    text : l10n('download-vidyoportal-logs'),
					    tooltip : l10n('click-to-download-file'),
					    formBind : true,
					    disabled : true,
					    handler:'vidyoPortalLogsExportBtn'
					}, {
					    xtype : 'button',
					    text : l10n('router-config-download-audit-logs'),
					    tooltip : l10n('click-to-download-file'),
					    formBind : true,
					    disabled : true,
					    handler : 'auditExportBtn'
					}, '->'       
                ]
            },
             {
                xtype : 'form',
                  trackResetOnLoad: true,
                height:150,
                padding:10,
                frame:true,
                title:l10n('log-aggregation'),
                reference:'logfqdnform',
                layout : {
                    type : 'hbox',
                    pack : 'center'
                },               
              
                items : [{
                xtype : 'textfield',
                fieldLabel : l10n('log-aggregation-server-FQDN'),
                vtype : 'FQDNValidate',
               
                labelWidth : 300,
                width : 600,
                name : 'logfqdn',
                reference:'logfqdn'
            }],buttonAlign:'center',
                buttons : [
					{
					    xtype : 'button',
					    text : l10n('save'),					   
					    formBind : true	,
                        listeners: {
                click: 'onClickLogFQDNSave'
            }				   
					}, {
					    xtype : 'button',
					    text : l10n('cancel'),					   
                         handler: 'resetFQDNLog'
					},     
                ],
                 listeners:{
                     afterrender:'loadLogFQDNForm'
                },
  
            }
        ,{
            xtype : 'grid',
            reference : 'sysUpgradeLogsGrid',
            width : '100%',
            border : 0,
            style : {
                border : '0 0 0 0'
            },
            selType : 'checkboxmodel',
            allowDeselect : true,
            selModel : Ext.create('Ext.selection.CheckboxModel', {
                injectCheckbox : 'first',
                showHeaderCheckbox : false,
                checkOnly : true,
                mode : 'SINGLE'
            }),
            bind : {
                store : '{sysUpgradeLogGridStore}'
            },
            title : '<span class="header-title">'+l10n('maintenance-install-logs')+'</span>',
            titleAlign : 'center',
            componentCls : 'mypaneltransparent',
            columns : [{
                text : l10n('file-name'),
                dataIndex : 'fileName',
                flex : 1
            }, {
                text : l10n('creation-date'),
                dataIndex : 'timestamp',
                flex : 1
            }],
            dockedItems: [{
                xtype: 'toolbar',
                items : [{
                    xtype : 'button',
                    text : l10n('download'),
                    tooltip : l10n('download-the-selected-file-from-server'),
                    listeners : {
                        click : 'sysUpgradeDownload'
                    }
                },{
					xtype: 'button',
				    text : l10n('AdminLicense-refresh'),
				    iconCls:'x-fa fa-refresh',
				    handler : 'getSystemLogsData',
				    tooltip : l10n('AdminLicense-refresh')
				}]
            }]
        }];

        me.callParent(arguments);
    }
});

Ext.define('SuperApp.view.settings.CDRaccess', {
    extend : 'Ext.panel.Panel',
    requires : ['SuperApp.model.settings.CDRModel', 'SuperApp.view.settings.CDRAccess.CDRExportPurgeController', 'SuperApp.view.settings.CDRAccess.CDRExportPurgeViewModel'],
    width : '100%',
    frame: true,
    alias : 'widget.cdraccess',
    autoDestroy : true,
    title : {
        text : '<span class="header-title">'+l10n('cdr-access')+'</span>',
        textAlign : 'center'
    },
    reference : 'cdrAccessPanel',
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    items : [{
    	xtype: 'form',
    	reference : 'cdrAccessForm',
    	layout : {
            type : 'vbox',
            align : 'center'
        },
        errorReader : Ext.create('Ext.data.XmlReader', {
            record : 'field',
            model : Ext.create("SuperApp.model.settings.Field"),
            success : '@success'
        }),
        standardSubmit : false,
        buttonAlign : 'center',
        margin : '10, 0, 0, 0',
        bodyPadding: 5,
        items : [
        {
            xtype: 'fieldset',
            border: false,
            layout: {
            	type :'vbox',
            	align : 'center',
            	pack: 'center'
            },
            width : '100%',
            items: [{
                xtype : 'checkboxfield',
                fieldLabel : l10n('cdr-collection'),
                reference : 'cdrEnabled',
                name : 'enabled',
                inputValue: '1',
                uncheckedValue: '0',
                bind : {
                	value : '{enabled}'
                },
                labelWidth : 200,
                width : 500
            },{
                xtype : 'checkboxfield',
                fieldLabel : l10n('cdr-database-access-control'),
                reference : 'cdrAccess',
                name : 'allowdeny',
                bind : {
                	value : '{allowdeny}',
                    hidden:'{fipsEnabled}'
                },
                inputValue: '1',
                uncheckedValue: '0',
                labelWidth : 200,
                width : 500,
                listeners : {
                	change: function(cb, checked) {
                		var fieldset = cb.ownerCt;
                		Ext.Array.forEach(fieldset.query('textfield'), function(field) {
                            field.setDisabled(!checked);
                            field.el.animate({opacity: !checked ? 0.3 : 1});
                        });
                		Ext.Array.forEach(fieldset.query('checkbox[reference = cdrAllowDelete]'), function(field) {
                            field.setDisabled(!checked);
                            field.el.animate({opacity: !checked ? 0.3 : 1});
                        });
                	}
                }
            },
            {
                xtype : 'textfield',
                fieldLabel : l10n('username'),
                name : 'id',
                reference : 'id',
                labelAlign : 'left',
                labelWidth : 200,
                width : 500,
                readOnly : true,
                disabled : true,
                bind : {
                	value : '{id}',
                      hidden:'{fipsEnabled}'
                	//disabled : '{!allowdeny}'
                }
            }, {
                xtype : 'textfield',
                inputType : 'password',
                name : 'password',
                fieldLabel : l10n('access-password'),
                labelAlign : 'left',
                reference : 'password',
                labelWidth : 200,
                width : 500,
                maskRe : /[^ ]/,
                disabled : true,
                bind : {
                	value : '{password}'       ,
                      hidden:'{fipsEnabled}'         	
                }
            }, {
                xtype : 'textfield',
                fieldLabel : l10n('ip-or-hostname'),
                name : 'ip',
                reference : 'ip',
                disabled : true,
                bind : {
                	value : '{ip}',
                      hidden:'{fipsEnabled}'
                },
                labelAlign : 'left',
                labelWidth : 200,
                width : 500
            }, {
                xtype : 'checkboxfield',
                fieldLabel : l10n('allow-delete'),
                checked:false,
                bind : {
                	value : '{allowdelete}',
                      hidden:'{fipsEnabled}'
                },
                disabled : true,
                inputValue: '1',
                uncheckedValue: '0',
                labelAlign : 'before',
                labelWidth : 200,
                width : 500,
                name : 'allowdelete',
                reference : 'cdrAllowDelete'
            }]
        }
        ],
        buttons : [{
            text : l10n('save'),
            formBind : true,
            disabled : true,
            handler : 'cdrAccessSave'
        }, {
            text : l10n('cancel'),
            handler : 'getCdrAccessData'
        }]
    },{
        xtype : 'form',
        title : {
            text : l10n('cdr-export-purge'),
            textAlign : 'center'
        },
        bodyPadding: 5,
        viewModel : {
            type : 'CDRExportPurgeViewModel'
        },
        margin : '5, 0, 0, 0',
        reference : 'cdrExportPurgeForm',
        errorReader : Ext.create('Ext.data.XmlReader', {
            record : 'field',
            model : Ext.create("SuperApp.model.settings.Field"),
            success : '@success'
        }),
        items : [{
            xtype : 'hidden',
            reference : 'oneallvalue',
            name : 'oneallvalue',
            value : 'all'
        }, {
            xtype : 'fieldset',
            title : l10n('tenant'),
            layout : 'hbox',
            items : [{
                xtype : 'radiogroup',
                vertical : true,
                columns : 1,
                name : 'oneall',
                margin : '10, 0, 10, 0',
                items : [{
                    boxLabel : l10n('one-tenant'),
                    name : 'oneall',
                    width : 100,
                    inputValue : 'one',
                    listeners : {
                        change : 'oneTenant'
                    }
                }, {
                    boxLabel : l10n('all-tenants'),
                    name : 'oneall',
                    inputValue : 'all',
                    checked : true,
                    listeners : {
                        change : 'allTenants'
                    }
                }]
            }, {
                xtype : 'combobox',
                fieldLabel : '',
                width : 300,
                disabled : true,
                reference : 'exportPurgeCombo',
                name : 'tenantName',
                margin : '10, 0, 10, 0',
                bind : {
                    store : '{tenantds}'
                },
                valueField : 'tenantID',
                displayField : 'tenantName',
                typeAhead : true,
                triggerAction : 'all',
                allowBlank : true,
                editable : true,
                forceSelection:true,
                queryMode : 'remote',
                queryParam: 'tenantName',
                selectOnFocus : true,
                resizable : false,
                minChars : 1
            }]
        }, {
            xtype : 'fieldset',
            title : l10n('period-of-time'),
            layout : 'hbox',
            items : [{
                xtype : 'hidden',
                reference : 'dateperiodvalue',
                name : 'dateperiodvalue',
                value : 'range'
            }, {
                xtype : 'radiogroup',
                columns : 3,
                margin : '10, 0, 10, 0',
                items : [{
                    boxLabel : l10n('date-range'),
                    name : 'dateperiod',
                    inputValue : 'range',
                    checked : true,
                    width : 100,
                    listeners : {
                        check : 'datefieldRadio'
                    }
                }, {
                    xtype : 'datefield',
                    width : 160,
                    margin : '0, 0, 10, 0',
                    allowBlank : false,
                    format : 'Y-m-d H:i:s',
                    submitFormat : 'Y-m-d H:i:s',
                    msgTarget : 'qtip',
                    name : 'startdate',
                    endDateField : 'enddate',
                    reference : 'startdate',
                    value : new Date(),
                    listeners : {
                        invalid : 'invalidDate',
                        valid : 'validDate'
                    }

                }, {
                    xtype : 'datefield',
                    width : 160,
                    margin : '0, 0, 0, 20',
                    allowBlank : false,
                    format : 'Y-m-d H:i:s',
                    submitFormat : 'Y-m-d H:i:s',
                    msgTarget : 'qtip',
                    name : 'enddate',
                    startDateField : 'startdate',
                    reference : 'enddate',
                    value : new Date(),
                    listeners : {
                        invalid : 'invalidDate',
                        valid : 'validDate'
                    }
                }]
            }]
        }],
        buttons : [{
            text : l10n('export'),
            reference : 'export',
            formBind : true,
            handler : 'exportBtnHandler'
        }, {
            text : l10n('purge'),
            reference : 'purge',
            handler : 'purgeBtnHandler'
        }],
        buttonAlign : 'center'
    }]
});

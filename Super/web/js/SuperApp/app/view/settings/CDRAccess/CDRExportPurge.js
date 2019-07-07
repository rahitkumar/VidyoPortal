Ext.define('Field', {
    extend : 'Ext.data.Model',
    fields : ['id', 'msg']
});

Ext.define('SuperApp.view.settings.CDRAccess.CDRExportPurge', {
    extend : 'Ext.window.Window',
    title : l10n('cdr-export-purge'),
    width : 600,
    floating : true,
    draggable : true,
    modal : true,
    border : true,
    layout : 'fit',
    closeAction : 'destory',
    controller : 'CDRExportPurgeController',
    requires : ['SuperApp.view.settings.CDRAccess.CDRExportPurgeController', 'SuperApp.view.settings.CDRAccess.CDRExportPurgeViewModel'],
    viewModel : {
        type : 'CDRExportPurgeViewModel'
    },
    items : [],
    initComponent : function() {
        var items = [{
            xtype : 'form',
            border : false,
            margin : 10,
            ui : 'footer',
            cls :'white-footer',
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
                    margin : 10,
                    name : 'oneall',
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
                    margin : 10,
                    disabled : true,
                    reference : 'exportPurgeCombo',
                    name : 'tenantName',
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
                    items : [{
                        boxLabel : l10n('date-range'),
                        name : 'dateperiod',
                        inputValue : 'range',
                        checked : true,
                        listeners : {
                            check : 'datefieldRadio'
                        }
                    }, {
                        xtype : 'datefield',
                        margin : 10,
                        width : 160,
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
                        margin : 10,
                        width : 160,
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
                handler : 'exportBtnHandler'
            }, {
                text : l10n('purge'),
                reference : 'purge',
                handler : 'purgeBtnHandler'
            }],
            buttonAlign : 'center'
        }];
        this.items = items;
        this.callParent();
    }
}); 
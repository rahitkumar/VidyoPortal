/**
 * @class CDRAccess
 */
Ext.define('AdminApp.view.settings.cdr.CDRAccess', {
    extend: 'Ext.form.Panel',
    alias: 'widget.cdraccess',
    controller: 'CDRExportPurgeController',
    viewModel: {
        type: 'CDRExportPurgeViewModel'
    },
    requires: [
        'AdminApp.view.settings.cdr.CDRExportPurgeController', 'AdminApp.view.settings.cdr.CDRExportPurgeViewModel'
    ],
    frame: false,
    height: 800,
    border: false,

    title: {
        text: '<span class="header-title">' + l10n('cdr-access') + '</span>',
        textAlign: 'center'
    },
    border: false,
    reference: 'cdrExportPurgeForm',
    errorReader: Ext.create('Ext.data.XmlReader', {
        record: 'field',
        model: Ext.create("AdminApp.model.Field"),
        success: '@success'
    }),
    items: [{
        xtype: 'fieldset',
         height: '100%',
      
        layout: {
            type: 'hbox',
            align: 'center',
            pack: 'center',
        },
        bodyPadding: 10,
        items: [{
            xtype: 'hiddenfield',
            reference: 'dateperiodvalue',
            name: 'dateperiod',
            value: 'range'
        }, {
            xtype: 'hiddenfield',
            reference: 'oneallvalue',
            name: 'oneall',
            value: 'one'
        }, {
            xtype: 'datefield',
            width: 160,
            margin: '0 10 3 3',
            allowBlank: false,
            format: 'Y-m-d H:i:s',
            submitFormat: 'Y-m-d H:i:s',
            msgTarget: 'qtip',
            name: 'startdate',
            endDateField: 'enddate',
            reference: 'startdate',
            value: new Date(),
            listeners: {
                invalid: 'invalidDate',
                valid: 'validDate'
            }

        }, {
            xtype: 'datefield',
            width: 160,
            allowBlank: false,
            format: 'Y-m-d H:i:s',
            submitFormat: 'Y-m-d H:i:s',
            msgTarget: 'qtip',
            name: 'enddate',
            startDateField: 'startdate',
            reference: 'enddate',
            value: new Date(),
            listeners: {
                invalid: 'invalidDate',
                valid: 'validDate'
            }
        }]
    }],
    buttonAlign: 'center',
    buttons: [{
        text: l10n('export'),
        reference: 'export',
        handler: 'exportBtnHandler'
    },
    {
        text: l10n('cancel'),
        reference: 'export',
        handler: 'cancelBtnHandler'
    }],


    listeners: {
        render: 'cdrExportPurgeRender'
    }
});
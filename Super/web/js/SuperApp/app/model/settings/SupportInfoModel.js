Ext.define('SuperApp.model.settings.SupportInfoModel', {
    extend: 'Ext.data.Model',
    fields: ['contactInfo'],
    proxy: {
        type: 'ajax',
        url:'contactinfo.ajax',
        actionMethods:  {create: "POST", read: "POST", update: "POST", destroy: "POST"},
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
});
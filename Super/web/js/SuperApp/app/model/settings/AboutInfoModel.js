Ext.define('SuperApp.model.settings.AboutInfoModel', {
    extend: 'Ext.data.Model',
    fields: ['aboutInfo'],
    proxy: {
        type: 'ajax',
        url:'aboutinfo.ajax',
        actionMethods:  {create: "POST", read: "POST", update: "POST", destroy: "POST"},
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
});

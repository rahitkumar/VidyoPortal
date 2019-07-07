Ext.define('SuperApp.model.settings.InviteTextModel', {
    extend: 'Ext.data.Model',
    fields: ['invitationEmailContent','voiceOnlyContent','webcastContent'],
    proxy: {
        type: 'ajax',
        url:'invitationsetting.ajax',
        actionMethods:  {create: "POST", read: "POST", update: "POST", destroy: "POST"},
        reader: {
            type: 'xml',
            record: 'row',
            rootProperty:'dataset'
        }
    }
});
Ext.define('SuperApp.model.settings.EventsNotifyServerModel',{
    extend :'Ext.data.Model',

    fields :[{
        name :'eventsNotificationEnabled',
        type :'string'
    },{
        name :'primaryServer',
        type : 'string'
    },{
        name :'primaryServerPort',
        type : 'string'
    },{
        name :'secondaryServer',
        type : 'string'
    },{
        name :'secondaryServerPort',
        type : 'string'
    }]
});

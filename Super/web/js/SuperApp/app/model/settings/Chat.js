Ext.define('SuperApp.model.settings.Chat',{
    extend :'Ext.data.Model',
     
    fields :[{
        name :'chatVidyoPortalLabel',
        mapping :'chat > chatVidyoPortalLabel'
    },{
        name :'chatVidyoPortalLabelDesc',
        mapping :'chat > chatVidyoPortalLabelDesc'
    },{
        name :'chatPublicDefaultLabel',
        mapping :'chat > chatPublicDefaultLabel'
    },{
        name :'chatPublicDefaultLabelDesc',
        mapping :'chat > chatPublicDefaultLabelDesc'
    },{
        name :'chatPrivateDefaultLabel',
        mapping :'chat > chatPrivateDefaultLabel'
    },{
        name :'chatPrivateDefaultLabelDesc',
        mapping :'chat > chatPrivateDefaultLabelDesc'
    },{
        name :'chatVidyoPortalAvailable',
        mapping :'chat > chatVidyoPortalAvailable',
        type : 'boolean'
    },{
        name :'chatDefaultPublicStatus',
        mapping :'chat > chatDefaultPublicStatus',
        type : 'boolean'
    },{
        name :'chatDefaultPrivateStatus',
        mapping :'chat > chatDefaultPrivateStatus',
        type : 'boolean'
    }]
});
